package com.jpmsoft.projectProducer.service.completion.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.completion.BuildingInfoSubMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.BuildingInfoSub;
import com.jpmsoft.projectProducer.service.completion.IBuildingInfoSubService;
import org.springframework.stereotype.Service;

@Service
public class BuildingInfoSubImpl extends ServiceImpl<BuildingInfoSubMapper, BuildingInfoSub> implements IBuildingInfoSubService {
}
