package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInvitationNegotiationDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInvitationNegotiation;

/**
 * 邀标控制价
 */
public interface ISecInvitationNegotiationService extends IService<SecInvitationNegotiation> {
    /**
     * 分页查询邀标控制价
     *
     * @param request
     * @return
     */
    BaseResponse getSecInvitationNegotiationList(SecInvitationNegotiationDTO request);

    /**
     * 获取邀标控制价多条件筛选信息
     *
     * @param request
     * @return
     */
    BaseResponse getSecInvitationNegotiationClassInfo(SecInvitationNegotiationDTO request);
}
