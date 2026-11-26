package com.jpmsoft.projectProducer.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.easyexcel.annotations.ExcelImportDataValid;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel监听器
 *
 */
@Slf4j
public class EasyExcelReadDataListener<T> extends AnalysisEventListener<T> {

    public List<T> list = new ArrayList<>();
    private int cellIndex = 1;

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        log.info("解析到一条数据：{}", JacksonUtils.toJSONString(t));
        cellIndex= analysisContext.readRowHolder().getRowIndex()+1;
        //检查@ExcelDataValid标注字段
        ExcelImportDataValid.valid(t,cellIndex);
        list.add(t);
        cellIndex += 1;
    }

    /**
     * 所有数据解析完成了 都会来调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
         log.info("所有数据解析完成了 都会来调用");
    }
}
