package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysTaskProcessinTimeMapper;
import com.jpmsoft.jpbpm.pojo.dto.ApprovalTimelimitDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.*;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class SysTaskProcessinTimeImpl extends ServiceImpl<SysTaskProcessinTimeMapper, SysTaskProcessinTime> implements SysTaskProcessinTimeService {
    @Autowired
    TaskProcessinService taskProcessinService;
    @Autowired
    SysConfigEntityService sysConfigEntityService;
    @Autowired
    TaskService taskService;
    @Autowired
    private FlowUserService flowUserService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileService fileService;
    @Autowired
    private TaskProcessUserService taskProcessUserService;

    @Override
    public BaseResponse synchronizationTaskProcessinTime(String startDate, String endDate, String customerId,String tCode,String year) {
        QueryWrapper<File> fileWrapper = new QueryWrapper<>();
        fileWrapper.ge("FileCreateDate", startDate);
        fileWrapper.le("FileCreateDate", endDate);
        if (!"All".equals(customerId)) {
            fileWrapper.eq("Te_Id", customerId);
        }
        List<File> fileList = fileService.getList(fileWrapper,tCode,year);
        if (fileList.size() > 0) {
            //获取当天为同步的审批记录信息
            List<String> fileIds = fileList.stream().map(n -> n.getFileid()).collect(Collectors.toList());
            List<SysTaskProcessinInfoVo> list = taskProcessinService.getSysTaskProcessinInfo(fileIds, customerId);

            //获取当天已经同步的审批信息99
            List<String> tpIdList = baseMapper.getTpId(fileIds, customerId);
            if (list.size() > 0) {
                List<String> tpIds = list.stream().map(n -> n.getTpId()).collect(Collectors.toList());
                List<SysTaskProcessUserVo> tpuList = taskProcessUserService.GetTaskProcessUserInfo(tpIds, customerId,year,year);
                //根据项目id获取所需要的部门信息
                List<String> pIdList = list.stream().map(n -> n.getpId()).collect(Collectors.toList());
                List<SysDepartmentVo> PdNameList = baseMapper.getDNameByPid(pIdList, customerId);

                //获取节点配置的岗位信息
                List<String> actIdList = list.stream().map(n -> n.getTpActid()).collect(Collectors.toList());
                QueryWrapper<FlowUser> flowUserWrapper = new QueryWrapper<>();
                flowUserWrapper.in("FlowUserAct", actIdList);// 业务流程
                if (!"All".equals(customerId)) {
                    flowUserWrapper.eq("Te_Id", customerId);
                }
                List<FlowUser> flowUserList = flowUserService.list(flowUserWrapper);
                //根据用户id获取所需要的部门信息
                List<String> userIdList = list.stream().map(n -> n.getTpUser()).collect(Collectors.toList());
                if (tpuList.size() > 0) {
                    userIdList.addAll(tpuList.stream().map(n -> n.getTpuUser()).collect(Collectors.toList()));
                }
                List<SysDepartmentVo> UdNameList = baseMapper.getDNameByUid(userIdList, customerId);
                List<String> codeList = new ArrayList<>();
                codeList.add("approvalTimeSetting");//审批时效设置
                codeList.add("approvalTimeSpecialDepartments");//审批时效设置
                codeList.add("processApprovalDepartment");//s审批部门获取规则
                String festivalHoliday = "1";
                String workTimeType = "ALL";
                String workShift = "";
                String closingTime = "";
                Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
                if (smsTemplateList.get("approvalTimeSetting") != null) {
                    String data = smsTemplateList.get("approvalTimeSetting").toString();
                    Object obj = JacksonUtils.parseObject(data, new TypeReference<Map<String, String>>() {
                    });
                    if (((Map) obj).get("festivalHoliday") != null) {
                        festivalHoliday = ((Map) obj).get("festivalHoliday").toString();
                    }
                    if (((Map) obj).get("workShift") != null) {
                        workShift = ((Map) obj).get("workShift").toString();
                    }
                    if (((Map) obj).get("closingTime") != null) {
                        closingTime = ((Map) obj).get("closingTime").toString();
                    }

                }
                if (ValidateUtil.isEmpty(workShift)) {
                    workShift = "0:00";
                }
                if (ValidateUtil.isEmpty(closingTime)) {
                    closingTime = "59:59";
                }
                if (workShift.equals("0:00") && closingTime.equals("59:59")) {
                    workTimeType = "ALL";
                } else {
                    workTimeType = "1";
                }


                List<SysTaskProcessinTime> newList = new ArrayList<>();


                for (File file : fileList) {
                    Date dotime = null;
                    List<SysTaskProcessinInfoVo> tpList = list.stream().filter(n -> n.getTpEntityid().equals(file.getFileid())).sorted(Comparator.comparing(SysTaskProcessinInfoVo::getTpDotime).reversed()).collect(Collectors.toList());
                    if (tpList.size() > 0) {
                        dotime = tpList.get(0).getTpDotime();
                        for (SysTaskProcessinInfoVo item : tpList) {
                            if (!tpIdList.contains(item.getTpId())) {
                                SysTaskProcessinTime obj = new SysTaskProcessinTime();
                                obj.setTptId(GenerateUUID.getUUID());
                                obj.setTpId(item.getTpId());
                                obj.setTptActId(item.getTpActid());
                                obj.setTptActName(item.getActname());
                                if (ValidateUtil.isEmpty(item.getTpActivityType())) {
                                    obj.setTptActType("userTask");
                                } else {
                                    obj.setTptActType(item.getTpActivityType());
                                }
                                obj.setTptBeginTime(item.getTpStarttime());
                                obj.setTptDoTime(item.getTpDotime());
                                obj.setTptCId(item.getcId());
                                obj.setTptCName(item.getcName());
                                obj.setTptUser(item.getTpUser());
                                obj.setTptUserName(item.getuName());
                                obj.setTptUserType(item.getuType());
                                obj.setTptApprovalType(item.getTpType());
                                obj.setTptName(item.getTpName());
                                obj.setTptTimeoutTime(item.getActHour());
                                obj.setTptUserAccount(item.getuAccount());
                                obj.setTptPId(file.getFileproject());


                                obj.setTptFileId(file.getFileid());
                                obj.setTptApprovalState((Integer) file.getFileflowstatus());
                                obj.setTptApprovalTime(dotime);
                                obj.setTptFileCode(file.getFilecode());
                                obj.setTptExplain(item.getTpExplain());
                                if (!"All".equals(customerId)) {
                                    obj.setTeId(customerId);
                                }
                                List<SysDepartmentVo> uDname = UdNameList.stream().filter(n -> n.getuId().equals(item.getTpUser())).collect(Collectors.toList());
                                List<SysDepartmentVo> pDname = PdNameList.stream().filter(n -> n.getpId().equals(item.getpId())).collect(Collectors.toList());
                                Map<String, String> dName = departmentProces(uDname, smsTemplateList, flowUserList, pDname);
                                obj.setTptDCode(dName.get("dCode"));
                                obj.setTptDId(dName.get("dId"));
                                obj.setTptDName(dName.get("dName"));
                                if (pDname.size() > 0) {
                                    obj.setTptPDcode(pDname.get(0).getdCode());
                                    obj.setTptPDid(pDname.get(0).getdId());
                                    obj.setTptPDname(pDname.get(0).getdName());
                                }

                                if ("退回".equals(item.getTpType())) {
                                    if (tpuList.size() > 0) {
                                        List<SysTaskProcessUserVo> tpuObj = tpuList.stream().filter(n -> n.getTpuTpId().equals(item.getTpId())).collect(Collectors.toList());
                                        if (tpuObj.size() > 0) {
                                            SysTaskProcessUserVo tpu = tpuObj.get(0);
                                            obj.setTptrActId(tpu.getTpuSendActivityid());
                                            obj.setTptrActName(tpu.getActname());
                                            obj.setTptrUserId(tpu.getTpuUser());
                                            obj.setTptrUserName(tpu.getuName());
                                            obj.setTptrUserType(tpu.getuType());
                                            obj.setTptrUserAccount(tpu.getuAccount());
                                            if ("1".equals(tpu.getuType())) {
                                                List<SysDepartmentVo> uDnamer = UdNameList.stream().filter(n -> n.getuId().equals(tpu.getTpuUser())).collect(Collectors.toList());
                                                Map<String, String> dNames = departmentProces(uDnamer, smsTemplateList, flowUserList, pDname);
                                                obj.setTptrDCode(dNames.get("dCode"));
                                                obj.setTptrDId(dNames.get("dId"));
                                                obj.setTptrDName(dNames.get("dName"));
                                            }

                                        }
                                    }
                                }
                                newList.add(obj);
                            }
                        }
                    }
                }
                Integer num = 0;
                if (newList.size() > 0) {
                    num = baseMapper.batchAddition(newList, festivalHoliday, workTimeType, workShift, closingTime);
                }

                return new BaseResponse("操作成功！", "true", num);
            } else {
                return new BaseResponse("操作成功", "true", 0);
            }

        } else {

            return new BaseResponse("操作成功", "false", 0);

        }


    }

    /*
     *
     * 生成审批时效时，部门获取方法
     *
     * */
    public Map<String, String> departmentProces(List<SysDepartmentVo> dList, Map<String, Object> smsTemplateList, List<FlowUser> flowUserList, List<SysDepartmentVo> pDList) {
        String sPid = "";
        String dId = "";
        String dCode = "";
        String dName = "";
        List<Object> approvalTimeSpecialDepartments = new ArrayList<>();
        if (smsTemplateList.get("approvalTimeSpecialDepartments") != null) {
            try {
                approvalTimeSpecialDepartments = JacksonUtils.parseObject(smsTemplateList.get("approvalTimeSpecialDepartments").toString(), new TypeReference<List<Object>>() {
                });
            } catch (Exception ex) {
                 log.error("流程服务：",ex);
            }
        }
        List<Object> processApprovalDepartment = new ArrayList<>();
        if (smsTemplateList.get("processApprovalDepartment") != null) {
            try {
                processApprovalDepartment = JacksonUtils.parseObject(smsTemplateList.get("processApprovalDepartment").toString(), new TypeReference<List<Object>>() {
                });
            } catch (Exception ex) {
                log.error("流程服务：",ex);
            }
        }

        //根据组织机构里设置的主部门等信息生成审批时效的所属部门
        if (dList.size() > 0) {
            if (dList.size() == 1) {
                dId = dList.get(0).getdId();
                dName = dList.get(0).getdName();
                dCode = dList.get(0).getdCode();
                sPid = dList.get(0).getpId();
            } else {
                String MainIdentification = "";
                for (SysDepartmentVo atem : dList) {
                    String puMainDep = atem.getPuMainDep();
                    if ("1".equals(puMainDep)) {
                        dId = atem.getdId();
                        dName = atem.getdName();
                        dCode = atem.getdCode();
                        sPid = atem.getpId();
                        MainIdentification = "1";
                    }
                    if (flowUserList != null && flowUserList.size() > 0) {
                        if (ValidateUtil.isEmpty(MainIdentification)) {
                            List<FlowUser> fuList = flowUserList.stream().filter(n -> n.getFlowuserrole().contains(atem.getpId())).collect(Collectors.toList());
                            if (fuList.size() > 0) {
                                dId = atem.getdId();
                                dName = atem.getdName();
                                dCode = atem.getdCode();
                                sPid = atem.getpId();
                            }
                        }
                    }
                }
                if (ValidateUtil.isEmpty(dId)) {
                    dId = dList.get(0).getdId();
                    dName = dList.get(0).getdName();
                    dCode = dList.get(0).getdCode();
                    sPid = dList.get(0).getpId();
                }
            }
            //根据基础配置的部门名称获取规则信息获取部门信息
            if (processApprovalDepartment.size() > 0 && dList.size() > 0) {
                try {
                    int type = 0;
                    for (Object dSets : processApprovalDepartment) {
                        Object child = ((Map) dSets).get("child");
                        Object typeSet = ((Map) dSets).get("type");
                        List<String> dIds = new ArrayList<>();
                        for (Object o : (List<?>) child) {
                            Object dIdStr = ((Map) o).get("key");
                            dIds.add(dIdStr.toString());
                            List<SysDepartmentVo> ds = dList.stream().filter(n -> n.getdId().equals(dIdStr)).collect(Collectors.toList());
                            if (ds.size() > 0 && type == 0) {
                                dId = ds.get(0).getdId();
                                dName = ds.get(0).getdName();
                                dCode = ds.get(0).getdCode();
                                sPid = ds.get(0).getpId();
                                type = 1;
                            }
                        }
                        if ("project".equals(typeSet)) {
                            List<SysDepartmentVo> ds = dList.stream().filter(n -> dIds.contains(n.getdId())).collect(Collectors.toList());
                            if (ds.size() == dList.size()) {
                                //当前操作用户的所属部门都再项目部门层级，根据项目所属部门赋值
                                if (pDList.size() > 0) {
                                    dId = pDList.get(0).getdId();
                                    dName = pDList.get(0).getdName();
                                    dCode = pDList.get(0).getdCode();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    log.error("流程服务：",e);
                }
            }
        } else {
            String str = "未找到部门信息";
        }
        //基础配置的主副部门
        try {
            if (ValidateUtil.isNotEmpty(sPid)) {
                if (approvalTimeSpecialDepartments.size() > 0) {
                    for (Object ate : approvalTimeSpecialDepartments) {
                        Object vluList = ((Map) ate).get("value");
                        for (Object o : (List<?>) vluList) {
                            String pId = ((Map) o).get("pId").toString();
                            if (sPid.equals(pId)) {
                                dCode = ((Map) ate).get("code").toString();
                                dId = ((Map) ate).get("kye").toString();
                                dName = ((Map) ate).get("name").toString();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("流程服务：",ex);
        }

        if (pDList.size() > 0 && ValidateUtil.isEmpty(dName)) {
            dId = pDList.get(0).getdId();
            dName = pDList.get(0).getdName();
            dCode = pDList.get(0).getdCode();
        }
        Map<String, String> returnObj = new HashMap<>();
        returnObj.put("dId", dId);
        returnObj.put("dCode", dCode);
        returnObj.put("dName", dName);
        returnObj.put("sPid", sPid);
        return returnObj;
    }


    @Override
    public int TaskProcessinTimeCreate(TaskProcessin request, String activityType, String nextActId, String nextActName, String prId, String customerId,String tCode) {

        try {
            QueryWrapper<SysTaskProcessinTime> flowWrapper = new QueryWrapper<>();
            flowWrapper.eq("tp_id", request.getTpId());// 业务流程
            List<SysTaskProcessinTime> list = baseMapper.selectList(flowWrapper);
            if (list.size() == 0) {

                List<String> codeList = new ArrayList<>();
                codeList.add("approvalTimeSetting");//审批时效设置
                codeList.add("approvalTimeSpecialDepartments");//审批时效设置
                codeList.add("processApprovalDepartment");//s审批部门获取规则
                codeList.add("generateApprovalTime");//是否生成审批时效记录
                String festivalHoliday = "1";
                String workTimeType = "ALL";
                String workShift = "";
                String closingTime = "";
                Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
                File file = fileService.getFileById(request.getTpEntityid(),tCode,festivalHoliday);
                if (smsTemplateList.get("approvalTimeSetting") != null) {
                    String data = smsTemplateList.get("approvalTimeSetting").toString();
                    Object obj = JacksonUtils.parseObject(data, new TypeReference<Map<String, String>>() {
                    });
                    if (((Map) obj).get("festivalHoliday") != null) {
                        festivalHoliday = ((Map) obj).get("festivalHoliday").toString();
                    }
                    if (((Map) obj).get("workShift") != null) {
                        workShift = ((Map) obj).get("workShift").toString();
                    }
                    if (((Map) obj).get("closingTime") != null) {
                        closingTime = ((Map) obj).get("closingTime").toString();
                    }

                }
                if (ValidateUtil.isEmpty(workShift)) {
                    workShift = "0:00";
                }
                if (ValidateUtil.isEmpty(closingTime)) {
                    closingTime = "59:59";
                }
                if (workShift.equals("0:00") && closingTime.equals("59:59")) {
                    workTimeType = "ALL";
                } else {
                    workTimeType = "1";
                }

                String generateApprovalTime = "";
                if (smsTemplateList.get("generateApprovalTime") != null) {
                    generateApprovalTime = smsTemplateList.get("generateApprovalTime").toString();
                }
                if (!("1".equals(generateApprovalTime))) {
                    return 0;
                }
                List<SysTaskProcessinTime> newList = new ArrayList<>();

                SysTaskProcessinTime obj = new SysTaskProcessinTime();
                obj.setTptId(GenerateUUID.getUUID());
                obj.setTpId(request.getTpId());
                obj.setTptActId(request.getTpActid());
                obj.setTptActName(request.getActname());
                if (ValidateUtil.isEmpty(activityType)) {
                    obj.setTptActType("userTask");
                } else {
                    obj.setTptActType(activityType);
                }
                obj.setTptBeginTime(request.getTpStarttime());
                obj.setTptDoTime(request.getTpDotime());
                obj.setTptApprovalType(request.getTpType());
                obj.setTptName(request.getTpName());
                obj.setTptUser(request.getTpUser());
                obj.setTptTimeoutTime(request.getTpActhour());
                obj.setTptFileId(request.getTpEntityid());
                obj.setTptFileCode(file.getFilecode());
                obj.setTptExplain(request.getTpExplain());
                obj.setTptExplain(request.getTpExplain());
                obj.setTptPId(prId);
                if (!"All".equals(customerId)) {
                    obj.setTeId(customerId);
                }
                List<String> userIdList = new ArrayList<>();
                userIdList.add(request.getTpUser());
                List<SysDepartmentVo> UdNameList = baseMapper.getDNameByUid(userIdList, customerId);
                List<Map<String, String>> userList = baseMapper.getUserInfo(request.getTpUser(), customerId);
                QueryWrapper<FlowUser> flowUserWrapper = new QueryWrapper<>();
                flowUserWrapper.in("FlowUserAct", request.getTpActid());// 业务流程
                if (!"All".equals(customerId)) {
                    flowUserWrapper.eq("Te_Id", customerId);
                }
                List<FlowUser> flowUserList = flowUserService.list(flowUserWrapper);
                //P_Name,P_Id
                String sPid = "";
                if (userList.size() > 0) {
                    Map<String, String> user = userList.get(0);
                    obj.setTptCId(getKey(user, "C_ID"));
                    obj.setTptCName(getKey(user, "C_Name"));
                    obj.setTptUserName(getKey(user, "U_Name"));
                    obj.setTptUserType(getKey(user, "U_Type"));
                    obj.setTptUserAccount(getKey(user, "U_Account"));

                }
                List<String> pIds = new ArrayList<>();
                pIds.add(file.getFileproject());
                List<SysDepartmentVo> pDname = baseMapper.getDNameByPid(pIds, customerId);
                Map<String, String> dNames = departmentProces(UdNameList, smsTemplateList, flowUserList, pDname);
                obj.setTptDCode(dNames.get("dCode"));
                obj.setTptDId(dNames.get("dId"));
                obj.setTptDName(dNames.get("dName"));

                if (pDname != null && pDname.size() > 0) {
                    obj.setTptPDcode(pDname.get(0).getdCode());
                    obj.setTptPDid(pDname.get(0).getdId());
                    obj.setTptPDname(pDname.get(0).getdName());
                }

                if ("退回".equals(request.getTpType())) {
                    obj.setTptrUserId(request.getTpSend());
                    obj.setTptrActId(nextActId);
                    obj.setTptrActName(nextActName);
                    List<Map<String, String>> ruserList = baseMapper.getUserInfo(request.getTpSend(), customerId);
                    if (ruserList.size() > 0) {
                        Map<String, String> user = ruserList.get(0);
                        obj.setTptrUserName(getKey(user, "U_Name"));
                        obj.setTptrUserType(getKey(user, "U_Type"));
                        obj.setTptrUserAccount(getKey(user, "U_Account"));

                    }
                    List<String> userIdListr = new ArrayList<>();
                    userIdListr.add(request.getTpSend());
                    List<SysDepartmentVo> UdNameListr = baseMapper.getDNameByUid(userIdListr, customerId);
                    Map<String, String> dNamer = departmentProces(UdNameListr, smsTemplateList, flowUserList, pDname);
                    obj.setTptrDCode(dNamer.get("dCode"));
                    obj.setTptrDId(dNamer.get("dId"));
                    obj.setTptrDName(dNamer.get("dName"));
                }
                newList.add(obj);
                Integer num = baseMapper.batchAddition(newList, festivalHoliday, workTimeType, workShift, closingTime);
                return num;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer updateTaskProcessinTime(Date approvalTime, String state, String fileId, String customerId) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String data = sdf.format(approvalTime);
            return baseMapper.updateTaskProcessinTime(data, state, fileId);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public BaseResponse updateFilecodeByFiles(String startDate, String endDate, String customerId) {
        List<SysTaskProcessinTime> tptList = baseMapper.getTaskProcessinTimeNotCode(customerId,startDate,endDate);
        if (tptList.size() > 0) {
            List<String> fileIds = tptList.stream().map(n -> n.getTptFileId()).distinct().collect(Collectors.toList());
            if(fileIds.size()>0){
                fileIds.forEach(n->{
                    baseMapper.updateTptFileCodeByFileIds(n);
                });

            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateFilecodeByFile(String code, String fileid, String customerId,String tCode,String year) {
        File f = fileService.getFileById(fileid,tCode,year);
        f.setFilecode(code);
        fileService.updateById(f);
        QueryWrapper<SysTaskProcessinTime> tptWrapper = new QueryWrapper<>();
        tptWrapper.eq("Tpt_File_Id", fileid);
        if (!"All".equals(customerId)) {
            tptWrapper.eq("Te_Id", customerId);
        }
        List<SysTaskProcessinTime> tptList = baseMapper.selectList(tptWrapper);
        tptList = tptList.stream().filter(n -> ValidateUtil.isEmpty(n.getTptFileCode())).collect(Collectors.toList());
        if (tptList.size() > 0) {
            for (SysTaskProcessinTime tptObj : tptList) {
                tptObj.setTptFileCode(code);
                baseMapper.updateById(tptObj);
            }
        }
        return BaseResponse.ok();
    }


    public List<TaskPrescriptionVo> taskHandling(List<TaskCountVo> task, String customerId, List<String> dIds, String type) {
        List<TaskPrescriptionVo> returnList = new ArrayList<>();
        if (task != null && task.size() > 0) {
            //获取当前流程规则信息
            List<String> actIdList = task.stream().map(n -> n.getActId()).distinct().collect(Collectors.toList());
            QueryWrapper<FlowUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("FlowUserAct", actIdList);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<FlowUser> flowUserList = flowUserService.list(flowUserWrapper);

            //根据项目id获取所需要的部门信息
            List<String> pIdList = task.stream().map(n -> n.getpId()).distinct().collect(Collectors.toList());
            List<SysDepartmentVo> PdNameList = baseMapper.getDNameByPid(pIdList, customerId);
            //获取审批设置的部门获取规则
            List<String> uIds = task.stream().map(n -> n.getuId()).distinct().collect(Collectors.toList());
            List<SysDepartmentVo> UdNameList = baseMapper.getDNameByUid(uIds, customerId);
            List<String> codeList = new ArrayList<>();
            codeList.add("approvalTimeSpecialDepartments");//审批时效设置
            codeList.add("processApprovalDepartment");//s审批部门获取规则
            Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);


            for (TaskCountVo obj : task) {
                List<SysDepartmentVo> uDname = UdNameList.stream().filter(n -> n.getuId().equals(obj.getuId())).collect(Collectors.toList());
                List<SysDepartmentVo> pDname = PdNameList.stream().filter(n -> n.getpId().equals(obj.getpId())).collect(Collectors.toList());
                List<FlowUser> flowUsers = flowUserList.stream().filter(n -> n.getFlowuseract().equals(obj.getActId())).collect(Collectors.toList());
                Map<String, String> dName = departmentProces(uDname, smsTemplateList, flowUsers, pDname);

                TaskPrescriptionVo pro = new TaskPrescriptionVo();
                pro.setdId(dName.get("dId"));
                pro.setdName(dName.get("dName"));
                pro.setTotal("1");
                pro.setuId(obj.getuId());
                pro.setuName(obj.getuName());
                if (pDname.size() > 0 && ValidateUtil.isEmpty(pro.getdName()) && type.equals("1")) {
                    pro.setdId(pDname.get(0).getdId());
                    pro.setdName(pDname.get(0).getdName());
                }
                if (dIds != null && dIds.size() > 0) {
                    if (dIds.contains(pro.getdId())) {
                        returnList.add(pro);
                    }
                } else {
                    if (ValidateUtil.isNotEmpty(pro.getdId())) {
                        returnList.add(pro);
                    }
                }
            }
        }
        return returnList;
    }


    @Override
    public BaseResponse getPrescription(ApprovalTimelimitDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", !CheckNullUtil.checkObject(request.getSortFiled()) ? request.getSortFiled() : "dName");
        params.put("sortType", !CheckNullUtil.checkObject(request.getSortType()) ? request.getSortType() : "asc");
        params.put("sortSideFiled", !CheckNullUtil.checkObject(request.getSortSideFiled()) ? request.getSortSideFiled() : "");

        params.put("tpTypes", !CheckNullUtil.checkObject(request.getTpTypes()) ? request.getTpTypes() : null);
        params.put("overtime", !CheckNullUtil.checkObject(request.getOvertime()) ? request.getOvertime() : 24);
        params.put("dIds", !CheckNullUtil.checkObject(request.getdIds()) ? request.getdIds() : null);
        params.put("uType", !CheckNullUtil.checkObject(request.getuType()) ? request.getuType() : "ALL");
        params.put("uName", !CheckNullUtil.checkObject(request.getuName()) ? request.getuName() : null);
        params.put("starTime", !CheckNullUtil.checkObject(request.getStarTime()) ? request.getStarTime() : null);
        params.put("completeStarTime", !CheckNullUtil.checkObject(request.getCompleteStarTime()) ? request.getCompleteStarTime() : null);
        params.put("completeEndTime", !CheckNullUtil.checkObject(request.getCompleteEndTime()) ? request.getCompleteEndTime() : null);
        params.put("userAccount", !CheckNullUtil.checkObject(request.getUserAccount()) ? request.getUserAccount() : null);
        params.put("tptFileCode", !CheckNullUtil.checkObject(request.getTptFileCode()) ? request.getTptFileCode() : null);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        params.put("endTime", !CheckNullUtil.checkObject(request.getEndTime()) ? request.getEndTime() : null);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalNotPersonne");//不参与统计的用户信息
        codeList.add("approvalNotAct");//不参与统计的节点信息
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalNotPersonne") != null) {
            String uStr = smsTemplateList.get("approvalNotPersonne").toString();
            String[] uObj = uStr.split(",");
            QueryWrapper<SysUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("U_Account", uObj);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<SysUser> flowUserList = sysUserService.list(flowUserWrapper);
            if (flowUserList.size() > 0) {
                List<String> uids = flowUserList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                params.put("notUidS", uids);
            } else {
                params.put("notUidS", null);
            }
        } else {
            params.put("notUidS", null);
        }

        if (smsTemplateList.get("approvalNotAct") != null) {
            String actStr = smsTemplateList.get("approvalNotAct").toString();
            if (ValidateUtil.isNotEmpty(actStr)) {
                String[] actObj = actStr.split(",");
                params.put("notActName", actObj);
            } else {
                params.put("notActName", null);
            }
        } else {
            params.put("notActName", null);
        }
        List<ApprovalTimelimitVo> datas = baseMapper.getPrescription(params);
        List<TaskCountVo> taskPrescription = taskService.getPrescription(params);
        Integer counts = baseMapper.getPrescriptionCount(params);
        Map<String, Object> data = new HashMap<>();
        List<TaskPrescriptionVo> list = taskHandling(taskPrescription, customerId, request.getdIds(), "1");
        for (ApprovalTimelimitVo en : datas) {
            List<TaskPrescriptionVo> lis = list.stream().filter(n -> n.getdId().equals(en.getdId()) && n.getuId().equals(en.getUserId())).collect(Collectors.toList());
            en.setTotalTimes(en.getTpCount() + lis.size());
        }
        data.put("ApprovalTimelimitVo", datas);
        return BaseResponse.ok(data, counts);
    }


    @Override
    public BaseResponse getPrescriptionDetails(ApprovalTimelimitDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", !CheckNullUtil.checkObject(request.getSortFiled()) ? request.getSortFiled() : "Tpt_D_Name");
        params.put("sortType", !CheckNullUtil.checkObject(request.getSortType()) ? request.getSortType() : "asc");
        params.put("sortSideFiled", !CheckNullUtil.checkObject(request.getSortSideFiled()) ? request.getSortSideFiled() : "");

        params.put("tpTypes", !CheckNullUtil.checkObject(request.getTpTypes()) ? request.getTpTypes() : null);
        params.put("overtime", !CheckNullUtil.checkObject(request.getOvertime()) ? request.getOvertime() : 24);
        params.put("dIds", !CheckNullUtil.checkObject(request.getdIds()) ? request.getdIds() : null);
        params.put("uType", !CheckNullUtil.checkObject(request.getuType()) ? request.getuType() : "ALL");
        params.put("uName", !CheckNullUtil.checkObject(request.getuName()) ? request.getuName() : null);
        params.put("starTime", !CheckNullUtil.checkObject(request.getStarTime()) ? request.getStarTime() : null);
        params.put("endTime", !CheckNullUtil.checkObject(request.getEndTime()) ? request.getEndTime() : null);
        params.put("completeStarTime", !CheckNullUtil.checkObject(request.getCompleteStarTime()) ? request.getCompleteStarTime() : null);
        params.put("completeEndTime", !CheckNullUtil.checkObject(request.getCompleteEndTime()) ? request.getCompleteEndTime() : null);
        params.put("userAccount", !CheckNullUtil.checkObject(request.getUserAccount()) ? request.getUserAccount() : null);
        params.put("tptFileCode", !CheckNullUtil.checkObject(request.getTptFileCode()) ? request.getTptFileCode() : null);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalNotPersonne");//不参与统计的用户信息
        codeList.add("approvalNotAct");//不参与统计的节点信息
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalNotPersonne") != null) {
            String uStr = smsTemplateList.get("approvalNotPersonne").toString();
            String[] uObj = uStr.split(",");
            QueryWrapper<SysUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("U_Account", uObj);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<SysUser> flowUserList = sysUserService.list(flowUserWrapper);
            if (flowUserList.size() > 0) {
                List<String> uids = flowUserList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                params.put("notUidS", uids);
            } else {
                params.put("notUidS", null);
            }
        } else {
            params.put("notUidS", null);
        }

        if (smsTemplateList.get("approvalNotAct") != null) {
            String actStr = smsTemplateList.get("approvalNotAct").toString();
            if (ValidateUtil.isNotEmpty(actStr)) {
                String[] actObj = actStr.split(",");
                params.put("notActName", actObj);
            } else {
                params.put("notActName", null);
            }
        } else {
            params.put("notActName", null);
        }
        List<ApprovalDetailsVo> datas = baseMapper.getPrescriptionDetails(params);
        Integer counts = baseMapper.getPrescriptionDetailsCount(params);
        return BaseResponse.ok(datas, counts);
    }


    @Override
    public BaseResponse getPrescriptionD(ApprovalTimelimitDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", !CheckNullUtil.checkObject(request.getSortFiled()) ? request.getSortFiled() : "dName");
        params.put("sortType", !CheckNullUtil.checkObject(request.getSortType()) ? request.getSortType() : "asc");
        params.put("sortSideFiled", !CheckNullUtil.checkObject(request.getSortSideFiled()) ? request.getSortSideFiled() : "");

        params.put("tpTypes", !CheckNullUtil.checkObject(request.getTpTypes()) ? request.getTpTypes() : null);
        params.put("workDay", !CheckNullUtil.checkObject(request.getWorkDay()) ? request.getWorkDay() : "1");
        params.put("workTimeType", !CheckNullUtil.checkObject(request.getWorkTimeType()) ? request.getWorkTimeType() : "ALL");
        params.put("workTimeStar", !CheckNullUtil.checkObject(request.getWorkTimeStar()) ? request.getWorkTimeStar() : "0:00");
        params.put("workTimeEnd", !CheckNullUtil.checkObject(request.getWorkTimeEnd()) ? request.getWorkTimeEnd() : "0:00");
        params.put("overtime", !CheckNullUtil.checkObject(request.getOvertime()) ? request.getOvertime() : 24);
        params.put("dIds", !CheckNullUtil.checkObject(request.getdIds()) ? request.getdIds() : null);
        params.put("uType", !CheckNullUtil.checkObject(request.getuType()) ? request.getuType() : "ALL");
        params.put("uName", !CheckNullUtil.checkObject(request.getuName()) ? request.getuName() : null);
        params.put("starTime", !CheckNullUtil.checkObject(request.getStarTime()) ? request.getStarTime() : null);
        params.put("endTime", !CheckNullUtil.checkObject(request.getEndTime()) ? request.getEndTime() : null);
        params.put("completeStarTime", !CheckNullUtil.checkObject(request.getCompleteStarTime()) ? request.getCompleteStarTime() : null);
        params.put("completeEndTime", !CheckNullUtil.checkObject(request.getCompleteEndTime()) ? request.getCompleteEndTime() : null);
        params.put("userAccount", !CheckNullUtil.checkObject(request.getUserAccount()) ? request.getUserAccount() : null);
        params.put("tptFileCode", !CheckNullUtil.checkObject(request.getTptFileCode()) ? request.getTptFileCode() : null);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalNotPersonne");//不参与统计的用户信息
        codeList.add("approvalNotAct");//不参与统计的节点信息
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalNotPersonne") != null) {
            String uStr = smsTemplateList.get("approvalNotPersonne").toString();
            String[] uObj = uStr.split(",");
            QueryWrapper<SysUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("U_Account", uObj);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<SysUser> flowUserList = sysUserService.list(flowUserWrapper);
            if (flowUserList.size() > 0) {
                List<String> uids = flowUserList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                params.put("notUidS", uids);
            } else {
                params.put("notUidS", null);
            }
        } else {
            params.put("notUidS", null);
        }

        if (smsTemplateList.get("approvalNotAct") != null) {
            String actStr = smsTemplateList.get("approvalNotAct").toString();
            if (ValidateUtil.isNotEmpty(actStr)) {
                String[] actObj = actStr.split(",");
                params.put("notActName", actObj);
            } else {
                params.put("notActName", null);
            }
        } else {
            params.put("notActName", null);
        }
        List<ApprovalTimelimitVo> datas = baseMapper.getPrescriptionD(params);
        Integer counts = baseMapper.getPrescriptionDCount(params);
        List<TaskCountVo> taskPrescription = taskService.getPrescription(params);
        Map<String, Object> data = new HashMap<>();
        List<TaskPrescriptionVo> list = taskHandling(taskPrescription, customerId, request.getdIds(), "1");
        for (ApprovalTimelimitVo en : datas) {
            List<TaskPrescriptionVo> lis = list.stream().filter(n -> n.getdId().equals(en.getdId())).collect(Collectors.toList());
            en.setTotalTimes(en.getTpCount() + lis.size());
        }
        data.put("ApprovalTimelimitVo", datas);
        return BaseResponse.ok(data, counts);
    }


    @Override
    public BaseResponse getSysTaskProcessinTimeByTpId(String tpId, String customerId) {
        QueryWrapper<SysTaskProcessinTime> flowWrapper = new QueryWrapper<>();
        flowWrapper.eq("tp_id", tpId);// 业务流程
        List<SysTaskProcessinTime> list = baseMapper.selectList(flowWrapper);
        if (list.size() > 0) {
            return BaseResponse.ok(list.get(0), 1);
        } else {
            return BaseResponse.ok();
        }
    }

    @Override
    public BaseResponse getSysTaskProcessinTimeByTpIds(List<String> tpId, String customerId) {
        if (ValidateUtil.isEmpty(tpId)) {
            return BaseResponse.ok();
        }
        QueryWrapper<SysTaskProcessinTime> flowWrapper = new QueryWrapper<>();
        flowWrapper.in("tp_id", tpId);// 业务流程
        List<SysTaskProcessinTime> list = baseMapper.selectList(flowWrapper);
        if (list.size() > 0) {
            return BaseResponse.ok(list, list.size());
        } else {
            return BaseResponse.ok();
        }
    }

    @Override
    public BaseResponse updateSysTaskProcessinTimeByTpId(String tpId, String ageing,String tCode, String customerId,String year) {
        if (ValidateUtil.isNotEmpty(ageing)) {
            QueryWrapper<SysTaskProcessinTime> flowWrapper = new QueryWrapper<>();
            flowWrapper.eq("tp_id", tpId);// 业务流程
            List<SysTaskProcessinTime> list = baseMapper.selectList(flowWrapper);
            if (list.size() == 0) {
                TaskProcessin tp = taskProcessinService.getTaskProcessById(tpId,tCode,year);
                TaskProcessinTimeCreate(tp, tp.getTpActtype(), tp.getTpActid(), tp.getTpActid(), tp.getTpProject(), customerId,tCode);
            }
            baseMapper.updateSysTaskProcessinTimeByTpId(tpId, ageing);
            return BaseResponse.ok("修改成功！");
        } else {
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse getRejectStatisticsD(ApprovalTimelimitDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", !CheckNullUtil.checkObject(request.getSortFiled()) ? request.getSortFiled() : "dName");
        params.put("sortType", !CheckNullUtil.checkObject(request.getSortType()) ? request.getSortType() : "asc");
        params.put("sortSideFiled", !CheckNullUtil.checkObject(request.getSortSideFiled()) ? request.getSortSideFiled() : "");

        params.put("tpTypes", !CheckNullUtil.checkObject(request.getTpTypes()) ? request.getTpTypes() : null);
        params.put("workDay", !CheckNullUtil.checkObject(request.getWorkDay()) ? request.getWorkDay() : "1");
        params.put("workTimeType", !CheckNullUtil.checkObject(request.getWorkTimeType()) ? request.getWorkTimeType() : "ALL");
        params.put("workTimeStar", !CheckNullUtil.checkObject(request.getWorkTimeStar()) ? request.getWorkTimeStar() : "0:00");
        params.put("workTimeEnd", !CheckNullUtil.checkObject(request.getWorkTimeEnd()) ? request.getWorkTimeEnd() : "0:00");
        params.put("overtime", !CheckNullUtil.checkObject(request.getOvertime()) ? request.getOvertime() : 24);
        params.put("dIds", !CheckNullUtil.checkObject(request.getdIds()) ? request.getdIds() : null);
        params.put("uType", !CheckNullUtil.checkObject(request.getuType()) ? request.getuType() : "ALL");
        params.put("uName", !CheckNullUtil.checkObject(request.getuName()) ? request.getuName() : null);
        params.put("starTime", !CheckNullUtil.checkObject(request.getStarTime()) ? request.getStarTime() : null);
        params.put("endTime", !CheckNullUtil.checkObject(request.getEndTime()) ? request.getEndTime() : null);
        params.put("completeStarTime", !CheckNullUtil.checkObject(request.getCompleteStarTime()) ? request.getCompleteStarTime() : null);
        params.put("completeEndTime", !CheckNullUtil.checkObject(request.getCompleteEndTime()) ? request.getCompleteEndTime() : null);
        params.put("userAccount", !CheckNullUtil.checkObject(request.getUserAccount()) ? request.getUserAccount() : null);
        params.put("tptFileCode", !CheckNullUtil.checkObject(request.getTptFileCode()) ? request.getTptFileCode() : null);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalNotPersonne");//不参与统计的用户信息
        codeList.add("approvalNotAct");//不参与统计的节点信息
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalNotPersonne") != null) {
            String uStr = smsTemplateList.get("approvalNotPersonne").toString();
            String[] uObj = uStr.split(",");
            QueryWrapper<SysUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("U_Account", uObj);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<SysUser> flowUserList = sysUserService.list(flowUserWrapper);
            if (flowUserList.size() > 0) {
                List<String> uids = flowUserList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                params.put("notUidS", uids);
            } else {
                params.put("notUidS", null);
            }
        } else {
            params.put("notUidS", null);
        }

        if (smsTemplateList.get("approvalNotAct") != null) {
            String actStr = smsTemplateList.get("approvalNotAct").toString();
            if (ValidateUtil.isNotEmpty(actStr)) {
                String[] actObj = actStr.split(",");
                params.put("notActName", actObj);
            } else {
                params.put("notActName", null);
            }
        } else {
            params.put("notActName", null);
        }
        List<RejectStatisticsVo> datas = baseMapper.getRejectStatisticsD(params);
        Integer counts = baseMapper.getRejectStatisticsDCount(params);
        return BaseResponse.ok(datas, counts);
    }


    @Override
    public BaseResponse getRejectStatisticsPD(ApprovalTimelimitDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", !CheckNullUtil.checkObject(request.getSortFiled()) ? request.getSortFiled() : "dName");
        params.put("sortType", !CheckNullUtil.checkObject(request.getSortType()) ? request.getSortType() : "asc");
        params.put("sortSideFiled", !CheckNullUtil.checkObject(request.getSortSideFiled()) ? request.getSortSideFiled() : "");

        params.put("tpTypes", !CheckNullUtil.checkObject(request.getTpTypes()) ? request.getTpTypes() : null);
        params.put("workDay", !CheckNullUtil.checkObject(request.getWorkDay()) ? request.getWorkDay() : "1");
        params.put("workTimeType", !CheckNullUtil.checkObject(request.getWorkTimeType()) ? request.getWorkTimeType() : "ALL");
        params.put("workTimeStar", !CheckNullUtil.checkObject(request.getWorkTimeStar()) ? request.getWorkTimeStar() : "0:00");
        params.put("workTimeEnd", !CheckNullUtil.checkObject(request.getWorkTimeEnd()) ? request.getWorkTimeEnd() : "0:00");
        params.put("overtime", !CheckNullUtil.checkObject(request.getOvertime()) ? request.getOvertime() : 24);
        params.put("dIds", !CheckNullUtil.checkObject(request.getdIds()) ? request.getdIds() : null);
        params.put("uType", !CheckNullUtil.checkObject(request.getuType()) ? request.getuType() : "ALL");
        params.put("uName", !CheckNullUtil.checkObject(request.getuName()) ? request.getuName() : null);
        params.put("starTime", !CheckNullUtil.checkObject(request.getStarTime()) ? request.getStarTime() : null);
        params.put("endTime", !CheckNullUtil.checkObject(request.getEndTime()) ? request.getEndTime() : null);
        params.put("completeStarTime", !CheckNullUtil.checkObject(request.getCompleteStarTime()) ? request.getCompleteStarTime() : null);
        params.put("completeEndTime", !CheckNullUtil.checkObject(request.getCompleteEndTime()) ? request.getCompleteEndTime() : null);
        params.put("userAccount", !CheckNullUtil.checkObject(request.getUserAccount()) ? request.getUserAccount() : null);
        params.put("tptFileCode", !CheckNullUtil.checkObject(request.getTptFileCode()) ? request.getTptFileCode() : null);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalNotPersonne");//不参与统计的用户信息
        codeList.add("approvalNotAct");//不参与统计的节点信息
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalNotPersonne") != null) {
            String uStr = smsTemplateList.get("approvalNotPersonne").toString();
            String[] uObj = uStr.split(",");
            QueryWrapper<SysUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("U_Account", uObj);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<SysUser> flowUserList = sysUserService.list(flowUserWrapper);
            if (flowUserList.size() > 0) {
                List<String> uids = flowUserList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                params.put("notUidS", uids);
            } else {
                params.put("notUidS", null);
            }
        } else {
            params.put("notUidS", null);
        }

        if (smsTemplateList.get("approvalNotAct") != null) {
            String actStr = smsTemplateList.get("approvalNotAct").toString();
            if (ValidateUtil.isNotEmpty(actStr)) {
                String[] actObj = actStr.split(",");
                params.put("notActName", actObj);
            } else {
                params.put("notActName", null);
            }
        } else {
            params.put("notActName", null);
        }
        List<RejectStatisticsVo> datas = baseMapper.getRejectStatisticsPD(params);
        Integer counts = baseMapper.getRejectStatisticsPDCount(params);
        return BaseResponse.ok(datas, counts);
    }


    @Override
    public BaseResponse getRejectStatisticsUD(ApprovalTimelimitDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", !CheckNullUtil.checkObject(request.getSortFiled()) ? request.getSortFiled() : "dName");
        params.put("sortType", !CheckNullUtil.checkObject(request.getSortType()) ? request.getSortType() : "asc");
        params.put("sortSideFiled", !CheckNullUtil.checkObject(request.getSortSideFiled()) ? request.getSortSideFiled() : "");

        params.put("tpTypes", !CheckNullUtil.checkObject(request.getTpTypes()) ? request.getTpTypes() : null);
        params.put("workDay", !CheckNullUtil.checkObject(request.getWorkDay()) ? request.getWorkDay() : "1");
        params.put("workTimeType", !CheckNullUtil.checkObject(request.getWorkTimeType()) ? request.getWorkTimeType() : "ALL");
        params.put("workTimeStar", !CheckNullUtil.checkObject(request.getWorkTimeStar()) ? request.getWorkTimeStar() : "0:00");
        params.put("workTimeEnd", !CheckNullUtil.checkObject(request.getWorkTimeEnd()) ? request.getWorkTimeEnd() : "0:00");
        params.put("overtime", !CheckNullUtil.checkObject(request.getOvertime()) ? request.getOvertime() : 24);
        params.put("dIds", !CheckNullUtil.checkObject(request.getdIds()) ? request.getdIds() : null);
        params.put("uType", !CheckNullUtil.checkObject(request.getuType()) ? request.getuType() : "ALL");
        params.put("uName", !CheckNullUtil.checkObject(request.getuName()) ? request.getuName() : null);
        params.put("starTime", !CheckNullUtil.checkObject(request.getStarTime()) ? request.getStarTime() : null);
        params.put("endTime", !CheckNullUtil.checkObject(request.getEndTime()) ? request.getEndTime() : null);
        params.put("completeStarTime", !CheckNullUtil.checkObject(request.getCompleteStarTime()) ? request.getCompleteStarTime() : null);
        params.put("completeEndTime", !CheckNullUtil.checkObject(request.getCompleteEndTime()) ? request.getCompleteEndTime() : null);
        params.put("userAccount", !CheckNullUtil.checkObject(request.getUserAccount()) ? request.getUserAccount() : null);
        params.put("tptFileCode", !CheckNullUtil.checkObject(request.getTptFileCode()) ? request.getTptFileCode() : null);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalNotPersonne");//不参与统计的用户信息
        codeList.add("approvalNotAct");//不参与统计的节点信息
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalNotPersonne") != null) {
            String uStr = smsTemplateList.get("approvalNotPersonne").toString();
            String[] uObj = uStr.split(",");
            QueryWrapper<SysUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("U_Account", uObj);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<SysUser> flowUserList = sysUserService.list(flowUserWrapper);
            if (flowUserList.size() > 0) {
                List<String> uids = flowUserList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                params.put("notUidS", uids);
            } else {
                params.put("notUidS", null);
            }
        } else {
            params.put("notUidS", null);
        }

        if (smsTemplateList.get("approvalNotAct") != null) {
            String actStr = smsTemplateList.get("approvalNotAct").toString();
            if (ValidateUtil.isNotEmpty(actStr)) {
                String[] actObj = actStr.split(",");
                params.put("notActName", actObj);
            } else {
                params.put("notActName", null);
            }
        } else {
            params.put("notActName", null);
        }
        List<RejectStatisticsVo> datas = baseMapper.getRejectStatisticsUD(params);
        Integer counts = baseMapper.getRejectStatisticsUDCount(params);
        return BaseResponse.ok(datas, counts);
    }

    @Override
    public BaseResponse getRejectStatisticsRUD(ApprovalTimelimitDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", !CheckNullUtil.checkObject(request.getSortFiled()) ? request.getSortFiled() : "dName");
        params.put("sortType", !CheckNullUtil.checkObject(request.getSortType()) ? request.getSortType() : "asc");
        params.put("sortSideFiled", !CheckNullUtil.checkObject(request.getSortSideFiled()) ? request.getSortSideFiled() : "");

        params.put("tpTypes", !CheckNullUtil.checkObject(request.getTpTypes()) ? request.getTpTypes() : null);
        params.put("workDay", !CheckNullUtil.checkObject(request.getWorkDay()) ? request.getWorkDay() : "1");
        params.put("workTimeType", !CheckNullUtil.checkObject(request.getWorkTimeType()) ? request.getWorkTimeType() : "ALL");
        params.put("workTimeStar", !CheckNullUtil.checkObject(request.getWorkTimeStar()) ? request.getWorkTimeStar() : "0:00");
        params.put("workTimeEnd", !CheckNullUtil.checkObject(request.getWorkTimeEnd()) ? request.getWorkTimeEnd() : "0:00");
        params.put("overtime", !CheckNullUtil.checkObject(request.getOvertime()) ? request.getOvertime() : 24);
        params.put("dIds", !CheckNullUtil.checkObject(request.getdIds()) ? request.getdIds() : null);
        params.put("uType", !CheckNullUtil.checkObject(request.getuType()) ? request.getuType() : "ALL");
        params.put("uName", !CheckNullUtil.checkObject(request.getuName()) ? request.getuName() : null);
        params.put("starTime", !CheckNullUtil.checkObject(request.getStarTime()) ? request.getStarTime() : null);
        params.put("endTime", !CheckNullUtil.checkObject(request.getEndTime()) ? request.getEndTime() : null);
        params.put("completeStarTime", !CheckNullUtil.checkObject(request.getCompleteStarTime()) ? request.getCompleteStarTime() : null);
        params.put("completeEndTime", !CheckNullUtil.checkObject(request.getCompleteEndTime()) ? request.getCompleteEndTime() : null);
        params.put("userAccount", !CheckNullUtil.checkObject(request.getUserAccount()) ? request.getUserAccount() : null);
        params.put("tptFileCode", !CheckNullUtil.checkObject(request.getTptFileCode()) ? request.getTptFileCode() : null);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalNotPersonne");//不参与统计的用户信息
        codeList.add("approvalNotAct");//不参与统计的节点信息
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalNotPersonne") != null) {
            String uStr = smsTemplateList.get("approvalNotPersonne").toString();
            String[] uObj = uStr.split(",");
            QueryWrapper<SysUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("U_Account", uObj);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<SysUser> flowUserList = sysUserService.list(flowUserWrapper);
            if (flowUserList.size() > 0) {
                List<String> uids = flowUserList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                params.put("notUidS", uids);
            } else {
                params.put("notUidS", null);
            }
        } else {
            params.put("notUidS", null);
        }

        if (smsTemplateList.get("approvalNotAct") != null) {
            String actStr = smsTemplateList.get("approvalNotAct").toString();
            if (ValidateUtil.isNotEmpty(actStr)) {
                String[] actObj = actStr.split(",");
                params.put("notActName", actObj);
            } else {
                params.put("notActName", null);
            }
        } else {
            params.put("notActName", null);
        }
        List<RejectStatisticsVo> datas = baseMapper.getRejectStatisticsRUD(params);
        Integer counts = baseMapper.getRejectStatisticsRUDCount(params);
        return BaseResponse.ok(datas, counts);
    }


    @Override
    public BaseResponse rejectInnformationCorrection(String startDate, String endDate, String customerId){
        QueryWrapper<SysTaskProcessinTime> Wrapper = new QueryWrapper<>();
        Wrapper.ge("Tpt_Do_Time",startDate);
        Wrapper.le("Tpt_Do_Time",endDate);
        Wrapper.eq("Tpt_Approval_Type","退回");
        if (!"All".equals(customerId)) {
            Wrapper.eq("Te_Id", customerId);
        }
          List<SysTaskProcessinTime>   getTpList=baseMapper.selectList(Wrapper);
        if(getTpList.size()>0){
            List<String> codeList = new ArrayList<>();
            codeList.add("approvalTimeSpecialDepartments");//审批时效设置
            codeList.add("processApprovalDepartment");//s审批部门获取规则
            codeList.add("generateApprovalTime");//是否生成审批时效记录

            Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
              List<String>  uids=getTpList.stream().map(n->n.getTptrUserId()).collect(Collectors.toList());
            List<SysDepartmentVo> UdNameList = baseMapper.getDNameByUid(uids, customerId);
            List<SysUser> userList =sysUserService.listByIds(uids);
            List<String> actIds=getTpList.stream().map(k->k.getTptrActId()).collect(Collectors.toList());
            QueryWrapper<FlowUser> flowUserWrapper = new QueryWrapper<>();
            flowUserWrapper.in("FlowUserAct", actIds);// 业务流程
            if (!"All".equals(customerId)) {
                flowUserWrapper.eq("Te_Id", customerId);
            }
            List<FlowUser> flowUserList = flowUserService.list(flowUserWrapper);
            List<String> pIds =getTpList.stream().map(k->k.getTptPId()).collect(Collectors.toList());
            List<SysDepartmentVo> pDnameList = baseMapper.getDNameByPid(pIds, customerId);
            getTpList.forEach(n->{
                List<SysUser> uList= userList .stream().filter(k->k.getuId().equals(n.getTptrUserId())).collect(Collectors.toList());
                if(uList.size()>0){
                    SysUser uObj=uList.get(0);
                    n.setTptrUserName(uObj.getuName());
                    n.setTptrUserType(uObj.getuType());
                    n.setTptrUserAccount(uObj.getuAccount());
                    List<SysDepartmentVo> pDname=pDnameList.stream().filter(k->k.getpId().equals(n.getTptrUserId())).collect(Collectors.toList());
                    if ("1".equals(uObj.getuType())) {
                        List<SysDepartmentVo> uDnamer = UdNameList.stream().filter(k -> k.getuId().equals(n.getTptrUserId())).collect(Collectors.toList());

                        Map<String, String> dNames = departmentProces(uDnamer, smsTemplateList, flowUserList, pDname);
                        n.setTptrDCode(dNames.get("dCode"));
                        n.setTptrDId(dNames.get("dId"));
                        n.setTptrDName(dNames.get("dName"));
                    }else{
                        if(pDname.size()>0){
                            n.setTptrDCode(pDname.get(0).getdCode());
                            n.setTptrDId(pDname.get(0).getdId());
                            n.setTptrDName(pDname.get(0).getdName());
                        }else{
                            n.setTptrDCode("");
                            n.setTptrDId("");
                            n.setTptrDName("");
                        }

                    }
                }
                baseMapper.updateById(n);
            });
        }

        return BaseResponse.ok("修改成功");

    }


    public String getKey(Map<String, String> obj, String key) {
        try {
            if (obj.get(key) != null) {
                Object val = obj.get(key);
                return val.toString();
            } else if (obj.get(key.toLowerCase(Locale.ENGLISH)) != null) {
                String keyStr = key.toLowerCase(Locale.ENGLISH);
                Object val = obj.get(keyStr);
                return val.toString();
            } else if (obj.get(key.toUpperCase(Locale.ENGLISH)) != null) {
                String keyStr = key.toUpperCase(Locale.ENGLISH);
                Object val = obj.get(keyStr);
                return val.toString();
            } else {
                return "";
            }
        } catch (Exception ex) {
            log.error("流程服务：",ex);
            return "";
        }
    }
}
