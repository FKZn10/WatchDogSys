package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractMultiRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 多项目合同
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Slf4j
@RestController
@RequestMapping("/v1/contractMulti")
@Api(value = "多项目合同", tags = "多项目合同")
public class ConContractMultiController {
    @Resource
    private ConFeign conFeign;

    /**
     * 创建或更新多项目监理合同
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("创建或更新多项目监理合同")
    @PostMapping("/createOrUpdateContractMulti")
    public BaseResponse createOrUpdateContractMulti(@RequestBody ConContractMultiRequest request, @RequestHeader String token) {
        log.info("createOrUpdateContractMulti");
        return conFeign.createOrUpdateContractMulti(request, token);
    }

    /**
     * 审批结束后同步到合同表
     *
     * @param request
     * @return
     */
    @ApiOperation("审批结束后同步到合同表")
    @PostMapping("/syncToContract")
    public BaseResponse syncToContract(@RequestBody ConContractMultiRequest request) {
        log.info("syncToContract");
        return conFeign.syncToContract(request);
    }

    /**
     * 审批通过后同步多项目合同及扩展字段。
     *
     * @param request 包含多项目合同ID和目标合同模板编码tCode的请求
     * @return 同步结果
     */
    @ApiOperation("审批结束后同步多项目合同及扩展字段")
    @PostMapping("/syncToContractJNDX")
    public BaseResponse syncToContractJNDX(@RequestBody ConContractMultiRequest request) {
        return conFeign.syncToContractJNDX(request);
    }

    /**
     * 获取多项目合同分页列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("获取多项目合同分页列表")
    @PostMapping("/getContractMultiPage")
    public BaseResponse getContractMultiPage(@RequestBody ConContractMultiRequest request, @RequestHeader String token) {
        log.info("getContractMultiPage");
        return conFeign.getContractMultiPage(request, token);
    }

    /**
     * 获取多项目合同详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取多项目合同详情")
    @GetMapping("/getContractMultiById")
    public BaseResponse getContractMultiById(@RequestParam("id") String id) {
        log.info("getContractMultiById");
        return conFeign.getContractMultiById(id);
    }
}