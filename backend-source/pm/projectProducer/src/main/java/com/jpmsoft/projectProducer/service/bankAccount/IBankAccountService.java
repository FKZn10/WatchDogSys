package com.jpmsoft.projectProducer.service.bankAccount;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Account.BankAccountRequest;
import com.jpmsoft.projectProducer.pojo.entity.bankAccount.BankAccount;


public interface IBankAccountService extends IService<BankAccount> {

    BaseResponse createOrUpdateBankAccount(BankAccountRequest request);

    BaseResponse getBankAccountList(BankAccountRequest request);


    /**
     * 鉴权列表查询
     *
     * @param request 入参请求体
     * @return 分页查询结果
     */
    BaseResponse getBankAccountAuthList(BankAccountRequest request, String uId);

    BaseResponse getBankAccountById(String baId);
}
