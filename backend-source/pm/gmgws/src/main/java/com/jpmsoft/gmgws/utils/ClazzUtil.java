package com.jpmsoft.gmgws.utils;

import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.gmgws.constants.ResMsg;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 11:51
 */
public class ClazzUtil {

    public static Field getField(List<Field> fieldList, String field) {
        String internedName = field.intern();
        for (int i = 0; i < fieldList.size(); i++) {
            Field f = fieldList.get(i);
            if (f.getName() == internedName) {
                return f;
            }
        }
        throw new JPMException(String.format(ResMsg.notTheReq, field));
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }


}
