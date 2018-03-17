package com.hao.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hao.util.base.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hao.util.base.ValidateException;

/**
 * 类ImportExcelUtil.java的实现描述：excel导入处理分析数据
 * 
 * @author 吴昊 2018年3月17日09:01:15
 */
public class ImportExcelUtil {

    /**
     * 批量导入的最小数据条数：1
     */
    private static final int IMPORT_MIN_ROW_NO    = 1;
    /**
     * 批量导入的最小工作组个数：1
     */
    private static final int IMPORT_MIN_SHEET_NO  = 1;
    /**
     * 批量导入的最大工作组个数：1
     */
    private static final int IMPORT_MAX_SHEET_NO  = 1;
    /**
     * 批量导入开始读取的行号：3,索引是2
     */
    private static final int READ_START_ROW_NO    = 2;
    /**
     * 批量导入开始读取的列号：2
     */
    private static final int READ_START_COLUMN_NO = 2;

    /**
     * 说明方法描述：分析处理excel数据，返回list
     * 
     * @param excelPath
     * @param importMaxRowNo 允许导入的数据量（行数）
     * @return
     * @throws Exception
     * @throws ValidateException
     * @time 2018年3月17日09:01:24
     * @author 吴昊
     */
    public static List<Map<Integer, Object>> dealDataForExcel(String fileName, File file,
                                                              int importMaxRowNo) throws Exception, ValidateException {
        if (StringUtil.isBlank(fileName)) {
            throw new ValidateException("FUL0007");
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (StringUtil.isBlank(suffix)) {
            throw new ValidateException("FUL0013");
        }
        // 判断格式是否合法
        List<Map<Integer, Object>> list = null;
        if (StringUtil.equals(suffix, "xls")) {
            list = dealDataForXls(file, importMaxRowNo);
        } else if (StringUtil.equals(suffix, "xlsx")) {
            list = dealDataForXlsx(file, importMaxRowNo);
        } else {
            throw new ValidateException("FUL0013");
        }
        return list;
    }

    /**
     * 分析excel的内容,2007以下的版本xlsx
     * 
     * @param path
     * @return
     */
    private static List<Map<Integer, Object>> dealDataForXlsx(File file, int importMaxRowNo) throws Exception,
                                                                                             ValidateException {
        List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
        // 工作簿
        XSSFWorkbook hwb = null;
        hwb = new XSSFWorkbook(new FileInputStream(file));
        if (hwb != null) {
            // 循环读取所有sheet
            int sheetCount = hwb.getNumberOfSheets();
            if (sheetCount < IMPORT_MIN_SHEET_NO) {
                throw new ValidateException("FUL0014");
            }
            for (int sheetIndex = 0; sheetIndex < IMPORT_MAX_SHEET_NO; sheetIndex++) {
                // 获取到第sheetIndex个sheet中数据
                XSSFSheet sheet = hwb.getSheetAt(sheetIndex);
                if (sheet != null) {
                    int totalRowNo = sheet.getLastRowNum() - 1;
                    if (totalRowNo < IMPORT_MIN_ROW_NO) {
                        throw new ValidateException("FUL0014");
                    }
                    if (totalRowNo > importMaxRowNo) {
                        throw new ValidateException("批量导入每次最多只能导入" + importMaxRowNo + "条数据");
                    }
                    for (int i = READ_START_ROW_NO; i < sheet.getLastRowNum(); i++) {// 第三行开始取值，第一行为标题行
                        XSSFRow row = sheet.getRow(i); // 获取到第i列的行数据(表格行)
                        if (row != null) {
                            Map<Integer, Object> map = new HashMap<Integer, Object>();
                            for (int j = READ_START_COLUMN_NO - 1; j < row.getLastCellNum(); j++) {// 从第二列开始取值，第一列为序号
                                XSSFCell cell = row.getCell(j); // 获取到第j行的数据(单元格)
                                if (cell != null) {
                                    String cellValue = "";
                                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC
                                        || cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                                        HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                                        cellValue = dataFormatter.formatCellValue(cell);
                                    } else {
                                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                        cellValue = cell.getStringCellValue();
                                    }
                                    if (StringUtil.isNotBlank(cellValue)) {
                                        // 解决1234.0 去掉后面的.0
                                        String[] item = cellValue.split("[.]");
                                        if (1 < item.length && "0".equals(item[1])) {
                                            cellValue = item[0];
                                        }
                                        map.put(j, cellValue);
                                    }
                                }
                                // else {
                                // log.info("解析数据---------->第" + (i + 1) + "行，第" + j + "个单元格值为：空");
                                // }
                            }
                            if (map != null && map.size() > 0) {
                                list.add(map);
                            }
                        }
                    }
                }
            }
        }
        if (list == null || list.size() == 0) {
            throw new ValidateException("FUL0014");
        }
        return list;
    }

