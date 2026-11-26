package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemManageRequest;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineSystemManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

/**
 * 业务管理规划 - 体系模板
 *
 * @author tianhelin
 * @date 2025/6/10
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectOutline")
public class OutlineSystemManageController {
    @Resource
    private IOutlineSystemManageService outlineSystemManageService;

    /**
     * 获取体系模板分页
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineSystemManagePage")
    public BaseResponse getOutlineSystemManagePage(@RequestBody OutlineSystemManageRequest request) {
        log.info("getOutlineSystemManagePage");
        return outlineSystemManageService.getOutlineSystemManagePage(request);
    }

    /**
     * 获取体系模板详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getOutlineSystemManageById")
    public BaseResponse getOutlineSystemManageById(@RequestParam("id") String id) {
        log.info("getOutlineSystemManageById");
        return outlineSystemManageService.getOutlineSystemManageById(id);
    }

    /**
     * 创建或更新体系模板
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/createOrUpdateOutlineSystemManage")
    public BaseResponse createOrUpdateOutlineSystemManage(@RequestBody OutlineSystemManageRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineSystemManage");
        return outlineSystemManageService.createOrUpdateOutlineSystemManage(request, token);
    }

    /**
     * 删除体系模板
     *
     * @param request
     * @return
     */
    @PostMapping("/deleteOutlineSystemManageById")
    public BaseResponse deleteOutlineSystemManageById(@RequestBody OutlineSystemManageRequest request) {
        log.info("deleteOutlineSystemManageById");
        return outlineSystemManageService.deleteOutlineSystemManageById(request);
    }

    /**
     * 批量更新体系模板状态
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/batchUpdateSystemManageStatus")
    public BaseResponse batchUpdateSystemManageStatus(@RequestBody OutlineSystemManageRequest request, @RequestHeader String token) {
        log.info("batchUpdateSystemManageStatus");
        return outlineSystemManageService.batchUpdateSystemManageStatus(request, token);
    }

    /**
     * 复制体系模板
     *
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/copyOutlineSystemManageById")
    public BaseResponse copyOutlineSystemManageById(@RequestParam String id, @RequestHeader String token) {
        log.info("copyOutlineSystemManageById");
        return outlineSystemManageService.copyOutlineSystemManageById(id, token);
    }

    /**
     * 导入项目管理大纲Word
     * 内部调用
     *
     * @param map
     * @param osmId
     * @param token
     * @return
     */
    @PostMapping("/importOutlineProjectWord")
    public BaseResponse importOutlineProjectWord(@RequestBody LinkedHashMap<String, String> map, @RequestParam("osmId") String osmId, @RequestHeader String token) {
        log.info("importOutlineProjectWord");
        return outlineSystemManageService.importOutlineProjectWord(map, osmId, token);
    }
}
