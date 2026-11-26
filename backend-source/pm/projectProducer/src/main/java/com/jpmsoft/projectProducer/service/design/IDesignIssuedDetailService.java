package com.jpmsoft.projectProducer.service.design;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedDetailModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;

import com.jpmsoft.projectProducer.pojo.entity.design.DesignIssuedDetail;

import java.util.List;


public interface IDesignIssuedDetailService extends IService<DesignIssuedDetail> {

    void createOrUpdateDesignIssueReissueDetail(DesignResultsIssuedModel request, String driId,String token);

    List<DesignIssuedDetailModel> getDesignIssueReissueDetailById(String id);
}
