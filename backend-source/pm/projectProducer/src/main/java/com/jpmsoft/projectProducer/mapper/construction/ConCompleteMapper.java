package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConCompleteModel;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConComplete;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConCompleteMapper extends BaseMapper<ConComplete> {


    List<ConCompleteModel> getConCompleteList(@Param("page") IPage<ConCompleteModel> page, @Param("params") Map<String, Object> params);

    ConCompleteModel getConCompleteById(@Param("coId") String coId);
}
