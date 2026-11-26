package com.jpmsoft.projectProducer.service.integrity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Integrity.FunctionalIntegrityWarningRequest;
import com.jpmsoft.projectProducer.pojo.entity.integrity.FunctionalIntegrityWarning;

/**
 * 功能预警流程记录
 */
public interface IFunctionalIntegrityWarningService extends IService<FunctionalIntegrityWarning> {
    /**
     * 高频风险环节看板
     *
     * @param request
     * @return
     */
    BaseResponse getIntegrityWarningLedger(FunctionalIntegrityWarningRequest request);

    /**
     * 高频风险岗位看板
     *
     * @param request
     * @return
     */
    BaseResponse getIntegrityWarningPositionLedger(FunctionalIntegrityWarningRequest request);
}
