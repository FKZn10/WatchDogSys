package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.fundPlan.ProgressDeclarationDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressContractPlanDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclarationDtl;
import com.jpmsoft.projectProducer.service.fundPlan.IProgressDeclarationDtlService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 20:00
 */
@Service
public class ProgressDeclarationDtlImpl extends ServiceImpl<ProgressDeclarationDtlMapper, ProgressDeclarationDtl>
implements IProgressDeclarationDtlService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdate(List<ProgressDeclarationDtlDTO> data, String pdId) {
        deleteByPdId(pdId);
        if (!CollectionUtils.isEmpty(data))
        {
            List<ProgressDeclarationDtl> entites=new ArrayList<>();
            List<String> relationIds=new ArrayList<>();
            List<String> fileIds=new ArrayList<>();
            for (ProgressDeclarationDtlDTO datum : data) {
                ProgressDeclarationDtl entity=new ProgressDeclarationDtl();
                BeanUtils.copyProperties(datum,entity);
                entity.setId(GenerateUUID.getUUID(datum.getId()));
                entity.setPdId(pdId);
                entites.add(entity);
                relationIds.add(datum.getPddFile());
                fileIds.add(datum.getPddFileIds());
            }
            this.saveBatch(entites);
            sysFileAnnexService.refreshRelationFile(relationIds,fileIds,pdId);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<ProgressDeclarationDtlDTO> selectByPdId(String pdId) {
        return baseMapper.selectByPdId(pdId);
    }

    @Override
    public void deleteByPdId(String pdId) {
        QueryWrapper<ProgressDeclarationDtl> wrapper=new QueryWrapper<>();
        wrapper.eq("pd_id",pdId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ProgressDeclarationDtl> selectAllByContract(String conId) {
        QueryWrapper<ProgressDeclarationDtl> wrapper=new QueryWrapper<>();
        wrapper.eq("con_id",conId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<ProgressContractPlanDTO> selectAllByContractList(List<String> conId) {
        return baseMapper.selectAllByContractList(conId);
    }

    @Override
    public List<ProgressDeclarationDtl> pddMoneyMountyAmt(List<String> ids) {
        return baseMapper.pddMoneyMountyAmt(ids);
    }

    @Override
    public List<ProgressDeclarationDtl> selectProgerssData(String id) {

        return baseMapper.selectProgerssData(id);
    }

    @Override
    public List<ProgressDeclarationDtlDTO> getiProgressDeclarationId(String id) {
        return baseMapper.getiProgressDeclarationId(id);
    }
}
