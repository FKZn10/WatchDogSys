package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceCurrentDto;
import com.jpmsoft.userProducer.service.ISysSequenceCurrentCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "编码规则-当前编码", value = "编码规则-当前编码")
@RequestMapping("/v1/seqCurrentCode")
public class SysSequenceCurrentCodeController {

    @Resource
    private ISysSequenceCurrentCodeService sysSequenceCurrentCodeService;

    @ApiOperation(value = "分页查询当前编码")
    @PostMapping("/getSysSequenceCurrentCodeBySid")
    public BaseResponse getSysSequenceCurrentCodeBySid(@RequestBody SysSequenceCurrentDto request) {
        log.info("getSysSequenceCurrentCodeBySid");
        return sysSequenceCurrentCodeService.getSysSequenceCurrentCodeBySid(request);
    }

    @ApiOperation(value = "新增修改当前编号即重置规则")
    @PostMapping("/createOrUpdateSequenceCurrentCode")
    public BaseResponse createOrUpdateSequenceCurrentCode(@RequestBody SysSequenceCurrentDto request) {
        log.info("createOrUpdateSequenceCurrentCode");
        return sysSequenceCurrentCodeService.createOrUpdateSequenceCurrentCode(request);
    }

    @ApiOperation(value = "ID删除")
    @GetMapping("/delSequenceCurrentCodeById")
    public BaseResponse delSequenceCurrentCodeById(@RequestParam String id) {
        log.info("delSequenceCurrentCodeById");
        return sysSequenceCurrentCodeService.delSequenceCurrentCodeById(id);
    }
}
