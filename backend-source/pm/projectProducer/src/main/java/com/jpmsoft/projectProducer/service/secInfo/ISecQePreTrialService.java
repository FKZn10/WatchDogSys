package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecQePreTrialDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQePreTrial;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:18
 */
public interface ISecQePreTrialService extends IService<SecQePreTrial> {

    BaseResponse createOrUpdateSecQePreTrial(SecQePreTrialDto request);

    BaseResponse getSecQePreTrialById(String id);
}
