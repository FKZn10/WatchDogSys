package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecProcurementPlanAmountMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementPlanAmountDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementPlanAmount;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementPlanAmountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SecProcurementPlanAmountImpl extends ServiceImpl<SecProcurementPlanAmountMapper, SecProcurementPlanAmount> implements ISecProcurementPlanAmountService {

    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public void createOrUpdateSecProcurementPlanAmount(List<SecProcurementPlanAmountDto> planAmountList, String ppId) {
        //校验是否超概
        if(CollectionUtil.isNotEmpty(planAmountList)){
            Map<String, String> bddNameMap =new HashMap<>(16);
            Map<String, Double> bddAmountMap = new HashMap<>(16);
            //概算明细id集合
            List<String> bddIdList = new ArrayList<>();
            Double planAmount = 0D;
            for (SecProcurementPlanAmountDto secProcurementPlanAmountDto : planAmountList) {
                planAmount = 0D;
                bddNameMap.put(secProcurementPlanAmountDto.getBddId(), secProcurementPlanAmountDto.getBddName());
                if(bddAmountMap.containsKey(secProcurementPlanAmountDto.getBddId())){
                    planAmount = bddAmountMap.get(secProcurementPlanAmountDto.getBddId());
                }
                Float selectProportion = secProcurementPlanAmountDto.getSelectProportion();
                if(selectProportion==null){
                    selectProportion=0F;
                }
                planAmount += selectProportion.doubleValue();
                bddAmountMap.put(secProcurementPlanAmountDto.getBddId(), planAmount);
                bddNameMap.put(secProcurementPlanAmountDto.getBddId(), secProcurementPlanAmountDto.getBddName());
                bddIdList.add(secProcurementPlanAmountDto.getBddId());
            }



            String secAmountOverestimationCode = "secAmountOverestimation";
            String configByCode = sysConfigService.getConfigByCode(secAmountOverestimationCode);
            if(configByCode==null || !"1".equals(configByCode)){
                Map<String, Float> planAmountByBddIdMap = new HashMap<>(16);
                if(CollectionUtil.isNotEmpty(bddIdList)){
                    planAmountByBddIdMap = getPlanAmountMapByBddIds(bddIdList,ppId);
                }

                if(!bddAmountMap.isEmpty()){
                    for (Map.Entry<String, Double> item : bddAmountMap.entrySet()) {
                        double selectAmount = item.getValue().doubleValue();
                        if(selectAmount>100){
                            if(bddNameMap.containsKey(item.getKey())){
                                String s = bddNameMap.get(item.getKey());
                                throw new JPMException("科目【"+s+"】选择比例不能大于100%");
                            }else{
                                throw new JPMException("科目选择比例不能大于100%");
                            }
                        }
                        if(planAmountByBddIdMap.containsKey(item.getKey())){
                            Double amount = planAmountByBddIdMap.get(item.getKey()).doubleValue();
                            if(selectAmount>(100-amount)){
                                if(bddNameMap.containsKey(item.getKey())){
                                    String s = bddNameMap.get(item.getKey());
                                    throw new JPMException("科目【"+s+"】剩余比例"+(100-amount)+"%");
                                }else{
                                    throw new JPMException("科目剩余比例"+(100-amount)+"%");
                                }
                            }
                        }
                    }
                }
            }




        }
        deleteOldAll(ppId);
        if (CollectionUtil.isNotEmpty(planAmountList)) {
            List<SecProcurementPlanAmount> entitys = new ArrayList<>();
            for (SecProcurementPlanAmountDto secProcurementPlanAmountDto : planAmountList) {
                SecProcurementPlanAmount entity=new SecProcurementPlanAmount();
                entity.setPpaId(GenerateUUID.getUUID());
                entity.setPpId(ppId);
                entity.setBdId(secProcurementPlanAmountDto.getBdId());
                entity.setBddId(secProcurementPlanAmountDto.getBddId());
                entity.setBddAmount(secProcurementPlanAmountDto.getBddAmount());
                entity.setBddName(secProcurementPlanAmountDto.getBddName());
                entity.setSelectAmount(secProcurementPlanAmountDto.getSelectAmount());
                entity.setSelectProportion(secProcurementPlanAmountDto.getSelectProportion());
                entitys.add(entity);
            }

            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    private void deleteOldAll(String ppId) {
        QueryWrapper<SecProcurementPlanAmount> wrapper = new QueryWrapper<>();
        wrapper.eq("pp_id", ppId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecProcurementPlanAmountDto> getListByPpId(String ppId) {
        List<SecProcurementPlanAmountDto>  amountDtoList = baseMapper.getListByPpId(ppId);
        return amountDtoList;
    }

    @Override
    public List<SecProcurementPlanAmount> getPlanAmountByPid(String pId) {
        List<SecProcurementPlanAmount>  amountList = baseMapper.getPlanAmountByPid(pId);
        return amountList;
    }

    @Override
    public List<SecProcurementPlanAmount> getPlanAmountByBddIds(List<String> bddIdList) {
        List<SecProcurementPlanAmount>  amountList = baseMapper.getInvBudgetDeclareDtlByBddIdList(bddIdList);
        return amountList;
    }

    public Map<String, Float> getPlanAmountMapByBddIds(List<String> bddIdList,String ppId) {
        List<SecProcurementPlanAmount> planAmountList = getPlanAmountByBddIds(bddIdList);
        if(CollectionUtil.isNotEmpty(planAmountList) && StrUtil.isNotBlank(ppId)){
            //排除自己
            planAmountList=planAmountList.stream().filter(item->!item.getPpId().equals(ppId)).collect(Collectors.toList());
        }
        Map<String, Float> planAmountMap = getPlanAmountMap(planAmountList);
        return planAmountMap;
    }
    @Override
    public Map<String, Float> getPlanAmountMapByPid(String pId) {
        List<SecProcurementPlanAmount> planAmountByPid = getPlanAmountByPid(pId);
        Map<String, Float> planAmountMap = getPlanAmountMap(planAmountByPid);
        return planAmountMap;
    }

    @Override
    public List<SecProcurementPlanAmount> getInvBudgetDeclareDtlByPpid(String ppId) {
        List<SecProcurementPlanAmount> list =   baseMapper.getInvBudgetDeclareDtlByPpid(ppId);
        return list;
    }



    private Map<String, Float> getPlanAmountMap(List<SecProcurementPlanAmount>  amountList) {
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
