package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecDocControlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocControl;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 11:17
 */
public interface ISecDocControlService extends IService<SecDocControl> {

    BaseResponse createOrUpdateSecDocControl(SecDocControlDto request);

    BaseResponse getSecDocControlList(SecDocControlDto request);

    BaseResponse getSecDocControlById(String id);
}
