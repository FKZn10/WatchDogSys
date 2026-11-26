package com.jpmsoft.projectProducer.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.jpmsoft.common.utils.ValidateUtil;

/**
 * easyexcel自定义转换器
 * 字符串转布尔值
 * @author: 李文涛 aaron
 * @date: 2022/8/2 14:12
 */
public class CustomStrBoolConverter implements Converter<Boolean> {

    @Override
    public Boolean convertToJavaData(ReadConverterContext<?> context) throws Exception {
        String str = context.getReadCellData().getStringValue();
        if (ValidateUtil.isEmpty(str)) return null;
        if (str.equals("是")) return true;
        else return false;
    }
}
