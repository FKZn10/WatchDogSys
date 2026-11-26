package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.PreProApprovalDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProApprovalDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegisterDtl;
import com.jpmsoft.projectProducer.service.project.IPreProApprovalDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PreProApprovalDtlImpl extends ServiceImpl<PreProApprovalDtlMapper, PreProApprovalDtl> implements IPreProApprovalDtlService {
    @Override
    public void createOrUpdatePreProApprovalDtl(List<PreProApprovalDtlModel> datas, String prId) {
        deletePreProApprovalDtlByPrId(prId);
        if (datas != null && datas.size() > 0) {
            List<PreProApprovalDtl> entitys = new ArrayList<>();
            int i = 0;
            for (PreProApprovalDtlModel data : datas) {
                PreProApprovalDtl entity = new PreProApprovalDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setPadId(id);
                entity.setPadFid(prId);
                entity.setPadOrder(i += 1);
                entity.setPadParenetId("0");
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

    private void addChildNodes(List<PreProApprovalDtl> entitys, List<PreProApprovalDtlModel> datas, String prId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (PreProApprovalDtlModel data : datas) {
                PreProApprovalDtl entity = new PreProApprovalDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setPadId(id);
                entity.setPadFid(prId);
                entity.setPadParenetId(parentId);
                entity.setPadOrder(i += 1);
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), prId, id);
                }
            }
        }
    }


    @Override
    public void deletePreProApprovalDtlByPrId(String prId) {
        QueryWrapper<PreProApprovalDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Pad_Fid", prId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<PreProApprovalDtlModel> getPreProApprovalDtlByPrId(String prId) {
        QueryWrapper<PreProApprovalDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Pad_Fid", prId);
        List<PreProApprovalDtl> pojos = baseMapper.selectList(wrapper);
        return getPreProApprovalDtlTree(pojos);
    }

    @Override
    public Map<String, List<PreProApprovalDtlModel>> getPreProApprovalDtlModelByBdIds(List<String> pIds, List<String> padFids) {
        QueryWrapper<PreProApprovalDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("pad_fid", padFids.get(0));
        for (int i = 1; i < padFids.size(); i++) {
            wrapper.or().eq("pad_fid", padFids.get(i));
        }
        List<PreProApprovalDtl> entitys = baseMapper.selectList(wrapper);
        Map<String, List<PreProApprovalDtlModel>> data = new HashMap<>();
        for (int i = 0; i < padFids.size(); i++) {
            String padFid = padFids.get(i);
            List<PreProApprovalDtl> pojos = entitys.stream().filter(n -> n.getPadFid().equals(padFid)).collect(Collectors.toList());
            if (pojos != null && pojos.size() > 0) {
                List<PreProApprovalDtlModel> datas = new ArrayList<>();
                List<PreProApprovalDtl> mainDtl = pojos.stream().filter(n -> n.getPadParenetId().equals("0")).collect(Collectors.toList());
                getNodesDtls(datas, pojos, mainDtl);
                data.put(pIds.get(i), datas);
            }
        }
        return data;
    }

    private List<PreProApprovalDtlModel> getPreProApprovalDtlTree(List<PreProApprovalDtl> pojos) {
        if (pojos != null && pojos.size() > 0) {
            List<PreProApprovalDtlModel> datas = new ArrayList<>();
            List<PreProApprovalDtl> mainDtl = pojos.stream().filter(n -> n.getPadParenetId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return null;
    }
    private void getNodesDtls(List<PreProApprovalDtlModel> datas, List<PreProApprovalDtl> pojos, List<PreProApprovalDtl> mainDtl) {
        for (PreProApprovalDtl dtl : mainDtl) {
            PreProApprovalDtlModel data = new PreProApprovalDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<PreProApprovalDtl> childs = pojos.stream().filter(n -> n.getPadParenetId().equals(data.getPadId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<PreProApprovalDtlModel> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs);
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }




    @Override
    public List<PreProApprovalDtlModel> getPreProApprovalDtlByPId(String pId) {
        List<PreProApprovalDtl> pojos = getPreProApprovalDtlByPid(pId);
        List<PreProApprovalDtlModel> datas = new ArrayList<>();
        if (pojos != null && pojos.size() > 0) {
            List<PreProApprovalDtl> mainDtl = pojos.stream().filter(n -> n.getPadParenetId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return datas;
    }

    private List<PreProApprovalDtl> getPreProApprovalDtlByPid(String pId) {
        QueryWrapper<PreProApprovalDtl> wrapper = new QueryWrapper<>();
        wrapper.apply(String.format(" Pad_Fid in (select Pa_Id from Pre_Pro_Approval where Pa_State=7 and P_Id='%s')", pId));
        wrapper.orderByAsc("Pad_Number");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BigDecimal getPreProApprovalDtlAmountByPid(String pId) {
        List<PreProApprovalDtl> pojos = getPreProApprovalDtlByPid(pId);
        BigDecimal amount= null;
        if (pojos != null && pojos.size() > 0) {
            List<PreProApprovalDtl> mainDtl = pojos.stream().filter(n -> n.getPadParenetId().equals("0")).collect(Collectors.toList());
            getNodesDtlAmoint(amount, pojos, mainDtl);
            return amount;
        }
        return amount;
    }

    private void getNodesDtlAmoint(BigDecimal datas, List<PreProApprovalDtl> pojos, List<PreProApprovalDtl> mainDtl) {
        if(ValidateUtil.isNull(datas))
            datas=BigDecimal.ZERO;
        for (PreProApprovalDtl dtl : mainDtl) {
            InvBudgetDeclareDtlModel data = new InvBudgetDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<PreProApprovalDtl> childs = pojos.stream().filter(n -> n.getPadParenetId().equals(data.getBddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                getNodesDtlAmoint(datas, pojos, childs);
            }
            else
            {
                datas=datas.add(dtl.getPadTotal());
            }
        }
    }


}
