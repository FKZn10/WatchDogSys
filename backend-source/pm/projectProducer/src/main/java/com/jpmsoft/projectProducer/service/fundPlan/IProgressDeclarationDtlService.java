package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressContractPlanDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclarationDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 20:00
 */
public interface IProgressDeclarationDtlService extends IService<ProgressDeclarationDtl> {


    BaseResponse createOrUpdate(List<ProgressDeclarationDtlDTO> data,String pdId);

    List<ProgressDeclarationDtlDTO> selectByPdId(String pdId);


    void deleteByPdId(String pdId);


    List<ProgressDeclarationDtl> selectAllByContract(String conId);

    List<ProgressContractPlanDTO> selectAllByContractList(List<String> conId);

    List<ProgressDeclarationDtl> pddMoneyMountyAmt(List<String> ids);

    List<ProgressDeclarationDtl> selectProgerssData(String id);

    List<ProgressDeclarationDtlDTO> getiProgressDeclarationId(String id);
}
