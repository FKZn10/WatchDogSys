package com.jpmsoft.projectProducer.controller.v1.actualMeasurement;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingItemRequest;
import com.jpmsoft.projectProducer.service.actualMeasurement.IDrawingItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/drawingItem")
public class drawingItemController {

    @Autowired
    private IDrawingItemService iDrawingItemService;
    @PostMapping("/createOrUpdateDrawingItem")
    public BaseResponse createOrUpdateDrawingItem(@RequestBody DrawingItemRequest request, @RequestHeader String token){
        log.info("createOrUpdateDrawingItem");
        return iDrawingItemService.createOrUpdateDrawingItem(request,token);
    }

        @PostMapping("/getDrawingItemList")
    public BaseResponse getDrawingItemList(@RequestBody DrawingItemRequest request, @RequestHeader String token){
        log.info("getDrawingItemList");
        return iDrawingItemService.getDrawingItemList(request,token);
    }


    @GetMapping("/getDrawingItemById")
    public BaseResponse getDrawingItemById(@RequestParam String id){
        log.info("getDrawingItemById");
        return iDrawingItemService.getDrawingItemById(id);
    }

    // 获取图纸目录列表
    @GetMapping("/getDrawingItemListByDmId")
    public BaseResponse getDrawingItemListByDmId(@RequestParam String dmId){
        log.info("getDrawingItemListByDmId");
        return iDrawingItemService.getDrawingItemListByDmId(dmId);
    }
}
