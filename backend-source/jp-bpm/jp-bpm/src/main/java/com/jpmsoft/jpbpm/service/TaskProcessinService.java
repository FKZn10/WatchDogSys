package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.TaskRequest;
import com.jpmsoft.common.model.request.UserTaskProcessinRequest;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.jpbpm.pojo.dto.SysTaskProcessinDto;
import com.jpmsoft.jpbpm.pojo.dto.SysTaskProcessinUpdateDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.SysTaskProcessinInfoVo;
import com.jpmsoft.jpbpm.pojo.vo.TaskCoordinationTaskVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface TaskProcessinService extends IService<TaskProcessin> {

    TaskProcessin getTaskProcessById(String id, String tCode,String year);

    List<TaskProcessin> getListByIds(List<String> ids, String tCode,String year);

    List<TaskProcessin> getList(QueryWrapper<TaskProcessin> queryWrapper, String tCode,String year);

    List<TaskProcessin> getList(LambdaQueryWrapper<TaskProcessin> queryWrapper, String tCode,String year);

    List<TaskProcessin> getList(QueryWrapper<TaskProcessin> queryWrapper, List<String> tCode,String year);

    List<TaskProcessin> getList(LambdaQueryWrapper<TaskProcessin> queryWrapper, List<String> tCode,String year);

    boolean updateTaskProcessById(TaskProcessin obj, String tCode,String year);

    boolean saveTaskProcessBatch(List<TaskProcessin> list, String tCode,String year);
    int deleteTaskProcessin(String id,String tCode,String year);
    /**
     * 返回新的历史记录
     *
     * @param old_task 当前处理的任务实体
     * @param result   处理结果
     * @param explain  处理说明
     * @param user     操作人编号
     * @param type     操作类型
     * @param flow     流程实体信息
     * @param nextUser 下一步操作人
     * @return 返回新的历史记录实体
     */
    TaskProcessin GetHis(Task old_task, String result, String explain, String user, String type, Flow flow, String nextUser, String Acttype, Float acthour, String customerId, String requestSource);

    TaskProcessin GetHis(File file, String explain, String user, FlowActivity flowAct, String ruleId, Date doTime, String tpName, String taskgetflagstr, String taskReturnType, String customerId, String requestSource);

    int insertTaskProcessin(TaskProcessin processinPojo, String tCode,String year);


    List<TaskProcessin> getListByTpFlowIdAndTpEntityId(String tpFlowId, String tpEntityId, String customerId,String year);

    /**
     * 退回发起人时查询
     *
     * @return
     */
    List<TaskProcessin> getTaskProcessinByReturnstart(String tp_entityid, String tp_flowid, String tp_actid, String customerId,String tCode,String year);


    /**
     * 已办列表
     *
     * @param request
     * @return
     */
    BaseResponse havetodolist(TaskRequest request, String customerId);


    /**
     * 我的事务PM
     * 用户已办流程列表
     *
     * @param userId
     * @param request
     * @return
     */
    BaseResponse getUserTaskProcessList(String userId, UserTaskProcessinRequest request, String customerId);

    /**
     * 用户已办流程列表（去重版）
     * 对于同一个tp_user和tp_entityid，只保留最新的一条办理记录
     *
     * @param userId 用户ID
     * @param request 查询请求
     * @param customerId 客户ID
     * @return 去重后的已办任务列表
     */
    BaseResponse getUserTaskProcessListDistinct(String userId, UserTaskProcessinRequest request, String customerId);

    BaseResponse getApproveList(FilerecordRequest request, String customerId, String token, String year);

    List<Map<String, Object>> getSomeFieldsTaskProcessins(String tpEntityid, String customerId,String tCode,String year);

    BaseResponse getTaskProcessByTpId(String tpId, String tCode, String customerId,String year);

    BaseResponse getFileProcessStatus(String fileId, String tCode, String uId, String customerId,String year);


    BaseResponse getApproveListPreTask(String fileId, String userId, String customerId, String tCode,String year);

    BaseResponse getTaskProcessList(SysTaskProcessinDto request, String customerId);

    BaseResponse deleteTaskProcessById(String id,String tCode,String year);

    BaseResponse updateTaskProcess(SysTaskProcessinUpdateDto request);

    List<SysTaskProcessinInfoVo> getSysTaskProcessinInfo(List<String> tpIds, String customerId);

    BaseResponse getMinTimeAndMaxTime(String tCode,String customerId,String year);

    BaseResponse createTaskProcess(TaskProcessin request,String tCode,String year);

    BaseResponse getUserTaskProcessListCJ(String userId, TaskCoordinationTaskVo request, String customerId);

    BaseResponse checkUserInApprovalProcess(List<String> ids, LogUserInfoVo user);
}
