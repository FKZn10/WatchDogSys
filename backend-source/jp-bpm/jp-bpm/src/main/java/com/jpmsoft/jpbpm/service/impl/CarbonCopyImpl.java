package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.SysCarbonCopyRequest;
import com.jpmsoft.common.model.request.TaskRequest;
import com.jpmsoft.common.model.request.UserCarbonCopyRequest;
import com.jpmsoft.common.model.response.CarbonCopyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.CarbonCopyMapper;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.dto.DepartmentPCjConfDTO;
import com.jpmsoft.jpbpm.pojo.dto.SysCcDto;
import com.jpmsoft.jpbpm.pojo.dto.SysCcSendDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import com.jpmsoft.jpbpm.pojo.vo.SysPositionPRoleAndUidVo;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import com.jpmsoft.jpbpm.util.DateUtil;
import dm.jdbc.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
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
public class CarbonCopyImpl extends ServiceImpl<CarbonCopyMapper, CarbonCopy> implements CarbonCopyService {
    @Resource
    private RestTemplate restTemplate;

    @Autowired
    SysConfigEntityService sysConfigEntityService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileService fileService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SysPositionUserService sysPositionUserService;

    @Autowired
    private SysPositionPRoleService sysPositionPRoleService;
    @Autowired
    private SysPositionRoleService sysPositionRoleService;
    @Autowired
    private FlowActivityService flowActivityService;
    // 正则表达式匹配仅包含SELECT关键字的查询语句
    private static final Pattern SELECT_PATTERN = Pattern.compile("^\\s*SELECT\\s+.*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    /**
     * 检验SQL语句是否只是查询语句
     *
     * @param sql SQL语句
     * @return 如果只包含SELECT关键字，返回true；否则返回false
     */
    public static boolean isQueryOnly(String sql) {
        return SELECT_PATTERN.matcher(sql).matches();
    }

    @Override
    public void GetCarbonCopyList(FlowActivity act, String user, String entityID, String title, String processID, List<String> cc_users, String type, String customerId, String token, String pId, String tCode, String year) {
        List<String> retList = new ArrayList<>();
        QueryWrapper<CarbonCopy> wrapper = new QueryWrapper<>();
        wrapper.eq("CC_ActID", act.getActid());
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<CarbonCopy> datas = baseMapper.selectList(wrapper);
        List<String> userList = datas.stream().map(CarbonCopy::getCcUser).collect(Collectors.toList());
        List<String> users = new ArrayList<>();
        //TODO: 流程设置中当前节点设置了默认抄送用户 待获取默认抄送用户 发送抄送信息
        //判断当前流程节点是否开启了自动抄送
        if (type.equals("1")) {
            if (act.getActcancarboncopy() != null && (act.getActcancarboncopy().equals("Automatic") || act.getActcancarboncopy().equals("Manual_Automatic"))) {
                //开启自动抄送
                //获取节点中设置的岗位抄送信息
                String actCarbonCopyPost = act.getActcarboncopyPost();
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
                //获取节点中设置的部门抄送信息
                String actCarbonCopyDepartment = act.getActcarboncopyDepartment();
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

                //角色抄送
                String roleStr = act.getActcarboncopyRole();
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


                //获取节点中设置的人员抄送信息
                String actCarbonCopyUser = act.getActcarboncopyUser();
                if (!ValidateUtil.isEmpty(actCarbonCopyUser)) {
                    String[] userid = actCarbonCopyUser.split(",");
                    users.addAll(java.util.Arrays.asList(userid));
                }
                //sql抄送集
                if (ValidateUtil.isNotEmpty(act.getActcarboncopySql())) {
                    String sql = act.getActcarboncopySql();
                    if (isQueryOnly(sql)) {
                        sql = sql.replace("[fileId]", entityID).replace("[actId]", act.getActid());
                        List<Map> userData = baseMapper.getDataToSql(sql);
                        if (!CollectionUtils.isEmpty(userData)) {
                            for (Map obj : userData
                            ) {
                                for (Object key : obj.keySet()) {
                                    if ("U_ID".toLowerCase(Locale.ENGLISH).equals(key.toString().toLowerCase(Locale.ENGLISH))) {
                                        users.add(obj.get(key) + "");
                                    }
                                }
                            }
                        }
                    }
                }


                //自定义接口抄送集
                if (ValidateUtil.isNotEmpty(act.getActInterfaceUrl()) && ValidateUtil.isNotEmpty(act.getActRequestMethod())) {
                    BaseResponse baseResponse = null;
                    String urlStr = act.getActInterfaceUrl();
                    urlStr = urlStr.replace("[fileId]", entityID).replace("[actId]", act.getActid());
                    HttpURLConnection connection = null;
                    BufferedReader in = null;
                    try {
                        URL url = new URL(urlStr);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setReadTimeout(10000);
                        connection.setRequestProperty("token", token);
                        connection.setRequestProperty("Token", token);
                        if ("post".equals(act.getActRequestMethod().toLowerCase(Locale.ENGLISH))) {
                            connection.setRequestMethod("POST");
                            // 设置允许输入输出
                            connection.setDoOutput(true);
                            // 设置请求属性，例如Content-Type
                            connection.setRequestProperty("Content-Type", "application/json");
                            if (ValidateUtil.isNotEmpty(act.getActRequestParameter())) {
                                String dat = act.getActRequestParameter().replace("[fileId]", entityID).replace("[actId]", act.getActid());
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

                        } else if ("get".equals(act.getActRequestMethod().toLowerCase(Locale.ENGLISH))) {
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

            }
            //抄送经办人，历史审批人
            String frontNode = act.getActcarboncopyFrontNode();
            if (!StringUtil.isEmpty(frontNode)) {
                if (frontNode.equals("01")) {
                    //发起人
                    File fileobj = fileService.getFileById(entityID, tCode, year);
                    if (fileobj != null) {
                        users.add(fileobj.getFilecreateuser());
                    }
                }
                if (frontNode.equals("02")) {
                    //历史审批人
                    List<Map<String, Object>> TaskProcessinlist = taskProcessinService.getSomeFieldsTaskProcessins(entityID, customerId, tCode, year);

                    if (TaskProcessinlist.size() > 0) {
                        for (Map<String, Object> tp : TaskProcessinlist
                        ) {
                            String userid = tp.get("tp_user") == null ? tp.get("TP_USER").toString() : tp.get("tp_user").toString();
                            if (!users.contains(userid)) {
                                users.add(userid);
                            }
                        }


                    }
                }
                if (frontNode.equals("03")) {
                    //发起人以及历史审批人
                    File fileobj = fileService.getFileById(entityID, tCode, year);
                    if (fileobj != null) {
                        users.add(fileobj.getFilecreateuser());
                    }
                    List<Map<String, Object>> TaskProcessinlist = taskProcessinService.getSomeFieldsTaskProcessins(entityID, customerId, tCode, year);

                    if (TaskProcessinlist.size() > 0) {
                        for (Map<String, Object> tp : TaskProcessinlist
                        ) {
                            String userid = tp.get("tp_user") == null ? tp.get("TP_USER").toString() : tp.get("tp_user").toString();
                            if (!users.contains(userid)) {
                                users.add(userid);
                            }
                        }


                    }
                }

                if (frontNode.equals("04") && ValidateUtil.isNotEmpty(act.getActApprovalNodeNumber())) {
                    //抄送给指定审批环节的审批人
                    List<Map<String, Object>> TaskProcessinlist = taskProcessinService.getSomeFieldsTaskProcessins(entityID, customerId, tCode, year);
                    if (!CollectionUtils.isEmpty(TaskProcessinlist)) {
                        for (Map obj : TaskProcessinlist
                        ) {
                            String actCode = "";
                            String uid = "";
                            for (Object key : obj.keySet()) {
                                if ("ActCode".toLowerCase(Locale.ENGLISH).equals(key.toString().toLowerCase(Locale.ENGLISH))) {
                                    actCode = obj.get(key) + "";
                                }
                                if ("tp_user".toLowerCase(Locale.ENGLISH).equals(key.toString().toLowerCase(Locale.ENGLISH))) {
                                    uid = obj.get(key) + "";
                                }
                            }
                            if (act.getActApprovalNodeNumber().equals(actCode) && ValidateUtil.isNotEmpty(uid)) {
                                users.add(uid);
                            }
                        }
                    }
                }
            }
        }

        if (cc_users != null && cc_users.size() > 0)
            users.addAll(cc_users);       // 用户自由选择的抄送用户
        List<CarbonCopy> pojos = new ArrayList<>();
        //用户信息去重
        users = users.stream().filter(n -> !ValidateUtil.isEmpty(n)).distinct().collect(Collectors.toList());


        List<SysUser> userInfoList = sysUserService.getUserAllByUids(users, customerId);
        //循环生成抄送信息
        users.forEach(u -> {


            CarbonCopy pojo = new CarbonCopy();
            pojo.setCcId(GenerateUUID.getUUID());
            pojo.setCcActid(act.getActid());
            pojo.setCcUser(u);
            pojo.setCcFlowid(act.getActflowid());
            pojo.setCcTime(new Date());
            pojo.setCcCreateuser(user);
            pojo.setCcRead("未阅");
            pojo.setCcReadtime(null);
            pojo.setCcTitle(title);
            pojo.setCcFileid(entityID);
            pojo.setCcProcessid(processID);
            pojo.setCcExplain(null);
            pojo.setCcExplain(null);
            if (cc_users.contains(u)) {
                pojo.setIsSystem("user");
            } else {
                pojo.setIsSystem("system");
            }
            if (!"All".equals(customerId)) {
                pojo.setTeId(customerId);
            }
            pojos.add(pojo);
        });
        if (pojos.size() > 0) {
            this.saveBatch(pojos);
        }
    }

    @Override
    public BaseResponse CctransactionSend(SysCcSendDto request, String customerId) {
        String taskid = request.getTaskid();//任务id
        String user = request.getUserId();//当前用户
        List<String> ccuser = request.getCcIds();//抄送用户
        String fileid = request.getFileId();//流程实例id
        Task taskobjen = taskService.getById(taskid);
        if (taskobjen == null) {
            //获取当前用户最近的一次审批信息
            String yearStr = "";
            File fileObj = fileService.getFileById(fileid, request.gettCode(), request.getYear());
            if (fileObj != null && fileObj.getFilecreatedate() != null) {
                yearStr = fileObj.getFilecreatedate().getYear() + "";
            }
            QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
            wrapper.eq("tp_entityid", fileid);
            wrapper.eq("tp_acttype", "userTask");
            wrapper.eq("tp_type", "提交");
            wrapper.orderByDesc("tp_dotime");
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            List<TaskProcessin> TaskProcessin = taskProcessinService.getList(wrapper, request.gettCode(), yearStr);
            if (TaskProcessin.size() > 0) {
                taskobjen = new Task();
                taskobjen.setTaskcurrentactid(TaskProcessin.get(0).getTpActid());
                taskobjen.setTaskentityid(fileid);
                taskobjen.setTaskflow(fileObj.getFlowId());
                taskobjen.setTaskid(TaskProcessin.get(0).getTpTaskid());
            }
        }
        if (taskobjen != null) {
            FlowActivity FlowActivityobj = flowActivityService.getById(taskobjen.getTaskcurrentactid());
            List<CarbonCopy> pojos = new ArrayList<>();
            //用户信息去重
            ccuser = ccuser.stream().filter(n -> !ValidateUtil.isEmpty(n)).distinct().collect(Collectors.toList());
            String taskName = taskobjen.getTaskname();
            String taskId = taskobjen.getTaskid();
            //循环生成抄送信息
            ccuser.forEach(u -> {
                CarbonCopy pojo = new CarbonCopy();
                pojo.setCcId(GenerateUUID.getUUID());
                pojo.setCcActid(FlowActivityobj.getActid());
                pojo.setCcUser(u);
                pojo.setCcTitle(request.getTaskname());
                pojo.setCcFlowid(FlowActivityobj.getActflowid());
                pojo.setCcTime(new Date());
                pojo.setCcCreateuser(user);
                pojo.setCcRead("未阅");
                pojo.setCcReadtime(null);
                pojo.setCcFileid(fileid);
                pojo.setCcProcessid(taskId);
                pojo.setCcExplain(null);
                pojo.setIsSystem("user");
                if (!"All".equals(customerId)) {
                    pojo.setTeId(customerId);
                }
                pojos.add(pojo);
            });
            if (pojos.size() > 0) {
                this.saveBatch(pojos);
            }
            return new BaseResponse(true, "抄送成功!");
        } else {
            return new BaseResponse("抄送失败!");
        }
    }

    @Override
    public List<Map> getDataToSql(String sqlStr) {
        return baseMapper.getDataToSql(sqlStr);
    }

    @Override
    public BaseResponse Cctransaction(SysCcDto requst, String customerId) {
        //抄送办理
        CarbonCopy obj = baseMapper.selectById(requst.getCcId());
        obj.setCcExplain(requst.getCcIdea());
        obj.setCcExplaintime(new Date());
        baseMapper.updateById(obj);
        return new BaseResponse(true, "提交成功！", obj.getCcId());
    }


    @Override
    public BaseResponse CctransactionRead(SysCcDto requst, String customerId) {
        //抄送办理
        CarbonCopy obj = baseMapper.selectById(requst.getCcId());
        obj.setCcRead("已阅");
        obj.setCcReadtime(new Date());
        baseMapper.updateById(obj);
        return new BaseResponse(true, "");
    }


    @Override
    public BaseResponse Cclist(TaskRequest request, String customerId) {

        QueryWrapper<CarbonCopy> wrapper = new QueryWrapper<>();
        if (request.getUserid() != null || request.getUserid().equals("") == false)
            wrapper.eq("CC_User", request.getUserid());

        if (request.getStatusstr() != null || request.getStatusstr().equals("") == false)
            wrapper.eq("CC_Read", request.getUserid());

        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        IPage<CarbonCopy> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return new BaseResponse("获取成功", iPage, total.intValue());
    }


    @Override
    public List<CarbonCopy> GetCarbonCopyPojoByFileid(String Fileid, String customerId) {
        QueryWrapper<CarbonCopy> wrapper = new QueryWrapper<>();
        wrapper.eq("CC_FileID", Fileid);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<CarbonCopy> CarbonCopy = baseMapper.selectList(wrapper);
        return CarbonCopy;
    }

    @Override
    public Integer getUserCarbonCopyCount(String userId, String customerId) {
        QueryWrapper<CarbonCopy> wrapper = new QueryWrapper<>();
        wrapper.eq("CC_User", userId);
        wrapper.eq("CC_Read", "未阅");
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectCount(wrapper).intValue();
    }

    @Override
    public List<AgencyStatisticsVo> statisticsUserAgency(AgencyStatisticsDto request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("uId", request.getTCodes());
        params.put("tCodes", request.getUId());
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<AgencyStatisticsVo> data = baseMapper.statisticsUserAgency(request, customerId);
        return data;
    }


    @Override
    public BaseResponse getUserCarbonCopyListByfileid(FilerecordRequest requst, String customerId) {
        QueryWrapper<CarbonCopy> wrapper = new QueryWrapper<>();
        wrapper.eq("CC_FileID", requst.getEntityid());
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<CarbonCopy> CarbonCopy = baseMapper.selectList(wrapper);
        List<String> userid = CarbonCopy.stream().map(n -> n.getCcUser()).collect(Collectors.toList());
        List<String> useridsend = CarbonCopy.stream().map(n -> n.getCcCreateuser()).collect(Collectors.toList());
        userid.addAll(useridsend);
        List<SysUser> users = sysUserService.getUserAllByUids(userid, customerId);
        List<CarbonCopyResponse> newData = new ArrayList<>();
        //获取基础配置信息
        List<String> codeList = new ArrayList<>();
        codeList.add("externalUsersNotsee");
        Map<String, Object> configStr = sysConfigEntityService.getConfigEntity(codeList, customerId);
        String externalUsersNotsee = "";

        if (configStr.get("externalUsersNotsee") != null) {
            externalUsersNotsee = configStr.get("externalUsersNotsee").toString();
        }
        CarbonCopy.forEach(data -> {

            CarbonCopyResponse objtask = new CarbonCopyResponse();
            BeanUtils.copyProperties(data, objtask);
            if (!ValidateUtil.isEmpty(data.getCcUser())) {
                List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getCcUser())).collect(Collectors.toList());
                if (user != null && user.size() > 0)
                    objtask.setCcUserName(user.get(0).getuName());
                objtask.setCcUserType(user.get(0).getuType());
            }
            if (!ValidateUtil.isEmpty(data.getCcCreateuser())) {
                List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getCcCreateuser())).collect(Collectors.toList());
                if (user != null && user.size() > 0)
                    objtask.setCcCreateuser(user.get(0).getuName());
            }
            newData.add(objtask);
        });
        List<CarbonCopyResponse> newDataentity = newData.stream().sorted(Comparator.comparing(CarbonCopyResponse::getCcTime).reversed()).collect(Collectors.toList());
        return new BaseResponse("获取数据成功！", newDataentity, newDataentity.size());
    }

    @Override
    public BaseResponse getUserCarbonCopyList(String userId, UserCarbonCopyRequest request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "CC_Time");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "DESC");

