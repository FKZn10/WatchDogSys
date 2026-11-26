package com.jpmsoft.projectProducer.service.actualMeasurement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistributionCheck;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jjz
 */
public interface ITaskDistributionCheckService extends IService<TaskDistributionCheck> {

    void createOrUpdateTaskDistributionCheck(List<TaskDistributionCheckModel> taskDistributionCheckModels, String tdId);


    BaseResponse createOrUpdateTaskDistributionCheckSingle(TaskDistributionCheckModel model);

    BaseResponse createOrUpdateTaskDistributionCheckNew(List<TaskDistributionCheckModel> taskDistributionCheckModels);

    List<TaskDistributionCheckModel> getTaskDistributionCheckByTdId(String tdId);

    BaseResponse deleteByTdId(String tdId);

    BaseResponse backWriteTaskDistributionCheck(TaskDistributionCheckModel request);

    List<TaskDistributionCheck> getTaskDistributionCheckByCiId(String ciId);

    BaseResponse getTaskDistributionCheckList(TaskDistributionCheckModel request, String uid);

    BaseResponse getTaskDistributionCheckByDmId(TaskDistributionCheckModel request);

    BaseResponse getTaskDistributionCheckByCreateTimeNew(TaskDistributionCheckModel request);

    BaseResponse generateQualityCheckReport(QsRecordRequest request,String token);
}
