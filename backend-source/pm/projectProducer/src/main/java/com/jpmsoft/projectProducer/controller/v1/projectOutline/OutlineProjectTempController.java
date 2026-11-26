package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectTempRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目管理大纲体系-关联业务功能
 *
 * @author tianhelin
 * @date 2025/6/17
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectOutline/projectTemp")
public class OutlineProjectTempController {
    @Resource
    private IOutlineProjectTempService outlineProjectTempService;

    /**
     * 新增项目管理大纲体系-关联业务功能
     *
     * @param request
     * @return
     */
    @PostMapping("/createOutlineProjectTemp")
    public BaseResponse createOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token) {
        log.info("createOutlineProjectTemp");
        return outlineProjectTempService.createOutlineProjectTemp(request, token);
    }

    /**
     * 删除项目管理大纲体系-关联业务功能
     *
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/deleteOutlineProjectTempById")
    public BaseResponse deleteOutlineProjectTempById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineProjectTempById");
        return outlineProjectTempService.deleteOutlineProjectTempById(id, token);
    }

    /**
     * 修改项目管理大纲体系-关联业务功能
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/updateOutlineProjectTemp")
    public BaseResponse updateOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token) {
        log.info("updateOutlineProjectTemp");
        return outlineProjectTempService.updateOutlineProjectTemp(request, token);
    }

    /**
     * 查询项目大类
     *
     * @param id
     * @return
     */
    @GetMapping("/getOutlineProjectTempById")
    public BaseResponse getOutlineProjectTempById(@RequestParam("id") String id) {
        log.info("getOutlineProjectTempById");
        return outlineProjectTempService.getOutlineProjectTempById(id);
    }

    /**
     * 分页查询项目大类
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineProjectTempPage")
    public BaseResponse getOutlineProjectTempPage(@RequestBody OutlineProjectTempRequest request) {
        log.info("getOutlineProjectTempPage");
        return outlineProjectTempService.getOutlineProjectTempPage(request);
    }

    /**
     * 自动保存项目管理大纲体系-关联业务功能
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/autoSaveOutlineProjectTemp")
    public BaseResponse autoSaveOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineProjectTemp");
        if (ValidateUtil.isEmpty(request.getId())) {
            return outlineProjectTempService.createOutlineProjectTemp(request, token);
        } else {
            return outlineProjectTempService.updateOutlineProjectTemp(request, token);
        }
    }
}
