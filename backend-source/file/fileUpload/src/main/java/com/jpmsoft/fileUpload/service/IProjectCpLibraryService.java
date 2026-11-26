package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.entity.ProjectCpLibrary;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/22 14:40
 */
public interface IProjectCpLibraryService extends IService<ProjectCpLibrary> {

    void exportProjectCpLibrary(HttpServletResponse response);

    BaseResponse importProjectCpLibrary(MultipartFile file, LogUserInfoVo user);
}
