package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertChangeModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertChange;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;

import java.util.List;

/**
 * 专家库变更记录
 */
public interface ISecExpertChangeService extends IService<SecExpertChange> {
    BaseResponse createOrUpdateSecExpertChange(SecExpertChangeModel request, String token);

    BaseResponse getSecExpertChangeList(SecExpertChangeModel request);

    BaseResponse getSecExpertChangeById(String id);

    BaseResponse syncSecExpertChangeData(String id);

    BaseResponse invalidProcessData(List<String> exIds);

    BaseResponse batchSyncChange(List<String> exIds);

    BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token);

    BaseResponse updateChangeStatus(String id);
}
