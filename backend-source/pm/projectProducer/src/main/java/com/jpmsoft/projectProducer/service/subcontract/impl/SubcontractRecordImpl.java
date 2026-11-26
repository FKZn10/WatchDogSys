package com.jpmsoft.projectProducer.service.subcontract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Subcontract.SubcontractRecordRequest;
import com.jpmsoft.common.model.response.Subcontract.SubcontractRecordResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.subcontract.SubcontractRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractRecord;
import com.jpmsoft.projectProducer.rabbitMQ.Sender;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.subcontract.ISubcontractRecordService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SubcontractRecordImpl extends ServiceImpl<SubcontractRecordMapper, SubcontractRecord> implements ISubcontractRecordService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private Sender sender;

    @Value("${spring.profiles.active}")
    private String active;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "scId", codeParam = "scCode", method = "setScCode", codeRule = "SubcontractRecord", beanClass = SubcontractRecordRequest.class)
    public BaseResponse createOrUpdateSubcontractRecord(SubcontractRecordRequest request) {
        SubcontractRecord entity = new SubcontractRecord();
        if (ValidateUtil.isEmpty(request.getScCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<SubcontractRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Sc_Code", request.getScCode());
        if (!ValidateUtil.isEmpty(request.getScId()))
            wrapper.ne("Sc_Id", request.getScId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (ValidateUtil.isEmpty(request.getScId())) {
            entity.setScId(GenerateUUID.getUUID());
            entity.setScCreateTime(new Date());
            entity.setScStateTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getScId(), request.gettId(), entity.getScState() + "", entity.getScCreateUid(), entity.getScCreateUname()
                    , entity.getScCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getScId());

            sendSubcontractRecord(entity, "0");
            return BaseResponse.ok(entity.getScId());
        } else {
            SubcontractRecord model = baseMapper.selectById(request.getScId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getScState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setScCreateUid(model.getScCreateUid());
            entity.setScCreateTime(model.getScCreateTime());
            entity.setScCreateUname(model.getScCreateUname());
            entity.setScState(model.getScState());
            entity.setScStateTime(model.getScStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getScId(), request.gettId(), entity.getScState() + "", entity.getScCreateUid(), entity.getScCreateUname()
                    , entity.getScCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getScId());

            sendSubcontractRecord(entity, "1");
            return BaseResponse.ok(entity.getScId());
        }
    }

    @Override
    public BaseResponse getSubcontractRecordList(SubcontractRecordRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Subcontract_Record", uId, "Sc_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Sc_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<SubcontractRecordRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("scTitle").put("scName").put("pName").put("conId").put("pId")
                .put("conName").put("scCode").put("scMode").put("scFp").put("scSp").put("scState")
                .put("scResident").put("scResidentTel").put("scResidentSsn").put("notScConId");
        IPage<SubcontractRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SubcontractRecordResponse> datas = baseMapper.getSubcontractRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSubcontractRecordByScId(String scId) {
        return BaseResponse.ok(baseMapper.getSubcontractRecordByScId(scId));
    }

    @Override
    public BaseResponse getSubcontractRecordByScIds(List<String> scIds) {
        return BaseResponse.ok(baseMapper.selectBatchIds(scIds));
    }

    @Override
    public BaseResponse getSubcontractRecordByScMasterConId(String scMasterConId) {
        QueryWrapper<SubcontractRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Sc_Master_Con_Id", scMasterConId);
        wrapper.eq("Sc_Sup","是");
        return BaseResponse.ok(baseMapper.selectList(wrapper));
    }


    private void sendSubcontractRecord(SubcontractRecord data, String opType) {
        if (active.equals("bj")) {
            log.info("调用推送分包合同");
//            Map<String, Object> sendData = new HashMap<>();
//            sendData.put("action", "pushSubcontractData");
//            sendData.put("data", JacksonUtils.toJSONString(data));
//            sendData.put("opType", opType);
//            sender.send(JacksonUtils.toJSONString(sendData));
        }
    }
}
