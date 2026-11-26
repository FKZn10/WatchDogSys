package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fund.FundNumberInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.FundNumberInfo;
import com.jpmsoft.projectProducer.pojo.request.fund.FundNumberInfoRequest;

public interface IFundNumberInfoService extends IService<FundNumberInfo> {
    BaseResponse createOrUpdateFundNumberInfo(FundNumberInfoDto request);

    BaseResponse getFundNumberInfoList(FundNumberInfoRequest request,String uid);

    BaseResponse getFundNumberInfoById(String id);
}
