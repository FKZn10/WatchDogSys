package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDutyDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityPostDuty;

import java.util.Date;
import java.util.List;

/**
 * 安全责任岗位职责明细服务。
 */
public interface ISafeResponsibilityPostDutyService extends IService<SafeResponsibilityPostDuty> {
    /**
     * 新增或修改岗位职责。
     */
    BaseResponse createOrUpdateSafeResponsibilityPostDuty(SafeResponsibilityPostDutyDTO request, String uid, String uname);

    /**
     * 查询岗位职责。
     */
    BaseResponse getSafeResponsibilityPostDutyList(SafeResponsibilityPostDutyDTO request);

    /**
     * 删除岗位职责。
     */
    BaseResponse deleteSafeResponsibilityPostDuty(SafeResponsibilityPostDutyDTO request, String uid, String uname);

    /**
     * 查询岗位下全部职责。
     */
    List<SafeResponsibilityPostDutyDTO> listByPostId(String postId);

    /**
     * 整体同步岗位职责。
     */
    void replacePostDuties(String systemId, String postId, List<SafeResponsibilityPostDutyDTO> duties,
                           String uid, String uname, Date now);

    /**
     * 删除岗位下全部职责。
     */
    void deleteByPostId(String postId);
}
