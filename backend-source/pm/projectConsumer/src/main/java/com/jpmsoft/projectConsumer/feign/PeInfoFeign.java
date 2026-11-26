package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.*;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.projectConsumer.pojo.dto.peinfo.PeInfoUnMeetDto;
import com.jpmsoft.projectConsumer.pojo.dto.peinfo.PeMoneyReturnDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 履约评价
 */
@FeignClient(name = "project-producer", contextId = "peInfo")
public interface PeInfoFeign {

    /***************************************履约评价表***************************************/
    @PostMapping("/v1/peForm/createOrUpdatePeForm")
    BaseResponse createOrUpdatePeForm(@RequestBody PeFormRequest request);

    @PostMapping("/v1/peForm/getPeFormList")
    BaseResponse getPeFormList(@RequestBody PeFormRequest request, @RequestHeader String token);

    @GetMapping("/v1/peForm/deletePeFormByFId")
    BaseResponse deletePeFormByFId(@RequestParam String fId);

    @GetMapping("/v1/peForm/getPeFormCascadeInfoByFId")
    BaseResponse getPeFormCascadeInfoByFId(@RequestParam String fId);

    /***************************************履约评价表详细***************************************/

    @PostMapping("/v1/peFormDetail/createOrUpdatePeFormDetail")
    BaseResponse createOrUpdatePeFormDetail(@RequestBody PeFormDetailRequest request);

    @PostMapping("/v1/peFormDetail/getPeFormDetailList")
    BaseResponse getPeFormDetailList(@RequestBody PeFormDetailRequest request, @RequestHeader String token);

    @GetMapping("/v1/peFormDetail/deletePeFormDetailByFdId")
    BaseResponse deletePeFormDetailByFdId(@RequestParam String fdId);

    /***************************************评价标准***************************************/
    @PostMapping("/v1/peFormDetail/createOrUpdatePeFormStandard")
    BaseResponse createOrUpdatePeFormStandard(@RequestBody PeFormDetailStandardRequest request);

    @PostMapping("/v1/peFormDetail/getPeFormStandardList")
    BaseResponse getPeFormStandardList(@RequestBody PeFormDetailStandardRequest request);

    @GetMapping("/v1/peFormDetail/deletePeFormStandardById")
    BaseResponse deletePeFormStandardById(@RequestParam String sId);

    /***************************************评价等级规则***************************************/

    @GetMapping("/v1/peGradeRule/deletePeGradeRuleByRId")
    BaseResponse deletePeGradeRuleByRId(@RequestParam String rId);

    @PostMapping("/v1/peGradeRule/getPeGradeRuleList")
    BaseResponse getPeGradeRuleList(@RequestBody PeGradeRuleRequest request, @RequestHeader String token);

    @PostMapping("/v1/peGradeRule/createOrUpdatePeGradeRule")
    BaseResponse createOrUpdatePeGradeRule(@RequestBody PeGradeRuleRequest request);

    /***************************************履约评价***************************************/


    @PostMapping("/v1/peInfo/createOrUpdatePeInfo")
    BaseResponse createOrUpdatePeInfo(@RequestBody PeInfoModel model);

    @PostMapping("/v1/peInfo/getPeInfoList")
    BaseResponse getPeInfoList(@RequestBody PeInfoModel model, @RequestHeader String token);

    @PostMapping("/v1/peInfo/getPeInfoUnMeetApproval")
    BaseResponse getPeInfoUnMeetApproval(@RequestBody PeInfoUnMeetDto model, @RequestHeader String token);

    @PostMapping("/v1/peInfo/getPeInfoListNoAuth")
    BaseResponse getPeInfoListNoAuth(@RequestBody PeInfoModel model);

    @PostMapping("/v1/peInfo/getPeInfoRanking")
    BaseResponse getPeInfoRanking(@RequestBody PeInfoRankingRequest request);

    @GetMapping("/v1/peInfo/getPeInfoByPeId")
    BaseResponse getPeInfoByPeId(@RequestParam String peId);

    @GetMapping("/v1/peInfo/deletePeInfoByPeId")
    BaseResponse deletePeInfoByPeId(@RequestParam String peId);

    @PostMapping("/v1/peInfo/updateFlowState")
    BaseResponse updateFlowState(@RequestBody PeInfoModel model);

    /*********************考核评分汇总表*********************/
    @PostMapping("/v1/collect/createOrUpdatePeInfoCollect")
    BaseResponse createOrUpdatePeInfoCollect(@RequestBody PeInfoCollectRequest request);

    @GetMapping("/v1/collect/deletePeInfoCollectByPecId")
    BaseResponse deletePeInfoCollectByPecId(@RequestParam String pecId);

    @PostMapping("/v1/collect/updatePeInfoCollectFlowState")
    BaseResponse updatePeInfoCollectFlowState(@RequestBody PeInfoCollectRequest request);

