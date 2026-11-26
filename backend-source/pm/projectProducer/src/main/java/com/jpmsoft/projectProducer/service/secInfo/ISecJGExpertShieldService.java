package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertShieldDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertShield;

import java.util.List;

public interface ISecJGExpertShieldService extends IService<SecJGExpertShield> {

    void createOrUpdateSecExpertShield(List<SecJGExpertShieldDto> datas, String srId);


    void delSecExpertShieldBySrId(String srId);


}
