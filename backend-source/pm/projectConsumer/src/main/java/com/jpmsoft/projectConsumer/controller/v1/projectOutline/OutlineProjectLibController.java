package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectLibRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "业务管理规划-项目管理大纲体系", tags = "业务管理规划-项目管理大纲体系")
@RequestMapping("/v1/projectOutline")
public class OutlineProjectLibController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 获取大纲体系树形结构
     *
     * @param request
     * @return
     */
    @ApiOperation("获取大纲体系树形结构")
    @PostMapping("/getOutlineProjectLibTree")
    public BaseResponse getOutlineProjectLibTree(@RequestBody OutlineProjectLibRequest request) {
        log.info("getOutlineProjectLibTree");
        return outlineFeign.getOutlineProjectLibTree(request);
    }

    /**
     * 获取大纲体系详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取大纲体系详情")
    @GetMapping("/getOutlineProjectLibById")
    public BaseResponse getOutlineProjectLibById(@RequestParam("id") String id) {
        log.info("getOutlineProjectLibById");
        return outlineFeign.getOutlineProjectLibById(id);
    }

    /**
     * 创建或更新大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("创建或更新大纲体系")
    @PostMapping("/createOrUpdateOutlineProjectLib")
    public BaseResponse createOrUpdateOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineProjectLib");
        return outlineFeign.createOrUpdateOutlineProjectLib(request, token);
    }

    /**
     * 自动保存大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("自动保存大纲体系")
    @PostMapping("/autoSaveOutlineProjectLib")
    public BaseResponse autoSaveOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineProjectLib");
        return outlineFeign.autoSaveOutlineProjectLib(request, token);
    }

    /**
     * 删除大纲体系
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("删除大纲体系")
    @GetMapping("/deleteOutlineProjectLibById")
    public BaseResponse deleteOutlineProjectLibById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineProjectLibById");
        return outlineFeign.deleteOutlineProjectLibById(id, token);
    }

    /**
     * 获取大纲体系列表
     *
     * @param opmId
     * @return
     */
    @ApiOperation("获取大纲体系列表")
    @GetMapping("/getOutlineProjectLibListByOpmId")
    public BaseResponse getOutlineProjectLibListByOpmId(@RequestParam("opmId") String opmId) {
        log.info("getOutlineProjectLibListByOpmId");
        return outlineFeign.getOutlineProjectLibListByOpmId(opmId);
    }

    /**
     * 更新目录排序
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("更新目录排序")
    @PostMapping("/updateOutlineProjectLibSort")
    public BaseResponse updateOutlineProjectLibSort(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token) {
        log.info("updateOutlineProjectLibSort");
        return outlineFeign.updateOutlineProjectLibSort(request, token);
    }

    /**
     * 获取项目对应的大纲体系树形结构
     *
     * @param pId
     * @return
     */
    @ApiOperation("获取项目对应的大纲体系树形结构")
    @GetMapping("/getOutlineProjectLibListByPid")
    public BaseResponse getOutlineProjectLibListByPid(@RequestParam("pId") String pId, @RequestParam(value = "oplName", required = false) String oplName, @RequestParam(value = "opmCode", required = false) String opmCode) {
        log.info("getOutlineProjectLibListByPid");
        return outlineFeign.getOutlineProjectLibListByPid(pId, oplName, opmCode);
    }

    /**
     * 查找或替换大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("查找或替换大纲体系")
    @PostMapping("searchOrReplaceOutlineProjectLib")
    public BaseResponse searchOrReplaceOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token) {
        log.info("searchOrReplaceOutlineProjectLib");
        return outlineFeign.searchOrReplaceOutlineProjectLib(request, token);
    }

    /**
     * 校验当前内容是否包含其他项目
     *
     * @param opmId
     * @return
     */
    @ApiOperation("校验当前大纲内容是否包含其他项目名称")
    @GetMapping("/verifyProjectLibWithProjectName")
    public BaseResponse verifyProjectLibWithProjectName(@RequestParam("opmId") String opmId) {
        log.info("verifyProjectLibWithProjectName");
        return outlineFeign.verifyProjectLibWithProjectName(opmId);
    }

    /**
     * 根据大纲体系id获取FileId
     *
     * @param oplId
     * @return
     */
    @ApiOperation("根据大纲体系id获取FileId")
    @GetMapping("/getFileIdByOplId")
    public BaseResponse getFileIdByOplId(@RequestParam("oplId") String oplId) {
        log.info("getFileIdByOplId");
        return outlineFeign.getFileIdByOplId(oplId);
    }

    /**
     * 根据pId和tCode获取大纲体系id
     *
     * @param pId
     * @param tCode
     * @return
     */
    @ApiOperation("根据pId和tCode获取大纲体系id")
    @GetMapping("/getOplIdByPidAndTCode")
    public BaseResponse getOplIdByPidAndTCode(@RequestParam("pId") String pId, @RequestParam("tCode") String tCode) {
        log.info("getOplIdByPidAndTCode");
        return outlineFeign.getOplIdByPidAndTCode(pId, tCode);
    }

    /**
     * 检查大纲叶子节点是否为空
     *
     * @param opmId
     * @return
     */
    @ApiOperation("检查大纲叶子节点是否为空")
    @GetMapping("/checkEmptyOutlineProjectLib")
    public BaseResponse checkEmptyOutlineProjectLib(@RequestParam("opmId") String opmId) {
        log.info("checkEmptyOutlineProjectLib");
        return outlineFeign.checkEmptyOutlineProjectLib(opmId);
    }
}
