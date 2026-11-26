package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.other.OuterUserPosition;

import java.util.List;

public interface IOuterUserPositionService extends IService<OuterUserPosition> {


    List<OuterUserPosition> selectByOuterId(String ourId);

}
