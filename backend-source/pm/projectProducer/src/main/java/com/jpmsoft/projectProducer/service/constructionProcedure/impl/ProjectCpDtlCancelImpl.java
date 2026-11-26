package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.proCp.ProjectCpDtlCancelModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpDtlCancelMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtlCancel;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlCancelService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectCpDtlCancelImpl extends FileImpl<ProjectCpDtlCancelMapper, ProjectCpDtlCancel, ProjectCpDtlCancelModel> implements IProjectCpDtlCancelService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IProjectCpDtlService projectCpDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProjectCpDtlCancel(ProjectCpDtlCancelModel request) {
        boolean disable = false;
        if (ValidateUtil.isEmpty(request.getCdcId())) {
            disable = true;
            QueryWrapper<ProjectCpDtlCancel> wrapper = new QueryWrapper<>();
            wrapper.eq("Cpd_Id", request.getCpdId());
            wrapper.ne("State", ApprovalStatusEnum.Invalid.getIndex());
            wrapper.ne("State", ApprovalStatusEnum.Already_Over.getIndex());
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        ProjectCpDtlCancel entity = new ProjectCpDtlCancel();
        createOrUpdateFileEntity(entity, request);
        if (disable)
            projectCpDtlService.updateCpDtlStatus(entity.getCpdId(), 0);
        return BaseResponse.ok(entity.getCdcId());
    }

    @Override
    public BaseResponse getProjectCpDtlCancelList(ProjectCpDtlCancelModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Project_Cp_Dtl_Cancel", uId, "Cdc_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ProjectCpDtlCancelModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("cpdName").put("pCode")
                .put("state").put("cdcCreateUname").put("pId").put("cpdId");
        IPage<ProjectCpDtlCancelModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpDtlCancelModel> data = baseMapper.getProjectCpDtlCancelList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProjectCpDtlCancelById(String cdcId) {
        ProjectCpDtlCancelModel data = baseMapper.getProjectCpDtlCancelById(cdcId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse existApproveCpCancel(String cpdId) {
        LambdaQueryWrapper<ProjectCpDtlCancel> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(ProjectCpDtlCancel::getState, ApprovalStatusEnum.Invalid.getIndex());
        wrapper.ne(ProjectCpDtlCancel::getState, ApprovalStatusEnum.Already_Over.getIndex());
        return BaseResponse.ok(baseMapper.exists(wrapper));
    }

    @Override
    public BaseResponse enableProjectCpDtlStatus(String cdcId) {
        ProjectCpDtlCancel pojo = baseMapper.selectById(cdcId);
        if (pojo != null && Objects.equals(pojo.getState(), ApprovalStatusEnum.Invalid.getIndex())) {
            projectCpDtlService.updateCpDtlStatus(pojo.getCpdId(), 1);
        }
        return BaseResponse.ok();
    }
}
