package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.fund.BankAccountInfo;
import com.jpmsoft.projectProducer.pojo.request.fund.BankAccountInfoRequest;

public interface IBankAccountInfoService extends IService<BankAccountInfo>{
    BaseResponse getBankAccountInfoList(BankAccountInfoRequest request,String uid);

    BaseResponse getBankAccountInfoByID(BankAccountInfoRequest request,String uid);

}
