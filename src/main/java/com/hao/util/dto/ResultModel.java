
package com.hao.util.dto;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 类ResultModel.java的实现描述:接口返回类型,用来返回json数据
 * 
 * @author  吴昊 2018年3月6日19:32:30
 */
public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = 9041530013023432967L;
    /**
     * 接口返回结果对象
     */
    @ApiModelProperty(value = "接口返回结果对象")
    private T                 returnValue;
    /**
     * 返回结果是否成功，true成功，false失败
     */
    @ApiModelProperty(value = "返回结果是否成功，true成功，false失败")
    private boolean           isSuccessed      = true;
    /**
     * 返回结果失败时的错误代码
     */
    @ApiModelProperty(value = "返回结果失败时的错误代码")
    private String            errorCode;
    /**
     * 返回结果失败时的错误描述
     */
    @ApiModelProperty(value = "返回结果失败时的错误描述")
    private String            errorDesc;

    public static <E extends Serializable> ResultModel<E> newInstance() {
        return new ResultModel<E>();
    }

    /**
     * 接口返回结果对象
     * 
     * @return the returnValue
     */
    public T getReturnValue() {
        return returnValue;
    }

    /**
     * 接口返回结果对象
     * 
     * @param returnValue the returnValue to set
     */
    public void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * 返回结果是否成功，true成功，false失败
     * 
     * @return the isSuccessed
     */
    public boolean isSuccessed() {
        return isSuccessed;
    }

    /**
     * 返回结果是否成功，true成功，false失败
     * 
     * @param isSuccessed the isSuccessed to set
     */
    public void setSuccessed(boolean isSuccessed) {
        this.isSuccessed = isSuccessed;
    }

    /**
     * 返回结果失败时的错误代码
     * 
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 返回结果失败时的错误代码
     * 
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 返回结果失败时的错误描述
     * 
     * @return the errorDesc
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * 返回结果失败时的错误描述
     * 
     * @param errorDesc the errorDesc to set
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
