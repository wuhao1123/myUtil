
package com.hao.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName OkHttpUtils.java
 * @Description 功能描述： 用于发送http请求
 * @author 吴昊
 */
public class OkHttpUtils {

    // 创建一个 OkHttpClient
    private static final OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(5,
                                                                                                TimeUnit.SECONDS).readTimeout(10,
                                                                                                                              TimeUnit.SECONDS).build();

    // 参数格式
    public static final MediaType     JSON          = MediaType.parse("application/json; charset=utf-8");

    /**
     * 方法描述:httpGet
     * 
     * @author 吴昊
     * @param url serverHost可以带参数如http://www.baidu.com?param1=xxx&param2=xxx
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpGet
     * 
     * @author 吴昊
     * @param url serverHost
     * @param params 参数:map格式
     * @return
     * @throws IOException
     */
    public static String get(String url, Map<String, Object> params) throws IOException {
        // 构建请求参数
        StringBuffer paramSb = new StringBuffer();
        if (params != null) {
            for (Entry<String, Object> e : params.entrySet()) {

                if (e.getValue() != null) {
                    paramSb.append(URLEncoder.encode(e.getKey(), "UTF-8"));
                    paramSb.append("=");
                    // 将参数值urlEncode编码,防止传递中乱码
                    paramSb.append(URLEncoder.encode(e.getValue().toString(), "UTF-8"));
                    paramSb.append("&");
                }
            }
        }
        if (paramSb.length() > 0) {
            String paramStr = paramSb.toString();
            paramStr = paramStr.substring(0, paramStr.length() - 1);
            url += "?" + paramStr;
        }
        System.out.println(url);
        Request request = new Request.Builder().url(url).get().build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpPost
     * 
     * @author 吴昊
     * @param url serverHost
     * @param params 参数:json格式
     * @return
     * @throws IOException
     */
    public static String post(String url, String params) throws IOException {
        RequestBody body = RequestBody.create(JSON, params);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpPost
     * 
     * @author 吴昊
     * @param url serverHost
     * @param params 参数:map格式
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, Object> params) throws IOException {

        FormBody.Builder build = new FormBody.Builder();
        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                build.add(URLEncoder.encode(key, "UTF-8"), URLEncoder.encode(params.get(key).toString(), "UTF-8"));
            }
        }
        RequestBody body = build.build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpPut
     * 
     * @author 吴昊
     * @param url serverHost
     * @param params 参数:json格式
     * @return
     * @throws IOException
     */
    public static String put(String url, String params) throws IOException {
        RequestBody body = RequestBody.create(JSON, params);
        Request request = new Request.Builder().url(url).put(body).build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpPut
     * 
     * @author 吴昊
     * @param url serverHost
     * @param params 参数:map格式数
     * @return
     * @throws IOException
     */
    public static String put(String url, Map<String, Object> params) throws IOException {
        FormBody.Builder build = new FormBody.Builder();
        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                build.add(URLEncoder.encode(key, "UTF-8"), URLEncoder.encode(params.get(key).toString(), "UTF-8"));
            }
        }
        RequestBody body = build.build();

        Request request = new Request.Builder().url(url).put(body).build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpDelete
     * 
     * @author 吴昊
     * @param url serverHost
     * @param params 参数:json格式
     * @return
     * @throws IOException
     */
    public static String delete(String url) throws IOException {
        Request request = new Request.Builder().url(url).delete().build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpPatch
     * 
     * @author 吴昊
     * @param url serverHost
     * @param params 参数:map格式
     * @return
     * @throws IOException
     */
    public static String patch(String url, Map<String, Object> params) throws IOException {
        FormBody.Builder build = new FormBody.Builder();
        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                build.add(URLEncoder.encode(key, "UTF-8"), URLEncoder.encode(params.get(key).toString(), "UTF-8"));
            }
        }
        RequestBody body = build.build();
        Request request = new Request.Builder().url(url).patch(body).build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpPatch
     * 
     * @author 吴昊
     * @param url serverHost
     * @param params 参数:json格式
     * @return
     * @throws IOException
     */
    public static String patch(String url, String params) throws IOException {
        RequestBody body = RequestBody.create(JSON, params);
        Request request = new Request.Builder().url(url).patch(body).build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 方法描述:httpHead
     * 
     * @author 吴昊
     * @param url serverHost
     * @return
     * @throws IOException
     */

    public static String head(String url) throws IOException {
        Request request = new Request.Builder().url(url).head().build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

}
