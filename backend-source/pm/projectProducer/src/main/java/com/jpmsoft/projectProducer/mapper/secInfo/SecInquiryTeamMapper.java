package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryTeam;
import org.apache.ibatis.annotations.Param;


public interface SecInquiryTeamMapper extends BaseMapper<SecInquiryTeam> {


    SecInquiryTeamModel getSecInquiryTeamBySitId(@Param("sitId") String sitId);
}
