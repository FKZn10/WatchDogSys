package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityCopyDTO;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilitySystemDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilitySystem;

/**
 * 安全责任体系服务。
 */
public interface ISafeResponsibilitySystemService extends IService<SafeResponsibilitySystem> {
    /**
     * 新增或修改责任体系。
     */
    BaseResponse createOrUpdateSafeResponsibilitySystem(SafeResponsibilitySystemDTO request, String uid, String uname);

    /**
     * 筛选责任体系列表。
     */
    BaseResponse getSafeResponsibilitySystemList(SafeResponsibilitySystemDTO request);

    /**
     * 筛选责任体系树。
     */
    BaseResponse getSafeResponsibilitySystemTree(SafeResponsibilitySystemDTO request);

    /**
     * 删除责任体系。
     */
    BaseResponse deleteSafeResponsibilitySystem(SafeResponsibilitySystemDTO request, String uid, String uname);

    /**
     * 复制责任体系。
     */
    BaseResponse copySafeResponsibilitySystem(SafeResponsibilityCopyDTO request, String uid, String uname);
}
