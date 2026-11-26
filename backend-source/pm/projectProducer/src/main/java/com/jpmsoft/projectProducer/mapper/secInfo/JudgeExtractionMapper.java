package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionSearchDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.JudgeExtraction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【judge_extraction(评委抽取表)】的数据库操作Mapper
* @createDate 2025-04-24 18:08:19
* @Entity com.whjpsoft.pojo.JudgeExtraction
*/
public interface JudgeExtractionMapper extends BaseMapper<JudgeExtraction> {
    List<Object> getJudgeExtractionClassInfo(@Param("authSql") String authSql, @Param("value") String value);

    List<JudgeExtraction>  queryJudgeExtractionByPage(@Param("page") Page<JudgeExtraction> page,@Param("params")  JudgeExtractionSearchDTO params,
                                                      @Param("authSql")  String authSql);
}




