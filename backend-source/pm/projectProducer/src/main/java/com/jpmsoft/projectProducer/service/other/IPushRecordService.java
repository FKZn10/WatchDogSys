package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.PushData.PushRequest;
import com.jpmsoft.projectProducer.pojo.entity.other.PushRecord;

public interface IPushRecordService extends IService<PushRecord> {

    /**
     * 推送项目信息创建推送记录
     *
     * @param request
     * @param key
     */
    void createPushRecord(PushRequest request, String key);
}
