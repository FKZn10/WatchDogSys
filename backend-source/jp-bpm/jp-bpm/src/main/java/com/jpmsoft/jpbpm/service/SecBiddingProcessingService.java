package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.SecBiddingProcessing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SecBiddingProcessingService  extends IService<SecBiddingProcessing> {

    BaseResponse createSecBiddingProcessing(SecBiddingProcessing secBiddingProcessing);

    BaseResponse updateSecBiddingProcessing(String id, Integer States);

    BaseResponse  updateSecBiddingProcessing(SecBiddingProcessing obj);

    BaseResponse updateSecBiddingProcessing(String id, String secId, String tCode, Integer states, String customerId);

    List<SecBiddingProcessing> getSecBiddingProcessingBySecId(String secId);

    List<SecBiddingProcessing> getSecBiddingProcessingBySecIds(List<String> secIds);

    BaseResponse updateOtherSecBiddingProcessing(String id, String tCode, Integer states, String customerId,String year);
}
