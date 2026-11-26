package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.*;
import com.jpmsoft.common.model.request.ContractAuthorityBathDTO;
import com.jpmsoft.common.model.request.SysContractAuthorityRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.projectConsumer.pojo.dto.contract.*;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.ProgressDeclarationDtlDTO;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import com.jpmsoft.projectConsumer.pojo.vo.contract.*;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 合同管理
 */
@FeignClient(name = "project-producer", contextId = "contract")
public interface ConFeign {



    /**
     * 新增或修改合同
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/contract/createOrUpdateContract")
    BaseResponse createOrUpdateContract(@RequestBody ConContractRequest request);

    /**
     * 国资平台合同数据接入
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/contract/saveForGZ")
    BaseResponse saveForGZ(@RequestBody ConContractRequestDto request);

    /**
     * 新增或修改合同
     * 北京工行推送数据专用
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/contract/BjCreateOrUpdateContract")
    BaseResponse BjCreateOrUpdateContract(@RequestBody ConContractRequest request);

    /**
     * 获取合同列表
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/contract/getContractList")
    BaseResponse getContractList(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/getContractGgList")
    BaseResponse getContractGgList(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/getMainContractList")
    BaseResponse getMainContractList(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/getContractListNew")
    BaseResponse getContractListNew(@RequestBody ConContractRequestNew request,@RequestHeader String token);


    @PostMapping("/v1/contract/getContractListByPermission")
    BaseResponse getContractListByPermission(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/getContractListForDangerousProject")
    BaseResponse getContractListForDangerousProject(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/getContractListNoAuth")
    BaseResponse getContractListNoAuth(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/getContractPartialDataList")
    BaseResponse getContractPartialDataList(@RequestBody ConContractRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/getContractBudgetList")
    BaseResponse getContractBudgetList(@RequestBody ContractBudgetDto request, @RequestHeader String token);

    @GetMapping("/v1/contract/getConBudgetSum")
    BaseResponse getConBudgetSum(@RequestParam String pId);

    /**
     * 项目id查询合同总金额-智慧工地总仓
     *
     * @param pIds
     * @return
     */
    @GetMapping("/v1/contract/getConMoneyTotal")
    public BaseResponse getConMoneyTotal(@RequestBody List<String> pIds);

    /**
     * 新增或修改合同
     * <p>
     * 同步创建合同支付计划、合同金额明细
     */
    @PostMapping("/v1/contract/createOrUpdateContractWithMoneyPay")
    BaseResponse createOrUpdateContractWithMoneyPay(@RequestBody ConContractModel request);

    /**
     * 获取合同列表
     * 以及合同支付计划、合同金额明细
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/contract/getContractWithMoneyPayList")
    BaseResponse getContractWithMoneyPayList(@RequestBody ConContractModel request, @RequestHeader String token);

    /**
     * ID查询合同详情
     *
     * @param conId
     * @return
     */
    @GetMapping("/v1/contract/getContractByConId")
    BaseResponse getContractByConId(@RequestParam String conId);

    @GetMapping("/v1/contract/getContractSecurityMoney")
    BaseResponse getContractSecurityMoney(@RequestParam String conId);
    @GetMapping("/v1/contract/selectChContractSettlement")
    BaseResponse selectChContractSettlement(@RequestParam String conId);

    /**
     * ID集合查询合同详情
     *
     * @param conIds
     * @return
     */
    @GetMapping("/v1/contract/getContractByConIds")
    BaseResponse getContractByConIds(@RequestBody List<String> conIds);


    /**
     * ID查询合同详情
     * 以及合同支付计划、合同金额明细
     *
     * @param conId
     * @return
     */
    @GetMapping("/v1/contract/getContractWithMoneyPayByConId")
    BaseResponse getContractWithMoneyPayByConId(@RequestParam String conId);

    @GetMapping("/v1/contract/getRelieveContractByConId")
    BaseResponse getRelieveContractByConId(@RequestParam String conId);

    /**
     * 删除合同
     *
     * @param ids
     * @return
     */
    @GetMapping("/v1/contract/deleteContractByIds")
    BaseResponse deleteContractByIds(@RequestParam String ids);

    /**
     * 合同审批
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/contract/approvalContract")
    BaseResponse approvalContract(@RequestBody ConContractRequest request);

    @GetMapping("/v1/contract/BjPushContractWhenConPay")
    BaseResponse BjPushContractWhenConPay(@RequestParam String payId);


    @GetMapping("/v1/contract/updateContractCodeById")
    BaseResponse updateContractCodeById(@RequestParam String conId, @RequestParam String conCode);

    @PostMapping("/v1/contract/updateContractKeyInfoById")
    BaseResponse updateContractKeyInfoById(@RequestBody ConContractRequest request);

    @GetMapping("/v1/contract/updateContractBudget")
    BaseResponse updateContractBudget(@RequestParam String conId, @RequestParam String bddId);

    @GetMapping("/v1/contract/secContractExist")
    BaseResponse secContractExist(@RequestParam String secId, @RequestParam String pId);

    @GetMapping("/v1/contract/getSecContract")
    BaseResponse getSecContract(@RequestParam String secId, @RequestParam String pId);


    @GetMapping("/v1/contract/can2ProcessVoidingTheContract")
    BaseResponse can2ProcessVoidingTheContract(@RequestParam String id);

    @PostMapping("/v1/contract/getContractFundStatistics")
    BaseResponse getContractFundStatistics(@RequestBody ContractFundStatisticsDTO request, @RequestHeader String token);


    @GetMapping("/v1/contract/getConContractByPid")
    BaseResponse getConContractByPid(@RequestParam String id);

    @GetMapping("/v1/contract/getContractMoney")
    BaseResponse getContractMoney(@RequestParam String id);

    @PostMapping("/v1/contract/updateConContractApproveStatus")
    BaseResponse updateConContractApproveStatus(@RequestBody ConContractRequest request);

    @GetMapping("/v1/contract/chekConAmountOver")
    BaseResponse chekConAmountOver(@RequestParam String pId, @RequestParam BigDecimal amount);

    @PostMapping("/v1/contract/getContractStampDuty")
    BaseResponse getContractStampDuty(@RequestBody ContractStampDutyModel data);

    @PostMapping("/v1/contract/exportContractStampDuty")
    Response exportContractStampDuty(@RequestBody ContractStampDutyModel request, @RequestHeader String token);


    @GetMapping("/v1/contract/getConPayPartyBPayList")
    BaseResponse getConPayPartyBPayList(@RequestParam String conId);


    /***********************************************合同支付***************************************************/
    @PostMapping("/v1/conPay/createOrUpdateConPay")
    BaseResponse createOrUpdateConPay(@RequestBody ConPayRequest request);

    @PostMapping("/v1/conPay/updateConPayInFlow")
    BaseResponse updateConPayInFlow(@RequestBody ConPayRequest request);

    @GetMapping("/v1/conPay/canToCreateConPay")
    BaseResponse canToCreateConPay(@RequestParam String conId);

    @PostMapping("/v1/conPay/getConPayList")
    BaseResponse getConPayList(@RequestBody ConPayRequest request, @RequestHeader String token);

    @PostMapping("/v1/conPay/exportConPayList")
    Response exportConPayList(@RequestBody ConPayRequest request, @RequestHeader("token") String token);

    @PostMapping("/v1/conPay/getConPayStandingBook")
    BaseResponse getConPayStandingBook(@RequestBody ConPayRequest request, @RequestHeader String token);

