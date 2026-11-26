package com.jpmsoft.projectProducer.easyexcel.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.List;
import java.util.Properties;


public class HeadWriteHandler implements CellWriteHandler {
    private String[] cellWriterPropertys;//属性
    private String[] cellWriterValues;//值
    PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${","}");
 
    public HeadWriteHandler(String cellProperty[],String [] cellvalues) {
        this.cellWriterPropertys=cellProperty;
        this.cellWriterValues = cellvalues;
    }
 
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {
        if (head != null) {
            List<String> headNameList = head.getHeadNameList();
            if (CollectionUtils.isNotEmpty(headNameList)) {
                for (int i = 0; i < cellWriterPropertys.length; i++) {
                    Properties properties = new Properties();
                    properties.setProperty(cellWriterPropertys[i], cellWriterValues[i]);
                    for (int j = 0; j < headNameList.size(); j++) {
                        headNameList.set(j, propertyPlaceholderHelper.replacePlaceholders(headNameList.get(j), properties));
                    }
                }
            }
        }
    }
 
}