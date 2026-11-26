package com.jpmsoft.projectProducer.service.acpm.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.mapper.acpm.AcpmProjectInvestmentApprovalMapper;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmProInvApprovalFormDto;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmProjectInvestmentApprovalDto;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmProjectInvestmentApproval;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmProjectInvestmentApprovalSelectVo;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmProjectInvestmentApprovalVo;
import com.jpmsoft.projectProducer.service.acpm.IAcpmProjectInvestmentApprovalService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 14:41
 */
@Service
public class AcpmProjectInvestmentApprovalImpl extends
        BaseFileImpl<AcpmProjectInvestmentApprovalMapper, AcpmProjectInvestmentApproval, AcpmProjectInvestmentApprovalDto> implements IAcpmProjectInvestmentApprovalService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "piaCode", method = "setPiaCode", codeRule = "ProjectInvestmentApproval", beanClass = AcpmProjectInvestmentApprovalDto.class)
    public BaseResponse createOrUpdate(AcpmProjectInvestmentApprovalDto request) {
        AcpmProjectInvestmentApproval entity = new AcpmProjectInvestmentApproval();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPiaBiddingDoc(), request.getPiaRequestDoc()), entity.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPiaBiddingDoc(), request.getPiaRequestDoc()),
                    Arrays.asList(request.getFileIds(), request.getPiaBiddingDocIds(), request.getPiaRequestDocIds()), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }


    @Override
    public BaseResponse getList(AcpmProInvApprovalFormDto request) {
        QueryMap<AcpmProInvApprovalFormDto> params = new QueryMap<>(request);
        setFilterMap(params, request);
        List<AcpmProjectInvestmentApprovalVo> datas = baseMapper.getList(params.getQuery());
        Integer total = baseMapper.getCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getById(String id) {
        AcpmProjectInvestmentApprovalVo data = baseMapper.getById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSelectList(AcpmProInvApprovalFormDto request) {
        QueryMap<AcpmProInvApprovalFormDto> params = new QueryMap<>(request);
        setFilterMap(params, request);
        List<AcpmProjectInvestmentApprovalSelectVo> datas = baseMapper.getSelectList(params.getQuery());
        Integer total = baseMapper.getCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }


    private void setFilterMap(QueryMap<AcpmProInvApprovalFormDto> params, AcpmProInvApprovalFormDto request) {
        params.put("piaCode").put("piaName").put("piaArea").put("piaType").put("piaDeveloper")
                .put("piaIsAgencyApproval").put("piaRequirementForMan").put("createUname").put("state")
                .put(request.getPiaTenderClosingTimes(), "piaTenderClosingTimeStart", "piaTenderClosingTimeEnd")
                .put(request.getPiaAnswerClosingTimes(), "piaAnswerClosingTimeStart", "piaAnswerClosingTimeEnd")
                .put(request.getPiaBidBondMoneys(), "piaBidBondMoneyStart", "piaBidBondMoneyEnd")
                .put(request.getPiaPerformanceBonds(), "piaPerformanceBondStart", "piaPerformanceBondEnd")
                .put(request.getPiaAgencyFees(), "piaAgencyFeeStart", "piaAgencyFeeEnd")
                .put(request.getPiaPeoples(), "piaPeopleStart", "piaPeopleEnd")
                .put(request.getPiaPerCapitas(), "piaPerCapitaStart", "piaPerCapitaEnd")
                .put(request.getPiaServiceFees(), "piaServiceFeeStart", "piaServiceFeeEnd")
                .put(request.getPiaStampDutys(), "piaStampDutyStart", "piaStampDutyEnd")
                .put(request.getPiaAchievements(), "piaAchievementStart", "piaAchievementEnd")
                .put(request.getPiaLaborCosts(), "piaLaborCostStart", "piaLaborCostEnd")
                .put(request.getPiaTotalCosts(), "piaTotalCostStart", "piaTotalCostEnd")
                .put(request.getPiaGrossProfits(), "piaGrossProfitStart", "piaGrossProfitEnd")
                .put(request.getPiaNetProfits(), "piaNetProfitStart", "piaNetProfitEnd")
                .put(request.getPiaGrossProfitMargins(), "piaGrossProfitMarginStart", "piaGrossProfitMarginEnd")
                .put(request.getPiaProvisionalInvestments(), "piaProvisionalInvStart", "piaProvisionalInvEnd")
                .put(request.getPiaGuaranteeAmtReqs(), "piaGuaranteeAmtReqStart", "piaGuaranteeAmtReqEnd");
    }
}
