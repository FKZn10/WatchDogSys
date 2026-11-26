package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOtherProcurementModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecOtherProcurementMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOtherProcurement;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecOtherProcurementService;
import com.jpmsoft.projectProducer.service.secInfo.ISecPurchaserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SecOtherProcurementImpl extends ServiceImpl<SecOtherProcurementMapper, SecOtherProcurement> implements ISecOtherProcurementService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Resource
    private ISecPurchaserService secPurchaserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    //@CodeGeneration(key = "sopId", codeParam = "sopCode", method = "setSopCode", codeRule = "SecOtherProcurement", beanClass = SecOtherProcurementModel.class)
    public BaseResponse createOrUpdateSecOtherProcurement(SecOtherProcurementModel request) {
        SecOtherProcurement sec = new SecOtherProcurement();
        String codeRule = null;
        if (ValidateUtil.isEmpty(request.getSopId()) && apiUtilsService.sequenceRestEnable("SecOtherProcurement" + request.getSopType()) == true) {
            request.setSopCode(apiUtilsService.generateCodeByTCode("SecOtherProcurement" + request.getSopType(), request));
            if (ValidateUtil.isEmpty(request.getSopCode()))
                return BaseResponse.fail(ResMsg.codeGenerError);
            codeRule = "SecOtherProcurement" + request.getSopType();
        }
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<SecOtherProcurement> wrapper = new QueryWrapper<>();
        wrapper.eq("Sop_Code", request.getSecId());
        if (!ValidateUtil.isEmpty(request.getSopId()))
            wrapper.ne("Sop_Id", request.getSopId());
        wrapper.ne("Sop_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getSopId())) {
            sec.setSopCreateTime(new Date());
            sec.setSopId(GenerateUUID.getUUID());
            sec.setSopState(ValidateUtil.isEmpty(request.getSopState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getSopState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getSopId(), request.gettId(), sec.getSopState() + "", sec.getSopCreateUid(), sec.getSopCreateUname()
                    , sec.getSopCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getSopId());

        } else {
            SecOtherProcurement entity = baseMapper.selectById(request.getSopId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getSopState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setSopCreateTime(entity.getSopCreateTime());
            sec.setSopCreateUid(entity.getSopCreateUid());
            sec.setSopCreateUname(entity.getSopCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getSopId(), request.gettId(), sec.getSopState() + "", sec.getSopCreateUid(), sec.getSopCreateUname()
                    , sec.getSopCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getSopId());

        }
        secPurchaserService.createOrUpdateSecPurchaser(request.getSecPurchasers(), sec.getSopId());
        if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, request);
        return BaseResponse.ok(sec.getSopId());
    }

    @Override
    public BaseResponse getSecOtherProcurementList(SecOtherProcurementModel request) {
        QueryMap<SecOtherProcurementModel> params = new QueryMap<>(request);
        params.put("secId").put("sopPurchaseMethod").put("sopType").put("sopState").put("sopCode").put("sopEvaluation");
        List<SecOtherProcurementModel> datas = baseMapper.getSecOtherProcurementList(params.getQuery());
        Integer total = baseMapper.getSecOtherProcurementListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecOtherProcurementById(String sopId) {
        SecOtherProcurementModel data = baseMapper.getSecOtherProcurementById(sopId);
        if (data != null)
            data.setSecPurchasers(secPurchaserService.selectBySopId(sopId));
        return BaseResponse.ok(data, 0);
    }
}
