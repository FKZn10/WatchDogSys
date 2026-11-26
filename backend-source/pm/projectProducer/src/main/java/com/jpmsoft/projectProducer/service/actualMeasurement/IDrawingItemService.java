package com.jpmsoft.projectProducer.service.actualMeasurement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.CheckItemRequest;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingItemRequest;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingManagementCatalogueRequest;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingItem;


public interface IDrawingItemService extends IService<DrawingItem> {


    BaseResponse createOrUpdateDrawingItem(DrawingItemRequest request, String token);

    BaseResponse getDrawingItemList(DrawingItemRequest request, String token);

    BaseResponse getDrawingItemById(String id);


    BaseResponse getDrawingItemListByDmId(String dmId);
}
