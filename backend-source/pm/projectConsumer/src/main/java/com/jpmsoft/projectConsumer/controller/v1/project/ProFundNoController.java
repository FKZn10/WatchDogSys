package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/20 15:02
 */
@Slf4j
@RestController
@Api(tags = "项目经费", value = "项目经费")
@RequestMapping("/v1/proFundNo")
public class ProFundNoController {

    @Autowired
    private PreFeign preFeign;


    @ApiOperation(value = "项目ID查询项目经费")
    @GetMapping("/getProFundNoByPid")
    public BaseResponse getProFundNoByPid(@RequestParam String pId) {
        return preFeign.getProFundNoByPid(pId);
    }
}
