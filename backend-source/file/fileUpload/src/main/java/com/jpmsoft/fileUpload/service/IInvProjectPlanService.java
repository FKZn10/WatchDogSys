package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.GmInvProPlanDto;
import com.jpmsoft.fileUpload.pojo.entity.InvProjectPlan;

import java.util.List;

public interface IInvProjectPlanService extends IService<InvProjectPlan> {

    BaseResponse importInvProjectPlanByExcel(List<InvProjectPlan> datas, String uId);
    BaseResponse importGmInvProPlan(List<GmInvProPlanDto> datas);
}
