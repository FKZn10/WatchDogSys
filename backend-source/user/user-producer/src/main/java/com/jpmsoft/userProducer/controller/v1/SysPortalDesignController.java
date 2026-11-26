package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPortalDesignDto;
import com.jpmsoft.userProducer.service.ISysPortalDesignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "门户管理", value = "门户管理")
@RequestMapping("/v1/portalDesign")
public class SysPortalDesignController {
    @Resource
    private ISysPortalDesignService sysPortalDesignService;

    @ApiOperation(value = "创建修改门户信息")
    @PostMapping("/CreateOrUpdateSysPortalDesign")
    public BaseResponse CreateOrUpdateSysPortalDesign(@RequestBody SysPortalDesignDto request) {
        return sysPortalDesignService.CreateOrUpdateSysPortalDesign(request);
    }


    @ApiOperation(value = "分页查询支门户信息")
    @PostMapping("/GetSysPortalDesignList")
    public BaseResponse GetSysPortalDesignList(@RequestBody SysPortalDesignDto request) {
        return sysPortalDesignService.GetSysPortalDesignList(request);
    }
//
//    @ApiOperation(value = "ID查询支付模板")
//    @GetMapping("/getSysPayTemplateById")
//    public BaseResponse getSysPayTemplateById(@RequestParam String id) {
//        return sysPortalDesignService.importSysPortalDesign(id);
//    }
}
