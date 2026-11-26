package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.GanttTasksProcess;
import org.springframework.stereotype.Service;

@Service
public interface GanttTasksProcessService extends IService<GanttTasksProcess> {

    void  ganttTasksProcessToUpdateGanttTasks(String id);
}
