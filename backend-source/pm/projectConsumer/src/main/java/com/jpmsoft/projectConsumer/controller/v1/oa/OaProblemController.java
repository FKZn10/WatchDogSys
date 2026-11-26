package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "OA问题登记反馈-项目问题登记表", value = "OA问题登记反馈-项目问题登记表")
@RequestMapping("/OA/Problem")
public class OaProblemController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改项目问题登记表")
    @PostMapping("/createOrUpdateOaProblem")
    public BaseResponse createOrUpdateOaProblem(@RequestBody OaProblemModel request) {
        log.info("createOrUpdateOaProblem");
        return oaFeign.createOrUpdateOaProblem(request);
    }

    @ApiOperation(value = "分页查询项目问题登记表")
    @PostMapping("/getOaProblemList")
    public BaseResponse getOaProblemList(@RequestBody OaProblemModel request, @RequestHeader String token) {
        log.info("getOaProblemList");
        return oaFeign.getOaProblemList(request, token);
    }

    @ApiOperation(value = "ID查询项目问题登记表")
    @GetMapping("/getOaProblemById")
    public BaseResponse getOaProblemById(@RequestParam String pmId) {
        log.info("getOaProblemById");
        return oaFeign.getOaProblemById(pmId);
    }
}
