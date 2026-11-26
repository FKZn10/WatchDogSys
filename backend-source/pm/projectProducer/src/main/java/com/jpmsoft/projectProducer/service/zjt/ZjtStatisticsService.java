package com.jpmsoft.projectProducer.service.zjt;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zjt.ProjectUpStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.dto.zjt.ZjtProjectExportDTO;

public interface ZjtStatisticsService {

    BaseResponse getProjectUpStatistics(ProjectUpStatisticsDTO request);


    BaseResponse exportProject(ZjtProjectExportDTO request,String token);
}
