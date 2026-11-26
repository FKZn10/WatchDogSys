package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInvitationSchemaDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInvitationSchema;

/**
 * 邀标工作方案
 */
public interface ISecInvitationSchemaService extends IService<SecInvitationSchema> {
    /**
     * 获取邀请招标列表
     *
     * @param request
     * @return
     */
    BaseResponse getSecInvitationSchemaList(SecInvitationSchemaDTO request);

    /**
     * 获取邀请招标多条件筛选
     *
     * @param request
     * @return
     */
    BaseResponse getSecInvitationSchemaClassInfo(SecInvitationSchemaDTO request);
}
