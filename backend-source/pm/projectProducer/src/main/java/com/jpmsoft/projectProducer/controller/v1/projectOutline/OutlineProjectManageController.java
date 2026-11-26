package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectManageRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectManageService;
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
@RequestMapping("/v1/projectOutline")
public class OutlineProjectManageController {
    @Resource
    private IOutlineProjectManageService outlineProjectManageService;

    /**
     * 获取项目管理大纲管理分页
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineProjectManagePage")
    public BaseResponse getOutlineProjectManagePage(@RequestBody OutlineProjectManageRequest request) {
        log.info("getOutlineProjectManagePage");
        return outlineProjectManageService.getOutlineProjectManagePage(request);
    }

    /**
     * 获取项目管理详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getOutlineProjectManageById")
    public BaseResponse getOutlineProjectManageById(@RequestParam("id") String id) {
        log.info("getOutlineProjectManageById");
        return outlineProjectManageService.getOutlineProjectManageById(id);
    }

    /**
     * 获取项目管理详情
     *
     * @param id
     * @return
     */
    @GetMapping("/selectOutlineProjectManageById")
    public OutlineProjectManageResponse selectOutlineProjectManageById(@RequestParam("id") String id) {
        log.info("selectOutlineProjectManageById");
        return outlineProjectManageService.selectOutlineProjectManageById(id);
    }

    /**
     * 创建或更新项目管理
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/createOrUpdateOutlineProjectManage")
    public BaseResponse createOrUpdateOutlineProjectManage(@RequestBody OutlineProjectManageRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineProjectManage");
        return outlineProjectManageService.createOrUpdateOutlineProjectManage(request, token);
    }

    /**
     * 删除项目管理
     *
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/deleteOutlineProjectManageById")
    public BaseResponse deleteOutlineProjectManageById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineProjectManageById");
        return outlineProjectManageService.deleteOutlineProjectManageById(id, token);
    }

    /**
     * 批量更新项目管理状态
     *
     * @param request
     * @return
     */
    @PostMapping("/batchUpdateProjectManageStatus")
    public BaseResponse batchUpdateProjectManageStatus(@RequestBody OutlineProjectManageRequest request) {
        log.info("batchUpdateProjectManageStatus");
        return outlineProjectManageService.batchUpdateProjectManageStatus(request);
    }

    /**
     * 批量更新项目管理启用状态
     *
     * @param request
     * @return
     */
    @PostMapping("/batchUpdateProjectManageOpmStatus")
    public BaseResponse batchUpdateProjectManageOpmStatus(@RequestBody OutlineProjectManageRequest request) {
        log.info("batchUpdateProjectManageOpmStatus");
        return outlineProjectManageService.batchUpdateProjectManageOpmStatus(request);
    }

    /**
     * 将模板转化成项目管理大纲
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/coverSystemManageToProjectManage")
    public BaseResponse coverSystemManageToProjectManage(@RequestBody OutlineProjectManageRequest request, @RequestHeader String token) {
        log.info("coverSystemManageToProjectManage");
        return outlineProjectManageService.coverSystemManageToProjectManage(request, token);
    }

    /**
     * 获取项目对应的项目管理大纲
     *
     * @param pId
     * @param opmCode
     * @return
     */
    @GetMapping("/getOutlineProjectManageByPid")
    public BaseResponse getOutlineProjectManageByPid(@RequestParam("pId") String pId, @RequestParam(value = "opmCode", defaultValue = "01") String opmCode) {
        log.info("getOutlineProjectManageByPid");
        return outlineProjectManageService.getOutlineProjectManageByPid(pId, opmCode);
    }

    /**
     * 获取项目对应的项目管理大纲列表
     *
     * @param pId
     * @return
     */
    @GetMapping("/getOutlineProjectManageByPidList")
    public BaseResponse getOutlineProjectManageByPidList(@RequestParam("pId") String pId) {
        log.info("getOutlineProjectManageByPidList");
        return outlineProjectManageService.getOutlineProjectManageByPidList(pId);
    }

    /**
     * 获取前置功能未完善的模板
     *
     * @param opmId
     * @return
     */
    @GetMapping("/getPreTypeVerifyIds")
    public BaseResponse getPreTypeVerifyIds(@RequestParam("opmId") String opmId) {
        log.info("getPreTypeVerifyIds");
        return outlineProjectManageService.getPreTypeVerifyIds(opmId);
    }

    /**
     * 获取大纲台账统计
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineProjectLedgerCount")
    public BaseResponse getOutlineProjectLedgerCount(@RequestBody OutlineProjectManageRequest request) {
        log.info("getOutlineProjectLedgerCount");
        return outlineProjectManageService.getOutlineProjectLedgerCount(request);
    }

    /**
     * 获取大纲台账分页
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineProjectLedgerPage")
    public BaseResponse getOutlineProjectLedgerPage(@RequestBody OutlineProjectManageRequest request) {
        log.info("getOutlineProjectLedgerPage");
        return outlineProjectManageService.getOutlineProjectLedgerPage(request);
    }
}
