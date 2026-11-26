package com.jpmsoft.gmgws.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.TaskMapper;
import com.jpmsoft.gmgws.pojo.entity.Task;
import com.jpmsoft.gmgws.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class TaskImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {



    @Override
    public List<Task> queryNodes(List<String> flowIds) {
        return baseMapper.queryNodes(flowIds);
    }
}