    @PostMapping("/v1/collect/getPeInfoCollectList")
    BaseResponse getPeInfoCollectList(@RequestBody PeInfoCollectRequest request);

    @GetMapping("/v1/collect/getPeInfoCollectByPecId")
    BaseResponse getPeInfoCollectByPecId(@RequestParam String pecId);

    @GetMapping("/v1/collect/getPeInfoCollectExportByPecId")
    BaseResponse getPeInfoCollectExportByPecId(@RequestParam String pecId, @RequestHeader String token);

    @GetMapping("/v1/collect/deletePeInfoCollectDtlWhenBackByPecId")
    BaseResponse deletePeInfoCollectDtlWhenBackByPecId(@RequestParam String pecId);
    /*********************考核分汇总清单*********************/

    /**
     * 查询未生成清单的所有履约评价
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/collectDtl/getPeInfoNotGenerateCollectDtl")
    BaseResponse getPeInfoNotGenerateCollectDtl(@RequestBody PeInfoCollectDtlRequest request, @RequestHeader String token);

    @PostMapping("/v1/collectDtl/getPeInfoCollectDtlList")
    BaseResponse getPeInfoCollectDtlList(@RequestBody PeInfoCollectDtlRequest request, @RequestHeader String token);

    /*********************履约评价结果送达*********************/

    @PostMapping("/v1/peResult/signPeResult")
    BaseResponse signPeResult(@RequestBody PeResultRequest request);

    @PostMapping("/v1/peResult/getPeResultList")
    BaseResponse getPeResultList(@RequestBody PeResultRequest request, @RequestHeader String token);

    @PostMapping("/v1/peResult/getPeResultByRid")
    BaseResponse getPeResultByRid(@RequestBody PeResultRequest request);

    /*********************上会确认退回*********************/

    @PostMapping("/v1/meetingConfirm/meetingConfirmBack")
    BaseResponse meetingConfirmBack(@RequestBody PeMeetingConfirmBackRequest request);

    @PostMapping("/v1/meetingConfirm/getMeetingConfirmBackList")
    BaseResponse getMeetingConfirmBackList(@RequestBody PeMeetingConfirmBackRequest request, @RequestHeader String token);

    @PostMapping("/v1/meetingConfirm/updatePeInfoWhenMeetingConfirm")
    BaseResponse updatePeInfoWhenMeetingConfirm(@RequestBody PeInfoModel model);

    /*********************履约评价结果申诉*********************/

    @PostMapping("/v1/resultAppeal/createOrUpdateResultAppeal")
    BaseResponse createOrUpdateResultAppeal(@RequestBody PeResultAppealRequest request);

    @PostMapping("/v1/resultAppeal/getResultAppealList")
    BaseResponse getResultAppealList(@RequestBody PeResultAppealRequest request, @RequestHeader String token);

    @PostMapping("/v1/resultAppeal/deleteResultAppealById")
    BaseResponse deleteResultAppealById(@RequestBody PeResultAppealRequest request);

    /***************************************考核评分上会呈批***************************************/

    @PostMapping("/v1/peMeetingApproval/createOrUpdatePeMeetingApproval")
    BaseResponse createOrUpdatePeMeetingApproval(@RequestBody PeMeetingApprovalRequest request);

    @PostMapping("/v1/peMeetingApproval/getPeMeetingApprovalList")
    BaseResponse getPeMeetingApprovalList(@RequestBody PeMeetingApprovalRequest request, @RequestHeader String token);

    @GetMapping("/v1/peMeetingApproval/getPeMeetingApprovalById")
    BaseResponse getPeMeetingApprovalById(@RequestParam String maId);

    @GetMapping("/v1/peMeetingApproval/updatePeStateByMaId")
    BaseResponse updatePeStateByMaId(@RequestParam String peState, @RequestParam String maId);


    @GetMapping("/v1/peInfo/getPeInfoStatistics")
    BaseResponse getPeInfoStatistics(@RequestParam String yearStr);

    /***************************************考核评分上会呈批***************************************/

    @PostMapping("/v1/peMoneyReturn/createOrUpdatePeMoneyReturn")
    BaseResponse createOrUpdatePeMoneyReturn(@RequestBody PeMoneyReturnDTO request);

    @PostMapping("/v1/peMoneyReturn/getPeMoneyReturnList")
    BaseResponse getPeMoneyReturnList(@RequestBody PeMoneyReturnDTO request, @RequestHeader String token);

    @GetMapping("/v1/peMoneyReturn/getPeMoneyReturnById")
    BaseResponse getPeMoneyReturnById(@RequestParam String id);

    @GetMapping("/v1/peMoneyReturn/getPeMoneySumByConId")
    BaseResponse getPeMoneySumByConId(@RequestParam String conId);

}
