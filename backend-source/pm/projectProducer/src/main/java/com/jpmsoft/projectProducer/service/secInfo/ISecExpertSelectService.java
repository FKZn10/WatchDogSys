package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertSelect;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/8 16:18
 */
public interface ISecExpertSelectService extends IService<SecExpertSelect> {

    BaseResponse createOrUpdateSecExpertSelect(SecExpertSelectDto request);

    BaseResponse getSecExpertSelectList(SecExpertSelectDto request, String uId);

    BaseResponse getSecExpertSelectById(String id);

    BaseResponse checkExPeople(ExtractExpertDto request, String teId);

    void updateState(String id, Integer state);
}
