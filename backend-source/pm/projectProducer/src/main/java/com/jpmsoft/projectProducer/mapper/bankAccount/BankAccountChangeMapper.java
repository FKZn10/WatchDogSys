package com.jpmsoft.projectProducer.mapper.bankAccount;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Account.BankAccountChangeResponse;
import com.jpmsoft.projectProducer.pojo.entity.bankAccount.BankAccountChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface BankAccountChangeMapper extends BaseMapper<BankAccountChange> {

    List<BankAccountChangeResponse> getBankAccountChangeList(@Param("page") IPage<BankAccountChangeResponse> page, @Param("params") Map<String, Object> params);


    BankAccountChangeResponse getBankAccountChangeById(@Param("bacId") String bacId);

}
