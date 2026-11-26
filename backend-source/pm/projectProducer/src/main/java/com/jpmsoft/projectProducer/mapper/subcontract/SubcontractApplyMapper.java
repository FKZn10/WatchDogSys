package com.jpmsoft.projectProducer.mapper.subcontract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Subcontract.SubcontractApplyResponse;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SubcontractApplyMapper extends BaseMapper<SubcontractApply> {

    List<SubcontractApplyResponse> getSubcontractApplyList(@Param("page") IPage<SubcontractApplyResponse> page, @Param("params") Map<String, Object> params);


    SubcontractApplyResponse getSubcontractApplyBySaId(@Param("saId") String saId);
}
