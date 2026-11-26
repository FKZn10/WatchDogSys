package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.InvestmentControl.InvProjectPlanRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvProjectPlanResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvProjectPlanMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApplyFlow;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvProjectPlan;
import com.jpmsoft.projectProducer.service.contract.IConPayApplyFlowService;
import com.jpmsoft.projectProducer.service.investment.IInvProjectPlanService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvProjectPlanImpl extends ServiceImpl<InvProjectPlanMapper, InvProjectPlan> implements IInvProjectPlanService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IConPayApplyFlowService conPayApplyFlowService;

    @Override
    public void createOrUpdateInvProjectPlan(List<InvProjectPlanRequest> datas, String ifrId) {
        deleteAllByIfrId(ifrId);
        List<InvProjectPlan> entitys = new ArrayList<>();
        datas.forEach(data -> {
            InvProjectPlan entity = new InvProjectPlan();
            BeanUtils.copyProperties(data, entity);
            entity.setIfrId(ifrId);
            entity.setIppId(GenerateUUID.getUUID());
            entity.setIppAccumulatedFunds(MathUtil.DecimalMultiplicationInt(entity.getIppAccumulatedFunds(), 10000));
            entity.setIppFundsArranged(MathUtil.DecimalMultiplicationInt(entity.getIppFundsArranged(), 10000));
            entity.setIppAnnualAccumFund(MathUtil.DecimalMultiplicationInt(entity.getIppAnnualAccumFund(), 10000));
            entity.setIppReleaseFunds(MathUtil.DecimalMultiplicationInt(entity.getIppReleaseFunds(), 10000));
            entity.setIppAnnualReleaseFunds(MathUtil.DecimalMultiplicationInt(entity.getIppAnnualReleaseFunds(), 10000));
            entitys.add(entity);
        });
        saveBatch(entitys);
    }

    @Override
    public BaseResponse getInvProjectPlanList(InvProjectPlanRequest request, String uId) {
        QueryMap<InvProjectPlanRequest> params = new QueryMap<>(request);
        params.put("authSql", " where 1=1 ").put("pCode").put("haveIfrId").put("pName").put("ifrReplyCode")
                .put("ippPType").put("ippDocNumber").put("ippLiableCompany").put("ippLiableMan").put("ippInvestor")
                .put("ippBuildContent").put("ippBuildNature").put("ippProgressLastYear").put("ippNowYearProgress")
                .put(request.getIppPlanInvs(), "ippPlanInvStart", "ippPlanInvEnd").put("pId")
                .put(request.getIppAnnualReleaseFundses(), "ippAccumulatedFundsStart", "ippAccumulatedFundsEnd")
                .put(request.getIppAccumulatedFundses(), "ippAnnualReleaseFundsStart", "ippAnnualReleaseFundsEnd")
                .put(request.getIppBuildPeriods(), "ippBuildPeriodStart", "ippBuildPeriodEnd");
        IPage<InvProjectPlanResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvProjectPlanResponse> datas = baseMapper.getInvProjectPlanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    private void deleteAllByIfrId(String ifrId) {
        QueryWrapper<InvProjectPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("Ifr_Id", ifrId);
        baseMapper.delete(wrapper);
    }


    @Override
    public BaseResponse getInvProPlanHistoryAmt(String pId, String year) {
        QueryWrapper<InvProjectPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.isNull("Ifr_Id");
        List<InvProjectPlan> datas = baseMapper.selectList(wrapper);
        BigDecimal allCount = datas.stream().map(InvProjectPlan::getIppReleaseFunds).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<InvProjectPlan> thisYear = datas.stream().filter(n -> n.getIppYear().equals(year)).collect(Collectors.toList());
        BigDecimal yearCount = BigDecimal.ZERO;
        BigDecimal ippReleaseFundsCount = BigDecimal.ZERO;

        if (thisYear != null && thisYear.size() > 0) {
            yearCount = datas.stream().map(InvProjectPlan::getIppReleaseFunds).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            ippReleaseFundsCount = datas.stream().map(InvProjectPlan::getIppReleaseFunds).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        Map<String, BigDecimal> money = new HashMap<>();
        money.put("ippAnnualAccumFund", allCount);
        money.put("ippAnnualReleaseFunds", yearCount);
        money.put("ippReleaseFunds", ippReleaseFundsCount);
        return BaseResponse.ok(money);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvProjectPlan(InvProjectPlanRequest request) {
        InvProjectPlan entity = new InvProjectPlan();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getIppId())) {
            entity.setIppCreateTime(new Date());
            entity.setIppId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(request.getIppFile(), entity.getIppId());

        } else {
            InvProjectPlan model = baseMapper.selectById(request.getIppId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setIppCreateTime(model.getIppCreateTime());
            entity.setIppCreateUid(model.getIppCreateUid());
            entity.setIppCreateUname(model.getIppCreateUname());
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(request.getIppFile(), request.getIppFileIds(), entity.getIppId());

        }
        return BaseResponse.ok(entity.getIppId());
    }

    @Override
    public BaseResponse getInvProjectPlanById(String ippId) {
        InvProjectPlanResponse data = baseMapper.getInvProjectPlanById(ippId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getInvProjectPlanByPIdAndYear(String pId, Integer year, String fundsType) {
        QueryWrapper<InvProjectPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("Ipp_Year", year);
        wrapper.eq("Ipp_Source_Funds", fundsType);
        wrapper.isNotNull("Ifr_Id");
        List<InvProjectPlan> datas = baseMapper.selectList(wrapper);
        QueryWrapper<ConPayApplyFlow> conPayPojoWrapper = new QueryWrapper<>();
        conPayPojoWrapper.eq("P_Id", pId);
        conPayPojoWrapper.eq("State", "7");
        List<ConPayApplyFlow> ConPayApplyFlowList = conPayApplyFlowService.list(conPayPojoWrapper);
        Map<String, Object> money = new HashMap<>();
        money.put("InvProjectPlan", datas);
        money.put("ConPayApplyFlowList", ConPayApplyFlowList);
        return BaseResponse.ok(money);
    }

    @Override
    public List<InvProjectPlan> getInvProjectPlanListByPids(List<String> pIds) {
        return baseMapper.getInvProjectPlanListByPids(pIds);
    }
}
