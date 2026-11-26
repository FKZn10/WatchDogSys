package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdown;

/**
 * @author yb.Sun
 * @date 2024/10/25 15:48
 */
public interface IInvestmentEstimateBreakdownService extends IService<InvestmentEstimateBreakdown> {

    /**
     * 保存投资估算分解
     * @param model
     * @return
     */
    BaseResponse createOrUpdateInvestmentEstimateBreakdown(InvestmentEstimateBreakdownModel model);

    /**
     * 列表
     * @param model
     * @return
     */
    BaseResponse getInvestmentEstimateBreakdownList(InvestmentEstimateBreakdownModel model,String uId);

    /**
     * 详情
     * @param id
     * @return
     */
    BaseResponse getInvestmentEstimateBreakdownById(String id);


    BaseResponse deleteInvestmentEstimateBreakdownById(String id);

    BaseResponse removeInvestmentEstimateBreakdownById(String id);

    BaseResponse getProjectIdList(InvestmentEstimateBreakdownModel request);

    BaseResponse getInvestmentEstimateBreakdownByProjectId(String projectId);

    /**
     * 通过项目获取估算分解
     * @param pId
     * @return
     */
    BaseResponse getInvestmentEstimateBreakdownByPId(String pId);

    BaseResponse getUnusedProjectIdList(InvestmentEstimateBreakdownModel request);

    BaseResponse getLatestInvestmentEstimateBreakdownById(String id);
}
