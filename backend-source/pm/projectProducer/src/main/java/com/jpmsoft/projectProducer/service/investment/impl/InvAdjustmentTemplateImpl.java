package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentTemplateRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentTemplateDtlModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvAdjustmentTemplateResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvAdjustmentTemplateMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvRelatedRelationshipsMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoBudgetMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplate;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentTemplateDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentTemplateService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class InvAdjustmentTemplateImpl extends ServiceImpl<InvAdjustmentTemplateMapper, InvAdjustmentTemplate> implements IInvAdjustmentTemplateService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IInvAdjustmentTemplateDtlService invAdjustmentTemplateDtlService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IConContractService conContractService;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private InvRelatedRelationshipsMapper invRelatedRelationshipsMapper;

    @Resource
    private SecInfoBudgetMapper secInfoBudgetMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvAdjustmentTemplate(InvAdjustmentTemplateRequest request) {
        InvAdjustmentTemplate entity = new InvAdjustmentTemplate();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getAtId())) {
            entity.setCreateTime(new Date());
            entity.setAtId(GenerateUUID.getUUID());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getAtId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getAtId());
        } else {
            InvAdjustmentTemplate model = baseMapper.selectById(request.getAtId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getState())) return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setState(model.getState());
            entity.setStateTime(model.getStateTime());
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getAtId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()), Arrays.asList(request.getFileIds()), entity.getAtId());
        }
        invAdjustmentTemplateDtlService.createOrUpdateInvAdjustmentTemplateDtl(request.getInvAdjustmentTemplateDtls(), entity.getAtId());
        return BaseResponse.ok(entity.getAtId());
    }

    @Override
    public BaseResponse getInvAdjustmentTemplateList(InvAdjustmentTemplateRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.NOT.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvAdjustmentTemplateRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("atType").put("atTitle").put("state").put("nostate");
        IPage<InvAdjustmentTemplateResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvAdjustmentTemplateResponse> datas = baseMapper.getAdjustmentTemplateList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvAdjustmentTemplateByAtId(String atId) {
        InvAdjustmentTemplateResponse data = baseMapper.getAdjustmentTemplateByAtId(atId);
        if (data != null) {
            List<InvAdjustmentTemplateDtlModel> child = invAdjustmentTemplateDtlService.getInvAdjustmentTemplateDtlModelByAtId(data.getAtId());
            data.setInvAdjustmentTemplateDtls(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteInvAdjustmentTemplateByAtId(String atId) {
        InvAdjustmentTemplate model = baseMapper.selectById(atId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getState())) return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(atId);
        invAdjustmentTemplateDtlService.deleteAllByAtId(atId);
        baseMapper.deleteById(atId);
        return BaseResponse.ok();
    }
}
