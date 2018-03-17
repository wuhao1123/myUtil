package com.hao.util;

import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 类GetPlaceByIp.java的实现描述：根据ip获得地区
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public class GetPlaceByIp {

    /**
     * 方法描述:根据ip获得城市信息（百度api：http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + ip）
     * 
     * @author 吴昊 2018年3月17日09:01:42
     * @param ip
     * @return
     * @throws IOException
     */
    public static String getCityFromIpByBaiduApi(String ip) throws IOException {
        String url = "http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + ip;
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            ObjectMapper mapper = new ObjectMapper();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = mapper.readValue(jsonText, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> content = (Map<String, Object>) map.get("content");
            @SuppressWarnings("unchecked")
            Map<String, Object> address_detail = (Map<String, Object>) content.get("address_detail");
            return address_detail.get("city").toString();

        } finally {
            is.close();
        }
    }

    /**
     * 方法描述:根据ip获得地区省份（百度api：http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + ip）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     * @throws IOException
     */
    public static String getProvinceFromIpByBaiduApi(String ip) throws IOException {
        String url = "http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + ip;
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            ObjectMapper mapper = new ObjectMapper();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = mapper.readValue(jsonText, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> content = (Map<String, Object>) map.get("content");
            @SuppressWarnings("unchecked")
            Map<String, Object> address_detail = (Map<String, Object>) content.get("address_detail");
            return address_detail.get("province").toString();

        } finally {
            is.close();
        }
    }

    /**
     * 方法描述:根据ip获得地区map（百度api：http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + ip）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getAddressMapFromIpByBaiduApi(String ip) throws IOException {
        String url = "http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + ip;
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(jsonText, Map.class);
            Map<String, Object> content = (Map<String, Object>) map.get("content");
            return (Map<String, Object>) content.get("address_detail");
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * 方法描述:根据ip获取城市信息（新浪http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=xxx）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     */
    public static String getCityFromIpBySina(String ip) {
        try {
            String resout = OkHttpUtils.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip);
            JSONObject obj = JSONObject.fromObject(resout);
            return obj.get("city") + "市";
        } catch (Exception e) {
            return "未知";
        }
    }

    /**
     * 方法描述:根据ip获取省份信息（新浪http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=xxx）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     */
    public static String getProvinceFromIpBySina(String ip) {
        try {
            String resout = OkHttpUtils.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip);
            JSONObject obj = JSONObject.fromObject(resout);
            return obj.get("province") + "省";
        } catch (Exception e) {
            return "未知";
        }
    }

    /**
     * 方法描述:根据ip获取地址信息：省市map（新浪http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=xxx）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     */
    public static Map<String, String> getAddressMapFromIpBySina(String ip) {
        Map<String, String> addressMap = new HashMap<String, String>();
        try {
            String resout = OkHttpUtils.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip);
            JSONObject obj = JSONObject.fromObject(resout);
            addressMap.put("province", obj.get("province") + "省");
            addressMap.put("city", obj.get("city") + "市");
            return addressMap;
        } catch (Exception e) {
            addressMap.put("pro", "未知");
            addressMap.put("city", "未知");
            return addressMap;
        }
    }

    /**
     * 方法描述:根据ip获取省市信息（太平洋网络http://whois.pconline.com.cn/ipJson.jsp?ip=xxx）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     */
    public static String getAddressFromIpByPconline(String ip) {
        String resout = "";
        try {
            resout = OkHttpUtils.get("http://whois.pconline.com.cn/ipJson.jsp?ip=" + ip);
            resout = resout.replace("if(window.IPCallBack) {IPCallBack(", "");
            resout = resout.substring(0, resout.lastIndexOf(");}"));
            JSONObject obj = JSONObject.fromObject(resout);
            return obj.get("pro") + "" + obj.get("city");
        } catch (Exception e) {
            return "未知";
        }
    }

    /**
     * 方法描述:根据ip获取城市信息（太平洋网络http://whois.pconline.com.cn/ipJson.jsp?ip=xxx）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     */
    public static String getCityFromIpByPconline(String ip) {
        String resout = "";
        try {
            resout = OkHttpUtils.get("http://whois.pconline.com.cn/ipJson.jsp?ip=" + ip);
            resout = resout.replace("if(window.IPCallBack) {IPCallBack(", "");
            resout = resout.substring(0, resout.lastIndexOf(");}"));
            JSONObject obj = JSONObject.fromObject(resout);
            return obj.get("city") + "";
        } catch (Exception e) {
            return "未知";
        }
    }

    /**
     * 方法描述:根据ip获取省份信息（太平洋网络http://whois.pconline.com.cn/ipJson.jsp?ip=xxx）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     */
    public static String getProvinceFromIpByPconline(String ip) {
        String resout = "";
        try {
            resout = OkHttpUtils.get("http://whois.pconline.com.cn/ipJson.jsp?ip=" + ip);
            resout = resout.replace("if(window.IPCallBack) {IPCallBack(", "");
            resout = resout.substring(0, resout.lastIndexOf(");}"));
            JSONObject obj = JSONObject.fromObject(resout);
            return obj.get("pro") + "";
        } catch (Exception e) {
            return "未知";
        }
    }

    /**
     * 方法描述:根据ip获取地址信息：省市map（太平洋网络http://whois.pconline.com.cn/ipJson.jsp?ip=xxx）
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param ip
     * @return
     */
    public static Map<String, String> getAddressMapFromIpByPconline(String ip) {
        String resout = "";
        Map<String, String> addressMap = new HashMap<>();
        try {
            resout = OkHttpUtils.get("http://whois.pconline.com.cn/ipJson.jsp?ip" + ip);
            resout = resout.replace("if(window.IPCallBack) {IPCallBack(", "");
            resout = resout.substring(0, resout.lastIndexOf(");}"));
            JSONObject obj = JSONObject.fromObject(resout);
            addressMap.put("province", obj.get("pro") + "");
            addressMap.put("city", obj.get("city") + "");
            return addressMap;
        } catch (Exception e) {
            addressMap.put("pro", "未知");
            addressMap.put("city", "未知");
            return addressMap;
        }
    }

    public static void main(String[] args) throws IOException {
        // 百度接口
        System.out.println(getProvinceFromIpByBaiduApi("125.119.100.154"));
        System.out.println(getCityFromIpByBaiduApi("125.119.100.154"));
        System.out.println(getAddressMapFromIpByBaiduApi("125.119.100.154"));
        // 新浪接口
        System.out.println(getProvinceFromIpBySina("125.119.100.154"));
        System.out.println(getCityFromIpBySina("125.119.100.154"));
        System.out.println(getAddressMapFromIpBySina("125.119.100.154"));
        // 太平洋网络接口
        System.out.println(getProvinceFromIpByPconline("125.119.100.154"));
        System.out.println(getCityFromIpByPconline("125.119.100.154"));
        System.out.println(getAddressMapFromIpByPconline("125.119.100.154"));
    }

}
