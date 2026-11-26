package com.jpmsoft.projectProducer.mapper.bankAccount;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Account.BankAccountResponse;
import com.jpmsoft.projectProducer.pojo.entity.bankAccount.BankAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface BankAccountMapper extends BaseMapper<BankAccount> {


    List<BankAccountResponse> getBankAccountList(@Param("page") IPage<BankAccountResponse> page, @Param("params") Map<String, Object> params);

    BankAccountResponse getBankAccountById(@Param("baId") String baId);
}
