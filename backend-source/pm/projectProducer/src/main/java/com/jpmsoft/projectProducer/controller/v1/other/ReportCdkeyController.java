package com.jpmsoft.projectProducer.controller.v1.other;

import com.google.zxing.WriterException;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.other.IReportCdkeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/2 11:23
 */
@Slf4j
@RestController
@RequestMapping("/v1/questionnaire/")
public class ReportCdkeyController {

    @Resource
    private IReportCdkeyService reportCdkeyService;

    /**
     * 问卷报表二维码生成
     *
     * @param reportId 问卷报表ID
     */
    @GetMapping("/downloadFile/qrCode")
    public void qrCode(@RequestParam String reportId, HttpServletRequest request, HttpServletResponse response) throws IOException, WriterException {
        reportCdkeyService.qrCode(reportId, request, response);
    }


    @GetMapping("/checkPhone")
    public   BaseResponse checkPhone(@RequestParam String phone){
        return reportCdkeyService.checkPhone(phone);
    }


    @GetMapping("/checkStudentNum")
    public   BaseResponse checkStudentNum(@RequestParam String studentNum){
        return reportCdkeyService.checkStudentNum(studentNum);
    }

    @GetMapping("/outMax")
    public   BaseResponse outMax(){
        return reportCdkeyService.outMax();
    }
}
