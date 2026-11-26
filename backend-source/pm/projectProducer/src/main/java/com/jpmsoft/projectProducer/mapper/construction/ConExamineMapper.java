package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConExamineModel;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConExamine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConExamineMapper extends BaseMapper<ConExamine> {

    List<ConExamineModel> getConExamineList(@Param("page") IPage<ConExamineModel> page, @Param("params") Map<String, Object> params);


    ConExamineModel getConExamineById(@Param("exId") String exId);
}
