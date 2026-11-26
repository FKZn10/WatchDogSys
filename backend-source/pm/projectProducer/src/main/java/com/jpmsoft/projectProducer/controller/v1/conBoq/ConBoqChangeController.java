package com.jpmsoft.projectProducer.controller.v1.conBoq;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqChangeDto;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 合同工程量清单变更主表控制器
 */
@Slf4j
@RestController
@RequestMapping("/v1/conBoqChange")
public class ConBoqChangeController {
    @Resource
    private IConBoqChangeService conBoqChangeService;

    /**
     * 新增或修改变更清单主表及子表数据
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    @PostMapping("/createOrUpdateConBoqChange")
    public BaseResponse createOrUpdateConBoqChange(@RequestBody ConBoqChangeDto request) {
        log.info("createOrUpdateConBoqChange");
        return conBoqChangeService.createOrUpdateConBoqChange(request);
    }

    /**
     * 获取变更清单列表
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    @PostMapping("/getConBoqChangeList")
    public BaseResponse getConBoqChangeList(@RequestBody ConBoqChangeDto request) {
        log.info("getConBoqChangeList");
        return conBoqChangeService.getConBoqChangeList(request);
    }

    /**
     * 根据id获取变更清单主表及子表数据
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @GetMapping("/getConBoqChangeById")
    public BaseResponse getConBoqChangeById(@RequestParam String id) {
        log.info("getConBoqChangeById");
        return conBoqChangeService.getConBoqChangeById(id);
    }

    /**
     * 根据id删除变更清单主表及子表数据
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @GetMapping("/deleteConBoqChangeById")
    public BaseResponse deleteConBoqChangeById(@RequestParam String id) {
        log.info("deleteConBoqChangeById");
        return conBoqChangeService.deleteConBoqChangeById(id);
    }

    /**
     * 同步变更清单
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @GetMapping("/syncConBoqChange")
    public BaseResponse syncConBoqChange(@RequestParam String id) {
        log.info("syncConBoqChange");
        return conBoqChangeService.syncConBoqChange(id);
    }
}

