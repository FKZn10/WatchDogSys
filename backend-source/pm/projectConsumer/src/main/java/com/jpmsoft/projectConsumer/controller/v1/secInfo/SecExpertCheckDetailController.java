package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckDetailModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "一标一评", value = "考核评分")
@RequestMapping("/v1/expert/detail")
public class SecExpertCheckDetailController {

    @Resource
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改专家考核评分")
    @PostMapping("createOrupdateSecExpertCheckDetailAndDtl")
    public BaseResponse createOrupdateSecExpertCheckDetailAndDtl(@RequestBody SecExpertCheckDetailModel request, @RequestHeader String token) {
        log.info("createOrupdateSecExpertCheckDetailAndDtl");
        return secFeign.createOrupdateSecExpertCheckDetailAndDtl(request,token);

    }
    @ApiOperation(value = "ID查询专家考核评分")
    @GetMapping("getSecExpertCheckDetailAndDtlById")
    public BaseResponse getSecExpertCheckDetailAndDtlById(@RequestParam String id, @RequestHeader String token) {
        log.info("getSecExpertCheckDetailAndDtl");
        return secFeign.getSecExpertCheckDetailAndDtlById(id,token);
    }

    @ApiOperation(value = "查询专家考核评分选择次数")
    @GetMapping("getSecExpertCheckDetailAndDtlSelect")
    public BaseResponse getSecExpertCheckDetailAndDtlSelect(@RequestParam String srdId, @RequestHeader String token) {
        log.info("getSecExpertCheckDetailAndDtlSelect");
        return secFeign.getSecExpertCheckDetailAndDtlSelect(srdId,token);
    }

    @ApiOperation(value = "作废专家考核评分")
    @GetMapping("deleteSecExpertCheckDetailBySrId")
    public BaseResponse deleteSecExpertCheckDetailBySrId(@RequestParam String srId,@RequestHeader String token) {
        log.info("deleteSecExpertCheckDetailBySrId");
        return secFeign.deleteSecExpertCheckDetailBySrId(srId,token);
    }

}
