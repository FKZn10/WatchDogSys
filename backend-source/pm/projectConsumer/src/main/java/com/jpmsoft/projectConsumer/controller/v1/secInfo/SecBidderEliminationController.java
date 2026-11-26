package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidderEliminationModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "过多投标人淘汰", value = "过多投标人淘汰")
@RequestMapping("/v1/bidderElimination")
public class SecBidderEliminationController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改过多投标人淘汰")
    @PostMapping("/createOrUpdateSecBidderElimination")
    public BaseResponse createOrUpdateSecBidderElimination(@RequestBody SecBidderEliminationModel request) {
        log.info("createOrUpdateSecBidderElimination");
        return secFeign.createOrUpdateSecBidderElimination(request);
    }

    @ApiOperation(value = "分页查询过多投标人淘汰")
    @PostMapping("/getSecBidderEliminationList")
    public BaseResponse getSecBidderEliminationList(@RequestBody SecBidderEliminationModel request) {
        log.info("getSecBidderEliminationList");
        return secFeign.getSecBidderEliminationList(request);
    }

    @ApiOperation(value = "ID查询过多投标人淘汰")
    @GetMapping("/getSecBidderEliminationById")
    public BaseResponse getSecBidderEliminationById(@RequestParam String sbeId) {
        log.info("getSecBidderEliminationById");
        return secFeign.getSecBidderEliminationById(sbeId);
    }
}
