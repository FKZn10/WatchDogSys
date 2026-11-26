package com.jpmsoft.projectProducer.service.dh;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.dh.MonthlyInspectionReportDTO;
import com.jpmsoft.projectProducer.pojo.entity.dh.MonthlyInspectionReport;

public interface IMonthlyInspectionReportService extends IService<MonthlyInspectionReport> {
    BaseResponse getMonthlyInspectionReportList(MonthlyInspectionReportDTO request);

    BaseResponse getMonthlyInspectionReportListClassInfo(MonthlyInspectionReportDTO request);
}
