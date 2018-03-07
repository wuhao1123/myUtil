package com.hao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by 吴昊
 */
public final class ValidateUtils {

    // 邮箱
    public final static String  PATTERN_EMAIL     = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
    // 移动电话
    public final static String  PATTERN_MOBILE    = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";
    // 固定电话
    public final static String  PATTERN_TELEPHONE = "^([0-9]{3,4}-)?[0-9]{7,8}$";
    // 联系方式（固定电话和移动电话均可）
    public final static String  PATTERN_PHONE     = "^((0\\d{2,3}-{0,1}\\d{7,8})|(\\d{7,8})|(1[35784]\\d{9}))$";
    // 数字
    public final static String  PATTERN_NUM       = "^[-]{0,1}[0-9]*$";
    // 邮政编码
    public final static String  PATTERN_POSTNUM   = "^[0-9]{6}$";
    // 身份证
    /**
     * <pre>
     * 省、直辖市代码表：
     *     11 : 北京  12 : 天津  13 : 河北       14 : 山西  15 : 内蒙古
     *     21 : 辽宁  22 : 吉林  23 : 黑龙江  31 : 上海  32 : 江苏
     *     33 : 浙江  34 : 安徽  35 : 福建       36 : 江西  37 : 山东
     *     41 : 河南  42 : 湖北  43 : 湖南       44 : 广东  45 : 广西      46 : 海南
     *     50 : 重庆  51 : 四川  52 : 贵州       53 : 云南  54 : 西藏
     *     61 : 陕西  62 : 甘肃  63 : 青海       64 : 宁夏  65 : 新疆
     *     71 : 台湾
     *     81 : 香港  82 : 澳门
     *     91 : 国外
     * </pre>
     */
    private final static String CITYCODE[]        = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33",
                                                      "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50",
                                                      "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81",
                                                      "82", "91" };

    /**
     * 每位加权因子
     */
    private final static int    POWER[]           = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    public static class Builder {

        // 字段值
        private String  value;
        // 是否必填
        private boolean required;
        // 是否为数字
        private boolean num;
        // 正则表达式
        private String  pattern;
        // 字符最小(大)长度，若是数字，则表示最小(大)数字限制
        private Integer min;
        private Integer max;

        public Builder(){
            this((String) null);
        }

        public Builder(String value){
            this.value = value;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder required(boolean required) {
            this.required = required;
            return this;
        }

        public Builder num(boolean num) {
            this.num = num;
            return this;
        }

        public Builder pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder min(Integer min) {
            this.min = min;
            return this;
        }

        public Builder max(Integer max) {
            this.max = max;
            return this;
        }

        public Builder minAndMax(Integer min, Integer max) {
            this.min = min;
            this.max = max;
            return this;
        }

        public boolean isMail() {
            return this.pattern(ValidateUtils.PATTERN_EMAIL).validate();
        }

        public boolean isTelephone() {
            return this.pattern(ValidateUtils.PATTERN_TELEPHONE).validate();
        }

        public boolean isMobile() {
            return this.pattern(ValidateUtils.PATTERN_MOBILE).validate();
        }

        public boolean isPhone() {
            return this.pattern(ValidateUtils.PATTERN_PHONE).validate();
        }

        public boolean isPostNum() {
            return this.pattern(ValidateUtils.PATTERN_POSTNUM).validate();
        }

        public boolean isNum() {
            return this.num(true).validate();
        }

        public boolean isMail(boolean required) {
            return this.required(required).pattern(ValidateUtils.PATTERN_EMAIL).validate();
        }

        public boolean isTelephone(boolean required) {
            return this.required(required).pattern(ValidateUtils.PATTERN_TELEPHONE).validate();
        }

        public boolean isMobile(boolean required) {
            return this.required(required).pattern(ValidateUtils.PATTERN_MOBILE).validate();
        }

        public boolean isNum(boolean required) {
            return this.required(required).num(true).validate();
        }

        public boolean isPhone(boolean required) {
            return this.required(required).pattern(ValidateUtils.PATTERN_PHONE).validate();
        }

        public boolean isPostNum(boolean required) {
            return this.required(required).pattern(ValidateUtils.PATTERN_POSTNUM).validate();
        }

        public boolean validate() {
            if (this.value == null || "".equals(this.value)) {
                if (this.required != false) {
                    return false;
                }
            } else {
                if (this.num == true) {
                    Pattern p1 = Pattern.compile(ValidateUtils.PATTERN_NUM);
                    if (p1.matcher(this.value).matches() == false) {
                        return false;
                    }
                    if (min != null && Integer.valueOf(this.value) < min) {
                        return false;
                    }
                    if (max != null && Integer.valueOf(this.value) > max) {
                        return false;
                    }
                } else {
                    if (min != null && this.value.length() < min) {
                        return false;
                    }
                    if (max != null && this.value.length() > max) {
                        return false;
                    }
                }
                if (this.pattern != null) {
                    Pattern p2 = Pattern.compile(this.pattern);
                    if (p2.matcher(this.value).matches() == false) {
                        return false;
                    }
                }
            }
            return true;
        }

        public boolean isIDCard() {
            if (this.value == null || "".equals(this.value)) {
                if (this.required != false) {
                    return false;
                }
            } else {
                if (this.value.length() == 15) {
                    return validate15IDCard();
                }
                return validate18Idcard();
            }
            return true;
        }

        public boolean isIDCard(boolean required) {
            return this.required(true).isIDCard();
        }

        public boolean validate18Idcard() {

            // 非18位为假
            if (this.value.length() != 18) {
                return false;
            }
            // 获取前17位
            String idcard17 = this.value.substring(0, 17);

            // 前17位全部为数字
            if (!isDigital(idcard17)) {
                return false;
            }

            String provinceid = this.value.substring(0, 2);
            // 校验省份
            if (!checkProvinceid(provinceid)) {
                return false;
            }

            // 校验出生日期
            String birthday = this.value.substring(6, 14);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            try {
                Date birthDate = sdf.parse(birthday);
                String tmpDate = sdf.format(birthDate);
                if (!tmpDate.equals(birthday)) {// 出生年月日不正确
                    return false;
                }

            } catch (ParseException e1) {

                return false;
            }

            // 获取第18位
            String idcard18Code = this.value.substring(17, 18);

            char c[] = idcard17.toCharArray();

            int bit[] = converCharToInt(c);

            int sum17 = 0;

            sum17 = getPowerSum(bit);

            // 将和值与11取模得到余数进行校验码判断
            String checkCode = getCheckCodeBySum(sum17);
            if (null == checkCode) {
                return false;
            }
            // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
            if (!idcard18Code.equalsIgnoreCase(checkCode)) {
                return false;
            }

            return true;
        }

        /**
         * 校验15位身份证
         *
         * <pre>
         * 只校验省份和出生年月日
         * </pre>
         *
         * @return
         */
        public boolean validate15IDCard() {
            // 非15位为假
            if (this.value.length() != 15) {
                return false;
            }

            // 15全部为数字
            if (!isDigital(this.value)) {
                return false;
            }

            String provinceid = this.value.substring(0, 2);
            // 校验省份
            if (!checkProvinceid(provinceid)) {
                return false;
            }

            String birthday = this.value.substring(6, 12);

            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

            try {
                Date birthDate = sdf.parse(birthday);
                String tmpDate = sdf.format(birthDate);
                if (!tmpDate.equals(birthday)) {// 身份证日期错误
                    return false;
                }

            } catch (ParseException e1) {

                return false;
            }

            return true;
        }

        /**
         * 将15位的身份证转成18位身份证
         *
         * @return
         */
        public String convertIdcarBy15bit() {
            if (this.value == null) {
                return null;
            }

            // 非15位身份证
            if (this.value.length() != 15) {
                return null;
            }

            // 15全部为数字
            if (!isDigital(this.value)) {
                return null;
            }

            String provinceid = this.value.substring(0, 2);
            // 校验省份
            if (!checkProvinceid(provinceid)) {
                return null;
            }

            String birthday = this.value.substring(6, 12);

            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

            Date birthdate = null;
            try {
                birthdate = sdf.parse(birthday);
                String tmpDate = sdf.format(birthdate);
                if (!tmpDate.equals(birthday)) {// 身份证日期错误
                    return null;
                }

            } catch (ParseException e1) {
                return null;
            }

            Calendar cday = Calendar.getInstance();
            cday.setTime(birthdate);
            String year = String.valueOf(cday.get(Calendar.YEAR));

            String idcard17 = this.value.substring(0, 6) + year + this.value.substring(8);

            char c[] = idcard17.toCharArray();
            String checkCode = "";

            // 将字符数组转为整型数组
            int bit[] = converCharToInt(c);

            int sum17 = 0;
            sum17 = getPowerSum(bit);

            // 获取和值与11取模得到余数进行校验码
            checkCode = getCheckCodeBySum(sum17);

            // 获取不到校验位
            if (null == checkCode) {
                return null;
            }
            // 将前17位与第18位校验码拼接
            idcard17 += checkCode;
            return idcard17;
        }

        /**
         * 校验省份
         *
         * @param provinceid
         * @return 合法返回TRUE，否则返回FALSE
         */
        private boolean checkProvinceid(String provinceid) {
            for (String id : ValidateUtils.CITYCODE) {
                if (id.equals(provinceid)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 数字验证
         *
         * @param str
         * @return
         */
        private static boolean isDigital(String str) {
            return str.matches("^[0-9]*$");
        }

        /**
         * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
         *
         * @param bit
         * @return
         */
        private static int getPowerSum(int[] bit) {

            int sum = 0;

            if (ValidateUtils.POWER.length != bit.length) {
                return sum;
            }

            for (int i = 0; i < bit.length; i++) {
                for (int j = 0; j < ValidateUtils.POWER.length; j++) {
                    if (i == j) {
                        sum = sum + bit[i] * ValidateUtils.POWER[j];
                    }
                }
            }
            return sum;
        }

        /**
         * 将和值与11取模得到余数进行校验码判断
         *
         * @param sum17
         * @return 校验位
         */
        private static String getCheckCodeBySum(int sum17) {
            String checkCode = null;
            switch (sum17 % 11) {
                case 10:
                    checkCode = "2";
                    break;
                case 9:
                    checkCode = "3";
                    break;
                case 8:
                    checkCode = "4";
                    break;
                case 7:
                    checkCode = "5";
                    break;
                case 6:
                    checkCode = "6";
                    break;
                case 5:
                    checkCode = "7";
                    break;
                case 4:
                    checkCode = "8";
                    break;
                case 3:
                    checkCode = "9";
                    break;
                case 2:
                    checkCode = "x";
                    break;
                case 1:
                    checkCode = "0";
                    break;
                case 0:
                    checkCode = "1";
                    break;
            }
            return checkCode;
        }

        /**
         * 将字符数组转为整型数组
         *
         * @param c
         * @return
         * @throws NumberFormatException
         */
        private static int[] converCharToInt(char[] c) throws NumberFormatException {
            int[] a = new int[c.length];
            int k = 0;
            for (char temp : c) {
                a[k++] = Integer.parseInt(String.valueOf(temp));
            }
            return a;
        }
    }
}
