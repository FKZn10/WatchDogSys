package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertSelect;

public interface ISecJGExpertSelectService extends IService<SecJGExpertSelect> {

    void updateState(String id, Integer state);
}
