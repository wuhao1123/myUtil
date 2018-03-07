package com.hao.util.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ErrorCode.java
 * @Description 功能描述： 异常返回码定义枚举
 * @author 吴昊 2018年3月6日19:31:35
 */
public enum ErrorCode {

                       ILLEGAL_PARAMS("400", "request params invalid"), SERVER_ERROR("500", "system error"),
                       /**
                        * 用户信息已失效，请重新登录
                        */
                       ILLEGAL_USER("1001", "用户信息已失效，请重新登录！");

    private String errorCode;

    private String errorMessage;

    /**
     * @param errorCode
     * @param errorMessage
     */
    private ErrorCode(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 所有状态
     * 
     * @return
     */
    public static Map<String, String> getAllState() {
        ErrorCode[] sts = ErrorCode.values();
        Map<String, String> maps = new HashMap<String, String>();
        for (ErrorCode tmp : sts) {
            maps.put(tmp.errorCode, tmp.errorMessage);
        }
        return maps;
    }

}
