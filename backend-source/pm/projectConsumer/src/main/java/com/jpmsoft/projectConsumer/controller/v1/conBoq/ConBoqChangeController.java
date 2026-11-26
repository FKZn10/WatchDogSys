package com.jpmsoft.projectConsumer.controller.v1.conBoq;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqChangeDto;
import com.jpmsoft.projectConsumer.feign.ConBoqFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 合同工程量清单变更主表控制器
 */
@Slf4j
@RestController
@RequestMapping("/v1/conBoqChange")
@Api(tags = "合同工程量清单变更主表", value = "合同工程量清单变更主表")
public class ConBoqChangeController {
    @Resource
    private ConBoqFeign conBoqFeign;

    /**
     * 新增或修改变更清单主表及子表数据
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    @ApiOperation(value = "新增或修改变更清单主表及子表数据")
    @PostMapping("/createOrUpdateConBoqChange")
    public BaseResponse createOrUpdateConBoqChange(@RequestBody ConBoqChangeDto request) {
        log.info("createOrUpdateConBoqChange");
        return conBoqFeign.createOrUpdateConBoqChange(request);
    }

    /**
     * 获取变更清单列表
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    @ApiOperation(value = "获取变更清单列表")
    @PostMapping("/getConBoqChangeList")
    public BaseResponse getConBoqChangeList(@RequestBody ConBoqChangeDto request) {
        log.info("getConBoqChangeList");
        return conBoqFeign.getConBoqChangeList(request);
    }

    /**
     * 根据id获取变更清单主表及子表数据
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @ApiOperation(value = "根据id获取变更清单主表及子表数据")
    @GetMapping("/getConBoqChangeById")
    public BaseResponse getConBoqChangeById(@RequestParam String id) {
        log.info("getConBoqChangeById");
        return conBoqFeign.getConBoqChangeById(id);
    }

    /**
     * 根据id删除变更清单主表及子表数据
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @ApiOperation(value = "根据id删除变更清单主表及子表数据")
    @GetMapping("/deleteConBoqChangeById")
    public BaseResponse deleteConBoqChangeById(@RequestParam String id) {
        log.info("deleteConBoqChangeById");
        return conBoqFeign.deleteConBoqChangeById(id);
    }

    /**
     * 同步变更清单
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @ApiOperation(value = "同步变更清单")
    @GetMapping("/syncConBoqChange")
    public BaseResponse syncConBoqChange(@RequestParam String id) {
        log.info("syncConBoqChange");
        return conBoqFeign.syncConBoqChange(id);
    }
}

