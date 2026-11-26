package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessUser;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessin;
import com.jpmsoft.jpbpm.pojo.vo.SysTaskProcessUserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskProcessUserService extends IService<TaskProcessUser> {




    TaskProcessUser getTaskProcessUserById(String id,String tCode,String year);

    List<TaskProcessUser>  getListByIds(List<String> ids,String tCode,String year);

    List<TaskProcessUser> getList(QueryWrapper<TaskProcessUser> queryWrapper,String tCode,String year);

    List<TaskProcessUser> getList(LambdaQueryWrapper<TaskProcessUser> queryWrapper,String tCode,String year);
    List<TaskProcessUser> getList(QueryWrapper<TaskProcessUser> queryWrapper,List<String> tCode,String year);

    List<TaskProcessUser> getList(LambdaQueryWrapper<TaskProcessUser> queryWrapper,List<String> tCode,String year);
    int insertTaskProcessUser(TaskProcessUser taskProcessUser,String tCode,String year);
    boolean  updateTaskProcessUserById(TaskProcessUser obj,String tCode,String year);

    boolean saveTaskProcessUserBatch(List<TaskProcessUser>  list,String tCode,String year);

    void GetNewTaskProcessUse(String processid, String taskuser,String tpusendactivityid,String customerId,String tCode,String year);

    List<TaskProcessUser> GetTaskProcessUser(List<String> tpidlist,String customerId,String tCode,String year);


    List<SysTaskProcessUserVo> GetTaskProcessUserInfo(List<String> tpidlist,String customerId,String tCode,String year);
}
