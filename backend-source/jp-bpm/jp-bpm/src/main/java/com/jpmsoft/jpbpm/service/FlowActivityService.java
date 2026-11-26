package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.NextActivitiesDto;
import com.jpmsoft.jpbpm.pojo.dto.userselectionDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.UserSelectionVo;
import com.jpmsoft.jpbpm.pojo.vo.userCVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FlowActivityService extends IService<FlowActivity> {

    /**
     * 获取下一步流程节点用户信息
     *
     * @param requst
     * @return
     */
    BaseResponse getNextActivities(NextActivitiesDto requst,String customerId);

    /**
     * 获取下一步流程节点用户信息
     *
     * @param requst         请求参数
     * @param customerId     租户标识
     * @param topUserEnabled 是否启用「下一步经办人置顶」：为 true 时按 SYS_Config 中 nextUserTopUserIds 配置的顺序，
     *                       把命中的用户排在该节点经办人列表最前面；仅前端选人页面入口传 true，自动完成等内部调用传 false
     * @return
     */
    BaseResponse getNextActivities(NextActivitiesDto requst, String customerId, boolean topUserEnabled);


    FlowActivity getStarActivity(List<FlowActivity> flowActivityList, List<FlowRule> FlowRules,String customerId );

    List<FlowActivity> getFlowActivityByActFlowID(String ActFlowID,String customerId);

    List<FlowActivity> GetSYSFlowActivityEntitybind(FlowActivity new_obj, Map<String,Object> Reverseconditions, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, Integer neglect,String customerId);

    Map<String,String> GetSYSFlowActivityProcess(FlowActivity new_obj, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, Map<String, String> data,String customerId);

    Map<String,String> Getflowline(FlowActivity Starobj, List<Task> Tasklist, List<TaskProcessin> taskProcessinList, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules,String customerId);
    /**
     * 提交时任务代码生成方法
     * @param new_obj
     * @param Reverseconditions
     * @param FlowActivitys
     * @param FlowRules
     * @param task_Processinlist
     * @param selectactivity
     * @param his
     * @param flow
     * @param old_task
     * @param explain
     * @return
     */
    Map<String,String> GetSYSFlowActivityEntitysubmit(FlowActivity new_obj, Map<String,Object> Reverseconditions, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, List<TaskProcessin> task_Processinlist, List<userselectionDto> selectactivity, TaskProcessin his, Flow flow, Task old_task, String explain
            , Map<String, String> data, String taskname,List<Task> nowTask,String customerId,String requestSource);

    List<FlowActivity>  GetFlowActivityList(String key,FlowActivity new_obj, Map<String,Object> Reverseconditions, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, Integer neglect, String checkType, List<String> checkFieldStr,String customerId,List<String> allActId);


    List<FlowActivity>  GetNodeWithPermission(FlowActivity newObj);


    BaseResponse getReturnStatus(String actId, String flowId,String customerId);


    Map<String, Object> setTorsionConditions(String fileId, String uId, String conditions,String customerId,String tCode,String year);

    BaseResponse getBusinessProcessingProgress(String uId,String customerId,String year);


    List<FlowActivity> RecursiveNodeCanreturned(List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, FlowActivity newObj,String customerId);


    List<String> getEndActivity(FlowActivity Starobj, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules,String customerId);

    List<userCVo> selectUserByPostRole(List<String> Activityid, FlowActivity item, String pId, String customerId);

    List<Map<String,Object>> GetSYSFlowActivityEntitybindEnd(FlowActivity new_obj, Map<String, Object> Reverseconditions, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, Integer neglect, String customerId);

    List<UserSelectionVo> getActUser(List<FlowActivity> FlowActivityList, String Reverseconditions, Map<String, String> pData, List<String> actIds, Map<String, Object> newConditions, List<FlowActivity> FlowActivitys, List<Map<String, Object>> TaskProcessinlist,String pId);

    void  setActListALLToNull(String key);

    List<String> GetSYSFlowActivityEntityGateway(FlowActivity new_obj, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, List<Task> nowTaskList, String customerId, String requestSource);

    BaseResponse getNextActivitiesByActId(NextActivitiesDto requst, String customerId);

    Map<String, Integer> getFlowActNum(List<FlowActivity> flowActivityList, List<FlowRule> FlowRuleList, FlowActivity flowActivity);



    List<FlowActivity> getAllSubsequentNodes(FlowActivity Starobj, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules);


    List<String> getNodesAllowedNodeIds(List<FlowActivity> actList, List<TaskProcessin> tpList, String uId, String type, String status,List<String> actIds);

    List<String> getCanReturnNodeId(List<FlowActivity> flowActivityList,String actId);
}
