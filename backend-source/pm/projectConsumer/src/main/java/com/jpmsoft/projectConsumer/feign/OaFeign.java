package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.*;
import com.jpmsoft.common.model.requestAndResponse.Oa.*;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectConsumer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectConsumer.pojo.dto.oa.OaAnnualPlanChangeDto;
import com.jpmsoft.projectConsumer.pojo.dto.oa.OaAnnualPlanDto;
import com.jpmsoft.projectConsumer.pojo.dto.oa.OaKnowledgeGroupDTO;
import com.jpmsoft.projectConsumer.pojo.dto.oa.ReportedCompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 综合办公
 */
@FeignClient(name = "project-producer", contextId = "oa")
public interface OaFeign {

    /***********************************************通知公告***************************************************/
    @PostMapping("/v1/OA/Notice/createOrUpdateOaNotice")
    BaseResponse createOrUpdateOaNotice(@RequestBody OaNoticeRequest request);

    @GetMapping("/v1/OA/Notice/getOaNoticeByNid")
    BaseResponse getOaNoticeByNid(@RequestParam String nId);

    @PostMapping("/v1/OA/Notice/getOaNoticeList")
    BaseResponse getOaNoticeList(@RequestBody OaNoticeRequest request);

    @PostMapping("/v1/OA/Notice/getUserOaNoticeList")
    BaseResponse getUserOaNoticeList(@RequestBody OaNoticeRequest request);

    @GetMapping("/v1/OA/Notice/deleteOaNoticeByNid")
    BaseResponse deleteOaNoticeByNid(@RequestParam String nId);

    @PostMapping("/v1/OA/Notice/createOrUpdateViewRecords")
    BaseResponse createOrUpdateViewRecords(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/OA/Notice/getViewRecordsById")
    BaseResponse getViewRecordsById(@RequestBody RequestBean requestBean);

    /***********************************************通知公告 阅读记录***************************************************/

    @PostMapping("/v1/OA/NoticeRead/createOaNoticeRead")
    BaseResponse createOaNoticeRead(@RequestBody OaNoticeReadRequest request);

    @PostMapping("/v1/OA/NoticeRead/updateOaNoticeRead")
    BaseResponse updateOaNoticeRead(@RequestBody OaNoticeReadRequest request);

    @GetMapping("/v1/OA/NoticeRead/deleteOaNoticeReadByNrId")
    BaseResponse deleteOaNoticeReadByNrId(@RequestParam String nrId);

    @GetMapping("/v1/OA/NoticeRead/getOaNoticeReadListByNid")
    BaseResponse getOaNoticeReadListByNid(@RequestParam("nId") String nId, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "isRead", required = false) String isRead);

    @PostMapping("/v1/OA/NoticeRead/getOaNoticeReadList")
    BaseResponse getOaNoticeReadList(@RequestBody OaNoticeReadRequest request);

    /**********************************************督办任务***************************************************/

    @PostMapping("/v1/OA/SupervisionTask/createOrUpdateOaSupervisionTask")
    BaseResponse createOrUpdateOaSupervisionTask(@RequestBody OaSupervisionTaskRequest request);

    @PostMapping("/v1/OA/SupervisionTask/getOaSupervisionTaskList")
    BaseResponse getOaSupervisionTaskList(@RequestBody OaSupervisionTaskRequest request, @RequestHeader String token);

    @GetMapping("/v1/OA/SupervisionTask/getOaSupervisionTaskByStId")
    BaseResponse getOaSupervisionTaskByStId(@RequestParam String stId);

    @PostMapping("/v1/OA/SupervisionTask/exportOaSupervisionTaskStatistics")
    BaseResponse exportOaSupervisionTaskStatistics(@RequestBody OaSupervisionTaskRequest request);

    /**********************************************整改反馈记录***************************************************/
    @PostMapping("/v1/OA/SupervisionFeedback/createOrUpdateOaSupervisionFeedback")
    BaseResponse createOrUpdateOaSupervisionFeedback(@RequestBody OaSupervisionFeedbackRequest request);

    @PostMapping("/v1/OA/SupervisionFeedback/getOaSupervisionFeedbackList")
    BaseResponse getOaSupervisionFeedbackList(@RequestBody OaSupervisionFeedbackRequest request);

    @GetMapping("/v1/OA/SupervisionFeedback/getOaSupervisionFeedbackBySfId")
    BaseResponse getOaSupervisionFeedbackBySfId(@RequestParam String sfId);

