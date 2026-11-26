package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamGroupModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryTeamGroup;

import java.util.List;

public interface ISecInquiryTeamGroupService extends IService<SecInquiryTeamGroup> {

    /**
     * 询价小组ID查看是否有询价成员
     *
     * @param qegId
     * @return
     */
    Boolean havingInquiryTeamGroupBySitId(String sitId);

    void createOrUpdateInquiryTeamGroup(List<SecInquiryTeamGroupModel> models, String sitId);

    List<SecInquiryTeamGroupModel> getSecInquiryTeamGroupBySitId(String sitId);

    List<SecInquiryTeamGroupModel> getSecInquiryTeamGroupBySecId(String secId);
}
