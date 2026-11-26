package com.jpmsoft.projectProducer.service.supervision;

import com.baomidou.mybatisplus.extension.service.IService;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.PunishRequest;
import com.jpmsoft.projectProducer.pojo.entity.supervision.Punish;

public interface IPunishService extends IService<Punish> {


    BaseResponse getPunishList(PunishRequest request, String uid);
}