    /**********************************************工作报告（日报、周报、月报、季报、年报）***************************************************/

    @PostMapping("/v1/OA/WorkReport/createOrUpdateOaWorkReport")
    BaseResponse createOrUpdateOaWorkReport(@RequestBody OaWorkReportRequest request);

    @PostMapping("/v1/OA/WorkReport/getOaWorkReportList")
    BaseResponse getOaWorkReportList(@RequestBody OaWorkReportRequest request, @RequestHeader String token);

    @PostMapping("/v1/OA/WorkReport/getOaWorkReportListOnlyDep")
    BaseResponse getOaWorkReportListOnlyDep(@RequestBody OaWorkReportRequest request, @RequestHeader String token);

    @PostMapping("/v1/OA/WorkReport/getOaWorkReportSumList")
    BaseResponse getOaWorkReportSumList(@RequestBody OaWorkReportRequest request, @RequestHeader String token);

    @PostMapping("/v1/OA/WorkReport/getOaWorkReportListNoAuth")
    BaseResponse getOaWorkReportListNoAuth(@RequestBody OaWorkReportRequest request);

    @GetMapping("/v1/OA/WorkReport/getOaWorkReportById")
    BaseResponse getOaWorkReportById(@RequestParam String wrId);

    /**********************************************工程周报、月报***************************************************/
    @PostMapping("/v1/OA/proReport/createOrUpdateOaProjectReport")
    BaseResponse createOrUpdateOaProjectReport(@RequestBody OaProjectReportModel request);

    @PostMapping("/v1/OA/proReport/getOaProjectReportList")
    BaseResponse getOaProjectReportList(@RequestBody OaProjectReportModel request);

    @GetMapping("/v1/OA/proReport/getOaProjectReportById")
    BaseResponse getOaProjectReportById(@RequestParam String prId);

    @GetMapping("/v1/OA/proReport/getOaProReportInvSum")
    BaseResponse getOaProReportInvSum(@RequestParam String wrId);

    @GetMapping("/v1/OA/proReport/getLastProjectReportByPid")
    BaseResponse getLastProjectReportByPid(@RequestParam String pId, @RequestParam String prType);

    /**********************************************工单***************************************************/
    @PostMapping("/v1/OA/WorkOrder/createOrUpdateOaWorkorder")
    BaseResponse createOrUpdateOaWorkorder(@RequestBody OaWorkorderRequest request);

    @PostMapping("/v1/OA/WorkOrder/getOaWorkorderList")
    BaseResponse getOaWorkorderList(@RequestBody OaWorkorderRequest request, @RequestHeader String token);

    @GetMapping("/v1/OA/WorkOrder/getOaWorkorderById")
    BaseResponse getOaWorkorderById(@RequestParam String woId);

    @PostMapping("/v1/OA/WorkOrder/updateReplyInfo")
    BaseResponse updateReplyInfo(@RequestBody OaWorkorderRequest request);

    /**********************************************知识库目录***************************************************/
    @PostMapping("/v1/OA/Knowledge/createOrUpdateOaKnowledgeBase")
    BaseResponse createOrUpdateOaKnowledgeBase(@RequestBody OaKnowledgeBaseModel request);

    @PostMapping("/v1/OA/Knowledge/getOaKnowledgeBaseTree")
    BaseResponse getOaKnowledgeBaseTree(@RequestBody OaKnowledgeBaseModel request,@RequestHeader String token);

    @GetMapping("/v1/OA/Knowledge/deleteOaKnowledgeBaseById")
    BaseResponse deleteOaKnowledgeBaseById(@RequestParam String kbId);

    @PostMapping("/v1/OA/Knowledge/onDragOaKnowledgeBase")
    BaseResponse onDragOaKnowledgeBase(@RequestBody OnDragDto request);

    /**********************************************知识库文件***************************************************/

    @PostMapping("/v1/OA/KnowledgeFile/createOrUpdateOaKnowledgeBaseFile")
    BaseResponse createOrUpdateOaKnowledgeBaseFile(@RequestBody OaKnowledgeBaseFileModel request);

    @PostMapping("/v1/OA/KnowledgeFile/getOaKnowledgeBaseFileList")
    BaseResponse getOaKnowledgeBaseFileList(@RequestBody OaKnowledgeBaseFileModel request);

    @PostMapping("/v1/OA/KnowledgeFile/getOaKnowledgeBaseFileAllList")
    BaseResponse getOaKnowledgeBaseFileAllList();

