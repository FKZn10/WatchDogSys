package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblem;

/**
 * 项目问题登记表
 */
public interface IOaProblemService extends IService<OaProblem> {

    BaseResponse createOrUpdateOaProblem(OaProblemModel request);

    BaseResponse getOaProblemList(OaProblemModel request, String uId);

    BaseResponse getOaProblemById(String pmId);

}
