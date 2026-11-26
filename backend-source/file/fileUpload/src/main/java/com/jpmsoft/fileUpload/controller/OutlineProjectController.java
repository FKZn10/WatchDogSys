package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.fileUpload.service.IOutlineProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/v1/projectOutline")
@Api(value = "项目管理大纲", tags = "项目管理大纲")
public class OutlineProjectController {
    @Resource
    private IOutlineProjectService outlineProjectService;

    @ApiOperation(value = "导出项目管理大纲Word")
    @GetMapping("/exportOutlineProjectWord")
    public void exportOutlineProjectWord(HttpServletResponse response, @RequestParam("opmId") String opmId, @RequestParam(value = "export", defaultValue = "word") String export, @RequestHeader String token) {
        log.info("exportOutlineProjectWord");
        try {
            outlineProjectService.exportOutlineProjectWord(response, opmId, export, token);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new JPMException("管理大纲导出失败");
        }
    }

    @ApiOperation(value = "导入项目管理大纲Word")
    @PostMapping("/importOutlineProjectWord")
    public BaseResponse importOutlineProjectWord(@RequestParam("file") MultipartFile file, @RequestParam("osmId") String osmId, @RequestHeader String token) {
        log.info("importOutlineProjectWord");
        return outlineProjectService.importOutlineProjectWord(file, osmId, token);
    }
}
