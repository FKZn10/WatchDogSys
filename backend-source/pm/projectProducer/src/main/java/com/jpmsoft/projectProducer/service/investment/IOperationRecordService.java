package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.OperationRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface IOperationRecordService extends IService<OperationRecord> {

    void createRecord(String entityId, LogUserInfoVo user,String orType,String oldStatus,String newStatus);

    OperationRecord entity(String entityId, LogUserInfoVo user, String orType, String oldStatus, String newStatus, String orRemarks);

    BaseResponse getOperationRecordList(String entityId);
}
