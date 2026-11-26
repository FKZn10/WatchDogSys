package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.StorageChangeDetail;
import com.jpmsoft.common.model.request.SecInfo.*;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.*;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.*;
import com.jpmsoft.projectConsumer.request.ReturnInitiatorDto;
import com.jpmsoft.projectConsumer.request.StorageChangeRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * 标段管理
 */
@FeignClient(name = "project-producer", contextId = "secInfo")
public interface SecFeign {

    /*********************标段信息*********************/
    @PostMapping("/v1/secInfo/createOrUpdateSecInfo")
    BaseResponse createOrUpdateSecInfo(@RequestBody SecInfoRequest request);

    @PostMapping("/v1/secInfo/getSecInfoList")
    BaseResponse getSecInfoList(@RequestBody SecInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/secInfo/getSecInfoListGroupByProject")
    BaseResponse getSecInfoListGroupByProject(@RequestBody SecInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/secInfo/getSecInfoListOne2Many")
    BaseResponse getSecInfoListOne2Many(@RequestBody SecInfoRequest request);

    @PostMapping("/v1/secInfo/getSecInfoListNoAuth")
    BaseResponse getSecInfoListNoAuth(@RequestBody SecInfoRequest request);

    @GetMapping("/v1/secInfo/deleteSecInfoById")
    BaseResponse deleteSecInfoById(@RequestParam String secId);

    @PostMapping("/v1/secInfo/getSecInfoByPId")
    BaseResponse getSecInfoByPId(@RequestBody SecInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/secInfo/getCjSecInfoByPId")
    BaseResponse getCjSecInfoByPId(@RequestBody SecInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/secInfo/updateSecBiddingTypeById")
    BaseResponse updateSecBiddingTypeById(@RequestBody SecInfoRequest request);

    @GetMapping("/v1/secInfo/getSecInfoById")
    BaseResponse getSecInfoById(@RequestParam String secId);

    @GetMapping("/v1/secInfo/getSecProjectList")
    BaseResponse getSecProjectList(@RequestBody SecProjectModel request, @RequestHeader String token);

    @GetMapping("/v1/secInfo/getSecProjectStatisticsList")
    BaseResponse getSecProjectStatisticsList(@RequestBody SecProjectModel request, @RequestHeader String token);

    @PostMapping("/v1/secInfo/updateApproveStatusSecInfo")
    BaseResponse updateApproveStatusSecInfo(@RequestBody SecInfoRequest request);

    @GetMapping("/v1/secInfo/secInfoCanHandle")
    BaseResponse secInfoCanHandle(@RequestParam String secId);

    @GetMapping("/v1/secInfo/existDuplicateName")
    BaseResponse existDuplicateName(@RequestParam String name, @RequestParam(required = false) String secId);

    @PostMapping("/v1/secInfo/getSecInfoAmountTree")
    BaseResponse getSecInfoAmountTree(@RequestBody SecInfoAmountDto planAmountDto);

    @PostMapping("/v1/secInfo/getSecInfoTZClassInfo")
    BaseResponse getSecInfoTZClassInfo(@RequestBody SecInfoTZRequest request, @RequestHeader String token);

    @PostMapping("/v1/secInfo/getInvestmentSecInfo")
    BaseResponse getInvestmentSecInfo(@RequestBody InvestmentSecInfoRequest request);


    /***********************************************招标公告***************************************************/
    @PostMapping("/v1/notice/getSecNoticeList")
    BaseResponse getSecNoticeList(@RequestBody SecNoticeRequest request);

    @GetMapping("/v1/notice/deleteSecNoticeByNid")
    BaseResponse deleteSecNoticeByNid(@RequestParam String nId);

    @GetMapping("/v1/notice/getSecNoticeByNid")
    BaseResponse getSecNoticeByNid(@RequestParam String nId);

    @GetMapping("/v1/notice/getSecNoticeLastApprovedBySecId")
    BaseResponse getSecNoticeLastApprovedBySecId(@RequestParam String secId);

    @PostMapping("/v1/notice/createOrUpdateSecNotice")
    BaseResponse createOrUpdateSecNotice(@RequestBody SecNoticeRequest request);

    /***********************************************招标文件***************************************************/
    @PostMapping("/v1/secDocument/createOrUpdateSecDocument")
    BaseResponse createOrUpdateSecDocument(@RequestBody SecDocumentRequest request);

    @GetMapping("/v1/secDocument/deleteSecDocumentBydId")
    BaseResponse deleteSecDocumentBydId(@RequestParam String dId);

    @GetMapping("/v1/secDocument/can2CreateSecDocument")
    BaseResponse can2CreateSecDocument(@RequestParam String secId);


    @GetMapping("/v1/secDocument/getSecDocumentBydId")
    BaseResponse getSecDocumentBydId(@RequestParam String dId);

    @GetMapping("/v1/secDocument/getSecDocumentBySecId")
    BaseResponse getSecDocumentBySecId(@RequestParam String secId);

    @PostMapping("/v1/secDocument/getSecDocumentList")
    BaseResponse getSecDocumentList(@RequestBody SecDocumentRequest request);

    @PostMapping("/v1/secDocument/getSecDocumentListNJCJ")
    BaseResponse getSecDocumentListNJCJ(@RequestBody SecDocumentRequest request);


    @PostMapping("/v1/secDocument/updateSecDocumentById")
    BaseResponse updateSecDocumentById(@RequestBody SecDocumentRequest request);

    @PostMapping("/v1/secDocument/getSecDocumentListOne2Many")
    BaseResponse getSecDocumentListOne2Many(@RequestBody SecDocumentRequest request);

    @PostMapping("/v1/secDocument/getSecDocumentGroupByProject")
    BaseResponse getSecDocumentGroupByProject(@RequestBody SecDocumentRequest request, @RequestHeader String token);

    /***********************************************答疑补遗***************************************************/
    @PostMapping("/v1/answerQuestion/createOrUpdateSecAnswerQuestion")
    BaseResponse createOrUpdateSecAnswerQuestion(@RequestBody SecAnswerQuestionRequest request);

    @GetMapping("/v1/answerQuestion/deleteSecAnswerQuestionByaqId")
    BaseResponse deleteSecAnswerQuestionByaqId(@RequestParam String aqId);

    @GetMapping("/v1/answerQuestion/getSecAnswerQuestionByaqId")
    BaseResponse getSecAnswerQuestionByaqId(@RequestParam String aqId);

    @PostMapping("/v1/answerQuestion/getSecAnswerQuestionList")
    BaseResponse getSecAnswerQuestionList(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token);

    @PostMapping("/v1/answerQuestion/getSecAnswerQuestionListOne2Many")
    BaseResponse getSecAnswerQuestionListOne2Many(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token);

    @GetMapping("/v1/answerQuestion/approveSyncDocumentBidTimeAndLocation")
    BaseResponse approveSyncDocumentBidTimeAndLocation(@RequestParam String aqId);

    /***********************************************招标控制价***************************************************/
    @PostMapping("/v1/controlPrice/createOrUpdateControlPrice")
    BaseResponse createOrUpdateControlPrice(@RequestBody SecControlPriceRequest request);

    @GetMapping("/v1/controlPrice/deleteControlPriceBycpId")
    BaseResponse deleteControlPriceBycpId(@RequestParam String cpId);

    @GetMapping("/v1/controlPrice/getControlPriceBycpId")
    BaseResponse getControlPriceBycpId(@RequestParam String cpId);

    @PostMapping("/v1/controlPrice/getControlPriceList")
    BaseResponse getControlPriceList(@RequestBody SecControlPriceRequest request);

    @GetMapping("/v1/controlPrice/getControlPriceBysecId")
    BaseResponse getControlPriceBysecId(@RequestParam String secId);

    /***********************************************中标通知书***************************************************/
    @PostMapping("/v1/winNotice/createOrUpdateWinNotice")
    BaseResponse createOrUpdateWinNotice(@RequestBody SecWinNoticeRequest request);

    @PostMapping("/v1/winNotice/getWinNoticeListOne2Many")
    BaseResponse getWinNoticeList(@RequestBody SecWinNoticeRequest request);

    @PostMapping("/v1/winNotice/getWinNoticeListOne2Many")
    BaseResponse getWinNoticeListOne2Many(@RequestBody SecWinNoticeRequest request);

    @GetMapping("/v1/winNotice/getWinNoticeByWnId")
    BaseResponse getWinNoticeByWnId(@RequestParam String wnId);

    @GetMapping("/v1/winNotice/getLatestApproveWinNotice")
    BaseResponse getLatestApproveWinNotice(@RequestParam String secId);

    @GetMapping("/v1/winNotice/deleteWinNoticeByWnId")
    BaseResponse deleteWinNoticeByWnId(@RequestParam String wnId);

    /***********************************************资格审查小组***************************************************/
    @PostMapping("/v1/qeGroup/createOrUpdateSecQeGroup")
    BaseResponse createOrUpdateSecQeGroup(@RequestBody SecQeGroupRequest request);

    @GetMapping("/v1/qeGroup/deleteSecQeGroupByQegId")
    BaseResponse deleteSecQeGroupByQegId(@RequestParam String qegId);

    @GetMapping("/v1/qeGroup/getSecQeGroupByQegId")
    BaseResponse getSecQeGroupByQegId(@RequestParam String qegId);

    /***********************************************资格审查报告***************************************************/

    @PostMapping("/v1/qeReport/createOrUpdateQeReport")
    BaseResponse createOrUpdateQeReport(@RequestBody SecQeReportRequest request);

    @PostMapping("/v1/qeReport/getQeReportList")
    BaseResponse getQeReportList(@RequestBody SecQeReportRequest request);

    @GetMapping("/v1/qeReport/deleteQeReportByQerId")
    BaseResponse deleteQeReportByQerId(@RequestParam String qerId);

    @GetMapping("/v1/qeReport/getQeReportByQerId")
    BaseResponse getQeReportByQerId(@RequestParam String qerId);

    /***********************************************定标资料***************************************************/

    @PostMapping("/v1/determinDoc/createOrUpdateDeterminDoc")
    BaseResponse createOrUpdateDeterminDoc(@RequestBody SecDeterminDocRequest request);

    @GetMapping("/v1/determinDoc/cancelSecDeterminDocByBdId")
    BaseResponse cancelSecDeterminDocByBdId(@RequestParam String bdId);

    @GetMapping("/v1/determinDoc/getSecDeterminDocByBdId")
    BaseResponse getSecDeterminDocByBdId(@RequestParam String bdId);


    /***********************************************评标报告***************************************************/

    @PostMapping("/v1/evalReport/createOrUpdateEvalReport")
    BaseResponse createOrUpdateEvalReport(@RequestBody SecEvalReportRequest request);

    @GetMapping("/v1/evalReport/cancelSecEvalReportByErId")
    BaseResponse cancelSecEvalReportByErId(@RequestParam String erId);

    @GetMapping("/v1/evalReport/getSecEvalReportByErId")
    BaseResponse getSecEvalReportByErId(@RequestParam String erId);

    @GetMapping("/v1/evalReport/getSecEvalReportBySecId")
    BaseResponse getSecEvalReportBySecId(@RequestParam String secId);

    /**********************************************评标专家构成***************************************************/
    @PostMapping("/v1/bee/createOrUpdateSecBidEvaluationExpert")
    BaseResponse createOrUpdateSecBidEvaluationExpert(@RequestBody SecBidEvaluationExpertRequest request);

    @GetMapping("/v1/bee/getSecBidEvaluationExpertById")
    BaseResponse getSecBidEvaluationExpertById(@RequestParam String beeId);

    @PostMapping("/v1/bee/getSecBidEvaluationExpertList")
    BaseResponse getSecBidEvaluationExpertList(@RequestBody SecBidEvaluationExpertRequest request);

    /**********************************************开标结果***************************************************/
    @PostMapping("/v1/openResult/createOrUpdateSecOpenResult")
    BaseResponse createOrUpdateSecOpenResult(@RequestBody SecOpenResultRequest request);

    @GetMapping("/v1/openResult/getSecOpenResultById")
    BaseResponse getSecOpenResultById(@RequestParam String orId);

    @PostMapping("/v1/openResult/getSecOpenResultList")
    BaseResponse getSecOpenResultList(@RequestBody SecOpenResultRequest request);

    @PostMapping("/v1/openResult/getSecOpenResultListOne2Many")
    BaseResponse getSecOpenResultListOne2Many(@RequestBody SecOpenResultRequest request);

    /**********************************************中标结果公示***************************************************/
    @PostMapping("/v1/publicityResults/createOrUpdateSecPublicityResults")
    BaseResponse createOrUpdateSecPublicityResults(@RequestBody SecPublicityResultsRequest request);

    @GetMapping("/v1/publicityResults/getSecPublicityResultsById")
    BaseResponse getSecPublicityResultsById(@RequestParam String prId);

    @PostMapping("/v1/publicityResults/getSecPublicityResultsList")
    BaseResponse getSecPublicityResultsList(@RequestBody SecPublicityResultsRequest request);

    @PostMapping("/v1/publicityResults/getSecPublicityResultsListOne2Many")
    BaseResponse getSecPublicityResultsListOne2Many(@RequestBody SecPublicityResultsRequest request);

    /**********************************************中标质疑投诉处理***************************************************/
    @PostMapping("/v1/challenge/createOrUpdateSecChallengeComplaints")
    BaseResponse createOrUpdateSecChallengeComplaints(@RequestBody SecChallengeComplaintsRequest request);

    @GetMapping("/v1/challenge/getSecChallengeComplaintsById")
    BaseResponse getSecChallengeComplaintsById(@RequestParam String sccId);

    @PostMapping("/v1/challenge/getSecChallengeComplaintsList")
    BaseResponse getSecChallengeComplaintsList(@RequestBody SecChallengeComplaintsRequest request);


    @PostMapping("/v1/challenge/getSecChallengeComplaintsListOne2Many")
    BaseResponse getSecChallengeComplaintsListOne2Many(@RequestBody SecChallengeComplaintsRequest request);

    /**********************************************开评标质疑投诉处理***************************************************/

    @PostMapping("/v1/openComplaints/createOrUpdateSecOpenComplaints")
    BaseResponse createOrUpdateSecOpenComplaints(@RequestBody SecOpenComplaintsDto request);

    @PostMapping("/v1/openComplaints/getSecOpenComplaintsList")
    BaseResponse getSecOpenComplaintsList(@RequestBody SecOpenComplaintsDto request);

    @GetMapping("/v1/openComplaints/getSecOpenComplaintsById")
    BaseResponse getSecOpenComplaintsById(@RequestParam String id);

    /**********************************************招标文件***************************************************/
    @PostMapping("/v1/determinResult/createOrUpdateSecDeterminResult")
    BaseResponse createOrUpdateSecDeterminResult(@RequestBody SecDeterminResultRequest request);

    @GetMapping("/v1/determinResult/getSecDeterminResultById")
    BaseResponse getSecDeterminResultById(@RequestParam String drId);

    @GetMapping("/v1/determinResult/getFirstDeterminResultBySecId")
    BaseResponse getFirstDeterminResultBySecId(@RequestParam String secId);

    @PostMapping("/v1/determinResult/getSecDeterminResultList")
    BaseResponse getSecDeterminResultList(@RequestBody SecDeterminResultRequest request);

    /**********************************************招标方案***************************************************/
    @PostMapping("/v1/biddingScheme/createOrUpdateSecBiddingScheme")
    BaseResponse createOrUpdateSecBiddingScheme(@RequestBody SecBiddingSchemeModel request);

    @PostMapping("/v1/biddingScheme/getSecBiddingSchemeList")
    BaseResponse getSecBiddingSchemeList(@RequestBody SecBiddingSchemeModel request);

    @GetMapping("/v1/biddingScheme/getSecBiddingSchemeById")
    BaseResponse getSecBiddingSchemeById(@RequestParam String bsId);

    @GetMapping("/v1/biddingScheme/updateSecBiddingScheme")
    BaseResponse updateSecBiddingScheme(@RequestBody SecBiddingSchemeModel request);

    /**********************************************其它采购***************************************************/

    @PostMapping("/v1/otherProcurement/createOrUpdateSecOtherProcurement")
    BaseResponse createOrUpdateSecOtherProcurement(@RequestBody SecOtherProcurementModel request);

    @PostMapping("/v1/otherProcurement/getSecOtherProcurementList")
    BaseResponse getSecOtherProcurementList(@RequestBody SecOtherProcurementModel request);

    @GetMapping("/v1/otherProcurement/getSecOtherProcurementById")
    BaseResponse getSecOtherProcurementById(@RequestParam String sopId);

    /**********************************************投标保证金***************************************************/

    @PostMapping("/v1/bidSecurity/createOrUpdateSecBidSecurity")
    BaseResponse createOrUpdateSecBidSecurity(@RequestBody SecBidSecurityModel request);

    @PostMapping("/v1/bidSecurity/getSecBidSecurityList")
    BaseResponse getSecBidSecurityList(@RequestBody SecBidSecurityModel request);

    @GetMapping("/v1/bidSecurity/getSecBidSecurityById")
    BaseResponse getSecBidSecurityById(@RequestParam String sbsId);

    /**********************************************招标采购备案***************************************************/
    @PostMapping("/v1/procurementFiling/createOrUpdateSecProcurementFiling")
    BaseResponse createOrUpdateSecProcurementFiling(@RequestBody SecProcurementFilingModel request);

    @PostMapping("/v1/procurementFiling/getSecProcurementFilingList")
    BaseResponse getSecProcurementFilingList(@RequestBody SecProcurementFilingModel request, @RequestHeader String token);

    @GetMapping("/v1/procurementFiling/getSecProcurementFilingById")
    BaseResponse getSecProcurementFilingById(@RequestParam String pfId);

    @GetMapping("/v1/procurementFiling/getSecProcurementFilingLastByPid")
    BaseResponse getSecProcurementFilingLastByPid(@RequestParam String pId);

    @GetMapping("/v1/procurementFiling/approve2GenerateSecInfo")
    BaseResponse approve2GenerateSecInfo(@RequestParam String pfId);

    /**********************************************定标准备方案***************************************************/
    @PostMapping("/v1/calibrationScheme/createOrUpdateSecCalibrationScheme")
    BaseResponse createOrUpdateSecCalibrationScheme(@RequestBody SecCalibrationSchemeModel request);

    @PostMapping("/v1/calibrationScheme/getSecCalibrationSchemeList")
    BaseResponse getSecCalibrationSchemeList(@RequestBody SecCalibrationSchemeModel request);

    @GetMapping("/v1/calibrationScheme/getSecCalibrationSchemeById")
    BaseResponse getSecCalibrationSchemeById(@RequestParam String scsId);

    /**********************************************过多投标人淘汰***************************************************/
    @PostMapping("/v1/bidderElimination/createOrUpdateSecBidderElimination")
    BaseResponse createOrUpdateSecBidderElimination(@RequestBody SecBidderEliminationModel request);

    @PostMapping("/v1/bidderElimination/getSecBidderEliminationList")
    BaseResponse getSecBidderEliminationList(@RequestBody SecBidderEliminationModel request);

    @GetMapping("/v1/bidderElimination/getSecBidderEliminationById")
    BaseResponse getSecBidderEliminationById(@RequestParam String sbeId);

    /**********************************************招标清标***************************************************/
    @PostMapping("/v1/brightMoon/createOrUpdateSecBrightMoon")
    BaseResponse createOrUpdateSecBrightMoon(@RequestBody SecBrightMoonModel request);

    @PostMapping("/v1/brightMoon/getSecBrightMoonList")
    BaseResponse getSecBrightMoonList(@RequestBody SecBrightMoonModel request);

    @GetMapping("/v1/brightMoon/getSecBrightMoonById")
    BaseResponse getSecBrightMoonById(@RequestParam String sbmId);

    /**********************************************定标清标***************************************************/
    @PostMapping("/v1/calibrationMoon/createOrUpdateSecCalibrationMoon")
    BaseResponse createOrUpdateSecCalibrationMoon(@RequestBody SecBrightMoonModel request);

    @PostMapping("/v1/calibrationMoon/getSecCalibrationMoonList")
    BaseResponse getSecCalibrationMoonList(@RequestBody SecBrightMoonModel request);

    @GetMapping("/v1/calibrationMoon/getSecCalibrationMoonById")
    BaseResponse getSecCalibrationMoonById(@RequestParam String sbmId);

    /**********************************************工程概算***************************************************/

    @GetMapping("/v1/secInfoBudget/getSecInfoBudgetList")
    BaseResponse getSecInfoBudgetList(@RequestParam String sibCost);

    /**********************************************专家库***************************************************/

    @PostMapping("/v1/secExpert/createOrUpdateSecExpert")
    BaseResponse createOrUpdateSecExpert(@RequestBody SecExpertModel request);

    @PostMapping("/v1/secExpert/createOrUpdateSecExpertCurrent")
    BaseResponse createOrUpdateSecExpertCurrent(@RequestBody SecExpertModel request);

    @PostMapping("/v1/secExpert/createOrUpdateSecExpertVerify")
    BaseResponse createOrUpdateSecExpertVerify(@RequestBody SecExpertModel request);

    @GetMapping("/v1/secExpert/resetPlaintextNum")
    BaseResponse resetPlaintextNum();

    @PostMapping("/v1/secExpert/getSecExpertList")
    BaseResponse getSecExpertList(@RequestBody SecExpertModel request);

    @PostMapping("/v1/secExpert/getSecExpertCjDzList")
    BaseResponse getSecExpertCjDzList(@RequestBody SecExpertModel request, @RequestHeader String token);

    @PostMapping("/v1/secExpert/exportExcelSecExpertCjDzList")
    Response exportExcelSecExpertCjDzList(@RequestBody SecExpertModel request, @RequestHeader String token);

    @GetMapping("/v1/secExpert/getSecExpertById")
    BaseResponse getSecExpertById(@RequestParam String exId);

    @GetMapping("/v1/secExpert/deleteSecExpertById")
    BaseResponse deleteSecExpertById(@RequestParam String exId);

    @GetMapping("/v1/secExpert/expertExists")
    BaseResponse expertExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token);

    @PostMapping("/v1/secExpert/extractExpertInfo")
    BaseResponse extractExpertInfo(@RequestBody ExtractExpertDto request, @RequestHeader String token);

    @PostMapping("/v1/secExpert/extractExpert")
    BaseResponse extractExpert(@RequestBody ExtractExpertDto request, @RequestHeader String token);

    @PostMapping("/v1/secExpert/getSecExpertWhenVacation")
    BaseResponse getSecExpertWhenVacation(@RequestBody SecExpertVacationDTO request, @RequestHeader String token);

    @PostMapping("/v1/secExpert/getSecExpertWhenVacationNoId")
    BaseResponse getSecExpertWhenVacationNoId(@RequestBody SecExpertVacationDTO request, @RequestHeader String token);

    @GetMapping("/v1/secExpert/getSecExpertByCurrent")
    BaseResponse getSecExpertByCurrent(@RequestHeader String token);

    @PostMapping("/v1/secExpert/updatePersonnel")
    public BaseResponse updatePersonnel(@RequestBody SecExpertModel request);

    /**********************************************专家库（建管）***************************************************/

    @PostMapping("/v1/secJGExpert/createOrUpdateSecJGExpert")
    BaseResponse createOrUpdateSecJGExpert(@RequestBody SecJGExpertModel request);

    @PostMapping("/v1/secJGExpert/createOrUpdateSecJGExpertCurrent")
    BaseResponse createOrUpdateSecJGExpertCurrent(@RequestBody SecJGExpertModel request);

    @PostMapping("/v1/secJGExpert/createOrUpdateSecJGExpertVerify")
    BaseResponse createOrUpdateSecJGExpertVerify(@RequestBody SecJGExpertModel request);

    @GetMapping("/v1/secJGExpert/resetJGPlaintextNum")
    BaseResponse resetJGPlaintextNum();

    @PostMapping("/v1/secJGExpert/getSecJGExpertList")
    BaseResponse getSecJGExpertList(@RequestBody SecJGExpertModel request);

    @PostMapping("/v1/secJGExpert/getSecJGExpertCjDzList")
    BaseResponse getSecJGExpertCjDzList(@RequestBody SecJGExpertModel request, @RequestHeader String token);

    @PostMapping("/v1/secJGExpert/exportExcelSecJGExpertCjDzList")
    Response exportExcelSecJGExpertCjDzList(@RequestBody SecJGExpertModel request, @RequestHeader String token);

    @PostMapping("/v1/secJGExpert/excelSecJGExpertTemplate")
    Response excelSecJGExpertTemplate(@RequestBody SecJGExpertModel request, @RequestHeader String token);


    @PostMapping(value = "/v1/secJGExpert/importSecJGExpert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResponse importSecJGExpert(@RequestPart("files") MultipartFile files, @RequestHeader String token);

    @GetMapping("/v1/secJGExpert/getSecJGExpertById")
    BaseResponse getSecJGExpertById(@RequestParam String exId);

    @GetMapping("/v1/secJGExpert/deleteSecJGExpertById")
    BaseResponse deleteSecJGExpertById(@RequestParam String exId);

    @GetMapping("/v1/secJGExpert/expertJGExists")
    BaseResponse expertJGExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token);

    @PostMapping("/v1/secJGExpert/extractJGExpertInfo")
    BaseResponse extractJGExpertInfo(@RequestBody ExtractJGExpertDto request, @RequestHeader String token);

    @PostMapping("/v1/secJGExpert/extractJGExpert")
    BaseResponse extractJGExpert(@RequestBody ExtractJGExpertDto request, @RequestHeader String token);

    @PostMapping("/v1/secJGExpert/extractJGAgencyExpert")
    BaseResponse extractJGAgencyExpert(@RequestBody ExtractJGAgencyExpertDto request, @RequestHeader String token);


    @PostMapping("/v1/secJGExpert/getSecJGExpertWhenVacation")
    BaseResponse getSecJGExpertWhenVacation(@RequestBody SecJGExpertVacationDTO request, @RequestHeader String token);

    @PostMapping("/v1/secJGExpert/getSecJGExpertWhenVacationNoId")
    BaseResponse getSecJGExpertWhenVacationNoId(@RequestBody SecJGExpertVacationDTO request, @RequestHeader String token);

    @GetMapping("/v1/secJGExpert/getSecJGExpertByCurrent")
    BaseResponse getSecJGExpertByCurrent(@RequestHeader String token);

    /**********************************************招标公告发布***************************************************/

    @PostMapping("/v1/announcement/createOrUpdateSecAnnouncementBidding")
    BaseResponse createOrUpdateSecAnnouncementBidding(@RequestBody SecAnnouncementBiddingModel request);

    @PostMapping("/v1/announcement/getSecAnnouncementBiddingList")
    BaseResponse getSecAnnouncementBiddingList(@RequestBody SecAnnouncementBiddingModel request);

    @GetMapping("/v1/announcement/getSecAnnouncementBiddingById")
    BaseResponse getSecAnnouncementBiddingById(@RequestParam String sabId);


    /**********************************************招标计划***************************************************/

    @PostMapping("/v1/biddingPlan/createOrUpdateSecBiddingPlan")
    BaseResponse createOrUpdateSecBiddingPlan(@RequestBody SecBiddingPlanRequest request);

    @GetMapping("/v1/biddingPlan/refreshSecBiddingPlanSign")
    BaseResponse refreshSecBiddingPlanSign();

    @PostMapping("/v1/biddingPlan/getSecBiddingPlanList")
    BaseResponse getSecBiddingPlanList(@RequestBody SecBiddingPlanRequest request, @RequestHeader String token);

    @GetMapping("/v1/biddingPlan/getSecBiddingPlanById")
    BaseResponse getSecBiddingPlanById(@RequestParam String bpId);

    @GetMapping("/v1/biddingPlan/getSecBiddingPlanByPIdNJCJ")
    BaseResponse getSecBiddingPlanByPIdNJCJ(@RequestParam String pId);

    @PostMapping("/v1/biddingPlan/appoval2CreateSecInfoSysFile")
    BaseResponse appoval2CreateSecInfoSysFile(@RequestBody SecInfoRequest request);

    @PostMapping("/v1/biddingPlan/getSecBiddingPlanGroupByProject")
    BaseResponse getSecBiddingPlanGroupByProject(@RequestBody SecBiddingPlanRequest request, @RequestHeader String token);

    @PostMapping("/v1/biddingPlan/getTZClassInfo")
    BaseResponse getTZClassInfo(@RequestBody SecBiddingPlanTZRequest request, @RequestHeader String token);

    @GetMapping("/v1/biddingPlan/getSecBiddingPlanByPId")
    BaseResponse getSecBiddingPlanByPId(@RequestParam String pId);

    /**********************************************招标文件发布***************************************************/

    @PostMapping("/v1/documentRelease/createOrUpdateSecDocumentRelease")
    BaseResponse createOrUpdateSecDocumentRelease(@RequestBody SecDocumentReleaseModel request);

    @PostMapping("/v1/documentRelease/getSecDocumentReleaseList")
    BaseResponse getSecDocumentReleaseList(@RequestBody SecDocumentReleaseModel request);

    @GetMapping("/v1/documentRelease/getSecDocumentReleaseById")
    BaseResponse getSecDocumentReleaseById(@RequestParam String sdrId);

    @GetMapping("/v1/documentRelease/appoval2SyncDocument")
    BaseResponse appoval2SyncDocument(@RequestParam String sdrId);

    /**********************************************询价小组***************************************************/
    @PostMapping("/v1/inquiryTeam/createOrUpdateSecInquiryTeam")
    BaseResponse createOrUpdateSecInquiryTeam(@RequestBody SecInquiryTeamModel request);

    @GetMapping("/v1/inquiryTeam/deleteSecInquiryTeamBySitId")
    BaseResponse deleteSecInquiryTeamBySitId(@RequestParam String sitId);

    @GetMapping("/v1/inquiryTeam/getSecInquiryTeamBySitId")
    BaseResponse getSecInquiryTeamBySitId(@RequestParam String sitId);

    /**********************************************巡价结果***************************************************/

    @PostMapping("/v1/priceResultsf/createOrUpdateSecPriceResultsf")
    BaseResponse createOrUpdateSecPriceResultsf(@RequestBody SecPriceResultsfModel request);

    @PostMapping("/v1/priceResultsf/getSecPriceResultsfList")
    BaseResponse getSecPriceResultsfList(@RequestBody SecPriceResultsfModel request);

    @GetMapping("/v1/priceResultsf/deleteSecPriceResultsfBySprId")
    BaseResponse deleteSecPriceResultsfBySprId(@RequestParam String sprId);

    @GetMapping("/v1/priceResultsf/getSecPriceResultsfBySprId")
    BaseResponse getSecPriceResultsfBySprId(@RequestParam String sprId);

    /**********************************************暂估价***************************************************/
    @PostMapping("/v1/provisionalEstimate/createOrUpdateSecProvisionalEstimate")
    BaseResponse createOrUpdateSecProvisionalEstimate(@RequestBody SecProvisionalEstimateModel request);

    @PostMapping("/v1/provisionalEstimate/getSecProvisionalEstimateModelList")
    BaseResponse getSecProvisionalEstimateModelList(@RequestBody SecProvisionalEstimateModel request, @RequestHeader String token);

    @GetMapping("/v1/provisionalEstimate/getSecProvisionalEstimateByPeId")
    BaseResponse getSecProvisionalEstimateByPeId(@RequestParam String peId);

    /**********************************************专家选取记录（专家库、建管专家库等）***************************************************/

    @PostMapping("/v1/selectionRecordMultiple/createOrUpdateSecSelectionRecordMultiple")
    BaseResponse createOrUpdateSecSelectionRecordMultiple(@RequestBody SecSelectionRecordMultipleModel request, @RequestHeader String token);

    @PostMapping("/v1/selectionRecordMultiple/getSecSelectionRecordMultipleList")
    BaseResponse getSecSelectionRecordMultipleList(@RequestBody SecSelectionRecordMultipleModel request, @RequestHeader String token);

    @GetMapping("/v1/selectionRecordMultiple/getSecSelectionRecordMultipleById")
    BaseResponse getSecSelectionRecordMultipleById(@RequestParam String id);

    @GetMapping("/v1/selectionRecordMultiple/deleteSecSelectionRecordMultipleById")
    BaseResponse deleteSecSelectionRecordMultipleById(@RequestParam String id);

    @PostMapping("/v1/selectionRecordMultiple/createOrUpdateExpertExtractMultiple")
    BaseResponse createOrUpdateExpertExtractMultiple(@RequestBody SecSelectionRecordMultipleModel request, @RequestHeader String token);

    @GetMapping("/v1/selectionRecordMultiple/getSecExpertExtractMultipleById")
    BaseResponse getSecExpertExtractMultipleById(@RequestParam String id);

    @PostMapping("/v1/selectionRecordMultiple/extractJGExpertMultiple")
    BaseResponse extractJGExpertMultiple(@RequestBody ExtractExpertMultipleDto request, @RequestHeader String token);

    @PostMapping("/v1/selectionRecordMultiple/approvalDataExpertMultiple")
    BaseResponse approvalDataExpertMultiple(@RequestBody SecSelectionRecordMultipleModel request);

    @GetMapping("/v1/selectionRecordMultiple/getOtherExpertSecInfo")
    BaseResponse getOtherExpertSecInfo(@RequestParam String secId);

    /**********************************************专家选取记录***************************************************/

    @PostMapping("/v1/selectionRecord/createOrUpdateSecSelectionRecord")
    BaseResponse createOrUpdateSecSelectionRecord(@RequestBody SecSelectionRecordModel request);

    @PostMapping("/v1/selectionRecord/getSecSelectionRecordList")
    BaseResponse getSecSelectionRecordList(@RequestBody SecSelectionRecordModel request, @RequestHeader String token);

    @PostMapping("/v1/selectionRecord/getSecSelectionRecordClassInfo")
    BaseResponse getSecSelectionRecordClassInfo(@RequestBody SecSelectionRecordModel request, @RequestHeader String token);

    @GetMapping("/v1/selectionRecord/getSecSelectionRecordById")
    BaseResponse getSecSelectionRecordById(@RequestParam String srId);

    @GetMapping("/v1/selectionRecord/deleteSecSelectionRecordById")
    BaseResponse deleteSecSelectionRecordById(@RequestParam String srId);

    @GetMapping("/v1/selectionRecord/getSelectionRecordDtlCount")
    BaseResponse getSelectionRecordDtlCount(@RequestParam String exId);

    @PostMapping("/v1/selectionRecord/updateSecSelectionRecordDtl")
    BaseResponse updateSecSelectionRecordDtl(@RequestBody SecSelectionRecordDtlModel request);

    @PostMapping("/v1/selectionRecord/createOrUpdateExpertExtract")
    BaseResponse createOrUpdateExpertExtract(@RequestBody SecSelectionRecordModel request);

    @GetMapping("/v1/selectionRecord/getSecExpertExtractById")
    BaseResponse getSecExpertExtractById(@RequestParam String srId);

    @GetMapping("/v1/selectionRecord/getSecExpertExtractByEsId")
    BaseResponse getSecExpertExtractByEsId(@RequestParam String esId);

    @PostMapping("/v1/selectionRecord/getSecExpertExtractList")
    BaseResponse getSecExpertExtractList(@RequestBody SecExpertSelectDto request, @RequestHeader String token);

    @PostMapping("/v1/selectionRecord/getSectionDocumentCount")
    BaseResponse getSectionDocumentCount(@RequestBody SecSelectionRecordModel request);

    @GetMapping("/v1/selectionRecord/getTenderingMethodByProjectAndSection")
    BaseResponse getTenderingMethodByProjectAndSection(@RequestParam("pId") String pId,
                                                        @RequestParam("secId") String secId);

    @GetMapping("/v1/selectionRecord/cancelExtract2cancelSelect")
    BaseResponse cancelExtract2cancelSelect(@RequestParam String srId);

    /**********************************************专家选取记录（建管）***************************************************/

    @PostMapping("/v1/jgSelectionRecord/createOrUpdateSecJGSelectionRecord")
    BaseResponse createOrUpdateSecJGSelectionRecord(@RequestBody SecJGSelectionRecordModel request);

    @PostMapping("/v1/jgSelectionRecord/getSecJGSelectionRecordList")
    BaseResponse getSecJGSelectionRecordList(@RequestBody SecJGSelectionRecordModel request, @RequestHeader String token);

    @GetMapping("/v1/jgSelectionRecord/getJGSectionDocumentCount")
    BaseResponse getJGSectionDocumentCount(@RequestBody SecJGSelectionRecordModel request);


    @GetMapping("/v1/jgSelectionRecord/getSecJGSelectionRecordById")
    BaseResponse getSecJGSelectionRecordById(@RequestParam String srId);

    @GetMapping("/v1/jgSelectionRecord/deleteSecJGSelectionRecordById")
    BaseResponse deleteSecJGSelectionRecordById(@RequestParam String srId);

    @GetMapping("/v1/jgSelectionRecord/getJGSelectionRecordDtlCount")
    BaseResponse getJGSelectionRecordDtlCount(@RequestParam String exId);

    @PostMapping("/v1/jgSelectionRecord/updateSecJGSelectionRecordDtl")
    BaseResponse updateSecJGSelectionRecordDtl(@RequestBody SecJGSelectionRecordDtlModel request);

    @PostMapping("/v1/jgSelectionRecord/checkJGExPeople")
    BaseResponse checkJGExPeople(@RequestBody ExtractJGExpertDto request, @RequestHeader String token);


    @PostMapping("/v1/jgSelectionRecord/createOrUpdateJGExpertExtract")
    BaseResponse createOrUpdateJGExpertExtract(@RequestBody SecJGSelectionRecordModel request);

    @GetMapping("/v1/jgSelectionRecord/getSecJGExpertExtractById")
    BaseResponse getSecJGExpertExtractById(@RequestParam String srId);

    @GetMapping("/v1/jgSelectionRecord/getSecJGExpertExtractByEsId")
    BaseResponse getSecJGExpertExtractByEsId(@RequestParam String esId);

    @PostMapping("/v1/jgSelectionRecord/getSecJGExpertExtractList")
    BaseResponse getSecJGExpertExtractList(@RequestBody SecJGExpertSelectDto request, @RequestHeader String token);

    @GetMapping("/v1/jgSelectionRecord/cancelJGExtractCancelSelect")
    BaseResponse cancelJGExtractCancelSelect(@RequestParam String srId);

    /*********************招标计划变更*********************/

    @PostMapping("/v1/biddingPlanChange/createOrUpdateSecBiddingPlanChange")
    BaseResponse createOrUpdateSecBiddingPlanChange(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token);

    @PostMapping("/v1/biddingPlanChange/getSecBiddingPlanChangeList")
    BaseResponse getSecBiddingPlanChangeList(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token);

    @GetMapping("/v1/biddingPlanChange/getSecBiddingPlanChangeById")
    BaseResponse getSecBiddingPlanChangeById(@RequestParam String bpcId);

    @PostMapping("/v1/biddingPlanChange/approval2UpdateSecInfo")
    BaseResponse approval2UpdateSecInfo(@RequestBody SecBiddingPlanChangeRequest request, @RequestHeader String token);


    /*********************招标代理专家抽取*********************/

    @PostMapping("/v1/jgAgencySelectionRecord/createOrUpdateSecJGAgencySelectionRecord")
    BaseResponse createOrUpdateSecJGAgencySelectionRecord(@RequestBody SecJGAgencySelectionRecordModel request, @RequestHeader String token);

    @PostMapping("/v1/jgAgencySelectionRecord/getSecJGAgencySelectionRecordList")
    BaseResponse getSecJGAgencySelectionRecordList(@RequestBody SecJGAgencySelectionRecordModel request, @RequestHeader String token);

    @PostMapping("/v1/jgAgencySelectionRecord/getJGAgencySectionDocumentCount")
    BaseResponse getJGAgencySectionDocumentCount(@RequestBody SecJGAgencySelectionRecordModel request);


    @GetMapping("/v1/jgAgencySelectionRecord/getSecJGAgencySelectionRecordById")
    BaseResponse getSecJGAgencySelectionRecordById(@RequestParam String srId);

    @GetMapping("/v1/jgAgencySelectionRecord/deleteSecJGAgencySelectionRecordById")
    BaseResponse deleteSecJGAgencySelectionRecordById(@RequestParam String srId);


    @GetMapping("/v1/jgAgencySelectionRecord/getJGAgencySelectionRecordDtlCount")
    BaseResponse getJGAgencySelectionRecordDtlCount(@RequestParam String exId);

    @PostMapping("/v1/jgAgencySelectionRecord/updateSecJGAgencySelectionRecordDtl")
    BaseResponse updateSecJGAgencySelectionRecordDtl(@RequestBody SecJGAgencySelectionRecordDtlModel request);

    @PostMapping("/v1/jgAgencySelectionRecord/createOrUpdateJGAgencyExpertExtract")
    BaseResponse checkJGAgencyExPeople(@RequestBody ExtractJGAgencyExpertDto request, @RequestHeader String token);


    @PostMapping("/v1/jgAgencySelectionRecord/createOrUpdateJGAgencyExpertExtract")
    BaseResponse createOrUpdateJGAgencyExpertExtract(@RequestBody SecJGAgencySelectionRecordModel request, @RequestHeader String token);


    @GetMapping("/v1/jgAgencySelectionRecord/getSecJGAgencyExpertExtractById")
    BaseResponse getSecJGAgencyExpertExtractById(@RequestParam String srId);

    @GetMapping("/v1/jgAgencySelectionRecord/getSecJGAgencyExpertExtractByEsId")
    BaseResponse getSecJGAgencyExpertExtractByEsId(@RequestParam String esId);

    @GetMapping("/v1/jgAgencySelectionRecord/getSecJGAgencySelectionRecordDtlModelById")
    BaseResponse getSecJGAgencySelectionRecordDtlModelById(@RequestParam String srId);

    @PostMapping("/v1/jgAgencySelectionRecord/getSecJGAgencyExpertExtractList")
    BaseResponse getSecJGAgencyExpertExtractList(@RequestBody SecJGAgencyExpertSelectDto request, @RequestHeader String token);


    @GetMapping("/v1/jgAgencySelectionRecord/cancelJGAgencyExtractCancelSelect")
    BaseResponse cancelJGAgencyExtractCancelSelect(@RequestParam String srId);

    @GetMapping("/v1/jgAgencySelectionRecord/getSecJGAgencyValueById")
    BaseResponse getSecJGAgencyValueById(@RequestParam String srId);

    /*********************定标报告*********************/

    @PostMapping("/v1/calibrationReport/createOrUpdateCalibrationReport")
    BaseResponse createOrUpdateCalibrationReport(@RequestBody CalibrationReportDto request);

    @PostMapping("/v1/calibrationReport/createCalibrationReport")
    BaseResponse createCalibrationReport(@RequestBody CalibrationReportDto request);

    @PostMapping("/v1/calibrationReport/updateCalibrationReport")
    BaseResponse updateCalibrationReport(@RequestBody CalibrationReportDto request);

    @PostMapping("/v1/calibrationReport/getCalibrationReportList")
    BaseResponse getCalibrationReportList(@RequestBody CalibrationReportDto request);

    @GetMapping("/v1/calibrationReport/getCalibrationReportById")
    BaseResponse getCalibrationReportById(@RequestParam String id);

    @GetMapping("/v1/calibrationReport/getCalibrationReportBySecId")
    BaseResponse getCalibrationReportBySecId(@RequestParam String secId);

    /*********************招标文件控制价*********************/

    @PostMapping("/v1/docControl/createOrUpdateSecDocControl")
    BaseResponse createOrUpdateSecDocControl(@RequestBody SecDocControlDto request);

    @PostMapping("/v1/docControl/getSecDocControlList")
    BaseResponse getSecDocControlList(@RequestBody SecDocControlDto request);

    @GetMapping("/v1/docControl/getSecDocControlById")
    BaseResponse getSecDocControlById(@RequestParam String id);

    /*********************询价记录*********************/

    @PostMapping("/v1/secInquiryRecord/createOrUpdateSecInquiryRecord")
    BaseResponse createOrUpdateSecInquiryRecord(@RequestBody SecInquiryRecordDto request);


    @PostMapping("/v1/secInquiryRecord/getSecInquiryRecordList")
    BaseResponse getSecInquiryRecordList(@RequestBody SecInquiryRecordDto request);

    @GetMapping("/v1/secInquiryRecord/getSecInquiryRecordById")
    BaseResponse getSecInquiryRecordById(@RequestParam String id);

    @GetMapping("/v1/secInquiryRecord/getSecInquiryRecordBySecId")
    BaseResponse getSecInquiryRecordBySecId(@RequestParam String secId);

    @GetMapping("/v1/secInquiryRecord/getSecQuotationUnitBySecId")
    BaseResponse getSecQuotationUnitBySecId(@RequestParam String secId);

    @GetMapping("/v1/secInquiryRecord/getNewSecQuotationUnitBySecId")
    BaseResponse getNewSecQuotationUnitBySecId(@RequestParam String secId);

    /*********************直接委托*********************/
    @PostMapping("/v1/entrust/createOrUpdateSecEntrust")
    BaseResponse createOrUpdateSecEntrust(@RequestBody SecEntrustDto request);

    @GetMapping("/v1/entrust/getSecEntrustById")
    BaseResponse getSecEntrustById(@RequestParam String id);

    @PostMapping("/v1/entrust/getSecEntrustList")
    BaseResponse getSecEntrustList(@RequestBody SecEntrustDto request);

    /*********************专家选取申请*********************/
    @PostMapping("/v1/secExpertSelect/createOrUpdateSecExpertSelect")
    BaseResponse createOrUpdateSecExpertSelect(@RequestBody SecExpertSelectDto request);

    @PostMapping("/v1/secExpertSelect/getSecExpertSelectList")
    BaseResponse getSecExpertSelectList(@RequestBody SecExpertSelectDto request, @RequestHeader String token);

    @GetMapping("/v1/secExpertSelect/getSecExpertSelectById")
    BaseResponse getSecExpertSelectById(@RequestParam String id);

    @PostMapping("/v1/secExpertSelect/checkExPeople")
    BaseResponse checkExPeople(@RequestBody ExtractExpertDto request, @RequestHeader String token);

    /*********************征集公告*********************/
    @PostMapping("/v1/solicitationNotice/createOrUpdateSecSolicitationNotice")
    BaseResponse createOrUpdateSecSolicitationNotice(@RequestBody SecSolicitationNoticeDto request);

    @PostMapping("/v1/solicitationNotice/getSecSolicitationNoticeList")
    BaseResponse getSecSolicitationNoticeList(@RequestBody SecSolicitationNoticeDto request);

    @GetMapping("/v1/solicitationNotice/getSecSolicitationNoticeById")
    BaseResponse getSecSolicitationNoticeById(@RequestParam String id);

    @GetMapping("/v1/solicitationNotice/getSecSolicitationNoticeLastApprovedBySecId")
    BaseResponse getSecSolicitationNoticeLastApprovedBySecId(@RequestParam String secId);

    /*********************资格预审*********************/
    @PostMapping("/v1/qePreTrial/createOrUpdateSecQePreTrial")
    BaseResponse createOrUpdateSecQePreTrial(@RequestBody SecQePreTrialDto request);

    @GetMapping("/v1/qePreTrial/getSecQePreTrialById")
    BaseResponse getSecQePreTrialById(@RequestParam String id);

    /*********************招标采购申请*********************/

    @PostMapping("/v1/procurementApply/createOrUpdateSecProcurementApply")
    BaseResponse createOrUpdateSecProcurementApply(@RequestBody SecProcurementApplyDto request);

    @GetMapping("/v1/procurementApply/getSecProcurementApplyById")
    BaseResponse getSecProcurementApplyById(@RequestParam String id);

    /*********************清标小组*********************/

    @PostMapping("/v1/clearTeam/createOrUpdateSecClearTeam")
    BaseResponse createOrUpdateSecClearTeam(@RequestBody SecClearTeamDTO request);

    @GetMapping("/v1/clearTeam/getSecClearTeamById")
    BaseResponse getSecClearTeamById(@RequestParam String id);

    /*********************质询*********************/

    @PostMapping("/v1/secQuestion/createOrUpdateSecQuestion")
    BaseResponse createOrUpdateSecQuestion(@RequestBody SecQuestionDTO request);

    @GetMapping("/v1/secQuestion/getSecQuestionById")
    BaseResponse getSecQuestionById(@RequestParam String id);

    /*********************入围定标委员会及监督委员会构成*********************/

    @PostMapping("/v1/calibrationSupervision/createOrUpdateSecCalibrationSupervision")
    BaseResponse createOrUpdateSecCalibrationSupervision(@RequestBody SecCalibrationSupervisionDTO request);

    @GetMapping("/v1/calibrationSupervision/getSecCalibrationSupervisionById")
    BaseResponse getSecCalibrationSupervisionById(@RequestParam String id);

    /*********************定标委员会及监督委员会构成*********************/

    @PostMapping("/v1/committeeComposition/createOrUpdateSecCommitteeComposition")
    BaseResponse createOrUpdateSecCommitteeComposition(@RequestBody SecCommitteeCompositionDTO request);

    @GetMapping("/v1/committeeComposition/getSecCommitteeCompositionById")
    BaseResponse getSecCommitteeCompositionById(@RequestParam String id);

    /*********************专家暂停申请*********************/
    @PostMapping("/v1/expert/pause/createOrUpdateSecExpertPause")
    BaseResponse createOrUpdateSecExpertPause(@RequestBody SecExpertPauseDTO request);

    @PostMapping("/v1/expert/pause/getSecExpertPauseList")
    BaseResponse getSecExpertPauseList(@RequestBody SecExpertPauseDTO request, @RequestHeader String token);

    @GetMapping("/v1/expert/pause/getSecExpertPauseById")
    BaseResponse getSecExpertPauseById(@RequestParam String id);

    @GetMapping("/v1/expert/pause/approveSecExpertPauseById")
    BaseResponse approveSecExpertPauseById(@RequestParam String id);

    /*********************专家请假申请*********************/

    @PostMapping("/v1/expert/vacation/createOrUpdateSecExpertVacation")
    BaseResponse createOrUpdateSecExpertVacation(@RequestBody SecExpertVacationDTO request);

    @PostMapping("/v1/expert/vacation/getSecExpertVacationList")
    BaseResponse getSecExpertVacationList(@RequestBody SecExpertVacationDTO request, @RequestHeader String token);

    @GetMapping("/v1/expert/vacation/getSecExpertVacationById")
    BaseResponse getSecExpertVacationById(@RequestParam String id);

    @GetMapping("/v1/expert/vacation/approveSecExpertVacationById")
    BaseResponse approveSecExpertVacationById(@RequestParam String id);

    /*********************专家考核评分*********************/

    @PostMapping("/v1/expert/check/createOrUpdateSecExpertCheck")
    BaseResponse createOrUpdateSecExpertCheck(@RequestBody SecExpertCheckDTO request);

    @PostMapping("/v1/expert/check/getSecExpertCheckList")
    BaseResponse getSecExpertCheckList(@RequestBody SecExpertCheckDTO request, @RequestHeader String token);

    @GetMapping("/v1/expert/check/getSecExpertCheckById")
    BaseResponse getSecExpertCheckById(@RequestParam String id);

    @GetMapping("/v1/expert/check/getExpertCheckDtlList")
    BaseResponse getExpertCheckDtlList(@RequestParam String secId, @RequestHeader String token);

    /*********************专家考核申诉*********************/

    @PostMapping("/v1/expert/appeal/createOrUpdateSecExpertAppeal")
    BaseResponse createOrUpdateSecExpertAppeal(@RequestBody SecExpertAppealDTO request);


    @PostMapping("/v1/expert/appeal/getSecExpertAppealList")
    BaseResponse getSecExpertAppealList(@RequestBody SecExpertAppealDTO request, @RequestHeader String token);

    @GetMapping("/v1/expert/appeal/getSecExpertAppealById")
    BaseResponse getSecExpertAppealById(@RequestParam String id);

    @GetMapping("/v1/expert/appeal/approveSecExpertAppealById")
    BaseResponse approveSecExpertAppealById(@RequestParam String id);

    @PostMapping("/v1/expert/appeal/updateSecExpertAppealDtl")
    BaseResponse updateSecExpertAppealDtl(@RequestBody SecExpertAppealDtlDTO request);

    @PostMapping("/v1/expert/appeal/updateSecExpertAppealDtlBath")
    BaseResponse updateSecExpertAppealDtlBath(@RequestBody List<SecExpertAppealDtlDTO> request);

    /*********************考核评价表*********************/
    @PostMapping("/v1/expert/secForm/createOrUpdateSecForm")
    BaseResponse createOrUpdateSecForm(@RequestBody SecFormDTO request);

    @PostMapping("/v1/expert/secForm/createOrUpdateSecFormAndDetail")
    BaseResponse createOrUpdateSecFormAndDetail(@RequestBody SecFormDTO request);

    @PostMapping("/v1/expert/secForm/getSecFormList")
    BaseResponse getSecFormList(@RequestBody SecFormDTO request);

    @GetMapping("/v1/expert/secForm/getSecFormById")
    BaseResponse getSecFormById(@RequestParam String id);

    @GetMapping("/v1/expert/secForm/getSecFormLatest")
    BaseResponse getSecFormLatest(@RequestHeader String token);

    @GetMapping("/v1/expert/secForm/deleteSecFormById")
    BaseResponse deleteSecFormById(@RequestParam String id);

    /*********************考核评价表详细*********************/


    @PostMapping("/v1/expert/secFormDetail/createOrUpdateSecFormDetail")
    BaseResponse createOrUpdateSecFormDetail(@RequestBody SecFormDetailDTO request);

    @PostMapping("/v1/expert/secFormDetail/getSecFormDetailList")
    BaseResponse getSecFormDetailList(@RequestBody SecFormDetailDTO request);

    @GetMapping("/v1/expert/secFormDetail/deleteSecFormDetailById")
    BaseResponse deleteSecFormDetailById(@RequestParam String id);

    @GetMapping("/v1/expert/secFormDetail/getSecFormDetailLatestList")
    BaseResponse getSecFormDetailLatestList();


    /*********************专家补抽*********************/
    @PostMapping("/v1/secExpertSupplement/createOrUpdateExpertSupplement")
    BaseResponse createOrUpdateExpertSupplement(@RequestBody SecExpertSupplementDTO request);

    @PostMapping("/v1/secExpertSupplement/getExpertSupplementList")
    BaseResponse getExpertSupplementList(@RequestBody SecExpertSupplementDTO request);

    @GetMapping("/v1/secExpertSupplement/getExpertSupplementById")
    BaseResponse getExpertSupplementById(@RequestParam String id);

    @GetMapping("/v1/secExpertSupplement/approveAppendSelection")
    BaseResponse approveAppendSelection(@RequestParam String id);

    /*********************评标专家申请*********************/
    @PostMapping("/v1/expert/audit/createOrUpdateSecExpertAudit")
    BaseResponse createOrUpdateSecExpertAudit(@RequestBody SecExpertAuditDTO request, @RequestHeader String token);

    @PostMapping("/v1/expert/audit/createOrUpdateSecExpertAuditDz")
    BaseResponse createOrUpdateSecExpertAuditDz(@RequestBody SecExpertAuditDTO request, @RequestHeader String token);

    @PostMapping("/v1/expert/audit/getSecExpertAuditList")
    BaseResponse getSecExpertAuditList(@RequestBody SecExpertAuditDTO request);

    @GetMapping("/v1/expert/audit/getSecExpertAuditById")
    BaseResponse getSecExpertAuditById(@RequestParam String id);

    @GetMapping("/v1/expert/audit/approve2SyncExpertState")
    BaseResponse approve2SyncExpertState(@RequestParam String id);


    /*********************评标专家申请（建管）*********************/
    @PostMapping("/v1/jgExpert/audit/createOrUpdateSecJGExpertAudit")
    BaseResponse createOrUpdateSecJGExpertAudit(@RequestBody SecJGExpertAuditDTO request, @RequestHeader String token);

    @PostMapping("/v1/jgExpert/audit/createOrUpdateSecJGExpertAuditDz")
    BaseResponse createOrUpdateSecJGExpertAuditDz(@RequestBody SecJGExpertAuditDTO request, @RequestHeader String token);

    @PostMapping("/v1/jgExpert/audit/getSecJGExpertAuditList")
    BaseResponse getSecJGExpertAuditList(@RequestBody SecJGExpertAuditDTO request);

    @GetMapping("/v1/jgExpert/audit/getSecJGExpertAuditById")
    BaseResponse getSecJGExpertAuditById(@RequestParam String id);

    @GetMapping("/v1/jgExpert/audit/approveSyncExpertState")
    BaseResponse approveSyncExpertState(@RequestParam String id);


    /*********************招标工作方案*********************/
    @PostMapping("/v1/workSchema/createOrUpdateSecWorkSchema")
    BaseResponse createOrUpdateSecWorkSchema(@RequestBody SecWorkSchemaDTO request);

    @PostMapping("/v1/workSchema/getSecWorkSchemaList")
    BaseResponse getSecWorkSchemaList(@RequestBody SecWorkSchemaDTO request);

    @GetMapping("/v1/workSchema/getSecWorkSchemaByWsId")
    BaseResponse getSecWorkSchemaByWsId(@RequestParam String wsId);

    @GetMapping("/v1/workSchema/getSecWorkSchemaBySecId")
    BaseResponse getSecWorkSchemaBySecId(@RequestParam String secId);

    /*********************招标工作方案及询价文件*********************/

    @PostMapping("/v1/workSchemaInquiryDoc/createOrUpdateSecWorkSchemaInquiryDoc")
    BaseResponse createOrUpdateSecWorkSchemaInquiryDoc(@RequestBody SecWorkSchemaInquiryDocDTO request);

    @PostMapping("/v1/workSchemaInquiryDoc/getSecWorkSchemaInquiryDocList")
    BaseResponse getSecWorkSchemaInquiryDocList(@RequestBody SecWorkSchemaInquiryDocDTO request);

    @GetMapping("/v1/workSchemaInquiryDoc/getSecWorkSchemaInquiryDocByWsidId")
    BaseResponse getSecWorkSchemaInquiryDocByWsidId(@RequestParam String wsidId);


    @GetMapping("/v1/workSchemaInquiryDoc/getSecWorkSchemaInquiryDocBySecId")
    BaseResponse getSecWorkSchemaInquiryDocBySecId(@RequestParam String secId);


    /*********************招标意向*********************/

    @PostMapping("/v1/intention/createOrUpdateSecIntention")
    BaseResponse createOrUpdateSecIntention(@RequestBody SecIntentionDTO request);

    @GetMapping("/v1/intention/getSecIntentionById")
    BaseResponse getSecIntentionById(@RequestParam String id);

    /**********************招采计划*********************/
    @PostMapping("/v1/procurementPlan/createOrUpdateSecProcurementPlan")
    BaseResponse createOrUpdateSecProcurementPlan(@RequestBody SecProcurementPlanDto request);

    @GetMapping("/v1/procurementPlan/getSecProcurementPlanAmountTree")
    BaseResponse getSecProcurementPlanAmountTree(@RequestParam String pId);

    @GetMapping("/v1/procurementPlan/getSecProcurementPlanList")
    BaseResponse getSecProcurementPlanList(@RequestBody SecProcurementPlanDto request, @RequestHeader String token);

    @GetMapping("/v1/procurementPlan/getSecProcurementPlanById")
    BaseResponse getSecProcurementPlanById(@RequestParam String ppId);

    @GetMapping("/v1/procurementPlan/getSecProcurementPlanByPid")
    BaseResponse getSecProcurementPlanByPid(@RequestParam String pId);

    /**********************控制价请审*********************/
    @PostMapping("/v1/controlPricePleaseReview/createOrUpdateControlPriceReview")
    BaseResponse createOrUpdateControlPriceReview(@RequestBody SecControlPricePleaseReviewRequest request);

    @PostMapping("/v1/controlPricePleaseReview/deleteControlPriceReviewById")
    BaseResponse deleteControlPriceReviewById(@RequestParam String cpprId);


    @GetMapping("/v1/controlPricePleaseReview/getControlPriceReviewById")
    BaseResponse getControlPriceReviewById(@RequestParam String cpprId);


    @PostMapping("/v1/controlPricePleaseReview/getControlPriceReviewList")
    BaseResponse getControlPriceReviewList(@RequestBody SecControlPricePleaseReviewRequest request);


    /**********************合同签订请审*********************/
    @PostMapping("/v1/contractSignPleaseReview/createOrUpdateContractSignPleaseReview")
    BaseResponse createOrUpdateContractSignPleaseReview(@RequestBody SecContractSignPleaseReviewRequest request);

    @GetMapping("/v1/contractSignPleaseReview/deleteContractSignPleaseReviewById")
    BaseResponse deleteContractSignPleaseReviewById(@RequestParam String csprId);

    @GetMapping("/v1/contractSignPleaseReview/getContractSignPleaseReviewById")
    BaseResponse getContractSignPleaseReviewById(@RequestParam String csprId);

    @PostMapping("/v1/contractSignPleaseReview/getContractSignPleaseReviewList")
    BaseResponse getContractSignPleaseReviewList(@RequestBody SecContractSignPleaseReviewRequest request);

    @GetMapping("/v1/jgSelectionRecord/getSecJGSelectionRecordPrintById")
    BaseResponse getSecJGSelectionRecordPrintById(@RequestParam String srId);

    @GetMapping("/v1/jgSelectionRecord/getSecJGSelectionRecordPrintInsecDtlModelsById")
    BaseResponse getSecJGSelectionRecordPrintInsecDtlModelsById(@RequestParam String srId, @RequestHeader String token);

    @GetMapping("/v1/jgSelectionRecord/getSecJGSelectionRecordPrintOutsecDtlModelsById")
    BaseResponse getSecJGSelectionRecordPrintOutsecDtlModelsById(@RequestParam String srId, @RequestHeader String token);

    @GetMapping("/v1/jgSelectionRecord/getSecJGSelectionRecordPrintSrInsideExListById")
    BaseResponse getSecJGSelectionRecordPrintSrInsideExListById(@RequestParam String srId, @RequestHeader String token);

    @GetMapping("/v1/jgSelectionRecord/getSecJGSelectionRecordPrintSrOutsideExListById")
    BaseResponse getSecJGSelectionRecordPrintSrOutsideExListById(@RequestParam String srId, @RequestHeader String token);

    @GetMapping("/v1/biddingPlanGm/selectLastApprovedMoney")
    BaseResponse selectLastApprovedMoney(@RequestParam String secId);

    @PostMapping("/v1/secJGExpert/extractJGExpertCj")
    BaseResponse extractJGExpertCj(@RequestBody ExtractJGExpertDto request, @RequestHeader String token);


    @PostMapping("/v1/insideExpert/createOrUpdateInsideExpert")
    BaseResponse createOrUpdateInsideExpert(@RequestBody InsExpertRequest insExpertRequest);

    @PostMapping("/v1/insideExpert/getInsExpertClassInfo")
    BaseResponse getInsExpertClassInfo(@RequestBody InsExpertSearchDTO request);

    @GetMapping("/v1/insideExpert/queryNotInLib")
    BaseResponse queryNotInLib();

    @GetMapping("/v1/insideExpert/queryInLib")
    BaseResponse queryInLib();

    @GetMapping("/v1/insideExpert/queryInLibByPage")
    BaseResponse queryInLib(@RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @GetMapping("/v1/insideExpert/queryInLibByPage")
    BaseResponse queryInLibByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @GetMapping("/v1/insideExpert/queryNotInLibByPage")
    BaseResponse queryNotInLibByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @PostMapping("/v1/insideExpert/chooseExpert")
    BaseResponse chooseExpert(@RequestBody JudgeExtractionSearchDTO judgeExtractionSearchDTO);

    @GetMapping("/v1/insideExpert/getExpertByExId")
    BaseResponse getExpertByExId(@RequestParam("exId") String exId);

    @PostMapping("/v1/insideExpert/inLibraryOrChange")
    public BaseResponse inLibraryOrChange(@RequestBody StorageChangeRequest request);

    @GetMapping("/v1/insideExpert/rewriteInsExpert")
    public BaseResponse rewriteInsExpert(@RequestParam("storageChangeId") String storageChangeId);

    @GetMapping("/v1/insideExpert/deleteInsExpert")
    public BaseResponse deleteInsExpert(@RequestParam("exId") String exId);

    @GetMapping("/v1/insideExpert/reject")
    public BaseResponse reject(@RequestParam("exId") String exId);

    @PostMapping("/v1/insideExpert/queryExpertByPage")
    BaseResponse queryExpertByPage(@RequestBody InsExpertSearchDTO request);

    @GetMapping("/v1/insideExpert/queryStorageChange")
    BaseResponse queryStorageChange(@RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @GetMapping("/v1/insideExpert/queryStorageChangeDetail")
    BaseResponse queryStorageDetail(@RequestParam("id") String id);

    @PostMapping("/v1/insideExpert/createOrUpdateJudgeExtraction")
    BaseResponse createOrUpdateJudgeExtraction(@RequestBody JudgeExtractionDTO judgeExtraction);

    @GetMapping("/v1/insideExpert/deleteJudgeExtraction")
    BaseResponse deleteJudgeExtraction(@RequestParam("judgeExtractionId") String judgeExtractionId);

    @GetMapping("/v1/insideExpert/getJudgeExtractionBySecId")
    BaseResponse getJudgeExtractionBySecId(@RequestParam("secId") String secId);

    @GetMapping("/v1/insideExpert/queryJudgeExtraction")
    BaseResponse queryJudgeExtraction(@RequestParam("judgeExtractionId") String judgeExtractionId);

    @PostMapping("/v1/insideExpert/queryJudgeExtractionByPage")
    BaseResponse queryJudgeExtractionByPage(@RequestBody JudgeExtractionSearchDTO request);

    @PostMapping("/v1/insideExpert/getJudgeExtractionClassInfo")
    BaseResponse getJudgeExtractionClassInfo(@RequestBody JudgeExtractionSearchDTO request, @RequestHeader String token);

    @PostMapping("/v1/insideExpert/expertExtraction")
    BaseResponse expertExtraction(@RequestBody JudgeExtractionSearchDTO request);

    @PostMapping("/v1/insideExpert/CreateOrUpdateStorageChangeDetail")
    BaseResponse CreateOrUpdateStorageChangeDetail(@RequestBody StorageChangeDetail request);


    @PostMapping("/v1/storageChange/createOrUpdateStorageChange")
    BaseResponse createOrUpdateStorageChange(@RequestBody StorageChangeRequest request);

    @PostMapping("/v1/storageChange/getStorageChangeList")
    BaseResponse getStorageChangeList(@RequestBody StorageChangeRequest request);



    @GetMapping("/v1/secInfo/getSecBiddingTypeByIds")
    BaseResponse getSecBiddingTypeByIds(@RequestParam String secIds);

    @GetMapping("/v1/biddingPlan/judgmentSecBiddingPlanByPId")
    BaseResponse judgmentSecBiddingPlanByPId(@RequestParam("pId") String pId);

    @GetMapping("/v1/biddingPlan/judgmentSecBiddingPlanAmount")
    BaseResponse judgmentSecBiddingPlanAmount(@RequestParam("type") String type, @RequestParam("id") String id, @RequestParam("money") BigDecimal money);

    @GetMapping("/v1/biddingPlan/getSecBiddingPlanSubjectListByPId")
    BaseResponse getSecBiddingPlanSubjectListByPId(@RequestParam("pId") String pId,
                                                   @RequestParam(value = "type", required = false) String type,
                                                   @RequestParam(value = "excludeExtra", required = false) String excludeExtra,
                                                   @RequestParam(value = "bpcId", required = false) String bpcId);

    @GetMapping("/v1/biddingPlan/getSecBiddingPlanSubjectListByPIdTree")
    BaseResponse getSecBiddingPlanSubjectListByPIdTree(@RequestParam("pId") String pId,
                                                       @RequestParam(value = "type", required = false) String type,
                                                       @RequestParam(value = "excludeExtra", required = false) String excludeExtra,
                                                       @RequestParam(value = "bpcId", required = false) String bpcId);

    @GetMapping("/v1/biddingPlan/getSecFileRelationIdByPId")
    BaseResponse getSecFileRelationIdByPId(@RequestParam("pId") String pId);

    @GetMapping("/v1/biddingPlanChange/getSecInfoChangeListByBpId")
    BaseResponse getSecInfoChangeListByBpId(@RequestParam("bpId") String bpId);

    @PostMapping("/v1/secInfo/getSecInfoGDGY")
    BaseResponse getSecInfoGDGY(@RequestBody SecInfoRequest request);

    @GetMapping("/v1/expert/check/getSecExpertCheckBySrId")
    BaseResponse getSecExpertCheckBySrId(@RequestParam String srId, @RequestHeader String token);

    @GetMapping("/v1/expert/check/getSecEXpertCheckSendToDo")
    BaseResponse getSecEXpertCheckSendToDo();

    @PostMapping("/v1/expert/detail/createOrupdateSecExpertCheckDetailAndDtl")
    BaseResponse createOrupdateSecExpertCheckDetailAndDtl(@RequestBody SecExpertCheckDetailModel request, @RequestHeader String token);

    @GetMapping("/v1/expert/detail/getSecExpertCheckDetailAndDtlById")
    BaseResponse getSecExpertCheckDetailAndDtlById(@RequestParam String id, @RequestHeader String token);
    @GetMapping("/v1/expert/detail/getSecExpertCheckDetailAndDtlSelect")
    BaseResponse getSecExpertCheckDetailAndDtlSelect(@RequestParam String srdId, @RequestHeader(required = false) String token);

    @GetMapping("/v1/expert/detail/deleteSecExpertCheckDetailBySrId")
    BaseResponse deleteSecExpertCheckDetailBySrId(@RequestParam String srId, @RequestHeader String token);

    @PostMapping("/v1/secDocument/getSecDocumentListClassInfo")
    BaseResponse getSecDocumentListClassInfo(@RequestBody SecDocumentRequest request, @RequestHeader String token);

    @PostMapping("/v1/secDocument/getSecDocumentListClass")
    BaseResponse getSecDocumentListClass(@RequestBody SecDocumentRequest request, @RequestHeader String token);

    @PostMapping("/v1/answerQuestion/getSecAnswerQuestionListClassInfo")
    BaseResponse getSecAnswerQuestionListClassInfo(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token);

    @PostMapping("/v1/answerQuestion/getSecAnswerQuestionListClass")
    BaseResponse getSecAnswerQuestionListClass(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token);

    @PostMapping("/v1/workSchema/getSecWorkSchemaListClassInfo")
    BaseResponse getSecWorkSchemaListClassInfo(@RequestBody SecWorkSchemaDTO request, @RequestHeader String token);

    @PostMapping("/v1/workSchema/getSecWorkSchemaListClass")
    BaseResponse getSecWorkSchemaListClass(@RequestBody SecWorkSchemaDTO request, @RequestHeader String token);

    @PostMapping("/v1/secInquiryRecord/getSecInquiryRecordListClass")
    BaseResponse getSecInquiryRecordListClass(@RequestBody SecInquiryRecordDto request,@RequestHeader String token);

    @PostMapping("/v1/secInquiryRecord/getSecInquiryRecordListClassInfo")
    BaseResponse getSecInquiryRecordListClassInfo(@RequestBody SecInquiryRecordDto request, @RequestHeader String token);

    @PostMapping("/v1/biddingNegotiation/createOrUpdateSecBiddingNegotiation")
    BaseResponse createOrUpdateSecBiddingNegotiation(@RequestBody SecBiddingNegotiationDto request);

    @PostMapping("/v1/biddingNegotiation/getSecBiddingNegotiationListClassInfo")
    BaseResponse getSecBiddingNegotiationListClassInfo(@RequestBody SecBiddingNegotiationDto request,@RequestHeader String token);

    @PostMapping("/v1/biddingNegotiation/getSecBiddingNegotiationListClass")
    BaseResponse getSecBiddingNegotiationListClass(@RequestBody SecBiddingNegotiationDto request, @RequestHeader String token);

    @GetMapping("/v1/biddingNegotiation/getSecBiddingNegotiationByBnId")
    BaseResponse getSecBiddingNegotiationByBnId(@RequestParam String bnId);

    @GetMapping("/v1/biddingNegotiation/deleteSecBiddingNegotiationByBnId")
    BaseResponse deleteSecBiddingNegotiationByBnId(@RequestParam String bnId);

    @PostMapping("/v1/selectionRecord/getSecSelectionRecordInfoBySecId")
    BaseResponse getSecSelectionRecordInfoBySecId(@RequestBody SecSelectionRecordModel request);

    @PostMapping("/v1/secDocument/getpBusinessUnit")
    BaseResponse getpBusinessUnit(@RequestBody List<String> pIds);

    @PostMapping("/v1/secJGExpert/extractJGExpertJudge")
    BaseResponse extractJGExpertJudge(@RequestBody ExtractJGExpertDto request, @RequestHeader String token);

    @GetMapping("/v1/secInfo/getConSecInfoGgById")
    BaseResponse getConSecInfoGgById(@RequestParam String secId);

    @GetMapping("/v1/biddingPlan/getEstimatedBudgetAmountByPId")
    BaseResponse getEstimatedBudgetAmountByPId(@RequestParam String pId, @RequestParam(value = "type", required = false) String type);

    @GetMapping("/v1/biddingPlan/getSecBiddingPlanDetailExportExcel")
    Response getSecBiddingPlanDetailExportExcel(@RequestParam("bpId") String bpId);

    @PostMapping(value = "/v1/secExpert/importExcelSecExpertCjDzList", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResponse importExcelSecExpertCjDzList(@RequestPart("file") MultipartFile file, @RequestHeader String token);

    @GetMapping("/v1/winNotice/getWinNoticeCompleteBySecId")
    BaseResponse getWinNoticeCompleteBySecId(@RequestParam String secId);

    @GetMapping("/v1/biddingPlanChange/haveChangeSecInfoBySecId")
    BaseResponse haveChangeSecInfoBySecId(@RequestParam("secId") String secId);

    @PostMapping("/v1/evalReport/getSecEvalReportListClassInfo")
    BaseResponse getSecEvalReportListClassInfo(@RequestBody SecEvalReportClassInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/srequalificationReport/getSecPrequalificationReportList")
    BaseResponse getSecPrequalificationReportList(@RequestBody SecPrequalificationReportRequest request, @RequestHeader String token);

    @PostMapping("/v1/srequalificationReport/getSecPrequalificationReportClassInfo")
    BaseResponse getSecPrequalificationReportClassInfo(@RequestBody SecPrequalificationReportRequest request, @RequestHeader String token);

    @PostMapping("/v1/calibrationReport/getSecCalibrationReportList")
    BaseResponse getSecCalibrationReportList(@RequestBody SecCalibrationReportRequest request, @RequestHeader String token);

    @PostMapping("/v1/calibrationReport/getSecCalibrationReportClassInfo")
    BaseResponse getSecCalibrationReportClassInfo(@RequestBody SecCalibrationReportRequest request, @RequestHeader String token);

    @PostMapping("/v1/evalReport/getSecEvalReportList")
    BaseResponse getSecEvalReportList(@RequestBody SecEvalReportClassInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/winNotice/getWinNoticeListClass")
    BaseResponse getWinNoticeListClass(@RequestBody SecWinNoticeRequest request, @RequestHeader String token);

    @PostMapping("/v1/winNotice/getWinNoticeListClassInfo")
    BaseResponse getWinNoticeListClassInfo(@RequestBody SecWinNoticeRequest request, @RequestHeader String token);

    @GetMapping("/v1/biddingPlan/getSecPrecheck")
    BaseResponse getSecPrecheck(@RequestParam("tCode") String tCode, @RequestParam("secId") String secId);

    @GetMapping("/v1/winNotice/getWinningBidInformation")
    BaseResponse getWinningBidInformation(@RequestParam("secId") String secId);

    @PostMapping("/v1/evalReport/getSecEvalReportNoAuthList")
    BaseResponse getSecEvalReportNoAuthList(@RequestBody SecEvalReportClassInfoRequest request);

    @GetMapping("/v1/biddingPlan/updateSecBiddingPlanResponsibleLeader")
    BaseResponse updateSecBiddingPlanResponsibleLeader(@RequestParam("bpId") String bpId, @RequestParam("responsibleLeader") String responsibleLeader);

    @GetMapping("/v1/biddingPlanChange/updateSecBiddingPlanChangeResponsibleLeader")
    BaseResponse updateSecBiddingPlanChangeResponsibleLeader(@RequestParam("bpcId") String bpcId, @RequestParam("responsibleLeader") String responsibleLeader);

    @GetMapping("/v1/secExpert/getSecExpertByuId")
    BaseResponse getSecExpertByuId(@RequestParam(required = false, value = "uId") String uId, @RequestHeader(required = false) String token);

    @PostMapping("/v1/secExpertChange/createOrUpdateSecExpertChange")
    BaseResponse createOrUpdateSecExpertChange(@RequestBody SecExpertChangeModel request, @RequestHeader String token);

    @PostMapping("/v1/secExpertChange/getSecExpertChangeList")
    BaseResponse getSecExpertChangeList(@RequestBody SecExpertChangeModel request);

    @GetMapping("/v1/secExpertChange/getSecExpertChangeById")
    BaseResponse getSecExpertChangeById(@RequestParam String id);

    @GetMapping("/v1/secExpertChange/syncSecExpertChangeData")
    BaseResponse syncSecExpertChangeData(@RequestParam String id);

    @PostMapping("/v1/secExpertDelete/createOrUpdateSecExpertDelete")
    BaseResponse createOrUpdateSecExpertDelete(@RequestBody SecExpertDeleteModel request, @RequestHeader String token);

    @PostMapping("/v1/secExpert/getSecExpertCjDzListClass")
    BaseResponse getSecExpertCjDzListClass(@RequestBody SecExpertModel request, @RequestHeader String token);

    @PostMapping("/v1/secExpertDelete/getSecExpertDeleteList")
    BaseResponse getSecExpertDeleteList(@RequestBody SecExpertDeleteModel request);

    @GetMapping("/v1/secExpertDelete/getSecExpertDeleteById")
    BaseResponse getSecExpertDeleteById(@RequestParam String id);

    @PostMapping("/v1/selectionRecord/getSelectionRecordDtlListByExId")
    BaseResponse getSelectionRecordDtlListByExId(@RequestBody SecSelectionRecordDtlDto request);

    @PostMapping("/v1/expert/audit/returnInitiatorToTask")
    BaseResponse returnInitiatorToTaskAudit(@RequestBody ReturnInitiatorDto request, @RequestHeader String token);

    @PostMapping("/v1/secExpert/returnInitiatorToTask")
    BaseResponse returnInitiatorToTaskExpert(@RequestBody ReturnInitiatorDto request, @RequestHeader String token);

    @PostMapping("/v1/expert/pause/getSecExpertPauseListClass")
    BaseResponse getSecExpertPauseListClass(@RequestBody SecExpertPauseDTO request, @RequestHeader String token);

    @GetMapping("/v1/secExpertChange/syncSecExpertDeleteData")
    BaseResponse syncSecExpertDeleteData(@RequestParam String id);

    @PostMapping("/v1/expert/auditGroup/createOrUpdateSecExpertAuditGroup")
    BaseResponse createOrUpdateSecExpertAuditGroup(@RequestBody SecExpertAuditGroupDTO request, @RequestHeader String token);

    @PostMapping("/v1/expert/auditGroup/createOrUpdateSecExpertAuditGroupDz")
    BaseResponse createOrUpdateSecExpertAuditGroupDz(@RequestBody SecExpertAuditGroupDTO request, @RequestHeader String token);

    @PostMapping("/v1/expert/auditGroup/getSecExpertAuditGroupList")
    BaseResponse getSecExpertAuditGroupList(@RequestBody SecExpertAuditGroupDTO request);

    @GetMapping("/v1/expert/auditGroup/getSecExpertAuditGroupById")
    BaseResponse getSecExpertAuditGroupById(@RequestParam String id);

    @GetMapping("/v1/expert/audit/getSecExpertAuditDetailListGroup")
    BaseResponse getSecExpertAuditDetailListGroup();

    @GetMapping("/v1/expert/audit/getSecExpertAuditByYear")
    BaseResponse getSecExpertAuditByYear(@RequestParam Integer year);

    @GetMapping("/v1/expert/auditGroup/syncSecExpert")
    BaseResponse syncSecExpert(@RequestParam String id);

    @GetMapping("/v1/expert/pause/approveSecExpertPause")
    BaseResponse approveSecExpertPause();

    @GetMapping("/v1/secExpert/copyAllSecExpert")
    BaseResponse copyAllSecExpert(@RequestParam(required = false) Integer year);

    @GetMapping("/v1/expert/audit/invalidExpertProcessData")
    BaseResponse invalidExpertProcessData(@RequestParam String id);

    @PostMapping("/v1/secExpertDelete/returnInitiatorToTask")
    BaseResponse returnInitiatorToTaskDelete(@RequestBody ReturnInitiatorDto request, @RequestHeader String token);

    @PostMapping("/v1/secExpertChange/returnInitiatorToTask")
    BaseResponse returnInitiatorToTaskChange(@RequestBody ReturnInitiatorDto request, @RequestHeader String token);

    @GetMapping("/v1/secExpert/updateChangeStatus")
    BaseResponse updateChangeStatus(@RequestParam String exId, @RequestParam String changeStatus);

    @GetMapping("/v1/secExpertChange/updateChangeStatus")
    BaseResponse updateChangeStatusChange(@RequestParam String id);

    @GetMapping("/v1/secExpertDelete/updateChangeStatus")
    BaseResponse updateChangeStatusDelete(@RequestParam String id);

    @GetMapping("/v1/expert/audit/getSecExpertAuditChangeStatusInfoByYear")
    BaseResponse getSecExpertAuditChangeStatusInfoByYear(@RequestParam String exApplicantCId, @RequestParam(required = false) Integer year);

    @GetMapping("/v1/expert/check/syncSecExpertPause")
    BaseResponse syncSecExpertPause(@RequestParam String id);

    @GetMapping("/v1/secExpert/handleDisableSecExpert")
    BaseResponse handleDisableSecExpert();

    @PostMapping("/v1/secInfoSubjectExtra/createOrUpdateSecInfoSubjectExtra")
    BaseResponse createOrUpdateSecInfoSubjectExtra(@RequestBody SecInfoSubjectExtraDTO request);

    @GetMapping("/v1/secInfoSubjectExtra/deleteSecInfoSubjectExtraById")
    BaseResponse deleteSecInfoSubjectExtraById(@RequestParam String id);

    @PostMapping("/v1/secInvitationSchema/getSecInvitationSchemaList")
    BaseResponse getSecInvitationSchemaList(@RequestBody SecInvitationSchemaDTO request);

    @PostMapping("/v1/secInvitationSchema/getSecInvitationSchemaClassInfo")
    BaseResponse getSecInvitationSchemaClassInfo(@RequestBody SecInvitationSchemaDTO request);

    @PostMapping("/v1/secInvitationNegotiation/getSecInvitationNegotiationList")
    BaseResponse getSecInvitationNegotiationList(@RequestBody SecInvitationNegotiationDTO request);

    @PostMapping("/v1/secInvitationNegotiation/getSecInvitationNegotiationClassInfo")
    BaseResponse getSecInvitationNegotiationClassInfo(@RequestBody SecInvitationNegotiationDTO request);

    @PostMapping("/v1/secExpert/getExtractSecExpertCount")
    BaseResponse getExtractSecExpertCount(@RequestBody ExtractExpertDto request, @RequestHeader String token);

    @GetMapping("/v1/secDocument/getDocumentRate")
    BaseResponse getDocumentRate(@RequestParam String secId);

    @PostMapping("/v1/biddingPlan/createOrUpdateFromOldSecBiddingPlan")
    BaseResponse createOrUpdateFromOldSecBiddingPlan(@RequestBody SecBiddingPlanRequest request);

    @GetMapping("/v1/secExpert/syncCompanyToSecExpert")
    BaseResponse syncCompanyToSecExpert(@RequestParam String uId);
}
