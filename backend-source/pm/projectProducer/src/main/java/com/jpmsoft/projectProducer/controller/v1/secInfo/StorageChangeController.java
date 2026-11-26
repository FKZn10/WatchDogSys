package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.StorageChangeRequest;
import com.jpmsoft.projectProducer.service.secInfo.StorageChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("v1/storageChange")
public class StorageChangeController {

    @Resource
    private StorageChangeService storageChangeService;

    @PostMapping("/createOrUpdateStorageChange")
    public BaseResponse createOrUpdateStorageChange(@RequestBody StorageChangeRequest request) {
        return storageChangeService.createOrUpdateStorageChange(request);
    }

    @PostMapping("/getStorageChangeList")
    public BaseResponse getStorageChangeList(@RequestBody StorageChangeRequest request) {
        return storageChangeService.getStorageChangeList(request);
    }

}
