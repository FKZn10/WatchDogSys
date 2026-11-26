package com.jpmsoft.userProducer.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysTenantPayDTO;
import com.jpmsoft.userProducer.pojo.dto.SysToolTableRelationshipDto;
import com.jpmsoft.userProducer.pojo.entity.SysToolTableRelationship;
import com.jpmsoft.userProducer.service.SysTool.ISysToolTableRelationshipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "数据管理工具——数据删除关系映射", value = "数据管理工具——数据删除关系映射")
@RequestMapping("/v1/SysTool")
public class SysToolTableRelationshipController {
    @Resource
    ISysToolTableRelationshipService sysToolTableRelationshipService;


    @ApiOperation(value = "创建数据表映射关系")
    @PostMapping("/CreateOrUpdateOrDeleteSysToolTableRelationship")
    public BaseResponse CreateOrUpdateOrDeleteSysToolTableRelationship(@RequestBody SysToolTableRelationshipDto request) {
        log.info("CreateOrUpdateOrDeleteSysToolTableRelationship");
        return sysToolTableRelationshipService.CreateOrUpdateOrDeleteSysToolTableRelationship(request);
    }

    @ApiOperation(value = "表编码查询映射信息")
    @GetMapping("/GetSysToolTableRelationshi")
    public  BaseResponse GetSysToolTableRelationshi(@RequestParam String TableCode,@RequestParam String owner ){
        log.info("GetSysToolTableRelationshi");
        return  sysToolTableRelationshipService.GetSysToolTableRelationshi(TableCode,owner);
    }

    @ApiOperation(value = "同步表关系映射")
    @GetMapping("/SynchronizeForeignKeys")
    public  BaseResponse SynchronizeForeignKeys(@RequestParam String owner ){
        log.info("SynchronizeForeignKeys");
        return  sysToolTableRelationshipService.SynchronizeForeignKeys(owner);
    }

    @ApiOperation(value = "获取关系映射及数据条数")
    @GetMapping("/GetSysToolTableRelationshiAndData")
    public  BaseResponse GetSysToolTableRelationshiAndData( @RequestParam String TableCode,@RequestParam String pk,@RequestParam String id, @RequestParam String owner ){
        log.info("GetSysToolTableRelationshiAndData");
        return  sysToolTableRelationshipService.GetSysToolTableRelationshiAndData(TableCode,pk,id,owner);
    }

    @ApiOperation(value = "获取关系映射")
    @GetMapping("/GetSysToolTableRelationshiMap")
    public BaseResponse  GetSysToolTableRelationshiMap(@RequestParam String TableCode, @RequestParam String pk, @RequestParam String id, @RequestParam String owner){
        return  sysToolTableRelationshipService.GetSysToolTableRelationshiMap(TableCode,pk,id,owner);
    }
    @ApiOperation(value = "获取数据条数")
    @GetMapping("/GetSysToolTableRelationshiDataByPage")
    public BaseResponse GetSysToolTableRelationshiDataByPage(@RequestParam String TableCode,@RequestParam String pk, @RequestParam Integer page, @RequestParam String id, @RequestParam String owner){
        return  sysToolTableRelationshipService.GetSysToolTableRelationshiDataByPage(TableCode,pk,page,id,owner);
    }


    @ApiOperation(value = "查询关系数据")
    @GetMapping("/GetSysToolTableRelationshiData")
    public BaseResponse GetSysToolTableRelationshiData( @RequestParam String code, @RequestParam String owner){
        return  sysToolTableRelationshipService.GetSysToolTableRelationshiData(code,owner);
    }

    @ApiOperation(value = "导入关系数据")
    @PostMapping("/importSysToolTableRelationshi")
    public  BaseResponse importSysToolTableRelationshi(@RequestBody List<SysToolTableRelationship> data , @Validated String owner){
        return  sysToolTableRelationshipService.importSysToolTableRelationshi(data,owner);
    }
}
