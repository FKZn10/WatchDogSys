package com.jpmsoft.projectProducer.service.design.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Design.DesignInitialBudgetDtlRequest;
import com.jpmsoft.common.model.response.Design.DesignInitialBudgetDtlResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.design.DesignInitialBudgetDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignInitialBudgetDtl;
import com.jpmsoft.projectProducer.service.design.IDesignInitialBudgetDtlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DesignInitialBudgetDtlImpl extends ServiceImpl<DesignInitialBudgetDtlMapper, DesignInitialBudgetDtl> implements IDesignInitialBudgetDtlService {


    @Override
    public void createOrUpdateDesignInitialBudgetDtl(List<DesignInitialBudgetDtlRequest> diBudgetDtls, String diId) {
        deleteAllByDiId(diId);
        if(CollectionUtil.isNotEmpty(diBudgetDtls)){
            List<DesignInitialBudgetDtl> list=new ArrayList<>();
            for (DesignInitialBudgetDtlRequest diBudgetDtl : diBudgetDtls) {
                DesignInitialBudgetDtl entity = new DesignInitialBudgetDtl();
                BeanUtil.copyProperties(diBudgetDtl, entity);
                entity.setDibdId(GenerateUUID.getUUID(entity.getDibdId()));
                entity.setDiId(diId);
                list.add(entity);
            }
            if(CollectionUtil.isNotEmpty(list)){
                this.saveOrUpdateBatch(list);
            }
        }
    }

    @Override
    public List<DesignInitialBudgetDtlResponse> getDesignInitialBudgetDtlListByDiId(String diId) {
        List<DesignInitialBudgetDtl> budgetDtlList = baseMapper.getBudgetDtlListByDiId(diId);
        List<DesignInitialBudgetDtlResponse> list=new ArrayList<>();
        if(CollectionUtil.isNotEmpty(budgetDtlList)){
            list=BeanUtils.copyList(budgetDtlList, DesignInitialBudgetDtlResponse.class);
        }
        return list;
    }

    @Override
    public void deleteDesignInitialBudgetDtlByDiId(String diId) {
        deleteAllByDiId(diId);
    }

    private void deleteAllByDiId(String diId) {
        QueryWrapper<DesignInitialBudgetDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("di_id", diId);
        baseMapper.delete(wrapper);
    }


}
