package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.SysFlowRequest;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.jpbpm.pojo.dto.*;
import com.jpmsoft.jpbpm.pojo.entity.SysExtendValue;
import com.jpmsoft.jpbpm.pojo.entity.SysMultiFormFlow;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "流程组件", value = "流程组件")
@RequestMapping("/v1/flow")
public class FlowController {

    @Autowired
    private FlowService flowService;

    @Autowired
    private FileTemplateService fileTemplateService;

    @Autowired
    private SysCoordinationTaskService sysCoordinationTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FlowActivityService flowActivityService;

    @Autowired
    private TaskProcessinService taskProcessinService;

    @Autowired
    private SysCoordinationProcessService sysCoordinationProcessService;

    @Autowired
    private CarbonCopyService carbonCopyService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysMultiFormFlowService sysMultiFormFlowService;

    @Autowired
    private TaskPushCallRecordService taskPushCallRecordService;
    @Autowired
    SysTaskProcessinTimeService sysTaskProcessinTimeService;
    @Autowired
    SysExtendValueService sysExtendValueService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取下一步流程节点用户信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取下一步流程节点用户信息")
    @PostMapping("/getNextActivities")
    public BaseResponse getNextActivities(@RequestBody NextActivitiesDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getNextActivities");
        String customerId = redisUtils.getCustomerId(token);
        return flowActivityService.getNextActivities(request, customerId, true);
    }

    /**
     * 获取代办信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取代办信息")
    @PostMapping("/Gettask")
    public BaseResponse Gettask(@RequestBody GetTaskDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("Gettask");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.Gettask(request, customerId);
    }

    /**
     * 获取流程文档模板信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取流程文档模板信息")
    @PostMapping("/getTemplate")
    public BaseResponse getTemplate(@RequestBody TemplateinitializationDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getTemplate");
        String customerId = redisUtils.getCustomerId(token);
        return fileTemplateService.getTemplate(request, customerId);
    }

    /**
     * 初始化流程节点信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "初始化流程节点信息")
    @PostMapping("/getStartActivityInfo")
    public BaseResponse getStartActivityInfo(@RequestBody StartActivityInfoDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getStartActivityInfo");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.getStartActivityInfo(request, customerId);
    }

    /**
     * 流程提交方法
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "流程提交方法")
    @PostMapping("/submitFlow")
    public synchronized BaseResponse submitFlow(@RequestBody SubmitFlowRequstDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("submitFlow");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.submitFlow(request, customerId, token);
    }


    /**
     * 退回流程
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "退回流程")
    @PostMapping("/sendbackFlow")
    public BaseResponse sendbackFlow(@RequestBody SendbackFlowDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("sendbackFlow");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.sendbackFlow(request, customerId, token);
    }


    /**
     * 停止流程
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "停止流程")
    @PostMapping("/Stopprocess")
    public BaseResponse Stopprocess(@RequestBody StopProcessDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("Stopprocess");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.Stopprocess(request, customerId, token);
    }


    /**
     * 更换流程操作人
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "更换流程操作人")
    @PostMapping("/changeProcessOperator")
    public BaseResponse changeProcessOperator(@RequestBody ChangeProcessOperatorDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("changeProcessOperator");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.changeProcessOperator(request, customerId, token);
    }


    /**
     * 补充意见
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "补充意见")
    @PostMapping("/supplementaryOpinions")
    public BaseResponse supplementaryOpinions(@RequestBody SupplementaryOpinionsDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("supplementaryOpinions");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.supplementaryOpinions(request, customerId);
    }

    /**
     * 流程撤回
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "流程撤回")
    @PostMapping("/withdrawalProcess")
    public BaseResponse withdrawalProcess(@RequestBody WithdrawalProcessDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("withdrawalProcess");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.withdrawalProcess(request, customerId, token);
    }

    /**
     * 发起沟通
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "发起沟通")
    @PostMapping("/initiateCommunication")
    public BaseResponse initiateCommunication(@RequestBody InitiateCommunicationDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("initiateCommunication");
        String customerId = redisUtils.getCustomerId(token);
        return sysCoordinationTaskService.initiateCommunication(request, customerId, token);
    }


    /**
     * 提交沟通
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "提交沟通")
    @PostMapping("/submitCommunicate")
    public BaseResponse submitCommunicate(@RequestBody SubmitCommunicateDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("submitCommunicate");
        String customerId = redisUtils.getCustomerId(token);
        return sysCoordinationTaskService.submitCommunicate(request, customerId);
    }


    /**
     * 停止沟通
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "停止沟通")
    @PostMapping("/stopCommunicating")
    public BaseResponse stopCommunicating(@RequestBody StopCommunicatingDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("stopCommunicating");
        String customerId = redisUtils.getCustomerId(token);
        return sysCoordinationTaskService.stopCommunicating(request, customerId);
    }


    /**
     * 流程记录
     *
     * @param
     * @return
     */
    @ApiOperation(value = "根据表单id获取流程记录")
    @PostMapping("/getApproveList")
    public BaseResponse getApproveList(@RequestBody FilerecordRequest request, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("getApproveList");
        String customerId = redisUtils.getCustomerId(token);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        request.setuType(user.getUType());
        return taskProcessinService.getApproveList(request, customerId, token, year);
    }


