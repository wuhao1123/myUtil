
package com.hao.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ListUtil.java
 * @Description 功能描述： 集合操作类
 * @author 吴昊 2018年3月17日09:02:11
 */
public class ListUtil {

    /**
     * 说明方法描述：拆分集合
     * 
     * @param resList 待拆分的集合
     * @param count 拆分后每个集合的数据量
     * @return
     * @time 2018年3月17日09:02:19
     * @author 吴昊
     */
    public static <T> List<List<T>> splitList(List<T> resList, int count) {

        if (resList == null || count < 1) {
            return null;
        }
        List<List<T>> ret = new ArrayList<List<T>>();
        int size = resList.size();
        if (size <= count) { // 数据量不足count指定的大小
            ret.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            // 前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                ret.add(itemList);
            }
            // last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                ret.add(itemList);
            }
        }
        return ret;

    }

    /**
     * 说明方法描述：给集合分页
     * 
     * @param listSize 集合元素数量
     * @param startNum 开始
     * @param endNum 结束
     * @return
     * @time 2018年3月17日09:02:28
     * @author 吴昊
     */
    public static List<Object> getTotalPage(int listSize, int startNum, int endNum) {
        // ================将数据分页==============
        // 查询后的数据集合,该对象同样用户截取后的数据集合
        List<Object> obj = new ArrayList<Object>();
        // 总的页数
        int pageCount = 0;
        /* 计算出总共能分成多少页 */
        if (listSize % endNum > 0) // 数据总数和每页显示的总数不能整除的情况
        {
            pageCount = listSize / endNum + 1;
        } else // 数据总数和每页显示的总数能整除的情况
        {
            pageCount = listSize / endNum;
        }
        if (listSize > 0) {
            if (startNum <= pageCount) {
                if (startNum == 1) // 当前页数为第一页
                {
                    if (listSize <= endNum) // 数据总数小于每页显示的数据条数
                    {
                        // 截止到总的数据条数(当前数据不足一页，按一页显示)，这样才不会出现数组越界异常
                        obj = obj.subList(0, listSize);
                    } else {
                        obj = obj.subList(0, endNum);
                    }
                } else {
                    // 截取起始下标
                    int fromIndex = (startNum - 1) * endNum;
                    // 截取截止下标
                    int toIndex = startNum * endNum;
                    /* 计算截取截止下标 */
                    if ((listSize - toIndex) % endNum >= 0) {
                        toIndex = startNum * endNum;
                    } else {
                        toIndex = (startNum - 1) * endNum + (listSize % endNum);
                    }
                    if (listSize >= toIndex) {
                        obj = obj.subList(fromIndex, toIndex);
                    }
                }
            } else {
                obj = null;
            }
        }
        return obj;
    }

    /**
     * 说明：将list转换为数组
     * 
     * @param params
     * @return
     * @author 吴昊
     */
    public static Object[] listToArray(List<Object> params) {
        Object[] objs = new Object[0];
        if (params != null && params.size() > 0) {
            objs = new Object[params.size()];
            int index = 0;
            for (Object object : params) {
                objs[index] = object;
                index += 1;
            }
        }
        return objs;
    }

}
