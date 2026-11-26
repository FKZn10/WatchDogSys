package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundPlanMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlan;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtl;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundPlanVo;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanDtlService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:07
 */
@Service
public class FundPlanImpl extends BaseFileImpl<FundPlanMapper, FundPlan, FundPlanDto> implements IFundPlanService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IFundPlanDtlService fundPlanDtlService;
    @Resource
    private IConContractService conContractService;

    @Override
    @CodeGeneration(key = "id", codeParam = "fpCode", method = "setFpCode", codeRule = "FundPlan", beanClass = FundPlanDto.class)
    public BaseResponse createOrUpdateFundPlan(FundPlanDto request) {
        FundPlan entity = new FundPlan();
        if (ValidateUtil.isEmpty(request.getFpCode())) return BaseResponse.fail(ResMsg.codeIsEmpty);
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        fundPlanDtlService.createOrUpdateFundPlanDtl(request.getFundPlanDtls(),request.getFundPlanDtls2(),request.getFundPlanDtls3(), entity.getId(), entity.getPId(),entity.getFpType());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getFundPlanList(FundPlanDto request, String uId) {
        String authSql = sysUserService.onlyCreateorAndApprover("Fund_Plan", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State","id");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<FundPlanDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("state").put("createUname")
                .put("cName").put("fpYear").put("fpMonth").put("pId").put("fpType");
        IPage<FundPlanVo> page = new Page<>(request.getPage(), request.getSize());
        List<FundPlanVo> datas = baseMapper.getFundPlanList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getFundPlanById(String id) {
        FundPlanVo data = baseMapper.getFundPlanById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getProFpDemandFinalSum(String pId) {
        return BaseResponse.ok(baseMapper.getProFpDemandFinalSum(pId));
    }

    @Override
    public void updateFinalInfo(String id) {
        List<FundPlanDtl> dtls = fundPlanDtlService.getFundPlanDtlByFpId(id);
        FundPlan fp = baseMapper.selectById(id);
        if (!CollectionUtils.isEmpty(dtls)) {
            List<FundPlanDtl> semands = dtls.stream().filter(n -> ValidateUtil.isNotNull(n.getFpdDemandFinal())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(semands)) fp.setFpDemandFinal(BigDecimal.ZERO);
            else
                fp.setFpDemandFinal(semands.stream().map(FundPlanDtl::getFpdDemandFinal).reduce(BigDecimal.ZERO, BigDecimal::add));
            List<FundPlanDtl> semandTotals = dtls.stream().filter(n -> ValidateUtil.isNotNull(n.getFpdDemandTotalFinal())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(semandTotals)) fp.setFpDemandTotalFinal(BigDecimal.ZERO);
            else
                fp.setFpDemandTotalFinal(semandTotals.stream().map(FundPlanDtl::getFpdDemandTotalFinal).reduce(BigDecimal.ZERO, BigDecimal::add));
            fp.setFpDemandRatioTotalFinal(MathUtil.decimalDivide(fp.getFpDemandTotalFinal(), conContractService.getConMoneyTotalSum(dtls.stream().map(FundPlanDtl::getConId).collect(Collectors.toList()))).floatValue());
            baseMapper.updateById(fp);
        }
    }

    @Override
    public BigDecimal getDemand(String id, String conId) {
        BigDecimal amt = baseMapper.getDemand(id, conId);
        return amt;
    }
}
