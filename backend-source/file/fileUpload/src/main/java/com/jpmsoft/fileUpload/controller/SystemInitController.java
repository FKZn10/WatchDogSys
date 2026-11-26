package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统初始化部分模块数据导入导出
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/22 15:57
 */
@Slf4j
@RestController
@Api(tags = "系统初始化部分模块数据导入导出", value = "系统初始化部分模块数据导入导出")
@RequestMapping("/systemInit")
public class SystemInitController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IQsTypeService qsTypeService;
    @Resource
    private IArchiveDirectoryService archiveDirectoryService;
    @Resource
    private IMeTypeLibraryService meTypeLibraryService;
    @Resource
    private IProjectCpLibraryService projectCpLibraryService;
    @Resource
    private IRiskPreventionMeasureService riskPreventionMeasureService;

    @ApiOperation(value = "导出风险点防控措施")
    @GetMapping("/exportRiskPreventionMeasure")
    public void exportRiskPreventionMeasure(HttpServletResponse response) {
        riskPreventionMeasureService.exportRiskPreventionMeasure(response);
    }

    @ApiOperation(value = "导入风险点防控措施")
    @PostMapping("/importRiskPreventionMeasure")
    public BaseResponse importRiskPreventionMeasure(MultipartFile file, @RequestHeader String token) {
        return riskPreventionMeasureService.importRiskPreventionMeasure(file, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "导出质量安全类别")
    @GetMapping("/exportQsType")
    public void exportQsType(HttpServletResponse response) {
        qsTypeService.exportQsType(response);
    }

    @ApiOperation(value = "导入质量安全类别")
    @PostMapping("/importQsType")
    public BaseResponse importQsType(MultipartFile file, @RequestHeader String token) {
        return qsTypeService.importQsType(file, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "导出档案目录")
    @GetMapping("/exportArchiveDirectory")
    public void exportArchiveDirectory(HttpServletResponse response) {
        archiveDirectoryService.exportArchiveDirectory(response);
    }

    @ApiOperation(value = "导入档案目录")
    @PostMapping("/importArchiveDirectory")
    public BaseResponse importArchiveDirectory(MultipartFile file, @RequestHeader String token) {
        return archiveDirectoryService.importArchiveDirectory(file, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "导出材料设备品种库")
    @GetMapping("/exportMeTypeLibrary")
    public void exportMeTypeLibrary(HttpServletResponse response) {
        meTypeLibraryService.exportMeTypeLibrary(response);
    }

    @ApiOperation(value = "导入材料设备品种库")
    @PostMapping("/importMeTypeLibrary")
    public BaseResponse importMeTypeLibrary(MultipartFile file, @RequestHeader String token) {
        return meTypeLibraryService.importMeTypeLibrary(file, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "导出项目建设程序库")
    @GetMapping("/exportProjectCpLibrary")
    public void exportProjectCpLibrary(HttpServletResponse response) {
        projectCpLibraryService.exportProjectCpLibrary(response);
    }

    @ApiOperation(value = "导入项目建设程序库")
    @PostMapping("/importProjectCpLibrary")
    public BaseResponse importProjectCpLibrary(MultipartFile file, @RequestHeader String token) {
        return projectCpLibraryService.importProjectCpLibrary(file, redisUtils.getUserInfoByToken(token));
    }

}
