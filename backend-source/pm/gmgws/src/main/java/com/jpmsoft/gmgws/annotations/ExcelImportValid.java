package com.jpmsoft.gmgws.annotations;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.constants.FileMsg;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * 验证ExcelValid注解标注字段是否为空
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 11:25
 */
@Component
public class ExcelImportValid {



    /**
     * @param object    行对象
     * @param cellIndex 当前为X行数据
     * @throws JPMException
     */
    public static void valid(Object object, int cellIndex) throws JPMException {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            //设置可访问
            field.setAccessible(true);
            //属性的值
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new JPMException(field.getAnnotation(ExcelValid.class).message());
            }
            //是否包含必填校验注解
            boolean isExcelValid = field.isAnnotationPresent(ExcelValid.class);
            if (isExcelValid && Objects.isNull(fieldValue)) {
                throw new JPMException("第"+ cellIndex + "行" + field.getAnnotation(ExcelValid.class).message());
            }

        }

    }
}
