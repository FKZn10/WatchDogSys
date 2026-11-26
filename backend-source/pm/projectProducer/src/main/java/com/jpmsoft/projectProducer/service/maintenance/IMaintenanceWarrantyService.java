package com.jpmsoft.projectProducer.service.maintenance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceWarrantyRequest;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceWarranty;

public interface IMaintenanceWarrantyService extends IService<MaintenanceWarranty> {


    BaseResponse createOrUpdateMaintenanceWarranty(MaintenanceWarrantyRequest request);

    BaseResponse getMaintenanceWarrantyList(MaintenanceWarrantyRequest request, String uId);

    BaseResponse getMaintenanceWarrantyListNoAuth(MaintenanceWarrantyRequest request);

    BaseResponse getMaintenanceWarrantyById(String mwId);

}
