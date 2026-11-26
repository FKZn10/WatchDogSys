package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthSearchRequest;
import com.jpmsoft.common.model.response.InvestmentControl.GetCreateFieldDataRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvCompleteMonthResponse;
import com.jpmsoft.common.model.response.InvestmentControl.TransferStatusUpdateRequest;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import org.apache.poi.ss.usermodel.Workbook;

import java.math.BigDecimal;
import java.util.List;

public interface IInvCompleteMonthService extends IService<InvCompleteMonth> {

    BaseResponse createOrUpdateInvCompleteMonth(InvCompleteMonthRequest request);

    BaseResponse getInvCompleteMonthList(InvCompleteMonthRequest request, String uId);

    PageResult<InvCompleteMonthResponse> getInvCompleteMonthSearchList(InvCompleteMonthSearchRequest request, String uId);

    List<ClassInfoAnnotation> getInvSearchTableList();

    BaseResponse getInvCompleteMonthByIcmId(String icmId);

    BaseResponse canToCreateInvCompleteMonth(InvCompleteMonthRequest request);


    BaseResponse exportInvCompleteMonthStatistics(InvCompleteMonthRequest request);

    /**
     * 根据项目ID获取其最后一次审批时的累计完成概算投资
     *
     * @param pId 项目ID
     * @return
     */
    BigDecimal getLastIcmBudgetInvestmentByPid(String pId);

    /**
     * 根据项目ID获取当年审批通过的的累计完成概算投资
     *
     * @param pId  项目ID
     * @param year 年份
     * @return
     */
    BigDecimal getCurrentYearIcmBudgetInvByPid(String pId, String year);

    /**
     * 修改单据流转状态
     * @param request
     * @param token
     * @return
     */
    BaseResponse updateTransferStatus(TransferStatusUpdateRequest request, String token);

    /**
     * 获取资金计划创建初始值
     * @param request
     * @return
     */
    BaseResponse getCreateFieldData(GetCreateFieldDataRequest request);

    /**
     * 项目主管单位经办人资金计划统计分析报表
     * @param request
     * @param uId
     * @return
     */
    BaseResponse countInvCompleteMonth(InvCompleteMonthRequest request, String uId);

    /**
     * 集团经办人资金计划统计分析报表
     * @param request
     * @param uId
     * @return
     */
    BaseResponse countInvCompleteMonthForAll(InvCompleteMonthRequest request, String uId);

    Workbook countInvCompleteMonthExportExcel(InvCompleteMonthRequest request, String uId);

    Workbook invCompleteMonthListExportExcel(InvCompleteMonthRequest request, String uId);

    Workbook countInvCompleteMonthForAllExportExcel(InvCompleteMonthRequest request, String uId);

    List<InvCompleteMonth> getActualInvestment(List<String> ipvIds);

    BaseResponse createOrUpdateInvCompleteMonthXz(InvCompleteMonthRequest request);

    /**
     * 获取上一个月的数据
     * @param request
     * @return
     */
    BaseResponse getNearCompleteMonthData(GetCreateFieldDataRequest request);


    BaseResponse getMonthlyInvestmentSum(String pid,String year,Integer month);

    /**
     * 批量完成后续的月完成情况
     *
     * @param request
     * @return
     */
    BaseResponse completeSubsequentInvCompleteMonth(TransferStatusUpdateRequest request);

    /**
     * 根据ipvId列表，重新计算年度累计完成投资额和完成百分比
     *
     * @param ipvIds
     * @return
     */
    BaseResponse refreshIcmAnnualInvestment(List<String> ipvIds);

    /**
     * 重新计算所有的年度累计完成投资额和完成百分比
     *
     * @return
     */
    BaseResponse refreshAllIcmAnnualInvestment();

    /**
     * 获取月度投资完成统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getInvCompleteMonthStatisticsOverviewList(InvCompleteMonthRequest request, String teId);
}
