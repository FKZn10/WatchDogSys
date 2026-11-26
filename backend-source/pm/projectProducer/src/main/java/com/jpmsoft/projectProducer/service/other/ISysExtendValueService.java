package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.other.SysExtendValue;

public interface ISysExtendValueService extends IService<SysExtendValue> {
    void deleteByFileId(String planId);
}
