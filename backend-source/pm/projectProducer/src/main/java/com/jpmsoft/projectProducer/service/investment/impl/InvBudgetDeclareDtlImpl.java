package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRelatedRelationshipsModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.InvBudgetDeclareDtlMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvRelatedRelationshipsMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRelatedRelationships;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.vo.investment.BudgetConPayVo;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvBudgetDeclareDtlImpl extends ServiceImpl<InvBudgetDeclareDtlMapper, InvBudgetDeclareDtl> implements IInvBudgetDeclareDtlService {

    @Resource
    private ISecInfoBudgetService secInfoBudgetService;

    @Resource
    private InvRelatedRelationshipsMapper invRelatedRelationshipsMapper;


    @Override
    public void createOrUpdateInvBudgetDeclareDtl(List<InvBudgetDeclareDtlModel> datas, String bdId) {
        deleteAllByBdId(bdId);
        if (datas != null && datas.size() > 0) {
            List<InvBudgetDeclareDtl> entitys = new ArrayList<>();
            int i = 0;
            for (InvBudgetDeclareDtlModel data : datas) {
                InvBudgetDeclareDtl entity = new InvBudgetDeclareDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setBddId(id);
                entity.setBdId(bdId);
                entity.setBddOrder(i += 1);
                entity.setBddParentId("0");
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), bdId, id);
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
    public void createOrUpdateInvRelatedRelationships(List<InvRelatedRelationshipsModel> datas, String bdId) {
        deleteAllByBdId2(bdId);
        QueryWrapper<InvRelatedRelationships> wrapper = new QueryWrapper<>();
        wrapper.eq("Main_Id", bdId);
        this.invRelatedRelationshipsMapper.delete(wrapper);
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (InvRelatedRelationshipsModel data : datas) {
                InvRelatedRelationships entity = new InvRelatedRelationships();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setId(id);
                entity.setMainId(bdId);
                this.invRelatedRelationshipsMapper.insert(entity);
            }
        }
    }


    @Override
    public List<InvBudgetDeclareDtlModel> getInvBudgetDeclareDtlModelByBdId(String bdId,String pId) {
        QueryWrapper<InvBudgetDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Bd_Id", bdId);
        List<InvBudgetDeclareDtl> pojos = baseMapper.selectList(wrapper);
        List<SecInfoBudgetDeclareDtl> secInfoList=secInfoBudgetService.getSecInfoBudgetListByPId(pId);
        return getBudgetDeclareDtlTree(pojos,secInfoList);
    }

    @Override
    public BaseResponse getInvBudgetConPayInfo(String pId) {
        List<BudgetConPayVo> datas = baseMapper.getInvBudgetConPayInfo(pId);
        List<BudgetConPayVo> sumDatas = new ArrayList<>();
        //region 计算每个节点汇总值
        for (BudgetConPayVo data : datas) {
            List<BudgetConPayVo> childs = new ArrayList<>();
            List<BudgetConPayVo> child = datas.stream().filter(n -> n.getBddParentId().equals(data.getBddId())).collect(Collectors.toList());
            getChildNodes(datas, childs, child);
            //取所有子集值汇总
            data.setConMoneyTotal(data.getConMoneyTotal().add(childs.stream().map(BudgetConPayVo::getConMoneyTotal).reduce(BigDecimal.ZERO, BigDecimal::add)));
            data.setConPayTotal(data.getConPayTotal().add(childs.stream().map(BudgetConPayVo::getConPayTotal).reduce(BigDecimal.ZERO, BigDecimal::add)));
            sumDatas.add(data);
        }
        //endregion
        Set<BudgetConPayVo> checkDatas = new HashSet<>();
        //region 校验子节点是否金额超标
        //合同支付金额是否超出
        for (BudgetConPayVo data : sumDatas) {
            if (data.getConPayTotal().compareTo(data.getConMoneyTotal()) == 1) {
                //合同支付金额超出合同支付金额汇总
                data.setPayOut(true);
                setParentPayOut(checkDatas, sumDatas, data.getBddParentId());
            }
            checkDatas.add(data);
        }
        sumDatas = new ArrayList<>(checkDatas);
        checkDatas = new HashSet<>();
        //校验合同金额
        for (BudgetConPayVo data : sumDatas) {
            if (data.getConMoneyTotal().compareTo(data.getBddTotal()) == 1) {
                //合同合同金额超出合同金额汇总
                data.setConOut(true);
                setParentConOut(checkDatas, sumDatas, data.getBddParentId());
            }
            checkDatas.add(data);
        }
        sumDatas = new ArrayList<>(checkDatas);
        //endregion
        //region 组成树形结构
        if (!CollectionUtils.isEmpty(sumDatas)) {
            List<BudgetConPayVo> vos = new ArrayList<>();
            List<BudgetConPayVo> mainDtl = sumDatas.stream().filter(n -> n.getBddParentId().equals("0")).collect(Collectors.toList());
            setVoChild(vos, sumDatas, mainDtl);
            return BaseResponse.ok(vos);
        }
        //endregion
        return BaseResponse.ok();
    }

    @Override
    public List<String> getAllChildIdByBddId(String bddId) {
        List<InvBudgetDeclareDtl> alls = baseMapper.getAllByBddId(bddId);
        if (!CollectionUtils.isEmpty(alls)) {
            List<InvBudgetDeclareDtl> treeAll = new ArrayList<>();
            InvBudgetDeclareDtl the = alls.stream().filter(n -> n.getBddId().equals(bddId)).findFirst().get();
            treeAll.add(the);
            setTreeAll(treeAll, alls, the.getBddId());
            return treeAll.stream().map(InvBudgetDeclareDtl::getBddId).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<String> getAllChildIdByPId(String pId) {
        return baseMapper.getAllByPId(pId);
    }


    @Override
    public Map<String, List<InvBudgetDeclareDtlModel>> getInvBudgetDeclareDtlModelByBdIds(List<String> pIds, List<String> bdIds) {
        QueryWrapper<InvBudgetDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Bd_Id", bdIds.get(0));
        for (int i = 1; i < bdIds.size(); i++) {
            wrapper.or().eq("Bd_Id", bdIds.get(i));
        }
        List<InvBudgetDeclareDtl> entitys = baseMapper.selectList(wrapper);
        Map<String, List<InvBudgetDeclareDtlModel>> data = new HashMap<>();
        for (int i = 0; i < bdIds.size(); i++) {
            String bdId = bdIds.get(i);
            List<InvBudgetDeclareDtl> pojos = entitys.stream().filter(n -> n.getBdId().equals(bdId)).collect(Collectors.toList());
            if (pojos != null && pojos.size() > 0) {
                List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
                List<InvBudgetDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getBddParentId().equals("0")).collect(Collectors.toList());
                List<SecInfoBudgetDeclareDtl> secInfoList=new ArrayList<>();
                getNodesDtls(datas, pojos, mainDtl,secInfoList);
                data.put(pIds.get(i), datas);
            }
        }
        return data;
    }


    @Override
    public void deleteAllByBdId(String bdId) {
        QueryWrapper<InvBudgetDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Bd_Id", bdId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteAllByBdId2(String bdId) {
        QueryWrapper<InvRelatedRelationships> wrapper = new QueryWrapper<>();
        wrapper.eq("Main_Id", bdId);
        this.invRelatedRelationshipsMapper.delete(wrapper);
    }


    @Override
    public BaseResponse getInvBudgetDeclareDtlTreeByPid(String pId) {
        List<InvBudgetDeclareDtl> pojos = getInvBudgetDeclareDtlByPid(pId);
        if (pojos != null && pojos.size() > 0) {
            List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
            List<InvBudgetDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getBddParentId().equals("0")).collect(Collectors.toList());
            List<SecInfoBudgetDeclareDtl> secInfoList=secInfoBudgetService.getSecInfoBudgetListByPId(pId);
            getNodesDtls(datas, pojos, mainDtl,secInfoList);
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<InvBudgetDeclareDtlModel> getInvBudgetDeclareDtlTreeListByPid(String pId,String Type) {
        List<InvBudgetDeclareDtl> pojos = getInvBudgetDeclareDtlByPidAndType(pId,Type);
        List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
        if (pojos != null && pojos.size() > 0) {
            List<InvBudgetDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getBddParentId().equals("0")).collect(Collectors.toList());
            List<SecInfoBudgetDeclareDtl> secInfoList=secInfoBudgetService.getSecInfoBudgetListByPId(pId);
            getNodesDtls(datas, pojos, mainDtl,secInfoList);
            return datas;
        }
        return datas;
    }

    @Override
    public List<InvBudgetDeclareDtlModel> getInvBudgetDeclareDtlDetailsListByPid(String pId,String Type) {
        List<InvBudgetDeclareDtl> pojos = getInvBudgetDeclareDtlByPidAndType(pId,Type);
        List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
        if (pojos != null && pojos.size() > 0) {
            List<InvBudgetDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getBddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls2(datas, pojos, mainDtl);
            return datas;
        }
        return datas;
    }
    private void getNodesDtls2(List<InvBudgetDeclareDtlModel> datas, List<InvBudgetDeclareDtl> pojos, List<InvBudgetDeclareDtl> mainDtl) {
        for (InvBudgetDeclareDtl dtl : mainDtl) {
            InvBudgetDeclareDtlModel data = new InvBudgetDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            datas.add(data);
            List<InvBudgetDeclareDtl> childs = pojos.stream().filter(n -> n.getBddParentId().equals(data.getBddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                getNodesDtls2(datas, pojos, childs);
            }
        }
    }




    @Override
    public BaseResponse getInvBudgetDeclareDtlListByPid(String pId) {
        List<InvBudgetDeclareDtl> pojos = getInvBudgetDeclareDtlByPid(pId);
        if (ValidateUtil.isNotEmpty(pojos)) {
            List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
            datas = pojos.stream().map(n -> {
                InvBudgetDeclareDtlModel model = new InvBudgetDeclareDtlModel();
                BeanUtils.copyProperties(n, model);
                return model;
            }).collect(Collectors.toList());
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok();
    }

    private List<InvBudgetDeclareDtlModel> getBudgetDeclareDtlTree(List<InvBudgetDeclareDtl> pojos,List<SecInfoBudgetDeclareDtl> secInfoList) {
        if (pojos != null && pojos.size() > 0) {
            List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
            List<InvBudgetDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getBddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtls(datas, pojos, mainDtl,secInfoList);
            return datas;
        }
        return Collections.emptyList();
    }


    public List<InvBudgetDeclareDtl> getInvBudgetDeclareDtlByPid(String pId) {
        QueryWrapper<InvBudgetDeclareDtl> wrapper = new QueryWrapper<>();
        wrapper.apply(String.format(" Bd_Id in (select Bd_Id from Inv_Budget_Declare where Bd_State=7 and P_Id='%s')", pId));
        wrapper.orderByAsc("Bdd_Number");
        return baseMapper.selectList(wrapper);
    }

    public List<InvBudgetDeclareDtl> getInvTypeBudgetDeclareDtlByPid(String pId) {

        return new ArrayList<>();
    }

    @Override
    public List<InvBudgetDeclareDtl> getInvTypeBudgetDeclareDtlByBdIdList(List<String> bdIdList) {
        return baseMapper.getInvTypeBudgetDeclareDtlByBdIdList(bdIdList);
    }

    public List<InvBudgetDeclareDtl> getInvBudgetDeclareDtlByPidAndType(String pId,String type) {
        QueryWrapper<InvBudgetDeclareDtl> wrapper = new QueryWrapper<>();
        String sql=" Bd_Id in (select Bd_Id from Inv_Budget_Declare where Bd_State=7 and P_Id='"+pId+"')";
        if(ValidateUtil.isNotNull(type))
            sql=" Bd_Id in (select Bd_Id from Inv_Budget_Declare where Bd_State=7 and Bd_Type='"+type+"' and P_Id='"+pId+"')";
        wrapper.apply(String.format(sql));
        wrapper.orderByAsc("Bdd_Order");
        return baseMapper.selectList(wrapper);
    }





    private void getNodesDtls(List<InvBudgetDeclareDtlModel> datas, List<InvBudgetDeclareDtl> pojos, List<InvBudgetDeclareDtl> mainDtl,List<SecInfoBudgetDeclareDtl> secInfoList) {
        for (InvBudgetDeclareDtl dtl : mainDtl) {
            InvBudgetDeclareDtlModel data = new InvBudgetDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            //判断是否有关联关系
            String id=data.getBddId();
            if(secInfoList.size()>0)
            {
                List<SecInfoBudgetDeclareDtl> secInfoResponse = secInfoList.stream().filter(n->n.getBddId().equals(id)).collect(Collectors.toList());
                if(secInfoResponse.size()>0)
                {
                    List<String> secIds=secInfoResponse.stream().map(SecInfoBudgetDeclareDtl::getSecId).collect(Collectors.toList());
                    if(secIds.size()>0)
                    {
                        data.setSfAssociation("1");
                        data.setAssociationSectionIds(secIds);
                    }
                    else
                        data.setSfAssociation("0");
                }
                else
                {
                    data.setSfAssociation("0");
                }
            }
            else
            {
                data.setSfAssociation("0");
            }
            List<InvBudgetDeclareDtl> childs = pojos.stream().filter(n -> n.getBddParentId().equals(data.getBddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<InvBudgetDeclareDtlModel> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs,secInfoList);
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }

    private void addChildNodes(List<InvBudgetDeclareDtl> entitys, List<InvBudgetDeclareDtlModel> datas, String bdId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (InvBudgetDeclareDtlModel data : datas) {
                InvBudgetDeclareDtl entity = new InvBudgetDeclareDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setBddId(id);
                entity.setBdId(bdId);
                entity.setBddParentId(parentId);
                entity.setBddOrder(i += 1);
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), bdId, id);
                }
            }
        }
    }


    private void setParentConOut(Set<BudgetConPayVo> checkDatas, List<BudgetConPayVo> sumDatas, String parentId) {
        if (!parentId.equals("0")) {
            List<BudgetConPayVo> parents = sumDatas.stream().filter(n -> n.getBddId().equals(parentId)).collect(Collectors.toList());
            for (BudgetConPayVo parent : parents) {
                parent.setConOut(true);
                checkDatas.add(parent);
                setParentConOut(checkDatas, sumDatas, parent.getBddParentId());
            }
        }
    }

    private void setParentPayOut(Set<BudgetConPayVo> checkDatas, List<BudgetConPayVo> sumDatas, String parentId) {
        if (!parentId.equals("0")) {
            List<BudgetConPayVo> parents = sumDatas.stream().filter(n -> n.getBddId().equals(parentId)).collect(Collectors.toList());
            for (BudgetConPayVo parent : parents) {
                parent.setPayOut(true);
                checkDatas.add(parent);
                setParentPayOut(checkDatas, sumDatas, parent.getBddParentId());
            }
        }
    }


    private void setVoChild(List<BudgetConPayVo> datas, List<BudgetConPayVo> pojos, List<BudgetConPayVo> mainDtl) {
        for (BudgetConPayVo dtl : mainDtl) {
            BudgetConPayVo data = new BudgetConPayVo();
            BeanUtils.copyProperties(dtl, data);
            List<BudgetConPayVo> childs = pojos.stream().filter(n -> n.getBddParentId().equals(data.getBddId())).collect(Collectors.toList());
            if (childs.size() > 0) {
                List<BudgetConPayVo> childNode = new ArrayList<>();
                setVoChild(childNode, pojos, childs);
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }

    private void getChildNodes(List<BudgetConPayVo> datas, List<BudgetConPayVo> pojos, List<BudgetConPayVo> child) {
        if (!CollectionUtils.isEmpty(child)) {
            pojos.addAll(child);
            for (BudgetConPayVo data : child) {
                List<BudgetConPayVo> childs = datas.stream().filter(n -> n.getBddParentId().equals(data.getBddId())).collect(Collectors.toList());
                getChildNodes(datas, pojos, childs);
            }
        }
    }

    private void setTreeAll(List<InvBudgetDeclareDtl> treeAll, List<InvBudgetDeclareDtl> alls, String parentId) {
        List<InvBudgetDeclareDtl> childs = alls.stream().filter(n -> n.getBddParentId().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childs)) {
            for (InvBudgetDeclareDtl child : childs) {
                treeAll.add(child);
                setTreeAll(treeAll, alls, child.getBddId());
            }
        }
    }

    @Override
    public BigDecimal getInvBudgetDeclareDtlAmountByPid(String pId) {
        List<InvBudgetDeclareDtl> pojos = getInvBudgetDeclareDtlByPid(pId);
        BigDecimal amount= null;
        if (pojos != null && pojos.size() > 0) {
            List<InvBudgetDeclareDtl> mainDtl = pojos.stream().filter(n -> n.getBddParentId().equals("0")).collect(Collectors.toList());
            getNodesDtlAmoint(amount, pojos, mainDtl);
            return amount;
        }
        return amount;
    }

    private void getNodesDtlAmoint(BigDecimal datas, List<InvBudgetDeclareDtl> pojos, List<InvBudgetDeclareDtl> mainDtl) {
        if(ValidateUtil.isNull(datas))
            datas=BigDecimal.ZERO;
        for (InvBudgetDeclareDtl dtl : mainDtl) {
            InvBudgetDeclareDtlModel data = new InvBudgetDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvBudgetDeclareDtl> childs = pojos.stream().filter(n -> n.getBddParentId().equals(data.getBddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                getNodesDtlAmoint(datas, pojos, childs);
            }
            else
            {
                datas=datas.add(dtl.getBddTotal());
            }
        }
    }
}
