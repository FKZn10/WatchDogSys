package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnnouncementBiddingModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecAnnouncementBidding;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecAnnouncementBiddingMapper extends BaseMapper<SecAnnouncementBidding> {

    List<SecAnnouncementBiddingModel> getSecAnnouncementBiddingList(@Param("params") Map<String, Object> params);

    Integer getSecAnnouncementBiddingListCount(@Param("params") Map<String, Object> params);

    SecAnnouncementBiddingModel getSecAnnouncementBiddingById(@Param("sabId") String sabId);
}
