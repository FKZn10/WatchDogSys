package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressContractPlanDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclaration;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclarationDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:59
 */
public interface ProgressDeclarationMapper extends BaseMapper<ProgressDeclaration> {


    ProgressDeclarationDTO  getProgressDeclarationById(@Param("id") String id);


    List<ProgressDeclarationDTO> getProgressDeclarationList(@Param("page") IPage<ProgressDeclarationDTO> page,@Param("params") ProgressDeclarationDTO request);


    ProgressDeclarationDtl getPlanNodes(@Param("conId") String conId);

    List<ProgressDeclarationDtl> getPlanNodeLists(@Param("conId") String conId);

    Page<ProgressContractPlanDTO> getContractDeclarationList(@Param("page") Page<ProgressContractPlanDTO> page, @Param("dto") ProgressDeclarationMatchDTO dto);

    void deleteSysTaskEntityId(@Param("id") String id);

    void deleteSysTaskProcessinEntityId(@Param("id") String id);
}
