package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.ZNPJIndicatorInfoTypeConfig;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolutionDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PreProjectSolutionDetailsMapper extends BaseMapper<PreProjectSolutionDetails> {
    void insertBatch(@Param("list") List<PreProjectSolutionDetails> preProjectSolutionDetails);

    void updateStatusById(@Param("primaryId")String primaryId, @Param("status")int status);

    List<PreProjectSolutionDetails> selectBatchId(@Param("primaryId")String primaryId);

    ZNPJIndicatorInfoTypeConfig getZNPJIndicatorInfoTypeConfig(@Param("infoType")String infoType);
}
