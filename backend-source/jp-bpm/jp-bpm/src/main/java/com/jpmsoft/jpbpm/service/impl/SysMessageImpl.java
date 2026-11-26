package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysMessageMapper;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.SysPositionPRoleAndUidVo;
import com.jpmsoft.jpbpm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.jpmsoft.jpbpm.util.fileLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysMessageImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {
    @Autowired
    SysConfigEntityService sysConfigEntityService;

    @Autowired
    FileService fileService;

    @Autowired
    SysUserService sysUserService;
    @Autowired
    TaskProcessinService taskProcessinService;
    @Autowired
    FlowActivityService flowActivityService;
    @Autowired
    private SysPositionUserService sysPositionUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPositionPRoleService sysPositionPRoleService;
    @Autowired
    private SysPositionRoleService sysPositionRoleService;
    @Autowired
    private CarbonCopyService carbonCopyService;
    @Autowired
    private  ISysMessageReceiveService sysMessageReceiveService;
    @Value("${bpm.logSavePath}")
    public String logSavePath;
    @Override
    public void generateMassage(String MassageType, List<String> userList, String fileId, String actId, String taskNmae, String resultstr, String opinion, String userId, String customerId, String token, String pId,String tCode,String year) {
        fileLog fl = new fileLog();
        try {
            fl.write_to_txt(logSavePath, "进入平台消息生成接口");
        } catch (Exception ex) {
            log.error("流程服务：", ex);
        }
        try {
            List<String> codeList = new ArrayList<>();
            codeList.add("systemName");
            codeList.add("systemNameShort");
            //获取配置的默认短信信息
            Map<String, Object> MassageTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);


            //获取文档信息
            File fileobj = fileService.getFileById(fileId,tCode,year);
            List<SysMessage> MassageList = new ArrayList<>();

            FlowActivity flowActivity = flowActivityService.getById(actId);
            String actMassageType = flowActivity.getActMassageType();
            try {
                fl.write_to_txt(logSavePath, "判断是否开启平台消息推送：actMassageType" + actMassageType);
            } catch (Exception ex) {
                log.error("流程服务：", ex);
            }
            if (actMassageType != null && actMassageType.equals("1")) {
                try {
                    fl.write_to_txt(logSavePath, "开启平台消息推送");
                } catch (Exception ex) {
                    log.error("流程服务：", ex);
                }
                //设置了短信通知
                try {
                    fl.write_to_txt(logSavePath, "判断是否配置发送创建人：ActMassageS："+flowActivity.getActMassageS());
                } catch (Exception ex) {
                    log.error("流程服务：", ex);
                }
                if (flowActivity.getActMassageS() != null && flowActivity.getActMassageS().equals("TRUE")) {
                    //上一步任务创建人
                    String MassageTemplate = "";
                    if (!ValidateUtil.isEmpty(flowActivity.getActMassageSt())) {
                        MassageTemplate = flowActivity.getActMassageSt();
                    }
                    try {
                        fl.write_to_txt(logSavePath, "判断是否配置了消息模板：MassageTemplate："+MassageTemplate);
                    } catch (Exception ex) {
                        log.error("流程服务：", ex);
                    }
                    if (!ValidateUtil.isEmpty(MassageTemplate)) {
                        //有短信模板信息
                        String newMassage = MassageGeneration(MassageTemplate, fileobj, taskNmae, MassageTemplateList.get("systemName").toString(), MassageTemplateList.get("systemNameShort").toString(), resultstr, opinion, userId, flowActivity.getActname());
                        newMassage = newMassage.replace("{发起人}", fileobj.getFilecreateusername());
                        try {
                            fl.write_to_txt(logSavePath, "发起人id：Filecreateuser："+fileobj.getFilecreateuser());
                        } catch (Exception ex) {
                            log.error("流程服务：", ex);
                        }
                        SysUser userobj = sysUserService.getById(fileobj.getFilecreateuser());
                        if (userobj != null) {

                                MassageList.add(SetSysShortMessagePojo(newMassage, fileobj.gettCode(), userobj.getuId()));
                            try {
                                fl.write_to_txt(logSavePath, "生成平台消息：Filecreateuser："+fileobj.getFilecreateuser());
                            } catch (Exception ex) {
                                log.error("流程服务：", ex);
                            }
                        }else{
                            try {
                                fl.write_to_txt(logSavePath, "未获取到用户信息：Filecreateuser："+fileobj.getFilecreateuser());
                            } catch (Exception ex) {
                                log.error("流程服务：", ex);
                            }
                        }
                    }
                }
                if (flowActivity.getActMassageN() != null && flowActivity.getActMassageN().equals("TRUE")) {
                    //下一步审批人
                    String MassageTemplate = "";
                    if (!ValidateUtil.isEmpty(flowActivity.getActMassageNt())) {
                        MassageTemplate = flowActivity.getActMassageNt();
                    }

                    if (!ValidateUtil.isEmpty(MassageTemplate)) {
                        //有短信模板信息
                        String newMassage = MassageGeneration(MassageTemplate, fileobj, taskNmae, MassageTemplateList.get("systemName").toString(), MassageTemplateList.get("systemNameShort").toString(), resultstr, opinion, userId, flowActivity.getActname());
                        List<SysUser> userobjlist = sysUserService.getUserAllByUids(userList, customerId);
                        userobjlist.forEach(n -> {
                            String newMassagen = newMassage.replace("{审批人}", n.getuName());
                                //生成短信信息
                                MassageList.add(SetSysShortMessagePojo(newMassage, fileobj.gettCode(), n.getuId()));

                        });
                    }

                }
                if (flowActivity.getActMassageH() != null && flowActivity.getActMassageH().equals("TRUE")) {
                    //所有历史审批人
                    String MassageTemplate = "";
                    if (!ValidateUtil.isEmpty(flowActivity.getActMassageNt())) {
                        MassageTemplate = flowActivity.getActMassageNt();
                    }
                    if (!ValidateUtil.isEmpty(MassageTemplate)) {
                        //有短信模板信息
                        String newMassage = MassageGeneration(MassageTemplate, fileobj, taskNmae, MassageTemplateList.get("systemName").toString(), MassageTemplateList.get("systemNameShort").toString(), resultstr, opinion, userId, flowActivity.getActname());
                        //获取所有的审批记录
                        List<TaskProcessin> taskplist = taskProcessinService.getListByTpFlowIdAndTpEntityId(fileobj.getFlowId(), fileobj.getFileid(), customerId,year);
                        if (taskplist.size() > 0) {
                            List<String> userid = taskplist.stream().map(n -> n.getTpUser()).collect(Collectors.toList());
                            List<SysUser> userobjlist = sysUserService.getUserAllByUids(userid, customerId);
                            userobjlist = userobjlist.stream().filter(n -> !userList.contains(n.getuId()) && !n.getuId().equals(fileobj.getFilecreateuser())).collect(Collectors.toList());
                            userobjlist.forEach(n -> {
                                String newMassagen = newMassage.replace("{审批人}", n.getuName());
                                    //生成短信信息
                                    MassageList.add(SetSysShortMessagePojo(newMassage, fileobj.gettCode(), n.getuId()));
                            });
                        }
                    }
                }
                List<String> users = new ArrayList<>();
                //给指定岗位发送短信
                String actCarbonCopyPost = flowActivity.getActMassagePost();
                if (!ValidateUtil.isEmpty(actCarbonCopyPost)) {
                    String[] pIds = actCarbonCopyPost.split(",");
                    if (pIds.length > 0) {
                        QueryWrapper<SysPositionUser> puwrapper = new QueryWrapper<>();
                        Integer num = 0;
                        puwrapper.in("P_ID", pIds);
                        List<SysPositionUser> upList = sysPositionUserService.list(puwrapper);
                        if (upList.size() > 0) {
                            users.addAll(upList.stream().map(n -> n.getuId()).collect(Collectors.toList()));
                        }
                    }
                }

                //给指定角色发送短信

                String roleStr = flowActivity.getActMassageRole();
                if (ValidateUtil.isNotEmpty(roleStr)) {
                    String[] roleList = roleStr.split(",");
                    if (roleList.length > 0) {
                        QueryWrapper<SysRole> rolewrapper = new QueryWrapper<>();
                        for (Integer i = 0; i < roleList.length; i++) {
                            if (i == 0) {
                                rolewrapper.eq("Role_ID", roleList[i]);
                            } else {

                                rolewrapper.or().eq("Role_ID", roleList[i]);
                            }
                        }
                        List<SysRole> RoleList = sysRoleService.list(rolewrapper);
                        List<SysRole> sysRolueList = new ArrayList<>();
                        List<SysPositionPRoleAndUidVo> pPRList = new ArrayList<>();
                        List<SysPositionRole> Position_RoleAll = new ArrayList<>();
                        List<SysPositionUser> Position_UserAll = new ArrayList<>();
                        List<String> Plist = new ArrayList<>();
                        List<String> ulist = new ArrayList<>();
                        if (RoleList.size() > 0) {
                            //系统角色
                            sysRolueList = RoleList.stream().filter(n -> n.getRoleType() != null && n.getRoleType().equals("1")).collect(Collectors.toList());
                            if (sysRolueList.size() > 0 && ValidateUtil.isNotEmpty(pId)) {
                                //系统角色，只获取项目组织结构人员信息
                                List<String> arIdList = sysRolueList.stream().map(n -> n.getRoleId()).collect(Collectors.toList());
                                //根据角色获取项目组织机构人员信息
                                pPRList = sysPositionPRoleService.getUidByRuleIdAndPid(pId, arIdList, customerId);
                                if (!CollectionUtils.isEmpty(pPRList)) {
                                    List<String> ls = pPRList.stream().map(n -> n.getuId()).collect(Collectors.toList());
                                    ulist.addAll(ls);
                                }
                            }

                            //非系统角色
                            List<SysRole> nsysRolueList = RoleList.stream().filter(n -> n.getRoleType() != null && n.getRoleType().equals("2")).collect(Collectors.toList());
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
                                    if (!CollectionUtils.isEmpty(Position_UserAll)) {
                                        List<String> ls = Position_UserAll.stream().map(n -> n.getuId()).collect(Collectors.toList());
                                        ulist.addAll(ls);
                                    }
                                }
                            }
                        }
                        if (!CollectionUtils.isEmpty(ulist)) {
                            users.addAll(ulist);
                        }
                    }
                }

                //给指定部门发送短信
                String actCarbonCopyDepartment = flowActivity.getActMassageDepartment();
                if (!ValidateUtil.isEmpty(actCarbonCopyDepartment)) {
                    //获取部门下的用户信息
                    String[] dIds = actCarbonCopyDepartment.split(",");
                    if (dIds.length > 0) {
                        String sqlstr = "";
                        Integer num = 0;
                        for (Integer i = 0; i < dIds.length; i++) {
                            if (!ValidateUtil.isEmpty(dIds[i])) {
                                if (num == 0) {
                                    sqlstr += " and  ( D_ID='" + dIds[i] + "' ";
                                } else {
                                    sqlstr += "or  D_ID='" + dIds[i] + "' ";
                                }
                                num = num + 1;
                            }
                        }
                        if (sqlstr.length() > 0) {
                            sqlstr += ")";
                            users.addAll(sysPositionUserService.getPositionUserIdbydids(sqlstr, customerId));
                        }
                    }
                }


                //给指定人员发送短信
                String actCarbonCopyUser = flowActivity.getActMassageUser();
                if (!ValidateUtil.isEmpty(actCarbonCopyUser)) {
                    String[] userid = actCarbonCopyUser.split(",");
                    users.addAll(java.util.Arrays.asList(userid));
                }
                //根据sql查询结果发送短信
                if (ValidateUtil.isNotEmpty(flowActivity.getActMassageSql())) {
                    String sql = flowActivity.getActMassageSql();
                    if (isQueryOnly(sql)) {
                        sql = sql.replace("[fileId]", fileId).replace("[actId]", flowActivity.getActid());
                        List<Map> userData = carbonCopyService.getDataToSql(sql);
                        if (!CollectionUtils.isEmpty(userData)) {
                            for (Map obj : userData
                            ) {
                                for (Object key : obj.keySet()) {
                                    if ("U_ID".toLowerCase(Locale.ENGLISH).equals(key.toString().toLowerCase(Locale.ENGLISH))) {
                                        if (ValidateUtil.isNotEmpty(obj.get(key) + "")) {
                                            String val = obj.get(key) + "";
                                            if (val.indexOf(",") != -1) {
                                                String[] ids = val.split(",");
                                                for (int i = 0; i < ids.length; i++) {
                                                    users.add(ids[i]);
                                                }
                                            } else {
                                                users.add(obj.get(key) + "");
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                //根据接口返回结果发送短信
                if (ValidateUtil.isNotEmpty(flowActivity.getActMassageInterfaceUrl()) && ValidateUtil.isNotEmpty(flowActivity.getActMassageRequestMethod())) {
                    BaseResponse baseResponse = null;
                    String urlStr = flowActivity.getActMassageInterfaceUrl();
                    urlStr = urlStr.replace("[fileId]", fileId).replace("[actId]", flowActivity.getActid());
                    HttpURLConnection connection = null;
                    BufferedReader in = null;
                    try {
                        URL url = new URL(urlStr);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setReadTimeout(10000);
                        connection.setRequestProperty("token", token);
                        connection.setRequestProperty("Token", token);
                        if ("post".equals(flowActivity.getActMassageRequestMethod().toLowerCase(Locale.ENGLISH))) {
                            connection.setRequestMethod("POST");
                            // 设置允许输入输出
                            connection.setDoOutput(true);
                            // 设置请求属性，例如Content-Type
                            connection.setRequestProperty("Content-Type", "application/json");
                            if (ValidateUtil.isNotEmpty(flowActivity.getActMassageRequestParameter())) {
                                String dat = flowActivity.getActMassageRequestParameter().replace("[fileId]", fileId).replace("[actId]", flowActivity.getActid());
                                String[] dat1 = dat.replace("{", "").replace("}", "").split(",");
                                Map<String, String> mapData = new HashMap<>();
                                for (String d : dat1) {
                                    if (ValidateUtil.isNotEmpty(d)) {
                                        String[] entry = d.split(":");
                                        mapData.put(entry[0].replaceAll("^\"|\"$", "").trim(), entry[1].trim());
                                    }
                                }
                                String postData = JacksonUtils.toJSONString(mapData);
                                // 写入POST数据
                                byte[] outputInBytes = postData.getBytes("UTF-8");
                                OutputStream os = connection.getOutputStream();
                                os.write(outputInBytes);
                                os.close();
                            }
                            HttpHeaders headers = new HttpHeaders();
                            MediaType type1 = MediaType.parseMediaType("application/json; charset=UTF-8");
                            headers.setContentType(type1);
                            headers.add("token", token);
                            headers.add("Accept", MediaType.APPLICATION_JSON.toString());

                        } else if ("get".equals(flowActivity.getActMassageRequestMethod().toLowerCase(Locale.ENGLISH))) {
                            connection.setRequestMethod("GET");
                        }
                        connection.connect();
                        if (connection.getResponseCode() == 200) {
                            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            if (null != in) {
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                ObjectMapper mapper = new ObjectMapper();
                                baseResponse = mapper.readValue(response.toString(), BaseResponse.class);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        if (null != in) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        connection.disconnect();
                    }
                    if (baseResponse != null) {
                        if (baseResponse.getSuccess()) {
                            //返回成功
                            Object returnData = baseResponse.getData();
                            if (returnData != null) {
                                if (returnData instanceof List) {
                                    List<Object> lis = (List<Object>) returnData;
                                    if (!CollectionUtils.isEmpty(lis)) {
                                        for (Object o : lis) {
                                            Map<String, String> en = (Map<String, String>) o;
                                            if (ValidateUtil.isNotEmpty(en.get("uId"))) {
                                                users.add(en.get("uId"));
                                            }
                                        }
                                    }
                                } else {
                                    if (returnData != null) {
                                        Map<String, String> en = (Map<String, String>) returnData;
                                        if (ValidateUtil.isNotEmpty(en.get("uId"))) {
                                            users.add(en.get("uId"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (users.size() > 0) {
                    String MassageTemplate = flowActivity.getActMassageTem();
                    if (!ValidateUtil.isEmpty(MassageTemplate)) {
                        //有短信模板信息
                        String newMassage = MassageGeneration(MassageTemplate, fileobj, taskNmae, MassageTemplateList.get("systemName").toString(), MassageTemplateList.get("systemNameShort").toString(), resultstr, opinion, userId, flowActivity.getActname());
                        List<SysUser> userobjlist = sysUserService.getUserAllByUids(users, customerId);
                        userobjlist.forEach(n -> {
                            String newMassagen = newMassage.replace("{审批人}", n.getuName());

                                //生成短信信息
                                MassageList.add(SetSysShortMessagePojo(newMassage, fileobj.gettCode(), n.getuId()));

                        });
                    }
                }
                try {
                    fl.write_to_txt(logSavePath, "平台消息保存数量："+MassageList.size());
                } catch (Exception ex) {
                    log.error("流程服务：", ex);
                }
                if (MassageList.size() > 0) {
                    MassageList.forEach(n -> {
                        SysMessageReceive  newObj=new  SysMessageReceive();
                        newObj.setId(GenerateUUID.getUUID());
                        newObj.setSmId(n.getId());
                        newObj.setSmrUser(n.getSmSendUser());
                        newObj.setSmrSendStatus("1");
                        newObj.setSmrSendTime(n.getCreateTime());
                        baseMapper.insert(n);
                        sysMessageReceiveService.save(newObj);

                    });
                }
            }
        } catch (Exception ex) {
            log.error("短信通知发送错误！", ex);
            try {
                fl.write_to_txt(logSavePath, "平台消息生成错误："+ex);
            } catch (Exception esx) {
                log.error("流程服务：", esx);
            }
        }
    }


    /**
     * 检验SQL语句是否只是查询语句
     *
     * @param sql SQL语句
     * @return 如果只包含SELECT关键字，返回true；否则返回false
     */
    // 正则表达式匹配仅包含SELECT关键字的查询语句
    private static final Pattern SELECT_PATTERN = Pattern.compile("^\\s*SELECT\\s+.*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    public static boolean isQueryOnly(String sql) {
        return SELECT_PATTERN.matcher(sql).matches();
    }

    @Override
    public void generateMassage(File fileobj, String taskNmae, String actId, String userId, String uName, String phoneNumber, String MassageCont, String actName, String resultstr, String opinion, String customerId) {
        if (ValidateUtil.isNotEmpty(MassageCont)) {
            List<String> codeList = new ArrayList<>();
            codeList.add("systemName");
            codeList.add("systemNameShort");
            //获取配置的默认短信信息
            Map<String, Object> MassageTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
            String newMassage = MassageGeneration(MassageCont, fileobj, taskNmae, MassageTemplateList.get("systemName").toString(), MassageTemplateList.get("systemNameShort").toString(), resultstr, opinion, userId, actName);
            baseMapper.insert(SetSysShortMessagePojo(newMassage, fileobj.gettCode(), userId));
        }
    }

    public SysMessage SetSysShortMessagePojo(String MassageContent, String code, String uId) {
        SysMessage obj = new SysMessage();
        obj.setId(GenerateUUID.getUUID());
        obj.setSmContent(MassageContent);
        obj.setSmtCode(code);
        obj.setSmTitle("流程审批消息通知");
        obj.setCreateTime(new Date());
        obj.setSmSendUser(uId);
        obj.setSmType("01");
        return obj;
    }


    public String MassageGeneration(String MassageTemplate, File fileObj, String taksName, String systemName, String systemNameShort, String resultstr, String opinion, String userId, String actName) {
        //短信处理
        MassageTemplate = MassageTemplate.replace("{任务名称}", taksName);
        MassageTemplate = MassageTemplate.replace("{办理时间}", (new Date()).toString());
        MassageTemplate = MassageTemplate.replace("{审批结果}", resultstr);
        MassageTemplate = MassageTemplate.replace("{系统名称}", systemName);
        MassageTemplate = MassageTemplate.replace("{系统简称}", systemNameShort);
        MassageTemplate = MassageTemplate.replace("{审批意见}", opinion);
        MassageTemplate = MassageTemplate.replace("{审批步骤}", actName);
        if (MassageTemplate.contains("{办理人}")) {
            SysUser userObj = sysUserService.getById(userId);
            if (userObj != null) {
                MassageTemplate = MassageTemplate.replace("{办理人}", userObj.getuName());
            }
        }
        if (MassageTemplate.contains("{项目名称}")) {
            if (!ValidateUtil.isEmpty(fileObj.getFileproject())) {
                String pName = baseMapper.selectPnameByPid(fileObj.getFileproject());
                MassageTemplate = MassageTemplate.replace("{项目名称}", pName);
            }

        }
        if (MassageTemplate.contains("{工程名称}")) {
            if (!ValidateUtil.isEmpty(fileObj.getFilesectionid())) {
                String secName = baseMapper.selectsecNameBySecId(fileObj.getFilesectionid());
                MassageTemplate = MassageTemplate.replace("{工程名称}", secName);
            }
        }
        if (MassageTemplate.contains("{合同名称}")) {
            if (!ValidateUtil.isEmpty(fileObj.getFilecontract())) {
                String conName = baseMapper.selectConNamaByConId(fileObj.getFilecontract());
                MassageTemplate = MassageTemplate.replace("{合同名称}", conName);
            }

        }
        return MassageTemplate;
    }


}
