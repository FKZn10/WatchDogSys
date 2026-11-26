package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.request.evaluate.BaseEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;
import com.jpmsoft.projectProducer.service.contract.IContractEvaluateService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ContractEvaluateImpl  implements IContractEvaluateService {
    @Autowired
    private ConContractMapper conContractMapper;

    @Autowired
    private ConPayMapper conPayMapper;
    /*
    根据项目、合同获取合同中安全文明施工费（万元）【合同签订】
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IndicatorDataResponse getContractScMoney(BaseEvaluateRequest request) {
        ConContractResponse contract=conContractMapper.getContractByConId(request.getContractId());
        BigDecimal ScMoney=BigDecimal.ZERO;
        if(ValidateUtil.isNotNull(contract))
            ScMoney= MathUtil.decimalDivide(contract.getConScMoney(),new BigDecimal(10000), 6);
        if(ScMoney==null||ScMoney==BigDecimal.ZERO){
            return IndicatorDataResponse.fail("未查询到【安全文明措施】金额或者【安全文明措施】金额为0",0);
        }
        return IndicatorDataResponse.ok(ScMoney);
    }

    /*
    根据项目、合同获取累计支出安全文明施工费（万元）【合同支付】
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IndicatorDataResponse getConPayTotalScMoney(BaseEvaluateRequest request) {
        QueryWrapper<ConPay> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", request.getContractId());
        wrapper.eq("P_Id", request.getProjectId());
        wrapper.eq("Pay_Type","05");
        wrapper.eq("Pay_State", ApprovalStatusEnum.Already_Over.getIndex());
        BigDecimal TotalScMoney=conPayMapper.selectList(wrapper).stream()
                .map(ConPay::getPayApply).reduce(BigDecimal.ZERO,BigDecimal::add);
        if(TotalScMoney==null||TotalScMoney==BigDecimal.ZERO){
            return IndicatorDataResponse.fail("未查询到【安全文明施工费】合同支付信息",0);
        }
        return IndicatorDataResponse.ok(MathUtil.decimalDivide(TotalScMoney,new BigDecimal(10000), 6));
    }
}
