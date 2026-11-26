package com.jpmsoft.projectProducer.mapper.actualMeasurement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionResponse;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistribution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jjz
 */
public interface TaskDistributionMapper extends BaseMapper<TaskDistribution> {

    TaskDistributionResponse getTaskDistributionByTdId(@Param("tdId") String tdId);

    List<TaskDistributionResponse> getTaskDistributionList(@Param("page") IPage<TaskDistributionResponse> page,
                                                           @Param("params") Map<String, Object> params);

    List<TaskDistributionCheckModel> getTaskDistributionCheckByDiId(@Param("diId") String diId);

    TaskDistributionResponse getTaskDistributionByDiId(@Param("diId") String diId);

    TaskDistributionResponse getTaskDistributionByDmId(@Param("dmId") String dmId);

    List<TaskDistributionCheckModel> getTaskDistributionCheckByDmId(@Param("dmId") String dmId);
}
