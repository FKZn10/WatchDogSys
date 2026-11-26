package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeInspectionReportRequest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeInspectionReport;

public interface IMeInspectionReportService extends IService<MeInspectionReport> {

    BaseResponse createOrUpdateMeInspectionReport(MeInspectionReportRequest request);

    BaseResponse getMeInspectionReportList(MeInspectionReportRequest request, String uId);

    BaseResponse getMeInspectionReportByIrId(String irId);
}
