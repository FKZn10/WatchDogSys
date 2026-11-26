package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.PushData.PushRecordModel;
import com.jpmsoft.userProducer.pojo.entity.PushRecord;


public interface IPushRecordService extends IService<PushRecord> {

    BaseResponse getPushRecordList(PushRecordModel request);

    BaseResponse rePushDataAgain(String prId);

}
