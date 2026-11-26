package com.jpmsoft.projectProducer.service.visaChange.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.Contract.ConEcResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConEcMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMapper;
import com.jpmsoft.projectProducer.mapper.visaChange.ConDccsCompanyMapper;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDccsCompanyDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEc;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDccsCompany;
import com.jpmsoft.projectProducer.pojo.request.evaluate.BaseEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;
import com.jpmsoft.projectProducer.service.contract.IContractEvaluateService;
import com.jpmsoft.projectProducer.service.visaChange.IConDccsCompanyService;
import com.jpmsoft.projectProducer.service.visaChange.IVisaChangeComplateRateService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class VisaChangeComplateRateImpl  implements IVisaChangeComplateRateService {
    @Autowired
    private ConEcMapper conEcMapper;

    @Autowired
    private ConContractMapper conContractMapper;

    /*
    根据项目、合同获取累计完成变更申报手续数（份）占累计发起变更数（份）比例
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IndicatorDataResponse getVisaChangeCompleteRate(BaseEvaluateRequest request) {
        QueryWrapper<ConEc> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", request.getContractId());
        wrapper.eq("P_Id", request.getProjectId());
        wrapper.eq("Ec_State", ApprovalStatusEnum.Already_Over.getIndex());
        List<ConEc> conEcList=conEcMapper.selectList(wrapper);
        if(CollectionUtil.isEmpty(conEcList)){
            return IndicatorDataResponse.fail("未查询到累计完成变更申报手续数（份）",0);
        }
        Integer AlreadyComplate =conEcList.size();
        QueryWrapper<ConEc> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("Con_Id", request.getContractId());
        wrapper2.eq("P_Id", request.getProjectId());
        wrapper2.ne("Ec_State", ApprovalStatusEnum.Invalid.getIndex());
        List<ConEc> conEcListAll=conEcMapper.selectList(wrapper2);
        if(CollectionUtil.isEmpty(conEcListAll)){
            return IndicatorDataResponse.fail("未查询到累计发起变更数（份）",0);
        }
        Integer InitiateNum=conEcListAll.size();
        BigDecimal Rate=BigDecimal.ZERO;
        if(!BigDecimal.ZERO.equals(InitiateNum))
            Rate= MathUtil.decimalDivide(new BigDecimal(AlreadyComplate),new BigDecimal(InitiateNum), 4).multiply(new BigDecimal(100));
        return IndicatorDataResponse.ok(Rate);
    }

     /*
    根据项目、合同获取未发起的潜在变更金额（万元）占合同额比例
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IndicatorDataResponse getVisaChangePotentialMoneyRate(BaseEvaluateRequest request) {
        QueryWrapper<ConEc> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", request.getContractId());
        wrapper.eq("P_Id", request.getProjectId());
        wrapper.eq("Ec_State", ApprovalStatusEnum.Not_Started.getIndex());
        List<ConEc> conEcListAll=conEcMapper.selectList(wrapper);
        if(CollectionUtil.isEmpty(conEcListAll)){
            return IndicatorDataResponse.fail("未查询未发起的潜在变更金额（万元）",0);
        }
        BigDecimal ecApplyMoney=conEcMapper.selectList(wrapper).stream().filter(k->ValidateUtil.isNotNull(k.getEcApplyMoney()))
                .map(ConEc::getEcApplyMoney).reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal Rate=BigDecimal.ZERO;
        BigDecimal conMoneyTotal=conContractMapper.getContractByConId(request.getContractId()).getConMoneyTotal();
        if(ValidateUtil.isNotNull(conMoneyTotal)&&!BigDecimal.ZERO.equals(conMoneyTotal))
        {
            Rate= MathUtil.decimalDivide(ecApplyMoney,conMoneyTotal, 4).multiply(new BigDecimal(100));
        }

        return IndicatorDataResponse.ok(Rate);
    }

    @Override
    public IndicatorDataResponse getVisaChange(BaseEvaluateRequest request) {
        QueryWrapper<ConEc> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("Con_Id", request.getContractId());
        wrapper2.eq("P_Id", request.getProjectId());
        wrapper2.ne("Ec_State", ApprovalStatusEnum.Invalid.getIndex());
        Integer InitiateNum=conEcMapper.selectCount(wrapper2).intValue();
        return IndicatorDataResponse.ok(InitiateNum);
    }

    @Override
    public IndicatorDataResponse getVisaChangeComplete(BaseEvaluateRequest request) {
        QueryWrapper<ConEc> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("Con_Id", request.getContractId());
        wrapper2.eq("P_Id", request.getProjectId());
        wrapper2.ne("Ec_State", ApprovalStatusEnum.Invalid.getIndex());
        Integer InitiateNum=conEcMapper.selectCount(wrapper2).intValue();
        return IndicatorDataResponse.ok(InitiateNum);
    }

    @Override
    public IndicatorDataResponse getVisaChangePotentialMoney(BaseEvaluateRequest request) {
        QueryWrapper<ConEc> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", request.getContractId());
        wrapper.eq("P_Id", request.getProjectId());
        wrapper.eq("Ec_State", ApprovalStatusEnum.Not_Started.getIndex());
        BigDecimal ecApplyMoney=conEcMapper.selectList(wrapper).stream().filter(k->ValidateUtil.isNotNull(k.getEcApplyMoney()))
                .map(ConEc::getEcApplyMoney).reduce(BigDecimal.ZERO,BigDecimal::add);
        return IndicatorDataResponse.ok(ecApplyMoney.doubleValue());
    }
}