    @GetMapping("/v1/OA/KnowledgeFile/deleteOaKnowledgeBaseFileById")
    BaseResponse deleteOaKnowledgeBaseFileById(@RequestParam String kbfId);

    /**********************************************考勤组***************************************************/
    @PostMapping("/v1/OA/AttendanceGroup/createOrUpdateAttendanceGroup")
    BaseResponse createOrUpdateAttendanceGroup(@RequestBody OaAttendanceGroupModel request);

    @PostMapping("/v1/OA/AttendanceGroup/createOrUpdateAttendanceGroupAndWeekday")
    BaseResponse createOrUpdateAttendanceGroupAndWeekday(@RequestBody OaAttendanceGroupModel request);

    @PostMapping("/v1/OA/AttendanceGroup/getAttendanceGroupList")
    BaseResponse getAttendanceGroupList(@RequestBody OaAttendanceGroupModel request);

    @GetMapping("/v1/OA/AttendanceGroup/getAttendanceGroupById")
    BaseResponse getAttendanceGroupById(@RequestParam String agId);

    /**********************************************考勤人员***************************************************/
    @PostMapping("/v1/OA/AttendanceMember/createOrUpdateAttendanceMember")
    BaseResponse createOrUpdateAttendanceMember(@RequestBody OaAttendanceMemberModel request);

    @PostMapping("/v1/OA/AttendanceMember/createOrUpdateAttendanceMemberByBath")
    BaseResponse createOrUpdateAttendanceMemberByBath(@RequestBody List<OaAttendanceMemberModel> request);

    @PostMapping("/v1/OA/AttendanceMember/getAttendanceMemberList")
    BaseResponse getAttendanceMemberList(@RequestBody OaAttendanceMemberModel request);

    /**********************************************工作日设置***************************************************/
    @PostMapping("/v1/OA/AttendanceWeekday/createOrUpdateAttendanceWeekday")
    BaseResponse createOrUpdateAttendanceWeekday(@RequestBody OaAttendanceWeekdayModel request);

    @PostMapping("/v1/OA/AttendanceWeekday/getAttendanceWeekdayList")
    BaseResponse getAttendanceWeekdayList(@RequestBody OaAttendanceWeekdayModel request);

    /**********************************************考勤记录***************************************************/

    @PostMapping("/v1/OA/Attendance/createOrUpdateAttendance")
    BaseResponse createOrUpdateAttendance(@RequestBody OaAttendanceModel request);

    @PostMapping("/v1/OA/Attendance/getAttendanceList")
    BaseResponse getAttendanceList(@RequestBody OaAttendanceModel request);

    /**********************************************请假申请***************************************************/
    @PostMapping("/v1/OA/AttendanceLeave/createOrUpdateAttendanceLeave")
    BaseResponse createOrUpdateAttendanceLeave(@RequestBody OaAttendanceLeaveModel request);

    @PostMapping("/v1/OA/AttendanceLeave/getAttendanceLeaveList")
    BaseResponse getAttendanceLeaveList(@RequestBody OaAttendanceLeaveModel request);

    @GetMapping("/v1/OA/AttendanceLeave/getAttendanceLeaveById")
    BaseResponse getAttendanceLeaveById(@RequestParam String alId);

    /**********************************************补卡申请***************************************************/
    @PostMapping("/v1/OA/AttendanceSupplement/createOrUpdateAttendanceSupplement")
    BaseResponse createOrUpdateAttendanceSupplement(@RequestBody OaAttendanceSupplementModel request);

    @PostMapping("/v1/OA/AttendanceSupplement/getAttendanceSupplementList")
    BaseResponse getAttendanceSupplementList(@RequestBody OaAttendanceSupplementModel request);

    @GetMapping("/v1/OA/AttendanceSupplement/getAttendanceSupplementById")
    BaseResponse getAttendanceSupplementById(@RequestParam String asId);

    @PostMapping("v1/OA/AttendanceSupplement/updateAttendance")
    BaseResponse updateAttendance(@RequestBody OaAttendanceSupplementModel request);

    /**********************************************AB授权***************************************************/
    @PostMapping("/v1/OA/abAuthorize/createOrUpdatePersonalBpmAuthorize")
    BaseResponse createOrUpdatePersonalBpmAuthorize(@RequestBody SysPersonalBpmAuthorizeModel request, @RequestHeader String token);

