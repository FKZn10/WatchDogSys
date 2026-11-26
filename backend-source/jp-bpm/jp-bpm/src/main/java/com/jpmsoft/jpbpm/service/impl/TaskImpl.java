package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.AuditTypeEnum;
import com.jpmsoft.common.model.request.SysFileRequest;
import com.jpmsoft.common.model.request.SysTaskRequest;
import com.jpmsoft.common.model.request.UserTaskRequest;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.response.SysFileResponse;
import com.jpmsoft.common.model.response.TaskResponse;
import com.jpmsoft.common.model.third.AgencyModel;
import com.jpmsoft.common.rabbit.AuditSender;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.TaskMapper;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.dto.BatchhandlingTaskDto;
import com.jpmsoft.jpbpm.pojo.dto.BatchhandlingTaskListDto;
import com.jpmsoft.jpbpm.pojo.dto.GetTaskDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.*;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import com.jpmsoft.jpbpm.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Value("${bpm.sendtodourl}")
    public String senToDoUrl;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private FlowRuleService flowRuleService;

    @Resource
    private SysPersonalBpmAuthorizeService sysPersonalBpmAuthorizeService;
    @Resource
    public SysUserService sysUserService;
    @Resource
    private SysConfigEntityService sysConfigEntityService;
    @Resource
    private FlowActivityService flowActivityService;
    @Resource
    private FlowService flowService;
    @Resource
    private FileTemplateService fileTemplateService;
    @Resource
    private TaskPushCallRecordService taskPushCallRecordService;

    @Resource
    private SysCoordinationTaskService sysCoordinationTaskService;
    @Resource
    private FileService fileService;
    @Resource
    private AuditSender auditSender;
    @Resource
    private IHolidayVacationsService holidayVacationsService;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    private SysPositionUserService sysPositionUserService;
    @Resource
    private ISysMessageService sysMessageService;
    @Resource
    private ISysMessageReceiveService sysMessageReceiveService;
    @Resource
    private ISysTaskMassageService sysTaskMassageService;
    @Resource
    private ISysTaskFirstReadService sysTaskFirstReadService;

    @Override
    public BaseResponse Gettask(GetTaskDto request, String customerId) {
        String solutionid = request.getSolutionid();
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List data;
        if (ValidateUtil.isEmpty(solutionid))
            data = baseMapper.selectList(wrapper);
        else {
            wrapper.eq("TaskClient", solutionid);
            data = baseMapper.selectList(wrapper);
        }
        int total = 0;
        if (data != null)
            total = data.size();
        return new BaseResponse("获取成功", data, total);
    }

    @Override
    public Task GetNewTaskobj(FlowRule r, TaskProcessin his, Task old_task, Flow flow, String nextUser, String ActHour, String taskname, String customerId) {
        Task task = new Task();
        task.setTaskid(GenerateUUID.getUUID());
        task.setTaskread("未读");
        task.setTaskname(taskname);  // 任务名称
        task.setTaskclient(flow.getFlowclient());
        task.setTaskflow(flow.getFlowid());
        task.setTaskpreactid(r.getRulebeginactid());
        task.setTaskcurrentactid(r.getRuleendactid());
        task.setTaskproject(old_task.getTaskproject());
        task.setTaskrule(r.getRuleid());
        task.setTaskentityid(his.getTpEntityid());
        task.setTasksendtime(new Date());
        task.setTasksenduser(his.getTpUser());
        task.setTaskuser(nextUser);
        task.setTaskType(his.getTpType());
        task.setTaskparentactid(old_task.getTaskparentactid());
        task.setTaskparentflowid(old_task.getTaskparentflowid());
        task.setTaskparenttaskid(old_task.getTaskparenttaskid());
        task.setTasksection(old_task.getTasksection());
        if (!"All".equals(customerId)) {
            task.setTeId(customerId);
        }
        Date date = new Date();
        task.setTaskstarttime(date);
        task.setTaskendtime(DateUtil.DateChangeHour(date, DateUtil.TastProcessingtime(ActHour)));
        task.setFlowId(flow.getFlowid());
        task.setTCode(flow.getTCode());
        return task;
    }


    @Override
    public void deleteTaskByIdOrTaskEntityID(String taskId, String TaskEntityID, String customerId) {
        if (!ValidateUtil.isEmpty(taskId) && !ValidateUtil.isEmpty(TaskEntityID)) {
            baseMapper.deleteTaskByIdOrTaskEntityID(taskId, TaskEntityID);
            sysCoordinationTaskService.deleteByIdOrTaskEntityID(taskId, TaskEntityID, customerId);
        } else if (!ValidateUtil.isEmpty(taskId)) {
            baseMapper.deleteById(taskId);
            sysCoordinationTaskService.deleteByTaskId(taskId, customerId);
        } else if (!ValidateUtil.isEmpty(TaskEntityID)) {
            baseMapper.deleteTaskByTaskEntityID(TaskEntityID);
            sysCoordinationTaskService.deleteByEntityId(TaskEntityID, customerId);

        }
    }

    @Override
    public void deleteTaskByTaskEntityID(String TaskEntityID, String customerId) {
        if (!ValidateUtil.isEmpty(TaskEntityID)) {
            baseMapper.deleteTaskByTaskEntityID(TaskEntityID);
            sysCoordinationTaskService.deleteByEntityId(TaskEntityID, customerId);
        }
    }

    @Override
    @Transactional
    public void deleteTaskByTaskEntityIDAndTaskFlowAndTaskSendUser(String taskEntityId, String taskFlow, String taskSendUser) {
        if (!ValidateUtil.isEmpty(taskEntityId) && !ValidateUtil.isEmpty(taskFlow) && !ValidateUtil.isEmpty(taskSendUser)) {
            baseMapper.deleteTaskByTaskEntityIDAndTaskFlowAndTaskSendUser(taskEntityId, taskFlow, taskSendUser);
            sysCoordinationTaskService.deleteByTaskEntityIDAndTaskFlowAndTaskSendUser(taskEntityId, taskFlow, taskSendUser);
        }
    }

    @Override
    public Integer seleteTaskCountByTaskEntityID(String TaskEntityID, String customerId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("TaskEntityID", TaskEntityID);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectCount(wrapper).intValue();
    }

    @Override
    public void updateTaskUserByTaskId(String taskUser, String taskId) {
        Task task = baseMapper.selectById(taskId);
        if (task != null) {
            task.setTaskuser(taskUser);
            baseMapper.updateById(task);
        }
    }

    @Override
    public void updateTaskCoordinationByTaskId(String taskCoordination, String taskId) {
        Task task = baseMapper.selectById(taskId);
        if (task != null) {
            task.setTaskcoordination(taskCoordination);
            baseMapper.updateById(task);
        }
    }

    @Override
    public List<Task> getTaskListByTaskIdAndTaskEntityId(String taskId, String taskEntityId, String customerId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("TaskFlow", taskId);
        wrapper.eq("TaskEntityID", taskEntityId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectList(wrapper);
    }


    /**
     * 待办列表
     *
     * @param fileid
     * @return
     */
    @Override
    public List<Task> todolists(String fileid, String customerId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("TaskEntityID", fileid);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectList(wrapper);
    }


    @Override
    public BaseResponse getUserTaskList(String userId, UserTaskRequest request, String customerId, boolean isDock) {
        List<String> userIds = new ArrayList<>();
        List<String> abUid = sysPersonalBpmAuthorizeService.getCreateUidByAuthorizerUid(userId, customerId);
        if (abUid != null && abUid.size() > 0)
            userIds.addAll(abUid);
        userIds.add(userId);
        QueryMap<UserTaskRequest> params = new QueryMap<>(request);
        String tCodes = request.gettCode();
        //可能是多个编码为避免前端修改故不修改入参格式
        if (ValidateUtil.isNotEmpty(tCodes))
            if (tCodes.contains("-"))
                params.put("tCodes", Arrays.asList(tCodes.split("-")));
            else
                params.put("tCode");
        params.put("taskSendUser").put("taskname").put("secName").put("fileCode").put("taskUserName")
                .put("pName").put("conName").put("flowName").put("taskSendTimeStart").put("taskSendTimeEnd").put("userIds", userIds);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        if ("remainder".equals(request.getSortFiled())) {
            params.put("dataCount", "All");
            params.put("sortFiled", "");
        } else {
            params.put("dataCount", "");
        }


        String typeStr = request.getTypeStr();
        if ("1".equals(typeStr)) {
            params.put("typeStr", "1");
            params.put("userIds", null);
            params.put("uId", userId);
        } else {
            params.put("typeStr", null);
        }
        int total = 0;
        List<TaskResponse> datas = baseMapper.getUserTaskList(params.getQuery());
        List<TaskResponse> list = getTaskListRemainder(datas, request.getCurrentTime(), customerId);
        if ("remainder".equals(request.getSortFiled())) {
            if (request.getLessThanTime() != null) {
                list = list.stream().filter(n -> n.getRemainder().compareTo(request.getLessThanTime()) < 0).collect(Collectors.toList());
            }
            total = list.size();
            if (ValidateUtil.isNotEmpty(request.getSortType())) {
                if (request.getSortType().toLowerCase(Locale.ENGLISH).equals("asc")) {
                    list = list.stream().sorted(Comparator.comparing(TaskResponse::getRemainder)).collect(Collectors.toList());
                } else {
                    list = list.stream().sorted(Comparator.comparing(TaskResponse::getRemainder).reversed()).collect(Collectors.toList());
                }
            }
            if (list.size() > 0) {
                Integer startNum = (request.getPage() - 1) * request.getSize();
                Integer endNum = request.getPage() * request.getSize();
                if (list.size() > endNum) {
                    list = list.subList(startNum, endNum);
                } else if (list.size() < startNum) {
                    list = null;
                } else {
                    list = list.subList(startNum, list.size());
                }
            }


        } else {
            total = baseMapper.getUserTaskListCount(params.getQuery());
        }
        if (isDock && !CollectionUtils.isEmpty(list)) {
            //是对接查询待办信息
            List<TaskDockPageVO> results = new ArrayList<>();
            String toDoUrl = sysConfigEntityService.getConfByCode("toDoUrl");
            if (ValidateUtil.isEmpty(toDoUrl))
                return BaseResponse.fail("JPM方缺少待办地址配置!");
            for (TaskResponse task : list) {
                TaskDockPageVO res = new TaskDockPageVO();
                res.setTaskId(task.getTaskid());
                res.setTaskName(task.getTaskname());
                res.setTaskType(task.getTaskType());
                res.setPName(task.getpName());
                res.setConName(task.getConName());
                res.setSecName(task.getSecName());
                res.setSendUserName(task.getSendUserName());
                res.setTaskUserName(task.getTaskUserName());
                res.setTaskSendTime(DateUtil.DateToString(task.getTaskendtime(), "yyyy-MM-dd HH:mm:ss"));
                res.setTaskRead(task.getTaskread());
                res.setTaskReadTime(DateUtil.DateToString(task.getTaskreadtime(), "yyyy-MM-dd HH:mm:ss"));
                res.setFlowName(task.getFlowName());
                res.setFlowCreateUname(task.getFilecreateusername());
                res.setFlowCreateTime(task.getFilecreatedate());
                res.setFlowState(task.getFileflowstatus());
                res.setRemainder(task.getRemainder());
                res.setTaskWebUrl(toDoUrl + task.getTaskid());
                results.add(res);
            }
            return BaseResponse.ok(results, total);
        }
        return new BaseResponse("获取成功", list, total);
    }


    @Override
    public BaseResponse getUserTaskListCJ(String userId, TaskCoordinationTaskVo request,String customerId) {
        List<String> userIds = new ArrayList<>();
        List<String> abUid = sysPersonalBpmAuthorizeService.getCreateUidByAuthorizerUid(userId, customerId);
        if (abUid != null && abUid.size() > 0)
            userIds.addAll(abUid);
        userIds.add(userId);
        Map<String, Object> params = new HashMap<>();
        params.put("userIds", userIds);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "createtime");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "desc");
        params.put("taskname", !ValidateUtil.isEmpty(request.getTaskName()) ? request.getTaskName() : null);
        params.put("tasknames", !CollectionUtils.isEmpty(request.getTaskNames()) ? request.getTaskNames() : null);
        params.put("pNames", !CollectionUtils.isEmpty(request.getpNames()) ? request.getpNames() : null);
        params.put("conNames", !CollectionUtils.isEmpty(request.getConNames()) ? request.getConNames() : null);
        params.put("secNames", !CollectionUtils.isEmpty(request.getSecNames()) ? request.getSecNames() : null);
        params.put("createNames", !CollectionUtils.isEmpty(request.getCreateNames()) ? request.getCreateNames() : null);
        params.put("doUserNames", !CollectionUtils.isEmpty(request.getDoUserNames()) ? request.getDoUserNames() : null);
        params.put("fileCreateNames", !CollectionUtils.isEmpty(request.getFileCreateNames()) ? request.getFileCreateNames() : null);
        params.put("startTime", !ValidateUtil.isEmpty(request.getStartTime()) ? request.getStartTime() : null);
        params.put("endTime", !ValidateUtil.isEmpty(request.getEndTime()) ? request.getEndTime() : null);
        List<TaskCoordinationTaskVo> list = baseMapper.getTaskCoordinationTask(params);
        Integer total = baseMapper.getTaskCoordinationTaskCount(params);
        //
        List<Map<String, Object>>  keys=new ArrayList<>();
        Map<String, Object>  obj=new HashMap<>();
        obj.put("keys", baseMapper.getTaskCoordinationTaskKey(params,"t2.taskName"));
        obj.put("name", "任务名称");
        obj.put("code", "taskNames");
        obj.put("type", "string");
        keys.add(obj);
        Map<String, Object>  obj2=new HashMap<>();
        obj2.put("keys", baseMapper.getTaskCoordinationTaskKey(params,"pre_project.P_Name"));
        obj2.put("name", "所属项目");
        obj2.put("code", "pNames");
        obj2.put("type", "string");
        keys.add(obj2);
        Map<String, Object>  obj0=new HashMap<>();
        obj0.put("keys", baseMapper.getTaskCoordinationTaskKey(params,"sys_file.FileCreateUserName"));
        obj0.put("name", "发起人");
        obj0.put("code", "fileCreateNames");
        obj0.put("type", "string");
        keys.add(obj0);
        Map<String, Object>  obj1=new HashMap<>();
        obj1.put("keys", baseMapper.getTaskCoordinationTaskKey(params,"u.U_Name"));
        obj1.put("name", "发送人");
        obj1.put("code", "createNames");
        obj1.put("type", "string");
        keys.add(obj1);
        Map<String, Object>  obj3=new HashMap<>();
        obj3.put("keys", "");
        obj3.put("name", "接收时间");
        obj3.put("code", "createtime");
        obj3.put("type", "data");
        keys.add(obj3);
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("list", list);
        returnData.put("keys", keys);
        return BaseResponse.ok(returnData, total);
    }


    @Override
    public BaseResponse getTaskList(SysTaskRequest request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "Taskendtime");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "DESC");

        params.put("taskname", !ValidateUtil.isEmpty(request.getTaskname()) ? request.getTaskname() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        params.put("taskSendUser", !ValidateUtil.isEmpty(request.getTaskSendUser()) ? request.getTaskSendUser() : null);
        params.put("taskUser", !ValidateUtil.isEmpty(request.getTaskUser()) ? request.getTaskUser() : null);
        params.put("secName", !ValidateUtil.isEmpty(request.getSecName()) ? request.getSecName() : null);
        params.put("fileId", !ValidateUtil.isEmpty(request.getFileId()) ? request.getFileId() : null);
        params.put("taskSendUserId", !ValidateUtil.isEmpty(request.getTaskSendUserId()) ? request.getTaskSendUserId() : null);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        if ("remainder".equals(request.getSortFiled())) {
            params.put("dataCount", "All");
        } else {
            params.put("dataCount", "");
        }
        List<TaskResponse> datas = baseMapper.getTaskList(params);
        Integer total = baseMapper.getTaskListCount(params);
        return new BaseResponse("获取成功", datas, total);
    }


    public List<TaskResponse> getTaskListRemainder(List<TaskResponse> oldList, Date currentTime, String customerId) {
        if (oldList == null || oldList.size() == 0) {
            return oldList;
        }
        List<TaskResponse> list = new ArrayList<>();
        List<String> actId = oldList.stream().map(n -> n.getTaskcurrentactid()).collect(Collectors.toList());//获取当前任务节点
        List<FlowActivity> actList = flowActivityService.listByIds(actId);
        Date minDatime = oldList.stream().filter(o -> o.getTasksendtime() != null).map(TaskResponse::getTasksendtime).distinct().min((e1, e2) -> e1.compareTo(e2)).get();
        if (currentTime == null) {
            currentTime = new Date();
        }
        return holidayVacationsService.timeEfficiencyCalculation(oldList, minDatime, currentTime, actList, customerId);

    }


    public Task getById(String id) {
        return baseMapper.selectById(id);
    }


    @Override
    public BaseResponse setTaskStatus(String taskid) {
        Task taskentity = baseMapper.selectById(taskid);
        if (ValidateUtil.isNull(taskentity)) {
            return BaseResponse.fail("任务不存在！");
        }
        taskentity.setTaskread("已读");
        taskentity.setTaskreadtime(new Date());
        baseMapper.updateById(taskentity);
        // 存储第一次读取待办的时间
        sysTaskFirstReadService.createFirstRead(taskentity);
        return new BaseResponse(true, "更新成功！");
    }


    @Override
    public BaseResponse GetFileTask(String fileId, String userId, String customerId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("TaskEntityID", fileId);
        wrapper.eq("TaskUser", userId);
        wrapper.eq("Task_Return_Type", "start");
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<Task> entitylist = baseMapper.selectList(wrapper);
        return new BaseResponse("获取成功！", entitylist, 1);
    }

    @Override
    public List<Task> getTaskListByFiles(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        } else {
            QueryWrapper<Task> wrapper = new QueryWrapper<>();
            wrapper.in("TaskEntityID", ids);
            return baseMapper.selectList(wrapper);
        }
    }


    @Override
    public List<Task> getTaskByEntityIdAndCurrentActId(String entityId, String actId, String customerId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("TaskEntityID", entityId);
        wrapper.eq("TaskCurrentActID", actId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<Task> entitylist = baseMapper.selectList(wrapper);
        return entitylist;
    }

    @Override
    public void deleteTaskByEntityIdAndCurrentActId(String entityId, String actId, String taskId, String customerId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("TaskEntityID", entityId);
        wrapper.eq("TaskCurrentActID", actId);
        wrapper.ne("TaskID", taskId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        baseMapper.delete(wrapper);
    }

    @Override
    public Integer getUserTaskListCount(String userId, String customerId) {
        Map<String, Object> params = new HashMap<>();
        List<String> userIds = new ArrayList<>();
        List<String> abUid = sysPersonalBpmAuthorizeService.getCreateUidByAuthorizerUid(userId, customerId);
        if (abUid != null && abUid.size() > 0)
            userIds.addAll(abUid);
        userIds.add(userId);
        params.put("userIds", userIds);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        return baseMapper.getUserTaskListCount(params);
    }


    @Override
    public BaseResponse getTaskById(String id, String customerId) {
        return new BaseResponse("获取成功！", baseMapper.selectById(id), 1);
    }


    @Override
    public BaseResponse deleteTaskById(String id, String fileId, LogUserInfoVo user) {
        baseMapper.deleteById(id);
        auditSender.sendAudit("删除待办信息", user, fileId, AuditTypeEnum.del.getkey());
        return BaseResponse.ok();
    }

    @Override
    @Transactional
    public BaseResponse createTask(Task request, LogUserInfoVo user, String customerId, String token, String tCode,String year) {
        FlowRule Ruleobj = flowRuleService.lineofretirement(request.getTaskflow(), request.getTaskpreactid(), request.getTaskcurrentactid(), "提交", customerId);
        request.setTaskrule(Ruleobj.getRuleid());
        baseMapper.insert(request);
        File fileObj = fileService.getFileById(request.getTaskentityid(), tCode,year);
        fileService.updatefile(fileObj.gettCode(), request.getTaskentityid(), "next", request.getTaskflow(), customerId, token,year);
        auditSender.sendAudit("新增待办信息", user, request.getTaskentityid(), AuditTypeEnum.add.getkey());
        return BaseResponse.ok();
    }


    @Override
    public List<AgencyStatisticsVo> statisticsUserAgency(AgencyStatisticsDto request, String customerId) {
        List<AgencyStatisticsVo> data = baseMapper.statisticsUserAgency(request, customerId);
        return data;
    }

    @Override
    public List<TaskCountVo> getPrescription(Map<String, Object> params) {
        return baseMapper.getPrescription(params);
    }


    @Override
    public List<AutomaticTasks> getAutomaticTasks(Map<String, Object> params) {
        return baseMapper.getAutomaticTasks(params);
    }


    //关联员权限账号查询待办信息
    @Override
    public BaseResponse getTaskListByUserId(String userId, UserTaskRequest request, String customerId, boolean isDock) {
        QueryMap<UserTaskRequest> params = new QueryMap<>(request);
        String tCodes = request.gettCode();
        //可能是多个编码为避免前端修改故不修改入参格式
        if (ValidateUtil.isNotEmpty(tCodes))
            if (tCodes.contains("-"))
                params.put("tCodes", Arrays.asList(tCodes.split("-")));
            else
                params.put("tCode");
        params.put("taskSendUser").put("taskname")
                .put("pName").put("conName").put("flowName").put("taskSendTimeStart").put("taskSendTimeEnd");
        params.put("teId", "All".equals(customerId) ? null : customerId);
        if ("remainder".equals(request.getSortFiled())) {
            params.put("dataCount", "All");
            params.put("sortFiled", "");
        } else {
            params.put("dataCount", "");
        }

        List<String> dIds = sysPositionUserService.getDeptIdList(userId, customerId);
        if (CollectionUtils.isEmpty(dIds)) {
            return new BaseResponse("获取成功", new ArrayList<>(), 0);
        } else if (!dIds.contains("ALL")) {
            params.put("minDids", dIds);
            //获取当前用户的所属
            SysUser userObj = sysUserService.getById(userId);
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.eq("C_ID", userObj.getcId());
            List<SysUser> users = sysUserService.list(wrapper);
            if (!CollectionUtils.isEmpty(users)) {
                List<String> uids = users.stream().map(n -> n.getuId()).collect(Collectors.toList());
                params.put("minUids", uids);
            }
        }
        params.put("minType", "1");
        params.put("taskSendTimeStartMin", DateUtil.DateChangeDay(new Date(), -30));
        int total = 0;
        List<TaskResponse> datas = baseMapper.getUserTaskList(params.getQuery());
        List<TaskResponse> list = getTaskListRemainder(datas, request.getCurrentTime(), customerId);
        if ("remainder".equals(request.getSortFiled())) {
            if (request.getLessThanTime() != null) {
                list = list.stream().filter(n -> n.getRemainder().compareTo(request.getLessThanTime()) < 0).collect(Collectors.toList());
            }
            total = list.size();
            if (ValidateUtil.isNotEmpty(request.getSortType())) {
                if (request.getSortType().toLowerCase(Locale.ENGLISH).equals("asc")) {
                    list = list.stream().sorted(Comparator.comparing(TaskResponse::getRemainder)).collect(Collectors.toList());
                } else {
                    list = list.stream().sorted(Comparator.comparing(TaskResponse::getRemainder).reversed()).collect(Collectors.toList());
                }
            }
            if (list.size() > 0) {
                Integer startNum = (request.getPage() - 1) * request.getSize();
                Integer endNum = request.getPage() * request.getSize();
                if (list.size() > endNum) {
                    list = list.subList(startNum, endNum);
                } else if (list.size() < startNum) {
                    list = null;
                } else {
                    list = list.subList(startNum, list.size());
                }
            }


        } else {
            total = baseMapper.getUserTaskListCount(params.getQuery());
        }
        return new BaseResponse("获取成功", list, total);
    }


    //批量生成待办信息
    @Override
    public BaseResponse batchSendTask(BatchhandlingTaskDto requestData) {
        String token = null;
        String year=new Date().getYear()+"";
        String tCode = requestData.gettCode();
        // String uId = requestData.();
        String teId = requestData.getTeId();
        String currentNode = requestData.getCurrentNode();
        String customerId = "All";
        if (ValidateUtil.isEmpty(tCode)) {
            return BaseResponse.fail("未传入文档模板编码");
        }
//        if (ValidateUtil.isEmpty(uId)) {
//            return BaseResponse.fail("未传入提交人id");
//        }
        if (ValidateUtil.isEmpty(teId)) {
            customerId = "All";
            //  return  BaseResponse.fail("未传入客户id");
        } else {
            customerId = teId;
        }
        List<BatchhandlingTaskListDto> qdList = requestData.getSendList();
        if (qdList != null && qdList.size() > 0) {
            //根据文档模板编码获取文档模板信息
            QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
            wrapper.eq("T_Code", tCode);
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            FileTemplate FileTemplateobj = fileTemplateService.getOne(wrapper);
            if (FileTemplateobj == null) {
                return BaseResponse.fail("未找到文档模板");
            }
            String flowid = "";
            try {
                flowid = flowService.GetEnableFlowID("", "ALL", "ALL", tCode, customerId);
                if (ValidateUtil.isEmpty(flowid)) {
                    return BaseResponse.fail("未找到启用的流程");
                }
            } catch (Exception e) {
                return BaseResponse.fail("获取流程图失败，错误信息：" + e.getMessage());
            }

            //获取流程图信息
            QueryWrapper<FlowActivity> actWrapper = new QueryWrapper<>();
            actWrapper.eq("ActFlowID", flowid);
            if (!"All".equals(customerId)) {
                actWrapper.eq("Te_Id", customerId);
            }
            List<FlowActivity> FlowActivitys = flowActivityService.list(actWrapper);
            //获取当前流程下所有的连接线
            List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
            //获取流程的第一个节点
            FlowActivity FlowActivityobj = new FlowActivity();
            if ("startNode".equals(currentNode) || "createNode".equals(currentNode) || "returnStartNode".equals(currentNode)) {
                FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                if (FlowActivityobj == null) {
                    return BaseResponse.fail("流程图设置异常，未找到开始节点");
                }
            } else {
                FlowActivityobj = flowActivityService.getById(currentNode);
                if (FlowActivityobj == null) {
                    return BaseResponse.fail("参数错误，未找到当前节点");
                }
            }


            List<String> fileIds = qdList.stream().map(n -> n.getFileId()).collect(Collectors.toList());
            List<File> fileList = fileService.getListByIds(fileIds, tCode,year);
            if (CollectionUtils.isEmpty(fileList)) {
                return BaseResponse.fail("file信息未找到");
            }
            List<Map<String, Object>> returnInfo = new ArrayList<>();
            //获取业务表信息
            List<Task> taskList = new ArrayList<>();
            List<TaskProcessin> tpList = new ArrayList<>();
            List<Map<String, Object>> businessData = new ArrayList<>();
            if (ValidateUtil.isNotEmpty(requestData.getOnlineConditions()) && "true".equals(requestData.getOnlineConditions())) {
                businessData = fileService.getData(FileTemplateobj.gettTable(), FileTemplateobj.gettPrimaryKey(), fileIds, customerId);
            }

            //获取当前流程图下的
            List<FlowActivity> nextActs = new ArrayList<>();
            List<String> actId = new ArrayList<>();
            if ("startNode".equals(currentNode) || "returnStartNode".equals(currentNode)) {
                nextActs.add(FlowActivityobj);
                actId.add(FlowActivityobj.getActid());
            } else {
                actId = flowService.GetNextTask(FlowActivityobj, FlowActivitys, FlowRules);
                if (actId.size() < 1) {
                    return BaseResponse.fail("流程图设置异常，未找到下一步审批节点");
                }
                List<String> idsAct = actId;
                nextActs = FlowActivitys.stream().filter(n -> idsAct.contains(n.getActid())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(nextActs)) {
                    return BaseResponse.fail("流程图设置异常，未找到下一步审批节点");
                }
            }

            Map<String, List<SysUser>> nextActUser = new HashMap<>();
            for (FlowActivity item : nextActs
            ) {
                nextActUser.put(item.getActid(), null);
                if (item.getActusershowtype().equals("0")) {
                    //解析表单上的项目id
                    List<userCVo> u_idlist = flowActivityService.selectUserByPostRole(actId, item, "", customerId);
                    if (!CollectionUtils.isEmpty(u_idlist)) {
                        nextActUser.put(item.getActid(), sysUserService.getUserAllByUids(u_idlist.stream().map(c -> c.getuId()).collect(Collectors.toList()), customerId));
                    }
                }
            }
            String idStr = "";
            List<TaskProcessin> tPLsit = new ArrayList<>();
            for (BatchhandlingTaskListDto item : qdList) {

                Map<String, Object> objInfo = new HashMap<>();
                objInfo.put("key", item.getFileId());
                objInfo.put("fileId", item.getFileId());
                objInfo.put("tCode", tCode);
                objInfo.put("currentNode", currentNode);
                objInfo.put("taskIds", new ArrayList<String>());
                objInfo.put("taskUsers", new ArrayList<String>());
                objInfo.put("taskCount", "0");
                objInfo.put("persisted", "false");
                try {
                    List<File> files = fileList.stream().filter(n -> n.getFileid().equals(item.getFileId())).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(files)) {
                        objInfo.put("code", "102");
                        objInfo.put("massage", "未找到file信息");
                        returnInfo.add(objInfo);
                        log.warn("batchSendTask单条跳过: tCode={}, fileId={}, code={}, message={}",
                                tCode, item.getFileId(), objInfo.get("code"), objInfo.get("message"));
                        continue;
                    }
                    File fileObj = files.get(0);

                    //过去表单条件信息
                    Map<String, Object> newConditions = new HashMap<>();
                    List<FlowActivity> FlowActivityList = new ArrayList<>();
                    if ("startNode".equals(currentNode) || "returnStartNode".equals(currentNode)) {
                        FlowActivityList.add(FlowActivityobj);
                    } else {

                        if (ValidateUtil.isNotEmpty(requestData.getOnlineConditions()) && "true".equals(requestData.getOnlineConditions())) {
                            Map<String, Object> condition = new HashMap<>();
                            condition.put("pId", fileObj.getFileproject());
                            List<Map<String, Object>> cons = businessData.stream().filter(n -> getKey(n, FileTemplateobj.gettPrimaryKey()) != null && getKey(n, FileTemplateobj.gettPrimaryKey()).toString().equals(item.getFileId())).collect(Collectors.toList());
                            if (cons != null && cons.size() > 0) {
                                Map<String, Object> dataStr = cons.get(0);
                                for (String key : dataStr.keySet()) {
                                    condition.put(toCamelCase(key), dataStr.get(key));
                                }
                            }

                            try {

                                newConditions = flowActivityService.setTorsionConditions(item.getFileId(), item.getuId(), JacksonUtils.toJSONString(condition), customerId, tCode,year);

                            } catch (Exception e) {
                                objInfo.put("code", "102");
                                objInfo.put("massage", "线上条件错误" + e.getMessage());
                            }
                        }
                        FlowActivityList = flowActivityService.GetSYSFlowActivityEntitybind(FlowActivityobj, newConditions, FlowActivitys, FlowRules, 0, customerId);
                        if (CollectionUtils.isEmpty(FlowActivityList)) {
                            objInfo.put("code", "102");
                            objInfo.put("massage", "未获取到下一步经办人");
                        }
                        //判断下一步流程是否已经结束
                        FlowActivity SYS_FlowActivityend = null;
                        try {
                            SYS_FlowActivityend = FlowActivityList.stream().filter(n -> n.getActtype().equals("endEvent")).findFirst().get();
                        } catch (Exception ex) {
                        }
                        //获取下一步是否有结束节点
                        if (SYS_FlowActivityend != null && FlowActivityList.size() > 1) {
                            objInfo.put("code", "102");
                            objInfo.put("massage", "流程设置异常：下一步存在结束节点和审批节点");

                        } else if (SYS_FlowActivityend != null) {
                            objInfo.put("code", "102");
                            objInfo.put("massage", "下一步为结束，未生成待办");
                            returnInfo.add(objInfo);
                            log.warn("batchSendTask单条跳过: tCode={}, fileId={}, code={}, message={}",
                                    tCode, item.getFileId(), objInfo.get("code"), objInfo.get("message"));
                            continue;
                        }
                    }
                    int stat = 0;
                    for (FlowActivity act : FlowActivityList
                    ) {
                        String nextUid = item.getProcessed();
                        if (ValidateUtil.isEmpty(nextUid)) {
                            List<SysUser> users = nextActUser.get(act.getActid());
                            if (!CollectionUtils.isEmpty(users)) {
                                nextUid = users.get(0).getuId();
                            }
                        }
                        if (ValidateUtil.isNotEmpty(nextUid)) {
                            FlowRule fu = FlowRules.stream().filter(n -> n.getRuleendactid().equals(act.getActid())).findFirst().get();
                            Task entity = new Task();
                            entity.setTaskid(GenerateUUID.getUUID());
                            entity.setTaskflow(act.getActflowid());
                            entity.setTaskrule(fu.getRuleid());
                            entity.setTaskentityid(item.getFileId());
                            entity.setTaskclient("");
                            entity.setTaskproject("0");
                            entity.setTaskcurrentactid(act.getActid());
                            entity.setTaskpreactid(FlowActivityobj.getActid());
                            entity.setTaskuser(nextUid);
                            entity.setTasksendtime(new Date());
                            entity.setTaskname(FileTemplateobj.gettName());
                            entity.setTasksenduser(item.getuId());
                            Date date = new Date();
                            entity.setTaskstarttime(date);
                            if (act.getActhour() != null) {
                                entity.setTaskendtime(DateUtil.DateChangeHour(date, DateUtil.TastProcessingtime(act.getActhour().toString())));
                            } else {
                                entity.setTaskendtime(date);
                            }
                            entity.setTaskread("未读");
                            entity.setTasksection(fileObj.getFilesectionid());
                            entity.setTaskcontract(fileObj.getFilecontract());
                            entity.setTaskgetflagstr("");
                            if ("returnStartNode".equals(currentNode)) {
                                entity.setTaskReturnType("start");
                            } else {
                                entity.setTaskReturnType("");
                            }
                            if (!"All".equals(customerId)) {
                                entity.setTeId(customerId);
                            }
                            taskList.add(entity);
                            idStr = idStr + "','" + item.getFileId();
                            objInfo.put("code", "100");
                            objInfo.put("massage", "成功");
                            ((List<String>) objInfo.get("taskIds")).add(entity.getTaskid());
                            ((List<String>) objInfo.get("taskUsers")).add(nextUid);
                            objInfo.put("taskCount", String.valueOf(stat + 1));
                            stat++;
                        }
                    }

                    if (ValidateUtil.isNotNull(item.getOperator())) {
                        List<TaskProcessin> tps = tPLsit.stream().filter(p -> p.getTpEntityid().equals(fileObj.getFileid()) && p.getTpUser().equals(item.getOperator())).collect(Collectors.toList());
                        if (CollectionUtils.isEmpty(tps)) {
                            //生成已办信息
                            Date date = new Date();
                            TaskProcessin his = new TaskProcessin();
                            his.setTpActid(FlowActivityobj.getActid());
                            his.setActname(FlowActivityobj.getActname());
                            his.setTpDotime(new Date());
                            his.setTpFlowid(flowid);
                            his.setTpId(GenerateUUID.getUUID());
                            his.setTpExplain(item.getOpinion());
                            his.setTpResult("同意");
                            //his.tp_ruleid = null;//起始活动的历史记录规则为空taskType
                            his.setTpEntityid(item.getFileId());
                            his.setTpProject(fileObj.getFileproject());
                            his.setTpName(FileTemplateobj.gettName());
                            his.setTpUser(item.getOperator());
                            his.setTpCountersigntitle(FlowActivityobj.getActcountersigntitle());
                            his.setTpType("提交");
                            his.setTpStarttime(new Date());
                            his.setTpEndtime(new Date());
                            his.setTpActtype(FlowActivityobj.getActtype());
                            his.setTpTaskReturnType("start");
                            his.setTpClient("");
                            if (!"All".equals(customerId)) {
                                his.setTeId(customerId);
                            }
                            his.setTpSend(item.getuId());
                            tPLsit.add(his);
                        }

                    }


                    if (stat == 0) {
                        objInfo.put("massage", "下一步未找到流程处理人");
                        objInfo.put("code", "102");
                        log.warn("batchSendTask单条跳过: tCode={}, fileId={}, code={}, message={}",
                                tCode, item.getFileId(), objInfo.get("code"), objInfo.get("message"));
                    }
                    returnInfo.add(objInfo);
                } catch (Exception ex) {
                    List<String> taskIds = (List<String>) objInfo.get("taskIds");
                    if (!CollectionUtils.isEmpty(taskIds)) {
                        objInfo.put("massage", "成功，但后续处理异常：" + ex.getMessage());
                        objInfo.put("code", "100");
                    } else {
                        objInfo.put("massage", "异常" + ex.getMessage());
                        objInfo.put("code", "500");
                    }
                    returnInfo.add(objInfo);
                    log.warn("batchSendTask单条异常: tCode={}, fileId={}, currentNode={}, taskIds={}",
                            tCode, item.getFileId(), currentNode, taskIds, ex);
                }
            }
            try {
                int num = 0;
                int num1 = 0;
                int num2 = 0;
                if (!CollectionUtils.isEmpty(tPLsit)&&(!"1".equals(requestData.getNotGenerateTask()))) {
                    taskProcessinService.saveBatch(tPLsit);
                }
                if (taskList.size() > 0) {
                    num = baseMapper.insertTask(taskList);
                    int states = 0;
                    if ("startNode".equals(currentNode)) {
                        states = ApprovalStatusEnum.Not_Started.getIndex();
                    } else if ("createNode".equals(currentNode)) {
                        states = ApprovalStatusEnum.Started.getIndex();
                    } else if ("returnStartNode".equals(currentNode)) {
                        states = ApprovalStatusEnum.Returned_Initiator.getIndex();
                    } else {
                        states = ApprovalStatusEnum.Under_Approval.getIndex();
                    }
                    if (num != taskList.size()) {
                        log.warn("batchSendTask待办插入数量不一致: tCode={}, requestSize={}, taskListSize={}, inserted={}",
                                tCode, qdList.size(), taskList.size(), num);
                    }
                    if (num > 0) {
                        Set<String> persistedTaskIds = listByIds(taskList.stream()
                                .map(Task::getTaskid)
                                .collect(Collectors.toList()))
                                .stream()
                                .map(Task::getTaskid)
                                .collect(Collectors.toSet());
                        for (Map<String, Object> itemResult : returnInfo) {
                            if (!"100".equals(String.valueOf(itemResult.get("code")))) {
                                continue;
                            }
                            List<String> taskIds = (List<String>) itemResult.get("taskIds");
                            if (!CollectionUtils.isEmpty(taskIds) && persistedTaskIds.containsAll(taskIds)) {
                                itemResult.put("persisted", "true");
                            } else {
                                log.warn("batchSendTask待办明细未完全落库: tCode={}, fileId={}, taskIds={}, persistedTaskIds={}",
                                        tCode, itemResult.get("fileId"), taskIds, persistedTaskIds);
                            }
                        }
                    }
                    if (num > 0) {
                        idStr = idStr.substring(2, idStr.length()) + "'";
                        num1 = fileService.updateFileFlowStatusByFileIds(states, idStr, flowid, tCode,year);
                        if (ValidateUtil.isNotEmpty(FileTemplateobj.gettStateTimeKey())) {
                            num2 = fileService.updateBusinessStateAndStateTimeBath(FileTemplateobj.gettTable(), FileTemplateobj.gettPrimaryKey(), FileTemplateobj.gettStateKey(), FileTemplateobj.gettStateTimeKey(), idStr, states, new Date());
                        } else {
                            num2 = fileService.updateBusinessStateBath(FileTemplateobj.gettTable(), FileTemplateobj.gettPrimaryKey(), FileTemplateobj.gettStateKey(), idStr, states);
                        }
                    }
                    log.info("batchSendTask批量生成待办完成: tCode={}, requestSize={}, taskListSize={}, inserted={}, fileSyncCount={}, businessSyncCount={}, returnInfoSize={}",
                            tCode, qdList.size(), taskList.size(), num, num1, num2, returnInfo.size());
                    return BaseResponse.ok("流程提交成功:生成" + num + "条待办；同步" + num1 + "条file信息；同步" + num2 + "条业务信息", returnInfo);
                } else {
                    return BaseResponse.fail("待办生成失败", returnInfo);
                }

            } catch (Exception ex) {
                log.warn("batchSendTask保存待办异常: tCode={}, requestSize={}, taskListSize={}",
                        tCode, qdList.size(), taskList.size(), ex);
                return BaseResponse.fail("待办保存异常：" + ex.getMessage(), returnInfo);
            }

        } else {
            return BaseResponse.fail("未传入数据");
        }


    }

    public Object getKey(Map<String, Object> obj, String key) {
        if (obj.get(key) != null) {
            return obj.get(key);
        } else if (obj.get(key.toLowerCase(Locale.ENGLISH)) != null) {
            String keyStr = key.toLowerCase(Locale.ENGLISH);
            return obj.get(keyStr);
        } else if (obj.get(key.toUpperCase(Locale.ENGLISH)) != null) {
            String keyStr = key.toUpperCase(Locale.ENGLISH);
            return obj.get(keyStr);
        } else {
            return "";
        }
    }

    public static String toCamelCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    @Override
    @Transactional
    public void sendToDoTask(Task task, String tpid, String state, String subState, String token, String customerId, String tCode,String year) {
        List<String> codeList = new ArrayList<>();
        codeList.add("senToDoUrl");//待办推送地址
        codeList.add("openSenToDo");//是否开启待办推送
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (ValidateUtil.isEmpty(senToDoUrl)) {
            Object senToDoUrlStr = smsTemplateList.get("senToDoUrl");
            if (senToDoUrlStr != null) {
                senToDoUrl = senToDoUrlStr.toString();
            }
        }

        Object openSenToDo = smsTemplateList.get("openSenToDo");
        //开启待办推送
        if (task != null && !ValidateUtil.isEmpty(openSenToDo) && openSenToDo.toString().equals("1")) {
            try {
                List<String> uid = new ArrayList<>();
                uid.add(task.getTaskuser());
                uid.add(task.getTasksenduser());
                File fileObj = fileService.getFileById(task.getTaskentityid(), tCode,year);
                if (fileObj != null) {
                    uid.add(fileObj.getFilecreateuser());
                }
                List<SysUser> ulist = sysUserService.listByIds(uid);
                //配置了推送待办地址，开始推送
                AgencyModel obj = new AgencyModel();
                obj.setTaskId(task.getTaskid());
                obj.setTitle(task.getTaskname());

                obj.setTpId(tpid);
                if (!"-1".equals(subState)) {
                    obj.setSubState(subState);
                }
                List<SysUser> sedusers = ulist.stream().filter(n -> n.getuId().equals(task.getTasksenduser())).collect(Collectors.toList());
                if (sedusers.size() > 0) {
                    obj.setThirdSenderId(sedusers.get(0).getuAccount());
                    obj.setSenderName(sedusers.get(0).getuName());
                    obj.setNoneBindingSender(sedusers.get(0).getuAccount());
                }
                List<SysUser> taskusers = ulist.stream().filter(n -> n.getuId().equals(task.getTaskuser())).collect(Collectors.toList());
                if (sedusers.size() > 0) {
                    obj.setNoneBindingReceiver(taskusers.get(0).getuAccount());
                    obj.setThirdReceiverId(taskusers.get(0).getuAccount());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                obj.setCreationDate(sdf.format(task.getTasksendtime()));
                obj.setState(state);
                obj.setEntityId(task.getTaskentityid());
                try {
                    if (fileObj != null) {
                        List<SysUser> careatUObj = ulist.stream().filter(n -> n.getuId() != null && n.getuId().equals(fileObj.getFilecreateuser())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(careatUObj)) {
                            if (careatUObj.get(0) != null)
                                obj.setFileCreator(careatUObj.get(0).getuAccount());
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                //获取流程步骤名称
                FlowActivity actionObj = flowActivityService.getById(task.getTaskcurrentactid());
                if (actionObj != null) {
                    obj.setActName(actionObj.getActname());
                }
                //获取流程名称
                Flow flowObj = flowService.getById(task.getTaskflow());
                if (flowObj != null) {
                    obj.setFlowName(flowObj.getFlowname());
                }
                log.info("开始执行线程内容");
                if (!ValidateUtil.isEmpty(senToDoUrl)) {
                    try {
                        sendtodo runnable01 = new sendtodo();
                        runnable01.setUrl(senToDoUrl);
                        runnable01.setRestTemplate(restTemplate);
                        runnable01.setObj(obj);
                        runnable01.setToken(token);
                        runnable01.setTaskPushCallRecordService(taskPushCallRecordService);
                        runnable01.start();
                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                        taskPushCallRecordService.createTaskPushCallRecord(JacksonUtils.toJSONString(obj), "0", "");
                    }
                } else {
                    taskPushCallRecordService.createTaskPushCallRecord(JacksonUtils.toJSONString(obj), "0", "");
                }
            } catch (Exception ex) {
                taskPushCallRecordService.createTaskPushCallRecord(ex.getMessage() + "【" + JacksonUtils.toJSONString(task) + "】", "0", "");
            }
        }
    }

    @Override
    public void generateReminder(String customerId) {
        // String customerId = "All";
        List<String> codeList = new ArrayList<>();
        codeList.add("taskToMassageSet");
        codeList.add("systemName");
        codeList.add("systemNameShort");
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        //String massageTem = "";
        String systemName = "";
        String systemNameShort = "";
        List<String> pNameList = new ArrayList<>();
        if (smsTemplateList != null) {
            if (smsTemplateList.get("taskToMassageSet") != null) {
                Object obj = JacksonUtils.parseObject(smsTemplateList.get("taskToMassageSet") + "", new TypeReference<Map<String, String>>() {
                });
                Map<String, Object> sns = (Map<String, Object>) obj;
//                if (sns.get("massageTem") != null) {
//                    massageTem = sns.get("massageTem").toString();
//                }
                if (sns.get("pNames") != null) {
                    String pNames = sns.get("pNames").toString();
                    if (pNames.contains(",")) {
                        pNameList = Arrays.asList(pNames.split(","));
                    } else {
                        pNameList.add(pNames);
                    }

                }
            }
            if (smsTemplateList.get("systemName") != null) {
                systemName = smsTemplateList.get("systemName").toString();
            }
            if (smsTemplateList.get("systemNameShort") != null) {
                systemNameShort = smsTemplateList.get("systemNameShort").toString();
            }
        }
        if (ValidateUtil.isNotEmpty(pNameList)) {
            //获取需要未生成超时的，可能会生成超时的待办信息
            //获取基础配置信息
            List<TaskResponse> task = baseMapper.getNeedMassageTask(DateUtil.DateToString(DateUtil.DateChangeDay(new Date(), -1), "yyyy-MM-dd"));
            log.error("【待办超时预警】获取任务数量：" + task.size());
            List<TaskResponse> list = getTaskListRemainder(task, new Date(), customerId);
            if (!CollectionUtils.isEmpty(task)) {
                log.error("【待办超时预警】过滤需要生成预警的待办数量");
                list = list.stream().filter(n -> n.getRemainder().compareTo(BigDecimal.valueOf(-48)) == -1).collect(Collectors.toList());
                log.error("【待办超时预警】过滤需要生成预警的待办数量：" + list.size());
                if (!CollectionUtils.isEmpty(list)) {
                    //需要生成抄送的用户信息
                    List<String> uIds = list.stream().map(TaskResponse::getTaskuser).collect(Collectors.toList());
                    List<String> pIds = list.stream().map(TaskResponse::getFileproject).collect(Collectors.toList());
                    Map<String, Object> params = new HashMap<>();
                    params.put("userIds", deduplicate(uIds));
                    params.put("pIds", deduplicate(pIds));
                    params.put("pNames", pNameList);
                    List<Map> ss = sysUserService.getUidByUidInfo(params);
                    log.error("【待办超时预警】根据待办人员获取“" + String.join(",", pNameList) + "”数量：" + ss.size());
                    List<SysMessage> massageList = new ArrayList<>();
                    List<SysMessageReceive> massageRList = new ArrayList<>();
                    List<sysTaskMassage> taskList = new ArrayList<>();

                    if (!CollectionUtils.isEmpty(ss)) {
                        //获取用户的所属项目组织机构的项目经理信息
                        List<String> uids = new ArrayList<>();
                        for (Map u : ss) {
                            String uId = (String) getKey(u, "U_ID");
                            if (uids.contains(uId)) {
                                continue;
                            }
                            uids.add(uId);
                            String pId = (String) getKey(u, "P_ID");
                            String pPName = (String) getKey(u, "PP_Name");
                            if (ValidateUtil.isNotEmpty(uId) && ValidateUtil.isNotEmpty(pId) && ValidateUtil.isNotEmpty(pPName) && pNameList.contains(pPName)) {
                                log.error("【待办超时预警】uId：" + uId);
                                List<TaskResponse> tasks = list.stream().filter(n -> pId.equals(n.getFileproject())).collect(Collectors.toList());
                                log.error("【待办超时预警】uId：" + uId + ";task:" + tasks.size());
                                if (!CollectionUtils.isEmpty(tasks)) {
                                    for (TaskResponse task1 : tasks) {
                                        //生成消息通知信息
//                                        SysMessage n = setMassageTem(massageTem, task1, systemName, systemNameShort, uId);
//                                        massageList.add(n);
//                                        SysMessageReceive newObj = new SysMessageReceive();
//                                        newObj.setId(GenerateUUID.getUUID());
//                                        newObj.setSmId(n.getId());
//                                        newObj.setSmrUser(n.getSmSendUser());
//                                        newObj.setSmrSendStatus("1");
//                                        newObj.setSmrSendTime(n.getCreateTime());
//                                        massageRList.add(newObj);
                                        sysTaskMassage obj = new sysTaskMassage();
                                        obj.setId(GenerateUUID.getUUID());
                                        obj.setTaskId(task1.getTaskid());
                                        obj.setUIds(uId);
                                        taskList.add(obj);
                                    }
                                } else {
                                    log.error("超时生成平台消息：uId:" + uId + "；pId" + pId + "（未获取到超时待办信息，不生成平台消息）");
                                }
                            }
                        }

                    }

//                    if (!CollectionUtils.isEmpty(massageRList)) {
//                        sysMessageService.saveBatch(massageList);
//                    }
//                    if (!CollectionUtils.isEmpty(massageRList)) {
//                        sysMessageReceiveService.saveBatch(massageRList);
//                    }
                    if (!CollectionUtils.isEmpty(taskList)) {
                        log.error("【待办超时预警】：需要保存的待办数量" + taskList.size());
                        try {
                            if (taskList.size() <= 60) {
                                log.error("【待办超时预警】：数量小于60，直接保存");
                                saveTaskMassage(taskList);
                                log.error("【待办超时预警】：数量小于60，直接保存完成");
                            } else {
                                List<List<sysTaskMassage>> listObj = groupList(taskList, 60);
                                Integer i = 0;
                                for (List<sysTaskMassage> li : listObj) {
                                    i++;
                                    log.error("【待办超时预警】：第“" + i + "”轮" + li.size() + "条开始保存");
                                    try {
                                        saveTaskMassage(li);
                                        log.error("【待办超时预警】：第“" + i + "”轮" + li.size() + "条保存完成");
                                    } catch (Exception ex) {
                                        log.error("【待办超时预警】：第“" + i + "”轮" + li.size() + "条开始保存,保存结果失败");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(e.getMessage(), e);
                        }

                    }

                    sysTaskMassageService.deleteUselessData();

                }
            }
        } else {
            log.error("超时生成平台消息：未设置转换配置");
        }
    }

    public Boolean saveTaskMassage(List<sysTaskMassage> taskList) {
        Boolean flag = false;
        String typeStr = "2";
        if ("1".equals(typeStr)) {
            sysTaskMassageService.insertSysTaskMassage(taskList);
        } else if ("2".equals(typeStr)) {
            try {
                flag = sysTaskMassageService.saveBatch(taskList);
                log.error(taskList.size() + "条保存完成:" + flag);
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        } else if ("3".equals(typeStr)) {
            for (sysTaskMassage item : taskList) {
                try {
                    Boolean t = sysTaskMassageService.save(item);
                    log.error("1条保存完成");
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }

            }
        }

        return flag;

    }

    public <T> List<List<T>> groupList(List<T> list, int groupSize) {
        List<List<T>> groupedLists = new ArrayList<>();
        int index = 0;
        while (index < list.size()) {
            int endIndex = Math.min(index + groupSize, list.size());
            List<T> group = list.subList(index, endIndex);
            groupedLists.add(group);
            index += groupSize;
        }
        return groupedLists;
    }


    public static List<String> deduplicate(List<String> list) {
        Set<String> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }

    public SysMessage setMassageTem(String massageTem, TaskResponse tr, String systemName, String systemNameShort, String uId) {

        if (massageTem.contains("{systemName}")) {
            massageTem = massageTem.replace("{systemName}", systemName);
        }
        if (massageTem.contains("{systemNameShort}")) {
            massageTem = massageTem.replace("{systemNameShort}", systemNameShort);
        }
        if (massageTem.contains("{conName}")) {
            massageTem = massageTem.replace("{conName}", tr.getConName());
        }
        if (massageTem.contains("{pName}")) {
            massageTem = massageTem.replace("{pName}", tr.getpName());
        }
        if (massageTem.contains("{secName}")) {
            massageTem = massageTem.replace("{secName}", tr.getSecName());
        }
        if (massageTem.contains("{taskname}")) {
            massageTem = massageTem.replace("{taskname}", tr.getTaskname());
        }
        if (massageTem.contains("{taskUserName}")) {
            massageTem = massageTem.replace("{taskUserName}", tr.getTaskUserName());
        }
        if (massageTem.contains("{等待时间}")) {
            massageTem = massageTem.replace("{等待时间}", tr.getRemainder() + "");
        }
        SysMessage obj = new SysMessage();
        obj.setId(GenerateUUID.getUUID());
        obj.setSmContent(massageTem);
        obj.setSmtCode("taskToMassage");
        obj.setSmTitle("流程审批消息通知");
        obj.setCreateTime(new Date());
        obj.setSmSendUser(uId);
        obj.setSmType("01");
        return obj;
    }


    public static class sendtodo extends Thread {
        private RestTemplate restTemplate;
        private String senToDoUrl;
        private TaskPushCallRecordService taskPushCallRecordService;

        public void setRestTemplate(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public void setUrl(String url) {
            this.senToDoUrl = url;
        }

        public void setTaskPushCallRecordService(TaskPushCallRecordService taskPushCallRecordService) {
            this.taskPushCallRecordService = taskPushCallRecordService;
        }

        private AgencyModel obj;
        private String token;

        public void setObj(AgencyModel obj) {
            this.obj = obj;
        }

        ;

        public void setToken(String token) {
            this.token = token;
        }

        ;

        @Override
        public void run() {
            try {
                log.info("开始执行线程内容");
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                headers.add("token", token);
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(obj), headers);
                BaseResponse baseResponse = restTemplate.postForObject(senToDoUrl, formEntity, BaseResponse.class);
                taskPushCallRecordService.createTaskPushCallRecord(JacksonUtils.toJSONString(obj), baseResponse.getSuccess() == true ? "1" : "0", "");
                log.info("返回参数：" + JacksonUtils.toJSONString(baseResponse));
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                taskPushCallRecordService.createTaskPushCallRecord(JacksonUtils.toJSONString(obj), "0", "");
            }
        }
    }

    @Override
    public BaseResponse getUserTodoList(String uId, SysFileRequest request, String customerId) {
        List<Integer> states = Arrays.asList(ApprovalStatusEnum.Not_Started.getIndex());
        QueryMap<SysFileRequest> map = new QueryMap<>(request);
        map.put("pName").put("filetitle").put("filecode").put("tCode").put("conName").put("taskNames").put("fileflowstatuses")
                .put("pNames").put("fileCreateDate").put(request.getFileCreateDates(), "fileCreateDateStart", "fileCreateDateEnd")
                .put("startTime").put("endTime").put("conNames").put("secName").put("secNames");
        Page<SysFileResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SysFileResponse> list = baseMapper.getUserTodoList(page, uId, customerId, map.getQuery(), states);
        List<Map<String, Object>> keys = new ArrayList<>();
        Map<String, Object> obj = new HashMap<>();
        obj.put("keys", baseMapper.getUserTodoListTaskKey(map.getQuery(), uId, customerId, "mn.FileTitle", states));
        obj.put("name", "功能名称");
        obj.put("code", "taskNames");
        obj.put("type", "string");
        keys.add(obj);
        Map<String, Object> obj2 = new HashMap<>();
        obj2.put("keys", baseMapper.getUserTodoListTaskKey(map.getQuery(), uId, customerId, "pp.P_Name", states));
        obj2.put("name", "所属项目");
        obj2.put("code", "pNames");
        obj2.put("type", "string");
        keys.add(obj2);
        // Map<String, Object> obj3 = new HashMap<>();
        // obj3.put("keys", baseMapper.getUserTodoListTaskKey(map.getQuery(), uId, customerId, "mn.FileFlowStatus", states));
        // obj3.put("name", "流程状态");
        // obj3.put("code", "fileflowstatuses");
        // obj3.put("type", "dictionary");
        // obj3.put("dictionary", "JPMsoft_LCZT");
        // keys.add(obj3);
        Map<String, Object> obj4 = new HashMap<>();
        obj4.put("keys", "");
        obj4.put("name", "创建时间");
        obj4.put("code", "fileCreateDates");
        obj4.put("type", "data");
        keys.add(obj4);
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("list", list);
        returnData.put("keys", keys);
        return BaseResponse.ok(returnData, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getUserTodoCount(String uId, String customerId) {
        return BaseResponse.ok(baseMapper.getUserTodoCount(uId, customerId, Arrays.asList(ApprovalStatusEnum.Not_Started.getIndex())));
    }

    /**
     * 获取没有数据的流程，用于维护
     *
     * @param state
     * @param voidData
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse getNoDataFlow(Integer state, Boolean voidData) {
        // 查询全部的流程
        List<File> list = fileService.list(Wrappers.<File>lambdaQuery()
                .ne(File::getFileflowstatus, ApprovalStatusEnum.Invalid.getIndex())
                .eq(File::getFileflowstatus, state)
                .isNotNull(File::gettCode)
        );
        // 按照tCode分组
        Map<String, List<File>> map = list.stream().collect(Collectors.groupingBy(File::gettCode));
        // 返回体
        List<SysFileResponse> returnList = new ArrayList<>();
        // 分别处理每个tCode的流程
        for (String tCode : map.keySet()) {
            if (ValidateUtil.isEmpty(tCode)) {
                continue;
            }
            List<File> files = map.get(tCode);
            // 获取流程模板
            FileTemplate fileTemplate = fileTemplateService.getOne(Wrappers.<FileTemplate>lambdaQuery()
                    .eq(FileTemplate::gettCode, tCode)
                    .last(" limit 1 ")
            );
            if (ValidateUtil.isNotNull(fileTemplate) && ValidateUtil.isNotEmpty(files)) {
                // 获取流程的表信息
                String tableName = fileTemplate.gettTable();
                String primaryKey = fileTemplate.gettPrimaryKey();
                if (ValidateUtil.isEmpty(tableName) || ValidateUtil.isEmpty(primaryKey)) {
                    log.warn("流程模板【{}】的表信息不完整，tCode:{};tableName:{};primaryKey:{}", fileTemplate.gettName(), tCode, tableName, primaryKey);
                    continue;
                }
                Set<String> fileIds = files.stream().map(File::getFileid).collect(Collectors.toSet());
                // 返回存在的id
                List<String> existIds;
                try {
                    existIds = baseMapper.getExistFileIds(tableName, primaryKey, fileIds);
                } catch (Exception e) {
                    log.error("查询流程数据异常，tCode:{};tableName:{};primaryKey:{}", tCode, tableName, primaryKey, e);
                    continue;
                }
                // 过滤出不存在的id
                List<String> noDataIds = fileIds.stream().filter(id -> !existIds.contains(id)).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(noDataIds)) {
                    // 处理结果
                    List<File> collect = files.stream().filter(file -> noDataIds.contains(file.getFileid())).collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(collect)) {
                        collect.forEach(file -> {
                            SysFileResponse obj = new SysFileResponse();
                            obj.setFileid(file.getFileid());
                            obj.settId(file.gettId());
                            obj.setFiletitle(file.getFiletitle());
                            obj.setFilecode(file.getFilecode());
                            obj.setFilekeyword(file.getFilekeyword());
                            obj.setFileemergencylevel(file.getFileemergencylevel());
                            obj.setFiledescription(file.getFiledescription());
                            obj.setFileflowstatus(file.getFileflowstatus());
                            obj.setFilecreatedate(file.getFilecreatedate());
                            obj.setFilecreateUser(file.getFilecreateuser());
                            obj.setFilecreateusername(file.getFilecreateusername());
                            obj.setFilecreatecompanyid(file.getFilecreatecompanyid());
                            obj.setFileproject(file.getFileproject());
                            obj.setFilesectionid(file.getFilesectionid());
                            obj.setFilecontract(file.getFilecontract());
                            obj.settName(fileTemplate.gettName());
                            obj.settCode(file.gettCode());
                            obj.setFlowId(file.getFlowId());
                            returnList.add(obj);
                        });
                    }
                }
            }
        }
        // 如果传入voidData为true，则作废这些数据
        if (ValidateUtil.isNotNull(voidData) && voidData && ValidateUtil.isNotEmpty(returnList)) {
            List<String> fileIds = returnList.stream().map(SysFileResponse::getFileid).collect(Collectors.toList());
            // 作废
            fileService.update(Wrappers.<File>lambdaUpdate()
                    .set(File::getFileflowstatus, ApprovalStatusEnum.Invalid.getIndex())
                    .in(File::getFileid, fileIds)
            );
            // 删除待办
            this.remove(Wrappers.<Task>lambdaQuery()
                    .in(Task::getTaskentityid, fileIds)
            );
            log.info("作废数据id：{}", String.join(",", fileIds));
        }
        return BaseResponse.ok(returnList, returnList.size());
    }
}
