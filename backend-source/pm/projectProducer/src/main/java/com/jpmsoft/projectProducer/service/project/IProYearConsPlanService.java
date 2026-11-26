package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProYearConsPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProYearConsPlan;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:13
 */
public interface IProYearConsPlanService extends IService<ProYearConsPlan> {

    BaseResponse createOrUpdateProYearConsPlan(ProYearConsPlanDto request);

    BaseResponse getProYearConsPlanList(ProYearConsPlanDto request);

    BaseResponse getProYearConsPlanById(String id);
}
