package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeTempRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(value = "项目管理大纲体系模板-关联业务功能", tags = "项目管理大纲体系模板-关联业务功能")
@RequestMapping("/v1/projectOutline/nodeTemp")
public class OutlineNodeTempController {
    @Resource
    private OutlineFeign outlineFeign;

    @ApiOperation("新增体系模板-关联业务功能")
    @PostMapping("/createOutlineNodeTemp")
    public BaseResponse createOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token) {
        log.info("createOutlineNodeTemp");
        return outlineFeign.createOutlineNodeTemp(request, token);
    }

    @ApiOperation("分页查询体系模板-关联业务功能")
    @PostMapping("/getOutlineNodeTempList")
    public BaseResponse getOutlineNodeTempList(@RequestBody OutlineNodeTempRequest request) {
        log.info("getOutlineNodeTempList");
        return outlineFeign.getOutlineNodeTempList(request);
    }

    @ApiOperation("ID删除体系模板-关联业务功能")
    @GetMapping("/deleteOutlineNodeTempById")
    public BaseResponse deleteOutlineNodeTempById(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteOutlineNodeTempById");
        return outlineFeign.deleteOutlineNodeTempById(id, token);
    }

    @ApiOperation("更新体系模板-关联业务功能")
    @PostMapping("/updateOutlineNodeTemp")
    public BaseResponse updateOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token) {
        log.info("updateOutlineNodeTemp");
        return outlineFeign.updateOutlineNodeTemp(request, token);
    }

    @ApiOperation("自动保存体系模板-关联业务功能")
    @PostMapping("/autoSaveOutlineNodeTemp")
    public BaseResponse autoSaveOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineNodeTemp");
        return outlineFeign.autoSaveOutlineNodeTemp(request, token);
    }
}
