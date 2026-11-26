package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.service.IOutlineDatasourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/OutlineDatasource")
@Api(tags = "大纲数据源配置", value = "大纲数据源配置")
public class OutlineDatasourceController {
    @Resource
    private IOutlineDatasourceService outlineDatasourceService;

    /**
     * 导出大纲数据源配置
     *
     * @param response
     * @param ids
     */
    @ApiOperation("导出大纲数据源配置")
    @PostMapping("/exportOutlineDatasourceByIds")
    public void exportOutlineDatasourceByIds(HttpServletResponse response, @RequestBody List<String> ids) {
        log.info("exportOutlineDatasourceByIds");
        outlineDatasourceService.exportOutlineDatasourceByIds(response, ids);
    }

    /**
     * 导入大纲数据源配置
     *
     * @param file
     * @param token
     * @return
     */
    @ApiOperation("导入大纲数据源配置")
    @PostMapping("/importOutlineDatasource")
    public BaseResponse importOutlineDatasource(MultipartFile file, @RequestHeader String token) {
        log.info("importOutlineDatasource");
        return outlineDatasourceService.importOutlineDatasource(file, token);
    }
}
