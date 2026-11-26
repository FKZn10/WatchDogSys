package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportClassInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecEvalReport;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecEvalReportService extends IService<SecEvalReport> {

    BaseResponse createOrUpdateEvalReport(SecEvalReportRequest request);

    BaseResponse cancelSecEvalReportByErId(String erId);

    BaseResponse getSecEvalReportByErId(String erId);


    BaseResponse getSecEvalReportBySecId(String secId);

    BaseResponse getSecEvalReportList(SecEvalReportClassInfoRequest request, LogUserInfoVo user);

    BaseResponse getSecEvalReportListClassInfo(SecEvalReportClassInfoRequest request, LogUserInfoVo user);

    BaseResponse getSecEvalReportNoAuthList(SecEvalReportClassInfoRequest request);
}
