package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingReport;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingReportVo;

public interface FinalAccountingReportService extends IService<FinalAccountingReport> {
    BaseResponse getFinalAccountsList(FinalAccountingReportVo request, String uId);

    BaseResponse createOrUpdateFinalAccounts(FinalAccountingReportVo request, String uId);

    BaseResponse getFinalAccountsById(String id);

    BaseResponse getInvestmentBudgetByPId(String pId);

    BaseResponse deleteFinalAccountsById(String id);

    BaseResponse queryFinalAccountsClassInfo(FinalAccountingReportVo request);
}
