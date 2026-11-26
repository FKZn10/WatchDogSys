package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.investment.InvAdjustmentDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentDtl;
import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvAdjustmentDtlImpl extends ServiceImpl<InvAdjustmentDtlMapper, InvAdjustmentDtl> implements IInvAdjustmentDtlService {


    @Override
    public void createOrUpdateInvAdjustmentDtl(List<InvAdjustmentDtlModel> datas, String iaId) {
        deleteAllByIaId(iaId);
        if (datas != null && datas.size() > 0) {
            List<InvAdjustmentDtl> entitys = new ArrayList<>();
            int i = 0;
            for (InvAdjustmentDtlModel data : datas) {
                InvAdjustmentDtl entity = new InvAdjustmentDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setAdId(id);
                entity.setIaId(iaId);
                entity.setAdOrder(i += 1);
                entity.setAdParentId("0");
                entitys.add(entity);
                if (data.getInvAdjustmentDtls() != null) {
                    addChildNodes(entitys, data.getInvAdjustmentDtls(), iaId, id);
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
    public List<String> getAllChildIdByAdId(String atdId) {
        List<InvAdjustmentDtl> alls = baseMapper.getAllByAtdId(atdId);
        if (!CollectionUtils.isEmpty(alls)) {
            List<InvAdjustmentDtl> treeAll = new ArrayList<>();
            InvAdjustmentDtl the = alls.stream().filter(n -> n.getAdId().equals(atdId)).findFirst().get();
            treeAll.add(the);
            setTreeAll(treeAll, alls, the.getAdId());
            return treeAll.stream().map(InvAdjustmentDtl::getAdId).collect(Collectors.toList());
        }
        return null;
    }


    @Override
    public void deleteAllByIaId(String iaId) {
        QueryWrapper<InvAdjustmentDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ia_Id", iaId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<InvAdjustmentDtlModel> getInvAdjustmentDtlModelByIaId(String iaId) {
        QueryWrapper<InvAdjustmentDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ia_Id", iaId);
        List<InvAdjustmentDtl> pojos = baseMapper.selectList(wrapper);
        return getAdjustmentDtlTree(pojos);
    }


    private List<InvAdjustmentDtlModel> getAdjustmentDtlTree(List<InvAdjustmentDtl> pojos) {
        if (pojos != null && pojos.size() > 0) {
            List<InvAdjustmentDtlModel> datas = new ArrayList<>();
            List<InvAdjustmentDtl> mainDtl = pojos.stream().filter(n -> n.getAdParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return null;
    }

    private void getNodesDtls(List<InvAdjustmentDtlModel> datas, List<InvAdjustmentDtl> pojos, List<InvAdjustmentDtl> mainDtl) {
        for (InvAdjustmentDtl dtl : mainDtl) {
            InvAdjustmentDtlModel data = new InvAdjustmentDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvAdjustmentDtl> childs = pojos.stream().filter(n -> n.getAdParentId().equals(data.getAdId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<InvAdjustmentDtlModel> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs);
                data.setInvAdjustmentDtls(childNode);
            }
            datas.add(data);
        }
    }

    private void addChildNodes(List<InvAdjustmentDtl> entitys, List<InvAdjustmentDtlModel> datas, String iaId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (InvAdjustmentDtlModel data : datas) {
                InvAdjustmentDtl entity = new InvAdjustmentDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setAdId(id);
                entity.setIaId(iaId);
                entity.setAdParentId(parentId);
                entity.setAdOrder(i += 1);
                entitys.add(entity);
                if (data.getInvAdjustmentDtls() != null) {
                    addChildNodes(entitys, data.getInvAdjustmentDtls(), iaId, id);
                }
            }
        }
    }

    private void setTreeAll(List<InvAdjustmentDtl> treeAll, List<InvAdjustmentDtl> alls, String parentId) {
        List<InvAdjustmentDtl> childs = alls.stream().filter(n -> n.getAdParentId().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childs)) {
            for (InvAdjustmentDtl child : childs) {
                treeAll.add(child);
                setTreeAll(treeAll, alls, child.getAdId());
            }
        }
    }
}
