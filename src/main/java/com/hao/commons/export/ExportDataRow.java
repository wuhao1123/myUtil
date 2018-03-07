package com.hao.commons.export;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExportDataRow.java
 * @Description 功能描述：excel导出数据行
 * @author 吴昊 2018年2月26日17:06:07
 */
public class ExportDataRow {

    /** 导出数据列集合 **/
    private List<String> dataColumnList = new ArrayList<>();

    /**
     * @return 获取导出数据列集合
     */
    public List<String> getDataColumnList() {
        return dataColumnList;
    }

    /**
     * @param 设置导出数据列集合
     */
    public void setDataColumnList(List<String> dataColumnList) {
        this.dataColumnList = dataColumnList;
    }

}
