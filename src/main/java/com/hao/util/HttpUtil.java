package com.hao.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @ClassName HttpUtil.java
 * @Description 功能描述： http请求工具类，可发送get、post请求返回数据
 * @author 吴昊 2018年3月6日20:02:38
 * @CopyRight 杭州微财网络科技有限公司
 */
public class HttpUtil {

    /**
     * 说明方法描述：发送http请求，抛异常
     * 
     * @param sendUrl 发送url
     * @param params 参数
     * @param sendType 请求类型：GET or POST
     * @param charset 编码
     * @param repeat_request_count 发生异常重复请求次数，默认传0
     * @param repeat_request_max_count 发生异常重复请求最大次数
     * @return
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String sendHttpShowException(String sendUrl, Map<String, String> params, String sendType,
                                               String charset, int repeat_request_count,
                                               int repeat_request_max_count) throws Exception {
        URL url = null;
        HttpURLConnection con = null;
        OutputStreamWriter osw = null;
        // 构建请求参数
        StringBuffer paramSb = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                if (e.getValue() != null) {
                    paramSb.append(e.getKey());
                    paramSb.append("=");
                    // 将参数值urlEncode编码,防止传递中乱码
                    paramSb.append(URLEncoder.encode(e.getValue(), "UTF-8"));
                    paramSb.append("&");
                }
            }
        }
        // 尝试发送请求
        try {
            if (paramSb.length() > 0) {
                String paramStr = paramSb.toString();
                paramStr = paramStr.substring(0, paramStr.length() - 1);
                url = new URL(sendUrl + "?" + paramStr);
            } else {
                url = new URL(sendUrl);
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(sendType);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(true);
            // 设置Content-Type
            con.setRequestProperty("Content-Type", "text/html;charset=" + charset);
            // 设置http请求超时时间30000毫秒（30秒）
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);
            osw = new OutputStreamWriter(con.getOutputStream(), charset);
            osw.flush();
            osw.close();

        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new Exception("网络或者接口异常,请稍后重试！");
                }
            }
        }
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
        String temp;
        while ((temp = br.readLine()) != null) {
            buffer.append(temp);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * 说明方法描述：发送http请求，不抛异常
     * 
     * @param sendUrl 发送url
     * @param params 参数
     * @param sendType 请求类型：GET or POST
     * @param charset 编码
     * @param repeat_request_count 发生异常重复请求次数，默认传0
     * @param repeat_request_max_count 发生异常重复请求最大次数
     * @return
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String sendHttp(String sendUrl, Map<String, String> params, String sendType, String charset,
                                  int repeat_request_count, int repeat_request_max_count) throws Exception {
        URL url = null;
        HttpURLConnection con = null;
        OutputStreamWriter osw = null;
        // 构建请求参数
        StringBuffer paramSb = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                if (e.getValue() != null) {
                    paramSb.append(e.getKey());
                    paramSb.append("=");
                    // 将参数值urlEncode编码,防止传递中乱码
                    paramSb.append(URLEncoder.encode(e.getValue(), "UTF-8"));
                    paramSb.append("&");
                }
            }
        }
        StringBuffer buffer = new StringBuffer();
        // 尝试发送请求
        try {
            if (paramSb.length() > 0) {
                String paramStr = paramSb.toString();
                paramStr = paramStr.substring(0, paramStr.length() - 1);
                url = new URL(sendUrl + "?" + paramStr);
            } else {
                url = new URL(sendUrl);
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(sendType);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(true);
            // 设置Content-Type
            con.setRequestProperty("Content-Type", "text/html;charset=" + charset);
            // 设置http请求超时时间30000毫秒（30秒）
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);
            osw = new OutputStreamWriter(con.getOutputStream(), charset);
            osw.flush();
            osw.close();

            // 读取返回内容
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 发生异常重复发请求
            throw new Exception("网络或者接口异常,请稍后重试！");
        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new Exception("网络或者接口异常,请稍后重试！");
                }
            }
        }
        return buffer.toString();
    }

    public static String GetPostUrl(String sendUrl, Map<String, String> params, String sendType, String charset,
                                    int repeat_request_count, int repeat_request_max_count) {
        URL url = null;
        HttpURLConnection httpurlconnection = null;

        try {
            // 构建请求参数
            StringBuffer paramSb = new StringBuffer();
            if (params != null) {
                for (Entry<String, String> e : params.entrySet()) {
                    if (e.getValue() != null) {
                        paramSb.append(e.getKey());
                        paramSb.append("=");
                        // 将参数值urlEncode编码,防止传递中乱码
                        paramSb.append(URLEncoder.encode(e.getValue(), "UTF-8"));
                        paramSb.append("&");
                    }
                }
                paramSb.substring(0, paramSb.length() - 1);
            }
            url = new URL(sendUrl + "?" + paramSb.toString());
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setRequestMethod("GET");
            httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);

            // 设置http请求超时时间30000毫秒（30秒）
            httpurlconnection.setConnectTimeout(30000);
            httpurlconnection.setReadTimeout(30000);
            httpurlconnection.setUseCaches(true);
            /*
             * if (submitMethod.equalsIgnoreCase("POST")) {
             * httpurlconnection.getOutputStream().write(postData.getBytes("GBK"));
             * httpurlconnection.getOutputStream().flush(); httpurlconnection.getOutputStream().close(); }
             */

            int code = httpurlconnection.getResponseCode();
            if (code == 200) {
                DataInputStream in = new DataInputStream(httpurlconnection.getInputStream());
                int len = in.available();
                byte[] by = new byte[len];
                in.readFully(by);
                String rev = new String(by, "UTF-8");

                in.close();

                return rev;
            } else {
                // http 请求返回非 200状态时处理
                return "<?xml version=\"1.0\" encoding=\"utf-8\" ?><error>发送第三方请求失败</error>";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return null;
    }

    // post请求方式
    public static String http(String url, Map<String, String> params) {
        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                if (e.getValue() != null) {
                    sb.append(e.getKey());
                    sb.append("=");
                    sb.append(e.getValue());
                    sb.append("&");
                }
            }
            sb.substring(0, sb.length() - 1);
        }
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            // // POST 只能为大写，严格限制，post会不识别
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            // 一定要有返回值，否则无法把请求发送给server端。
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

}
