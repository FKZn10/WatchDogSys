package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.fund.PayAmountInit;
import com.jpmsoft.projectProducer.pojo.request.fund.PayAmountInitRequest;

public interface IPayAmountInitService extends IService<PayAmountInit> {
    BaseResponse checkPayAmountInit(PayAmountInitRequest request,String uid);

    BaseResponse updatePayAmountInit(PayAmountInitRequest request,String uid);

    BaseResponse getPayAmountInitList(PayAmountInitRequest request,String uid);
}
