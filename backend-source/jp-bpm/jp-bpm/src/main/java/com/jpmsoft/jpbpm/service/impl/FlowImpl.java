package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.SysFlowRequest;
import com.jpmsoft.common.model.response.SysFlowResponse;
import com.jpmsoft.common.model.response.TaskProcessResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.FlowMapper;
import com.jpmsoft.jpbpm.pojo.dto.*;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.FileSecidVo;
import com.jpmsoft.jpbpm.pojo.vo.FileSecidsVo;
import com.jpmsoft.jpbpm.pojo.vo.UserSelectionVo;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import com.jpmsoft.jpbpm.util.DateUtil;
import com.jpmsoft.jpbpm.util.FlowSubmitRules;
import com.jpmsoft.jpbpm.util.fileLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FlowImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {
    @Value("${bpm.logSavePath}")
    public String logSavePath;
    @Autowired
    SysConfigEntityService sysConfigEntityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FlowActivityService flowActivityService;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    private TaskProcessUserService taskProcessUserService;
    @Autowired
    private CarbonCopyService carbonCopyService;
    @Autowired
    private FlowRuleService flowRuleService;
    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysShortMessageService sysShortMessageService;
    @Autowired
    private SysCoordinationProcessService sysCoordinationProcessService;
    @Autowired
    private SysTaskBackupsService sysTaskBackupsService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private FileTemplateService fileTemplateService;

    @Autowired
    private SysCoordinationTaskBackupsService sysCoordinationTaskBackupsService;

    @Autowired
    SysTaskProcessinTimeService sysTaskProcessinTimeService;

    @Autowired
    SysCoordinationTaskService sysCoordinationTaskService;

    @Autowired
    ISysMessageService sysMessageService;
    @Autowired
    ISysTaskReturnMapService sysTaskReturnMapService;
    @Autowired
    SysFlowNextUserSelectService sysFlowNextUserSelectService;

    @Override
    public BaseResponse getStartActivityInfo(StartActivityInfoDto request, String customerId) {
        String taskid = request.getTaskid();//任务id
        String templatecode = request.getTemplatecode();//模板编码
        if (ValidateUtil.isEmpty(taskid) && !ValidateUtil.isEmpty(templatecode)) {
            QueryWrapper<Flow> wrapper = new QueryWrapper<>();
            wrapper.eq("T_Code", templatecode);
            wrapper.eq("FlowEnable", "1");
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            Flow flowentyobj = baseMapper.selectOne(wrapper);
            if (flowentyobj != null) {
                List<FlowActivity> FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowentyobj.getFlowid(), customerId);
                //获取当前流程下所有的连接线
                List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowentyobj.getFlowid(), customerId);
                FlowActivity FlowActivityobjs = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                if (FlowActivityobjs != null)
                    //成功获取到当前流程节点
                    return new BaseResponse(true, "获取成功", FlowActivityobjs);
                else return new BaseResponse("流程节点获取异常!");

            } else return new BaseResponse("未设置主版本流程!");
        } else if (!ValidateUtil.isEmpty(taskid)) {
            //根据任务id获取当前活动信息
            Task taskobjs = taskService.getById(taskid);
            if (taskobjs != null) {
                FlowActivity FlowRuleList = flowActivityService.getById(taskobjs.getTaskcurrentactid());
                return new BaseResponse(true, "获取成功", FlowRuleList);
            } else return new BaseResponse("参数输入错误!");

        } else return new BaseResponse("参数丢失!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse submitFlow(SubmitFlowRequstDto request, String customerId, String token) {
        fileLog fl = new fileLog();
        //声明存放submitFlow方法运行日志
        List<String> llowLogs = new ArrayList<>();
        llowLogs.add("时间：" + new Date() + "；进入submitFlowf方法；");
        int status_str = 0;
        List<String> massage = new ArrayList<>();
        String taskid = request.getTaskid();//任务id
        String templatecode = request.getTemplatecode();//模板编码
        String Reverseconditions = request.getCondition();//扭转条件
        //String GetFlagstr = "";//流程提交方式（是的原路返回）
        String p_id = request.getProjectid();//项目id
        String c_id = request.getCompanyid();//单位id
        String solutionid = request.getSolutionid();//文档所属编码
        String Activityid = request.getActivityid();//当前审批节点id
        String explain = request.getExplain();//审批意见
        String taskname = request.getTaskname();//任务名称
        String user_id = request.getUser_id();//当前用户id
        String file_id = request.getFile_id();//文档id
        String yearStr = request.getYear();
        String endStr = "";
        String operationtype = request.getOperationtype();//操作类型
        List<userselectionDto> nextuser = request.getNextuser();//用户选择的用户信息
        List<String> cc_Users = request.getCc_user();//用户选择的抄送用户信息
        String testtype = request.getTesttype();
        String requestSource = request.getRequestSource();
        File filePojo = fileService.getFileById(file_id, request.gettCode(), yearStr);
        if (ValidateUtil.isEmpty(p_id) && filePojo != null) {
            p_id = filePojo.getFileproject();
            if (filePojo.getFilecreatedate() != null) {
                yearStr = new SimpleDateFormat("yyyy").format(filePojo.getFilecreatedate());
            }
        }
        if (ValidateUtil.isEmpty(taskname)) {
            //任务名称没有
            FileTemplate ft = fileTemplateService.getTemplatebyTCode(filePojo.gettCode(), customerId);
            if (ft != null) {
                taskname = ft.gettName();
            } else {
                return BaseResponse.fail("流程提交失败，未设置任务名称");
            }
        }
        Map<String, Object> newConditions = flowActivityService.setTorsionConditions(file_id, user_id, Reverseconditions, customerId, request.gettCode(), yearStr);
        taskname = taskNameReplacement(file_id, templatecode, taskname, newConditions, customerId, yearStr);
        //验证用户选择的下一步节点
        if ((nextuser != null && nextuser.size() > 0) || operationtype.equals("end") || operationtype.equals("return")) {
            int i = 0;
            for (userselectionDto item : nextuser) {
                if (ValidateUtil.isEmpty(item.getActityid()) || ValidateUtil.isEmpty(item.getUserid())) {
                    i = 1;
                    break;
                }
            }
            if (i == 1) return new BaseResponse("下一步审批人信息丢失");
        } else return new BaseResponse("请选择下一步审批人");

        FlowActivity FlowActivityobj = null;
        Task Taskobj = null;//任务信息；
        String flowid = null;
        List<FlowActivity> FlowActivitys = new ArrayList<>();
        List<FlowRule> FlowRules = new ArrayList<>();
        if (ValidateUtil.isEmpty(request.getFlowid())) {
            try {
                flowid = GetEnableFlowID(solutionid, c_id, p_id, templatecode, customerId);
            } catch (Exception ex) {
                log.info("GG1");
                log.error(ex.getMessage(), ex);
                //报错了、不继续执行
                if (ValidateUtil.isNotEmpty(filePojo.getFlowId())) {
                    flowid = filePojo.getFlowId();
                } else {
                    return BaseResponse.fail("流程图设置异常，当前流程无法提交");
                }
            }
        } else {
            flowid = request.getFlowid();
        }

        Flow flow = baseMapper.selectById(flowid);
        if (!ValidateUtil.isEmpty(Activityid)) {
            FlowActivityobj = flowActivityService.getById(Activityid);
        } else if (ValidateUtil.isEmpty(taskid) && !ValidateUtil.isEmpty(templatecode)) {
            llowLogs.add("（行：208）时间：" + new Date() + "；没有传任务id,当前流程为新发起");
            //没有任务id，当前操作为发起新的流程
            //获取开始节点流程任务活动节点
            FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
            //获取当前流程下所有的连接线
            FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
            FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
            llowLogs.add("（行：215）时间：" + new Date() + "；获取流程流程图结束；");
        } else {
            Taskobj = taskService.getById(taskid);
            if (Taskobj != null) {
                if (ValidateUtil.isEmpty(file_id)) {
                    file_id = Taskobj.getTaskentityid();
                }
                if (ValidateUtil.isEmpty(flowid)) {
                    flowid = filePojo.getFlowId();
                }
                String actid = Taskobj.getTaskcurrentactid();
                String GetFlagstr = Taskobj.getTaskgetflagstr();
                flow = baseMapper.selectById(flowid);
                FlowActivityobj = flowActivityService.getById(actid);//获取当前任务id
                Float Actityhour = null;
                if (FlowActivityobj.getActhour() != null) {
                    Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
                }
                if (filePojo == null) {
                    filePojo = fileService.getFileById(Taskobj.getTaskentityid(), request.gettCode(), yearStr);
                    Taskobj.setFlowId(flowid);
                    Taskobj.setSecId(filePojo.getFilesectionid());
                    Taskobj.setProjectId(filePojo.getFileproject());
                    Taskobj.setContractId(filePojo.getFilecontract());
                    Taskobj.setTCode(filePojo.gettCode());
                }
                if (GetFlagstr != null && GetFlagstr.trim().equals("1")) {
                    //原路返回
                    llowLogs.add("（行：243）时间：" + new Date() + "；当前流程提交为原路返回");
                    //判断当流程的所属流程图是否为主版本
                    String flowidold = Taskobj.getTaskflow();
                    if (ValidateUtil.isEmpty(flowidold)) {
                        flowidold = filePojo.getFlowId();
                    }
                    Flow flowobj = baseMapper.selectById(flowidold);
                    //获取流程的基础配置信息，判断退回发起人后，流程是否是按照新的流程走还是老的流程走
                    List<String> codeList = new ArrayList<>();
                    codeList.add("processSubmission");
                    Map<String, Object> configelist = sysConfigEntityService.getConfigEntity(codeList, customerId);
                    Object processSubmission = configelist.get("processSubmission");

                    String isReturn = "1";//默认原路返回
                    if ((flowobj != null && flowobj.getFlowenable() != null && flowobj.getFlowenable().equals("1")) || (processSubmission != null && processSubmission.equals("returnOld"))) {
                        isReturn = "1";
                    } else if (!"start".equals(Taskobj.getTaskReturnType())) {
                        isReturn = "1";
                    } else {
                        isReturn = "0";
                    }

                    if (isReturn.equals("1")) {
                        llowLogs.add("（行：266）时间：" + new Date() + "；开始生成流程提交记录");
                        //{生成任务信息 }----------------------------------------------------------------------------------------
                        TaskProcessin his = taskProcessinService.GetHis(Taskobj, "同意", explain, user_id, "提交", flow, Taskobj.getTasksenduser(), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体
                        int i1 = taskProcessinService.insertTaskProcessin(his, request.gettCode(), yearStr);
                        llowLogs.add("（行：270）时间：" + new Date() + "；流程提交记录保存，影响数：" + i1);

                        sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                        String Nextuser = FindsubmitterMan(Taskobj.getTaskentityid(), Taskobj.getTaskflow(), Taskobj.getTaskcurrentactid(), Taskobj.getTaskpreactid(), user_id, customerId, request.gettCode(), yearStr);

                        if (ValidateUtil.isEmpty(Nextuser)) {
//                            if (!Taskobj.getTasksenduser().equals(user_id)) {
                            Nextuser = Taskobj.getTasksenduser();
//                            } else {
//                                return new BaseResponse("流程提交失败，获取下一步办理人失败，无法原路返回！");
//                            }
                        }
                        //判断当前进班人和下一步经办人是否是同一个人，如果是同一个人，则可能纯在问题，进行补偿获取
                        if (user_id.equals(Nextuser)) {
                            //获取下一步节点的，节点id，根据节点id获取历史审批记录
                            QueryWrapper<TaskProcessin> qw = new QueryWrapper<>();
                            qw.eq("tp_entityid", file_id);
                            qw.eq("tp_actid", Taskobj.getTaskpreactid());
                            qw.eq("tp_type", "退回");
                            qw.ne("tp_user", user_id);
                            qw.orderByDesc("tp_dotime");
                            List<TaskProcessin> tps = taskProcessinService.list(qw);
                            if (!CollectionUtils.isEmpty(tps)) {
                                Nextuser = tps.get(0).getTpUser();
                            }
                        }

                        llowLogs.add("（行：282）时间：" + new Date() + "；获取流程原路返回的下一步经办人为：" + Nextuser);
                        Task task_sqlstr = GetNewTask(GenerateUUID.getUUID(), Taskobj.getTaskpreactid(), actid, his, Taskobj, taskname, Nextuser, flow, null, new Date(), new Date(), "", null, null, null, null, null, customerId);
                        //原路返回的流程，重新生成原来冻结的流程信息
                        List<SysTaskBackups> taskBackupsList = sysTaskBackupsService.getListByTaskId(taskid, customerId);
                        if (taskBackupsList.size() > 0) {
                            llowLogs.add("（行：287）时间：" + new Date() + "；开始恢复冻结流程：涉及流程数量：" + taskBackupsList.size());
                            for (SysTaskBackups tobj : taskBackupsList) {
                                Task taskEnt = new Task();
                                BeanUtils.copyProperties(tobj, taskEnt);
                                //检查冻结的流程和生成的流程是否重复
                                if (!(task_sqlstr.getTaskuser().equals(tobj.getTaskuser()) && task_sqlstr.getTaskcurrentactid().equals(tobj.getTaskcurrentactid()))) {
                                    taskEnt.setTasksendtime(new Date());
                                    taskEnt.setTaskstarttime(new Date());
                                    try {
                                        BigDecimal bg = DateUtil.getDifferHour(tobj.getTaskstarttime().toString(), tobj.getTaskendtime().toString());
                                        taskEnt.setTaskendtime(DateUtil.DateChangeHour(taskEnt.getTaskstarttime(), Double.parseDouble(bg.toString())));
                                    } catch (Exception ex) {
                                        log.error("流程服务：", ex);
                                    }
                                    taskEnt.setContractId(filePojo.getFilecontract());
                                    taskEnt.setProjectId(filePojo.getFileproject());
                                    taskEnt.setSecId(filePojo.getFilesectionid());
                                    boolean i2 = taskService.save(taskEnt);
                                    llowLogs.add("（行：305）时间：" + new Date() + "；冻结流程任务保存，保存结果：" + i2);
                                }
                            }

                            sysTaskBackupsService.deleteByTaskId(taskid, customerId);
                            llowLogs.add("（行：310）时间：" + new Date() + "；删除冻结的流程任务");
                            List<String> taskId = taskBackupsList.stream().map(n -> n.getTaskid()).collect(Collectors.toList());
                            sysCoordinationTaskBackupsService.recoverySysCoordinationTask(taskId, customerId);
                            llowLogs.add("（行：313）时间：" + new Date() + "；删除冻结沟通任务");
                        }
                        task_sqlstr.setContractId(filePojo.getFilecontract());
                        task_sqlstr.setProjectId(filePojo.getFileproject());
                        task_sqlstr.setSecId(filePojo.getFilesectionid());
                        boolean i2 = taskService.save(task_sqlstr);
                        llowLogs.add("（行：319）时间：" + new Date() + "；正常提交的流程任务保存，保存结果：" + i2);
                        //生成发送记录信息（由那个节点发送到那个节点）
                        taskProcessUserService.GetNewTaskProcessUse(his.getTpId(), task_sqlstr.getTaskuser(), task_sqlstr.getTaskcurrentactid(), customerId, request.gettCode(), yearStr);
                        boolean tdc = taskService.removeById(Taskobj.getTaskid());//删除原有的待办信息
                        llowLogs.add("（行：323）时间：" + new Date() + "；删除当前处理任务，结果:" + tdc);
                        //生成抄送信息
                        carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, Taskobj.getTaskentityid(), Taskobj.getTaskname(), his.getTpId(), cc_Users, "1", customerId, token, p_id, request.gettCode(), yearStr);
                        fileService.updatefile(templatecode, file_id, "next", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                        llowLogs.add("（行：327）时间：" + new Date() + "；修改file表流程状态");
                        //判断当前审批节点是否需要发送短信
                        List<String> nextuser_o = new ArrayList<>();
                        nextuser_o.add(Taskobj.getTasksenduser());
                        //TODO:生成短信息和邮件等通知信息新增sql语句
                        //sql_all_str.AddRange(Get_shortmessage(FlowActivityobj, Taskobj, nextuser_o, explain, user_id));
                        List<String> usetlist = new ArrayList<>();
                        usetlist.add(Nextuser);

                        sysShortMessageService.generateSMS("", usetlist, file_id, Taskobj.getTaskcurrentactid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                        sysMessageService.generateMassage("", usetlist, file_id, Taskobj.getTaskcurrentactid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);

                        llowLogs.add("（行339）时间：" + new Date() + "；原路返回流程提交处理结束");
                        //原路返回流程提交结束

                        // 流程提交成功，删除掉待沟通的任务
                        sysCoordinationTaskService.deleteByEntityId(file_id, customerId);
                        llowLogs.add("（行：344）时间：" + new Date() + "；根据file_id(" + file_id + ")删除沟通信息");
                        //删除当前任务节点的驳回记录信息
                        List<SysTaskReturnMap> strmList = sysTaskReturnMapService.selectByFileId(file_id);
                        if (!CollectionUtils.isEmpty(strmList)) {
                            String actId = FlowActivityobj.getActid();
                            List<SysTaskReturnMap> actMapList = strmList.stream().filter(n -> n.getOperateTargetAct().equals(actId)).collect(Collectors.toList());
                            for (SysTaskReturnMap it : actMapList) {
                                sysTaskReturnMapService.DeleteById(it.getId());
                            }
                            llowLogs.add("（行：353）时间：" + new Date() + "；删除SysTaskReturnMap数据，共删除" + actMapList.size());
                        }
                        //记录提交日志信息
                        fl.write_to_txt_batch(logSavePath, user_id, llowLogs);
                        return new BaseResponse(true, "流程提交成功！", his.getTpId());
                    } else {
                        //获取当前任务活动id
                        FlowActivityobj = flowActivityService.getById(actid);
                        //获取开始节点流程任务活动节点
                        FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
                        //获取当前流程下所有的连接线
                        FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
                        if (!FlowActivityobj.getActflowid().equals(flowid)) {
                            //获取到的节点所属流程与要走流程不一致时
                            FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                        }
                        llowLogs.add("（行：369）时间：" + new Date() + "；非原路返回流程提交获取相关流程图信息1");
                    }
                } else {
                    if (ValidateUtil.isEmpty(flowid)) {
                        flowid = filePojo.getFlowId();
                    }
                    //flowid = Taskobj.getTaskflow();
                    //获取当前任务活动id
                    FlowActivityobj = flowActivityService.getById(actid);
                    //获取开始节点流程任务活动节点
                    FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
                    //获取当前流程下所有的连接线
                    FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
                    if (!FlowActivityobj.getActflowid().equals(flowid)) {
                        //获取到的节点所属流程与要走流程不一致时
                        FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                        llowLogs.add("（行：385）时间：" + new Date() + "；非原路返回流程提交获取相关流程图信息2");
                    }
                }
            } else {
                return new BaseResponse(false, "当前待办任务已处理！请勿重复提交", "");
            }


        }
        //获取当前文档下所有的审批记录信息
        List<TaskProcessin> Task_Processinlist = taskProcessinService.getListByTpFlowIdAndTpEntityId(flowid, file_id, customerId, yearStr);
        llowLogs.add("（行：396）时间：" + new Date() + "；获取审批记录信息");
        List<Task> new_task = new ArrayList<>();
        String Taskproseeid = "";
        int smstypen = 0;
        //设置流程条件判断的任务数
        FlowSubmitRules.all_count = 0;
        QueryWrapper<Task> taskcoyunt = new QueryWrapper<>();
        taskcoyunt.eq("TaskEntityID", file_id);
        taskcoyunt.ne("TaskID", taskid);
        if (!"All".equals(customerId)) {
            taskcoyunt.eq("Te_Id", customerId);
        }
        List<Task> remainingTasks = taskService.list(taskcoyunt);
        FlowSubmitRules.current_count = remainingTasks.size();
        llowLogs.add("（行：410）时间：" + new Date() + "；判断当前流程处理节点是否为空");
        if (FlowActivityobj != null) {
            llowLogs.add("（行：412）时间：" + new Date() + "；下一步流程提交");
            //获取有没有需要原路返回的
            List<SysTaskReturnMap> strmListP = sysTaskReturnMapService.selectByFileId(file_id);
            if (!CollectionUtils.isEmpty(strmListP)) {
                String newActId = FlowActivityobj.getActid();
                List<SysTaskReturnMap> lis = strmListP.stream().filter(n -> n.getOperateTargetAct().equals(newActId)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(lis)) {
                    //判断当前虚拟连接线是否有，没有就新建一个
                    String endActID = lis.get(0).getOperateAct();
                    List<FlowRule> rulesLis = FlowRules.stream().filter(k -> k.getRulebeginactid().equals(newActId) && k.getRuleendactid().equals(endActID)).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(rulesLis)) {
                        //生成新的连接线
                        FlowRule o = new FlowRule();
                        o.setRulebeginactid(newActId);
                        o.setRuleendactid(endActID);
                        o.setRulesystype("提交");
                        o.setRuleflowid(FlowActivityobj.getActid());
                        FlowRules.add(o);
                    }
                }
            }

            //获取当前节点的原路返回线上虚拟路线
            //正常流程提交（包含流程开始和流程提交），程序实现开始---------------------------
            if (Taskobj != null) {
                //提交流程
                //判断当前任务的沟通是否已经结束
                if (Taskobj.getTaskcoordination() != null && Taskobj.getTaskcoordination().equals("YES")) {
                    //删除沟通信息
                    sysCoordinationTaskService.deleteByEntityId(file_id, customerId);
                }
                // return new BaseResponse("流程提交失败，当前任务节点存在其它沟通任务，请等待沟通完成后，再次提交流程！");
                else {
                    Task currentAct = Taskobj;
                    llowLogs.add("时间：" + new Date() + "；");
                    //当前活动为子流程的结束活动
                    if (FlowActivityobj.getActtype().equals("COMPLETION") && !ValidateUtil.isEmpty(currentAct.getTaskparentflowid())) {
                        //TODO:结束子流程
                        return new BaseResponse(true, "", "");
                    } else {
                        //当前活动为子流程启动前的活动
                        if (FlowActivityobj.getActtype().equals("SUBPROCESS")) {
                            //TODO:启动子流程
                            return new BaseResponse(true, "", "");
                        }
                    }

                    if (ValidateUtil.isEmpty(operationtype)) return new BaseResponse("流程提交错误，参数丢失");
                    else if (operationtype.equals("next")) {
                        List<TaskProcessin> Task_Processinnewlist = new ArrayList<>();
                        Float Actityhour = null;
                        if (FlowActivityobj.getActhour() != null) {
                            Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
                        }
                        if (!Taskobj.getTaskcurrentactid().equals(FlowActivityobj.getActid())) {
                            Taskobj.setTaskcurrentactid(FlowActivityobj.getActid());
                        }

                        TaskProcessin his_new = taskProcessinService.GetHis(Taskobj, "同意", explain, user_id, "提交", flow, String.join(",", nextuser.stream().map(userselectionDto::getUserid).collect(Collectors.toList())), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体y()));
                        Task_Processinlist.add(his_new);
                        List<String> needDeleteTaskId = new ArrayList<>();
                        Map<String, String> data = new HashMap<>();
                        data.put("Task_Processinnewlist", JacksonUtils.toJSONString(Task_Processinnewlist));
                        data.put("massage", JacksonUtils.toJSONString(massage));
                        data.put("new_task", JacksonUtils.toJSONString(new_task));
                        data.put("status_str", status_str + "");
                        data.put("addsSecond", "1");
                        data.put("needDeleteTaskId", JacksonUtils.toJSONString(needDeleteTaskId));
                        data = flowActivityService.GetSYSFlowActivityEntitysubmit(FlowActivityobj, newConditions, FlowActivitys, FlowRules, Task_Processinlist, nextuser, his_new, flow, Taskobj, explain, data, taskname, remainingTasks, customerId, requestSource);
                        new_task = JacksonUtils.parseObject(data.get("new_task"), new TypeReference<List<Task>>() {
                        });
                        massage = JacksonUtils.parseObject(data.get("massage"), List.class);
                        Task_Processinnewlist = JacksonUtils.parseObject(data.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
                        });
                        needDeleteTaskId = JacksonUtils.parseObject(data.get("needDeleteTaskId"), new TypeReference<List<String>>() {
                        });
                        //判断当前节点是否时并发审批节点
                        if (ValidateUtil.isNotEmpty(FlowActivityobj.getActConcurrencyEnd())) {
                            //删除当前节点，提交的记录信息
                            QueryWrapper<SysFlowNextUserSelect> sysFlowNextUserSelect = new QueryWrapper<>();
                            sysFlowNextUserSelect.eq("file_Id", file_id);
                            sysFlowNextUserSelect.eq("flow_Id", FlowActivityobj.getActflowid());
                            sysFlowNextUserSelect.eq("operate_Act_Id", FlowActivityobj.getActid());
                            List<SysFlowNextUserSelect> oldFnusList = new ArrayList<>();
                            try {
                                oldFnusList = sysFlowNextUserSelectService.list(sysFlowNextUserSelect);
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error("流程服务：缺少Sys_Flow_Next_User_Select表");
                            }
                            if (!CollectionUtils.isEmpty(oldFnusList)) {
                                List<SysFlowNextUserSelect> currentOperatorList = oldFnusList.stream().filter(n -> n.getOperateUid() != null && n.getOperateUid().equals(user_id)).collect(Collectors.toList());
                                if (!CollectionUtils.isEmpty(currentOperatorList)) {
                                    try {
                                        sysFlowNextUserSelectService.removeBatchByIds(currentOperatorList.stream().map(n -> n.getId()).collect(Collectors.toList()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        log.error("流程服务：缺少Sys_Flow_Next_User_Select表");
                                    }
                                }
                            }
                            if (FlowActivityobj.getActConcurrencyEnd().equals("0")) {
                                //任何一人审核通过
                                taskService.deleteTaskByEntityIdAndCurrentActId(file_id, FlowActivityobj.getActid(), Taskobj.getTaskid(), customerId);
                                llowLogs.add("（行：515）时间：" + new Date() + "；删除当前节点下处理任务");
                            } else {
                                //全部审核通过
                                List<Task> actTaskList = taskService.getTaskByEntityIdAndCurrentActId(file_id, FlowActivityobj.getActid(), customerId);
                                actTaskList = actTaskList.stream().filter(n -> !n.getTaskid().equals(taskid)).collect(Collectors.toList());
                                if (actTaskList.size() > 0) {
                                    //当前节点存在未审核完成的待办信息，不能生成新的待办,生成提交记录信息
                                    List<SysFlowNextUserSelect> fnusList = new ArrayList<>();
                                    for (int i = 0; i < new_task.size(); i++) {
                                        Task newObj = new_task.get(i);
                                        List<FlowActivity> nextActList = FlowActivitys.stream().filter(n -> n.getActid().equals(newObj.getTaskcurrentactid())).collect(Collectors.toList());
                                        if (!CollectionUtils.isEmpty(nextActList)) {
                                            if ("1".equals(nextActList.get(0).getActConcurrencyType())) {
                                                //下一个节点也是多选
                                                SysFlowNextUserSelect fnus = new SysFlowNextUserSelect();
                                                fnus.setCreateTime(new Date());
                                                fnus.setFlowId(FlowActivityobj.getActflowid());
                                                fnus.setOperateUid(user_id);
                                                fnus.setOperateActId(FlowActivityobj.getActid());
                                                fnus.setTargetActId(newObj.getTaskcurrentactid());
                                                fnus.setTargetUid(newObj.getTaskuser());
                                                fnus.setId(GenerateUUID.getUUID());
                                                fnus.setFileId(file_id);
                                                fnusList.add(fnus);
                                            }
                                        }


                                    }
                                    try {
                                        sysFlowNextUserSelectService.saveBatch(fnusList);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        log.error("流程服务：缺少Sys_Flow_Next_User_Select表");
                                    }

                                    new_task = new ArrayList<>();
                                } else {
                                    //多选审核通过，根据操作记录生成额外的待办记录
                                    if (!CollectionUtils.isEmpty(oldFnusList)) {
                                        List<SysFlowNextUserSelect> currentOperatorList = oldFnusList.stream().filter(n -> n.getOperateUid() != null && !n.getOperateUid().equals(user_id)).collect(Collectors.toList());
                                        if (!CollectionUtils.isEmpty(currentOperatorList)) {
                                            //生成新的待办信息
                                            for (SysFlowNextUserSelect fnus : currentOperatorList) {
                                                //判断下一步节点是否设置的多选
                                                List<FlowActivity> nextActList = FlowActivitys.stream().filter(n -> n.getActid().equals(fnus.getTargetActId())).collect(Collectors.toList());
                                                if (!CollectionUtils.isEmpty(nextActList)) {
                                                    if ("1".equals(nextActList.get(0).getActConcurrencyType())) {
                                                        List<Task> newSendTask = new_task.stream().filter(n -> n.getTaskcurrentactid().equals(fnus.getTargetActId()) && n.getTaskuser().equals(fnus.getTargetUid())).collect(Collectors.toList());
                                                        if (CollectionUtils.isEmpty(newSendTask)) {
                                                            Task newTask = new Task();
                                                            newTask.setTaskid(GenerateUUID.getUUID());
                                                            newTask.setTaskentityid(file_id);
                                                            newTask.setFlowId(fnus.getFlowId());
                                                            newTask.setTaskcurrentactid(fnus.getTargetActId());
                                                            newTask.setTaskuser(fnus.getTargetUid());
                                                            newTask.setTaskpreactid(fnus.getOperateActId());
                                                            newTask.setTasksenduser(fnus.getOperateUid());
                                                            newTask.setTaskread("未读");
                                                            newTask.setTaskname(taskname);  // 任务名称
                                                            newTask.setTaskclient(flow.getFlowclient());
                                                            newTask.setTaskflow(flow.getFlowid());
                                                            newTask.setTaskproject(new_task.get(0).getTaskproject());
                                                            newTask.setTaskrule(new_task.get(0).getTaskrule());
                                                            newTask.setTasksendtime(new Date());
                                                            newTask.setTaskType(new_task.get(0).getTaskType());
                                                            newTask.setTaskparentactid(new_task.get(0).getTaskparentactid());
                                                            newTask.setTaskparentflowid(new_task.get(0).getTaskparentflowid());
                                                            newTask.setTaskparenttaskid(new_task.get(0).getTaskparenttaskid());
                                                            newTask.setTasksection(new_task.get(0).getTasksection());
                                                            if (!"All".equals(customerId)) {
                                                                newTask.setTeId(customerId);
                                                            }
                                                            Date date = new Date();
                                                            newTask.setTaskstarttime(date);
                                                            newTask.setTaskendtime(new_task.get(0).getTaskendtime());
                                                            newTask.setTCode(flow.getTCode());
                                                            new_task.add(newTask);
                                                        }
                                                    }
                                                }
                                            }
                                            try {
                                                //删除掉操作记录信息
                                                sysFlowNextUserSelectService.removeBatchByIds(currentOperatorList.stream().map(n -> n.getId()).collect(Collectors.toList()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                log.error("流程服务：缺少Sys_Flow_Next_User_Select表");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        String status_st = data.get("status_str");
                        status_str = Integer.parseInt(status_st);
                        if (status_str == 1) {
                            Task_Processinnewlist.add(his_new);

                            //判断是否需要跳过
                            Map<String, String> pData = new HashMap<>();
                            pData.put("pId", p_id);
                            pData.put("customerId", customerId);
                            pData.put("userid", user_id);
                            pData.put("fileId", file_id);
                            pData.put("cId", c_id);
                            Map<String, String> returnData = processSkip(flow, FlowActivitys, FlowRules, Task_Processinlist, new_task, remainingTasks, newConditions, pData, taskname, Reverseconditions, customerId, requestSource, p_id, yearStr);
                            new_task = JacksonUtils.parseObject(returnData.get("returnTasks"), new TypeReference<List<Task>>() {
                            });
                            String endNumber = returnData.get("endNumber").toString();
                            Task_Processinnewlist.addAll(JacksonUtils.parseObject(returnData.get("returnTaskProcessins"), new TypeReference<List<TaskProcessin>>() {
                            }));
                            if (new_task.size() > 0) {
                                boolean ctds = taskService.saveBatch(new_task);
                                llowLogs.add("（行：629）时间：" + new Date() + "；保存待办信息；共计保存：" + new_task.size() + "；保存结果：" + ctds);
                                smstypen = 1;
                            }
                            for (userselectionDto item : nextuser)
                                taskProcessUserService.GetNewTaskProcessUse(his_new.getTpId(), item.getUserid(), item.getActityid(), customerId, request.gettCode(), yearStr);
                            if (Task_Processinnewlist.size() > 0) {
                                taskProcessinService.saveTaskProcessBatch(Task_Processinnewlist, request.gettCode(), yearStr);
                                for (TaskProcessin obj : Task_Processinnewlist) {
                                    sysTaskProcessinTimeService.TaskProcessinTimeCreate(obj, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                                }
                            }
                            //生成删除当前待办的sql
                            List<String> taskIds = new ArrayList<>();
                            taskIds.add(Taskobj.getTaskid());
                            if (!CollectionUtils.isEmpty(needDeleteTaskId)) {
                                taskIds.addAll(needDeleteTaskId);
                            }
                            boolean batchByIds = taskService.removeBatchByIds(taskIds);
                            llowLogs.add("（行：647）时间：" + new Date() + "；删除待办；共计保存：" + taskIds.size() + "；保存结果：" + batchByIds);
                            //生成抄送信息
                            carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, Taskobj.getTaskentityid(), Taskobj.getTaskname(), his_new.getTpId(), cc_Users, "1", customerId, token, p_id, request.gettCode(), yearStr);
                            fileService.updatefile(templatecode, file_id, endNumber, flowid, customerId, token, yearStr);//更新文档信息的审批状态
                            Taskproseeid = his_new.getTpId();
                        }
                    } else if (operationtype.equals("end")) {
                        status_str = 1;
                        Float Actityhour = null;
                        if (FlowActivityobj.getActhour() != null) {
                            Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
                        }


                        TaskProcessin his_new = taskProcessinService.GetHis(Taskobj, "同意", explain, user_id, "提交", flow, String.join(",", nextuser.stream().map(userselectionDto::getUserid).collect(Collectors.toList())), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体y()));
                        taskProcessinService.insertTaskProcessin(his_new, request.gettCode(), yearStr);
                        sysTaskProcessinTimeService.TaskProcessinTimeCreate(his_new, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                        //获取当前流程下的所有待办信息
                        List<Task> oldList = new ArrayList<>();
                        oldList = taskService.todolists(file_id, customerId);
                        if (!CollectionUtils.isEmpty(oldList)) {
                            String actId = FlowActivityobj.getActid();
                            oldList = oldList.stream().filter(n -> !n.getTaskcurrentactid().equals(actId)).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(oldList)) {
                                //当前流程还存在待办任务，额外增加判断是的运行结束流程
                                List<String> deleteIds = flowActivityService.GetSYSFlowActivityEntityGateway(FlowActivityobj, FlowActivitys, FlowRules, oldList, customerId, requestSource);
                                if (!CollectionUtils.isEmpty(deleteIds)) {
                                    oldList = oldList.stream().filter(n -> !deleteIds.contains(n.getTaskid())).collect(Collectors.toList());
                                }
                            }
                        }
                        smstypen = 1;
                        carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, Taskobj.getTaskentityid(), Taskobj.getTaskname(), his_new.getTpId(), cc_Users, "1", customerId, token, p_id, request.gettCode(), yearStr);
                        //判断当前节点是否时并发审批节点
                        if (ValidateUtil.isNotEmpty(FlowActivityobj.getActConcurrencyEnd())) {
                            //
                            if (FlowActivityobj.getActConcurrencyEnd().equals("0")) {
                                //任何一人审核通过
                                if (CollectionUtils.isEmpty(oldList) && !"通过并结束流程".equals(testtype)) {
                                    endStr = "end";
                                    fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                                    taskService.deleteTaskByIdOrTaskEntityID(Taskobj.getTaskid(), Taskobj.getTaskentityid(), customerId);
                                    llowLogs.add("（行：688）时间：" + new Date() + "；删除待办；删除处所有待办");
                                    List<Map<String, Object>> newActList = flowActivityService.GetSYSFlowActivityEntitybindEnd(FlowActivityobj, newConditions, FlowActivitys, FlowRules, 0, customerId);
                                    if (!CollectionUtils.isEmpty(newActList)) {
                                        for (Map<String, Object> act : newActList) {
                                            FlowActivity actobj = (FlowActivity) act.get("act");
                                            FlowRule rulObj = (FlowRule) act.get("rul");
                                            Task task_new = taskService.GetNewTaskobj(rulObj, his_new, Taskobj, flow, Taskobj.getTaskuser(), actobj.getActhour() + "", taskname, customerId);
                                            task_new.setProjectId(filePojo.getFileproject());
                                            task_new.setSecId(filePojo.getFilesectionid());
                                            task_new.setContractId(filePojo.getFilecontract());
                                            TaskProcessin hisNew = taskProcessinService.GetHis(task_new, "同意", explain, user_id, "提交", flow, String.join(",", nextuser.stream().map(userselectionDto::getUserid).collect(Collectors.toList())), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体y()));
                                            taskProcessinService.insertTaskProcessin(hisNew, request.gettCode(), yearStr);
                                            sysTaskProcessinTimeService.TaskProcessinTimeCreate(hisNew, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                                        }
                                    }
                                } else {
                                    if ("通过并结束流程".equals(testtype)) {
                                        taskService.deleteTaskByIdOrTaskEntityID("", file_id, customerId);
                                        llowLogs.add("（行：706）时间：" + new Date() + "；删除待办；删除处所有待办");
                                        endStr = "end";
                                        fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                                    }
                                    QueryWrapper<Task> wrapper = new QueryWrapper<>();
                                    wrapper.eq("TaskEntityID", file_id);
                                    wrapper.eq("TaskCurrentActID", FlowActivityobj.getActid());
                                    if (!"All".equals(customerId)) {
                                        wrapper.eq("Te_Id", customerId);
                                    }
                                    boolean bdts = taskService.remove(wrapper);
                                    llowLogs.add("（行：716）时间：" + new Date() + "；删除待办；删除当前流程下当前节点的所有待办；entityId(" + file_id + "),actId(" + FlowActivityobj.getActid() + "),结果：" + bdts);
                                }

                            } else {
                                //全部审核通过
                                List<Task> actTaskList = taskService.getTaskByEntityIdAndCurrentActId(file_id, FlowActivityobj.getActid(), customerId);
                                actTaskList = actTaskList.stream().filter(n -> !n.getTaskid().equals(taskid)).collect(Collectors.toList());
                                if (actTaskList.size() > 0) {
                                    //当前节点存在未审核完成的待办信息，流程不能结束
                                    boolean dts = taskService.removeById(Taskobj.getTaskid());
                                    llowLogs.add("（行：726）时间：" + new Date() + "；删除待办；根据id删除待办，id：" + Taskobj.getTaskid() + ",结果：" + dts);
                                } else {
                                    if (CollectionUtils.isEmpty(oldList) && !"通过并结束流程".equals(testtype)) {
                                        endStr = "end";
                                        fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                                        taskService.deleteTaskByIdOrTaskEntityID(Taskobj.getTaskid(), Taskobj.getTaskentityid(), customerId);
                                        llowLogs.add("（行：731）时间：" + new Date() + "；删除待办；删除全部的待办");
                                        List<Map<String, Object>> newActList = flowActivityService.GetSYSFlowActivityEntitybindEnd(FlowActivityobj, newConditions, FlowActivitys, FlowRules, 0, customerId);
                                        if (!CollectionUtils.isEmpty(newActList)) {
                                            for (Map<String, Object> act : newActList) {
                                                FlowActivity actobj = (FlowActivity) act.get("act");
                                                FlowRule rulObj = (FlowRule) act.get("rul");
                                                Task task_new = taskService.GetNewTaskobj(rulObj, his_new, Taskobj, flow, Taskobj.getTaskuser(), actobj.getActhour() + "", taskname, customerId);
                                                TaskProcessin hisNew = taskProcessinService.GetHis(task_new, "同意", explain, user_id, "提交", flow, String.join(",", nextuser.stream().map(userselectionDto::getUserid).collect(Collectors.toList())), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体y()));
                                                taskProcessinService.insertTaskProcessin(hisNew, request.gettCode(), yearStr);
                                                sysTaskProcessinTimeService.TaskProcessinTimeCreate(hisNew, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                                            }
                                        }
                                    } else {
                                        if ("通过并结束流程".equals(testtype)) {
                                            taskService.deleteTaskByIdOrTaskEntityID("", file_id, customerId);
                                            llowLogs.add("（行：746）时间：" + new Date() + "；删除待办；删除所有的待办");
                                            endStr = "end";
                                            fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                                        }
                                        taskService.removeById(Taskobj.getTaskid());
                                        llowLogs.add("（行：750）时间：" + new Date() + "；删除待办；根据id删除待办，id：" + Taskobj.getTaskid());
                                    }
                                }
                            }
                        } else {
                            if (CollectionUtils.isEmpty(oldList) && !"通过并结束流程".equals(testtype)) {
                                endStr = "end";
                                fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态

                                taskService.removeById(Taskobj.getTaskid());
                                llowLogs.add("（行：759）时间：" + new Date() + "；删除待办；根据id删除待办，id：" + Taskobj.getTaskid());
                                taskService.deleteTaskByIdOrTaskEntityID("", file_id, customerId);
                                llowLogs.add("（行：761）时间：" + new Date() + "；删除待办；当前所有待办");
                                List<Map<String, Object>> newActList = flowActivityService.GetSYSFlowActivityEntitybindEnd(FlowActivityobj, newConditions, FlowActivitys, FlowRules, 0, customerId);
                                if (!CollectionUtils.isEmpty(newActList)) {
                                    for (Map<String, Object> act : newActList) {
                                        FlowActivity actobj = (FlowActivity) act.get("act");
                                        FlowRule rulObj = (FlowRule) act.get("rul");
                                        Task task_new = taskService.GetNewTaskobj(rulObj, his_new, Taskobj, flow, Taskobj.getTaskuser(), actobj.getActhour() + "", taskname, customerId);
                                        task_new.setProjectId(filePojo.getFileproject());
                                        task_new.setSecId(filePojo.getFilesectionid());
                                        task_new.setContractId(filePojo.getFilecontract());
                                        TaskProcessin hisNew = taskProcessinService.GetHis(task_new, "同意", explain, user_id, "提交", flow, String.join(",", nextuser.stream().map(userselectionDto::getUserid).collect(Collectors.toList())), actobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体y()));
                                        taskProcessinService.insertTaskProcessin(hisNew, request.gettCode(), yearStr);
                                        sysTaskProcessinTimeService.TaskProcessinTimeCreate(hisNew, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                                    }
                                }
                            } else {
                                if ("通过并结束流程".equals(testtype)) {
                                    taskService.deleteTaskByIdOrTaskEntityID("", file_id, customerId);
                                    llowLogs.add("（行：779）时间：" + new Date() + "；删除待办；当前所有待办");
                                    endStr = "end";
                                    fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                                } else {
                                    fileService.updatefile(templatecode, file_id, "next", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                                }
                                taskService.removeById(Taskobj.getTaskid());
                                llowLogs.add("（行：785）时间：" + new Date() + "；删除待办；根据id删除待办，id：" + Taskobj.getTaskid());
                            }
                        }
                        Taskproseeid = his_new.getTpId();
                    }
                }
            } else {
                //流程启动
                Date date = new Date();
                TaskProcessin his = new TaskProcessin();
                his.setTpActid(FlowActivityobj.getActid());
                his.setActname(FlowActivityobj.getActname());
                his.setTpDotime(new Date());
                his.setTpFlowid(flow.getFlowid());
                his.setTpId(GenerateUUID.getUUID());
                his.setTpExplain(explain);
                his.setTpResult("同意");
                //his.tp_ruleid = null;//起始活动的历史记录规则为空taskType
                his.setTpEntityid(file_id);
                his.setTpProject(p_id);
                his.setTpName(taskname);
                his.setTpUser(user_id);
                his.setTpCountersigntitle(FlowActivityobj.getActcountersigntitle());
                his.setTpType("提交");
                his.setTpStarttime(new Date());
                his.setTpEndtime(new Date());
                his.setTpActtype(FlowActivityobj.getActtype());
                his.setTpTaskReturnType("start");
                his.setTpClient(requestSource);
                if (!"All".equals(customerId)) {
                    his.setTeId(customerId);
                }
                his.setTpSend(String.join(",", nextuser.stream().map(userselectionDto::getUserid).collect(Collectors.toList())));
                //his.tp_section=s
                List<TaskProcessin> Task_Processinnewlist = new ArrayList<>();
                Task_Processinlist.add(his);
                Task old_new = new Task();
                old_new.setTaskproject(p_id);
                old_new.setTaskuser(user_id);
                if (operationtype.equals("end")) {
                    status_str = 1;
                    Float Actityhour = null;
                    if (FlowActivityobj.getActhour() != null) {
                        Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
                    }
                    taskProcessinService.insertTaskProcessin(his, request.gettCode(), yearStr);

                    sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                    List<Map<String, Object>> newActList = flowActivityService.GetSYSFlowActivityEntitybindEnd(FlowActivityobj, newConditions, FlowActivitys, FlowRules, 0, customerId);
                    if (!CollectionUtils.isEmpty(newActList)) {
                        for (Map<String, Object> act : newActList) {
                            FlowActivity actobj = (FlowActivity) act.get("act");
                            FlowRule rulObj = (FlowRule) act.get("rul");
                            TaskProcessin hisObj = new TaskProcessin();
                            hisObj.setTpActid(actobj.getActid());
                            hisObj.setActname(actobj.getActname());
                            hisObj.setTpDotime(new Date());
                            hisObj.setTpExplain(explain);
                            hisObj.setTpFlowid(flow.getFlowid());
                            hisObj.setTpId(GenerateUUID.getUUID());
                            hisObj.setTpResult("同意");
                            //his.tp_ruleid = null;//起始活动的历史记录规则为空
                            hisObj.setTpEntityid(file_id);
                            hisObj.setTpProject(p_id);
                            hisObj.setTpName(taskname);
                            hisObj.setTpUser(user_id);
                            hisObj.setTpActtype(actobj.getActtype());
                            hisObj.setTpCountersigntitle(actobj.getActcountersigntitle());
                            hisObj.setTpType("提交");
                            hisObj.setTpStarttime(new Date());
                            hisObj.setTpEndtime(new Date());
                            hisObj.setTpActname(actobj.getActname());
                            if (!"All".equals(customerId)) {
                                hisObj.setTeId(customerId);
                            }
                            taskProcessinService.insertTaskProcessin(hisObj, request.gettCode(), yearStr);
                            sysTaskProcessinTimeService.TaskProcessinTimeCreate(hisObj, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                        }
                    }
                    //生成抄送信息
                    smstypen = 1;
                    carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, file_id, taskname, his.getTpId(), cc_Users, "1", customerId, token, p_id, request.gettCode(), yearStr);
                    endStr = "end";
                    fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                    // taskService.deleteTaskByIdOrTaskEntityID(Taskobj.getTaskid(), Taskobj.getTaskentityid());
                    Taskproseeid = his.getTpId();

                } else {
                    Map<String, String> data = new HashMap<>();
                    data.put("Task_Processinnewlist", JacksonUtils.toJSONString(Task_Processinnewlist));
                    data.put("massage", JacksonUtils.toJSONString(massage));
                    data.put("new_task", JacksonUtils.toJSONString(new_task));
                    data.put("status_str", status_str + "");
                    data.put("addsSecond", "1");
                    List<String> needDeleteTaskId = new ArrayList<>();
                    data.put("needDeleteTaskId", JacksonUtils.toJSONString(needDeleteTaskId));
                    data = flowActivityService.GetSYSFlowActivityEntitysubmit(FlowActivityobj, newConditions, FlowActivitys, FlowRules, Task_Processinlist, nextuser, his, flow, old_new, explain, data, taskname, remainingTasks, customerId, requestSource);
                    new_task = JacksonUtils.parseObject(data.get("new_task"), new TypeReference<List<Task>>() {
                    });
                    massage = JacksonUtils.parseObject(data.get("massage"), List.class);
                    Task_Processinnewlist = JacksonUtils.parseObject(data.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
                    });
                    status_str = Integer.parseInt(data.get("status_str"));
                    if (status_str == 1) {
                        Task_Processinnewlist.add(his);
                        //流程重新发起，清除原有的所有待办信息

                        if (new_task.size() > 0) {
                            taskService.deleteTaskByIdOrTaskEntityID("", file_id, customerId);
                            taskService.saveBatch(new_task);
                            smstypen = 1;
                        }
                        for (userselectionDto item : nextuser)
                            taskProcessUserService.GetNewTaskProcessUse(his.getTpId(), item.getUserid(), item.getActityid(), customerId, request.gettCode(), yearStr);
                        if (Task_Processinnewlist.size() > 0) {
                            taskProcessinService.saveTaskProcessBatch(Task_Processinnewlist, request.gettCode(), yearStr);
                            for (TaskProcessin obj : Task_Processinnewlist) {
                                sysTaskProcessinTimeService.TaskProcessinTimeCreate(obj, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                            }
                        }
                        //生成抄送信息
                        carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, file_id, taskname, his.getTpId(), cc_Users, "1", customerId, token, p_id, request.gettCode(), yearStr);
                        //更新文档信息的审批状态
                        fileService.updatefile(templatecode, file_id, "new", flowid, customerId, token, yearStr);
                    }
                    Taskproseeid = his.getTpId();
                }
            }
            if (status_str == 1) {
                //抄送
                if (smstypen > 0) {
                    //TODO生成短信息和邮件等通知信息新增sql语句
                    List<String> userlist = new ArrayList<>();
                    nextuser.forEach(n -> {
                        if ((!ValidateUtil.isEmpty(n.getSmsStatus()) && n.getSmsStatus().equals("1")) || (!ValidateUtil.isEmpty(n.getSmstype()) && n.getSmstype().equals("1"))) {
                            if (n.getUserid().contains(",")) {
                                String[] userList = n.getUserid().split(",");
                                for (int i = 0; i < userList.length; i++) {
                                    userlist.add(userList[i]);
                                }
                            } else {
                                userlist.add(n.getUserid());
                            }
                        }
                    });
                    sysShortMessageService.generateSMS("", userlist, file_id, FlowActivityobj.getActid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                    sysMessageService.generateMassage("", userlist, file_id, FlowActivityobj.getActid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                }
                //流程成功后
                //successfulevent(new_task, "1");
                taskService.sendToDoTask(Taskobj, Taskproseeid, "1", "1", token, customerId, request.gettCode(), yearStr);
                if (operationtype.equals("end") && "end".equals(endStr)) {
                    //流程最后一步，获取结束节点的相关配置信息，并进行抄送和短信的发送
                    List<FlowActivity> FlowActivityList = flowActivityService.GetSYSFlowActivityEntitybind(FlowActivityobj, newConditions, FlowActivitys, FlowRules, 0, customerId);
                    if (FlowActivityList.size() > 0) {
                        List<FlowActivity> activityList = FlowActivityList.stream().filter(n -> n.getActtype().equals("endEvent")).collect(Collectors.toList());
                        if (activityList.size() == 0) {
                            activityList = FlowActivitys.stream().filter(n -> n.getActtype().equals("endEvent")).collect(Collectors.toList());
                        }
                        if (activityList.size() > 0) {
                            //抄送
                            carbonCopyService.GetCarbonCopyList(activityList.get(0), user_id, file_id, taskname, Taskproseeid, new ArrayList<>(), "1", customerId, token, p_id, request.gettCode(), yearStr);
                            sysShortMessageService.generateSMS("", new ArrayList<>(), file_id, activityList.get(0).getActid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                            sysMessageService.generateMassage("", new ArrayList<>(), file_id, activityList.get(0).getActid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                        }
                    }
                }
                // 流程提交成功，删除掉待沟通的任务
                sysCoordinationTaskService.deleteByEntityId(file_id, customerId);
                //删除当前任务节点的驳回记录信息
                List<SysTaskReturnMap> strmList = sysTaskReturnMapService.selectByFileId(file_id);
                if (!CollectionUtils.isEmpty(strmList)) {
                    String actId = FlowActivityobj.getActid();
                    List<SysTaskReturnMap> actMapList = strmList.stream().filter(n -> n.getOperateTargetAct().equals(actId)).collect(Collectors.toList());
                    for (SysTaskReturnMap it : actMapList) {
                        //判断当前节点是否有备份单位待办信息
                        String taskIds = it.getTaksId();
                        List<SysTaskBackups> taskBackupsList = sysTaskBackupsService.getListByTaskId(taskIds, customerId);
                        if (!CollectionUtils.isEmpty(taskBackupsList)) {
                            llowLogs.add("时间：" + new Date() + "；开始恢复冻结流程：涉及流程数量：" + taskBackupsList.size());
                            for (SysTaskBackups tobj : taskBackupsList) {
                                Task taskEnt = new Task();
                                BeanUtils.copyProperties(tobj, taskEnt);
                                //检查冻结的流程和生成的流程是否重复
                                if (!(it.getOperateActUser().equals(tobj.getTaskuser()) && it.getOperateAct().equals(tobj.getTaskcurrentactid()))) {
                                    taskEnt.setTasksendtime(new Date());
                                    taskEnt.setTaskstarttime(new Date());
                                    try {
                                        BigDecimal bg = DateUtil.getDifferHour(tobj.getTaskstarttime().toString(), tobj.getTaskendtime().toString());
                                        taskEnt.setTaskendtime(DateUtil.DateChangeHour(taskEnt.getTaskstarttime(), Double.parseDouble(bg.toString())));
                                    } catch (Exception ex) {
                                        log.error("流程服务：", ex);
                                    }
                                    taskEnt.setProjectId(filePojo.getFileproject());
                                    taskEnt.setSecId(filePojo.getFilesectionid());
                                    taskEnt.setContractId(filePojo.getFilecontract());
                                    boolean i2 = taskService.save(taskEnt);
                                    llowLogs.add("时间：" + new Date() + "；冻结流程任务保存，保存结果：" + i2);
                                }
                            }
                            sysTaskBackupsService.deleteByTaskId(taskIds, customerId);
                            llowLogs.add("时间：" + new Date() + "；删除冻结的流程任务");
                            List<String> taskIdList = taskBackupsList.stream().map(n -> n.getTaskid()).collect(Collectors.toList());
                            sysCoordinationTaskBackupsService.recoverySysCoordinationTask(taskIdList, customerId);
                            llowLogs.add("时间：" + new Date() + "；删除冻结沟通任务");
                        }

                        sysTaskReturnMapService.DeleteById(it.getId());
                    }

                }
                //记录运行日志
                fl.write_to_txt_batch(logSavePath, user_id, llowLogs);
                return new BaseResponse(true, "流程提交成功！", Taskproseeid);
            } else {
                if (massage.size() > 0)
                    return new BaseResponse(String.join("", massage.stream().distinct().collect(Collectors.toList())));
                else return new BaseResponse("流程提交错误！");
            }
        } else return new

                BaseResponse("流程提交错误，当前任务节点获取失败！");
    }


    public Map<String, String> processSkip(Flow flow, List<FlowActivity> allActList, List<FlowRule> allRuleList, List<TaskProcessin> allTaskProcessin, List<Task> newTask, List<Task> remainingTasks, Map<String, Object> newConditions, Map<String, String> pData, String taskname, String Reverseconditions, String customerId, String requestSource, String pId, String yearStr) {
        Map<String, String> returnData = new HashMap<>();
        List<TaskProcessin> returnTaskProcessins = new ArrayList<>();
        List<Task> returnTasks = newTask;
        int endNumber = 0;
        String type = "";
        //获取基础配置信息
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalSkipSetting");
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        Object approvalSkipSetting = smsTemplateList.get("approvalSkipSetting");
        if (approvalSkipSetting != null) {
            Object obj = JacksonUtils.parseObject(approvalSkipSetting.toString(), new TypeReference<Map<String, String>>() {
            });

            Boolean onState = false;
            Boolean globalState = false;
            String defaultOpinion = "";
            String defaultUid = "";
            Boolean noOneSubmit = false;
            Boolean multipleSubmis = false;

            if (((Map) obj).get("onState") != null) {
                String onStateStr = ((Map) obj).get("onState").toString();
                onState = onStateStr == "true";
            }
            if (((Map) obj).get("globalState") != null) {
                String globalStateStr = ((Map) obj).get("globalState").toString();
                globalState = globalStateStr == "true";
            }
            if (((Map) obj).get("noOneSubmit") != null) {
                String noOneSubmitStr = ((Map) obj).get("noOneSubmit").toString();
                noOneSubmit = noOneSubmitStr == "true";
            }
            if (((Map) obj).get("multipleSubmis") != null) {
                String multipleSubmisStr = ((Map) obj).get("multipleSubmis").toString();
                multipleSubmis = multipleSubmisStr == "true";
            }
            if (((Map) obj).get("defaultOpinion") != null) {
                defaultOpinion = ((Map) obj).get("defaultOpinion").toString();
            }
            if (((Map) obj).get("defaultUid") != null) {
                defaultUid = ((Map) obj).get("defaultUid").toString();
            }

            //当前
            if (!CollectionUtils.isEmpty(newTask) && onState) {
                Map<String, List<Task>> data = newTask.stream().collect(Collectors.groupingBy(Task::getTaskuser, Collectors.toList()));
                for (String key : data.keySet()) {
                    List<Task> taskList = data.get(key);
                    if (taskList.size() == 1) {
                        //当前只有处理人只有1条待办，判断当前节点是否允许跳过
                        List<FlowActivity> acts = allActList.stream().filter(n -> n.getActid().equals(taskList.get(0).getTaskcurrentactid())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(acts)) {
                            String actSkipStatus = acts.get(0).getActSkipStatus();
                            if ("1".equals(actSkipStatus) || globalState) {
                                //获取流程节点上设置的默认审批意见
                                String massageStr = acts.get(0).getActDefaultOpinion();
                                if (ValidateUtil.isNotEmpty(massageStr)) {
                                    defaultOpinion = massageStr;
                                }

                                //节点允许跳过，开始判断当前任务处理人是否有历史审批记录
                                List<TaskProcessin> taslPList = allTaskProcessin;
                                //获取最新的驳回信息
                                List<TaskProcessin> processinList = allTaskProcessin.stream().filter(n -> "退回".equals(n.getTpType()) && n.getTpDotime() != null).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList());
                                if (!CollectionUtils.isEmpty(processinList)) {
                                    Date doTime = processinList.get(0).getTpDotime();
                                    taslPList = allTaskProcessin.stream().filter(n -> n.getTpDotime().compareTo(doTime) > 0 && "提交".equals(n.getTpType())).collect(Collectors.toList());
                                }
                                List<TaskProcessin> olist = taslPList.stream().filter(n -> n.getTpUser().equals(taskList.get(0).getTaskuser())).collect(Collectors.toList());
                                if (!CollectionUtils.isEmpty(olist)) {
                                    //可以跳过
                                    List<FlowActivity> FlowActivityList = flowActivityService.GetSYSFlowActivityEntitybind(acts.get(0), newConditions, allActList, allRuleList, 0, customerId);
                                    //判断下一步流程是否已经结束
                                    FlowActivity SYS_FlowActivityend = null;
                                    try {
                                        SYS_FlowActivityend = FlowActivityList.stream().filter(n -> n.getActtype().equals("endEvent")).findFirst().get();
                                    } catch (Exception ex) {
                                        log.error("流程服务：", ex);
                                    }
                                    //获取下一步是否有结束节点
                                    if (SYS_FlowActivityend != null && FlowActivityList.size() > 1) {
                                        //下一步节点存在多个活动，并且包含结束节点，提示流程节点设置异常
                                        log.error("流程图设置异常不能跳过【" + pData.get("fileId") + "===(" + acts.get(0).getActname() + ")】");
                                    } else if (SYS_FlowActivityend != null) {
                                        //结束处理
                                        TaskProcessin his_new = taskProcessinService.GetHis(taskList.get(0), "同意", defaultOpinion, taskList.get(0).getTaskuser(), "提交", flow, "", acts.get(0).getActtype(), Float.valueOf("0.00"), customerId, requestSource);       // 历史记录实体y()));
                                        returnTaskProcessins.add(his_new);
                                        endNumber = endNumber + 1;
                                        returnTasks.remove(taskList.get(0));
                                        type = "end";
                                    } else {
                                        List<String> Activityid = FlowActivityList.stream().map(FlowActivity::getActid).collect(Collectors.toList());
                                        List<Map<String, Object>> TaskProcessinlist = taskProcessinService.getSomeFieldsTaskProcessins(pData.get("fileId"), customerId, flow.getTCode(), yearStr);
                                        //循环每个节点，并获取对应userid节点下的用户信息

                                        List<UserSelectionVo> returlist = flowActivityService.getActUser(FlowActivityList, Reverseconditions, pData, Activityid, newConditions, allActList, TaskProcessinlist, pId);
                                        if (!CollectionUtils.isEmpty(returlist)) {
                                            if (returlist.size() == 1) {
                                                //可以跳过
                                                String uId = "";


                                                if (!CollectionUtils.isEmpty(returlist.get(0).getUserlist())) {
                                                    if (returlist.get(0).getUserlist().size() == 1 || multipleSubmis) {
                                                        uId = returlist.get(0).getUserlist().get(0).getUserid();
                                                    }
                                                } else {
                                                    if (noOneSubmit) {
                                                        uId = defaultUid;
                                                    }
                                                }
                                                if (ValidateUtil.isNotEmpty(uId)) {
                                                    TaskProcessin his_new = taskProcessinService.GetHis(taskList.get(0), "同意", defaultOpinion, taskList.get(0).getTaskuser(), "提交", flow, uId, acts.get(0).getActtype(), Float.valueOf("0.00"), customerId, requestSource);       // 历史记录实体y()));
                                                    Map<String, String> dataMap = new HashMap<>();
                                                    Integer status_str = 0;
                                                    List<Task> newTasks = new ArrayList<>();
                                                    List<TaskProcessin> newTaskProcessins = new ArrayList<>();
                                                    dataMap.put("Task_Processinnewlist", JacksonUtils.toJSONString(newTaskProcessins));
                                                    dataMap.put("massage", JacksonUtils.toJSONString(new ArrayList<>()));
                                                    dataMap.put("new_task", JacksonUtils.toJSONString(newTasks));
                                                    dataMap.put("status_str", status_str + "");
                                                    dataMap.put("addsSecond", "1");
                                                    List<userselectionDto> nextuser = new ArrayList<>();
                                                    userselectionDto userSelect = new userselectionDto();
                                                    userSelect.setUserid(uId);
                                                    userSelect.setActityid(returlist.get(0).getNodeid());
                                                    nextuser.add(userSelect);
                                                    List<String> needDeleteTaskId = new ArrayList<>();
                                                    dataMap.put("needDeleteTaskId", JacksonUtils.toJSONString(needDeleteTaskId));
                                                    dataMap = flowActivityService.GetSYSFlowActivityEntitysubmit(acts.get(0), newConditions, allActList, allRuleList, allTaskProcessin, nextuser, his_new, flow, taskList.get(0), defaultOpinion, dataMap, taskname, remainingTasks, customerId, requestSource);
                                                    newTasks = JacksonUtils.parseObject(dataMap.get("new_task"), new TypeReference<List<Task>>() {
                                                    });
                                                    newTaskProcessins = JacksonUtils.parseObject(dataMap.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
                                                    });
                                                    String status_st = dataMap.get("status_str");
                                                    status_str = Integer.parseInt(status_st);

                                                    if (status_str == 1) {
                                                        returnTaskProcessins.addAll(newTaskProcessins);
                                                        returnTasks.addAll(newTasks);
                                                        returnTaskProcessins.add(his_new);
                                                        returnTasks.remove(taskList.get(0));
                                                    }
                                                }

                                            } else {
                                                //不能跳过
                                                log.error("下一步流程节点不唯一，不能自动跳过【" + pData.get("fileId") + "===(" + acts.get(0).getActname() + ")】");
                                            }
                                        } else {
                                            //不能跳过
                                            log.error("下一步未找到流程节点，不能跳过【" + pData.get("fileId") + "===(" + acts.get(0).getActname() + ")】");
                                        }
                                    }


                                }

                            }
                        }
                    }
                }

            }
        }
        returnData.put("returnTaskProcessins", JacksonUtils.toJSONString(returnTaskProcessins));
        returnData.put("returnTasks", JacksonUtils.toJSONString(returnTasks));
        returnData.put("endNumber", type == "end" ? "end" : endNumber == 0 ? "next" : endNumber == newTask.size() ? "end" : "next");
        return returnData;
    }


    public String FindsubmitterMan(String fileid, String flowid, String newactityid, String nextactityid, String userid, String customerId, String tCode, String yearStr) {
        List<TaskProcessin> taskprocessinlist = taskProcessinService.getListByTpFlowIdAndTpEntityId(flowid, fileid, customerId, yearStr);
        List<String> tp_list = taskprocessinlist.stream().map(n -> n.getTpId()).collect(Collectors.toList());
        List<TaskProcessUser> TaskList = taskProcessUserService.GetTaskProcessUser(tp_list, customerId, tCode, yearStr);
        //获取所有发送给当前操作人的审批记录
        TaskList = TaskList.stream().filter(atem -> atem.getTpuSendActivityid() != null && atem.getTpuSendActivityid().equals(newactityid) && atem.getTpuUser() != null && atem.getTpuUser().equals(userid)).collect(Collectors.toList());
        if (TaskList.size() > 0) {
            //说明，谁最新一次发送给我，我原路返回时就发送给谁
            List<String> idlist = TaskList.stream().map(n -> n.getTpuTpId()).collect(Collectors.toList());
            TaskProcessin newTaskProcessinPojo = taskprocessinlist.stream().filter(n -> idlist.contains(n.getTpId())).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).findFirst().get();
            String nextuser = newTaskProcessinPojo.getTpUser();
            return nextuser;
        } else {
            return "";
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse sendbackFlow(SendbackFlowDto request, String customerId, String token) {
        String taskid = request.getTaskid();//任务id
        String backtype = request.getBacktype();//退回类型
        String explain = request.getExplain();//审批意见
        String taskname = request.getTaskname();//任务名称
        String file_id = request.getFile_id();//文档id
        String user_id = request.getUser_id();//当前操作用户
        List<String> cc_Users = request.getCc_user();//用户选择的抄送用户信息
        String returntype = request.getReturntype();//返回类型
        String Taskproseeid = "";//返回审批记录id
        String smsType = request.getSmstype();//是否发送短信
        String requestSource = request.getRequestSource();
        String yearStr = request.getYear();

        File filePojo = fileService.getFileById(file_id, request.gettCode(), yearStr);
        if (filePojo != null) {
            if (filePojo.getFilecreatedate() != null) {
                yearStr = DateUtil.DateToString(filePojo.getFilecreatedate(), "yyyy");
            }
        }
        if (!ValidateUtil.isEmpty(taskid)) {
            Task taskentity = taskService.getById(taskid);
            Map<String, String> map = new HashMap<>();
            map.put("file_id", file_id);
            map.put("taskname", taskname);
            map.put("backtype", backtype);
            map.put("user_id", user_id);
            map.put("explain", explain);
            map.put("returntype", returntype);
            map.put("Taskproseeid", Taskproseeid);
            map.put("customerId", customerId);
            map.put("yearStr", yearStr);
            map.put("tCode", request.gettCode());
            map.put("token", token);
            map.put("smsType", smsType);
            map.put("requestSource", requestSource);
            map.put("ReturnaActId", request.getReturnaActId());
            map.put("ReturnUser", request.getReturnUser());
            return returnLogic(taskentity, filePojo, map, cc_Users);
        } else return new BaseResponse("参数丢失！");
    }

    public BaseResponse returnLogic(Task taskentity, File filePojo, Map<String, String> map, List<String> cc_Users) {
        String file_id = map.get("file_id");
        String taskname = map.get("taskname");
        String backtype = map.get("backtype");
        String user_id = map.get("user_id");
        String explain = map.get("explain");
        String returntype = map.get("returntype");
        String Taskproseeid = map.get("Taskproseeid");
        String customerId = map.get("customerId");
        String yearStr = map.get("yearStr");
        String tCode = map.get("tCode");
        String token = map.get("token");
        String smsType = map.get("smsType");
        String requestSource = map.get("requestSource");
        String returnaActId = map.get("ReturnaActId");
        String returnUser = map.get("ReturnUser");

        if (taskentity != null) {
            List<String> codeList = new ArrayList<>();
            codeList.add("anyNodeReturn");//是否允许任意节点退回
            codeList.add("continuousReturn");//是否允许连续退回
            Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
            Object anyNodeReturn = smsTemplateList.get("anyNodeReturn");

            if ("1".equals(smsTemplateList.get("continuousReturn"))) {
                if ("退回".equals(taskentity.getTaskType())) {
                    //退回的待办不能再继续退回
                    return new BaseResponse("当前任务不能再退回！");
                }
            }
            if (filePojo == null) {
                filePojo = fileService.getFileById(taskentity.getTaskentityid(), tCode, yearStr);
                taskentity.setFlowId(filePojo.getFlowId());
                taskentity.setSecId(filePojo.getFilesectionid());
                taskentity.setProjectId(filePojo.getFileproject());
                taskentity.setContractId(filePojo.getFilecontract());
                taskentity.setTCode(filePojo.gettCode());
            }
            if (ValidateUtil.isEmpty(file_id)) {
                file_id = taskentity.getTaskentityid();
            }
            if (ValidateUtil.isEmpty(taskname)) {
                taskname = taskentity.getTaskname();
            }
            String flowid = taskentity.getTaskflow();
            Flow flow = baseMapper.selectById(flowid);
            FlowActivity current_node = flowActivityService.getById(taskentity.getTaskcurrentactid());
            if (current_node.getActbacktype().equals("CanNot")) return new BaseResponse("当前步骤不能退回！");
            else {
                String returnstr = "pre";
                if (ValidateUtil.isEmpty(backtype))
                    backtype = Submissionstatusdetermination(current_node.getActbacktype());

                List<String> userList = new ArrayList<>();
                List<Task> new_task = new ArrayList<>();
                //判断退回类型
                if (backtype.equals("[initiator]")) {
                    //退回发起人。。。。。
                    int count_task = taskService.seleteTaskCountByTaskEntityID(taskentity.getTaskentityid(), customerId);

                    String anyNodeReturnvlue = "0";
                    if (anyNodeReturn != null) {
                        anyNodeReturnvlue = anyNodeReturn.toString();
                    }
                    if (count_task <= 1 || anyNodeReturnvlue.equals("1")) {
                        Map<String, String> newdata = Returnstart(flow, taskentity, explain, file_id, user_id, new_task, cc_Users, returntype, anyNodeReturnvlue, customerId, requestSource, token, tCode, yearStr);
                        returnstr = newdata.get("returnstr").toString();
                        Taskproseeid = newdata.get("taskproseeid").toString();
                        userList = JacksonUtils.parseObject(newdata.get("userList"), List.class);
                    } else {
                        return new BaseResponse("当前流程还存在其它审批活动，无法退回到发起人！");
                    }
                } else if (backtype.equals("[laststep]")) {
                    //退回上一步。。。。。
                    //获取流程当前节点
                    try {
                        Map<String, String> newdata = Takestepback(flow, taskentity, current_node, explain, file_id, user_id, new_task, cc_Users, customerId, requestSource, token, tCode, yearStr);
                        returnstr = newdata.get("returnstr");
                        Taskproseeid = newdata.get("taskproseeid");
                        userList = JacksonUtils.parseObject(newdata.get("userList"), List.class);
                    } catch (Exception ex) {
                        log.error("流程服务：", ex);
                        return new BaseResponse("服务内部错误！");
                    }
                } else if (backtype.equals("[anyNode]")) {
                    //退回任意节点
                    //获取当前流程下的所有流程几点信息
                    List<FlowActivity> FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
                    //获取当前流程下所有的连接线
                    List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
                    //生成退回连接线
                    FlowRule Ruleobj = null;
                    Ruleobj = flowRuleService.lineofretirement(flowid, taskentity.getTaskcurrentactid(), returnaActId, "退回", customerId);
                    Float Actityhour = null;
                    if (current_node.getActhour() != null) {
                        Actityhour = Float.parseFloat(current_node.getActhour() + "");
                    }
                    current_node = FlowActivitys.stream().filter(n -> n.getActid().equals(returnaActId)).findFirst().get();
                    TaskProcessin his = taskProcessinService.GetHis(taskentity, "退回", explain, user_id, "退回", flow, returnUser, current_node.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体
                    Task task_new = taskService.GetNewTaskobj(Ruleobj, his, taskentity, flow, returnUser, current_node.getActhour() + "", taskentity.getTaskname(), customerId);
                    task_new.setProjectId(filePojo.getFileproject());
                    task_new.setSecId(filePojo.getFilesectionid());
                    task_new.setContractId(filePojo.getFilecontract());
                    //判断退回节点是否是开始节点
                    List<FlowActivity> _FlowActivitystartlist = Getspreviousnode(FlowActivitys, FlowRules, current_node);
                    if (_FlowActivitystartlist.size() > 0) {
                        if (_FlowActivitystartlist.get(0).getActtype().equals("startEvent")) {
                            task_new.setTaskReturnType("start");
                            returnstr = "start";
                        }
                    }
                    task_new.setTaskgetflagstr(returntype);
                    String anyNodeReturnvlue = "0";
                    if (anyNodeReturn != null) {
                        anyNodeReturnvlue = anyNodeReturn.toString();
                    }
                    List<Task> oldList = new ArrayList<>();
                    //获取当前流程下的所有待办信息
                    oldList = taskService.todolists(file_id, customerId);
                    if (!"start".equals(returnstr)) {
                        List<FlowActivity> nextActs = GetsNextNodeALL(FlowActivitys, FlowRules, current_node);
                        nextActs.add(current_node);
                        if (nextActs.size() > 0) {
                            List<String> actIds = nextActs.stream().map(n -> n.getActid()).collect(Collectors.toList());
                            oldList = oldList.stream().filter(n -> actIds.contains(n.getTaskcurrentactid())).collect(Collectors.toList());
                        }
                    }
                    if ("1".equals(returntype)) {
                        //备份被驳回节点之后的待办信息
                        if (oldList.size() > 0) {
                            List<Task> oldLists = oldList.stream().filter(n -> !n.getTaskid().equals(taskentity.getTaskid())).collect(Collectors.toList());
                            if (oldLists.size() > 0) {
                                //退回发起人，记录
                                for (Task obj : oldLists) {
                                    SysTaskBackups newTask = new SysTaskBackups();
                                    BeanUtils.copyProperties(obj, newTask);
                                    //newTask.setTaskid(GenerateUUID.getUUID());
                                    newTask.setTaskIdNew(task_new.getTaskid());
                                    newTask.setTasksendtime(new Date());
                                    newTask.setTaskstarttime(new Date());
                                    try {
                                        BigDecimal bg = DateUtil.getDifferHour(obj.getTaskstarttime().toString(), obj.getTaskendtime().toString());
                                        newTask.setTaskendtime(DateUtil.DateChangeHour(newTask.getTaskstarttime(), Double.parseDouble(bg.toString())));
                                    } catch (Exception ex) {
                                        log.error("流程服务：", ex);
                                    }

                                    sysTaskBackupsService.save(newTask);
                                }
                                //沟通任务备份
                                List<String> taskId = oldLists.stream().map(n -> n.getTaskid()).collect(Collectors.toList());
                                sysCoordinationTaskBackupsService.backups(taskId, customerId);
                            }
                        }

                    }
                    taskService.removeBatchByIds(oldList.stream().map(n -> n.getTaskid()).collect(Collectors.toList()));
                    Taskproseeid = his.getTpId();
                    taskProcessinService.insertTaskProcessin(his, tCode, yearStr);

                    sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, current_node.getActtype(), task_new.getTaskpreactid(), "", filePojo.getFileproject(), customerId, tCode);

                    //获取退回节点信息
                    taskService.save(task_new);
                    taskProcessUserService.GetNewTaskProcessUse(his.getTpId(), task_new.getTaskuser(), task_new.getTaskcurrentactid(), customerId, tCode, yearStr);
                    taskService.deleteTaskByEntityIdAndCurrentActId(his.getTpEntityid(), taskentity.getTaskcurrentactid(), taskentity.getTaskid(), customerId);
                    //生成抄送信息
                    carbonCopyService.GetCarbonCopyList(current_node, user_id, taskentity.getTaskentityid(), taskentity.getTaskname(), his.getTpId(), cc_Users, "0", customerId, token, filePojo.getFileproject(), tCode, yearStr);
                    if ("1".equals(task_new.getTaskgetflagstr())) {
                        SysTaskReturnMap objp = new SysTaskReturnMap();
                        objp.setFileId(file_id);
                        objp.setOperateAct(taskentity.getTaskcurrentactid());
                        objp.setOperateActUser(taskentity.getTaskuser());
                        objp.setOperateTargetAct(task_new.getTaskcurrentactid());
                        objp.setTaksId(task_new.getTaskid());
                        sysTaskReturnMapService.create(objp, customerId);
                    }
                }
                //更新文档信息的审批状态
                if (!returnstr.equals("start")) {
                    returnstr = "pre";
                }
                //更新待办信息

                fileService.updatefile(filePojo.gettCode(), file_id, returnstr, flowid, customerId, token, yearStr);
                //TODO:流程成功后
                sysShortMessageService.generateSMS(smsType, userList, file_id, current_node.getActid(), taskname, "不通过", explain, user_id, customerId, token, filePojo.getFileproject(), tCode, yearStr);
                sysMessageService.generateMassage("", userList, file_id, current_node.getActid(), taskname, "通过", explain, user_id, customerId, token, filePojo.getFileproject(), tCode, yearStr);
                taskService.sendToDoTask(taskentity, Taskproseeid, "1", "3", token, customerId, tCode, yearStr);
                //successfulevent(new_task, "2");
                return new BaseResponse(true, "流程退回成功！", Taskproseeid);
            }
        } else return new BaseResponse("任务信息获取失败！");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse Stopprocess(StopProcessDto request, String customerId, String token) {
        String explain = request.getExplain();
        String user = request.getUser_id();
        String taskid = request.getTaskid();
        String requestSource = request.getRequestSource();
        String yearStr = request.getYear();
        Task old_task = taskService.getById(taskid);
        File filePojo = fileService.getFileById(old_task.getTaskentityid(), request.gettCode(), yearStr);
        if (filePojo != null) {
            if (filePojo.getFilecreatedate() != null) {
                yearStr = DateUtil.DateToString(filePojo.getFilecreatedate(), "yyyy");
            }
        }

        TaskProcessin his = new TaskProcessin();
        his.setTpActid(old_task.getTaskcurrentactid());
        his.setActname("【人工停止】");
        his.setTpDotime(new Date());
        his.setTpExplain(explain);
        his.setTpFlowid(old_task.getTaskflow());
        his.setTpProject(old_task.getTaskproject());
        his.setTpId(GenerateUUID.getUUID());
        his.setTpResult("同意");
        his.setTpEntityid(old_task.getTaskentityid());
        his.setTpName(old_task.getTaskname());
        his.setTpUser(user);
        his.setTpType("停止");
        his.setTpSigntime(old_task.getTaskstarttime());
        his.setTpEndtime(old_task.getTaskendtime());
        his.setTpProject(old_task.getTaskproject());
        his.setTpSection(old_task.getTasksection());
        his.setTpContract(old_task.getTaskcontract());
        his.setTpFrom(old_task.getTasksenduser());
        his.setTpClient(requestSource);
        if (!"All".equals(customerId)) {
            his.setTeId(customerId);
        }
        ;
        taskProcessinService.insertTaskProcessin(his, request.gettCode(), yearStr);

        fileService.updatefile(filePojo.gettCode(), old_task.getTaskentityid(), "stop", old_task.getTaskflow(), customerId, token, yearStr);//更新文档信息的审批状态
        taskService.deleteTaskByTaskEntityID(old_task.getTaskentityid(), customerId);
        taskService.sendToDoTask(old_task, his.getTpId(), "1", "2", token, customerId, request.gettCode(), yearStr);
        return new BaseResponse(true, "流程停止成功！", his.getTpId());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse changeProcessOperator(ChangeProcessOperatorDto request, String customerId, String token) {

        String explain = request.getExplain();
        String user = request.getUser_id();
        String taskid = request.getTaskid();
        String nextuser = request.getNextuser() == null ? "" : request.getNextuser().size() == 0 ? "" : request.getNextuser().stream().findFirst().get().getUserid();//下一步流程操作人
        String yearStr = request.getYear();
        Task old_task = taskService.getById(taskid);
        if (ValidateUtil.isEmpty(yearStr)) {
            File fileObj = fileService.getFileById(old_task.getTaskentityid(), request.gettCode(), yearStr);
            if (fileObj != null) {
                if (fileObj.getFilecreatedate() != null) {
                    yearStr = DateUtil.DateToString(fileObj.getFilecreatedate(), "yyyy");
                }
            }
        }
        if ("撤回".equals(old_task.getTaskType())) {
            return BaseResponse.fail("当前任务是撤回任务，不能更换流程操作人");
        }
        Flow flow = baseMapper.selectById(old_task.getTaskflow());
        FlowActivity FlowActivityobj = flowActivityService.getById(old_task.getTaskcurrentactid());
        Float Actityhour = null;
        if (FlowActivityobj.getActhour() != null) {
            Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
        }
        String requestSource = request.getRequestSource();
        TaskProcessin his = new TaskProcessin();
        his.setActname("【更换流程操作人】");
        his.setTpDotime(new Date());
        his.setTpExplain(explain);
        his.setTpActid(old_task.getTaskcurrentactid());
        his.setTpFlowid(old_task.getTaskflow());
        his.setTpProject(old_task.getTaskproject());
        his.setTpId(GenerateUUID.getUUID());
        his.setTpResult("同意");
        his.setTpEntityid(old_task.getTaskentityid());
        his.setTpName(old_task.getTaskname());
        his.setTpUser(user);
        his.setTpType("提交");
        his.setTpEndtime(new Date());
        his.setTpSigntime(new Date());
        his.setTpSend(nextuser);
        his.setTpActhour(Actityhour);
        his.setTpActtype(FlowActivityobj.getActtype());
        his.setTpFileid(old_task.getTaskentityid());
        his.setTpTemplatecodeo(flow.getTCode());
        his.setTpFrom(old_task.getTasksenduser());
        his.setTpSection(old_task.getTasksection());
        his.setTpContract(old_task.getTaskcontract());
        his.setTpTaskReturnType(old_task.getTaskReturnType());
        his.setTpTaskgetflagstr(old_task.getTaskgetflagstr());
        his.setTpTaskid(old_task.getTaskid());
        his.setTpClient(requestSource);
        if (!"All".equals(customerId)) {
            his.setTeId(customerId);
        }
        taskProcessinService.insertTaskProcessin(his, request.gettCode(), yearStr);
        old_task.setTaskuser(nextuser);
        old_task.setTaskid(GenerateUUID.getUUID());
        taskService.save(old_task);
//        List<String> ids = new ArrayList<>();
//        ids.add(taskid);
        taskService.removeById(taskid);
        taskService.sendToDoTask(old_task, his.getTpId(), "0", "-1", token, customerId, request.gettCode(), yearStr);
        return new BaseResponse(true, "更换流程操作人成功！");

    }


    @Override
    public BaseResponse supplementaryOpinions(SupplementaryOpinionsDto request, String customerId) {
        if (request != null) {
            String taskid = request.getTaskid();//任务id
            String explain = request.getExplain();//意见
            String user = request.getUser_id();//当前用户
            String requestSource = request.getRequestSource();
            String yearStr = request.getYear();

            TaskProcessin Task_Processinentity = taskProcessinService.getTaskProcessById(taskid, request.gettCode(), request.getYear());
            if (Task_Processinentity != null) {
                if (ValidateUtil.isEmpty(yearStr)) {
                    File fileObj = fileService.getFileById(Task_Processinentity.getTpEntityid(), request.gettCode(), yearStr);
                    if (fileObj != null) {
                        if (fileObj.getFilecreatedate() != null) {
                            yearStr = DateUtil.DateToString(fileObj.getFilecreatedate(), "yyyy");
                        }
                    }
                }
                Flow flow = baseMapper.selectById(Task_Processinentity.getTpFlowid());
                TaskProcessin his = new TaskProcessin();
                his.setTpActid(Task_Processinentity.getTpActid());
                his.setActname(Task_Processinentity.getActname());
                his.setTpDotime(new Date());
                his.setTpExplain(explain);
                his.setTpFlowid(flow.getFlowid());
                his.setTpId(GenerateUUID.getUUID());
                his.setTpResult("【补充意见】");
                //his.tp_ruleid = null;//起始活动的历史记录规则为空
                his.setTpEntityid(Task_Processinentity.getTpEntityid());
                his.setTpProject(Task_Processinentity.getTpProject());
                his.setTpName(Task_Processinentity.getTpName());
                his.setTpUser(user);
                his.setTpActtype(Task_Processinentity.getTpActtype());
                his.setTpCountersigntitle(Task_Processinentity.getTpCountersigntitle());
                his.setTpType("补充意见");
                his.setTpStarttime(new Date());
                his.setTpEndtime(new Date());
                his.setTpFileid(Task_Processinentity.getTpId());
                his.setTpTaskid(Task_Processinentity.getTpTaskid());
                his.setTpActname(Task_Processinentity.getTpActname());
                his.setTpClient(requestSource);
                if (!"All".equals(customerId)) {
                    his.setTeId(customerId);
                }
                taskProcessinService.insertTaskProcessin(his, request.gettCode(), yearStr);

                return new BaseResponse(true, "补充意见成功！", his.getTpId());
            } else return new BaseResponse("获取审批记录失败！");
        } else return new BaseResponse("参数丢失！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse withdrawalProcess(WithdrawalProcessDto request, String customerId, String token) {

        //下一步流程操作人
        //String nextuser = request.getNextuser() != null ? "" : request.getNextuser().size() > 0 ? "" : request.nextuser.stream().findFirst().get().getUserid();
        String taskid = request.getTaskid();//任务id
        String explain = request.getExplain();//意见

        String user = request.getUser_id();//当前用户
        String fileId = request.getFileId();//退回的表单fileid
        String requestSource = request.getRequestSource();
        TaskProcessin Task_Processinentitys = null;
        File fileObj = null;
        FlowActivity FlowActivityobj = null;
        String tpTaskgetflagstr = "";
        List<FlowActivity> FlowActivitys = new ArrayList<>();
        List<FlowRule> FlowRules = new ArrayList<>();
        String yearStr = request.getYear();

        String startId = "";
        if (ValidateUtil.isNotEmpty(fileId)) {
            //文档id不为空
            fileObj = fileService.getFileById(fileId, request.gettCode(), yearStr);
            if (ValidateUtil.isEmpty(yearStr)) {
                if (fileObj.getFilecreatedate() != null) {
                    yearStr = DateUtil.DateToString(fileObj.getFilecreatedate(), "yyyy");
                }
            }
            FlowActivitys = flowActivityService.getFlowActivityByActFlowID(fileObj.getFlowId(), customerId);
            //获取当前流程下所有的连接线
            FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(fileObj.getFlowId(), customerId);
            FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
            startId = FlowActivityobj.getActid();
            if (ValidateUtil.isEmpty(taskid)) {
                //获取当前用户最新一次审批记录
                QueryWrapper<TaskProcessin> TaskProcessinWrapper = new QueryWrapper<>();
                TaskProcessinWrapper.eq("tp_entityid", fileId);
                TaskProcessinWrapper.eq("tp_user", user);
                TaskProcessinWrapper.eq("tp_acttype", "userTask");
                TaskProcessinWrapper.eq("tp_type", "提交");//只要提交的
                // TaskProcessinWrapper.eq("tp_actid", FlowActivityobj.getActid());
                TaskProcessinWrapper.ne("ActName", "【更换流程操作人】");
                TaskProcessinWrapper.orderByDesc("tp_dotime");

                if (!"All".equals(customerId)) {
                    TaskProcessinWrapper.eq("Te_Id", customerId);
                }

                List<TaskProcessin> TaskProcessin = taskProcessinService.getList(TaskProcessinWrapper, request.gettCode(), yearStr);
                if (TaskProcessin.size() > 0) {
                    Task_Processinentitys = TaskProcessin.get(0);
                    if (!FlowActivityobj.getActid().equals(Task_Processinentitys.getTpActid())) {
                        FlowActivityobj = flowActivityService.getById(Task_Processinentitys.getTpActid());
                    }

                }
            }
        }
        if (ValidateUtil.isNotEmpty(taskid)) {
            Task_Processinentitys = taskProcessinService.getTaskProcessById(taskid, request.gettCode(), yearStr);
            if (Task_Processinentitys != null && Task_Processinentitys.getActname().equals("【更换流程操作人】")) {
                return new BaseResponse("当前审批记录为”【更换流程操作人】“记录，不支持撤回");
            }
            if (ValidateUtil.isEmpty(fileId)) {
                fileObj = fileService.getFileById(Task_Processinentitys.getTpEntityid(), request.gettCode(), yearStr);
                fileId = fileObj.getFileid();
                FlowActivityobj = flowActivityService.getById(Task_Processinentitys.getTpActid());
                FlowActivitys = flowActivityService.getFlowActivityByActFlowID(fileObj.getFlowId(), customerId);
                //获取当前流程下所有的连接线
                FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(fileObj.getFlowId(), customerId);
                //获取开始流程开始节点
                FlowActivity FlowActivityobj1 = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                if (FlowActivityobj1 != null) {
                    startId = FlowActivityobj1.getActid();
                }
            }

        }

        if (Task_Processinentitys != null) {

            //夺取当前节点的后续可选步骤
            List<String> actity_new = GetNextTask(FlowActivityobj, FlowActivitys, FlowRules);
            actity_new.add(Task_Processinentitys.getTpActid());
            //获取当前用户发出的待办信息
            QueryWrapper<Task> taskWrapper = new QueryWrapper<>();
            taskWrapper.eq("TaskEntityID", Task_Processinentitys.getTpEntityid());
            taskWrapper.eq("TaskSendUser", user);
            taskWrapper.in("TaskPreActID", actity_new);
            if (!"All".equals(customerId)) {
                taskWrapper.eq("Te_Id", customerId);
            }
            List<Task> entitylist = taskService.list(taskWrapper);
            if (entitylist.size() == 0) {
                if (!"start".equals(request.getWithdrawalSteps())) {
                    return new BaseResponse("未查询到当前账号发出的待办信息，无法撤回！");
                } else {
                    //撤回发起人，并且重新走流程
                    FlowRule Ruleobj = new FlowRule();
                    Ruleobj = flowRuleService.lineofretirement(fileObj.getFlowId(), GenerateUUID.getUUID(), FlowActivityobj.getActid(), "撤回", customerId);
                    TaskProcessin his = taskProcessinService.GetHis(fileObj, explain, user, FlowActivityobj, Ruleobj.getRuleid(), new Date(), fileObj.getFiletitle(), "", "", customerId, requestSource);
                    taskProcessinService.insertTaskProcessin(his, request.gettCode(), yearStr);
                    sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, FlowActivityobj.getActtype(), FlowActivityobj.getActid(), FlowActivityobj.getActname(), fileObj.getFileproject(), customerId, request.gettCode());
                    fileService.updatefile(fileObj.gettCode(), fileObj.getFileid(), "start", fileObj.getFlowId(), customerId, token, yearStr);
                    taskService.deleteTaskByTaskEntityID(fileObj.getFileid(), customerId);
                    return new BaseResponse(true, "流程撤回成功!");
                }
            } else {
                //有当前用户发出的待办信息，正常撤回上一步
                if (!FlowActivityobj.getActtype().equals("userTask")) {
                    return new BaseResponse("当前记录为网关记录不能撤回！");
                }
                if (Task_Processinentitys.getTpUser().equals(entitylist.get(0).getTaskuser())) {
                    return new BaseResponse("当前流程已存在该账号任务中！无法撤回！");
                }
                List<String> toDoWithdrawalVerification = new ArrayList<>();
                toDoWithdrawalVerification.add("toDoWithdrawalVerification");
                Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(toDoWithdrawalVerification, customerId);
                Object toDoWithdrawal = smsTemplateList.get("toDoWithdrawalVerification");
                if (!(toDoWithdrawal != null && toDoWithdrawal.equals("1"))) {
                    List<Task> readlist = entitylist.stream().filter(n -> n.getTaskread().equals("已读")).collect(Collectors.toList());
                    if (readlist.size() > 0) {
                        //不能撤回
                        return new BaseResponse("下一步经办人已阅，当前任务无法撤回");
                    }
                }

                //判断并发之后是否允许撤回
                //
                String sendIds = Task_Processinentitys.getTpSend();
                if (ValidateUtil.isNotEmpty(sendIds)) {
                    String[] ids = sendIds.split(",");
                    if (ids.length > entitylist.size()) {
                        //发出的任务数大于现存任务数；不能撤回
                        return new BaseResponse("下一步经办人已经开始办理，当前任务无法撤回");
                    }
                }
                //可以撤回
                FlowRule Ruleobj = new FlowRule();
                Ruleobj = flowRuleService.lineofretirement(Task_Processinentitys.getTpFlowid(), entitylist.get(0).getTaskcurrentactid(), Task_Processinentitys.getTpActid(), "撤回", customerId);
                TaskProcessin his = new TaskProcessin();
                his.setActname("【撤回】");
                his.setTpDotime(new Date());
                his.setTpExplain(explain);
                his.setTpFlowid(Task_Processinentitys.getTpFlowid());
                his.setTpProject(Task_Processinentitys.getTpProject());
                his.setTpId(GenerateUUID.getUUID());
                his.setTpResult("同意");
                his.setTpEntityid(Task_Processinentitys.getTpEntityid());
                his.setTpName(Task_Processinentitys.getTpName());
                his.setTpUser(user);
                his.setTpType("撤回");
                his.setTpActtype(FlowActivityobj.getActtype());
                his.setTpActhour(Task_Processinentitys.getTpActhour());
                his.setTpFrom(user);
                his.setTpFileid(Task_Processinentitys.getTpFileid());
                his.setTpSend(user);
                his.setTpTemplatecodeo(Task_Processinentitys.getTpTemplatecodeo());
                his.setTpActid(entitylist.get(0).getTaskcurrentactid());
                his.setTpRuleid(Ruleobj.getRuleid());
                his.setTpSigntime(Task_Processinentitys.getTpDotime());
                //his.setTpEndtime();
                his.setTpClient(requestSource);
                his.setTpDotime(new Date());
                his.setTpTaskgetflagstr(Task_Processinentitys.getTpTaskgetflagstr());
                his.setTpTaskReturnType(Task_Processinentitys.getTpTaskReturnType());
                if (!"All".equals(customerId)) {
                    his.setTeId(customerId);
                }
                taskProcessinService.insertTaskProcessin(his, request.gettCode(), yearStr);

                double hour = FlowActivityobj.getActhour() == null ? 24d : Double.parseDouble(FlowActivityobj.getActhour() + "");
                Flow flow = new Flow();
                tpTaskgetflagstr = Task_Processinentitys.getTpTaskgetflagstr();
                if (ValidateUtil.isNotEmpty(tpTaskgetflagstr)) {
                    tpTaskgetflagstr = tpTaskgetflagstr.trim();
                }
                //判断是否是跨节点
                List<FlowActivity> nextNodes = GetsNextNode(FlowActivitys, FlowRules, FlowActivityobj);
                if (nextNodes.size() > 0) {
                    if (nextNodes.stream().filter(n -> n.getActid().equals(entitylist.get(0).getTaskcurrentactid())).count() > 0) {
                        tpTaskgetflagstr = "";
                        //相邻节点，需要重新选择
                    }
                }
                flow.setFlowid(Task_Processinentitys.getTpFlowid());
                Task task = GetNewTask(Task_Processinentitys.getTpRuleid(), Task_Processinentitys.getTpActid(), his.getTpActid(), his, entitylist.stream().findFirst().get(), his.getTpName(), user, flow, null, new Date(), DateUtil.DateChangeHour(new Date(), hour), null, null, null, null, tpTaskgetflagstr, Task_Processinentitys.getTpTaskReturnType(), customerId);
                task.setTasksenduser(entitylist.get(0).getTasksenduser());
                File filePojo = fileService.getFileById(Task_Processinentitys.getTpEntityid(), request.gettCode(), yearStr);
                sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                Boolean updataFile = false;
                if (FlowActivityobj.getActid().equals(startId)) {
                    task.setTaskReturnType("start");
                    //设置流程撤回状态
                    updataFile = true;
                    fileService.updatefile(filePojo.gettCode(), Task_Processinentitys.getTpEntityid(), "start", Task_Processinentitys.getTpFlowid(), customerId, token, request.gettCode());

                } else {
                    if (!ValidateUtil.isEmpty(task.getTaskReturnType()) && task.getTaskReturnType().equals("start")) {
                        updataFile = true;
                        fileService.updatefile(filePojo.gettCode(), Task_Processinentitys.getTpEntityid(), "start", Task_Processinentitys.getTpFlowid(), customerId, token, yearStr);
                    }
                }

                List<Task> oldList = new ArrayList<>();
                //获取目前存在的所有待办信息
                oldList = taskService.todolists(fileId, customerId);
                if (!"start".equals(request.getWithdrawalSteps())) {
                    //获取当前节点的所有后置节点
                    List<FlowActivity> nextActs = GetsNextNodeALL(FlowActivitys, FlowRules, FlowActivityobj);
                    nextActs.add(FlowActivityobj);
                    if (nextActs.size() > 0) {
                        List<String> actIds = nextActs.stream().map(n -> n.getActid()).collect(Collectors.toList());
                        String actId = FlowActivityobj.getActid();
                        oldList = oldList.stream().filter(n -> actIds.contains(n.getTaskcurrentactid()) || n.getTaskpreactid().equals(actId)).collect(Collectors.toList());
                    }
                }
                if ("1".equals(tpTaskgetflagstr)) {
                    //原路返回，需要备份待办信息
                    if (oldList.size() > 0) {
                        if (oldList.size() > 0) {
                            //退回发起人，记录
                            for (Task obj : oldList) {
                                SysTaskBackups newTask = new SysTaskBackups();
                                BeanUtils.copyProperties(obj, newTask);
                                newTask.setTaskIdNew(task.getTaskid());
                                newTask.setTasksendtime(new Date());
                                newTask.setTaskstarttime(new Date());
                                try {
                                    BigDecimal bg = DateUtil.getDifferHour(obj.getTaskstarttime().toString(), obj.getTaskendtime().toString());
                                    newTask.setTaskendtime(DateUtil.DateChangeHour(newTask.getTaskstarttime(), Double.parseDouble(bg.toString())));
                                } catch (Exception ex) {
                                    log.error("流程服务：", ex);
                                }
                                sysTaskBackupsService.save(newTask);
                            }
                            //沟通任务备份
                            List<String> taskId = oldList.stream().map(n -> n.getTaskid()).collect(Collectors.toList());
                            sysCoordinationTaskBackupsService.backups(taskId, customerId);
                        }
                    }
                }
                if (oldList.size() > 0) {
                    //删除待办信息
                    taskService.removeBatchByIds(oldList.stream().map(n -> n.getTaskid()).collect(Collectors.toList()));
                }
                task.setTaskType("撤回");
                //重新设置当前任务状态
                //获取当前流程的所有审批记录信息
                QueryWrapper<TaskProcessin> tpWrapper = new QueryWrapper<>();
                tpWrapper.eq("tp_entityid", Task_Processinentitys.getTpEntityid());
                // tpWrapper.eq("tp_type","退回");
                List<TaskProcessin> tpListAll = taskProcessinService.getList(tpWrapper, request.gettCode(), yearStr);
                List<String> tpIds = tpListAll.stream().filter(n -> "退回".equals(n.getTpType())).map(n -> n.getTpId()).collect(Collectors.toList());
                if (tpIds.size() > 0) {
                    QueryWrapper<TaskProcessUser> tpuWrapper = new QueryWrapper<>();
                    tpuWrapper.eq("tpu_Send_Activityid", Task_Processinentitys.getTpActid());
                    tpuWrapper.in("Tpu_Tp_Id", tpIds);
                    List<TaskProcessUser> tpuList = taskProcessUserService.getList(tpuWrapper, request.gettCode(), yearStr);
                    if (tpuList.size() > 0) {
                        List<String> tpuIds = tpuList.stream().map(n -> n.getTpuTpId()).collect(Collectors.toList());
                        TaskProcessin tp = tpListAll.stream().filter(n -> tpuIds.contains(n.getTpId())).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).findFirst().get();
                        //获取最近一次驳回之后是否还有其它审批记录信息
                        List<TaskProcessin> otherList = tpListAll.stream().filter(n -> (!n.getTpUser().equals(user)) && n.getTpDotime().compareTo(tp.getTpDotime()) == 1).collect(Collectors.toList());
                        if (otherList.size() == 0) {
                            task.setTaskType("退回");
                        }
                    }
                }
                task.setContractId(filePojo.getFilecontract());
                task.setProjectId(filePojo.getFileproject());
                task.setSecId(filePojo.getFilesectionid());
                taskService.save(task);
                for (Task taskobj : entitylist) {
                    taskService.sendToDoTask(taskobj, his.getTpId(), "1", "2", token, customerId, request.gettCode(), yearStr);
                }
                return new BaseResponse(true, "流程撤回成功!");
            }
        } else {
            return new BaseResponse("获取审批记录失败！");
        }
    }


    @Override
    public String GetEnableFlowID(String solutionID, String companyID, String projectID, String templateCode, String customerId) throws Exception {
        // QueryWrapper<FlowPojo> wrapper = new QueryWrapper<>();
//        wrapper.eq("FlowEnable", "1");
//        wrapper.eq("T_Code", templateCode);
        //List<FlowPojo> datas = baseMapper.selectList(wrapper);
        if (ValidateUtil.isEmpty(projectID)) {
            projectID = "ALL";
        }
        if (ValidateUtil.isEmpty(companyID)) {
            companyID = "ALL";
        }
        List<Flow> datas = baseMapper.getSomeFieldsFlow(templateCode, customerId);
        //组织结构ID不为空，获取当前流程的所有组织机构信息
        List<Department> dList = departmentService.getDepartmentAll(customerId);
        if (!ValidateUtil.isEmpty(companyID)) {

            //如果项目ID不是"ALL"，则查找对应的项目流程，如果找不到流程，则使用项目通用流程
            if (!projectID.equals("ALL")) {
                String projectIDstr = projectID;
                String companyIDstr = companyID;
                List<Flow> flowOld = datas.stream().filter(e -> e.getFlowproject().equals(projectIDstr)).collect(Collectors.toList());
                List<Flow> flow = getDepartmentFlow(flowOld, dList, companyIDstr);
                if (flow.size() == 0) {
                    flow = flowOld.stream().filter(n -> n.getFlowcompany() != null && n.getFlowcompany().equals("ALL")).collect(Collectors.toList());
                }
                if (flow.size() > 0) {
                    return flow.get(0).getFlowid().toString();
                } else {
                    List<Flow> objOld = datas.stream().filter(e -> e.getFlowproject().equals("ALL")).collect(Collectors.toList());
                    List<Flow> obj = getDepartmentFlow(objOld, dList, companyIDstr);
                    if (obj.size() > 0) {
                        return obj.get(0).getFlowid().toString();
                    } else {
                        List<Flow> entity = datas.stream().filter(e -> e.getFlowproject().equals("ALL") && e.getFlowcompany().equals("ALL")).collect(Collectors.toList());
                        if (entity.size() > 0) {
                            return entity.get(0).getFlowid().toString();
                        } else {
                            return null;
                        }
                    }
                }
            } else {
                String companyIDstr = companyID;
                String projectIDstr = projectID;
                List<Flow> objOld = datas.stream().filter(e -> e.getFlowproject().equals(projectIDstr)).collect(Collectors.toList());
                List<Flow> obj = getDepartmentFlow(objOld, dList, companyIDstr);
                if (obj.size() > 0) {
                    return obj.get(0).getFlowid().toString();
                } else {
                    List<Flow> objentity = datas.stream().filter(e -> e.getFlowproject().equals(projectIDstr) && e.getFlowcompany().equals("ALL")).collect(Collectors.toList());
                    if (objentity.size() > 0) {
                        return objentity.get(0).getFlowid().toString();
                    } else {
                        return null;
                    }
                }
            }
        } else {

            //如果项目ID不是"ALL"，则查找对应的项目流程，如果找不到流程，则使用项目通用流程
            if (!projectID.equals("ALL")) {
                String projectIDstr = projectID;
                String companyIDstr = companyID;
                List<Flow> objOld = datas.stream().filter(e -> e.getFlowproject().equals(projectIDstr)).collect(Collectors.toList());
                List<Flow> flow = getDepartmentFlow(objOld, dList, companyIDstr);
                if (flow.size() > 0) {
                    return flow.get(0).getFlowid().toString();
                } else {
                    List<Flow> obj = datas.stream().filter(e -> e.getFlowproject().equals("ALL")).collect(Collectors.toList());
                    if (obj.size() > 0) return obj.get(0).getFlowid().toString();
                    else return null;
                }
            } else {
                String projectIDstr = projectID;
                List<Flow> obj = datas.stream().filter(e -> e.getFlowproject().equals(projectIDstr)).collect(Collectors.toList());
                if (obj.size() > 0) {
                    return obj.get(0).getFlowid().toString();
                } else {
                    return null;
                }
            }
        }
    }


    //获取组织机构流程信息
    public List<Flow> getDepartmentFlow(List<Flow> flowList, List<Department> dList, String cId) {
        if (flowList.size() == 0 || dList.size() == 0 || ValidateUtil.isEmpty(cId)) {
            return new ArrayList<>();
        }
        List<Department> newDList = dList.stream().filter(n -> n.getdId().equals(cId)).collect(Collectors.toList());
        if (newDList.size() > 0) {
            String newDid = newDList.get(0).getdId();
            return getDepartmentParentFlow(flowList, dList, newDid);

        } else {
            return new ArrayList<>();
        }
    }

    public List<Flow> getDepartmentParentFlow(List<Flow> flowList, List<Department> dList, String dId) {
        List<Department> newDList = dList.stream().filter(n -> n.getdId().equals(dId)).collect(Collectors.toList());
        if (newDList.size() > 0) {
            String newDid = newDList.get(0).getdId();
            List<Flow> newFileLisd = flowList.stream().filter(n -> n.getFlowcompany() != null && n.getFlowcompany().equals(newDid)).collect(Collectors.toList());
            if (newFileLisd.size() > 0) {
                return newFileLisd;
            } else {
                String dpId = newDList.get(0).getdParentid();
                if (dpId != null && !dpId.equals("0")) {
                    return getDepartmentParentFlow(flowList, dList, dpId);
                } else {
                    return new ArrayList<>();
                }
            }
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public BaseResponse getFlowList(SysFlowRequest request, String uId, String customerId) {
        Map<String, Object> params = new HashMap<>();
        if (request.isFilterOwnerDep()) {
            List<String> depIds = sysUserService.getAllChildDepartment(uId, customerId, customerId);
            if (CollectionUtils.isEmpty(depIds)) return BaseResponse.ok(new ArrayList<>(), 0);
            params.put("ownerDepIds", depIds);
        }
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "FlowCreateTime");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "desc");

        params.put("flowname", !ValidateUtil.isEmpty(request.getFlowname()) ? request.getFlowname() : null);
        params.put("flowSolutionName", !ValidateUtil.isEmpty(request.getFlowSolutionName()) ? request.getFlowSolutionName() : null);
        params.put("flowCompanyName", !ValidateUtil.isEmpty(request.getFlowCompanyName()) ? request.getFlowCompanyName() : null);
        params.put("tName", !ValidateUtil.isEmpty(request.gettName()) ? request.gettName() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<SysFlowResponse> datas = baseMapper.getFlowList(params);
        Integer total = baseMapper.getFlowListCount(params);
        return new BaseResponse("获取成功", datas, total);
    }


    /**
     * @param nowSYS_FlowActivity
     * @param FlowActivityAll
     * @param FlowRuleAll
     * @return
     */
    @Override
    public List<String> GetNextTask(FlowActivity nowSYS_FlowActivity, List<FlowActivity> FlowActivityAll, List<FlowRule> FlowRuleAll) {
        List<String> newid = new ArrayList<String>();
        //List<TaskPojo> list_new = new ArrayList<>();
        //获取当前流程节点的下级连接线
        List<FlowRule> FlowRuleList = FlowRuleAll.stream().filter(n -> n.getRulebeginactid() != null && n.getRulebeginactid().equals(nowSYS_FlowActivity.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        //判断当前节点是否是强制强制设置设置了，强制并发或者强制合并
        String oldActTypestr = nowSYS_FlowActivity.getActtype();
        //List<FlowActivityPojo> currentlist = new ArrayList<>();
        int statuso = FlowRuleList.size();//满足条件的数量；
        int statusn = 0;//实际满足条件的数量
        int number_str = 0;
        for (FlowRule item : FlowRuleList) {
            FlowActivity newactivity = FlowActivityAll.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).findFirst().get();
            newid.add(newactivity.getActid());
            //计算虚拟的任务信息
            String ActTypestr = newactivity.getActtype();
            if (ActTypestr.equals("exclusiveGateway")) {
                //排他网关
                //只走第一次满足条件的流出线
                newid.addAll(GetNextTask(newactivity, FlowActivityAll, FlowRuleAll));
            } else if (ActTypestr.equals("parallelGateway")) {
                //并行网关
                //并行网关， 用来在流转中实现无条件的聚合分支决策和发散分支决策。当流程执行到这个网关，所有流出的迁移线条件都会被忽略，
                //只有全部流入的分支都到达时才会触发发散分支行为。所有流出的迁移线都会被选中。让流程沿着所有流出的迁移线并行运行,强制并发，强制合并
                //获取历史审批记录，判断当前并行网关的流入线都已执行完成
                newid.addAll(GetNextTask(newactivity, FlowActivityAll, FlowRuleAll));
            } else if (ActTypestr.equals("inclusiveGateway")) {
                //包容网关
                newid.addAll(GetNextTask(newactivity, FlowActivityAll, FlowRuleAll));
            }
        }
        return newid;
    }


    public Task GetNewTask(String RuleID, String RuleEndActID, String RuleBeginActID, TaskProcessin his, Task old_task, String taskname, String nextUser, Flow flow, String TaskCoordination, Date TaskStartTime, Date TaskEndTime, String TaskRB, Date TaskReadTime, String TaskSection, String TaskContract, String TaskGetFlagstr, String TaskReturnType, String customerId) {
        Task entity = new Task();
        entity.setTaskid(GenerateUUID.getUUID());
        entity.setTaskflow(flow.getFlowid());
        entity.setTaskrule(RuleID);
        entity.setTaskentityid(his.getTpEntityid());
        entity.setTaskclient(flow.getFlowclient());
        entity.setTaskproject("0");
        entity.setTaskcurrentactid(RuleEndActID);
        entity.setTaskpreactid(RuleBeginActID);
        entity.setTaskuser(nextUser);
        entity.setTasksendtime(new Date());
        entity.setTaskname(taskname);
        entity.setTasksenduser(his.getTpUser());
        entity.setTaskparenttaskid(old_task.getTaskparentflowid());
        entity.setTaskparentflowid(old_task.getTaskparenttaskid());
        entity.setTaskparentactid(TaskCoordination);
        entity.setTaskcoordination(TaskCoordination);
        entity.setTaskstarttime(TaskStartTime);
        entity.setTaskendtime(TaskEndTime);
        entity.setTaskrb(TaskRB);
        entity.setTaskread("未读");
        entity.setTaskreadtime(TaskReadTime);
        entity.setTasksection(TaskSection);
        entity.setTaskcontract(TaskContract);
        entity.setTaskgetflagstr(TaskGetFlagstr);
        entity.setTaskReturnType(TaskReturnType);
        if (!"All".equals(customerId)) {
            entity.setTeId(customerId);
        }
        return entity;
    }


    private Map<String, String> Returnstart(Flow flow, Task taskentity, String explain, String file_id, String user_id, List<Task> tasklist, List<String> cc_Users, String returntype, String returnStatus, String customerId, String requestSource, String token, String tCode, String yearStr) {
        String flowid = flow.getFlowid();

        List<FlowActivity> FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flow.getFlowid(), customerId);
        //获取当前流程下所有的连接线
        List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flow.getFlowid(), customerId);
        FlowActivity FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
        List<TaskProcessin> SYS_FlowActivitylist = taskProcessinService.getTaskProcessinByReturnstart(file_id, flowid, FlowActivityobj.getActid(), customerId, tCode, yearStr);
        TaskProcessin startask = SYS_FlowActivitylist.stream().sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).findFirst().get();
        String Taskproseeid = "";
        if (startask != null) {
            //生成退回连接线
            FlowRule Ruleobj = new FlowRule();
            Float Actityhour = null;
            if (FlowActivityobj.getActhour() != null) {
                Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
            }
            Ruleobj = flowRuleService.lineofretirement(flowid, taskentity.getTaskcurrentactid(), FlowActivityobj.getActid(), "退回", customerId);
            TaskProcessin his = taskProcessinService.GetHis(taskentity, "退回", explain, user_id, "退回", flow, startask.getTpUser(), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体
            Taskproseeid = his.getTpId();
            taskProcessinService.insertTaskProcessin(his, tCode, yearStr);
            File filePojo = fileService.getFileById(taskentity.getTaskentityid(), tCode, yearStr);
            sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, FlowActivityobj.getActtype(), startask.getTpActid(), startask.getActname(), filePojo.getFileproject(), customerId, tCode);
            Task task_new = taskService.GetNewTaskobj(Ruleobj, his, taskentity, flow, startask.getTpUser(), FlowActivityobj.getActhour() + "", taskentity.getTaskname(), customerId);
            task_new.setSecId(filePojo.getFilesectionid());
            task_new.setProjectId(filePojo.getFileproject());
            task_new.setContractId(filePojo.getFilecontract());
            if (returnStatus.equals("1")) {
                //允许退回，删除所有的待办信息
                //获取目前存在的所有待办信息
                List<Task> oldList = taskService.todolists(file_id, customerId);
                if (oldList.size() > 0) {
                    List<Task> oldLists = oldList.stream().filter(n -> !n.getTaskid().equals(taskentity.getTaskid())).collect(Collectors.toList());
                    if (oldLists.size() > 0) {
                        //退回发起人，记录
                        for (Task obj : oldLists) {
                            SysTaskBackups newTask = new SysTaskBackups();
                            BeanUtils.copyProperties(obj, newTask);
                            /// newTask.setTaskid(GenerateUUID.getUUID());
                            newTask.setTaskIdNew(task_new.getTaskid());
                            newTask.setTasksendtime(new Date());
                            newTask.setTaskstarttime(new Date());
                            try {
                                BigDecimal bg = DateUtil.getDifferHour(obj.getTaskstarttime().toString(), obj.getTaskendtime().toString());
                                newTask.setTaskendtime(DateUtil.DateChangeHour(newTask.getTaskstarttime(), Double.parseDouble(bg.toString())));
                            } catch (Exception ex) {
                                log.error("流程服务：", ex);
                            }
                            sysTaskBackupsService.save(newTask);
                        }

                        //沟通任务备份
                        List<String> taskId = oldLists.stream().map(n -> n.getTaskid()).collect(Collectors.toList());
                        sysCoordinationTaskBackupsService.backups(taskId, customerId);
                    }
                }
                taskService.deleteTaskByTaskEntityID(taskentity.getTaskentityid(), customerId);
            } else {
                taskService.removeById(taskentity.getTaskid());
            }
            task_new.setTaskReturnType("start");
            task_new.setTaskgetflagstr(returntype);
            taskService.save(task_new);
            taskProcessUserService.GetNewTaskProcessUse(his.getTpId(), task_new.getTaskuser(), task_new.getTaskcurrentactid(), customerId, tCode, yearStr);
            //删除原有的待办信息


            //生成抄送信息
            carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, taskentity.getTaskentityid(), taskentity.getTaskname(), his.getTpId(), cc_Users, "0", customerId, token, filePojo.getFileproject(), tCode, yearStr);
            //判断当前审批节点是否需要发送短信
            List<String> nextuser_o = new ArrayList<>();
            nextuser_o.add(task_new.getTasksenduser());
            //TODO：生成短信息和邮件等通知信息新增sql语句
            //sql_all_str.AddRange(Get_shortmessage(FlowActivityobj, taskentity, nextuser_o, explain, user_id));
            tasklist.add(task_new);
            if ("1".equals(returntype)) {
                SysTaskReturnMap objp = new SysTaskReturnMap();
                objp.setFileId(file_id);
                objp.setOperateAct(taskentity.getTaskcurrentactid());
                objp.setOperateActUser(taskentity.getTaskuser());
                objp.setOperateTargetAct(task_new.getTaskcurrentactid());
                objp.setTaksId(task_new.getTaskid());
                sysTaskReturnMapService.create(objp, customerId);
            }
        }
        Map<String, String> returndata = new HashMap<>();
        returndata.put("returnstr", "start");
        returndata.put("taskproseeid", Taskproseeid);
        returndata.put("userList", JacksonUtils.toJSONString(tasklist.stream().map(n -> n.getTaskuser()).collect(Collectors.toList())));
        return returndata;
    }

    private Map<String, String> Takestepback(Flow flow, Task taskentity, FlowActivity old_activity, String explain, String file_id, String user_id, List<Task> tasklist, List<String> cc_Users, String customerId, String requestSource, String token, String tCode, String yearStr) throws Exception {
        List<String> sql_all_str = new ArrayList<>();
        String flowid = flow.getFlowid();
        String returnstr = "pre";
        String taskproseeid = "";
        //获取当前流程下的所有流程几点信息
        List<FlowActivity> FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
        //获取当前流程下所有的连接线
        List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);

        //获取当前文档下所有的审批记录信息
        List<TaskProcessin> Task_Processinlist = taskProcessinService.getListByTpFlowIdAndTpEntityId(flowid, file_id, customerId, yearStr);
        List<FlowActivity> _FlowActivitylist = Getspreviousnode(FlowActivitys, FlowRules, old_activity);


        List<String> actlist = _FlowActivitylist.stream().map(FlowActivity::getActid).collect(Collectors.toList());
        TaskProcessin Task_Processinold = Task_Processinlist.stream().filter(n -> actlist.contains(n.getTpActid())).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).findFirst().get();
        if (Task_Processinold != null) {
            FlowActivity Returnednode = _FlowActivitylist.stream().filter(n -> n.getActid().equals(Task_Processinold.getTpActid())).findFirst().get();
            if (Returnednode != null) {
                if (Allowedreturn(FlowActivitys, FlowRules, Returnednode)) {
                    //判断退回节点是否是开始节点
                    List<FlowActivity> _FlowActivitystartlist = Getspreviousnode(FlowActivitys, FlowRules, Returnednode);

                    //生成退回连接线
                    FlowRule Ruleobj = null;
                    Ruleobj = flowRuleService.lineofretirement(flowid, taskentity.getTaskcurrentactid(), Returnednode.getActid(), "退回", customerId);
                    Float Actityhour = null;
                    if (Returnednode.getActhour() != null) {
                        Actityhour = Float.parseFloat(Returnednode.getActhour() + "");
                    }
                    TaskProcessin his = taskProcessinService.GetHis(taskentity, "退回", explain, user_id, "退回", flow, Task_Processinold.getTpUser(), Returnednode.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体
                    taskproseeid = his.getTpId();
                    taskProcessinService.insertTaskProcessin(his, tCode, yearStr);
                    File filePojo = fileService.getFileById(taskentity.getTaskentityid(), tCode, yearStr);
                    sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, Returnednode.getActtype(), Task_Processinold.getTpActid(), Task_Processinold.getActname(), filePojo.getFileproject(), customerId, tCode);
                    Task task_new = taskService.GetNewTaskobj(Ruleobj, his, taskentity, flow, Task_Processinold.getTpUser(), Returnednode.getActhour() + "", taskentity.getTaskname(), customerId);
                    task_new.setSecId(filePojo.getFilesectionid());
                    task_new.setProjectId(filePojo.getFileproject());
                    task_new.setContractId(filePojo.getFilecontract());
                    if (_FlowActivitystartlist.size() > 0) {
                        if (_FlowActivitystartlist.get(0).getActtype().equals("startEvent")) {
                            task_new.setTaskReturnType("start");
                            returnstr = "start";
                        }
                    }
                    List<Task> oldList = new ArrayList<>();
                    oldList = taskService.todolists(file_id, customerId);
                    if (!"start".equals(returnstr)) {
                        List<FlowActivity> nextActs = GetsNextNodeALL(FlowActivitys, FlowRules, Returnednode);
                        nextActs.add(Returnednode);
                        if (nextActs.size() > 0) {
                            List<String> actIds = nextActs.stream().map(n -> n.getActid()).collect(Collectors.toList());
                            oldList = oldList.stream().filter(n -> actIds.contains(n.getTaskcurrentactid())).collect(Collectors.toList());
                        }
                    }
                    taskService.removeBatchByIds(oldList.stream().map(n -> n.getTaskid()).collect(Collectors.toList()));
                    taskService.save(task_new);
                    taskProcessUserService.GetNewTaskProcessUse(his.getTpId(), task_new.getTaskuser(), task_new.getTaskcurrentactid(), customerId, tCode, yearStr);
                    taskService.deleteTaskByEntityIdAndCurrentActId(his.getTpEntityid(), taskentity.getTaskcurrentactid(), taskentity.getTaskid(), customerId);

                    //生成抄送信息
                    carbonCopyService.GetCarbonCopyList(Returnednode, user_id, taskentity.getTaskentityid(), taskentity.getTaskname(), his.getTpId(), cc_Users, "0", customerId, token, filePojo.getFileproject(), tCode, yearStr);
                    //判断当前审批节点是否需要发送短信
                    List<String> nextuser_o = new ArrayList<>();
                    nextuser_o.add(task_new.getTaskuser());
                    //TODO:生成短信息和邮件等通知信息新增sql语句
                    //sql_all_str.AddRange(Get_shortmessage(Returnednode, taskentity, nextuser_o, explain, user_id));
                    // tasklist.add(task_new);
                    if ("1".equals(task_new.getTaskgetflagstr())) {
                        SysTaskReturnMap objp = new SysTaskReturnMap();
                        objp.setFileId(file_id);
                        objp.setOperateAct(taskentity.getTaskcurrentactid());
                        objp.setOperateActUser(taskentity.getTaskuser());
                        objp.setOperateTargetAct(task_new.getTaskcurrentactid());
                        objp.setTaksId(task_new.getTaskid());
                        sysTaskReturnMapService.create(objp, customerId);
                    }
                    Map<String, String> returndata = new HashMap<>();
                    returndata.put("returnstr", returnstr);
                    returndata.put("taskproseeid", taskproseeid);
                    returndata.put("userList", JacksonUtils.toJSONString(nextuser_o));
                    return returndata;
                } else {
                    throw new RuntimeException("当前流程无法退回！");
                }
            } else {
                throw new RuntimeException("流程退回失败！");
            }
        } else throw new RuntimeException("获取退回步骤失败！");

    }

    private String Submissionstatusdetermination(String status) {
        if (status.equals("StartNode")) {
            return "[initiator]";
        } else if (status.equals("PreviousNode")) {
            return "[laststep]";
        } else {
            return "";
        }
    }

    private List<FlowActivity> Getspreviousnode(List<FlowActivity> FlowActivitylist, List<FlowRule> FlowRuleslist, FlowActivity old_activity) {
        List<FlowActivity> FlowActivityList = new ArrayList<>();
        //反推查询（线上条件进行忽略）：避免因后续证件扭转条件导致退回失败
        List<FlowRule> new_list = FlowRuleslist.stream().filter(n -> n.getRulesystype().equals("提交") && n.getRuleendactid().equals(old_activity.getActid())).collect(Collectors.toList());
        for (FlowRule item : new_list) {
            FlowActivity activityobj = FlowActivitylist.stream().filter(n -> n.getActid().equals(item.getRulebeginactid())).findFirst().get();
            if (activityobj != null)
                if (activityobj.getActtype().equals("exclusiveGateway") || activityobj.getActtype().equals("parallelGateway") || activityobj.getActtype().equals("inclusiveGateway")) {
                    FlowActivityList.addAll(Getspreviousnode(FlowActivitylist, FlowRuleslist, activityobj));
                } else {
                    FlowActivityList.add(activityobj);
                }
        }
        return FlowActivityList;
    }

    private List<FlowActivity> GetsNextNode(List<FlowActivity> FlowActivitylist, List<FlowRule> FlowRuleslist, FlowActivity old_activity) {
        List<FlowActivity> FlowActivityList = new ArrayList<>();
        //反推查询（线上条件进行忽略）：避免因后续证件扭转条件导致退回失败
        List<FlowRule> new_list = FlowRuleslist.stream().filter(n -> n.getRulesystype().equals("提交") && n.getRulebeginactid().equals(old_activity.getActid())).collect(Collectors.toList());
        for (FlowRule item : new_list) {
            FlowActivity activityobj = FlowActivitylist.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).findFirst().get();
            if (activityobj != null)
                if (activityobj.getActtype().equals("exclusiveGateway") || activityobj.getActtype().equals("parallelGateway") || activityobj.getActtype().equals("inclusiveGateway")) {
                    FlowActivityList.addAll(GetsNextNode(FlowActivitylist, FlowRuleslist, activityobj));
                } else {
                    FlowActivityList.add(activityobj);
                }
        }
        return FlowActivityList;
    }

    private List<FlowActivity> GetsNextNodeALL(List<FlowActivity> FlowActivitylist, List<FlowRule> FlowRuleslist, FlowActivity old_activity) {
        List<FlowActivity> FlowActivityList = new ArrayList<>();
        //反推查询（线上条件进行忽略）：避免因后续证件扭转条件导致退回失败
        List<FlowRule> new_list = FlowRuleslist.stream().filter(n -> n.getRulebeginactid().equals(old_activity.getActid())).collect(Collectors.toList());
        for (FlowRule item : new_list) {
            FlowActivity activityobj = FlowActivitylist.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).findFirst().get();
            if (activityobj != null) {
                FlowActivityList.add(activityobj);
                FlowActivityList.addAll(GetsNextNodeALL(FlowActivitylist, FlowRuleslist, activityobj));
            }
        }
        return FlowActivityList;
    }

    private Boolean Allowedreturn(List<FlowActivity> FlowActivitylist, List<FlowRule> FlowRuleslist, FlowActivity old_activit) {
        FlowRule FlowRuleold = FlowRuleslist.stream().filter(n -> n.getRulebeginactid().equals(old_activit.getActid())).findFirst().get();
        FlowActivity FlowActivityold_ = null;
        List<FlowActivity> flowlist = FlowActivitylist.stream().filter(n -> n.getActid().equals(FlowRuleold.getRuleendactid())).collect(Collectors.toList());
        if (flowlist.size() > 0) {
            FlowActivityold_ = flowlist.get(0);
        }
        if (FlowActivityold_ != null && FlowActivityold_.getActtype() != null && FlowActivityold_.getActtype().equals("parallelGateway")) {
            //退回节点的下一步节点不是并行网关
            String acid = FlowActivityold_.getActid();
            List<FlowRule> _FlowRules = FlowRuleslist.stream().filter(n -> n.getRuleendactid().equals(acid)).collect(Collectors.toList());
            List<String> flowruleid = _FlowRules.stream().map(FlowRule::getRulebeginactid).collect(Collectors.toList());
            List<FlowActivity> new_ActivityE = FlowActivitylist.stream().filter(n -> flowruleid.contains(n.getActid())).collect(Collectors.toList());
            if (new_ActivityE.size() == 1 && flowruleid.contains(old_activit.getActid())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public BaseResponse getflowmap(FilerecordRequest requst, String customerId) {
        String flowIds = "";
        if (ValidateUtil.isEmpty(requst.getEntityid())) {
            try {
                flowIds = GetEnableFlowID(requst.getSolutionId(), requst.getCid(), requst.getPid(), requst.getTemplatecode(), customerId);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        File fileobj = null;
        String fileStatus = "";
        String yearStr = requst.getYear();
        if (ValidateUtil.isNotEmpty(requst.getEntityid())) {
            fileobj = fileService.getFileById(requst.getEntityid(), requst.gettCode(), yearStr);
            if (fileobj != null) {
                if (fileobj.getFilecreatedate() != null) {
                    yearStr = DateUtil.DateToString(fileobj.getFilecreatedate(), "yyyy");
                }
                flowIds = fileobj.getFlowId();
                fileStatus = fileobj.getFileflowstatus() + "";
            }
        }
        if (ValidateUtil.isEmpty(flowIds)) {
            try {
                flowIds = GetEnableFlowID(requst.getSolutionId(), requst.getCid(), requst.getPid(), requst.getTemplatecode(), customerId);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        String keyStr = GenerateUUID.getUUID();
        //获取当前流程下所有的审批节点
        List<FlowActivity> activity = flowActivityService.getFlowActivityByActFlowID(flowIds, customerId);
        //获取当前流程下的所有审批连接线
        List<FlowRule> Rule = flowRuleService.getFlowRuleListByRuleFlowID(flowIds, customerId);
        //获取当前流程的版本信息
        List<String> actityid_list = new ArrayList<>();
        List<FlowActivity> sstaactities = activity.stream().filter(item -> item.getActtype().equals("startEvent")).collect(Collectors.toList());
        if (sstaactities != null && sstaactities.size() < 1) return new BaseResponse("流程配置错误！");
        FlowActivity staactity = sstaactities.stream().findFirst().get();
        //计算第一步
        List<FlowActivity> FlowActivityList = flowActivityService.GetSYSFlowActivityEntitybind(staactity, new HashMap<>(), activity, Rule, 0, customerId);
        //计算流程的开始节点
        actityid_list.add(staactity.getActid());
        FlowActivity fristactity = FlowActivityList.stream().sorted(Comparator.comparing(FlowActivity::getActname).reversed()).findFirst().get();

        List<FlowActivity> flowactivtynew = new ArrayList<>();
        List<FlowRule> newrulelist = new ArrayList<>();
        //获取流程图过滤信息
        List<String> flowMapFiltering = new ArrayList<>();
        flowMapFiltering.add("flowMapFiltering");
        flowMapFiltering.add("processSkipShielding");
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(flowMapFiltering, customerId);
        Object flowMapFilter = smsTemplateList.get("flowMapFiltering");
        Object processSkipShielding = smsTemplateList.get("processSkipShielding");
        if (flowMapFilter != null && flowMapFilter.equals("1")) {
            Map<String, Object> newConditions = flowActivityService.setTorsionConditions(requst.getEntityid(), "", requst.getCondition(), customerId, requst.gettCode(), yearStr);
            List<String> allActIds = new ArrayList<>();
            flowactivtynew = flowActivityService.GetFlowActivityList(keyStr, staactity, newConditions, activity, Rule, 0, "1", Arrays.asList("cId", "cCreditCode", "cName"), customerId, allActIds);
            flowactivtynew.add(staactity);
            List<String> actidlist = flowactivtynew.stream().map(n -> n.getActid()).collect(Collectors.toList());
            newrulelist = Rule.stream().filter(n -> actidlist.contains(n.getRulebeginactid()) && actidlist.contains(n.getRuleendactid())).collect(Collectors.toList());
        }
        flowActivityService.setActListALLToNull(keyStr);
        Flow obj = baseMapper.selectById(flowIds);
        List<TaskProcessResponse> newData = new ArrayList<>();
        List<TaskProcessin> tpList = new ArrayList<>();
        List<Map<String, Object>> taskdata = new ArrayList<>();
        List<String> taskActIds = new ArrayList<>();
        if (fileobj != null) {
            flowIds = fileobj.getFlowId();
            if (!ValidateUtil.isEmpty(flowIds)) {

                String fileStatusO = fileobj.getFileflowstatus() + "";
                //获取当前流程的所有审批记录
                List<TaskProcessin> Task_ProcessinAll = taskProcessinService.getListByTpFlowIdAndTpEntityId(flowIds, fileobj.getFileid(), customerId, yearStr);
                //只要提交的审批记录信息
                Task_ProcessinAll = Task_ProcessinAll.stream().filter(n -> n.getTpType().equals("提交")).collect(Collectors.toList());
                tpList = Task_ProcessinAll;
                List<Task> taskobj = taskService.todolists(requst.getEntityid(), customerId);

                //获取发起人节点
                List<TaskProcessin> strtask = Task_ProcessinAll.stream().filter(n -> n.getTpActid() != null && n.getTpActid().equals(fristactity.getActid()) && (n.getTpTaskgetflagstr() == null || (n.getTpTaskgetflagstr() != null && !n.getTpTaskgetflagstr().trim().equals("1")))).collect(Collectors.toList());
                if (strtask.size() > 0) {
                    Date startdotime = strtask.parallelStream().sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList()).get(0).getTpDotime();//获取最新一次不是原路返回的流程处理记录
                    Task_ProcessinAll = Task_ProcessinAll.stream().filter(n -> n.getTpDotime().getTime() >= startdotime.getTime()).collect(Collectors.toList());
                }
                List<TaskProcessin> TaskProcessinentity = new ArrayList<>();
                Map<String, String> new_TaskProcessi = flowActivityService.Getflowline(staactity, taskobj, Task_ProcessinAll, activity, Rule, customerId);
                TaskProcessinentity = JacksonUtils.parseObject(new_TaskProcessi.get("TaskProcessin_new"), new TypeReference<List<TaskProcessin>>() {
                });
                List<FlowRule> new_FlowRule = JacksonUtils.parseObject(new_TaskProcessi.get("FlowRulePojo_new"), new TypeReference<List<FlowRule>>() {
                });
                actityid_list.addAll(new_FlowRule.stream().map(n -> n.getRuleid()).collect(Collectors.toList()));
                actityid_list.addAll(TaskProcessinentity.stream().map(n -> n.getTpActid()).collect(Collectors.toList()));
                //计算结束节点
                if (fileobj.getFileflowstatus() == 7) {
                    List<TaskProcessin> actLists = TaskProcessinentity.stream().filter(n -> n.getTpType().equals("提交")).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList());
                    if (!ValidateUtil.isEmpty(actLists)) {
                        TaskProcessin actidList = actLists.get(0);
                        List<String> ruleStr = Rule.stream().filter(n -> actidList.getTpActid().equals(n.getRulebeginactid())).map(n -> n.getRuleendactid()).collect(Collectors.toList());
                        List<FlowActivity> actList = activity.stream().filter(item -> ruleStr.contains(item.getActid()) && item.getActtype().equals("endEvent")).collect(Collectors.toList());
                        if (actList.size() > 0) {
                            FlowActivity objactity = actList.get(0);
                            actityid_list.add(objactity.getActid());
                            actityid_list.add(Rule.stream().filter(item -> item.getRuleendactid().equals(objactity.getActid())).findFirst().get().getRuleid());
                        } else {
                            List<FlowActivity> acts = activity.stream().filter(n -> n.getActid().equals(actidList.getTpActid())).collect(Collectors.toList());
                            if (acts.size() > 0) {
                                actityid_list.addAll(flowActivityService.getEndActivity(acts.get(0), activity, Rule, customerId));
                            }
                        }

                    }
                }

                actityid_list.addAll(taskobj.stream().map(n -> n.getTaskcurrentactid()).collect(Collectors.toList()));
                HashSet set = new HashSet(actityid_list);
                // 清空list集合
                actityid_list.clear();
                // 将去重后的元素重新添加到list中
                actityid_list.addAll(set);
                List<String> userid = Task_ProcessinAll.stream().map(n -> n.getTpUser()).collect(Collectors.toList());
                List<String> uid = taskobj.stream().map(n -> n.getTaskuser()).collect(Collectors.toList());
                userid.addAll(uid);
                List<SysUser> users = sysUserService.getUserAllByUids(userid, customerId);
                List<TaskProcessin> actAll = Task_ProcessinAll;

                List<TaskProcessResponse> finalNewData = newData;
                Task_ProcessinAll.forEach(data -> {
                    TaskProcessResponse objtask = new TaskProcessResponse();
                    BeanUtils.copyProperties(data, objtask);
                    objtask.setFlowRecordRule(obj.getFlowRecordRule());
                    if (!ValidateUtil.isEmpty(data.getTpUser()) && users.size() > 0) {
                        List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getTpUser())).collect(Collectors.toList());
                        if (user != null && user.size() > 0) objtask.setTpUser(user.get(0).getuName());
                        objtask.setTpUserType(user.get(0).getuType());
                    }


                    List<FlowActivity> acts = activity.stream().filter(n -> n.getActid().equals(data.getTpActid())).collect(Collectors.toList());

                    if (acts.size() > 0 && (!"7".equals(fileStatusO))) {
                        if ("1".equals(acts.get(0).getActApprovalHiddenType())) {
                            //隐藏
                            String codes = acts.get(0).getActApprovalHiddenCondition();
                            List<FlowActivity> actL = activity.stream().filter(n -> codes.contains(n.getActcode())).collect(Collectors.toList());
                            if (actL.size() > 0) {
                                List<String> actIds = actL.stream().map(n -> n.getActid()).collect(Collectors.toList());
                                List<TaskProcessin> actsd = actAll.stream().filter(n -> codes.contains(n.getTpActid())).collect(Collectors.toList());
                                if (actsd.size() == 0) {
                                    objtask = new TaskProcessResponse();
                                    objtask.setTpActid(data.getTpActid());
                                }
                            } else {
                                objtask = new TaskProcessResponse();
                                objtask.setTpActid(data.getTpActid());
                            }
                            //newTpList.stream().filter(n->codes.contains(n.gett) n.getTpActid().equals())
                        } else if ("2".equals(acts.get(0).getActApprovalHiddenType())) {
                            //加密
                            String codes = acts.get(0).getActApprovalHiddenCondition();
                            List<FlowActivity> actL = activity.stream().filter(n -> codes.contains(n.getActcode())).collect(Collectors.toList());
                            if (actL.size() > 0) {
                                List<String> actIds = actL.stream().map(n -> n.getActid()).collect(Collectors.toList());
                                List<TaskProcessin> actsd = actAll.stream().filter(n -> codes.contains(n.getTpActid())).collect(Collectors.toList());
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

                    if (processSkipShielding != null && processSkipShielding.toString().equals("1")) {
                        if (!data.getTpType().equals("跳过") && !data.getTpType().equals("跳过（结束）")) {
                            finalNewData.add(objtask);
                        }
                    } else {
                        finalNewData.add(objtask);
                    }
                });
                //获取当前任务的所属审批人
                newData.addAll(finalNewData);
                List<Map<String, Object>> newTask = new ArrayList<>();
                taskobj.forEach(item -> {
                    Map<String, Object> tasko = new HashMap<>();
                    taskActIds.add(item.getTaskcurrentactid());
                    tasko.put("taskId", item.getTaskcurrentactid());
                    String userName = "";
                    List<SysUser> userobj = users.stream().filter(n -> n.getuId().equals(item.getTaskuser())).collect(Collectors.toList());
                    if (userobj.size() > 0) {
                        userName = userobj.get(0).getuName();
                    }
                    if (activity != null && activity.size() > 0) {
                        List<FlowActivity> acts = activity.stream().filter(k -> k.getActid().equals(item.getTaskcurrentactid())).collect(Collectors.toList());
                        if (acts != null && acts.size() > 0) {
                            if ("1".equals(acts.get(0).getActApprovalHiddenType())) {
                                userName = "___";
                            } else if ("2".equals(acts.get(0).getActApprovalHiddenType())) {
                                userName = "***";
                            }
                        }
                    }
                    tasko.put("userName", userName);
                    tasko.put("startTime", item.getTaskstarttime());
                    tasko.put("endTime", item.getTaskendtime());
                    tasko.put("taskRead", item.getTaskread());
                    tasko.put("taskReadtime", item.getTaskreadtime());
                    newTask.add(tasko);
                });
                taskdata = newTask;
            }
        }

        List<String> taskAcIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(taskActIds)) {
            taskAcIds.addAll(taskActIds);
            List<FlowActivity> actObjList = activity.stream().filter(p -> taskActIds.contains(p.getActid())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(actObjList)) {
                for (FlowActivity act : actObjList) {
                    List<FlowActivity> actEList = flowActivityService.getAllSubsequentNodes(act, activity, Rule);
                    if (!CollectionUtils.isEmpty(actEList)) {
                        taskAcIds.addAll(actEList.stream().map(FlowActivity::getActid).collect(Collectors.toList()));
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(taskdata)) {
            List<String> actIds = flowActivityService.getNodesAllowedNodeIds(flowactivtynew, tpList, requst.getuId(), "2", fileStatus, taskAcIds);
            if (CollectionUtils.isEmpty(actIds)) {
                taskdata = new ArrayList<>();
            } else {
                taskdata = taskdata.stream().filter(n -> actIds.contains(n.get("taskId"))).collect(Collectors.toList());

            }
        }
        if (!CollectionUtils.isEmpty(actityid_list)) {
            List<String> actIds = flowActivityService.getNodesAllowedNodeIds(flowactivtynew, tpList, requst.getuId(), "1", fileStatus, taskAcIds);
            if (CollectionUtils.isEmpty(actIds)) {
                actityid_list = new ArrayList<>();
            } else {
                actityid_list = actityid_list.stream().filter(n -> actIds.contains(n)).collect(Collectors.toList());
                newData = newData.stream().filter(n -> actIds.contains(n.getTpActid())).collect(Collectors.toList());
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("xmlstr", obj);
        data.put("actity", actityid_list);
        data.put("taskid", taskdata);
        data.put("TaskProcessin", newData);
        data.put("flowActivityList", flowactivtynew);
        data.put("flowRule", newrulelist);
        return new BaseResponse("", data, 1);
    }


    @Override
    public BaseResponse getFlow(GetFlowDto requst, String customerId) {
        String id = null;
        try {
            id = GetEnableFlowID("", requst.getCompanyId(), requst.getProjectId(), requst.getTemplateCode(), customerId);
        } catch (Exception ex) {
            log.info("GG1");
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        if (ValidateUtil.isEmpty(id)) return new BaseResponse("未找到流程");
        Flow flowobj = baseMapper.selectById(id);
        if (flowobj != null) {
            String yearStr = requst.getYear();
            List<FlowActivity> flowactivty = flowActivityService.getFlowActivityByActFlowID(id, customerId);
            FlowActivity startobj = flowactivty.stream().filter(n -> n.getActtype().equals("startEvent")).findFirst().get();
            List<FlowRule> Rule = flowRuleService.getFlowRuleListByRuleFlowID(id, customerId);
            Map<String, Object> newConditions = flowActivityService.setTorsionConditions("", "", requst.getCondition(), customerId, requst.gettCode(), yearStr);
            String keyStr = GenerateUUID.getUUID();
            List<String> allActIds = new ArrayList<>();
            List<FlowActivity> flowactivtynew = flowActivityService.GetFlowActivityList(keyStr, startobj, newConditions, flowactivty, Rule, 0, "0", new ArrayList<>(), customerId, allActIds);
            flowActivityService.setActListALLToNull(keyStr);
            flowactivtynew.add(startobj);
            flowactivtynew = flowactivtynew.stream().sorted(Comparator.comparing(FlowActivity::getActorder)).collect(Collectors.toList());
            List<FlowRule> FlowRule = flowRuleService.getFlowRuleListByRuleFlowID(id, customerId);
            Map<String, Object> data = new HashMap<>();
            data.put("Flowobj", flowobj);
            data.put("Flowactivtys", flowactivtynew.stream().filter(n -> !n.getActtype().contains("Gateway")));
            data.put("FlowRules", FlowRule);
            return new BaseResponse("", data, 1);
        } else {
            return new BaseResponse("未找到流程");
        }
    }

    @Override
    public List<FlowActivity> getFlowActivities(String tCode, String customerId) {
        String id = null;
        try {
            id = GetEnableFlowID("", "ALL", "ALL", tCode, customerId);
        } catch (Exception ex) {
            log.info("GG1");
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        Flow flowobj = baseMapper.selectById(id);
        if (flowobj != null) {
            List<FlowActivity> flowactivty = flowActivityService.getFlowActivityByActFlowID(id, customerId);
//            FlowActivity startobj = flowactivty.stream().filter(n -> n.getActtype().equals("startEvent")).findFirst().get();
//            List<FlowRule> Rule = flowRuleService.getFlowRuleListByRuleFlowID(id, customerId);
//            Map<String, Object> newConditions = flowActivityService.setTorsionConditions("", "", "", customerId, tCode);
//            String keyStr = GenerateUUID.getUUID();
//            List<String> allActIds = new ArrayList<>();
//            List<FlowActivity> flowactivtynew = flowActivityService.GetFlowActivityList(keyStr, startobj, newConditions, flowactivty, Rule, 0, "0", new ArrayList<>(), customerId, allActIds);
//            flowactivtynew.add(startobj);
//            flowactivtynew = flowactivtynew.stream().sorted(Comparator.comparing(FlowActivity::getActorder)).collect(Collectors.toList());
            return flowactivty.stream().filter(n -> !n.getActtype().contains("Gateway")).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public Map<String, Object> approvalRecordAuthority(ApprovalRecordAuthorityDto request, String uId, String customerId) {

        QueryWrapper<File> fileWrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.gettCode()) && request.gettCode().size() > 0) {
            fileWrapper.in("T_Code", request.gettCode());
        }
        if (!ValidateUtil.isEmpty(request.getFileId())) {
            fileWrapper.eq("FileID", request.getFileId());
        }
        if (!ValidateUtil.isEmpty(request.getpId())) {
            fileWrapper.eq("FileProject", request.getpId());
        }
        if (!ValidateUtil.isEmpty(request.getSecId())) {
            fileWrapper.eq("FileSectionID", request.getSecId());
        }
        if (!ValidateUtil.isEmpty(request.getConId())) {
            fileWrapper.eq("FileContract", request.getConId());
        }
        if (!"All".equals(customerId)) {
            fileWrapper.eq("Te_Id", customerId);
        }
        String yearStr = request.getYear();
        List<File> filesList = fileService.getList(fileWrapper, request.gettCode(), yearStr);
        Map<String, Object> data = new HashMap<>();
        if (filesList.size() > 0) {
            List<String> fileIds = filesList.stream().map(n -> n.getFileid()).collect(Collectors.toList());
            QueryWrapper<CarbonCopy> carbonCopyWrapper = new QueryWrapper<>();
            carbonCopyWrapper.in("CC_FileID", fileIds);
            carbonCopyWrapper.eq("CC_User", uId);
            if (!"All".equals(customerId)) {
                carbonCopyWrapper.eq("Te_Id", customerId);
            }
            List<CarbonCopy> carbonCopyList = carbonCopyService.list(carbonCopyWrapper);

            QueryWrapper<SysCoordinationProcess> sysCoordinationProcessWrapper = new QueryWrapper<>();
            sysCoordinationProcessWrapper.in("CP_FileID", fileIds);
            sysCoordinationProcessWrapper.eq("CP_User", uId);
            if (!"All".equals(customerId)) {
                sysCoordinationProcessWrapper.eq("Te_Id", customerId);
            }
            List<SysCoordinationProcess> sysCoordinationProcessList = sysCoordinationProcessService.list(sysCoordinationProcessWrapper);

            QueryWrapper<TaskProcessin> taskProcessinWrapper = new QueryWrapper<>();
            taskProcessinWrapper.in("tp_entityid", fileIds);
            taskProcessinWrapper.in("tp_user", uId);
            if (!"All".equals(customerId)) {
                taskProcessinWrapper.eq("Te_Id", customerId);
            }
            List<TaskProcessin> taskProcessin = taskProcessinService.getList(taskProcessinWrapper, request.gettCode(), yearStr);

            if (!ValidateUtil.isEmpty(request.gettCode()) && request.gettCode().size() > 0) {
                List<String> tCodeList = request.gettCode();
                tCodeList.forEach(n -> {
                    Boolean boolStr = false;
                    List<File> newFileList = filesList.stream().filter(k -> k.gettCode().equals(n)).collect(Collectors.toList());
                    if (newFileList.size() > 0) {
                        List<String> newFileIds = newFileList.stream().map(l -> l.getFileid()).collect(Collectors.toList());
                        List<CarbonCopy> carbonCopyListNew = carbonCopyList.stream().filter(o -> newFileIds.contains(o.getCcFileid())).collect(Collectors.toList());
                        if (carbonCopyList.size() > 0) {
                            boolStr = true;
                        }
                        List<SysCoordinationProcess> sysCoordinationProcessListNew = sysCoordinationProcessList.stream().filter(o -> newFileIds.contains(o.getCpFileid())).collect(Collectors.toList());
                        if (sysCoordinationProcessList.size() > 0) {
                            boolStr = true;
                        }
                        List<TaskProcessin> taskProcessinNew = taskProcessin.stream().filter(o -> newFileIds.contains(o.getTpFileid())).collect(Collectors.toList());
                        if (taskProcessin.size() > 0) {
                            boolStr = true;
                        }
                    }
                    data.put(n, boolStr);
                });

            } else {

            }
        }
        return data;
    }

    @Override
    public Map<String, Object> AllowWithdrawJudgment(String tpId, String tCode, String customerId, String yearStr) {
        Map<String, Object> data = new HashMap<>();
        Boolean type = true;
        String massage = "";

        if (!ValidateUtil.isEmpty(tpId)) {
            //获取撤回操作的审批记录
            TaskProcessin Task_Processinentitys = taskProcessinService.getTaskProcessById(tpId, tCode, yearStr);
            if (Task_Processinentitys != null) {
                Date dodatetime = Task_Processinentitys.getTpDotime();
                //获取流程审批记录的当前节点
                FlowActivity FlowActivitynow = flowActivityService.getById(Task_Processinentitys.getTpActid());
                if (FlowActivitynow != null) {
                    if (!FlowActivitynow.getActtype().equals("userTask")) {
                        type = false;
                        massage = "当前记录为网关记录不能撤回！";
                    }
                    //获取当前节点的所属流程
                    Flow flow = baseMapper.selectById(FlowActivitynow.getActflowid());
                    if (flow != null) {
                        //获取当前流程下的所有流程节点
                        List<FlowActivity> FlowActivityAll = flowActivityService.getFlowActivityByActFlowID(flow.getFlowid(), customerId);
                        //获取当前流程下的所有连接线信息
                        List<FlowRule> FlowRuleAll = flowRuleService.getFlowRuleListByRuleFlowID(flow.getFlowid(), customerId);
                        //获取当前流程下的所有审批记录信息
                        List<TaskProcessin> Task_ProcessinAll = taskProcessinService.getListByTpFlowIdAndTpEntityId(flow.getFlowid(), Task_Processinentitys.getTpEntityid(), customerId, yearStr);

                        //获取当前流程下的所有待办信息
                        List<Task> TaskEntityAll = taskService.getTaskListByTaskIdAndTaskEntityId(flow.getFlowid(), Task_Processinentitys.getTpEntityid(), customerId);
                        //夺取当前节点的后续可选步骤
                        List<String> actity_new = GetNextTask(FlowActivitynow, FlowActivityAll, FlowRuleAll);
                        actity_new.add(Task_Processinentitys.getTpActid());

                        //获取可能退回的节点信息
                        List<FlowActivity> actList = Getspreviousnode(FlowActivityAll, FlowRuleAll, FlowActivitynow);
                        if (actList.size() > 0) {
                            actity_new.addAll(actList.stream().map(n -> n.getActid()).collect(Collectors.toList()));
                        }
                        List<String> useractity = actity_new.stream().filter(n -> n.startsWith("Activity")).collect(Collectors.toList());

                        TaskEntityAll = TaskEntityAll.stream().filter(item -> actity_new.contains(item.getTaskpreactid()) && useractity.contains(item.getTaskcurrentactid())).collect(Collectors.toList());
                        //判断当前退回是否还存在发出的待办信息
                        if (TaskEntityAll.size() == 0) {
                            type = false;
                            massage = "当前流程无法撤回，未找到发出的待办信息！";
                        }
                        //获取流程图过滤信息
                        List<String> toDoWithdrawalVerification = new ArrayList<>();
                        toDoWithdrawalVerification.add("toDoWithdrawalVerification");
                        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(toDoWithdrawalVerification, customerId);
                        Object toDoWithdrawal = smsTemplateList.get("toDoWithdrawalVerification");
                        if (toDoWithdrawal != null && toDoWithdrawal.equals("1")) {
                        } else {
                            List<Task> readlist = TaskEntityAll.stream().filter(n -> n.getTaskread().equals("已读")).collect(Collectors.toList());
                            if (readlist.size() > 0) {
                                //不能撤回
                                type = false;
                                massage = "经办人已查看，当前流程不能撤回！";
                            }
                        }
                        List<TaskProcessin> approvelist = Task_ProcessinAll.stream().filter(item -> useractity.contains(item.getTpActid()) && !item.getTpId().equals(Task_Processinentitys.getTpActid()) && item.getTpDotime().getTime() > dodatetime.getTime() && (!item.getActname().equals("【更换流程操作人】"))).collect(Collectors.toList());
                        if (approvelist.size() > 0) {
                            //不能撤回
                            type = false;
                            massage = "当前流程不能撤回！";
                        }

                    } else {
                        type = false;
                        massage = "获取流程信息失败！";
                    }
                } else {
                    type = false;
                    massage = "获取流程审批记录的所属活动节点失败！";
                }
            } else {
                type = false;
                massage = "获取审批记录失败！";
            }
        } else {
            type = false;
            massage = "参数丢失！";
        }

        data.put("type", type);
        data.put("massage", massage);
        return data;
    }


    @Override
    public List<Flow> getSomeFieldsFlow(String tCode, String customerId) {
        return baseMapper.getSomeFieldsFlow(tCode, customerId);
    }


    public String taskNameReplacement(String fileId, String tCode, String taskName, Map<String, Object> map, String customerId, String yearStr) {
        String idCode = "";
        String tTable = "";
        if (ValidateUtil.isEmpty(taskName) || ValidateUtil.isEmpty(idCode) || ValidateUtil.isEmpty(tTable)) {
            if (ValidateUtil.isEmpty(tCode)) {
                File fileObj = fileService.getFileById(fileId, tCode, yearStr);
                if (fileObj != null) {
                    tCode = fileObj.gettCode();

                }

            }

            FileTemplate FileTemplateobj = fileTemplateService.getTemplatebyTCode(tCode, customerId);
            if (FileTemplateobj != null) {
                idCode = FileTemplateobj.gettPrimaryKey();
                tTable = FileTemplateobj.gettTable();
                if (ValidateUtil.isNotEmpty(FileTemplateobj.gettTaskName())) {
                    taskName = FileTemplateobj.gettTaskName();
                }
            }
        }
        if (ValidateUtil.isNotEmpty(taskName)) {
            Pattern dynamic = Pattern.compile(".*\\$\\{([x00-xF]+)}.*");
            Pattern dynamicLimitCount = Pattern.compile("\\$\\{([x00-xF]+)}");
            Set<String> paramSet = new LinkedHashSet<>();
            //先判断给定文本是否存在${key}形式的文本
            if (dynamic.matcher(taskName).matches()) {
                Matcher matcher = dynamicLimitCount.matcher(taskName);
                while (matcher.find()) {
                    //获取 ${key}
                    String group = matcher.group();
                    int i = group.lastIndexOf("}");
                    //截取其中的key
                    String substring = group.substring(2, i);
                    Object valus = map.get(substring);
                    if (!(valus != null && ValidateUtil.isNotEmpty(valus.toString()))) {
                        paramSet.add(substring);
                    }
                }
            }
            try {
                if (paramSet.size() > 0) {
                    //获取表单字段信息

                    Map<String, Object> mapObj = baseMapper.getData(idCode, tTable, fileId);
                    if (mapObj != null) {
                        paramSet.forEach(n -> {
                            map.put(n, getKey(mapObj, n));
                        });
                    }

                }
            } catch (Exception ex) {
                log.error("流程服务：", ex);
            }
            StringSubstitutor sub = new StringSubstitutor(map);
            String content = sub.replace(taskName);
            return content;
        } else {
            return taskName;
        }

    }

    public Object getKey(Map<String, Object> obj, String key) {
        key = humpToLine(key);
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

    public static String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase(Locale.ENGLISH));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String customerId, String token) {

        File fileObj = fileService.getFileById(request.getFileId(), request.gettCode(), request.getYear());
        if (fileObj != null) {
            String pid = fileObj.getFileproject();
            String tCode = fileObj.gettCode();
            String flowid = fileObj.getFlowId();
            String requestSource = request.getRequestSource();
            String yearStr = request.getYear();
            if (ValidateUtil.isEmpty(yearStr)) {
                if (fileObj.getFilecreatedate() != null) {
                    yearStr = DateUtil.DateToString(fileObj.getFilecreatedate(), "yyyy");
                }
            }
            //获取开始节点流程任务活动节点
            if (ValidateUtil.isEmpty(flowid)) {
                fileObj.setFileflowstatus(0);
                fileService.updatefile(fileObj.gettCode(), request.getFileId(), "start", flowid, customerId, token, yearStr);
                return new BaseResponse(true, "操作成功");
            } else {

                List<FlowActivity> FlowActivitys = new ArrayList<>();
                List<FlowRule> FlowRules = new ArrayList<>();
                FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
                //获取当前流程下所有的连接线
                FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
                FlowActivity FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                if (FlowActivityobj != null) {
                    Date date = new Date();
                    TaskProcessin his = new TaskProcessin();
                    his.setTpActid(FlowActivityobj.getActid());
                    his.setActname(FlowActivityobj.getActname());
                    his.setTpDotime(new Date());
                    his.setTpFlowid(flowid);
                    his.setTpId(GenerateUUID.getUUID());
                    his.setTpExplain(request.getOpinion());
                    his.setTpResult("退回");
                    //his.tp_ruleid = null;//起始活动的历史记录规则为空
                    his.setTpEntityid(request.getFileId());
                    his.setTpProject(pid);
                    his.setTpName(fileObj.getFiletitle());
                    his.setTpUser(request.getuId());
                    his.setTpCountersigntitle(FlowActivityobj.getActcountersigntitle());
                    his.setTpType("退回");
                    his.setTpStarttime(new Date());
                    his.setTpEndtime(new Date());
                    his.setTpActtype(FlowActivityobj.getActtype());
                    his.setTpSend(fileObj.getFilecreateuser());
                    his.setTpClient(requestSource);
                    if (!"All".equals(customerId)) {
                        his.setTeId(customerId);
                    }
                    taskProcessinService.insertTaskProcessin(his, tCode, yearStr);
                    sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, FlowActivityobj.getActtype(), FlowActivityobj.getActid(), FlowActivityobj.getActname(), fileObj.getFileproject(), customerId, request.gettCode());
                    taskService.deleteTaskByIdOrTaskEntityID("", request.getFileId(), customerId);
                    Task task = new Task();
                    task.setTaskid(GenerateUUID.getUUID());
                    task.setTaskread("未读");
                    task.setTaskname(his.getTpName());  // 任务名称
                    task.setTaskclient("");
                    task.setTaskflow(flowid);
                    task.setTaskpreactid("");
                    task.setTaskcurrentactid(FlowActivityobj.getActid());
                    task.setTaskproject(fileObj.getFileproject());
                    task.setTaskrule("");
                    task.setTaskentityid(his.getTpEntityid());
                    task.setTasksendtime(new Date());
                    task.setTasksenduser(his.getTpUser());
                    task.setTaskuser(fileObj.getFilecreateuser());
                    task.setTaskReturnType("start");
                    task.setTaskstarttime(date);
                    task.setTaskendtime(date);
                    if (!"All".equals(customerId)) {
                        task.setTeId(customerId);
                    }

                    task.setSecId(fileObj.getFilesectionid());
                    task.setProjectId(fileObj.getFileproject());
                    task.setContractId(fileObj.getFilecontract());
                    task.setFlowId(flowid);
                    task.setTCode(fileObj.gettCode());
                    taskService.save(task);
                    fileService.updatefile(fileObj.gettCode(), request.getFileId(), "start", flowid, customerId, token, yearStr);
                    return new BaseResponse(true, "操作成功");
                } else {
                    return new BaseResponse("未找到流程开始节点");
                }
            }

        } else {
            return new BaseResponse("未找到表单实例");
        }


    }

    @Override
    public BaseResponse getReturnActity(String fileId, String tCode, String taskId, String customerId, String year) {
        File fileObj = fileService.getFileById(fileId, tCode, year);
        if (fileObj != null) {
            List<FlowActivity> actList = flowActivityService.getFlowActivityByActFlowID(fileObj.getFlowId(), customerId);
            List<FlowRule> ruleList = flowRuleService.getFlowRuleListByRuleFlowID(fileObj.getFlowId(), customerId);
            Task taskObj = null;
            if ("结束节点".equals(taskId)) {
                taskObj = createEndNodes(fileId, "", fileObj.getFiletitle(), fileObj.getFlowId(), customerId);
            } else {
                taskObj = taskService.getById(taskId);
            }
            if (taskObj != null) {
                String actId = taskObj.getTaskcurrentactid();
                List<FlowActivity> flowAct = actList.stream().filter(n -> n.getActid().equals(actId)).collect(Collectors.toList());
                if (flowAct.size() > 0) {
                    List<FlowActivity> newActList = flowActivityService.RecursiveNodeCanreturned(actList, ruleList, flowAct.get(0), customerId);
                    if (newActList.size() > 0) {
                        List<String> actNewList = newActList.stream().map(n -> n.getActid()).collect(Collectors.toList());
                        QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
                        wrapper.eq("tp_entityid", fileId);
                        wrapper.eq("tp_acttype", "userTask");
                        wrapper.in("tp_actid", actNewList);
                        if (!"All".equals(customerId)) {
                            wrapper.eq("Te_Id", customerId);
                        }
                        List<TaskProcessin> TaskProcessinList = taskProcessinService.getList(wrapper, tCode, year);
                        List<String> userid = TaskProcessinList.stream().map(n -> n.getTpUser()).collect(Collectors.toList());
                        List<SysUser> users = sysUserService.getUserAllByUids(userid, customerId);
                        List<TaskProcessResponse> newData = new ArrayList<>();
                        //获取开始节点
                        Map<String, Integer> map = new HashMap<>();
                        List<FlowActivity> startAct = actList.stream().filter(n -> n.getActtype().equals("startEvent")).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(startAct)) {
                            map = flowActivityService.getFlowActNum(actList, ruleList, startAct.get(0));
                        }

                        for (int i = 0; i < TaskProcessinList.size(); i++) {
                            TaskProcessin data = TaskProcessinList.get(i);
                            TaskProcessResponse objtask = new TaskProcessResponse();
                            BeanUtils.copyProperties(data, objtask);
                            if (!ValidateUtil.isEmpty(data.getTpUser())) {
                                List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getTpUser())).collect(Collectors.toList());
                                if (user != null && user.size() > 0) {
                                    objtask.setTpUserName(user.get(0).getuName());
                                    objtask.setTpUserType(user.get(0).getuType());

                                }
                            }
                            if (map.get(data.getTpActid()) != null) {
                                objtask.setActNum((Integer) map.get(data.getTpActid()));
                            } else {
                                objtask.setActNum((Integer) 9999);
                            }
                            List<TaskProcessResponse> setList = newData.stream().filter(n -> n.getTpActid().equals(data.getTpActid())).collect(Collectors.toList());
                            if (setList.size() == 0) {
                                newData.add(objtask);
                            }

                        }
                        //先根据actNUm排序，在根据tpDotime排序
                        try {
                            newData = newData.stream().sorted(Comparator.comparing(TaskProcessResponse::getActNum).thenComparing(TaskProcessResponse::getTpDotime)).collect(Collectors.toList());
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("流程服务：获取退回任意节点列表，排序失败");
                        }
                        //过滤掉不允许退回的节点信息
                        try {
                            List<String> actIds = flowActivityService.getCanReturnNodeId(actList, actId);
                            newData = newData.stream().filter(n -> actIds.contains(n.getTpActid())).collect(Collectors.toList());
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("流程服务：获取退回任意节点列表，排序失败");
                        }

                        return new BaseResponse("", newData, newData.size());
                    } else {
                        return new BaseResponse("流程节点获取失败！");
                    }
                } else {
                    return new BaseResponse("当前操作节点获取失败");
                }

            } else {
                return new BaseResponse("当前任务信息获取失败");
            }
        } else {
            return new BaseResponse("流程实例获取失败");
        }
    }


    @Override
    protected Class<Flow> currentModelClass() {
        return super.currentModelClass();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse skipactivity(SubmitFlowRequstDto request, String customerId, String token) {
        int status_str = 0;
        List<String> massage = new ArrayList<>();
        String taskid = request.getTaskid();//任务id
        String templatecode = request.getTemplatecode();//模板编码
        String Reverseconditions = request.getCondition();//扭转条件
        //String GetFlagstr = "";//流程提交方式（是的原路返回）
        String p_id = request.getProjectid();//项目id
        String c_id = request.getCompanyid();//单位id
        String solutionid = request.getSolutionid();//文档所属编码
        String Activityid = request.getActivityid();//当前审批节点id
        String explain = request.getExplain();//审批意见
        String taskname = request.getTaskname();//任务名称
        String user_id = request.getUser_id();//当前用户id
        String file_id = request.getFile_id();//文档id
        String operationtype = request.getOperationtype();//操作类型
        List<userselectionDto> nextuser = request.getNextuser();//用户选择的用户信息
        List<String> cc_Users = request.getCc_user();//用户选择的抄送用户信息
        String requestSource = request.getRequestSource();
        String yearStr = request.getYear();
        File filePojo = fileService.getFileById(file_id, request.gettCode(), yearStr);
        if (ValidateUtil.isEmpty(p_id) && filePojo != null) {
            p_id = filePojo.getFileproject();
            if (filePojo.getFilecreatedate() != null) {
                yearStr = DateUtil.DateToString(filePojo.getFilecreatedate(), "yyyy");
            }
        }
        Map<String, Object> newConditions = flowActivityService.setTorsionConditions(file_id, user_id, Reverseconditions, customerId, request.gettCode(), yearStr);
        taskname = taskNameReplacement(file_id, templatecode, taskname, newConditions, customerId, yearStr);
        //验证用户选择的下一步节点
        if ((nextuser != null && nextuser.size() > 0) || operationtype.equals("end") || operationtype.equals("return")) {
            int i = 0;
            for (userselectionDto item : nextuser) {
                if (ValidateUtil.isEmpty(item.getActityid()) || ValidateUtil.isEmpty(item.getUserid())) {
                    i = 1;
                    break;
                }
            }
            if (i == 1) return new BaseResponse("下一步审批人信息丢失");
        } else return new BaseResponse("请选择下一步审批人");

        FlowActivity FlowActivityobj = null;
        Task Taskobj = null;//任务信息；
        String flowid = null;
        List<FlowActivity> FlowActivitys = new ArrayList<>();
        List<FlowRule> FlowRules = new ArrayList<>();
        if (ValidateUtil.isEmpty(request.getFlowid())) {
            try {
                flowid = GetEnableFlowID(solutionid, c_id, p_id, templatecode, customerId);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        } else {
            flowid = request.getFlowid();
        }

        Flow flow = baseMapper.selectById(flowid);
        if (!ValidateUtil.isEmpty(Activityid)) {
            FlowActivityobj = flowActivityService.getById(Activityid);
        } else if (ValidateUtil.isEmpty(taskid) && !ValidateUtil.isEmpty(templatecode)) {
            //没有任务id，当前操作为发起新的流程
            //获取开始节点流程任务活动节点
            FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
            //获取当前流程下所有的连接线
            FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
            FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
        } else {
            Taskobj = taskService.getById(taskid);
            if (Taskobj != null) {
                if (ValidateUtil.isEmpty(file_id)) {
                    file_id = Taskobj.getTaskentityid();
                }
                String actid = Taskobj.getTaskcurrentactid();
                String GetFlagstr = Taskobj.getTaskgetflagstr();
                flow = baseMapper.selectById(flowid);
                FlowActivityobj = flowActivityService.getById(actid);//获取当前任务id
                Float Actityhour = null;
                if (FlowActivityobj.getActhour() != null) {
                    Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
                }
                if (filePojo == null) {
                    filePojo = fileService.getFileById(Taskobj.getTaskentityid(), request.gettCode(), yearStr);
                    Taskobj.setFlowId(filePojo.getFlowId());
                    Taskobj.setSecId(filePojo.getFilesectionid());
                    Taskobj.setProjectId(filePojo.getFileproject());
                    Taskobj.setContractId(filePojo.getFilecontract());
                    Taskobj.setTCode(filePojo.gettCode());
                }
                if (GetFlagstr != null && GetFlagstr.trim().equals("1")) {
                    //原路返回

                    //判断当流程的所属流程图是否为主版本
                    String flowidold = Taskobj.getTaskflow();
                    Flow flowobj = baseMapper.selectById(flowidold);
                    //获取流程的基础配置信息，判断退回发起人后，流程是否是按照新的流程走还是老的流程走
                    List<String> codeList = new ArrayList<>();
                    codeList.add("processSubmission");
                    Map<String, Object> configelist = sysConfigEntityService.getConfigEntity(codeList, customerId);
                    Object processSubmission = configelist.get("processSubmission");
                    if ((flowobj != null && flowobj.getFlowenable() != null && flowobj.getFlowenable().equals("1")) || (processSubmission != null && processSubmission.equals("returnOld"))) {

                        //{生成任务信息 }----------------------------------------------------------------------------------------
                        TaskProcessin his = taskProcessinService.GetHis(Taskobj, "同意", explain, user_id, "提交", flow, Taskobj.getTasksenduser(), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体
                        taskProcessinService.insertTaskProcessin(his, request.gettCode(), yearStr);
                        sysTaskProcessinTimeService.TaskProcessinTimeCreate(his, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                        String Nextuser = FindsubmitterMan(Taskobj.getTaskentityid(), Taskobj.getTaskflow(), Taskobj.getTaskcurrentactid(), Taskobj.getTaskpreactid(), user_id, customerId, request.gettCode(), yearStr);

                        if (ValidateUtil.isEmpty(Nextuser)) {
//                            if (!Taskobj.getTasksenduser().equals(user_id)) {
                            Nextuser = Taskobj.getTasksenduser();
//                            } else {
//                                return new BaseResponse("流程提交失败，获取下一步办理人失败，无法原路返回！");
//                            }
                        }
                        Task task_sqlstr = GetNewTask(GenerateUUID.getUUID(), Taskobj.getTaskpreactid(), actid, his, Taskobj, taskname, Nextuser, flow, null, new Date(), new Date(), "", null, null, null, null, null, customerId);
                        //原路返回的流程，重新生成原来冻结的流程信息
                        List<SysTaskBackups> taskBackupsList = sysTaskBackupsService.getListByTaskId(taskid, customerId);
                        if (taskBackupsList.size() > 0) {
                            for (SysTaskBackups tobj : taskBackupsList) {
                                Task taskEnt = new Task();
                                BeanUtils.copyProperties(tobj, taskEnt);
                                taskEnt.setTasksendtime(new Date());
                                taskEnt.setTaskstarttime(new Date());
                                try {
                                    BigDecimal bg = DateUtil.getDifferHour(tobj.getTaskstarttime().toString(), tobj.getTaskendtime().toString());
                                    taskEnt.setTaskendtime(DateUtil.DateChangeHour(taskEnt.getTaskstarttime(), Double.parseDouble(bg.toString())));
                                } catch (Exception ex) {
                                    log.error("流程服务：", ex);
                                }
                                taskEnt.setSecId(filePojo.getFilesectionid());
                                taskEnt.setProjectId(filePojo.getFileproject());
                                taskEnt.setContractId(filePojo.getFilecontract());
                                taskService.save(taskEnt);
                            }
                        }
                        task_sqlstr.setSecId(filePojo.getFilesectionid());
                        task_sqlstr.setProjectId(filePojo.getFileproject());
                        task_sqlstr.setContractId(filePojo.getFilecontract());
                        taskService.save(task_sqlstr);
                        //生成发送记录信息（由那个节点发送到那个节点）
                        taskProcessUserService.GetNewTaskProcessUse(his.getTpId(), task_sqlstr.getTaskuser(), task_sqlstr.getTaskcurrentactid(), customerId, request.gettCode(), yearStr);
                        taskService.removeById(Taskobj.getTaskid());//删除原有的待办信息

                        //生成抄送信息
                        carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, Taskobj.getTaskentityid(), Taskobj.getTaskname(), his.getTpId(), cc_Users, "1", customerId, token, p_id, request.gettCode(), yearStr);
                        fileService.updatefile(templatecode, file_id, "next", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                        //判断当前审批节点是否需要发送短信
                        List<String> nextuser_o = new ArrayList<>();
                        nextuser_o.add(Taskobj.getTasksenduser());
                        //TODO:生成短信息和邮件等通知信息新增sql语句
                        //sql_all_str.AddRange(Get_shortmessage(FlowActivityobj, Taskobj, nextuser_o, explain, user_id));
                        List<String> usetlist = new ArrayList<>();
                        usetlist.add(Nextuser);

                        sysShortMessageService.generateSMS("", usetlist, file_id, Taskobj.getTaskcurrentactid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                        sysMessageService.generateMassage("", usetlist, file_id, Taskobj.getTaskcurrentactid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                        //原路返回流程提交结束
                        return new BaseResponse(true, "流程提交成功！", his.getTpId());
                    } else {
                        //获取当前任务活动id
                        FlowActivityobj = flowActivityService.getById(actid);
                        //获取开始节点流程任务活动节点
                        FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
                        //获取当前流程下所有的连接线
                        FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
                        if (!FlowActivityobj.getActflowid().equals(flowid)) {
                            //获取到的节点所属流程与要走流程不一致时
                            FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                        }
                    }
                } else {

                    //flowid = Taskobj.getTaskflow();
                    //获取当前任务活动id
                    FlowActivityobj = flowActivityService.getById(actid);
                    //获取开始节点流程任务活动节点
                    FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
                    //获取当前流程下所有的连接线
                    FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
                    if (!FlowActivityobj.getActflowid().equals(flowid)) {
                        //获取到的节点所属流程与要走流程不一致时
                        FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                    }
                }
            }
        }
        //获取当前文档下所有的审批记录信息
        List<TaskProcessin> Task_Processinlist = taskProcessinService.getListByTpFlowIdAndTpEntityId(flowid, file_id, customerId, yearStr);
        List<Task> new_task = new ArrayList<>();
        String Taskproseeid = "";
        int smstypen = 0;
        //设置流程条件判断的任务数
        FlowSubmitRules.all_count = 0;
        QueryWrapper<Task> taskcoyunt = new QueryWrapper<>();
        taskcoyunt.eq("TaskEntityID", file_id);
        taskcoyunt.ne("TaskID", taskid);
        if (!"All".equals(customerId)) {
            taskcoyunt.eq("Te_Id", customerId);
        }
        List<Task> remainingTasks = taskService.list(taskcoyunt);
        FlowSubmitRules.current_count = remainingTasks.size();
        if (FlowActivityobj != null) {
            //正常流程提交（包含流程开始和流程提交），程序实现开始---------------------------
            if (Taskobj != null) {
                //提交流程
                //判断当前任务的沟通是否已经结束
                if (Taskobj.getTaskcoordination() != null && Taskobj.getTaskcoordination().equals("YES"))
                    return new BaseResponse("流程提交失败，当前任务节点存在其它沟通任务，请等待沟通完成后，再次提交流程！");
                else {
                    Task currentAct = Taskobj;

                    //当前活动为子流程的结束活动
                    if (FlowActivityobj.getActtype().equals("COMPLETION") && !ValidateUtil.isEmpty(currentAct.getTaskparentflowid())) {
                        //TODO:结束子流程
                        return new BaseResponse(true, "", "");
                    } else {
                        //当前活动为子流程启动前的活动
                        if (FlowActivityobj.getActtype().equals("SUBPROCESS")) {
                            //TODO:启动子流程
                            return new BaseResponse(true, "", "");
                        }
                    }

                    if (ValidateUtil.isEmpty(operationtype)) return new BaseResponse("流程提交错误，参数丢失");
                    else if (operationtype.equals("next")) {
                        List<TaskProcessin> Task_Processinnewlist = new ArrayList<>();
                        Float Actityhour = null;
                        if (FlowActivityobj.getActhour() != null) {
                            Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
                        }
                        if (!Taskobj.getTaskcurrentactid().equals(FlowActivityobj.getActid())) {
                            Taskobj.setTaskcurrentactid(FlowActivityobj.getActid());
                        }

                        TaskProcessin his_new = taskProcessinService.GetHis(Taskobj, "跳过", explain, user_id, "跳过", flow, String.join(",", nextuser.stream().map(userselectionDto::getUserid).collect(Collectors.toList())), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体y()));
                        Task_Processinlist.add(his_new);
                        Map<String, String> data = new HashMap<>();
                        data.put("Task_Processinnewlist", JacksonUtils.toJSONString(Task_Processinnewlist));
                        data.put("massage", JacksonUtils.toJSONString(massage));
                        data.put("new_task", JacksonUtils.toJSONString(new_task));
                        data.put("status_str", status_str + "");
                        data.put("addsSecond", "1");
                        List<String> needDeleteTaskId = new ArrayList<>();
                        data.put("needDeleteTaskId", JacksonUtils.toJSONString(needDeleteTaskId));
                        data = flowActivityService.GetSYSFlowActivityEntitysubmit(FlowActivityobj, newConditions, FlowActivitys, FlowRules, Task_Processinlist, nextuser, his_new, flow, Taskobj, explain, data, taskname, remainingTasks, customerId, requestSource);
                        new_task = JacksonUtils.parseObject(data.get("new_task"), new TypeReference<List<Task>>() {
                        });
                        massage = JacksonUtils.parseObject(data.get("massage"), List.class);
                        Task_Processinnewlist = JacksonUtils.parseObject(data.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
                        });
                        //判断当前节点是否时并发审批节点
                        if (ValidateUtil.isNotEmpty(FlowActivityobj.getActConcurrencyEnd())) {
                            //
                            if (FlowActivityobj.getActConcurrencyEnd().equals("0")) {
                                //任何一人审核通过
                                taskService.deleteTaskByEntityIdAndCurrentActId(file_id, FlowActivityobj.getActid(), Taskobj.getTaskid(), customerId);
                            } else {
                                //全部审核通过
                                List<Task> actTaskList = taskService.getTaskByEntityIdAndCurrentActId(file_id, FlowActivityobj.getActid(), customerId);
                                actTaskList = actTaskList.stream().filter(n -> !n.getTaskid().equals(taskid)).collect(Collectors.toList());
                                if (actTaskList.size() > 0) {
                                    //当前节点存在未审核完成的待办信息，不能生成新的待办
                                    new_task = new ArrayList<>();
                                }
                            }
                        }
                        String status_st = data.get("status_str");
                        status_str = Integer.parseInt(status_st);
                        if (status_str == 1) {
                            Task_Processinnewlist.add(his_new);
                            if (new_task.size() > 0) {
                                List<Task> newTask = new ArrayList<>();
                                for (Task n : new_task) {
                                    n.setTasksenduser(Taskobj.getTasksenduser());
                                    n.setTaskpreactid(Taskobj.getTaskpreactid());
                                    newTask.add(n);
                                }
                                taskService.saveBatch(newTask);
                                smstypen = 1;
                            }
                            for (userselectionDto item : nextuser)
                                taskProcessUserService.GetNewTaskProcessUse(his_new.getTpId(), item.getUserid(), item.getActityid(), customerId, request.gettCode(), yearStr);
                            if (Task_Processinnewlist.size() > 0) {
                                taskProcessinService.saveTaskProcessBatch(Task_Processinnewlist, request.gettCode(), yearStr);
                                for (TaskProcessin obj : Task_Processinnewlist) {
                                    sysTaskProcessinTimeService.TaskProcessinTimeCreate(obj, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                                }

                            }
                            //生成删除当前待办的sql
                            taskService.removeById(Taskobj.getTaskid());
                            //生成抄送信息
                            carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, Taskobj.getTaskentityid(), Taskobj.getTaskname(), his_new.getTpId(), cc_Users, "1", customerId, token, p_id, request.gettCode(), yearStr);
                            fileService.updatefile(templatecode, file_id, "next", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                            Taskproseeid = his_new.getTpId();
                        }
                    } else if (operationtype.equals("end")) {
                        status_str = 1;
                        Float Actityhour = null;
                        if (FlowActivityobj.getActhour() != null) {
                            Actityhour = Float.parseFloat(FlowActivityobj.getActhour() + "");
                        }
                        TaskProcessin his_new = taskProcessinService.GetHis(Taskobj, "", explain, user_id, "跳过（结束）", flow, String.join(",", nextuser.stream().map(userselectionDto::getUserid).collect(Collectors.toList())), FlowActivityobj.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体y()));
                        taskProcessinService.insertTaskProcessin(his_new, request.gettCode(), yearStr);
                        sysTaskProcessinTimeService.TaskProcessinTimeCreate(his_new, FlowActivityobj.getActtype(), "", "", filePojo.getFileproject(), customerId, request.gettCode());
                        //生成抄送信息
                        smstypen = 1;
                        carbonCopyService.GetCarbonCopyList(FlowActivityobj, user_id, Taskobj.getTaskentityid(), Taskobj.getTaskname(), his_new.getTpId(), cc_Users, "1", customerId, token, p_id, request.gettCode(), yearStr);
                        //判断当前节点是否时并发审批节点
                        if (ValidateUtil.isNotEmpty(FlowActivityobj.getActConcurrencyEnd())) {
                            //
                            if (FlowActivityobj.getActConcurrencyEnd().equals("0")) {
                                //任何一人审核通过
                                fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                                taskService.deleteTaskByIdOrTaskEntityID(Taskobj.getTaskid(), Taskobj.getTaskentityid(), customerId);
                            } else {
                                //全部审核通过
                                List<Task> actTaskList = taskService.getTaskByEntityIdAndCurrentActId(file_id, FlowActivityobj.getActid(), customerId);
                                actTaskList = actTaskList.stream().filter(n -> !n.getTaskid().equals(taskid)).collect(Collectors.toList());
                                if (actTaskList.size() > 0) {
                                    //当前节点存在未审核完成的待办信息，流程不能结束
                                    taskService.removeById(Taskobj.getTaskid());
                                } else {
                                    fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                                    taskService.deleteTaskByIdOrTaskEntityID(Taskobj.getTaskid(), Taskobj.getTaskentityid(), customerId);
                                }
                            }
                        } else {
                            fileService.updatefile(templatecode, file_id, "end", flowid, customerId, token, yearStr);//更新文档信息的审批状态
                            taskService.removeById(Taskobj.getTaskid());
                        }
                        Taskproseeid = his_new.getTpId();
                    }
                }
            } else {
                return new BaseResponse("当前操作节点获取失败！");
            }
            if (status_str == 1) {
                //抄送
                if (smstypen > 0) {
                    //TODO生成短信息和邮件等通知信息新增sql语句
                    List<String> userlist = new ArrayList<>();
                    nextuser.forEach(n -> {
                        if ((!ValidateUtil.isEmpty(n.getSmsStatus()) && n.getSmsStatus().equals("1")) || (!ValidateUtil.isEmpty(n.getSmstype()) && n.getSmstype().equals("1"))) {
                            if (n.getUserid().contains(",")) {
                                String[] userList = n.getUserid().split(",");
                                for (int i = 0; i < userList.length; i++) {
                                    userlist.add(userList[i]);
                                }
                            } else {
                                userlist.add(n.getUserid());
                            }
                        }
                    });
                    sysShortMessageService.generateSMS("", userlist, file_id, FlowActivityobj.getActid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                    sysMessageService.generateMassage("", userlist, file_id, FlowActivityobj.getActid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                }
                //流程成功后
                //successfulevent(new_task, "1");
                taskService.sendToDoTask(Taskobj, Taskproseeid, "1", "1", token, customerId, request.gettCode(), yearStr);
                if (operationtype.equals("end")) {
                    //流程最后一步，获取结束节点的相关配置信息，并进行抄送和短信的发送
                    List<FlowActivity> FlowActivityList = flowActivityService.GetSYSFlowActivityEntitybind(FlowActivityobj, newConditions, FlowActivitys, FlowRules, 0, customerId);
                    if (FlowActivityList.size() > 0) {
                        List<FlowActivity> activityList = FlowActivityList.stream().filter(n -> n.getActtype().equals("endEvent")).collect(Collectors.toList());
                        if (activityList.size() > 0) {
                            //抄送
                            carbonCopyService.GetCarbonCopyList(activityList.get(0), user_id, file_id, taskname, Taskproseeid, new ArrayList<>(), "1", customerId, token, p_id, request.gettCode(), yearStr);
                            sysShortMessageService.generateSMS("", new ArrayList<>(), file_id, activityList.get(0).getActid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                            sysMessageService.generateMassage("", new ArrayList<>(), file_id, activityList.get(0).getActid(), taskname, "通过", explain, user_id, customerId, token, p_id, request.gettCode(), yearStr);
                        }
                    }
                }

                //删除当前任务节点的驳回记录信息
                List<SysTaskReturnMap> strmList = sysTaskReturnMapService.selectByFileId(file_id);
                if (!CollectionUtils.isEmpty(strmList)) {
                    String actId = FlowActivityobj.getActid();
                    List<SysTaskReturnMap> actMapList = strmList.stream().filter(n -> n.getOperateTargetAct().equals(actId)).collect(Collectors.toList());
                    for (SysTaskReturnMap it : actMapList) {
                        sysTaskReturnMapService.DeleteById(it.getId());
                    }

                }
                return new BaseResponse(true, "流程提交成功！", Taskproseeid);
            } else {
                if (massage.size() > 0)
                    return new BaseResponse(String.join("", massage.stream().distinct().collect(Collectors.toList())));
                else return new BaseResponse("流程提交错误！");
            }
        } else return new BaseResponse("流程提交错误，当前任务节点获取失败！");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse returnInitiatorByFileId(String fileId, String tCode, String account, String operationType, String year) {
        File fileObj = fileService.getFileById(fileId, tCode, year);
        if (fileObj != null) {
            //获取所有的流程信息；
            String flowid = fileObj.getFlowId();
            String customerId = "All";
            String actId = "";
            String userId = "";
            String fileType = "start";
            if ("last".equals(operationType)) {
                //获取当前流程下的所有审批记录信息
                List<TaskProcessin> taskPlist = taskProcessinService.getListByTpFlowIdAndTpEntityId(fileObj.getFlowId(), fileObj.getFileid(), customerId, year);
                if (!CollectionUtils.isEmpty(taskPlist)) {
                    List<TaskProcessin> newList = taskPlist.stream().filter(n -> "userTask".equals(n.getTpActtype())).sorted(Comparator.comparing(TaskProcessin::getTpDotime, Comparator.reverseOrder())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(newList)) {
                        actId = newList.get(0).getTpActid();
                        userId = newList.get(0).getTpUser();
                        fileType = "pre";
                    }
                }

            }

            if (ValidateUtil.isEmpty(actId)) {
                List<FlowActivity> FlowActivitys = flowActivityService.getFlowActivityByActFlowID(flowid, customerId);
                if (CollectionUtils.isEmpty(FlowActivitys)) {
                    return BaseResponse.fail("流程图设置异常，未能退回发起人");
                }
                //获取当前流程下所有的连接线
                List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
                FlowActivity FlowActivityobj = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);
                if (FlowActivityobj == null) {
                    return BaseResponse.fail("流程图设置异常，未能退回发起人");
                }
                actId = FlowActivityobj.getActid();
                userId = fileObj.getFilecreateuser();
            }
            String sendUid = "";
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.eq("U_Account", account);
            wrapper.eq("U_State", 1);
            List<SysUser> userList = sysUserService.list(wrapper);
            if (!CollectionUtils.isEmpty(userList)) {
                sendUid = userList.get(0).getuId();
            }

            Date date = new Date();
            Task task = new Task();
            task.setTaskid(GenerateUUID.getUUID());
            task.setTaskread("未读");
            task.setTaskname(fileObj.getFiletitle() + "【第三方系统退回】");  // 任务名称
            task.setTaskclient("");
            task.setTaskflow(flowid);
            task.setTaskpreactid("");
            task.setTaskcurrentactid(actId);
            task.setTaskproject(fileObj.getFileproject());
            task.setTaskrule("");
            task.setTaskentityid(fileId);
            task.setTasksendtime(new Date());
            task.setTasksenduser(sendUid);
            task.setTaskuser(userId);
            if ("start".equals(fileType)) {
                task.setTaskReturnType("start");
                taskService.deleteTaskByTaskEntityID(fileObj.getFileid(), customerId);
            }
            task.setTaskstarttime(date);
            task.setTaskendtime(date);
            if (!"All".equals(customerId)) {
                task.setTeId(customerId);

            }
            task.setTCode(fileObj.gettCode());
            task.setFlowId(fileId);
            task.setSecId(fileObj.getFilesectionid());
            task.setProjectId(fileObj.getFileproject());
            task.setContractId(fileObj.getFilecontract());
            taskService.save(task);
            fileService.updatefile(fileObj.gettCode(), fileId, fileType, flowid, customerId, "", year);
            return BaseResponse.ok("流程退回成功");

        } else {
            return BaseResponse.fail("未找到文档信息");
        }

    }


    @Override
    public BaseResponse getFlowMapBytCode(String tCode) {
        QueryWrapper<Flow> wrapper = new QueryWrapper<>();
        wrapper.eq("t_code", tCode);
        wrapper.eq("FlowEnable", "1");
        return BaseResponse.ok(baseMapper.selectList(wrapper));
    }

    @Override
    public BaseResponse returnFinalStep(List<String> fileIds) {
        try {
            // 根据冒号分割fileIds，形成map
            Map<String, String> fileIdMap = new HashMap<>();
            if (fileIds != null && !fileIds.isEmpty()) {
                for (String fileId : fileIds) {
                    if (fileId != null && fileId.contains("：")) {
                        String[] parts = fileId.split("：", 2);
                        if (parts.length == 2) {
                            fileIdMap.put(parts[0], parts[1]);
                        } else {
                            fileIdMap.put(fileId, "");
                        }
                    } else {
                        fileIdMap.put(fileId, "");
                    }
                }
            }

            List<String> ids = new ArrayList<>(fileIdMap.keySet());

            //根据传入的fileId查询file表信息
            String year = DateUtil.DateToString(new Date(), "yyyy");
            List<File> fileList = fileService.getListByIds(ids, "", year);
            if (!CollectionUtils.isEmpty(fileList)) {

                List<String> flowIds = fileList.stream().map(File::getFlowId).distinct().collect(Collectors.toList());
                QueryWrapper<FlowActivity> actWrapper = new QueryWrapper<>();
                actWrapper.in("ActFlowID", flowIds);
                List<FlowActivity> actList = flowActivityService.list(actWrapper);
                QueryWrapper<TaskProcessin> tpQueryWrapper = new QueryWrapper<>();
                tpQueryWrapper.in("tp_entityid", ids);
                List<TaskProcessin> tpList = taskProcessinService.list(tpQueryWrapper);
                List<Task> taskList = new ArrayList<>();
                List<TaskProcessin> doneList = new ArrayList<>();
                List<File> newFileList = new ArrayList<>();
                for (File fileObj : fileList) {
                    List<TaskProcessin> fileTpList = tpList.stream().filter(n -> n.getTpEntityid().equals(fileObj.getFileid()) && "userTask".equals(n.getTpActtype()) && "提交".equals(n.getTpType())).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(fileTpList)) {
                        //判断追后审批的流程节点是否还在流程图中
                        TaskProcessin tpObj = fileTpList.get(0);
                        List<FlowActivity> acts = actList.stream().filter(n -> n.getActid().equals(tpObj.getTpActid())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(acts)) {
                            //生成已办
                            TaskProcessin taskProcessin = new TaskProcessin();
                            taskProcessin.setTpId(GenerateUUID.getUUID());
                            taskProcessin.setTpActid("");
                            taskProcessin.setActname("司库审批");
                            taskProcessin.setTpDotime(new Date());
                            taskProcessin.setTpFlowid(tpObj.getTpFlowid());
                            String tpExplain = fileIdMap.getOrDefault(tpObj.getTpEntityid(), "支付失败");
                            taskProcessin.setTpExplain(tpExplain);
                            taskProcessin.setTpResult("退回");
                            taskProcessin.setTpName(tpObj.getTpName());
                            taskProcessin.setTpFrom(tpObj.getTpFrom());
                            taskProcessin.setTpEntityid(tpObj.getTpEntityid());
                            taskProcessin.setTpProject(tpObj.getTpProject());
                            taskProcessin.setTpUser("e9fe32c6fc6d4d6ebf14bd928706fed6");
                            taskProcessin.setTpCountersigntitle(tpObj.getTpCountersigntitle());
                            taskProcessin.setTpType("退回");
                            taskProcessin.setTpStarttime(new Date());
                            taskProcessin.setTpEndtime(new Date());
                            taskProcessin.setTpActtype(tpObj.getTpActtype());
                            taskProcessin.setTpTaskReturnType(tpObj.getTpTaskReturnType());
                            taskProcessin.setTpClient("PC");
                            taskProcessin.setTeId(tpObj.getTeId());
                            taskProcessin.setTpSend(tpObj.getTpSend());
                            doneList.add(taskProcessin);

                            //生成待办
                            Task task = new Task();
                            task.setTaskid(GenerateUUID.getUUID());
                            task.setTaskread("未读");
                            task.setTaskname(fileObj.getFiletitle() + "（司库退回）");  // 任务名称
                            task.setTaskflow(acts.get(0).getActflowid());
                            task.setTaskcurrentactid(tpObj.getTpActid());
                            task.setTaskproject(fileObj.getFileproject());
                            task.setTaskentityid(fileObj.getFileid());
                            task.setTasksendtime(new Date());
                            task.setTasksenduser("系统自动退回");
                            task.setTaskuser(tpObj.getTpUser());
                            task.setTaskType("退回");
                            task.setTeId(fileObj.getTeId());
                            Date date = new Date();
                            task.setTaskstarttime(date);
                            task.setTaskendtime(DateUtil.DateChangeHour(date, DateUtil.TastProcessingtime("24")));
                            task.setFlowId(fileObj.getFlowId());
                            task.setTCode(fileObj.gettCode());
                            taskList.add(task);
                            newFileList.add(fileObj);
                        }
                    }
                }
                taskProcessinService.saveBatch(doneList);
                taskService.saveBatch(taskList);
                fileService.updateFileBath(newFileList, "next", "", year);


            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("流程服务：批量退回发起人错误：" + e.getMessage());
        }
        return BaseResponse.ok("");
    }


    public Task createEndNodes(String fileId, String userId, String taskname, String flowId, String customerId) {
        Task taskentity = new Task();
        taskentity.setTaskid(GenerateUUID.getUUID());
        taskentity.setTaskname(taskname);
        taskentity.setTaskentityid(fileId);
        taskentity.setTaskflow(flowId);
        taskentity.setTaskuser(userId);
        //获取当前文档的所有审批记录
        //获取当前流程下的审批记录信息
        QueryWrapper<TaskProcessin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tp_entityid", fileId);
        List<TaskProcessin> tpList = taskProcessinService.list(queryWrapper);
        if (CollectionUtils.isEmpty(tpList)) {
            log.error("流程服务：创建结束节点任务失败，00");
            return null;
        }
        //获取流程节点信息
        List<FlowActivity> flowActivitys = flowActivityService.getFlowActivityByActFlowID(flowId, customerId);
        if (CollectionUtils.isEmpty(flowActivitys)) {
            log.error("流程服务：创建结束节点任务失败，01");
            return null;
        }
        List<FlowActivity> endActList = flowActivitys.stream().filter(n -> n.getActtype() != null && n.getActtype().equals("endEvent")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(endActList)) {
            log.error("流程服务：创建结束节点任务失败，02");
            return null;
        }
        if (endActList.size() == 1) {
            //只有1个结束节点，直接赋值
            taskentity.setTaskcurrentactid(endActList.get(0).getActid());//当前办理节点,这里固定获取流程的结束节点
        } else {
            //两个及以上，获取最后一个人审批的对应节点
            //获取当前流程下所有的连接线
            List<FlowRule> flowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowId, customerId);
            List<String> actIds = flowActivitys.stream().map(FlowActivity::getActid).collect(Collectors.toList());
            List<TaskProcessin> tps = tpList.stream().filter(n -> actIds.contains(n.getTpActid())).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(tps)) {
                taskentity.setTaskcurrentactid(endActList.get(0).getActid());
            } else {
                String actId = tps.get(0).getTpActid();
                taskentity.setTasksenduser(tps.get(0).getTpUser());
                taskentity.setTaskpreactid(actId);
                FlowActivity actObj = flowActivitys.stream().filter(n -> n.getActid().equals(actId)).findFirst().orElse(null);
                if (actObj == null) {
                    taskentity.setTaskcurrentactid(endActList.get(0).getActid());
                }
                //获取当前节点对应的结束节点
                List<FlowActivity> otherActList = flowActivityService.getAllSubsequentNodes(actObj, flowActivitys, flowRules);
                if (CollectionUtils.isEmpty(otherActList)) {
                    //默认一个结束节点
                    taskentity.setTaskcurrentactid(endActList.get(0).getActid());
                }
                List<FlowActivity> endActList1 = otherActList.stream().filter(n -> actIds.contains(n.getActid())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(endActList1)) {
                    taskentity.setTaskcurrentactid(endActList.get(0).getActid());
                } else {
                    taskentity.setTaskcurrentactid(endActList1.get(0).getActid());
                }
            }
        }
        return taskentity;
    }

    @Override
    public BaseResponse processReturn(SendbackFlowDto request, String customerId, String token) {
        String backtype = request.getBacktype();//退回类型
        String explain = request.getExplain();//审批意见
        String taskname = request.getTaskname();//任务名称
        String file_id = request.getFile_id();//文档id
        String user_id = request.getUser_id();//当前操作用户
        List<String> cc_Users = request.getCc_user();//用户选择的抄送用户信息
        String returntype = "";//返回类型，不结收前端的入参，固定为按顺序提交
        String Taskproseeid = "";//返回审批记录id
        String smsType = request.getSmstype();//是否发送短信

        String requestSource = request.getRequestSource();
        String yearStr = request.getYear();

        File filePojo = fileService.getFileById(file_id, request.gettCode(), yearStr);
        if (filePojo != null) {
            if (filePojo.getFilecreatedate() != null) {
                yearStr = DateUtil.DateToString(filePojo.getFilecreatedate(), "yyyy");
            }
            if (ValidateUtil.isNotEmpty(filePojo.getFlowId())) {
                Task taskentity = createEndNodes(file_id, user_id, taskname, filePojo.getFlowId(), customerId);
                Map<String, String> map = new HashMap<>();
                map.put("file_id", file_id);
                map.put("taskname", taskname);
                map.put("backtype", backtype);
                map.put("user_id", user_id);
                map.put("explain", explain);
                map.put("returntype", returntype);
                map.put("Taskproseeid", Taskproseeid);
                map.put("customerId", customerId);
                map.put("yearStr", yearStr);
                map.put("tCode", request.gettCode());
                map.put("token", token);
                map.put("smsType", smsType);
                map.put("requestSource", requestSource);
                map.put("ReturnaActId", request.getReturnaActId());
                map.put("ReturnUser", request.getReturnUser());
                return returnLogic(taskentity, filePojo, map, cc_Users);
            } else {
                return BaseResponse.fail("当前业务未进行流程审批，流程退回失败");
            }
        } else {
            return BaseResponse.fail("未找到file信息，流程退回失败");
        }

    }

    @Override
    public BaseResponse getFlowMapNotAuthentication(FilerecordRequest requst, String customerId) {
        String flowIds = "";
        if (ValidateUtil.isEmpty(requst.getEntityid())) {
            try {
                flowIds = GetEnableFlowID(requst.getSolutionId(), requst.getCid(), requst.getPid(), requst.getTemplatecode(), customerId);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        File fileobj = null;
        String fileStatus = "";
        String yearStr = requst.getYear();
        if (ValidateUtil.isNotEmpty(requst.getEntityid())) {
            fileobj = fileService.getFileById(requst.getEntityid(), requst.gettCode(), yearStr);
            if (fileobj != null) {
                if (fileobj.getFilecreatedate() != null) {
                    yearStr = DateUtil.DateToString(fileobj.getFilecreatedate(), "yyyy");
                }
                flowIds = fileobj.getFlowId();
                fileStatus = fileobj.getFileflowstatus() + "";
            }
        }
        if (ValidateUtil.isEmpty(flowIds)) {
            try {
                flowIds = GetEnableFlowID(requst.getSolutionId(), requst.getCid(), requst.getPid(), requst.getTemplatecode(), customerId);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        String keyStr = GenerateUUID.getUUID();
        //获取当前流程下所有的审批节点
        List<FlowActivity> activity = flowActivityService.getFlowActivityByActFlowID(flowIds, customerId);
        //获取当前流程下的所有审批连接线
        List<FlowRule> Rule = flowRuleService.getFlowRuleListByRuleFlowID(flowIds, customerId);
        //获取当前流程的版本信息
        List<String> actityid_list = new ArrayList<>();
        List<FlowActivity> sstaactities = activity.stream().filter(item -> item.getActtype().equals("startEvent")).collect(Collectors.toList());
        if (sstaactities != null && sstaactities.size() < 1) return new BaseResponse("流程配置错误！");
        FlowActivity staactity = sstaactities.stream().findFirst().get();
        //计算第一步
        List<FlowActivity> FlowActivityList = flowActivityService.GetSYSFlowActivityEntitybind(staactity, new HashMap<>(), activity, Rule, 0, customerId);
        //计算流程的开始节点
        actityid_list.add(staactity.getActid());
        FlowActivity fristactity = FlowActivityList.stream().sorted(Comparator.comparing(FlowActivity::getActname).reversed()).findFirst().get();


        flowActivityService.setActListALLToNull(keyStr);
        Flow obj = baseMapper.selectById(flowIds);
        List<TaskProcessResponse> newData = new ArrayList<>();
        List<TaskProcessin> tpList = new ArrayList<>();
        List<Map<String, Object>> taskdata = new ArrayList<>();
        List<String> taskActIds = new ArrayList<>();
        if (fileobj != null) {
            flowIds = fileobj.getFlowId();
            if (ValidateUtil.isEmpty(flowIds)) {
                return new BaseResponse("未建立流程图关联");
            }
            //获取当前流程的所有审批记录
            List<TaskProcessin> Task_ProcessinAll = taskProcessinService.getListByTpFlowIdAndTpEntityId(flowIds, fileobj.getFileid(), customerId, yearStr);
            //只要提交的审批记录信息
            Task_ProcessinAll = Task_ProcessinAll.stream().filter(n -> n.getTpType().equals("提交")).collect(Collectors.toList());
            tpList = Task_ProcessinAll;
            List<Task> taskobj = taskService.todolists(requst.getEntityid(), customerId);
            //获取发起人节点
            List<TaskProcessin> strtask = Task_ProcessinAll.stream().filter(n -> n.getTpActid() != null && n.getTpActid().equals(fristactity.getActid()) && (n.getTpTaskgetflagstr() == null || (n.getTpTaskgetflagstr() != null && !n.getTpTaskgetflagstr().trim().equals("1")))).collect(Collectors.toList());
            if (strtask.size() > 0) {
                Date startdotime = strtask.parallelStream().sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList()).get(0).getTpDotime();//获取最新一次不是原路返回的流程处理记录
                Task_ProcessinAll = Task_ProcessinAll.stream().filter(n -> n.getTpDotime().getTime() >= startdotime.getTime()).collect(Collectors.toList());
            }
            List<String> userid = Task_ProcessinAll.stream().map(n -> n.getTpUser()).collect(Collectors.toList());
            List<String> uid = taskobj.stream().map(n -> n.getTaskuser()).collect(Collectors.toList());
            userid.addAll(uid);
            List<SysUser> users = sysUserService.getUserAllByUids(userid, customerId);
            Task_ProcessinAll.forEach(data -> {
                TaskProcessResponse objtask = new TaskProcessResponse();
                BeanUtils.copyProperties(data, objtask);
                objtask.setFlowRecordRule(obj.getFlowRecordRule());
                if (!ValidateUtil.isEmpty(data.getTpUser()) && users.size() > 0) {
                    List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getTpUser())).collect(Collectors.toList());
                    if (user != null && user.size() > 0) objtask.setTpUser(user.get(0).getuName());
                    objtask.setTpUserType(user.get(0).getuType());
                }
                newData.add(objtask);
            });
            //获取当前任务的所属审批人
            List<Map<String, Object>> newTask = new ArrayList<>();
            taskobj.forEach(item -> {
                Map<String, Object> tasko = new HashMap<>();
                taskActIds.add(item.getTaskcurrentactid());
                tasko.put("taskId", item.getTaskcurrentactid());
                String userName = "";
                List<SysUser> userobj = users.stream().filter(n -> n.getuId().equals(item.getTaskuser())).collect(Collectors.toList());
                if (userobj.size() > 0) {
                    userName = userobj.get(0).getuName();
                }
                tasko.put("userName", userName);
                tasko.put("startTime", item.getTaskstarttime());
                tasko.put("endTime", item.getTaskendtime());
                tasko.put("taskRead", item.getTaskread());
                tasko.put("taskReadtime", item.getTaskreadtime());
                newTask.add(tasko);
            });
            taskdata = newTask;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("xmlstr", obj);
        data.put("actity", actityid_list);
        data.put("taskid", taskdata);
        data.put("TaskProcessin", newData);
        data.put("flowActivityList", activity);
        data.put("flowRule", Rule);
        return new BaseResponse("", data, 1);
    }

    @Override
    public BaseResponse getFileByFlow(List<GetFlowDto> requests, String customerId) {
        if (ValidateUtil.isEmpty(requests)) {
            return BaseResponse.fail("请求参数不能为空");
        }
        Map<String, List<String>> resultList = new HashMap<>();
        requests.forEach(request -> {
            String id = null;
            try {
                id = GetEnableFlowID("", request.getCompanyId(), request.getProjectId(), request.getTemplateCode(), customerId);
            } catch (Exception ex) {
                log.info("GG1");
                log.error(ex.getMessage(), ex);
                ex.printStackTrace();
            }
            if (ValidateUtil.isEmpty(id)) {
                return;
            }
            Flow flowobj = baseMapper.selectById(id);
            if (flowobj != null) {
                String yearStr = request.getYear();
                List<FlowActivity> flowactivty = flowActivityService.getFlowActivityByActFlowID(id, customerId);
                FlowActivity startobj = flowactivty.stream().filter(n -> "startEvent".equals(n.getActtype())).findFirst().get();
                List<FlowRule> Rule = flowRuleService.getFlowRuleListByRuleFlowID(id, customerId);
                Map<String, Object> newConditions = flowActivityService.setTorsionConditions("", "", request.getCondition(), customerId, request.gettCode(), yearStr);
                String keyStr = GenerateUUID.getUUID();
                List<String> allActIds = new ArrayList<>();
                List<FlowActivity> flowactivtynew = flowActivityService.GetFlowActivityList(keyStr, startobj, newConditions, flowactivty, Rule, 0, "0", new ArrayList<>(), customerId, allActIds);
                flowActivityService.setActListALLToNull(keyStr);
                flowactivtynew.add(startobj);
                flowactivtynew = flowactivtynew.stream().sorted(Comparator.comparing(FlowActivity::getActorder)).collect(Collectors.toList());

                List<String> actTemplateCodes = flowactivtynew.stream().filter(n -> !n.getActtype().contains("Gateway")).map(FlowActivity::getActtemplatecode).collect(Collectors.toList());
                resultList.put(newConditions.get("secId").toString(), actTemplateCodes);
            }
        });

        List<FileSecidsVo> result = new ArrayList<>();
        resultList.forEach((key, value) -> {
            FileSecidDto fileSecidDto = new FileSecidDto();
            fileSecidDto.setSecid(key);
            fileSecidDto.setCode(value);
            List<FileSecidVo> sectionFileDetails = fileService.getSectionFileDetails(fileSecidDto, customerId);
            result.add(new FileSecidsVo(key, sectionFileDetails));
        });
        return BaseResponse.ok(result);
    }
}
