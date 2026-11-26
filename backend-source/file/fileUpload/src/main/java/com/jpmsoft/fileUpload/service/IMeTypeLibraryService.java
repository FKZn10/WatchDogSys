package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.entity.MeTypeLibrary;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/22 15:32
 */
public interface IMeTypeLibraryService extends IService<MeTypeLibrary> {

    void exportMeTypeLibrary(HttpServletResponse response);

    BaseResponse importMeTypeLibrary(MultipartFile file, LogUserInfoVo user);
}
