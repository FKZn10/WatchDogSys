package com.jpmsoft.projectProducer.service.fund.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.fund.GroupFundPlanDetailSubjectDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.fund.GroupFundPlanDetailSubjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanDetailSubject;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanDetailSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资金计划明细-概算关联表 服务实现类
 */
@Service
public class GroupFundPlanDetailSubjectImpl extends ServiceImpl<GroupFundPlanDetailSubjectMapper, GroupFundPlanDetailSubject> implements IGroupFundPlanDetailSubjectService {
    /**
     * 批量创建资金计划明细-概算关联表
     *
     * @param subjectList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createSubjectBatch(List<GroupFundPlanDetailSubjectDTO> subjectList) {
        if (ValidateUtil.isEmpty(subjectList)) {
            return BaseResponse.ok();
        }
        this.remove(Wrappers.<GroupFundPlanDetailSubject>lambdaQuery().eq(GroupFundPlanDetailSubject::getGfpId, subjectList.get(0).getGfpId()));
        List<GroupFundPlanDetailSubject> entityList = subjectList.stream().map(dto -> {
            GroupFundPlanDetailSubject entity = new GroupFundPlanDetailSubject();
            entity.setId(GenerateUUID.getUUID());
            entity.setGfpId(dto.getGfpId());
            entity.setGfpdId(dto.getGfpdId());
            entity.setIbbId(dto.getIbbId());
            entity.setIebId(dto.getIebId());
            return entity;
        }).collect(Collectors.toList());
        this.saveBatch(entityList);
        return BaseResponse.ok();
    }

    /**
     * 根据资金计划明细ID获取关联的概算信息
     *
     * @param id
     * @return
     */
    @Override
    public List<GroupFundPlanDetailSubjectDTO> getByGfpId(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return Collections.emptyList();
        }
        return baseMapper.getByGfpId(id);
    }
}