        params.put("ccTitle", !ValidateUtil.isEmpty(request.getCcTitle()) ? request.getCcTitle() : null);
        params.put("ccTitles", !ValidateUtil.isEmpty(request.getCcTitles()) ? request.getCcTitles() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("pNames", !ValidateUtil.isEmpty(request.getpNames()) ? request.getpNames() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        params.put("conNames", !ValidateUtil.isEmpty(request.getConNames()) ? request.getConNames() : null);
        params.put("ccCreateName", !ValidateUtil.isEmpty(request.getCcCreateName()) ? request.getCcCreateName() : null);
        params.put("filecreateusernames", !ValidateUtil.isEmpty(request.getFilecreateusernames()) ? request.getFilecreateusernames() : null);
        params.put("ccSendStartTime", !ValidateUtil.isEmpty(request.getCcSendStartTime()) ? DateUtil.DateAddHMS(request.getCcSendStartTime(), 0, 0, 0) : null);
        params.put("ccSendEndTime", !ValidateUtil.isEmpty(request.getCcSendEndTime()) ? DateUtil.DateAddHMS(request.getCcSendEndTime(), 23, 59, 59) : null);
        params.put("ccReadStartTime", !ValidateUtil.isEmpty(request.getCcReadStartTime()) ? DateUtil.DateAddHMS(request.getCcReadStartTime(), 0, 0, 0) : null);
        params.put("ccReadEndTime", !ValidateUtil.isEmpty(request.getCcReadEndTime()) ? DateUtil.DateAddHMS(request.getCcReadEndTime(), 23, 59, 59) : null);
        params.put("ccRead", !ValidateUtil.isEmpty(request.getCcRead()) ? request.getCcRead() : null);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<CarbonCopyResponse> datas = baseMapper.getUserCarbonCopyList(params);
        Integer total = baseMapper.getUserCarbonCopyListCount(params);
        if ("CJ".equals(request.getPmType())) {
            Map<String, Object> returnData = new HashMap<>();

            List<String> fileIds = datas.stream().map(n -> n.getCcFileid()).distinct().collect(Collectors.toList());
            List<SysUser> users = sysUserService.getAllUser(customerId);
            List<String> actIds = new ArrayList<>();
            List<Task> taskList = taskService.getTaskListByFiles(fileIds);
            List<FlowActivity> actList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(taskList)) {
                actIds.addAll(taskList.stream().map(n -> n.getTaskcurrentactid()).distinct().collect(Collectors.toList()));
            }
            if (!CollectionUtils.isEmpty(actIds)) {
                actList = flowActivityService.listByIds(actIds);
            }

            List<CarbonCopyResponse> newData = new ArrayList<>();
            for (CarbonCopyResponse data : datas) {


                //获取任务名称
                List<Task> tList = taskList.stream().filter(n -> n.getTaskentityid().equals(data.getCcFileid())).collect(Collectors.toList());
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
            returnData.put("list", newData);
            List<Map<String, Object>> keys = new ArrayList<>();
            Map<String, Object> obj = new HashMap<>();
            obj.put("keys", baseMapper.getUserCarbonCopyTitle(params, "cc.CC_Title"));
            obj.put("name", "任务名称");
            obj.put("code", "ccTitles");
            obj.put("type", "string");
            keys.add(obj);
            Map<String, Object> obj2 = new HashMap<>();
            obj2.put("keys", baseMapper.getUserCarbonCopyTitle(params, "pro.P_Name"));
            obj2.put("name", "所属项目");
            obj2.put("code", "pNames");
            obj2.put("type", "string");
            keys.add(obj2);
            Map<String, Object> obj0 = new HashMap<>();
            obj0.put("keys", baseMapper.getUserCarbonCopyTitle(params, "f.FileCreateUserName"));
            obj0.put("name", "发起人");
            obj0.put("code", "filecreateusernames");
            obj0.put("type", "string");
            keys.add(obj0);
            Map<String, Object> obj1 = new HashMap<>();
            obj1.put("keys", "");
            obj1.put("name", "阅读时间");
            obj1.put("code", "ccReadtime");
            obj1.put("type", "data");
            keys.add(obj1);
            Map<String, Object> obj3 = new HashMap<>();
            obj3.put("keys", "");
            obj3.put("name", "接收时间");
            obj3.put("code", "ccTime");
            obj3.put("type", "data");
            keys.add(obj3);
            returnData.put("keys", keys);
            return BaseResponse.ok(returnData, total);
        }
        return new BaseResponse("获取成功", datas, total);
    }

    @Override
    public BaseResponse getCarbonCopyList(SysCarbonCopyRequest request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "CC_Time");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "DESC");

