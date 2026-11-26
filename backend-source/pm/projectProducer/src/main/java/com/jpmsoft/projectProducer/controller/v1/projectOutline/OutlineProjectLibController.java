package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectLibRequest;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectLibService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 业务管理规划 - 项目管理大纲体系
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectOutline")
public class OutlineProjectLibController {
    @Resource
    private IOutlineProjectLibService outlineProjectLibService;

    /**
     * 获取大纲体系树形结构
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineProjectLibTree")
    public BaseResponse getOutlineProjectLibTree(@RequestBody OutlineProjectLibRequest request) {
        log.info("getOutlineProjectLibTree");
        return outlineProjectLibService.getOutlineProjectLibTree(request);
    }

    /**
     * 获取大纲体系详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getOutlineProjectLibById")
    public BaseResponse getOutlineProjectLibById(@RequestParam("id") String id) {
        log.info("getOutlineProjectLibById");
        return outlineProjectLibService.getOutlineProjectLibById(id);
    }

    /**
     * 创建或更新大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/createOrUpdateOutlineProjectLib")
    public BaseResponse createOrUpdateOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineProjectLib");
        return outlineProjectLibService.createOrUpdateOutlineProjectLib(request, token);
    }

    /**
     * 自动保存大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/autoSaveOutlineProjectLib")
    public BaseResponse autoSaveOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineProjectLib");
        return outlineProjectLibService.createOrUpdateOutlineProjectLib(request, token);
    }

    /**
     * 删除大纲体系
     *
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/deleteOutlineProjectLibById")
    public BaseResponse deleteOutlineProjectLibById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineProjectLibById");
        return outlineProjectLibService.deleteOutlineProjectLibById(id, token);
    }

    /**
     * 获取大纲体系列表
     *
     * @param opmId
     * @return
     */
    @GetMapping("/getOutlineProjectLibListByOpmId")
    public BaseResponse getOutlineProjectLibListByOpmId(@RequestParam("opmId") String opmId) {
        log.info("getOutlineProjectLibListByOpmId");
        return outlineProjectLibService.getOutlineProjectLibListByOpmId(opmId);
    }

    /**
     * 更改大纲体系目录排序
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/updateOutlineProjectLibSort")
    public BaseResponse updateOutlineProjectLibSort(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token) {
        log.info("updateOutlineProjectLibSort");
        return outlineProjectLibService.updateOutlineProjectLibSort(request, token);
    }

    /**
     * 获取项目对应的大纲体系树形结构
     *
     * @param pId
     * @return
     */
    @GetMapping("/getOutlineProjectLibListByPid")
    public BaseResponse getOutlineProjectLibListByPid(@RequestParam("pId") String pId, @RequestParam(value = "oplName", required = false) String oplName, @RequestParam(value = "opmCode", required = false) String opmCode) {
        log.info("getOutlineProjectLibListByPid");
        return outlineProjectLibService.getOutlineProjectLibListByPid(pId, oplName, opmCode);
    }

    /**
     * 查找或替换大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/searchOrReplaceOutlineProjectLib")
    public BaseResponse searchOrReplaceOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token) {
        log.info("searchOrReplaceOutlineProjectLib");
        return outlineProjectLibService.searchOrReplaceOutlineProjectLib(request, token);
    }

    /**
     * 校验当前内容是否包含其他项目
     *
     * @param opmId
     * @return
     */
    @GetMapping("/verifyProjectLibWithProjectName")
    public BaseResponse verifyProjectLibWithProjectName(@RequestParam("opmId") String opmId) {
        log.info("verifyProjectLibWithProjectName");
        return outlineProjectLibService.verifyProjectLibWithProjectName(opmId);
    }

    /**
     * 根据大纲体系id获取FileId
     *
     * @param oplId
     * @return
     */
    @GetMapping("/getFileIdByOplId")
    public BaseResponse getFileIdByOplId(@RequestParam("oplId") String oplId) {
        log.info("getFileIdByOplId");
        return outlineProjectLibService.getFileIdByOplId(oplId);
    }

    /**
     * 根据pId和tCode获取大纲体系id
     *
     * @param pId
     * @param tCode
     * @return
     */
    @GetMapping("/getOplIdByPidAndTCode")
    public BaseResponse getOplIdByPidAndTCode(@RequestParam("pId") String pId, @RequestParam("tCode") String tCode) {
        log.info("getOplIdByPidAndTCode");
        return outlineProjectLibService.getOplIdByPidAndTCode(pId, tCode);
    }

    /**
     * 检查大纲叶子节点是否为空
     *
     * @param opmId
     * @return
     */
    @GetMapping("/checkEmptyOutlineProjectLib")
    public BaseResponse checkEmptyOutlineProjectLib(@RequestParam("opmId") String opmId) {
        log.info("checkEmptyOutlineProjectLib");
        return outlineProjectLibService.checkEmptyOutlineProjectLib(opmId);
    }
}