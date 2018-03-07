package com.hao.util;

import org.apache.commons.mail.SimpleEmail;

public class SendEmailUtil {

    /**
     * 方法实现描述:邮件发送
     * 
     * @author 吴昊
     * @param mailServerHost 邮件发送服务器host
     * @param mailServerUser 邮箱服务器用户名
     * @param mailServerPassword 邮箱服务器密码
     * @param mailServerFrom 邮件发送人名称
     * @param toEmail 接收人邮箱
     * @param title 邮件标题
     * @param content 邮件内容
     * @return boolean 发送结果：true：成功
     */
    public static boolean sendEmail(String mailServerHost, final String mailServerUser, final String mailServerPassword,
                                    String mailServerFrom, String toEmail, String title, String content) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName(mailServerHost);
            // 用户名和密码
            email.setAuthentication(mailServerUser, mailServerPassword);
            // 设置编码
            email.setCharset("UTF-8");
            // 接收方
            email.addTo(toEmail);
            // 发件人
            email.setFrom(mailServerUser, mailServerFrom);
            // 标题
            email.setSubject(title);
            // 内容
            email.setMsg(content);
            email.send();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
