package com.hao.util;

import java.util.UUID;

/**
 * 类UuidUtil.java的实现描述:uuid工具类
 * 
 * @author 吴昊
 */
public class UuidUtil {

    /**
     * 方法实现描述:获取UUID
     * 
     * @author 吴昊
     * @param is32bit 是否去掉-
     * @return String
     */
    public static String getUuidByJdk(boolean is32bit) {
        String uuid = UUID.randomUUID().toString();
        if (is32bit) {
            return uuid.toString().replace("-", "");
        }
        return uuid;
    }

}
