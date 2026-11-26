package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.GanttTasks;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface GanttTasksService extends IService<GanttTasks> {

    void RecursionUpdateGanttTasksParent(String taskId, Date finishTime);
}
