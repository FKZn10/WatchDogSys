package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.GanttTasksProcess;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/4/26 10:28
 */
public interface IGanttTasksProcessService extends IService<GanttTasksProcess> {

    List<GanttTasksProcess> getByTaskIds(List<String> taskIds);

    void create(List<GanttTasksProcess> gtpAdds);
}
