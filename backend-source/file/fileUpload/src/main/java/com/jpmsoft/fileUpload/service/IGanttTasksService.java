package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.entity.GanttTasks;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IGanttTasksService extends IService<GanttTasks> {

    void exportGanttTaskFeedback(String planId, HttpServletResponse response);

    void exportGanttTaskFeedBackZip(String planId, HttpServletResponse response);

    BaseResponse importGanttTaskFeedback(MultipartFile file, LogUserInfoVo user);

    BaseResponse importGanttTaskFeedbackZip(MultipartFile file, LogUserInfoVo user) throws Exception;
}
