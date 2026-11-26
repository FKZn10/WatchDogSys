package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertDeleteModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertDelete;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;

import java.util.List;

/**
 * 专家删除申请
 */
public interface ISecExpertDeleteService extends IService<SecExpertDelete> {
    BaseResponse createOrUpdateSecExpertDelete(SecExpertDeleteModel request, String token);

    BaseResponse getSecExpertDeleteList(SecExpertDeleteModel request);

    BaseResponse getSecExpertDeleteById(String id);

    BaseResponse syncSecExpertDeleteData(String id);

    BaseResponse invalidProcessData(List<String> exIds);

    BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token);

    BaseResponse updateChangeStatus(String id);
}
