package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysRegionDTO;
import com.jpmsoft.userProducer.service.ISysRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/27 10:06
 */
@Slf4j
@RestController
@Api(tags = "地区(省市区镇)", value = "地区(省市区镇)")
@RequestMapping("/v1/region")
public class SysRegionController {

    @Resource
    private ISysRegionService sysRegionService;

    @ApiOperation(value = "新增修改地区")
    @PostMapping("/createOrUpdateSysRegion")
    public BaseResponse createOrUpdateSysRegion(@RequestBody SysRegionDTO request) {
        log.info("createOrUpdateSysRegion");
        return sysRegionService.createOrUpdateSysRegion(request);
    }

    @ApiOperation(value = "查询地区树")
    @GetMapping("/getSysRegionTree")
    public BaseResponse getSysRegionTree() {
        log.info("getSysRegionTree");
        return sysRegionService.getSysRegionTree();
    }

    @ApiOperation(value = "查询地区树(新)")
    @GetMapping("/getSysRegionTreeNew")
    public BaseResponse getSysRegionTreeNew() {
        log.info("getSysRegionTreeNew");
        return sysRegionService.getSysRegionTreeNew();
    }

    @ApiOperation(value = "查询地区树根据上级编码")
    @GetMapping("/getSysRegionByParentCode")
    public BaseResponse getSysRegionByParentCode(@RequestParam String parentCode, @RequestParam(required = false) Boolean exclude) {
        log.info("getSysRegionByParentCode");
        return sysRegionService.getSysRegionByParentCode(parentCode, exclude == null || exclude);
    }

    @ApiOperation(value = "id查询地区")
    @GetMapping("/getSysRegionById")
    public BaseResponse getSysRegionById(@RequestParam String id) {
        log.info("getSysRegionById");
        return sysRegionService.getSysRegionById(id);
    }

    @ApiOperation(value = "id删除地区（会自动删除下级）")
    @GetMapping("/deleteRegionById")
    public BaseResponse deleteRegionById(@RequestParam String id) {
        log.info("deleteRegionById");
        return sysRegionService.deleteRegionById(id);
    }

    @ApiOperation(value = "json文件导入数据")
    @PostMapping("/importRegion")
    public BaseResponse importRegion(@RequestParam MultipartFile file) {
        log.info("importRegion");
        return sysRegionService.importRegion(file);
    }
}
