package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemLibRequest;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineSystemLibService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 业务管理规划 - 体系库
 *
 * @author tianhelin
 * @date 2025/6/10
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectOutline")
public class OutlineSystemLibController {
    @Resource
    private IOutlineSystemLibService outlineSystemLibService;

    /**
     * 获取体系库树形结构
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineSystemLibTree")
    public BaseResponse getOutlineSystemLibTree(@RequestBody OutlineSystemLibRequest request) {
        log.info("getOutlineSystemLibTree");
        return outlineSystemLibService.getOutlineSystemLibTree(request);
    }

    /**
     * 获取体系库详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getOutlineSystemLibById")
    public BaseResponse getOutlineSystemLibById(@RequestParam("id") String id) {
        log.info("getOutlineSystemLibById");
        return outlineSystemLibService.getOutlineSystemLibById(id);
    }

    /**
     * 创建或更新体系库
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/createOrUpdateOutlineSystemLib")
    public BaseResponse createOrUpdateOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineSystemLib");
        return outlineSystemLibService.createOrUpdateOutlineSystemLib(request, token);
    }

    /**
     * 自动保存体系库
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/autoSaveOutlineSystemLib")
    public BaseResponse autoSaveOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineSystemLib");
        return outlineSystemLibService.createOrUpdateOutlineSystemLib(request, token);
    }

    /**
     * 删除体系库
     *
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/deleteOutlineSystemLibById")
    public BaseResponse deleteOutlineSystemLibById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineSystemLibById");
        return outlineSystemLibService.deleteOutlineSystemLibById(id, token);
    }

    /**
     * 批量更新体系库启用状态
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/batchUpdateSystemLibOslStatus")
    public BaseResponse batchUpdateSystemLibOslStatus(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("batchUpdateSystemLibOslStatus");
        return outlineSystemLibService.batchUpdateSystemLibOslStatus(request, token);
    }

    /**
     * 更新模板目录排序
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/updateOutlineSystemLibSort")
    public BaseResponse updateOutlineSystemLibSort(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("updateOutlineSystemLibSort");
        return outlineSystemLibService.updateOutlineSystemLibSort(request, token);
    }

    /**
     * 查找或替换大纲体系模板
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("searchOrReplaceOutlineSystemLib")
    public BaseResponse searchOrReplaceOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token) {
        log.info("searchOrReplaceOutlineSystemLib");
        return outlineSystemLibService.searchOrReplaceOutlineSystemLib(request, token);
    }

    /**
     * 检查模板叶子节点是否为空
     *
     * @param osmId
     * @param token
     * @return
     */
    @GetMapping("/checkEmptyOutlineSystemLib")
    public BaseResponse checkEmptyOutlineSystemLib(@RequestParam("osmId") String osmId, @RequestHeader String token) {
        log.info("checkEmptyOutlineSystemLib");
        return outlineSystemLibService.checkEmptyOutlineSystemLib(osmId, token);
    }

    /**
     * 在lib有编制模板的情况下，判断添加前置业务后，对应的数据源是否添加完全
     * 若未完全，自动关联对应的数据源
     *
     * @param osmId
     * @param token
     * @return
     */
    @GetMapping("/checkAndAutoAddDatasource")
    public BaseResponse checkAndAutoAddDatasource(@RequestParam("osmId") String osmId, @RequestHeader String token) {
        log.info("checkAndAutoAddDatasource");
        return outlineSystemLibService.checkAndAutoAddDatasource(osmId, token);
    }
}
