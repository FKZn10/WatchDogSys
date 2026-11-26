package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecCalibrationSchemeModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecCalibrationSchemeMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationScheme;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationSchemeService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SecCalibrationSchemeImpl extends ServiceImpl<SecCalibrationSchemeMapper, SecCalibrationScheme> implements ISecCalibrationSchemeService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecCalibrationScheme(SecCalibrationSchemeModel request) {
        SecCalibrationScheme sec = new SecCalibrationScheme();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getScsId())) {
            sec.setScsCreateTime(new Date());
            sec.setScsId(GenerateUUID.getUUID());
            sec.setScsState(ValidateUtil.isEmpty(request.getScsState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getScsState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getScsId(), request.gettId(), sec.getScsState() + "", sec.getScsCreateUid(), sec.getScsCreateUname()
                    , sec.getScsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getScsMinutesMeeting(), request.getScsCalibrationScheme()), sec.getScsId());

            return BaseResponse.ok(sec.getScsId());
        } else {
            SecCalibrationScheme entity = baseMapper.selectById(request.getScsId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getScsState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setScsCreateTime(entity.getScsCreateTime());
            sec.setScsCreateUid(entity.getScsCreateUid());
            sec.setScsCreateUname(entity.getScsCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getScsId(), request.gettId(), sec.getScsState() + "", sec.getScsCreateUid(), sec.getScsCreateUname()
                    , sec.getScsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getScsMinutesMeeting(), request.getScsCalibrationScheme()),
                    Arrays.asList(request.getFileIds(), request.getScsMinutesMeetingIds(), request.getScsCalibrationSchemeIds()), sec.getScsId());


            return BaseResponse.ok(sec.getScsId());
        }
    }

    @Override
    public BaseResponse getSecCalibrationSchemeList(SecCalibrationSchemeModel request) {
        QueryMap<SecCalibrationSchemeModel> params = new QueryMap<>(request);
        params.put("secId").put("scsState").put("secName").put("scsCreateUname");
        List<SecCalibrationSchemeModel> datas = baseMapper.getSecCalibrationSchemeList(params.getQuery());
        Integer total = baseMapper.getSecCalibrationSchemeListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecCalibrationSchemeById(String scsId) {
        SecCalibrationSchemeModel data = baseMapper.getSecCalibrationSchemeById(scsId);
        return BaseResponse.ok(data, 0);
    }
}
