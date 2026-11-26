package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminResultRequest;
import com.jpmsoft.common.model.response.SecInfo.SecDeterminResultResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDeterminResultMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDeterminResult;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecDeterminCpyService;
import com.jpmsoft.projectProducer.service.secInfo.ISecDeterminResultService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.LogicUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SecDeterminResultImpl extends ServiceImpl<SecDeterminResultMapper, SecDeterminResult> implements ISecDeterminResultService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecDeterminCpyService secDeterminCpyService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecDeterminResult(SecDeterminResultRequest request) {
        SecDeterminResult sec = new SecDeterminResult();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);

        if (ValidateUtil.isEmpty(request.getDrId())) {
            sec.setDrCreateTime(new Date());
            sec.setDrId(GenerateUUID.getUUID());
            sec.setDrState(ValidateUtil.isEmpty(request.getDrState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getDrState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getDrId(), request.gettId(), sec.getDrState() + "", sec.getDrCreateUid(), sec.getDrCreateUname()
                    , sec.getDrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getDrId());

        } else {
            SecDeterminResult entity = baseMapper.selectById(request.getDrId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getDrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setDrCreateTime(entity.getDrCreateTime());
            sec.setDrCreateUid(entity.getDrCreateUid());
            sec.setDrCreateUname(entity.getDrCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getDrId(), request.gettId(), sec.getDrState() + "", sec.getDrCreateUid(), sec.getDrCreateUname()
                    , sec.getDrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getDrId());

        }
        secDeterminCpyService.createOrUpdateSecDeterminCpy(request.getSecDeterminCpys(), sec.getDrId());
        return BaseResponse.ok(sec.getDrId());
    }

    @Override
    public BaseResponse getSecDeterminResultList(SecDeterminResultRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "Dr_Create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);

        params.put("secId", ValidateUtil.isEmpty(request.getSecId()) ? null : request.getSecId());
        params.put("drBidWinner", ValidateUtil.isEmpty(request.getDrBidWinner()) ? null : request.getDrBidWinner());
        params.put("drCalibrationType", ValidateUtil.isEmpty(request.getDrCalibrationType()) ? null : request.getDrCalibrationType());
        params.put("drCalibrationMode", ValidateUtil.isEmpty(request.getDrCalibrationMode()) ? null : request.getDrCalibrationMode());

        params.put("drPreferential", ValidateUtil.isEmpty(request.getDrPreferential()) ? null : request.getDrPreferential());
        params.put("secName", ValidateUtil.isEmpty(request.getSecName()) ? null : request.getSecName());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("drRemark", ValidateUtil.isEmpty(request.getDrRemark()) ? null : request.getSecName());
        params.put("drBidWinnerName", ValidateUtil.isEmpty(request.getDrBidWinnerName()) ? null : request.getDrBidWinnerName());
        params.put("drCreateUname", ValidateUtil.isEmpty(request.getDrCreateUname()) ? null : request.getDrCreateUname());
        LogicUtil.putParamToMap(params, request.getDrCreateTimes(), "drCreateTimeStart", "drCreateTimeEnd");
        LogicUtil.putParamToMap(params, request.getDrCalibrationDates(), "drCalibrationDateStart", "drCalibrationDateEnd");
        LogicUtil.putParamToMap(params, request.getDrVacancies(), "drVacancieStart", "drVacancieEnd");


        List<SecDeterminResultResponse> datas = baseMapper.getSecDeterminResultList(params);
        Integer total = baseMapper.getSecDeterminResultListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecDeterminResultById(String drId) {
        SecDeterminResultResponse data = baseMapper.getSecDeterminResultById(drId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getFirstDeterminResultBySecId(String secId) {
        SecDeterminResultResponse data = baseMapper.getFirstDeterminResultBySecId(secId);
        return BaseResponse.ok(data);
    }
}