    @PostMapping("/v1/OA/abAuthorize/getSelfPersonalBpmAuthorizeList")
    BaseResponse getSelfPersonalBpmAuthorizeList(@RequestBody SysPersonalBpmAuthorizeModel request, @RequestHeader String token);

    @PostMapping("/v1/OA/abAuthorize/getPersonalBpmAuthorizeList")
    BaseResponse getPersonalBpmAuthorizeList(@RequestBody SysPersonalBpmAuthorizeModel request);

    @GetMapping("/v1/OA/abAuthorize/getPersonalBpmAuthorizeById")
    BaseResponse getPersonalBpmAuthorizeById(@RequestParam String baId);

    /**********************************************考勤地点***************************************************/
    @PostMapping("/v1/OA/AttendanceLocation/createOrUpdateOaAttendanceLocation")
    BaseResponse createOrUpdateOaAttendanceLocation(@RequestBody OaAttendanceLocationModel request);

    @GetMapping("/v1/OA/AttendanceLocation/deleteOaAttendanceLocationById")
    BaseResponse deleteOaAttendanceLocationById(@RequestParam String alId);

    @PostMapping("/v1/OA/AttendanceLocation/getOaAttendanceLocationList")
    BaseResponse getOaAttendanceLocationList(@RequestBody OaAttendanceLocationModel request);

    /**********************************************项目问题登记表***************************************************/
    @PostMapping("/v1/OA/Problem/createOrUpdateOaProblem")
    BaseResponse createOrUpdateOaProblem(@RequestBody OaProblemModel request);

    @PostMapping("/v1/OA/Problem/getOaProblemList")
    BaseResponse getOaProblemList(@RequestBody OaProblemModel request, @RequestHeader String token);

    @GetMapping("/v1/OA/Problem/getOaProblemById")
    BaseResponse getOaProblemById(@RequestParam String pmId);

    /**********************************************项目问题详情（清单）***************************************************/
    @PostMapping("/v1/OA/ProblemDtl/getOaProblemDtlList")
    BaseResponse getOaProblemDtlList(@RequestBody OaProblemDtlModel request, @RequestHeader String token);

    /**********************************************问题反馈***************************************************/
    @PostMapping("/v1/OA/ProblemFeedback/createOrUpdateOaProblemFeedback")
    BaseResponse createOrUpdateOaProblemFeedback(@RequestBody OaProblemFeedbackModel request);

    @PostMapping("/v1/OA/ProblemFeedback/getOaProblemFeedbackList")
    BaseResponse getOaProblemFeedbackList(@RequestBody OaProblemFeedbackModel request);

    @GetMapping("/v1/OA/ProblemFeedback/getOaProblemFeedbackById")
    BaseResponse getOaProblemFeedbackById(@RequestParam String pmfId);

    @GetMapping("/v1/OA/ProblemFeedback/approve2RefreshProblemStatus")
    BaseResponse approve2RefreshProblemStatus(@RequestParam String pmfId);

    @GetMapping("/v1/OA/ProblemFeedback/getOaProblemFeedbackByPmdId")
    BaseResponse getOaProblemFeedbackByPmdId(@RequestParam String pmdId);

    /**********************************************项目人员报备***************************************************/

    @PostMapping("/v1/OA/repPersonnel/createOrUpdateReportedPersonnel")
    BaseResponse createOrUpdateReportedPersonnel(@RequestBody ReportedPersonnelModel request);

    @PostMapping("/v1/OA/repPersonnel/createOrUpdateReportedPersonnelCj")
    BaseResponse createOrUpdateReportedPersonnelCj(@RequestBody ReportedPersonnelModel request);

    @PostMapping("/v1/OA/repPersonnel/getReportedPersonnelList")
    BaseResponse getReportedPersonnelList(@RequestBody ReportedPersonnelModel request, @RequestHeader String token);

    @GetMapping("/v1/OA/repPersonnel/getReportedPersonnelById")
    BaseResponse getReportedPersonnelById(@RequestParam String rpId);

    @GetMapping("/v1/OA/repPersonnel/approveReportedPersonnelById")
    BaseResponse approveReportedPersonnelById(@RequestParam String rpId);

    @GetMapping("/v1/OA/repPersonnel/xazhjj/approve2SyncDepartmentP")
    BaseResponse approve2SyncDepartmentP(@RequestParam String rpId,@RequestHeader String token);

    /******************施工日志************************/
    @PostMapping("/v1/OA/WorkReport/getOaWorkReportStandingbook")
    BaseResponse getOaWorkReportStandingbook(@RequestBody RequestBean requestBean, @RequestHeader String token);

