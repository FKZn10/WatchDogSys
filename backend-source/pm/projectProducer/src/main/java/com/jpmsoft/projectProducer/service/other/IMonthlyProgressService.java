package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ProgressStatisticsRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectActivityDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.TInformationFilling;

public interface IMonthlyProgressService extends IService<TInformationFilling> {
    BaseResponse getContractAmount(String year, String month, String pId);


    BaseResponse getProjectActivity(ProjectActivityDto request, String uid);

    BaseResponse selectAreaAndValue(String year, String month, String oId);

    /**
     * 政府投资工程项目 进展情况统计表
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse getProgressStatisticsList(ProgressStatisticsRequest request, String token);
}
