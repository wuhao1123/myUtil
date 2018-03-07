/*
 * Copyright 2017 bianxianmao.com All right reserved. This software is the confidential and proprietary information of
 * bianxianmao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with bianxianmao.com.
 */
package com.hao.commons.export;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴昊
 * @Created 2018-02-26 17:25:00
 * @Modifier 吴昊
 * @Description
 * @Version BUILD1001
 */
public class ExportExample {
    public static void main(String[] args) throws Exception {
        ExportModel exportModel = new ExportModel();
        exportModel.setHeadNames("test1,test2,test3");
        List<ExportDataRow> dataRowList = new ArrayList<>();

        ExportDataRow exportDataRow = new ExportDataRow();
        List<String> list = new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");

        exportDataRow.setDataColumnList(list);

        dataRowList.add(exportDataRow);

        HttpServletResponse response = new HttpServletResponseWrapper(null);

        ExportExcel.exportExcel(response,exportModel,"文件名");

    }
}
