package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.FlowStatusEnum;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.mapper.project.*;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.*;
import com.jpmsoft.projectProducer.service.contract.IBGContactOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BGContactOrderServiceImpl extends ServiceImpl<BGContactOrderMapper, BGContactOrder> implements IBGContactOrderService {

    @Resource
    private BGConUnitOrderMapper BGConUnitOrderMapper;

    @Resource
    private BGDeclarationMapper BGDeclarationMapper;

    @Resource
    private BGVisaOrderMapper bgVisaOrderMapper;

    @Resource
    private ClearSectionMapper clearSectionMapper;


    @Override
    public BigDecimal getLJBGMoney(ProBGRequest request) {
        BigDecimal CUOMoney = baseMapper.getBGContactOrderMoney(request.getConId());
        return CUOMoney;
    }

    @Override
    public String SetContactOrderBL(ProBGRequest request) {
        String PayId = request.getPayId();
        if (PayId == null || PayId == "")
            return "OK";

        BGContactOrder model = baseMapper.selectById(request.getPayId());
        Integer State = model.getState();
        BigDecimal coMoney = model.getCoMoney();
        //判断流程是否审批完成
        if (State == 7) {
            if (request.getPayType().equals("指令单")) {
                BGConUnitOrder BGConUnitOrder_model = BGConUnitOrderMapper.selectById(request.getConId());

                BigDecimal cuoBL = BGConUnitOrder_model.getCuoBL();
                BigDecimal cuomoney = BGConUnitOrder_model.getCuoMoney();

                BigDecimal cuoNewbl = coMoney.divide(cuomoney, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100.0));
                cuoBL = cuoBL.subtract(cuoNewbl);
                BGConUnitOrder_model.setCuoBL(cuoBL);
                BGConUnitOrderMapper.updateById(BGConUnitOrder_model);
            } else if (request.getPayType().equals("变更单")) {
                BGDeclaration BGDeclaration_model = BGDeclarationMapper.selectById(request.getConId());

                BigDecimal dBL = BGDeclaration_model.getDBL();
                BigDecimal dmoney = BGDeclaration_model.getDMoney();

                BigDecimal dNewbl = coMoney.divide(dmoney, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100.0));
                dBL = dBL.subtract(dNewbl);
                BGDeclaration_model.setDBL(dBL);
                BGDeclarationMapper.updateById(BGDeclaration_model);
            }
        }
        return "OK";
    }


    @Override
    public Page<ProBGDTO> getBGContactOrderListByType(ProBGRequest request) {
        Page<ProBGDTO> PayRankVO = baseMapper.BGContactOrderListByType(new Page(request.getPage(), request.getSize()), request);
        return PayRankVO;
    }

    @Override
    public BigDecimal getSumOfContactItemRateByContactItemId(String contactItemId) {
        return baseMapper.sumOfRateByContactItemId(contactItemId);
    }

    @Override
    public Map<String, BigDecimal> getContractChangeAmountSum(List<String> contractIdList) {
        if (CollUtil.isEmpty(contractIdList)) {
            return Collections.emptyMap();
        }
        Map<String, BigDecimal> contractChangeAmountMap = new HashMap<>();
        for (String contractId : contractIdList) {
            contractChangeAmountMap.put(contractId, BigDecimal.ZERO);
        }
        Map<String, BigDecimal> itemToMoneyMap = new HashMap<>();
        Map<String, String> itemIdToContractIdMap = new HashMap<>();

        // 获取合同下的工程变更数据
        List<BGDeclaration> declarationList = BGDeclarationMapper.selectList(new LambdaQueryWrapper<BGDeclaration>()
                .in(BGDeclaration::getDCId, contractIdList)
                .eq(BGDeclaration::getState, FlowStatusEnum.Finish.getIndex()));
        for (BGDeclaration declaration : declarationList) {
            BigDecimal finalConfirmMoney = declaration.getFinalConfirmMoney();
            finalConfirmMoney = finalConfirmMoney == null ? BigDecimal.ZERO : finalConfirmMoney;
            itemToMoneyMap.put(declaration.getId(), finalConfirmMoney);
            itemIdToContractIdMap.put(declaration.getId(), declaration.getDCId());
        }

        // 获取合同下的建设单位指令单数据
        List<BGConUnitOrder> conUnitOrderList = BGConUnitOrderMapper.selectList(new LambdaQueryWrapper<BGConUnitOrder>()
                .eq(BGConUnitOrder::getState, FlowStatusEnum.Finish.getIndex())
                .in(BGConUnitOrder::getCuoConId, contractIdList));
        for (BGConUnitOrder conUnitOrder : conUnitOrderList) {
            BigDecimal finalConfirmMoney = conUnitOrder.getFinalConfirmMoney();
            finalConfirmMoney = finalConfirmMoney == null ? BigDecimal.ZERO : finalConfirmMoney;
            itemToMoneyMap.put(conUnitOrder.getId(), finalConfirmMoney);
            itemIdToContractIdMap.put(conUnitOrder.getId(), conUnitOrder.getCuoConId());
        }

        // 获取工程联系单数据
        List<BGContactOrder> contactOrderList = baseMapper.selectList(new LambdaQueryWrapper<BGContactOrder>()
                .eq(BGContactOrder::getState, FlowStatusEnum.Finish.getIndex())
                .in(BGContactOrder::getCoConId, contractIdList));
        for (BGContactOrder contactOrder : contactOrderList) {
            String coGLId = contactOrder.getCoGLId();
            if (coGLId == null) {
                BigDecimal finalConfirmMoney = contactOrder.getFinalConfirmMoney();
//                contractChangeAmountMap.put(contactOrder.getCoConId(), NumberUtil.add(changeAmountSum, finalConfirmMoney));
                itemToMoneyMap.put(contactOrder.getId(), finalConfirmMoney);
                itemIdToContractIdMap.put(contactOrder.getId(),contactOrder.getCoConId());
            } else {
                BigDecimal contactItemMoney = contactOrder.getCoGLMoney() == null ? BigDecimal.ZERO : contactOrder.getCoGLMoney();
                BigDecimal finalConfirmMoney = contactOrder.getFinalConfirmMoney() == null ? BigDecimal.ZERO : contactOrder.getFinalConfirmMoney();
                BigDecimal itemMoneySum = itemToMoneyMap.get(coGLId);
                itemMoneySum = itemMoneySum.subtract(contactItemMoney).add(finalConfirmMoney);
                itemToMoneyMap.put(coGLId, itemMoneySum);
            }
        }

        // 获取工程量签证数据
        List<BGVisaOrder> visaOrderList = bgVisaOrderMapper.selectList(new LambdaQueryWrapper<BGVisaOrder>()
                .eq(BGVisaOrder::getState, FlowStatusEnum.Finish.getIndex())
                .in(BGVisaOrder::getContractId, contractIdList));
        for (BGVisaOrder visaOrder : visaOrderList) {
            String itemId = visaOrder.getVoGLID();
            if (itemId == null) {
                String contractId = visaOrder.getContractId();
                BigDecimal changeAmountSum = contractChangeAmountMap.get(contractId);
                BigDecimal finalConfirmMoney = visaOrder.getFinalConfirmMoney();
                contractChangeAmountMap.put(contractId, changeAmountSum.add(finalConfirmMoney));
            } else {
                BigDecimal finalConfirmMoney = visaOrder.getFinalConfirmMoney();
                itemToMoneyMap.put(itemId, finalConfirmMoney);
            }
        }

        // 将事项金额累加到合同上
        for (Map.Entry<String, BigDecimal> entry : itemToMoneyMap.entrySet()) {
            String itemId = entry.getKey();
            BigDecimal itemMoney = entry.getValue();
            String contractId = itemIdToContractIdMap.get(itemId);
            if (contractId == null) {
                continue;
            }
            BigDecimal changeAmountSum = contractChangeAmountMap.get(contractId);
            changeAmountSum = changeAmountSum.add(itemMoney);
            contractChangeAmountMap.put(contractId, changeAmountSum);
        }

        // 施工图清标数据
        List<ClearSection> clearSectionList = clearSectionMapper.selectList(new LambdaQueryWrapper<ClearSection>()
                .eq(ClearSection::getState, FlowStatusEnum.Finish.getIndex())
                .in(ClearSection::getContractId, contractIdList));
        for (ClearSection clearSection : clearSectionList) {
            String contractId = clearSection.getContractId();
            BigDecimal changeAmountSum = contractChangeAmountMap.get(contractId);
            BigDecimal finalConfirmMoney = clearSection.getFinalConfirmMoney();
            contractChangeAmountMap.put(contractId, NumberUtil.add(changeAmountSum, finalConfirmMoney));
        }

        return contractChangeAmountMap;
    }


}