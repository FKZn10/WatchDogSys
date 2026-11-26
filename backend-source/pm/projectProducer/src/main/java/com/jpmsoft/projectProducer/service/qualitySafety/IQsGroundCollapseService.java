package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsGroundCollapseModel;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsGroundCollapse;

public interface IQsGroundCollapseService extends IService<QsGroundCollapse> {

    BaseResponse createOrUpdateQsGroundCollapse(QsGroundCollapseModel request);

    BaseResponse getQsGroundCollapseList(QsGroundCollapseModel request, String uId);

    BaseResponse getQsGroundCollapseById(String gcId);
}
