package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDto;
import com.jpmsoft.userProducer.service.ISysSequenceSetService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "编码规则-编码规则配置", value = "编码规则-编码规则配置")
@RequestMapping("/v1/seqSet")
public class SysSequenceSetController {

    @Resource
    private ISysSequenceSetService sysSequenceSetService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @ApiOperation(value = "新增修改编码规则配置")
    @PostMapping("/createOrUpdateSysSequenceSet")
    public BaseResponse createOrUpdateSysSequenceSet(@RequestBody SysSequenceSetDto request) {
        log.info("createOrUpdateSysSequenceSet");
        return sysSequenceSetService.createOrUpdateSysSequenceSet(request);
    }

    @TenantId
    @ApiOperation(value = "分页查询编码规则配置")
    @PostMapping("/getSysSequenceSetList")
    public BaseResponse getSysSequenceSetList(@RequestBody SysSequenceSetDto request) {
        log.info("getSysSequenceSetList");
        return sysSequenceSetService.getSysSequenceSetList(request);
    }

    @ApiOperation(value = "模板编码查询编码规则配置及其详细")
    @GetMapping("/getSysSequenceSetByTcode")
    public BaseResponse getSysSequenceSetByTcode(@RequestParam String tCode, @RequestHeader(required = false) String token) {
        log.info("getSysSequenceSetByTcode");
        return sysSequenceSetService.getSysSequenceSetByTcode(tCode, redisUtils.getTeId(token));
    }

    @ApiOperation(value = "根据编码规则生成对应编码")
    @PostMapping("/generateCodeByTCode")
    public String generateCodeByTCode(@RequestParam String tCode, @RequestBody Object request, @RequestHeader(required = false) String token) {
        try {
            log.info("generateCodeByTCode");
            return sysSequenceSetService.generateCodeByTCode(tCode, request, redisUtils.getTeId(token));
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "根据编码规则生成对应编码")
    @PostMapping("/generateCodeByCustomized")
    public String generateCodeByCustomized(@RequestParam String tCode, @RequestParam(required = false) String pId,
                                           @RequestParam(required = false) String conId, @RequestParam(required = false) String secId,
                                           @RequestBody Object request, @RequestHeader(required = false) String token) {
        try {
            log.info("generateCodeByCustomized");
            return sysSequenceSetService.generateCodeByTCode(tCode, pId, conId, secId, request, redisUtils.getTeId(token));
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "刷新自增序号")
    @PostMapping("/refershAutoNo")
    public BaseResponse refershAutoNo(@RequestParam String tCode, @RequestBody Object request, @RequestHeader(required = false) String token) {
        log.info("refershAutoNo");
        return sysSequenceSetService.refershAutoNo(tCode, request, redisUtils.getTeId(token));
    }

    @ApiOperation(value = "刷新自增序号")
    @PostMapping("/refershAutoNoByCustomized")
    public BaseResponse refershAutoNoByCustomized(@RequestParam String tCode, @RequestParam(required = false) String pId,
                                                  @RequestParam(required = false) String conId, @RequestParam(required = false) String secId,
                                                  @RequestBody Object request, @RequestHeader(required = false) String token) {
        return sysSequenceSetService.refershAutoNoByCustomized(tCode, pId, conId, secId, request, redisUtils.getTeId(token));
    }

    @ApiOperation(value = "校验是否启用编码规则")
    @GetMapping("/sequenceRestEnable")
    public Boolean sequenceRestEnable(@RequestParam String tCode, @RequestHeader(required = false) String token) {
        try {
            log.info("sequenceRestEnable");
            return sysSequenceSetService.sequenceRestEnable(tCode, redisUtils.getTeId(token));
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
            return null;
        }
    }
}
