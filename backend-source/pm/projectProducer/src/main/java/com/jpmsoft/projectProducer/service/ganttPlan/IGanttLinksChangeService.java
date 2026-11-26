package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GanttLinksChangeDTO;
import com.jpmsoft.common.model.response.Plan.GanttLinksChangeVO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttLinksChange;

import java.util.List;
import java.util.Map;


public interface IGanttLinksChangeService extends IService<GanttLinksChange> {

    void createOrUpdateLinksChange(Map<String, String> keys, List<GanttLinksChangeDTO> ganttLinks,String planId);

    void deleteAllByPlanId(String planId);

    List<GanttLinksChangeVO> getGanttLinksChangeByPlanId(String planId);

}
