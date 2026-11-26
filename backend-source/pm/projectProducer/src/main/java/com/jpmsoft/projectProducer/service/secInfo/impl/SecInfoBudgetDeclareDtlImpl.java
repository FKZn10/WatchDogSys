package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecInfoBudgetDeclareDtlDto;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSourcesFundsModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoBudgetDeclareDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSourcesFunds;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetDeclareDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SecInfoBudgetDeclareDtlImpl extends ServiceImpl<SecInfoBudgetDeclareDtlMapper, SecInfoBudgetDeclareDtl> implements ISecInfoBudgetDeclareDtlService {


    @Override
    public List<SecInfoBudgetDeclareDtl> getDataList(String pId, String ppId) {
        List<SecInfoBudgetDeclareDtl> dataList = baseMapper.getDataList(pId, ppId);
        return dataList;
    }

    @Override
    public Map<String, Float> getAmountMap(String pId, String ppId) {
        List<SecInfoBudgetDeclareDtl> dataList = getDataList(pId, ppId);
        Map<String, Float> planAmountMap = getSecInfoAmountMap(dataList);
        return planAmountMap;
    }

    @Override
    public List<SecInfoBudgetDeclareDtlDto> getSecInfoBudgetDeclareDtlBySecIds(List<String> secIds) {
        return baseMapper.getSecInfoBudgetDeclareDtlBySecIds(secIds);
    }

    @Override
    public void createOrUpdateSecInfoBudgetDeclareDtl(List<SecInfoBudgetDeclareDtl> datas) {
        if (ValidateUtil.isNotEmpty(datas)) {
            saveBatch(datas);
        }
    }

    @Override
    public void delSecInfoBudgetDeclareDtlByBpId(String bpId) {
        if (ValidateUtil.isEmpty(bpId)) return;
        baseMapper.delSecInfoBudgetDeclareDtlByBpId(bpId);
    }

    @Override
    public List<SecInfoBudgetDeclareDtl> getSecInfoBudgetDeclareDtlByIds(List<String> sibdIdList) {
        return baseMapper.getSecInfoBudgetDeclareDtlByIds(sibdIdList);
    }

    private Map<String, Float> getSecInfoAmountMap(List<SecInfoBudgetDeclareDtl> amountList) {
        Map<String, Float> map = new HashMap<>();
        if(CollectionUtil.isNotEmpty(amountList)){
            //amountList按bddId分组，计算每组selectProportion之和
            amountList.forEach(amount -> {
                String bddId = amount.getBddId();
                Float selectProportion = amount.getSelectProportion();
                if(map.containsKey(bddId)){
                    Float oldSelectProportion = map.get(bddId);
                    map.put(bddId, oldSelectProportion + selectProportion);
                }else{
                    map.put(bddId, selectProportion);
                }
            });
        }
        return map;
    }


}
