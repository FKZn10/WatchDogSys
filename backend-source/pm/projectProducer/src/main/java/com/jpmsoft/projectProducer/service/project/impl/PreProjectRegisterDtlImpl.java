package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsRegModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.PreProjectRegisterDtlMapper;
import com.jpmsoft.projectProducer.mapper.project.ProSourcesFundsRegMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegisterDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFundsReg;
import com.jpmsoft.projectProducer.service.project.IPreProjectRegisterDtlService;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsRegService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreProjectRegisterDtlImpl extends ServiceImpl<PreProjectRegisterDtlMapper, PreProjectRegisterDtl> implements IPreProjectRegisterDtlService {
    @Override
    public void createOrUpdatePreProjectRegisterDtl(List<PreProjectRegisterDtlModel> datas, String prId) {
        deletePreProjectRegisterDtlByPrId(prId);
        if (datas != null && datas.size() > 0) {
            List<PreProjectRegisterDtl> entitys = new ArrayList<>();
            int i = 0;
            for (PreProjectRegisterDtlModel data : datas) {
                PreProjectRegisterDtl entity = new PreProjectRegisterDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setPrdId(id);
                entity.setPrdFid(prId);
                entity.setPrdOrder(i += 1);
                entity.setPrdParenetId("0");
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), prId, id);
                }
            }
            if (entitys.size() > 0) {
             saveBatch(entitys);
            }
        }
    }

    private void addChildNodes(List<PreProjectRegisterDtl> entitys, List<PreProjectRegisterDtlModel> datas, String prId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (PreProjectRegisterDtlModel data : datas) {
                PreProjectRegisterDtl entity = new PreProjectRegisterDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setPrdId(id);
                entity.setPrdFid(prId);
                entity.setPrdParenetId(parentId);
                entity.setPrdOrder(i += 1);
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), prId, id);
                }
            }
        }
    }
    @Override
    public void deletePreProjectRegisterDtlByPrId(String prId) {
        QueryWrapper<PreProjectRegisterDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Prd_Fid", prId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<PreProjectRegisterDtlModel> getPreProjectRegisterDtlByPrId(String prId) {
        QueryWrapper<PreProjectRegisterDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Prd_Fid", prId);
        List<PreProjectRegisterDtl> pojos = baseMapper.selectList(wrapper);
        return getPreProjectRegisterDtlTree(pojos);
    }

    private List<PreProjectRegisterDtlModel> getPreProjectRegisterDtlTree(List<PreProjectRegisterDtl> pojos) {
        if (pojos != null && pojos.size() > 0) {
            List<PreProjectRegisterDtlModel> datas = new ArrayList<>();
            List<PreProjectRegisterDtl> mainDtl = pojos.stream().filter(n -> n.getPrdParenetId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return null;
    }
    private void getNodesDtls(List<PreProjectRegisterDtlModel> datas, List<PreProjectRegisterDtl> pojos, List<PreProjectRegisterDtl> mainDtl) {
        for (PreProjectRegisterDtl dtl : mainDtl) {
            PreProjectRegisterDtlModel data = new PreProjectRegisterDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<PreProjectRegisterDtl> childs = pojos.stream().filter(n -> n.getPrdParenetId().equals(data.getPrdId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<PreProjectRegisterDtlModel> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs);
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }
}