    @PostMapping("/v1/conPay/getConPayGroupByContract")
    BaseResponse getConPayGroupByContract(@RequestBody ConPayRequest request, @RequestHeader String token);

    @PostMapping("/v1/conPay/getConPayFundAllocation")
    BaseResponse getConPayFundAllocation(@RequestBody ConPayRequest request, @RequestHeader String token);

    @PostMapping("/v1/conPay/getConPayListByConId")
    BaseResponse getConPayListByConId(@RequestBody ConPayRequest request);

    @GetMapping("/v1/conPay/getConPayBypayId")
    BaseResponse getConPayBypayId(@RequestParam String payId);

    @PostMapping("/v1/conPay/getConPayWHByConId")
    BaseResponse getConPayWHByConId(@RequestBody ConPayRequest request);

    @PostMapping("/v1/conPay/getLastApprovedConPay")
    BaseResponse getLastApprovedConPay(@RequestBody ConPayRequest request);

    @PostMapping("/v1/conBG/getLJBGMoney")
    BaseResponse getLJBGMoney(@RequestBody ProBGRequest request);

    @PostMapping("/v1/conBG/SetConUnitOrderBL")
    BaseResponse SetConUnitOrderBL(@RequestBody ProBGRequest request);

    @PostMapping("/v1/conBG/SetDeclarationBL")
    BaseResponse SetDeclarationBL(@RequestBody ProBGRequest request);

    @PostMapping("/v1/conBG/SetContactOrderBL")
    BaseResponse SetContactOrderBL(@RequestBody ProBGRequest request);

    @PostMapping("/v1/conBG/getBGListByConId")
    BaseResponse getBGListByConId(@RequestBody ProBGRequest request);

    @PostMapping("/v1/conBG/getBGListByType")
    BaseResponse getBGListByType(@RequestBody ProBGRequest request);

    @PostMapping("/v1/conPay/getPayCumulativeStatistics")
    BaseResponse getPayCumulativeStatistics(@RequestBody ConPayRequest request);

    @PostMapping("/v1/conPay/getPayCumulativeStatisticsCH")
    BaseResponse getPayCumulativeStatisticsCH(@RequestBody ConPayRequest request);

    @GetMapping("/v1/conPay/getPayCountByProAndYear")
    BaseResponse getPayCountByProAndYear(@RequestParam String pId, @RequestParam String year);


    @GetMapping("/v1/conPay/getConPayByPid")
    BaseResponse getConPayByPid(@RequestParam String pId);

    @GetMapping("/v1/conPay/getPayProjectPaymentSurplus")
    BaseResponse getPayProjectPaymentSurplus(@RequestParam String pId, @RequestParam String year);

    @PostMapping("/v1/conPay/getConPayFundStatistics")
    BaseResponse getConPayFundStatistics(@RequestBody ConPayFundStatisticsDTO request);

    @PostMapping("/v1/conPay/contractPayCount")
    BaseResponse contractPayCount(@RequestBody ConPayCountDto request);

    @GetMapping("/v1/conPay/getPayDoneTotalByConId")
    BigDecimal getPayDoneTotalByConId(@RequestParam String conId, @RequestParam String pType);

    @GetMapping("/v1/conPay/getContractListByPIdAndConId")
    BaseResponse getContractListByPIdAndConId(@RequestParam String pId, @RequestParam String conId);

    @GetMapping("/v1/conPay/getPayDefaultTotalByPIdAndConId")
    BaseResponse getPayDefaultTotalByPIdAndConId(@RequestParam String pId, @RequestParam String conId, @RequestParam(value = "type", required = false) String type);

    @PostMapping("/v1/conPay/contractPayListForCj")
    BaseResponse contractPayListForCj(@RequestBody ContractPayListForCj contractPayListForCj,@RequestHeader String token);

    @PostMapping("/v1/conPay/contractPayListForCjByPId")
    BaseResponse contractPayListForCjByPId(@RequestBody ContractPayListForCjDTO request);

    @PostMapping("/v1/conPay/contractPayListForCjByConId")
    BaseResponse contractPayListForCjByConId(@RequestBody ContractPayListForCjSub request);

    @PostMapping("/v1/conPay/getEngineeringSpecialAccount")
    BaseResponse getEngineeringSpecialAccount(@RequestBody EngineeringSpecialAccount request,@RequestHeader String token);

    @PostMapping("/v1/conPay/getEngineeringSpecialAccountForJt")
    BaseResponse getEngineeringSpecialAccountForJt(@RequestBody EngineeringSpecialAccountForJT request);

    @PostMapping("/v1/conPay/getEngineeringSpecialAccountForQy")
    BaseResponse getEngineeringSpecialAccountForQy(@RequestBody EngineeringSpecialAccountForQY request);

    @PostMapping("/v1/conPay/postConpayForSKApi")
    BaseResponse postConpayForSKApi(@RequestBody ConPayRequest request);

    /***********************************************工程联系单***************************************************/

    /**
     * 新增或修改工程联系单
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/contactList/createOrUpdateContactList")
    BaseResponse createOrUpdateContactList(@RequestBody ConContactListRequest request);

    /**
     * ID删除
     *
     * @param clId
     * @return
     */
    @GetMapping("/v1/contactList/deleteContactLisByClId")
    BaseResponse deleteContactLisByClId(@RequestParam String clId);

    @GetMapping("/v1/contactList/getContactLisByClId")
    BaseResponse getContactLisByClId(@RequestParam String clId);

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/contactList/getConContactList")
    BaseResponse getConContactList(@RequestBody ConContactListRequest request, @RequestHeader String token);

    @PostMapping("/v1/contactList/getConContactListNoAuth")
    BaseResponse getConContactListNoAuth(@RequestBody ConContactListRequest request);

    /***********************************************变更事项申请***************************************************/
    /**
     * 新增或修改变更事项申请
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/conEc/createOrUpdateConEc")
    BaseResponse createOrUpdateConEc(@RequestBody ConEcRequest request);

    @PostMapping("/v1/conEc/updateConEc")
    BaseResponse updateConEc(@RequestBody ConEcRequest request);

    /**
     * ID删除
     *
     * @param ecId
     * @return
     */
    @GetMapping("/v1/conEc/deleteConEcByEcId")
    BaseResponse deleteConEcByEcId(@RequestParam String ecId);

    @GetMapping("/v1/conEc/getConEcByEcId")
    BaseResponse getConEcByEcId(@RequestParam String ecId);

    @GetMapping("/v1/conEc/getConEcByConId")
    BaseResponse getConEcByConId(@RequestParam String conId);

    @GetMapping("/v1/conEc/getConEcCountByConId")
    BaseResponse getConEcCountByConId(@RequestParam String conId);

    @GetMapping("/v1/conEc/changeTtems")
    BaseResponse changeTtems(@RequestParam String conId);

    @PostMapping("/v1/conEc/saveBgzl")
    BaseResponse saveBgzl(@RequestBody ConEcRequest request);

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/conEc/getConEcList")
    BaseResponse getConEcList(@RequestBody ConEcRequest request, @RequestHeader String token);


