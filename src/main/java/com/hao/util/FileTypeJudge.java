package com.hao.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类FileTypeJudge.java的实现描述:文件类型判断类
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public final class FileTypeJudge {

    /**
     * Constructor
     */
    private FileTypeJudge(){
    }

    /**
     * 方法实现描述:将文件头转换成16进制字符串
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param src 原生byte
     * @return String 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 方法实现描述: 得到文件头
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param filePath 文件路径(物理路径)
     * @return 文件头
     * @throws IOException String
     */
    private static String getFileContent(String filePath) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(filePath);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 方法描述:得到文件头
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param inputStream 文件流
     * @return
     * @throws IOException
     */
    private static String getFileContentByInputStream(InputStream inputStream) throws IOException {

        byte[] b = new byte[28];
        try {
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 方法实现描述:判断文件类型
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param filePath 文件路径
     * @return
     * @throws IOException FileType 文件类型
     */
    public static FileType getType(String filePath) throws IOException {

        String fileHead = getFileContent(filePath);
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }

    /**
     * 方法实现描述:判断文件类型
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param inputStream 文件流
     * @return
     * @throws IOException FileType 文件类型
     */
    public static FileType getTypeByInputStream(InputStream inputStream) throws IOException {

        String fileHead = getFileContentByInputStream(inputStream);
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }

}
