package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "设计管理（项目信息）", value = "设计管理（项目信息）")
@RequestMapping("/design/v1/designProjectInfo")
public class DesignProjectInfoController {

    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "获取设计管理（项目信息）数据列表")
    @PostMapping("/getDesignProjectInfoList")
    public BaseResponse getDesignProjectInfoList(@RequestBody DesignProjectInfoModel request, @RequestHeader String token) {
        log.info("getDesignProjectInfoList");
        return baseFeign.getDesignProjectInfoList(request,token);
    }

    @ApiOperation(value = "获取最新的设计管理（项目信息）数据")
    @PostMapping("/getDesignProjectInfoByBusiness")
    public BaseResponse getDesignProjectInfoByBusiness(@RequestBody DesignProjectInfoModel request) {
        log.info("getDesignProjectInfoByBusiness");
        return baseFeign.getDesignProjectInfoByBusiness(request);
    }


}
