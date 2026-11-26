package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.other.TaskProcessinMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.service.other.TaskProcessinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskProcessinServiceImpl extends ServiceImpl<TaskProcessinMapper, TaskProcessin> implements TaskProcessinService {
}
