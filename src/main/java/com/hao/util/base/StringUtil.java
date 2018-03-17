package com.hao.util.base;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @ClassName StringUtil.java
 * @Description 功能描述： string处理类
 * @author 吴昊
 */
public class StringUtil extends StringUtils {

    /**
     * 方法描述: 将BigDecimal格式化成千分位格式字符串
     * 
     * @author 吴昊
     * @param number
     * @return
     */
    public static String formatBigDecimal(BigDecimal number) {
        if (number == null) {
            return null;
        }
        if (number.compareTo(new BigDecimal(1)) > 0) {
            NumberFormat currency = NumberFormat.getCurrencyInstance();// 建立货币格式
            String formatNumber = currency.format(number);
            formatNumber = formatNumber.replace("￥", "").replace("$", "");
            return formatNumber;
        }
        return number + "";

    }

    /**
     * 方法描述:将Long格式化成千分位格式字符串
     * 
     * @author 吴昊
     * @param number
     * @return
     */
    public static String formatLong(Long number) {
        if (number == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat("##0,000");// 格式化成千分位
        String numberStr = number.toString();
        String formatNumber = "";// 返回的格式化字符串
        long numberAbs = Math.abs(number);// 求绝对值
        // 如果大于1000转换成千分位格式
        if (numberAbs >= 1000) {
            formatNumber = format.format(number);
        } else {
            formatNumber = numberStr;
        }
        return formatNumber;
    }

    /**
     * 方法描述:判断小数位数是否符合，支持大数，整数，负数，小数，不支持科学记数法
     * 
     * @author 吴昊
     * @param number
     * @param bit
     * @return
     */
    public static boolean isPositiveNumberDecimal(String number, int bit) {
        boolean flag = false;
        if (bit < 0 || StringUtil.isBlank(number)) {
            return false;
        }
        BigDecimal bigDecimal = new BigDecimal(number);
        // 格式化位数+1的值
        bigDecimal = bigDecimal.setScale((bit + 1), BigDecimal.ROUND_UP);
        String decimal = String.valueOf(bigDecimal.toPlainString());
        // 判断位数
        if (decimal.indexOf(".") != -1) {// 判断为小数
            String pointString = decimal.split("\\.")[1];// 截取小数位后面的值
            // 去除字符末尾的零
            pointString = pointString.replaceAll("(0)*$", "");
            if (pointString.length() < (bit + 1)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 方法描述:计算字符串的字节长度
     * 
     * @author 吴昊
     * @param s
     * @return
     */
    public static int getStrByteLength(String s) {
        int length = 0;
        if (isBlank(s)) {
            return 0;
        }
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }
        }
        return length;
    }

    /**
     * @param str
     * @param length
     * @return
     * @author leon
     */
    public static String getStringByBytes(String str, int length) {
        if (isBlank(str) || length == 0) return "";

        StringBuffer result = new StringBuffer();
        if (StringUtil.getStrByteLength(str) > length) {
            int count = 0;
            char[] chars = str.toCharArray();
            int charLength = chars.length;
            for (int i = 0; i < charLength; i++) {
                int ascii = Character.codePointAt(chars, i);
                if (ascii >= 0 && ascii <= 255) {
                    count++;
                } else {
                    count += 2;
                }
                if (count < 391) {
                    result.append(chars[i]);
                }
            }
        } else {
            return str;
        }
        return result.toString();
    }

    /**
     * 方法描述:判断字符是否是正数
     * 
     * @author 吴昊
     * @param str
     * @return
     */
    public static boolean isPositiveNumber(String str) {
        return str.matches("^[+]?(([0-9]+)([.]([0-9]+))?)$");
    }

    /**
     * 方法描述:验证某个值是否符合某个正则表达式
     * 
     * @author 吴昊
     * @param str
     * @param regular
     * @return
     */
    public static boolean isRegularTrue(String str, String regular) {
        return str.matches(regular);
    }

    /**
     * 方法描述:转换金额，将金额转换为元，万元，亿元，亿万元
     * 
     * @author 吴昊
     * @param money
     * @return
     */
    public static String formatMoney(BigDecimal money) {
        String moneyUnit = "元";// 最新成交金额单位
        BigDecimal moneyWan = new BigDecimal(10000);
        BigDecimal moneyYi = new BigDecimal(100000000);
        BigDecimal moneyWanYi = new BigDecimal("1000000000000");
        // 转换单位，并四舍五入保留两位小数
        if (money == null) {
            money = new BigDecimal(0);
            moneyUnit = "元";
        } else if ((money.compareTo(moneyWan) == 0 || money.compareTo(moneyWan) > 0) && money.compareTo(moneyYi) < 0) {
            money = money.divide(moneyWan, 2, BigDecimal.ROUND_HALF_UP);
            moneyUnit = "万元";
        } else if ((money.compareTo(moneyYi) == 0 || money.compareTo(moneyYi) > 0) && money.compareTo(moneyWanYi) < 0) {
            money = money.divide(moneyYi, 2, BigDecimal.ROUND_HALF_UP);
            moneyUnit = "亿元";
        } else if (money.compareTo(moneyWanYi) == 0 || money.compareTo(moneyWanYi) > 0) {
            money = money.divide(moneyWanYi, 2, BigDecimal.ROUND_HALF_UP);
            moneyUnit = "万亿元";
        }

        return money + "," + moneyUnit;
    }

    /**
     * 方法描述:替换//，\/,/\,\\为 File.separator
     * 
     * @author 吴昊
     * @param path
     * @return
     */
    public static String replaceSeparator(String path) {
        if (StringUtil.isNotBlank(path)) {
            path = path.replace("//", File.separator);
            path = path.replace("/\\", File.separator);
            path = path.replace("/", File.separator);
            path = path.replace("\\/", File.separator);
            path = path.replace("\\\\", File.separator);
            path = path.replace("\\", File.separator);
            return path;
        }
        return null;

    }

    /**
     * 方法描述:替换host中的url//,\/,/\,\\为/
     * 
     * @author 吴昊
     * @param path
     * @return
     */
    public static String replaceHostSeparator(String path) {
        if (StringUtil.isNotBlank(path)) {
            path = path.replace("\\", "/").replace("//", "/");
            path = path.replace("/\\", "/").replace("\\/", "/");
            return path;
        }
        return null;

    }

    /**
     * 方法描述:去除字符串中的某个字符
     * 
     * @author 吴昊
     * @param Str
     * @param ch
     * @return
     */
    public static String removeChar(String Str, char ch) {
        StringBuffer buffer = new StringBuffer();
        int position = 0;
        char currentChar;

        while (position < Str.length()) {
            currentChar = Str.charAt(position++);
            // 如果当前字符不是要去除的字符，则将当前字符加入到StringBuffer中
            if (currentChar != ch) buffer.append(currentChar);
        }
        return buffer.toString();
    }

    /**
     * 方法描述:：去除字符串中的某个字符
     * 
     * @author 吴昊
     * @param str
     * @return
     */
    public static String removeSpace(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim().replace(" ", "").replace(" ", "");// 首尾中间普通中英文空格
        str = str.replace("%20", "");// 浏览器转义空格
        str = str.replace(" +", "");// 去掉所有空格
        str = str.replace("\\s*", "");// 替换大部分空白字符， 不限于空格 \s 可以匹配空格、制表符、换页符等空白字符的其中任意一个
        return str;
    }

    /**
     * 提供精确的乘法运算
     * 
     * @author 吴昊
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static Double strMul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(v1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static String getRandStr(int n) {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < n; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }
}
