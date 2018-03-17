package com.hao.util;

import com.hao.util.base.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类CatchImage.java的实现描述：抓取的图片工具类
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public class CatchImageUtil {

    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";

    /**
     * 方法说明：获取html中的标签
     * 
     * @param htmlStr
     * @return List<String>
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static List<String> getImageTag(String htmlStr) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(htmlStr);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
    }

    /**
     * 方法说明：获取第一张图片标签
     * 
     * @param htmlStr
     * @return String
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String getFristImageTag(String htmlStr) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(htmlStr);
        String imgUrl = "";
        while (matcher.find()) {
            imgUrl = matcher.group();
        }

        return imgUrl;
    }

    /**
     * 方法说明：根据img标签获取ImageSrc地址
     * 
     * @param imageTag
     * @return String
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String getImageSrc(String imageTag) {
        String listImgSrc = "";
        Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(imageTag);
        while (matcher.find()) {
            listImgSrc = matcher.group().substring(0, matcher.group().length() - 1);
            break;
        }
        return listImgSrc;
    }

    /**
     * 方法说明：根据img标签列表获取ImageSrc地址列表
     * 
     * @param listImageUrl
     * @return List<String>
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static List<String> getImageSrc(List<String> listImageTag) {
        List<String> listImgSrc = new ArrayList<String>();
        for (String image : listImageTag) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return listImgSrc;
    }

    /***
     * 下载图片
     * 
     * @param listImgSrc
     */
    public static void downloadImgList(List<String> listImgUrl, String savePath) {
        try {
            if (listImgUrl != null && StringUtil.isNotBlank(savePath)) {

                // 创建文件夹
                savePath.replace("\\", "/");
                File targetDir = new File(savePath);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }
                for (String url : listImgUrl) {
                    String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                    URL uri = new URL(url);
                    InputStream in = uri.openStream();
                    // 创建文件
                    String filePath = savePath + "/" + imageName;
                    filePath.replace("\\", "/");
                    File file = new File(filePath);
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int length = 0;
                    while ((length = in.read(buf, 0, buf.length)) != -1) {
                        fo.write(buf, 0, length);
                    }
                    in.close();
                    fo.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法说明：下载单张图片
     * 
     * @param imgUrl
     * @param savePath void
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static void downloadOneImg(String imgUrl, String savePath) {
        try {
            if (imgUrl != null && StringUtil.isNotBlank(savePath)) {
                String imageName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.length());
                URL uri = new URL(imgUrl);
                InputStream in = uri.openStream();
                // 创建文件夹
                savePath.replace("\\", "/");
                File targetDir = new File(savePath);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }
                // 创建文件
                String filePath = savePath + "/" + imageName;
                filePath.replace("\\", "/");
                File file = new File(filePath);
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int length = 0;
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }
                in.close();
                fo.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
