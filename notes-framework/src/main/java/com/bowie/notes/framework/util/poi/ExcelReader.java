package com.bowie.notes.framework.util.poi;

import com.bowie.notes.framework.entity.enumeration.ExcelSheetNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * excel 解析类
 *
 * @author Dax
 * @since 15 :27  2018/5/17
 */
@Slf4j
public final class ExcelReader {

    private ExcelReader() {
    }

    public static List<Map<String, Object>> sheetData(MultipartFile file, ExcelSheetNameEnum sheetNameEnum, ExcelConfig config) {
        try {
            log.debug("ExcelReader38--" + sheetNameEnum.sheetName());
            InputStream is = file.getInputStream();
            String filename = file.getOriginalFilename();
            if (StringUtils.isEmpty(filename)) {
                return null;
            }
            boolean isXls = filename.endsWith("xls");
            Map<String, List<Map<String, Object>>> sheetMap = ExcelReader.asyncReader(is, isXls, config, sheetNameEnum);
            log.debug("ExcelReader46--" + sheetNameEnum.sheetName());
            List<Map<String, Object>> sheetData = sheetMap.get(sheetNameEnum.sheetName());
            return sheetData;
        } catch (Exception e) {
            log.error("sheetData解析出错",e);
            return null;
        }
    }

    /**
     * 同步读取 异步读取.
     *
     * @param inputStream the input stream
     * @param flag        the flag
     * @param config      the config
     * @return the map
     * @throws IOException the io exception
     */
    public static Map<String, List<Map<String, Object>>> syncReader(InputStream inputStream, boolean flag, ExcelConfig config) throws IOException {

        Workbook wb = flag ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        Map<String, List<Map<String, Object>>> data = new HashMap<>(16);
        Iterator<Sheet> sheetIterator = wb.sheetIterator();
//        遍历所有 sheet
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            sheetReader(sheet, data, config);
        }
        return data;
    }

    /**
     * 多线程读取数据.
     * excel的每个sheet 会被独立的线程来解析过滤 处理
     *
     * @param inputStream the input stream
     * @param flag        the flag
     * @param config      the config
     * @return the map
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    public static Map<String, List<Map<String, Object>>> asyncReader(InputStream inputStream, boolean flag, final ExcelConfig config, ExcelSheetNameEnum sheetNameEnum) throws ExecutionException, InterruptedException, IOException {
        Workbook workbook = flag ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        if (sheetNameEnum != null) {
            Sheet sheet = workbook.getSheet(sheetNameEnum.sheetName());
            Map<String, List<Map<String, Object>>> sheetData = new HashMap<>(16);
            sheetReader(sheet, sheetData, config);
            return sheetData;
        }
        Map<String, List<Map<String, Object>>> data = new HashMap<>(16);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();

        int sheetsCount = workbook.getNumberOfSheets();
        ExecutorService executorService = Executors.newFixedThreadPool(sheetsCount);
        List<Future<Map<String, List<Map<String, Object>>>>> futures = new ArrayList<>(sheetsCount);

        while (sheetIterator.hasNext()) {
            final Sheet sheet = sheetIterator.next();
            Callable<Map<String, List<Map<String, Object>>>> task = () -> {
                Map<String, List<Map<String, Object>>> sheetData = new HashMap<>(16);
                sheetReader(sheet, sheetData, config);
                return sheetData;
            };
            Future<Map<String, List<Map<String, Object>>>> future = executorService.submit(task);
            futures.add(future);
        }

        for (Future<Map<String, List<Map<String, Object>>>> future : futures) {
            data.putAll(future.get());
        }
        executorService.shutdown();
        return data;

    }

    /**
     * sheet解析
     *
     * @param sheet
     * @param sheetData sheet解析数据 包括合法   不合法
     * @param config
     */
    private static void sheetReader(Sheet sheet, Map<String, List<Map<String, Object>>> sheetData, ExcelConfig config) {
        String sheetName = sheet.getSheetName();
        sheetName = sheetName.toLowerCase();
        log.debug("sheetReader132--" + sheetName);
        int rowStart = sheet.getFirstRowNum();
        int rowEnd = sheet.getLastRowNum();
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> errorList = new ArrayList<>();
        //外层循环标识 用于跳过
        //遍历row
        for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if(rowNum >= config.getRowNum()){
                int lastColumn = row.getLastCellNum();
                int index = 0;
                Map<String, Object> map = new HashMap<>(16);
                //遍历cell
                boolean router = true;
                for (int cn = 0; cn < lastColumn; cn++) {
                    Cell cell = row.getCell(cn,Row.RETURN_NULL_AND_BLANK);

                    String val = cellReader(cell);
                    String key = config.getColumnNames()[index];
                    CellRule rule = config.getRules().get(key);
                    boolean checked = Validator.checkVal(val, rule);

                    if (!checked) {
                        router = false;
                    }
                    map.put(key, val);

                    index++;
                }
                if (router) {
                    list.add(map);
                } else {
                    errorList.add(map);
                }
            }
        }
        //有效 集合 错误集合分类 被传递出去
        if (list.size() > 0) {
            sheetData.put(sheetName, list);
        }
        if (errorList.size() > 0) {
            String key = "error" + sheetName;
            sheetData.put(key, errorList);
        }
    }


    /**
     * 解析 cell 规则
     *
     * @param cell cell
     * @return 去除空格 cell String
     */
    private static String cellReader(Cell cell) {
        String cellValue;
        if (cell != null) {
            // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
            switch (cell.getCellType()) {
                case 0:
                    //0代表日期
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        Date d = cell.getDateCellValue();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cellValue = format.format(d);
                    }else{

                        DecimalFormat df = new DecimalFormat("####################.###");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                case 1:
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case 2:
                    cellValue = String.valueOf(cell.getDateCellValue());
                    break;
                case 4:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case 5:
                    cellValue = String.valueOf(cell.getErrorCellValue());
                    break;
                default:
                    cellValue = "";
                    break;
            }
        } else {
            cellValue = "";
        }
        return cellValue.trim();
    }
}