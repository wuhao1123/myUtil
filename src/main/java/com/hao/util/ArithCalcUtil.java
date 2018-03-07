package com.hao.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * 类ArithCalcUtil.java的实现描述：数字精确计算工具类
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public class ArithCalcUtil {

    /**
     * 提供精确加法计算的add方法
     * 
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(Double value1, Double value2, int scale) {
        if (value1 == null) {
            value1 = 0.0;
        }
        if (value2 == null) {
            value2 = 0.0;
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确减法运算的sub方法
     * 
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(Double value1, Double value2, int scale) {
        if (value1 == null) {
            value1 = 0.0;
        }
        if (value2 == null) {
            value2 = 0.0;
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确乘法运算的mul方法
     * 
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(Double value1, Double value2, int scale) {
        if (value1 == null) {
            value1 = 0.0;
        }
        if (value2 == null) {
            value2 = 0.0;
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return (b1.multiply(b2)).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的除法运算方法div
     * 
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     */
    public static BigDecimal div(Double value1, Double value2, int scale) {
        if (value1 == null) {
            value1 = 0.0;
        }
        if (value2 == null) {
            value2 = 0.0;
        }
        if (scale < 0) {
            scale = 0;
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @description : 检验BigDecimal是否为空
     * @param value
     * @return 空则返回0
     * @author : 吴昊 2018年3月6日20:02:38
     */
    public static BigDecimal CheckBigDecimal(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value;
    }

    /**
     * @description : 检查是否是数值
     * @param str 传入的参数
     * @return boolean
     * @author : 吴昊 2018年3月6日20:02:38
     */
    public static boolean isNumeric(String str) {
        if (StringUtil.isNotBlank(str)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            return pattern.matcher(str).matches();
        } else {
            return false;
        }
    }
}
