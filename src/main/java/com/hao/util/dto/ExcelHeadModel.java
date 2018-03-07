package com.hao.util.dto;

import java.io.Serializable;

/**
 * 类ExcelHeadModel.java的实现描述：Excel导出的头内容格式
 * 
 * @author 吴昊 2018年3月6日19:32:00
 */
public class ExcelHeadModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            headName;             // 头名称
    private boolean           isSelect;             // 是否下拉框
    private String[]          selectValues;         // 下拉框值
    private boolean           isNumber;             // 是否数字
    private boolean           isNonEmpty;           // 是否非空

    /**
     * @return the headName
     */
    public String getHeadName() {
        return headName;
    }

    /**
     * @param headName the headName to set
     */
    public void setHeadName(String headName) {
        this.headName = headName;
    }

    /**
     * @return the isSelect
     */
    public boolean isSelect() {
        return isSelect;
    }

    /**
     * @param isSelect the isSelect to set
     */
    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    /**
     * @return the selectValues
     */
    public String[] getSelectValues() {
        return selectValues;
    }

    /**
     * @param selectValues the selectValues to set
     */
    public void setSelectValues(String[] selectValues) {
        this.selectValues = selectValues;
    }

    /**
     * @return the isNumber
     */
    public boolean isNumber() {
        return isNumber;
    }

    /**
     * @param isNumber the isNumber to set
     */
    public void setNumber(boolean isNumber) {
        this.isNumber = isNumber;
    }

    /**
     * @return the isNonEmpty
     */
    public boolean isNonEmpty() {
        return isNonEmpty;
    }

    /**
     * @param isNonEmpty the isNonEmpty to set
     */
    public void setNonEmpty(boolean isNonEmpty) {
        this.isNonEmpty = isNonEmpty;
    }

}
