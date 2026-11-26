package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.jpbpm.mapper.SysCoordinationTaskBackupsMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysCoordinationTask;
import com.jpmsoft.jpbpm.pojo.entity.SysCoordinationTaskBackups;
import com.jpmsoft.jpbpm.service.SysCoordinationTaskBackupsService;
import com.jpmsoft.jpbpm.service.SysCoordinationTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysCoordinationTaskBackupsImpl extends ServiceImpl<SysCoordinationTaskBackupsMapper, SysCoordinationTaskBackups> implements SysCoordinationTaskBackupsService {

    @Autowired
    SysCoordinationTaskService sysCoordinationTaskService;
    @Override
    @Transactional
    public List<SysCoordinationTaskBackups> getSysCoordinationTaskBackupsByTaskId(List<String> taskId,String customerId){
        QueryWrapper<SysCoordinationTaskBackups> wrapper = new QueryWrapper<>();
        wrapper.in("CT_TaskID", taskId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        return  baseMapper.selectList(wrapper);

    }


    @Override
    @Transactional
    public   void  deleteSysCoordinationTaskBackupsByTaskId(List<String> taskId,String customerId){
        QueryWrapper<SysCoordinationTaskBackups> wrapper = new QueryWrapper<>();
        wrapper.in("CT_TaskID", taskId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
          baseMapper.delete(wrapper);
    }

    @Override
    @Transactional
    public   void  deleteSysCoordinationTaskBackupsByEntityId(String entityId,String customerId){
        QueryWrapper<SysCoordinationTaskBackups> wrapper = new QueryWrapper<>();
        wrapper.eq("CT_FileID", entityId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        baseMapper.delete(wrapper);
    }



    @Override
    @Transactional
    public   void  backups(List<String> taskId,String customerId){
        QueryWrapper<SysCoordinationTask> wrapper = new QueryWrapper<>();
        wrapper.in("CT_TaskID", taskId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<SysCoordinationTask>  list= sysCoordinationTaskService.list(wrapper);
        if(list.size()>0){
            for (SysCoordinationTask obj : list) {
                SysCoordinationTaskBackups newTask = new SysCoordinationTaskBackups();
                BeanUtils.copyProperties(obj, newTask);
                baseMapper.insert(newTask);
            }
        }
    }


    @Override
    @Transactional
    public   void  recoverySysCoordinationTask(List<String> taskId,String customerId){
        QueryWrapper<SysCoordinationTaskBackups> wrapper = new QueryWrapper<>();
        wrapper.in("CT_TaskID", taskId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<SysCoordinationTaskBackups>  list= baseMapper.selectList(wrapper);
        if(list.size()>0){
            for (SysCoordinationTaskBackups obj : list) {
                SysCoordinationTask newTask = new SysCoordinationTask();
                BeanUtils.copyProperties(obj, newTask);
                sysCoordinationTaskService.save(newTask);

            }
            List<String> ids=list.stream().map(n->n.getCtId()).collect(Collectors.toList());
            baseMapper.deleteBatchIds(ids);
        }
    }

}
