package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundAppropriationMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAppropriationDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAppropriation;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.ConAppropriationStatisticsVo;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundAppropriationDtlVo;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundAppropriationVo;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundPlanDtlVo;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAppropriationDtlService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAppropriationService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanDtlService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:31
 */
@Service
public class FundAppropriationImpl extends BaseFileImpl<FundAppropriationMapper, FundAppropriation, FundAppropriationDto>
        implements IFundAppropriationService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IFundAppropriationDtlService fundAppropriationDtlService;
    @Resource
    private IFundPlanService fundPlanService;
    @Resource
    private IFundPlanDtlService fundPlanDtlService;

    @Override
    @CodeGeneration(key = "id", codeParam = "faCode", method = "setFaCode", codeRule = "FundAppropriation", beanClass = FundAppropriationDto.class)
    public BaseResponse createOrUpdateFundAppropriation(FundAppropriationDto request) {
        haveUnClose(request.getFpId(), request.getId());
        FundAppropriation entity = new FundAppropriation();
        if (ValidateUtil.isEmpty(request.getFaCode())) return BaseResponse.fail(ResMsg.codeIsEmpty);
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        fundAppropriationDtlService.createOrUpdateFundAppropriationDtl(request.getFundAppropriationDtls(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getFundAppropriationList(FundAppropriationDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Fund_Appropriation", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<FundAppropriationDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("state").put("createUname").put("pName")
                .put("faYear").put("faMonth").put("pId");
        IPage<FundAppropriationVo> page = new Page<>(request.getPage(), request.getSize());
        List<FundAppropriationVo> datas = baseMapper.getFundAppropriationList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getFundAppropriationById(String id) {
        FundAppropriationVo data = baseMapper.getFundAppropriationById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getProCompletedInvSum(String pId) {
        BigDecimal amt = baseMapper.getProCompletedInvSum(pId);
        return BaseResponse.ok(amt);
    }

    @Override
    public BaseResponse getConAppropriationDtl(String fpId, String conId) {
        ConAppropriationStatisticsVo data = baseMapper.getConAppropriationDtl(conId);
        if (data == null) data = new ConAppropriationStatisticsVo();
        if (ValidateUtil.isNotEmpty(fpId))
            data.setDemand(fundPlanService.getDemand(fpId, conId));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getAllFundPlanDtlByFpId(String fpId) {
        List<FundPlanDtlVo> datas = fundPlanDtlService.getAllFundPlanDtlByFpId(fpId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<FundAppropriationDtlVo> results = datas.stream().map(data -> {
                FundAppropriationDtlVo res = new FundAppropriationDtlVo();
                res.setConName(data.getConName());
                res.setConMoneyTotal(data.getConMoneyTotal());
                res.setpId(data.getpId());
                res.setConId(data.getConId());
                res.setPName(data.getPName());
                res.setFadSort(data.getFpdSort());
                res.setConPayClause(data.getConPayClause());
                res.setFadDemand(data.getFpdDemandFinal());
                ConAppropriationStatisticsVo stats = baseMapper.getConAppropriationDtl(data.getConId());
                if (stats == null) stats = new ConAppropriationStatisticsVo();
                res.setFadInvestmentCompleted(stats.getInvCompleted());
                res.setFadPayTotal(stats.getPayTotal());
                return res;
            }).collect(Collectors.toList());
            return BaseResponse.ok(results);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getFaDemandSumByPid(String pId) {
        return BaseResponse.ok(baseMapper.getFaDemandSumByPid(pId));
    }


    private void haveUnClose(String fpId, String id) {
        if (ValidateUtil.isEmpty(id)) {
            QueryWrapper<FundAppropriation> wrapper = new QueryWrapper<>();
            wrapper.eq("Fp_Id", fpId);
            wrapper.ne("State", ApprovalStatusEnum.Invalid.getIndex());
            wrapper.ne("State", ApprovalStatusEnum.Already_Over.getIndex());
            if (baseMapper.exists(wrapper)) throw new JPMException(ResMsg.recordUnFinish);
        }
    }
}
