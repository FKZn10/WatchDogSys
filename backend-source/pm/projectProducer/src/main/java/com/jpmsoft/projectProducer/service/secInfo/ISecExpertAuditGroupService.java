package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditGroupDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditGroup;

/**
 * 集团专家评审
 */
public interface ISecExpertAuditGroupService extends IService<SecExpertAuditGroup> {
    BaseResponse createOrUpdateSecExpertAuditGroup(SecExpertAuditGroupDTO request, String token);

    BaseResponse createOrUpdateSecExpertAuditGroupDz(SecExpertAuditGroupDTO request, String token);

    BaseResponse getSecExpertAuditGroupList(SecExpertAuditGroupDTO request);

    BaseResponse getSecExpertAuditGroupById(String id);

    BaseResponse getSecExpertAuditGroupByYear(Integer year);

    BaseResponse syncSecExpert(String id);
}
