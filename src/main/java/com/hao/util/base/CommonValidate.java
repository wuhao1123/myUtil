package com.hao.util.base;

/**
 * @ClassName CommonValidate.java
 * @Description 功能描述：
 * @author 吴昊 2018年3月6日20:02:38
 */
public class CommonValidate {

    public static final CommonValidate ME = new CommonValidate();

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
