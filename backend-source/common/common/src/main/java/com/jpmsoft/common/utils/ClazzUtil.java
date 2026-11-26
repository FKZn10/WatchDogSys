package com.jpmsoft.common.utils;



import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/6/26 9:40
 */
public class ClazzUtil {

    /**
     * 获取指定方法
     *
     * @param objectClass
     * @param fieldName
     * @param method
     * @return
     */
    public static Method getMethod(Class<?> objectClass, String fieldName, String method) {
        try {
            Field[] fields = objectClass.getDeclaredFields();
            if (fields.length > 0) {
                if (CollectionUtils.isEmpty(Arrays.stream(fields).filter(n -> n.getName().equals(fieldName)).collect(Collectors.toList())))
                    return null;
            }
            Class<?>[] parameterTypes = new Class<?>[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            return objectClass.getDeclaredMethod(method, parameterTypes);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }


}
