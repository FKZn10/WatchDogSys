package com.jpmsoft.projectProducer.service.zdyxy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.MaintenanceRecordModel;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;

public interface ZdyxyMaintenanceRecordService extends IService<MaintenanceRecord> {
    BaseResponse saveOrUpdateData(MaintenanceRecordModel request);

    BaseResponse approvalData(MaintenanceRecordModel request);

    BaseResponse getList(MaintenanceRecordModel request, String token);

    BaseResponse getListAuth(MaintenanceRecordModel request, String token);

    BaseResponse getDetail(String mrId);

    BaseResponse setMrRepairState(MaintenanceRecordModel request);

    void setRepairState(MaintenanceRecordModel request);

    BaseResponse syncApplicationWarrantyStatus(String id);

    BaseResponse updateRepairRecordStatus();
}
