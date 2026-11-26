package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnnouncementBiddingModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecAnnouncementBidding;

public interface ISecAnnouncementBiddingService extends IService<SecAnnouncementBidding> {

    BaseResponse createOrUpdateSecAnnouncementBidding(SecAnnouncementBiddingModel request);

    BaseResponse getSecAnnouncementBiddingList(SecAnnouncementBiddingModel request);

    BaseResponse getSecAnnouncementBiddingById(String sabId);
}