    /**
     * 流程管理
     * 已部署流程
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "流程管理 已部署流程")
    @PostMapping("/getFlowList")
    public BaseResponse getFlowList(@RequestBody SysFlowRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getFlowList");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.getFlowList(request, redisUtils.getUid(token), customerId);
    }


    @PostMapping("/getTemplatebyfileid")
    public BaseResponse getTemplatebyfileid(@RequestBody FilerecordRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getTemplatebyfileid");
        String customerId = redisUtils.getCustomerId(token);
        return fileTemplateService.getTemplatebyfileid(request, customerId);
    }


    @ApiOperation(value = "根据表单id获取沟通记录")
    @PostMapping("/getCoordinationProcessByfileid")
    public BaseResponse getCoordinationProcessByfileid(@RequestBody FilerecordRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getCoordinationProcessByfileid");
        String customerId = redisUtils.getCustomerId(token);
        return sysCoordinationProcessService.getCoordinationProcessByfileid(request, customerId);
    }


    @ApiOperation(value = "根据表单id获取抄送记录")
    @PostMapping("/getUserCarbonCopyListByfileid")
    public BaseResponse getUserCarbonCopyListByfileid(@RequestBody FilerecordRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getUserCarbonCopyListByfileid");
        String customerId = redisUtils.getCustomerId(token);
        return carbonCopyService.getUserCarbonCopyListByfileid(request, customerId);
    }


    @ApiOperation(value = "根局表单id过去流程图信息")
    @PostMapping("/getflowmap")
    public BaseResponse getflowmap(@RequestBody FilerecordRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getflowmap");
        String customerId = redisUtils.getCustomerId(token);
        request.setuId(redisUtils.getUid(token));
        return flowService.getflowmap(request, customerId);
    }


    @ApiOperation(value = "抄送办理")
    @PostMapping("/Cctransaction")
    public BaseResponse Cctransaction(@RequestBody SysCcDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("Cctransaction");
        String customerId = redisUtils.getCustomerId(token);
        return carbonCopyService.Cctransaction(request, customerId);
    }


    @ApiOperation(value = "抄送阅读状态更新")
    @PostMapping("/CctransactionRead")
    public BaseResponse CctransactionRead(@RequestBody SysCcDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("CctransactionRead");
        String customerId = redisUtils.getCustomerId(token);
        return carbonCopyService.CctransactionRead(request, customerId);
    }

    @ApiOperation(value = "根据entityId删除抄送记录")
    @PostMapping("/deleteCarbonCopyByEntityId")
    public BaseResponse deleteCarbonCopyByEntityId(@RequestBody SysCcSendDto request) {
        log.info("deleteCarbonCopyByEntityId");
        return carbonCopyService.deleteCarbonCopyByEntityId(request);
    }

    @ApiOperation(value = "沟通提交")
    @PostMapping("/submitCommunicatecom")
    public BaseResponse submitCommunicatecom(@RequestBody InitiateCommunicationDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("submitCommunicatecom");
        String customerId = redisUtils.getCustomerId(token);
        return sysCoordinationTaskService.submitCommunicatecom(request, customerId);
    }


    @ApiOperation(value = "设置审批状态")
    @PostMapping("/Settingapprovalstatus")
    public BaseResponse Settingapprovalstatus(@RequestBody FileStatusDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("Settingapprovalstatus");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.Settingapprovalstatus(request, customerId, token, redisUtils.getUserInfoByToken(token));
    }


    @ApiOperation(value = "新增file表单信息")
    @PostMapping("/fileentitycreate")
    public BaseResponse fileentitycreate(@RequestBody WorkFlowFileDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("fileentitycreate");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.fileentitycreate(request, customerId, token);
    }


    @ApiOperation(value = "获取附件关联信息集合")
    @GetMapping("/getfilerelationlist")
    public BaseResponse getfilerelationlist(@RequestParam String request, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("filerelationlist");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.filerelationlist(request, tCode, customerId, year);
    }

    @ApiOperation(value = "获取附件关联信息集合(根据fileId集合)")
    @PostMapping("/getFileRelationByFileIds")
    public BaseResponse getFileRelationByFileIds(@RequestBody List<String> fileIds, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("getFileRelationByFileIds");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getFileRelationByFileIds(fileIds, tCode, customerId, year);
    }


    @ApiOperation(value = "获取流程节点信息")
    @PostMapping("/getFlow")
    public BaseResponse getFlow(@RequestBody GetFlowDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getFlow");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.getFlow(request, customerId);
    }


    @ApiOperation(value = "设置任务的阅读状态")
    @GetMapping("/setTaskStatus")
    public BaseResponse setTaskStatus(@RequestParam String request) {
        log.info("setTaskStatus");
        return taskService.setTaskStatus(request);
    }

    @ApiOperation(value = "获取指定标段的file信息")
    @PostMapping("/getFilebySecid")
    public BaseResponse getFilebySecid(@RequestBody FileSecidDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getFilebySecid");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getFilebySecid(request, customerId);
    }

    @ApiOperation(value = "批量获取指定标段的file信息")
    @PostMapping("/getFilebySecids")
    public BaseResponse getFilebySecids(@RequestBody FileSecidDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getFilebySecids");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getFilebySecids(request, customerId);
    }

    @ApiOperation(value = "批量获取流程的file信息")
    @PostMapping("/getFileByFlow")
    public BaseResponse getFileByFlow(@RequestBody List<GetFlowDto> request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getFileByFlow");
        return flowService.getFileByFlow(request, redisUtils.getCustomerId(token));
    }

    @ApiOperation(value = "")
    @PostMapping("/getDangerousProjectFileBySecId")
    public BaseResponse getDangerousProjectFileBySecId(@RequestBody FileSecidDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getDangerousProjectFileBySecId");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getDangerousProjectFileBySecId(request, customerId);
    }


    @ApiOperation(value = "根据用户表单获取任务id")
    @GetMapping("/GetFileTask")
    public BaseResponse GetFileTask(@RequestParam String fileId, @RequestParam String userId, @RequestHeader(value = "token", required = false) String token) {
        log.info("GetFileTask");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.GetFileTask(fileId, userId, customerId);
    }

    /**
     * 根据流程任务id获取流程对象信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据任务id获取任务详细信息")
    @GetMapping("/getTaskById")
    public BaseResponse getTaskById(@RequestParam String id, @RequestHeader(value = "token", required = false) String token) {
        log.info("getTaskById");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.getTaskById(id, customerId);
    }

    /**
     * 根据流程任务id获取流程对象信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据审批记录id获取审批详细信息")
    @GetMapping("/getTaskProcessByTpId")
    public BaseResponse getTaskProcessByTpId(@RequestParam String id, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("getTaskProcessByTpId");
        String customerId = redisUtils.getCustomerId(token);
        return taskProcessinService.getTaskProcessByTpId(id, tCode, customerId, year);
    }


    /**
     * 获取流程模板和流程文档信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取文档模板信息")
    @PostMapping("/getTemplateList")
    public BaseResponse getTemplateList(@RequestBody FilerecordRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getTemplateList");
        String customerId = redisUtils.getCustomerId(token);
        return fileTemplateService.getTemplateList(request, customerId);
    }

    /**
     * 新建表单关联信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新建表单关联信息")
    @PostMapping("/creaetSysMultiFormFlowPojo")
    public BaseResponse creaetSysMultiFormFlowPojo(@RequestBody List<SysMultiFormFlow> request, @RequestHeader(value = "token", required = false) String token) {
        log.info("creaetSysMultiFormFlowPojo");
        String customerId = redisUtils.getCustomerId(token);
        return sysMultiFormFlowService.create(request, customerId);
    }

    /**
     * 新建表单关联信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取表单关联信息")
    @PostMapping("/GetSysMultiFormFlow")
    public BaseResponse GetSysMultiFormFlow(@RequestBody SysMultiFormFlow request, @RequestHeader(value = "token", required = false) String token) {
        log.info("GetSysMultiFormFlow");
        String customerId = redisUtils.getCustomerId(token);
        List<SysMultiFormFlow> list = sysMultiFormFlowService.GetSysMultiFormFlow(request, customerId);
        return new BaseResponse("", list, list.size());
    }

    /**
     * 新建表单关联信息
     *
     * @param secIds
     * @return
     */
    @PostMapping("/getBiddingProcedures")
    public BaseResponse getBiddingProcedures(@RequestBody List<String> secIds, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year, @RequestParam(value = "businessCode", required = false) String businessCode) {
        log.info("getBiddingProcedures");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getBiddingProcedures(secIds, customerId, year,businessCode);
    }

