package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentTemplateDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRelatedRelationshipsModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoBudgetModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.InvAdjustmentTemplateDtlMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvRelatedRelationshipsMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplateDtl;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRelatedRelationships;
import com.jpmsoft.projectProducer.pojo.vo.investment.BudgetConPayVo;
import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentTemplateDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvAdjustmentTemplateDtlImpl extends ServiceImpl<InvAdjustmentTemplateDtlMapper, InvAdjustmentTemplateDtl> implements IInvAdjustmentTemplateDtlService {


    @Override
    public void createOrUpdateInvAdjustmentTemplateDtl(List<InvAdjustmentTemplateDtlModel> datas, String atId) {
        deleteAllByAtId(atId);
        if (datas != null && datas.size() > 0) {
            List<InvAdjustmentTemplateDtl> entitys = new ArrayList<>();
            int i = 0;
            for (InvAdjustmentTemplateDtlModel data : datas) {
                InvAdjustmentTemplateDtl entity = new InvAdjustmentTemplateDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setAtdId(id);
                entity.setAtId(atId);
                entity.setAtOrder(i += 1);
                entity.setAtParentId("0");
                entitys.add(entity);
                if (data.getInvAdjustmentTemplateDtls() != null) {
                    addChildNodes(entitys, data.getInvAdjustmentTemplateDtls(), atId, id);
                }
            }
            if (entitys.size() > 0)
            {
                //baseMapper.addBatch(entitys);
                saveBatch(entitys);
            }

        }
    }

    @Override
    public List<String> getAllChildIdByAtdId(String atdId) {
        List<InvAdjustmentTemplateDtl> alls = baseMapper.getAllByAtdId(atdId);
        if (!CollectionUtils.isEmpty(alls)) {
            List<InvAdjustmentTemplateDtl> treeAll = new ArrayList<>();
            InvAdjustmentTemplateDtl the = alls.stream().filter(n -> n.getAtdId().equals(atdId)).findFirst().get();
            treeAll.add(the);
            setTreeAll(treeAll, alls, the.getAtdId());
            return treeAll.stream().map(InvAdjustmentTemplateDtl::getAtdId).collect(Collectors.toList());
        }
        return null;
    }


    @Override
    public void deleteAllByAtId(String atId) {
        QueryWrapper<InvAdjustmentTemplateDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("At_Id", atId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<InvAdjustmentTemplateDtlModel> getInvAdjustmentTemplateDtlModelByAtId(String atId) {
        QueryWrapper<InvAdjustmentTemplateDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("At_Id", atId);
        List<InvAdjustmentTemplateDtl> pojos = baseMapper.selectList(wrapper);
        return getAdjustmentTemplateDtlTree(pojos);
    }


    private List<InvAdjustmentTemplateDtlModel> getAdjustmentTemplateDtlTree(List<InvAdjustmentTemplateDtl> pojos) {
        if (pojos != null && pojos.size() > 0) {
            List<InvAdjustmentTemplateDtlModel> datas = new ArrayList<>();
            List<InvAdjustmentTemplateDtl> mainDtl = pojos.stream().filter(n -> n.getAtParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return null;
    }

    private void getNodesDtls(List<InvAdjustmentTemplateDtlModel> datas, List<InvAdjustmentTemplateDtl> pojos, List<InvAdjustmentTemplateDtl> mainDtl) {
        for (InvAdjustmentTemplateDtl dtl : mainDtl) {
            InvAdjustmentTemplateDtlModel data = new InvAdjustmentTemplateDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvAdjustmentTemplateDtl> childs = pojos.stream().filter(n -> n.getAtParentId().equals(data.getAtdId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<InvAdjustmentTemplateDtlModel> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs);
                data.setInvAdjustmentTemplateDtls(childNode);
            }
            datas.add(data);
        }
    }

    private void addChildNodes(List<InvAdjustmentTemplateDtl> entitys, List<InvAdjustmentTemplateDtlModel> datas, String atId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (InvAdjustmentTemplateDtlModel data : datas) {
                InvAdjustmentTemplateDtl entity = new InvAdjustmentTemplateDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setAtdId(id);
                entity.setAtId(atId);
                entity.setAtParentId(parentId);
                entity.setAtOrder(i += 1);
                entitys.add(entity);
                if (data.getInvAdjustmentTemplateDtls() != null) {
                    addChildNodes(entitys, data.getInvAdjustmentTemplateDtls(), atId, id);
                }
            }
        }
    }

    private void setTreeAll(List<InvAdjustmentTemplateDtl> treeAll, List<InvAdjustmentTemplateDtl> alls, String parentId) {
        List<InvAdjustmentTemplateDtl> childs = alls.stream().filter(n -> n.getAtParentId().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childs)) {
            for (InvAdjustmentTemplateDtl child : childs) {
                treeAll.add(child);
                setTreeAll(treeAll, alls, child.getAtdId());
            }
        }
    }
}
