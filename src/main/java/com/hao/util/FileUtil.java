package com.hao.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 类FileUtil.java的实现描述:文件操作工具类
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public class FileUtil {

    /**
     * 方法实现描述:将文件名转换编码
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param s
     * @return String
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 方法实现描述:根据不同浏览器将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param request
     * @param s
     * @return
     * @throws Exception String
     */
    public static String toUtf8String(HttpServletRequest request, String s) throws Exception {
        String agent = request.getHeader("User-Agent");
        boolean isFireFox = (agent != null && agent.toLowerCase().indexOf("firefox") != -1);
        if (isFireFox) {
            s = new String(s.getBytes("UTF-8"), "ISO8859-1");
        } else {
            s = toUtf8String(s);
            if ((agent != null && agent.indexOf("MSIE") != -1)) {
                if (s.length() > 150) {
                    // 根据request的locale 得出可能的编码
                    s = new String(s.getBytes("UTF-8"), "ISO8859-1");
                }
            }
        }
        return s;
    }

    /**
     * 方法实现描述:将文本转换成文件存储，返回文件路径url
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param text
     * @param savePath
     * @return
     * @throws IOException String
     */
    public static String convertTextToFile(String text, String savePath) throws IOException {
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;

        try {

            File file = new File(savePath);
            if (file.exists()) {
                file.delete();
            }
            outputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            char[] chars = text.toCharArray();
            for (char c : chars) {
                bufferedWriter.write(c);
            }
            bufferedWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                // 关闭输出流,写入流
                outputStream.close();
                outputStreamWriter.close();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        StringBuffer textSb = new StringBuffer();
        for (int i = 0; i < 1000000; i++) {
            textSb.append("2836d8095b5d431bab3b1a48e881d205-2836d8095b5d431bab3b1a48e881d205-2836d8095b5d431bab3b1a48e881d205"
                          + i + "-" + "hao" + "\n");
        }
        convertTextToFile(textSb.toString(), "D:\\test.txt");
    }

    /**
     * 方法实现描述:将文件转换成文本字符串，返回文本字符串
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param filePath
     * @return String
     */
    public static String convertFileToText(String filePath) {

        File file = new File(filePath);

        String fileContent = "";
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {

            if (file.isFile() && file.exists()) {
                inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    fileContent += line;
                }
                bufferedReader.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                // 关闭输入流,写入流
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return fileContent;
    }

    /**
     * 方法实现描述:将文本转换为输入流
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param inputString
     * @return InputStream
     */
    public static InputStream getStringStream(String inputString) {
        if (StringUtil.isNotBlank(inputString)) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(inputString.getBytes());
                return tInputStringStream;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 方法实现描述:根据文件url获取文件size
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param fileUrl
     * @return long
     */
    public static long getFileSize(String fileUrl) {
        if (StringUtil.isNotBlank(fileUrl)) {
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10 * 1000);
                String fileLength = conn.getHeaderField("Content-Length");
                if (StringUtil.isNotBlank(fileLength)) {
                    return Long.valueOf(fileLength);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

}
