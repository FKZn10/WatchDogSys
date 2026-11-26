package com.jpmsoft.common.utils;

//import org.apache.commons.lang.ObjectUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 判空
 *
 * @author : zhangl
 */
public abstract class ValidateUtil {

    /**
     * Check whether string s is null
     */
    public static boolean isNull(Object s) {
        return (s == null);
    }

    /**
     * Check whether string s is NOT null
     */
    public static boolean isNotNull(Object s) {
        return (s != null);
    }

    /**
     * Check whether string s is null
     */
    public static boolean isNull(String s) {
        return (s == null);
    }

    /**
     * Check whether string s is NOT null
     */
    public static boolean isNotNull(String s) {
        return (s != null);
    }

    /**
     * Check whether strings is empty.
     */
    public static boolean isEmpty(String s) {
        return ((s == null) || (s.length() == 0));
    }

    public static boolean isEmpNum(String s) {
        return ((s == null) || (s.length() == 0)) || s.equalsIgnoreCase("null");
    }

    /**
     * 校验当前字符串是否为空
     *
     * @param s
     * @return 空则返回null 否则返回本身
     */
    public static String checkEmpty(String s) {
        return isEmpty(s) ? null : s;
    }

    /**
     * 多个字符串其中一个为empty则返回true
     * 均不为empty则返回false
     *
     * @param args
     * @return
     */
    public static boolean isAnObjEmpty(String... args) {
        for (String s : args) {
            if (ValidateUtil.isEmpty(s))
                return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空
     * 空则返回空字符
     *
     * @param s
     * @return
     */
    public static String isEmptyReturnEmptyStr(String s) {
        return isEmpty(s) ? "" : s;
    }

    /**
     * 用于便于替换原org.springframework.util.StringUtils
     */
    public static boolean isEmpty(Object obj) {
        return ((obj == null) || (obj.toString().equals("")));
    }

    /**
     * Check whether collection c is empty.
     */
    public static boolean isEmpty(Collection<?> c) {
        return ((c == null) || (c.size() == 0));
    }

    /**
     * Check whether string s is NOT empty.
     */
    public static boolean isNotEmpty(String s) {
        return ((s != null) && (s.length() > 0));
    }
    /** Check whether object o is NOT empty. */
//	public static boolean isNotEmpty(Object o) {
//		String str = ObjectUtils.toString(o, "");
//		return isNotEmpty(str);
//	}

    /**
     * Check whether collection c is NOT empty.
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return ((c != null) && (c.size() > 0));
    }

    /**
     * Check whether Map c is NOT empty.
     */
    public static boolean isNotEmpty(Map<?, ?> c) {
        return ((c != null) && (!c.isEmpty()));
    }

    /**
     * Check whether Object c is a String.
     */
    public static boolean isString(Object obj) {
        return ((obj != null) && (obj instanceof String));
    }

    /**
     * Check whether Map map is empty.
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return ((map == null) || map.isEmpty());
    }

    /**
     * Check whether String[] s is empty
     */
    public static boolean isEmpty(String[] s) {
        return ((s == null) || (s.length == 0));
    }

    /**
     * Check whether String[] s is NOT empty
     */
    public static boolean isNotEmpty(String[] s) {
        return ((s != null) && (s.length > 0));
    }

    /**
     * Check whether a trimed String s is empty
     */
    public static boolean isTrimEmpty(String s) {
        return ((s == null) || (s.trim().length() == 0));
    }

    /**
     * Returns true if all characters are correct email format
     */
    public static boolean isEmail(String email) {
        return email != null
                && email
                .matches("(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)");
    }

    /**
     * Return true if all characters are correct URL format
     */
    public static boolean isUrl(String url) {
        return url != null
                && url.matches("http://([w-]+.)+[w-]+(/[\\w- ./?%&=]*)?");
    }

    /**
     * Check whether Array array is empty
     */
    public static boolean isEmptyArray(Object array) {
        if (null == array)
            return true;
        if (array.getClass().isArray())
            return Array.getLength(array) == 0;
        return false;
    }

    /**
     * 全部为空
     *
     * @param args
     * @return
     */
    public static boolean isAllEmpty(String... args) {
        for (String s : args) {
            if (ValidateUtil.isNotEmpty(s))
                return false;
        }
        return true;
    }

    /**
     * 字符串内不包含其中一个字符
     * 则返回true 反之则false
     *
     * @param value
     * @param c
     * @return
     */
    public static boolean notContains(String value, String... c) {
        for (String s : c) {
            if (!value.contains(s))
                return true;
        }
        return false;
    }

    public static boolean allNotEmpty(String... args) {
        for (String arg : args) {
            if (ValidateUtil.isEmpty(arg)) return false;
        }
        return true;
    }
}
