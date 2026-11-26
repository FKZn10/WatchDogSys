package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaWorkorderRequest;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaWorkorder;

public interface IOaWorkorderService extends IService<OaWorkorder> {

    BaseResponse createOrUpdateOaWorkorder(OaWorkorderRequest request);

    BaseResponse getOaWorkorderList(OaWorkorderRequest request, String uId);

    BaseResponse getOaWorkorderById(String woId);

    BaseResponse updateReplyInfo(OaWorkorderRequest request);
}
