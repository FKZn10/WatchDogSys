package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GanttLinksRequest;
import com.jpmsoft.common.model.response.Plan.GanttLinksResponse;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttLinks;

import java.util.List;


public interface IGanttLinksService extends IService<GanttLinks> {

    void createOrUpdateGanttLinks(List<GanttLinksRequest> ganttLinks, String planId, List<String> relDel);

    List<GanttLinksResponse> getLinksByPlanId(String planId);

    void deleteGanttLinksByPlanId(String planId);
}
