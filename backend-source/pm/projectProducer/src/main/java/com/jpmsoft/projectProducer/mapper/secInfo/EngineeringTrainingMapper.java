package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.EngineeringTrainingModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.EngineeringTraining;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EngineeringTrainingMapper extends BaseMapper<EngineeringTraining> {
    List<EngineeringTrainingModel> getEngineeringTrainingList(@Param("params") Map<String, Object> query,
                                                              @Param("page") IPage<EngineeringTrainingModel> page);

    EngineeringTrainingModel getEngineeringTrainingById(@Param("id")String id);
}
