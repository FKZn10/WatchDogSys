package com.jpmsoft.fileUpload.utils;

import com.jpmsoft.common.utils.ValidateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

@Deprecated
public class CheckNullUtil {

    @Deprecated
    public static Boolean checkString(String... args) {
        for (String s : args) {
            if (ValidateUtil.isEmpty(s))
                return true;
        }
        return false;
    }

    @Deprecated
    public static Boolean checkObject(Object... args) {
        for (Object o : args) {
            if (o == null)
                return true;
        }
        return false;
    }


}
