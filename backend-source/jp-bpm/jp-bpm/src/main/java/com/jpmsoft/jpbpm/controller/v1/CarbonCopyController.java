package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysCarbonCopyRequest;
import com.jpmsoft.common.model.request.UserCarbonCopyRequest;
import com.jpmsoft.jpbpm.pojo.dto.SysCcSendDto;
import com.jpmsoft.jpbpm.service.CarbonCopyService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Slf4j
@ApiIgnore
@Api(tags = "抄送流程", value = "抄送流程")
@RestController
@RequestMapping("/v1/carbonCopy")
public class CarbonCopyController {
    @Resource
    private CarbonCopyService carbonCopyService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 用户抄送流程
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getUserCarbonCopyList")
    public BaseResponse getUserCarbonCopyList(@RequestHeader(value = "token", required = false) String token, @RequestBody UserCarbonCopyRequest request) {
        Object uId = redisUtils.get(token);
        if (uId == null)
            return new BaseResponse("Token失效！");
        log.info("getUserCarbonCopyList");
        String customerId = redisUtils.getCustomerId(token);
        return carbonCopyService.getUserCarbonCopyList(uId.toString(), request, customerId);
    }

    /**
     * 流程管理
     * 抄送记录
     *
     * @param request
     * @return
     */
    @PostMapping("/getCarbonCopyList")
    public BaseResponse getCarbonCopyList(@RequestBody SysCarbonCopyRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getCarbonCopyList");
        String customerId = redisUtils.getCustomerId(token);
        return carbonCopyService.getCarbonCopyList(request, customerId);
    }


    /**
     * 流程管理
     * 抄送记录
     *
     * @param request
     * @return
     */
    @PostMapping("/FreeCC")
    public BaseResponse FreeCC(@RequestBody SysCcSendDto request, @RequestHeader(value = "token", required = false) String token) {
        String customerId = redisUtils.getCustomerId(token);
        return carbonCopyService.FreeCC(request, customerId);
    }


}
