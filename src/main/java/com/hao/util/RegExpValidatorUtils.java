
package com.hao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegExpValidatorUtils.java
 * @Description 功能描述： 正则表达式校验工具类
 * @author 吴昊
 */
public class RegExpValidatorUtils {

    /**
     * 说明方法描述：验证邮箱
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isEmail(String str) {
        String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证IP地址
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isIP(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证网址Url
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isUrl(String str) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证座机电话号码，不能用于手机号验证
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isTelephone(String str) {
        String regex = "^(\\d{3,4}-)?\\d{6,8}$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证输入密码条件(字符与数据同时出现)
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isPassword(String str) {
        String regex = "^(\\w){6,16}$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证输入密码长度 (6-18位)
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isPasswLength(String str) {
        String regex = "^\\d{6,18}$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证输入邮政编号
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isPostalcode(String str) {
        String regex = "^\\d{6}$";
        return match(regex, str);
    }

    /**
     * @author 吴昊 验证手机号码
     * 中国电信发布中国3G号码段:中国联通185,186;中国移动188,187,145,147,149;中国电信170,171,173,175,176,177,,78,189,180共6个号段。
     * 3G业务专属的180-189号段已基本分配给各运营商使用, 其中180、189分配给中国电信,187、188归中国移动使用,185、186属于新联通。
     * 中国移动拥有号码段：139、138、137、136、135、134、159、158、157（3G）、152、151、150、188（3G）、187（3G）;14个号段
     * 中国联通拥有号码段：130、131、132、155、156（3G）、186（3G）、185（3G）;6个号段 中国电信拥有号码段：133、153、189（3G）、180（3G）;4个号码段 移动:
     * 2G号段(GSM网络)有139,138,137,136,135,134(0-8),159,158,152,151,150 3G号段(TD-SCDMA网络)有157,188,187 147是移动TD上网卡专用号段. 联通:
     * 2G号段(GSM网络)有130,131,132,155,156 3G号段(WCDMA网络)有186,185 电信: 2G号段(CDMA网络)有133,153 3G号段(CDMA网络)有189,180
     * @param String mobileNo
     * @return boolean
     * @author 吴昊
     */
    public static boolean isMobileNO(String str) {
        String regex = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证输入身份证号
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isIDcard(String str) {
        String regex = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        // String regex = "(^\\d{18}$)|(^\\d{15}$)";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证输入字符串是不是带两位小数的数字
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isTowSmallDigitDecimal(String str) {
        String regex = "^[0-9]+(.[0-9]{2})?$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证字符串是不是数字并且验证数字位数和小数位
     * 
     * @param str
     * @param decimalLength 数字非小数部分最大长度
     * @param smallDigit 小数点最大位数
     * @return
     * @author 吴昊
     */
    public static boolean limitDecimalFormat(String str, int decimalLength, int smallDigit) {
        String regex = "^[0-9]{1," + decimalLength + "}(.[0-9]{0," + smallDigit + "})?$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证字符串是不是数字并且验证数字位数和小数位，且数字大于0小于1
     * 
     * @param str
     * @param smallDigit 小数点最大位数
     * @return
     * @author 吴昊
     */
    public static boolean betweenZeroAndOne(String str, int smallDigit) {
        String regex = "^?(0\\.\\d*[0-9]{0," + smallDigit + "})$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证输入一年的12个月
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isMonth(String str) {
        String regex = "^(0?[[1-9]|1[0-2])$";
        return match(regex, str);
    }

    /**
     * 说明方法描述： 验证输入一个月的31天
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isDay(String str) {
        String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证日期时间
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isDate(String str) {
        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证数字输入
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isNumber(String str) {
        String regex = "^[0-9]*$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证非零的正整数
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isIntNumber(String str) {
        String regex = "^\\+?[1-9][0-9]*$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证大写字母
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isUpChar(String str) {
        String regex = "^[A-Z]+$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证小写字母
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isLowChar(String str) {
        String regex = "^[a-z]+$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证输入字母
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isLetter(String str) {
        String regex = "^[A-Za-z]+$";
        return match(regex, str);
    }

    /**
     * 说明方法描述：验证输入汉字
     * 
     * @param str
     * @return
     * @author 吴昊
     */
    public static boolean isChinese(String str) {
        String regex = "^[\u4e00-\u9fa5],{0,}$";
        return match(regex, str);
    }

    /**
     * 方法实现描述:根据正则表达式验证字符串
     * 
     * @author 吴昊
     * @param regex 正则表达式字符串
     * @param str 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     * @return boolean
     */
    private static boolean match(String regex, String str) {
        if (StringUtil.isBlank(regex) || StringUtil.isBlank(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
