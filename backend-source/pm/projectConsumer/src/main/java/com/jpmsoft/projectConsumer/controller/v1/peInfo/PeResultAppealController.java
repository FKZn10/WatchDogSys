package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeResultAppealRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "履约评价结果申诉", value = "履约评价结果申诉")
@RequestMapping("/v1/resultAppeal")
public class PeResultAppealController {
    @Autowired
    private PeInfoFeign peInfoFeign;


    @ApiOperation(value = "创建或修改履约评价结果申诉")
    @PostMapping("/createOrUpdateResultAppeal")
    public BaseResponse createOrUpdateResultAppeal(@RequestBody PeResultAppealRequest request) {
        log.info("createOrUpdateResultAppeal");
        return peInfoFeign.createOrUpdateResultAppeal(request);
    }

    @ApiOperation(value = "获取履约评价结果申诉列表")
    @PostMapping("/getResultAppealList")
    public BaseResponse getResultAppealList(@RequestBody PeResultAppealRequest request, @RequestHeader String token) {
        log.info("getResultAppealList");
        return peInfoFeign.getResultAppealList(request, token);
    }

    @ApiOperation(value = "ID删除履约评价结果申诉")
    @PostMapping("/deleteResultAppealById")
    public BaseResponse deleteResultAppealById(@RequestBody PeResultAppealRequest request) {
        log.info("deleteResultAppealById");
        return peInfoFeign.deleteResultAppealById(request);
    }
}
