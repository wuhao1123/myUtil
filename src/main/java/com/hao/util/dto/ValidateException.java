/*
 * Copyright 2016 bianxianmao.com All right reserved. This software is the confidential and proprietary information of
 * bianxianmao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with bianxianmao.com.
 */
package com.hao.util.dto;

/**
 * 类ValidateException.java的实现描述:校验自定义异常处理类
 * 
 * @author leon 2016年8月11日 下午1:11:53
 */
public class ValidateException extends BaseException {

    private static final long serialVersionUID = 2729611264067131179L;

    public ValidateException(String errorCode, String errorMsg){
        super(errorCode, errorMsg);
    }

    public ValidateException(String errorMsg){
        super(errorMsg);
    }

    public ValidateException(Throwable cause, String errorCode, String errorMsg){
        super(cause, errorCode, errorMsg);
    }

    public ValidateException(Throwable cause, String errorMsg){
        super(cause, errorMsg);
    }

    public ValidateException(Throwable cause){
        super(cause);
    }
}
