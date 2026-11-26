package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jpmsoft.fileUpload.mapper.TaskProcessinMapper;
import com.jpmsoft.fileUpload.pojo.entity.TaskProcessin;
import com.jpmsoft.fileUpload.service.ITaskProcessinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TaskProcessinImpl extends ServiceImpl<TaskProcessinMapper, TaskProcessin> implements ITaskProcessinService {

}
