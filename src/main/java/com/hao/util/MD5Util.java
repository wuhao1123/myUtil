package com.hao.util;

import com.hao.util.base.StringUtil;
import com.hao.util.base.ValidateException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 * 类MD5Util.java的实现描述：MD5工具类
 * 
 * @author 吴昊
 */
public class MD5Util {

    // URL有效期：5分钟
    private static final long URL_EFFECTIVE_TIME = 5 * 1000 * 60;

    /**
     * 方法描述:将字符串MD5加码 生成32位md5码
     * 
     * @author 吴昊
     * @param inStr
     * @return
     */
    public static String md5(String inStr) throws ValidateException, Exception {
        try {
            return DigestUtils.md5Hex(inStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new ValidateException("MD5签名过程中出现错误");
        }
    }

    /**
     * 方法描述:验证url有效期
     * 
     * @author 吴昊
     * @param timestamp
     * @throws Exception
     */
    public static void validateURLEffective(long timestamp) throws ValidateException, Exception {
        long now = System.currentTimeMillis();
        if ((now - timestamp) > (URL_EFFECTIVE_TIME)) {
            // System.out.println("URL链接失效 URL link failure");
            throw new ValidateException(URLEncoder.encode("URL链接失效", "UTF-8"));
        }
    }

    /**
     * 方法描述:签名字符串
     * 
     * @author 吴昊
     * @param params 需要签名的参数
     * @param appSecret 签名密钥
     * @return
     */
    public static String sign(HashMap<String, Object> params, String appSecret) throws ValidateException, Exception {
        StringBuilder valueSb = new StringBuilder();
        params.put("appSecret", appSecret);
        // 将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<String, Object>(params);
        Set<Entry<String, Object>> entrys = sortParams.entrySet();
        // 遍历排序的字典,并拼接value1+value2......格式
        for (Entry<String, Object> entry : entrys) {
            if (entry.getValue() != null) {
                valueSb.append(entry.getValue());
                // System.out.println("params sort :" + entry.getKey() + "-------->" + entry.getValue());
            }
        }
        // 此处删除appSecret，因为appSecret不能出现在url上，否则生成MD5签名无法验证
        params.remove("appSecret");
        return md5(valueSb.toString());
    }

    /**
     * 方法描述:验证签名
     * 
     * @author 吴昊
     * @param appSecret 加密秘钥
     * @param request
     * @return
     * @throws Exception
     */
    public static boolean verify(String appSecret, HttpServletRequest request) throws ValidateException, Exception {

        String sign = request.getParameter("sign");
        if (sign == null) {
            throw new ValidateException(URLEncoder.encode("请求中没有带签名", "UTF-8"));
        }
        if (request.getParameter("timestamp") == null) {
            throw new ValidateException(URLEncoder.encode("请求中没有带时间戳", "UTF-8"));
        }
        // 验证url有效期
        validateURLEffective(Long.valueOf(request.getParameter("timestamp")));

        HashMap<String, String> params = new HashMap<String, String>();

        // 获取url参数
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign") && StringUtil.isNotBlank(request.getParameter(paramName))) {
                // 拼接参数值字符串并进行utf-8解码，防止中文乱码产生
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
            }
        }

        params.put("appSecret", appSecret);

        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortParams.entrySet();

        // 遍历排序的字典,并拼接value1+value2......格式
        StringBuilder valueSb = new StringBuilder();
        for (Entry<String, String> entry : entrys) {
            valueSb.append(entry.getValue());
            // System.out.println("params sort :" + entry.getKey() + "-------->" + entry.getValue());
        }

        String mysign = md5(valueSb.toString());
        // System.out.println("mysign-------------------------->" + mysign);
        // System.out.println("sign-------------------------->" + sign);
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }

    }

}
