package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.InvProjectPlan;
import com.jpmsoft.gmgws.pojo.entity.SysConfig;

import java.util.List;

public interface InvProjectPlanService  extends IService<InvProjectPlan> {

    List<InvProjectPlan>  getDataIdS(List<String> ids );
}
