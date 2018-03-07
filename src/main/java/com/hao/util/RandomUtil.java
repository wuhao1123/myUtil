/*
 * Copyright 2016 bianxianmao.com All right reserved. This software is the confidential and proprietary information of
 * bianxianmao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with bianxianmao.com.
 */
package com.hao.util;

import java.util.Random;

/**
 * 类RandomUtil.java的实现描述:生成随机数
 * 
 * @author 吴昊
 */
public class RandomUtil {

    // 定义验证码所取值
    private static final String VALIDATE_CODE_VALUE = "qazwsxedcrfvtgbyhnujmiklopQWERTYUIPLKJHGFDSAZXCVBNM0123456789";
    // 随机数
    private static final String NUMBER_VALUE        = "0123456789";

    private static Random       random              = new Random();

    /**
     * 方法实现描述:生成随机数
     * 
     * @author 吴昊
     * @param maxNumber 最大数
     * @return int
     */
    public static int getRandomNum(int maxNumber) {
        return random.nextInt(maxNumber);
    }

    /**
     * 方法实现描述:生成随机码(包含字母)
     * 
     * @author 吴昊
     * @param codeLength 生成的长度
     * @return String
     */
    public static String getRandomCode(int codeLength) {
        // 定义验证码所取值
        StringBuffer strCode = new StringBuffer();
        // 生成四位数随机验证码内容
        for (int i = 0; i < codeLength; i++) {
            strCode.append(VALIDATE_CODE_VALUE.charAt(getRandomNum(VALIDATE_CODE_VALUE.length())));
        }
        return strCode.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomCode(12));
    }

    /**
     * 方法实现描述:生成随机数（纯数字）
     * 
     * @author 吴昊
     * @param numberLength 随机数位数
     * @return String
     */
    public static String getRandom(int numberLength) {
        // 定义验证码所取值
        StringBuffer strCode = new StringBuffer();
        // 生成四位数随机验证码内容
        for (int i = 0; i < numberLength; i++) {
            strCode.append(NUMBER_VALUE.charAt(getRandomNum(NUMBER_VALUE.length())));
        }
        return strCode.toString();
    }

    public static final String generateApiKey() {
        return generateNumString(32);
    }

    public static final String generateSecretKey() {
        return generateNumString(16);
    }

    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 法描述:获取字符+数字随机字符
     * 
     * @author 吴昊
     * @param length
     * @return
     */
    private static String generateNumString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

}
