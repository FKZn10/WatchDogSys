package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.entity.QsType;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/22 15:41
 */
public interface IQsTypeService extends IService<QsType> {

    void exportQsType(HttpServletResponse response);

    BaseResponse importQsType(MultipartFile file, LogUserInfoVo user);
}
