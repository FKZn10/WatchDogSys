package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundCollectionDetailsDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundCollectionDetails;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/17 14:34
 */
public interface IFundCollectionDetailsService extends IService<FundCollectionDetails> {

    BaseResponse createOrUpdateFundCollectionDetails(FundCollectionDetailsDto request);

    BaseResponse getFundCollectionDetailsList(FundCollectionDetailsDto request, String uId);

    /**
     * 资金收款项目清单
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getFundCollectionProjectList(PreProjectRequest request, String uId);

    BaseResponse getFundCollectionDetailsById(String id);

    /**
     * pid查询取该项目下最新的一条审批通过记录
     *
     * @param pId
     * @return
     */
    BaseResponse getLastFundCollectionDetailsByPid(String pId);

    BaseResponse statisticsBatchNoAndAmt(String pId);

    BaseResponse getProjectFundCollectionDetailsList(PreProjectRequest request, String uId);
}
