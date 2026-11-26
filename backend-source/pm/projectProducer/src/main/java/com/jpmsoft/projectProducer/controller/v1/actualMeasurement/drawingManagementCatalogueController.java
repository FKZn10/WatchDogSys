package com.jpmsoft.projectProducer.controller.v1.actualMeasurement;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingManagementCatalogueRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.service.actualMeasurement.IDrawingManagementCatalogueService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/drawingManagementCatalogue")
public class drawingManagementCatalogueController {
    @Autowired
    private IDrawingManagementCatalogueService iDrawingManagementCatalogueService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateDrawingManagementCatalogue")
    public BaseResponse createOrUpdateDrawingManagementCatalogue(@RequestBody DrawingManagementCatalogueRequest request, @RequestHeader String token) {
        log.info("createOrUpdateDrawingManagementCatalogue");
        return iDrawingManagementCatalogueService.createOrUpdateDrawingManagementCatalogue(request,token);
    }

    @GetMapping("/getDrawingManagementCatalogueTree")
    public BaseResponse getDrawingManagementCatalogueTree(@RequestParam String pId,@RequestHeader String token) {
        log.info("getDrawingManagementCatalogueTree");
         String uid = redisUtils.getUid(token);
        return iDrawingManagementCatalogueService.getDrawingManagementCatalogueTree(pId,token);
    }

    /**
     * 根据筛选条件 获取爆点数量
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getDrawingManagementCatalogueTreeByUser")
    public BaseResponse getDrawingManagementCatalogueTreeByUser(@RequestBody DrawingManagementCatalogueRequest request) {
        log.info("getDrawingManagementCatalogueTreeByUser");
        return iDrawingManagementCatalogueService.getDrawingManagementCatalogueTreeByUser(request);
    }

    @GetMapping("/deleteDrawingManagementCatalogue")
    public BaseResponse deleteDrawingManagementCatalogue(@RequestParam String id) {
        log.info("deleteDrawingManagementCatalogue");
        return iDrawingManagementCatalogueService.deleteDrawingManagementCatalogueById(id);
    }

    @GetMapping("/getDrawingManagementCatalogueById")
    public BaseResponse getDrawingManagementCatalogueById(@RequestParam String id) {
        log.info("getDrawingManagementCatalogueById");
        return iDrawingManagementCatalogueService.getDrawingManagementCatalogueById(id);
    }


    @GetMapping("/upOrDownDrawingManagementCatalogue")
    public BaseResponse upOrDownDrawingManagementCatalogue(@RequestParam String id, @RequestParam String action) {
        log.info("upOrDownDrawingManagementCatalogue");
        return iDrawingManagementCatalogueService.upOrDownDrawingManagementCatalogue(id, action);
    }

    @PostMapping("/onDragDrawingManagementCatalogue")
    public BaseResponse onDragDrawingManagementCatalogue(@RequestBody OnDragDto request) {
        log.info("onDragDrawingManagementCatalogue");
        return iDrawingManagementCatalogueService.onDragDrawingManagementCatalogue(request);
    }




}
