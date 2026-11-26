package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.AuditTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.SysFileRequest;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.response.SysFileResponse;
import com.jpmsoft.common.rabbit.AuditSender;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.config.RbFanout;
import com.jpmsoft.jpbpm.config.RequestDataHelper;
import com.jpmsoft.jpbpm.constants.BpmMsg;
import com.jpmsoft.jpbpm.mapper.FileMapper;
import com.jpmsoft.jpbpm.pojo.dto.*;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.ActityinfoVo;
import com.jpmsoft.jpbpm.pojo.vo.FileSecidVo;
import com.jpmsoft.jpbpm.pojo.vo.FileSecidsVo;
import com.jpmsoft.jpbpm.pojo.vo.SysPositionPRoleAndUidVo;
import com.jpmsoft.jpbpm.service.*;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import com.jpmsoft.jpbpm.util.fileLog;
import dm.jdbc.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Value("${bpm.logSavePath}")
    public String logSavePath;
    @Autowired
    private CarbonCopyService carbonCopyService;
    @Autowired
    private SysCoordinationProcessService sysCoordinationProcessService;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    private FileAnnexService fileAnnexService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FileTemplateService fileTemplateService;
    @Autowired
    private PreProjectChangeNameService preProjectChangeNameService;
    @Autowired
    private OaProblemFeedbackService oaProblemFeedbackService;
    @Autowired
    private GanttTasksProcessService ganttTasksProcessService;
    @Autowired
    private SysMultiFormFlowService sysMultiFormFlowService;
    @Autowired
    private SecInfoService secInfoService;
    @Autowired
    private FlowActivityService flowActivityService;
    @Autowired
    private FlowRuleService flowRuleService;
    @Autowired
    private SysConfigEntityService sysConfigEntityService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private SysTaskProcessinTimeService sysTaskProcessinTimeService;
    @Autowired
    private FlowUserService flowUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPositionUserService sysPositionUserService;
    @Autowired
    private SysPositionRoleService sysPositionRoleService;
    @Resource
    private RbFanout fanout;
    @Autowired
    private SysPositionPRoleService sysPositionPRoleService;
    @Resource
    private AuditSender auditSender;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SecBiddingProcessingService secBiddingProcessingService;
    @Autowired
    private SysJiMuReportService sysJiMuReportService;
    @Autowired
    private SysSplitTableDtlService sysSplitTableDtlService;
    @Autowired
    private SysProcessInvalidationRecordService sysProcessInvalidationRecordService;

    String tableName = "SYS_File";


    public String getTableName(String cTode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(cTode, year, tableName);
        return tableCode;
    }

    @Override
    @Transactional
    public File getFileById(String fileId, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            File obj = baseMapper.selectById(fileId);
            if (obj != null) {
                return obj;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        if (tableCode.equals(tableName)) {
            return null;
        }
        //存在分表信息

        RequestDataHelper.setRequestData(tableCode);
        File obj = baseMapper.selectById(fileId);
        RequestDataHelper.clear();
        if (obj == null) {
            obj = baseMapper.selectById(fileId);
        }
        return obj;

    }

    @Override
    @Transactional
    public List<File> getListByIds(List<String> fileIds, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            List<File> lis = baseMapper.selectBatchIds(fileIds);
            if (CollectionUtils.isNotEmpty(lis)) {
                return lis;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息

        RequestDataHelper.setRequestData(tableCode);

        List<File> obj = baseMapper.selectBatchIds(fileIds);
        RequestDataHelper.clear();
        if (CollectionUtils.isEmpty(obj)) {
            obj = baseMapper.selectBatchIds(fileIds);
        }
        return obj;

    }

    @Override
    @Transactional
    public List<File> getList(QueryWrapper<File> queryWrapper, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            List<File> lis = baseMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(lis)) {
                return lis;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);
        List<File> obj = baseMapper.selectList(queryWrapper);
        RequestDataHelper.clear();
        if (CollectionUtils.isEmpty(obj)) {
            obj = baseMapper.selectList(queryWrapper);
        }
        return obj;
    }

    @Override
    @Transactional
    public List<File> getList(LambdaQueryWrapper<File> queryWrapper, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            List<File> lis = baseMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(lis)) {
                return lis;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        RequestDataHelper.setRequestData(tableCode);
        List<File> obj = baseMapper.selectList(queryWrapper);
        RequestDataHelper.clear();
        if (CollectionUtils.isEmpty(obj)) {
            obj = baseMapper.selectList(queryWrapper);
        }
        return obj;
    }

    @Override
    @Transactional
    public List<File> getList(QueryWrapper<File> queryWrapper, List<String> tCodes, String year) {
        List<File> fileList = new ArrayList<>();
        for (String tCode : tCodes) {
            String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
            if (tableCode.equals(tableName)) {
                List<File> obj1 = baseMapper.selectList(queryWrapper);
                if (CollectionUtils.isNotEmpty(obj1)) {
                    //去重
                    fileList.addAll(obj1);
                } else {
                    String tableCode1 = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
                    RequestDataHelper.setRequestData(tableCode1);
                    List<File> obj2 = baseMapper.selectList(queryWrapper);

                    if (CollectionUtils.isNotEmpty(obj2)) {
                        fileList.addAll(obj2);
                    }
                }
            } else {
                RequestDataHelper.setRequestData(tableCode);
                List<File> obj = baseMapper.selectList(queryWrapper);

                if (CollectionUtils.isEmpty(obj)) {
                    obj = baseMapper.selectList(queryWrapper);
                }
                fileList.addAll(obj);
            }
        }
        fileList = duplicateRemoval(fileList);
        return fileList;
    }

    @Override
    @Transactional
    public List<File> getList(LambdaQueryWrapper<File> queryWrapper, List<String> tCodes, String year) {
        List<File> fileList = new ArrayList<>();
        fileList.addAll(baseMapper.selectList(queryWrapper));
        for (String tCode : tCodes) {
            String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
            if (tableCode.equals(tableName)) {
                List<File> obj1 = baseMapper.selectList(queryWrapper);
                if (CollectionUtils.isNotEmpty(obj1)) {
                    fileList.addAll(obj1);
                } else {
                    String tableCode1 = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
                    RequestDataHelper.setRequestData(tableCode1);
                    List<File> obj2 = baseMapper.selectList(queryWrapper);

                    if (CollectionUtils.isNotEmpty(obj2)) {
                        fileList.addAll(obj2);
                    }
                }
            } else {
                RequestDataHelper.setRequestData(tableCode);
                List<File> obj = baseMapper.selectList(queryWrapper);

                if (CollectionUtils.isEmpty(obj)) {
                    obj = baseMapper.selectList(queryWrapper);
                }
                fileList.addAll(obj);
            }
        }
        fileList = duplicateRemoval(fileList);
        return fileList;
    }


    public List<File> duplicateRemoval(List<File> fileList) {
        List<File> duplicateList = new ArrayList<>();
        //去重
        fileList.forEach(n -> {
            List<File> lis = duplicateList.stream().filter(k -> k.getFileid().equals(n.getFileid())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(lis)) {
                duplicateList.add(n);
            }
        });
        return duplicateList;
    }

    @Override
    @Transactional
    public boolean updateFileById(File file, String tCode, String year) {
        Boolean type = false;
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            type = baseMapper.updateById(file) == 1 ? true : false;
            if (type == false) {
                String tableCode1 = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
                RequestDataHelper.setRequestData(tableCode1);
                type = baseMapper.updateById(file) == 1 ? true : false;

            }
        } else {
            //存在分表信息
            RequestDataHelper.setRequestData(tableCode);
            type = baseMapper.updateById(file) == 1 ? true : false;


            if (type == false) {
                type = baseMapper.updateById(file) == 1 ? true : false;
            }
        }
        return type;
    }

    @Override
    @Transactional
    public boolean insertFile(File file, String tCode, String year) {
        Boolean type = false;
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            type = baseMapper.insert(file) == 1 ? true : false;
            if (type == false) {
                String tableCode1 = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
                RequestDataHelper.setRequestData(tableCode1);
                type = baseMapper.insert(file) == 1 ? true : false;

            }
        } else {
            //存在分表信息
            RequestDataHelper.setRequestData(tableCode);
            type = baseMapper.insert(file) == 1 ? true : false;

        }
        return type;
    }

    @Override
    @Transactional
    public boolean saveFileBatch(List<File> files, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            files.forEach(file -> {
                baseMapper.insert(file);
            });
        } else {
            //存在分表信息
            RequestDataHelper.setRequestData(tableCode);
            files.forEach(file -> {
                baseMapper.insert(file);
            });

        }
        return true;
    }

    @Override
    @Transactional
    public BaseResponse updatefile(String tCode, String file_id, String style, String flow_id, String customerId, String token, String year) {
        fileLog fl = new fileLog();
        try {
            fl.write_to_txt(logSavePath, "进入file表流程状态修改方法：fileID" + file_id);
        } catch (Exception ex) {
            log.error("流程服务：", ex);
        }
        int flowStatus = getStatus(style);
        if (!style.equals("addexplain") && !style.equals("superviseflow")) {
            baseMapper.updateFileFlowStatusByFileId(getTableName(tCode, year), flowStatus, file_id, flow_id);
            sysTaskProcessinTimeService.updateTaskProcessinTime(new Date(), flowStatus + "", file_id, customerId);
            File fileObj = getFileById(file_id, tCode, year);
            Integer states = 0;
            if (flowStatus == 7) {
                states = 3;
            } else if (flowStatus == 1 || flowStatus == 2 || flowStatus == 4 || flowStatus == 9) {
                states = 2;
            }
            secBiddingProcessingService.updateSecBiddingProcessing(file_id, fileObj.getFilesectionid(), tCode, states, customerId);
            try {
                fl.write_to_txt(logSavePath, "流程状态修改完成，修改值为“" + flowStatus + "”：fileID" + file_id);
            } catch (Exception ex) {
                log.error("流程服务：", ex);
            }
            // 如果是补充、督办意见，则不修改文档的流程状态
            //多流程表单时，同步主流程状态
            fileStatusUpdate(file_id, flowStatus, customerId, tCode, year);
            //synchronizationFile(file_id, flowStatus);

            BaseResponse baseResponse = syncFileAndBusinessState(tCode, file_id, flowStatus, flow_id, customerId, token, year);
            if (flowStatus == 7) {
                sysJiMuReportService.createSysJiMuReport(file_id, tCode, fileObj.gettId(), customerId);
            }
            try {
                fl.write_to_txt(logSavePath, "流程状态修改完成，方法执行完成”：fileID" + file_id);
            } catch (Exception ex) {
                log.error("流程服务：", ex);
            }
            return baseResponse;
        }

        return new BaseResponse(true, "", flowStatus);
    }

    public int getStatus(String style) {
        int flowStatus = -1;
        if (style.equals("end") || style.equals("endfreeflow") || style.equals("finish"))
            flowStatus = ApprovalStatusEnum.Already_Over.getIndex();
        else if (style.equals("pre")) flowStatus = ApprovalStatusEnum.Returned.getIndex();
        else if (style.equals("start")) flowStatus = ApprovalStatusEnum.Returned_Initiator.getIndex();
        else if (style.equals("stop")) flowStatus = ApprovalStatusEnum.Invalid.getIndex();
        else if (style.equals("next") || style.equals("refuse") || style.equals("changeoperator") || style.equals("skip") || style.equals("startfreeflow") || style.equals("submitfreeflow") || style.equals("startfreeflow_assign") || style.equals("backfreeflow"))
            flowStatus = ApprovalStatusEnum.Under_Approval.getIndex();
        else if (style.equals("new")) flowStatus = ApprovalStatusEnum.Started.getIndex();
        else flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
        return flowStatus;
    }

    @Override
    @Transactional
    public BaseResponse updateFileBath(List<File> fileList, String style, String token, String year) {
        fileLog fl = new fileLog();
        int flowStatus = getStatus(style);

        if (!style.equals("addexplain") && !style.equals("superviseflow")) {
            //
            List<String> tCodes = fileList.stream().map(File::gettCode).distinct().collect(Collectors.toList());
            for (String tCode : tCodes) {
                List<File> files = fileList.stream().filter(file -> file.gettCode().equals(tCode)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(files)) {
                    List<String> fileIds = files.stream().map(File::getFileid).distinct().collect(Collectors.toList());
                    baseMapper.updateFileFlowStatusByFileIdList(getTableName(tCode, year), flowStatus, fileIds);
                    if (flowStatus == 7) {
                        List<String> allFileIds = fileList.stream().map(File::getFileid).distinct().collect(Collectors.toList());
                        sysJiMuReportService.createSysJiMuReportBatch(allFileIds, tCode, files.get(0).gettId(), files.get(0).getTeId());
                    }
                    String ids = "'" + String.join("','", fileIds) + "'";
                    BaseResponse baseResponse = syncFileAndBusinessStateBatch(tCode, ids, flowStatus, files.get(0).getTeId(), token, year);
                }

            }
        }
        return new BaseResponse(true, "", flowStatus);

    }


    @Override
    public BaseResponse getFileList(SysFileRequest request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : null);
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : null);

        params.put("filetitle", !ValidateUtil.isEmpty(request.getFiletitle()) ? request.getFiletitle() : null);
        params.put("filetitles", !ValidateUtil.isEmpty(request.getFiletitles()) ? request.getFiletitles() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("pNames", !ValidateUtil.isEmpty(request.getpNames()) ? request.getpNames() : null);
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        //params.put("conNames", !ValidateUtil.isEmpty(request.getConNames()) ? request.getConNames() : null);
        params.put("tName", !ValidateUtil.isEmpty(request.gettName()) ? request.gettName() : null);
        //params.put("tNames", !ValidateUtil.isEmpty(request.gettNames()) ? request.gettNames() : null);
        params.put("tCode", ValidateUtil.isNotEmpty(request.gettCode()) ? request.gettCode() : null);
        params.put("secName", !ValidateUtil.isEmpty(request.getSecName()) ? request.getSecName() : null);
        // params.put("secNames", !ValidateUtil.isEmpty(request.getSecNames()) ? request.getSecNames() : null);
        params.put("cName", !ValidateUtil.isEmpty(request.getcName()) ? request.getcName() : null);
        //params.put("cNames", !ValidateUtil.isEmpty(request.getcNames()) ? request.getcNames() : null);
        params.put("started", !CheckNullUtil.checkObject(request.getStarted()) ? request.getStarted() : null);
        params.put("uId", !CheckNullUtil.checkObject(request.getuId()) ? request.getuId() : null);
        params.put("uName", !ValidateUtil.isEmpty(request.getuName()) ? request.getuName() : null);
        //params.put("uNames", !ValidateUtil.isEmpty(request.getuNames()) ? request.getuNames() : null);
        params.put("pBuildModel", !ValidateUtil.isEmpty(request.getpBuildModel()) ? request.getpBuildModel() : null);
        params.put("pMajorType", !ValidateUtil.isEmpty(request.getpMajorType()) ? request.getpMajorType() : null);
        params.put("pType", !ValidateUtil.isEmpty(request.getpType()) ? request.getpType() : null);
        params.put("filecode", !ValidateUtil.isEmpty(request.getFilecode()) ? request.getFilecode() : null);
        params.put("fileCreateDate", !ValidateUtil.isEmpty(request.getFileCreateDate()) ? request.getFileCreateDate() : null);
        params.put("taskName", !ValidateUtil.isEmpty(request.getTaskName()) ? request.getTaskName() : null);
        //params.put("taskNames", !ValidateUtil.isEmpty(request.getTaskNames()) ? request.getTaskNames() : null);
        params.put("fileflowstatus", !ValidateUtil.isEmpty(request.getFileflowstatus()) ? request.getFileflowstatus() : null);
        params.put("fileflowstatuses", !ValidateUtil.isEmpty(request.getFileflowstatuses()) ? request.getFileflowstatuses() : null);
        if (request.getFileCreateDates() != null) {
            params.put("startTime", !ValidateUtil.isEmpty(request.getFileCreateDates().get(0)) ? request.getFileCreateDates().get(0) : null);
            params.put("endTime", !ValidateUtil.isEmpty(request.getFileCreateDates().get(1)) ? request.getFileCreateDates().get(1) : null);
        } else {
            params.put("startTime", null);
            params.put("endTime", null);
        }
        params.put("teId", "All".equals(customerId) ? null : customerId);
        params.put("fileid", !ValidateUtil.isEmpty(request.getFileid()) ? request.getFileid() : null);
        List<SysFileResponse> datas = baseMapper.getFileList(params);
        List<String> entityIds = datas.stream().map(n -> n.getFileid()).distinct().collect(Collectors.toList());
        List<Task> taskList = taskService.getTaskListByFiles(entityIds);
        List<String> uIds = new ArrayList<>();
        List<String> actIds = new ArrayList<>();
        if (!org.springframework.util.CollectionUtils.isEmpty(taskList)) {
            uIds.addAll(taskList.stream().map(l -> l.getTaskuser()).collect(Collectors.toList()));
            actIds.addAll(taskList.stream().map(n -> n.getTaskcurrentactid()).distinct().collect(Collectors.toList()));
        }
        List<FlowActivity> actList = new ArrayList<>();
        List<SysUser> users = new ArrayList<>();

        if (!org.springframework.util.CollectionUtils.isEmpty(actIds)) {
            actList = flowActivityService.listByIds(actIds);
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(uIds)) {
            users = sysUserService.listByIds(uIds);
        }
        List<SysFileResponse> newData = new ArrayList<>();
        for (SysFileResponse data : datas) {
            List<Task> tList = taskList.stream().filter(n -> n.getTaskentityid().equals(data.getFileid())).collect(Collectors.toList());
            if (!org.springframework.util.CollectionUtils.isEmpty(tList)) {
                List<Map<String, String>> tUserList = new ArrayList<>();
                for (Task t : tList) {
                    String actName = "";
                    String taskUserName = "";
                    Integer num = 0;
                    List<FlowActivity> acts = actList.stream().filter(b -> b.getActid().equals(t.getTaskcurrentactid())).collect(Collectors.toList());
                    if (!org.springframework.util.CollectionUtils.isEmpty(acts)) {
                        actName = acts.get(0).getActname();
                        if (ValidateUtil.isNotNull(acts.get(0).getActorder())) {
                            num = acts.get(0).getActorder();
                        }
                    }
                    List<SysUser> userList = users.stream().filter(b -> b.getuId().equals(t.getTaskuser())).collect(Collectors.toList());
                    if (!org.springframework.util.CollectionUtils.isEmpty(userList)) {
                        taskUserName = userList.get(0).getuName();
                    }
                    Map<String, String> ob = new HashMap<>();
                    ob.put("actName", actName);
                    ob.put("taskUserName", taskUserName);
                    tUserList.add(ob);
                }
                data.setTaskUserAndActList(tUserList);
            }
            newData.add(data);
        }
        Integer total = baseMapper.getFileListCount(params);
        if ("CJ".equals(request.getPmType())) {
            List<Map<String, Object>> keys = new ArrayList<>();
            Map<String, Object> obj = new HashMap<>();
            obj.put("keys", baseMapper.getFileTitles(params, "f.FileTitle"));
            obj.put("name", "任务名称");
            obj.put("code", "filetitles");
            obj.put("type", "string");
            keys.add(obj);
            Map<String, Object> obj1 = new HashMap<>();
            obj1.put("keys", baseMapper.getFileTitles(params, "pro.P_Name"));
            obj1.put("name", "所属项目");
            obj1.put("code", "pNames");
            obj1.put("type", "string");
            keys.add(obj1);
            Map<String, Object> obj2 = new HashMap<>();
            obj2.put("keys", baseMapper.getFileTitles(params, "f.FileFlowStatus"));
            obj2.put("name", "流程状态");
            obj2.put("code", "fileflowstatuses");
            obj2.put("type", "string");
            keys.add(obj2);
            Map<String, Object> obj3 = new HashMap<>();
            obj3.put("keys", "");
            obj3.put("name", "创建时间");
            obj3.put("code", "fileCreateDates");
            obj3.put("type", "data");
            keys.add(obj3);
            //List<String> keys=baseMapper.getFileTitles(params);
            Map<String, Object> returnData = new HashMap<>();
            returnData.put("list", newData);
            returnData.put("keys", keys);
            return BaseResponse.ok(returnData, total);
        }
        return new BaseResponse("获取成功", newData, total);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse Settingapprovalstatus(FileStatusDto request, String customerId, String token, LogUserInfoVo user) {
        Integer status = request.getFilestatus();
        File obj = getFileById(request.getFileid(), request.gettCode(), request.getYear());

        if (obj != null) {
            String yearStr = request.getYear();
            if (ValidateUtil.isNotNull(yearStr) && obj.getFilecreatedate() != null) {
                yearStr = obj.getFilecreatedate().getYear() + "";

            }
            // obj.setFileflowstatus(request.getFilestatus());
            Integer num = baseMapper.updateFileFlowStatusByFileId(getTableName(request.gettCode(), yearStr), status, request.getFileid(), obj.getFlowId());
            if (request.getFilestatus() == 3 || request.getFilestatus().equals("3") || request.getFilestatus() == 7 || request.getFilestatus().equals("7")) {
                //删除任务信息
                taskService.deleteTaskByTaskEntityID(request.getFileid(), customerId);
            }
            try {
                //同步对接的流程信息
                QueryWrapper<Task> wrapper = new QueryWrapper<>();
                wrapper.eq("TaskEntityID", request.getFileid());
                if (!"All".equals(customerId)) {
                    wrapper.eq("Te_Id", customerId);
                }
                List<Task> taskList = taskService.list(wrapper);
                if (taskList.size() > 0) {
                    List<String> taskPreActIdList = taskList.stream().map(n -> n.getTaskpreactid()).collect(Collectors.toList());

                    QueryWrapper<TaskProcessin> taskPwrapper = new QueryWrapper<>();
                    taskPwrapper.eq("tp_entityid", request.getFileid());
                    taskPwrapper.eq("tp_acttype", "userTask");
                    if (!"All".equals(customerId)) {
                        taskPwrapper.eq("Te_Id", customerId);
                    }
                    List<TaskProcessin> taskProcessin = taskProcessinService.getList(taskPwrapper, request.gettCode(), yearStr);
                    for (Task item : taskList) {
                        String tpId = GenerateUUID.getUUID();
                        List<TaskProcessin> taskProcessinObj = taskProcessin.stream().filter(n -> n.getTpActid() != null && n.getTpActid().equals(item.getTaskpreactid())).collect(Collectors.toList());
                        if (taskProcessinObj.size() > 0) {
                            TaskProcessin newtaskProcessinObj = taskProcessinObj.stream().sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).findFirst().get();
                            tpId = newtaskProcessinObj.getTpId();
                        }
                        taskService.sendToDoTask(item, tpId, "0", "-1", token, customerId, request.gettCode(), yearStr);
                    }
                }
                Integer states = 0;
                if (status == 7) {
                    states = 3;
                } else if (status == 1 || status == 2 || status == 4 || status == 9) {
                    states = 2;
                }
                secBiddingProcessingService.updateSecBiddingProcessing(obj.getFileid(), obj.getFilesectionid(), obj.gettCode(), states, customerId);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
            }
            if ("7".equals(status) || status == 7) {
                sysJiMuReportService.createSysJiMuReport(obj.getFileid(), obj.gettCode(), obj.gettId(), customerId);
            }
            BaseResponse baseResponse = syncFileAndBusinessState(obj.gettCode(), obj.getFileid(), status, obj.getFlowId(), customerId, token, yearStr);
            auditSender.sendAudit("业务信息作废（业务编码：" + obj.gettCode() + " ）", user, obj.getFileid(), AuditTypeEnum.upd.getkey());
            // return new BaseResponse(true, "操作成功：file影响行数："+num+"，流程状态修改为"+ status+"，fileid为"+request.getFileid()+"；业务表同步状态："+baseResponse.getSuccess());
            //生成作废记录信息
            String invalidExplanation = request.getInvalidExplanation();
            if (ValidateUtil.isNotEmpty(invalidExplanation)) {
                SysProcessInvalidationRecordDto pIr = new SysProcessInvalidationRecordDto();
                pIr.setInvalidExplanation(invalidExplanation);
                pIr.setOperator(user.getId());
                pIr.setOperatorName(user.getName());
                pIr.setFileId(obj.getFileid());
                sysProcessInvalidationRecordService.insertSysProcessInvalidationRecord(pIr);
            }
            return new BaseResponse(true, "操作成功");
        } else {
            return new BaseResponse(false, "未获取到file信息");
        }

    }


    @Override
    @Transactional
    public BaseResponse fileentitycreate(WorkFlowFileDto request, String customerId, String token) {
        String yearStr = request.getYear();
        File fileobj = getFileById(request.getFileid(), request.gettCode(), yearStr);
        if (ValidateUtil.isEmpty(yearStr) && fileobj != null) {
            if (fileobj.getFilecreatedate() != null) {
                yearStr = fileobj.getFilecreatedate().getYear() + "";
            }
        }
        File fileobjnew = new File();
        BeanUtils.copyProperties(fileobj, fileobjnew);
        fileobjnew.setFileid(request.getTaskProseeid());
        fileobjnew.setFileRelationId(request.getFile_relation_id());
        fileobjnew.setFilecreateuser(request.getUid());
        fileobjnew.setFilecreatedate(new Date());
        fileobjnew.setFilecode(fileobj.getFilecode() + "_Flow");
        fileobjnew.setFileflowstatus(7);
        fileobjnew.settCode(fileobj.gettCode() + "_Flow");
        if (!"All".equals(customerId)) {
            fileobjnew.setTeId(customerId);
        }
        insertFile(fileobjnew, fileobj.gettCode(), yearStr);
        try {
            //fileAnnexService.updateFileAnnexByrelationid(request.getFile_relation_id(),request.getIds());
            if (!ValidateUtil.isEmpty(request.getIds())) {
                BaseResponse fileRefersh = fileAnnexService.refreshRelationFile(request.getFile_relation_id(), request.getIds(), fileobj.getFileid(), token);
            }
        } catch (Exception ex) {
            log.error("流程服务：", ex);
            return new BaseResponse("服务内部错误！");
        }
        return new BaseResponse();
    }


    public void fileStatusUpdate(String fileId, Integer flowStatus, String customerId, String tCode, String year) {
        try {
            SysMultiFormFlow obj = new SysMultiFormFlow();
            obj.setMffMainFileId(fileId);
            List<SysMultiFormFlow> list = sysMultiFormFlowService.GetSysMultiFormFlow(obj, customerId);
            if (list.size() > 0) {
                list.forEach(n -> {
                    if (!n.getMffFileId().equals(fileId)) {
                        baseMapper.updateFileFlowStatusByFileId(getTableName(tCode, year), flowStatus, n.getMffFileId(), "");
                        // 如果是补充、督办意见，则不修改文档的流程状态
                        //BaseResponse baseResponse = syncFileAndBusinessState(n.gettCode(), n.getMffFileId(), flowStatus, "");
                    }
                });
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


    @Override
    public BaseResponse filerelationlist(String fileid, String tCode, String customerId, String year) {
        File fileentity = getFileById(fileid, tCode, year);
        if (fileentity != null) {

            //抄送
            List<CarbonCopy> CCList = carbonCopyService.GetCarbonCopyPojoByFileid(fileid, customerId);

            //沟通
            List<SysCoordinationProcess> Coordinlist = sysCoordinationProcessService.GetysCoordinationProcessbyFileid(fileid, customerId);
            //沟通
            QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
            wrapper.eq("tp_entityid", fileentity.getFileid());
            List<TaskProcessin> TaskProcessinlist = taskProcessinService.getList(wrapper, tCode, year);
            List<String> ccidlist = CCList.stream().map(n -> n.getCcId()).collect(Collectors.toList());
            ccidlist.addAll(Coordinlist.stream().map(n -> n.getCpId()).collect(Collectors.toList()));
            ccidlist.addAll(TaskProcessinlist.stream().map(n -> n.getTpId()).collect(Collectors.toList()));
            List<ActityinfoVo> ActityinfoList = new ArrayList<>();
            Map<String, Object> data = new HashMap<>();
            data.put("FileRelationid", fileentity.getFileRelationId());
            if (ccidlist.size() > 0) {
                List<File> filepojelist = getListByIds(ccidlist, tCode, year);
                List<String> Relationids = filepojelist.stream().map(n -> n.getFileRelationId()).collect(Collectors.toList());
                data.put("Relationids", Relationids);
                data.put("Actityinfos", setActityinfo(CCList, TaskProcessinlist, filepojelist, Coordinlist));
            } else {
                data.put("Relationids", new ArrayList<>());
                data.put("Actityinfos", new ArrayList<>());
            }

            return new BaseResponse(true, "操作成功", data);
        } else {
            return new BaseResponse("获取文档信息失败！");
        }
    }

    public List<ActityinfoVo> setActityinfo(List<CarbonCopy> CCList, List<TaskProcessin> TaskProcessinlist, List<File> filepojelist, List<SysCoordinationProcess> Coordinlist) {
        List<ActityinfoVo> ActityinfoList = new ArrayList<>();
        CCList.forEach(item -> {
            ActityinfoVo objentity = new ActityinfoVo();
            objentity.setOperationType("抄送");
            objentity.setDotime(item.getCcExplaintime());
            List<TaskProcessin> taskprocessinobj = TaskProcessinlist.stream().filter(n -> n.getTpActid() != null && n.getTpActid().equals(item.getCcActid())).collect(Collectors.toList());
            if (taskprocessinobj.size() > 0) {
                objentity.setActionName(taskprocessinobj.get(0).getActname());
                objentity.setActionId(taskprocessinobj.get(0).getTpId());
            }

            List<File> filecc = filepojelist.stream().filter(n -> n.getFileid().equals(item.getCcId())).collect(Collectors.toList());
            if (filecc.size() > 0) {
                if (!StringUtil.isEmpty(filecc.get(0).getFileRelationId())) {
                    objentity.setRelationDd(filecc.get(0).getFileRelationId());
                    ActityinfoList.add(objentity);
                }
            }

        });
        Coordinlist.forEach(n -> {
            ActityinfoVo objentity = new ActityinfoVo();
            objentity.setOperationType("沟通");
            objentity.setDotime(n.getCpTime());
            List<TaskProcessin> taskprocessinobj = TaskProcessinlist.stream().filter(item -> item.getTpActid() != null && item.getTpActid().equals(n.getCpActid())).collect(Collectors.toList());
            if (taskprocessinobj.size() > 0) {
                objentity.setActionName(taskprocessinobj.get(0).getActname());
                objentity.setActionId(n.getCpId());
            }
            List<File> filecc = filepojelist.stream().filter(item -> item.getFileid().equals(n.getCpId())).collect(Collectors.toList());
            if (filecc.size() > 0) {
                if (!StringUtil.isEmpty(filecc.get(0).getFileRelationId())) {
                    objentity.setRelationDd(filecc.get(0).getFileRelationId());
                    ActityinfoList.add(objentity);
                }
            }

        });


        TaskProcessinlist.forEach(n -> {
            ActityinfoVo objentity = new ActityinfoVo();
            if (n.getTpType().equals("补充意见")) {
                objentity.setOperationType("补充意见");
            } else {
                objentity.setOperationType("审批");
            }

            objentity.setDotime(n.getTpDotime());
            objentity.setActionName(n.getActname());
            objentity.setActionId(n.getTpId());
            List<File> filecc = filepojelist.stream().filter(item -> item.getFileid().equals(n.getTpId())).collect(Collectors.toList());
            if (filecc.size() > 0) {
                if (!StringUtil.isEmpty(filecc.get(0).getFileRelationId())) {
                    objentity.setRelationDd(filecc.get(0).getFileRelationId());
                    ActityinfoList.add(objentity);
                }
            }
        });
        return ActityinfoList;
    }

    @Override
    public BaseResponse getFileRelationByFileIds(List<String> fileIds, String tCode, String customerId, String year) {
        List<String> fileIdList = new ArrayList<>();
        fileIdList.addAll(fileIds);

        //抄送
        QueryWrapper<CarbonCopy> carbonCopyWrapper = new QueryWrapper<>();
        QueryWrapper<SysCoordinationProcess> sysCoordinationProcessWrapper = new QueryWrapper<>();
        QueryWrapper<TaskProcessin> taskProcessinWrapper = new QueryWrapper<>();
        for (int i = 0; i < fileIds.size(); i++) {
            if (i == 0) {
                carbonCopyWrapper.eq("CC_FileID", fileIds.get(i));
                sysCoordinationProcessWrapper.eq("CP_FileID", fileIds.get(i));
                taskProcessinWrapper.eq("tp_entityid", fileIds.get(i));

            } else {
                carbonCopyWrapper.or().eq("CC_FileID", fileIds.get(i));
                sysCoordinationProcessWrapper.or().eq("CP_FileID", fileIds.get(i));
                taskProcessinWrapper.or().eq("tp_entityid", fileIds.get(i));
            }
        }
        if (!"All".equals(customerId)) {
            carbonCopyWrapper.eq("Te_Id", customerId);
        }
        List<CarbonCopy> CCList = carbonCopyService.list(carbonCopyWrapper);

        //沟通
        if (!"All".equals(customerId)) {
            sysCoordinationProcessWrapper.eq("Te_Id", customerId);
        }
        List<SysCoordinationProcess> Coordinlist = sysCoordinationProcessService.list(sysCoordinationProcessWrapper);
        if (!"All".equals(customerId)) {
            taskProcessinWrapper.eq("Te_Id", customerId);
        }
        List<TaskProcessin> TaskProcessinlist = taskProcessinService.getList(taskProcessinWrapper, tCode, year);
        if (CCList.size() > 0) {
            fileIdList.addAll(CCList.stream().map(n -> n.getCcId()).collect(Collectors.toList()));
        }
        if (Coordinlist.size() > 0) {
            fileIdList.addAll(Coordinlist.stream().map(n -> n.getCpId()).collect(Collectors.toList()));
        }
        if (TaskProcessinlist.size() > 0) {
            fileIdList.addAll(TaskProcessinlist.stream().map(n -> n.getTpId()).collect(Collectors.toList()));
        }
        List<File> fileEntityList = getListByIds(fileIdList, tCode, year);
        List<Map<String, Object>> data = new ArrayList<>();
        fileIds.forEach(n -> {
            List<File> fileObj = fileEntityList.stream().filter(k -> k.getFileid().equals(n)).collect(Collectors.toList());
            if (fileObj.size() > 0) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("fileId", fileObj.get(0).getFileid());
                dataMap.put("fileTitle", fileObj.get(0).getFiletitle());
                dataMap.put("fileRelationid", fileObj.get(0).getFileRelationId());
                List<String> Relationids = fileEntityList.stream().map(k -> k.getFileRelationId()).collect(Collectors.toList());
                List<ActityinfoVo> actityInfoList = setActityinfo(CCList.stream().filter(l -> l.getCcFileid().equals(n)).collect(Collectors.toList()), TaskProcessinlist.stream().filter(l -> l.getTpEntityid().equals(n)).collect(Collectors.toList()), fileEntityList, Coordinlist.stream().filter(l -> l.getCpFileid().equals(n)).collect(Collectors.toList()));
                dataMap.put("relationIds", actityInfoList.stream().map(a -> a.getRelationDd()));
                dataMap.put("actityInfos", actityInfoList);
                data.add(dataMap);
            }
        });
        return new BaseResponse(true, "操作成功", data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse getFilebySecid(FileSecidDto request, String customerId) {
        return new BaseResponse(true, "操作成功", getSectionFileDetails(request, customerId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<FileSecidVo> getSectionFileDetails(FileSecidDto request, String customerId) {
        List<FileSecidVo> data = new ArrayList<>();
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getSecid())) {
            wrapper.eq("FileSectionID", request.getSecid());
        }
        if (!ValidateUtil.isEmpty(request.getpId())) {
            wrapper.eq("FileProject", request.getpId());
        }
        if (!ValidateUtil.isEmpty(request.getConId())) {
            wrapper.eq("FileContract", request.getConId());
        }
        wrapper.in("T_Code", request.getCode());
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        //是否展示作废状态的文档：默认过滤，配置项showInvalidSectionFile=1时不过滤
        List<String> configCodes = new ArrayList<>();
        configCodes.add("showInvalidSectionFile");
        Map<String, Object> configMap = sysConfigEntityService.getConfigEntity(configCodes, customerId);
        boolean showInvalidFile = configMap.get("showInvalidSectionFile") != null && "1".equals(configMap.get("showInvalidSectionFile").toString());
        if (!showInvalidFile) {
            wrapper.ne("FileFlowStatus", ApprovalStatusEnum.Invalid.getIndex());
        }
        List<File> Lisst = getList(wrapper, request.getCode(), request.getYear());

        List<SecBiddingProcessing> sbpList = secBiddingProcessingService.getSecBiddingProcessingBySecId(request.getSecid());

        request.getCode().forEach(item -> {
            List<File> obj = Lisst.stream().filter(n -> n.gettCode() != null && n.gettCode().trim().equals(item)).collect(Collectors.toList());
            FileSecidVo objfile = new FileSecidVo();
            objfile.setTypeName(item);
            //危大工程数据处理
            if ("Dangerousproject".equals(item)) {
                String dangerousProjectId = this.baseMapper.queryDangerousProjectId(request.getSecid());
                if (StringUtils.isNotBlank(dangerousProjectId)) {
                    File dangerousProjectFile = this.getById(dangerousProjectId);
                    objfile.setFileList(Collections.singletonList(dangerousProjectFile));
                    Lisst.add(dangerousProjectFile);
                }
            } else {
                objfile.setFileList(obj);
            }
            if (CollectionUtils.isNotEmpty(sbpList)) {
                List<SecBiddingProcessing> sbps = sbpList.stream().filter(n -> n.getTCode().equals(item)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(sbps)) {
                    objfile.setDescribe(sbps.get(0).getNotes());
                    objfile.setProcessingStatus(sbps.get(0).getState());
                }
            }
            data.add(objfile);
        });

        if (Lisst.size() > 0) {
            FileSecidVo objfile = new FileSecidVo();
            objfile.setTypeName("nowObj");
            try {
                objfile.setFileList(Lisst.stream().filter(n -> n.getFilecreatedate() != null).sorted(Comparator.comparing(File::getFilecreatedate).reversed()).collect(Collectors.toList()).get(0));
            } catch (Exception ex) {
                objfile.setFileList(Lisst.get(0));
                log.error("流程服务：", ex);
            }

            if (CollectionUtils.isNotEmpty(sbpList)) {
                List<SecBiddingProcessing> spLis = sbpList.stream().filter(n -> n.getState() != 0).sorted(Comparator.comparing(SecBiddingProcessing::getEndTime).reversed()).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(spLis)) {
                    String code = spLis.get(0).getTCode();
                    List<File> fis = Lisst.stream().filter(n -> n.gettCode().equals(code)).collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(fis)) {
                        objfile.setFileList(fis.get(0));
                    } else {
                        objfile.setFileList(null);
                    }
                } else {
                    List<SecBiddingProcessing> spLs = sbpList.stream().filter(n -> n.getState() == 0).sorted(Comparator.comparing(SecBiddingProcessing::getEndTime)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(spLs)) {
                        String code = spLs.get(0).getTCode();
                        List<File> fis = Lisst.stream().filter(n -> n.gettCode().equals(code)).collect(Collectors.toList());
                        if (ValidateUtil.isNotEmpty(fis)) {
                            objfile.setFileList(fis.get(0));
                        } else {
                            objfile.setFileList(null);
                        }
                    }
                }
            }

            data.add(objfile);
        }
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse getFilebySecids(FileSecidDto request, String customerId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail("请求参数不能为空！");
        }
        List<String> secIds = request.getSecIds();
        List<String> codes = request.getCode();
        if (ValidateUtil.isEmpty(secIds) || ValidateUtil.isEmpty(codes)) {
            return BaseResponse.ok();
        }
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(secIds)) {
            wrapper.in("FileSectionID", secIds);
        }
        if (!ValidateUtil.isEmpty(request.getpId())) {
            wrapper.eq("FileProject", request.getpId());
        }
        if (!ValidateUtil.isEmpty(request.getConId())) {
            wrapper.eq("FileContract", request.getConId());
        }
        wrapper.in("T_Code", codes);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        wrapper.ne("FileFlowStatus", ApprovalStatusEnum.Invalid.getIndex());
        List<File> files = getList(wrapper, codes, request.getYear());
        Map<String, List<File>> fileMap = files.stream().collect(Collectors.groupingBy(File::getFilesectionid));

        List<SecBiddingProcessing> sbpList = secBiddingProcessingService.getSecBiddingProcessingBySecIds(secIds);
        Map<String, List<SecBiddingProcessing>> sbpMap = sbpList.stream().collect(Collectors.groupingBy(SecBiddingProcessing::getSecId));

        List<FileSecidsVo> result = new ArrayList<>();
        secIds.forEach(secId -> {
            List<File> subFiles = fileMap.getOrDefault(secId, new ArrayList<>());
            List<SecBiddingProcessing> subSbpList = sbpMap.getOrDefault(secId, new ArrayList<>());
            List<FileSecidVo> data = new ArrayList<>();
            codes.forEach(item -> {
                List<File> obj = subFiles.stream()
                        .filter(n -> n.gettCode() != null && n.gettCode().trim().equals(item))
                        .collect(Collectors.toList());
                FileSecidVo objfile = new FileSecidVo();
                objfile.setTypeName(item);
                // 危大工程数据处理
                if ("Dangerousproject".equals(item)) {
                    String dangerousProjectId = baseMapper.queryDangerousProjectId(secId);
                    if (ValidateUtil.isNotEmpty(dangerousProjectId)) {
                        File dangerousProjectFile = this.getById(dangerousProjectId);
                        if (dangerousProjectFile != null) {
                            objfile.setFileList(Collections.singletonList(dangerousProjectFile));
                            subFiles.add(dangerousProjectFile);
                        }
                    }
                } else {
                    objfile.setFileList(obj);
                }
                if (ValidateUtil.isNotEmpty(subSbpList)) {
                    List<SecBiddingProcessing> sbps = subSbpList.stream()
                            .filter(n -> n.getTCode().equals(item))
                            .collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(sbps)) {
                        objfile.setDescribe(sbps.get(0).getNotes());
                        objfile.setProcessingStatus(sbps.get(0).getState());
                    }
                }
                data.add(objfile);
            });
            if (!subFiles.isEmpty()) {
                FileSecidVo objfile = new FileSecidVo();
                objfile.setTypeName("nowObj");
                try {
                    objfile.setFileList(subFiles.stream()
                            .filter(n -> n.getFilecreatedate() != null).max(Comparator.comparing(File::getFilecreatedate))
                            .orElse(subFiles.get(0)));
                } catch (Exception ex) {
                    if (!subFiles.isEmpty()) {
                        objfile.setFileList(subFiles.get(0));
                    }
                    log.error("流程服务处理 nowObj 异常：", ex);
                }
                if (ValidateUtil.isNotEmpty(subSbpList)) {
                    List<SecBiddingProcessing> spLis = subSbpList.stream()
                            .filter(n -> n.getState() != 0)
                            .sorted(Comparator.comparing(SecBiddingProcessing::getEndTime).reversed())
                            .collect(Collectors.toList());
                    String code = null;
                    if (ValidateUtil.isNotEmpty(spLis)) {
                        code = spLis.get(0).getTCode();
                    } else {
                        List<SecBiddingProcessing> spLs = subSbpList.stream()
                                .filter(n -> n.getState() == 0)
                                .sorted(Comparator.comparing(SecBiddingProcessing::getEndTime))
                                .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(spLs)) {
                            code = spLs.get(0).getTCode();
                        }
                    }
                    if (ValidateUtil.isNotEmpty(code)) {
                        final String targetCode = code;
                        List<File> fis = subFiles.stream()
                                .filter(n -> ValidateUtil.isNotEmpty(n.gettCode()) && n.gettCode().equals(targetCode))
                                .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(fis)) {
                            objfile.setFileList(fis.get(0));
                        }
                    }
                }
                data.add(objfile);
            }
            result.add(new FileSecidsVo(secId, data));
        });
        return BaseResponse.ok(result);
    }

    @Override
    public BaseResponse getDangerousProjectFileBySecId(FileSecidDto request, String customerId) {
        List<FileSecidVo> data = new ArrayList<>();
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getSecid())) {
            wrapper.eq("FileSectionID", request.getSecid());
        }
        if (!ValidateUtil.isEmpty(request.getpId())) {
            wrapper.eq("FileProject", request.getpId());
        }
        if (!ValidateUtil.isEmpty(request.getConId())) {
            wrapper.eq("FileContract", request.getConId());
        }
        wrapper.in("T_Code", request.getCode());
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<File> Lisst = getList(wrapper, request.getCode(), request.getYear());

        File lastNode = new File();
        Integer fileflowstatusOfEnd = null;
        for (String item : request.getCode()) {
            List<File> obj = Lisst.stream().filter(n -> n.gettCode() != null && n.gettCode().trim().equals(item)).collect(Collectors.toList());
            FileSecidVo objfile = new FileSecidVo();
            objfile.setTypeName(item);
            //危大工程数据处理
            if ("Dangerousproject".equals(item)) {
                String dangerousProjectId = this.baseMapper.queryDangerousProjectId(request.getSecid());
                if (StringUtils.isNotBlank(dangerousProjectId)) {
                    File dangerousProjectFile = this.getById(dangerousProjectId);
                    objfile.setFileList(Collections.singletonList(dangerousProjectFile));
                    objfile.setFileflowstatus(dangerousProjectFile.getFileflowstatus());
                    Lisst.add(dangerousProjectFile);
                    lastNode = dangerousProjectFile;
                }
            } else {
                //找到该节点已结束的数据，如果没有则取最新的一条数据
                if (CollectionUtils.isNotEmpty(obj)) {
                    Optional<File> optionalFile = obj.stream().filter(n -> n.getFileflowstatus() == 7).findFirst();
                    if (optionalFile.isPresent()) {
                        lastNode = optionalFile.get();
                    } else {
                        lastNode = obj.get(obj.size() - 1);
                    }
                }

                //fileflowstatus:当前节点有已结束的数据则为 7
                //没有已结束的数据，有2 和 9状态的数据，则为2
                //有数据，但不存在上面两种情况，随意取值
                //obj为空，则fileflowstatus也为空
                //合并节点【危大工程检查/验收】即使有“已结束”的反馈数据，节点也不打勾（✔），直到【危大工程结束】节点有“已结束”的反馈数据，【危大工程检查/验收】节点才打勾
                if (CollectionUtils.isNotEmpty(obj)) {
                    objfile.setFileflowstatus(0);
                    long finishCount = obj.stream().filter(n -> n.getFileflowstatus() == 7).count();
                    long errorCount = obj.stream().filter(n -> n.getFileflowstatus() == 2 || n.getFileflowstatus() == 9).count();
                    if (finishCount > 0 && !"DangerousProjectOngoing".equals(item)) {
                        objfile.setFileflowstatus(7);
                    }
                    if (finishCount == 0 && errorCount > 0) {
                        objfile.setFileflowstatus(2);
                    }
                    if (objfile.getFileflowstatus() == 7 && "DangerousProjectEnd".equals(item)) {
                        fileflowstatusOfEnd = 7;
                    }
                }


                objfile.setFileList(obj);
            }
            data.add(objfile);
        }

        for (FileSecidVo vo : data) {
            if (vo.getTypeName().equals("DangerousProjectOngoing") && fileflowstatusOfEnd != null && fileflowstatusOfEnd == 7) {
                vo.setFileflowstatus(7);
            }
        }

        if (Lisst.size() > 0) {
            FileSecidVo objfile = new FileSecidVo();
            objfile.setTypeName("nowObj");
            try {
                objfile.setFileList(Lisst.stream().filter(n -> n.getFilecreatedate() != null && n.getFileflowstatus() == 7).sorted(Comparator.comparing(File::getFilecreatedate).reversed()).collect(Collectors.toList()).get(0));
            } catch (Exception ex) {
                objfile.setFileList(Lisst.get(0));
                log.error("流程服务：", ex);
            }
            data.add(objfile);
        }

        FileSecidVo lastNodeFile = new FileSecidVo();
        lastNodeFile.setTypeName("lastObj");
        lastNodeFile.setFileList(lastNode);
        data.add(lastNodeFile);

        return new BaseResponse(true, "操作成功", data);
    }

    @Override
    public BaseResponse getFileSecauthority(String id, String tCode, String uId, String customerId, String year) {
        // 配置全部数据权限可以直接查看
        if (sysUserService.checkShowAllDataAuth(uId)) {
            return BaseResponse.ok(true);
        }

        List<String> uidlist = new ArrayList<>();
        File obj = getFileById(id, tCode, year);
        QueryWrapper<Flow> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(tCode)) {
            wrapper.eq("T_Code", tCode);
        } else {
            wrapper.eq("T_Code", "BidHandle");
        }
        wrapper.eq("FlowEnable", "1");
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        wrapper.orderByDesc("FlowLastEditTime");
        List<Flow> flowList = flowService.list(wrapper);
        uidlist.add(obj.getFilecreateuser());
        if (flowList != null && flowList.size() > 0) {
            String flowId = flowList.get(0).getFlowid();
            List<FlowActivity> actList = flowActivityService.getFlowActivityByActFlowID(flowId, customerId);
            if (actList != null && actList.size() > 0) {
                List<FlowActivity> tCodeList = actList.stream().filter(n -> StringUtil.isNotEmpty(n.getActtemplatecode()) && n.getActtemplatecode().equals(tCode)).collect(Collectors.toList());
                if (tCodeList.size() > 0) {
                    String actId = tCodeList.get(0).getActid();
                    QueryWrapper<FlowUser> uWrapper = new QueryWrapper<>();
                    uWrapper.eq("FlowUserAct", actId);
                    uWrapper.eq("FlowUserFlow", flowId);
                    if (!"All".equals(customerId)) {
                        uWrapper.eq("Te_Id", customerId);
                    }
                    List<FlowUser> userList = flowUserService.list(uWrapper);
                    List<FlowUser> FlowUserr = userList.stream().filter(n -> n.getFlowuserrole() != null && n.getFlowuserrole().startsWith("role")).collect(Collectors.toList());
                    List<FlowUser> FlowUserd = userList.stream().filter(n -> n.getFlowuserrole() != null && n.getFlowuserrole().startsWith("department_position")).collect(Collectors.toList());
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
                        List<SysRole> roleList = sysRoleService.list(rolewrapper);
                        List<SysRole> sysRolueList = new ArrayList<>();
                        List<SysRole> nsysRolueList = new ArrayList<>();
                        if (roleList.size() > 0) {
                            //系统角色
                            sysRolueList = roleList.stream().filter(n -> n.getRoleType() != null && n.getRoleType().equals("1")).collect(Collectors.toList());
                            if (sysRolueList.size() > 0) {
                                //系统角色，只获取项目组织结构人员信息
                                List<String> arIdList = sysRolueList.stream().map(n -> n.getRoleId()).collect(Collectors.toList());
                                //根据角色获取项目组织机构人员信息
                                pPRList = sysPositionPRoleService.getUidByRuleIdAndPid(obj.getFileproject(), arIdList, customerId);
                            }

                            //非系统角色
                            nsysRolueList = roleList.stream().filter(n -> n.getRoleType() != null && n.getRoleType().equals("2")).collect(Collectors.toList());
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


                    //并集，直接返回查询到的用户信息
                    if (Position_UserAll.size() > 0) {
                        uidlist.addAll(Position_UserAll.stream().map(n -> n.getuId()).collect(Collectors.toList()));
                    }
                    if (pPRList.size() > 0) {
                        uidlist.addAll(pPRList.stream().map(n -> n.getuId()).collect(Collectors.toList()));
                    }

                }
            }
        }

        //获取审批记录信息
        QueryWrapper<TaskProcessin> tpWrapper = new QueryWrapper<>();
        tpWrapper.eq("tp_entityid", id);
        List<TaskProcessin> tpList = taskProcessinService.getList(tpWrapper, tCode, year);
        if (tpList != null && tpList.size() > 0) {
            List<String> uIds = tpList.stream().map(n -> n.getTpUser()).collect(Collectors.toList());
            uidlist.addAll(uIds);
        }
        if (uidlist != null && uidlist.size() > 0 && uidlist.contains(uId)) {
            return new BaseResponse(true, "操作成功", true);
        } else {
            return new BaseResponse(true, "操作成功", false);
        }

    }


    @Override
    public BaseResponse syncFileAndBusinessState(String tCode, String id, Integer state, String flowId, String customerId, String token, String year) {
        List<String> codeList = new ArrayList<>();
        codeList.add("processApprovalTime");//业务表是否有审批时间
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        Boolean processApprovalTime = true;//同步
        if (smsTemplateList.get("processApprovalTime") != null && "0".equals(smsTemplateList.get("processApprovalTime").toString())) {
            processApprovalTime = false;
        }
        if (ValidateUtil.isEmpty(tCode)) return new BaseResponse("缺少流程模板编码！");
        FileTemplate template = fileTemplateService.getTemplatebyTCode(tCode, customerId);
        if (template == null) return new BaseResponse("缺少流程模板配置！");
        if (CheckNullUtil.checkString(template.gettPrimaryKey(), template.gettStateTimeKey(), template.gettStateKey(), template.gettTable()) && processApprovalTime) {
            return new BaseResponse("流程模板配置错误！");
        }
        Date time = new Date();
        try {
            boolean flag = true;
            if (template.gettTable().equalsIgnoreCase("CON_CONTRACT")) {
                //此处同时处理更新编码，防止同事务内多次更新同一条数据导致死锁
                Integer history = baseMapper.contractHistory(id);
                history = ValidateUtil.isNull(history) ? 0 : history;//缺省非历史合同
                //历史合同不生成编码不走此处逻辑
                if (history == 0 && Objects.equals(state, ApprovalStatusEnum.Already_Over.getIndex()) && sysConfigEntityService.endProcessGenerateCode(customerId)) {
                    String code = fileAnnexService.generateContractCode(id, token);
                    if (ValidateUtil.isEmpty(code)) throw new JPMException(BpmMsg.codeGenError);
                    if (processApprovalTime) {
                        baseMapper.updateContractStateAndStateTime(template.gettTable(), template.gettPrimaryKey(), template.gettStateKey(), template.gettStateTimeKey(), id, state, time, code);
                    } else {
                        baseMapper.updateContractState(template.gettTable(), template.gettPrimaryKey(), template.gettStateKey(), id, state, code);
                    }
                    flag = false;
                }
            }
            if (flag) {
                if (processApprovalTime) {
                    baseMapper.updateBusinessStateAndStateTime(template.gettTable(), template.gettPrimaryKey(), template.gettStateKey(), template.gettStateTimeKey(), id, state, time);
                } else {
                    baseMapper.updateBusinessState(template.gettTable(), template.gettPrimaryKey(), template.gettStateKey(), id, state);
                }
            }
            fanout.fanout(template.gettTable(), template.gettPrimaryKey(), template.gettStateKey(), template.gettStateTimeKey(), id, state, processApprovalTime ? time : null);
            syncState2AdditionalAction(template.gettTable(), id, state);
        } catch (JPMException ex) {
            log.error("流程服务：", ex);
            return BaseResponse.fail(ex.getCode(), ex.getMsg());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("流程服务：", ex);
            return new BaseResponse("流程模板配置错误！");
        }
        return new BaseResponse(true, "操作成功！");
    }


    public BaseResponse syncFileAndBusinessStateBatch(String tCode, String ids, Integer state, String customerId, String token, String year) {
        //批量执行业务表状态（该接方法实现逻辑为精简版，不生成合同编码）
        List<String> codeList = new ArrayList<>();
        codeList.add("processApprovalTime");//业务表是否有审批时间
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        Boolean processApprovalTime = true;//同步
        if (smsTemplateList.get("processApprovalTime") != null && "0".equals(smsTemplateList.get("processApprovalTime").toString())) {
            processApprovalTime = false;
        }
        if (ValidateUtil.isEmpty(tCode)) return new BaseResponse("缺少流程模板编码！");
        FileTemplate template = fileTemplateService.getTemplatebyTCode(tCode, customerId);
        if (template == null) return new BaseResponse("缺少流程模板配置！");
        if (CheckNullUtil.checkString(template.gettPrimaryKey(), template.gettStateTimeKey(), template.gettStateKey(), template.gettTable()) && processApprovalTime) {
            return new BaseResponse("流程模板配置错误！");
        }
        Date time = new Date();
        try {

            if (processApprovalTime) {
                baseMapper.updateBusinessStateAndStateTimeBath(template.gettTable(), template.gettPrimaryKey(), template.gettStateKey(), template.gettStateTimeKey(), ids, state, time);
            } else {
                baseMapper.updateBusinessStateBath(template.gettTable(), template.gettPrimaryKey(), template.gettStateKey(), ids, state);
            }


        } catch (JPMException ex) {
            log.error("流程服务：", ex);
            return BaseResponse.fail(ex.getCode(), ex.getMsg());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("流程服务：", ex);
            return new BaseResponse("流程模板配置错误！");
        }
        return new BaseResponse(true, "操作成功！");
    }


    @Override
    public BaseResponse getBiddingProcedures(List<String> secIds, String customerId, String year, String businessCode) {
        //获取招标办理的所有业务节点
        try {
            String codeStr = "BidHandle";
            if (ValidateUtil.isNotEmpty(businessCode)) {
                codeStr = businessCode;
            }
            String flowId = flowService.GetEnableFlowID("", "", "", codeStr, customerId);
            List<String> tCodes = new ArrayList<>();
            QueryWrapper<File> wrapper = new QueryWrapper<>();
            if (flowId != null) {
                List<FlowActivity> flowactivty = flowActivityService.getFlowActivityByActFlowID(flowId, customerId);
                if (flowactivty.size() > 0) {
                    tCodes = flowactivty.stream().map(n -> n.getActtemplatecode()).collect(Collectors.toList());
                    wrapper.in("T_Code", tCodes);
                }
            }
            if (secIds.size() > 0) {
                wrapper.in("FileSectionID", secIds);
            }
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            List<File> listFile = getList(wrapper, tCodes, year);
            List<SecBiddingProcessing> sbpList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(secIds)) {
                sbpList = secBiddingProcessingService.getSecBiddingProcessingBySecIds(secIds);

            }
            Map<String, Object> data = new HashMap<>();
            for (String n : secIds) {
                if (listFile.size() > 0) {
                    List<File> nList = listFile.stream().filter(k -> n.equals(k.getFilesectionid())).collect(Collectors.toList());
                    if (nList.size() > 0) {
                        nList = nList.stream().filter(k -> k.getFilecreatedate() != null).sorted(Comparator.comparing(File::getFilecreatedate).reversed()).collect(Collectors.toList());
                        data.put(n, nList.get(0).getFiletitle());
                        data.put(n + "_state", nList.get(0).getFileflowstatus());
                        if (CollectionUtils.isNotEmpty(sbpList)) {
                            String tCode = nList.get(0).getFilecode();
                            List<SecBiddingProcessing> sbps = sbpList.stream().filter(k -> n.equals(k.getSecId()) && k.getTCode().equals(tCode)).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(sbps)) {
                                Integer stat = sbps.get(0).getState();
                                if (stat == 3) {
                                    data.put(n + "_state", 7);
                                } else if (stat == 2) {
                                    data.put(n + "_state", 4);
                                } else {
                                    data.put(n + "_state", stat);
                                }

                            } else {
                                data.put(n + "_state", nList.get(0).getFileflowstatus());
                            }
                        } else {
                            data.put(n + "_state", nList.get(0).getFileflowstatus());
                        }
                    } else {
                        data.put(n, "标段信息");
                        data.put(n + "_state", 7);
                    }
                } else {
                    data.put(n, "标段信息");
                }

            }
            return new BaseResponse(true, "操作成功！", data);
        } catch (Exception ex) {
            log.error("流程服务：", ex);
            return new BaseResponse(false, ex.getMessage(), null);
        }
    }

    @Override
    public BaseResponse getBidProgress(List<GetFlowDto> getFlowDtos, String tCode, String customerId, String year) {
        //流程
        String codeStr = "BidHandle";
        if (ValidateUtil.isNotEmpty(tCode)) {
            codeStr = tCode;
        }
        List<Flow> flows = flowService.getSomeFieldsFlow(codeStr, customerId);
        Flow flow = null;
        if (flows.size() > 0) flow = flows.get(0);
        if (ValidateUtil.isEmpty(flow)) return new BaseResponse("未找到流程");
        ////节点
        List<FlowActivity> flowactivty = flowActivityService.getFlowActivityByActFlowID(flow.getFlowid(), customerId);
        //节点连线
        List<FlowRule> Rule = flowRuleService.getFlowRuleListByRuleFlowID(flow.getFlowid(), customerId);
        //开始节点
        FlowActivity startobj = flowactivty.stream().filter(n -> n.getActtype().equals("startEvent")).findFirst().orElse(null);
        //已走流程
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        wrapper.or().in("FileSectionID", getFlowDtos.stream().map(GetFlowDto::getSecId).collect(Collectors.toList()));
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<File> listFile = getList(wrapper, tCode, new Date().getYear() + "");

        Map<String, Object> data = new HashMap<>();
        for (GetFlowDto getFlowDto : getFlowDtos) {
            Map<String, Object> newConditions = flowActivityService.setTorsionConditions("", "", getFlowDto.getCondition(), customerId, tCode, year);
            String keyStr = GenerateUUID.getUUID();
            List<String> allActIds = new ArrayList<>();
            List<FlowActivity> flowActivityList = flowActivityService.GetFlowActivityList(keyStr, startobj, newConditions, flowactivty, Rule, 0, "0", new ArrayList<>(), customerId, allActIds);
            flowActivityService.setActListALLToNull(keyStr);
            List<FlowActivity> currentFlowActivityList = flowActivityList.stream().filter(n -> !n.getActtype().contains("Gateway")).collect(Collectors.toList());
            List<String> templateCodes = currentFlowActivityList.stream().map(FlowActivity::getActtemplatecode).collect(Collectors.toList());
            List<File> currentFiles = listFile.stream().filter(file -> file.getFilesectionid().contains(getFlowDto.getSecId()) && templateCodes.contains(file.gettCode()) && file.getFileflowstatus() != 0).collect(Collectors.toList());
            Map<String, Object> section = new HashMap<>();
            section.put("flowActivityList", currentFlowActivityList);
            section.put("files", currentFiles);
            section.put("nowObj", currentFiles.stream().max(Comparator.comparing(File::getFilecreatedate)).orElse(null));
            data.put(getFlowDto.getSecId(), section);
        }
        return new BaseResponse(true, "操作成功", data);
    }

    @Override
    public BaseResponse updateFileProjectByFileId(String fileId, String pId, String customerId, String tCode, String year) {
        File obj = getFileById(fileId, tCode, year);
        if (obj != null) {
            if (ValidateUtil.isEmpty(obj.getFileproject()) && !ValidateUtil.isEmpty(pId) && !pId.equals("ALL")) {
                obj.setFileproject(pId);
                updateFileById(obj, tCode, year);
            }
        }
        return new BaseResponse(true, "操作成功！");
    }

    @Override
    public BaseResponse getFileByFileId(String fileId, String customerId, String tCode, String year) {
        return new BaseResponse("操作成功！", baseMapper.getFileByFileId(fileId, getTableName(tCode, year)), 1);
    }

    @Override
    public List<Map<String, Object>> getFileByCode(List<String> tCodes, String uId, String customerId, String year) {
        List<String> tableCodeList = new ArrayList<>();
        for (String tCode : tCodes) {
            tableCodeList.add(getTableName(tCode, year));
        }
        //去重
        tableCodeList = tableCodeList.stream().distinct().collect(Collectors.toList());
        List<Map<String, Object>> list = new ArrayList<>();
        for (String tableCode : tableCodeList) {
            list.addAll(baseMapper.getFileByCode(tCodes, uId, customerId, tableCode));
        }
        return list;
    }

    @Override
    public BaseResponse insertSysFile(File request, String customerId) {
        if (ValidateUtil.isEmpty(request.getFileid())) {
            return BaseResponse.fail("文档id缺失");
        }
        if (ValidateUtil.isEmpty(request.gettCode())) {
            return BaseResponse.fail("文档模板编码缺失");
        }
        if (ValidateUtil.isEmpty(request.gettId())) {
            return BaseResponse.fail("文档模板id缺失");
        }
        if (ValidateUtil.isEmpty(request.getFiletitle())) {
            return BaseResponse.fail("文档名称缺失");
        }
        if (ValidateUtil.isEmpty(request.getFilesectionid())) {
            return BaseResponse.fail("通用附件关联id缺失");
        }
        if (ValidateUtil.isEmpty(request.getFilecreateuser())) {
            return BaseResponse.fail("文档创建人id缺失");
        }
        if (ValidateUtil.isEmpty(request.getFilecreateusername())) {
            return BaseResponse.fail("文档创建人姓名缺失");
        }
        try {
            File obj = getFileById(request.getFileid(), request.gettCode(), request.getFilecreatedate().getYear() + "");
            if (obj != null) {
                updateFileById(request, request.gettCode(), request.getFilecreatedate().getYear() + "");
            } else {
                request.setFilecreatedate(new Date());
                if (request.getFileflowstatus() == null) {
                    request.setFileflowstatus(0);
                }
                insertFile(request, request.gettCode(), request.getFilecreatedate().getYear() + "");
            }
            return BaseResponse.ok("文档信息保存成功", request);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return BaseResponse.fail("文档信息保存失败");
        }
    }

    /**
     * 同步更新文档表和业务表流程状态
     * 原属于存储过程逻辑
     * 额外操作
     *
     * @param table
     * @throws Exception
     */
    private void syncState2AdditionalAction(String table, String id, Integer state) throws Exception {
        if (state != ApprovalStatusEnum.Already_Over.getIndex()) return;
        switch (table.toUpperCase(Locale.ENGLISH)) {
            case "PRE_PROJECT":
                baseMapper.updateProjectStatus(id);
                break;
            case "PRE_PROJECTCHANGENAME":
                preProjectChangeNameService.approve2SyncProjectName(id);
                break;
            case "OA_PROBLEM_FEEDBACK":
                oaProblemFeedbackService.sncUpdateOAProblemDtlStatus(id);
                break;
            case "GANTT_TASKS_PROCESS":
                ganttTasksProcessService.ganttTasksProcessToUpdateGanttTasks(id);
                break;
            case "SEC_BIDDING_PLAN":
                secInfoService.updateByBpId(id, state, new Date());
                break;
        }
    }


    /**
     * 同步审批记录中的file信息
     */
    private void synchronizationFile(String tCode, String fileId, Integer state, String year) {
        try {
            baseMapper.updateFileByFIleIds(getTableName(tCode, year), fileId, state);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }


    @Override
    public BaseResponse getMinTimeAndMaxTime(String customerId, String tCode, String year) {
        return new BaseResponse(true, "获取成功", baseMapper.getMinTimeAndMaxTime(customerId, getTableName(tCode, year)));
    }


    @Override
    public Map<String, Object> getData(String table, String key, String id, String customerId) {
        return baseMapper.getData(table, key, id);
    }

    @Override
    public List<Map<String, Object>> getData(String table, String key, List<String> ids, String customerId) {
        if (ids.size() > 0) {
            String idStr = "";
            for (String item : ids) {
                idStr = idStr + "','" + item;
            }
            idStr = idStr.substring(2, idStr.length()) + "'";
            return baseMapper.getDataList(table, key, idStr);
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public Integer updateFileFlowStatusByFileIds(Integer FileFlowStatus, String FileIds, String FlowID, String tCode, String year) {
        return baseMapper.updateFileFlowStatusByFileIds(getTableName(tCode, year), FileFlowStatus, FileIds, FlowID);
    }

    @Override
    public Integer updateBusinessStateBath(String table, String primaryKey, String stateKey, String ids, Integer state) {
        return baseMapper.updateBusinessStateBath(table, primaryKey, stateKey, ids, state);
    }

    @Override
    public Integer updateBusinessStateAndStateTimeBath(String table, String primaryKey, String stateKey, String timeKey, String ids, Integer state, Date time) {
        return baseMapper.updateBusinessStateAndStateTimeBath(table, primaryKey, stateKey, timeKey, ids, state, time);
    }

    @Override
    public BaseResponse judgeNodeApproved(String fileId, String actCode, String customerId, String year) {
        File fileObj = getFileById(fileId, actCode, year);
        if (fileObj != null) {
            if (ValidateUtil.isEmpty(fileObj.getFlowId())) {
                return new BaseResponse(false, "当前表单未获取到流程图", false);
            }
            List<FlowActivity> FlowActivitys = flowActivityService.getFlowActivityByActFlowID(fileObj.getFlowId(), customerId);
            //获取当前流程下所有的连接线
            List<FlowRule> FlowRules = flowRuleService.getFlowRuleListByRuleFlowID(fileObj.getFlowId(), customerId);
            FlowActivity FlowActivityobjs = flowActivityService.getStarActivity(FlowActivitys, FlowRules, customerId);

            if (ValidateUtil.isNotEmpty(actCode)) {
                List<String> codes = new ArrayList<>();
                if (actCode.contains(",")) {
                    codes = Arrays.asList(actCode.split(","));
                } else {
                    codes.add(actCode);
                }
                List<String> codeList = codes;
                List<String> actIdList = FlowActivitys.stream().filter(n -> codeList.contains(n.getActcode())).map(n -> n.getActid()).collect(Collectors.toList());
                if (actIdList != null && actIdList.size() > 0) {
                    //获取审批记录信息
                    List<TaskProcessin> tpList = taskProcessinService.getListByTpFlowIdAndTpEntityId(fileObj.getFlowId(), fileObj.getFileid(), customerId, year);
                    if (tpList != null && tpList.size() > 0) {
                        //获取最新的一轮审批开始时间
                        List<TaskProcessin> newTpList = tpList.stream().filter(n -> n.getTpActid().equals(FlowActivityobjs.getActid()) && (n.getTpTaskgetflagstr() == null || "0".equals(n.getTpTaskgetflagstr()))).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList());
                        ;
                        if (newTpList.size() > 0) {
                            Date startTime = newTpList.get(0).getTpDotime();
                            List<TaskProcessin> nTpList = tpList.stream().filter(n -> actIdList.contains(n.getTpActid()) && startTime.compareTo(n.getTpDotime()) < 0).collect(Collectors.toList());
                            if (nTpList.size() > 0) {
                                return new BaseResponse(true, "当前编码所属步骤已审批", true);
                            } else {
                                return new BaseResponse(true, "当前所属编码未审批", false);
                            }
                        } else {
                            return new BaseResponse(false, "当前表单未获取到开始步骤审批记录", false);
                        }
                    } else {
                        return new BaseResponse(false, "当前表单没有审批记录", false);
                    }
                } else {
                    return new BaseResponse(false, "当前表单所属流程图未找到传入编码", false);
                }
            } else {
                return new BaseResponse(false, "未传入节点编码", false);
            }

        } else {
            return new BaseResponse(false, "获取成功", false);
        }

    }


    @Override
    public BaseResponse getBiddingProceduresCode(List<String> secIds, String customerId, String year, String businessCode) {
        //获取招标办理的所有业务节点
        try {
            String codeStr = "BidHandle";
            if (ValidateUtil.isNotEmpty(businessCode)) {
                codeStr = businessCode;
            }
            String flowId = flowService.GetEnableFlowID("", "", "", codeStr, customerId);
            List<String> tCodes = new ArrayList<>();
            QueryWrapper<File> wrapper = new QueryWrapper<>();
            if (flowId != null) {
                List<FlowActivity> flowactivty = flowActivityService.getFlowActivityByActFlowID(flowId, customerId);
                if (flowactivty.size() > 0) {
                    tCodes = flowactivty.stream().map(n -> n.getActtemplatecode()).collect(Collectors.toList());
                    wrapper.in("T_Code", tCodes);
                }
            }
            if (secIds.size() > 0) {
                wrapper.in("FileSectionID", secIds);
            }
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            List<File> listFile = baseMapper.selectBatchIds(secIds);
            listFile.addAll(getList(wrapper, tCodes, year));
            List<SecBiddingProcessing> sbpList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(listFile)) {
                String secId = listFile.get(0).getFilesectionid();
                sbpList = secBiddingProcessingService.getSecBiddingProcessingBySecId(secId);

            }
            Map<String, Object> data = new HashMap<>();
            for (String n : secIds) {
                Map<String, Object> map = new HashMap<>();
                if (listFile.size() > 0) {
                    List<File> nList = listFile.stream().filter(k -> n.equals(k.getFilesectionid())).collect(Collectors.toList());
                    if (nList.size() > 0) {
                        nList = nList.stream().filter(k -> k.getFilecreatedate() != null).sorted(Comparator.comparing(File::getFilecreatedate).reversed()).collect(Collectors.toList());
                        map.put("title", nList.get(0).getFiletitle());
                        map.put("code", nList.get(0).gettCode());
                        map.put("state", nList.get(0).getFileflowstatus());

                        data.put(n, nList.get(0).getFiletitle());
                        data.put(n + "_state", nList.get(0).getFileflowstatus());
                        if (CollectionUtils.isNotEmpty(sbpList)) {
                            String tCode = nList.get(0).getFilecode();
                            List<SecBiddingProcessing> sbps = sbpList.stream().filter(k -> k.getTCode().equals(tCode)).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(sbps)) {
                                Integer stat = sbps.get(0).getState();
                                if (stat == 3) {
                                    map.put("state", 7);
                                } else if (stat == 2) {
                                    map.put("state", 4);
                                } else {
                                    map.put("state", stat);
                                }

                            } else {
                                map.put("state", nList.get(0).getFileflowstatus());
                            }
                        } else {
                            map.put("state", nList.get(0).getFileflowstatus());
                        }
                    } else {
                        map.put("title", "标段信息");
                        map.put("code", "SectionInfo");
                        map.put("state", 7);
                    }
                } else {
                    map.put("title", "标段信息");
                    map.put("code", "SectionInfo");
                    map.put("state", 0);
                }
                data.put(n, map);
            }

            return new BaseResponse(true, "操作成功！", data);
        } catch (Exception ex) {
            log.error("流程服务：", ex);
            return new BaseResponse(false, ex.getMessage(), null);
        }
    }

    @Override
    public BaseResponse updatePageCodeInFile(String fileId, String pageCode, String tCode, String year) {
        File fileObj = getFileById(fileId, tCode, year);
        fileObj.setPageCode(pageCode);
        updateFileById(fileObj, tCode, year);
        return new BaseResponse(true, "数据修改成功！", true);
    }

    @Override
    public BaseResponse getApprovalProgressByFileIds(List<String> ids, String actCode, String year) {
        if (CollectionUtils.isEmpty(ids)) {
            return new BaseResponse(false, "请传入id", false);
        }
        //获取所有的
        List<File> listFile = baseMapper.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(listFile)) {
            return new BaseResponse(false, "未获取到文档信息", false);
        }
        List<Task> taskList = taskService.getTaskListByFiles(ids);
        List<SysUser> userList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(taskList)) {
            List<String> uIds = taskList.stream().map(p -> p.getTaskuser()).collect(Collectors.toList());
            userList = sysUserService.listByIds(uIds);
        }

        QueryWrapper<TaskProcessin> tpWrapper = new QueryWrapper<>();
        tpWrapper.in("tp_entityid", ids);
        List<String> tCodes = listFile.stream().map(File::gettCode).distinct().collect(Collectors.toList());

        List<TaskProcessin> taskProcessins = taskProcessinService.getList(tpWrapper, tCodes, year);
        List<String> flowIds = listFile.stream().map(File::getFlowId).distinct().collect(Collectors.toList());
        //List<Flow> flowList = flowService.listByIds(flowIds);

        //获取所有的节点信息
        QueryWrapper<FlowActivity> flowActivityQueryWrapper = new QueryWrapper<>();
        flowActivityQueryWrapper.in("ActFlowID", flowIds);
        List<FlowActivity> flowActivityList = flowActivityService.list(flowActivityQueryWrapper);

        //过去所有的流程线信息
        QueryWrapper<FlowRule> flowRuleQueryWrapper = new QueryWrapper<>();
        flowRuleQueryWrapper.in("RuleFlowID", flowIds);
        List<FlowRule> flowRuleList = flowRuleService.list(flowRuleQueryWrapper);

        Map<String, Object> data = new HashMap<>();
        for (String n : ids) {
            Map<String, Object> map = new HashMap<>();
            List<File> fList = listFile.stream().filter(k -> k.getFileid().equals(n)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(fList)) {
                Integer flowStatus = fList.get(0).getFileflowstatus();
                if (flowStatus == 7) {
                    map.put("state", "paid");
                }
                if (flowStatus == 3) {
                    map.put("state", "invalid");
                }
                if (flowStatus == 2 || flowStatus == 4) {
                    map.put("state", "unpaid");
                }
                if (flowStatus == 1 || flowStatus == 9) {
                    //审批中或退回（退回上一步），判断是否经过actCode编码的所属节点
                    String flowId = fList.get(0).getFlowId();
                    List<FlowActivity> flowActs = flowActivityList.stream().filter(o -> o.getActflowid().equals(flowId)).collect(Collectors.toList());
                    List<FlowRule> flowRules = flowRuleList.stream().filter(o -> o.getRuleflowid().equals(flowId)).collect(Collectors.toList());
                    //获取开始节点
                    FlowActivity factObj = flowActivityService.getStarActivity(flowActs, flowRules, "All");
                    if (factObj != null) {
                        //获取发起节点最后一一次审批
                        List<String> actes = flowActs.stream().filter(o -> o.getActcode() != null && o.getActcode().equals(actCode)).map(o -> o.getActid()).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(actes)) {
                            List<TaskProcessin> tpList = taskProcessins.stream().filter(p ->p.getTpEntityid().equals(n)& p.getTpActid().equals(factObj.getActid())&&p.getTpType().equals("提交")&&p.getTpActtype().equals("userTask")).sorted(Comparator.comparing(TaskProcessin::getTpDotime).reversed()).collect(Collectors.toList());
                            //获取第一条数据"
                            if (CollectionUtils.isNotEmpty(tpList)) {
                                Date doTime = tpList.get(0).getTpDotime();
                                //获取actCode的所属节点

                                List<TaskProcessin> yTp = taskProcessins.stream().filter(u ->u.getTpEntityid().equals(n)&&u.getTpDotime().compareTo(doTime) >= 0 && actes.contains(u.getTpActid())).collect(Collectors.toList());
                                if (CollectionUtils.isNotEmpty(yTp)) {
                                    map.put("state", "paid");
                                } else {
                                    map.put("state", "unpaid");
                                }
                            } else {
                                map.put("state", "unpaid");
                                map.put("massage", "未找到发起节点的审批记录");
                            }
                        } else {
                            map.put("state", "unpaid");
                            map.put("massage", "未找到指定的流程节点");
                        }
                    } else {
                        map.put("state", "unpaid");
                        map.put("massage", "未找到发起节点");
                    }
                }
                //获取待办信息
                List<Task> tList = taskList.stream().filter(o -> o.getTaskentityid().equals(n)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(tList)) {
                    List<Map<String, String>> lis = new ArrayList<>();
                    for (Task h : tList) {
                        Map<String, String> m = new HashMap<>();
                        List<FlowActivity> acts = flowActivityList.stream().filter(o -> o.getActid().equals(h.getTaskcurrentactid())).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(acts)) {
                            m.put("actId", acts.get(0).getActid());
                            m.put("actName", acts.get(0).getActname());
                        } else {
                            m.put("actId", h.getTaskcurrentactid());
                            m.put("actName", "");
                        }
                        if (CollectionUtils.isNotEmpty(userList)) {
                            List<SysUser> uList = userList.stream().filter(u -> u.getuId().equals(h.getTaskuser())).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(uList)) {
                                m.put("uId", uList.get(0).getuId());
                                m.put("uName", uList.get(0).getuName());
                            } else {
                                m.put("uId", h.getTaskuser());
                                m.put("uName", "");
                            }

                        } else {
                            m.put("uId", h.getTaskuser());
                            m.put("uName", "");
                        }

                        lis.add(m);
                    };
                    map.put("approvalProcess", lis);
                }
            }
            data.put(n, map);
        }
        return BaseResponse.ok(data);
    }


}
