package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.CalibrationReportDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.CalibrationReport;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 09:31
 */
public interface ICalibrationReportService extends IService<CalibrationReport> {

    BaseResponse createOrUpdateCalibrationReport(CalibrationReportDto request);

    BaseResponse createCalibrationReport(CalibrationReportDto request);

    BaseResponse updateCalibrationReport(CalibrationReportDto request);

    BaseResponse getCalibrationReportList(CalibrationReportDto request);

    BaseResponse getCalibrationReportById(String id);

    /**
     * 根据标段id查询审批通过的最新的一条定标报告信息
     *
     * @param secId
     * @return
     */
    BaseResponse getCalibrationReportBySecId(String secId);
}
