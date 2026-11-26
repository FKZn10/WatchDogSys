package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecFormDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecForm;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 17:01
 */
public interface ISecFormService extends IService<SecForm> {

    SecForm createOrUpdate(SecFormDTO request);
    BaseResponse createOrUpdateSecForm(SecFormDTO request);

    BaseResponse getSecFormList(SecFormDTO request);

    BaseResponse getSecFormLatest(String teId);

    BaseResponse getSecFormById(String id);

    BaseResponse deleteSecFormById(String id);

    String getSecFormLatestId(String teId);
}
