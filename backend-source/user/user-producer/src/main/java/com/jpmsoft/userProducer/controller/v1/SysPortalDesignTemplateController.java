package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPortalDesignTemplateDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalDesignTemplate;
import com.jpmsoft.userProducer.service.ISysPortalDesignTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "门户设计__模板", value = "门户设计__模板")
@RequestMapping("/v1/SysPortalDesignTemplate")
public class SysPortalDesignTemplateController {
    @Resource
    private ISysPortalDesignTemplateService sysPortalDesignService;

    @ApiOperation(value = "创建修改门户信息")
    @PostMapping("/CreateOrUpdateSysPortalDesignTemplate")
    public BaseResponse CreateOrUpdateSysPortalDesignTemplate(@RequestBody SysPortalDesignTemplateDto request) {
        return sysPortalDesignService.CreateOrUpdateSysPortalDesignTemplate(request);
    }


    @ApiOperation(value = "分页查询支门户信息")
    @PostMapping("/GetSysPortalDesignTemplateList")
    public BaseResponse GetSysPortalDesignTemplateList(@RequestBody SysPortalDesignTemplateDto request) {
        return sysPortalDesignService.GetSysPortalDesignTemplateList(request);
    }
}
