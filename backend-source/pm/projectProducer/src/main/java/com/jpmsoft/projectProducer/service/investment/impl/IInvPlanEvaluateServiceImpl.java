package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.util.StrUtil;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.mapper.investment.InvCompleteMonthMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvMonthlyScheduleMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvPlanYearMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvPlanYearYwMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlySchedule;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYear;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYearYw;
import com.jpmsoft.projectProducer.pojo.request.evaluate.investment.InvPlanEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.request.evaluate.qualitySafety.QualitySafetyEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvPlanAnnualMonthCompletionVo;
import com.jpmsoft.projectProducer.service.investment.IInvPlanEvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class IInvPlanEvaluateServiceImpl implements IInvPlanEvaluateService {
    @Autowired
    private InvPlanYearMapper invPlanYearMapper;
    @Autowired
    private InvMonthlyScheduleMapper invMonthlyScheduleMapper;

    @Autowired
    private InvPlanYearYwMapper invPlanYearYwMapper;
    private  IndicatorDataResponse getIndicatorDataResponse(InvPlanEvaluateRequest request) {
        if (request ==null){
            return IndicatorDataResponse.fail("缺失请求信息",0);
        }
        if(StrUtil.isEmpty(request.getProjectId())){
            return IndicatorDataResponse.fail("缺失项目信息",0);
        }
        if(StrUtil.isEmpty(request.getPeCompanyId())){
            return IndicatorDataResponse.fail("缺失单位信息",0);
        }
        if(StrUtil.isEmpty(request.getContractId())){
            return IndicatorDataResponse.fail("缺失合同信息",0);
        }
        if (request.getEvaluationYear()==null){
            return IndicatorDataResponse.fail("缺失年份时间信息",0);
        }
        if (request.getEvaluationMonth()==null){
            return IndicatorDataResponse.fail("缺失月份时间信息",0);
        }
        String projectId = request.getProjectId();
        String contractID = request.getContractId();
        Integer year = request.getEvaluationYear();
        InvPlanYear invPlanYear =invPlanYearMapper.selectInvPlanYear(projectId,contractID, year);
        if(invPlanYear==null){
            return IndicatorDataResponse.fail("未查询到年度投资信息",0);
        }
        List<InvPlanYearYw> invPlanYearYws = invPlanYearYwMapper.selectInvCompleteMonthList(projectId,contractID, year);
        if(CollectionUtils.isEmpty(invPlanYearYws)){
            return IndicatorDataResponse.fail("未查询到投资跟踪信息",0);
        }
        return null;
    }
    private InvPlanAnnualMonthCompletionVo getInvPlanAnnualMonthCompletionVo(InvPlanEvaluateRequest request){
        InvPlanAnnualMonthCompletionVo invPlanAnnualMonthCompletionVo = new InvPlanAnnualMonthCompletionVo();//年度投资任务（万元）
        invPlanAnnualMonthCompletionVo.setAnnualInvestment(0.0);
        String projectId = request.getProjectId();
        String contractID = request.getContractId();
        Integer year = request.getEvaluationYear();
        Date conEndDate = request.getEvaluateEndDate();
        Integer month = request.getEvaluationMonth();
        Calendar cal = Calendar.getInstance();
        cal.setTime(conEndDate);
        int conEndDateMonth = cal.get(Calendar.MONTH) + 1;
        log.info("conEndDateMonth：{}",conEndDateMonth);
        InvPlanYear invPlanYear =invPlanYearMapper.selectInvPlanYear(projectId,contractID, year);
        if (invPlanYear!=null){
            List<InvMonthlySchedule> invMonthlySchedules
                    = invMonthlyScheduleMapper.selectInvMonthlyScheduleByInvPlanYear(invPlanYear.getIpvId());
            List<BigDecimal> invMonthlyScheduleInvs = invMonthlySchedules.stream()
                    .map(InvMonthlySchedule::getImsCumulativePay).collect(Collectors.toList());
            Double annualInvestment=invMonthlyScheduleInvs.stream().mapToDouble(BigDecimal::doubleValue).sum();//invPlanYear.getIpvAnnualInv()!=null? invPlanYear.getIpvAnnualInv().doubleValue():
            annualInvestment=annualInvestment/10000;
            //年度投资任务（万元）
            invPlanAnnualMonthCompletionVo.setAnnualInvestment(annualInvestment);
            //今年累计完成投资额（万元）
            Double cumulativeInvestment = 0.0;
            List<InvPlanYearYw> invPlanYearYws = invPlanYearYwMapper.selectInvCompleteMonthList(projectId,contractID, year);
            if (CollectionUtils.isNotEmpty(invPlanYearYws)){
                List<BigDecimal> IcmMonthlyInvestment = invPlanYearYws.stream().map(InvPlanYearYw::getIpvTotalFunds).collect(Collectors.toList());
                cumulativeInvestment = IcmMonthlyInvestment.stream().mapToDouble(BigDecimal::doubleValue).sum();
                cumulativeInvestment=cumulativeInvestment/10000;
            }
            invPlanAnnualMonthCompletionVo.setCumulativeInvestment(cumulativeInvestment);

            Double annualInvestmentProportion;
            //今年累计完成投资额（万元）占年度投资任务（万元）比例
            if (annualInvestment!=0.0){
                Double annualPlan=annualInvestment*conEndDateMonth/12;
                log.info("annualPlan：{}",annualPlan);
                annualInvestmentProportion=cumulativeInvestment/annualPlan;
                if (annualInvestmentProportion>1.0){
                    annualInvestmentProportion=1.0;
                }
            }else {
                annualInvestmentProportion=0.0;
            }
            invPlanAnnualMonthCompletionVo.setAnnualInvestmentProportion(annualInvestmentProportion);
            //当月计划投资额（万元）
            Double monthlyPlanInvestment = 0.0;
            InvMonthlySchedule invMonthlySchedule =
                    invMonthlySchedules.stream().filter(n-> month.equals(n.getImsMonth())).findFirst().orElse(null);
            if (invMonthlySchedule!=null){
                monthlyPlanInvestment = invMonthlySchedule.getImsCumulativePay()!=null? invMonthlySchedule.getImsCumulativePay().doubleValue():0.0;
                monthlyPlanInvestment =monthlyPlanInvestment/10000;
            }
            invPlanAnnualMonthCompletionVo.setMonthlyPlanInvestment(monthlyPlanInvestment);
            //当月完成投资额（万元）
            Double monthlyInvestment = 0.0;
            if (CollectionUtils.isNotEmpty(invPlanYearYws)){
                log.info("invCompleteMonthList：{}",JacksonUtils.toJSONString(invPlanYearYws));
                List<BigDecimal> IcmMonthlyInvestment = invPlanYearYws.stream().filter(invPlanYearYw -> month.toString().equals(invPlanYearYw.getIpvConstructionStage())).map(InvPlanYearYw::getIpvTotalFunds).collect(Collectors.toList());
                monthlyInvestment = IcmMonthlyInvestment.stream().mapToDouble(BigDecimal::doubleValue).sum();
                monthlyInvestment=monthlyInvestment/10000;
            }
            invPlanAnnualMonthCompletionVo.setMonthlyInvestment(monthlyInvestment);
            Double monthlyInvestmentProportion;
            //当月完成投资额（万元）占当月计划投资额（万元）比例
            if (monthlyPlanInvestment!=0.0){
                monthlyInvestmentProportion=monthlyInvestment/monthlyPlanInvestment;
                if (monthlyInvestmentProportion>1.0){
                    monthlyInvestmentProportion=1.0;
                }
            }else {
                monthlyInvestmentProportion=0.0;
            }
            invPlanAnnualMonthCompletionVo.setMonthlyInvestmentProportion(monthlyInvestmentProportion);
            log.info("invPlanAnnualMonthCompletionVo：{}",JacksonUtils.toJSONString(invPlanAnnualMonthCompletionVo));
        }else {
            Double annualInvestment=  0.0;//invPlanYear.getIpvAnnualInv()!=null? invPlanYear.getIpvAnnualInv().doubleValue():
            //年度投资任务（万元）
            invPlanAnnualMonthCompletionVo.setAnnualInvestment(annualInvestment);
            //今年累计完成投资额（万元）
            Double cumulativeInvestment = 0.0;

            invPlanAnnualMonthCompletionVo.setCumulativeInvestment(cumulativeInvestment);

            Double annualInvestmentProportion=0.0;

            invPlanAnnualMonthCompletionVo.setAnnualInvestmentProportion(annualInvestmentProportion);
            //当月计划投资额（万元）
            Double monthlyPlanInvestment = 0.0;
            invPlanAnnualMonthCompletionVo.setMonthlyPlanInvestment(monthlyPlanInvestment);
            //当月完成投资额（万元）
            Double monthlyInvestment = 0.0;

            invPlanAnnualMonthCompletionVo.setMonthlyInvestment(monthlyInvestment);
            Double monthlyInvestmentProportion=0.0;

            invPlanAnnualMonthCompletionVo.setMonthlyInvestmentProportion(monthlyInvestmentProportion);
        }

        return invPlanAnnualMonthCompletionVo;
    }

    @Override
    /**
     * 根据项目、单位和评价年份获取年度投资完成情况
     * 今年累计完成投资额（万元）占年度投资任务（万元）比例
     * 当月完成投资额（万元）占当月计划投资额（万元）比例
     * 年度投资任务（万元）
     * 今年累计完成投资额（万元）
     * 当月计划投资额（万元）
     * 当月完成投资额（万元）
     *
     */
    public IndicatorDataResponse getInvPlanAnnualCompletion(InvPlanEvaluateRequest request) {
        log.info("getInvPlanAnnualMonthCompletion");
        log.info("request：{}",JacksonUtils.toJSONString(request));
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        InvPlanAnnualMonthCompletionVo invoiceMonthCompletionVo = getInvPlanAnnualMonthCompletionVo(request);
        Double annualInvestmentProportion = invoiceMonthCompletionVo.getAnnualInvestmentProportion();
        log.info("annualInvestmentProportion：{}",annualInvestmentProportion);
        return IndicatorDataResponse.ok(annualInvestmentProportion);
    }

    @Override
    public IndicatorDataResponse getInvPlanMonthCompletion(InvPlanEvaluateRequest request) {
        log.info("getInvPlanMonthCompletion");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        InvPlanAnnualMonthCompletionVo invoiceMonthCompletionVo = getInvPlanAnnualMonthCompletionVo(request);
        Double monthlyInvestmentProportion = invoiceMonthCompletionVo.getMonthlyInvestmentProportion();
        log.info("monthlyInvestmentProportion：{}",monthlyInvestmentProportion);
        return IndicatorDataResponse.ok(monthlyInvestmentProportion);
    }

    @Override
    public IndicatorDataResponse getInvPlanAnnualInvestment(InvPlanEvaluateRequest request) {
        log.info("getInvPlanAnnualInvestment");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        InvPlanAnnualMonthCompletionVo invoiceMonthCompletionVo = getInvPlanAnnualMonthCompletionVo(request);
        Double annualInvestment = invoiceMonthCompletionVo.getAnnualInvestment();
        log.info("annualInvestment:{}",annualInvestment);
        return IndicatorDataResponse.ok(annualInvestment);
    }

    @Override
    public IndicatorDataResponse getInvPlanMonthlyInvestment(InvPlanEvaluateRequest request) {
        log.info("getInvPlanMonthlyInvestment");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        InvPlanAnnualMonthCompletionVo invoiceMonthCompletionVo = getInvPlanAnnualMonthCompletionVo(request);
        Double monthlyInvestment = invoiceMonthCompletionVo.getMonthlyInvestment();
        log.info("monthlyInvestment:{}",monthlyInvestment);
        return IndicatorDataResponse.ok(monthlyInvestment);
    }

    @Override
    public IndicatorDataResponse getInvPlanMonthlyPlanInvestment(InvPlanEvaluateRequest request) {
        log.info("getInvPlanMonthlyPlanInvestment");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        InvPlanAnnualMonthCompletionVo invoiceMonthCompletionVo = getInvPlanAnnualMonthCompletionVo(request);
        Double monthlyPlanInvestment = invoiceMonthCompletionVo.getMonthlyPlanInvestment();
        log.info("monthlyPlanInvestment:{}",monthlyPlanInvestment);
        return IndicatorDataResponse.ok(monthlyPlanInvestment);
    }

    @Override
    public IndicatorDataResponse getInvPlanCumulativeInvestment(InvPlanEvaluateRequest request) {
        log.info("getInvPlanCumulativeInvestment");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        InvPlanAnnualMonthCompletionVo invoiceMonthCompletionVo = getInvPlanAnnualMonthCompletionVo(request);
        Double cumulativeInvestment = invoiceMonthCompletionVo.getCumulativeInvestment();
        log.info("cumulativeInvestment:{}",cumulativeInvestment);
        return IndicatorDataResponse.ok(cumulativeInvestment);
    }
}
