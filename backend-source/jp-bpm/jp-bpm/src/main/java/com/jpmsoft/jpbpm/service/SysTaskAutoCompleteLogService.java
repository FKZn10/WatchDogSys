package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskAutoCompleteLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysTaskAutoCompleteLogService  extends IService<SysTaskAutoCompleteLog> {

    BaseResponse getTaskAutoCompleteLogByTpIds(List<String> tpIds,String customerId);
    BaseResponse getTaskAutoCompleteLogByTaskIds(List<String> TaskIds,String customerId);
    BaseResponse deleteTaskAutoCompleteLogByTaskId(String id,String customerId);
}
