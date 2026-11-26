package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBiddingSchemeModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBiddingSchemeMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingScheme;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingSchemeService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingSchemeUnitSerivice;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SecBiddingSchemeImpl extends ServiceImpl<SecBiddingSchemeMapper, SecBiddingScheme> implements ISecBiddingSchemeService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecBiddingSchemeUnitSerivice secBiddingSchemeUnitSerivice;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecBiddingScheme(SecBiddingSchemeModel request) {
        SecBiddingScheme sec = new SecBiddingScheme();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId())) return BaseResponse.fail(ResMsg.notReqParam);
        sec.setBsAnnualInvestment(MathUtil.DecimalMultiplicationInt(sec.getBsAnnualInvestment(), 10000));
        sec.setBsProjectInvestment(MathUtil.DecimalMultiplicationInt(sec.getBsProjectInvestment(), 10000));
        sec.setBsApprovedAmount(MathUtil.DecimalMultiplicationInt(sec.getBsApprovedAmount(), 10000));
        if (ValidateUtil.isEmpty(request.getBsId())) {
            sec.setBsCreateTime(new Date());
            sec.setBsId(GenerateUUID.getUUID());
            sec.setBsState(ValidateUtil.isEmpty(request.getBsState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getBsState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getBsId(), request.gettId(), sec.getBsState() + "", sec.getBsCreateUid(), sec.getBsCreateUname(), sec.getBsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBsApprovalDoc(), request.getBsBasisDocFile(),request.getBsInstructions()), sec.getBsId());
        } else {
            SecBiddingScheme entity = baseMapper.selectById(request.getBsId());
            if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getBsState())) return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setBsCreateTime(entity.getBsCreateTime());
            sec.setBsCreateUid(entity.getBsCreateUid());
            sec.setBsCreateUname(entity.getBsCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getBsId(), request.gettId(), sec.getBsState() + "", sec.getBsCreateUid(), sec.getBsCreateUname(), sec.getBsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBsApprovalDoc(), request.getBsBasisDocFile(),request.getBsInstructions()), Arrays.asList(request.getFileIds(), request.getBsApprovalDocIds(), request.getBsBasisDocFileIds(),request.getBsInstructionsIds()), sec.getBsId());
        }
        secBiddingSchemeUnitSerivice.createOrUpdateSecBiddingSchemeUnit(request.getSecBiddingSchemeUnits(), sec.getBsId());
        return BaseResponse.ok(sec.getBsId());
    }

    @Override
    public BaseResponse getSecBiddingSchemeList(SecBiddingSchemeModel request) {
        QueryMap<SecBiddingSchemeModel> params = new QueryMap<>(request);
        params.put("bsApprovalType").put("secId").put("bsState").put("secName")
                .put("bsApprovalFlow").put("bsWhetherStandard").put("bsCreateUname")
                .put("bsCalculationMethod").put("bsContractingType")
                .put(request.getBsProjectInvestments(), "bsProjectInvestmentStart", "bsProjectInvestmentEnd")
                .put(request.getBsAnnualInvestments(), "bsAnnualInvestmentStart", "bsAnnualInvestmentEnd")
                .put(request.getBsApprovedAmounts(), "bsApprovedAmountStart", "bsApprovedAmountEnd")
                .put(request.getBsCreateTimes(), "bsCreateTimeStart", "bsCreateTimeEnd");
        List<SecBiddingSchemeModel> datas = baseMapper.getSecBiddingSchemeList(params.getQuery());
        Integer total = baseMapper.getSecBiddingSchemeListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecBiddingSchemeById(String bsId) {
        return BaseResponse.ok(baseMapper.getSecBiddingSchemeById(bsId));
    }

    @Override
    public BaseResponse updateSecBiddingScheme(SecBiddingSchemeModel request) {
        SecBiddingScheme entity = baseMapper.selectById(request.getBsId());
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        SecBiddingScheme sec = new SecBiddingScheme();
        BeanUtils.copyProperties(request, sec);
        sec.setBsCreateTime(entity.getBsCreateTime());
        sec.setBsCreateUid(entity.getBsCreateUid());
        sec.setBsCreateUname(entity.getBsCreateUname());
        sec.setSecId(entity.getSecId());
        baseMapper.updateById(sec);
        return BaseResponse.ok();
    }
}