        params.put("ccTitle", !ValidateUtil.isEmpty(request.getCcTitle()) ? request.getCcTitle() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        params.put("ccCreateName", !ValidateUtil.isEmpty(request.getCcCreateName()) ? request.getCcCreateName() : null);
        params.put("ccSendStartTime", !ValidateUtil.isEmpty(request.getCcSendStartTime()) ? DateUtil.DateAddHMS(request.getCcSendStartTime(), 0, 0, 0) : null);
        params.put("ccSendEndTime", !ValidateUtil.isEmpty(request.getCcSendEndTime()) ? DateUtil.DateAddHMS(request.getCcSendEndTime(), 23, 59, 59) : null);
        params.put("ccRead", !ValidateUtil.isEmpty(request.getCcRead()) ? request.getCcRead() : null);
        params.put("ccUserName", !ValidateUtil.isEmpty(request.getCcUserName()) ? request.getCcUserName() : null);
        params.put("teId", "All".equals(customerId) ? null : customerId);
        List<CarbonCopyResponse> datas = baseMapper.getCarbonCopyList(params);
        Integer total = baseMapper.getCarbonCopyListCount(params);
        return new BaseResponse("获取成功", datas, total);
    }


    //自由抄送
    public BaseResponse FreeCC(SysCcSendDto request, String customerId) {
        String user = request.getUserId();//当前用户
        List<String> ccuser = request.getCcIds();//抄送用户
        String fileid = request.getFileId();//流程实例id
        List<CarbonCopy> pojos = new ArrayList<>();
        //用户信息去重
        ccuser = ccuser.stream().filter(n -> !ValidateUtil.isEmpty(n)).distinct().collect(Collectors.toList());
        //循环生成抄送信息
        ccuser.forEach(u -> {
            CarbonCopy pojo = new CarbonCopy();
            pojo.setCcId(GenerateUUID.getUUID());
            pojo.setCcActid("");
            pojo.setCcUser(u);
            pojo.setCcTitle(request.getTaskname());
            pojo.setCcFlowid("");
            pojo.setCcTime(new Date());
            pojo.setCcCreateuser(user);
            pojo.setCcRead("未阅");
            pojo.setCcReadtime(null);
            pojo.setCcFileid(fileid);
            pojo.setCcProcessid("");
            pojo.setCcExplain(null);
            pojo.setIsSystem("user");
            if (!"All".equals(customerId)) {
                pojo.setTeId(customerId);
            }
            pojos.add(pojo);
        });
        if (pojos.size() > 0) {
            this.saveBatch(pojos);
        }
        return new BaseResponse(true, "抄送成功!");
    }

