package com.jpmsoft.projectProducer.service.actualMeasurement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.TaskDistributionRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistribution;

import java.util.List;

/**
 * @author jjz
 */
public interface ITaskDistributionService extends IService<TaskDistribution> {


    BaseResponse createOrUpdateTaskDistributionAndCheck(TaskDistributionRequest request);

    BaseResponse batchCreateTaskDistributionByDmIds(TaskDistributionRequest request);

    BaseResponse createOrUpdateTaskDistributionCheckNew(List<TaskDistributionCheckModel> request);

    BaseResponse getTaskDistributionByTdId(String tdId);


    BaseResponse getTaskDistributionList(TaskDistributionRequest request, String uid);

    BaseResponse deleteTaskDistributionByTdId(String tdId);

    BaseResponse deleteTaskDistributionCheckByTdId(String tdId);

    List<TaskDistributionCheckModel> getTaskDistributionCheckByDiId(String diId);

    BaseResponse getTaskDistributionByDiId(String diId);

    BaseResponse getTaskDistributionByDmId(String dmId);

    List<TaskDistributionCheckModel> getTaskDistributionCheckByDmId(String diId);
}
