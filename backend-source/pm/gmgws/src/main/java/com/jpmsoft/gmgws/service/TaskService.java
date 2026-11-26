package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService extends IService<Task> {


    List<Task> queryNodes(List<String> flowIds);
}
