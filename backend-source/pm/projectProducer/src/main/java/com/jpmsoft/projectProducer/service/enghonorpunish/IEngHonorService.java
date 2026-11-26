package com.jpmsoft.projectProducer.service.enghonorpunish;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngHonorDto;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngHonor;

public interface IEngHonorService extends IService<EngHonor> {


    BaseResponse getEngHonorList(EngHonorDto engHonorDto);

}
