package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.other.OuterUserRegisterDTO;
import com.jpmsoft.projectProducer.service.other.IOuterUserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.GET;


@RestController
@RequestMapping("/v1/outer/userRegister")
@Slf4j
public class OuterUserRegisterController {

    @Resource
    private IOuterUserRegisterService outerUserRegisterService;

    /**
     * 系统外部发起用户注册
     *
     * @param request
     * @return
     */
    @PostMapping("/registerUser")
    public BaseResponse registerUser(@RequestBody OuterUserRegisterDTO request) {
        log.info("registerUser");
        return outerUserRegisterService.registerUser(request);
    }

    /**
     * 审批通过创建用户数据
     *
     * @param id
     * @return
     */
    @GetMapping("/approve2CreateUser")
    public BaseResponse approve2CreateUser(@RequestParam String id) {
        log.info("approve2CreateUser");
        return outerUserRegisterService.approve2CreateUser(id);
    }
}
