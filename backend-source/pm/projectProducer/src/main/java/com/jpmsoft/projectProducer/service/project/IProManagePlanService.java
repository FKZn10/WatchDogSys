package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProManagePlanDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.ProManagePlan;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/18 10:21
 */
public interface IProManagePlanService extends IService<ProManagePlan> {

    BaseResponse createOrUpdateProManagePlan(ProManagePlanDTO request);

    BaseResponse getProManagePlanList(ProManagePlanDTO request,String uId);

    BaseResponse getProManagePlanById(String id);
}
