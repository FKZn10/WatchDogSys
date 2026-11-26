package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.ReplyInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.other.ReplyInfo;

public interface IReplyInfoService extends IService<ReplyInfo> {

    BaseResponse createOrUpdateReplyInfo(ReplyInfoModel request);

    BaseResponse getReplyInfoList(ReplyInfoModel request,String uid);

    BaseResponse getReplyInfoById(String riId);
}
