package com.jpmsoft.projectProducer.service.actualMeasurement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingManagementCatalogueRequest;
import com.jpmsoft.common.model.request.ActualMeasurement.EquipmentManagementRequest;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingManagementCatalogue;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.EquipmentManagement;


public interface IEquipmentManagementService extends IService<EquipmentManagement> {

    BaseResponse getEquipmentManagementList(EquipmentManagementRequest request, String token);
}
