
package com.hao.util;

import com.hao.util.base.StringUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 类PinyinUtil.java的实现描述：字符串转拼音工具类
 * 
 * @author 吴昊
 */
public class PinyinUtil {

    /**
     * 说明方法描述：将汉字转换为全拼
     * 
     * @param sourceStr 要转换的字符串
     * @param connectStr 连接符
     * @param isFirstToUppper 是否首字母大写
     * @return
     * @author 吴昊
     */
    public static String getPinYin(String sourceStr, String connectStr, boolean isFirstToUppper) {
        if (StringUtil.isBlank(sourceStr)) {
            return sourceStr;
        }
        char[] tempChar = null;
        // 将字符串转换为字符数组
        tempChar = sourceStr.toCharArray();
        // 创建一个字符串数组和字符数组长度相同
        String[] newStrs = new String[tempChar.length];
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();
        pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        // 输出的拼音字符串
        String pinyinStr = "";
        int t0 = tempChar.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断能否为汉字字符
                if (Character.toString(tempChar[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    // 将汉字的几种全拼都存到newStrs数组中
                    newStrs = PinyinHelper.toHanyuPinyinStringArray(tempChar[i], pinyinFormat);
                    // 将首字母转为大写
                    if (isFirstToUppper) {
                        String temp = newStrs[0].substring(0, 1).toUpperCase();
                        newStrs[0] = temp + newStrs[0].substring(1);
                    }
                    // 加连接符
                    if (StringUtil.isNotBlank(connectStr) && i + 1 != t0) {
                        newStrs[0] += connectStr;
                    }
                    // 取出该汉字全拼的第一种读音并连接到字符串t4后
                    pinyinStr += newStrs[0];
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串pinyinStr后
                    pinyinStr += Character.toString(tempChar[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pinyinStr;
    }

    /**
     * 说明方法描述：提取每个汉字的首字母
     * 
     * @param sourceStr 要转换的字符串
     * @param connectStr 连接符
     * @param isToUpper 是否转为大写
     * @return
     * @author 吴昊
     */
    public static String getPinYinHeadChar(String sourceStr, String connectStr, boolean isToUpper) {
        if (StringUtil.isBlank(sourceStr)) {
            return sourceStr;
        }
        String pinyinStr = "";
        for (int j = 0; j < sourceStr.length(); j++) {
            char word = sourceStr.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                pinyinStr += pinyinArray[0].charAt(0);
            } else {
                pinyinStr += word;
            }
            // 加连接符
            if (StringUtil.isNotBlank(connectStr) && j + 1 != sourceStr.length()) {
                pinyinStr += connectStr;
            }

        }
        if (isToUpper) {
            pinyinStr = pinyinStr.toUpperCase();
        }

        return pinyinStr;
    }
}