    @PostMapping("/v1/OA/AttendanceLeave/update")
    BaseResponse update(@RequestBody OaAttendanceLeaveModel requestBean);

    @GetMapping("/v1/OA/AttendanceLeave/getById")
    BaseResponse getById(@RequestParam String alId);

    @GetMapping("/v1/OA/AttendanceLeave/updateApproveStatus")
    BaseResponse updateApproveStatus(@RequestParam String id);

    @PostMapping("/v1/OA/WorkReport/getOaWorkReportNeverSub")
    BaseResponse getOaWorkReportNeverSub(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/OA/WorkReport/countOaWorkReport")
    BaseResponse countOaWorkReport(@RequestBody List<String> cIds, @RequestParam("type") String type);

    /***********************************************项目单位报备***************************************************/
    @PostMapping("/v1/OA/repCompany/createOrUpdateReportedCompany")
    BaseResponse createOrUpdateReportedCompany(@RequestBody ReportedCompanyDto request);

    @PostMapping("/v1/OA/repCompany/getReportedCompanyList")
    BaseResponse getReportedCompanyList(@RequestBody ReportedCompanyDto request, @RequestHeader String token);

    @GetMapping("/v1/OA/repCompany/getReportedCompanyById")
    BaseResponse getReportedCompanyById(@RequestParam String id);

    @GetMapping("/v1/OA/repCompany/getReportedCompanyGroupTypeByPid")
    BaseResponse  getReportedCompanyGroupTypeByPid(@RequestParam String pId);
    @GetMapping("/v1/OA/repCompany/approved2SyncCompanyInfo")
    BaseResponse approved2SyncCompanyInfo(@RequestParam String id);

    /***********************************************年度项目计划***************************************************/

    @PostMapping("/v1/OA/annualPlan/createOrUpdateOaAnnualPlan")
    BaseResponse createOrUpdateOaAnnualPlan(@RequestBody OaAnnualPlanDto request);

    @PostMapping("/v1/OA/annualPlan/getOaAnnualPlanList")
    BaseResponse getOaAnnualPlanList(@RequestBody OaAnnualPlanDto request);

    @GetMapping("/v1/OA/annualPlan/getOaAnnualPlanById")
    BaseResponse getOaAnnualPlanById(@RequestParam String id);

    @PostMapping("/v1/OA/annualPlan/getOaAnnualPlanDtlList")
    BaseResponse getOaAnnualPlanDtlList(@RequestBody OaAnnualPlanDto request);

    /***********************************************年度项目计划变更***************************************************/

    @PostMapping("/v1/OA/annualPlanChange/createOrUpdateOaAnnualPlanChange")
    BaseResponse createOrUpdateOaAnnualPlanChange(@RequestBody OaAnnualPlanChangeDto request);

    @PostMapping("/v1/OA/annualPlanChange/getOaAnnualPlanChangeList")
    BaseResponse getOaAnnualPlanChangeList(@RequestBody OaAnnualPlanChangeDto request);

    @GetMapping("/v1/OA/annualPlanChange/getOaAnnualPlanChangeById")
    BaseResponse getOaAnnualPlanChangeById(@RequestParam String id);

    @GetMapping("/v1/OA/annualPlanChange/approval2SyncOaAnnualPlan")
    BaseResponse approval2SyncOaAnnualPlan(@RequestParam String id);
    /***********************************************知识库组和用户权限配置***************************************************/

    @PostMapping("/v1/OA/KnowledgeGroup/createOrUpdateOaKnowledgeGroupBath")
    BaseResponse createOrUpdateOaKnowledgeGroupBath(@RequestBody List<OaKnowledgeGroupDTO> request);

    @GetMapping("/v1/OA/KnowledgeGroup/getOaKnowledgeGroupByKbId")
    BaseResponse getOaKnowledgeGroupByKbId(@RequestParam String kbId);
    /***********************************************河北会议变更***************************************************/


    @GetMapping("/v1/OA/meetingChange/timeOut2LockMeeting")
    BaseResponse timeOut2LockMeeting(@RequestParam Integer day);

    @GetMapping("/v1/OA/meetingChange/approve2UnlockMeeting")
    BaseResponse approve2UnlockMeeting(@RequestParam String id);

    @GetMapping("/v1/OA/meetingChange/queryDataList")
    BaseResponse  queryDataList(@RequestParam Integer page,@RequestParam Integer size);
}
