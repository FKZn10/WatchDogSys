package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecControlPriceRequest;
import com.jpmsoft.common.model.response.SecInfo.SecControlPriceResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecControlPrice;

import java.util.List;

public interface ISecControlPriceService extends IService<SecControlPrice> {

    BaseResponse createOrUpdateControlPrice(SecControlPriceRequest request);

    BaseResponse deleteControlPriceBycpId(String cpId);

    BaseResponse getControlPriceList(SecControlPriceRequest request);

    BaseResponse getControlPriceBycpId(String cpId);


    BaseResponse getControlPriceBysecId(String secId);

    List<SecControlPriceResponse> getControlPriceBypId(String pId);
}
