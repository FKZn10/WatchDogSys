package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecCalibrationMoonMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationMoon;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationMoonService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SecCalibrationMoonImpl extends ServiceImpl<SecCalibrationMoonMapper, SecCalibrationMoon> implements ISecCalibrationMoonService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecCalibrationMoon(SecBrightMoonModel request) {
        SecCalibrationMoon sec = new SecCalibrationMoon();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getSbmId())) {
            sec.setSbmCreateTime(new Date());
            sec.setSbmId(GenerateUUID.getUUID());
            sec.setSbmState(ValidateUtil.isEmpty(request.getSbmState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getSbmState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getSbmId(), request.gettId(), sec.getSbmState() + "", sec.getSbmCreateUid(), sec.getSbmCreateUname()
                    , sec.getSbmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSbmBidClearanceReport(), request.getSbmListBidClearingTeams(), request.getSbmContractorInformation()), sec.getSbmId());

            return BaseResponse.ok(sec.getSbmId());
        } else {
            SecCalibrationMoon entity = baseMapper.selectById(request.getSbmId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getSbmState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setSbmCreateTime(entity.getSbmCreateTime());
            sec.setSbmCreateUid(entity.getSbmCreateUid());
            sec.setSbmCreateUname(entity.getSbmCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getSbmId(), request.gettId(), sec.getSbmState() + "", sec.getSbmCreateUid(), sec.getSbmCreateUname()
                    , sec.getSbmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSbmBidClearanceReport(), request.getSbmListBidClearingTeams(), request.getSbmContractorInformation())
                    , Arrays.asList(request.getFileIds(), request.getSbmBidClearanceReportIds(), request.getSbmListBidClearingTeamsIds(), request.getSbmContractorInformationIds()), sec.getSbmId());

            return BaseResponse.ok(sec.getSbmId());
        }
    }

    @Override
    public BaseResponse getSecCalibrationMoonList(SecBrightMoonModel request) {
        QueryMap<SecBrightMoonModel> params = new QueryMap<>(request);
        params.put("secId").put("secName").put("sbmCreateUname").put("sbmState");
        List<SecBrightMoonModel> datas = baseMapper.getSecCalibrationMoonList(params.getQuery());
        Integer total = baseMapper.getSecCalibrationMoonCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecCalibrationMoonById(String sbmId) {
        SecBrightMoonModel data = baseMapper.getSecCalibrationMoonById(sbmId);
        return BaseResponse.ok(data, 0);
    }
}
