package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.request.StorageChangeRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "评标评委专家入库", value = "评标评委专家入库")
@RequestMapping("/v1/storageChange")
public class StorageChangeController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation("新增修改评标评委专家入库")
    @PostMapping("/createOrUpdateStorageChange")
    public BaseResponse createOrUpdateStorageChange(@RequestBody StorageChangeRequest request) {
        return secFeign.createOrUpdateStorageChange(request);
    }

    @ApiOperation("分页查询评标评委专家入库")
    @PostMapping("/getStorageChangeList")
    public BaseResponse getStorageChangeList(@RequestBody StorageChangeRequest request) {
        return secFeign.getStorageChangeList(request);
    }

}
