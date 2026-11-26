package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequestDto;
import com.jpmsoft.common.model.request.Contract.ConContractRequestNew;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractLedgerForWJTParam;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractStampDutyModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractBudgetDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractFundStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.apache.poi.ss.usermodel.Workbook;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface IConContractService extends IService<ConContract> {


    BaseResponse selectChContractSettlement(String conId);

    /**
     * 新增或修改合同
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateContract(ConContractRequest request);

    /**
     * 国资平台合同数据接入
     *
     * @param request
     * @return
     */
    BaseResponse saveForGZ(ConContractRequestDto request);

    /**
     * 新增或修改合同
     * 北京工行推送数据专用
     *
     * @param request
     * @return
     */
    BaseResponse BjCreateOrUpdateContract(ConContractRequest request);

    /**
     * 新增或修改合同
     * <p>
     * 同步创建合同支付计划、合同金额明细
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateContractWithMoneyPay(ConContractModel request);

    /**
     * 获取合同列表
     * 以及合同支付计划、合同金额明细
     *
     * @param request
     * @return
     */
    BaseResponse getContractWithMoneyPayList(ConContractModel request, String uId);

    /**
     * 获取合同列表
     *
     * @param request
     * @return
     */
    BaseResponse getContractList(ConContractRequest request, String uId);

    /**
     * 获取合同列表（广工）
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getContractGgList(ConContractRequest request, String uId);

    /**
     * 合同列表查询
     * 根据合同权限配置过滤
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getContractListByPermission(ConContractRequest request, String uId);

    /**
     * 危大工程获取合同列表
     *
     * @param request
     * @return
     */
    BaseResponse getContractListForDangerousProject(ConContractRequest request, String uId);

    /**
     * 获取合同列表 不鉴权
     *
     * @param request
     * @param user
     * @return
     */
    BaseResponse getContractListNoAuth(ConContractRequest request, LogUserInfoVo user);

    /**
     * 仅查询部分数据
     * 不健全查询所有
     *
     * @param request
     * @param user
     * @return
     */
    BaseResponse getContractPartialDataList(ConContractRequest request, LogUserInfoVo user);

    /**
     * 管理概算清单分页查询合同信息
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getContractBudgetList(ContractBudgetDto request, String uId);

    /**
     * 项目id查询无概算合同汇总数据
     *
     * @param pId
     * @return
     */
    BaseResponse getConBudgetSum(String pId);

    CommonResponse getConMoneyTotal(List<String> pIds);

    /**
     * ID查询合同详情
     * 以及合同支付计划、合同金额明细
     *
     * @param conId
     * @return
     */
    BaseResponse getContractWithMoneyPayByConId(String conId);

    /**
     * ID查询合同详情
     *
     * @param conId
     * @return
     */
    BaseResponse getContractByConId(String conId);

    BaseResponse getConEvaluateAmt(String conId);

    /**
     * ID集合查询合同详情
     *
     * @param conIds
     * @return
     */
    BaseResponse getContractByConIds(List<String> conIds);

    /**
     * ID查询解除合同详情
     *
     * @param conId
     * @return
     */
    BaseResponse getRelieveContractByConId(String conId);

    /**
     * 删除合同
     *
     * @param ids
     * @return
     */
    BaseResponse deleteContractByIds(String ids);


    /**
     * 审批合同
     *
     * @param request
     * @return
     */
    BaseResponse approvalContract(ConContractRequest request);


    /**
     * 合同类型分组统计数量
     *
     * @return
     */
    BaseResponse getContractStatisticsGroupConType(String teId);


    /**
     * 合同统计总览
     *
     * @return
     */
    BaseResponse getContractStatisticsOverview(String teId);

    /**
     * 获取合同总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getContractStatisticsOverviewList(ConContractRequest request, String teId);

    /**
     * 合同签订情况
     *
     * @return
     */
    BaseResponse getContractStatisticsSigning(String teId);

    /**
     * 招标方式统计合同
     *
     * @return
     */
    BaseResponse getContractStatisticsBiddingType(String teId);


    /**
     * 北京工行 合同支付审批时推送总包合同数据
     *
     * @param payId
     * @return
     */
    BaseResponse BjPushContractWhenConPay(String payId);


    BaseResponse updateContractCodeById(String conId, String conCode);


    /**
     * 维护合同关键信息
     *
     * @param request
     * @return
     */
    BaseResponse updateContractKeyInfoById(ConContractRequest request);


    /**
     * 更新合同概算申报清单ID
     *
     * @param conId
     * @param bddId
     * @return
     */
    BaseResponse updateContractBudget(String conId, String bddId);

    /**
     * 多合同金额求和
     *
     * @param conIds
     * @return
     */
    BigDecimal getConMoneyTotalSum(List<String> conIds);


    BigDecimal getConMoneyTotalSum(String pId);

    List<ConContract> getConContractBySecId(String secId);

    /**
     * 更新合同编码
     * 根据编码规则
     *
     * @param id
     * @return
     */
    String generateContractCode(String id);

    /**
     * 判断当前合同是否可以作废
     *
     * @param id
     * @return
     */
    boolean can2ProcessVoidingTheContract(String id);

    /**
     * 合同资金统计
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getContractFundStatistics(ContractFundStatisticsDTO request, String uId);

    /**
     * 标段ID查询是否存在未作废的合同
     *
     * @param secId
     * @return
     */
    Boolean secContractExist(String secId, String pId);

    /**
     * 通过项目、标段获取最新一条审核通过的合同数据
     *
     * @param secId
     * @param pId
     * @return
     */
    BaseResponse getSecContract(String secId, String pId);

    /**
     * 项目查询合同信息
     *
     * @param id
     * @return
     */
    BaseResponse getConContractByPid(String id);

    /**
     * 合同签订回调接口
     *
     * @param request
     * @return
     */
    BaseResponse updateConContractApproveStatus(ConContractRequest request);

    /*
   判断合同金额是否超过预警值
    */
    BaseResponse chekConAmountOver(String pId, BigDecimal amount);


    /*
    获取合同印花税明细
    */
    BaseResponse getContractStampDuty(ContractStampDutyModel data);


    Workbook exportContractStampDuty(ContractStampDutyModel request, String uId);

    /**
     * 获取合同金额
     * 合同金额+补充合同
     *
     * @param conId
     * @return
     */
    BigDecimal getContractMoney(String conId);

    BaseResponse getConPayPartyBPayList(String conId);

    BaseResponse getContractLedgerForWJT(ContractLedgerForWJTParam param);

    BaseResponse updateContractInfoWJTById(ConContractRequest request);

    List<ConContractResponse> getContractListExcel(ConContractRequest request, String uId);

    BaseResponse updateContractInfoWJTQD(ConContractRequest request);

    BaseResponse getContractSecurityMoney(String conId);

    List<ConContract> getConContractpId(String pId);

    BaseResponse getBalancePaymentDataList(ProgressDeclarationMatchDTO dto, String uId);

    BaseResponse getTCSettlementAuditDataList(ProgressDeclarationMatchDTO dto, String uId);

    BaseResponse getPreliminaryAuditDataList(ProgressDeclarationMatchDTO dto, String uId);

    BaseResponse getFinalAccountDataList(ProgressDeclarationMatchDTO dto, String uId);

    BaseResponse getBalancePaymentPlanDataList(ProgressDeclarationMatchDTO dto, String uid);

    Boolean getCompleteById(String conId);

    BaseResponse getConpayByIds(ConPayRequest request);

    List<ConContractResponse> getContractListByName(Set<String> request, String uid);

    BaseResponse getConpayByIdsGDGY(ConPayRequest request);

    BaseResponse getContractListNew(ConContractRequestNew request, String uId);

    BaseResponse getMainContractList(ConContractRequest request, String uid);

    BaseResponse getInvestmentControlLedgerHB(ConContractRequest request);

    BaseResponse updateContractById(ConContractRequest request);

    List<ConContractResponse> exportContractFiles(ConContractRequest request);

    BaseResponse getContractStatistics(ConContractRequest request);

    /**
     * 根据乙方单位ID列表汇总已结束合同金额。
     *
     * @param companyIds 单位ID列表
     * @param teId       租户ID，未开启租户模式时为空
     * @return 合同金额合计
     */
    BaseResponse getFinishedContractMoneyByCompanyIds(List<String> companyIds, String teId);

    BaseResponse syncToParticipantUnit(String conId, LogUserInfoVo user);

    /**
     * 获取当前用户可见合同列表的分类字段及可选值。
     *
     * @param request 合同列表筛选条件
     * @param uId 当前用户ID
     * @return 分类字段及其可选值
     */
    BaseResponse getContractListClassInfo(ConContractRequest request, String uId);
}
