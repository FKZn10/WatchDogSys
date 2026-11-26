package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/2 11:23
 */
@Slf4j
@RestController
@RequestMapping("/v1/questionnaire")
public class ReportCdkeyController {

    @Resource
    private BaseFeign baseFeign;

    /**
     * 问卷报表二维码生成
     *
     * @param reportId 问卷报表ID
     */
    @ApiOperation(value = "获取领导看板总览")
    @GetMapping("/downloadFile/qrCode")
    public void qrCode(@RequestParam String reportId, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + GenerateUUID.getUUID() + ".png");
        response.getOutputStream().write(baseFeign.qrCode(reportId));
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @ApiOperation(value = "判断手机号是否已填报问卷")
    @GetMapping("/checkPhone")
    public BaseResponse checkPhone(@RequestParam String phone) {
        return baseFeign.checkPhone(phone);
    }


    @ApiOperation(value = "判断学号是否已填报问卷")
    @GetMapping("/checkStudentNum")
    public BaseResponse checkStudentNum(@RequestParam String studentNum) {
        return baseFeign.checkStudentNum(studentNum);
    }

    @ApiOperation(value = "是否超出问卷最大限制")
    @GetMapping("/outMax")
    public BaseResponse outMax() {
        return baseFeign.outMax();
    }
}
