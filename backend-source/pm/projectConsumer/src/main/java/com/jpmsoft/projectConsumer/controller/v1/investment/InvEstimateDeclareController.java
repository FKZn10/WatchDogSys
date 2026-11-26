package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvEstimateDeclareRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "估算申报", value = "估算申报")
@RequestMapping("/inv/v1/invEstimateDeclare")
public class InvEstimateDeclareController {
    @Resource
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改估算申报")
    @PostMapping("/createOrUpdateInvEstimateDeclare")
    public BaseResponse createOrUpdateInvEstimateDeclare(@RequestBody InvEstimateDeclareRequest request) {
        log.info("createOrUpdateInvEstimateDeclare");
        return invFeign.createOrUpdateInvEstimateDeclare(request);
    }

    @ApiOperation(value = "分页查询估算申报")
    @PostMapping("/getInvEstimateDeclareList")
    public BaseResponse getInvEstimateDeclareList(@RequestBody InvEstimateDeclareRequest request, @RequestHeader String token) {
        log.info("getInvEstimateDeclareList");
        return invFeign.getInvEstimateDeclareList(request, token);
    }

    @ApiOperation(value = "ID查询估算申报")
    @GetMapping("/getInvEstimateDeclareByBdId")
    public BaseResponse getInvEstimateDeclareByBdId(@RequestParam String edId) {
        log.info("getInvEstimateDeclareByBdId");
        return invFeign.getInvEstimateDeclareByEdId(edId);
    }

    @ApiOperation(value = "ID删除估算申报")
    @GetMapping("/deleteInvEstimateDeclareByBdId")
    public BaseResponse deleteInvEstimateDeclareByBdId(@RequestParam String edId) {
        log.info("deleteInvEstimateDeclareByBdId");
        return invFeign.deleteInvEstimateDeclareByEdId(edId);
    }

    @ApiOperation(value = "项目ID集合获取对应估算清单树")
    @PostMapping("/getInvEstimateDeclareDtlByPIds")
    public BaseResponse getInvEstimateDeclareDtlByPIds(@RequestBody List<String> pIds) {
        log.info("getInvEstimateDeclareDtlByPIds");
        return invFeign.getInvEstimateDeclareDtlByPIds(pIds);
    }

    @ApiOperation(value = "项目ID获取对应估算信息")
    @GetMapping("/getInvEstimateDeclareByPId")
    public BaseResponse getInvEstimateDeclareByPId(@RequestParam String pId) {
        log.info("getInvEstimateDeclareByPId");
        return invFeign.getInvEstimateDeclareByPId(pId);
    }



    @ApiOperation(value = "项目ID获取估算和估算清单")
    @GetMapping("/getInvEstimateDeclareAndDtlByPid")
    public BaseResponse getInvEstimateDeclareAndDtlByPid(@RequestParam String pId) {
        log.info("getInvEstimateDeclareAndDtlByPid");
        return invFeign.getInvEstimateDeclareAndDtlByPid(pId);
    }
}