    /**
     * 招标办理进度
     *
     * @param getFlowDtos
     * @return
     */
    @PostMapping("/getBidProgress")
    public BaseResponse getBidProgress(@RequestBody List<GetFlowDto> getFlowDtos, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("getBiddingProcedures");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getBidProgress(getFlowDtos, customerId, tCode, year);
    }

    /**
     * 新建表单关联信息
     *
     * @param fileId
     * @param pId
     * @return
     */
    @GetMapping("/updateFileProjectByFileId")
    public BaseResponse updateFileProjectByFileId(@RequestParam String fileId, @RequestParam String pId, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("updateFileProjectByFileId");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.updateFileProjectByFileId(fileId, pId, customerId, tCode, year);
    }


    @GetMapping("/getFileProcessStatus")
    public BaseResponse getFileProcessStatus(@RequestParam String fileId, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("getFileProcessStatus");
        String customerId = redisUtils.getCustomerId(token);
        return taskProcessinService.getFileProcessStatus(fileId, tCode, redisUtils.getUid(token), customerId, year);
    }

    @GetMapping("/getReturnStatus")
    public BaseResponse getReturnStatus(@RequestParam String actId, String flowId, @RequestHeader(value = "token", required = false) String token) {
        log.info("getReturnStatus");
        String customerId = redisUtils.getCustomerId(token);
        return flowActivityService.getReturnStatus(actId, flowId, customerId);
    }

