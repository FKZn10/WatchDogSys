package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.fileUpload.service.ISysTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/22 11:45
 */
@Slf4j
@RestController
@Api(tags = "数据库表信息", value = "数据库表信息")
@RequestMapping("/tb")
public class SysTableController {

    @Resource
    private ISysTableService sysTableService;

    @ApiOperation(value = "导出XML")
    @PostMapping("/exxm")
    public void exxm(HttpServletResponse response, @RequestBody(required = false) List<String> tableCodes) {
        sysTableService.exportTableXml(response, tableCodes);
    }

    @ApiOperation(value = "XML导入")
    @PostMapping("/imxm")
    public void exxm(MultipartFile file) throws JAXBException {
        sysTableService.importTableXml(file);
    }

}
