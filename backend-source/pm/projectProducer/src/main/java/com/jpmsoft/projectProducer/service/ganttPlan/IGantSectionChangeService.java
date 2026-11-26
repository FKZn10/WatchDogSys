package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSection;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSectionChange;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/28 20:43
 */
public interface IGantSectionChangeService extends IService<GantSectionChange> {

    void createOrUpdateGantSection(List<GantSectionDTO> data,String planId);

    void deleteByPlanId(String planId);

    List<GantSectionDTO> selectByPlanId(String planId);

    List<GantSectionDTO> selectByPlanIds(List<String> planIds);

}
