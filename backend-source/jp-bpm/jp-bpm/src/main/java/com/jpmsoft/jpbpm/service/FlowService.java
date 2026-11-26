package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.SysFlowRequest;
import com.jpmsoft.jpbpm.pojo.dto.*;
import com.jpmsoft.jpbpm.pojo.entity.Flow;
import com.jpmsoft.jpbpm.pojo.entity.FlowActivity;
import com.jpmsoft.jpbpm.pojo.entity.FlowRule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FlowService extends IService<Flow> {

    /**
     * 初始化流程节点信息
     *
     * @param request
     * @return
     */
    BaseResponse getStartActivityInfo(StartActivityInfoDto request,String customerId);

    /**
     * 流程提交方法
     *
     * @param request
     * @return
     */
    BaseResponse submitFlow(SubmitFlowRequstDto request,String customerId,String  token);


    /**
     * 退回流程
     *
     * @param request
     * @return
     */
    BaseResponse sendbackFlow(SendbackFlowDto request,String customerId,String  token);


    /**
     * 停止流程
     *
     * @param request
     * @return
     */
    BaseResponse Stopprocess(StopProcessDto request,String customerId,String  token);


    /**
     * 更换流程操作人
     *
     * @param request
     * @return
     */
    BaseResponse changeProcessOperator(ChangeProcessOperatorDto request,String customerId,String  token);


    /**
     * 补充意见
     *
     * @param request
     * @return
     */
    BaseResponse supplementaryOpinions(SupplementaryOpinionsDto request,String customerId);


    /**
     * 流程撤回
     *
     * @param request
     * @return
     */
    BaseResponse withdrawalProcess(WithdrawalProcessDto request,String customerId,String  token);

    /**
     * 根据客户端、文档所属客户、文档所属项目、文档所属模板返回主版本流程ID
     *
     * @param solutionID   文档所属客户
     * @param companyID    单位ID
     * @param projectID    文档所属项目
     * @param templateCode 文档所属模板
     * @return
     */
    String GetEnableFlowID(String solutionID, String companyID, String projectID, String templateCode,String customerId) throws Exception;


    /**
     * 流程管理
     * 已部署流程
     *
     * @param request
     * @return
     */
    BaseResponse getFlowList(SysFlowRequest request, String uId,String customerId);


    BaseResponse getflowmap(FilerecordRequest requst,String customerId);




    BaseResponse getFlow(GetFlowDto requst,String customerId);

    Map<String, Object> approvalRecordAuthority(ApprovalRecordAuthorityDto request, String uId,String customerId);


    Map<String, Object> AllowWithdrawJudgment(String tpId,String tCode,String customerId,String yearStr);


    List<Flow> getSomeFieldsFlow(String tCode,String customerId);


    BaseResponse returnInitiatorToTask(ReturnInitiatorDto request,String customerId,String  token);


    BaseResponse  getReturnActity(String fileId,String tCode,String taskId,String customerId,String year);

    BaseResponse skipactivity(SubmitFlowRequstDto request,String customerId,String  token);
    List<String> GetNextTask(FlowActivity nowSYS_FlowActivity, List<FlowActivity> FlowActivityAll, List<FlowRule> FlowRuleAll);

    BaseResponse  returnInitiatorByFileId(String fileId,String tCode,String account,String operationType,String year);

    List<FlowActivity>  getFlowActivities(String tCode, String customerId);

    BaseResponse getFlowMapBytCode(String tCode);

    BaseResponse returnFinalStep(List<String> fileIds);


    BaseResponse processReturn(SendbackFlowDto request, String customerId, String token);


    BaseResponse getFlowMapNotAuthentication(FilerecordRequest requst, String customerId);

    BaseResponse getFileByFlow(List<GetFlowDto> request, String customerId);
}
