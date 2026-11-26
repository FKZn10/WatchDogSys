package com.jpmsoft.userProducer.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.entity.DriveManager;
import com.jpmsoft.userProducer.service.IDriveManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "ukey驱动管理", value = "ukey驱动管理")
@RequestMapping("/v1/drive")
public class DriveManagerController {

    @Resource
    private IDriveManagerService driveManagerService;


    @ApiOperation(value = "新增修改")
    @PostMapping(value = "/createOrUpdateDriveManager")
    public BaseResponse createOrUpdateDriveManager(@RequestBody DriveManager request) {
        return driveManagerService.createOrUpdateDriveManager(request);
    }


    @ApiOperation(value = "id查询")
    @GetMapping(value = "/getDriveManagerById")
    public BaseResponse getDriveManagerById(@RequestParam String id) {
        return driveManagerService.getDriveManagerById(id);
    }


    @ApiOperation(value = "id删除")
    @GetMapping(value = "/deleteById")
    public BaseResponse deleteById(@RequestParam String id) {
        return driveManagerService.deleteById(id);
    }

    @ApiOperation(value = "分类查询驱动（全部启用的）")
    @GetMapping(value = "/getDriveDownload")
    public BaseResponse getDriveDownload() {
        return driveManagerService.getDriveDownload();
    }


    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/getDriveManagerList")
    public BaseResponse getDriveManagerList(@RequestBody DriveManager request) {
        return driveManagerService.getDriveManagerList(request);
    }

}