    /**
     * 根据entityId删除抄送记录
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteCarbonCopyByEntityId(SysCcSendDto request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail("参数错误");
        }
        this.remove(Wrappers.<CarbonCopy>lambdaQuery().eq(CarbonCopy::getCcFileid, request.getFileId()));
        return BaseResponse.ok();
    }

    /**
     * 发送监理现场用表抄送
     *
     * @param request
     * @param uId
     * @param customerId
     * @return
     */
    @Override
    public BaseResponse sendSupervisionCarbonCopy(SysCcSendDto request, String uId, String customerId) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getUnitIds()) || ValidateUtil.isAnObjEmpty(request.getpId(), request.getFileId())) {
            return BaseResponse.fail("参数错误");
        }
        DepartmentPCjConfDTO conf = sysConfigEntityService.getDepartmentPCjConf();
        // 需要抄送的部门编码
        List<String> depCodes = Arrays.asList(
                conf.getCjdwCode(), conf.getJsdwCode(), conf.getJldwCode(), conf.getSgdwCode(),
                conf.getKcdwCode(), conf.getSjdwCode(), conf.getGzsjdwCode()
        );
        // 需要抄送的岗位编码
        List<String> positionCodes = Arrays.asList(
                conf.getJsXmfzrCode(), conf.getZjlgcsCode(), conf.getSgxmjlCode(), conf.getKcxmfzrCode(),
                conf.getSjxmfzrCode(), conf.getXmsjfzrCode()
        );
        // 查询需要抄送的人员
        List<String> uIds = baseMapper.getSupervisionCarbonCopyUids(depCodes, positionCodes, request.getpId(), request.getUnitIds());
        // 查询file信息
        File file = fileService.getById(request.getFileId());
        if (!file.getFileflowstatus().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
            return BaseResponse.fail("当前流程状态不允许抄送！");
        }
        String fileName = file.getFiletitle();
        // 抄送
        SysCcSendDto dto = new SysCcSendDto();
        dto.setTaskid(GenerateUUID.getUUID());
        dto.setUserId(uId);
        dto.setFileId(request.getFileId());
        dto.setTaskname(fileName);
        dto.setCcIds(uIds);
        return CctransactionSend(dto, customerId);
    }
}
