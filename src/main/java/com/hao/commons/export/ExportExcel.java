package com.hao.commons.export;

import com.hao.util.base.ValidateException;
import jodd.util.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExportExcel.java
 * @Description 功能描述：导出excel
 * @author 吴昊 2018年2月26日17:06:17
 */
public class ExportExcel {

    /** HSSFWorkbook工作表对象 **/
    private static HSSFWorkbook  workbook                   = null;
    /** excel头默认样式 **/
    private static HSSFCellStyle titleDefaultStyle          = null;
    /** excel头加红色样式 **/
    private static HSSFCellStyle titleRedStyle              = null;
    /** excel内容样式 **/
    private static HSSFCellStyle dataStyle                  = null;
    /** excel首行高度 **/
    private static final short   EXCEL_TITLE_ROW_HEIGHT     = 500;
    /** excel行高度 **/
    private static final short   EXCEL_ROW_HEIGHT           = 400;
    /** 生成excel列每个字符宽度 **/
    private static final short   EXCEL_COLUMN_WIDTH_OF_CHAR = 300;
    /** 生成excel最小列宽 **/
    private static final short   EXCEL_COLUMN_MIN_WIDTH     = 4500;
    /** 字体大小 **/
    private static final short   FONT_SIZE                  = 8;
    /** 字体 **/
    private static final String  FONT_NAME                  = "楷体";

    /**
     * @Description 方法描述：导出excel
     * @author 吴昊 2018年2月26日17:11:03
     * @param response HttpServletResponse响应体
     * @param exportModel 导出的数据模型
     * @param fileName 的哦出文件名称
     * @throws ValidateException
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, ExportModel exportModel,
                                   String fileName) throws ValidateException, Exception {

        // excel文件对象
        workbook = new HSSFWorkbook();
        // 工作表对象
        HSSFSheet sheet = workbook.createSheet(fileName);
        // 设置列头样式
        setTitleCellStyles(workbook, sheet);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(1, true);
        // 设置数据样式
        setDataCellStyles(workbook, sheet);
        List<ExcelHeadModel> excelHeadModels = makeExcelTmp(exportModel);

        Map<String, String> mapHeads = new HashMap<>();
        for (String headName : exportModel.getHeadNames().split(",")) {
            mapHeads.put(headName, headName);
        }
        // 创建一行列头数据
        createAppRowHead(sheet, 2, fileName, excelHeadModels);

        // 从第二行开始
        // 创建一行数据，默认生成EXCEL_ROW_NUM行空数据
        int i = 3;
        for (ExportDataRow exportDataRow : exportModel.getDataRowList()) {
            createAppRow(sheet, excelHeadModels, i, exportDataRow, mapHeads, i);
            i++;
        }

        // 生成输出文件
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);

        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition",
                           "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));

        ServletOutputStream out = response.getOutputStream();

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);

            byte[] buff = new byte[2048];
            int bytesRead;

            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }

        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }

    }

    /**
     * @Description 方法描述： 设置列头样式
     * @author leon 2017年11月20日 下午9:50:57
     * @param workbook
     * @param sheet
     */
    private static void setTitleCellStyles(HSSFWorkbook workbook, HSSFSheet sheet) {
        titleDefaultStyle = workbook.createCellStyle();

        // 设置边框
        titleDefaultStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleDefaultStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleDefaultStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleDefaultStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置背景色
        titleDefaultStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        titleDefaultStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 设置居中
        // 水平居中
        titleDefaultStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 垂直居中
        titleDefaultStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName(FONT_NAME);
        // 设置字体大小
        font.setFontHeightInPoints(FONT_SIZE);
        // 粗体显示
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        // 选择需要用到的字体格式
        titleDefaultStyle.setFont(font);
        // 设置自动换行
        titleDefaultStyle.setWrapText(false);
    }

    /**
     * @Description 方法描述： 设置数据样式
     * @author leon 2017年11月20日 下午9:50:42
     * @param workbook
     * @param sheet
     */
    private static void setDataCellStyles(HSSFWorkbook workbook, HSSFSheet sheet) {
        dataStyle = workbook.createCellStyle();

        // 设置边框
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置居中
        // 水平居中
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 垂直居中
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName(FONT_NAME);
        // 粗体显示
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体大小
        font.setFontHeightInPoints(FONT_SIZE);
        // 选择需要用到的字体格式
        dataStyle.setFont(font);
        // 设置自动换行
        dataStyle.setWrapText(false);
    }

