package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamGroupModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryTeamGroup;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface SecInquiryTeamGroupMapper extends BaseMapper<SecInquiryTeamGroup> {

    List<SecInquiryTeamGroupModel> getSecInquiryTeamGroupBySitId(@RequestParam("sitId") String sitId);

    List<SecInquiryTeamGroupModel> getSecInquiryTeamGroupBySecId(@RequestParam("secId") String secId);
}
