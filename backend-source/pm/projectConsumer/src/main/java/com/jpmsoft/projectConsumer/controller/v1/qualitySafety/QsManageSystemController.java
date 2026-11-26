package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.qualitySafety.QsManageSystemDto;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "质量管理体系", value = "质量管理体系")
@RequestMapping("/safety/v1/qsManageSystem")
public class QsManageSystemController {

    @Autowired
    private BaseFeign baseFeign;


    @ApiOperation(value = "创建或修改质量管理体系")
    @PostMapping("/createOrUpdateQsManageSystem")
    public BaseResponse createOrUpdateQsManageSystem(@RequestBody QsManageSystemDto request) {
        log.info("createOrUpdateQsManageSystem");
        return baseFeign.createOrUpdateQsManageSystem(request);
    }


    @ApiOperation(value = "获取质量管理体系列表")
    @PostMapping("/getQsManageSystemList")
    public BaseResponse getQsManageSystemList(@RequestBody QsManageSystemDto request, @RequestHeader String token) {
        log.info("getQsManageSystemList");
        return baseFeign.getQsManageSystemList(request, token);
    }

    @ApiOperation(value = "id查询质量管理体系")
    @GetMapping("/getQsManageSystemById")
    public BaseResponse getQsManageSystemById(@RequestParam String id) {
        log.info("getQsManageSystemById");
        return baseFeign.getQsManageSystemById(id);
    }

    @ApiOperation(value = "根据PId和备注查找岗位人员")
    @GetMapping("/getQsNodePerson")
    public BaseResponse getQsNodePersonByMark(@RequestParam String pId) {
        log.info("getQsNodePerson");
        return baseFeign.getQsNodePersonByMark(pId);
    }


}
