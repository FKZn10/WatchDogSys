package com.jpmsoft.projectConsumer.controller.v1.acpm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.acpm.AcpmInvituoMarginApplicationDto;
import com.jpmsoft.projectConsumer.feign.AcpmFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 投拓保证金申请
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:51
 */
@Slf4j
@RestController
@Api(tags = "投拓保证金申请", value = "投拓保证金申请")
@RequestMapping("/acpm/v1/InvMarginApp")
public class AcpmInvituoMarginApplicationController {


    @Autowired
    private AcpmFeign acpmFeign;

    @ApiOperation(value = "创建修改投拓保证金申请")
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AcpmInvituoMarginApplicationDto request) {
        return acpmFeign.createOrUpdate(request);
    }

    @ApiOperation(value = "分页查询投拓保证金申请")
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody AcpmInvituoMarginApplicationDto request) {
        return acpmFeign.getList(request);
    }

    @ApiOperation(value = "ID查询投拓保证金申请")
    @GetMapping("/getInvMarginAppById")
    public BaseResponse getInvMarginAppById(@RequestParam String id) {
        return acpmFeign.getInvMarginAppById(id);
    }
}