    @ApiOperation(value = "根据用户记录获取表单数据查看权限")
    @PostMapping("/approvalRecordAuthority")
    public BaseResponse approvalRecordAuthority(@RequestBody ApprovalRecordAuthorityDto request, @RequestHeader(value = "token", required = false) String token) {
        Object uId = redisUtils.get(token);
        if (uId == null)
            return new BaseResponse("Token失效！");
        log.info("approvalRecordAuthority");
        String customerId = redisUtils.getCustomerId(token);
        Map<String, Object> boolStr = flowService.approvalRecordAuthority(request, uId.toString(), customerId);
        return new BaseResponse(true, "操作成功", boolStr);
    }

    @ApiOperation(value = "判断当前审批记录是否允许撤回")
    @GetMapping("/AllowWithdrawJudgment")
    public BaseResponse AllowWithdrawJudgment(@RequestParam String tpId, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("approvalRecordAuthority");
        String customerId = redisUtils.getCustomerId(token);
        Map<String, Object> boolStr = flowService.AllowWithdrawJudgment(tpId, tCode, customerId, year);
        return new BaseResponse(true, "操作成功", boolStr);
    }


    @ApiOperation(value = "业务办理提醒")
    @GetMapping("/getBusinessProcessingProgress")
    public BaseResponse getBusinessProcessingProgress(@RequestParam String uId, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("getBusinessProcessingProgress");
        String customerId = redisUtils.getCustomerId(token);
        return flowActivityService.getBusinessProcessingProgress(uId, customerId, year);
    }

