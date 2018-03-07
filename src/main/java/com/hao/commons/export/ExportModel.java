package com.hao.commons.export;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExportModel.java
 * @Description 功能描述：导出excel数据模型
 * @author 吴昊 2018年2月26日17:14:02
 */
public class ExportModel {

    /** 导出数据行集合 **/
    private List<ExportDataRow> dataRowList = new ArrayList<>();
    /** 导出数据列头：多个以逗号隔开，如：日期,订单号,金额...... **/
    private String              headNames;

    /**
     * @return 获取导出数据行集合
     */
    public List<ExportDataRow> getDataRowList() {
        return dataRowList;
    }

    /**
     * @Description 设置导出数据行集合
     */
    public void setDataRowList(List<ExportDataRow> dataRowList) {
        this.dataRowList = dataRowList;
    }

    /**
     * @return 导出数据列头：多个以逗号隔开，如：日期,订单号,金额......
     */
    public String getHeadNames() {
        return headNames;
    }

    /**
     * @param 导出数据列头：多个以逗号隔开，如：日期,订单号,金额......
     */
    public void setHeadNames(String headNames) {
        this.headNames = headNames;
    }

}
