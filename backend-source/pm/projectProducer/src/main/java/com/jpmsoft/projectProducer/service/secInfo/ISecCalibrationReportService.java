package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecCalibrationReportRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationReport;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * 定标情况报告 Service 接口
 */
public interface ISecCalibrationReportService extends IService<SecCalibrationReport> {
    BaseResponse getSecCalibrationReportList(SecCalibrationReportRequest request, LogUserInfoVo user);

    BaseResponse getSecCalibrationReportClassInfo(SecCalibrationReportRequest request, LogUserInfoVo user);
}
