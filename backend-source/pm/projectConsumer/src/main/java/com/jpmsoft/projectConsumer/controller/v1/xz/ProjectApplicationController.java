package com.jpmsoft.projectConsumer.controller.v1.xz;

import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.xz.ProjectApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.ProjectApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import com.jpmsoft.projectConsumer.feign.ProjectApplicationFeign;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 14:13
 * @Description:
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/v1/xz/projectApplication")
public class ProjectApplicationController {

    @Autowired
    private ProjectApplicationFeign projectApplicationFeign;

    @PostMapping("/page")
    public CommonResponse<PageResult<ProjectApplicationVO>> page(@RequestBody ProjectApplicationQueryRequest request){
        return projectApplicationFeign.page(request);
    }

    @PostMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, @RequestBody ProjectApplicationQueryRequest request) {
        Response feignResponse = projectApplicationFeign.exportExcel(request);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("ProjectApplicationExportExcel  error");
        }
    }

    @PostMapping("/create")
    public CommonResponse<String> create(@RequestBody ProjectApplicationCreateRequest request){
        return projectApplicationFeign.create(request);
    }

    @PostMapping("/update")
    public CommonResponse<String> update(@RequestBody ProjectApplicationCreateRequest request){
        return projectApplicationFeign.update(request);
    }

    @PostMapping("/detail")
    public CommonResponse<ProjectApplicationVO> detail(@RequestBody ProjectApplicationCreateRequest request){
        return projectApplicationFeign.detail(request);
    }

    @PostMapping("/syncApproveState")
    public CommonResponse<Void> syncApproveState(@RequestBody ProjectApplicationCreateRequest request) {
        projectApplicationFeign.syncApproveState(request);
        return CommonResponse.ok();
    }

    @PostMapping("/taskStatus")
    public CommonResponse<Void> taskStatus(@RequestBody ProjectApplicationCreateRequest request) {
        projectApplicationFeign.taskStatus(request);
        return CommonResponse.ok();
    }

    @PostMapping("/remove")
    public CommonResponse<Void> remove(@RequestBody ProjectApplicationCreateRequest request) {
        projectApplicationFeign.remove(request);
        return CommonResponse.ok();
    }
}
