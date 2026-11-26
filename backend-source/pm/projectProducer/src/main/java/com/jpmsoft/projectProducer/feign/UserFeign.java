package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.projectProducer.pojo.dto.system.SelectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/28 9:58
 */
@FeignClient(name = "user-producer", contextId = "user-producer")
public interface UserFeign {

    @PostMapping("/v1/seqSet/generateCodeByTCode")
    String generateCodeByTCode(@RequestParam String tCode, @RequestBody Object request, @RequestHeader(required = false) String token);

    @PostMapping("/v1/seqSet/refershAutoNo")
    BaseResponse refershAutoNo(@RequestParam String tCode, @RequestBody Object request, @RequestHeader(required = false) String token);

    @GetMapping("/v1/seqSet/sequenceRestEnable")
    Boolean sequenceRestEnable(@RequestParam String tCode, @RequestHeader(required = false) String token);

    @GetMapping("/v1/SystemManage/getPowerTreeList")
    BaseResponse getPowerTreeList(@RequestHeader String token);


    @PostMapping("/v1/SystemManage/getDictionaryTreeByCodes")
    BaseResponse getDictionaryTreeByCodes(@RequestBody SelectDto selectDto);

    @PostMapping("/v1/OrganizationManage/syncSafeResponsibilityPostPerson")
    BaseResponse syncSafeResponsibilityPostPerson(@RequestBody SafeResponsibilityPostPersonDTO request);
}
