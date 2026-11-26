package com.jpmsoft.projectProducer.service.maintenance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceContactRequest;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceContact;

public interface IMaintenanceContactService extends IService<MaintenanceContact> {


    BaseResponse createOrUpdateMaintenanceContact(MaintenanceContactRequest request);

    BaseResponse getMaintenanceContactList(MaintenanceContactRequest request, String uId);

    BaseResponse getMaintenanceContactById(String mcId);

}
