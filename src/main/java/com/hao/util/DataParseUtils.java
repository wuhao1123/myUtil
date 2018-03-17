package com.hao.util;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.hao.util.base.ResultModel;
import com.hao.util.base.StringUtil;

import java.util.List;

/**
 * 类DataParseUtils.java的实现描述：转换请求的接口数据
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public class DataParseUtils {

    /**
     * 方法描述:返回ResultModel
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ResultModel<T> parseResultModel(String json, Class<T> clazz) {

        if (StringUtil.isBlank(json)) {
            return null;
        }
        if (clazz.getClass().isInstance(String.class)) {
            return JSON.parseObject(json, ResultModel.class);
        }
        ResultModel<T> resultModel = JSON.parseObject(json, ResultModel.class);
        resultModel.setReturnValue(JSON.parseObject(resultModel.getReturnValue().toString(), clazz));
        return resultModel;
    }

    /**
     * 方法描述:返回ResultModel包装的list集合
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ResultModel<List<T>> parseResultModelList(String json, Class<T> clazz) {
        if (StringUtil.isBlank(json)) {
            return null;
        }
        ResultModel<List<T>> resultModel = JSON.parseObject(json, ResultModel.class);
        resultModel.setReturnValue(parseList(resultModel.getReturnValue().toString(), clazz));
        return resultModel;
    }

    /**
     * 方法描述:返回一个list集合
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseArray(json, clazz);
    }

    /**
     * 方法描述:返回带分页的list
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ResultModel<PageInfo<T>> parseResultModelPageInfoList(String json, Class<T> clazz) {
        if (StringUtil.isBlank(json)) {
            return null;
        }
        ResultModel<PageInfo<T>> resultModel = JSON.parseObject(json, ResultModel.class);
        PageInfo<T> page = JSON.parseObject(resultModel.getReturnValue().toString(), PageInfo.class);
        resultModel.setReturnValue(page);
        List<T> objects = JSON.parseArray(resultModel.getReturnValue().getList().toString(), clazz);
        resultModel.getReturnValue().setList(objects);
        return resultModel;
    }

}
