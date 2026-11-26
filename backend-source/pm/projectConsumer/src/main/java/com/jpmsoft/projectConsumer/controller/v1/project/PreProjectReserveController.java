package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import com.jpmsoft.projectConsumer.pojo.dto.project.PreProjectReserveDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 城建项目储备
 *
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:55
 */
@Slf4j
@RestController
@Api(tags = "项目储备", value = "项目储备")
@RequestMapping("/v1/projectReserve")
public class PreProjectReserveController {
    @Resource
    private PreFeign preFeign;

    @ApiOperation(value = "创建修改项目储备")
    @PostMapping("/createOrUpdateProjectReserve")
    public BaseResponse createOrUpdateProjectReserve(@RequestBody PreProjectReserveDTO request) {
        log.info("createOrUpdateProjectReserve");
        return preFeign.createOrUpdateProjectReserve(request);
    }

    @ApiOperation(value = "分页查询项目储备")
    @PostMapping("/getProjectReserveList")
    public BaseResponse getProjectReserveList(@RequestBody PreProjectReserveDTO request,@RequestHeader String token) {
        log.info("getProjectReserveList");
        return preFeign.getProjectReserveList(request,token);
    }

    @ApiOperation(value = "ID查询项目储备")
    @GetMapping("/getProjectReserveById")
    public BaseResponse getProjectReserveById(@RequestParam String id) {
        log.info("getProjectReserveById");
        return preFeign.getProjectReserveById(id);
    }

    @ApiOperation(value = "删除项目储备")
    @GetMapping("/deleteProjectReserveById")
    public BaseResponse deleteProjectReserveById(@RequestParam String id) {
        log.info("deleteProjectReserveById");
        return preFeign.deleteProjectReserveById(id);
    }

    @ApiOperation(value = "更新储备状态")
    @GetMapping("/updateReserveStatus")
    public BaseResponse updateReserveStatus(@RequestParam String id,@RequestParam String status,@RequestParam(required = false) String pId)
    {
        log.info("updateReserveStatus");
        return preFeign.updateReserveStatus(id,status,pId);
    }
    @ApiOperation(value = "多条件筛选")
    @PostMapping("/queryReserveListClassInfo")
    public BaseResponse queryReserveListClassInfo(@RequestBody PreProjectReserveDTO request,@RequestHeader String token) {
        log.info("queryReserveListClassInfo");
        return preFeign.queryReserveListClassInfo(request,token);
    }

    @ApiOperation(value = "ID查询历史记录")
    @GetMapping("/getProjectReserveDtlById")
    public  BaseResponse getProjectReserveDtlById(@RequestParam String id ){
        return preFeign.getProjectReserveDtlById(id);
    }

    @ApiOperation(value = "同步项目储备到项目")
    @GetMapping("/syncProjectReserveToProject")
    public BaseResponse syncProjectReserveToProject(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("syncProjectReserveToProject");
        return preFeign.syncProjectReserveToProject(id, token);
    }

    @ApiOperation(value = "将储备编码改为项目编码")
    @GetMapping("/changeReserveCodeToProjectCode")
    public BaseResponse changeReserveCodeToProjectCode(@RequestParam("pId") String pId, @RequestHeader String token) {
        log.info("changeReserveCodeToProjectCode");
        return preFeign.changeReserveCodeToProjectCode(pId, token);
    }

    /**
     * 在建设模式变更时刷新项目储备编码
     *
     * @param id
     * @param oldPprModel
     * @return
     */
    @ApiOperation(value = "在建设模式变更时刷新项目储备编码")
    @GetMapping("/refreshProjectReserveCode")
    public BaseResponse refreshProjectReserveCode(@RequestParam("id") String id, @RequestParam("oldPprModel") String oldPprModel) {
        log.info("refreshProjectReserveCode");
        return preFeign.refreshProjectReserveCode(id, oldPprModel);
    }
}
