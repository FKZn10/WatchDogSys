package com.jpmsoft.projectProducer.service.constructionProcedure.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpRprMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCp;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpRpr;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpRprService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class ProjectCpRprServiceImpl extends ServiceImpl<ProjectCpRprMapper, ProjectCpRpr> implements IProjectCpRprService {

    @Resource
    private IGanttTasksService ganttTasksService;
    @Resource
    private IGanttPlanService ganttPlanService;
    @Resource
    private IProjectCpService projectCpService;
    @Resource
    private IProjectCpDtlService projectCpDtlService;

    @Override
    public BaseResponse approve2SyncGanttTaskState(String id) {
        ProjectCpRpr rpr = baseMapper.selectById(id);
        if (rpr == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (Objects.equals(rpr.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (ValidateUtil.isNotNull(rpr.getIsEnd()) && rpr.getIsEnd()) {
            ProjectCpDtl dtl = projectCpDtlService.getById(rpr.getCpdId());
            ProjectCp cp = projectCpService.selectByCpdId(dtl.getCpId());
            GanttPlan plan = ganttPlanService.selectByPid(cp.getpId());
            if (plan != null) {
                ganttTasksService.cpApprove2SyncGanttTaskState(dtl, plan, rpr);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse canToDeleteGanttTaskChange(String pId, String cplId) {
        ProjectCp cp = projectCpService.selectByPid(pId);
        if (cp == null) return BaseResponse.ok(true);
        ProjectCpDtl dtl = projectCpDtlService.getByCpAndCpl(cp.getCpId(), cplId);
        if (dtl == null) return BaseResponse.ok(true);
        LambdaQueryWrapper<ProjectCpRpr> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(ProjectCpRpr::getState, ApprovalStatusEnum.Invalid.getIndex());
        wrapper.eq(ProjectCpRpr::getCpdId, dtl.getCpdId());
        return BaseResponse.ok(!baseMapper.exists(wrapper));
    }
}
