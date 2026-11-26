package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import com.jpmsoft.projectConsumer.pojo.dto.other.OuterUserRegisterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "系统外部用户注册", value = "系统外部用户注册")
@RestController
@RequestMapping("/v1/outer/userRegister")
@Slf4j
public class OuterUserRegisterController {

    @Resource
    private BaseFeign baseFeign;

    /**
     * 系统外部发起用户注册
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "系统外部发起用户注册")
    @PostMapping("/registerUser")
    public BaseResponse registerUser(@RequestBody OuterUserRegisterDTO request) {
        log.info("registerUser");
        return baseFeign.registerUser(request);
    }

    /**
     * 审批通过创建用户数据
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "审批通过创建用户数据")
    @GetMapping("/approve2CreateUser")
    public BaseResponse approve2CreateUser(@RequestParam String id) {
        log.info("approve2CreateUser");
        return baseFeign.approve2CreateUser(id);
    }
}
