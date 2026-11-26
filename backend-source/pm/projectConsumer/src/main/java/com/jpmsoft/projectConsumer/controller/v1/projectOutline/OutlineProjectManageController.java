package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectManageRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 业务管理规划 - 项目管理
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@Slf4j
@RestController
@Api(value = "业务管理规划-项目管理", tags = "业务管理规划-项目管理")
@RequestMapping("/v1/projectOutline")
public class OutlineProjectManageController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 获取项目管理大纲管理分页
     *
     * @param request
     * @return
     */
    @ApiOperation("获取项目管理大纲管理分页")
    @PostMapping("/getOutlineProjectManagePage")
    public BaseResponse getOutlineProjectManagePage(@RequestBody OutlineProjectManageRequest request) {
        log.info("getOutlineProjectManagePage");
        return outlineFeign.getOutlineProjectManagePage(request);
    }

    /**
     * 获取项目管理详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取项目管理大纲管理详情")
    @GetMapping("/getOutlineProjectManageById")
    public BaseResponse getOutlineProjectManageById(@RequestParam("id") String id) {
        log.info("getOutlineProjectManageById");
        return outlineFeign.getOutlineProjectManageById(id);
    }

    /**
     * 获取项目管理详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取项目管理大纲管理详情（内部调用）")
    @GetMapping("/selectOutlineProjectManageById")
    public OutlineProjectManageResponse selectOutlineProjectManageById(@RequestParam("id") String id) {
        log.info("selectOutlineProjectManageById");
        return outlineFeign.selectOutlineProjectManageById(id);
    }

    /**
     * 创建或更新项目管理
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("创建或更新项目管理大纲管理")
    @PostMapping("/createOrUpdateOutlineProjectManage")
    public BaseResponse createOrUpdateOutlineProjectManage(@RequestBody OutlineProjectManageRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineProjectManage");
        return outlineFeign.createOrUpdateOutlineProjectManage(request, token);
    }

    /**
     * 删除项目管理
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("删除项目管理大纲管理")
    @GetMapping("/deleteOutlineProjectManageById")
    public BaseResponse deleteOutlineProjectManageById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineProjectManageById");
        return outlineFeign.deleteOutlineProjectManageById(id, token);
    }

    /**
     * 批量更新项目管理状态
     *
     * @param request
     * @return
     */
    @ApiOperation("批量更新项目管理大纲管理状态")
    @PostMapping("/batchUpdateProjectManageStatus")
    public BaseResponse batchUpdateProjectManageStatus(@RequestBody OutlineProjectManageRequest request) {
        log.info("batchUpdateProjectManageStatus");
        return outlineFeign.batchUpdateProjectManageStatus(request);
    }

    /**
     * 批量更新项目管理启用状态
     *
     * @param request
     * @return
     */
    @ApiOperation("批量更新项目管理大纲管理启用状态")
    @PostMapping("/batchUpdateProjectManageOpmStatus")
    public BaseResponse batchUpdateProjectManageOpmStatus(@RequestBody OutlineProjectManageRequest request) {
        log.info("batchUpdateProjectManageOpmStatus");
        return outlineFeign.batchUpdateProjectManageOpmStatus(request);
    }

    /**
     * 将模板转化成项目管理大纲
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("将模板转化成项目管理大纲")
    @PostMapping("/coverSystemManageToProjectManage")
    public BaseResponse coverSystemManageToProjectManage(@RequestBody OutlineProjectManageRequest request, @RequestHeader String token) {
        log.info("coverSystemManageToProjectManage");
        return outlineFeign.coverSystemManageToProjectManage(request, token);
    }

    /**
     * 获取项目对应的项目管理大纲
     *
     * @param pId
     * @param opmCode
     * @return
     */
    @ApiOperation("获取项目对应的项目管理大纲")
    @GetMapping("/getOutlineProjectManageByPid")
    public BaseResponse getOutlineProjectManageByPid(@RequestParam("pId") String pId, @RequestParam(value = "opmCode", defaultValue = "01") String opmCode) {
        log.info("getOutlineProjectManageByPid");
        return outlineFeign.getOutlineProjectManageByPid(pId, opmCode);
    }

    /**
     * 获取项目对应的项目管理大纲列表
     *
     * @param pId
     * @return
     */
    @ApiOperation("获取项目对应的项目管理大纲列表")
    @GetMapping("/getOutlineProjectManageByPidList")
    public BaseResponse getOutlineProjectManageByPidList(@RequestParam("pId") String pId) {
        log.info("getOutlineProjectManageByPidList");
        return outlineFeign.getOutlineProjectManageByPidList(pId);
    }

    /**
     * 获取前置功能未完善的模板
     *
     * @param opmId
     * @return
     */
    @ApiOperation("获取前置功能未完善的模板")
    @GetMapping("/getPreTypeVerifyIds")
    public BaseResponse getPreTypeVerifyIds(@RequestParam("opmId") String opmId) {
        log.info("getPreTypeVerifyIds");
        return outlineFeign.getPreTypeVerifyIds(opmId);
    }

    /**
     * 获取大纲台账统计
     *
     * @param request
     * @return
     */
    @ApiOperation("获取大纲台账统计")
    @PostMapping("/getOutlineProjectLedgerCount")
    public BaseResponse getOutlineProjectLedgerCount(@RequestBody OutlineProjectManageRequest request) {
        log.info("getOutlineProjectLedgerCount");
        return outlineFeign.getOutlineProjectLedgerCount(request);
    }

    /**
     * 获取大纲台账分页
     *
     * @param request
     * @return
     */
    @ApiOperation("获取大纲台账分页")
    @PostMapping("/getOutlineProjectLedgerPage")
    public BaseResponse getOutlineProjectLedgerPage(@RequestBody OutlineProjectManageRequest request) {
        log.info("getOutlineProjectLedgerPage");
        return outlineFeign.getOutlineProjectLedgerPage(request);
    }
}
