package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemManageRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 业务管理规划 - 体系模板
 *
 * @author tianhelin
 * @date 2025/6/10
 */
@Slf4j
@RestController
@Api(value = "业务管理规划-体系模板", tags = "业务管理规划-体系模板")
@RequestMapping("/v1/projectOutline")
public class OutlineSystemManageController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 获取体系模板分页
     *
     * @param request
     * @return
     */
    @ApiOperation("获取体系模板分页")
    @PostMapping("/getOutlineSystemManagePage")
    public BaseResponse getOutlineSystemManagePage(@RequestBody OutlineSystemManageRequest request) {
        log.info("getOutlineSystemManagePage");
        return outlineFeign.getOutlineSystemManagePage(request);
    }

    /**
     * 获取体系模板详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取体系模板详情")
    @GetMapping("/getOutlineSystemManageById")
    public BaseResponse getOutlineSystemManageById(@RequestParam("id") String id) {
        log.info("getOutlineSystemManageById");
        return outlineFeign.getOutlineSystemManageById(id);
    }

    /**
     * 创建或更新体系模板
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("创建或更新体系模板")
    @PostMapping("/createOrUpdateOutlineSystemManage")
    public BaseResponse createOrUpdateOutlineSystemManage(@RequestBody OutlineSystemManageRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineSystemManage");
        return outlineFeign.createOrUpdateOutlineSystemManage(request, token);
    }

    /**
     * 删除体系模板
     *
     * @param request
     * @return
     */
    @ApiOperation("删除体系模板")
    @PostMapping("/deleteOutlineSystemManageById")
    public BaseResponse deleteOutlineSystemManageById(@RequestBody OutlineSystemManageRequest request) {
        log.info("deleteOutlineSystemManageById");
        return outlineFeign.deleteOutlineSystemManageById(request);
    }

    /**
     * 批量更新体系模板状态
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("批量更新体系模板状态")
    @PostMapping("/batchUpdateSystemManageStatus")
    public BaseResponse batchUpdateSystemManageStatus(@RequestBody OutlineSystemManageRequest request, @RequestHeader String token) {
        log.info("batchUpdateSystemManageStatus");
        return outlineFeign.batchUpdateSystemManageStatus(request, token);
    }

    /**
     * 复制体系模板
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("复制体系模板")
    @GetMapping("/copyOutlineSystemManageById")
    public BaseResponse copyOutlineSystemManageById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("copyOutlineSystemManageById");
        return outlineFeign.copyOutlineSystemManageById(id, token);
    }
}
