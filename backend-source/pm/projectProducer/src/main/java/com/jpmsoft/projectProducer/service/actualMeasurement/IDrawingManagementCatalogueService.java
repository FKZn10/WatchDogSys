package com.jpmsoft.projectProducer.service.actualMeasurement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingManagementCatalogueRequest;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingManagementCatalogue;


public interface IDrawingManagementCatalogueService extends IService<DrawingManagementCatalogue> {

    BaseResponse createOrUpdateDrawingManagementCatalogue(DrawingManagementCatalogueRequest request, String token);

    BaseResponse getDrawingManagementCatalogueTree(String pId,String token);

    BaseResponse deleteDrawingManagementCatalogueById(String id);

    BaseResponse getDrawingManagementCatalogueById(String id);

    BaseResponse getDrawingManagementCatalogueTreeByUser(DrawingManagementCatalogueRequest request);

    BaseResponse upOrDownDrawingManagementCatalogue(String id, String action);

    BaseResponse onDragDrawingManagementCatalogue(OnDragDto request);
}
