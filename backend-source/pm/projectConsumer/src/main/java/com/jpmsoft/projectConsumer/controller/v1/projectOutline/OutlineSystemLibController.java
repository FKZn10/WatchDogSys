package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemLibRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 业务管理规划 - 体系库
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@Slf4j
@RestController
@Api(value = "业务管理规划-体系库", tags = "业务管理规划-体系库")
@RequestMapping("/v1/projectOutline")
public class OutlineSystemLibController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 获取体系库树形结构
     *
     * @param request
     * @return
     */
    @ApiOperation("获取体系库树形结构")
    @PostMapping("/getOutlineSystemLibTree")
    public BaseResponse getOutlineSystemLibTree(@RequestBody OutlineSystemLibRequest request) {
        log.info("getOutlineSystemLibTree");
        return outlineFeign.getOutlineSystemLibTree(request);
    }

    /**
     * 获取体系库详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取体系库详情")
    @GetMapping("/getOutlineSystemLibById")
    public BaseResponse getOutlineSystemLibById(@RequestParam("id") String id) {
        log.info("getOutlineSystemLibById");
        return outlineFeign.getOutlineSystemLibById(id);
    }

    /**
     * 创建或更新体系库
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("创建或更新体系库")
    @PostMapping("/createOrUpdateOutlineSystemLib")
    public BaseResponse createOrUpdateOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineSystemLib");
        return outlineFeign.createOrUpdateOutlineSystemLib(request, token);
    }

    /**
     * 自动保存体系库
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("自动保存体系库")
    @PostMapping("/autoSaveOutlineSystemLib")
    public BaseResponse autoSaveOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineSystemLib");
        return outlineFeign.autoSaveOutlineSystemLib(request, token);
    }

    /**
     * 删除体系库
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("ID删除体系库")
    @GetMapping("/deleteOutlineSystemLibById")
    public BaseResponse deleteOutlineSystemLibById(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteOutlineSystemLibById");
        return outlineFeign.deleteOutlineSystemLibById(id, token);
    }

    /**
     * 批量更新体系库启用状态
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("批量更新体系库启用状态")
    @PostMapping("/batchUpdateSystemLibOslStatus")
    public BaseResponse batchUpdateSystemLibOslStatus(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("batchUpdateSystemLibOslStatus");
        return outlineFeign.batchUpdateSystemLibOslStatus(request, token);
    }

    /**
     * 更新模板目录排序
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("更新模板目录排序")
    @PostMapping("/updateOutlineSystemLibSort")
    public BaseResponse updateOutlineSystemLibSort(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("updateOutlineSystemLibSort");
        return outlineFeign.updateOutlineSystemLibSort(request, token);
    }

    /**
     * 查找或替换大纲体系模板
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("查找或替换大纲体系模板")
    @PostMapping("searchOrReplaceOutlineSystemLib")
    public BaseResponse searchOrReplaceOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("searchOrReplaceOutlineSystemLib");
        return outlineFeign.searchOrReplaceOutlineSystemLib(request, token);
    }

    /**
     * 检查模板叶子节点是否为空
     *
     * @param osmId
     * @param token
     * @return
     */
    @ApiOperation("检查模板叶子节点是否为空")
    @GetMapping("/checkEmptyOutlineSystemLib")
    public BaseResponse checkEmptyOutlineSystemLib(@RequestParam("osmId") String osmId, @RequestHeader String token) {
        log.info("checkEmptyOutlineSystemLib");
        return outlineFeign.checkEmptyOutlineSystemLib(osmId, token);
    }
}
