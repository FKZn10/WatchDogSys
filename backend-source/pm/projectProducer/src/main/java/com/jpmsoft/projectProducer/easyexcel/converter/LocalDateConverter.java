package com.jpmsoft.projectProducer.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateConverter implements Converter<LocalDate> {

    @Override
    public Class<LocalDate> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(ReadCellData<?> cellData,ExcelContentProperty contentProperty,GlobalConfiguration globalConfiguration) throws Exception {
        if(null==cellData) {
            return null;
        }
        LocalDate result=null;
        if(cellData.getType()==CellDataTypeEnum.NUMBER) {
            if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
                Date date= DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                        globalConfiguration.getUse1904windowing(), null);
                result =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else {
                Date date=  DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                        contentProperty.getDateTimeFormatProperty().getUse1904windowing(), null);
                result =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        }if(cellData.getType()==CellDataTypeEnum.STRING) {
            String value=cellData.getStringValue();
            if(value.contains("-")) {
                try {
                    result= LocalDate.parse(cellData.getStringValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(value.contains("/")) {
                try {
                    result= LocalDate.parse(new SimpleDateFormat("yyyy/MM/dd").format( value) );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

}