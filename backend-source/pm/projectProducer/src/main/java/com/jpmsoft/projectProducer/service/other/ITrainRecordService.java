package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.other.ApplyRole;
import com.jpmsoft.projectProducer.pojo.entity.other.TrainStaff;

public interface ITrainRecordService {


    BaseResponse getTrainRecord(TrainStaff trainStaff);
}
