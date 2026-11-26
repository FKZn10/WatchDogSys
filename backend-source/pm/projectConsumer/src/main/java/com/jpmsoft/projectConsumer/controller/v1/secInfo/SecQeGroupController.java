package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecQeGroupRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "资格审查小组", value = "资格审查小组")
@RequestMapping("/v1/qeGroup")
public class SecQeGroupController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改资格审查小组")
    @PostMapping("/createOrUpdateSecQeGroup")
    public BaseResponse createOrUpdateSecQeGroup(@RequestBody SecQeGroupRequest request) {
        log.info("createOrUpdateSecQeGroup");
        return secFeign.createOrUpdateSecQeGroup(request);
    }

    @ApiOperation(value = "ID删除资格审查小组")
    @GetMapping("/deleteSecQeGroupByQegId")
    public BaseResponse deleteSecQeGroupByQegId(@RequestParam String qegId) {
        log.info("deleteSecQeGroupByQegId");
        return secFeign.deleteSecQeGroupByQegId(qegId);
    }

    @ApiOperation(value = "ID查询资格审查小组")
    @GetMapping("/getSecQeGroupByQegId")
    public BaseResponse getSecQeGroupByQegId(@RequestParam String qegId) {
        log.info("getSecQeGroupByQegId");
        return secFeign.getSecQeGroupByQegId(qegId);
    }
}
