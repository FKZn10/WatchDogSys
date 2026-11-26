package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecEntrustDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecEntrust;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/31 11:01
 */
public interface ISecEntrustService extends IService<SecEntrust> {

    BaseResponse createOrUpdateSecEntrust(SecEntrustDto request);

    BaseResponse getSecEntrustById(String id);

    BaseResponse getSecEntrustList(SecEntrustDto request);
}
