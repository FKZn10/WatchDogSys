package com.jpmsoft.projectConsumer.controller.v1.acpm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.acpm.AcpmProInvApprovalFormDto;
import com.jpmsoft.projectConsumer.pojo.dto.acpm.AcpmProjectInvestmentApprovalDto;
import com.jpmsoft.projectConsumer.feign.AcpmFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 项目投拓立项
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:24
 */
@Slf4j
@RestController
@Api(tags = "项目投拓立项", value = "项目投拓立项")
@RequestMapping("/acpm/v1/proInvApproval")
public class AcpmProjectInvestmentApprovalController {

    @Autowired
    private AcpmFeign acpmFeign;

    @ApiOperation(value = "创建修改项目投拓立项")
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AcpmProjectInvestmentApprovalDto request) {
        return acpmFeign.createOrUpdate(request);
    }

    @ApiOperation(value = "分页查询项目投拓立项")
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody AcpmProInvApprovalFormDto request) {
        return acpmFeign.getList(request);
    }

    @ApiOperation(value = "ID查询项目投拓立项")
    @GetMapping("/getProInvApprovalById")
    public BaseResponse getProInvApprovalById(@RequestParam String id) {
        return acpmFeign.getProInvApprovalById(id);
    }

    @ApiOperation(value = "分页获取项目投拓立项选项")
    @PostMapping("/getSelectList")
    public BaseResponse getSelectList(@RequestBody AcpmProInvApprovalFormDto request) {
        return acpmFeign.getSelectList(request);
    }
}
