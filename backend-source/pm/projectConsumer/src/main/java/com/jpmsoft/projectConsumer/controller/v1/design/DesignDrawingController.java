package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "图纸管理", value = "图纸管理")
@RequestMapping("/design/v1/designDrawing")
public class DesignDrawingController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改图纸管理")
    @PostMapping("/createOrUpdateDesignDrawing")
    public BaseResponse createOrUpdateDesignDrawing(@RequestBody DesignDrawingModel request) {
        log.info("createOrUpdateDesignDrawing");
        return baseFeign.createOrUpdateDesignDrawing(request);
    }

    @ApiOperation(value = "分页查询图纸管理")
    @PostMapping("/getDesignDrawingList")
    public BaseResponse getDesignDrawingList(@RequestBody DesignDrawingModel request, @RequestHeader String token) {
        log.info("getDesignDrawingList");
        return baseFeign.getDesignDrawingList(request, token);
    }

    @ApiOperation(value = "分页查询图纸管理-不鉴权")
    @PostMapping("/getDesignDrawingListNoAuth")
    public BaseResponse getDesignDrawingListNoAuth(@RequestBody DesignDrawingModel request) {
        log.info("getDesignDrawingListNoAuth");
        return baseFeign.getDesignDrawingListNoAuth(request);
    }

    @ApiOperation(value = "ID查询图纸管理")
    @GetMapping("/getDesignDrawingById")
    public BaseResponse getDesignDrawingById(@RequestParam String ddId) {
        log.info("getDesignDrawingById");
        return baseFeign.getDesignDrawingById(ddId);
    }

    @ApiOperation(value = "分页查询图纸管理-润微")
    @PostMapping("/getDesignDrawingListRW")
    public BaseResponse getDesignDrawingListRW(@RequestBody DesignDrawingModel request, @RequestHeader String token) {
        log.info("getDesignDrawingListRW");
        return baseFeign.getDesignDrawingListRW(request, token);
    }
}
