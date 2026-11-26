package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.EngineeringFundApplicationRejectRequest;
import com.jpmsoft.projectProducer.pojo.dto.fund.EngineeringFundApplicationDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplication;
import com.jpmsoft.projectProducer.pojo.request.fund.EngineeringFundApplicationRequest;
import com.jpmsoft.projectProducer.pojo.response.EFAResponse;

public interface IEngineeringFundApplicationService extends IService<EngineeringFundApplication> {
    /**
     * 创建或更新工程资金申请
     * @param request
     * @return
     */
    BaseResponse createOrUpdateEngineeringFundApplication(EngineeringFundApplicationDto request);

    BaseResponse approvalData(EngineeringFundApplicationRequest request);
    /**
     * 获取工程资金申请列表
     * @param request
     * @return
     */
    BaseResponse getEngineeringFundApplicationList(EngineeringFundApplicationRequest request,String uid);
    /**
     * 获取工程资金申请详情
     * @param id
     * @return
     */
    BaseResponse getEngineeringFundApplicationById(String id);

    /**
     * 获取资金文号信息
     * @param request
     * @return
     */
    BaseResponse getFundNumberInfo(EngineeringFundApplicationRequest request);

    BaseResponse getBankInfo(EngineeringFundApplicationRequest request);

    BaseResponse receiveCallback(EFAResponse response) ;

    BaseResponse sendData(EngineeringFundApplicationRequest request);

    BaseResponse reject(EngineeringFundApplicationRejectRequest engineeringFundApplicationRejectRequest);
}
