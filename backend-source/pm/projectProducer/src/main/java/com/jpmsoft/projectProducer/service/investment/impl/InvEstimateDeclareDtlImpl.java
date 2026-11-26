package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvEstimateDeclareDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.InvEstimateDeclareDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvEstimateDeclareDtl;
import com.jpmsoft.projectProducer.service.investment.IInvEstimateDeclareDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvEstimateDeclareDtlImpl extends ServiceImpl<InvEstimateDeclareDtlMapper, InvEstimateDeclareDtl> implements IInvEstimateDeclareDtlService {

    @Override
    public void createOrUpdateInvEstimateDeclareDtl(List<InvEstimateDeclareDtlModel> datas, String EdId) {
        deleteAllByEdId(EdId);
        if (datas != null && datas.size() > 0) {
            List<InvEstimateDeclareDtl> entitys = new ArrayList<>();
            int i = 0;
            for (InvEstimateDeclareDtlModel data : datas) {
                InvEstimateDeclareDtl entity = new InvEstimateDeclareDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setEddId(id);
                entity.setEdId(EdId);
                entity.setEddOrder(i += 1);
                entity.setEddParentId("0");
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), EdId, id);
                }
            }
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public List<InvEstimateDeclareDtlModel> getInvEstimateDeclareDtlModelByEdId(String EdId) {
        QueryWrapper<InvEstimateDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ed_Id", EdId);
        List<InvEstimateDeclareDtl> pojos = baseMapper.selectList(wrapper);
        return getEstimateDeclareDtlTree(pojos);
    }

    @Override
    public List<String> getAllChildIdByEddId(String EddId) {
        List<InvEstimateDeclareDtl> alls = baseMapper.getAllByEddId(EddId);
        if (!CollectionUtils.isEmpty(alls)) {
            List<InvEstimateDeclareDtl> treeAll = new ArrayList<>();
            InvEstimateDeclareDtl the = alls.stream().filter(n -> n.getEddId().equals(EddId)).findFirst().get();
            treeAll.add(the);
            setTreeAll(treeAll, alls, the.getEddId());
            return treeAll.stream().map(InvEstimateDeclareDtl::getEddId).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<String> getAllChildIdByPId(String pId) {
        return baseMapper.getAllByPId(pId);
    }


    @Override
    public Map<String, List<InvEstimateDeclareDtlModel>> getInvEstimateDeclareDtlModelByEdIds(List<String> pIds, List<String> EdIds) {
        QueryWrapper<InvEstimateDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ed_Id", EdIds.get(0));
        for (int i = 1; i < EdIds.size(); i++) {
            wrapper.or().eq("Ed_Id", EdIds.get(i));
        }
        List<InvEstimateDeclareDtl> entitys = baseMapper.selectList(wrapper);
        Map<String, List<InvEstimateDeclareDtlModel>> data = new HashMap<>();
        for (int i = 0; i < EdIds.size(); i++) {
            String EdId = EdIds.get(i);
            List<InvEstimateDeclareDtl> pojos = entitys.stream().filter(n -> n.getEdId().equals(EdId)).collect(Collectors.toList());
            if (pojos != null && pojos.size() > 0) {
                List<InvEstimateDeclareDtlModel> datas = new ArrayList<>();
                List<InvEstimateDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getEddParentId().equals("0")).collect(Collectors.toList());
                getNodesDtls(datas, pojos, mainDtl);
                data.put(pIds.get(i), datas);
            }
        }
        return data;
    }


    @Override
    public void deleteAllByEdId(String EdId) {
        QueryWrapper<InvEstimateDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ed_Id", EdId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getInvEstimateDeclareDtlTreeByPid(String pId) {
        List<InvEstimateDeclareDtl> pojos = getInvEstimateDeclareDtlByPid(pId);
        if (pojos != null && pojos.size() > 0) {
            List<InvEstimateDeclareDtlModel> datas = new ArrayList<>();
            List<InvEstimateDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getEddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<InvEstimateDeclareDtlModel> getInvEstimateDeclareDtlTreeListByPid(String pId) {
        List<InvEstimateDeclareDtl> pojos = getInvEstimateDeclareDtlByPid(pId);
        List<InvEstimateDeclareDtlModel> datas = new ArrayList<>();
        if (pojos != null && pojos.size() > 0) {
            List<InvEstimateDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getEddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return datas;
    }


    @Override
    public BaseResponse getInvEstimateDeclareDtlListByPid(String pId) {
        List<InvEstimateDeclareDtl> pojos = getInvEstimateDeclareDtlByPid(pId);
        if (ValidateUtil.isNotEmpty(pojos)) {
            List<InvEstimateDeclareDtlModel> datas = new ArrayList<>();
            datas = pojos.stream().map(n -> {
                InvEstimateDeclareDtlModel model = new InvEstimateDeclareDtlModel();
                BeanUtils.copyProperties(n, model);
                return model;
            }).collect(Collectors.toList());
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok();
    }

    private List<InvEstimateDeclareDtlModel> getEstimateDeclareDtlTree(List<InvEstimateDeclareDtl> pojos) {
        if (pojos != null && pojos.size() > 0) {
            List<InvEstimateDeclareDtlModel> datas = new ArrayList<>();
            List<InvEstimateDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getEddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return null;
    }


    private List<InvEstimateDeclareDtl> getInvEstimateDeclareDtlByPid(String pId) {
        QueryWrapper<InvEstimateDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.apply(String.format(" Ed_Id in (select Ed_Id from Inv_Estimate_Declare where Ed_State=7 and P_Id='%s')", pId));
        wrapper.orderByAsc("Edd_Number");
        return baseMapper.selectList(wrapper);
    }


    private void getNodesDtls(List<InvEstimateDeclareDtlModel> datas, List<InvEstimateDeclareDtl> pojos, List<InvEstimateDeclareDtl> mainDtl) {
        for (InvEstimateDeclareDtl dtl : mainDtl) {
            InvEstimateDeclareDtlModel data = new InvEstimateDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvEstimateDeclareDtl> childs = pojos.stream().filter(n -> n.getEddParentId().equals(data.getEddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<InvEstimateDeclareDtlModel> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs);
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }

    private void addChildNodes(List<InvEstimateDeclareDtl> entitys, List<InvEstimateDeclareDtlModel> datas, String EdId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (InvEstimateDeclareDtlModel data : datas) {
                InvEstimateDeclareDtl entity = new InvEstimateDeclareDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setEddId(id);
                entity.setEdId(EdId);
                entity.setEddParentId(parentId);
                entity.setEddOrder(i += 1);
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), EdId, id);
                }
            }
        }
    }

    private void setTreeAll(List<InvEstimateDeclareDtl> treeAll, List<InvEstimateDeclareDtl> alls, String parentId) {
        List<InvEstimateDeclareDtl> childs = alls.stream().filter(n -> n.getEddParentId().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childs)) {
            for (InvEstimateDeclareDtl child : childs) {
                treeAll.add(child);
                setTreeAll(treeAll, alls, child.getEddId());
            }
        }
    }

    @Override
    public BigDecimal getInvEstimateDeclareDtlAmountByPid(String pId) {
        List<InvEstimateDeclareDtl> pojos = getInvEstimateDeclareDtlByPid(pId);
        BigDecimal amount= null;
        if (pojos != null && pojos.size() > 0) {
            List<InvEstimateDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getEddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtlAmoint(amount, pojos, mainDtl);
            return amount;
        }
        return amount;
    }

    private void getNodesDtlAmoint(BigDecimal datas, List<InvEstimateDeclareDtl> pojos, List<InvEstimateDeclareDtl> mainDtl) {
        if(ValidateUtil.isNull(datas))
            datas=BigDecimal.ZERO;
        for (InvEstimateDeclareDtl dtl : mainDtl) {
            InvEstimateDeclareDtlModel data = new InvEstimateDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvEstimateDeclareDtl> childs = pojos.stream().filter(n -> n.getEddParentId().equals(data.getEddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                getNodesDtlAmoint(datas, pojos, childs);
            }
            else
            {
                datas=datas.add(dtl.getEddTotal());
            }
        }
    }
}
