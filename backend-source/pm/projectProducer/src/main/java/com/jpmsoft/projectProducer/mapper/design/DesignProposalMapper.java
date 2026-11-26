package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProposalModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignProposal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DesignProposalMapper extends BaseMapper<DesignProposal> {


    List<DesignProposalModel> getDesignProposalList(@Param("page") IPage<DesignProposalModel> page, @Param("params") Map<String, Object> params);


    DesignProposalModel getDesignProposalById(@Param("dpId") String dpId);
}
