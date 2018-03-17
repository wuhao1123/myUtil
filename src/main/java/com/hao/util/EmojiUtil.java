package com.hao.util;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.hao.util.base.StringUtil;

/**
 * @ClassName EmojiUtil.java
 * @Description 功能描述：
 * @author leon 2017年9月15日 上午12:33:27
 * @CopyRight 杭州微财网络科技有限公司
 */
public class EmojiUtil {

    private static EmojiConverter emojiConverter = EmojiConverter.getInstance();

    /**
     * @Description 方法描述：将emojiStr转为 带有表情的字符
     * @author 吴昊 2018年3月6日20:02:38
     * @param emojiStr
     * @return
     */
    public static String emojiConverterUnicodeStr(String emojiStr) {
        if (StringUtil.isBlank(emojiStr)) {
            return null;
        }
        String result = emojiConverter.toUnicode(emojiStr);
        return result;
    }

    /**
     * @Description 方法描述： 带有表情的字符串转换为编码
     * @author 吴昊 2018年3月6日20:02:38
     * @param str
     * @return
     */
    public static String emojiConverterToAlias(String str) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        String result = emojiConverter.toAlias(str);
        return result;
    }

}
