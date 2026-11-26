package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.PushTypeEnum;
import com.jpmsoft.common.model.request.PushData.PushRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.mapper.other.PushRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.PushRecord;
import com.jpmsoft.projectProducer.service.other.IPushRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PushRecordImpl extends ServiceImpl<PushRecordMapper, PushRecord> implements IPushRecordService {

    @Override
    public void createPushRecord(PushRequest request, String key) {
        PushRecord entity = new PushRecord();
        entity.setPrTime(new Date());
        entity.setPrMsgId(request.getMsgId());
        entity.setPrRequest(JacksonUtils.toJSONString(request));
        entity.setPrDataId(key);
        entity.setPrState(0);
        entity.setPrType(PushTypeEnum.Project.getkey());
        entity.setPrId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
    }
}
