package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProCplTemplateRequest;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCp;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCplTemplate;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasks;

import java.util.List;

public interface IProjectCplTemplateService extends IService<ProjectCplTemplate> {

    /**
     * 新增或者修改 建设程序项目模板
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateProCplTemplate(ProCplTemplateRequest request);

    /**
     * 获取集合
     *
     * @param request
     * @return
     */
    BaseResponse getProCplTemplateList(ProCplTemplateRequest request);

    /**
     * 删除
     *
     * @param cpltId
     * @return
     */
    BaseResponse deleteProCplTemplateById(String cpltId);

    void approved2GenerateProjectCplTemp(GanttPlan plan, List<GanttTasks> tasksList);

    void refreshProjectCplTempAntProjectCp(GanttPlan plan, List<GanttTasks> tasksList);

    BaseResponse generateProjectCpRpr(String pId, ProjectCp projectCp, FileTemplateTree cpTemp);
}
