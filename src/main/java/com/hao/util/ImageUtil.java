package com.hao.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hao.util.base.StringUtil;
import com.tinify.Tinify;

/**
 * @ClassName ImageUtil.java
 * @Description 类实现描述:处理图片工具类
 * @author 吴昊 2018年3月6日20:02:38
 */
public class ImageUtil {

    /**
     * 左上角
     */
    private final static String LEFT_TOP     = "left_top";
    /**
     * 右上角
     */
    private final static String RIGHT_TOP    = "right_top";
    /**
     * 左下角
     */
    private final static String LEFT_BOTTOM  = "left_bottom";
    /**
     * 右下角
     */
    private final static String RIGHT_BOTTOM = "right_bottom";

    /**
     * 说明方法描述：根据宽高压缩图片
     * 
     * @param srcImgPath 源图片路径
     * @param compressedImagePath 压缩图片保存的路径
     * @param width 压缩图片的宽
     * @param height 压缩图片的高
     * @throws IOException
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static void compressedImageByWidthAndHeight(String srcImgPath, String compressedImgPath, int width,
                                                       int height) throws IOException {
        File srcFile = new File(srcImgPath);
        Image srcImg = ImageIO.read(srcFile);
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffImg.getGraphics().drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        ImageIO.write(buffImg, "JPEG", new File(compressedImgPath));
    }

    /**
     * 把水印印刷到图片上
     * 
     * @param pressImg -- 水印文件
     * @param targetImg -- 目标文件
     * @param loaction 水印位置：left-top：左上角，right-top：右上角，left-bottom：左下角，right-bottom：右下角
     * @param degree 水印旋转角度
     * @author 吴昊 2018年3月6日20:02:38
     */
    public final static void pressImage(String pressImg, String targetImg, String loaction, Integer degree) {
        try {
            // 目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            // 水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            // 水印坐标
            int x = 0, y = 0;
            if (StringUtil.equals(loaction, LEFT_TOP)) {
                g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
            } else if (StringUtil.equals(loaction, RIGHT_TOP)) {
                x = wideth - wideth_biao;
            } else if (StringUtil.equals(loaction, LEFT_BOTTOM)) {
                y = height - height_biao;
            } else if (StringUtil.equals(loaction, RIGHT_BOTTOM)) {
                x = wideth - wideth_biao;
                y = height - height_biao;
            } else {
                x = (wideth - wideth_biao) / 2;
                y = (height - height_biao) / 2;
            }

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), x, y);
            }
            g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName, new File(targetImg));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印文字水印图片
     * 
     * @par pressText --文字
     * @pam targetImg -- 目标图片
     * @par fontName -- 字体名
     * @param foStyle -- 字体
     * @param color -- 字体颜色
     * @param fontSize -- 字体大小
     * @param x -- 偏移量
     * @param y
     */

    public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, int color,
                                 int fontSize, int x, int y) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            g.setColor(Color.RED);
            g.setFont(new Font(fontName, fontStyle, fontSize));

            g.drawString(pressText, wideth - fontSize - x, height - fontSize / 2 - y);
            g.dispose();

            String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName, new File(targetImg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法描述:根据url压缩
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param apiKey=H3e0yJpIBkHrLrFVsPGmVDuuLq8WSFaf，可以去https://tinypng.com/dashboard/developers申请，默认500张/天
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] compressionFromUrl(String apiKey, final String url) throws IOException {
        Tinify.setKey(apiKey);
        return Tinify.fromUrl(url).toBuffer();
    }

    /**
     * 方法描述:根据buffer压缩
     * 
     * @author leon 2017年8月17日 下午4:14:59
     * @param apiKey=H3e0yJpIBkHrLrFVsPGmVDuuLq8WSFaf，可以去https://tinypng.com/dashboard/developers申请，默认500张/天
     * @param buffer
     * @return
     * @throws IOException
     */
    public static byte[] compressionFromBuffer(String apiKey, final byte[] buffer) throws IOException {
        Tinify.setKey(apiKey);
        return Tinify.fromBuffer(buffer).toBuffer();
    }

    /**
     * 方法描述:根据本地文件路径压缩
     * 
     * @author leon 2017年8月17日 下午4:14:59
     * @param apiKey=H3e0yJpIBkHrLrFVsPGmVDuuLq8WSFaf，可以去https://tinypng.com/dashboard/developers申请，默认500张/天
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] compressionFromFile(String apiKey, final String path) throws IOException {
        Tinify.setKey(apiKey);
        return Tinify.fromFile(path).toBuffer();
    }

    // public static void main(String[] args) throws IOException {
    // Tinify.setKey("H3e0yJpIBkHrLrFVsPGmVDuuLq8WSFaf");
    // Tinify.fromUrl("https://image.bianxianmao.com/2017/08/24/09c2ab93-f038-457b-89a1-d909f7f9244c").toFile("C:\\Users\\Administrator\\Desktop\\09c2ab93-f038-457b-89a1-d909f7f9244c");
    // }

}
