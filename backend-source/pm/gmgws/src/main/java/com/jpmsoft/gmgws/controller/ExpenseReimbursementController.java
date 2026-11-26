package com.jpmsoft.gmgws.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.pojo.dto.ExpenseReimbursementDTO;
import com.jpmsoft.gmgws.service.ExpenseReimbursementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/13 11:06
 */
@Slf4j
@RestController
@RequestMapping("/expenseReimbursement")
@Api(tags = "经费报销", value = "经费报销")
public class ExpenseReimbursementController {
    @Resource
    private ExpenseReimbursementService expenseReimbursementService;

    @ApiOperation(value = "查询可以解除协议的合同")
    @PostMapping("/getExpenseReimbursementList")
    public BaseResponse getExpenseReimbursementList(@RequestBody ExpenseReimbursementDTO request) {
        log.info("getExpenseReimbursementList");
        return expenseReimbursementService.getExpenseReimbursementList(request);
    }
}