    /**
     * 分析excel的内容,2007以上的版本xls
     * 
     * @param path
     * @return
     */
    private static List<Map<Integer, Object>> dealDataForXls(File file, int importMaxRowNo) throws Exception,
                                                                                            ValidateException {
        List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
        // 工作簿
        HSSFWorkbook hwb = null;
        hwb = new HSSFWorkbook(new FileInputStream(file));
        if (hwb != null) {
            // 循环读取所有sheet
            int sheetCount = hwb.getNumberOfSheets();
            if (sheetCount < IMPORT_MIN_SHEET_NO) {
                throw new ValidateException("FUL0014");
            }
            for (int sheetIndex = 0; sheetIndex < IMPORT_MAX_SHEET_NO; sheetIndex++) {
                // 获取到第sheetIndex个sheet中数据
                HSSFSheet sheet = hwb.getSheetAt(sheetIndex);
                if (sheet != null) {
                    int totalRowNo = sheet.getLastRowNum() - 1;
                    if (totalRowNo < IMPORT_MIN_ROW_NO) {
                        throw new ValidateException("FUL0014");
                    }
                    if (totalRowNo > importMaxRowNo) {
                        // log.info("批量导入每次最多只能导入" + importMaxRowNo + "条数据");
                        throw new ValidateException("批量导入每次最多只能导入" + importMaxRowNo + "条数据");
                    }
                    for (int i = READ_START_ROW_NO; i <= totalRowNo; i++) {// 第三行开始取值，第一行为标题行
                        HSSFRow row = sheet.getRow(i); // 获取到第i列的行数据(表格行)
                        if (row != null) {
                            Map<Integer, Object> map = new HashMap<Integer, Object>();
                            for (int j = READ_START_COLUMN_NO - 1; j < row.getLastCellNum(); j++) {// 从第二列开始取值，第一列为序号
                                HSSFCell cell = row.getCell(j); // 获取到第j行的数据(单元格)
                                if (cell != null) {
                                    String cellValue = "";
                                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC
                                        || cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                                        HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                                        cellValue = dataFormatter.formatCellValue(cell);
                                    } else {
                                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                        cellValue = cell.getStringCellValue();
                                    }
                                    // log.info("解析数据---------->第" + (i + 1) + "行，第" + j + "个单元格值为：" + cellValue);
                                    if (StringUtil.isNotBlank(cellValue)) {
                                        // 解决1234.0 去掉后面的.0
                                        String[] item = cellValue.split("[.]");
                                        if (1 < item.length && "0".equals(item[1])) {
                                            cellValue = item[0];
                                        }
                                        map.put(j, cellValue);
                                    }
                                }
                                // else {
                                // log.info("解析数据---------->第" + (i + 1) + "行，第" + j + "个单元格值为：空");
                                // }
                            }
                            if (map != null && map.size() > 0) {
                                list.add(map);
                            }
                        }
                    }
                }
            }
        }
        if (list == null || list.size() == 0) {
            throw new ValidateException("FUL0014");
        }
        return list;
    }
}
