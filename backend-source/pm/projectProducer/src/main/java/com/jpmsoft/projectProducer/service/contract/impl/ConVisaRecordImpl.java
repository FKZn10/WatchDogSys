package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Contract.ConVisaRecordRequest;
import com.jpmsoft.common.model.response.Contract.ConVisaRecordResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConVisaRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConVisaRecord;
import com.jpmsoft.projectProducer.service.contract.IConVisaRecordService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ConVisaRecordImpl extends ServiceImpl<ConVisaRecordMapper, ConVisaRecord> implements IConVisaRecordService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "vrId", codeParam = "vrCode", method = "setVrCode", codeRule = "ConVisaRecord", beanClass = ConVisaRecordRequest.class)
    public BaseResponse createOrUpdateConVisaRecord(ConVisaRecordRequest request) {
        ConVisaRecord entity = new ConVisaRecord();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getVrCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);

        QueryWrapper<ConVisaRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Vr_Code", request.getVrCode());
        if (!ValidateUtil.isEmpty(entity.getVrId()))
            wrapper.ne("Vr_Id", request.getVrId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getVrId())) {
            entity.setVrId(GenerateUUID.getUUID());
            entity.setVrCreateTime(new Date());
            entity.setVrStateTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getVrId(), request.gettId(), entity.getVrState() + "", entity.getVrCreateUid(), entity.getVrCreateUname()
                    , entity.getVrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getVrPic(), request.getVrFileContact(), request.getVrFileEstimate()), entity.getVrId());
        } else {
            ConVisaRecord model = baseMapper.selectById(request.getVrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getVrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setVrCreateTime(model.getVrCreateTime());
            entity.setVrCreateUid(model.getVrCreateUid());
            entity.setVrCreateUname(model.getVrCreateUname());
            entity.setVrState(model.getVrState());
            entity.setVrStateTime(model.getVrStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getVrId(), request.gettId(), entity.getVrState() + "", entity.getVrCreateUid(), entity.getVrCreateUname()
                    , entity.getVrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getVrPic(), request.getVrFileContact(), request.getVrFileEstimate())
                    , Arrays.asList(request.getFileIds(), request.getVrPicIds(), request.getVrFileContactIds(), request.getVrFileEstimateIds()), entity.getVrId());
        }
        return BaseResponse.ok(entity.getVrId());
    }

    @Override
    public BaseResponse getConVisaRecordList(ConVisaRecordRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Visa_Record", uId, "Vr_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Vr_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConVisaRecordRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("conName")
                .put("vrCode").put("vrState").put("conVisaFeeFilter").put("enisName");
        IPage<ConVisaRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConVisaRecordResponse> datas = baseMapper.getConVisaRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConVisaRecordByVrId(String vrId) {
        ConVisaRecordResponse data = baseMapper.getConVisaRecordByVrId(vrId);
        return BaseResponse.ok(data);
    }
}
