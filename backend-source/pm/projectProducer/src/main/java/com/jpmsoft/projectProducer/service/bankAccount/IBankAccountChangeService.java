package com.jpmsoft.projectProducer.service.bankAccount;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Account.BankAccountChangeRequest;
import com.jpmsoft.projectProducer.pojo.entity.bankAccount.BankAccountChange;


public interface IBankAccountChangeService extends IService<BankAccountChange> {

    BaseResponse createOrUpdateBankAccountChange(BankAccountChangeRequest request);

    BaseResponse getBankAccountChangeList(BankAccountChangeRequest request, String uId);

    BaseResponse getBankAccountChangeById(String bacId);

    BaseResponse getBankAccountChangeByConId(String conId);

}
