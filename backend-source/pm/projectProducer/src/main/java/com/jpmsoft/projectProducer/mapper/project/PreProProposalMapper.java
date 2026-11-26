package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProProposalModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProProposal;
import com.jpmsoft.projectProducer.pojo.vo.project.PreProProposalStatisticsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PreProProposalMapper extends BaseMapper<PreProProposal> {


    List<PreProProposalModel> getPreProProposalList(@Param("page") IPage<PreProProposalModel> page, @Param("params") Map<String, Object> params);

    PreProProposalStatisticsVo getPpInvestmentSum(@Param("params") Map<String, Object> params);

    PreProProposalModel getPreProProposalById(@Param("ppId") String ppId);
}
