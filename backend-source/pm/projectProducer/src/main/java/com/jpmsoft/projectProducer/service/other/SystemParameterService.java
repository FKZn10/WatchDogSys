package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.other.SystemParameter;

public interface SystemParameterService extends IService<SystemParameter> {
    SystemParameter getData();
}
