/*
 * Copyright 2016 bianxianmao.com All right reserved. This software is the confidential and proprietary information of
 * bianxianmao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with bianxianmao.com.
 */
package com.hao.util;

import com.hao.util.dto.ValidateException;

/**
 * @ClassName CommonValidate.java
 * @Description 功能描述：
 * @author 吴昊 2018年3月6日20:02:38
 */
public class CommonValidate {

    public static final CommonValidate me = new CommonValidate();

    /**
     * 方法实现描述:校验param是否为空
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param param
     * @throws ValidateException void
     */
    public void checkParam(Object param) throws ValidateException {
        if (param == null) {
            throw new ValidateException("request params invalid");
        }
    }

    /**
     * @Description 方法描述： 校验param是否为空
     * @author 吴昊 2018年3月6日20:02:38
     * @param param
     * @param message 错误提示
     */
    public void checkParam(Object param, String message) throws ValidateException {
        if (param == null) {
            throw new ValidateException(message);
        }
    }

    /**
     * @Description 方法描述： 校验参数长度
     * @author 吴昊 2018年3月6日20:02:38
     * @param param 字符串参数
     * @param message 错误提示
     * @param length 最大长度
     * @throws ValidateException
     */
    public void checkParamLength(String param, String message, int length) throws ValidateException {
        if (StringUtil.length(param) > length) {
            throw new ValidateException(message);
        }
    }

}
