package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemDtlModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "OA问题登记反馈-项目问题详情（清单）", value = "OA问题登记反馈-项目问题详情（清单）")
@RequestMapping("/OA/ProblemDtl")
public class OaProblemDtlController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "分页查询项目问题详情（清单）")
    @PostMapping("/getOaProblemDtlList")
    public BaseResponse getOaProblemDtlList(@RequestBody OaProblemDtlModel request, @RequestHeader String token) {
        log.info("getOaProblemDtlList");
        return oaFeign.getOaProblemDtlList(request, token);
    }
}
