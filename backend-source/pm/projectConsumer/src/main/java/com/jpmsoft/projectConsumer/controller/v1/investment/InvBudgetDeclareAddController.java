package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.investment.InvBudgetDeclareAddDTO;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:31
 */
@Slf4j
@RestController
@Api(tags = "概算追加", value = "概算追加")
@RequestMapping("/v1/budgetAdd")
public class InvBudgetDeclareAddController {

    @Resource
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改概算追加")
    @PostMapping("/createOrUpdateInvBudgetDeclareAdd")
    public BaseResponse createOrUpdateInvBudgetDeclareAdd(@RequestBody InvBudgetDeclareAddDTO request) {
        log.info("createOrUpdateInvBudgetDeclareAdd");
        return invFeign.createOrUpdateInvBudgetDeclareAdd(request);
    }

    @ApiOperation(value = "分页查询概算追加")
    @PostMapping("/getInvBudgetDeclareAddList")
    public BaseResponse getInvBudgetDeclareAddList(@RequestBody InvBudgetDeclareAddDTO request, @RequestHeader String token) {
        log.info("getInvBudgetDeclareAddList");
        return invFeign.getInvBudgetDeclareAddList(request, token);
    }

    @ApiOperation(value = "ID查询概算追加")
    @GetMapping("/getInvBudgetDeclareAddById")
    public BaseResponse getInvBudgetDeclareAddById(@RequestParam String id) {
        log.info("getInvBudgetDeclareAddById");
        return invFeign.getInvBudgetDeclareAddById(id);
    }

    @ApiOperation(value = "审批后追加到项目概算金额")
    @GetMapping("/approve2AddProBudget")
    public BaseResponse approve2AddProBudget(@RequestParam String id) {
        log.info("approve2AddProBudget");
        return invFeign.approve2AddProBudget(id);
    }
}
