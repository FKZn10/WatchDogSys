package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFinalAccountsApplyDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/2/23 15:59
 */
public interface IInvFinalAccountsApplyDtlService extends IService<InvFinalAccountsApplyDtl> {


    void createOrUpdate(List<InvFinalAccountsApplyDtlDto> datas,String faId);

    void delteByFaId(String faId);

    List<InvFinalAccountsApplyDtlDto> getListByFaId(String faId);
}
