package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityPostPerson;

/**
 * 安全责任书岗位人员
 */
public interface ISafeResponsibilityPostPersonService extends IService<SafeResponsibilityPostPerson> {
    /**
     * 新增或修改安全责任书岗位人员
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdatePostPerson(SafeResponsibilityPostPersonDTO request);

    /**
     * 获取安全责任书岗位人员列表
     *
     * @param request
     * @return
     */
    BaseResponse getPostPersonList(SafeResponsibilityPostPersonDTO request);

    /**
     * 获取安全责任书岗位人员
     *
     * @param id
     * @return
     */
    BaseResponse getPostPersonById(String id);

    /**
     * 删除安全责任书岗位人员
     *
     * @param id
     * @return
     */
    BaseResponse deletePostPersonById(String id);

    /**
     * 同步上报人员
     *
     * @param rpId
     * @return
     */
    BaseResponse syncReportedPersonnel(String rpId);
}
