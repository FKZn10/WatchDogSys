package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectStyleRequest;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectStyleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 管理规划子系统 样式
 *
 * @author tianhelin
 * @date 2025/8/18
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectOutline/style")
public class OutlineProjectStyleController {
    @Resource
    private IOutlineProjectStyleService outlineProjectStyleService;

    /**
     * 获取体系模板样式分页列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineStylePage")
    public BaseResponse getOutlineStylePage(@RequestBody OutlineProjectStyleRequest request) {
        log.info("getOutlineStylePage");
        return outlineProjectStyleService.getOutlineStylePage(request);
    }

    /**
     * 根据ID获取体系模板样式
     *
     * @param id
     * @return
     */
    @GetMapping("/getOutlineStyleById")
    public BaseResponse getOutlineStyleById(@RequestParam String id) {
        log.info("getOutlineStyleById");
        return outlineProjectStyleService.getOutlineStyleById(id);
    }

    /**
     * 删除体系模板样式
     *
     * @param request
     * @return
     */
    @PostMapping("/deleteOutlineStyleById")
    public BaseResponse deleteOutlineStyleById(@RequestBody OutlineProjectStyleRequest request) {
        log.info("deleteOutlineStyleById");
        return outlineProjectStyleService.deleteOutlineStyleById(request);
    }

    /**
     * 创建或更新体系模板样式
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/createOrUpdateOutlineStyle")
    public BaseResponse createOrUpdateOutlineStyle(@RequestBody OutlineProjectStyleRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineStyle");
        return outlineProjectStyleService.createOrUpdateOutlineStyle(request, token);
    }

    /**
     * 初始化样式数据
     *
     * @param token
     * @return
     */
    @GetMapping("/initializationOutlineStyle")
    public BaseResponse initializationOutlineStyle(@RequestHeader String token) {
        log.info("initializationOutlineStyle");
        outlineProjectStyleService.initializationOutlineStyle(token);
        return BaseResponse.ok("样式数据初始化成功");
    }
}
