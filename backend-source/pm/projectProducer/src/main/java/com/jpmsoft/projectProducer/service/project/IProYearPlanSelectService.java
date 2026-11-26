package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.project.ProYearPlanSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProYearPlanSelect;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:13
 */
public interface IProYearPlanSelectService extends IService<ProYearPlanSelect> {

    void createOrUpdateProYearPlanSelect(List<ProYearPlanSelectDto> datas, String ycpId);

    void delProYearPlanSelectByPcyId(String ycpId);
}
