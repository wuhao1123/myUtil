package com.hao.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 类CreateLuckyCodeUtil.java的实现描述：生成幸运码
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public class CreateLuckyCodeUtil {

    /**
     * 方法描述:根据数量生成幸运号,返回list
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param count
     * @param firstNumber 幸运号第一个开始数字，默认传1
     * @return
     */
    public static List<Integer> createLuckyCodeReturnList(int count, int firstNumber) {
        List<Integer> luckNumbers = new ArrayList<Integer>();
        int r = 10000000;
        for (int i = firstNumber; i <= count; i++) {
            luckNumbers.add(r + i);
        }
        // 给集合随机排序
        Collections.shuffle(luckNumbers);
        return luckNumbers;
    }

    /**
     * 方法描述:根据数量生成幸运号,返回字符串
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param count 幸运号数量
     * @return
     */
    public static String createLuckyCodeReturnString(int count) {
        if (count <= 0) {
            return null;
        }
        StringBuffer luckyCodeSb = new StringBuffer();
        int r = 10000000;
        for (int i = 1; i <= count; i++) {
            luckyCodeSb.append(r + i);
            luckyCodeSb.append(",");
        }
        String luckyCode = luckyCodeSb.toString();
        luckyCode = luckyCode.substring(0, luckyCode.length() - 1);
        return luckyCode;
    }

}
