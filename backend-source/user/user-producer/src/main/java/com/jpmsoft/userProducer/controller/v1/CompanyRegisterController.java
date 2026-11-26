package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.CompanyRegisterModel;
import com.jpmsoft.userProducer.service.CompanyRegisterService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@Api(tags = "单位注册", value = "单位注册")
@RequestMapping("/v1/companyRegister")
public class CompanyRegisterController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CompanyRegisterService companyRegisterService;
    @PostMapping("/createOrUpdateCompanyRegister")
    public BaseResponse createOrUpdateCompanyRegister(@RequestBody CompanyRegisterModel dto, HttpServletRequest request) {
        log.info("createOrUpdateCompanyRegister");
        return companyRegisterService.createOrUpdateCompanyRegister(dto,request);
    }


    @PostMapping("/approveCompanyRegister")
    public BaseResponse approveCompanyRegister(@RequestBody CompanyRegisterModel request) {
        log.info("approveCompanyRegister");
        return companyRegisterService.approveCompanyRegister(request);
    }

    @PostMapping("/getCompanyRegisterList")
    public BaseResponse getCompanyRegisterList(@RequestBody CompanyRegisterModel request, @RequestHeader String token) {
        log.info("getCompanyRegisterList");
        return companyRegisterService.getCompanyRegisterList(request,redisUtils.getUid(token));
    }

    @GetMapping("/getCompanyRegister")
    public BaseResponse getCompanyRegister(@RequestParam("id")String id, @RequestHeader String token) {
        log.info("getCompanyRegister");
        return companyRegisterService.getCompanyRegister(id);
    }
}
