package com.jpmsoft.gmgws.utils;

import com.jpmsoft.common.utils.ValidateUtil;

public class CheckNullUtil {

    public static final String STR_ENG_PATTERN = "^[a-z0-9A-Z]+$";

    public static Boolean checkString(String... args) {
        for (String s : args) {
            if (ValidateUtil.isEmpty(s))
                return true;
        }
        return false;
    }

    public static Boolean checkObject(Object... args) {
        for (Object o : args) {
            if (o == null)
                return true;
        }
        return false;
    }

    /**
     * 判断字符串是否包含特殊字符
     *
     * @param str
     * @return
     */
    public static Boolean checkStringConSpecialChar(String str) {
        if (ValidateUtil.isEmpty(str)) {
            return Boolean.TRUE;
        }
        boolean matches = str.matches(STR_ENG_PATTERN);
        if (matches) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
}
