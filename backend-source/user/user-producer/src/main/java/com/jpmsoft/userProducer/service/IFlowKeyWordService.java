package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.FlowKeyWordDto;
import com.jpmsoft.userProducer.pojo.entity.FlowKeyWord;

public interface IFlowKeyWordService extends IService<FlowKeyWord> {

    /**
     * 获取流程关键字信息列表
     *
     * @param request
     * @return
     */
    BaseResponse getFlowKeyWordList(FlowKeyWordDto request);

    /**
     * 新增或修改流程关键字信息
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdateFlowKeyWord(FlowKeyWordDto request);

    /**
     * 删除流程关键字信息
     *
     * @param request
     * @return
     */
    BaseResponse DeleteFlowKeyWord(FlowKeyWordDto request);


    /**
     * 获取流程关键字信息Select
     *
     * @return
     */
    BaseResponse getFlowKeyWordSelect(String teId);
}
