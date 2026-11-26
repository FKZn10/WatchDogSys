package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsPlanModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsPlanMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPlan;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsPlanService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class QsPlanImpl extends ServiceImpl<QsPlanMapper, QsPlan> implements IQsPlanService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysConfigService sysConfigService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "qspId", codeParam = "qspCode", method = "setQspCode", codeRule = "QsPlan", beanClass = QsPlanModel.class)
    public BaseResponse createOrUpdateQsPlan(QsPlanModel request) {
        QsPlan entity = new QsPlan();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getQspCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        QueryWrapper<QsPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("Qsp_Code", request.getQspCode());
        if (!ValidateUtil.isEmpty(request.getQspId()))
            wrapper.ne("Qsp_Id", request.getQspId());
        wrapper.ne("Qsp_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getQspId())) {
            entity.setQspCreateTime(new Date());
            entity.setQspId(GenerateUUID.getUUID());
            entity.setQspState(ValidateUtil.isEmpty(request.getQspState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getQspState());
            entity.setReplyState(2);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getQspId(), request.gettId(), entity.getQspState() + "", entity.getQspCreateUid(), entity.getQspCreateUname()
                    , entity.getQspCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getQspId());

            return BaseResponse.ok(entity.getQspId());
        } else {
            QsPlan model = baseMapper.selectById(request.getQspId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getQspState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setQspCreateTime(model.getQspCreateTime());
            entity.setQspCreateUid(model.getQspCreateUid());
            entity.setQspCreateUname(model.getQspCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getQspId(), request.gettId(), entity.getQspState() + "", entity.getQspCreateUid(), entity.getQspCreateUname()
                    , entity.getQspCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getQspId());


            return BaseResponse.ok(entity.getQspId());
        }
    }

    @Override
    public BaseResponse getQsPlanList(QsPlanModel request, String userId) {
        String conf = sysConfigService.getConfigByCode("specifyApiOnlyCreateorAndApprover");
        QueryMap<QsPlanModel> params = new QueryMap<>(request);
        if (ValidateUtil.isNotEmpty(conf) && conf.equals("1")) {
            StringBuilder sb = new StringBuilder();
            String dbCreateFiled = "qsp_create_uid";
            String primaryKey = "qsp_id";
            sb.append(" where (mn.").append(dbCreateFiled).append("='").append(userId).append("' or ");
            sb.append(" (select count(*) from Sys_Task_Processin st1p where st1p.tp_entityid=mn.").append(primaryKey)
                    .append(" and  st1p.tp_user='").append(userId).append("')>0)");
            String authSql = sb.toString();
            params.put("authSql", authSql);
        }
        params.put("qspProjectType").put("qspType").put("qspCategory").put("qspName").put("qspCode").put("replyState");
        IPage<QsPlanModel> page = new Page<>(request.getPage(), request.getSize());
        List<QsPlanModel> datas = baseMapper.getQsPlanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getQsPlanById(String qspId) {
        QsPlanModel data = baseMapper.getQsPlanById(qspId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse setQsPlanReplyState(QsPlanModel request) {
        QsPlan data = baseMapper.selectById(request.getQspId());
        if (data != null) {
            data.setReplyState(request.getReplyState());
            baseMapper.updateById(data);
            return BaseResponse.ok();
        } else {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
    }
}
