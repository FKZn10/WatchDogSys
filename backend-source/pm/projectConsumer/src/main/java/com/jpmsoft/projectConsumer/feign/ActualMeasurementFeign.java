package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.*;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.projectConsumer.pojo.dto.common.OnDragDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 档案
 */
@FeignClient(name = "project-producer", contextId = "ActualMeasurement")
public interface ActualMeasurementFeign {

    /***********************************************检查项***************************************************/
    @PostMapping("/v1/CheckItem/createOrUpdateCheckItem")
    BaseResponse createOrUpdateCheckItem(@RequestBody CheckItemRequest request,@RequestHeader String token);

    @GetMapping("/v1/CheckItem/deleteCheckItem")
    BaseResponse deleteCheckItem(@RequestParam String id);

    @GetMapping("/v1/CheckItem/getCheckItemById")
    BaseResponse getCheckItemById(@RequestParam String id);

    @GetMapping("/v1/CheckItem/getCheckItemTree")
    BaseResponse getCheckItemTree(@RequestHeader String token);

    @GetMapping("/v1/CheckItem/upOrDownCheckItem")
    BaseResponse upOrDownCheckItem(@RequestParam String ciId,@RequestParam String action);

    @GetMapping("/v1/CheckItem/selectAll")
    BaseResponse selectAll(@RequestHeader String token);

    @PostMapping("/v1/CheckItem/onDragCheckItem")
    BaseResponse onDragCheckItem(@RequestBody OnDragDto request);

    @PostMapping("/v1/CheckItem/getCheckItemList")
    BaseResponse getCheckItemList(@RequestBody CheckItemRequest request);

    /***********************************************图纸目录***************************************************/
    @PostMapping("/v1/drawingManagementCatalogue/createOrUpdateDrawingManagementCatalogue")
    BaseResponse createOrUpdateDrawingManagementCatalogue(@RequestBody DrawingManagementCatalogueRequest request,@RequestHeader String token);

    @GetMapping("/v1/drawingManagementCatalogue/getDrawingManagementCatalogueTree")
    BaseResponse getDrawingManagementCatalogueTree(@RequestParam String pId,@RequestHeader String token);

    @GetMapping("/v1/drawingManagementCatalogue/deleteDrawingManagementCatalogue")
    BaseResponse deleteDrawingManagementCatalogueById(@RequestParam String id);

    @GetMapping("/v1/drawingManagementCatalogue/getDrawingManagementCatalogueById")
    BaseResponse getDrawingManagementCatalogueById(@RequestParam String id);

    @PostMapping("/v1/drawingManagementCatalogue/getDrawingManagementCatalogueTreeByUser")
    BaseResponse getDrawingManagementCatalogueTreeByUser(@RequestBody DrawingManagementCatalogueRequest request);

    @GetMapping("/v1/drawingManagementCatalogue/upOrDownDrawingManagementCatalogue")
    BaseResponse upOrDownDrawingManagementCatalogue(@RequestParam String id,@RequestParam String action);

    @PostMapping("/v1/drawingManagementCatalogue/onDragDrawingManagementCatalogue")
    BaseResponse onDragDrawingManagementCatalogue(@RequestBody OnDragDto request);
    /***********************************************图纸管理***************************************************/
    @PostMapping("/v1/drawingItem/createOrUpdateDrawingItem")
    BaseResponse createOrUpdateDrawingItem(@RequestBody DrawingItemRequest request, @RequestHeader String token);

    @PostMapping("/v1/drawingItem/getDrawingItemList")
    BaseResponse getDrawingItemList(@RequestBody DrawingItemRequest request,@RequestHeader String token);

    @GetMapping("/v1/drawingItem/getDrawingItemById")
    BaseResponse getDrawingItemById(@RequestParam String id);

    @GetMapping("/v1/drawingItem/getDrawingItemListByDmId")
    BaseResponse getDrawingItemListByDmId(@RequestParam String dmId);

    /***********************************************任务分配***************************************************/
    @PostMapping("/v1/taskDistribution/createOrUpdateTaskDistributionAndCheck")
    BaseResponse createOrUpdateTaskDistributionAndCheck(@RequestBody TaskDistributionRequest request);

    @PostMapping("/v1/taskDistribution/batchCreateTaskDistributionByDmIds")
    BaseResponse batchCreateTaskDistributionByDmIds(@RequestBody TaskDistributionRequest request);

    @GetMapping("/v1/taskDistribution/getTaskDistributionByTdId")
    BaseResponse getTaskDistributionByTdId(@RequestParam String tdId);

    @PostMapping("/v1/taskDistribution/getTaskDistributionList")
    BaseResponse getTaskDistributionList(@RequestBody TaskDistributionRequest request, @RequestHeader String token);

    @GetMapping("/v1/taskDistribution/deleteTaskDistributionByTdId")
    BaseResponse deleteTaskDistributionByTdId(@RequestParam String tdId);

    @GetMapping("/v1/taskDistribution/deleteTaskDistributionCheckByTdId")
    BaseResponse deleteTaskDistributionCheckByTdId(@RequestParam String tdId);

    @PostMapping("/v1/taskDistribution/backWriteTaskDistributionCheck")
    BaseResponse backWriteTaskDistributionCheck(@RequestBody TaskDistributionCheckModel request);

    @GetMapping("/v1/taskDistribution/getTaskDistributionCheckByCiId")
    BaseResponse getTaskDistributionCheckByCiId(@RequestParam String ciId);

    @GetMapping("/v1/taskDistribution/getTaskDistributionCheckByDiId")
    BaseResponse getTaskDistributionCheckByDiId(@RequestParam String diId);

    @PostMapping("/v1/taskDistribution/getTaskDistributionCheckList")
    BaseResponse getTaskDistributionCheckList(@RequestBody TaskDistributionCheckModel request, @RequestHeader String token);

    @PostMapping("/v1/taskDistribution/getTaskDistributionCheckByDmId")
    BaseResponse getTaskDistributionCheckByDmId(@RequestBody TaskDistributionCheckModel request);

    @PostMapping("/v1/taskDistribution/getTaskDistributionCheckByCreateTimeNew")
    BaseResponse getTaskDistributionCheckByCreateTimeNew(@RequestBody TaskDistributionCheckModel request);

    @PostMapping("/v1/taskDistribution/createOrUpdateTaskDistributionCheckNew")
    BaseResponse createOrUpdateTaskDistributionCheckNew(@RequestBody List<TaskDistributionCheckModel> request);

    @GetMapping("/v1/taskDistribution/getTaskDistributionByDiId")
    BaseResponse getTaskDistributionByDiId(@RequestParam String diId);

    @GetMapping("/v1/taskDistribution/getTaskDistributionByDmId")
    BaseResponse getTaskDistributionByDmId(@RequestParam String dmId);

    @PostMapping("/v1/taskDistribution/generateQualityCheckReport")
    BaseResponse generateQualityCheckReport(@RequestBody QsRecordRequest request,@RequestHeader String token);

    @PostMapping("/v1/taskDistribution/createOrUpdateTaskDistributionCheckSingle")
    BaseResponse createOrUpdateTaskDistributionCheckSingle(@RequestBody TaskDistributionCheckModel request);

    /***********************************************设备管理***************************************************/
    @PostMapping("/v1/equipmentManagement/getEquipmentManagementList")
    BaseResponse getEquipmentManagementList(@RequestBody EquipmentManagementRequest request,@RequestHeader String token);



}
