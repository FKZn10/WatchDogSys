package com.jpmsoft.fileUpload.controller;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.fileUpload.service.CustomImportService;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 北京219自定义导入excel模板导入 控制器
 */
@RestController
@Api(tags = "自定义导入", value = "自定义导入")
@RequestMapping("/customImport")
public class CustomImportController {

    @Resource
    private CustomImportService customImportService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/importCustomData")
    public BaseResponse importFormData(@RequestPart("files") MultipartFile[] files, @RequestHeader String token) {
        LogUserInfoVo user = new LogUserInfoVo();
        BeanUtils.copyProperties(redisUtils.getUserInfoByToken(token), user);
        return customImportService.importData(files, user);
    }

    @PostMapping("/importComplaintData")
    public BaseResponse importComplaintData(@RequestPart("file") MultipartFile file, @RequestHeader String token) {
        LogUserInfoVo user = new LogUserInfoVo();
        BeanUtils.copyProperties(redisUtils.getUserInfoByToken(token), user);
        return customImportService.importComplaintData(file, user);
    }

    @PostMapping("/importLawsuitData")
    public BaseResponse importLawsuitData(@RequestPart("file") MultipartFile file, @RequestHeader String token) {
        LogUserInfoVo user = new LogUserInfoVo();
        BeanUtils.copyProperties(redisUtils.getUserInfoByToken(token), user);
        return customImportService.importLawsuitData(file, user);
    }

    @ApiOperation(value = "省试点项目补充导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "Excel 文件（省试点项目导入模板）", required = true, paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/importProvPilotProject")
    public BaseResponse importProvPilotProject(@RequestPart("file") MultipartFile file, @RequestHeader String token) {
        LogUserInfoVo user = new LogUserInfoVo();
        BeanUtils.copyProperties(redisUtils.getUserInfoByToken(token), user);
        return customImportService.importProvPilotProject(file, user, token);
    }

    @ApiOperation(value = "省试点企业补充导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "Excel 文件（省试点企业导入模板）", required = true, paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/importProvPilotEnterprise")
    public BaseResponse importProvPilotEnterprise(@RequestPart("file") MultipartFile file, @RequestHeader String token) {
        LogUserInfoVo user = new LogUserInfoVo();
        BeanUtils.copyProperties(redisUtils.getUserInfoByToken(token), user);
        return customImportService.importProvPilotEnterprise(file, user, token);
    }

}
