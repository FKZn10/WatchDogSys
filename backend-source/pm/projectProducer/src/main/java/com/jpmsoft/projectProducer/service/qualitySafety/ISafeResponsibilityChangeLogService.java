package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityChangeLogDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityChangeLog;

/**
 * 安全责任体系变更台账服务。
 */
public interface ISafeResponsibilityChangeLogService extends IService<SafeResponsibilityChangeLog> {
    /**
     * 查询变更台账。
     */
    BaseResponse getSafeResponsibilityChangeLogList(SafeResponsibilityChangeLogDTO request);

    /**
     * 保存变更台账。
     */
    void saveChangeLog(String systemId, String targetType, String targetId, String targetName,
                       String operationType, Object before, Object after, String uid, String uname,
                       String description, String batchNo);
}
