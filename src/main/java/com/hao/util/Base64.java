package com.hao.util;

import com.hao.util.base.StringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 类Base64.java的实现描述：TODO 类实现描述
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public class Base64 {

    /**
     * 方法描述:编码
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param str
     * @return
     */
    public static String encode(String str) {
        if (StringUtil.isBlank(str)) {
            return "";
        }
        try {
            return new sun.misc.BASE64Encoder().encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 方法描述:解码
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param str
     * @return
     */
    public static String decode(String str) {
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
            return new String(bt, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
