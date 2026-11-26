package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.TaskRequest;
import com.jpmsoft.common.model.request.UserTaskProcessinRequest;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.response.TaskProcessResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.config.RequestDataHelper;
import com.jpmsoft.jpbpm.mapper.TaskProcessinMapper;
import com.jpmsoft.jpbpm.pojo.dto.SysTaskProcessinDto;
import com.jpmsoft.jpbpm.pojo.dto.SysTaskProcessinUpdateDto;
import com.jpmsoft.jpbpm.pojo.dto.WorkFlowFileDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.ApprovalDurationVo;
import com.jpmsoft.jpbpm.pojo.vo.SysTaskProcessinInfoVo;
import com.jpmsoft.jpbpm.pojo.vo.TaskCoordinationTaskVo;
import com.jpmsoft.jpbpm.pojo.vo.TaskStepsVo;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import com.jpmsoft.jpbpm.util.DateUtil;
import com.jpmsoft.jpbpm.util.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskProcessinImpl extends ServiceImpl<TaskProcessinMapper, TaskProcessin> implements TaskProcessinService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    SysConfigEntityService sysConfigEntityService;
    @Autowired
    private FlowActivityService flowActivityService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileAnnexService fileAnnexService;
    @Autowired
    private FlowRuleService flowRuleService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FlowService flowService;
    @Autowired
    SysCoordinationTaskService sysCoordinationTaskService;
    @Autowired
    PreProjectService preProjectService;
    @Resource
    private SysPersonalBpmAuthorizeService sysPersonalBpmAuthorizeService;
    @Autowired
    TaskProcessUserService taskProcessUserService;
    //@Value("${bpm.tpSlectType}")
    private String tpSlectType = "0";
    @Autowired
    private SysPositionUserService sysPositionUserService;
    @Autowired
    private SysSplitTableDtlService sysSplitTableDtlService;
    String tableName = "Sys_Task_Processin";

    public String getTableName(String cTode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(cTode, year, tableName);
        return tableCode;
    }

    @Override
    @Transactional
    public TaskProcessin getTaskProcessById(String id, String tCode, String newYear) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, newYear, tableName);
        if (tableCode.equals(tableName)) {
            TaskProcessin obj = baseMapper.selectById(id);
            if (obj != null) {
                return obj;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);
        TaskProcessin obj = baseMapper.selectById(id);

        if (obj == null) {
            obj = baseMapper.selectById(id);
        }
        return obj;

    }

    @Override
    @Transactional
    public List<TaskProcessin> getListByIds(List<String> ids, String tCode, String newYear) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, newYear, tableName);
        if (tableCode.equals(tableName)) {
            List<TaskProcessin> list = baseMapper.selectBatchIds(ids);
            if (!CollectionUtils.isEmpty(list)) {
                return list;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);

        List<TaskProcessin> obj = baseMapper.selectBatchIds(ids);
        
        if (CollectionUtils.isEmpty(obj)) {
            obj = baseMapper.selectBatchIds(ids);
        }
        return obj;
    }

    @Override
    @Transactional
    public List<TaskProcessin> getList(QueryWrapper<TaskProcessin> queryWrapper, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            List<TaskProcessin> list = baseMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(list)) {
                return list;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);
        List<TaskProcessin> obj = baseMapper.selectList(queryWrapper);
        
        if (CollectionUtils.isEmpty(obj)) {
            obj = baseMapper.selectList(queryWrapper);
        }
        return obj;

    }

    @Override
    @Transactional
    public List<TaskProcessin> getList(LambdaQueryWrapper<TaskProcessin> queryWrapper, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            List<TaskProcessin> list = baseMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(list)) {
                return list;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);
        List<TaskProcessin> obj = baseMapper.selectList(queryWrapper);
        
        if (CollectionUtils.isEmpty(obj)) {
            obj = baseMapper.selectList(queryWrapper);
        }
        return obj;
    }

    @Override
    @Transactional
    public List<TaskProcessin> getList(QueryWrapper<TaskProcessin> queryWrapper, List<String> tCode, String year) {
        List<TaskProcessin> dataList = new ArrayList<>();
        for (String t : tCode) {
            String tableCode = sysSplitTableDtlService.getTableCodeByTcode(t, year, tableName);
            if (tableCode.equals(tableName)) {
                List<TaskProcessin> obj = baseMapper.selectList(queryWrapper);
                if (!CollectionUtils.isEmpty(obj)) {
                    dataList.addAll(obj);
                } else {
                    String tableCode2 = sysSplitTableDtlService.getTableCodeByTcode(t, "", tableName);
                    RequestDataHelper.setRequestData(tableCode2);
                    List<TaskProcessin> obj2 = baseMapper.selectList(queryWrapper);
                    
                    if (!CollectionUtils.isEmpty(obj2)) {
                        dataList.addAll(obj2);
                    }
                }
            } else {
                RequestDataHelper.setRequestData(tableCode);
                List<TaskProcessin> obj = baseMapper.selectList(queryWrapper);
                
                if (CollectionUtils.isEmpty(obj)) {
                    obj = baseMapper.selectList(queryWrapper);
                }
                dataList.addAll(obj);
            }
        }
        dataList=  duplicateRemoval(dataList);
        return dataList;
    }

    @Override
    @Transactional
    public List<TaskProcessin> getList(LambdaQueryWrapper<TaskProcessin> queryWrapper, List<String> tCode, String year) {
        List<TaskProcessin> dataList = new ArrayList<>();
        for (String t : tCode) {
            String tableCode = sysSplitTableDtlService.getTableCodeByTcode(t, year, tableName);
            if (tableCode.equals(tableName)) {
                List<TaskProcessin> obj = baseMapper.selectList(queryWrapper);
                if (!CollectionUtils.isEmpty(obj)) {
                    dataList.addAll(obj);
                } else {
                    String tableCode2 = sysSplitTableDtlService.getTableCodeByTcode(t, "", tableName);
                    RequestDataHelper.setRequestData(tableCode2);
                    List<TaskProcessin> obj2 = baseMapper.selectList(queryWrapper);
                    
                    if (!CollectionUtils.isEmpty(obj2)) {
                        dataList.addAll(obj2);
                    }
                }
            } else {
                RequestDataHelper.setRequestData(tableCode);

                List<TaskProcessin> obj = baseMapper.selectList(queryWrapper);
                
                if (CollectionUtils.isEmpty(obj)) {
                    obj = baseMapper.selectList(queryWrapper);
                }
                dataList.addAll(obj);
            }
        }
        dataList = duplicateRemoval(dataList);
        return dataList;
    }

    public List<TaskProcessin> duplicateRemoval(List<TaskProcessin> fileList) {
        List<TaskProcessin> duplicateList = new ArrayList<>();
        //去重
        fileList.forEach(n -> {
            List<TaskProcessin> lis = duplicateList.stream().filter(k -> k.getTpId().equals(n.getTpId())).collect(Collectors.toList());
            if (org.apache.commons.collections.CollectionUtils.isEmpty(lis)) {
                duplicateList.add(n);
            }
        });
        return duplicateList;
    }

    @Override
    @Transactional
    public boolean updateTaskProcessById(TaskProcessin obj, String tCode, String year) {
        Boolean type = false;
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            type = baseMapper.updateById(obj) == 1 ? true : false;
            if (type == false) {
                String tableCode2 = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
                RequestDataHelper.setRequestData(tableCode2);
                type = baseMapper.updateById(obj) == 1 ? true : false;

            }
        } else {
            RequestDataHelper.setRequestData(tableCode);
            type = baseMapper.updateById(obj) == 1 ? true : false;

            if (type == false) {
                type = baseMapper.updateById(obj) == 1 ? true : false;
            }
        }
        return type;

    }


    @Override
    @Transactional
    public boolean saveTaskProcessBatch(List<TaskProcessin> list, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            list.forEach(tp -> {
                baseMapper.insert(tp);
            });
        } else {
            //存在分表信息
            RequestDataHelper.setRequestData(tableCode);
            list.forEach(tp -> {
                baseMapper.insert(tp);
            });

        }
        return true;
    }


    @Override
    @Transactional
    public int insertTaskProcessin(TaskProcessin processinPojo, String tCode, String year) {
        int type = 0;
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (ValidateUtil.isEmpty(tableCode)) {
            type = baseMapper.insert(processinPojo);
        } else {
            //存在分表信息
            RequestDataHelper.setRequestData(tableCode);
            type = baseMapper.insert(processinPojo);

        }
        return type;
    }

    @Override
    @Transactional
    public int deleteTaskProcessin(String id, String tCode, String year) {
        int type = 0;
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (ValidateUtil.isEmpty(tableCode)) {
            type = baseMapper.deleteById(id);
        } else {
            //存在分表信息
            RequestDataHelper.setRequestData(tableCode);
            type = baseMapper.deleteById(id);

        }
        return type;
    }

    @Override
    public TaskProcessin GetHis(Task old_task, String result, String explain, String user, String type, Flow flow, String nextUser, String Acttype, Float acthour, String customerId, String requestSource) {
        FlowActivity act = flowActivityService.getById(old_task.getTaskcurrentactid());
        TaskProcessin his = new TaskProcessin();
        his.setTpActid(old_task.getTaskcurrentactid());
        his.setTpTaskid(old_task.getTaskid());
        his.setActname(act.getActname());
        his.setTpDotime(new Date());
        his.setTpExplain(explain);
        his.setTpFlowid(flow.getFlowid());
        his.setTpProject(flow.getFlowproject());
        his.setTpId(GenerateUUID.getUUID());
        his.setTpResult(result);
        his.setTpRuleid(old_task.getTaskrule());
        his.setTpEntityid(old_task.getTaskentityid());
        his.setTpName(old_task.getTaskname());
        his.setTpUser(user);
        his.setTpSend(nextUser);
        his.setTpCountersigntitle(act.getActcountersigntitle());
        his.setTpStarttime(old_task.getTaskstarttime());
        his.setTpEndtime(old_task.getTaskendtime());
        his.setTpTaskrb(old_task.getTaskrb());
        his.setTpType(type);
        his.setTpFrom(old_task.getTasksenduser());
        his.setTpTaskgetflagstr(old_task.getTaskgetflagstr());
        his.setTpTaskReturnType(old_task.getTaskReturnType());
        his.setTpClient(requestSource);
        if (!"All".equals(customerId)) {
            his.setTeId(customerId);
        }
        if (type.equals("跳过"))
            his.setTpSkip(1);
        //Long time = his.getTpDotime().getTime() - old_task.getTaskstarttime().getTime();
        //Float time1 = time.floatValue() / (1000f * 60f * 60f);
        his.setTpActhour(acthour);
        his.setTpActtype(Acttype);
        return his;
    }


    @Override
    public TaskProcessin GetHis(File file, String explain, String user, FlowActivity flowAct, String ruleId, Date doTime, String tpName, String taskgetflagstr, String taskReturnType, String customerId, String requestSource) {
        TaskProcessin his = new TaskProcessin();
        his.setActname("【撤回】");
        his.setTpDotime(new Date());
        his.setTpExplain(explain);
        his.setTpFlowid(file.getFlowId());
        his.setTpProject(file.getFileproject());
        his.setTpId(GenerateUUID.getUUID());
        his.setTpResult("同意");
        his.setTpEntityid(file.getFileid());
        his.setTpName(tpName);
        his.setTpUser(user);
        his.setTpType("撤回");
        his.setTpActtype(flowAct.getActtype());
        his.setTpActhour(flowAct.getActhour() != null ? Float.parseFloat(flowAct.getActhour() + "") : null);
        his.setTpFrom(user);
        his.setTpFileid(file.getFileid());
        his.setTpSend(user);
        his.setTpTemplatecodeo(file.gettCode());
        his.setTpActid(flowAct.getActid());
        his.setTpRuleid(ruleId);
        his.setTpSigntime(doTime);
        his.setTpDotime(new Date());
        his.setTpTaskgetflagstr(taskgetflagstr);
        his.setTpTaskReturnType(taskReturnType);
        his.setTpClient(requestSource);
        if (!"All".equals(customerId)) {
            his.setTeId(customerId);
        }
        return his;
    }


    @Override
    public List<TaskProcessin> getListByTpFlowIdAndTpEntityId(String tpFlowId, String tpEntityId, String customerId, String year) {
        QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
        Flow flow = flowService.getById(tpFlowId);
        wrapper.eq("tp_flowid", tpFlowId);
        wrapper.eq("tp_entityid", tpEntityId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        String tCode = "";
        if (flow != null) {
            tCode = flow.getTCode();
        }
        return getList(wrapper, tCode, year);
    }

    @Override
    public List<TaskProcessin> getTaskProcessinByReturnstart(String tp_entityid, String tp_flowid, String tp_actid, String customerId, String tCode, String year) {
        QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
        wrapper.eq("tp_entityid", tp_entityid);
        wrapper.eq("tp_flowid", tp_flowid);
        wrapper.eq("tp_actid", tp_actid);
        wrapper.eq("tp_type", "提交");
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        return getList(wrapper, tCode, year);
    }


    /**
     * 已办列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse havetodolist(TaskRequest request, String customerId) {

        return new BaseResponse();
    }

    @Override
    public BaseResponse getApproveListPreTask(String fileId, String tCode, String userId, String customerId, String year) {
        File fileObj = fileService.getFileById(fileId, tCode, year);
        QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
        wrapper.eq("tp_entityid", fileId);
        wrapper.eq("tp_acttype", "userTask");
        wrapper.eq("tp_user", userId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<TaskProcessin> TaskProcessin = getList(wrapper, tCode, year);
        QueryWrapper<Task> taskWrapper = new QueryWrapper<>();
        taskWrapper.eq("TaskEntityID", fileId);
        if (!"All".equals(customerId)) {
            taskWrapper.eq("Te_Id", customerId);
        }
        List<Task> entitylist = taskService.list(taskWrapper);
        List<FlowActivity> FlowActivitys = flowActivityService.getFlowActivityByActFlowID(fileObj.getFlowId(), customerId);
        //获取当前流程下所有的连接线
        List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(fileObj.getFlowId(), customerId);
        List<FlowActivity> returnList = new ArrayList<>();
        List<String> actIds = TaskProcessin.stream().map(n -> n.getTpActid()).collect(Collectors.toList());
        if (entitylist.size() > 0) {
            for (Task n : entitylist) {
                List<FlowActivity> newObj = FlowActivitys.stream().filter(k -> k.getActid().equals(n.getTaskcurrentactid())).collect(Collectors.toList());
                if (newObj.size() > 0) {
                    List<FlowActivity> actList = flowActivityService.RecursiveNodeCanreturned(FlowActivitys, FlowRules, newObj.get(0), customerId);
                    actList = actList.stream().filter(l -> actIds.contains(l.getActid())).collect(Collectors.toList());
                    returnList.addAll(actList);
                }
            }
            ;
        }
        if (returnList.size() > 0) {
            returnList = returnList.stream().distinct().collect(Collectors.toList());
        }
        return new BaseResponse("获取数据成功！", returnList, returnList.size());
    }

    @Override
    public BaseResponse getApproveList(FilerecordRequest requst, String customerId, String token, String year) {
        QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
        wrapper.eq("tp_entityid", requst.getEntityid());
        wrapper.eq("tp_acttype", "userTask");
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        year = requst.getYear();
        File fileObj = fileService.getFileById(requst.getEntityid(), requst.gettCode(), requst.getYear());
        year = DateUtil.DateToString(fileObj.getFilecreatedate(), "yyyy");
        List<TaskProcessin> taskProcessin = getList(wrapper, requst.gettCode(), year);
        List<TaskProcessResponse> newDataentity = new ArrayList<>();
        String flowId = "";
        if (taskProcessin != null && taskProcessin.size() > 0) {

            String flowRecordRule = "";
            try {
                Flow flow = flowService.getById(taskProcessin.stream().sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).findFirst().get().getTpFlowid());
                if (flow != null) {
                    flowId = flow.getFlowid();
                    flowRecordRule = flow.getFlowRecordRule();
                }
            } catch (Exception e) {

            }

            List<String> userid = taskProcessin.stream().map(n -> n.getTpUser()).collect(Collectors.toList());

            List<SysUser> users = sysUserService.getUserAllByUids(userid, customerId);
            List<String> uSignature = users.stream().map(n -> n.getuSignature()).collect(Collectors.toList());
            List<FileResponse> fileannexList = fileAnnexService.getFileByFileRelationIds(uSignature, token);
            List<FlowActivity> actListN = flowActivityService.getFlowActivityByActFlowID(flowId, customerId);
            List<String> actIdList = taskProcessin.stream().map(n -> n.getTpActid()).collect(Collectors.toList());
            List<FlowActivity> actList = flowActivityService.listByIds(actIdList);
            actList.addAll(actListN);
            List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowId, customerId);
            FlowActivity FlowActivityobj = flowActivityService.getStarActivity(actList, FlowRules, customerId);

            //获取最新一轮审批信息
            List<TaskProcessin> newTp = taskProcessin.stream().filter(n -> n.getTpDotime() != null && FlowActivityobj != null && n.getTpActid().equals(FlowActivityobj.getActid())).sorted(Comparator.comparing(TaskProcessin::getTpDotime)).collect(Collectors.toList());
            Date StartTime = fileObj.getFilecreatedate();
            if (!CollectionUtils.isEmpty(newTp)) {
                StartTime = newTp.get(0).getTpDotime();
            }
            Date StartData = StartTime;
            List<TaskProcessin> newTpList = taskProcessin.stream().filter(n -> n.getTpDotime() != null && n.getTpDotime().compareTo(StartData) > 1).collect(Collectors.toList());

            //获取基础配置信息
            List<String> codeList = new ArrayList<>();
            codeList.add("flowRecallRecord");
            codeList.add("approvalRecordGateway");
            codeList.add("externalUsersNotsee");
            codeList.add("processSkipShielding");

            Map<String, Object> configStr = sysConfigEntityService.getConfigEntity(codeList, customerId);
            String flowRecallRecord = "";
            String approvalRecordGateway = "";
            String externalUsersNotsee = "";
            String processSkipShielding = "";

            if (configStr.get("flowRecallRecord") != null) {
                flowRecallRecord = configStr.get("flowRecallRecord").toString();
            }
            if (configStr.get("approvalRecordGateway") != null) {
                approvalRecordGateway = configStr.get("approvalRecordGateway").toString();
            }
            if (configStr.get("externalUsersNotsee") != null) {
                externalUsersNotsee = configStr.get("externalUsersNotsee").toString();
            }

            if (configStr.get("processSkipShielding") != null) {
                processSkipShielding = configStr.get("processSkipShielding").toString();
            }
            List<TaskProcessResponse> newData = new ArrayList<>();
            for (int i = 0; i < taskProcessin.size(); i++) {
                TaskProcessin data = taskProcessin.get(i);
                TaskProcessResponse objtask = new TaskProcessResponse();
                BeanUtils.copyProperties(data, objtask);
                objtask.setFlowRecordRule(flowRecordRule);
                Boolean type = true;
                if (!ValidateUtil.isEmpty(data.getTpUser())) {
                    List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getTpUser())).collect(Collectors.toList());
                    if (user != null && user.size() > 0) {
                        objtask.setTpUserName(user.get(0).getuName());
                        objtask.setTpUserType(user.get(0).getuType());
                        if (!ValidateUtil.isEmpty(user.get(0).getuSignature())) {
                            List<FileResponse> userFile = fileannexList.stream().filter(n -> n.getRelationid() != null && n.getRelationid().equals(user.get(0).getuSignature())).collect(Collectors.toList());
                            if (userFile.size() > 0) {
                                objtask.setTpUserSignature(userFile.get(0).getUid());

                            }
                        }
                        if ("0".equals(externalUsersNotsee) && "1".equals(user.get(0).getuType()) && "2".equals(requst.getuType())) {
                            type = false;
                        }
                    }
                }
                if (flowRecallRecord.equals("0")) {
                    if (!ValidateUtil.isEmpty(objtask.getTpType()) && objtask.getTpType().equals("撤回")) {
                        type = false;
                    }
                }
                if (approvalRecordGateway.equals("0")) {
                    if (!ValidateUtil.isEmpty(objtask.getTpActtype()) && objtask.getTpActtype().contains("Gateway")) {
                        type = false;
                    }
                }
                if (processSkipShielding.equals("1")) {
                    if (!ValidateUtil.isEmpty(objtask.getTpType()) && (objtask.getTpType().equals("跳过") || objtask.getTpType().equals("跳过（结束）"))) {
                        type = false;
                    }
                }

                //
                List<FlowActivity> acts = actList.stream().filter(n -> n.getActid().equals(objtask.getTpActid())).collect(Collectors.toList());
                if (acts.size() > 0) {
                    objtask.setActOrder(acts.get(0).getActorder());
                    if (("7".equals(fileObj.getFileflowstatus()))) {
                        if ("1".equals(acts.get(0).getActApprovalHiddenType())) {
                            //隐藏
                            String codes = acts.get(0).getActApprovalHiddenCondition();
                            List<FlowActivity> actL = actList.stream().filter(n -> codes.contains(n.getActcode())).collect(Collectors.toList());
                            if (actL.size() > 0) {
                                List<String> actIds = actL.stream().map(n -> n.getActid()).collect(Collectors.toList());
                                List<TaskProcessin> actsd = newTpList.stream().filter(n -> codes.contains(n.getTpActid())).collect(Collectors.toList());
                                if (actsd.size() > 0) {
                                    type = true;
                                } else {
                                    type = false;
                                }
                            } else {
                                type = false;
                            }
                            //newTpList.stream().filter(n->codes.contains(n.gett) n.getTpActid().equals())
                        } else if ("2".equals(acts.get(0).getActApprovalHiddenType())) {
                            //加密
                            String codes = acts.get(0).getActApprovalHiddenCondition();
                            List<FlowActivity> actL = actList.stream().filter(n -> codes.contains(n.getActcode())).collect(Collectors.toList());
                            if (actL.size() > 0) {
                                List<String> actIds = actL.stream().map(n -> n.getActid()).collect(Collectors.toList());
                                List<TaskProcessin> actsd = newTpList.stream().filter(n -> codes.contains(n.getTpActid())).collect(Collectors.toList());
                                if (actsd.size() == 0) {
                                    objtask.setTpUser("***");
                                    objtask.setTpExplain("******");
                                }
                            } else {
                                objtask.setTpUser("***");
                                objtask.setTpExplain("******");
                            }
                        }
                    }
                }
                if (type) {
                    newData.add(objtask);
                }

            }
            newDataentity=newData;
            try {
                if (!CollectionUtils.isEmpty(newDataentity) && !CollectionUtils.isEmpty(actList)) {
                    String uId = redisUtils.getUid(token);
                    //获取当前流程的代办信息
                    List<String>  fileIds=new ArrayList<>();
                    fileIds.add(fileObj.getFileid());
                    List<Task>  taskList=taskService.getTaskListByFiles(fileIds);
                    List<String> taskActIds=new ArrayList<>();
                    if(!CollectionUtils.isEmpty(taskList)){
                        List<String>  acts=taskList.stream().map(Task::getTaskcurrentactid).collect(Collectors.toList());
                        taskActIds.addAll(acts);
                        List<FlowActivity>  actObjList=actList.stream().filter(p->acts.contains(p.getActid())).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(actObjList)){
                            actObjList.forEach(p->{
                                List<FlowActivity>  actEList=flowActivityService.getAllSubsequentNodes(p,actList,FlowRules);
                                if(!CollectionUtils.isEmpty(actEList)){
                                    taskActIds.addAll(actEList.stream().map(FlowActivity::getActid).collect(Collectors.toList()));
                                }
                            });
                        }
                    }
                    List<String> acIds = flowActivityService.getNodesAllowedNodeIds(actList, taskProcessin, uId, "1", fileObj.getFileflowstatus() + "",taskActIds);
                    if (!CollectionUtils.isEmpty(acIds)) {
                        newDataentity = newDataentity.stream().filter(n -> acIds.contains(n.getTpActid())).collect(Collectors.toList());
                    } else {
                        newDataentity = new ArrayList<>();
                    }
                }
            } catch (Exception e) {
                log.error("流程服务：权限过滤失败" + e.getMessage());
            }

            newDataentity = newDataentity.stream().sorted(Comparator.comparing(TaskProcessResponse::getTpDotime)).collect(Collectors.toList());
        }

        return new BaseResponse("获取数据成功！", newDataentity, newDataentity.size());
    }


    //here
    @Override
    public BaseResponse getUserTaskProcessList(String userId, UserTaskProcessinRequest request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "tp_dotime");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "desc");

        params.put("tpName", !ValidateUtil.isEmpty(request.getTpName()) ? request.getTpName() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        params.put("tpFileTitle", !ValidateUtil.isEmpty(request.getTpFileTitle()) ? request.getTpFileTitle() : null);
        params.put("tpSendName", !ValidateUtil.isEmpty(request.getTpSendName()) ? request.getTpSendName() : null);
        params.put("tpStarttime", !ValidateUtil.isEmpty(request.getTpStarttime()) ? DateUtil.DateAddHMS(request.getTpStarttime(), 0, 0, 0) : null);
        params.put("tpEndtime", !ValidateUtil.isEmpty(request.getTpEndtime()) ? DateUtil.DateAddHMS(request.getTpEndtime(), 23, 59, 59) : null);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        //获取基础配置信息
        List<String> codeList = new ArrayList<>();
        codeList.add("flowRecallRecord");
        codeList.add("approvalRecordGateway");
        Map<String, Object> configStr = sysConfigEntityService.getConfigEntity(codeList, customerId);
        String flowRecallRecord = "";
        String approvalRecordGateway = "";
        if (configStr.get("flowRecallRecord") != null) {
            flowRecallRecord = configStr.get("flowRecallRecord").toString();
            if (!flowRecallRecord.equals("0")) {
                flowRecallRecord = "";
            }
        }
        if (configStr.get("approvalRecordGateway") != null) {
            approvalRecordGateway = configStr.get("approvalRecordGateway").toString();
            if (!approvalRecordGateway.equals("0")) {
                approvalRecordGateway = "";
            }
        }
        params.put("flowRecallRecord", !ValidateUtil.isEmpty(flowRecallRecord) ? "撤回" : null);
        params.put("approvalRecordGateway", !ValidateUtil.isEmpty(approvalRecordGateway) ? "Gateway" : null);
        if (ValidateUtil.isNotEmpty(request.getMinType()) && request.getMinType().equals("1")) {
            params.put("userId", null);
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
            params.put("timeStartMin", DateUtil.DateChangeDay(new Date(), -30));
        }
        Map<String, Object> returnData = getUserTaskProcessList(params, request.gettCode(), request.getYear());
        List<TaskProcessResponse> datas = (List<TaskProcessResponse>) returnData.get("data");// baseMapper.getUserTaskProcessList(params);
        if (CollectionUtils.isEmpty(datas)) {
            return new BaseResponse("获取成功", new ArrayList<>(), 0);
        }
        List<String> uIds = datas.stream().map(r -> r.getTpUser()).distinct().collect(Collectors.toList());
        List<String> entityIds = datas.stream().map(n -> n.getTpEntityid()).distinct().collect(Collectors.toList());
        List<String> actIds = datas.stream().map(n -> n.getTpActid()).distinct().collect(Collectors.toList());
        List<Task> taskList = taskService.getTaskListByFiles(entityIds);
        List<FlowActivity> actList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(taskList)) {
            uIds.addAll(taskList.stream().map(l -> l.getTaskuser()).collect(Collectors.toList()));
            actIds.addAll(taskList.stream().map(n -> n.getTaskcurrentactid()).distinct().collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(actIds)) {
            actList = flowActivityService.listByIds(actIds);
        }
        List<SysUser> users = sysUserService.getAllUser(customerId);
        List<String> tpIds = datas.stream().map(TaskProcessResponse::getTpId).distinct().collect(Collectors.toList());
        List<String> codes = datas.stream().map(TaskProcessResponse::gettCode).distinct().collect(Collectors.toList());
        QueryWrapper<TaskProcessUser> tpuWrapper = new QueryWrapper<>();
        tpuWrapper.in("Tpu_Tp_Id", tpIds);
        List<TaskProcessUser> tpuList = taskProcessUserService.getList(tpuWrapper, codes, request.getYear());
        List<TaskProcessResponse> newData = new ArrayList<>();
        for (TaskProcessResponse data : datas) {

            if (!ValidateUtil.isEmpty(data.getTpFrom())) {
                List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getTpFrom())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(user)) {
                    data.setTpFromName(user.get(0).getuName());
                }
            }

            List<TaskProcessUser> tpus = tpuList.stream().filter(n -> n.getTpuTpId().equals(data.getTpId())).collect(Collectors.toList());
            // 修复：有流程发送用户关系时才组装发送人。
            if (!CollectionUtils.isEmpty(tpus)) {
                List<String> uids = tpus.stream().map(n -> n.getTpuUser()).distinct().collect(Collectors.toList());
                List<SysUser> useres = users.stream().filter(n -> uids.contains(n.getuId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(useres)) {
                    List<String> sendUserNames = new ArrayList<>();
                    List<String> sendUsers = new ArrayList<>();
                    useres.forEach(n -> {
                        sendUserNames.add(n.getuName());
                        sendUsers.add(n.getuId());
                    });
                    // 修复：姓名和用户 ID 分别写入对应字段。
                    data.setTpSendName(sendUserNames);
                    data.setTpSendIds(sendUsers);
                }
            }
            List<FlowActivity> actsTp = actList.stream().filter(b -> b.getActid().equals(data.getTpActid())).collect(Collectors.toList());
            Integer orderNumber = 0;
            if (!CollectionUtils.isEmpty(actsTp)) {
                if (ValidateUtil.isNotNull(actsTp.get(0).getActorder())) {
                    orderNumber = actsTp.get(0).getActorder();
                }
            }
            data.setActOrder(orderNumber);
            //获取任务名称
            List<Task> tList = taskList.stream().filter(n -> n.getTaskentityid().equals(data.getTpEntityid())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tList)) {
                List<Map<String, Object>> tUserList = new ArrayList<>();
                for (Task t : tList
                ) {
                    String actName = "";
                    String taskUserName = "";
                    Integer num = 0;
                    List<FlowActivity> acts = actList.stream().filter(b -> b.getActid().equals(t.getTaskcurrentactid())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(acts)) {
                        actName = acts.get(0).getActname();
                        if (ValidateUtil.isNotNull(acts.get(0).getActorder())) {
                            num = acts.get(0).getActorder();
                        }
                    }
                    List<SysUser> userList = users.stream().filter(b -> b.getuId().equals(t.getTaskuser())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(userList)) {
                        taskUserName = userList.get(0).getuName();
                    }
                    Map<String, Object> ob = new HashMap<>();
                    ob.put("actName", actName);
                    ob.put("taskUserName", taskUserName);
                    tUserList.add(ob);
                }
                data.setTaskUsetAndActList(tUserList);
            }

            newData.add(data);
        }
        Integer total = Integer.valueOf(returnData.get("total") + "");
        return new BaseResponse("获取成功", newData, total);
    }

    @Override
    public BaseResponse getUserTaskProcessListDistinct(String userId, UserTaskProcessinRequest request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
//        params.put("userId", "68D319413F904195A859973BD4C5702D");
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "tp_dotime");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "desc");

        params.put("tpName", !ValidateUtil.isEmpty(request.getTpName()) ? request.getTpName() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        params.put("tpFileTitle", !ValidateUtil.isEmpty(request.getTpFileTitle()) ? request.getTpFileTitle() : null);
        params.put("tpSendName", !ValidateUtil.isEmpty(request.getTpSendName()) ? request.getTpSendName() : null);
        params.put("tpStarttime", !ValidateUtil.isEmpty(request.getTpStarttime()) ? DateUtil.DateAddHMS(request.getTpStarttime(), 0, 0, 0) : null);
        params.put("tpEndtime", !ValidateUtil.isEmpty(request.getTpEndtime()) ? DateUtil.DateAddHMS(request.getTpEndtime(), 23, 59, 59) : null);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        
        // 获取基础配置信息
        List<String> codeList = new ArrayList<>();
        codeList.add("flowRecallRecord");
        codeList.add("approvalRecordGateway");
        Map<String, Object> configStr = sysConfigEntityService.getConfigEntity(codeList, customerId);
        String flowRecallRecord = "";
        String approvalRecordGateway = "";
        if (configStr.get("flowRecallRecord") != null) {
            flowRecallRecord = configStr.get("flowRecallRecord").toString();
            if (!flowRecallRecord.equals("0")) {
                flowRecallRecord = "";
            }
        }
        if (configStr.get("approvalRecordGateway") != null) {
            approvalRecordGateway = configStr.get("approvalRecordGateway").toString();
            if (!approvalRecordGateway.equals("0")) {
                approvalRecordGateway = "";
            }
        }
        params.put("flowRecallRecord", !ValidateUtil.isEmpty(flowRecallRecord) ? "撤回" : null);
        params.put("approvalRecordGateway", !ValidateUtil.isEmpty(approvalRecordGateway) ? "Gateway" : null);
        
        // 处理部门维度查询
        if (ValidateUtil.isNotEmpty(request.getMinType()) && request.getMinType().equals("1")) {
            params.put("userId", null);
            List<String> dIds = sysPositionUserService.getDeptIdList(userId, customerId);
            if (CollectionUtils.isEmpty(dIds)) {
                return new BaseResponse("获取成功", new ArrayList<>(), 0);
            } else if (!dIds.contains("ALL")) {
                params.put("minDids", dIds);
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
            params.put("timeStartMin", DateUtil.DateChangeDay(new Date(), -30));
        }
        
        // 调用去重查询方法
        Map<String, Object> returnData = getUserTaskProcessListDistinct(params, request.gettCode(), request.getYear());
        List<TaskProcessResponse> datas = (List<TaskProcessResponse>) returnData.get("data");
        
        if (CollectionUtils.isEmpty(datas)) {
            return new BaseResponse("获取成功", new ArrayList<>(), 0);
        }
        
        // 后续数据组装逻辑与原方法保持一致
        List<String> entityIds = datas.stream().map(n -> n.getTpEntityid()).distinct().collect(Collectors.toList());
        List<String> actIds = datas.stream().map(n -> n.getTpActid()).distinct().collect(Collectors.toList());
        List<Task> taskList = taskService.getTaskListByFiles(entityIds);
        List<FlowActivity> actList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(taskList)) {
            actIds.addAll(taskList.stream().map(n -> n.getTaskcurrentactid()).distinct().collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(actIds)) {
            actList = flowActivityService.listByIds(actIds);
        }
        List<SysUser> users = sysUserService.getAllUser(customerId);
        List<String> tpIds = datas.stream().map(TaskProcessResponse::getTpId).distinct().collect(Collectors.toList());
        List<String> codes = datas.stream().map(TaskProcessResponse::gettCode).distinct().collect(Collectors.toList());
        QueryWrapper<TaskProcessUser> tpuWrapper = new QueryWrapper<>();
        tpuWrapper.in("Tpu_Tp_Id", tpIds);
        List<TaskProcessUser> tpuList = taskProcessUserService.getList(tpuWrapper, codes, request.getYear());
        
        List<TaskProcessResponse> newData = new ArrayList<>();
        for (TaskProcessResponse data : datas) {
            // 设置发起人姓名
            if (!ValidateUtil.isEmpty(data.getTpFrom())) {
                List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getTpFrom())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(user)) {
                    data.setTpFromName(user.get(0).getuName());
                }
            }
            
            // 设置活动节点序号
            List<FlowActivity> actsTp = actList.stream().filter(b -> b.getActid().equals(data.getTpActid())).collect(Collectors.toList());
            Integer orderNumber = 0;
            if (!CollectionUtils.isEmpty(actsTp)) {
                if (ValidateUtil.isNotNull(actsTp.get(0).getActorder())) {
                    orderNumber = actsTp.get(0).getActorder();
                }
            }
            data.setActOrder(orderNumber);
            
            // 获取当前待办人和节点信息
            List<Task> tList = taskList.stream().filter(n -> n.getTaskentityid().equals(data.getTpEntityid())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tList)) {
                List<Map<String, Object>> tUserList = new ArrayList<>();
                for (Task t : tList) {
                    String actName = "";
                    String taskUserName = "";
                    List<FlowActivity> acts = actList.stream().filter(b -> b.getActid().equals(t.getTaskcurrentactid())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(acts)) {
                        actName = acts.get(0).getActname();
                    }
                    List<SysUser> userList = users.stream().filter(b -> b.getuId().equals(t.getTaskuser())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(userList)) {
                        taskUserName = userList.get(0).getuName();
                    }
                    Map<String, Object> ob = new HashMap<>();
                    ob.put("actName", actName);
                    ob.put("taskUserName", taskUserName);
                    tUserList.add(ob);
                }
                data.setTaskUsetAndActList(tUserList);
            }
            newData.add(data);
        }
        Integer total = Integer.valueOf(returnData.get("total") + "");
        return new BaseResponse("获取成功", newData, total);
    }


    public Map<String, Object> getUserTaskProcessList(Map<String, Object> params, String tCode, String year) {
        List<TaskProcessResponse> tpList = new ArrayList<>();
        long total = 0;
        if (!("1").equals(tpSlectType)) {
            Map<String, Object> tableCodes = new HashMap<>();
            tableCodes.put("stp", sysSplitTableDtlService.getTableCodeByTcode(tCode, year, "Sys_Task_Processin"));
            tableCodes.put("sf", sysSplitTableDtlService.getTableCodeByTcode(tCode, year, "SYS_File"));
            tableCodes.put("stpu", sysSplitTableDtlService.getTableCodeByTcode(tCode, year, "Sys_Task_Process_User"));
            tpList = baseMapper.getUserTaskProcessList(params, tableCodes);
            total = baseMapper.getUserTaskProcessListCount(params, tableCodes);
        } else {
            Boolean type = true;
            //获取用户待办信息
            List<PreProject> pList = new ArrayList<>();
            List<Map<String, String>> conList = new ArrayList<>();
            List<SysUser> uList = new ArrayList<>();
            List<String> tpId = new ArrayList<>();
            List<File> fileList = new ArrayList<>();
            if (ValidateUtil.isNotNull(params.get("pName"))) {
                LambdaQueryWrapper<PreProject> wrapper = new LambdaQueryWrapper<>();
                wrapper.like(PreProject::getpName, params.get("pName"));
                wrapper.select(PreProject::getpName, PreProject::getpId);
                pList = preProjectService.list(wrapper);
                if (CollectionUtils.isEmpty(pList)) {
                    type = false;
                }
            }

            if (ValidateUtil.isNotNull(params.get("conName"))) {
                conList = baseMapper.getContractInfo(params.get("conName") + "");
                if (CollectionUtils.isEmpty(conList)) {
                    type = false;
                }
            }

            if (ValidateUtil.isNotNull(params.get("tpFileTitle"))) {
                LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
                wrapper.like(File::getFiletitle, params.get("tpFileTitle"));
                if (!CollectionUtils.isEmpty(pList)) {
                    wrapper.in(File::getFileproject, pList.stream().map(n -> n.getpId()).collect(Collectors.toList()));
                }

                if (!CollectionUtils.isEmpty(conList)) {
                    wrapper.in(File::getFilecontract, conList.stream().map(n -> n.get("conId")).collect(Collectors.toList()));
                }

                wrapper.select(File::getFileid, File::getFiletitle, File::getFilecreateuser, File::getFilecreateusername);
                fileList = fileService.getList(wrapper, tCode, year);
                if (CollectionUtils.isEmpty(fileList)) {
                    type = false;
                }
            } else {
                if (!CollectionUtils.isEmpty(pList) || !CollectionUtils.isEmpty(conList)) {
                    LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
                    if (!CollectionUtils.isEmpty(pList)) {
                        wrapper.in(File::getFileproject, pList.stream().map(n -> n.getpId()).collect(Collectors.toList()));
                    }

                    if (!CollectionUtils.isEmpty(conList)) {
                        wrapper.in(File::getFilecontract, conList.stream().map(n -> n.get("conId")).collect(Collectors.toList()));
                    }

                    wrapper.select(File::getFileid, File::getFiletitle, File::getFilecreateuser, File::getFilecreateusername, File::getFilecontract, File::getFileproject);
                    fileList = fileService.getList(wrapper, tCode, year);
                    if (CollectionUtils.isEmpty(fileList)) {
                        type = false;
                    }
                }
            }
            if (type) {
                LambdaQueryWrapper<TaskProcessin> tpWrapper = new LambdaQueryWrapper<>();
                Page<TaskProcessin> page = new Page<>(((Integer) params.get("start")), ((Integer) params.get("size")));
                if (ValidateUtil.isNotNull(params.get("userId"))) {
                    tpWrapper.eq(TaskProcessin::getTpUser, params.get("userId"));

                }

                if (ValidateUtil.isNotNull(params.get("tpStarttime")) && ValidateUtil.isNotNull(params.get("tpEndtime"))) {
                    tpWrapper.between(TaskProcessin::getTpDotime, params.get("tpStarttime"), params.get("tpEndtime"));

                }

                if (ValidateUtil.isNotNull(params.get("tpName"))) {
                    tpWrapper.eq(TaskProcessin::getTpName, params.get("tpName"));

                }

                if (ValidateUtil.isNotNull(params.get("flowRecallRecord"))) {
                    //tp_type
                    tpWrapper.ne(TaskProcessin::getTpType, params.get("flowRecallRecord"));
                }

                if (ValidateUtil.isNotNull(params.get("approvalRecordGateway"))) {
                    //tp_acttype
                    tpWrapper.notLike(TaskProcessin::getTpActtype, params.get("approvalRecordGateway"));
                }

                if (ValidateUtil.isNotNull(params.get("teId"))) {
                    tpWrapper.eq(TaskProcessin::getTeId, params.get("teId"));
                }
                if (ValidateUtil.isNotNull(params.get("sortType")) && ValidateUtil.isNotNull(params.get("sortFiled"))) {
                    if ("desc".equals((params.get("sortType") + "").toLowerCase())) {
                        tpWrapper.orderByDesc(TaskProcessin::getTpDotime);
                    } else {
                        tpWrapper.orderByAsc(TaskProcessin::getTpDotime);
                    }
                } else {
                    tpWrapper.orderByDesc(TaskProcessin::getTpDotime);
                }

                List<TaskProcessin> tpLists = getList(tpWrapper, tCode, year);
                total = tpLists.size();

                if (!CollectionUtils.isEmpty(tpLists)) {
                    if (!CollectionUtils.isEmpty(fileList)) {
                        List<String> fileIds = fileList.stream().map(n -> n.getFileid()).collect(Collectors.toList());
                        tpLists = tpLists.stream().filter(n -> fileIds.contains(n.getTpEntityid())).collect(Collectors.toList());

                    }

                    if (ValidateUtil.isNotNull(params.get("tpSendName"))) {
                        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
                        wrapper.like(SysUser::getuName, params.get("tpSendName"));
                        wrapper.select(SysUser::getuName, SysUser::getuId);
                        uList = sysUserService.list(wrapper);

                        if (CollectionUtils.isEmpty(uList)) {
                            tpLists = new ArrayList<>();
                        } else {
                            LambdaQueryWrapper<TaskProcessUser> wrapperTpu = new LambdaQueryWrapper<>();
                            for (int i = 0; i < tpLists.size(); i++) {
                                String tpId2 = tpLists.get(i).getTpId();
                                wrapperTpu.or(c -> c.eq(TaskProcessUser::getTpuTpId, tpId2));
                            }
                            List<TaskProcessUser> tpuList = taskProcessUserService.getList(wrapperTpu, tCode, year);
                            if (CollectionUtils.isEmpty(tpuList)) {
                                tpLists = new ArrayList<>();
                            } else {
                                List<String> uids = uList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                                tpuList = tpuList.stream().filter(n -> uids.contains(n.getTpuUser())).collect(Collectors.toList());
                                tpId = tpuList.stream().map(n -> n.getTpuTpId()).collect(Collectors.toList());
                            }
                        }
                    }
                    if (!CollectionUtils.isEmpty(tpId)) {
                        List<String> tpIds = tpId;
                        tpLists = tpLists.stream().filter(n -> tpIds.contains(n.getTpId())).collect(Collectors.toList());
                    }


                    total = tpLists.size();
                    int start = (int) params.get("start");
                    int size = (int) params.get("size");
                    int endNumber = start + size;
                    if (tpLists.size() < endNumber) {
                        endNumber = tpLists.size();
                    }
                    tpLists = tpLists.subList(start, endNumber);
                    List<String> flowIds = tpLists.stream().map(n -> n.getTpFlowid()).collect(Collectors.toList());
                    List<Flow> flowList = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(flowIds)) {
                        flowList = flowService.listByIds(flowIds);
                    }
                    if (CollectionUtils.isEmpty(fileList)) {
                        List<String> fileIds = tpLists.stream().map(n -> n.getTpEntityid()).collect(Collectors.toList());
                        fileList = fileService.getListByIds(fileIds, tCode, year);

                        if (CollectionUtils.isEmpty(conList)) {
                            List<String> conId = fileList.stream().filter(n -> n.getFilecontract() != null).map(n -> n.getFilecontract()).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(conId)) {
                                conList = baseMapper.getContractInfoByIds(conId);
                            }
                        }
                        if (CollectionUtils.isEmpty(pList)) {
                            List<String> pIds = fileList.stream().filter(n -> n.getFileproject() != null).map(n -> n.getFileproject()).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(pIds)) {
                                pList = preProjectService.listByIds(pIds);
                            }
                        }
                    }
                    if (CollectionUtils.isEmpty(uList)) {
                        List<String> tpIds = tpLists.stream().map(n -> n.getTpId()).collect(Collectors.toList());
                        LambdaQueryWrapper<TaskProcessUser> wrapperTpu = new LambdaQueryWrapper<>();
                        for (int i = 0; i < tpIds.size(); i++) {
                            String uid = tpIds.get(i);
                            wrapperTpu.or(c -> c.eq(TaskProcessUser::getTpuTpId, uid));
                        }
                        List<TaskProcessUser> tpuList = taskProcessUserService.getList(wrapperTpu, tCode, year);
                        List<String> uIds = tpuList.stream().map(n -> n.getTpuId()).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(uIds)) {
                            uList = sysUserService.listByIds(uIds);
                        }
                    }
                    for (TaskProcessin obj : tpLists
                    ) {
                        TaskProcessResponse ent = new TaskProcessResponse();
                        BeanUtils.copyProperties(obj, ent);
                        List<Flow> fs = flowList.stream().filter(n -> n.getFlowid().equals(obj.getTpFlowid())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(fs)) {
                            ent.setTpFlowName(fs.get(0).getFlowname());
                        }

                        List<File> files = fileList.stream().filter(n -> n.getFileid().equals(obj.getTpEntityid())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(files)) {
                            ent.setTpFileid(files.get(0).getFileid());
                            ent.setTpFileTitle(files.get(0).getFiletitle());
                            ent.setFileCreateUser(files.get(0).getFilecreateuser());
                            ent.setFileCreateUserName(files.get(0).getFilecreateusername());
                            String pId = files.get(0).getFileproject();
                            String cId = files.get(0).getFilecontract();
                            if (ValidateUtil.isNotNull(pId)) {
                                List<PreProject> ps = pList.stream().filter(n -> n.getpId().equals(pId)).collect(Collectors.toList());
                                if (!CollectionUtils.isEmpty(ps)) {
                                    ent.setpName(ps.get(0).getpName());
                                }
                            }

                            if (ValidateUtil.isNotNull(cId)) {
                                List<Map<String, String>> cons = conList.stream().filter(n -> n.get("conId").equals(cId)).collect(Collectors.toList());
                                if (!CollectionUtils.isEmpty(cons)) {
                                    ent.setConName(cons.get(0).get("conName"));
                                }
                            }

                        }
                        tpList.add(ent);
                    }


                }
            }
        }
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", tpList);
        returnData.put("total", total);
        return returnData;

    }

    /**
     * 获取用户已办任务列表（去重版）
     * 对于同一个tp_user和tp_entityid，只保留最新的一条办理记录
     */
    public Map<String, Object> getUserTaskProcessListDistinct(Map<String, Object> params, String tCode, String year) {
        List<TaskProcessResponse> tpList ;
        long total = 0;
        

        Map<String, Object> tableCodes = new HashMap<>();
        tableCodes.put("stp", sysSplitTableDtlService.getTableCodeByTcode(tCode, year, "Sys_Task_Processin"));
        tableCodes.put("sf", sysSplitTableDtlService.getTableCodeByTcode(tCode, year, "SYS_File"));
        tableCodes.put("stpu", sysSplitTableDtlService.getTableCodeByTcode(tCode, year, "Sys_Task_Process_User"));

        tpList = baseMapper.getUserTaskProcessListDistinct(params, tableCodes);
        total = baseMapper.getUserTaskProcessListDistinctCount(params, tableCodes);

        
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", tpList);
        returnData.put("total", total);
        return returnData;
    }

    public List<TaskProcessin> paginateList(List<TaskProcessin> list, int pageSize, int pageNumber) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        if (startIndex > endIndex) {
            return Collections.emptyList();
        }
        return list.subList(startIndex, endIndex);
    }

    @Override
    public BaseResponse getTaskProcessList(SysTaskProcessinDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();

        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "tp_dotime");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "desc");
        params.put("tpDoUserName", !ValidateUtil.isEmpty(request.getTpDoUserName()) ? request.getTpDoUserName() : null);
        params.put("tpExplain", !ValidateUtil.isEmpty(request.getTpExplain()) ? request.getTpExplain() : null);
        params.put("actName", !ValidateUtil.isEmpty(request.getActName()) ? request.getActName() : null);
        params.put("flowName", !ValidateUtil.isEmpty(request.getFlowName()) ? request.getFlowName() : null);
        params.put("tName", !ValidateUtil.isEmpty(request.gettName()) ? request.gettName() : null);
        params.put("tpName", !ValidateUtil.isEmpty(request.getTpName()) ? request.getTpName() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        params.put("secName", !ValidateUtil.isEmpty(request.getSecName()) ? request.getSecName() : null);
        params.put("fileId", !ValidateUtil.isEmpty(request.getFileId()) ? request.getFileId() : null);
        params.put("tpFileTitle", !ValidateUtil.isEmpty(request.getTpFileTitle()) ? request.getTpFileTitle() : null);
        params.put("tpSendName", !ValidateUtil.isEmpty(request.getTpSendName()) ? request.getTpSendName() : null);
        params.put("starTime", !CheckNullUtil.checkObject(request.getStarTime()) ? request.getStarTime() : null);
        params.put("endTime", !CheckNullUtil.checkObject(request.getEndTime()) ? request.getEndTime() : null);
        params.put("actName", !CheckNullUtil.checkObject(request.getActName()) ? request.getActName() : null);
        params.put("tpStarttime", !ValidateUtil.isEmpty(request.getTpStarttime()) ? DateUtil.DateAddHMS(request.getTpStarttime(), 0, 0, 0) : null);
        params.put("tpEndtime", !ValidateUtil.isEmpty(request.getTpEndtime()) ? DateUtil.DateAddHMS(request.getTpEndtime(), 23, 59, 59) : null);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        //获取基础配置信息
        List<String> codeList = new ArrayList<>();
        codeList.add("flowRecallRecord");
        codeList.add("approvalRecordGateway");
        Map<String, Object> configStr = sysConfigEntityService.getConfigEntity(codeList, customerId);
        String flowRecallRecord = "";
        String approvalRecordGateway = "";
        if (configStr.get("approvalRecordGateway") != null) {
            approvalRecordGateway = configStr.get("approvalRecordGateway").toString();
            if (!approvalRecordGateway.equals("0")) {
                approvalRecordGateway = "";
            }
        }
        params.put("flowRecallRecord", !ValidateUtil.isEmpty(flowRecallRecord) ? "撤回" : null);
        params.put("approvalRecordGateway", !ValidateUtil.isEmpty(approvalRecordGateway) ? "Gateway" : null);

        String tCode = request.gettCode();
        Map<String, Object> tableCodes = new HashMap<>();
        String tableCode = "";
        if (!ValidateUtil.isEmpty(tCode)) {
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, request.getYear(), tableName);

        }
        if (!ValidateUtil.isEmpty(tableCode)) {
            tableCodes.put("stp", "Sys_Task_Processin_" + tableCode);
            tableCodes.put("sf", "SYS_File_" + tableCode);
            tableCodes.put("stpu", "Sys_Task_Process_User_" + tableCode);
        } else {
            tableCodes.put("stp", "Sys_Task_Processin");
            tableCodes.put("sf", "SYS_File");
            tableCodes.put("stpu", "Sys_Task_Process_User");
        }

        List<TaskProcessResponse> datas = baseMapper.getTaskProcessList(params, tableCodes);
        List<SysUser> users = sysUserService.getAllUser(customerId);
        List<TaskProcessResponse> newData = new ArrayList<>();
        datas.forEach(data -> {
            List<String> sendUsers = new ArrayList<>();
            if (!ValidateUtil.isEmpty(data.getTpFrom())) {
                String[] froms = data.getTpFrom().split(",");
                for (String from : froms) {
                    List<SysUser> user = users.stream().filter(n -> n.getuId().equals(from)).collect(Collectors.toList());
                    if (user != null && user.size() > 0)
                        sendUsers.add(user.get(0).getuName());
                    else
                        sendUsers.add("");
                }
            }

            data.setTpSendName(sendUsers);
            newData.add(data);
        });
        Integer total = baseMapper.getTaskProcessListCount(params, tableCodes);
        return new BaseResponse("获取成功", newData, total);
    }

    @Override
    public List<Map<String, Object>> getSomeFieldsTaskProcessins(String tpEntityid, String customerId, String tCode, String year) {

        return baseMapper.getSomeFieldsTaskProcessin(tpEntityid, getTableName(tCode, year));
    }


    @Override
    public BaseResponse getTaskProcessByTpId(String tpId, String customerId, String tCode, String year) {
        return new BaseResponse("获取成功", getTaskProcessById(tpId, tCode, year), 1);
    }

    @Override
    public BaseResponse getFileProcessStatus(String fileId, String tCode, String uId, String customerId, String year) {
        List<TaskStepsVo> taskList = baseMapper.getApprovalTask(fileId, customerId);
        List<ApprovalDurationVo> TaskProcessin = baseMapper.getApprovalDuration(fileId, customerId, getTableName(tCode, year));
        Map<String, Object> task = new HashMap<>();
        List<TaskStepsVo> returnData = new ArrayList<>();
        File fileObj = fileService.getFileById(fileId, tCode, year);
        if (fileObj != null) {
            List<FlowActivity> actList = flowActivityService.getFlowActivityByActFlowID(fileObj.getFlowId(), customerId);
            List<TaskStepsVo> finalReturnData = new ArrayList<>();
            taskList.forEach(n -> {
                if (actList != null && actList.size() > 0) {
                    List<FlowActivity> acts = actList.stream().filter(k -> k.getActid().equals(n.getActID())).collect(Collectors.toList());
                    if (acts != null && acts.size() > 0) {
                        if ("1".equals(acts.get(0).getActApprovalHiddenType())) {
                            n.setuName("_____");
                        } else if ("2".equals(acts.get(0).getActApprovalHiddenType())) {
                            n.setuName("***");
                        }
                    }
                }
                finalReturnData.add(n);
            });
            returnData=finalReturnData;
            try {
                if (!ValidateUtil.isEmpty(returnData)) {
                    List<String> acts = returnData.stream().map(TaskStepsVo::getActID).collect(Collectors.toList());
                    List<FlowActivity> actObjs = actList.stream().filter(k -> acts.contains(k.getActid())).collect(Collectors.toList());
                    List<String> actIds = flowActivityService.getNodesAllowedNodeIds(actObjs, new ArrayList<>(), uId, "2", fileObj.getFileflowstatus() + "",new ArrayList<>());
                    if (!ValidateUtil.isEmpty(actIds)) {
                        returnData = returnData.stream().filter(n -> actIds.contains(n.getActID())).collect(Collectors.toList());
                    } else {
                        returnData = new ArrayList<>();
                    }
                }
            } catch (Exception e) {
                log.error("流程服务：获取表单当前状态，过滤失败");
            }
            task.put("taskList", returnData);
        } else {
            task.put("taskList", taskList);
            returnData = taskList;
        }
        task.put("taskProcessin", TaskProcessin);

        //获取当前用户发出的沟通信息

        QueryWrapper<SysCoordinationTask> wrapper = new QueryWrapper<>();
        wrapper.eq("CT_FileID", fileId);
        wrapper.eq("CT_SendUser", uId);

        List<SysCoordinationTask> list = sysCoordinationTaskService.list(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            List<String> uids = list.stream().map(n -> n.getCtUser()).collect(Collectors.toList());
            List<SysUser> uList = sysUserService.getUserAllByUids(uids, customerId);
            if (!CollectionUtils.isEmpty(uList)) {
                String userName = uList.stream().map(n -> n.getuName()).collect(Collectors.joining("，"));
                task.put("commUserName", userName);
            }
        }
        return new BaseResponse("获取成功", task, 1);
    }


    @Override
    public BaseResponse deleteTaskProcessById(String id, String tCode, String year) {
        deleteTaskProcessin(id, tCode, year);
        return new BaseResponse(true, "删除成功");
    }

    @Override
    public BaseResponse updateTaskProcess(SysTaskProcessinUpdateDto request) {
        TaskProcessin obj = getTaskProcessById(request.getTpId(), request.gettCode(), request.getYear());
        if (obj != null) {
            if (ValidateUtil.isNotEmpty(request.getActName())) {
                obj.setActname(request.getActName());
            }
            if (ValidateUtil.isNotEmpty(request.getTpactid())) {
                obj.setTpActid(request.getTpactid());
            }
            if (ValidateUtil.isNotEmpty(request.getTpExplain())) {
                obj.setTpExplain(request.getTpExplain());
            }
            if (request.getTpDotime() != null) {
                obj.setTpDotime(request.getTpDotime());
            }
            if (ValidateUtil.isNotEmpty(request.getTpUser())) {
                obj.setTpUser(request.getTpUser());
            }

            if (ValidateUtil.isNotEmpty(request.getTpResult())) {
                obj.setTpResult(request.getTpResult());
            }

            if (ValidateUtil.isNotEmpty(request.getTpType())) {
                obj.setTpType(request.getTpType());
            }

            if (request.getTpStarttime() != null) {
                obj.setTpStarttime(request.getTpStarttime());
            }
            updateTaskProcessById(obj, request.gettCode(), request.getYear());
            return new BaseResponse(true, "修改成功");
        } else {
            return new BaseResponse(false, "参数错误");
        }

    }

    @Override
    public BaseResponse getMinTimeAndMaxTime(String tCode, String customerId, String year) {

        return new BaseResponse(true, "获取成功", baseMapper.getMinTimeAndMaxTime(getTableName(tCode, year)));
    }


    @Override
    public List<SysTaskProcessinInfoVo> getSysTaskProcessinInfo(List<String> fileIds, String customerId) {
        return baseMapper.getTaskProcessinNotTime(fileIds, "All".equals(customerId) ? null : customerId);
    }


    @Override
    public BaseResponse createTaskProcess(TaskProcessin request, String tCode, String year) {
        insertTaskProcessin(request, tCode, year);
        return BaseResponse.ok("保存成功！");
    }


    @Override
    public BaseResponse getUserTaskProcessListCJ(String userId, TaskCoordinationTaskVo request, String customerId) {
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        QueryMap<TaskCoordinationTaskVo> map = new QueryMap<>(request);
        map.put("userIds", userIds);
        map.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        map.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        map.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "dotime");
        map.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "desc");
        map.put("taskname", !ValidateUtil.isEmpty(request.getTaskName()) ? request.getTaskName() : null);
        map.put("tasknames", !ValidateUtil.isEmpty(request.getTaskNames()) ? request.getTaskNames() : null);
        map.put("pNames", !CollectionUtils.isEmpty(request.getpNames()) ? request.getpNames() : null);
        map.put("conNames", !CollectionUtils.isEmpty(request.getConNames()) ? request.getConNames() : null);
        map.put("secNames", !CollectionUtils.isEmpty(request.getSecNames()) ? request.getSecNames() : null);
        map.put("createNames", !CollectionUtils.isEmpty(request.getCreateNames()) ? request.getCreateNames() : null);
        map.put("doUserNames", !CollectionUtils.isEmpty(request.getDoUserNames()) ? request.getDoUserNames() : null);
        map.put("fileCreateNames", !CollectionUtils.isEmpty(request.getFileCreateNames()) ? request.getFileCreateNames() : null);
        map.put("startCreateTime", !ValidateUtil.isEmpty(request.getStartTime()) ? request.getStartTime() : null);
        map.put("endCreateTime", !ValidateUtil.isEmpty(request.getEndTime()) ? request.getEndTime() : null);
        map.put("startDoTime", !ValidateUtil.isEmpty(request.getStartDoTime()) ? request.getStartDoTime() : null);
        map.put("endDoTime", !ValidateUtil.isEmpty(request.getEndDoTime()) ? request.getEndDoTime() : null);
        List<String> createtimes = new ArrayList<>();
        // 格式化日期格式
        if (ValidateUtil.isNotEmpty(request.getCreatetimes())) {
            for (String createtime : request.getCreatetimes()) {
                if (createtime == null) {
                    continue;
                }
                String normalized = createtime.replace("Z", "").replace("T", " ");
                String datePart = normalized.substring(0, 10);
                createtimes.add(datePart);
            }
        }
        map.put(createtimes, "startCreateTime", "endCreateTime");
        List<String> doTimes = new ArrayList<>();
        // 格式化日期格式
        if (ValidateUtil.isNotEmpty(request.getDoTimes())) {
            for (String doTime : request.getDoTimes()) {
                if (doTime == null) {
                    continue;
                }
                String normalized = doTime.replace("Z", "").replace("T", " ");
                String datePart = normalized.substring(0, 10);
                doTimes.add(datePart);
            }
        }
        map.put(doTimes, "startDoTime", "endDoTime");

        List<TaskCoordinationTaskVo> list = baseMapper.getUserTaskProcessListCJ(map.getQuery());
        List<SysUser> users = sysUserService.getAllUser(customerId);
        List<String> entityIds = list.stream().map(n -> n.getFileId()).distinct().collect(Collectors.toList());
        List<String> actIds = list.stream().map(n -> n.getActId()).distinct().collect(Collectors.toList());
        List<Task> taskList = taskService.getTaskListByFiles(entityIds);
        List<FlowActivity> actList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(taskList)) {
            actIds.addAll(taskList.stream().map(n -> n.getTaskcurrentactid()).distinct().collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(actIds)) {
            actList = flowActivityService.listByIds(actIds);
        }

        List<TaskCoordinationTaskVo> newData = new ArrayList<>();
        for (TaskCoordinationTaskVo data : list) {


            //获取任务名称
            List<Task> tList = taskList.stream().filter(n -> n.getTaskentityid().equals(data.getFileId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tList)) {
                List<Map<String, Object>> tUserList = new ArrayList<>();
                for (Task t : tList
                ) {
                    String actName = "";
                    String taskUserName = "";
                    Integer num = 0;
                    List<FlowActivity> acts = actList.stream().filter(b -> b.getActid().equals(t.getTaskcurrentactid())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(acts)) {
                        actName = acts.get(0).getActname();
                        if (ValidateUtil.isNotNull(acts.get(0).getActorder())) {
                            num = acts.get(0).getActorder();
                        }
                    }
                    List<SysUser> userList = users.stream().filter(b -> b.getuId().equals(t.getTaskuser())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(userList)) {
                        taskUserName = userList.get(0).getuName();
                    }
                    Map<String, Object> ob = new HashMap<>();
                    ob.put("actName", actName);
                    ob.put("taskUserName", taskUserName);
                    tUserList.add(ob);
                }
                data.setTaskUsetAndActList(tUserList);
            }

            newData.add(data);
        }

        Integer total = baseMapper.getUserTaskProcessCountCJ(map.getQuery());
        List<Map<String, Object>> keys = new ArrayList<>();
        Map<String, Object> obj = new HashMap<>();
        obj.put("keys", baseMapper.getUserTaskProcessKeyCJ(map.getQuery(), "t2.taskName"));
        obj.put("name", "任务名称");
        obj.put("code", "taskNames");
        obj.put("type", "string");
        keys.add(obj);
        Map<String, Object> obj2 = new HashMap<>();
        obj2.put("keys", baseMapper.getUserTaskProcessKeyCJ(map.getQuery(), "pre_project.P_Name"));
        obj2.put("name", "所属项目");
        obj2.put("code", "pNames");
        obj2.put("type", "string");
        keys.add(obj2);
        Map<String, Object> obj0 = new HashMap<>();
        obj0.put("keys", baseMapper.getUserTaskProcessKeyCJ(map.getQuery(), "sys_file.FileCreateUserName"));
        obj0.put("name", "发起人");
        obj0.put("code", "fileCreateNames");
        obj0.put("type", "string");
        keys.add(obj0);
        Map<String, Object> obj1 = new HashMap<>();
        obj1.put("keys", baseMapper.getUserTaskProcessKeyCJ(map.getQuery(), "u.U_Name"));
        obj1.put("name", "发送人");
        obj1.put("code", "createNames");
        obj1.put("type", "string");
        keys.add(obj1);
        Map<String, Object> obj3 = new HashMap<>();
        obj3.put("keys", "");
        obj3.put("name", "接收时间");
        obj3.put("code", "createtimes");
        obj3.put("type", "dateRange");
        keys.add(obj3);
        Map<String, Object> obj4 = new HashMap<>();
        obj4.put("keys", "");
        obj4.put("name", "处理时间");
        obj4.put("code", "doTimes");
        obj4.put("type", "dateRange");
        keys.add(obj4);
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("list", newData);
        returnData.put("keys", keys);
        return BaseResponse.ok(returnData, total);
    }

    @Override
    public BaseResponse checkUserInApprovalProcess(List<String> ids, LogUserInfoVo user) {
        if (ValidateUtil.isEmpty(ids) || ValidateUtil.isNull(user)) {
            return BaseResponse.fail("参数错误");
        }
        // 返回体
        Map<String, Boolean> result = new HashMap<>();
        if (sysUserService.checkShowAllDataAuth(user.getId())) {
            ids.forEach(id -> result.put(id, true));
            return BaseResponse.ok(result);
        }
        // 查询这些id的审批记录中的uId
        List<WorkFlowFileDto> list = baseMapper.getApprovalUserByIds(ids);
        if (ValidateUtil.isEmpty(list)) {
            list = Collections.emptyList();
        }
        // 分组
        Map<String, List<String>> map = list.stream()
                .filter(Objects::nonNull)
                .filter(x -> x.getFileid() != null && x.getUid() != null)
                .collect(Collectors.groupingBy(
                        WorkFlowFileDto::getFileid,
                        Collectors.mapping(WorkFlowFileDto::getUid, Collectors.toList())
                ));

        String fileCode = sysConfigEntityService.getConfByCode("SectionWinNotice");
        // 查询file表，找到匹配fileCode与ids的
        List<File> files = fileService.list(Wrappers.<File>lambdaQuery().in(File::getFileid, ids));
        Map<String, File> fileMap = files.stream()
                .filter(Objects::nonNull)
                .filter(n -> n.getFileid() != null)
                .collect(Collectors.toMap(File::getFileid, n -> n, (n1, n2) -> n1));
        boolean expansion = files.stream()
                .filter(Objects::nonNull)
                .filter(n -> fileCode != null && fileCode.equalsIgnoreCase(n.gettCode()))
                .anyMatch(n -> Objects.equals(n.getFileflowstatus(), ApprovalStatusEnum.Already_Over.getIndex()));
        List<String> createUsers = files.stream()
                .filter(Objects::nonNull)
                .map(File::getFilecreateuser)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        Map<String, String> createUserCompanyMap = new HashMap<>();
        if (!ValidateUtil.isEmpty(createUsers)) {
            List<SysUser> sysUsers = sysUserService.list(Wrappers.<SysUser>lambdaQuery()
                    .in(SysUser::getuId, createUsers)
                    .select(SysUser::getuId, SysUser::getcId)
            );
            createUserCompanyMap = sysUsers.stream()
                    .filter(Objects::nonNull)
                    .filter(n -> n.getuId() != null && n.getcId() != null)
                    .collect(Collectors.toMap(SysUser::getuId, SysUser::getcId, (c1, c2) -> c1));
        }
        String njcj = sysConfigEntityService.getConfByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        final String njcjCompanyId = njcj;
        final Map<String, String> creatorCompanyMap = createUserCompanyMap;

        ids.forEach(id -> {
            List<String> uIds = map.getOrDefault(id, Collections.emptyList());
            boolean oldPermission = uIds.contains(user.getId());
            File file = fileMap.get(id);
            boolean njcjPermission = false;
            boolean sameCompanyPermission = false;
            if (expansion) {
                njcjPermission = user.getCId() != null && njcjCompanyId.equalsIgnoreCase(user.getCId());
                sameCompanyPermission = user.getCId() != null
                        && file != null
                        && file.getFilecreateuser() != null
                        && user.getCId().equalsIgnoreCase(creatorCompanyMap.get(file.getFilecreateuser()));
            }
            result.put(id, oldPermission || njcjPermission || sameCompanyPermission);
        });
        return BaseResponse.ok(result);
    }
}
