package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.sysTaskMassage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface sysTaskMassageMapper extends BaseMapper<sysTaskMassage> {
    @Delete("delete  from  Sys_Task_Massage  where task_Id not in (select TaskID from SYS_Task)")
    void deleteUselessData();

    void  insertSysTaskMassage(@Param("list") List<sysTaskMassage> list);
}
