package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysCoordinationTaskRequest;
import com.jpmsoft.common.model.request.TaskRequest;
import com.jpmsoft.common.model.request.UserCoordinationTaskRequest;
import com.jpmsoft.common.model.response.CoordinationTaskResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.jpbpm.mapper.SysCoordinationTaskMapper;
import com.jpmsoft.jpbpm.pojo.dto.*;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jpmsoft.common.utils.ValidateUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysCoordinationTaskImpl extends ServiceImpl<SysCoordinationTaskMapper, SysCoordinationTask> implements SysCoordinationTaskService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private FlowActivityService flowActivityService;
    @Autowired
    private SysCoordinationProcessService sysCoordinationProcessService;
    @Autowired
    private CarbonCopyService carbonCopyService;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    private FileService fileService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse initiateCommunication(InitiateCommunicationDto request,String customerId,String token) {
        if (request != null) {
            List<userselectionDto> link_user = request.getNextuser();//下一步流程操作人
            String taskid = request.getTaskid();//任务id
            String explain = request.getExplain();//意见
            String user = request.getUser_id();//当前用户
            String cname = request.getTaskname();//抄送任名称
            List<String> ccuser = request.getCcuser();//抄送用户
            String fileid = request.getFileid();//流程实例id
            Task taskobjen = taskService.getById(taskid);
            File fileObj=fileService.getFileById(fileid,request.gettCode(),request.getYear());
            String yearStr = request.getYear();
            if (fileObj != null&&fileObj.getFilecreatedate() != null) {
                yearStr=fileObj.getFilecreatedate().getYear()+"";
            }
            if(taskobjen ==null){
                //获取当前用户最近的一次审批信息

                QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
                wrapper.eq("tp_entityid", fileid);
                wrapper.eq("tp_acttype", "userTask");
                wrapper.eq("tp_type", "提交");
                if(!"All".equals(customerId) ){
                    wrapper.eq("Te_Id", customerId);
                }
                wrapper.orderByDesc("tp_dotime");
                List<TaskProcessin> TaskProcessin = taskProcessinService.getList(wrapper,request.gettCode(),yearStr);
                if(TaskProcessin.size()>0){
                    taskobjen=new Task();
                    taskobjen.setTaskcurrentactid(TaskProcessin.get(0).getTpActid());
                    taskobjen.setTaskentityid(fileid);
                    taskobjen.setTaskname(TaskProcessin.get(0).getTpName());
                    taskobjen.setTaskflow(fileObj.getFlowId());
                    taskobjen.setTaskid(TaskProcessin.get(0).getTpTaskid());
                }
            }
            if (taskobjen != null) {
                if (link_user.size() > 0) {
                    FlowActivity FlowActivityobj = flowActivityService.getById(taskobjen.getTaskcurrentactid());
                    String sfhq = "否";
                    if (link_user.size() > 1)
                        sfhq = "是";
                    List<SysCoordinationTask> models = new ArrayList<>();
                    for (userselectionDto item : link_user) {
                        SysCoordinationTask entity = new SysCoordinationTask();
                        entity.setCtId(GenerateUUID.getUUID());
                        entity.setCtActid(taskobjen.getTaskcurrentactid());
                        entity.setCtActname(FlowActivityobj.getActname());
                        entity.setCtCountersign(sfhq);
                        entity.setCtFileid(taskobjen.getTaskentityid());
                        entity.setCtFlowid(taskobjen.getTaskflow());
                        entity.setCtName(taskobjen.getTaskname());
                        entity.setCtSenduser(user);
                        entity.setCtTaskid(taskobjen.getTaskid());
                        entity.setCtTime(new Date());
                        entity.setCtUser(item.getUserid());
                        if (!"All".equals(customerId)) {
                            entity.setTeId(customerId);
                        }
                        models.add(entity);
                    }
                    this.saveBatch(models);
                    taskService.updateTaskCoordinationByTaskId("沟通中", taskobjen.getTaskid());
                    SysCoordinationProcess entity = new SysCoordinationProcess();
                    entity.setCpId(GenerateUUID.getUUID());
                    entity.setCpName(cname);
                    entity.setCpUser(user);
                    entity.setCpActid(taskobjen.getTaskcurrentactid());
                    entity.setCpExplain(explain);
                    entity.setCpFileid(taskobjen.getTaskentityid());
                    entity.setCpFlowid(taskobjen.getTaskflow());
                    entity.setCpSendtime(new Date());
                    entity.setCpSenduser(String.join(",", link_user.stream().map(n -> n.getUserid()).collect(Collectors.toList())));
                    entity.setCpTaskid(taskobjen.getTaskid());
                    entity.setCpTime(new Date());
                    if (!"All".equals(customerId)) {
                        entity.setTeId(customerId);
                    }
                    sysCoordinationProcessService.save(entity);
                    carbonCopyService.GetCarbonCopyList(FlowActivityobj, user, taskobjen.getTaskentityid(), taskobjen.getTaskname(), taskobjen.getTaskid(), ccuser,"0",customerId,token,fileObj.getFileproject(),request.gettCode(),yearStr);
                    return new BaseResponse(true, "沟通任务发起成功!", entity.getCpId());
                } else
                    return new BaseResponse("请选择沟通人员!");
            } else
                return new BaseResponse("获取任务信息失败!");
        } else
            return new BaseResponse("参数丢失!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse submitCommunicate(SubmitCommunicateDto request,String customerId) {
        if (request != null) {
            String taskid = request.getTaskid();//任务id
            String explain = request.getExplain();//意见
            String user = request.getUser_id();//当前用户
            String cname = request.getTaskname();//抄送任名称

            SysCoordinationTask objCoordinationTask = baseMapper.selectById(taskid);
            if (objCoordinationTask != null) {
                Task taskobjs = taskService.getById(objCoordinationTask.getCtTaskid());
                QueryWrapper<SysCoordinationTask> wrapper = new QueryWrapper<>();
                wrapper.eq("CT_TaskID", objCoordinationTask.getCtTaskid());
                if(!"All".equals(customerId) ){
                    wrapper.eq("Te_Id", customerId);
                }
                List<SysCoordinationTask> CoordinationTasklist = baseMapper.selectList(wrapper);
                if (CoordinationTasklist.size() == 1&&taskobjs!=null) {
                    //沟通结束
                    taskService.updateTaskCoordinationByTaskId("", taskobjs.getTaskid());
                }

                SysCoordinationProcess entity = new SysCoordinationProcess();
                entity.setCpId(GenerateUUID.getUUID());
                entity.setCpName(objCoordinationTask.getCtName());
                entity.setCpUser(objCoordinationTask.getCtUser());
                entity.setCpActid(objCoordinationTask.getCtActid());
                entity.setCpExplain(explain);
                entity.setCpFileid(objCoordinationTask.getCtFileid());
                entity.setCpFlowid(objCoordinationTask.getCtFlowid());
                entity.setCpSendtime(objCoordinationTask.getCtTime());
                entity.setCpSenduser(objCoordinationTask.getCtSenduser());
                entity.setCpTaskid(objCoordinationTask.getCtTaskid());
                entity.setCpTime(new Date());
                if (!"All".equals(customerId)) {
                    entity.setTeId(customerId);
                }
                sysCoordinationProcessService.save(entity);
                baseMapper.deleteById(objCoordinationTask.getCtId());
                return new BaseResponse(true, "提交成功！", entity.getCpId());
            } else
                return new BaseResponse("获取沟通任务错误！");

        } else
            return new BaseResponse("参数丢失！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse stopCommunicating(StopCommunicatingDto request,String customerId) {
        if (request != null) {
            String taskid = request.getTaskid();//任务id
            String explain = request.getExplain();//意见
            String user = request.getUser_id();//当前用户
            String cname = request.getTaskname();//抄送任名称
            QueryWrapper<SysCoordinationTask> wrapper = new QueryWrapper<>();
            wrapper.eq("CT_TaskID", taskid);
            wrapper.eq("CT_User", user);
            if(!"All".equals(customerId) ){
                wrapper.eq("Te_Id", customerId);
            }
            List<SysCoordinationTask> CoordinationTasklist = baseMapper.selectList(wrapper);
            if(CoordinationTasklist.size()>0){
                baseMapper.delete(wrapper);
                taskService.updateTaskCoordinationByTaskId("", taskid);
                SysCoordinationProcess entity = new SysCoordinationProcess();
                entity.setCpId(GenerateUUID.getUUID());
                entity.setCpName(CoordinationTasklist.get(0).getCtName());
                entity.setCpUser(user);
                entity.setCpActid(CoordinationTasklist.get(0).getCtActid());
                entity.setCpExplain(explain);
                entity.setCpFileid(CoordinationTasklist.get(0).getCtFileid());
                entity.setCpFlowid(CoordinationTasklist.get(0).getCtFlowid());
                entity.setCpSendtime(null);
                entity.setCpSenduser("");
                entity.setCpTaskid(CoordinationTasklist.get(0).getCtTaskid());
                entity.setCpTime(new Date());
                if (!"All".equals(customerId)) {
                    entity.setTeId(customerId);
                }
                sysCoordinationProcessService.save(entity);
                return new BaseResponse(true, "沟通停止成功!", entity.getCpId());
            }else{
                return new BaseResponse("获取沟通任务信息失败！");
            }
        } else
            return new BaseResponse("参数丢失！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse submitCommunicatecom(InitiateCommunicationDto request,String customerId) {
        if (request != null) {
            List<userselectionDto> link_user = request.getNextuser();//下一步流程操作人
            String taskid = request.getTaskid();//任务id
            String explain = request.getExplain();//意见
            String user = request.getUser_id();//当前用户
            String cname = request.getTaskname();//抄送任名称
            List<String> ccuser = request.getCcuser();//抄送用户
            String fileid = request.getFileid();//流程实例id
            QueryWrapper<SysCoordinationTask> wrapper = new QueryWrapper<>();
            wrapper.eq("CT_TaskID", taskid);
            wrapper.eq("CT_User", user);
            if(!"All".equals(customerId) ){
                wrapper.eq("Te_Id", customerId);
            }
            List<SysCoordinationTask> CoordinationTasklist = baseMapper.selectList(wrapper);
            if(CoordinationTasklist.size()>0){
                if (link_user.size() > 0) {
                    FlowActivity FlowActivityobj = flowActivityService.getById(CoordinationTasklist.get(0).getCtActname());
                    String sfhq = "否";
                    if (link_user.size() > 1)
                        sfhq = "是";
                    List<SysCoordinationTask> models = new ArrayList<>();
                    for (userselectionDto item : link_user) {
                        SysCoordinationTask entity = new SysCoordinationTask();
                        entity.setCtId(GenerateUUID.getUUID());
                        entity.setCtActid(CoordinationTasklist.get(0).getCtActid());
                        entity.setCtActname(FlowActivityobj.getActname());
                        entity.setCtCountersign(sfhq);
                        entity.setCtFileid(CoordinationTasklist.get(0).getCtFileid());
                        entity.setCtFlowid(CoordinationTasklist.get(0).getCtFlowid());
                        entity.setCtName(cname);
                        entity.setCtSenduser(user);
                        entity.setCtTaskid(CoordinationTasklist.get(0).getCtTaskid());
                        entity.setCtTime(new Date());
                        entity.setCtUser(item.getUserid());
                        if (!"All".equals(customerId)) {
                            entity.setTeId(customerId);
                        }
                        models.add(entity);
                    }
                    this.saveBatch(models);
                    taskService.updateTaskCoordinationByTaskId("沟通中", CoordinationTasklist.get(0).getCtTaskid());
                    SysCoordinationProcess entity = new SysCoordinationProcess();
                    entity.setCpId(GenerateUUID.getUUID());
                    entity.setCpName(cname);
                    entity.setCpUser(user);
                    entity.setCpActid(CoordinationTasklist.get(0).getCtActid());
                    entity.setCpExplain(explain);
                    entity.setCpFileid(CoordinationTasklist.get(0).getCtFileid());
                    entity.setCpFlowid(CoordinationTasklist.get(0).getCtFlowid());
                    entity.setCpSendtime(new Date());
                    entity.setCpSenduser(String.join(",", ccuser));
                    entity.setCpTaskid(CoordinationTasklist.get(0).getCtTaskid());
                    entity.setCpTime(new Date());
                    if (!"All".equals(customerId)) {
                        entity.setTeId(customerId);
                    }
                    sysCoordinationProcessService.save(entity);
                    baseMapper.deleteById(request.getCom_id());
                    return new BaseResponse(true, "沟通任务发起成功!", entity.getCpId());
                }else
                    return new BaseResponse("请选择沟通人员!");
            }else{
                return new BaseResponse("获取沟通任务信息失败！");
            }
        } else
            return new BaseResponse("参数丢失!");
    }


    /**
     * 沟通列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse tobereadinglist(TaskRequest request) {
        return new BaseResponse();
    }

    @Override
    public BaseResponse getUserCoordinationTaskList(String userId, UserCoordinationTaskRequest request,String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "CT_Time");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "DESC");

        params.put("ctName", !ValidateUtil.isEmpty(request.getCtName()) ? request.getCtName() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        params.put("ctSendUser", !ValidateUtil.isEmpty(request.getCtSendUser()) ? request.getCtSendUser() : null);
        params.put("teId", "All".equals(customerId)?null:customerId);
        List<CoordinationTaskResponse> datas = baseMapper.getUserCoordinationTaskList(params);
        Integer total = baseMapper.getUserCoordinationTaskListCount(params);
        return new BaseResponse("获取成功", datas, total);

    }

    @Override
    public Integer getUserCoordinationTaskListCount(String userId,String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("teId", "All".equals(customerId)?null:customerId);
        Integer total = baseMapper.getUserCoordinationTaskListCount(params);
        return total;
    }

    @Override
    public BaseResponse getCoordinationTaskList(SysCoordinationTaskRequest request,String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "CT_Time");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "DESC");

        params.put("ctName", !ValidateUtil.isEmpty(request.getCtName()) ? request.getCtName() : null);
        params.put("fileId", !ValidateUtil.isEmpty(request.getFileId()) ? request.getFileId() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        params.put("ctUser", !ValidateUtil.isEmpty(request.getCtUser()) ? request.getCtUser() : null);
        params.put("ctSendUser", !ValidateUtil.isEmpty(request.getCtSendUser()) ? request.getCtSendUser() : null);
        params.put("teId", "All".equals(customerId)?null:customerId);
        List<CoordinationTaskResponse> datas = baseMapper.getCoordinationTaskList(params);
        Integer total = baseMapper.getCoordinationTaskListCount(params);
        return new BaseResponse("获取成功", datas, total);
    }

    @Override
    public BaseResponse getUserToDoSummary(String userId,String customerId) {
        Map<String, Integer> data = new HashMap<>();
        data.put("carbonCopyCount", carbonCopyService.getUserCarbonCopyCount(userId,customerId));
        data.put("taskCount", taskService.getUserTaskListCount(userId,customerId));
        data.put("coordinationTaskCount", getUserCoordinationTaskListCount(userId,customerId));
        return new BaseResponse(true, "获取成功！", data);
    }

    @Override
    public List<AgencyStatisticsVo> statisticsUserAgency(AgencyStatisticsDto request,String customerId) {
        List<AgencyStatisticsVo> data=baseMapper.statisticsUserAgency(request,customerId);
        return data;
    }


    @Override
    public void deleteByTaskEntityIDAndTaskFlowAndTaskSendUser(String taskEntityId, String taskFlow, String taskSendUser) {
        baseMapper.deleteByTaskEntityIDAndTaskFlowAndTaskSendUser(taskEntityId,taskFlow,taskSendUser);
    }


    @Override
    public  void  deleteByEntityIdAndCurrentActId(String entityId,String actId ,String taskId,String customerId){
        baseMapper.deleteByEntityIdAndCurrentActId(entityId,actId,taskId);
    }


    @Override
    public  void deleteByEntityId(String entityId,String customerId){
        QueryWrapper<SysCoordinationTask> wrapper = new QueryWrapper<>();
        wrapper.in("CT_FileID", entityId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        baseMapper.delete(wrapper);
    }


    @Override
    public  void deleteByTaskId(String taskId,String customerId){
        QueryWrapper<SysCoordinationTask> wrapper = new QueryWrapper<>();
        wrapper.in("CT_TaskID", taskId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteByIdOrTaskEntityID(String taskId, String TaskEntityID,String customerId){
        baseMapper.deleteByIdOrTaskEntityID(taskId,TaskEntityID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public  BaseResponse deleteById(String  id,String customerId){
        SysCoordinationTask   obj=baseMapper.selectById(id);
        if(obj!=null){
            String  taskId=obj.getCtTaskid();
            QueryWrapper<SysCoordinationTask> wrapper = new QueryWrapper<>();
            wrapper.in("CT_TaskID", taskId);
            wrapper.ne("CT_ID",id);
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            List<SysCoordinationTask>  cList=baseMapper.selectList(wrapper);
            if(cList.size()==0){
               taskService.updateTaskCoordinationByTaskId("",taskId);
            }
        }
        baseMapper.deleteById(id);
        return new BaseResponse(true, "数据删除成功!");
    }
}
