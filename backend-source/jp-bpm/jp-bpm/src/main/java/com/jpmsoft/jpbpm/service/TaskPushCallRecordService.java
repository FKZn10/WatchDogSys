package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.TaskPushCallRecordDto;
import com.jpmsoft.jpbpm.pojo.entity.TaskPushCallRecord;
import org.springframework.stereotype.Service;

@Service
public interface TaskPushCallRecordService  extends IService<TaskPushCallRecord> {
    Integer createTaskPushCallRecord(String center, String state,String customerId);

    BaseResponse getTaskPushCallRecordList(TaskPushCallRecordDto request,String customerId);

    BaseResponse pushTodoTask(String id,String customerId,String  token);
}
