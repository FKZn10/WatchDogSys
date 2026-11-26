package com.jpmsoft.projectProducer.service.actualMeasurement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.CheckItemRequest;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.CheckItem;


public interface ICheckItemService extends IService<CheckItem> {
    BaseResponse createOrUpdateCheckItem(CheckItemRequest request, String token);

    BaseResponse deleteCheckItem(String id);

    BaseResponse getCheckItemById(String id);

    BaseResponse getCheckItemTree(String token);

    BaseResponse upOrDownCheckItem(String ciId, String action);

    BaseResponse selectAll(String token);

    BaseResponse onDragCheckItem(OnDragDto request);

    BaseResponse getCheckItemList(CheckItemRequest request);

}
