/*
 * Copyright 2015 imiansha.com All right reserved. This software is the confidential and proprietary information of
 * imiansha.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with imiansha.com.
 */
package com.hao.util;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 类CreateOrderNo.java的实现描述：生成单据号
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public class CreateOrderNo {

    private static final Long    maxNo                = 10000000l;                 // 最大流水号
    private static final int     stepNo               = 1;                         // 流水号每次增加数
    private static final Integer completionZeroLength = maxNo.toString().length(); // 流水号补0长度
    private static final String  dateFormat           = "yyyyMMddhhmmssSSS";       // 格式化日期格式
    private static final String  serialNumberFormat   = "%0{0}d";                  // // 0 代表前面补充0, {0}代表补0长度,d代表参数为正数型
    private static final int     randomNumber         = 6;                         // 随机数位数

    /**
     * 说明：根据台头和流水号生成单据号：单据号+时间：年月日时分秒毫秒+8位流水号（很有可能重复）
     * 
     * @param headStr 单据台头
     * @param serialNo 流水号（最近一个）,最好是根据每天的数据量生成count
     * @return
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String createOrderNo(String headStr, long serialNo) {
        String nowdate = DateUtil.dateToFormat(new Date(), dateFormat);
        String serialNumberFormatStr = MessageFormat.format(serialNumberFormat, completionZeroLength);
        if (serialNo >= maxNo) {
            serialNo = serialNo - maxNo;
        }
        serialNo = serialNo + stepNo;
        String sequenceNo = String.format(serialNumberFormatStr, serialNo);

        String orderNumber = headStr + nowdate + sequenceNo;

        return orderNumber;
    }

    /**
     * 方法说明：根据台头生成单据号：单据号+时间：年月日时分秒毫秒+6位水随机数（重复可能性很小）
     * 
     * @param headStr
     * @return String
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String createOrderNo(String headStr) {
        String nowdate = DateUtil.dateToFormat(new Date(), dateFormat);
        String orderNumber = headStr + nowdate + RandomUtil.getRandom(randomNumber);
        return orderNumber;
    }

    /**
     * 方法描述:获取系统流水号
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param length 指定流水号长度
     * @param toNumber 指定流水号是否全由数字组成
     * @return
     */
    public static String getSysJournalNo(int length, boolean isNumber) {
        // replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if (uuid.length() > length) {
            uuid = uuid.substring(0, length);
        } else if (uuid.length() < length) {
            for (int i = 0; i < length - uuid.length(); i++) {
                uuid = uuid + Math.round(Math.random() * 9);
                System.out.println("uuid------" + uuid);
            }
        }
        if (isNumber) {
            return uuid.replaceAll("a",
                                   "1").replaceAll("b",
                                                   "2").replaceAll("c",
                                                                   "3").replaceAll("d",
                                                                                   "4").replaceAll("e",
                                                                                                   "5").replaceAll("f",
                                                                                                                   "6");
        } else {
            return uuid;
        }
    }

}
