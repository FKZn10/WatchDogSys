package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCalibrationSupervisionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationSupervision;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:50
 */
public interface ISecCalibrationSupervisionService extends IService<SecCalibrationSupervision> {

    BaseResponse createOrUpdateSecCalibrationSupervision(SecCalibrationSupervisionDTO request);

    BaseResponse getSecCalibrationSupervisionById(String id);
}
