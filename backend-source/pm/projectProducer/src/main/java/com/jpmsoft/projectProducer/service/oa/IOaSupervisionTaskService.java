package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaSupervisionTaskRequest;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaSupervisionTask;

public interface IOaSupervisionTaskService extends IService<OaSupervisionTask> {

    BaseResponse createOrUpdateOaSupervisionTask(OaSupervisionTaskRequest request);

    BaseResponse getOaSupervisionTaskList(OaSupervisionTaskRequest request, String uId);

    BaseResponse getOaSupervisionTaskByStId(String stId);

    BaseResponse exportOaSupervisionTaskStatistics(OaSupervisionTaskRequest request);

}