    /**
     * 方法描述:创建一列应用列头
     * 
     * @author leon 2017年11月20日 下午9:50:09
     * @param sheet
     * @param rowIndex 行号
     * @param fileName 文件名
     * @param excelHeadModels 列头集合
     */
    private static void createAppRowHead(HSSFSheet sheet, int rowIndex, String fileName,
                                        List<ExcelHeadModel> excelHeadModels) {

        HSSFRow rowTitle = sheet.createRow(rowIndex - 2);
        HSSFCell serialNumberCellTitle = rowTitle.createCell(0);
        // excel标题
        serialNumberCellTitle.setCellValue(fileName);
        serialNumberCellTitle.setCellStyle(titleDefaultStyle);
        rowTitle.setHeight(EXCEL_TITLE_ROW_HEIGHT);

        sheet.addMergedRegion(new Region(0, (short) 0, 0, ((short) excelHeadModels.size())));
        HSSFRow row = sheet.createRow(rowIndex - 1);
        // 创建列
        // 0.序号
        HSSFCell serialNumberCell = row.createCell(0);
        serialNumberCell.setCellValue("序号");
        serialNumberCell.setCellStyle(titleDefaultStyle);
        int index = 1;

        for (ExcelHeadModel excelHeadModel : excelHeadModels) {
            row.setHeight(EXCEL_TITLE_ROW_HEIGHT);
            // 0.序号
            HSSFCell cell = row.createCell(index);
            cell.setCellValue(excelHeadModel.getHeadName().trim());
            // 设置红色为必填项
            if (excelHeadModel.getHeadName().trim().contains("*")) {
                cell.setCellStyle(titleRedStyle);
            } else {
                cell.setCellStyle(titleDefaultStyle);
            }
            index++;

        }

    }

    /**
     * @Description 方法描述： 创建一列数据
     * @author 吴昊 2018年2月26日17:13:15
     * @param sheet
     * @param excelHeadModels
     * @param num
     * @param exportDataRow
     * @param mapHeads
     * @param rowIndex
     * @throws Exception
     */
    private static void createAppRow(HSSFSheet sheet, List<ExcelHeadModel> excelHeadModels, int num,
                                     ExportDataRow exportDataRow, Map<String, String> mapHeads,
                                     int rowIndex) throws Exception {
        // 在第一行第一个单元格，插入下拉框
        HSSFRow row = sheet.createRow(rowIndex - 1);
        // 0.序号
        HSSFCell serialNumberCell = row.createCell(0);
        serialNumberCell.setCellValue(num - 2);
        serialNumberCell.setCellStyle(titleDefaultStyle);
        int index = 1;
        for (String exportDataColumn : exportDataRow.getDataColumnList()) {
            // 单元格值序号
            HSSFCell cell = row.createCell(index);
            // 单元格值
            if (StringUtil.isNotBlank(exportDataColumn) && !StringUtil.equals(exportDataColumn, "null")) {
                cell.setCellValue(exportDataColumn);
                row.setHeight(EXCEL_ROW_HEIGHT);
                cell.setCellStyle(dataStyle);
                int width = exportDataColumn.getBytes().length * EXCEL_COLUMN_WIDTH_OF_CHAR;
                if (width < EXCEL_COLUMN_MIN_WIDTH) {
                    sheet.setColumnWidth(index, EXCEL_COLUMN_MIN_WIDTH);
                } else {
                    sheet.setColumnWidth(index, width);
                }

            } else {
                cell.setCellValue("");
                row.setHeight(EXCEL_ROW_HEIGHT);
                cell.setCellStyle(dataStyle);
                sheet.setColumnWidth(index, EXCEL_COLUMN_MIN_WIDTH);
            }

            index++;
        }
        row.setHeight(EXCEL_TITLE_ROW_HEIGHT);

    }

    /**
     * @Description 方法描述： 组装列头
     * @author 吴昊 2018年2月26日17:12:31
     * @param exportModelVo
     * @return
     */
    private static List<ExcelHeadModel> makeExcelTmp(ExportModel exportModelVo) {
        List<ExcelHeadModel> excelHeadModels = new ArrayList<ExcelHeadModel>();
        // excel头
        String[] heads = exportModelVo.getHeadNames().split(",");
        for (String head : heads) {
            ExcelHeadModel excelHeadModel = new ExcelHeadModel();
            excelHeadModel.setHeadName(head);
            excelHeadModels.add(excelHeadModel);
        }

        return excelHeadModels;

    }
}
