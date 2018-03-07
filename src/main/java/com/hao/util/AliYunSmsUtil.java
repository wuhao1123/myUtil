package com.hao.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;

/**
 * @ClassName AliYunSmsUtil.java
 * @Description 功能描述： 阿里云短信发送服务
 * @author 吴昊 2018年3月6日20:02:38
 */
public class AliYunSmsUtil {

    private final static String REGION_ID    = "cn-hangzhou";                // 地区
    private final static String PRODUCT      = "Sms";                        // 产品：Sms短信
    private final static String DOMAIN       = "sms.aliyuncs.com";           // 发送短信域名
    // 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
    private final static String PARAM_STRING = "{\"code\":\" msgContent \"}";

    /**
     * * 方法实现描述:发送短信验证码
     * <p>
     * 具体说明请参考https://help.aliyun.com/document_detail/44366.html?spm=5176.2020520168.101.5.Yz9Dm2
     * </p>
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param accessKeyId 阿里云后台配置的key
     * @param secret 短信秘钥
     * @param signName 短信签名，
     * @param phone 电话号码
     * @param msgContent 短信内容
     * @param msgTemplateCode 短信模板code：管理控制台中配置的审核通过的短信模板的模板CODE（状态必须是验证通过）,如SMS_18200843
     * @return
     */
    public static boolean sendValidateCodeMsg(String accessKeyId, String secret, String signName, String phone,
                                              String msgContent, String msgTemplateCode) {
        CommonValidate.me.checkParam(accessKeyId);
        CommonValidate.me.checkParam(secret);
        CommonValidate.me.checkParam(signName);
        CommonValidate.me.checkParam(phone);
        CommonValidate.me.checkParam(msgContent);
        CommonValidate.me.checkParam(msgTemplateCode);
        boolean isSendSuccess = false;
        try {
            IClientProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, secret);
            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName(signName);
            request.setTemplateCode(msgTemplateCode);
            request.setParamString(PARAM_STRING.replace("msgContent", msgContent));
            // 目标手机号，多个手机号可以逗号分隔
            request.setRecNum(phone);
            client.getAcsResponse(request);
            isSendSuccess = true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return isSendSuccess;
    }

    /**
     * * 方法实现描述:发送短信
     * <p>
     * 具体说明请参考https://help.aliyun.com/document_detail/44366.html?spm=5176.2020520168.101.5.Yz9Dm2
     * </p>
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param accessKeyId 阿里云后台配置的key
     * @param secret 短信秘钥
     * @param signName 短信签名，
     * @param phone 电话号码
     * @param msgContent 短信内容
     * @param msgTemplateCode 短信模板code：管理控制台中配置的审核通过的短信模板的模板CODE（状态必须是验证通过）,如SMS_18200843
     * @return
     */
    public static boolean sendMsg(String accessKeyId, String secret, String signName, String phone, String msgContent,
                                  String msgTemplateCode) {
        CommonValidate.me.checkParam(accessKeyId);
        CommonValidate.me.checkParam(secret);
        CommonValidate.me.checkParam(signName);
        CommonValidate.me.checkParam(phone);
        CommonValidate.me.checkParam(msgTemplateCode);
        boolean isSendSuccess = false;
        try {
            IClientProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, secret);
            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName(signName);
            request.setTemplateCode(msgTemplateCode);
            if (StringUtil.isNotBlank(msgContent)) {
                request.setParamString(msgContent);
            }
            // 目标手机号，多个手机号可以逗号分隔
            request.setRecNum(phone);
            client.getAcsResponse(request);
            isSendSuccess = true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return isSendSuccess;
    }

}
