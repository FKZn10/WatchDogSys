package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.entity.RiskPreventionMeasure;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 8:52
 */
public interface IRiskPreventionMeasureService extends IService<RiskPreventionMeasure> {

    void exportRiskPreventionMeasure(HttpServletResponse response);

    BaseResponse importRiskPreventionMeasure(MultipartFile file, LogUserInfoVo user);
}
