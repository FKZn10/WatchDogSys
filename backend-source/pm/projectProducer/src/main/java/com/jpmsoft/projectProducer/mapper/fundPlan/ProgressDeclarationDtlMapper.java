package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressContractPlanDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclarationDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:59
 */
public interface ProgressDeclarationDtlMapper extends BaseMapper<ProgressDeclarationDtl> {

    List<ProgressDeclarationDtlDTO> selectByPdId(@Param("pdId") String pdId);

    List<ProgressContractPlanDTO> selectAllByContractList(@Param("conId") List<String> conId);

    List<ProgressDeclarationDtl> pddMoneyMountyAmt(@Param("ids") List<String> ids);

    List<ProgressDeclarationDtl> selectProgerssData(@Param("id") String id);

    List<ProgressDeclarationDtlDTO> getiProgressDeclarationId(@Param("id") String id);
}
