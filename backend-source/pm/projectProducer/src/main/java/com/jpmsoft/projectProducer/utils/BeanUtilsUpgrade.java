package com.jpmsoft.projectProducer.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


public class BeanUtilsUpgrade extends BeanUtils {

    /**
     * object转换
     * @param entity
     * @param targetCls
     * @param <T>
     * @return
     */
    public static <T> T copyObject(Object entity, Class<? extends T> targetCls) {
        if (entity == null) {
            return null;
        }
        Object target = null;
        try {
            target = targetCls.newInstance();
            copyProperties(entity, target);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) target;
    }

    /**
     * object集合转换
     * @param list
     * @param targetCls
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List list, Class<? extends T> targetCls) {
        List<T> resultList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Object entity : list) {
                resultList.add(copyObject(entity, targetCls));
            }
        }
        return resultList;
    }
}
