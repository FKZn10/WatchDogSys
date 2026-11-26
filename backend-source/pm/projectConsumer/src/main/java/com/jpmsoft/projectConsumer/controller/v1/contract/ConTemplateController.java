package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConTemplateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/8 10:34
 */
@Slf4j
@RestController
@Api(tags = "合同范本", value = "合同范本")
@RequestMapping("/v1/conTemplate")
public class ConTemplateController {

    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "新增合同范本")
    @PostMapping("/createConTemplate")
    public BaseResponse createConTemplate(@RequestBody ConTemplateDto request) {
        log.info("createConTemplate");
        return conFeign.createConTemplate(request);
    }


    @ApiOperation(value = "修改合同范本")
    @PostMapping("/updateConTemplate")
    public BaseResponse updateConTemplate(@RequestBody ConTemplateDto request) {
        log.info("updateConTemplate");
        return conFeign.updateConTemplate(request);
    }

    @ApiOperation(value = "分页合同范本")
    @PostMapping("/getConTemplateList")
    public BaseResponse getConTemplateList(@RequestBody ConTemplateDto request, @RequestHeader String token) {
        log.info("getConTemplateList");
        return conFeign.getConTemplateList(request, token);
    }

    @ApiOperation(value = "ID查询合同范本")
    @GetMapping("/getConTemplateById")
    public BaseResponse getConTemplateById(@RequestParam String id) {
        log.info("getConTemplateById");
        return conFeign.getConTemplateById(id);
    }

    @ApiOperation(value = "更新合同范本历史版本")
    @PostMapping("/updateConTemplateHistory")
    public BaseResponse updateConTemplateHistory(@RequestBody ConTemplateDto request) {
        log.info("updateConTemplateHistory");
        return conFeign.updateConTemplateHistory(request);
    }
}
