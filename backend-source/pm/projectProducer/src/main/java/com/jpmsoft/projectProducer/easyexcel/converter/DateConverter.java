package com.jpmsoft.projectProducer.easyexcel.converter;


import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<Date> {
    private static  final String PATTERN_YYYY_MM_DD = "yyyy/MM/dd";


    @Override
    public Class<Date> supportJavaTypeKey() {
        return Date.class;
    }

    /**
     * easyExcel导出数据类型转换
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     */
    @Override
    public Date convertToJavaData(ReadCellData<?> cellData,ExcelContentProperty contentProperty,GlobalConfiguration globalConfiguration) throws Exception {
        String value = cellData.getStringValue();
        if(StrUtil.isNotEmpty(value)){
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD);
            Date parse = sdf.parse(value);
            return parse;
        }else {
            return null;
        }
    }

    /**
     * easyExcel导入Date数据类型转换
     * @param context
     * @return
     */
    @Override
    public WriteCellData<String> convertToExcelData(WriteConverterContext<Date> context) throws Exception {
        Date date = context.getValue();
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD);
        return new WriteCellData<>(sdf.format(date));
    }
}
