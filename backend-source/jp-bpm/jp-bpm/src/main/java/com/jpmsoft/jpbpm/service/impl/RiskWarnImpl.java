package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.RiskWarnMapper;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.ConContractVo;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.DateUtil;
import com.jpmsoft.jpbpm.util.fileLog;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class RiskWarnImpl extends ServiceImpl<RiskWarnMapper, RiskWarn> implements RiskWarnService {
    @Autowired
    SysConfigEntityService sysConfigEntityService;

    @Autowired
    private FlowActivityService flowActivityService;

    @Autowired
    private SysPositionService sysPositionService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private CarbonCopyService carbonCopyService;

    @Autowired
    private FlowService flowService;
    @Autowired
    private FlowRuleService flowRuleService;

    @Autowired
    private SysPositionUserService sysPositionUserService;
    @Autowired
    private RiskWarnIndexSerice riskWarnIndexSerice;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysShortMessageService sysShortMessageService;
    @Autowired
    private FileService fileService;
    @Value("${bpm.logSavePath}")
    public String logSavePath;

    @Override
    public BaseResponse alertGenerationAgent(List<RiskWarn> request, String customerId) {
        //判断传入信息是否为有效
        logOutput("风险预警：进入方法；时间：" + (new Date()));
        if (request.size() > 0) {
            logOutput("风险预警：判断入参数量大于0；时间：" + (new Date()));
            //有数据
            //获取基础配置查看用户是否需要生成待办信息（1为生成、未设置则不生成、0或其它则不生成）
            List<String> codeList = new ArrayList<>();
            codeList.add("riskWarnSendtask");
            //codeList.add("ositionmMinister");//部长的所属岗位
            //codeList.add("alertCC");//是否开启抄送
            codeList.add("earlyWarningSMS");//短信
            codeList.add("systemName");//系统名称
            Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
            String systemName = "";
            if (smsTemplateList.get("systemName") != null) {
                systemName = smsTemplateList.get("systemName") + "";
            }
            Object riskWarnSendtask = smsTemplateList.get("riskWarnSendtask");
            List<Flow> flowList = new ArrayList<>();
            List<FlowRule> FlowRules = new ArrayList<>();
            List<FlowActivity> FlowActivitys = new ArrayList<>();
            logOutput("风险预警：获取短信配置信息；时间：" + (new Date()));
            if (ValidateUtil.isNotNull(riskWarnSendtask) && riskWarnSendtask.toString().equals("1")) {
                logOutput("风险预警：判断riskWarnSendtask为开启；时间：" + (new Date()));
                List<String> pIds = request.stream().map(n -> n.getpId()).collect(Collectors.toList());
                HashSet newids = new HashSet(pIds);
                pIds.clear();
                pIds.addAll(newids);
                //获取廉政预警的流程文档模板信息
                QueryWrapper<Flow> wrapper = new QueryWrapper<>();
                wrapper.eq("T_Code", "RiskWarn");
                wrapper.eq("FlowEnable", "1");
                wrapper.eq("FlowProject", "ALL");
                if (newids.size() > 0) {
                    wrapper.or().in("FlowProject", newids);
                }
                flowList = flowService.list(wrapper);
                if (CollectionUtils.isNotEmpty(flowList)) {
                    flowList = flowList.stream().filter(n -> n.getTCode().equals("RiskWarn")).collect(Collectors.toList());
                    //获取到本次使用的流程图
                    if (CollectionUtils.isNotEmpty(flowList)) {
                        logOutput("风险预警：判断存在风险预警流程图；时间：" + (new Date()));
                        List<String> flowids = flowList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getFlowid())).map(n -> n.getFlowid()).collect(Collectors.toList());
                        if (CollectionUtils.isEmpty(flowids)) {
                            return new BaseResponse("未获取到相关流程图信息！");
                        }
                        //流程图的各个节点信息
                        QueryWrapper<FlowActivity> awrapper = new QueryWrapper<>();
                        awrapper.in("ActFlowID", flowids);
                        if (!"All".equals(customerId)) {
                            awrapper.eq("Te_Id", customerId);
                        }
                        FlowActivitys = flowActivityService.list(awrapper);

                        QueryWrapper<FlowRule> rwrapper = new QueryWrapper<>();
                        rwrapper.in("RuleFlowID", flowids);
                        if (!"All".equals(customerId)) {
                            rwrapper.eq("Te_Id", customerId);
                        }
                        FlowRules = flowRuleService.list(rwrapper);
                    }
                }
            }
            //List<Map<String, Object>> ccUidList = new ArrayList<>();
            Object earlyWarningSMS = smsTemplateList.get("earlyWarningSMS");


            //获取预警指标信息
            List<String> rwiIds = request.stream().map(n -> n.getRwiId()).collect(Collectors.toList());
            List<RiskWarnIndex> rwilist = riskWarnIndexSerice.listByIds(rwiIds);
            //获取项目名称信息
            List<String> pIds = request.stream().map(n -> n.getpId()).collect(Collectors.toList());
            List<PreProject> pList = getProjectNameByPid(pIds);

            //获取工程名称信息
            List<String> secIds = request.stream().map(n -> n.getSecId()).collect(Collectors.toList());
            List<SecInfo> secList = getsecInfoNameByPid(secIds);

            //获取合同名称信息
            List<String> conIds = request.stream().map(n -> n.getConId()).collect(Collectors.toList());
            List<ConContractVo> conList = getContractNameByCid(conIds);
            //获取所有的用户信息
            List<String> uIds = request.stream().map(n -> n.getRwHandleUid()).collect(Collectors.toList());


            //获取当前用户的所属部门
            QueryWrapper<SysPositionUser> wrapper = new QueryWrapper<>();
            wrapper.in("U_ID", uIds);
            List<SysPositionUser> spuList = new ArrayList<>();
            List<SysPosition> poList = new ArrayList<>();
            spuList = sysPositionUserService.list(wrapper);
            //获取所有的责任人
            List<SysUser> userList = new ArrayList<>();
            QueryWrapper<SysUser> userWrapper = new QueryWrapper<>();
            userWrapper.in("U_ID", uIds);
            userList = sysUserService.list(userWrapper);
            if (CollectionUtils.isNotEmpty(spuList)) {
                List<String> spList = spuList.stream().map((n -> n.getpId())).collect(Collectors.toList());
                poList = sysPositionService.listByIds(spList);
            }
            List<String> dIdList = new ArrayList<>();
            //获取需要抄送的岗位信息
            List<String> dIds = rwilist.stream().map(n -> n.getRwiCcPost()).collect(Collectors.toList());
            logOutput("风险预警：获取项目数量" + pList.size() + ";d当前时间" + (new Date()));
            logOutput("风险预警：获取标段数量" + secList.size() + ";d当前时间" + (new Date()));
            logOutput("风险预警：获取合同数量" + conList.size() + ";d当前时间" + (new Date()));
            for (int i = 0; i < dIds.size(); i++) {
                String dId = dIds.get(i);
                if (ValidateUtil.isNotEmpty(dId)) {
                    dId = dId.replaceAll("\\|", "#&");
                    String[] dIdArry = dId.split("#&");
                    for (int a = 0; a < dIdArry.length; a++) {
                        String dIdStr = dIdArry[a];
                        if (ValidateUtil.isNotEmpty(dIdStr)) {
                            String d_Id = dIdStr.replace("department_position,", "");
                            dIdList.add(d_Id);
                        }
                    }
                }
            }
            List<SysPosition> ccpList = new ArrayList<>();
            List<SysPositionUser> diDStrList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(dIdList)) {
                ccpList = sysPositionService.listByIds(dIdList);
                if (CollectionUtils.isNotEmpty(ccpList)) {
                    List<String> pdIds = ccpList.stream().map(n -> n.getpId()).collect(Collectors.toList());
                    QueryWrapper<SysPositionUser> spuWrapper = new QueryWrapper<>();
                    spuWrapper.in("P_ID", pdIds);
                    diDStrList = sysPositionUserService.list(spuWrapper);
                }
            }


            uIds.addAll(diDStrList.stream().map(n -> n.getuId()).collect(Collectors.toList()));
            HashSet newuids = new HashSet(uIds);
            uIds.clear();
            uIds.addAll(newuids);
            List<SysUser> userlist = sysUserService.getUserAllByUids(uIds, customerId);
            logOutput("风险预警：判断ccUidList和flowList“；时间：" + (new Date()));
            if (flowList.size() > 0) {
                logOutput("风险预警：flowList或ccUidList数量大于0“；时间：" + (new Date()));
                List<Task> new_task = new ArrayList<>();
                List<CarbonCopy> carbonCopy = new ArrayList<>();
                List<SysShortMessage> sysShortMessagePojo = new ArrayList<>();
                List<File> files = new ArrayList<>();
                logOutput("风险预警：开始循环request；时间：" + (new Date()));
                for (RiskWarn item : request) {
                    try {
                        String personResponsible = item.getRwHandleUname();
                        if (ValidateUtil.isEmpty(personResponsible)) {
                            if (CollectionUtils.isNotEmpty(userlist)) {
                                List<SysUser> us = userList.stream().filter(n -> n.getuId().equals(item.getRwHandleUid())).collect(Collectors.toList());
                                if (ValidateUtil.isNotEmpty(us)) {
                                    personResponsible = us.get(0).getuName();
                                }
                            }
                        }
                        //获取当前预警信息所属的预警指标信息
                        List<RiskWarnIndex> newRwiList = rwilist.stream().filter(n -> n.getRwiId().equals(item.getRwiId())).collect(Collectors.toList());
                        String rwiCcPost = "";
                        String filterSet = "";
                        String rwiCcSmsType = "";
                        String rwiCcSmsTemplate = "";
                        //获取项目名称，
                        String pName = "";
                        if (ValidateUtil.isNotEmpty(item.getpId())) {
                            List<PreProject> pobj = pList.stream().filter(n -> item.getpId().equals(n.getpId())).collect(Collectors.toList());
                            if (pobj.size() > 0) {
                                pName = pobj.get(0).getpName();
                            }
                        }

                        //获取标段信息
                        String secName = "";
                        if (ValidateUtil.isNotEmpty(item.getSecId())) {
                            List<SecInfo> pobj = secList.stream().filter(n -> n.getSecId().equals(item.getSecId())).collect(Collectors.toList());
                            if (pobj.size() > 0) {
                                secName = pobj.get(0).getSecName();
                            }
                        }

                        //获取合同
                        String conName = "";
                        if (ValidateUtil.isNotEmpty(item.getConId())) {
                            List<ConContractVo> pobj = conList.stream().filter(n -> n.getConId().equals(item.getConId())).collect(Collectors.toList());
                            if (pobj.size() > 0) {
                                conName = pobj.get(0).getConName();
                            }
                        }
                        logOutput("风险预警：项目:" + item.getpId() + "；标段：" + item.getSecId() + "；合同：" + item.getConId() + "；时间：" + (new Date()));
                        logOutput("风险预警：项目:" + pName + "；标段：" + secName + "；合同：" + conName + "；时间：" + (new Date()));
                        String actId = "";
                        String flowId = "";
                        String titlestr = "";
                        String tId = "";
                        String tCode = "";
                        if (newRwiList.size() > 0) {
                            titlestr = newRwiList.get(0).getRwiName();  // 任务名称
                            rwiCcPost = newRwiList.get(0).getRwiCcPost();
                            filterSet = newRwiList.get(0).getRwiCcPostFilter();
                            rwiCcSmsType = newRwiList.get(0).getRwiCcSmsType();
                            rwiCcSmsTemplate = newRwiList.get(0).getRwiCcSmsTemplate();
                        } else {
                            titlestr = "预警督办任务";  // 任务名称
                        }
                        if (ValidateUtil.isNotNull(riskWarnSendtask) && riskWarnSendtask.toString().equals("1")) {
                            //生成待办信息
                            Flow flowobj = getflowentity(item.getpId(), flowList);
                            if (flowobj != null) {
                                tId = flowobj.getTId();
                                tCode = flowobj.getTCode();
                                flowId = flowobj.getFlowid();
                                List<FlowActivity> actlist = FlowActivitys.stream().filter(n -> n.getActflowid().equals(flowobj.getFlowid())).collect(Collectors.toList());
                                List<FlowRule> ruleslist = FlowRules.stream().filter(n -> n.getRuleflowid().equals(flowobj.getFlowid())).collect(Collectors.toList());
                                FlowActivity FlowActivityobj = flowActivityService.getStarActivity(actlist, ruleslist, customerId);
                                //基础配置 预警待办生成的发送人
                                String yjSendUser = sysConfigEntityService.getConfByCode("riskWarnTaskSendUserId");
                                if (FlowActivityobj != null) {
                                    //生成待办信息
                                    actId = FlowActivityobj.getActid();
                                    Task task = new Task();
                                    task.setTaskid(GenerateUUID.getUUID());
                                    task.setTaskread("未读");
                                    task.setTaskname(titlestr);  // 任务名称
                                    task.setTaskclient(flowobj.getFlowclient());
                                    task.setTaskflow(flowobj.getFlowid());
                                    task.setTaskproject(item.getpId());
                                    task.setTaskentityid(item.getRwId());
                                    task.setTasksendtime(new Date());
                                    if (ValidateUtil.isEmpty(yjSendUser))//如果没有基础配置保持原样默认为系统预警生成
                                    {
                                        task.setTasksenduser("[系统预警生成]");
                                    }else {
                                        //配置taskuser则取接收人赋值为发送人
                                        if (yjSendUser.equalsIgnoreCase("taskuser"))
                                            task.setTasksenduser(item.getRwHandleUid());
                                        else //否则认定基础配置为用户id 赋值为发送人
                                            task.setTasksenduser(yjSendUser);
                                    }
                                    task.setTaskuser(item.getRwHandleUid());
                                    task.setTaskcurrentactid(actId);
                                    if (!"All".equals(customerId)) {
                                        task.setTeId(customerId);
                                    }
                                    Date date = new Date();
                                    task.setTaskstarttime(date);
                                    task.setTaskendtime(DateUtil.DateChangeHour(date, DateUtil.TastProcessingtime(FlowActivityobj.getActhour() + "")));
                                    new_task.add(task);
                                    logOutput("风险预警：" + item.getRwId() + "生成待办成功；时间：" + (new Date()));
                                }
                            }

                        }

                        File filobj = createFile(item.getRwId(), tId, item.getRwState(),
                                item.getRwHandleUid(), personResponsible, new Date(), item.getpId(), "风险预警", "RiskWarn",
                                GenerateUUID.getUUID(), item.getSecId(), "RiskWarn", flowId, customerId, new Date().getYear() + "");
                        files.add(filobj);
                        logOutput("风险预警：" + item.getRwId() + "生成files成功；时间：" + (new Date()));
                        String ministerId = "";
                        try {
                            logOutput("风险预警：" + item.getRwId() + "抄送判断；rwiCcPost：" + rwiCcPost + "；时间：" + (new Date()));
                            if (ValidateUtil.isNotNull(rwiCcPost)) {
                                //生成抄送信息
                                logOutput("风险预警：" + item.getRwId() + "开始生成抄送信息；时间：" + (new Date()));

                                if ("1".equals(filterSet)) {
                                    logOutput("风险预警：" + item.getRwId() + "抄送信息生成中【和责任人相同部门】；时间：" + (new Date()));
                                    List<SysPositionUser> spu = spuList.stream().filter(n -> n.getuId().equals(item.getRwHandleUid())).collect(Collectors.toList());
                                    logOutput("风险预警：" + item.getRwId() + "岗位角色判断；spu：" + spu.size() + "；时间：" + (new Date()));
                                    if (CollectionUtils.isNotEmpty(spu)) {
                                        List<String> ppIds = spu.stream().map(n -> n.getpId()).collect(Collectors.toList());
                                        List<SysPosition> sysPList = new ArrayList<>();
                                        if (CollectionUtils.isNotEmpty(poList)) {
                                            sysPList = poList.stream().filter(n -> ppIds.contains(n.getpId())).collect(Collectors.toList());
                                        }
                                        logOutput("风险预警：" + item.getRwId() + "岗位角色判断；sysPList：" + sysPList.size() + "；时间：" + (new Date()));
                                        if (CollectionUtils.isNotEmpty(sysPList)) {
                                            List<String> dids = sysPList.stream().map(n -> n.getdId()).collect(Collectors.toList());
                                            rwiCcPost = rwiCcPost.replaceAll("\\|", "#&");
                                            String[] rwiCcPostS = rwiCcPost.split("#&");
                                            logOutput("风险预警：" + item.getRwId() + "岗位角色判断；diDStrList：" + diDStrList.size() + "；时间：" + (new Date()));
                                            if (diDStrList.size() > 0) {
                                                for (int i = 0; i < rwiCcPostS.length; i++) {
                                                    String dIdStr = rwiCcPostS[i];
                                                    logOutput("风险预警：" + item.getRwId() + "岗位角色判断；dIdStr：" + dIdStr + "；时间：" + (new Date()));
                                                    if (ValidateUtil.isNotEmpty(dIdStr)) {
                                                        String d_Id = dIdStr.replace("department_position,", "");
                                                        List<SysPosition> sps = ccpList.stream().filter(n -> n.getpId().equals(d_Id) && dids.contains(n.getdId())).collect(Collectors.toList());

                                                        if (CollectionUtils.isNotEmpty(sps)) {
                                                            List<String> pIdLis = sps.stream().map(n -> n.getpId()).collect(Collectors.toList());
                                                            List<SysPositionUser> dpList = diDStrList.stream().filter(n -> pIdLis.contains(n.getpId())).collect(Collectors.toList());
                                                            logOutput("风险预警：" + item.getRwId() + "岗位角色判断；dpList：" + dpList.size() + "；时间：" + (new Date()));
                                                            if (CollectionUtils.isNotEmpty(dpList)) {
                                                                for (SysPositionUser dpObj : dpList) {
                                                                    carbonCopy.add(setCarbonCopyPojo(actId, dpObj.getuId(), flowId, titlestr, item.getRwId(), customerId));
                                                                    //生成短信
                                                                    if ("1".equals(rwiCcSmsType) && ValidateUtil.isNotNull(rwiCcSmsTemplate)) {
                                                                        logOutput("风险预警：开始生成抄送短信");
                                                                        if (CollectionUtils.isNotEmpty(userlist)) {
                                                                            List<SysUser> uObj = userlist.stream().filter(n -> n.getuId().equals(dpObj.getuId())).collect(Collectors.toList());
                                                                            if (CollectionUtils.isNotEmpty(uObj)) {
                                                                                logOutput("风险预警：开始生成抄送短信，短信发送人" + uObj.get(0).getuName() + "；时间：" + (new Date()));
                                                                                String smsContent = "";
                                                                                try {
                                                                                    smsContent = smsGeneration1(rwiCcSmsTemplate, titlestr, systemName, pName, uObj.get(0).getuName(), secName, conName, titlestr, personResponsible);
                                                                                } catch (Exception exa) {
                                                                                    logOutput("风险预警：开始生成抄送短信，短信生成错误;生成结束；时间：" + (new Date()) + "；" + exa.getLocalizedMessage());
                                                                                }
                                                                                try {
                                                                                    if (checkPhoneNumber(uObj.get(0).getuMtel())) {
                                                                                        SysShortMessage ssm = SetSysShortMessagePojo(smsContent, uObj.get(0).getuName(), uObj.get(0).getuMtel(), actId, item.getRwId(), flowId, customerId);
                                                                                        if (ValidateUtil.isNotEmpty(smsContent)) {
                                                                                            sysShortMessagePojo.add(ssm);
                                                                                        }
                                                                                    }
                                                                                } catch (Exception exa) {
                                                                                    logOutput("风险预警：开始生成抄送短信，短信生成成功，添加错误;生成结束；时间：" + (new Date()) + "；" + exa.getLocalizedMessage());

                                                                                }
                                                                                logOutput("风险预警：开始生成抄送短信，短信发送人" + uObj.get(0).getuName() + ";生成结束；时间：" + (new Date()));
                                                                            }
                                                                        }
                                                                    }

                                                                }
                                                            }

                                                        }

                                                    }
                                                }
                                            }

                                        }
                                    }
                                    //责任人相同部门
                                } else if ("2".equals(filterSet)) {
                                    logOutput("风险预警：" + item.getRwId() + "抄送信息生成中【和责任人相同单位】；时间：" + (new Date()));
                                    //单位相同

                                    //获取当前用户的所属单位
                                    List<SysUser> spu = userlist.stream().filter(n -> n.getuId().equals(item.getRwHandleUid())).collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(spu)) {
                                        rwiCcPost = rwiCcPost.replaceAll("\\|", "#&");
                                        String[] rwiCcPostS = rwiCcPost.split("#&");
                                        if (diDStrList.size() > 0) {
                                            for (int i = 0; i < rwiCcPostS.length; i++) {
                                                String dIdStr = rwiCcPostS[i];
                                                if (ValidateUtil.isNotEmpty(dIdStr)) {
                                                    String d_Id = dIdStr.replace("department_position,", "");
                                                    List<SysPosition> sps = ccpList.stream().filter(n -> n.getpId().equals(d_Id)).collect(Collectors.toList());
                                                    if (CollectionUtils.isNotEmpty(sps)) {
                                                        List<String> pIdLis = sps.stream().map(n -> n.getpId()).collect(Collectors.toList());
                                                        List<SysPositionUser> dpList = diDStrList.stream().filter(n -> pIdLis.contains(n.getpId())).collect(Collectors.toList());
                                                        if (CollectionUtils.isNotEmpty(dpList)) {
                                                            for (SysPositionUser dpObj : dpList) {
                                                                if (CollectionUtils.isNotEmpty(userlist)) {
                                                                    List<SysUser> uObj = userlist.stream().filter(n -> n.getuId().equals(dpObj.getuId()) && n.getcId().equals(spu.get(0).getcId())).collect(Collectors.toList());
                                                                    if (CollectionUtils.isNotEmpty(uObj)) {
                                                                        carbonCopy.add(setCarbonCopyPojo(actId, uObj.get(0).getuId(), flowId, titlestr, item.getRwId(), customerId));
                                                                        //生成短信
                                                                        if ("1".equals(rwiCcSmsType) && ValidateUtil.isNotNull(rwiCcSmsTemplate)) {
                                                                            if (checkPhoneNumber(uObj.get(0).getuMtel())) {
                                                                                String smsContent = smsGeneration1(rwiCcSmsTemplate, titlestr, systemName, pName, uObj.get(0).getuName(), secName, conName, titlestr, personResponsible);
                                                                                SysShortMessage ssm = SetSysShortMessagePojo(smsContent, uObj.get(0).getuName(), uObj.get(0).getuMtel(), actId, item.getRwId(), flowId, customerId);
                                                                                sysShortMessagePojo.add(ssm);
                                                                            }
                                                                        }
                                                                    }

                                                                }
                                                            }
                                                        }

                                                    }

                                                }
                                            }
                                        }

                                    }
                                } else {
                                    logOutput("风险预警：" + item.getRwId() + "抄送信息生成中【无过滤条件】；时间：" + (new Date()));
                                    rwiCcPost = rwiCcPost.replaceAll("\\|", "#&");
                                    String[] rwiCcPostS = rwiCcPost.split("#&");
                                    if (diDStrList.size() > 0) {
                                        for (int i = 0; i < rwiCcPostS.length; i++) {
                                            String dIdStr = rwiCcPostS[i];
                                            if (ValidateUtil.isNotEmpty(dIdStr)) {
                                                String d_Id = dIdStr.replace("department_position,", "");
                                                List<SysPosition> sps = ccpList.stream().filter(n -> n.getpId().equals(d_Id)).collect(Collectors.toList());
                                                if (CollectionUtils.isNotEmpty(sps)) {
                                                    List<String> pIdLis = sps.stream().map(n -> n.getpId()).collect(Collectors.toList());
                                                    List<SysPositionUser> dpList = diDStrList.stream().filter(n -> pIdLis.contains(n.getpId())).collect(Collectors.toList());
                                                    if (CollectionUtils.isNotEmpty(dpList)) {
                                                        for (SysPositionUser dpObj : dpList) {
                                                            if (CollectionUtils.isNotEmpty(userlist)) {
                                                                List<SysUser> uObj = userlist.stream().filter(n -> n.getuId().equals(dpObj.getuId())).collect(Collectors.toList());
                                                                if (CollectionUtils.isNotEmpty(uObj)) {
                                                                    carbonCopy.add(setCarbonCopyPojo(actId, uObj.get(0).getuId(), flowId, titlestr, item.getRwId(), customerId));
                                                                    //生成短信
                                                                    if ("1".equals(rwiCcSmsType) && ValidateUtil.isNotNull(rwiCcSmsTemplate)) {
                                                                        if (checkPhoneNumber(uObj.get(0).getuMtel())) {
                                                                            String smsContent = smsGeneration1(rwiCcSmsTemplate, titlestr, systemName, pName, uObj.get(0).getuName(), secName, conName, titlestr, personResponsible);
                                                                            SysShortMessage ssm = SetSysShortMessagePojo(smsContent, uObj.get(0).getuName(), uObj.get(0).getuMtel(), actId, item.getRwId(), flowId, customerId);
                                                                            sysShortMessagePojo.add(ssm);
                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    }

                                                }

                                            }
                                        }
                                    }

                                }


                                logOutput("风险预警：" + item.getRwId() + "抄送信息生成完成；时间：" + (new Date()));
                            }
                        } catch (Exception ex) {
                            logOutput("风险预警：" + item.getRwId() + "生成抄送信息异常：“" + ex.getMessage() + "”；时间：" + (new Date()));
                        }

                        String uName = "";
                        List<SysUser> userobj = userlist.stream().filter(n -> n.getuId().equals(item.getRwHandleUid())).collect(Collectors.toList());
                        if (userobj.size() > 0) {
                            uName = userobj.get(0).getuName();
                        }
                        //预警短信
                        logOutput("风险预警：" + item.getRwId() + "”earlyWarningSMS：" + earlyWarningSMS + "“；时间：" + (new Date()));
                        if (ValidateUtil.isNotNull(earlyWarningSMS)) {
                            //生成待办信息
                            //短信，配置短信模板则发送短息，不配置则不发送短信
                            logOutput("风险预警：" + item.getRwId() + "”ministerId：" + ministerId + "“；时间：" + (new Date()));
                            if (!ValidateUtil.isEmpty(ministerId)) {
                                String ministerIds = ministerId;
                                List<SysUser> puser = userlist.stream().filter(n -> n.getuId().equals(ministerIds)).collect(Collectors.toList());
                                if (puser.size() > 0) {
                                    if (checkPhoneNumber(userobj.get(0).getuMtel())) {
                                        String smsContent = smsGeneration(earlyWarningSMS.toString(), titlestr, systemName, pName, uName, secName, conName, titlestr);
                                        SysShortMessage ssm = SetSysShortMessagePojo(smsContent, puser.get(0).getuName(), puser.get(0).getuMtel(), actId, item.getRwId(), flowId, customerId);
                                        sysShortMessagePojo.add(ssm);
                                        logOutput("风险预警：" + item.getRwId() + "生成短信1；时间：" + (new Date()));
                                    }
                                }

                            }

                            logOutput("风险预警：" + item.getRwId() + "”RwHandleUid：" + item.getRwHandleUid() + "“；时间：" + (new Date()));
                            if (!ministerId.equals(item.getRwHandleUid())) {
                                logOutput("风险预警：" + item.getRwId() + "”userobj：" + userobj.size() + "“；时间：" + (new Date()));
                                if (userobj.size() > 0) {
                                    if (checkPhoneNumber(userobj.get(0).getuMtel())) {
                                        String smsContent = smsGeneration(earlyWarningSMS.toString(), titlestr, systemName, pName, uName, secName, conName, titlestr);
                                        SysShortMessage ssm = SetSysShortMessagePojo(smsContent, userobj.get(0).getuName(), userobj.get(0).getuMtel(), actId, item.getRwId(), flowId, customerId);
                                        sysShortMessagePojo.add(ssm);
                                        logOutput("风险预警：" + item.getRwId() + "生成短信2；时间：" + (new Date()));
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        logOutput("风险预警：" + item.getRwId() + "异常：" + ex.getMessage() + "；时间：" + (new Date()));
                    }
                }
                logOutput("风险预警：结束循环request；时间：" + (new Date()));
                logOutput("风险预警：files:" + files.size() + "；时间：" + (new Date()));
                if (files.size() > 0)
                    fileService.saveFileBatch(files, "", new Date().getYear() + "");

                logOutput("风险预警：new_task:" + new_task.size() + "；时间：" + (new Date()));
                if (new_task.size() > 0) {
                    taskService.saveBatch(new_task);
                }
                logOutput("风险预警：carbonCopy:" + carbonCopy.size() + "；时间：" + (new Date()));
                if (carbonCopy.size() > 0) {
                    carbonCopyService.saveBatch(carbonCopy);
                }
                logOutput("风险预警：sysShortMessagePojo：" + sysShortMessagePojo.size() + "；时间：" + (new Date()));
                if (sysShortMessagePojo.size() > 0) {
                    sysShortMessageService.saveBatch(sysShortMessagePojo);
                }
                return new BaseResponse(true, "生成待办成功！");

            } else {
                //无法生成信息，返回
                logOutput("风险预警：生成待办失败；时间：" + (new Date()));
                return new BaseResponse("生成待办失败！");
            }

        } else {
            //没数据返回
            logOutput("风险预警：未获取到关键数据；时间：" + (new Date()));
            return new BaseResponse("未获取到关键数据！");
        }

    }

    public void logOutput(String e) {
        log.error(e);
        fileLog fl = new fileLog();
        try {
            fl.write_to_txt(logSavePath, e);
        } catch (Exception ex) {
            log.error("流程服务：", ex);
        }
    }

    public Flow getflowentity(String pId, List<Flow> flowlist) {
        if (ValidateUtil.isEmpty(pId)) {
            List<Flow> pList = flowlist.stream().filter(n -> n.getFlowproject().equals("ALL")).collect(Collectors.toList());
            if (pList.size() > 0) {
                return pList.get(0);
            } else {
                return null;
            }
        } else {
            List<Flow> pList = flowlist.stream().filter(n -> n.getFlowproject().equals(pId)).collect(Collectors.toList());
            if (pList.size() > 0) {
                return pList.get(0);
            } else {
                List<Flow> pLists = flowlist.stream().filter(n -> n.getFlowproject().equals("ALL")).collect(Collectors.toList());
                if (pLists.size() > 0) {
                    return pLists.get(0);
                } else {
                    return null;
                }
            }
        }
    }

    public CarbonCopy setCarbonCopyPojo(String actid, String uId, String FlowId, String title, String entityID, String customerId) {
        CarbonCopy pojo = new CarbonCopy();
        pojo.setCcId(GenerateUUID.getUUID());
        pojo.setCcActid(actid);
        pojo.setCcUser(uId);
        pojo.setCcFlowid(FlowId);
        pojo.setCcTime(new Date());
        pojo.setCcCreateuser("系统预警生成");
        pojo.setCcRead("未阅");
        pojo.setCcReadtime(null);
        pojo.setCcTitle(title);
        pojo.setCcFileid(entityID);
        pojo.setCcProcessid(null);
        pojo.setCcExplain(null);
        if (!"All".equals(customerId)) {
            pojo.setTeId(customerId);
        }
        return pojo;
    }

    public File createFile(String id, String tId, Integer flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String relationId, String sectionId, String tCode, String flowId, String customerId, String year) {
        File fileentity = fileService.getFileById(id, tCode, year);
        if (fileentity == null) {
            fileentity = new File();
            fileentity.setFileid(id);
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        if (flowStatus != null && flowStatus.equals("null"))
            flowStatus = null;
        fileentity.settCode(tCode);
        fileentity.settId(tId);
        fileentity.setFileflowstatus(flowStatus);
        fileentity.setFilecreateuser(createUserId);
        fileentity.setFilecreateusername(createUserName);
        fileentity.setFilecreatedate(createTime);
        fileentity.setFilesectionid(sectionId);
        fileentity.setFlowId(flowId);
        if (!"All".equals(customerId)) {
            fileentity.setTeId(customerId);
        }

        if (!ValidateUtil.isEmpty(pId))
            fileentity.setFileproject(pId);
        fileentity.setFileRelationId(relationId);
        return fileentity;
    }

    public SysShortMessage SetSysShortMessagePojo(String smsContent, String uName, String Mtel, String actId, String fileId, String flowId, String customerId) {
        SysShortMessage obj = new SysShortMessage();
        obj.setSmActid(actId);
        obj.setSmFileid(fileId);
        obj.setSmId(GenerateUUID.getUUID());
        obj.setSmContent(smsContent);
        obj.setSmFlowid(flowId);
        obj.setSmFrom("流程短信");
        obj.setSmCreateTime(new Date());
        obj.setSmMobilePhone(Mtel);
        obj.setSmUname(uName);
        if (!"All".equals(customerId)) {
            obj.setTeId(customerId);
        }
        return obj;
    }

    public String smsGeneration(String smsTemplate, String taksName, String systemName, String pName, String userName, String secName, String conName, String alertName) {
        //短信处理
        smsTemplate = smsTemplate.replace("{任务名称}", checkStr(taksName));
        smsTemplate = smsTemplate.replace("{办理时间}", (new Date()).toString());
        smsTemplate = smsTemplate.replace("{系统名称}", checkStr(systemName));
        smsTemplate = smsTemplate.replace("{预警名称}", checkStr(alertName));
        // smsTemplate = smsTemplate.replace("{预警等级}", earlyWarningLevel);


        if (smsTemplate.contains("{人员名称}")) {
            smsTemplate = smsTemplate.replace("{人员名称}", checkStr(userName));
        }
        if (smsTemplate.contains("{项目名称}")) {

            smsTemplate = smsTemplate.replace("{项目名称}", checkStr(pName));

        }
        if (smsTemplate.contains("{工程名称}")) {
            smsTemplate = smsTemplate.replace("{工程名称}", checkStr(secName));
        }
        if (smsTemplate.contains("{合同名称}")) {
            smsTemplate = smsTemplate.replace("{合同名称}", checkStr(conName));
        }
        return smsTemplate;
    }

    public String smsGeneration1(String smsTemplate, String taksName, String systemName, String pName, String userName, String secName, String conName, String alertName, String personLiable) {
        //短信处理
        try {


            smsTemplate = smsTemplate.replace("{任务名称}", checkStr(taksName));
            smsTemplate = smsTemplate.replace("{办理时间}", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            smsTemplate = smsTemplate.replace("{系统名称}", checkStr(systemName));
            smsTemplate = smsTemplate.replace("{预警名称}", checkStr(alertName));
            // smsTemplate = smsTemplate.replace("{预警等级}", earlyWarningLevel);


            if (smsTemplate.contains("{人员名称}")) {
                smsTemplate = smsTemplate.replace("{人员名称}", checkStr(userName));
            }
            if (smsTemplate.contains("{项目名称}")) {
                smsTemplate = smsTemplate.replace("{项目名称}", checkStr(pName));
            }
            if (smsTemplate.contains("{工程名称}")) {
                smsTemplate = smsTemplate.replace("{工程名称}", checkStr(secName));
            }
            if (smsTemplate.contains("{合同名称}")) {
                smsTemplate = smsTemplate.replace("{合同名称}", checkStr(conName));
            }
            logOutput("风险预警：短信替换{预警责任人}，替换前" + smsTemplate);
            if (smsTemplate.contains("{预警责任人}")) {
                logOutput("风险预警：短信替换{预警责任人}，准备替换");
                smsTemplate = smsTemplate.replace("{预警责任人}", checkStr(personLiable));
                logOutput("风险预警：短信替换{预警责任人}，替换后" + smsTemplate);
            } else {
                logOutput("风险预警：短信替换{预警责任人}，没匹配上" + smsTemplate);
            }

        } catch (Exception e) {
            logOutput("风险预警：短信替换错误" + e.getMessage());
        }
        return smsTemplate;
    }

    //获取项目名称等信息
    List<PreProject> getProjectNameByPid(List<String> pIds) {
        if (pIds.size() > 0) {
            String rwiIdstr = pIds.stream().map(String::valueOf).collect(Collectors.joining("','"));
            return baseMapper.getProjectNameByPid(rwiIdstr);
        } else {
            return null;
        }
    }

    //获取工程名称等信息
    List<ConContractVo> getContractNameByCid(List<String> cIds) {
        if (cIds.size() > 0) {
            String rwiIdstr = cIds.stream().map(String::valueOf).collect(Collectors.joining("','"));
            return baseMapper.getContractNameByCid(rwiIdstr);
        } else {
            return null;
        }
    }

    //获取合同名称等信息
    List<SecInfo> getsecInfoNameByPid(List<String> SecIds) {
        if (SecIds.size() > 0) {
            String rwiIdstr = SecIds.stream().map(String::valueOf).collect(Collectors.joining("','"));
            return baseMapper.getsecInfoNameByPid(rwiIdstr);
        } else {
            return null;
        }
    }

    public Boolean checkPhoneNumber(String phone) {
        //正则校验11位手机号
        String regex = "^1[3-9]\\d{9}$";
        return phone.matches(regex);
    }


    public String checkStr(String str) {
        if (ValidateUtil.isNull(str)) {
            return "";
        } else {
            return str;
        }
    }
}
