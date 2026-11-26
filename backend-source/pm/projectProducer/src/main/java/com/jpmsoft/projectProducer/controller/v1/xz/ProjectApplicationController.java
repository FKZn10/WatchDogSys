package com.jpmsoft.projectProducer.controller.v1.xz;

import com.jpmsoft.common.model.request.xz.ProjectApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.ProjectApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.service.xz.ProjectApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 14:13
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/xz/projectApplication")
@Slf4j
public class ProjectApplicationController {

    @Autowired
    private ProjectApplicationService projectApplicationService;

    @PostMapping("/page")
    public CommonResponse<PageResult<ProjectApplicationVO>> page(@RequestBody ProjectApplicationQueryRequest request){
        return CommonResponse.ok(projectApplicationService.page(request));
    }

    @PostMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response,
                                                @RequestBody ProjectApplicationQueryRequest request) {
        try {
            Workbook workbook = projectApplicationService.exportExcel(request);
            String fileName = URLEncoder.encode("项目申报.xls","UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("项目申报导出EXCEL导出出错{}",e.getMessage(),e);
        }
    }

    @PostMapping("/create")
    public CommonResponse<String> create(@RequestBody ProjectApplicationCreateRequest request){
        return CommonResponse.ok(projectApplicationService.create(request));
    }

    @PostMapping("/update")
    public CommonResponse<String> update(@RequestBody ProjectApplicationCreateRequest request){
        return CommonResponse.ok(projectApplicationService.update(request));
    }

    @PostMapping("/detail")
    public CommonResponse<ProjectApplicationVO> detail(@RequestBody ProjectApplicationCreateRequest request){
        return CommonResponse.ok(projectApplicationService.detail(request));
    }

    @PostMapping("/syncApproveState")
    public CommonResponse<Void> syncApproveState(@RequestBody ProjectApplicationCreateRequest request) {
        projectApplicationService.syncApproveState(request);
        return CommonResponse.ok();
    }

    @PostMapping("/taskStatus")
    public CommonResponse<Void> taskStatus(@RequestBody ProjectApplicationCreateRequest request) {
        projectApplicationService.taskStatus(request);
        return CommonResponse.ok();
    }

    @PostMapping("/remove")
    public CommonResponse<Void> remove(@RequestBody ProjectApplicationCreateRequest request, @RequestHeader(value = "token", required = false) String token) {
        projectApplicationService.remove(request, token);
        return CommonResponse.ok();
    }
}
