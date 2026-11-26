package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.SysFormDTO;
import com.jpmsoft.userProducer.service.ISysFormService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 9:25
 */
@Slf4j
@Api(tags = "表单设计", value = "表单设计")
@RestController
@RequestMapping("/customDesign/v1/sysForm")
public class SysFormController {

    @Resource
    private ISysFormService sysFormService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @ApiOperation(value = "新增修改")
    @PostMapping("/createOrUpdateSysForm")
    public BaseResponse createOrUpdateSysForm(@RequestBody SysFormDTO request) {
        log.info("createOrUpdateSysForm");
        return sysFormService.createOrUpdateSysForm(request);
    }

    @TenantId
    @ApiOperation(value = "新增修改(文件保存)")
    @PostMapping("/createOrUpdateSysFormInText")
    public BaseResponse createOrUpdateSysFormInText(@RequestBody MultipartFile request) {
        log.info("createOrUpdateSysFormInText");
        return sysFormService.createOrUpdateSysFormInText(request);
    }
    @TenantId
    @ApiOperation(value = "分页列表")
    @PostMapping("/getSysFormList")
    public BaseResponse getSysFormList(@RequestBody SysFormDTO request) {
        log.info("getSysFormList");
        return sysFormService.getSysFormList(request);
    }

    @ApiOperation(value = "ID查询")
    @GetMapping("/getSysFormById")
    public BaseResponse getSysFormById(@RequestParam String id) {
        log.info("getSysFormById");
        return sysFormService.getSysFormById(id);
    }

    @ApiOperation(value = "表单复制")
    @GetMapping("/copySysFormById")
    public BaseResponse copySysFormById(@RequestParam String id) {
        log.info("copySysFormById");
        return sysFormService.copySysFormById(id);
    }

    @ApiOperation(value = "tCode查询启用的数据")
    @GetMapping("/getSysFormByCode")
    public BaseResponse getSysFormByCode(@RequestParam String code, @RequestHeader String token) {
        log.info("getSysFormByCode");
        return sysFormService.getSysFormByCode(code, redisUtils.getTeId(token));
    }


    @ApiOperation(value = "tCode查询启用的数据")
    @GetMapping("/getSysFormByCodeNoToken")
    public BaseResponse getSysFormByCodeNoToken(@RequestParam String code) {
        log.info("getSysFormByCodeNoToken");
        return sysFormService.getSysFormByCode(code, null);
    }

    @ApiOperation(value = "批量导入")
    @PostMapping("/importSysForm")
    public BaseResponse importSysForm(@RequestBody List<SysFormDTO> request) {
        log.info("importSysForm");
        return sysFormService.importSysForm(request);
    }

    @ApiOperation(value = "表单文件导入")
    @PostMapping("/importSysFormInFile")
    public BaseResponse importSysFormInFile(@RequestParam MultipartFile request) {
        log.info("importSysForm");
        return sysFormService.importSysForm(request);
    }

}
