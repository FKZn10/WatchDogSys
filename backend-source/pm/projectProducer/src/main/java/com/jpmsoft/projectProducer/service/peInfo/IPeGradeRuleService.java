package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeGradeRuleRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeGradeRule;

public interface IPeGradeRuleService extends IService<PeGradeRule> {

    BaseResponse createOrUpdatePeGradeRule(PeGradeRuleRequest request);

    BaseResponse getPeGradeRuleList(PeGradeRuleRequest request, String uId);

    BaseResponse deletePeGradeRuleByRId(String rId);

}