    @ApiOperation(value = "获取推送调用记录信息")
    @PostMapping("/getTaskPushCallRecordList")
    public BaseResponse getTaskPushCallRecordList(@RequestBody TaskPushCallRecordDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getTaskPushCallRecordList");
        String customerId = redisUtils.getCustomerId(token);
        return taskPushCallRecordService.getTaskPushCallRecordList(request, customerId);
    }


    @ApiOperation(value = "从新推送代办信息")
    @GetMapping("/pushTodoTask")
    public BaseResponse pushTodoTask(@RequestParam String id, @RequestHeader(value = "token", required = false) String token) {
        log.info("pushTodoTask");
        String customerId = redisUtils.getCustomerId(token);
        return taskPushCallRecordService.pushTodoTask(id, customerId, token);
    }

    @ApiOperation(value = "获取流程审批时效信息")
    @PostMapping("/getPrescription")
    public BaseResponse getPrescription(@RequestBody ApprovalTimelimitDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getPrescription");
        String customerId = redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.getPrescription(request, customerId);
    }


    @ApiOperation(value = "获取部门审批时效信息")
    @PostMapping("/getPrescriptionD")
    public BaseResponse getPrescriptionD(@RequestBody ApprovalTimelimitDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getPrescriptionD");
        String customerId = redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.getPrescriptionD(request, customerId);
    }


    @ApiOperation(value = "获取审批时效信息详情")
    @PostMapping("/getPrescriptionDetails")
    public BaseResponse getPrescriptionDetails(@RequestBody ApprovalTimelimitDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getPrescriptionDetails");
        String customerId = redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.getPrescriptionDetails(request, customerId);
    }


    /**
     * 流程管理
     * 生成抄送
     *
     * @param request
     * @return
     */
    @PostMapping("/cctransactionSend")
    public BaseResponse cctransactionSend(@RequestBody SysCcSendDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("cctransactionSend");
        String customerId = redisUtils.getCustomerId(token);
        return carbonCopyService.CctransactionSend(request, customerId);
    }

    /**
     * 流程管理
     * 获取允许撤回的节点信息
     *
     * @param fileId
     * @return
     */
    @GetMapping("/getApproveListPreTask")
    public BaseResponse getApproveListPreTask(@RequestParam String fileId, @RequestParam String userId, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("getApproveListPreTask");
        String customerId = redisUtils.getCustomerId(token);
        return taskProcessinService.getApproveListPreTask(fileId, userId, customerId, tCode, year);
    }


