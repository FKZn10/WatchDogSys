package com.jpmsoft.projectProducer.controller.v1.actualMeasurement;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.CheckItemRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.service.actualMeasurement.ICheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/CheckItem")
public class CheckItemController {

    @Autowired
    private ICheckItemService iCheckItemService;

    @TenantId
    @PostMapping("/createOrUpdateCheckItem")
    public BaseResponse createOrUpdateCheckItem(@RequestBody CheckItemRequest request, @RequestHeader String token) {
        log.info("createOrUpdateCheckItem");
        return iCheckItemService.createOrUpdateCheckItem(request,token);
    }


    @GetMapping("/deleteCheckItem")
    public BaseResponse deleteCheckItem(@RequestParam String id) {
        log.info("deleteCheckItem");
        return iCheckItemService.deleteCheckItem(id);
    }

    @GetMapping("/getCheckItemById")
    public BaseResponse getCheckItemById(@RequestParam String id) {
        log.info("getCheckItemById");
        return iCheckItemService.getCheckItemById(id);
    }

    @GetMapping("/getCheckItemTree")
    public BaseResponse getCheckItemTree(@RequestHeader String token) {
        log.info("getCheckItemTree");
        return iCheckItemService.getCheckItemTree(token);
    }
    @GetMapping("/upOrDownCheckItem")
    public BaseResponse upOrDownCheckItem(@RequestParam String ciId, @RequestParam String action) {
        log.info("upOrDownCheckItem");
        return iCheckItemService.upOrDownCheckItem(ciId, action);
    }

    @GetMapping("/selectAll")
    public BaseResponse selectAll(@RequestHeader String token){
        log.info("selectAll");
        return iCheckItemService.selectAll(token);
    }

    @PostMapping("/onDragCheckItem")
    public BaseResponse onDragCheckItem(@RequestBody OnDragDto request) {
        log.info("onDragCheckItem");
        return iCheckItemService.onDragCheckItem(request);
    }

    @TenantId
    @PostMapping("/getCheckItemList")
    public BaseResponse getCheckItemList(@RequestBody CheckItemRequest request) {
        log.info("getCheckItemList");
        return iCheckItemService.getCheckItemList(request);
    }

}
