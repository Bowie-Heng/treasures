package com.bowie.notes.framework.util.poi;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author luonan
 * @date 2020-04-16 16:10
 */
public class CreateCellUtil {
    /**
     * 创建第一个合并单元格
     *
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param sheet
     * @param row
     * @param column
     * @param valueName
     * @param style
     */
    public static HSSFRow createFirstMergeCell(int firstRow, int lastRow, int firstCol, int lastCol, HSSFSheet sheet, int row, int column, String valueName, HSSFCellStyle style) {
        CellRangeAddress serial = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(serial);
        HSSFRow hssfRow = sheet.createRow(row);
        HSSFCell serialCell = hssfRow.createCell(column);
        serialCell.setCellValue(valueName);
        serialCell.setCellStyle(style);
        return hssfRow;
    }

    /**
     * 创建合并单元格
     *
     * @param sheet
     * @param row       行
     * @param style     风格
     * @param firstRow  起始行
     * @param lastRow   最终行
     * @param firstCol  起始列
     * @param lastCol   最终列
     * @param column    列
     * @param valueName 单元格内容
     */
    public static void createMergeCell(int firstRow, int lastRow, int firstCol, int lastCol, HSSFSheet sheet, HSSFRow row, int column, String valueName, HSSFCellStyle style) {
        CellRangeAddress remarks = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(remarks);
        HSSFCell remarksCell = row.createCell(column);
        remarksCell.setCellValue(valueName);
        remarksCell.setCellStyle(style);
    }


    /**
     * 新起一行创建单元格
     * @param sheet
     * @param row
     * @param column
     * @param valueName
     * @param style
     */
    public static HSSFRow createNewRowCell(HSSFSheet sheet, int row,int column,String valueName,HSSFCellStyle style){
        HSSFRow hssfRow = sheet.createRow(row);
        HSSFCell knowCheckCell = hssfRow.createCell(column);
        knowCheckCell.setCellValue(valueName);
        knowCheckCell.setCellStyle(style);
        return hssfRow;
    }

    /**
     * 创建单元格
     *
     * @param row       行
     * @param style     风格
     * @param column    列
     * @param valueName 单元格内容
     */
    public static void createCell(HSSFRow row, int column, String valueName, HSSFCellStyle style) {
        HSSFCell rateNumCell = row.createCell(column);
        rateNumCell.setCellValue(valueName);
        rateNumCell.setCellStyle(style);
    }

    /**
     * 生成excel文件
     *
     * @param filename
     * @param workbook
     * @throws Exception
     */
    public static void buildExcelFile(String filename, HSSFWorkbook workbook) throws Exception {
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    /**
     * 浏览器下载excel
     *
     * @param filename
     * @param workbook
     * @param response
     * @throws Exception
     */
    public static void buildExcelDocument(String filename, HSSFWorkbook workbook, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * excel中创建图片
     */
    public static void drawPictureInfoExcel(HSSFWorkbook wb, HSSFPatriarch patriarch, int startColumnIndex, int startRowIndex, int endColumnIndex, int endRowIndex, String pictureUrl) {
        try {
            //anchor主要用于设置图片的属性
            //前面四个分别对应单元格内的坐标（分辨率），先写死
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 500, 75, (short) startColumnIndex, startRowIndex, (short) endColumnIndex, endRowIndex);
            //Sets the anchor type （图片在单元格的位置）
            //0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.
            anchor.setAnchorType(0);
            URL url = new URL(pictureUrl);
            BufferedImage bufferImg = ImageIO.read(url);
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            byte[] data = byteArrayOut.toByteArray();
            patriarch.createPicture(anchor, wb.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