    @PostMapping("/v1/conEc/getConEcListByPermission")
    BaseResponse getConEcListByPermission(@RequestBody ConEcRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEc/getConEcStandingBook")
    BaseResponse getConEcStandingBook(@RequestBody ConEcRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEc/getConEcListNoAuth")
    BaseResponse getConEcListNoAuth(@RequestBody ConEcRequest request);

    @GetMapping("/v1/conEc/approve2CancelPreEc")
    BaseResponse approve2CancelPreEc(@RequestParam String ecId);

    @PostMapping("/v1/conEc/checkClzgAndZlje")
    BaseResponse checkClzgAndZlje(@RequestBody ConEcRequest request);


    /***********************************************变更费用申报***************************************************/
    @PostMapping("/v1/conEcFee/createOrUpdateConEcFee")
    BaseResponse createOrUpdateConEcFee(@RequestBody ConEcFeeRequest request);

    @PostMapping("/v1/conEcFee/getConEcFeeList")
    BaseResponse getConEcFeeList(@RequestBody ConEcFeeRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEcFee/getConEcFeeRelationList")
    BaseResponse getConEcFeeRelationList(@RequestBody ConEcFeeRelationListRequest request,
                                         @RequestHeader("token") String token);



    @PostMapping("/v1/conEcFee/createOrUpdateConEcFeeOrRecord")
    BaseResponse createOrUpdateConEcFeeOrRecord(@RequestBody ConEcFeeRequest request);

    @PostMapping("/v1/conEcFee/getConEcFeeListorRecord")
    BaseResponse getConEcFeeListorRecord(@RequestBody ConEcFeeRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEcFee/getConEcFeeClassInfo")
    BaseResponse getConEcFeeClassInfo(@RequestBody ConEcFeeRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEcFee/getConEcFeeStandingBook")
    BaseResponse getConEcFeeStandingBook(@RequestBody ConEcFeeRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEcFee/getConEcFeeListNoAuth")
    BaseResponse getConEcFeeListNoAuth(@RequestBody ConEcFeeRequest request);

    @PostMapping("/v1/conEcFee/getConEcFeeByEcfId")
    BaseResponse getConEcFeeByEcfId(@RequestBody ConEcFeeRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEcFee/getConEcFeeorRecordByEcfId")
    BaseResponse getConEcFeeorRecordByEcfId(@RequestBody ConEcFeeRequest request, @RequestHeader String token);

    @GetMapping("/v1/conEcFee/getConEcFeeApplyMoneySum")
    BaseResponse getConEcFeeApplyMoneySum(@RequestParam String conId);

    @PostMapping("/v1/conEcFee/createOrUpdateConEcFeeClear")
    BaseResponse createOrUpdateConEcFeeClear(@RequestBody ConEcFeeRequest request);

    @PostMapping("/v1/conEcFee/getConEcFeeClearList")
    BaseResponse getConEcFeeClearList(@RequestBody ConEcFeeRequest request, @RequestHeader String token);

    @GetMapping("/v1/conEcFee/getConEcFeeClearById")
    BaseResponse getConEcFeeClearById(@RequestParam String id);


    /***********************************************签证计量备案***************************************************/
    @PostMapping("/v1/conVisaRecord/createOrUpdateConVisaRecord")
    BaseResponse createOrUpdateConVisaRecord(@RequestBody ConVisaRecordRequest request);

    @PostMapping("/v1/conVisaRecord/getConVisaRecordList")
    BaseResponse getConVisaRecordList(@RequestBody ConVisaRecordRequest request, @RequestHeader String token);

    @GetMapping("/v1/conVisaRecord/getConVisaRecordByVrId")
    BaseResponse getConVisaRecordByVrId(@RequestParam String vrId);

    /***********************************************签证费用申报***************************************************/
    @PostMapping("/v1/conVisaFee/createOrUpdateConVisaFee")
    BaseResponse createOrUpdateConVisaFee(@RequestBody ConVisaFeeRequest request);

    @PostMapping("/v1/conVisaFee/getConVisaFeeList")
    BaseResponse getConVisaFeeList(@RequestBody ConVisaFeeRequest request, @RequestHeader String token);

    @GetMapping("/v1/conVisaFee/getConVisaFeeByVfId")
    BaseResponse getConVisaFeeByVfId(@RequestParam String vfId, @RequestParam(required = false) Integer conWhere);

    @GetMapping("/v1/conVisaFee/getVisaAmt")
    BaseResponse getVisaAmt(@RequestParam String conId);

    /***********************************************合同权限配置***************************************************/
    @PostMapping("/v1/contractAuth/createContractAuthority")
    BaseResponse createContractAuthority(@RequestBody SysContractAuthorityRequest request);

    @PostMapping("/v1/contractAuth/createOrDelAuthBath")
    BaseResponse createOrDelAuthBath(@RequestBody ContractAuthorityBathDTO request, @RequestHeader String token);

    @GetMapping("/v1/contractAuth/deleteContractAuthorityByCaId")
    BaseResponse deleteContractAuthorityByCaId(@RequestParam String caId);

    @PostMapping("/v1/contractAuth/deleteContractAuthBath")
    BaseResponse deleteContractAuthBath(@RequestBody List<String> ids);

    @PostMapping("/v1/contractAuth/getContractAuthorityList")
    BaseResponse getContractAuthorityList(@RequestBody SysContractAuthorityRequest request, @RequestHeader String token);

    /***********************************************保函签收***************************************************/
    @PostMapping("/v1/GuaranteeLetter/createOrUpdateGuaranteeLetterSign")
    BaseResponse createOrUpdateGuaranteeLetterSign(@RequestBody GuaranteeLetterSignRequest request);

    @PostMapping("/v1/GuaranteeLetter/getGuaranteeLetterSignList")
    BaseResponse getGuaranteeLetterSignList(@RequestBody GuaranteeLetterSignRequest request, @RequestHeader String token);

    @GetMapping("/v1/GuaranteeLetter/getGuaranteeLetterSignByglsId")
    BaseResponse getGuaranteeLetterSignByglsId(@RequestParam String glsId);

    @GetMapping("/v1/GuaranteeLetter/getGuaranteeLetterSignByContract")
    BaseResponse getGuaranteeLetterSignByContract(@RequestParam String conId);

    @GetMapping("/v1/GuaranteeLetter/getGuaranteeLetterExistence")
    BaseResponse getGuaranteeLetterExistence(@RequestParam String conId);


    /***********************************************保函办退***************************************************/
    @PostMapping("/v1/GuaranteeLetter/getGuaranteeLetterBackList")
    BaseResponse getGuaranteeLetterBackList(@RequestBody GuaranteeLetterBackRequest request, @RequestHeader String token);

    @PostMapping("/v1/GuaranteeLetter/createOrUpdateGuaranteeLetterBack")
    BaseResponse createOrUpdateGuaranteeLetterBack(@RequestBody GuaranteeLetterBackRequest request);

    @GetMapping("/v1/GuaranteeLetter/getGuaranteeLetterBackByglbId")
    BaseResponse getGuaranteeLetterBackByglbId(@RequestParam String glbId);

    @GetMapping("/v1/GuaranteeLetter/getGuaranteeLetterBackAndSignByglbId")
    BaseResponse getGuaranteeLetterBackAndSignByglbId(@RequestParam String glbId);


    /***********************************************代建合同实拨***************************************************/
    @PostMapping("/v1/aaAllocation/createOrUpdateAgentActualAllocation")
    BaseResponse createOrUpdateAgentActualAllocation(@RequestBody AgentActualAllocationModel request);

    @PostMapping("/v1/aaAllocation/getAgentActualAllocationList")
    BaseResponse getAgentActualAllocationList(@RequestBody AgentActualAllocationModel request, @RequestHeader String token);

    @GetMapping("/v1/aaAllocation/getAgentActualAllocationById")
    BaseResponse getAgentActualAllocationById(@RequestParam String aalId);

    @GetMapping("/v1/aaAllocation/getAalThisPayByConId")
    BaseResponse getAalThisPayByConId(@RequestParam String conId);

    @GetMapping("/v1/aaAllocation/getAalThisPayMoneyByAgentConId")
    BaseResponse getAalThisPayMoneyByAgentConId(@RequestParam String aalAgentConId);

    /***********************************************代建资金预拨***************************************************/
    @PostMapping("/v1/afAdvance/createOrUpdateAgentFundAdvance")
    BaseResponse createOrUpdateAgentFundAdvance(@RequestBody AgentFundAdvanceModel request);

    @PostMapping("/v1/afAdvance/getAgentFundAdvanceList")
    BaseResponse getAgentFundAdvanceList(@RequestBody AgentFundAdvanceModel request, @RequestHeader String token);

    @GetMapping("/v1/afAdvance/getAgentFundAdvanceById")
    BaseResponse getAgentFundAdvanceById(@RequestParam String afaId);

    @GetMapping("/v1/afAdvance/getAfaThisMoneyByConId")
    BaseResponse getAfaThisMoneyByConId(@RequestParam String conId);

    @GetMapping("/v1/afAdvance/getAfalThisMoneyByAfalConId")
    BaseResponse getAfalThisMoneyByAfalConId(@RequestParam String afalConId);

    /***********************************************合同范本***************************************************/

    @PostMapping("/v1/conTemplate/createConTemplate")
    BaseResponse createConTemplate(@RequestBody ConTemplateDto request);

    @PostMapping("/v1/conTemplate/updateConTemplate")
    BaseResponse updateConTemplate(@RequestBody ConTemplateDto request);

    @PostMapping("/v1/conTemplate/getConTemplateList")
    BaseResponse getConTemplateList(@RequestBody ConTemplateDto request, @RequestHeader String token);

    @GetMapping("/v1/conTemplate/getConTemplateById")
    BaseResponse getConTemplateById(@RequestParam String id);


    /***********************************************计量报审表***************************************************/

    @PostMapping("/v1/conMeasure/createOrUpdateConMeasure")
    BaseResponse createOrUpdateConMeasure(@RequestBody ConMeasureDto request);

    @PostMapping("/v1/conMeasure/createConMeasure")
    BaseResponse createConMeasure(@RequestBody ConMeasureDto request);

    @PostMapping("/v1/conMeasure/updateConMeasure")
    BaseResponse updateConMeasure(@RequestBody ConMeasureDto request);

    @PostMapping("/v1/conMeasure/updateConMeasureOnly")
    BaseResponse updateConMeasureOnly(@RequestBody ConMeasureDto request);

    @PostMapping("/v1/conMeasure/getConMeasureList")
    BaseResponse getConMeasureList(@RequestBody ConMeasureDto request,@RequestHeader String token);

    @GetMapping("/v1/conMeasure/getConMeasureById")
    BaseResponse getConMeasureById(@RequestParam String id);

    @GetMapping("/v1/conMeasure/getLastApproveMeasureByConId")
    BaseResponse getLastApproveMeasureByConId(@RequestParam String conId);

    @GetMapping("/v1/conMeasure/return2Initiator")
    BaseResponse return2Initiator(@RequestParam String id);

    @GetMapping("/v1/conMeasure/statisticsApplyAndAmt")
    BaseResponse statisticsApplyAndAmt(@RequestParam String id, @RequestParam(required = false) String conId);

    @GetMapping("/v1/conMeasure/statisticsAmtByConId")
    BaseResponse statisticsAmtByConId(@RequestParam String conId);

    /***********************************************计量单审核记录***************************************************/

    @PostMapping("/v1/conMeasureRecordCheck/createConMeasureRecordCheck")
    BaseResponse createConMeasureRecordCheck(@RequestBody ConMeasureRecordCheckDto request);

    @PostMapping("/v1/conMeasureRecordCheck/updateConMeasureRecordCheck")
    BaseResponse updateConMeasureRecordCheck(@RequestBody ConMeasureRecordCheckDto request);

    @GetMapping("/v1/conMeasureRecordCheck/getConMeasureRecordCheckById")
    BaseResponse getConMeasureRecordCheckById(@RequestParam String id);

    @PostMapping("/v1/conMeasureRecordCheck/getConMeasureRecordCheckList")
    BaseResponse getConMeasureRecordCheckList(@RequestBody ConMeasureRecordCheckDto request);


    /***********************************************资金支付申请***************************************************/

    @GetMapping("/v1/ConPayApply/createConPayApply")
    BaseResponse createConPayApply();

    @GetMapping("/v1/ConPayApply/getConPayApplyById")
    BaseResponse getConPayApplyById(@RequestParam String id);

    @PostMapping("/v1/ConPayApply/getConPayApplyList")
    BaseResponse getConPayApplyList(@RequestBody ConPayApplyDto request);

    /***********************************************资金支付详情申请***************************************************/

    @PostMapping("/v1/ConPayApplyFlow/createOrUpdateConPayApplyFlow")
    BaseResponse createOrUpdateConPayApplyFlow(@RequestBody ConPayApplyFlowDto request);

    @GetMapping("/v1/ConPayApplyFlow/getConPayApplyFlowById")
    BaseResponse getConPayApplyFlowById(@RequestParam String id);

    @GetMapping("/v1/ConPayApplyFlow/getConPayApplyFlowByConId")
    BaseResponse getConPayApplyFlowByConId(@RequestParam String conId);

    @PostMapping("/v1/ConPayApplyFlow/getConPayApplyFlowList")
    BaseResponse getConPayApplyFlowList(@RequestBody ConPayApplyFlowDto request);

    @PostMapping("/v1/ConPayApplyFlow/getConPayApplyFlowSumById")
    BaseResponse getConPayApplyFlowSumById(@RequestBody ConPayApplyFlowSumDto request);

    @GetMapping("/v1/ConPayApplyFlow/getConPayApplyFlowSumByConId")
    BaseResponse getConPayApplyFlowSumByConId(@RequestParam String conId);

    @PostMapping("/v1/ConPayApplyFlow/getConPayApplyFlowDataList")
    BaseResponse getConPayApplyFlowDataList(@RequestBody ConPayApplyFlowVo vo);

    @PostMapping("/v1/ConPayApplyFlow/getConPayApplyFlowStatsList")
    BaseResponse getConPayApplyFlowStatsList(@RequestBody ConPayApplyFlowVo vo);

    /***********************************************计量单***************************************************/

    @GetMapping("/v1/conMeasureRecord/getAllConMearByConIdAndCode")
    BaseResponse getAllConMearByConIdAndCode(@RequestParam String conId, @RequestParam String boqdCode);

    @PostMapping("/v1/conMeasureRecord/webAutoSaveConMeasureRecord")
    BaseResponse webAutoSaveConMeasureRecord(@RequestBody ConMeasureRecordDto request, @RequestHeader String token);

    @PostMapping("/v1/conMeasureRecord/can2deleteBoqd")
    BaseResponse can2deleteBoqd(@RequestBody List<String> boqdIds);

    /***********************************************任务委托单***************************************************/

    @PostMapping("/v1/conTaskDelegation/createOrUpdateConTaskDelegation")
    BaseResponse createOrUpdateConTaskDelegation(@RequestBody ConTaskDelegationDto request);

    @PostMapping("/v1/conTaskDelegation/getConTaskDelegationList")
    BaseResponse getConTaskDelegationList(@RequestBody ConTaskDelegationDto request, @RequestHeader String token);


    @GetMapping("/v1/conTaskDelegation/getConTaskDelegationById")
    BaseResponse getConTaskDelegationById(@RequestParam String id);

    @GetMapping("/v1/conTaskDelegation/getConTaskDelegationByConId")
    BaseResponse getConTaskDelegationByConId(@RequestParam String conId);

    /***********************************************合同支付条款***************************************************/

    @GetMapping("/v1/conPayPlan/getConContractPayPlanByConId")
    BaseResponse getConContractPayPlanByConId(@RequestParam String conId);

    @GetMapping("/v1/conPayPlan/ProgressDeclarationCompute")
    BaseResponse ProgressDeclarationCompute(@RequestParam ProgressDeclarationDtlDTO request);

    /***********************************************工期调整***************************************************/

    @PostMapping("/v1/conDurationAdjust/createOrUpdateConDurationAdjust")
    BaseResponse createOrUpdateConDurationAdjust(@RequestBody ConDurationAdjustDto request);

    @PostMapping("/v1/conDurationAdjust/getConDurationAdjustList")
    BaseResponse getConDurationAdjustList(@RequestBody ConDurationAdjustDto request, @RequestHeader String token);

    @GetMapping("/v1/conDurationAdjust/getConDurationAdjustById")
    BaseResponse getConDurationAdjustById(@RequestParam String id);


    /***********************************************合同变更***************************************************/

    @PostMapping("/v1/contractChange/createOrUpdateConContractChange")
    BaseResponse createOrUpdateConContractChange(@RequestBody ConContractChangeDTO request);

    @PostMapping("/v1/contractChange/getContractChangeList")
    BaseResponse getContractChangeList(@RequestBody ConContractChangeDTO request, @RequestHeader String token);

    @GetMapping("/v1/contractChange/getContractChangeById")
    BaseResponse getContractChangeById(@RequestParam String id);

    @GetMapping("/v1/contractChange/approve2RefreshContract")
    BaseResponse approve2RefreshContract(@RequestParam String id);

    /***********************************************合同支付现场检测关联表***************************************************/

    @GetMapping("/v1/conPayWitness/getConPayWitnessByPayId")
    BaseResponse getConPayWitnessByPayId(@RequestParam String payId);


    @GetMapping("/v1/conPayWitness/getConPayWitnessByOrderNo")
    BaseResponse getConPayWitnessByOrderNo(@RequestParam String orderNo);

    @GetMapping("/v1/conPayWitness/getConPayWitnessByConId")
    BaseResponse getConPayWitnessByConId(@RequestParam String conId);


    /***************************************工程变更令***************************************/
    @PostMapping("/v1/conEco/createOrUpdateConEco")
    BaseResponse createOrUpdateConEco(@RequestBody ConEcoDTO request);

    @PostMapping("/v1/conEco/getConEcoList")
    BaseResponse getConEcoList(@RequestBody ConEcoDTO request, @RequestHeader String token);

    @GetMapping("/v1/conEco/getConEcoById")
    BaseResponse getConEcoById(@RequestParam String ecoId);

    /***************************************合同收款发票***************************************/

    @PostMapping("/v1/conInvoice/createOrUpdateInvoice")
    BaseResponse createOrUpdateInvoice(@RequestBody ConInvoiceDTO request);

    @PostMapping("/v1/conInvoice/getInvoiceList")
    BaseResponse getInvoiceList(@RequestBody ConInvoiceDTO request, @RequestHeader String token);

    @GetMapping("/v1/conInvoice/getInvoiceById")
    BaseResponse getInvoiceById(@RequestParam String id);

    /***************************************授权委托书***************************************/

    @PostMapping("/v1/conPowerAttorney/createOrUpdateConPowerAttorney")
    BaseResponse createOrUpdateConPowerAttorney(@RequestBody ConPowerAttorneyDTO request);

    @PostMapping("/v1/conPowerAttorney/getConPowerAttorneyList")
    BaseResponse getConPowerAttorneyList(@RequestBody ConPowerAttorneyDTO request, @RequestHeader String token);

    @GetMapping("/v1/conPowerAttorney/getConPowerAttorneyById")
    BaseResponse getConPowerAttorneyById(@RequestParam String id);

    @GetMapping("/v1/conPowerAttorney/contractApprove2Create")
    BaseResponse contractApprove2Create(@RequestParam String conId);

    /***************************************发票签收***************************************/
    @PostMapping("/v1/conInvoiceSign/createOrUpdateConInvoiceSign")
    BaseResponse createOrUpdateConInvoiceSign(@RequestBody ConInvoiceSignDTO request);

    @PostMapping("/v1/conInvoiceSign/getConInvoiceSignList")
    BaseResponse getConInvoiceSignList(@RequestBody ConInvoiceSignDTO request, @RequestHeader String token);

    @GetMapping("/v1/conInvoiceSign/getConInvoiceSignById")
    BaseResponse getConInvoiceSignById(@RequestParam String id);

    @GetMapping("/v1/contactList/processSync/{uuid}")
    BaseResponse processSynContactList(@PathVariable("uuid") String uuid);

    @GetMapping("/v1/conEc/processSync/{uuid}")
    BaseResponse processSynConEc(@PathVariable("uuid") String uuid);

    /**
     * 列表
     *
     * @param pageNum
     * @param pageSize
     * @param projectName
     * @return
     */
    @GetMapping("/v1/multiContractConEc/page")
    BaseResponse pageMultiContractConEc(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false) String projectName,
                                        @RequestParam(required = false) String pId,
                                        @RequestParam(defaultValue = "1") String ecChangeType,@RequestHeader String token);

    // 多合同变更签证详情
    @GetMapping("/v1/multiContractConEc/detail/{uuid}")
    BaseResponse detailMultiContractConEc(@PathVariable("uuid") String uuid, @RequestHeader String token);

    // 多合同变更签证详情-获取项目累计变更签证金额
    @GetMapping("/v1/multiContractConEc/getProjectTotalConEcMoney")
    BaseResponse getProjectTotalConEcMoney(@RequestParam String projectId, @RequestParam String ecChangeType, @RequestParam String contractId);

    /**
     * 转发合同已审批变更、签证历史查询。
     *
     * @param projectId   项目ID
     * @param contractId  合同ID
     * @param excludeEcId 排除的当前子表记录ID，可为空
     * @param token       当前登录令牌
     * @return 含主流程审批时间和余额的合同历史明细
     */
    @GetMapping("/v1/multiContractConEc/getContractChangeHistory")
    BaseResponse getContractChangeHistory(
            @RequestParam("projectId") String projectId,
            @RequestParam("contractId") String contractId,
            @RequestParam(value = "excludeEcId", required = false) String excludeEcId,
            @RequestHeader("token") String token);

    @GetMapping("/v1/multiContractConEc/getFinishedDesignChangeApplyMoneyByProjectId")
    BaseResponse getFinishedDesignChangeApplyMoneyByProjectId(
            @RequestParam("projectId") String projectId,
            @RequestHeader("token") String token);

    // 创建多合同变更签证
    @PostMapping("/v1/multiContractConEc/createOrUpdate")
    BaseResponse createOrUpdateMultiContractConEc(@RequestBody MultiContractConEcRequest multiContractConEcRequest);

    // 多合同变更签证流程回调
    @GetMapping("/v1/multiContractConEc/processSyn/{uuid}")
    BaseResponse processSynMultiContractConEc(@PathVariable("uuid") String uuid);

    @PostMapping("/v1/multiContractConEc/syncChildState")
    BaseResponse syncMultiContractConEcChildState(@RequestParam("id") String id,
                                                  @RequestParam("type") String type);


    @PostMapping("/v1/conPay/bindAwardTicket")
    BaseResponse awardTicketBindPay(@RequestBody BindAwardTicketRequest bindAwardTicketRequest);

    @PostMapping("/v1/conPay/flowSubmitCallback")
    BaseResponse conPayFlowSubmitCallback(@RequestBody FlowSubmitCallbackRequest flowSubmitCallbackRequest);

    @GetMapping("/v1/conPay/countUnfinishedFlowByContractAndPayType")
    BaseResponse countUnfinishedFlowByContractAndPayType(@RequestParam(required = false) String contractId, @RequestParam(required = false) String payType);

    @GetMapping("/v1/conPay/getConPayPrintByPayId")
    BaseResponse getConPayPrintByPayId(@RequestParam String id);


    @GetMapping("/v1/conBG/getSumOfContactItemRateByContactItemId")
    BaseResponse getSumOfContactItemRateByContactItemId(@RequestParam(required = false) String contactItemId);


    /***********************************************付款请审***************************************************/
    @PostMapping("/v1/paymentReview/createOrUpdatePaymentReview")
    BaseResponse createOrUpdatePaymentReview(@RequestBody PaymentReviewRequest request);

    @PostMapping("/v1/paymentReview/getPaymentReviewList")
    BaseResponse getPaymentReviewList(@RequestBody PaymentReviewRequest request);

    @GetMapping("/v1/paymentReview/getPaymentReviewById")
    BaseResponse getPaymentReviewById(@RequestParam String id);

    @GetMapping("/v1/paymentReview/deletePaymentReviewById")
    BaseResponse deletePaymentReviewById(@RequestParam String id);

    @PostMapping("/v1/conPay/recalculateContractRetentionMoney")
    BaseResponse updateContractRetentionMoney(@RequestBody ConPayRequest request);

    @GetMapping("/v1/conPay/getConPayAmt")
    BaseResponse getConPayAmt(@RequestParam String conId);

    @GetMapping("/v1/conPay/getConCompayPayTotalById")
    BaseResponse getConCompayPayTotalById(@RequestParam String id);

    @GetMapping("/v1/conPay/automaticallyFundsPayment")
    BaseResponse automaticallyFundsPayment(@RequestBody ConPayRequest request, @RequestHeader String token);

    /***********************************合同乙方单位库合同价分配***************************************************/
    @GetMapping("/v1/conConsortiumDtl/slectConConsortiumDtlListByConId")
    BaseResponse slectConConsortiumDtlListByConId(@RequestParam String conId);


    /***********************************************支付报审清单***************************************************/
    @GetMapping("/v1/conPayMeasure/getUnMeasureApplyAndAmtByConId")
    BaseResponse getUnMeasureApplyAndAmtByConId(@RequestParam String conId);

    @GetMapping("/v1/conPayMeasure/getApplyAndAmtByPayId")
    BaseResponse getApplyAndAmtByPayId(@RequestParam String payId);

    @PostMapping("/v1/conPayMeasure/createOrUpdateConPayMeasure")
    BaseResponse createOrUpdateConPayMeasure(@RequestBody ConPayRequest request);

    /***********************************************变更事项申请-合同工程量清单变更***************************************************/

    @PostMapping("/v1/boqEcChange/createOrUpdateBoqEcChange")
    BaseResponse createOrUpdateBoqEcChange(@RequestBody List<ConBoqDtlDto> request, @RequestParam String formPriKey);

    @GetMapping("/v1/boqEcChange/getBoqEcChangeDtlByEcId")
    BaseResponse getBoqEcChangeDtlByEcId(@RequestParam String ecId);

    @GetMapping("/v1/boqEcChange/getBoqEcChangeDtlByBoqdId")
    BaseResponse getBoqEcChangeDtlByBoqdId(@RequestParam String boqdId);

    @GetMapping("/v1/boqEcChange/approved2RefreshConBoqDtl")
    BaseResponse approved2RefreshConBoqDtl(@RequestParam String ecId);

    @PostMapping("/v1/boqEcChange/can2Change")
    BaseResponse can2Change(@RequestBody ConBoqDtlDto request);


    @GetMapping("/fullProcessConsultServiceProtocolMeterageApply/getServiceProtocolContentItemByProjectId")
    BaseResponse getServiceProtocolContentItemByProjectId(@RequestParam String projectId);

    @PostMapping("/fullProcessConsultServiceProtocolMeterageApply/page")
    BaseResponse pageFullProcessConsultServiceProtocolMeterageApply(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request, @RequestHeader String token);

    @GetMapping("/fullProcessConsultServiceProtocolMeterageApply/getById")
    BaseResponse getFullProcessConsultServiceProtocolMeterageApplyById(@RequestParam String id);

    @PostMapping("/fullProcessConsultServiceProtocolMeterageApply/createOrUpdate")
    BaseResponse createOrUpdateFullProcessConsultServiceProtocolMeterageApply(@RequestBody FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest fullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest);

    @PostMapping("/fullProcessConsultServiceProtocolMeterageApply/deleteById/{id}")
    BaseResponse deleteFullProcessConsultServiceProtocolMeterageApplyById(@PathVariable("id") String id);

    @GetMapping("/fullProcessConsultServiceProtocolMeterageApply/getLastFullProcessConsultServiceProtocolMeterageApply")
    BaseResponse getLastFullProcessConsultServiceProtocolMeterageApply(@RequestParam String contractId);

    @PostMapping("/fullProcessConsultServiceProtocolMeterageApply/export")
    Response exportFullProcessConsultServiceProtocolMeterageApply(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request, @RequestHeader String token);

    @PostMapping("//fullProcessConsultServiceProtocolMeterageApply/getTZClassInfo")
    BaseResponse getFullProcessConsultServiceProtocolMeterageTZClassInfo(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request, @RequestHeader String token);

    @GetMapping("/fullProcessConsultServiceProtocolMeterageApply/getLastDeadline")
    BaseResponse getLastDeadline(@RequestParam String pId,@RequestParam String conId);
    @PostMapping("/v1/contract/getContractLedgerForWJT")
    BaseResponse getContractLedgerForWJT(@RequestBody ContractLedgerForWJTParam param);

    @PostMapping("/v1/contract/updateContractInfoWJTById")
    BaseResponse updateContractInfoWJTById(@RequestBody ConContractRequest request);

    @PostMapping("/v1/contract/updateContractInfoWJTQD")
    BaseResponse updateContractInfoWJTQD(@RequestBody ConContractRequest request);

    @PostMapping("/v1/conPay/uploadInvoice")
    BaseResponse uploadConPayInvoice(@RequestBody UploadPayInvoiceRequest request);


    @GetMapping("/v1/conPay/lastPeriodDeadline")
    BaseResponse lastPeriodDeadline(@RequestParam String  conId);


    @GetMapping("/v1/conPay/getpreUnderPayment")
    BaseResponse getpreUnderPayment(@RequestParam String pId);

    @GetMapping("/v1/conPay/getAccumulatedFeesByCId")
    BaseResponse getAccumulatedFeesByCId(@RequestParam String cId,@RequestParam String payCategory);

    @PostMapping("/v1/conPay/getConPayDataListTc")
    BaseResponse getConPayDataListTc(@RequestBody ConPayRequest request,@RequestHeader  String token);


    @PostMapping("/v1/conPay/getConPayDatadetailsTc")
    BaseResponse getConPayDatadetailsTc(@RequestBody ConPayRequest request,@RequestHeader  String token);
    @GetMapping("/v1/conPay/deleteConPayById")
    BaseResponse deleteConPayById(@RequestParam String payId);


    /***************************************太仓管委会资金拨付***************************************/

    @PostMapping("/taicang/plan/getContractTemplate")
    BaseResponse getContractTemplate(@RequestBody ContractResultVo contractResultVo);

    @PostMapping("/taicang/plan/saveContractPaymentPlan")
    BaseResponse saveContractPaymentPlan(@RequestBody ContractResultVo contractResultVo);

    @GetMapping("/taicang/plan/delContractTemplate")
    BaseResponse delContractTemplate(@RequestParam String id);

    @PostMapping("/taicang/plan/updateContractTemplate")
    BaseResponse updateContractTemplate(@RequestBody ContractResultVo contractResultVo) ;

    @PostMapping("/taicang/plan/updateContractTemplateDl")
    BaseResponse updateContractTemplateDl(@RequestBody ContractResultVo contractResultVo);

    @GetMapping("/taicang/plan/delContractTemplateDl")
    BaseResponse delContractTemplateDl(@RequestParam String id);

    @PostMapping("/taicang/plan/saveContractPaymentPlanDlNode")
    BaseResponse saveContractPaymentPlanDlNode(@RequestBody ContractResultVo contractResultVo);

    @GetMapping("/taicang/plan/getContrcatDataList")
    BaseResponse getContrcatDataList();

    @GetMapping("/taicang/plan/getPaymentConditionType")
    BaseResponse getPaymentConditionType();

    @GetMapping("/taicang/plan/getPaymentMethod")
    BaseResponse getPaymentMethod();

    @GetMapping("/taicang/plan/getPaymentBasis")
    BaseResponse getPaymentBasis();

    @PostMapping("/taicang/plan/getContrActDataList")
    BaseResponse getContrActDataList(@RequestBody ContractResultVo contractResultVo);

    @PostMapping("/taicang/plan/saveContractPaymentPlanBatch")
    BaseResponse saveContractPaymentPlanBatch(@RequestBody List<ContractResultVo> list);

    @PostMapping("/taicang/plan/getPayableConDataList")
    BaseResponse getPayableConDataList(@RequestBody ContractManageVo contractManageVo);


    @PostMapping("/taicang/plan/updatePaymentState")
    BaseResponse updatePaymentState(@RequestBody ContractManageVo contractManageVo);

    @GetMapping("/taicang/plan/getPaymentStateContr")
    BaseResponse getPaymentStateContr(@RequestParam("cId") String cId);

    @PostMapping("/taicang/plan/getCompanyPaySum")
    BaseResponse getCompanyPaySum(@RequestBody ContractManageVo contractManageVo);

    @PostMapping("/taicang/plan/getCapitalSituation")
    BaseResponse getCapitalSituation(@RequestBody ContractManageVo contractManageVo);

    @PostMapping("/taicang/plan/getProgressPlan")
    BaseResponse getProgressPlan(@RequestBody ProgressDeclarationVo progressDeclarationVo);

    @PostMapping("/taicang/plan/contrBrowse")
    BaseResponse contrBrowse(@RequestBody ContractBrowseVo contractBrowseVo);

    @PostMapping("/taicang/plan/moneyDisbursement")
    BaseResponse moneyDisbursement(@RequestBody ConPayDisbursementVo vo);

    @PostMapping("/taicang/plan/saveMoneyDisbursement")
    BaseResponse saveMoneyDisbursement(@RequestBody ConPayDisbursementVo vo);

    /***************************************合同权限配置-合同参与人员***************************************/

    @GetMapping("/v1/contractPermissionUser/addContractOne")
    BaseResponse addContractOne(@RequestParam String uid, @RequestParam String conId, @RequestHeader String token);

    @PostMapping("/v1/contractPermissionUser/addContractUserBatch")
    BaseResponse addContractUserBatch(@RequestBody List<String> userIds, @RequestParam String conId, @RequestHeader String token);
    @GetMapping("/v1/contractPermissionUser/addContractAll")
    BaseResponse addContractAll(@RequestParam String uid, @RequestHeader String token);
    @GetMapping("/v1/contractPermissionUser/deleteContractAll")
    BaseResponse deleteContractAll(@RequestParam String uid);
    @GetMapping("/v1/contractPermissionUser/deleteByConIdAndUid")
    BaseResponse deleteByConIdAndUid(@RequestParam String uid, @RequestParam String conId);

    @GetMapping("/v1/contractPermissionUser/deleteById")
    BaseResponse deletePermissionUserById(@RequestParam String id) ;

    @PostMapping("/v1/contractPermissionUser/selectUserPage")
    BaseResponse selectUserPage(@RequestBody ContractPermissionUserDTO request);

    @PostMapping("/v1/contractPermissionUser/selectContractPage")
    BaseResponse selectContractPage(@RequestBody ContractPermissionUserDTO request);

    /***************************************合同权限配置-合同参与单位***************************************/

    @GetMapping("/v1/contractPermissionCompany/addPermissionCompany")
    BaseResponse addPermissionCompany(@RequestParam String conId, @RequestParam String cId, @RequestHeader String token);

    @GetMapping("/v1/contractPermissionCompany/deletePermissionCompanyById")
    BaseResponse deletePermissionCompanyById(@RequestParam String id);

    @PostMapping("/v1/contractPermissionCompany/getPermissionCompanyList")
    BaseResponse getPermissionCompanyList(@RequestBody ContractPermissionCompanyDTO request);


    @PostMapping("/v1/contract/getFinalAccountDataList")
    BaseResponse getFinalAccountDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token);

    @PostMapping("/v1/contract/getPreliminaryAuditDataList")
    BaseResponse getPreliminaryAuditDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token);

    @PostMapping("/v1/contract/getTCSettlementAuditDataList")
    BaseResponse getTCSettlementAuditDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token);

    @PostMapping("/v1/contract/getBalancePaymentDataList")
    BaseResponse getBalancePaymentDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token);

    @PostMapping("/v1/contract/getBalancePaymentPlanDataList")
    BaseResponse getBalancePaymentPlanDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token);


    @GetMapping("/v1/conPay/chectConPayById")
    BaseResponse chectConPayByid(@RequestParam String conId);

    @PostMapping("/v1/contract/getConpayByIds")
    BaseResponse getConpayByIds(@RequestBody ConPayRequest request);

    @PostMapping("/v1/contract/getConpayByIdsGDGY")
    BaseResponse getConpayByIdsGDGY(@RequestBody ConPayRequest request);

    @PostMapping("/v1/conPay/flashbackConPay")
    BaseResponse flashbackConPay(@RequestParam("size") String size);

    @GetMapping("/v1/conPay/getFlashback")
    BaseResponse getFlashback();

    @GetMapping("/v1/conEcFee/getPreliminaryAmountSumByConId")
    BaseResponse getPreliminaryAmountSumByConId(@RequestParam("conId") String conId);

    @GetMapping("/v1/conEcFee/getConEcFeeChangePayList")
    BaseResponse getConEcFeeChangePayList(@RequestBody ConEcFeeRequest request, @RequestHeader String token);

    @GetMapping("/v1/contractPermissionUser/getCurrentUserContractIdList")
    BaseResponse getCurrentUserConIdList(@RequestHeader String token);
    @GetMapping("/v1/conEc/invalidConEcByEcId")
    BaseResponse invalidConEcByEcId(@RequestParam("ecId")String ecId);

    @GetMapping("/v1/conPay/getConPayApproveByPid")
    BaseResponse getConPayApproveByPid(@RequestParam String pId);

    @PostMapping("/v1/contractMulti/createOrUpdateContractMulti")
    BaseResponse createOrUpdateContractMulti(@RequestBody ConContractMultiRequest request, @RequestHeader String token);

    @PostMapping("/v1/contractMulti/syncToContract")
    BaseResponse syncToContract(@RequestBody ConContractMultiRequest request);

    /**
     * 审批通过后同步多项目合同及扩展字段。
     *
     * @param request 包含多项目合同ID和目标合同模板编码tCode的请求
     * @return 同步结果
     */
    @PostMapping("/v1/contractMulti/syncToContractJNDX")
    BaseResponse syncToContractJNDX(@RequestBody ConContractMultiRequest request);

    @PostMapping("/v1/contractMulti/getContractMultiPage")
    BaseResponse getContractMultiPage(@RequestBody ConContractMultiRequest request, @RequestHeader String token);

    @GetMapping("/v1/contractMulti/getContractMultiById")
    BaseResponse getContractMultiById(@RequestParam("id") String id);

    @PostMapping("/v1/conPayMulti/syncToConPay")
    BaseResponse syncToConPay(@RequestBody ConPayMultiRequest request);

    @GetMapping("/v1/conPayMulti/getConPayMultiById")
    BaseResponse getConPayMultiById(@RequestParam("id") String id);

    @PostMapping("/v1/conPayMulti/getConPayMultiPage")
    BaseResponse getConPayMultiPage(@RequestBody ConPayMultiRequest request, @RequestHeader String token);

    @GetMapping("/v1/conPay/getConPayApproveByConId")
    BaseResponse getConPayApproveByConId(@RequestParam String conId);

    @PostMapping("/v1/conTemplate/updateConTemplateHistory")
    BaseResponse updateConTemplateHistory(@RequestBody ConTemplateDto request);

    @PostMapping("/v1/contract/getInvestmentControlLedgerHB")
    BaseResponse getInvestmentControlLedgerHB(@RequestBody ConContractRequest request);

    @PostMapping("/v1/conEc/isMajorChangeTtems")
    BaseResponse isMajorChangeTtems(@RequestBody ConEcRequest request);

    @PostMapping("/v1/conMeasure/list/getConMeasureClassInfo")
    BaseResponse getConMeasureClassInfo(@RequestBody ConMeasureDto request);

    @PostMapping("/v1/conMeasure/list/getConMeasureInfo")
    BaseResponse getConMeasureInfo(@RequestBody ConMeasureDto request);

    @PostMapping("/v1/conEc/getConEcListClasInfo")
    BaseResponse getConEcListClasInfo(@RequestBody ConEcRequest request, @RequestHeader String token);

    @PostMapping("/v1/contract/updateContractById")
    BaseResponse updateContractById(@RequestBody ConContractRequest request);

    @PostMapping("/v1/conMeasurePrice/createOrUpdateConMeasurePrice")
    BaseResponse createOrUpdateConMeasurePrice(@RequestBody ConMeasurePriceRequest request);

    @PostMapping("/v1/conMeasurePrice/getConMeasurePriceList")
    BaseResponse getConMeasurePriceList(@RequestBody ConMeasurePriceRequest request, @RequestHeader String token);

    @PostMapping("/v1/conMeasurePrice/getConMeasurePriceListNoAuth")
    BaseResponse getConMeasurePriceListNoAuth(@RequestBody ConMeasurePriceRequest request);

    @GetMapping("/v1/conMeasurePrice/getConMeasurePriceById")
    BaseResponse getConMeasurePriceById(@RequestParam String id);

    @GetMapping("/v1/conMeasurePrice/getConMeasurePriceInfoByConId")
    BaseResponse getConMeasurePriceInfoByConId(@RequestParam("conId") String conId, @RequestParam(value = "id", required = false) String id);

    @PostMapping("/v1/contractMeasurementValuation/createOrUpdateContractMeasurementValuation")
    BaseResponse createOrUpdateContractMeasurementValuation(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token);

    @GetMapping("/v1/contractMeasurementValuation/getContractMeasurementValuationById")
    BaseResponse getContractMeasurementValuationById(
            @RequestParam("id") String id,
            @RequestHeader("token") String token);

    @PostMapping("/v1/contractMeasurementValuation/getContractMeasurementValuationList")
    BaseResponse getContractMeasurementValuationList(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token);

    @PostMapping("/v1/contractMeasurementValuation/getContractMeasurementValuationClassInfo")
    BaseResponse getContractMeasurementValuationClassInfo(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token);

    @PostMapping("/v1/contractMeasurementValuation/getFinishedConsultAndInternalAgencyContractListByPId")
    BaseResponse getFinishedConsultAndInternalAgencyContractListByPId(
            @RequestBody ContractMeasurementContractPageRequest request,
            @RequestHeader("token") String token);

    @GetMapping("/v1/payChangeAssociation/existsAssociation")
    BaseResponse existsAssociation(@RequestParam(required = false) String conId, @RequestParam(required = false) String payId);

    @PostMapping("/v1/conPay/getConPayListNJCJ2")
    BaseResponse getConPayListNJCJ2(@RequestBody ConPayRequest request, @RequestHeader String token);

    @GetMapping("/v1/conPay/refreshPaymentStatus")
    BaseResponse refreshPaymentStatus(@RequestParam String payId);

    @PostMapping("/v1/conPay/getConPayListWJT")
    BaseResponse getConPayListWJT(@RequestBody ConContractRequest request, @RequestHeader String token);

    @GetMapping("/v1/conPay/getPayActualPayTotalByConId")
    BaseResponse getPayActualPayTotalByConId(@RequestParam String conId);

    @GetMapping("/v1/ConPayApply/syncMarketInvestmentPayment")
    BaseResponse syncMarketInvestmentPayment();

    @PostMapping("/v1/ConPayApply/getMarketInvestmentPaymentList")
    BaseResponse getMarketInvestmentPaymentList(@RequestBody MarketInvestmentPaymentDto request);

    @GetMapping("/v1/conPay/getLastPayBalanceByPId")
    BaseResponse getLastPayBalanceByPId(@RequestParam("pId") String pId, @RequestParam("contractPayType") String contractPayType, @RequestParam(value = "id", required = false) String id);

    @PostMapping("/v1/contract/getContractStatistics")
    BaseResponse getContractStatistics(@RequestBody ConContractRequest request);

    @PostMapping("/v1/contract/getFinishedContractMoneyByCompanyIds")
    BaseResponse getFinishedContractMoneyByCompanyIds(@RequestBody List<String> companyIds,
                                                       @RequestHeader("token") String token);

    @GetMapping("/v1/conPay/judgmentPayCodeRepeat")
    BaseResponse judgmentPayCodeRepeat(@RequestParam("payCode") String payCode, @RequestParam(value = "payId", required = false) String payId);

    @GetMapping("/v1/contract/syncToParticipantUnit")
    BaseResponse syncToParticipantUnit(@RequestParam String conId, @RequestHeader String token);

    @GetMapping("/v1/conPay/getLastPayActualPayTotal")
    BaseResponse getLastPayActualPayTotal(@RequestParam String pId, @RequestParam String payCategory, @RequestParam String payPayeeNamePro);
    @PostMapping("/v1/conPayPlan/updateConPayPlan")
    BaseResponse updateConPayPlan(@RequestBody ConContractPayPlanDto request);

    @GetMapping("/v1/conEc/getMoneyStatistics")
    BaseResponse getMoneyStatistics(@RequestParam String conId, @RequestParam String itemCategory);

    @PostMapping("/v1/contract/getContractListClassInfo")
    BaseResponse getContractListClassInfo(@RequestBody ConContractRequest request, @RequestHeader String token);
}
