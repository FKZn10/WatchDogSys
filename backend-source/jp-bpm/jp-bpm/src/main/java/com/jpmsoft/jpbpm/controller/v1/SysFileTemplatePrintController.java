package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.SysFileTemplatePrintDto;
import com.jpmsoft.jpbpm.service.SysFileTemplatePrintService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "打印模板", value = "打印模板")
@RequestMapping("/v1/templatePrint")
public class SysFileTemplatePrintController {
    @Autowired
    private SysFileTemplatePrintService sysFileTemplatePrintService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "新增修改打印模板")
    @PostMapping("/createOrUpdateFileTemplatePrint")
    public BaseResponse createOrUpdateFileTemplatePrint(@RequestBody SysFileTemplatePrintDto request,@RequestHeader(value ="token",required = false) String token) {
        log.info("createOrUpdateFileTemplatePrint");
        String customerId=redisUtils.getCustomerId(token);
        return sysFileTemplatePrintService.createOrUpdateFileTemplatePrint(request,customerId);
    }

    @ApiOperation(value = "ID删除打印模板")
    @GetMapping("/deleteFileTemplatePrintBytpId")
    public BaseResponse deleteFileTemplatePrintBytpId(@RequestParam String tpId,@RequestHeader(value ="token",required = false) String token) {
        log.info("deleteFileTemplatePrintBytpId");
        String customerId=redisUtils.getCustomerId(token);
        return sysFileTemplatePrintService.deleteFileTemplatePrintBytpId(tpId,customerId);
    }

    @ApiOperation(value = "分页查询打印模板")
    @PostMapping("/getFileTemplatePrintList")
    public BaseResponse getFileTemplatePrintList(@RequestBody SysFileTemplatePrintDto request,@RequestHeader(value ="token",required = false) String token) {
        log.info("getFileTemplatePrintList");
        String customerId=redisUtils.getCustomerId(token);
        return sysFileTemplatePrintService.getFileTemplatePrintList(request,customerId);
    }
}
