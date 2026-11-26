package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GantSection;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/28 20:43
 */
public interface IGantSectionService extends IService<GantSection> {


    void createOrUpdateGantSection(List<GantSectionDTO> data,String planId);

    void createOrUpdateGantSection(List<GantSectionDTO> data, String planId, Map<String,String> taskGs);

    void deleteByPlanId(String planId);

    List<GantSectionDTO> selectByPlanId(String planId);

    List<GantSectionDTO> selectByPlanIds(List<String> planIds);

}
