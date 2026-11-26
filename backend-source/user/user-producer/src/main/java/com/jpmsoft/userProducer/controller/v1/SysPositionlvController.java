package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.PositionlvDto;
import com.jpmsoft.userProducer.service.ISysPositionlvService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "职级", value = "职级")
@RestController
@RequestMapping("/v1/OrganizationManage")
public class SysPositionlvController {

    @Resource
    private ISysPositionlvService sysPositionlvService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/GetPositionLVList")
    public BaseResponse GetPositionLVList(@RequestBody PositionlvDto positionlv) {
        log.info("GetPositionLVList");
        return sysPositionlvService.GetPositionLVList(positionlv);
    }

    @GetMapping("/GetPositionLVSelect")
    public BaseResponse GetPositionLVSelect(@RequestHeader(required = false) String token) {
        log.info("GetPositionLVSelect");
        return sysPositionlvService.GetPositionLVSelect(redisUtils.getTeId(token));
    }

    @TenantId
    @PostMapping("/CreateOrUpdatePositionLV")
    public BaseResponse CreateOrUpdatePositionLV(@RequestBody PositionlvDto positionlv) {
        log.info("CreateOrUpdatePositionLV");
        return sysPositionlvService.CreateOrUpdatePositionLV(positionlv);
    }

    @PostMapping("/DeletePositionLV")
    public BaseResponse DeletePositionLV(@RequestBody PositionlvDto positionlv) {
        log.info("DeletePositionLV");
        return sysPositionlvService.DeletePositionLV(positionlv);
    }
}
