package com.hao.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @ClassName WebUtil.java
 * @Description 功能描述： 浏览器相关方法
 * @author 吴昊
 */
public class WebUtil {

    // 编码
    private static final String ECODING = "UTF-8";

    /**
     * 方法描述:获取 request 中 json 字符串的内容
     * 
     * @author 吴昊
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestJsonString(HttpServletRequest request) throws IOException {
        String submitMehtod = request.getMethod();
        // GET
        if (submitMehtod.equals("GET")) {
            return new String(request.getQueryString().getBytes("iso-8859-1"), "utf-8").replaceAll("%22", "\"");
            // POST
        } else {

            int contentLength = request.getContentLength();
            if (contentLength < 0) {
                return null;
            }
            byte buffer[] = new byte[contentLength];
            for (int i = 0; i < contentLength;) {

                int readlen = request.getInputStream().read(buffer, i, contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
            }

            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            return new String(buffer, charEncoding);
        }
    }

    /**
     * 说明方法描述：根据url获取HTML内容
     * 
     * @param url
     * @return
     * @throws Exception
     * @author 吴昊
     */
    public static String getHTML(String url) throws Exception {
        if (StringUtil.isBlank(url)) {
            return null;
        }
        URL uri = new URL(url);
        URLConnection connection = uri.openConnection();
        InputStream in = connection.getInputStream();
        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        while ((in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, ECODING));
        }
        in.close();
        return sb.toString();
    }

    /**
     * 说明方法描述：获取用户浏览器信息
     * 
     * @param request
     * @return
     * @throws Exception
     * @author 吴昊
     */
    public static String getUserAgent(HttpServletRequest request) {
        try {
            String Agent = request.getHeader("User-Agent");
            if (!StringUtils.isEmpty(Agent) && !"unknown".equalsIgnoreCase(Agent)) {
                StringTokenizer st = new StringTokenizer(Agent, ";");
                st.nextToken();
                // 得到用户的浏览器名
                return st.nextToken();
            }
            return "unknown";
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    /**
     * 说明方法描述：获取用户访问的ip
     * 
     * @param request
     * @return
     * @author 吴昊
     */
    public static String getIpAddr(HttpServletRequest request) {
        try {
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (StringUtil.isNotBlank(ip) && ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
            return "未知ip";
        }
    }

    /**
     * 说明方法描述：获取上下文URL全路径
     * 
     * @param request
     * @return
     * @author 吴昊
     */
    public static String getContextPath(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath());
        String path = sb.toString();
        return path;
    }

    /**
     * 说明方法描述：获取完整请求路径(含内容路径及请求参数)
     * 
     * @param request
     * @return
     * @author 吴昊
     */
    public static String getRequestURIWithParam(HttpServletRequest request) {
        return request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    }

    /**
     * 说明方法描述：获取ParameterMap
     * 
     * @param request
     * @return
     * @author 吴昊
     */
    public static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> enume = request.getParameterNames();
        while (enume.hasMoreElements()) {
            String name = enume.nextElement();
            map.put(name, request.getParameter(name));
        }
        return map;
    }

    /**
     * 说明方法描述：
     * 
     * @param response
     * @param domain 设置cookie所在域
     * @param path 设置cookie所在路径
     * @param isHttpOnly 是否只读
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     * @author 吴昊
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, boolean isHttpOnly,
                                 String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);

        // 所在域：比如a1.4bu4.com 和 a2.4bu4.com 共享cookie
        if (null != domain && !domain.isEmpty()) {
            cookie.setDomain(domain);
        }

        // 设置cookie所在路径
        cookie.setPath("/");
        if (null != path && !path.isEmpty()) {
            cookie.setPath(path);
        }

        // 是否只读
        cookie.setHttpOnly(isHttpOnly);

        // 设置cookie的过期时间
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }

        // 添加cookie
        response.addCookie(cookie);
    }

    /**
     * 去除HTML代码
     * 
     * @param inputString
     * @return
     */
    public static String HtmltoText(String inputString) {
        String htmlStr = inputString; // 含HTML标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        Pattern p_ba;
        java.util.regex.Matcher m_ba;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
                                                                                                     // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
                                                                                                  // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String patternStr = "\\s+";

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); // 过滤空格

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;// 返回文本字符串
    }

    /**
     * 说明方法描述：把页面的信息替换成我们想要的信息存放数据库里面
     * 
     * @param sourcestr 页面得到的信息
     * @return
     * @author 吴昊
     */
    public static String getHTMLToString(String sourcestr) {
        if (sourcestr == null) {
            return "";
        }
        sourcestr = sourcestr.replaceAll("\\x26", "&amp;");// &
        sourcestr = sourcestr.replaceAll("\\x3c", "&lt;");// <
        sourcestr = sourcestr.replaceAll("\\x3e", "&gt;");// >
        sourcestr = sourcestr.replaceAll("\\x09", "&nbsp;&nbsp;&nbsp;&nbsp;");// tab键
        sourcestr = sourcestr.replaceAll("\\x20", "&nbsp;");// 空格
        sourcestr = sourcestr.replaceAll("\\x22", "&quot;");// "

        sourcestr = sourcestr.replaceAll("\r\n", "<br>");// 回车换行
        sourcestr = sourcestr.replaceAll("\r", "<br>");// 回车
        sourcestr = sourcestr.replaceAll("\n", "<br>");// 换行
        return sourcestr;
    }

    /**
     * 说明方法描述：把数据库里面的信息回显到页面上
     * 
     * @param sourcestr 数据库取得的信息
     * @return
     * @author 吴昊
     */
    public static String getStringToHTML(String sourcestr) {
        if (sourcestr == null) {
            return "";
        }
        sourcestr = sourcestr.replaceAll("&amp;", "\\x26");// &
        sourcestr = sourcestr.replaceAll("&lt;", "\\x3c");// <
        sourcestr = sourcestr.replaceAll("&gt;", "\\x3e");// >
        sourcestr = sourcestr.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "\\x09");// tab键
        sourcestr = sourcestr.replaceAll("&nbsp;", "\\x20");// 空格
        sourcestr = sourcestr.replaceAll("&quot;", "\\x22");// "

        sourcestr = sourcestr.replaceAll("<br>", "\r\n");// 回车换行
        sourcestr = sourcestr.replaceAll("<br>", "\r");// 回车
        sourcestr = sourcestr.replaceAll("<br>", "\n");// 换行
        return sourcestr;
    }

}
