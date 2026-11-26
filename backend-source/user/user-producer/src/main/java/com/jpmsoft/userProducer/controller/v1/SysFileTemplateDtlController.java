package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.SysFileTemplateDtlDTO;
import com.jpmsoft.userProducer.service.ISysFileTemplateDtlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文档模板扩展
 *
 * @author tianhelin
 * @date 2026/01/17
 */
@Slf4j
@Api(tags = "文档模板-扩展", value = "文档模板-扩展")
@RestController
@RequestMapping("/v1/SystemManage")
public class SysFileTemplateDtlController {
    @Resource
    private ISysFileTemplateDtlService sysFileTemplateDtlService;

    /**
     * 新增或修改单个文档模板扩展
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或修改单个文档模板扩展")
    @PostMapping("/createOrUpdateSysFileTemplateDtl")
    public BaseResponse createOrUpdateSysFileTemplateDtl(@RequestBody SysFileTemplateDtlDTO request) {
        log.info("createOrUpdateSysFileTemplateDtl");
        return sysFileTemplateDtlService.createOrUpdateSysFileTemplateDtl(request);
    }

    /**
     * 根据tId删除文档模板扩展
     *
     * @param tId
     * @return
     */
    @ApiOperation(value = "根据tId删除文档模板扩展")
    @GetMapping("/deleteSysFileTemplateDtlByTId")
    public BaseResponse deleteSysFileTemplateDtlByTId(@RequestParam String tId) {
        log.info("deleteSysFileTemplateDtlByTId");
        return sysFileTemplateDtlService.createOrUpdateSysFileTemplateDtlBatch(null, tId);
    }

    /**
     * 根据id删除文档模板扩展
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除文档模板扩展")
    @GetMapping("/deleteSysFileTemplateDtlById")
    public BaseResponse deleteSysFileTemplateDtlById(@RequestParam String id) {
        log.info("deleteSysFileTemplateDtlById");
        sysFileTemplateDtlService.removeById(id);
        return BaseResponse.ok();
    }

    /**
     * 根据tId获取文档模板扩展
     *
     * @param tId
     * @return
     */
    @ApiOperation(value = "根据tId获取文档模板扩展")
    @GetMapping("/getSysFileTemplateDtlByTId")
    public BaseResponse getSysFileTemplateDtlByTId(@RequestParam String tId) {
        log.info("getSysFileTemplateDtlByTId");
        return BaseResponse.ok(sysFileTemplateDtlService.getSysFileTemplateDtlBytIds(java.util.Collections.singletonList(tId)));
    }
}
