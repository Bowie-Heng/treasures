package com.bowie.notes.framework;


import cn.hutool.core.collection.CollectionUtil;
import com.bowie.notes.framework.entity.enumeration.ExcelSheetNameEnum;
import com.bowie.notes.framework.util.poi.CellRule;
import com.bowie.notes.framework.util.poi.ExcelConfig;
import com.bowie.notes.framework.util.poi.ExcelReader;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import static com.bowie.notes.framework.util.poi.CreateCellUtil.*;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PoiUtilsTests {

    @Test
    public void testRead() throws Exception {
        //正常情况下这个file是从Mvc的Controller层直接获取的，这里伪造一下方便验证
        MultipartFile file = new CommonsMultipartFile(new DiskFileItem("fieldName","json",true,"fileName"
        ,1,new File("path")));

        //按照config中的配置，读为一个list<Map>格式的数据，与config方法中的cols各个字段对应
        List<Map<String, Object>>  sheetData = ExcelReader.sheetData(file, ExcelSheetNameEnum.SHEET_ONE, config());
        System.out.println(sheetData);
    }


    @Test
    public void testWrite() throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("基础数据-小区");
        createCommunityTitle(workbook, sheet);
        List<Integer> createCellList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(createCellList)) {
            for (int i = 0; i < createCellList.size(); i++) {
                Integer c = createCellList.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                row.setHeightInPoints(18);
                row.createCell(0).setCellValue(c);
                row.createCell(1).setCellValue(c);
                row.createCell(2).setCellValue(c);
                row.createCell(3).setCellValue(c);
                row.createCell(4).setCellValue(c);
                row.createCell(5).setCellValue(c);
                row.createCell(6).setCellValue(c);
                row.createCell(7).setCellValue(c);
                row.createCell(8).setCellValue(c);
                row.createCell(9).setCellValue(c);
                row.createCell(10).setCellValue(c);
                row.createCell(11).setCellValue(c);
                row.createCell(12).setCellValue(c);
                row.createCell(13).setCellValue(c);
                row.createCell(14).setCellValue(c);
            }
        }

        String fileName = "基础数据-小区.xlsx";

        try {
            //生成excel文件
            buildExcelFile(fileName, workbook);

            //浏览器下载excel
            //这个response对应http请求返回的response
//            buildExcelDocument(fileName, workbook, response);
        } catch (Exception e) {
            //ignore
        }
    }

    private void createCommunityTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
        HSSFCellStyle normalStyle = workbook.createCellStyle();
        normalStyle.setAlignment(CellStyle.ALIGN_CENTER);
        normalStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        HSSFRow row = createNewRowCell(sheet, 0, 0, "小区名称", normalStyle);
        createCell(row, 1, "小区编号", normalStyle);
        createCell(row, 2, "所属街道办", normalStyle);
        createCell(row, 3, "所属社区", normalStyle);
        createCell(row, 4, "小区户数", normalStyle);
        createCell(row, 5, "已入驻户数", normalStyle);
        createCell(row, 6, "居民数量", normalStyle);
        createCell(row, 7, "小区地址", normalStyle);
        createCell(row, 8, "楼栋数", normalStyle);
        createCell(row, 9, "单元数", normalStyle);
        createCell(row, 10, "添加时间", normalStyle);
        createCell(row, 11, "运营公司", normalStyle);
        createCell(row, 12, "是否示范区", normalStyle);
        createCell(row, 13, "负责人姓名", normalStyle);
        createCell(row, 14, "负责人电话", normalStyle);

    }


    private ExcelConfig config() {
        ExcelConfig excelConfig = new ExcelConfig();
        //对应excel 中的所有字段 即使数据库没有对应的字段也需要指定  顺序必须跟表左右一致
        String[] cols = {"communityName", "communityCode", "streetName", "streetId", "houseNum", "enterHouseNum",
                "liveNum", "addressName", "lng", "lat", "buildNum", "unitNum", "propertyCompany", "companyName", "committeeId", "personInChargeName", "phone"};
        //字段校验规则  不需要校验 不声明即可
        Map<String, CellRule> ruleMap = new HashMap<>(16);
        excelConfig.setRules(ruleMap);
        excelConfig.setColumnNames(cols);
        //excel 数据（非表头）起止行 行号-1
        excelConfig.setRowNum(1);
        return excelConfig;
    }
}
