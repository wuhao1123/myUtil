
package com.hao.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 类PropUtil.java的实现描述:加载classpath下的properties文件
 * 
 * @author 吴昊
 */
public class PropUtil {

    private static String                  DEFAULT_ENCODING = "UTF-8";

    private static Prop                    prop             = null;
    private static final Map<String, Prop> map              = new HashMap<String, Prop>();

    private PropUtil(){
    }

    /**
     * Using the properties file. It will loading the properties file if not loading.
     * 
     * @see #use(String, String)
     */
    public static Prop use(String fileName) {
        return use(fileName, DEFAULT_ENCODING);
    }

    /**
     * Using the properties file. It will loading the properties file if not loading.
     * <p>
     * Example:<br>
     * PropUtil.use("config.txt", "UTF-8");<br>
     * PropUtil.use("other_config.txt", "UTF-8");<br>
     * <br>
     * String userName = PropUtil.get("userName");<br>
     * String password = PropUtil.get("password");<br>
     * <br>
     * userName = PropUtil.use("other_config.txt").get("userName");<br>
     * password = PropUtil.use("other_config.txt").get("password");<br>
     * <br>
     * PropUtil.use("com/jfinal/config_in_sub_directory_of_classpath.txt");
     * 
     * @param fileName the properties file's name in classpath or the sub directory of classpath
     * @param encoding the encoding
     */
    public static Prop use(String fileName, String encoding) {
        Prop result = map.get(fileName);
        if (result == null) {
            synchronized (map) {
                result = map.get(fileName);
                if (result == null) {
                    result = new Prop(fileName, encoding);
                    map.put(fileName, result);
                    if (PropUtil.prop == null) PropUtil.prop = result;
                }
            }
        }
        return result;
    }

    /**
     * Using the properties file bye File object. It will loading the properties file if not loading.
     * 
     * @see #use(File, String)
     */
    public static Prop use(File file) {
        return use(file, DEFAULT_ENCODING);
    }

    /**
     * Using the properties file bye File object. It will loading the properties file if not loading.
     * <p>
     * Example:<br>
     * PropUtil.use(new File("/var/config/my_config.txt"), "UTF-8");<br>
     * Strig userName = PropUtil.use("my_config.txt").get("userName");
     * 
     * @param file the properties File object
     * @param encoding the encoding
     */
    public static Prop use(File file, String encoding) {
        Prop result = map.get(file.getName());
        if (result == null) {
            synchronized (map) {
                result = map.get(file.getName());
                if (result == null) {
                    result = new Prop(file, encoding);
                    map.put(file.getName(), result);
                    if (PropUtil.prop == null) PropUtil.prop = result;
                }
            }
        }
        return result;
    }

    public static Prop useless(String fileName) {
        Prop previous = map.remove(fileName);
        if (PropUtil.prop == previous) PropUtil.prop = null;
        return previous;
    }

    public static void clear() {
        prop = null;
        map.clear();
    }

    public static Prop getProp() {
        if (prop == null)
            throw new IllegalStateException("Load propties file by invoking PropUtil.use(String fileName) method first.");
        return prop;
    }

    public static Prop getProp(String fileName) {
        return map.get(fileName);
    }

    public static String get(String key) {
        return getProp().get(key);
    }

    public static String get(String key, String defaultValue) {
        return getProp().get(key, defaultValue);
    }

    public static Integer getInt(String key) {
        return getProp().getInt(key);
    }

    public static Integer getInt(String key, Integer defaultValue) {
        return getProp().getInt(key, defaultValue);
    }

    public static Long getLong(String key) {
        return getProp().getLong(key);
    }

    public static Long getLong(String key, Long defaultValue) {
        return getProp().getLong(key, defaultValue);
    }

    public static Boolean getBoolean(String key) {
        return getProp().getBoolean(key);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return getProp().getBoolean(key, defaultValue);
    }

    public static boolean containsKey(String key) {
        return getProp().containsKey(key);
    }
}
