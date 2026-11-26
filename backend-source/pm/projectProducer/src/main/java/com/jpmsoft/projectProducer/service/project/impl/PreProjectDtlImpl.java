package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoBudgetModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.PreProjectDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProApprovalDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegisterDtl;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import com.jpmsoft.projectProducer.service.project.IPreProjectDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PreProjectDtlImpl extends ServiceImpl<PreProjectDtlMapper, PreProjectDtl> implements IPreProjectDtlService {

    @Resource
    private ISecInfoBudgetService secInfoBudgetService;
    @Override
    public void createOrUpdatePreProjectDtl(List<PreProjectDtlModel> datas, String prId) {
        deletePreProjectDtlByPrId(prId);
        if (datas != null && datas.size() > 0) {
            List<PreProjectDtl> entitys = new ArrayList<>();
            int i = 0;
            for (PreProjectDtlModel data : datas) {
                PreProjectDtl entity = new PreProjectDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setPpdId(id);
                entity.setPpdFid(prId);
                entity.setPpdOrder(i += 1);
                entity.setPpdParenetId("0");
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

    private void addChildNodes(List<PreProjectDtl> entitys, List<PreProjectDtlModel> datas, String prId, String parentId) {
        if (datas != null && datas.size() > 0) {
            int i = 0;
            for (PreProjectDtlModel data : datas) {
                PreProjectDtl entity = new PreProjectDtl();
                String id = GenerateUUID.getUUID();
                BeanUtils.copyProperties(data, entity);
                entity.setPpdId(id);
                entity.setPpdFid(prId);
                entity.setPpdParenetId(parentId);
                entity.setPpdOrder(i += 1);
                entitys.add(entity);
                if (data.getChildNodes() != null) {
                    addChildNodes(entitys, data.getChildNodes(), prId, id);
                }
            }
        }
    }


    @Override
    public void deletePreProjectDtlByPrId(String prId) {
        QueryWrapper<PreProjectDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ppd_Fid", prId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<PreProjectDtlModel> getPreProjectDtlByPrId(String prId,String pId) {
        QueryWrapper<PreProjectDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ppd_Fid", prId);
        List<PreProjectDtl> pojos = baseMapper.selectList(wrapper);
        return getPreProjectDtlTree(pojos,pId);
    }

    @Override
    public Map<String, List<PreProjectDtlModel>> getPreProjectDtlByIds(List<String> pId) {
        QueryWrapper<PreProjectDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ppd_Fid", pId.get(0));
        for (int i = 1; i < pId.size(); i++) {
            wrapper.or().eq("Ppd_Fid", pId.get(i));
        }
        List<PreProjectDtl> entitys = baseMapper.selectList(wrapper);
        Map<String, List<PreProjectDtlModel>> data = new HashMap<>();
        for (int i = 0; i < pId.size(); i++) {
            String bdId = pId.get(i);
            List<PreProjectDtl> pojos = entitys.stream().filter(n -> n.getPpdFid().equals(bdId)).collect(Collectors.toList());
            if (pojos != null && pojos.size() > 0) {
                List<PreProjectDtlModel> datas = new ArrayList<>();
                List<PreProjectDtl> mainDtl = pojos.stream().filter(n -> n.getPpdParenetId().equals("0")).collect(Collectors.toList());
                List<SecInfoBudgetDeclareDtl> secInfoList=new ArrayList<>();
                getNodesDtls(datas, pojos, mainDtl,secInfoList);
                data.put(pId.get(i), datas);
            }
        }
        return data;
    }

    private List<PreProjectDtlModel> getPreProjectDtlTree(List<PreProjectDtl> pojos,String pId) {
        if (pojos != null && pojos.size() > 0) {
            List<PreProjectDtlModel> datas = new ArrayList<>();
            List<PreProjectDtl> mainDtl = pojos.stream().filter(n -> n.getPpdParenetId().equals("0")).collect(Collectors.toList());
            List<SecInfoBudgetDeclareDtl> secInfoList=secInfoBudgetService.getSecInfoBudgetListByPId(pId);
            getNodesDtls(datas, pojos, mainDtl,secInfoList);
            return datas;
        }
        return null;
    }
    private void getNodesDtls(List<PreProjectDtlModel> datas, List<PreProjectDtl> pojos, List<PreProjectDtl> mainDtl,List<SecInfoBudgetDeclareDtl> secInfoList) {
        for (PreProjectDtl dtl : mainDtl) {
            PreProjectDtlModel data = new PreProjectDtlModel();
            BeanUtils.copyProperties(dtl, data);
            //判断是否有关联关系
            String id=data.getPpdId();
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
            List<PreProjectDtl> childs = pojos.stream().filter(n -> n.getPpdParenetId().equals(data.getPpdId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<PreProjectDtlModel> childNode = new ArrayList<>();
                getNodesDtls(childNode, pojos, childs,secInfoList);
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }

    @Override
    public List<PreProjectDtlModel> getPreProjectDtlByPId(String pId) {
        List<PreProjectDtl> pojos = getPreProjectDtlByPid(pId);
        List<PreProjectDtlModel> datas = new ArrayList<>();
        if (pojos != null && pojos.size() > 0) {
            List<PreProjectDtl> mainDtl = pojos.stream().filter(n -> n.getPpdParenetId().equals("0")).collect(Collectors.toList());
            List<SecInfoBudgetDeclareDtl> secInfoList=secInfoBudgetService.getSecInfoBudgetListByPId(pId);
            getNodesDtls(datas, pojos, mainDtl,secInfoList);
            return datas;
        }
        return datas;
    }

    private List<PreProjectDtl> getPreProjectDtlByPid(String pId) {
        QueryWrapper<PreProjectDtl> wrapper = new QueryWrapper<>();
        wrapper.apply(String.format(" Ppd_Fid in (select P_Id from Pre_Project where State=7 and P_Id='%s')", pId));
        wrapper.orderByAsc("Ppd_Number");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BigDecimal getPreProjectDtlAmountByPid(String pId) {
        List<PreProjectDtl> pojos = getPreProjectDtlByPid(pId);
        BigDecimal amount= null;
        if (pojos != null && pojos.size() > 0) {
            List<PreProjectDtl> mainDtl = pojos.stream().filter(n -> n.getPpdParenetId().equals("0")).collect(Collectors.toList());
            getNodesDtlAmoint(amount, pojos, mainDtl);
            return amount;
        }
        return amount;
    }

    private void getNodesDtlAmoint(BigDecimal datas, List<PreProjectDtl> pojos, List<PreProjectDtl> mainDtl) {
        if(ValidateUtil.isNull(datas))
            datas=BigDecimal.ZERO;
        for (PreProjectDtl dtl : mainDtl) {
            InvBudgetDeclareDtlModel data = new InvBudgetDeclareDtlModel();
            BeanUtils.copyProperties(dtl, data);
            List<PreProjectDtl> childs = pojos.stream().filter(n -> n.getPpdParenetId().equals(data.getBddId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                getNodesDtlAmoint(datas, pojos, childs);
            }
            else
            {
                datas=datas.add(dtl.getPpdTotal());
            }
        }
    }

}
