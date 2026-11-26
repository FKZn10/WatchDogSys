package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskBackups;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysTaskBackupsService extends IService<SysTaskBackups> {
    List<SysTaskBackups> getListByTaskId(String taskId,String customerId);
    void deleteByTaskId(String taskId,String customerId);
}
