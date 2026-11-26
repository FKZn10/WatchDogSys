package com.jpmsoft.projectProducer.service.enghonorpunish;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngPunishDto;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngPunish;

public interface IEngPunishService extends IService<EngPunish> {


    BaseResponse getEngPunishList(EngPunishDto engPunishDto);

}
