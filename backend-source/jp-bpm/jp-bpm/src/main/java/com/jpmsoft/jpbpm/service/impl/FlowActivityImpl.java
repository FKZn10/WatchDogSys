package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.FlowActivityMapper;
import com.jpmsoft.jpbpm.pojo.dto.NextActivitiesDto;
import com.jpmsoft.jpbpm.pojo.dto.userselectionDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.SysPositionPRoleAndUidVo;
import com.jpmsoft.jpbpm.pojo.vo.UserInfoVo;
import com.jpmsoft.jpbpm.pojo.vo.UserSelectionVo;
import com.jpmsoft.jpbpm.pojo.vo.userCVo;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.DateUtil;
import com.jpmsoft.jpbpm.util.FlowSubmitRules;
import com.jpmsoft.jpbpm.util.JPMCheckCondition;
import dm.jdbc.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class FlowActivityImpl extends ServiceImpl<FlowActivityMapper, FlowActivity> implements FlowActivityService {
    @Autowired
    SysConfigEntityService sysConfigEntityService;
    @Autowired
    private FlowRuleService flowRuleService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskProcessinService taskProcessinService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private FlowUserService flowUserService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPositionService sysPositionService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPositionRoleService sysPositionRoleService;
    @Autowired
    private SysPositionUserService sysPositionUserService;

    @Autowired
    private SysPositionPRoleService sysPositionPRoleService;

    @Autowired
    private FileService fileService;
    @Autowired
    private FileTemplateService fileTemplateService;
    @Autowired
    private PreProjectService preProjectService;
    @Autowired
    private SecInfoService secInfoService;
    @Autowired
    private DepartmentService departmentService;
    private Map<String, List<String>> actListALL = new HashMap<>();
    @Autowired
    ISysTaskReturnMapService sysTaskReturnMapService;

    @Override
    public void setActListALLToNull(String key) {
        actListALL.remove(key);
    }

    ;


    @Override
    public BaseResponse getNextActivities(NextActivitiesDto requst, String customerId) {
        //自动完成等内部调用：不启用经办人置顶，保持原有姓名排序结果
        return getNextActivities(requst, customerId, false);
    }

    @Override
    public BaseResponse getNextActivities(NextActivitiesDto requst, String customerId, boolean topUserEnabled) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date StarTime = new Date();
        log.info("进入下一步人员获取接口：" + sdf.format(StarTime));

        String taskid = requst.getTaskid();//任务id
        String templatecode = requst.getTemplatecode();//模板编码
        String Reverseconditions = requst.getCondition();//扭转条件
        String GetFlagstr = "";//流程提交方式（是的原路返回）
        String userid = requst.getUser_id();//上一步操作人id
        String p_id = requst.getProjectid();//项目id
        String c_id = requst.getCompanyid();//单位id
        String solutionID = requst.getSolutionid();//文档所属编码
        String clientID = requst.getClientid();//客户端
        String actIdNew = requst.getActId();
        String cId = "";//当前用户的所属单位
        String yearStr = requst.getYear();
        File filePojo = fileService.getFileById(requst.getFilseId(), requst.gettCode(), yearStr);
        if (ValidateUtil.isEmpty(p_id) && filePojo != null) {
            p_id = filePojo.getFileproject();
            if (ValidateUtil.isEmpty(yearStr) && filePojo.getFilecreatedate() != null) {
                yearStr = filePojo.getFilecreatedate().getTime() + "";
            }
        }
        if (ValidateUtil.isEmpty(requst.getcId())) {
            SysUser userobj = sysUserService.getById(userid);
            if (userobj != null) {
                cId = userobj.getcId() + "";
            }
        } else {
            cId = requst.getcId();
        }
        //获取前端传输过来的任务id
        String entityId = "";
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalSkipSetting");
        codeList.add("processSubmission");
        Map<String, Object> configelist = sysConfigEntityService.getConfigEntity(codeList, customerId);
        Object approvalSkipSetting = configelist.get("approvalSkipSetting");
        Boolean onState = true;
        if (approvalSkipSetting != null) {
            Object obj = JacksonUtils.parseObject(approvalSkipSetting.toString(), new TypeReference<Map<String, String>>() {
            });
            if (((Map) obj).get("onState") != null) {
                String onStateStr = ((Map) obj).get("onState").toString();
                onState = onStateStr == "true";
            }

        }
        try {

            FlowActivity FlowActivityobj = null;
            List<FlowRule> FlowRules = new ArrayList<>();
            List<FlowActivity> FlowActivitys = new ArrayList<>();
            //获取流程节点信息
            if (ValidateUtil.isEmpty(taskid) && !ValidateUtil.isEmpty(templatecode)) {
                //获取开始节点流程任务活动节点
                String flowid = flowService.GetEnableFlowID(solutionID, c_id, p_id, templatecode, customerId);
                if (ValidateUtil.isEmpty(flowid)) {
                    return new BaseResponse("未找到启用的流程！");
                }
                QueryWrapper<FlowActivity> wrapper = new QueryWrapper<>();
                wrapper.eq("ActFlowID", flowid);
                if (!"All".equals(customerId)) {
                    wrapper.eq("Te_Id", customerId);
                }
                FlowActivitys = baseMapper.selectList(wrapper);
                //获取当前流程下所有的连接线
                FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowid, customerId);
                FlowActivityobj = getStarActivity(FlowActivitys, FlowRules, customerId);
            } else if (!ValidateUtil.isEmpty(taskid)) {
                Task Taskobj = taskService.getById(taskid);
                if (Taskobj != null) {
                    String actid = Taskobj.getTaskcurrentactid();
                    GetFlagstr = Taskobj.getTaskgetflagstr();
                    entityId = Taskobj.getTaskentityid();
                    //只有发起才有原路返回
                    //第一步判断当前活动任务是否在开始节点\
                    String flowid = Taskobj.getTaskflow();
                    if (Taskobj.getTaskReturnType() != null && Taskobj.getTaskReturnType().equals("start")) {
                        //判断流程图是否变动

                        Flow flowobj = flowService.getById(flowid);
                        if (ValidateUtil.isNotEmpty(actIdNew)) {
                            actid = actIdNew;
                        }
                        FlowActivityobj = baseMapper.selectById(actid);
                        if (flowobj != null && flowobj.getFlowenable() != null && flowobj.getFlowenable().equals("1")) {
                            if (GetFlagstr != null && GetFlagstr.trim().equals("1")) {
                                //没有变动
                                //主版本 原路返回可以
                                //获取上一步流程活动节点
                                Map<String, Object> data = new HashMap<>();
                                data.put("Array", null);
                                data.put("datasstus", "return");
                                data.put("flowid", flowid);
                                data.put("FlowActivityobj", FlowActivityobj);
                                data.put("taskid", Taskobj.getTaskid());
                                return new BaseResponse("", data, 1);
                            } else {
                                //正常的流程
                                //获取当前任务活动id
                                QueryWrapper<FlowActivity> wrapper = new QueryWrapper<>();
                                wrapper.eq("ActFlowID", Taskobj.getTaskflow());
                                if (!"All".equals(customerId)) {
                                    wrapper.eq("Te_Id", customerId);
                                }
                                FlowActivitys = baseMapper.selectList(wrapper);
                                //获取当前流程下所有的连接线
                                FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(Taskobj.getTaskflow(), customerId);
                            }
                        } else {
                            //获取流程的基础配置信息，判断退回发起人后，流程是否是按照新的流程走还是老的流程走

                            Object processSubmission = configelist.get("processSubmission");
                            if (processSubmission != null && processSubmission.equals("returnOld") && GetFlagstr != null && GetFlagstr.trim().equals("1")) {
                                //没有变动
                                //主版本 原路返回可以
                                //获取上一步流程活动节点
                                Map<String, Object> data = new HashMap<>();
                                data.put("Array", null);
                                data.put("datasstus", "return");
                                data.put("flowid", flowid);
                                data.put("FlowActivityobj", FlowActivityobj);
                                data.put("taskid", Taskobj.getTaskid());
                                return new BaseResponse("", data, 1);
                            } else {
                                //有变动
                                String flowids = flowService.GetEnableFlowID(solutionID, c_id, p_id, templatecode, customerId);
                                if (ValidateUtil.isEmpty(flowids)) {
                                    return new BaseResponse("未找到启用的流程！");
                                }
                                QueryWrapper<FlowActivity> wrapper = new QueryWrapper<>();
                                wrapper.eq("ActFlowID", flowids);
                                if (!"All".equals(customerId)) {
                                    wrapper.eq("Te_Id", customerId);
                                }
                                FlowActivitys = baseMapper.selectList(wrapper);
                                //获取当前流程下所有的连接线
                                FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowids, customerId);
                                FlowActivityobj = getStarActivity(FlowActivitys, FlowRules, customerId);
                            }
                        }

                    } else {
                        if (GetFlagstr != null && GetFlagstr.trim().equals("1")) {
                            //没有变动
                            //主版本 原路返回可以
                            //获取上一步流程活动节点
                            Map<String, Object> data = new HashMap<>();
                            data.put("Array", null);
                            data.put("datasstus", "return");
                            data.put("flowid", flowid);
                            data.put("FlowActivityobj", FlowActivityobj);
                            data.put("taskid", Taskobj.getTaskid());
                            return new BaseResponse("", data, 1);
                        } else {
                            //正常的流程
                            //获取当前任务活动id
                            if (ValidateUtil.isNotEmpty(actIdNew)) {
                                actid = actIdNew;
                            }
                            FlowActivityobj = baseMapper.selectById(actid);
                            QueryWrapper<FlowActivity> wrapper = new QueryWrapper<>();
                            wrapper.eq("ActFlowID", Taskobj.getTaskflow());
                            if (!"All".equals(customerId)) {
                                wrapper.eq("Te_Id", customerId);
                            }
                            FlowActivitys = baseMapper.selectList(wrapper);
                            //获取当前流程下所有的连接线
                            FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(Taskobj.getTaskflow(), customerId);
                        }
                    }
                }
            }

            //判断当前流程节点是否存在
            if (FlowActivityobj != null) {
                //判断是否有原路返回的
                if (filePojo != null) {
                    List<SysTaskReturnMap> trmList = sysTaskReturnMapService.selectByFileId(filePojo.getFileid());
                    if (!CollectionUtils.isEmpty(trmList)) {
                        String actIdnow = FlowActivityobj.getActid();
                        List<SysTaskReturnMap> actList = trmList.stream().filter(n -> n.getOperateTargetAct().equals(actIdnow)).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(actList)) {
                            //原路返回
                            String actId = actList.get(0).getOperateAct();
                            String uIdNew = actList.get(0).getOperateActUser();
                            List<FlowActivity> aActs = FlowActivitys.stream().filter(n -> n.getActid().equals(actId)).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(aActs)) {
                                FlowActivity item = aActs.get(0);
                                List<UserSelectionVo> nextArr = new ArrayList<>();
                                UserSelectionVo UserSelectionobj = new UserSelectionVo();
                                UserSelectionobj.nodename = item.getActname();
                                UserSelectionobj.nodeid = item.getActid();
                                UserSelectionobj.slectType = "select";
                                UserSelectionobj.actSelectAll = item.getActSelectAll();
                                UserSelectionobj.actNum = 1;
                                UserSelectionobj.actSkipStatus = item.getActSkipStatus();
                                UserSelectionobj.multipleChoice = item.getActConcurrencyType();
                                if (item.getActnoticeW() != null && item.getActnoticeW().contains("01")) {
                                    if (item.getActnoticeN() != null && item.getActnoticeN().equals("TRUE")) {
                                        UserSelectionobj.smsNotificationStatus = "1";
                                    } else {
                                        UserSelectionobj.smsNotificationStatus = "0";
                                    }
                                } else {
                                    UserSelectionobj.smsNotificationStatus = "-1";
                                }
                                List<UserSelectionVo.Userinfo> ueerlist = new ArrayList<>();
                                SysUser user = sysUserService.getById(uIdNew);
                                UserSelectionVo.Userinfo ueerobj = new UserSelectionVo().new Userinfo();
                                ueerobj.setUserid(user.getuId().toString());
                                ueerobj.setUsername(user.getuName().toString());
                                ueerobj.setUserNum(1);
                                ueerlist.add(ueerobj);
                                UserSelectionobj.userlist = ueerlist;
                                nextArr.add(UserSelectionobj);
                                Map<String, Object> data = new HashMap<>();
                                data.put("Array", nextArr);
                                data.put("datasstus", "next");
                                data.put("flowid", aActs.get(0).getActflowid());
                                data.put("FlowActivityobj", FlowActivityobj);
                                return new BaseResponse(true, "", data);
                            }

                        }
                    }
                }
                //正常流程提交信息查询
                //获取当前流程节点的下级的所有流程节点
                Map<String, Object> newConditions = setTorsionConditions(entityId, userid, Reverseconditions, customerId, requst.gettCode(), yearStr);
                List<FlowActivity> FlowActivityList = GetSYSFlowActivityEntitybind(FlowActivityobj, newConditions, FlowActivitys, FlowRules, 0, customerId);
                //判断下一步流程是否已经结束
                FlowActivity SYS_FlowActivityend = null;
                try {
//
                    SYS_FlowActivityend = FlowActivityList.stream().filter(n -> n.getActtype() != null && n.getActtype().equals("endEvent")).findFirst().orElse(null);
                } catch (Exception ex) {
                    log.error("流程服务：", ex);
                }
                //获取下一步是否有结束节点
                if (SYS_FlowActivityend != null && FlowActivityList.size() > 1) {
                    //下一步节点存在多个活动，并且包含结束节点，提示流程节点设置异常
                    return new BaseResponse("流程设置错误!");
                } else if (SYS_FlowActivityend != null) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("Array", "");
                    data.put("datasstus", "end");
                    data.put("flowid", SYS_FlowActivityend.getActflowid());
                    data.put("FlowActivityobj", FlowActivityobj);
                    return new BaseResponse(true, "", data);
                } else {
                    List<String> Activityid = FlowActivityList.stream().map(FlowActivity::getActid).collect(Collectors.toList());
                    List<Map<String, Object>> TaskProcessinlist = taskProcessinService.getSomeFieldsTaskProcessins(entityId, customerId, requst.gettCode(), yearStr);
                    //循环每个节点，并获取对应userid节点下的用户信息
                    Map<String, String> pData = new HashMap<>();
                    pData.put("pId", p_id);
                    pData.put("customerId", customerId);
                    pData.put("userid", userid);
                    pData.put("fileId", requst.getFilseId());
                    pData.put("cId", cId);
                    pData.put("actId", FlowActivityobj.getActid());

                    //根据项目ID重新获取部门ID
                    String newDid = preProjectService.getDidByPid(p_id);
                    List<UserSelectionVo> returlist = getActUser(FlowActivityList, Reverseconditions, pData, Activityid, newConditions, FlowActivitys, TaskProcessinlist, newDid);
                    //判断下一步经办人，是否只有1个节点，并且只有1个人
                    String promptStr = "";
                    if (!CollectionUtils.isEmpty(returlist)) {
                        if (returlist.size() == 1) {
                            //只有一条数
                            if (!CollectionUtils.isEmpty(returlist.get(0).getUserlist())) {
                                if (returlist.get(0).getUserlist().size() == 1) {
                                    //zhi'you1条数据
                                    promptStr = "审批环节“" + returlist.get(0).getNodename() + "”已跳过";
                                    String uid = returlist.get(0).getUserlist().get(0).getUserid();
                                    Boolean slipStr = true;
                                    String nodeId = returlist.get(0).getNodeid();
                                    List<FlowActivity> nodeAct = FlowActivityList.stream().filter(n -> n.getActid().equals(nodeId)).collect(Collectors.toList());
                                    if (!CollectionUtils.isEmpty(nodeAct)) {
                                        if ("0".equals(nodeAct.get(0).getActSkipStatus())) {
                                            slipStr = false;
                                        }
                                        if ("1".equals(nodeAct.get(0).getActNotSkipPrompt())) {
                                            promptStr = "";
                                        } else {
                                            if (ValidateUtil.isNotEmpty(nodeAct.get(0).getActCustomizeSkipPrompt())) {
                                                promptStr = nodeAct.get(0).getActCustomizeSkipPrompt();
                                            }
                                        }
                                    }
                                    if (userid.equals(uid) && onState && slipStr) {
                                        //是同一个人，继续获取一步经办人信息
                                        List<FlowActivity> FlowActivityListN = GetSYSFlowActivityEntitybind(FlowActivityList.get(0), newConditions, FlowActivitys, FlowRules, 0, customerId);
                                        FlowActivity sysFlowActivityend = null;
                                        try {
                                            List<FlowActivity> factList = FlowActivityListN.stream().filter(n -> n.getActtype().equals("endEvent")).collect(Collectors.toList());
                                            if (!CollectionUtils.isEmpty(factList)) {
                                                sysFlowActivityend = factList.get(0);
                                            }
                                        } catch (Exception ex) {
                                            log.error("流程服务：", ex);
                                        }
                                        if (sysFlowActivityend != null && FlowActivityList.size() > 1) {
                                            promptStr = "";
                                        } else if (sysFlowActivityend != null) {
                                            Map<String, Object> dataN = new HashMap<>();
                                            dataN.put("Array", "");
                                            dataN.put("datasstus", "end");
                                            dataN.put("flowid", sysFlowActivityend.getActflowid());
                                            dataN.put("FlowActivityobj", FlowActivityobj);
                                            dataN.put("promptStr", promptStr);
                                            return new BaseResponse(true, "", dataN);
                                        } else {
                                            List<String> ActivityIds = FlowActivityListN.stream().map(FlowActivity::getActid).collect(Collectors.toList());
                                            returlist = getActUser(FlowActivityListN, Reverseconditions, pData, ActivityIds, newConditions, FlowActivitys, TaskProcessinlist, newDid);
                                        }
                                    } else {
                                        promptStr = "";
                                    }
                                }
                            }
                        }
                    }
                    //置顶排序放在「下一步唯一经办人与当前操作人相同则跳过节点」判断之后，避免影响跳过与提示逻辑
                    if (topUserEnabled) {
                        returlist = topUserSort(returlist, customerId);
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put("Array", returlist);
                    data.put("datasstus", "next");
                    data.put("flowid", FlowActivityobj.getActflowid());
                    data.put("FlowActivityobj", FlowActivityobj);
                    data.put("promptStr", promptStr);
                    data.put("onState", onState);
                    return new BaseResponse("", data, returlist.size());
                    //     }
                }
            } else {
                //未找到节点
                return new BaseResponse("未找到当前流程活动！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage(), ex);
            return new BaseResponse("获取下一步流程操作人失败！");
        }

    }

    @Override
    public BaseResponse getNextActivitiesByActId(NextActivitiesDto requst, String customerId) {
        List<UserSelectionVo> returlist = new ArrayList<>();
        String actId = requst.getActId();
        String flowId = requst.getFlowId();
        if (ValidateUtil.isEmpty(flowId)) {
            FlowActivity actObj = baseMapper.selectById(actId);
            if (actObj != null) {
                flowId = actObj.getActflowid();
            }
        }
        QueryWrapper<FlowActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("ActFlowID", flowId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<FlowActivity> FlowActivitys = baseMapper.selectList(wrapper);
        //获取当前流程下所有的连接线
        List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowId, customerId);
        List<FlowActivity> acts = FlowActivitys.stream().filter(a -> a.getActid().equals(actId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(acts)) {
            Map<String, Object> newConditions = setTorsionConditions(requst.getFilseId(), requst.getUser_id(), requst.getCondition(), customerId, requst.gettCode(), requst.getYear());
            List<FlowActivity> FlowActivityListN = GetSYSFlowActivityEntitybind(acts.get(0), newConditions, FlowActivitys, FlowRules, 0, customerId);
            FlowActivity sysFlowActivityend = null;
            try {
                List<FlowActivity> factList = FlowActivityListN.stream().filter(n -> n.getActtype().equals("endEvent")).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(factList)) {
                    sysFlowActivityend = factList.get(0);
                }
            } catch (Exception ex) {
                log.error("流程服务：", ex);
            }

            if (sysFlowActivityend != null) {
                Map<String, Object> dataN = new HashMap<>();
                dataN.put("Array", "");
                dataN.put("datasstus", "end");
                dataN.put("flowid", sysFlowActivityend.getActflowid());
                dataN.put("FlowActivityobj", acts);
                dataN.put("promptStr", "");
                return new BaseResponse(true, "", dataN);
            } else {
                SysUser userobj = sysUserService.getById(requst.getUser_id());
                String cId = "";
                if (userobj != null) {
                    cId = userobj.getcId() + "";
                }
                Map<String, String> pData = new HashMap<>();
                pData.put("pId", requst.getProjectid());
                pData.put("customerId", customerId);
                pData.put("userid", requst.getUser_id());
                pData.put("fileId", requst.getFilseId());
                pData.put("cId", cId);
                pData.put("actId", acts.get(0).getActid());
                List<String> ActivityIds = FlowActivityListN.stream().map(FlowActivity::getActid).collect(Collectors.toList());
                List<Map<String, Object>> TaskProcessinlist = taskProcessinService.getSomeFieldsTaskProcessins(requst.getFilseId(), customerId, requst.gettCode(), requst.getYear());
                String newDid = preProjectService.getDidByPid(requst.getProjectid());
                returlist = getActUser(FlowActivityListN, requst.getCondition(), pData, ActivityIds, newConditions, FlowActivitys, TaskProcessinlist, newDid);
            }
            //该接口只服务于前端选人页面，直接启用经办人置顶
            returlist = topUserSort(returlist, customerId);
            Map<String, Object> data = new HashMap<>();
            data.put("Array", returlist);
            data.put("datasstus", "next");
            data.put("flowid", acts.get(0).getActflowid());
            data.put("FlowActivityobj", acts.get(0));
            data.put("promptStr", "");
            data.put("onState", "");
            return new BaseResponse("", data, returlist.size());
        } else {
            return new BaseResponse("流程图错误，无法跳过", null, 0);
        }

    }

    @Override
    public List<UserSelectionVo> getActUser(List<FlowActivity> FlowActivityList, String
            Reverseconditions, Map<String, String> pData, List<String> actIds, Map<String, Object> newConditions, List<FlowActivity> FlowActivitys, List<Map<String, Object>> TaskProcessinlist, String
                                                    dcId) {
        List<UserSelectionVo> returlist = new ArrayList<>();
        //获取每个节点下的用户信息
        FlowActivityList = FlowActivityList.stream().sorted(Comparator.comparing(FlowActivity::getActcode)).distinct().collect(Collectors.toList());
        Integer actNum = 0;
        for (FlowActivity item : FlowActivityList) {
            actNum = actNum + 1;
            //获取当前节点下所有的用户信息
            UserSelectionVo UserSelectionobj = new UserSelectionVo();
            UserSelectionobj.nodename = item.getActname();
            UserSelectionobj.nodeid = item.getActid();
            UserSelectionobj.slectType = "select";
            UserSelectionobj.actSelectAll = item.getActSelectAll();
            UserSelectionobj.actNum = actNum;
            UserSelectionobj.actSkipStatus = item.getActSkipStatus();
            UserSelectionobj.multipleChoice = item.getActConcurrencyType();
            if (item.getActnoticeW() != null && item.getActnoticeW().contains("01")) {
                if (item.getActnoticeN() != null && item.getActnoticeN().equals("TRUE")) {
                    UserSelectionobj.smsNotificationStatus = "1";
                } else {
                    UserSelectionobj.smsNotificationStatus = "0";
                }
            } else {
                UserSelectionobj.smsNotificationStatus = "-1";
            }
            List<userCVo> u_idlist = new ArrayList<>();
            if (item.getActusershowtype().equals("0")) {
                //解析表单上的项目id
                Map<String, Object> conditionobj = JacksonUtils.parseObject(Reverseconditions, new TypeReference<Map<String, Object>>() {
                });
                String newPid = "";
                if (conditionobj != null) {
                    if (conditionobj.containsKey("pId") && conditionobj.get("pId") != null) {
                        newPid = conditionobj.get("pId").toString();
                    } else {
                        if (conditionobj.containsKey("projectid") && conditionobj.get("projectid") != null) {
                            newPid = conditionobj.get("projectid").toString();
                        }
                    }
                }
                if (StringUtil.isEmpty(newPid) && !StringUtil.isEmpty(pData.get("pId")) && !pData.get("pId").equals("ALL")) {
                    newPid = pData.get("pId");
                }
                u_idlist = selectUserByPostRole(actIds, item, newPid, pData.get("customerId"));
            } else if (item.getActusershowtype().equals("1")) {
                //前置节点用户审批人
                u_idlist = getPreStepApprover(pData.get("fileId"), item, FlowActivitys, TaskProcessinlist);
            } else if (item.getActusershowtype().equals("2")) {
                //用户自由选择
                UserSelectionobj.slectType = "openselect";
            } else if (item.getActusershowtype().equals("3")) {
                //当前审批人所在部门所有员工
                u_idlist = getDepartmentPersonnel(pData.get("userid"), pData.get("customerId"));
            } else if (item.getActusershowtype().equals("4")) {
                //指定人员（暂定）
                String actSet = item.getActUserAppoint();
                if (ValidateUtil.isNotEmpty(actSet)) {
                    String[] userInfo = actSet.split("#-#");
                    if (userInfo.length == 2) {
                        userCVo o = new userCVo();
                        o.setuId(userInfo[1]);
                        u_idlist.add(o);
                    }
                }
            } else if (item.getActusershowtype().equals("5") || item.getActusershowtype().equals("6")) {
                List<Department> dList = departmentService.getDepartmentAll(pData.get("customerId"));
                //cId
                List<String> dIds = getDepartmentIds(dList, dcId);
                dIds.add(dcId);
                if (!CollectionUtils.isEmpty(dIds)) {
                    QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
                    wrapper.in("D_ID", dIds);
                    if (item.getActusershowtype().equals("6")) {
                        wrapper.eq("P_Name", item.getActUserPost());
                    }

                    if (!"All".equals(pData.get("customerId"))) {
                        wrapper.eq("Te_Id", pData.get("customerId"));
                    }
                    List<SysPosition> pList = sysPositionService.list(wrapper);
                    if (!CollectionUtils.isEmpty(pList)) {
                        QueryWrapper<SysPositionUser> uWrapper = new QueryWrapper<>();
                        List<String> pIds = pList.stream().map(n -> n.getpId()).collect(Collectors.toList());
                        uWrapper.in("P_ID", pIds);
                        List<SysPositionUser> upList = sysPositionUserService.list(uWrapper);
                        if (!CollectionUtils.isEmpty(upList)) {
                            for (SysPositionUser n : upList
                            ) {
                                userCVo o = new userCVo();
                                o.setuId(n.getuId());
                                o.setpId(n.getpId());
                                u_idlist.add(o);

                            }
                        }
                    }
                }
            } else if (item.getActusershowtype().equals("7")) {
                if (!ValidateUtil.isEmpty(dcId)) {
                    QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
                    wrapper.eq("D_ID", dcId);
                    List<SysPosition> pList = sysPositionService.list(wrapper);
                    if (!CollectionUtils.isEmpty(pList)) {
                        QueryWrapper<SysPositionUser> uWrapper = new QueryWrapper<>();
                        List<String> pIds = pList.stream().map(n -> n.getpId()).collect(Collectors.toList());
                        uWrapper.in("P_ID", pIds);
                        uWrapper.eq("Pu_Duty", 1);
                        List<SysPositionUser> upList = sysPositionUserService.list(uWrapper);
                        if (!CollectionUtils.isEmpty(upList)) {
                            for (SysPositionUser n : upList
                            ) {
                                userCVo o = new userCVo();
                                o.setuId(n.getuId());
                                o.setpId(n.getpId());
                                u_idlist.add(o);
                            }
                        }
                    }
                }

            } else if (item.getActusershowtype().equals("8") || item.getActusershowtype().equals("9") || item.getActusershowtype().equals("10")) {
                //表单字段
                Object val = newConditions.get(item.getActFormField());//获取表单字段值
                if (val != null && ValidateUtil.isNotEmpty(val + "")) {
                    QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
                    wrapper.eq("D_ID", val.toString());
                    if (item.getActusershowtype().equals("9")) {
                        wrapper.eq("P_Name", item.getActUserPost());
                    }
                    if (!"All".equals(pData.get("customerId"))) {
                        wrapper.eq("Te_Id", pData.get("customerId"));
                    }
                    List<SysPosition> pList = sysPositionService.list(wrapper);
                    if (!CollectionUtils.isEmpty(pList)) {
                        QueryWrapper<SysPositionUser> uWrapper = new QueryWrapper<>();
                        List<String> pIds = pList.stream().map(n -> n.getpId()).collect(Collectors.toList());
                        uWrapper.in("P_ID", pIds);
                        if (item.getActusershowtype().equals("10")) {
                            uWrapper.eq("Pu_Duty", 1);
                        }
                        List<SysPositionUser> upList = sysPositionUserService.list(uWrapper);
                        if (!CollectionUtils.isEmpty(upList)) {
                            for (SysPositionUser n : upList
                            ) {
                                userCVo o = new userCVo();
                                o.setuId(n.getuId());
                                o.setpId(n.getpId());
                                u_idlist.add(o);
                            }
                        }
                    }
                }
            } else if (item.getActusershowtype().equals("11") || item.getActusershowtype().equals("12") || item.getActusershowtype().equals("13")) {
                //获取前序节点审批人
                List<userCVo> userList = getPreStepApprover(pData.get("fileId"), item, FlowActivitys, TaskProcessinlist);
                if (!CollectionUtils.isEmpty(userList)) {
                    String uId = userList.get(0).getuId();
                    //获取当前人员的所属部门信息
                    if (item.getActusershowtype().equals("11")) {
                        //前置审批人下部门所有人员
                        u_idlist = getDepartmentPersonnel(uId, pData.get("customerId"));
                    } else {
                        List<String> dplist = sysPositionUserService.getDidByUid(uId);
                        QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
                        wrapper.in("D_ID", dplist);
                        if (item.getActusershowtype().equals("12")) {
                            wrapper.eq("P_Name", item.getActUserPost());
                        }
                        if (!"All".equals(pData.get("customerId"))) {
                            wrapper.eq("Te_Id", pData.get("customerId"));
                        }
                        List<SysPosition> pList = sysPositionService.list(wrapper);
                        if (!CollectionUtils.isEmpty(pList)) {
                            QueryWrapper<SysPositionUser> uWrapper = new QueryWrapper<>();
                            List<String> pIds = pList.stream().map(n -> n.getpId()).collect(Collectors.toList());
                            uWrapper.in("P_ID", pIds);
                            if (item.getActusershowtype().equals("13")) {
                                uWrapper.eq("Pu_Duty", 1);
                            }
                            List<SysPositionUser> upList = sysPositionUserService.list(uWrapper);
                            if (!CollectionUtils.isEmpty(upList)) {
                                for (SysPositionUser n : upList
                                ) {
                                    userCVo o = new userCVo();
                                    o.setuId(n.getuId());
                                    o.setpId(n.getpId());
                                    u_idlist.add(o);
                                }
                            }
                        }

                    }
                }
            }
            List<UserSelectionVo.Userinfo> ueerlist = new ArrayList<>();
            //获取用户信息
            List<SysUser> userlist = new ArrayList<>();
            //List<Map<String,Object>> userlist = new ArrayList<>();
            if (u_idlist.size() > 0) {
                log.error("开始进行条件过滤,过滤前：" + u_idlist.size());
                if (item.getActusershowtype().equals("0")) {
                    if (item.getActuserfilter() != null && item.getActuserfilter().equals("01")) {
                        userlist = sysUserService.getUserAllByUids(u_idlist.stream().map(n -> n.getuId()).collect(Collectors.toList()), pData.get("customerId"));
                        userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());
                        log.error("提交人同一单位,过滤前：" + userlist.size());
                        //提交人同一单位
                        String newcId = pData.get("cId");
                        List<String> cIds = new ArrayList<>();
//                        //获取当前节点的历史审批人
//                        List<String> tpUList = TaskProcessinlist.stream().filter(n -> determineMapValue(n, "tp_actid", pData.get("actId"))).map(n -> n.get(getMapKey(n, "tp_user")).toString()).collect(Collectors.toList());
//                        if (!CollectionUtils.isEmpty(tpUList)) {
//                            List<String> uniqueList = new ArrayList<String>(new HashSet<String>(tpUList));
//                            List<SysUser> uList = sysUserService.getUserAllByUids(uniqueList, pData.get("customerId"));
//                            cIds = uList.stream().map(n -> n.getcId()).collect(Collectors.toList());
//                        }
                        cIds.add(newcId);
                        List<String> newCids = cIds;

                        userlist = userlist.stream().filter(n -> newCids.contains(n.getcId())).collect(Collectors.toList());
                        log.error("提交人单位：过滤条件：" + newcId + ";过滤后：" + userlist.size());
                    } else if (item.getActuserfilter() != null && item.getActuserfilter().equals("02")) {

                        //提交人所属部门下所有人员信息
                        List<userCVo> dpUlist = getDepartmentPersonnel(pData.get("userid").toString(), pData.get("customerId"));
                        List<String> ids = new ArrayList<>();
                        if (!CollectionUtils.isEmpty(dpUlist)) {
                            List<String> uIds = dpUlist.stream().map(n -> n.getuId()).collect(Collectors.toList());
                            for (userCVo uIdObj : u_idlist
                            ) {
                                if (uIds.contains(uIdObj.getuId())) {
                                    ids.add(uIdObj.getuId());
                                }
                            }
                        }
                        userlist = sysUserService.getUserAllByUids(ids, pData.get("customerId"));
                        userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());

                    } else if (item.getActuserfilter() != null && item.getActuserfilter().equals("03")) {
                        userlist = sysUserService.getUserAllByUids(u_idlist.stream().map(n -> n.getuId()).collect(Collectors.toList()), pData.get("customerId"));
                        userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());
                        log.error("与表单创建人同一单位,过滤前：" + userlist.size());
                        //与表单创建人同一单位
                        String newcId = newConditions.get("fileCId").toString();
                        userlist = userlist.stream().filter(n -> n.getcId().equals(newcId)).collect(Collectors.toList());
                        log.error("与表单创建人同一单位：过滤条件：" + newcId + ";过滤后：" + userlist.size());
                    } else if (item.getActuserfilter() != null && item.getActuserfilter().equals("04")) {
                        //与表单创建人同一部门
                        //List<String> dplist = sysPositionUserService.getDidByUid(newConditions.get("fileUId").toString());
                        List<userCVo> dpUlist = getDepartmentPersonnel(newConditions.get("fileUId").toString(), pData.get("customerId"));
                        List<String> ids = new ArrayList<>();
                        if (!CollectionUtils.isEmpty(dpUlist)) {
                            List<String> uIds = dpUlist.stream().map(n -> n.getuId()).collect(Collectors.toList());
                            for (userCVo uIdObj : u_idlist
                            ) {
                                if (uIds.contains(uIdObj.getuId())) {
                                    ids.add(uIdObj.getuId());
                                }
                            }
                        }
                        userlist = sysUserService.getUserAllByUids(ids, pData.get("customerId"));
                        userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());
                    } else if (item.getActuserfilter() != null && item.getActuserfilter().equals("05")) {
                        //与表单所属项目主管部门相同
                        List<userCVo> dplist = getUserIdByPid(pData.get("pId"), pData.get("customerId"));
                        List<String> ids = new ArrayList<>();
                        for (userCVo uIdObj : u_idlist
                        ) {
                            List<userCVo> dpLis = dplist.stream().filter(c -> c.getuId().equals(uIdObj.getuId())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(dpLis)) {
                                ids.add(uIdObj.getuId());
                            }
                        }
                        userlist = sysUserService.getUserAllByUids(ids, pData.get("customerId"));
                        userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());

                    } else if (item.getActuserfilter() != null && item.getActuserfilter().equals("06")) {
                        //与表单关联部门相同
                        Object val = newConditions.get(item.getActFilterFields());//获取表单字段值
                        List<userCVo> dplist = getUserByDid(val + "", pData.get("customerId"));
                        List<String> ids = new ArrayList<>();
                        for (userCVo uIdObj : u_idlist
                        ) {
                            List<userCVo> dpLis = dplist.stream().filter(c -> c.getuId().equals(uIdObj.getuId())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(dpLis)) {
                                ids.add(uIdObj.getuId());
                            }
                        }
                        userlist = sysUserService.getUserAllByUids(ids, pData.get("customerId"));
                        userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());
                    } else if (item.getActuserfilter() != null && item.getActuserfilter().equals("07")) {
                        //与表单关联单位相同
                        Object val = newConditions.get(item.getActFilterFields());//获取表单字段值
                        userlist = sysUserService.getUserAllByUids(u_idlist.stream().map(n -> n.getuId()).collect(Collectors.toList()), pData.get("customerId"));
                        userlist = userlist.stream().filter(n -> n.getcId().equals(val)).collect(Collectors.toList());
                    } else if (item.getActuserfilter() != null && item.getActuserfilter().equals("08")) {
                        List<userCVo> uList = getPreStepApprover(pData.get("fileId"), item, FlowActivitys, TaskProcessinlist);
                        if (!CollectionUtils.isEmpty(uList)) {
                            String uId = uList.get(0).getuId();
                            List<userCVo> dpUlist = getDepartmentPersonnel(uId, pData.get("customerId"));
                            List<String> ids = new ArrayList<>();
                            if (!CollectionUtils.isEmpty(dpUlist)) {
                                List<String> uIds = dpUlist.stream().map(n -> n.getuId()).collect(Collectors.toList());
                                for (userCVo uIdObj : u_idlist
                                ) {
                                    if (uIds.contains(uIdObj.getuId())) {
                                        ids.add(uIdObj.getuId());
                                    }
                                }
                            }
                            userlist = sysUserService.getUserAllByUids(ids, pData.get("customerId"));
                            userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());
                        }
                        //前置节点审批人所属主管部门
                    } else if (item.getActuserfilter() != null && item.getActuserfilter().equals("09")) {
                        //前置节点审批人所属单位过滤
                        List<userCVo> uList = getPreStepApprover(pData.get("fileId"), item, FlowActivitys, TaskProcessinlist);
                        if (!CollectionUtils.isEmpty(uList)) {
                            String uId = uList.get(0).getuId();
                            SysUser uObj = sysUserService.getById(uId);
                            if (uObj != null) {
                                userlist = sysUserService.getUserAllByUids(u_idlist.stream().map(n -> n.getuId()).collect(Collectors.toList()), pData.get("customerId"));
                                userlist = userlist.stream().filter(n -> n.getcId().equals(uObj.getcId())).collect(Collectors.toList());
                            }
                        }
                    } else {
                        userlist = sysUserService.getUserAllByUids(u_idlist.stream().map(n -> n.getuId()).collect(Collectors.toList()), pData.get("customerId"));
                        userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());
                    }
                } else {
                    userlist = sysUserService.getUserAllByUids(u_idlist.stream().map(n -> n.getuId()).collect(Collectors.toList()), pData.get("customerId"));
                    userlist = userlist.stream().filter(n -> n.getuState() != null && n.getuState().equals("1")).collect(Collectors.toList());
                }

                userlist = userlist.stream().sorted(Comparator.comparing(SysUser::getuName)).collect(Collectors.toList());
                Integer userNum = 0;
                for (SysUser atem : userlist) {
                    userNum = userNum + 1;
                    UserSelectionVo.Userinfo ueerobj = new UserSelectionVo().new Userinfo();
                    ueerobj.setUserid(atem.getuId().toString());
                    ueerobj.setUsername(atem.getuName().toString());
                    ueerobj.setUserNum(userNum);
                    ueerlist.add(ueerobj);
                }
            }
            UserSelectionobj.userlist = ueerlist;
            returlist.add(UserSelectionobj);
        }
        return returlist;
    }

    /**
     * 下一步经办人置顶：命中 SYS_Config(nextUserTopUserIds，Con_Value 存用户ID，多个用英文逗号拼接，顺序即置顶顺序)
     * 配置的用户排在该节点经办人列表最前面，其余人员保持原有姓名排序，序号(userNum)同步重排；
     * 未配置、配置为空或配置用户不在该节点经办人范围内时，返回原列表不做任何改动
     *
     * @param returlist  下一步各节点的经办人列表
     * @param customerId 租户标识
     * @return 置顶排序后的经办人列表
     */
    private List<UserSelectionVo> topUserSort(List<UserSelectionVo> returlist, String customerId) {
        if (CollectionUtils.isEmpty(returlist)) {
            return returlist;
        }
        List<String> topUids = new ArrayList<>();
        Object value = sysConfigEntityService.getConfigEntity(Collections.singletonList("nextUserTopUserIds"), customerId).get("nextUserTopUserIds");
        if (!ValidateUtil.isEmpty(value)) {
            for (String item : value.toString().split(",")) {
                if (ValidateUtil.isNotEmpty(item.trim())) {
                    topUids.add(item.trim());
                }
            }
        }
        if (CollectionUtils.isEmpty(topUids)) {
            return returlist;
        }
        for (UserSelectionVo node : returlist) {
            List<UserSelectionVo.Userinfo> userlist = node.getUserlist();
            if (CollectionUtils.isEmpty(userlist)) {
                continue;
            }
            List<UserSelectionVo.Userinfo> sorted = new ArrayList<>(userlist);
            sorted.sort(Comparator
                    .comparingInt((UserSelectionVo.Userinfo u) -> {
                        int index = topUids.indexOf(u.getUserid());
                        return index < 0 ? Integer.MAX_VALUE : index;
                    })
                    .thenComparing(UserSelectionVo.Userinfo::getUsername, Comparator.nullsLast(Comparator.<String>naturalOrder())));
            for (int i = 0; i < sorted.size(); i++) {
                sorted.get(i).setUserNum(i + 1);
            }
            node.setUserlist(sorted);
        }
        return returlist;
    }


    public Boolean determineMapValue(Map<String, Object> data, String key, Object value) {
        Object valueStr = "";
        for (String k : data.keySet()
        ) {
            if (k.toLowerCase(Locale.ENGLISH).equals(key)) {
                valueStr = data.get(k);
            }
        }
        if (ValidateUtil.isEmpty(valueStr)) {
            return false;
        }
        if (ValidateUtil.isEmpty(value)) {
            return false;
        }
        return value.toString().equals(valueStr.toString());

    }

    public String getMapKey(Map<String, Object> data, String key) {
        String keyStr = "";
        for (String k : data.keySet()
        ) {
            if (k.toLowerCase(Locale.ENGLISH).equals(key)) {
                keyStr = k;
            }
        }
        return keyStr;
    }

    public List<String> getDepartmentIds(List<Department> dList, String cId) {
        List<String> dids = new ArrayList<>();
        List<Department> newDList = dList.stream().filter(n -> n.getdId().equals(cId)).collect(Collectors.toList());
        if (newDList.size() > 0) {
            dids.addAll(getDepartmentSonIds(dList, newDList.get(0).getdId()));

        }
        return dids;
    }

    public List<String> getDepartmentSonIds(List<Department> dList, String cId) {
        List<String> dids = new ArrayList<>();
        List<Department> newDList = dList.stream().filter(n -> n.getdParentid().equals(cId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(newDList)) {
            for (Department atem : newDList) {
                dids.add(atem.getdId());
                dids.addAll(getDepartmentSonIds(dList, atem.getdId()));
            }
        }
        return dids;
    }

    /**
     * 岗位角色配置
     */
    @Override
    public List<userCVo> selectUserByPostRole(List<String> Activityid, FlowActivity item, String pId, String
            customerId) {
        //获取当前节点上设置的所有岗位以及角色信息
        Activityid = Activityid.stream().filter(n -> n.equals(item.getActid())).collect(Collectors.toList());
        List<FlowUser> FlowUsernew = flowUserService.getFlowUserInFlowUserActS(Activityid, customerId);
        List<SysRole> sysRolueList = new ArrayList<>();
        List<SysRole> nsysRolueList = new ArrayList<>();
        //申明岗位用户关系信息集合对象


        //声明岗位角色
        //sysPositionPRoleService

        List<FlowUser> FlowUserr = FlowUsernew.stream().filter(n -> n.getFlowuserrole() != null && n.getFlowuserrole().startsWith("role")).collect(Collectors.toList());
        List<FlowUser> FlowUserd = FlowUsernew.stream().filter(n -> n.getFlowuserrole() != null && n.getFlowuserrole().startsWith("department_position")).collect(Collectors.toList());
        List<String> Plist = new ArrayList<>();
        //角色岗位信息
        List<SysPositionRole> Position_RoleAll = new ArrayList<>();

        List<SysPositionPRoleAndUidVo> pPRList = new ArrayList<>();
        if (FlowUserr.size() > 0) {
            List<String> rIdList = FlowUserr.stream().map(n -> n.getFlowuserrole().replace("role,", "")).collect(Collectors.toList());
            QueryWrapper<SysRole> rolewrapper = new QueryWrapper<>();
            for (Integer i = 0; i < rIdList.size(); i++) {
                if (i == 0) {
                    rolewrapper.eq("Role_ID", rIdList.get(i));
                } else {

                    rolewrapper.or().eq("Role_ID", rIdList.get(i));
                }
            }
            List<SysRole> RoleList = sysRoleService.list(rolewrapper);
            if (RoleList.size() > 0) {
                //系统角色
                sysRolueList = RoleList.stream().filter(n -> n.getRoleType() != null && n.getRoleType().equals("1")).collect(Collectors.toList());
                if (sysRolueList.size() > 0) {
                    //系统角色，只获取项目组织结构人员信息
                    List<String> arIdList = sysRolueList.stream().map(n -> n.getRoleId()).collect(Collectors.toList());
                    //根据角色获取项目组织机构人员信息
                    pPRList = sysPositionPRoleService.getUidByRuleIdAndPid(pId, arIdList, customerId);
                }

                //非系统角色
                nsysRolueList = RoleList.stream().filter(n -> n.getRoleType() != null && n.getRoleType().equals("2")).collect(Collectors.toList());
                if (nsysRolueList.size() > 0) {
                    //非系统角色，只获取组织机构人员信息
                    QueryWrapper<SysPositionRole> PositionRolewrapper = new QueryWrapper<>();
                    for (Integer i = 0; i < nsysRolueList.size(); i++) {
                        if (i == 0) {
                            PositionRolewrapper.eq("Role_ID", nsysRolueList.get(i).getRoleId());
                        } else {
                            PositionRolewrapper.or().eq("Role_ID", nsysRolueList.get(i).getRoleId());
                        }
                    }
                    Position_RoleAll = sysPositionRoleService.list(PositionRolewrapper);
                    if (Position_RoleAll.size() > 0) {
                        List<String> plist = Position_RoleAll.stream().map(n -> n.getpId()).collect(Collectors.toList());
                        Plist.addAll(plist);
                    }
                }
            }
        }


        //岗位用户信息
        List<SysPositionUser> Position_UserAll = new ArrayList<>();
        if (FlowUserd.size() > 0) {
            List<String> dIdList = FlowUserd.stream().map(n -> n.getFlowuserrole().replace("department_position,", "")).collect(Collectors.toList());
            Plist.addAll(dIdList);
        }
        List<String> newplist = Plist.stream().distinct().collect(Collectors.toList());
        QueryWrapper<SysPositionUser> Positionwrapper = new QueryWrapper<>();
        if (newplist.size() > 0) {
            for (Integer i = 0; i < newplist.size(); i++) {
                if (i == 0) {
                    Positionwrapper.eq("P_ID", newplist.get(i));
                } else {
                    Positionwrapper.or().eq("P_ID", newplist.get(i));
                }
            }
            Position_UserAll = sysPositionUserService.list(Positionwrapper);
        }
        //人员信息获取完成

        List<userCVo> uidlist = new ArrayList<>();
        ///获取岗位所属部门信息
        if (!CollectionUtils.isEmpty(Position_UserAll)) {

        }
        if (!CollectionUtils.isEmpty(pPRList)) {
            List<String> pids = pPRList.stream().map(n -> n.getPpId()).collect(Collectors.toList());
        }
        //开始进行条件顾虑
        if (item.getActusertype().equals("OR")) {
            //并集，直接返回查询到的用户信息
            if (!CollectionUtils.isEmpty(Position_UserAll)) {
                List<String> pids = Position_UserAll.stream().map(n -> n.getpId()).collect(Collectors.toList());
                QueryWrapper<SysPosition> positionwrapper1 = new QueryWrapper<>();
                positionwrapper1.in("P_ID", pids);
                List<SysPosition> pList = sysPositionService.list(positionwrapper1);
                for (SysPositionUser n : Position_UserAll
                ) {

                    userCVo o = new userCVo();
                    o.setuId(n.getuId());
                    o.setpId(n.getpId());
                    log.error("根据角色获取的用户信息：" + n.getuId());
                    if (!CollectionUtils.isEmpty(pList)) {
                        log.error("根据角色获取的用户信息:有岗位");
                        List<SysPosition> p1List = pList.stream().filter(k -> k.getpId().equals(n.getpId())).collect(Collectors.toList());
                        log.error("根据角色获取的用户信息:有岗位：" + p1List.size());
                        if (!CollectionUtils.isEmpty(p1List)) {
                            log.error("根据角色获取的用户信息:有岗位：" + p1List.get(0).getdId());
                            o.setdId(p1List.get(0).getdId());
                        }
                    }
                    uidlist.add(o);

                }
            }
            if (pPRList.size() > 0) {
                for (SysPositionPRoleAndUidVo n : pPRList
                ) {
                    userCVo o = new userCVo();
                    o.setuId(n.getuId());
                    o.setRoleId(n.getRoleId());
                    o.setDpId(n.getdId());
                    uidlist.add(o);
                }
                ;
            }
        } else {
            //交集，返回每个每个勾选项都存在的用户
            if (Position_UserAll.size() > 0 || pPRList.size() > 0) {
                List<FlowUser> FlowUser_list = FlowUsernew.stream().filter(n -> n.getFlowuseract().equals(item.getActid())).collect(Collectors.toList());
                List<userCVo> uList = new ArrayList<>();
                Integer number = 0;
                for (FlowUser atem : FlowUser_list) {
                    //判断节点所选择人员是否为空
                    if (!ValidateUtil.isEmpty(atem.getFlowuserrole())) {
                        //不为空
                        //判断是岗位，还是角色
                        if (atem.getFlowuserrole().startsWith("department_position")) {

                            String dId = atem.getFlowuserrole().replace("department_position,", "");
                            //获取当前部门下的所有人员信息，同时根据前置业务进行判断
                            if (number > 0) {
                                List<userCVo> uLists = uList;
                                List<String> pids = Position_UserAll.stream().map(n -> n.getpId()).collect(Collectors.toList());
                                QueryWrapper<SysPosition> positionwrapper1 = new QueryWrapper<>();
                                positionwrapper1.in("P_ID", pids);
                                List<SysPosition> pList = sysPositionService.list(positionwrapper1);
                                for (SysPositionUser n : Position_UserAll
                                ) {
                                    if (n.getpId().equals(dId) && uLists.contains(n.getuId())) {
                                        userCVo o = new userCVo();
                                        o.setuId(n.getuId());
                                        o.setpId(n.getpId());
                                        if (!CollectionUtils.isEmpty(pList)) {
                                            List<SysPosition> p1List = pList.stream().filter(k -> k.getpId().equals(n.getpId())).collect(Collectors.toList());
                                            if (!CollectionUtils.isEmpty(p1List)) {
                                                o.setdId(p1List.get(0).getdId());
                                            }
                                        }
                                        uLists.add(o);
                                    }
                                }
                                ;

                            } else {
                                uList = new ArrayList<>();
                                List<String> pids = Position_UserAll.stream().map(n -> n.getpId()).collect(Collectors.toList());
                                QueryWrapper<SysPosition> positionwrapper1 = new QueryWrapper<>();
                                positionwrapper1.in("P_ID", pids);
                                List<SysPosition> pList = sysPositionService.list(positionwrapper1);
                                for (SysPositionUser n : Position_UserAll
                                ) {
                                    if (n.getpId().equals(dId)) {
                                        userCVo o = new userCVo();
                                        o.setuId(n.getuId());
                                        o.setpId(n.getpId());
                                        if (!CollectionUtils.isEmpty(pList)) {
                                            List<SysPosition> p1List = pList.stream().filter(k -> k.getpId().equals(n.getpId())).collect(Collectors.toList());
                                            if (!CollectionUtils.isEmpty(p1List)) {
                                                o.setdId(p1List.get(0).getdId());
                                            }
                                        }
                                        uList.add(o);
                                    }
                                }
                            }
                            number = number + 1;
                        } else {
                            String rId = atem.getFlowuserrole().replace("role,", "");
                            //根据角色获取部门信息
                            List<String> Position_Roleobj = Position_RoleAll.
                                    stream().filter(n -> n.getRoleId().equals(rId)).map(SysPositionRole::getpId).collect(Collectors.toList());
                            //组织结构人员信息查询
                            List<SysRole> sysRolueobj = sysRolueList.stream().filter(n -> n.getRoleId().equals(rId)).collect(Collectors.toList());
                            List<SysRole> nsysRolueobj = nsysRolueList.stream().filter(n -> n.getRoleId().equals(rId)).collect(Collectors.toList());
                            if (Position_Roleobj.size() > 0) {
                                if (number > 0) {
                                    List<userCVo> uLists = uList;
                                    //根据部门获取人员信息
                                    List<String> pids = Position_UserAll.stream().map(n -> n.getpId()).collect(Collectors.toList());
                                    QueryWrapper<SysPosition> positionwrapper1 = new QueryWrapper<>();
                                    positionwrapper1.in("P_ID", pids);
                                    List<SysPosition> pList = sysPositionService.list(positionwrapper1);
                                    for (SysPositionUser n : Position_UserAll
                                    ) {
                                        if (Position_Roleobj.contains(n.getpId()) && uLists.contains(n.getPuId())) {
                                            userCVo o = new userCVo();
                                            o.setuId(n.getuId());
                                            o.setpId(n.getpId());
                                            if (!CollectionUtils.isEmpty(pList)) {
                                                List<SysPosition> p1List = pList.stream().filter(k -> k.getpId().equals(n.getpId())).collect(Collectors.toList());
                                                if (!CollectionUtils.isEmpty(p1List)) {
                                                    o.setdId(p1List.get(0).getdId());
                                                }
                                            }
                                            uLists.add(o);
                                        }
                                    }
                                    ;

                                } else {
                                    //根据角色获取部门信息
                                    //根据部门获取人员信息
                                    List<String> pids = Position_UserAll.stream().map(n -> n.getpId()).collect(Collectors.toList());
                                    QueryWrapper<SysPosition> positionwrapper1 = new QueryWrapper<>();
                                    positionwrapper1.in("P_ID", pids);
                                    List<SysPosition> pList = sysPositionService.list(positionwrapper1);

                                    for (SysPositionUser n : Position_UserAll
                                    ) {
                                        if (Position_Roleobj.contains(n.getpId())) {
                                            userCVo o = new userCVo();
                                            o.setuId(n.getuId());
                                            o.setpId(n.getpId());
                                            if (!CollectionUtils.isEmpty(pList)) {
                                                List<SysPosition> p1List = pList.stream().filter(k -> k.getpId().equals(n.getpId())).collect(Collectors.toList());
                                                if (!CollectionUtils.isEmpty(p1List)) {
                                                    o.setdId(p1List.get(0).getdId());
                                                }
                                            }
                                            uList.add(o);
                                        }
                                    }
                                    ;
                                }
                            } else {
                                if (nsysRolueobj.size() > 0) {
                                    uList = new ArrayList<>();
                                }
                            }

                            //项目组织机构人员信息查询
                            if (pPRList.size() > 0) {
                                if (number > 0) {
                                    List<userCVo> uLists = uList;
                                    for (SysPositionPRoleAndUidVo n : pPRList
                                    ) {
                                        if (n.getRoleId().equals(rId) && uLists.contains(n.getuId())) {
                                            userCVo o = new userCVo();
                                            o.setuId(n.getuId());
                                            o.setRoleId(n.getRoleId());
                                            o.setDpId(n.getdId());
                                            uidlist.add(o);
                                        }
                                    }
                                    ;
                                } else {
                                    uList = new ArrayList<>();
                                    for (SysPositionPRoleAndUidVo n : pPRList
                                    ) {
                                        if (n.getRoleId().equals(rId)) {
                                            userCVo o = new userCVo();
                                            o.setuId(n.getuId());
                                            o.setRoleId(n.getRoleId());
                                            o.setDpId(n.getdId());
                                            uList.add(o);
                                        }
                                    }
                                    ;
                                }
                            } else {
                                if (sysRolueobj.size() > 0) {
                                    uList = new ArrayList<>();
                                }
                            }
                            number = number + 1;
                        }
                    }
                }
                uidlist = uList;
            }
        }
        return uidlist;
    }

    /**
     * 前序节点办理人
     */
    private List<userCVo> getPreStepApprover(String FileId, FlowActivity
            item, List<FlowActivity> FlowActivitys, List<Map<String, Object>> TaskProcessinPojolist) {
        List<userCVo> u_idlist = new ArrayList<>();
        //获取上一步节点
        List<FlowActivity> list = FlowActivitys.stream().filter(n -> n.getActcode().equals(item.getActcoderole())).collect(Collectors.toList());
        if (list.size() > 0) {
            List<String> actIds = list.stream().map(n -> n.getActid()).collect(Collectors.toList());
            List<Map<String, Object>> TaskProcessinlist = TaskProcessinPojolist.stream().filter(n -> ((n.get("tp_entityid") != null && n.get("tp_entityid").equals(FileId)) && (n.get("tp_actid") != null && actIds.contains(n.get("tp_actid")))) || ((n.get("TP_ENTITYID") != null && n.get("TP_ENTITYID").equals(FileId)) && (n.get("TP_ACTID") != null && actIds.contains(n.get("TP_ACTID"))))).collect(Collectors.toList());
            if (TaskProcessinlist.size() > 0) {
                Map<String, Object> TaskProcessinobj = TaskProcessinlist.get(0);
                userCVo o = new userCVo();
                o.setuId(TaskProcessinobj.get("tp_user") == null ? TaskProcessinobj.get("TP_USER").toString() : TaskProcessinobj.get("tp_user").toString());
                u_idlist.add(o);
            }
        }
        return u_idlist;
    }


    /**
     * getNextActivities
     * 流程用户自由选择
     */
    private List<userCVo> getUserByDid(String dId, String customerId) {
        //获取所有的部门信息
        List<Department> dObjes = departmentService.getDepartmentAll(customerId);
        //获取当前部门下的所有部门id
        List<String> returnData = new ArrayList<>();
        returnData.add(dId);
        returnData.addAll(getDids(dObjes, dId));
        List<userCVo> lis = sysPositionUserService.getPositionUserIdbydIds(returnData);
        return lis;
    }

    public List<String> getDids(List<Department> allList, String newId) {
        List<String> returnData = new ArrayList<>();
        List<Department> newList = allList.stream().filter(d -> d.getdParentid().equals(newId)).collect(Collectors.toList());
        for (Department d : newList) {
            returnData.add(d.getdId());
            returnData.addAll(getDids(allList, d.getdId()));
        }

        return returnData;
    }

    /**
     * 当前审批人的部门所有人
     */
    private List<userCVo> getDepartmentPersonnel(String userid, String customerId) {
        //岗位信息
        List<userCVo> PositionAllList = sysPositionUserService.getPositionUserIdbyuid(userid, customerId);
        return PositionAllList;
    }

    /**
     * 当前所属项目主管部门所有人
     */
    private List<userCVo> getUserIdByPid(String pId, String customerId) {
        //岗位信息
        List<userCVo> PositionAllList = sysPositionUserService.getUserIdByPid(pId, customerId);
        return PositionAllList;
    }

    @Override
    public FlowActivity getStarActivity(List<FlowActivity> flowActivityList, List<FlowRule> FlowRules, String
            customerId) {
        if (CollectionUtils.isEmpty(flowActivityList)) {
            return null;
        }
        if (flowActivityList.stream().filter(n -> "startEvent".equals(n.getActtype())).collect(Collectors.toList()).size() > 0) {


            FlowActivity FlowActivityobjs = flowActivityList.stream().filter(n -> "startEvent".equals(n.getActtype())).findFirst().get();
            //获取当前流程下的所有流程几点信息
            List<FlowActivity> FlowActivitys = flowActivityList.stream().filter(n -> n.getActflowid().equals(FlowActivityobjs.getActflowid())).collect(Collectors.toList());
            //获取当前流程节点的下级的所有流程节点
            List<FlowActivity> FlowRuleList = GetSYSFlowActivityEntitybind(FlowActivityobjs, new HashMap<>(), FlowActivitys, FlowRules, 0, customerId);
            if (FlowRuleList.size() > 0) {
                return FlowRuleList.stream().sorted(Comparator.comparing(FlowActivity::getActname).reversed()).findFirst().get();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public List<FlowActivity> getFlowActivityByActFlowID(String ActFlowID, String customerId) {
        QueryWrapper<FlowActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("ActFlowID", ActFlowID);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<FlowActivity> GetSYSFlowActivityEntitybind(FlowActivity
                                                                   new_obj, Map<String, Object> Reverseconditions, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, Integer
                                                                   neglect, String customerId) {
        if (new_obj == null) {
            return null;
        }
        List<FlowActivity> list_new = new ArrayList<>();
        //获取当前流程节点的下级连接线
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(new_obj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        int number_str = 0;
        for (FlowRule item : FlowRuleList) {
            String newRuleCondition = item.getRulecondition();
            Boolean continuestr = false;
            if (neglect == 0) {
                //不忽略条件
                if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions)) {
                    continuestr = true;
                } else {
                    log.error("------线上条件未满足------");
                    log.info("线上条件:" + item.getRulecondition());
                    log.info("内置条件:" + JacksonUtils.toJSONString(Reverseconditions));
                }
            } else if (neglect == 1) {
                //只走第一次满足条件的
                if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions) && number_str == 0) {
                    number_str = number_str + 1;
                    continuestr = true;
                }
            } else {
                //忽略条件
                continuestr = true;
            }
            if (continuestr) {

                FlowActivity newactivity = FlowActivitys.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).findFirst().get();
                String ActTypestr = newactivity.getActtype();//获取子节点的节点类型
                if (ActTypestr.equals("exclusiveGateway")) {
                    //排他网关
                    //只走第一次满足条件的流出线
                    number_str = number_str + 1;
                    list_new.addAll(GetSYSFlowActivityEntitybind(newactivity, Reverseconditions, FlowActivitys, FlowRules, 1, customerId));
                } else if (ActTypestr.equals("parallelGateway")) {
                    //并行网关
                    //并行网关， 用来在流转中实现无条件的聚合分支决策和发散分支决策。当流程执行到这个网关，所有流出的迁移线条件都会被忽略，
                    //只有全部流入的分支都到达时才会触发发散分支行为。所有流出的迁移线都会被选中。让流程沿着所有流出的迁移线并行运行
                    list_new.addAll(GetSYSFlowActivityEntitybind(newactivity, Reverseconditions, FlowActivitys, FlowRules, 2, customerId));
                } else if (ActTypestr.equals("inclusiveGateway")) {
                    //包容网关
                    //根据流出的线进行条件判断，所有满足条件的线都会走
                    list_new.addAll(GetSYSFlowActivityEntitybind(newactivity, Reverseconditions, FlowActivitys, FlowRules, 0, customerId));
                } else {
                    //不是网关
                    list_new.add(newactivity);
                }
            }
        }
        return list_new;
    }

    @Override
    public List<Map<String, Object>> GetSYSFlowActivityEntitybindEnd(FlowActivity
                                                                             new_obj, Map<String, Object> Reverseconditions, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, Integer
                                                                             neglect, String customerId) {
        if (new_obj == null) {
            return null;
        }
        List<Map<String, Object>> list_new = new ArrayList<>();
        //获取当前流程节点的下级连接线
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(new_obj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        int number_str = 0;
        for (FlowRule item : FlowRuleList) {
            String newRuleCondition = item.getRulecondition();
            Boolean continuestr = false;
            if (neglect == 0) {
                //不忽略条件
                if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions)) {
                    continuestr = true;
                }
            } else if (neglect == 1) {
                //只走第一次满足条件的
                if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions) && number_str == 0) {
                    number_str = number_str + 1;
                    continuestr = true;
                }
            } else {
                //忽略条件
                continuestr = true;
            }
            if (continuestr) {
                FlowActivity newactivity = FlowActivitys.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).findFirst().get();
                String ActTypestr = newactivity.getActtype();//获取子节点的节点类型
                if (ActTypestr.equals("exclusiveGateway")) {
                    //排他网关
                    //只走第一次满足条件的流出线
                    number_str = number_str + 1;
                    Map<String, Object> returnObj = new HashMap<>();
                    returnObj.put("act", newactivity);
                    returnObj.put("rul", item);
                    list_new.add(returnObj);
                    list_new.addAll(GetSYSFlowActivityEntitybindEnd(newactivity, Reverseconditions, FlowActivitys, FlowRules, 1, customerId));
                } else if (ActTypestr.equals("parallelGateway")) {
                    //并行网关
                    //并行网关， 用来在流转中实现无条件的聚合分支决策和发散分支决策。当流程执行到这个网关，所有流出的迁移线条件都会被忽略，
                    //只有全部流入的分支都到达时才会触发发散分支行为。所有流出的迁移线都会被选中。让流程沿着所有流出的迁移线并行运行
                    Map<String, Object> returnObj = new HashMap<>();
                    returnObj.put("act", newactivity);
                    returnObj.put("rul", item);
                    list_new.add(returnObj);
                    list_new.addAll(GetSYSFlowActivityEntitybindEnd(newactivity, Reverseconditions, FlowActivitys, FlowRules, 2, customerId));
                } else if (ActTypestr.equals("inclusiveGateway")) {
                    //包容网关
                    //根据流出的线进行条件判断，所有满足条件的线都会走
                    Map<String, Object> returnObj = new HashMap<>();
                    returnObj.put("act", newactivity);
                    returnObj.put("rul", item);
                    list_new.add(returnObj);
                    list_new.addAll(GetSYSFlowActivityEntitybindEnd(newactivity, Reverseconditions, FlowActivitys, FlowRules, 0, customerId));
                } else {
                    //不是网关

                    if (!newactivity.getActtype().equals("endEvent")) {
                        Map<String, Object> returnObj = new HashMap<>();
                        returnObj.put("act", newactivity);
                        returnObj.put("rul", item);
                        list_new.add(returnObj);
                        list_new.addAll(GetSYSFlowActivityEntitybindEnd(newactivity, Reverseconditions, FlowActivitys, FlowRules, 0, customerId));
                    }
                }
            }
        }
        return list_new;
    }

    @Override
    public Map<String, String> GetSYSFlowActivityProcess(FlowActivity
                                                                 new_obj, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, Map<String, String> data, String
                                                                 customerId) {
        List<FlowActivity> Activitylist_new = JacksonUtils.parseObject(data.get("new_Activity"), new TypeReference<List<FlowActivity>>() {
        });
        List<FlowRule> Rulelist_new = JacksonUtils.parseObject(data.get("new_FlowRule"), new TypeReference<List<FlowRule>>() {
        });
        List<FlowActivity> list_new = new ArrayList<>();
        //获取当前流程节点的下级连接线
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(new_obj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        for (FlowRule item : FlowRuleList) {
            FlowActivity newactivity = FlowActivitys.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).findFirst().get();
            String ActTypestr = newactivity.getActtype();//获取子节点的节点类型
            Rulelist_new.add(item);
            if (ActTypestr.equals("userTask")) {
                Activitylist_new.add(newactivity);
            } else {
                Map<String, String> data_new = new HashMap<>();
                data_new.put("new_Activity", JacksonUtils.toJSONString(Activitylist_new));
                data_new.put("new_FlowRule", JacksonUtils.toJSONString(Rulelist_new));
                Map<String, String> data1 = GetSYSFlowActivityProcess(newactivity, FlowActivitys, FlowRules, data_new, customerId);
                List<FlowActivity> Activitylist_newn = JacksonUtils.parseObject(data1.get("new_Activity"), new TypeReference<List<FlowActivity>>() {
                });
                List<FlowRule> Rulelist_newn = JacksonUtils.parseObject(data1.get("new_FlowRule"), new TypeReference<List<FlowRule>>() {
                });
                Activitylist_new.addAll(Activitylist_newn);
                Rulelist_new.addAll(Rulelist_newn);
            }
        }
        Map<String, String> returndata = new HashMap<>();
        returndata.put("new_Activity", JacksonUtils.toJSONString(Activitylist_new));
        returndata.put("new_FlowRule", JacksonUtils.toJSONString(Rulelist_new));
        return returndata;
    }


    @Override
    public Map<String, String> Getflowline(FlowActivity
                                                   Starobj, List<Task> Tasklist, List<TaskProcessin> taskProcessinList, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, String
                                                   customerId) {
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(Starobj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        int number = 0;
        List<TaskProcessin> TaskProcessin_new = new ArrayList<>();
        List<FlowRule> FlowRulelist = new ArrayList<>();
        List<String> rulestr = taskProcessinList.stream().map(n -> n.getTpRuleid()).collect(Collectors.toList());
        rulestr.addAll(Tasklist.stream().map(n -> n.getTaskrule()).collect(Collectors.toList()));
        List<String> rulesidlist = FlowRuleList.stream().map(n -> n.getRuleendactid()).collect(Collectors.toList());
        List<FlowActivity> list = FlowActivitys.stream().filter(n -> rulesidlist.contains(n.getActid())).collect(Collectors.toList());
        List<String> Actityidlist = list.stream().map(n -> n.getActid()).collect(Collectors.toList());
        List<Task> Tasklistall = Tasklist.stream().filter(n -> n.getTaskcurrentactid() != null && Actityidlist.contains(n.getTaskcurrentactid())).collect(Collectors.toList());
        for (FlowActivity newactivity : list) {
            FlowRule item = FlowRuleList.stream().filter(n -> n.getRuleendactid().equals(newactivity.getActid())).findFirst().get();

            String ActTypestr = newactivity.getActtype();//获取子节点的节点类型
            List<TaskProcessin> taskactity = taskProcessinList.stream().filter(n -> n.getTpActid() != null && n.getTpActid().equals(newactivity.getActid())).collect(Collectors.toList());
            if (ActTypestr.equals("userTask")) {
                int k = 0;
                List<Task> tasklist = Tasklistall.stream().filter(n -> n.getTaskcurrentactid() != null && n.getTaskcurrentactid().equals(newactivity.getActid())).collect(Collectors.toList());

                if (taskactity.size() > 0 || tasklist.size() > 0) {
                    if (rulestr.contains(item.getRuleid())) {
                        FlowRulelist.add(item);
                    }
                    number = number + 1;
                }
                if (taskactity.size() > 0 && tasklist.size() == 0) {
                    TaskProcessin_new.addAll(taskactity);
                    if (Tasklistall.size() == 0) {
                        Map<String, String> data1 = Getflowline(newactivity, Tasklist, taskProcessinList, FlowActivitys, FlowRules, customerId);
                        List<TaskProcessin> TaskProcessinlist = JacksonUtils.parseObject(data1.get("TaskProcessin_new"), new TypeReference<List<TaskProcessin>>() {
                        });
                        List<FlowRule> FlowRulePojo_new = JacksonUtils.parseObject(data1.get("FlowRulePojo_new"), new TypeReference<List<FlowRule>>() {
                        });
                        Integer return_task = Integer.parseInt(data1.get("return_task"));
                        if (return_task > 0) {
                            FlowRulelist.addAll(FlowRulePojo_new);
                            TaskProcessin_new.addAll(TaskProcessinlist);
                        }
                    } else {
                        //过滤审批条件
                        TaskProcessin newtask = taskactity.stream().sorted(Comparator.comparing(TaskProcessin::getTpDotime)).collect(Collectors.toList()).get(taskactity.size() - 1);
                        List<TaskProcessin> newtasklist = taskProcessinList.stream().filter(n -> n.getTpDotime().getTime() > newtask.getTpDotime().getTime()).collect(Collectors.toList());
                        if (newtasklist.size() > 0) {
                            Map<String, String> data1 = Getflowline(newactivity, Tasklist, newtasklist, FlowActivitys, FlowRules, customerId);
                            List<TaskProcessin> TaskProcessinlist = JacksonUtils.parseObject(data1.get("TaskProcessin_new"), new TypeReference<List<TaskProcessin>>() {
                            });
                            List<FlowRule> FlowRulePojo_new = JacksonUtils.parseObject(data1.get("FlowRulePojo_new"), new TypeReference<List<FlowRule>>() {
                            });
                            Integer return_task = Integer.parseInt(data1.get("return_task"));
                            if (return_task > 0) {
                                FlowRulelist.addAll(FlowRulePojo_new);
                                TaskProcessin_new.addAll(TaskProcessinlist);
                            }
                        }
                    }
                }
            } else {
                TaskProcessin_new.addAll(taskactity);
                Map<String, String> data1 = Getflowline(newactivity, Tasklist, taskProcessinList, FlowActivitys, FlowRules, customerId);
                List<TaskProcessin> TaskProcessinlist = JacksonUtils.parseObject(data1.get("TaskProcessin_new"), new TypeReference<List<TaskProcessin>>() {
                });
                List<FlowRule> FlowRulePojo_new = JacksonUtils.parseObject(data1.get("FlowRulePojo_new"), new TypeReference<List<FlowRule>>() {
                });
                Integer return_task = Integer.parseInt(data1.get("return_task"));
                if (return_task > 0) {
                    FlowRulelist.add(item);
                    FlowRulelist.addAll(FlowRulePojo_new);
                    TaskProcessin_new.addAll(taskactity);
                    number = number + 1;
                    TaskProcessin_new.addAll(TaskProcessinlist);
                }
            }
        }
        Map<String, String> returndata = new HashMap<>();
        returndata.put("TaskProcessin_new", JacksonUtils.toJSONString(TaskProcessin_new));
        returndata.put("FlowRulePojo_new", JacksonUtils.toJSONString(FlowRulelist));
        returndata.put("return_task", number + "");
        return returndata;
    }


    @Override
    public Map<String, String> GetSYSFlowActivityEntitysubmit(FlowActivity
                                                                      new_obj, Map<String, Object> Reverseconditions, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, List<TaskProcessin> task_Processinlist, List<userselectionDto> selectactivity, TaskProcessin
                                                                      his, Flow flow, Task old_task, String explain
            , Map<String, String> data, String taskname, List<Task> nowTask, String customerId, String
                                                                      requestSource) {
        if (new_obj == null) {
            return null;
        }
        List<TaskProcessin> Task_Processinreturn = JacksonUtils.parseObject(data.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
        });
        List<String> massage = JacksonUtils.parseObject(data.get("massage"), List.class);
        Integer identification = Integer.parseInt(data.get("status_str"));
        Integer addsSecond=0;
        if(ValidateUtil.isNotEmpty(data.get("addsSecond"))){
            addsSecond = Integer.parseInt(data.get("addsSecond"));
        }

        List<Task> list_new = JacksonUtils.parseObject(data.get("new_task"), new TypeReference<List<Task>>() {
        });
        List<String> needDeleteTaskId = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(data.get("needDeleteTaskId"))) {
            needDeleteTaskId = JacksonUtils.parseObject(data.get("needDeleteTaskId"), new TypeReference<List<String>>() {
            });
        }
        //获取当前流程节点的下级连接线
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(new_obj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        //判断当前节点是否是强制强制设置设置了，强制并发或者强制合并
        String oldActTypestr = new_obj.getActtype();
        int neglect = 0;
        if (oldActTypestr.equals("exclusiveGateway"))
            neglect = 1;
        else if (oldActTypestr.equals("parallelGateway"))
            neglect = 2;
        else if (oldActTypestr.equals("inclusiveGateway"))
            neglect = 0;
        else
            neglect = 0;
        List<FlowActivity> currentlist = new ArrayList<>();
        int statuso = 0;//满足条件的数量；
        int statusn = 0;//实际满足条件的数量
        int number_str = 0;
        List<FlowRule> okRule = new ArrayList<>();
        for (FlowRule item : FlowRuleList) {
            String newRuleCondition = item.getRulecondition();
            Boolean continuestr = false;
            FlowSubmitRules.setSYSFlowRuleEntity(item);

            if (neglect == 0) {
                //不忽略条件
                if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions) && FlowSubmitRules.CheckSysCondition(item.getRulecondition())) {
                    continuestr = true;
                    statuso = statuso + 1;
                } else {
                    if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions) && item.getRulecondition().contains("【task】")) {
                        //
                        statusn = statusn + 1;
                        statuso = statuso + 1;
                    }
                }
            } else if (neglect == 1) {
                //只走第一次满足条件的
                if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions) && FlowSubmitRules.CheckSysCondition(item.getRulecondition()) && number_str == 0) {
                    number_str = number_str + 1;
                    continuestr = true;
                    statuso = statuso + 1;
                } else {
                    if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions) && item.getRulecondition().contains("【task】")) {
                        //
                        statusn = statusn + 1;
                        statuso = statuso + 1;
                    }
                }
            } else {
                //忽略条件
                continuestr = true;
                statuso = statuso + 1;
            }
            if (continuestr) {
                FlowActivity newactivity = FlowActivitys.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).findFirst().get();

                //计算虚拟的任务信息
                Task task_new = taskService.GetNewTaskobj(item, his, old_task, flow, old_task.getTaskuser(), newactivity.getActhour() + "", taskname, customerId);
                Float Actityhour = null;
                if (newactivity.getActhour() != null) {
                    Actityhour = Float.parseFloat(newactivity.getActhour() + "");
                }
                TaskProcessin his_new = taskProcessinService.GetHis(task_new, "同意", explain, old_task.getTaskuser(), "提交", flow, task_new.getTaskuser(), newactivity.getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体
                his_new.setTpDotime(DateUtil.dataAddSecond(his_new.getTpDotime(),addsSecond));
                task_Processinlist.add(his_new);
                String ActTypestr = newactivity.getActtype();
                if (ActTypestr.equals("exclusiveGateway")) {
                    //排他网关
                    //只走第一次满足条件的流出线
                    number_str = number_str + 1;
                    int identification_new = 0;
                    List<Task> toGetList = new ArrayList<>();
                    Map<String, String> data1 = GetSYSFlowActivityEntitysubmit(newactivity, Reverseconditions, FlowActivitys, FlowRules, task_Processinlist, selectactivity, his_new, flow, task_new, explain,
                            setMapData(Task_Processinreturn, massage, identification_new, toGetList, needDeleteTaskId,addsSecond+1), taskname, nowTask, customerId, requestSource);
                    Task_Processinreturn = JacksonUtils.parseObject(data1.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
                    });
                    massage = JacksonUtils.parseObject(data1.get("massage"), List.class);
                    identification = Integer.parseInt(data1.get("status_str"));
                    List<Task> new_task = JacksonUtils.parseObject(data1.get("new_task"), new TypeReference<List<Task>>() {
                    });
                    needDeleteTaskId = JacksonUtils.parseObject(data1.get("needDeleteTaskId"), new TypeReference<List<String>>() {
                    });
                    if (identification == 1) {
                        statusn = statusn + identification;
                        //标识下级阶段选择正常，并且满足任务生成条件
                        //当前的时间减1秒
                        Task_Processinreturn.add(his_new);
                        list_new.addAll(new_task);
                    }
                } else if (ActTypestr.equals("parallelGateway")) {
                    //并行网关
                    //并行网关， 用来在流转中实现无条件的聚合分支决策和发散分支决策。当流程执行到这个网关，所有流出的迁移线条件都会被忽略，
                    //只有全部流入的分支都到达时才会触发发散分支行为。所有流出的迁移线都会被选中。让流程沿着所有流出的迁移线并行运行,强制并发，强制合并
                    //获取历史审批记录，判断当前并行网关的流入线都已执行完成
                    int identification_new = 0;
                    List<TaskProcessin> Task_Processin = new ArrayList<>();

                    List<Task> toGetList = new ArrayList<>();
                    Map<String, String> data1 = GetSYSFlowActivityEntitysubmit(newactivity, Reverseconditions, FlowActivitys, FlowRules, task_Processinlist, selectactivity, his_new, flow, task_new, explain,
                            setMapData(Task_Processinreturn, massage, identification_new, toGetList, needDeleteTaskId,addsSecond+1), taskname, nowTask, customerId, requestSource);
                    Task_Processinreturn = JacksonUtils.parseObject(data1.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
                    });
                    massage = JacksonUtils.parseObject(data1.get("massage"), List.class);
                    identification = Integer.parseInt(data1.get("status_str"));
                    List<Task> new_task = JacksonUtils.parseObject(data1.get("new_task"), new TypeReference<List<Task>>() {
                    });
                    needDeleteTaskId = JacksonUtils.parseObject(data1.get("needDeleteTaskId"), new TypeReference<List<String>>() {
                    });
                    if (identification == 1) {
                        statusn = statusn + identification;
                        //标识下级阶段选择正常，并且满足任务生成条件
                        //生成审批记录
                        //强制合并，没有走完则不生成后续待办
                        if (Mandatorymergejudgment(newactivity, FlowActivitys, FlowRules, task_Processinlist, nowTask, customerId)) {
                            Task_Processinreturn.add(his_new);
                            Task_Processinreturn.addAll(Task_Processin);
                            list_new.addAll(new_task);
                        }

                    }
                } else if (ActTypestr.equals("inclusiveGateway")) {
                    //包容网关
                    //根据流出的线进行条件判断，所有满足条件的线都会走
                    int identification_new = 0;

                    List<Task> toGetList = new ArrayList<>();
                    Map<String, String> data1 = GetSYSFlowActivityEntitysubmit(newactivity, Reverseconditions, FlowActivitys, FlowRules, task_Processinlist, selectactivity, his_new, flow, task_new, explain,
                            setMapData(Task_Processinreturn, massage, identification_new, toGetList, needDeleteTaskId,addsSecond+1), taskname, nowTask, customerId, requestSource);
                    Task_Processinreturn = JacksonUtils.parseObject(data1.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
                    });
                    massage = JacksonUtils.parseObject(data1.get("massage"), List.class);
                    identification = Integer.parseInt(data1.get("status_str"));
                    List<Task> new_task = JacksonUtils.parseObject(data1.get("new_task"), new TypeReference<List<Task>>() {
                    });
                    needDeleteTaskId = JacksonUtils.parseObject(data1.get("needDeleteTaskId"), new TypeReference<List<String>>() {
                    });
                    boolean okType = false;
                    List<FlowActivity> returnAct = RecursiveNodeCanreturned(FlowActivitys, FlowRules, newactivity, customerId);
                    List<String> actIds = returnAct.stream().map(k -> k.getActid()).collect(Collectors.toList());
                    if (nowTask.size() == 0) {
                        okType = true;
                    } else {
                        List<Task> task = nowTask.stream().filter(k -> actIds.contains(k.getTaskcurrentactid())).collect(Collectors.toList());
                        if (task.size() == 0) {
                            okType = true;
                        } else {
                            //说明当前相容网关前面纯在未处理的待办
                            if ("1".equals(newactivity.getActGatewayAccess())) {
                                //未审核完成的人，允许通过
                                needDeleteTaskId.addAll(task.stream().map(k -> k.getTaskid()).collect(Collectors.toList()));
                                okType = true;
                            } else {
                                okType = false;
                            }
                        }
                    }
                    if (identification == 1 && okType) {
                        //标识下级阶段选择正常，并且满足任务生成条件
                        statusn = statusn + identification;
                        Task_Processinreturn.add(his_new);
                        list_new.addAll(new_task);
                    } else {
                        //不满足任务生成条件
                    }

                } else {

                    //不是网关
                    currentlist.add(newactivity);
                    okRule.add(item);
                }
            } else {
                //不是最后一步的会签
                if (item.getRulecondition() != null && item.getRulecondition().trim().contains("[task]=0")) {
                    List<FlowActivity> newactivitylist = FlowActivitys.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).collect(Collectors.toList());
                    if (newactivitylist.size() > 0) {
                        //获取当前流程节点的下级连接线
                        List<FlowRule> newFlowRuleList = FlowRules.stream().filter(n -> n.getRuleendactid().equals(newactivitylist.get(0).getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
                        if (newFlowRuleList.size() > 0) {
                            statusn = 1;
                            statuso = 1;
                        }
                    }


                }
            }
        }

        List<String> act_select_list = selectactivity.stream().map(userselectionDto::getActityid).collect(Collectors.toList());
        List<FlowActivity> selectFlowActivity = currentlist.stream().filter(n -> act_select_list.contains(n.getActid())).collect(Collectors.toList());
        statusn = statusn + selectFlowActivity.size();

        if (!CollectionUtils.isEmpty(currentlist) && currentlist.size() == 1 && statusn == 0) {
            //当前节点只有一个，并且没有被选中，往下继续查询一个节点
            List<Task> toGetList = new ArrayList<>();
            Task task_new = taskService.GetNewTaskobj(okRule.get(0), his, old_task, flow, old_task.getTaskuser(), currentlist.get(0).getActhour() + "", taskname, customerId);
            Float Actityhour = null;
            if (currentlist.get(0).getActhour() != null) {
                Actityhour = Float.parseFloat(currentlist.get(0).getActhour() + "");
            }
            TaskProcessin his_new = taskProcessinService.GetHis(task_new, "同意", explain, old_task.getTaskuser(), "提交", flow, task_new.getTaskuser(), currentlist.get(0).getActtype(), Actityhour, customerId, requestSource);       // 历史记录实体
            his_new.setTpDotime(DateUtil.dataAddSecond(his_new.getTpDotime(),addsSecond));
            task_Processinlist.add(his_new);
            int identification_new = 0;
            Map<String, String> data1 = GetSYSFlowActivityEntitysubmit(currentlist.get(0), Reverseconditions, FlowActivitys, FlowRules, task_Processinlist, selectactivity, his_new, flow, task_new, explain,
                    setMapData(Task_Processinreturn, massage, identification_new, toGetList, needDeleteTaskId,addsSecond+1), taskname, nowTask, customerId, requestSource);
            Task_Processinreturn = JacksonUtils.parseObject(data1.get("Task_Processinnewlist"), new TypeReference<List<TaskProcessin>>() {
            });

            massage = JacksonUtils.parseObject(data1.get("massage"), List.class);
            identification = Integer.parseInt(data1.get("status_str"));
            List<Task> new_task = JacksonUtils.parseObject(data1.get("new_task"), new TypeReference<List<Task>>() {
            });
            if (identification == 1) {
                statusn = statusn + identification;
                Task_Processinreturn.add(his_new);
                list_new.addAll(new_task);
            }
        }


        if (statusn > 0 && statuso > 0) {
            if (oldActTypestr.equals("exclusiveGateway")) {//排他网关，只允许走一条
                if (statusn == 1)
                    identification = 1;//只走一条线
                else {
                    String mass_str = "下一步骤只能选择一个！";
                    massage.add(mass_str);
                }
            } else if (oldActTypestr.equals("parallelGateway")) {//并行网关所有的都要走
                if (statusn == statuso)
                    identification = 1;
                else {
                    String mass_str = "的下一步骤必须全选！";
                    massage.add(mass_str);
                }
            } else if (oldActTypestr.equals("inclusiveGateway"))
                identification = 1;//满足条件的都可以走
            else {
                //常规接节点，按照设置的进行判断
                if (ValidateUtil.isEmpty(new_obj.getActallowconcurrency()) || new_obj.getActallowconcurrency().equals("0")) {

                    //禁止并发
                    if (statusn == 1)
                        //只能选择一个节点
                        identification = 1;
                    else {
                        String mass_str = "下一步骤只能选择一个！";
                        massage.add(mass_str);
                    }

                } else if (new_obj.getActallowconcurrency() != null && new_obj.getActallowconcurrency().equals("1"))
                    //允许并发
                    identification = 1;
                else if (new_obj.getActallowconcurrency() != null && new_obj.getActallowconcurrency().equals("2")) {
                    //强制并发
                    if (statusn == statuso)
                        identification = 1;
                    else {
                        String mass_str = "下一步骤必须全选！";
                        massage.add(mass_str);
                    }
                } else
                    identification = 1;
            }

            //判断当前节点的勾选项是否满足条件
            if (identification == 1) {
                //生成新的任务
                for (FlowActivity itrm : selectFlowActivity) {
                    String userid = selectactivity.stream().filter(n -> n.getActityid().equals(itrm.getActid())).findFirst().get().getUserid();
                    if (userid.contains(",")) {
                        String[] userList = userid.split(",");
                        if (userList.length > 0) {
                            for (int i = 0; i < userList.length; i++) {
                                Task taskobj = taskService.GetNewTaskobj(FlowRuleList.stream().filter(n -> n.getRuleendactid().equals(itrm.getActid())).findFirst().get(), his, old_task, flow, userList[i], itrm.getActhour() + "", taskname, customerId);
                                list_new.add(taskobj);
//                                taskobj.setSecId(old_task.getSecId());
//                                taskobj.setContractId(old_task.getContractId());
//                                taskobj.setProjectId(old_task.getProjectId());
                            }
                        }
                    } else {
                        Task taskobj = taskService.GetNewTaskobj(FlowRuleList.stream().filter(n -> n.getRuleendactid().equals(itrm.getActid())).findFirst().get(), his, old_task, flow, userid, itrm.getActhour() + "", taskname, customerId);
//                        taskobj.setSecId(old_task.getSecId());
//                        taskobj.setContractId(old_task.getContractId());
//                        taskobj.setProjectId(old_task.getProjectId());
                        list_new.add(taskobj);
                    }

                }
            }
        } else
            massage.add("请正确选择下一步经办人！");

        //返回需要生成的任务数量
        return setMapData(Task_Processinreturn, massage, identification, list_new, needDeleteTaskId,0);
    }


    @Override
    public List<String> GetSYSFlowActivityEntityGateway(FlowActivity new_obj, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, List<Task> nowTaskList, String customerId, String requestSource) {
        if (new_obj == null) {
            return null;
        }
        List<String> needDeleteTaskId = new ArrayList<>();
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(new_obj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        List<FlowRule> okRule = new ArrayList<>();
        for (FlowRule item : FlowRuleList) {
            FlowActivity newactivity = FlowActivitys.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).findFirst().get();
            String ActTypestr = newactivity.getActtype();
            if (ActTypestr.equals("exclusiveGateway")) {
                //排他网关
                List<String> ids = GetSYSFlowActivityEntityGateway(newactivity, FlowActivitys, FlowRules, nowTaskList, customerId, requestSource);
                if (!CollectionUtils.isEmpty(ids)) {
                    needDeleteTaskId.addAll(ids);
                }
            } else if (ActTypestr.equals("parallelGateway")) {
                //并行网关
                List<String> ids = GetSYSFlowActivityEntityGateway(newactivity, FlowActivitys, FlowRules, nowTaskList, customerId, requestSource);
                if (!CollectionUtils.isEmpty(ids)) {
                    needDeleteTaskId.addAll(ids);
                }
            } else if (ActTypestr.equals("inclusiveGateway")) {
                //包容网关
                //根据流出的线进行条件判断，所有满足条件的线都会走
                List<String> ids = GetSYSFlowActivityEntityGateway(newactivity, FlowActivitys, FlowRules, nowTaskList, customerId, requestSource);
                if (!CollectionUtils.isEmpty(ids)) {
                    needDeleteTaskId.addAll(ids);
                }
                if (!CollectionUtils.isEmpty(nowTaskList)) {
                    List<FlowActivity> returnAct = RecursiveNodeCanreturned(FlowActivitys, FlowRules, newactivity, customerId);
                    List<String> actIds = returnAct.stream().map(k -> k.getActid()).collect(Collectors.toList());
                    List<Task> task = nowTaskList.stream().filter(k -> actIds.contains(k.getTaskcurrentactid())).collect(Collectors.toList());
                    needDeleteTaskId.addAll(task.stream().map(k -> k.getTaskid()).collect(Collectors.toList()));
                }
            }
        }
        return needDeleteTaskId;
    }


    private Map<String, String> setMapData
            (List<TaskProcessin> Task_Processinnewlist, List<String> massage, Integer
                    status_str, List<Task> new_task, List<String> needDeleteTaskId,Integer addSecond) {
        Map<String, String> data = new HashMap<>();
        data.put("Task_Processinnewlist", JacksonUtils.toJSONString(Task_Processinnewlist));
        data.put("massage", JacksonUtils.toJSONString(massage));
        data.put("new_task", JacksonUtils.toJSONString(new_task));
        data.put("status_str", status_str + "");
        data.put("needDeleteTaskId", JacksonUtils.toJSONString(needDeleteTaskId));
        data.put("addsSecond", addSecond+"");
        return data;
    }


    private Boolean Mandatorymergejudgment(FlowActivity
                                                   nowActivity, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, List<TaskProcessin> Task_Processin, List<Task> nowTask, String
                                                   customerId) {

        Boolean status = false;
        //强制合并
        //获取去流程的最新启动时间
        FlowActivity FlowActivityobjs = FlowActivitys.stream().filter(n -> n.getActtype().equals("startEvent")).findFirst().get();
        //获取当前流程下的所有流程几点信息
        //获取当前流程节点的下级的所有流程节点

        List<FlowActivity> FlowRuleList = GetSYSFlowActivityEntitybind(FlowActivityobjs, new HashMap<>(), FlowActivitys, FlowRules, 0, customerId);
        if (FlowRuleList.size() > 0) {
            String act_str = FlowRuleList.stream().sorted(Comparator.comparing(FlowActivity::getActname).reversed()).findFirst().get().getActid();
            List<TaskProcessin> task_pressonobj = Task_Processin.stream().filter(n -> n.getTpActid() != null && n.getTpActid().equals(act_str)).collect(Collectors.toList());
            task_pressonobj = task_pressonobj.stream().sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList());
            if (task_pressonobj.size() > 0) {
                Date start_time = task_pressonobj.get(0).getTpDotime();
                //获取流程的连接线
                List<String> act_idlist = FlowRules.stream().filter(n -> n.getRuleendactid().equals(nowActivity.getActid())).map(FlowRule::getRulebeginactid).collect(Collectors.toList());
                int number_str = Task_Processin.stream().filter(n -> n.getTpDotime().getTime() >= start_time.getTime() && act_idlist.contains(n.getTpActid()) && n.getActname() != null && !n.getActname().equals("【更换流程操作人】") && !n.getTpType().equals("撤回")).collect(Collectors.toList()).size();
                if (number_str < act_idlist.size()) {
                    //当前待办提交数量小于网关的数量，判读是否存在特殊操作
                    List<FlowActivity> returnAct = RecursiveNodeCanreturned(FlowActivitys, FlowRules, nowActivity, customerId);
                    List<String> actIds = returnAct.stream().map(k -> k.getActid()).collect(Collectors.toList());
                    if (nowTask.size() == 0) {
                        return true;
                    } else {
                        List<Task> task = nowTask.stream().filter(k -> actIds.contains(k.getTaskcurrentactid())).collect(Collectors.toList());
                        if (task.size() == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (nowTask.size() != 0) {
                        return false;
                    } else {
                        status = true;
                    }

                }
            } else {
                //没有获取到开始节点
                status = false;
            }
        } else {
            //流程异常，没有找到开始节点
            status = false;
        }
        return status;
    }

    @Override
    public List<FlowActivity> GetFlowActivityList(String key, FlowActivity
            new_obj, Map<String, Object> Reverseconditions, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, Integer
                                                          neglect, String checkType, List<String> checkFieldStr, String customerId, List<String> allActId) {
        if (new_obj == null) {
            return null;
        }
        List<FlowActivity> list_new = new ArrayList<>();
        //获取当前流程节点的下级连接线
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(new_obj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        int number_str = 0;
        for (FlowRule item : FlowRuleList) {
            String newRuleCondition = item.getRulecondition();
            boolean continuestr = false;
            if (neglect == 0) {
                //不忽略条件
                if (checkType.equals("1")) {
                    if (JPMCheckCondition.InspectionFieldConditions(item.getRulecondition(), Reverseconditions, checkFieldStr)) {
                        continuestr = true;
                    }
                } else {
                    if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions)) {
                        continuestr = true;
                    }
                }


            } else if (neglect == 1) {
                //只走第一次满足条件的
                if (checkType.equals("1")) {
                    if (JPMCheckCondition.InspectionFieldConditions(item.getRulecondition(), Reverseconditions, checkFieldStr)) {
                        continuestr = true;
                    }
                } else {
                    if (JPMCheckCondition.CheckCondition(item.getRulecondition(), Reverseconditions) && number_str == 0) {
                        number_str = number_str + 1;
                        continuestr = true;
                    }
                }

            } else {
                //忽略条件
                continuestr = true;
            }
            if (continuestr) {
                List<FlowActivity> actlist = FlowActivitys.stream().filter(n -> n.getActid().equals(item.getRuleendactid())).collect(Collectors.toList());

                if (actlist.size() > 0) {
                    FlowActivity newactivity = actlist.get(0);
                    List<String> idStrs = actListALL.get(key);
                    if (!CollectionUtils.isEmpty(idStrs)) {
                        List<String> tCheck = idStrs.stream().filter(n -> n.equals(newactivity.getActid())).collect(Collectors.toList());
                        if (tCheck.size() > 0) {
                            continue;
                        }
                    } else {
                        idStrs = new ArrayList<>();
                    }
                    if (allActId.contains(newactivity.getActid())) {
                        continue;
                    }
                    allActId.add(newactivity.getActid());
                    String ActTypestr = newactivity.getActtype();//获取子节点的节点类型
                    list_new.add(newactivity);
                    idStrs.add(newactivity.getActid());
                    actListALL.put(key, idStrs);
                    if (ActTypestr.equals("exclusiveGateway")) {
                        //排他网关
                        //只走第一次满足条件的流出线
                        number_str = number_str + 1;
                        list_new.addAll(GetFlowActivityList(key, newactivity, Reverseconditions, FlowActivitys, FlowRules, 1, checkType, checkFieldStr, customerId, allActId));
                    } else if (ActTypestr.equals("parallelGateway")) {
                        //并行网关
                        //并行网关， 用来在流转中实现无条件的聚合分支决策和发散分支决策。当流程执行到这个网关，所有流出的迁移线条件都会被忽略，
                        //只有全部流入的分支都到达时才会触发发散分支行为。所有流出的迁移线都会被选中。让流程沿着所有流出的迁移线并行运行
                        list_new.addAll(GetFlowActivityList(key, newactivity, Reverseconditions, FlowActivitys, FlowRules, 2, checkType, checkFieldStr, customerId, allActId));
                    } else if (ActTypestr.equals("inclusiveGateway")) {
                        //包容网关
                        //根据流出的线进行条件判断，所有满足条件的线都会走
                        list_new.addAll(GetFlowActivityList(key, newactivity, Reverseconditions, FlowActivitys, FlowRules, 0, checkType, checkFieldStr, customerId, allActId));
                    } else {
                        //不是网关

                        list_new.addAll(GetFlowActivityList(key, newactivity, Reverseconditions, FlowActivitys, FlowRules, 0, checkType, checkFieldStr, customerId, allActId));
                    }
                }
            }
        }

        return list_new;
    }

    @Override
    public List<FlowActivity> GetNodeWithPermission(FlowActivity newObj) {
        List<FlowActivity> list = new ArrayList<>();

        return list;
    }


    //逆推递归
    @Override
    public List<FlowActivity> RecursiveNodeCanreturned
    (List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, FlowActivity newObj, String customerId) {
        List<FlowActivity> returnlist = new ArrayList<>();
        if (newObj == null) {
            return null;
        }
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRuleendactid().equals(newObj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        for (FlowRule item : FlowRuleList) {
            FlowActivity newactivity = FlowActivitys.stream().filter(n -> n.getActid().equals(item.getRulebeginactid())).findFirst().get();
            String ActTypestr = newactivity.getActtype();//获取来源节点的节点类型
            if (ActTypestr.equals("exclusiveGateway")) {
                //排他网关
                //只走第一次满足条件的流出线
                List<FlowActivity> returnAct = RecursiveNodeCanreturned(FlowActivitys, FlowRules, newactivity, customerId);
                returnlist.addAll(returnAct);
            } else if (ActTypestr.equals("parallelGateway")) {
                //并行网关
                List<FlowActivity> returnAct = RecursiveNodeCanreturned(FlowActivitys, FlowRules, newactivity, customerId);
                returnlist.addAll(returnAct);

            } else if (ActTypestr.equals("inclusiveGateway")) {
                //包容网关
                List<FlowActivity> returnAct = RecursiveNodeCanreturned(FlowActivitys, FlowRules, newactivity, customerId);
                returnlist.addAll(returnAct);
            } else {
                //不是网关
                returnlist.add(newactivity);
                List<FlowActivity> returnAct = RecursiveNodeCanreturned(FlowActivitys, FlowRules, newactivity, customerId);
                returnlist.addAll(returnAct);
            }
        }
        return returnlist;
    }

    @Override
    public BaseResponse getReturnStatus(String actId, String flowId, String customerId) {
        //获取当前流程的所有节点
        Boolean returnType = false;

        //获取基础配置信息（是否允许原路返回）
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalReturn");
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        Object approvalReturn = smsTemplateList.get("approvalReturn");
        if (approvalReturn != null) {
            if (approvalReturn.toString().equals("1")) {
                //所有节点都可以原路返回
                return new BaseResponse("", true, 0);
            }
        }
        QueryWrapper<FlowActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("ActFlowID", flowId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<FlowActivity> FlowActivitys = baseMapper.selectList(wrapper);
        //获取当前流程下所有的连接线
        List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(flowId, customerId);
        List<FlowRule> FlowRuleNew = FlowRules.stream().filter(n -> n.getRuleendactid() != null && n.getRuleendactid().equals(actId)).collect(Collectors.toList());
        if (FlowRuleNew.size() > 0) {
            List<FlowActivity> FlowActivityNew = FlowActivitys.stream().filter(n -> n.getActid().equals(FlowRuleNew.get(0).getRulebeginactid())).collect(Collectors.toList());
            if (FlowActivityNew.size() > 0) {
                if (FlowActivityNew.get(0).getActtype().equals("userTask")) {
                    returnType = true;
                }
            }
        }
        return new BaseResponse("", returnType, 0);
    }

    //增加线上条件
    @Override
    public Map<String, Object> setTorsionConditions(String fileId, String uId, String conditions, String
            customerId, String tCode, String year) {
        log.info("传入的内置条件：" + conditions);
        Map<String, Object> conditionobj = new HashMap<>();
        if (!ValidateUtil.isEmpty(conditions)) {
            conditionobj = JacksonUtils.parseObject(conditions, Map.class);
        }
        log.info("传入的内置条件1：" + conditionobj);
        String projectId = "";
        String contractId = "";
        String sectionId = "";
        //获取文档信息
        File fileObj = fileService.getFileById(fileId, tCode, year);
        String cereateId = "";
        log.info("File：" + JacksonUtils.toJSONString(fileObj));
        List<String> userIds = new ArrayList<>();

        if (!ValidateUtil.isEmpty(getKey(conditionobj, "conId"))) {
            contractId = getKey(conditionobj, "conId").toString();
        }

        if (!ValidateUtil.isEmpty(getKey(conditionobj, "contractId"))) {
            contractId = getKey(conditionobj, "contractId").toString();
        }


        if (!ValidateUtil.isEmpty(getKey(conditionobj, "pId"))) {
            projectId = getKey(conditionobj, "pId").toString();
        }


        if (!ValidateUtil.isEmpty(getKey(conditionobj, "projectId"))) {
            projectId = getKey(conditionobj, "projectId").toString();
        }


        if (!ValidateUtil.isEmpty(getKey(conditionobj, "secId"))) {
            sectionId = getKey(conditionobj, "secId").toString();
        }


        if (!ValidateUtil.isEmpty(getKey(conditionobj, "sectionId"))) {
            sectionId = getKey(conditionobj, "sectionId").toString();
        }

        if (conditionobj == null) {
            conditionobj = new HashMap<>();
        }
        if (fileObj != null) {
            cereateId = fileObj.getFilecreateuser();
            if (ValidateUtil.isNotEmpty(cereateId)) {
                userIds.add(cereateId);
            }

            if (!ValidateUtil.isEmpty(fileObj.getFileproject())) {
                conditionobj.put("sysPID", fileObj.getFileproject());
                projectId = fileObj.getFileproject();
            }
            if (!ValidateUtil.isEmpty(fileObj.getFilecontract())) {
                conditionobj.put("sysConId", fileObj.getFilecontract());
                contractId = fileObj.getFilecontract();
            }
            if (!ValidateUtil.isEmpty(fileObj.getFilesectionid())) {
                conditionobj.put("sysSecId", fileObj.getFilesectionid());
                sectionId = fileObj.getFilesectionid();
            }
        }

        if (ValidateUtil.isNotEmpty(projectId)) {
            Map<String, Object> pObj = baseMapper.getPreProjectById(projectId);
            if (pObj != null) {
                conditionobj.put("sysPParentID", getKey(pObj, "P_Parent_ID"));
                conditionobj.put("sysPNmae", getKey(pObj, "P_Nmae"));
                conditionobj.put("sysPProjectAbbreviation", getKey(pObj, "P_Project_Abbreviation"));
                conditionobj.put("sysPOriginalName", getKey(pObj, "P_Origina_lName"));
                conditionobj.put("sysPNameInitials", getKey(pObj, "P_Name_Initials"));
                conditionobj.put("sysPProjectCode", getKey(pObj, "P_Project_Code"));
                conditionobj.put("sysPMajorType", getKey(pObj, "P_Major_Type"));
                conditionobj.put("sysPType", getKey(pObj, "P_Type"));
                conditionobj.put("sysPTotalDuration", getKey(pObj, "P_Total_Duration"));
                conditionobj.put("sysPUseUnit", getKey(pObj, "P_Use_Unit"));
                conditionobj.put("sysPIsAgentConstruction", getKey(pObj, "P_Is_Agent_Construction"));
                conditionobj.put("sysPAgentConstructionUnit", getKey(pObj, "P_Agent_ConstructionUnit"));

                conditionobj.put("sysPIsWholeProcessManagement", getKey(pObj, "P_Is_Whole_Process_Management"));
                conditionobj.put("sysPCmMode", getKey(pObj, "P_Cm_Mode"));
                conditionobj.put("sysPNature", getKey(pObj, "P_Nature"));
                conditionobj.put("sysPSuperviseType", getKey(pObj, "P_Supervise_Type"));
                conditionobj.put("sysPGroupType", getKey(pObj, "P_Group_Type"));
                conditionobj.put("sysPBuildModel", getKey(pObj, "P_Build_Model"));
                conditionobj.put("sysPInvTotal", getKey(pObj, "P_Inv_Total"));
                conditionobj.put("sysPApprovalUnit", getKey(pObj, "P_Approval_Unit"));
                conditionobj.put("sysPConstructionUnit", getKey(pObj, "P_Construction_Unit"));
                conditionobj.put("sysPCompetentDepartment", getKey(pObj, "P_Competent_Department"));
                conditionobj.put("sysPProjectDirector", getKey(pObj, "P_Project_Director"));
                conditionobj.put("sysPCategory", getKey(pObj, "P_Category"));

            }
        }


        if (ValidateUtil.isNotEmpty(contractId)) {
            Map<String, Object> conObj = baseMapper.getConContractById(contractId);
            if (conObj != null) {
                conditionobj.put("sysConName", getKey(conObj, "Con_Name"));
                conditionobj.put("sysConCode", getKey(conObj, "Con_Code"));
                conditionobj.put("sysConType", getKey(conObj, "Con_Type"));
                conditionobj.put("sysConMoneyTotal", getKey(conObj, "Con_Money_Total"));
                conditionobj.put("sysConPartyAId", getKey(conObj, "Con_Party_A_Id"));
                conditionobj.put("sysConPartyAOtherId", getKey(conObj, "Con_Party_A_Other_Id"));
                conditionobj.put("sysConPartyBId", getKey(conObj, "Con_Party_B_Id"));
                conditionobj.put("sysConSupervision_Org", getKey(conObj, "Con_Supervision_Org"));
                conditionobj.put("sysConConsortium", getKey(conObj, "Con_Consortium"));
                conditionobj.put("sysConPartyB1", getKey(conObj, "Con_Party_B1"));

                conditionobj.put("sysConPartyB2", getKey(conObj, "Con_Party_B2"));
                conditionobj.put("sysConPartyB3", getKey(conObj, "Con_Party_B3"));
                conditionobj.put("sysConPartyB4", getKey(conObj, "Con_Party_B4"));
                conditionobj.put("sysConPartyB5", getKey(conObj, "Con_Party_B5"));
                conditionobj.put("sysConMultiSubject", getKey(conObj, "Con_Multi_Subject"));
                conditionobj.put("sysConPartyCId", getKey(conObj, "Con_Party_C_Id"));
                conditionobj.put("sysConPartyDId", getKey(conObj, "Con_Party_D_Id"));
                conditionobj.put("sysConSup", getKey(conObj, "Con_Sup"));
                conditionobj.put("sysConBiddingType", getKey(conObj, "Con_Bidding_Type"));
                conditionobj.put("sysConIsParent", getKey(conObj, "Con_Is_Parent"));

                conditionobj.put("sysConIsHistory", getKey(conObj, "Con_Is_History"));
                conditionobj.put("sysConYear", getKey(conObj, "Con_Year"));
                conditionobj.put("sysConBasis", getKey(conObj, "Con_Basis"));
                conditionobj.put("sysConIsBidding", getKey(conObj, "Con_Is_Bidding"));
                conditionobj.put("sysConProfessionalType", getKey(conObj, "Con_Professional_Type"));
            }
        }

        if (ValidateUtil.isNotEmpty(sectionId)) {
            Map<String, Object> secObj = baseMapper.getSecInfoById(sectionId);
            if (secObj != null) {
                conditionobj.put("sysSecName", getKey(secObj, "Sec_Name"));
                conditionobj.put("sysSecCode", getKey(secObj, "Sec_Code"));
                conditionobj.put("sysSecYear", getKey(secObj, "Sec_Year"));
                conditionobj.put("sysSecBiddingType", getKey(secObj, "Sec_Bidding_Type"));
                conditionobj.put("sysSecConType", getKey(secObj, "Sec_Con_Type"));
                conditionobj.put("sysSecType", getKey(secObj, "Sec_Type"));
                conditionobj.put("sysSecMoney", getKey(secObj, "Sec_Money"));
                conditionobj.put("sysSecStartTime", getKey(secObj, "Sec_Start_Time"));
                conditionobj.put("sysSecEndTime", getKey(secObj, "Sec_End_Time"));
                conditionobj.put("sysSecTenderee", getKey(secObj, "Sec_Tenderee"));
                conditionobj.put("sysSecDeterminType", getKey(secObj, "Sec_Determin_Type"));
                conditionobj.put("sysSecPricingMethod", getKey(secObj, "Sec_Pricing_Method"));
                conditionobj.put("sysSecPurchaseType", getKey(secObj, "Sec_Purchase_Type"));
                conditionobj.put("sysSecEvalMethod", getKey(secObj, "Sec_Eval_Method"));
                conditionobj.put("sysSecEvaluateSeparation", getKey(secObj, "Sec_Evaluate_Separation"));
            }
        }


        userIds.add(uId);
        List<UserInfoVo> userList = sysUserService.getPersonnelSettings(userIds);
        log.info("userList：" + JacksonUtils.toJSONString(userList));
        if (userList.size() > 0) {
            for (UserInfoVo userObj : userList) {
                Object nUid = userObj.getuId();
                if (nUid != null) {
                    if (nUid.toString().equals(cereateId)) {
                        //当前用户

                        conditionobj.put("fileUId", userObj.getuId());
                        conditionobj.put("fileUName", userObj.getuName());
                        conditionobj.put("fileUCode", userObj.getuCode());
                        conditionobj.put("fileUType", userObj.getuType());
                        conditionobj.put("fileUAccount", userObj.getuAccount());
                        conditionobj.put("fileUSex", userObj.getuSex());

                        conditionobj.put("fileCId", userObj.getcId());
                        conditionobj.put("fileCName", userObj.getcName());
                        conditionobj.put("fileCCode", userObj.getcCode());
                        conditionobj.put("fileCType", userObj.getcType());
                        conditionobj.put("fileCCreditCode", userObj.getcCreditCode());
                        conditionobj.put("fileCMDMNO", userObj.getcMDMNO());

                        conditionobj.put("fileCSolution", userObj.getcSolution());
                        conditionobj.put("fileCAbbreviation", userObj.getcAbbreviation());
                        conditionobj.put("fileCGroupCode", userObj.getcGroupCode());
                        conditionobj.put("filePoPname", userObj.getPoPname());
                        conditionobj.put("filePoPcode", userObj.getPoPcode());

                        conditionobj.put("filePoPmdmno", userObj.getPoPmdmno());
                        conditionobj.put("fileDDid", userObj.getdDid());
                        conditionobj.put("fileDDname", userObj.getdDname());
                        conditionobj.put("fileDDcode", userObj.getdDcode());
                        conditionobj.put("fileDDparentId", userObj.getdDparentId());

                        conditionobj.put("fileDDsolution", userObj.getdDsolution());
                        conditionobj.put("fileDDcId", userObj.getdDcId());
                        conditionobj.put("fileRdRname", userObj.getRdRname());
                        conditionobj.put("fileRdRcode", userObj.getRdRcode());
                        conditionobj.put("fileRdRtype", userObj.getRdRtype());

                        conditionobj.put("fileRdRsolution", userObj.getRdRsolution());
                        conditionobj.put("filePopPPname", userObj.getPopPPname());
                        conditionobj.put("filePopPPcode", userObj.getPopPPcode());
                        conditionobj.put("filePopPPmdmno", userObj.getPopPPmdmno());
                        conditionobj.put("fileRpRname", userObj.getRpRname());

                        conditionobj.put("fileRpRcode", userObj.getRpRcode());
                        conditionobj.put("fileRpRtype", userObj.getRpRtype());
                        conditionobj.put("fileRpRsolution", userObj.getRpRsolution());
                        conditionobj.put("fileDpPDName", userObj.getDpPDName());
                        conditionobj.put("fileDpPDCode", userObj.getDpPDCode());

                        conditionobj.put("fileDpPDID", userObj.getDpPDID());
                        conditionobj.put("fileDpPDParentID", userObj.getDpPDParentID());
                        conditionobj.put("fileDpPDType", userObj.getDpPDType());
                        conditionobj.put("fileDpPDMDMNO", userObj.getDpPDMDMNO());
                        conditionobj.put("fileDpPID", userObj.getDpPID());
                        conditionobj.put("fileDpPDSolution", userObj.getDpPDSolution());
                    }
                    if (nUid.toString().equals(uId)) {
                        //发起人

                        conditionobj.put("uId", userObj.getuId());
                        conditionobj.put("uName", userObj.getuName());
                        conditionobj.put("uCode", userObj.getuCode());
                        conditionobj.put("uType", userObj.getuType());
                        conditionobj.put("uAccount", userObj.getuAccount());
                        conditionobj.put("uSex", userObj.getuSex());

                        conditionobj.put("cId", userObj.getcId());
                        conditionobj.put("cName", userObj.getcName());
                        conditionobj.put("cCode", userObj.getcCode());
                        conditionobj.put("cType", userObj.getcType());
                        conditionobj.put("cCreditCode", userObj.getcCreditCode());
                        conditionobj.put("cMDMNO", userObj.getcMDMNO());

                        conditionobj.put("cSolution", userObj.getcSolution());
                        conditionobj.put("cAbbreviation", userObj.getcAbbreviation());
                        conditionobj.put("cGroupCode", userObj.getcGroupCode());
                        conditionobj.put("poPname", userObj.getPoPname());
                        conditionobj.put("poPcode", userObj.getPoPcode());

                        conditionobj.put("poPmdmno", userObj.getPoPmdmno());
                        conditionobj.put("dDid", userObj.getdDid());
                        conditionobj.put("dDname", userObj.getdDname());
                        conditionobj.put("dDcode", userObj.getdDcode());
                        conditionobj.put("dDparentId", userObj.getdDparentId());

                        conditionobj.put("dDsolution", userObj.getdDsolution());
                        conditionobj.put("dDcId", userObj.getdDcId());
                        conditionobj.put("rdRname", userObj.getRdRname());
                        conditionobj.put("rdRcode", userObj.getRdRcode());
                        conditionobj.put("rdRtype", userObj.getRdRtype());

                        conditionobj.put("rdRsolution", userObj.getRdRsolution());
                        conditionobj.put("popPPname", userObj.getPopPPname());
                        conditionobj.put("popPPcode", userObj.getPopPPcode());
                        conditionobj.put("popPPmdmno", userObj.getPopPPmdmno());
                        conditionobj.put("rpRname", userObj.getRpRname());

                        conditionobj.put("rpRcode", userObj.getRpRcode());
                        conditionobj.put("rpRtype", userObj.getRpRtype());
                        conditionobj.put("rpRsolution", userObj.getRpRsolution());
                        conditionobj.put("dpPDName", userObj.getDpPDName());
                        conditionobj.put("dpPDCode", userObj.getDpPDCode());

                        conditionobj.put("dpPDID", userObj.getDpPDID());
                        conditionobj.put("dpPDParentID", userObj.getDpPDParentID());
                        conditionobj.put("dpPDType", userObj.getDpPDType());
                        conditionobj.put("dpPDMDMNO", userObj.getDpPDMDMNO());
                        conditionobj.put("dpPID", userObj.getDpPID());
                        conditionobj.put("dpPDSolution", userObj.getDpPDSolution());

                    }
                }
            }
        }

        log.info("返回系统内置条件：" + JacksonUtils.toJSONString(conditionobj));
        return conditionobj;
    }


    //获取系统业务流程办理情况
    @Override
    public BaseResponse getBusinessProcessingProgress(String uId, String customerId, String year) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        QueryWrapper<Flow> flowWrapper = new QueryWrapper<>();
        flowWrapper.eq("T_Code", "ProjectHandlingProgress");// 业务流程
        flowWrapper.eq("FlowEnable", "1");
        if (!"All".equals(customerId)) {
            flowWrapper.eq("Te_Id", customerId);
        }
        List<Flow> flowList = flowService.list(flowWrapper);
        if (flowList.size() > 0) {
            Flow flowObj = flowList.get(0);
            List<FlowActivity> flowActivityList = getFlowActivityByActFlowID(flowObj.getFlowid(), customerId);
            if (flowActivityList.size() > 0) {
                flowActivityList = flowActivityList.stream().sorted(Comparator.comparing(FlowActivity::getActorder)).collect(Collectors.toList());
                List<String> actTemplateCode = flowActivityList.stream().map(n -> n.getActtemplatecode()).collect(Collectors.toList());
                List<String> actBusinessStartNode = flowActivityList.stream().map(n -> n.getActBusinessStartNode()).collect(Collectors.toList());
                List<String> actBusinessEndNode = flowActivityList.stream().map(n -> n.getActBusinessEndNode()).collect(Collectors.toList());

                List<String> allCtode = new ArrayList<>();
                allCtode.addAll(actTemplateCode);
                allCtode.addAll(actBusinessStartNode);
                allCtode.addAll(actBusinessEndNode);
                allCtode = allCtode.stream().filter(n -> !ValidateUtil.isEmpty(n)).collect(Collectors.toList());
                List<Map<String, Object>> fileList = fileService.getFileByCode(allCtode, uId, customerId, year);

                QueryWrapper<FileTemplate> tWrapper = new QueryWrapper<>();
                tWrapper.eq("T_Stats", "1");// 业务流程
                tWrapper.in("T_Code", allCtode);
                if (!"All".equals(customerId)) {
                    tWrapper.eq("Te_Id", customerId);
                }
                List<FileTemplate> fileTemplateList = fileTemplateService.list(tWrapper);
                if (fileList.size() > 0) {
                    List<String> pIds = new ArrayList<>();
                    List<Map<String, Object>> newDate = new ArrayList<>();
                    for (Map<String, Object> data : fileList) {
                        pIds.add(getKey(data, "FileProject").toString());
                        Map<String, Object> obj = new HashMap<>();
                        obj.put("pId", getKey(data, "FileProject"));
                        obj.put("tCode", getKey(data, "tcode"));
                        if (!ValidateUtil.isEmpty(getKey(data, "tcode"))) {
                            newDate.add(obj);
                        }

                    }
                    List<PreProject> preProjectList = preProjectService.getPreProjectList(pIds, customerId);
                    if (preProjectList.size() > 0) {
                        for (PreProject project : preProjectList) {
                            List<Map<String, Object>> nowData = newDate.stream().filter(n -> n.get("pId").toString().equals(project.getpId())).collect(Collectors.toList());
                            if (nowData.size() > 0) {
                                String tCode = nowData.get(0).get("tCode").toString();
                                if (tCode.contains(",")) {
                                    tCode = tCode.split(",")[0];
                                }
                                String newTcode = tCode;
                                List<FlowActivity> actList = flowActivityList.stream().filter(n -> n.getActtemplatecode() != null && n.getActtemplatecode().equals(newTcode)).collect(Collectors.toList());
                                Boolean next = false;
                                int number = 0;
                                String nowTcode = "";
                                if (actList.size() > 0) {
                                    //当前业务节点已办理完成
                                    next = true;
                                    number = actList.get(0).getActorder();
                                    nowTcode = actList.get(0).getActtemplatecode();
                                } else {
                                    //当前可能不是
                                    String newTcodes = tCode;
                                    List<FlowActivity> startActList = flowActivityList.stream().filter(n -> n.getActBusinessStartNode() != null && n.getActBusinessStartNode().equals(newTcodes)).collect(Collectors.toList());
                                    List<FlowActivity> endActList = flowActivityList.stream().filter(n -> n.getActBusinessEndNode() != null && n.getActBusinessEndNode().equals(newTcodes)).collect(Collectors.toList());
                                    if (endActList.size() > 0) {
                                        //业务结束节点已办理完成
                                        next = true;
                                        number = endActList.get(0).getActorder();
                                        nowTcode = endActList.get(0).getActBusinessEndNode();
                                    }
                                }
                                if (next) {
                                    //获取下一步的节点
                                    int newnmber = number;
                                    List<FlowActivity> nextActList = flowActivityList.stream().filter(n -> n.getActorder() != null && n.getActorder() > newnmber).collect(Collectors.toList());
                                    if (nextActList.size() > 0) {
                                        nextActList = nextActList.stream().sorted(Comparator.comparing(FlowActivity::getActorder)).collect(Collectors.toList());
                                        FlowActivity nextObj = nextActList.get(0);
                                        String massage = "您申请的《{项目名称}》{前业务}信息审批已结束，请发起{后业务}";
                                        if (!ValidateUtil.isEmpty(nextObj.getActBusinessHandleTips())) {
                                            massage = nextObj.getActBusinessHandleTips();
                                        }
                                        if (massage.contains("{项目名称}")) {
                                            massage = massage.replace("{项目名称}", project.getpName());
                                        }
                                        if (massage.contains("{前业务}")) {
                                            String tcode = nowTcode;
                                            List<FileTemplate> nowTemp = fileTemplateList.stream().filter(n -> n.gettCode().equals(tcode)).collect(Collectors.toList());
                                            if (nowTemp.size() > 0) {
                                                massage = massage.replace("{前业务}", nowTemp.get(0).gettName());
                                            }
                                        }
                                        if (massage.contains("{后业务}")) {
                                            if (!ValidateUtil.isEmpty(nextObj.getActtemplatecode())) {
                                                List<FileTemplate> nowTemp = fileTemplateList.stream().filter(n -> n.gettCode().equals(nextObj.getActtemplatecode())).collect(Collectors.toList());
                                                if (nowTemp.size() > 0) {
                                                    if (nowTemp.get(0).gettType().equals("01")) {
                                                        massage = massage.replace("{后业务}", nowTemp.get(0).gettName());
                                                    } else {
                                                        if (!ValidateUtil.isEmpty(nextObj.getActBusinessStartNode())) {
                                                            List<FileTemplate> starTemp = fileTemplateList.stream().filter(n -> n.gettCode().equals(nextObj.getActBusinessStartNode())).collect(Collectors.toList());
                                                            if (starTemp.size() > 0) {
                                                                massage = massage.replace("{后业务}", starTemp.get(0).gettName());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        Map<String, Object> returnObj = new HashMap<>();
                                        returnObj.put("massage", massage);
                                        returnObj.put("menuCode", nextObj.getActBusinessMenuCode());
                                        returnList.add(returnObj);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new BaseResponse("操作成功！", returnList, returnList.size());
    }


    @Override
    public List<String> getEndActivity(FlowActivity
                                               Starobj, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules, String customerId) {
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(Starobj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        List<TaskProcessin> TaskProcessin_new = new ArrayList<>();
        List<String> actIdList = new ArrayList<>();
        for (FlowRule rule : FlowRuleList) {
            List<FlowActivity> actList = FlowActivitys.stream().filter(n -> n.getActid().equals(rule.getRuleendactid())).collect(Collectors.toList());
            if (actList.size() > 0) {
                if (actList.get(0).getActtype().equals("endEvent")) {
                    actIdList.add(rule.getRuleid());
                    actIdList.add(actList.get(0).getActid());

                } else if (!actList.get(0).getActtype().equals("userTask")) {
                    List<String> acts = getEndActivity(actList.get(0), FlowActivitys, FlowRules, customerId);
                    if (acts.size() > 0) {
                        actIdList.add(rule.getRuleid());
                        actIdList.add(actList.get(0).getActid());
                        actIdList.addAll(acts);
                    }
                }
            }
        }

        return actIdList;
    }

    @Override
    public Map<String, Integer> getFlowActNum(List<FlowActivity> flowActivityList, List<FlowRule> FlowRuleList, FlowActivity flowActivity) {
        Map<String, Integer> flowActNum = new HashMap<>();
        Map<String, Integer> flowActNumSub = new HashMap<>();
        List<FlowRule> rList = FlowRuleList.stream().filter(n -> n.getRulebeginactid().equals(flowActivity.getActid())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(rList)) {
            int num = 0;
            for (FlowRule rule : rList) {
                List<FlowActivity> flowActivities = flowActivityList.stream().filter(n -> n.getActid().equals(rule.getRuleendactid())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(flowActivities)) {

                    if (flowActivities.get(0).getActtype().equals("userTask")) {
                        num++;
                        flowActNum.put(flowActivities.get(0).getActid(), num);
                    }
                    Map<String, Integer> newFlowActNum = getFlowActNum(flowActivityList, FlowRuleList, flowActivities.get(0));
                    flowActNumSub.putAll(newFlowActNum);
                }
            }
            for (String actId : flowActNumSub.keySet()) {
                flowActNum.put(actId, num + flowActNumSub.get(actId));
            }
        }
        return flowActNum;
    }

    public Flow getFlow(List<Flow> flowList, String pId, String tcode, String companyID) {
        List<Flow> datas = flowList.stream().filter(n -> n.getTCode().equals("tcode")).collect(Collectors.toList());
        if (ValidateUtil.isEmpty(pId)) {
            pId = "All";
        }
        if (ValidateUtil.isEmpty(companyID)) {
            companyID = "All";
        }
        if (datas.size() > 0) {
            String projectIDstr = pId;
            String companyIDstr = companyID;
            List<Flow> flow = datas.stream().filter(e -> e.getFlowproject().equals(projectIDstr) && e.getFlowcompany().equals(companyIDstr)).collect(Collectors.toList());
            if (flow.size() > 0) {
                return flow.get(0);
            } else {
                List<Flow> obj = datas.stream().filter(e -> e.getFlowproject().equals("ALL") && e.getFlowcompany().equals(companyIDstr)).collect(Collectors.toList());
                if (obj.size() > 0) {
                    return obj.get(0);
                } else {
                    List<Flow> entity = datas.stream().filter(e -> e.getFlowproject().equals("ALL") && e.getFlowcompany().equals("ALL")).collect(Collectors.toList());
                    if (entity.size() > 0) {
                        return entity.get(0);
                    } else {
                        return null;
                    }
                }
            }
        } else {
            return null;
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


    //获取当前节点的后续所有节点，包括网关、结束节点等信息
    @Override
    public List<FlowActivity> getAllSubsequentNodes(FlowActivity Starobj, List<FlowActivity> FlowActivitys, List<FlowRule> FlowRules) {
        List<FlowActivity> subsequentNodes = new ArrayList<>();
        List<FlowRule> FlowRuleList = FlowRules.stream().filter(n -> n.getRulebeginactid().equals(Starobj.getActid()) && n.getRulesystype().equals("提交")).collect(Collectors.toList());
        for (FlowRule rule : FlowRuleList) {
            List<FlowActivity> actList = FlowActivitys.stream().filter(n -> n.getActid().equals(rule.getRuleendactid())).collect(Collectors.toList());
            if (actList.size() > 0) {
                subsequentNodes.addAll(actList);
                subsequentNodes.addAll(getAllSubsequentNodes(actList.get(0), FlowActivitys, FlowRules));
            }
        }
        return subsequentNodes;

    }

    //获取结束相容网关前需要删除的待办信息
    @Override
    public List<String> getNodesAllowedNodeIds(List<FlowActivity> actList, List<TaskProcessin> tpList, String uId, String type, String status,List<String>  taskActIds) {
        try {
            List<String> nodeIds = new ArrayList<>();
            //获取当前用户的用户类型，岗位，角色信息
            List<String> userIds = new ArrayList<>();
            userIds.add(uId);
            List<UserInfoVo> userList = baseMapper.getPersonnelSettings(userIds,"All");
            if (CollectionUtils.isEmpty(userList)) {
                //没找到用户的岗位角色信息，返回所有的节点信息不进行过滤
                return actList.stream().map(FlowActivity::getActid).collect(Collectors.toList());
            } else {
                //有用户角色信息
                List<String> jsList = new ArrayList<>();
                List<String> gwList = new ArrayList<>();
                String uType = userList.get(0).getuType();
                if (ValidateUtil.isNotEmpty(userList.get(0).getRdRid())) {
                    jsList.addAll(Arrays.asList(userList.get(0).getRdRid().split(",")));
                }
                if (ValidateUtil.isNotEmpty(userList.get(0).getRpRid())) {
                    jsList.addAll(Arrays.asList(userList.get(0).getRpRid().split(",")));
                }
                //去重
                if (ValidateUtil.isNotEmpty(userList.get(0).getPoPid())) {
                    gwList.addAll(Arrays.asList(userList.get(0).getPoPid().split(",")));
                }

                actList.forEach(n -> {
                    if ("1".equals(type)) {
                        //审批记录权限控制
                        if (ValidateUtil.isNotEmpty(n.getActRecordPower())) {
                            String actRecordPower = n.getActRecordPower();
                            if ("All".equals(actRecordPower)) {
                                //全部公开 All
                                nodeIds.add(n.getActid());
                            } else if ("01".equals(actRecordPower)) {
                                //部分公开 01
                                if (getScopeAuthority(n, jsList, gwList, uType)) {
                                    nodeIds.add(n.getActid());
                                }
                            } else if ("02".equals(actRecordPower)) {
                                //审批通过后全部公开 02
                                if ("7".equals(status)) {
                                    nodeIds.add(n.getActid());
                                }
                            } else if ("03".equals(actRecordPower)) {
                                //审批通过后部分公开 03
                                if ("7".equals(status) && getScopeAuthority(n, jsList, gwList, uType)) {
                                    nodeIds.add(n.getActid());
                                }
                            } else if ("04".equals(actRecordPower) && ValidateUtil.isNotEmpty(n.getActRecordPowerNode())) {
                                //指定节点审批通过后全部公开 04
                                if (getSpecifyNodes(n, tpList,taskActIds)) {
                                    nodeIds.add(n.getActid());
                                }

                            } else if ("05".equals(actRecordPower) && ValidateUtil.isNotEmpty(n.getActRecordPowerNode())) {
                                //指定节点审核通过后部分公开 05
                                if (getSpecifyNodes(n, tpList,taskActIds) && getScopeAuthority(n, jsList, gwList, uType)) {
                                    nodeIds.add(n.getActid());
                                }
                            }
                        } else {
                            nodeIds.add(n.getActid());
                        }
                    } else if ("2".equals(type)) {
                        //节点状态权限控制
                        if (ValidateUtil.isNotEmpty(n.getActNodeStatusPower())) {
                            List<String> actNodeStatusPower = Arrays.asList(n.getActNodeStatusPower().split(","));
                            if (actNodeStatusPower.contains("All")) {
                                nodeIds.add(n.getActid());
                            } else if (actNodeStatusPower.contains(uType)) {
                                nodeIds.add(n.getActid());
                            }
                        } else {
                            nodeIds.add(n.getActid());
                        }
                    } else {
                        nodeIds.add(n.getActid());
                    }
                });

                return nodeIds;
            }
        } catch (Exception e) {
            log.error("流程服务，审批记录、代办节点显示过滤失败");
            //出错了返回所有的节点id，过滤
            return actList.stream().map(FlowActivity::getActid).collect(Collectors.toList());
        }
    }

    //判断节点的允许范围
    public boolean getScopeAuthority(FlowActivity n, List<String> jsList, List<String> gwList, String uType) {
        AtomicBoolean oType = new AtomicBoolean(false);
        if (ValidateUtil.isNotEmpty(n.getActRecordPowerScope())) {
            //String[]  sss=n.getActRecordPowerScope().split("\\|");
            List<String> actRecordPowerScope = Arrays.asList(n.getActRecordPowerScope().split("\\|"));
            if (!CollectionUtils.isEmpty(actRecordPowerScope)) {

                actRecordPowerScope.forEach((k) -> {
                    if (ValidateUtil.isNotEmpty(k)) {
                        List<String> actRecordPowerScope2 = Arrays.asList(k.split(","));
                        if ("department_position".equals(actRecordPowerScope2.get(0)) && (!CollectionUtils.isEmpty(gwList))) {
                            //组织机构岗位
                            if (gwList.contains(actRecordPowerScope2.get(1))) {
                                oType.set(true);
                            }
                        } else if ("role".equals(actRecordPowerScope2.get(0)) && (!CollectionUtils.isEmpty(jsList))) {
                            //角色
                            if (jsList.contains(actRecordPowerScope2.get(1))) {
                                oType.set(true);
                            }
                        } else if ("uType".equals(actRecordPowerScope2.get(0)) && ValidateUtil.isNotEmpty(uType)) {
                            if (uType.equals(actRecordPowerScope2.get(1))) {
                                oType.set(true);
                            }
                        }
                    }
                });
            }

        }
        return oType.get();
    }


    //判断指定节点是否已经审批过了
    public boolean getSpecifyNodes(FlowActivity n, List<TaskProcessin> tpList,List<String> taskActIds) {
        //获取第一个审批的节点信息，认为是初始节点
        tpList = tpList.stream().sorted(Comparator.comparing(TaskProcessin::getTpDotime)).collect(Collectors.toList());
        AtomicBoolean oType = new AtomicBoolean(false);
        String actId = tpList.get(0).getTpActid();
        //
        List<TaskProcessin> tps = tpList.stream().filter(k -> k.getTpActid().equals(actId)).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(tps)) {
            Date doTime = tps.get(0).getTpDotime();
            String actRecordPowerNode = n.getActRecordPowerNode();
            List<TaskProcessin> ts = tpList.stream().filter(k -> actRecordPowerNode.contains(k.getTpActid()) && k.getTpDotime().compareTo(doTime) > 0).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(ts)) {
                //判断是否有代办信息：解决审核过又退回的问题
                if(!CollectionUtils.isEmpty(taskActIds)){
                    List<TaskProcessin> tList=ts.stream().filter(b->!taskActIds.contains(b.getTpActid())).collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(tList)){
                        oType.set(true);
                    }

                }else{
                    oType.set(true);
                }

            }

        }
        return oType.get();
    }



    //根据当前节点判断是否能退回
    @Override
    public  List<String> getCanReturnNodeId(List<FlowActivity> flowActivityList,String actId) {
        List<String> nodeIds = new ArrayList<>();
        flowActivityList.forEach(flowActivity -> {
            if("notAllowed".equals(flowActivity.getActReturnCurrent())) {
                //开启了不允许退回
                if(ValidateUtil.isNotEmpty(flowActivity.getActReturnCurrentOk())) {
                    if(flowActivity.getActReturnCurrentOk().contains(actId)){
                        nodeIds.add(flowActivity.getActid());
                    }
                }
            }else{
                nodeIds.add(flowActivity.getActid());
            }
        });
        return nodeIds;

    }

}
