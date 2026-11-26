package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GanttLinksTemplateDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttLinksTemplate;

import java.util.List;


public interface IGanttLinksTemplateService extends IService<GanttLinksTemplate> {
    void createOrUpdateGanttLinks(List<GanttLinksTemplateDTO> links, String ptId);


    List<GanttLinksTemplateDTO> getLinksByPtId(String ptId);
}
