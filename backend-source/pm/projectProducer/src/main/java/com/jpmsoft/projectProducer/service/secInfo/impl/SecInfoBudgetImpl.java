package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetConSecModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoBudgetModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoBudgetMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudget;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecInfoBudgetImpl extends ServiceImpl<SecInfoBudgetMapper, SecInfoBudget> implements ISecInfoBudgetService {

    @Override
    public void createOrUpdateSecInfoBudget(List<SecInfoBudgetModel> datas, String secId) {
        deleteBySecId(secId);
        if (datas != null && datas.size() > 0) {
            List<SecInfoBudget> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecInfoBudget entity = new SecInfoBudget();
                BeanUtils.copyProperties(data, entity);
                entity.setSibId(GenerateUUID.getUUID());
                entity.setSecId(secId);
                entity.setSibInvestment(MathUtil.DecimalMultiplicationInt(entity.getSibInvestment(), 10000));
                entity.setSibTotalExpenses(MathUtil.DecimalMultiplicationInt(entity.getSibTotalExpenses(), 10000));
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public BaseResponse getSecInfoBudgetList(String sibCost, String teId) {
        if (ValidateUtil.isNotEmpty(teId))
            return BaseResponse.ok(baseMapper.getSecInfoBudgetListWithTeId(sibCost, teId));
        else
            return BaseResponse.ok(baseMapper.getSecInfoBudgetList(sibCost));
    }

    @Override
    public List<SecInfoBudgetModel> getSecInfoBudgetListBySecId(String secId) {
        return baseMapper.getSecInfoBudgetListBySecId(secId);
    }


    private void deleteBySecId(String secId) {
        QueryWrapper<SecInfoBudget> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<InvBudgetConSecModel> getSecInfoBudgetById(String sibCost) {
        return baseMapper.getSecInfoBudgetById(sibCost);
    }

    @Override
    public List<SecInfoBudgetDeclareDtl> getSecInfoBudgetListByPId(String pId) {
        return baseMapper.getSecInfoBudgetListByPId(pId);
    }

}
