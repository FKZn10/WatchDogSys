package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.entity.SysFlowExplain;


public interface ISysFlowExplainService extends IService<SysFlowExplain> {

    BaseResponse createOrUpdateFlowExplain(SysFlowExplain request);

    BaseResponse getFlowExplainList(SysFlowExplain request);

    BaseResponse delFlowExplainById(String feId);
}
