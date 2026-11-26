package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.other.SysMessage;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPlanTask;

import java.util.List;

public interface ISysMessageService extends IService<SysMessage> {

    void createMessage(List<QsPlanTask> tasks);
}
