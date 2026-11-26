package com.jpmsoft.gmgws.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.service.BorrowingRetService;
import com.jpmsoft.gmgws.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/borrowing")
@Api(tags = "借还款(光明)", value = "借还款(光明)")
public class BorrowingRetController {



    @Autowired
    private BorrowingRetService borrowingRetService;

    @Resource
    private RedisUtils redisUtils;


    @ApiOperation(value = "获取借款记录")
    @GetMapping("/getLoanStill")
    public BaseResponse getLoanStill(@RequestHeader("token") String token){
      return   borrowingRetService.getLoanStill(redisUtils.getUid(token));
    }

    @ApiOperation("获取报销记录")
    @GetMapping("/getReimbursement")
    public BaseResponse getReimbursement(@RequestHeader("token") String token){
        return   borrowingRetService.getReimbursement(redisUtils.getUid(token));
    }




}
