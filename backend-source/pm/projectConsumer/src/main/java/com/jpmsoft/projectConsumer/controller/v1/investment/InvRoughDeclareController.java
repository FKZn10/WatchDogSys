package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvRoughDeclareRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "匡算申报", value = "匡算申报")
@RequestMapping("/inv/v1/invRoughDeclare")
public class InvRoughDeclareController {
    @Resource
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改匡算申报")
    @PostMapping("/createOrUpdateInvRoughDeclare")
    public BaseResponse createOrUpdateInvRoughDeclare(@RequestBody InvRoughDeclareRequest request) {
        log.info("createOrUpdateInvRoughDeclare");
        return invFeign.createOrUpdateInvRoughDeclare(request);
    }

    @ApiOperation(value = "分页查询匡算申报")
    @PostMapping("/getInvRoughDeclareList")
    public BaseResponse getInvRoughDeclareList(@RequestBody InvRoughDeclareRequest request, @RequestHeader String token) {
        log.info("getInvRoughDeclareList");
        return invFeign.getInvRoughDeclareList(request, token);
    }

    @ApiOperation(value = "ID查询匡算申报")
    @GetMapping("/getInvRoughDeclareByBdId")
    public BaseResponse getInvRoughDeclareByBdId(@RequestParam String rdId) {
        log.info("getInvRoughDeclareByBdId");
        return invFeign.getInvRoughDeclareByRdId(rdId);
    }

    @ApiOperation(value = "ID删除匡算申报")
    @GetMapping("/deleteInvRoughDeclareByBdId")
    public BaseResponse deleteInvRoughDeclareByBdId(@RequestParam String rdId) {
        log.info("deleteInvRoughDeclareByBdId");
        return invFeign.deleteInvRoughDeclareByRdId(rdId);
    }

    @ApiOperation(value = "项目ID集合获取对应匡算清单树")
    @PostMapping("/getInvRoughDeclareDtlByPIds")
    public BaseResponse getInvRoughDeclareDtlByPIds(@RequestBody List<String> pIds) {
        log.info("getInvRoughDeclareDtlByPIds");
        return invFeign.getInvRoughDeclareDtlByPIds(pIds);
    }

    @ApiOperation(value = "项目ID获取对应匡算信息")
    @GetMapping("/getInvRoughDeclareByPId")
    public BaseResponse getInvRoughDeclareByPId(@RequestParam String pId) {
        log.info("getInvRoughDeclareByPId");
        return invFeign.getInvRoughDeclareByPId(pId);
    }



    @ApiOperation(value = "项目ID获取匡算和匡算清单")
    @GetMapping("/getInvRoughDeclareAndDtlByPid")
    public BaseResponse getInvRoughDeclareAndDtlByPid(@RequestParam String pId) {
        log.info("getInvRoughDeclareAndDtlByPid");
        return invFeign.getInvRoughDeclareAndDtlByPid(pId);
    }
}
