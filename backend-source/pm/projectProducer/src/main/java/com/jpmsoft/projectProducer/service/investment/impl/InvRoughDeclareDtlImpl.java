package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRoughDeclareDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.InvRoughDeclareDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRoughDeclareDtl;
import com.jpmsoft.projectProducer.service.investment.IInvRoughDeclareDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvRoughDeclareDtlImpl extends ServiceImpl<InvRoughDeclareDtlMapper, InvRoughDeclareDtl> implements IInvRoughDeclareDtlService {

    @Override
    public void createOrUpdateInvRoughDeclareDtl(List<InvRoughDeclareDtlModel> datas, String RdId) {
        deleteAllByRdId(RdId);
        if (datas != null && datas.size() > 0) {
            List<InvRoughDeclareDtl> entitys = new ArrayList<>();
            int i = 0;
            for (InvRoughDeclareDtlModel data : datas) {
                InvRoughDeclareDtl entity = new InvRoughDeclareDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setRddId(id);
                entity.setRdId(RdId);
                entity.setRddOrder(i += 1);
                entity.setRddParentId("0");
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), RdId, id);
                }
            }
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public List<InvRoughDeclareDtlModel> getInvRoughDeclareDtlModelByRdId(String RdId) {
        QueryWrapper<InvRoughDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Rd_Id", RdId);
        List<InvRoughDeclareDtl> pojos = baseMapper.selectList(wrapper);
        return getRoughDeclareDtlTree(pojos);
    }

    @Override
    public List<String> getAllChildIdByRddId(String RddId) {
        List<InvRoughDeclareDtl> alls = baseMapper.getAllByRddId(RddId);
        if (!CollectionUtils.isEmpty(alls)) {
            List<InvRoughDeclareDtl> treeAll = new ArrayList<>();
            InvRoughDeclareDtl the = alls.stream().filter(n -> n.getRddId().equals(RddId)).findFirst().get();
            treeAll.add(the);
            setTreeAll(treeAll, alls, the.getRddId());
            return treeAll.stream().map(InvRoughDeclareDtl::getRddId).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<String> getAllChildIdByPId(String pId) {
        return baseMapper.getAllByPId(pId);
    }


    @Override
    public Map<String, List<InvRoughDeclareDtlModel>> getInvRoughDeclareDtlModelByRdIds(List<String> pIds, List<String> RdIds) {
        QueryWrapper<InvRoughDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Rd_Id", RdIds.get(0));
        for (int i = 1; i < RdIds.size(); i++) {
            wrapper.or().eq("Rd_Id", RdIds.get(i));
        }
        List<InvRoughDeclareDtl> entitys = baseMapper.selectList(wrapper);
        Map<String, List<InvRoughDeclareDtlModel>> data = new HashMap<>();
        for (int i = 0; i < RdIds.size(); i++) {
            String RdId = RdIds.get(i);
            List<InvRoughDeclareDtl> pojos = entitys.stream().filter(n -> n.getRdId().equals(RdId)).collect(Collectors.toList());
            if (pojos != null && pojos.size() > 0) {
                List<InvRoughDeclareDtlModel> datas = new ArrayList<>();
                List<InvRoughDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getRddParentId().equals("0")).collect(Collectors.toList());
                getNodesDtls(datas, pojos, mainDtl);
                data.put(pIds.get(i), datas);
            }
        }
        return data;
    }


    @Override
    public void deleteAllByRdId(String RdId) {
        QueryWrapper<InvRoughDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Rd_Id", RdId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getInvRoughDeclareDtlTreeByPid(String pId) {
        List<InvRoughDeclareDtl> pojos = getInvRoughDeclareDtlByPid(pId);
        if (pojos != null && pojos.size() > 0) {
            List<InvRoughDeclareDtlModel> datas = new ArrayList<>();
            List<InvRoughDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getRddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<InvRoughDeclareDtlModel> getInvRoughDeclareDtlTreeListByPid(String pId) {
        List<InvRoughDeclareDtl> pojos = getInvRoughDeclareDtlByPid(pId);
        List<InvRoughDeclareDtlModel> datas = new ArrayList<>();
        if (pojos != null && pojos.size() > 0) {
            List<InvRoughDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getRddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return datas;
    }


    @Override
    public BaseResponse getInvRoughDeclareDtlListByPid(String pId) {
        List<InvRoughDeclareDtl> pojos = getInvRoughDeclareDtlByPid(pId);
        if (ValidateUtil.isNotEmpty(pojos)) {
            List<InvRoughDeclareDtlModel> datas = new ArrayList<>();
            datas = pojos.stream().map(n -> {
                InvRoughDeclareDtlModel model = new InvRoughDeclareDtlModel();
                BeanUtils.copyProperties(n, model);
                return model;
            }).collect(Collectors.toList());
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok();
    }

    private List<InvRoughDeclareDtlModel> getRoughDeclareDtlTree(List<InvRoughDeclareDtl> pojos) {
        if (pojos != null && pojos.size() > 0) {
            List<InvRoughDeclareDtlModel> datas = new ArrayList<>();
            List<InvRoughDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getRddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl);
            return datas;
        }
        return null;
    }


    private List<InvRoughDeclareDtl> getInvRoughDeclareDtlByPid(String pId) {
        QueryWrapper<InvRoughDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.apply(String.format(" Rd_Id in (select Rd_Id from Inv_Rough_Declare where Rd_State=7 and P_Id='%s')", pId));
        wrapper.orderByAsc("Rdd_Number");
        return baseMapper.selectList(wrapper);
    }


    private void getNodesDtls(List<InvRoughDeclareDtlModel> datas, List<InvRoughDeclareDtl> pojos, List<InvRoughDeclareDtl> mainDtl) {
        for (InvRoughDeclareDtl dtl : mainDtl) {
            InvRoughDeclareDtlModel data = new InvRoughDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvRoughDeclareDtl> childs = pojos.stream().filter(n -> n.getRddParentId().equals(data.getRddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<InvRoughDeclareDtlModel> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs);
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }

    private void addChildNodes(List<InvRoughDeclareDtl> entitys, List<InvRoughDeclareDtlModel> datas, String RdId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (InvRoughDeclareDtlModel data : datas) {
                InvRoughDeclareDtl entity = new InvRoughDeclareDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setRddId(id);
                entity.setRdId(RdId);
                entity.setRddParentId(parentId);
                entity.setRddOrder(i += 1);
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), RdId, id);
                }
            }
        }
    }

    private void setTreeAll(List<InvRoughDeclareDtl> treeAll, List<InvRoughDeclareDtl> alls, String parentId) {
        List<InvRoughDeclareDtl> childs = alls.stream().filter(n -> n.getRddParentId().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childs)) {
            for (InvRoughDeclareDtl child : childs) {
                treeAll.add(child);
                setTreeAll(treeAll, alls, child.getRddId());
            }
        }
    }

    @Override
    public BigDecimal getInvRoughDeclareDtlAmountByPid(String pId) {
        List<InvRoughDeclareDtl> pojos = getInvRoughDeclareDtlByPid(pId);
        BigDecimal amount= null;
        if (pojos != null && pojos.size() > 0) {
            List<InvRoughDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getRddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtlAmoint(amount, pojos, mainDtl);
            return amount;
        }
        return amount;
    }

    private void getNodesDtlAmoint(BigDecimal datas, List<InvRoughDeclareDtl> pojos, List<InvRoughDeclareDtl> mainDtl) {
        if(ValidateUtil.isNull(datas))
            datas=BigDecimal.ZERO;
        for (InvRoughDeclareDtl dtl : mainDtl) {
            InvRoughDeclareDtlModel data = new InvRoughDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvRoughDeclareDtl> childs = pojos.stream().filter(n -> n.getRddParentId().equals(data.getRddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                getNodesDtlAmoint(datas, pojos, childs);
            }
            else
            {
                datas=datas.add(dtl.getRddTotal());
            }
        }
    }
}
