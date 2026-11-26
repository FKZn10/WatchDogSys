package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/5/21 16:31
 */
@Data
public class WordTempRenderingDataDTO implements Serializable {

    private LinkedHashMap<String, Object> main;
    private List<HashMap<String, Object>> childOne;
    private List<HashMap<String, Object>> childTwo;
    private List<HashMap<String, Object>> childThree;
    private List<HashMap<String, Object>> childFour;

    private List<QsrRecord> qsRecord;

    /**
     * 巡检问题列表
     */
    private List<QuestionList> questionList;


    /**
     * 三联单多重日期循环
     */
    private List<TripleMultiLoop> tripleMultiLoop;
}
