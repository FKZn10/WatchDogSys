package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysTaskBackupsMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskBackups;
import com.jpmsoft.jpbpm.service.SysTaskBackupsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SysTaskBackupsImpl extends ServiceImpl<SysTaskBackupsMapper, SysTaskBackups> implements SysTaskBackupsService {
    @Override
    public List<SysTaskBackups> getListByTaskId(String taskId,String customerId){
        QueryWrapper<SysTaskBackups> taskBackups = new QueryWrapper<>();
        taskBackups.eq("Task_Id_New", taskId);
        if(!"All".equals(customerId) ){
            taskBackups.eq("Te_Id", customerId);
        }
        return  baseMapper.selectList(taskBackups);
    }

    @Override
    public void deleteByTaskId(String taskId,String customerId){
        QueryWrapper<SysTaskBackups> taskBackups = new QueryWrapper<>();
        taskBackups.eq("Task_Id_New", taskId);
        if(!"All".equals(customerId) ){
            taskBackups.eq("Te_Id", customerId);
        }
        baseMapper.delete(taskBackups);
    }
}
