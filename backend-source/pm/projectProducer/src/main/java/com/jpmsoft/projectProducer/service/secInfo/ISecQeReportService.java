package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecQeReportRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeReport;

public interface ISecQeReportService extends IService<SecQeReport> {

    BaseResponse createOrUpdateQeReport(SecQeReportRequest request);

    BaseResponse getQeReportList(SecQeReportRequest request);

    BaseResponse deleteQeReportByQerId(String qerId);

    BaseResponse getQeReportByQerId(String qerId);
}