    /**
     * 流程管理
     * 生成抄送
     *
     * @param request
     * @return
     */
    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("returnInitiatorToTask");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.returnInitiatorToTask(request, customerId, token);
    }


    /**
     * 流程管理
     * 获取可以看退回的节点信息
     *
     * @param fileId
     * @return
     */
    @GetMapping("/getReturnActity")
    public BaseResponse getReturnActity(@RequestParam String fileId, @RequestParam String taskId, @RequestParam(value = "tCode", required = false) String tCode, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year) {
        log.info("getReturnActity");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.getReturnActity(fileId, tCode, taskId, customerId, year);
    }


    /**
     * 流程提交方法
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "流程跳过")
    @PostMapping("/skipactivity")
    public BaseResponse skipactivity(@RequestBody SubmitFlowRequstDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("skipactivity");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.skipactivity(request, customerId, token);
    }


    @PostMapping("/createOrUpdateSysExtendValue")
    public BaseResponse createOrUpdateSysExtendValue(@RequestBody List<SysExtendValue> extendConfs, @RequestHeader(value = "token", required = false) String token) {
        log.info("createOrUpdateSysExtendValue");
        String customerId = redisUtils.getCustomerId(token);
        return sysExtendValueService.createOrUpdateSysExtendValue(extendConfs, customerId);
    }

    @GetMapping("/getSysExtendValueByFileId")
    public BaseResponse getSysExtendValueByFileId(@RequestParam String fileId, @RequestHeader(value = "token", required = false) String token) {
        log.info("getSysExtendValueByFileId");
        String customerId = redisUtils.getCustomerId(token);
        return sysExtendValueService.getSysExtendValueByFileId(fileId, customerId);
    }

    @PostMapping("/getSysExtendValueByFileIds")
    public BaseResponse getSysExtendValueByFileIds(@RequestBody List<String> fileIds, @RequestHeader(value = "token", required = false) String token) {
        log.info("getSysExtendValueByFileIds");
        String customerId = redisUtils.getCustomerId(token);
        return sysExtendValueService.getSysExtendValueByFileIds(fileIds, customerId);
    }


    @GetMapping("/returnInitiatorByFileId")
    public BaseResponse returnInitiatorByFileId(@RequestParam String fileId, @RequestParam(value = "tCode", required = false) String tCode, @RequestParam(value = "year", required = false) String year, @RequestParam String account, @RequestParam String operationType) {
        log.info("getSysExtendValueByFileIds");
        return flowService.returnInitiatorByFileId(fileId, tCode, account, operationType, year);
    }


    @ApiOperation(value = "获取下一步流程节点用户信息")
    @PostMapping("/getNextActivitiesByActId")
    public BaseResponse getNextActivitiesByActId(@RequestBody NextActivitiesDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getNextActivitiesByActId");
        String customerId = redisUtils.getCustomerId(token);
        return flowActivityService.getNextActivitiesByActId(request, customerId);
    }


    @GetMapping("/getFlowMapBytCode")
    public BaseResponse getFlowMapBytCode(@RequestParam String tCode) {
        return flowService.getFlowMapBytCode(tCode);
    }


    @PostMapping("/getBiddingProceduresCode")
    public BaseResponse getBiddingProceduresCode(@RequestBody List<String> ids, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year, @RequestParam(value = "businessCode", required = false) String businessCode) {
        log.info("getBiddingProceduresCode");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getBiddingProceduresCode(ids, customerId, year,businessCode);
    }

    @PostMapping("/returnFinalStep")
    public BaseResponse returnFinalStep(@RequestBody List<String> ids){
        return flowService.returnFinalStep(ids);
    }

    @PostMapping("/processReturn")
    public  BaseResponse processReturn(@RequestBody SendbackFlowDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("processReturn");
        String customerId = redisUtils.getCustomerId(token);
        return flowService.processReturn(request,customerId,token);
    }

    @ApiOperation(value = "根局表单id获取流程图、审批记录、代办等相关息")
    @PostMapping("/getFlowMapNotAuthentication")
    public BaseResponse getFlowMapNotAuthentication(@RequestBody FilerecordRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getFlowMapNotAuthentication");
        String customerId = redisUtils.getCustomerId(token);
        request.setuId(redisUtils.getUid(token));
        return flowService.getFlowMapNotAuthentication(request, customerId);
    }

    /**
     * 发送监理现场用表抄送
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("发送监理现场用表抄送")
    @PostMapping("/sendSupervisionCarbonCopy")
    public BaseResponse sendSupervisionCarbonCopy(@RequestBody SysCcSendDto request, @RequestHeader("token") String token) {
        log.info("sendSupervisionCarbonCopy");
        return carbonCopyService.sendSupervisionCarbonCopy(request, redisUtils.getUid(token), redisUtils.getCustomerId(token));
    }
}
