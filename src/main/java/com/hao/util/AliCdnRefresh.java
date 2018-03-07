
package com.hao.util;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesRequest;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @ClassName AliCdnRefresh.java
 * @Description 功能描述： 阿里云CDN刷新
 * @author 吴昊 2018年3月6日19:36:52
 */
public class AliCdnRefresh {

    // 阿里云管理员账号给你生成的key和value
    private final static String OBJECT_TYPE_FILE      = "File";

    private final static String OBJECT_TYPE_DIRECTORY = "Directory";

    private final static String REGION_ID             = "cn-hangzhou"; // 地区

    /**
     * @Description 方法描述： 刷新一个文件
     * @author 吴昊 2018年3月6日19:38:05
     * @param accessKeyId
     * @param secret
     * @param url
     * @return
     */
    public static RefreshObjectCachesResponse reflushFileCDN(String accessKeyId, String secret, String url) {
        CommonValidate.me.checkParam(accessKeyId);
        CommonValidate.me.checkParam(secret);
        CommonValidate.me.checkParam(url);
        RefreshObjectCachesRequest describe = new RefreshObjectCachesRequest();
        describe.setObjectPath(url);
        describe.setObjectType(OBJECT_TYPE_FILE);
        return reflush(accessKeyId, secret, describe);
    }

    /**
     * @Description 方法描述： 刷新文件夹
     * @author 吴昊 2018年3月6日19:59:13
     * @param accessKeyId
     * @param secret
     * @param packageUrl
     * @return
     */
    public static RefreshObjectCachesResponse reflushPackageCDN(String accessKeyId, String secret, String packageUrl) {
        CommonValidate.me.checkParam(accessKeyId);
        CommonValidate.me.checkParam(secret);
        CommonValidate.me.checkParam(packageUrl);
        RefreshObjectCachesRequest describe = new RefreshObjectCachesRequest();
        describe.setObjectPath(packageUrl);
        describe.setObjectType(OBJECT_TYPE_DIRECTORY);
        return reflush(accessKeyId, secret, describe);
    }

    private static RefreshObjectCachesResponse reflush(String accessKeyId, String secret,
                                                       RefreshObjectCachesRequest describe) {
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        try {
            RefreshObjectCachesResponse response = client.getAcsResponse(describe);
            return response;
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // public static void main(String[] args) {
    // System.out.println(reflushFileCDN("http://file.migree.com.cn/2017/email_template.html").getRefreshTaskId());
    // }
}
