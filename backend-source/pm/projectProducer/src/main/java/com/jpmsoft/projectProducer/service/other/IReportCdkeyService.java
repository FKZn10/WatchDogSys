package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.google.zxing.WriterException;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.other.ReportCdkey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/2 10:51
 */
public interface IReportCdkeyService extends IService<ReportCdkey> {

    void qrCode(String reportId, HttpServletRequest request, HttpServletResponse response) throws IOException, WriterException;

    BaseResponse checkPhone(String phone);

    BaseResponse  checkStudentNum(String studentNum);

    BaseResponse outMax();
}
