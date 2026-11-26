package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeMeetingApprovalRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 考核评分上会呈批
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 11:23
 */
@RestController
@Api(tags = "考核评分上会呈批", value = "考核评分上会呈批")
@RequestMapping("/v1/peMeetingApproval")
public class PeMeetingApprovalController {

    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "创建或修改考核评分上会呈批")
    @PostMapping("/createOrUpdatePeMeetingApproval")
    public BaseResponse createOrUpdatePeMeetingApproval(@RequestBody PeMeetingApprovalRequest request) {
        return peInfoFeign.createOrUpdatePeMeetingApproval(request);
    }

    @ApiOperation(value = "分页查询考核评分上会呈批")
    @PostMapping("/getPeMeetingApprovalList")
    public BaseResponse getPeMeetingApprovalList(@RequestBody PeMeetingApprovalRequest request, @RequestHeader String token) {
        return peInfoFeign.getPeMeetingApprovalList(request, token);
    }

    @ApiOperation(value = "ID查询考核评分上会呈批")
    @GetMapping("/getPeMeetingApprovalById")
    public BaseResponse getPeMeetingApprovalById(@RequestParam String maId) {
        return peInfoFeign.getPeMeetingApprovalById(maId);
    }


    @GetMapping("/updatePeStateByMaId")
    public BaseResponse updatePeStateByMaId(@RequestParam String peState,@RequestParam String maId) {
        return  peInfoFeign.updatePeStateByMaId(peState,maId);
    }
}
