package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementApplyDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementApply;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:19
 */
public interface ISecProcurementApplyService extends IService<SecProcurementApply> {

    BaseResponse createOrUpdateSecProcurementApply(SecProcurementApplyDto request);

    BaseResponse getSecProcurementApplyById(String id);
}
