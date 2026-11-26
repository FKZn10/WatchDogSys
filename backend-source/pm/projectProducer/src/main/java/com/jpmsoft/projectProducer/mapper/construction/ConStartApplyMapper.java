package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConStartApplyModel;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConStartApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConStartApplyMapper extends BaseMapper<ConStartApply> {


    List<ConStartApplyModel> getConStartApplyList(@Param("page") IPage<ConStartApplyModel> page, @Param("params") Map<String, Object> params);

    ConStartApplyModel getConStartApplyById(@Param("saId") String saId);
}
