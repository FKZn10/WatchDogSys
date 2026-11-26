package com.jpmsoft.jpbpm.util;

import com.jpmsoft.common.utils.ValidateUtil;

public class CheckNullUtil {

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
}
