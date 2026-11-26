package com.jpmsoft.projectProducer.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.easyexcel.annotations.ExcelImportValid;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel监听器
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 10:57
 */
@Slf4j
public class EasyExcelReadListener<T> extends AnalysisEventListener<T> {

    public List<T> list = new ArrayList<>();
    private int cellIndex = 1;

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        log.info("解析到一条数据：{}", JacksonUtils.toJSONString(t));
        //检查@ExcelValid标注字段
        ExcelImportValid.valid(t,cellIndex);
        list.add(t);
        cellIndex += 1;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
