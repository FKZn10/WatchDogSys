package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jpmsoft.jpbpm.pojo.entity.SysCoordinationTaskBackups;

import java.util.List;

public interface SysCoordinationTaskBackupsService {

     List<SysCoordinationTaskBackups> getSysCoordinationTaskBackupsByTaskId(List<String> taskId,String customerId);

     void  deleteSysCoordinationTaskBackupsByTaskId(List<String> taskId,String customerId);

     void  deleteSysCoordinationTaskBackupsByEntityId(String entityId,String customerId);


    void  backups(List<String> taskId,String customerId);

    void  recoverySysCoordinationTask(List<String> taskId,String customerId);
}
