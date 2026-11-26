package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Plan.GanttPlanTemplateModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttPlanTemplateMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlanTemplate;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttLinksTemplateService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanTemplateService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksTemplateService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class GanttPlanTemplateImpl extends ServiceImpl<GanttPlanTemplateMapper, GanttPlanTemplate> implements IGanttPlanTemplateService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IGanttLinksTemplateService ganttLinksTemplateService;
    @Resource
    private IGanttTasksTemplateService ganttTasksTemplateService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGanttPlanTemplate(GanttPlanTemplateModel request) {
        GanttPlanTemplate entity = new GanttPlanTemplate();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getPtId())) {
            entity.setPtId(GenerateUUID.getUUID());
            entity.setPtCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(request.getPtFile(), entity.getPtId());
        } else {
            GanttPlanTemplate model = baseMapper.selectById(entity.getPtId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setPtCreateUid(model.getPtCreateUid());
            entity.setPtCreateUname(model.getPtCreateUname());
            entity.setPtCreateTime(model.getPtCreateTime());
            entity.setPtEditTime(new Date());
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(request.getPtFile(), request.getPtFileIds(), entity.getPtId());
        }
        ganttTasksTemplateService.createOrUpdateGanttTasks(request.getGanttTasksTemplates(), entity.getPtId());
        ganttLinksTemplateService.createOrUpdateGanttLinks(request.getGanttLinksTemplates(), entity.getPtId());
        return BaseResponse.ok(entity.getPtId());
    }

    @Override
    public BaseResponse getGanttPlanTemplateList(GanttPlanTemplateModel request) {
        QueryWrapper<GanttPlanTemplate> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (!ValidateUtil.isEmpty(request.getPtPType()))
            wrapper.eq("Pt_P_Type", request.getPtPType());
        if (!ValidateUtil.isEmpty(request.getPtPlanType()))
            wrapper.eq("Pt_Plan_Type", request.getPtPlanType());
        if (!ValidateUtil.isEmpty(request.getPtState()))
            wrapper.eq("Pt_State", request.getPtState());
        if (!ValidateUtil.isEmpty(request.getPtAutoPlan()))
            wrapper.eq("Pt_Auto_Plan", request.getPtAutoPlan());
        if (!ValidateUtil.isEmpty(request.getPtTemplateType()))
            wrapper.eq("Pt_Template_Type", request.getPtTemplateType());
        if (!ValidateUtil.isEmpty(request.getPtName()))
            wrapper.like("Pt_Name", request.getPtName());
        if (!CollectionUtils.isEmpty(request.getPtPlanTypes()))
            wrapper.in("Pt_Plan_Type", request.getPtPlanTypes());
        if (!CollectionUtils.isEmpty(request.getExcludeTypes()))
            wrapper.and(e -> e.notIn("Pt_Plan_Type", request.getExcludeTypes())
                    .or().isNull("Pt_Plan_Type")
            );
        if (!ValidateUtil.isEmpty(request.getSortFiled()) && !ValidateUtil.isEmpty(request.getSortType())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("Pt_Create_Time");
        IPage<GanttPlanTemplate> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse getGanttPlanTemplateById(String id) {
        GanttPlanTemplate plan = baseMapper.selectById(id);
        if (plan == null) return BaseResponse.ok(ResMsg.infoNotExist);
        GanttPlanTemplateModel model = new GanttPlanTemplateModel();
        BeanUtils.copyProperties(plan, model);
        model.setGanttLinksTemplates(ganttLinksTemplateService.getLinksByPtId(id));
        model.setGanttTasksTemplates(ganttTasksTemplateService.getGanttTasksTemplateTree(id));
        return BaseResponse.ok(model);
    }

    @Override
    public BaseResponse getMilestoneGanttPlanTemp() {
        QueryWrapper<GanttPlanTemplate> wrapper=new QueryWrapper<>();
        wrapper.eq("Pt_Plan_Type","Milestone");
        wrapper.eq("Pt_State",1);
        wrapper.orderByAsc("Pt_Create_Time");
        wrapper.last(" limit 1");
        GanttPlanTemplate plan=baseMapper.selectOne(wrapper);
        GanttPlanTemplateModel model = new GanttPlanTemplateModel();
        if (plan!=null)
        {
            BeanUtils.copyProperties(plan, model);
            model.setGanttLinksTemplates(ganttLinksTemplateService.getLinksByPtId(plan.getPtId()));
            model.setGanttTasksTemplates(ganttTasksTemplateService.getGanttTasksTemplateTree(plan.getPtId()));
        }
        return BaseResponse.ok(model);
    }
}
