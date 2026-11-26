package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.*;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Qs.QsPlanTaskQsRecordTypeRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordTypeRequest;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageModel;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.feign.UserFeign;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.leaderBoard.LeaderBoardMapper;
import com.jpmsoft.projectProducer.mapper.other.SysCompanyMapper;
import com.jpmsoft.projectProducer.mapper.other.SysShortMessageLogMapper;
import com.jpmsoft.projectProducer.mapper.other.SysShortMessageMapper;
import com.jpmsoft.projectProducer.mapper.other.TaskMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsPlanTaskMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.*;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.other.*;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPlanTask;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecord;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecordDuty;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsType;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskNewVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskSmsVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsRecordVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsPlanTaskService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordDutyService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsTypeService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/20 14:42
 */
@Slf4j
@Service
public class QsPlanTaskImpl extends BaseFileImpl<QsPlanTaskMapper, QsPlanTask, QsPlanTaskDto> implements IQsPlanTaskService {

    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IQsRecordService qsRecordService;
    @Resource
    private IQsRecordDutyService qsRecordDutyService;
    @Resource
    private IQsTypeService qsTypeService;
    @Resource
    private ISysConfigService sysConfigService;
    @Autowired
    private UserProducerFeign userProducerFeign;
    @Resource
    private IQsRecordDutyService iQsRecordDutyService;
    @Autowired
    private SysShortMessageMapper sysShortMessageMapper;
    @Autowired
    private SysShortMessageLogMapper sysShortMessageLogMapper;
    @Autowired
    private PreProjectMapper projectMapper;
    @Autowired
    private ConContractMapper contractMapper;
    @Autowired
    private SysCompanyMapper sysCompanyMapper;
    @Autowired
    private LeaderBoardMapper leaderBoardMapper;
    @Resource
    private ISysConfigService iSysConfigService;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Resource
    private UserFeign userFeign;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysMessageService sysMessageService;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private CarbonCopyService carbonCopyService;

    @Override
    @CodeGeneration(key = "qspId", codeParam = "ptCode", method = "setPtCode", codeRule = "QsPlanTask", beanClass = QsPlanTaskDto.class)
    public BaseResponse createOrUpdateQsPlanTask(QsPlanTaskDto request) {
        QsPlanTask entity = new QsPlanTask();
        // if (StrUtil.isEmpty(request.getId())) {
        //     String seqCode = "QsPlanTask";
        //     String code = generateCodeByTCode(seqCode, request);
        //     if (ValidateUtil.isEmpty(code))
        //         return new BaseResponse("编号生成失败");
        //     refershAutoNo(seqCode, request);
        //     request.setPtCode(code);
        // }
        entity = createOrUpdateEntityNotState(entity, request);
        //createOrUpdateEntityNotState
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPtProblemReport(), request.getPtRectifyReport()), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPtProblemReport(), request.getPtRectifyReport()),
                    Arrays.asList(request.getFileIds(), request.getPtProblemReportIds(), request.getPtRectifyReportIds()), entity.getId());
        List<QsRecordRequest> qsRecords = request.getQsRecords();
        if (CollectionUtil.isNotEmpty(qsRecords)) {
            //01、质量 02、安全
            String ptCategory = request.getPtCategory();
            Integer qsrCategory = "01".equals(ptCategory) ? 0 : 1;
            for (QsRecordRequest qsRecord : qsRecords) {
                qsRecord.setQsrCategory(qsrCategory);
                qsRecord.setQsrTime(request.getPtEvaluationTime());
                if (qsRecord.getQsrRectifyRequireTime() == null) {
                    qsRecord.setQsrRectifyRequireTime(request.getPtRectifyRequireTime());
                }
            }
        }
        qsRecordService.createOrUpdateQsRecord(qsRecords, entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    public String generateCodeByTCode(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/generateCodeByTCode?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }

    public String refershAutoNo(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/refershAutoNo?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }

    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        log.info("服务调用时:{}", JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }

    @Override
    public BaseResponse updateQsPlanTaskPartialInfo(QsPlanTaskDto request) {
        QsPlanTask entity = baseMapper.selectById(request.getId());
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        entity.setPtFinishCount(request.getPtFinishCount());
        entity.setPtRectifyReport(request.getPtRectifyReport());
        entity.setPtProblemReport(request.getPtProblemReport());
        entity.setPtRectifyTime(request.getPtRectifyTime());
        entity.setPtRectifyRemark(request.getPtRectifyRemark());
        entity.setPtReply(request.getPtReply());
        entity.setPtRectifyReply(request.getPtRectifyReply());
        baseMapper.updateById(entity);
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getPtRectifyReport(), request.getPtProblemReport())
                , Arrays.asList(request.getPtRectifyReportIds(), request.getPtProblemReportIds()), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getQsPlanTaskList(QsPlanTaskDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        return selectList(request, authSql);
    }

    @Override
    public BaseResponse getQsPlanTaskListNoAuth(QsPlanTaskDto request) {
//        return selectList(request, null);
        return selectListStatistics(request, null);
    }

    public BaseResponse selectList(QsPlanTaskDto request, String authSql) {

        QueryMap<QsPlanTaskDto> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("pId").put("pName").put("conName").put("ptPmName").put("ptCseName")
                .put("ptType").put("state").put("createUname").put("ptGroupLeaderName").put("ptMeasure").put("pType")
                .put("ptGroupMemberName").put("ptRectifyName").put("rectifyStatus").put("ptTeamLeaderName").put("ptIsConstruction")
                .put("ptContractorName").put("ptSupervisorName").put("ptCode").put("ptCategory").put("ptNoticeType").put("outTimeStatus")
                .put(request.getPtEvaluationTimes(), "ptEvaluationTimeStart", "ptEvaluationTimeEnd").put("overdueTask").put("ptCid")
                .put(request.getPtRectifyTimes(),"ptRectifyTimeStart","ptRectifyTimeEnd");
        IPage<QsPlanTaskVo> page = new Page<>(request.getPage(), request.getSize());
        log.info("查询参数:{}", JacksonUtils.toJSONString(query.getQuery()));
        List<QsPlanTaskVo> data = baseMapper.getQsPlanTaskList(page, query.getQuery());
        //List<String> ids = data.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
//        List<TaskProcessin> taskProcessins =new ArrayList<>();
//        if (!CollectionUtils.isEmpty(ids)) {
//            QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
//            wrapper.in("tp_entityid", ids);
//            taskProcessins =taskProcessinService.list(wrapper);
//        }
        //要求整改时间：取子表中，要求整改时间最迟的日期
        //
        //实际整改时间：取审批过程结束后，最后一个审批人的审批时间
        for (QsPlanTaskVo d : data) {
            d.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(d.getQsFinishCount()), new BigDecimal(d.getQsRecordCount()), 4, RoundingMode.DOWN));
            d.setQsReplyRate(MathUtil.decimalDivide(new BigDecimal(d.getQsReplyCount()), new BigDecimal(d.getQsRecordCount()), 4, RoundingMode.DOWN));
        }
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    public BaseResponse selectListStatistics(QsPlanTaskDto request, String authSql) {
        QueryMap<QsPlanTaskDto> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("pId").put("pName").put("conName").put("ptPmName").put("ptCseName")
                .put("ptType").put("state").put("createUname").put("ptGroupLeaderName").put("ptMeasure").put("pType")
                .put("ptGroupMemberName").put("ptRectifyName").put("ptTeamLeaderName").put("ptIsConstruction")
                .put("ptContractorName").put("ptSupervisorName").put("ptCode").put("ptCategory")
                .put(request.getPtEvaluationTimes(), "ptEvaluationTimeStart", "ptEvaluationTimeEnd").put("overdueTask").put("ptCid");
        IPage<QsPlanTaskVo> page = new Page<>(request.getPage(), request.getSize());
        List<QsPlanTaskVo> data = baseMapper.getQsPlanTaskList(page, query.getQuery());
        data.forEach(d -> {
            d.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(d.getQsFinishCount()), new BigDecimal(d.getQsRecordCount()), 4, RoundingMode.DOWN));
            d.setQsReplyRate(MathUtil.decimalDivide(new BigDecimal(d.getQsReplyCount()), new BigDecimal(d.getQsRecordCount()), 4, RoundingMode.DOWN));
        });

        //汇总
        QsPlanTaskVo dataTotal = baseMapper.getQsPlanTaskListStatistics(query.getQuery());
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("data", data);
        if (dataTotal != null) {
            BigDecimal totalQsRecordCount = dataTotal.getTotalQsRecordCount();
            BigDecimal totalQsFinishCount = dataTotal.getTotalQsFinishCount();
            BigDecimal totalQsReplyCount = dataTotal.getTotalQsReplyCount();
            totalQsRecordCount = totalQsRecordCount != null ? totalQsRecordCount : BigDecimal.ZERO;
            totalQsFinishCount = totalQsFinishCount != null ? totalQsFinishCount : BigDecimal.ZERO;
            totalQsReplyCount = totalQsReplyCount != null ? totalQsReplyCount : BigDecimal.ZERO;
            mapData.put("totalQsRecordCount", totalQsRecordCount);
            mapData.put("totalQsFinishCount", totalQsFinishCount);
            mapData.put("totalQsReplyCount", totalQsReplyCount);
            BigDecimal replyRate = BigDecimal.ZERO;
            if (totalQsRecordCount.compareTo(BigDecimal.ZERO) > 0) {
                replyRate = MathUtil.decimalDivide(totalQsReplyCount, totalQsRecordCount, 4, RoundingMode.DOWN);
            }
            mapData.put("totalQsReplyRate", replyRate);
            BigDecimal finishRate = BigDecimal.ZERO;
            if (totalQsRecordCount.compareTo(BigDecimal.ZERO) > 0) {
                finishRate = MathUtil.decimalDivide(totalQsFinishCount, totalQsRecordCount, 4, RoundingMode.DOWN);
            }
            mapData.put("totalQsFinishRate", finishRate);
        } else {
            mapData.put("totalQsRecordCount", BigDecimal.ZERO); // 问题总数
            mapData.put("totalQsFinishCount", BigDecimal.ZERO); // 整改数量
            mapData.put("totalQsReplyCount", BigDecimal.ZERO); // 回复数量
            mapData.put("totalQsReplyRate", BigDecimal.ZERO); // 回复率
            mapData.put("totalQsFinishRate", BigDecimal.ZERO); // 闭合率
        }
        return BaseResponse.ok(mapData, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getQsPlanTaskById(String id) {
        QsPlanTaskVo data = baseMapper.getQsPlanTaskById(id);
        if (data != null) {
            List<QsRecordResponse> qsRecords = data.getQsRecords();
            getQsRecordDutyList(qsRecords);
        }
        return BaseResponse.ok(data);
    }

    /**
     * 获取质量安全检查记录责任人数据
     *
     * @param qsRecords
     */
    private void getQsRecordDutyList(List<QsRecordResponse> qsRecords) {
        if (!CollectionUtils.isEmpty(qsRecords)) {
            List<QsRecordDutyDTO> qrds = qsRecordDutyService.getQsRecordDutyByQsrIds(qsRecords.stream().map(QsRecordResponse::getQsrId).collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(qrds)) {
                qsRecords.forEach(d -> {
                    List<QsRecordDutyDTO> qrd = qrds.stream().filter(f -> f.getQsrId().equals(d.getQsrId())).collect(Collectors.toList());
                    d.setQsRecordDutys(qrd);
                    if (!CollectionUtils.isEmpty(qrd)) {
                        StringBuilder cpyNames = new StringBuilder();
                        StringBuilder userNames = new StringBuilder();
                        for (QsRecordDutyDTO q : qrd) {
                            if (ValidateUtil.isNotEmpty(q.getQrdCpyName())) {
                                if (ValidateUtil.isEmpty(cpyNames))
                                    cpyNames.append(q.getQrdCpyName());
                                else cpyNames.append(",").append(q.getQrdCpyName());
                            }
                            if (ValidateUtil.isNotEmpty(q.getQrdUserName())) {
                                if (ValidateUtil.isEmpty(userNames))
                                    userNames.append(q.getQrdUserName());
                                else userNames.append(",").append(q.getQrdUserName());
                            }
                        }
                        d.setQrdUserNames(userNames.toString());
                        d.setQrdCpyNames(cpyNames.toString());
                    }
                });
            }
        }
    }

    @Override
    public BaseResponse updateQsPlanTaskRequireTime(QsPlanTaskDto request) {
        QsPlanTask entity = baseMapper.selectById(request.getId());
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        entity.setPtRectifyRequireTime(request.getPtRectifyRequireTime());
        baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getQsPlanTaskPage(QsPlanTaskDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<QsPlanTaskDto> query = new QueryMap<>(request);
        query.put("authSql", authSql);
        IPage<QsPlanTaskNewVo> page = new Page<>(request.getPage(), request.getSize());
        IPage<QsPlanTaskNewVo> datas = baseMapper.getQsPlanTaskPage(page, query.getQuery());
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse getProSafetyLedgerPage(ProSafetyLedgerDtlDTO request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<ProSafetyLedgerDtlDTO> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("pName").put("pCode")
                .put(request.getPtEvaluationTimes(), "xcStart", "xcEnd").put("ptCategory");
        //IPage<ProSafetyLedgerDtlDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ProSafetyLedgerDtlDTO> data = baseMapper.getProSafetyLedgerPage(query.getQuery());
        data.forEach(d -> {
            d.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(d.getQsFinishCount()), new BigDecimal(d.getQsRecordCount())));
        });
        ProSafetyLedgerDTO res = baseMapper.getProSafetyLedger(query.getQuery());
        res = res == null ? new ProSafetyLedgerDTO() : res;
        res.setData(data);
        res.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(res.getQsFinishCount()), new BigDecimal(res.getQsRecordCount())));
        return BaseResponse.ok(res);
    }

    @Override
    public BaseResponse getProSafetyLedgerPageYW(ProSafetyLedgerDtlDTO request, String uId) {
        long begin = System.currentTimeMillis();
        log.info("性能监控getProSafetyLedgerPageYW begin time :" + begin);
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<ProSafetyLedgerDtlDTO> query = new QueryMap<>(request);

        Map<String, QsType> qsTypeMap = getQsTypeMap();
        List<String> allIds = new ArrayList<>();
        if (ValidateUtil.isNotNull(request.getQstId())) {
            allIds = findDescendants(request.getQstId(), qsTypeMap);
            request.setQstIds(allIds);
        }
        log.info("allIds" + JacksonUtils.toJSONString(allIds));
        Date xcStart = null;
        Date xcEnd = null;
        if (ValidateUtil.isNotNull(request.getPtEvaluationTimes())) {
            if (ValidateUtil.isNotNull(request.getPtEvaluationTimes().get(0))) {
                if (StrUtil.isNotEmpty(request.getPtEvaluationTimes().get(0))) {
                    xcStart = DateUtil.parse(request.getPtEvaluationTimes().get(0), "yyyy-MM-dd");
                }
            }
            if (ValidateUtil.isNotNull(request.getPtEvaluationTimes().get(1))) {
                if (StrUtil.isNotEmpty(request.getPtEvaluationTimes().get(1))) {
                    xcEnd = DateUtil.parse(request.getPtEvaluationTimes().get(1), "yyyy-MM-dd");
                }
            }
        }
        log.info("性能监控getProSafetyLedgerPageYW select qstype time :" + (System.currentTimeMillis() - begin));
        Map<String, Object> resultMap = new HashMap<>();
        query.put("authSql", authSql).put("pName").put("pCode")
                .put(request.getPtEvaluationTimes(), "xcStart", "xcEnd").put("ptCategory").put("pType")
                .put("ptType").put("pProjectStatus").put("pStatus").put("sortSideFiled")
                .put("sortType").put("qstId", request.getQstId()).put("qstIds", request.getQstIds());
        //IPage<ProSafetyLedgerDtlDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ProSafetyLedgerDtlDTO> data = baseMapper.getProSafetyLedgerPage(query.getQuery());
        log.info("data:{}", JacksonUtils.toJSONString(data));
        List<String> projectIds = data.stream().map(ProSafetyLedgerDtlDTO::getpId).distinct().collect(Collectors.toList());
        List<ConContract> contractList = new ArrayList<>();
        List<QsPlanTask> qsPlanTaskList = new ArrayList<>();
        List<QsRecord> qsRecordList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(projectIds)) {
            contractList = baseMapper.getConContractListAll(projectIds);
            qsPlanTaskList = baseMapper.selectListsAll(projectIds, xcStart, xcEnd, request.getPtCategory(), allIds, request.getPtType());
            qsRecordList = baseMapper.selectQsRecordListsAll(projectIds, xcStart, xcEnd, request.getPtCategory(), allIds, request.getPtType());
        }
        Map<String, List<QsPlanTask>> qsPlanTaskMap = qsPlanTaskList.stream().collect(Collectors.groupingBy(QsPlanTask::getPId));

        //log.info("qsRecordList:{}",JacksonUtils.toJSONString(qsRecordList));
        qsRecordList = qsRecordList.stream().filter(q -> q.getPId() != null).collect(Collectors.toList());
        Map<String, List<QsRecord>> qsRecordMap = new HashMap<>();
        Map<String, List<QsRecord>> qsRecordMapFinish = new HashMap<>();
        if (CollectionUtil.isNotEmpty(qsRecordList)) {
            qsRecordMap = qsRecordList.stream().collect(Collectors.groupingBy(QsRecord::getPId));
            List<QsRecord> qsRecordListFinish = qsRecordList.stream().filter(q -> q.getQsrRectifyStatus().equals("03")).collect(Collectors.toList());
            qsRecordMapFinish = qsRecordListFinish.stream().collect(Collectors.groupingBy(QsRecord::getPId));
        }
        //log.info("qsRecordMap:{}",JacksonUtils.toJSONString(qsRecordMap));
        //log.info("qsRecordMapFinish:{}",JacksonUtils.toJSONString(qsRecordMapFinish));
        Map<String, List<QsRecord>> finalQsRecordMap = qsRecordMap;
        Map<String, List<QsRecord>> finalQsRecordMapFinish = qsRecordMapFinish;
        List<ConContract> finalContractList = contractList;
        data.forEach(e -> {
            e.setConstructionConMoney(finalContractList.stream()
                    .filter(c -> c.getConProjectId().equals(e.getpId())).map(ConContract::getConMoneyTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
            e.setQsPlanTaskCount(qsPlanTaskMap.getOrDefault(e.getpId(), new ArrayList<>()).size());
            e.setQsRecordCount(finalQsRecordMap.getOrDefault(e.getpId(), new ArrayList<>()).size());
            e.setQsFinishCount(finalQsRecordMapFinish.getOrDefault(e.getpId(), new ArrayList<>()).size());
            if (e.getQsRecordCount() == 0) {
                e.setQsFinishRate(BigDecimal.ZERO);
            } else {
                e.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(e.getQsFinishCount())
                        , new BigDecimal(e.getQsRecordCount()), 4, RoundingMode.DOWN));
            }
            //log.info("{}-{}-{}-{}-{}-{}",e.getPName(),e.getQsPlanTaskCount(),e.getQsRecordCount(),e.getQsFinishCount(),e.getQsFinishRate(),e.getConstructionConMoney());
        });
        log.info("性能监控getProSafetyLedgerPageYW select getProSafetyLedgerPage time :" + (System.currentTimeMillis() - begin));
//        //增加汇总
        if (!data.isEmpty()) {
            String sortSideFiled = request.getSortSideFiled();
            String sortType = request.getSortType();
            if (ValidateUtil.isNotEmpty(sortSideFiled) && ValidateUtil.isNotEmpty(sortType)) {
                switch (sortSideFiled) {
                    case "qsRecordCount":
                        if ("desc".equals(sortType.toLowerCase())) {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsRecordCount).reversed()).collect(Collectors.toList());
                        } else {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsRecordCount)).collect(Collectors.toList());
                        }
                        break;
                    case "qsFinishCount":
                        if ("desc".equals(sortType.toLowerCase())) {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishCount).reversed()).collect(Collectors.toList());
                        } else {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishCount)).collect(Collectors.toList());
                        }
                        break;
                    case "qsFinishRate":
                        if ("desc".equals(sortType.toLowerCase())) {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishRate).reversed()).collect(Collectors.toList());
                        } else {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishRate)).collect(Collectors.toList());
                        }
                        break;
                    case "qsPlanTaskCount":
                        if ("desc".equals(sortType.toLowerCase())) {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsPlanTaskCount).reversed()).collect(Collectors.toList());
                        } else {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsPlanTaskCount)).collect(Collectors.toList());
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsPlanTaskCount)).collect(Collectors.toList());
                        }
                    default:
                        break;
                }
            }
        }
        log.info("data:{}", JacksonUtils.toJSONString(data));
        List<ProSafetyLedgerDtlDTO> dataSum = baseMapper.getProSafetyLedgerPageSumTotal(query.getQuery());
        Map<String, List<QsRecord>> finalQsRecordMap1 = qsRecordMap;
        Map<String, List<QsRecord>> finalQsRecordMapFinish1 = qsRecordMapFinish;
        List<ConContract> finalContractList1 = contractList;
        dataSum.forEach(e -> {
            setCount(finalContractList1, qsPlanTaskMap, finalQsRecordMap1, finalQsRecordMapFinish1, e);
        });
        log.info("性能监控getProSafetyLedgerPageYW select getProSafetyLedgerPageSumTotal time :" + (System.currentTimeMillis() - begin));
//        //报告份数
        Integer qsPlanTaskCount = data.stream().filter(e -> ValidateUtil.isNotNull(e.getQsPlanTaskCount())).map(e -> {
            return new BigDecimal(e.getQsPlanTaskCount());
        }).reduce(BigDecimal.ZERO, BigDecimal::add).intValue();
//        //问题数量
        Integer qsRecordCount = data.stream().filter(e -> ValidateUtil.isNotNull(e.getQsRecordCount())).map(e -> {
            return new BigDecimal(e.getQsRecordCount());
        }).reduce(BigDecimal.ZERO, BigDecimal::add).intValue();
//        //整改数量
        Integer qsFinishCount = data.stream().filter(e -> ValidateUtil.isNotNull(e.getQsFinishCount())).map(e -> {
            return new BigDecimal(e.getQsFinishCount());
        }).reduce(BigDecimal.ZERO, BigDecimal::add).intValue();
//        //闭合率
        BigDecimal qsFinishRate = MathUtil.decimalDivide(new BigDecimal(qsFinishCount), new BigDecimal(qsRecordCount), 4, RoundingMode.DOWN);
        //施工合同额
        BigDecimal constructionConMoneySum = data.stream().filter(e -> ValidateUtil.isNotNull(e.getConstructionConMoney())).map(e -> e.getConstructionConMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
        //总投资额
        BigDecimal pInvTotalSum = data.stream().filter(e -> ValidateUtil.isNotNull(e.getPInvTotal())).map(e -> e.getPInvTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        resultMap.put("qsPlanTaskCount", qsPlanTaskCount);
        resultMap.put("qsRecordCount", qsRecordCount);
        resultMap.put("qsFinishCount", qsFinishCount);
        resultMap.put("qsFinishRateSum", qsFinishRate);
        resultMap.put("constructionConMoneySum", MathUtil.DecimalDivisionInt(constructionConMoneySum, 10000));
        resultMap.put("pInvTotalSum", MathUtil.DecimalDivisionInt(pInvTotalSum, 10000));


        //巡查统计类配置
        String code = "inspectionStatistics";
        String inspectionStatisticsConfig = sysConfigService.getConfigByCode(code);
        List<QsStatisticsConfigDto> statisticsConfigList = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(inspectionStatisticsConfig)) {
            statisticsConfigList = JSON.parseArray(inspectionStatisticsConfig, QsStatisticsConfigDto.class);
        }

        List<String> parentQstIdList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(statisticsConfigList)) {
            for (QsStatisticsConfigDto statisticsConfigDto : statisticsConfigList) {
                parentQstIdList.add(statisticsConfigDto.getParentQstId());
            }
        }

        IPage<QsRecordDto> qsRecordPage = new Page<>(request.getPage(), request.getSize());
        Map<String, Object> params = new HashMap<>();
        params.put("qstIdPathList", parentQstIdList);
        params.put("pName", request.getPName());
        params.put("pType", request.getPType());
        params.put("pStatus", request.getPStatus());
        params.put("pCode", request.getPCode());
        params.put("pProjectStatus", request.getPProjectStatus());
        params.put("qstIds", request.getQstIds());
        params.put("ptType", request.getPtType());
        if (StrUtil.isNotBlank(request.getPtEvaluationTimes().get(0))) {
            params.put("xcStart", request.getPtEvaluationTimes().get(0));
        }
        if (StrUtil.isNotBlank(request.getPtEvaluationTimes().get(1))) {
            params.put("xcEnd", request.getPtEvaluationTimes().get(1));
        }
        log.info("性能监控getProSafetyLedgerPageYW highQsRecordListparams{}", JacksonUtils.toJSONString(params));
        List<QsRecordResponse> highQsRecordList = baseMapper.getHighQsRecordTzList(null, params);
        log.info("性能监控getProSafetyLedgerPageYW select getHighQsRecordList time :" + (System.currentTimeMillis() - begin));
        List<QsStatisticsDto> statisticsDtoList = new ArrayList<>();
        String json = sysConfigService.getConfigByCode("ProblemDescriptionStatistics");
        JSONArray jsonArray = new JSONArray(json);
        List<String> allIds0 = new ArrayList<>();
        List<String> allIds1 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonArray)) {
            JSONObject json0 = jsonArray.getJSONObject(0);  //质量
            JSONObject json1 = jsonArray.getJSONObject(1);  //安全
            allIds0 = findDescendants(json0.getStr("parentQstId"), qsTypeMap);//质量
            allIds1 = findDescendants(json1.getStr("parentQstId"), qsTypeMap); //安全
        }
        statisticsDtoList = BeanUtils.copyList(statisticsConfigList, QsStatisticsDto.class);

        Map<String, Integer> qualityCount = new HashMap<>();  //质量
        Map<String, Integer> safetyCount = new HashMap<>();  //安全

        for (QsStatisticsDto qsStatisticsDto : statisticsDtoList) {
            QsRecordStatisticsDto qsRecordStatisticsDto = new QsRecordStatisticsDto();
            List<QsRecordResponse> qsRecords = highQsRecordList.stream().filter(qsRecord -> ValidateUtil.isNotEmpty(qsRecord.getQstPath())
                    && qsRecord.getQstPath().contains(qsStatisticsDto.getParentQstId())
            ).collect(Collectors.toList());
            List<QsRecordResponse> importantQuestions = qsRecords.stream().filter(qsRecord -> qsRecord.getQstIsImportant() != null && qsRecord.getQstIsImportant() == 1).collect(Collectors.toList());
            Integer questionNum = CollectionUtil.isEmpty(qsRecords) ? 0 : qsRecords.size();
            Integer importantQuestionNum = CollectionUtil.isEmpty(importantQuestions) ? 0 : importantQuestions.size();
            qsRecordStatisticsDto.setQuestionNum(questionNum);
            qsRecordStatisticsDto.setImportantQuestionNum(importantQuestionNum);

            if (qsStatisticsDto.getTitle().equals("质量") || qsStatisticsDto.getTitle().equals("安全")) {
                for (QsRecordResponse qsRecord : qsRecords) {
                    String qstPath = qsRecord.getQstPath();
                    String ptId = qsRecord.getPtId();
                    String[] ids = qstPath.split("/");
                    for (String id : ids) {
                        if (allIds0.contains(id)) {
                            qualityCount.put(ptId, qualityCount.getOrDefault(ptId, 0) + 1);
                        } else if (allIds1.contains(id)) {
                            safetyCount.put(ptId, safetyCount.getOrDefault(ptId, 0) + 1);
                        }
                    }
                }
            }
            List<QsRecordResponse> rectificationList = qsRecords.stream().filter(qsRecord -> StrUtil.isNotBlank(qsRecord.getQsrRectifyStatus()) && "03".equals(qsRecord.getQsrRectifyStatus())).collect(Collectors.toList());
            Integer rectificationNum = CollectionUtil.isEmpty(rectificationList) ? 0 : rectificationList.size();
            qsRecordStatisticsDto.setRectificationNum(rectificationNum);

            List<QsRecordResponse> importantRectificationList = rectificationList.stream().filter(qsRecord -> qsRecord.getQstIsImportant() != null && qsRecord.getQstIsImportant() == 1).collect(Collectors.toList());
            Integer importantRectificationNum = CollectionUtil.isEmpty(importantRectificationList) ? 0 : importantRectificationList.size();
            qsRecordStatisticsDto.setImportantRectificationNum(importantRectificationNum);

            double rectificationRate = 0;
            if (questionNum == 0) {
                rectificationRate = 0;
            } else {
                rectificationRate = ((double) rectificationNum / questionNum) * 100.0;
            }
            qsRecordStatisticsDto.setRectificationRate(String.format("%.2f", rectificationRate));


            double importantRectificationRate = 0;
            if (importantQuestionNum == 0) {
                importantRectificationRate = 0;
            } else {
                importantRectificationRate = ((double) importantRectificationNum / importantQuestionNum) * 100.0;
            }
            qsRecordStatisticsDto.setImportantRectificationRate(String.format("%.2f", importantRectificationRate));

            qsStatisticsDto.setQsRecordStatisticsDto(qsRecordStatisticsDto);
        }

        log.info("性能监控getProSafetyLedgerPageYW select last time :" + (System.currentTimeMillis() - begin));
        resultMap.put("data", data);
        resultMap.put("statisticList", statisticsDtoList);
        resultMap.put("qualityCount", qualityCount.size());
        resultMap.put("safetyCount", safetyCount.size());
        return BaseResponse.ok(resultMap);
    }

    private void setCount(List<ConContract> contractList, Map<String, List<QsPlanTask>> qsPlanTaskMap, Map<String,
            List<QsRecord>> qsRecordMap, Map<String, List<QsRecord>> qsRecordMapFinish, ProSafetyLedgerDtlDTO e) {
        e.setConstructionConMoney(contractList.stream()
                .filter(c -> c.getConProjectId().equals(e.getpId())).map(ConContract::getConMoneyTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        List<QsPlanTask> qsPlanTasks = qsPlanTaskMap.getOrDefault(e.getpId(), new ArrayList<>()).stream().filter(n -> n.getPtCategory().equals(e.getPtCategory())).collect(Collectors.toList());
        e.setQsPlanTaskCount(qsPlanTasks.size());
        List<String> qsPlanTaskIds;
        if (qsPlanTasks.size() > 0) {
            qsPlanTaskIds = qsPlanTasks.stream().map(QsPlanTask::getId).collect(Collectors.toList());
        } else {
            qsPlanTaskIds = new ArrayList<>();
        }
        e.setQsRecordCount((int) qsRecordMap.getOrDefault(e.getpId(), new ArrayList<>()).stream().filter(n -> qsPlanTaskIds.contains(n.getPtId())).count());
        e.setQsFinishCount((int) qsRecordMapFinish.getOrDefault(e.getpId(), new ArrayList<>()).stream().filter(n -> qsPlanTaskIds.contains(n.getPtId())).count());
        e.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(e.getQsFinishCount()), new BigDecimal(e.getQsRecordCount()), 4, RoundingMode.DOWN).multiply(new BigDecimal(100)).setScale(2, RoundingMode.UNNECESSARY));
    }

    @Override
    public BaseResponse qualitySafetyStatistics(QsRecordDto request, String uId) {
//        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
//        if (ValidateUtil.isEmpty(authSql))
//            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<QsRecordDto> query = new QueryMap<>(request);
        query.put("authSql", null).put("pId").put("pName").put("conName").put("ptPmName").put("ptCseName")
                .put("ptType").put("state").put("createUname").put("ptGroupLeaderName").put("ptMeasure")
                .put("ptGroupMemberName").put("ptRectifyName").put("ptTeamLeaderName").put("ptIsConstruction")
                .put("ptContractorName").put("ptSupervisorName").put("ptCode").put("ptCategory").put("pType")
                .put(request.getPtEvaluationTimes(), "ptEvaluationTimeStart", "ptEvaluationTimeEnd");
        String parentQstId = request.getParentQstId();
        List<QsRecordVo> list = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(parentQstId)) {
            List<String> qstIdList = new ArrayList<>();
            if (parentQstId.contains(",")) {
                String[] split = parentQstId.split(",");
                qstIdList.addAll(Arrays.asList(split));
            } else {
                qstIdList.add(parentQstId);
            }

            List<QsType> childQsType = qsTypeService.getChildQsType(qstIdList);
            if (!CollectionUtils.isEmpty(childQsType)) {
                List<String> qstIds = childQsType.stream().map(QsType::getQstId).collect(Collectors.toList());
                query.put("qstIdList", qstIds);
            }

            list = baseMapper.getQualitySafetyRecordList(query.getQuery());
            Set<String> qstNameSet = new HashSet<>();
            Iterator<QsRecordVo> iterator = list.iterator();
            while (iterator.hasNext()) {
                QsRecordVo next = iterator.next();
                if (next != null) {
                    String qstName = next.getQstName();
                    if (qstNameSet.contains(qstName)) {
                        iterator.remove();
                        continue;
                    }
                    qstNameSet.add(qstName);
                }
            }
        } else {
            throw new JPMException("请配置需要统计的类别编码");
        }
        Map<String, Object> resultMap = new HashMap<>();
        int sum = list.stream().mapToInt(QsRecordVo::getRecordCount).sum();
        resultMap.put("list", list);
        resultMap.put("recordCountSum", sum);
        resultMap.put("chart", null);
        return BaseResponse.ok(resultMap);
    }

    @Override
    public BaseResponse getHighQsRecordList(QsRecordDto request, String uid) {
        Map<String, QsType> qsTypeMap = getQsTypeMap();
        if (ValidateUtil.isNotNull(request.getQstId())) {
            List<String> allIds = findDescendants(request.getQstId(), qsTypeMap);
            request.setQstIds(allIds);
        }
        QueryMap<QsRecordDto> query = new QueryMap<>(request);
        query.put("authSql", null).put("pId").put("pName").put("conName").put("ptPmName").put("ptCseName")
                .put("ptType").put("state").put("createUname").put("ptGroupLeaderName").put("ptMeasure")
                .put("ptGroupMemberName").put("ptRectifyName").put("ptTeamLeaderName").put("ptIsConstruction")
                .put("ptContractorName").put("ptSupervisorName").put("ptCode").put("ptCategory").put("qstPath")
                .put("pType").put("projectName").put("qstIds")
                .put(request.getPtEvaluationTimes(), "ptEvaluationTimeStart", "ptEvaluationTimeEnd")
                .put("ptEvaluationTimeFilterStart").put("ptEvaluationTimeFilterEnd")
                .put("qstId");
        IPage<QsRecordDto> page = new Page<>(request.getPage(), request.getSize());
        List<QsRecordResponse> qsRecords = baseMapper.getHighQsRecordList(page, query.getQuery());
        getQsRecordDutyList(qsRecords);
        return BaseResponse.ok(qsRecords, Long.valueOf(page.getTotal()).intValue());
    }

    private List<String> findDescendants(String parentId, Map<String, QsType> nodeMap) {
        List<String> allIds = new ArrayList<>();
        if (!nodeMap.containsKey(parentId)) {
            return allIds;
        }
        allIds.add(parentId);
        for (QsType qsType : nodeMap.values()) {
            if (qsType.getQstParentId() != null && qsType.getQstParentId().equals(parentId)) {
                allIds.add(qsType.getQstId());
                allIds.addAll(findDescendants(qsType.getQstId(), nodeMap));
            }
        }
        Set<String> set = new HashSet<>(allIds);
        return new ArrayList<>(set);
    }

    @Override
    public BaseResponse updateQsPlanTaskQsRecordType(QsPlanTaskQsRecordTypeRequest request) {
        List<QsRecord> data = baseMapper.getQsPlanTaskQsRecordById(request.getQspId());
        log.info("updateQsPlanTaskQsRecordTypeRequest:{}", JacksonUtils.toJSONString(request));
        log.info("QsRecord:{}", JacksonUtils.toJSONString(data));
        List<QsRecordTypeRequest> qsRecordRequests = request.getQsRecords();
        for (QsRecordTypeRequest qsRecordRequest : qsRecordRequests) {
            QsRecord qsRecord = data.stream().filter(n -> n.getQsrId().equals(qsRecordRequest.getQsrId())).findFirst().orElse(null);
            log.info("QsRecord:{}", JacksonUtils.toJSONString(qsRecord));
            if (qsRecord != null) {
                qsRecord.setQstId(qsRecordRequest.getQstId());
                qsRecordService.updateById(qsRecord);
            }
        }
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse getQsPlanTaskListExport(QsPlanTaskDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<QsPlanTaskDto> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("pId").put("pName").put("conName").put("ptPmName").put("ptCseName")
                .put("ptType").put("state").put("createUname").put("ptGroupLeaderName").put("ptMeasure")
                .put("ptGroupMemberName").put("ptRectifyName").put("ptTeamLeaderName").put("ptIsConstruction")
                .put("ptContractorName").put("ptSupervisorName").put("ptCode").put("ptCategory")
                .put(request.getPtEvaluationTimes(), "ptEvaluationTimeStart", "ptEvaluationTimeEnd").put("overdueTask").put("ptCid");
        List<QsPlanTaskVo> data = baseMapper.getQsPlanTaskListExport(query.getQuery());
        List<String> ids = data.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
        for (QsPlanTaskVo d : data) {
            d.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(d.getQsFinishCount()), new BigDecimal(d.getQsRecordCount()), 4, RoundingMode.DOWN));
            d.setQsReplyRate(MathUtil.decimalDivide(new BigDecimal(d.getQsReplyCount()), new BigDecimal(d.getQsRecordCount()), 4, RoundingMode.DOWN));
        }
        log.info("QsPlanTaskListExport:{}", JacksonUtils.toJSONString(data));
        return BaseResponse.ok(data);
    }

    private List<QsPlanTaskVo> getInfos(List<QsPlanTaskVo> data, List<TaskProcessin> taskProcessins) {
        List<QsPlanTaskVo> safeList = new CopyOnWriteArrayList<>(data);
        Runnable task = () -> {
            safeList.forEach(d -> {
            });
        };
        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();
        return safeList;
    }

    private void getOverDueDays(QsPlanTaskVo d) {
        BigDecimal overdueDays = BigDecimal.ZERO;
        if (d.getPtRectifyActualTime() != null) {
            if (d.getPtRectifyRequireTime() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d.getPtRectifyActualTime());
                long days = DateUtil.between(d.getPtRectifyRequireTime(), calendar.getTime(), DateUnit.DAY, false);
                log.info("overdueDays:{}", days);
                overdueDays = BigDecimal.valueOf(days);
            }
            if (overdueDays.compareTo(BigDecimal.ZERO) > 0) {
                d.setOverdueDays(overdueDays);
            }
        }
    }

    @Override
    public BaseResponse copyHighQsRecord(String qstId, String qsrId, String ptId, String uId) {
        if (ValidateUtil.isAllEmpty(qstId, qsrId)) return BaseResponse.fail("缺少必填参数");
        QsRecord qs = qsRecordService.getById(qsrId);
        if (ValidateUtil.isNull(qs)) {
            return BaseResponse.fail("未查询到该数据");
        }
        SysUser user = sysUserService.getById(uId);
        QsRecord qsData = new QsRecord();
        org.springframework.beans.BeanUtils.copyProperties(qs, qsData);
        qsData.setQsrId(GenerateUUID.getUUID());
        qsData.setQsrCreateTime(new Date());
        qsData.setQsrCreateUid(user.getUId());
        qsData.setQsrCreateUname(user.getUName());
        qsData.setQstId(qstId);
        qsData.setQsrPic(GenerateUUID.getUUID());
        qsData.setPtId(ptId);
        qsData.setQsrRectifyPic(GenerateUUID.getUUID());
        sysFileAnnexService.copyFileAnnex2NewRelation(Arrays.asList(qs.getQsrPic(), qs.getQsrRectifyPic())
                , Arrays.asList(qsData.getQsrPic(), qsData.getQsrRectifyPic()), qsData.getQsrId());
        qsRecordService.save(qsData);
        //子表
        QsRecordDuty qsr = iQsRecordDutyService.getQsRecordQsrId(qsrId);
        if (ValidateUtil.isNotNull(qsr)) {
            QsRecordDuty qsRecordDuty = new QsRecordDuty();
            org.springframework.beans.BeanUtils.copyProperties(qsr, qsRecordDuty);
            qsRecordDuty.setId(GenerateUUID.getUUID());
            qsRecordDuty.setQsrId(qsData.getQsrId());
            iQsRecordDutyService.save(qsRecordDuty);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageYWExport(ProSafetyLedgerDtlDTO request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<ProSafetyLedgerDtlDTO> query = new QueryMap<>(request);

        Map<String, QsType> qsTypeMap = getQsTypeMap();
        List<String> allIds = new ArrayList<>();
        if (ValidateUtil.isNotNull(request.getQstId())) {
            allIds = findDescendants(request.getQstId(), qsTypeMap);
            request.setQstIds(allIds);
        }
        Date xcStart = null;
        Date xcEnd = null;
        if (ValidateUtil.isNotNull(request.getPtEvaluationTimes())) {
            if (ValidateUtil.isNotNull(request.getPtEvaluationTimes().get(0))) {
                if (StrUtil.isNotEmpty(request.getPtEvaluationTimes().get(0))) {
                    xcStart = DateUtil.parse(request.getPtEvaluationTimes().get(0), "yyyy-MM-dd");
                }
            }
            if (ValidateUtil.isNotNull(request.getPtEvaluationTimes().get(1))) {
                if (StrUtil.isNotEmpty(request.getPtEvaluationTimes().get(1))) {
                    xcEnd = DateUtil.parse(request.getPtEvaluationTimes().get(1), "yyyy-MM-dd");
                }
            }
        }

        query.put("authSql", authSql).put("pName").put("pCode")
                .put(request.getPtEvaluationTimes(), "xcStart", "xcEnd").put("ptCategory").put("pType").put("ptType").put("pProjectStatus").put("pStatus").put("sortSideFiled").put("sortType").put("qstIds", request.getQstIds());
        List<ProSafetyLedgerDtlDTO> data = baseMapper.getProSafetyLedgerPageExport(query.getQuery());
        List<String> projectIds = data.stream().map(ProSafetyLedgerDtlDTO::getpId).distinct().collect(Collectors.toList());
        List<ConContract> contractList = new ArrayList<>();
        List<QsPlanTask> qsPlanTaskList = new ArrayList<>();
        List<QsRecord> qsRecordList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(projectIds)) {
            contractList = baseMapper.getConContractListAll(projectIds);
            qsPlanTaskList = baseMapper.selectListsAll(projectIds, xcStart, xcEnd, request.getPtCategory(), allIds, request.getPtType());
            qsRecordList = baseMapper.selectQsRecordListsAll(projectIds, xcStart, xcEnd, request.getPtCategory(), allIds, request.getPtType());
        }
        Map<String, List<QsPlanTask>> qsPlanTaskMap = qsPlanTaskList.stream().collect(Collectors.groupingBy(QsPlanTask::getPId));

        //log.info("qsRecordList:{}",JacksonUtils.toJSONString(qsRecordList));
        qsRecordList = qsRecordList.stream().filter(q -> q.getPId() != null).collect(Collectors.toList());
        qsRecordList = qsRecordList.stream().filter(q -> q.getPId() != null).collect(Collectors.toList());
        Map<String, List<QsRecord>> qsRecordMap = new HashMap<>();
        Map<String, List<QsRecord>> qsRecordMapFinish = new HashMap<>();
        if (CollectionUtil.isNotEmpty(qsRecordList)) {
            qsRecordMap = qsRecordList.stream().collect(Collectors.groupingBy(QsRecord::getPId));
            List<QsRecord> qsRecordListFinish = qsRecordList.stream().filter(q -> q.getQsrRectifyStatus().equals("03")).collect(Collectors.toList());
            qsRecordMapFinish = qsRecordListFinish.stream().collect(Collectors.groupingBy(QsRecord::getPId));
        }
        //log.info("qsRecordMap:{}",JacksonUtils.toJSONString(qsRecordMap));
        //log.info("qsRecordMapFinish:{}",JacksonUtils.toJSONString(qsRecordMapFinish));
        Map<String, List<QsRecord>> finalQsRecordMap = qsRecordMap;
        Map<String, List<QsRecord>> finalQsRecordMapFinish = qsRecordMapFinish;
        List<ConContract> finalContractList = contractList;
        data.forEach(e -> {
            e.setConstructionConMoney(finalContractList.stream()
                    .filter(c -> c.getConProjectId().equals(e.getpId())).map(ConContract::getConMoneyTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
            e.setQsPlanTaskCount(qsPlanTaskMap.getOrDefault(e.getpId(), new ArrayList<>()).size());
            e.setQsRecordCount(finalQsRecordMap.getOrDefault(e.getpId(), new ArrayList<>()).size());
            e.setQsFinishCount(finalQsRecordMapFinish.getOrDefault(e.getpId(), new ArrayList<>()).size());
            if (e.getQsRecordCount() == 0) {
                e.setQsFinishRate(BigDecimal.ZERO);
            } else {
                e.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(e.getQsFinishCount())
                        , new BigDecimal(e.getQsRecordCount()), 4, RoundingMode.DOWN).multiply(new BigDecimal(100)));
            }
            //log.info("{}-{}-{}-{}-{}-{}",e.getPName(),e.getQsPlanTaskCount(),e.getQsRecordCount(),e.getQsFinishCount(),e.getQsFinishRate(),e.getConstructionConMoney());
        });
        data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishRate).reversed()).collect(Collectors.toList());
        return data;
    }

    @Override
    public BaseResponse verifyqsPlanTask(String pId, String ptType, String token) {
        String qualitySafeControl = iSysConfigService.getConfigByCode("qualitySafeControl");
        if (ValidateUtil.isEmpty(qualitySafeControl)) return BaseResponse.fail("缺少基本配置");
        BaseResponse userByToken = userProducerFeign.getUserByToken(token);
        JSONObject jsonObject = new JSONObject(userByToken.getData());
        //单位
        String cId = jsonObject.getStr("cId");
        if (ValidateUtil.isNull(cId)) {
            return BaseResponse.fail("当前用户没有分配单位，请联系管理员");
        }
        List<QsPlanTaskVo> qsPlanTasks = baseMapper.selectQsPlanTaskList(pId, ptType, DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN));
        if (!CollectionUtils.isEmpty(qsPlanTasks)) {
            Long count = qsPlanTasks.stream().filter(e -> e.getcId().equals(cId)).count();
            if (ptType.equals("08")) {
                if (count >= Long.valueOf(qualitySafeControl)) {
                    return BaseResponse.fail("为响应集团文件，质量安全检査中【施工单位自查】每天每个项目每个公司两种类型各只能上传" + qualitySafeControl + "条检查数据。");
                }
            } else if (ptType.equals("02")) {
                if (count >= Long.valueOf(qualitySafeControl)) {
                    return BaseResponse.fail("为响应集团文件，质量安全检査中【监理单位检查】每天每个项目每个公司两种类型各只能上传" + qualitySafeControl + "条检查数据。");
                }
            }
        }

        return BaseResponse.ok();
    }

    @Override
    public void generateSms() {
        //提醒内容调整：流程编码：XXXX，项目名称;数贸中心周边道路改造工程，该数据条目临近整改/超出完成时间，请尽快整改并完成审批。
        // 提醒方式调整：短信形式调整。  提醒条件调整：整改日期前三天，及超出整改日期
        List<QsPlanTaskSmsVo> qsPlanTaskList = baseMapper.selectListWithoutRectify();
        List<SysShortMessageModel> sysShortMessageModelList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(qsPlanTaskList)) {
            qsPlanTaskList.forEach(e -> {
                if (e.getPtEvaluationTime() != null) {
                    DateTime dateTime = DateTime.of(e.getPtEvaluationTime());
                    DateTime now = DateTime.now();
                    DateTime three = dateTime.offsetNew(DateField.DAY_OF_YEAR, -3);
                    //log.info("dateTime:{}now:{} three:{}",dateTime, now, three);
                    //整改日期前三天
                    if (three.compareTo(now) < 0) {
                        String content = "流程编码：" + e.getPtCode() + "，项目名称;" + e.getPName() + "，该数据条目临近整改完成时间，请尽快整改并完成审批。";
                        String contentType = "临近";
                        List<SysShortMessageLog> sysShortMessages = sysShortMessageLogMapper
                                .selectSysShortMessage(e.getId(), contentType, e.getPtCode());
                        List<SysShortMessage> sysShortMessagesLogs = sysShortMessageMapper.selectSysShortMessage(e.getId(), contentType);
                        if (CollectionUtils.isEmpty(sysShortMessages) && CollectionUtils.isEmpty(sysShortMessagesLogs)) {
                            addSms(sysShortMessageModelList, e, now, content);
                        }
                    }
                    //超出整改日期
                    if (dateTime.compareTo(now) < 0) {
                        String content = "流程编码：" + e.getPtCode() + "，项目名称;" + e.getPName() + "，该数据条目超出整改完成时间，请尽快整改并完成审批。";
                        String contentType = "超出";
                        List<SysShortMessageLog> sysShortMessagesLogs = sysShortMessageLogMapper
                                .selectSysShortMessage(e.getId(), contentType, e.getPtCode());
                        List<SysShortMessage> sysShortMessages = sysShortMessageMapper.selectSysShortMessage(e.getId(), contentType);
                        if (CollectionUtils.isEmpty(sysShortMessages) && CollectionUtils.isEmpty(sysShortMessagesLogs)) {
                            addSms(sysShortMessageModelList, e, now, content);
                        }
                    }
                }
            });
        }
        userProducerFeign.batchCreateShortMessage(sysShortMessageModelList);
    }

    private void addSms(List<SysShortMessageModel> sysShortMessageModelList,
                        QsPlanTaskSmsVo e, DateTime now, String content) {
        SysShortMessageModel sysShortMessageModel = new SysShortMessageModel();
        sysShortMessageModel.setSmId(GenerateUUID.getUUID());
        sysShortMessageModel.setSmContent(content);
        sysShortMessageModel.setSmMobilePhone(e.getUPhone());
        sysShortMessageModel.setSmUid(e.getUId());
        sysShortMessageModel.setSmFileid(e.getId());
        sysShortMessageModel.setSmUname(e.getUName());
        sysShortMessageModel.setSmCreateTime(now);
        sysShortMessageModel.setSmCreateUid("68D319413F904195A859973BD4C5702D");
        sysShortMessageModel.setSmCreateUname("admin");
        sysShortMessageModelList.add(sysShortMessageModel);
    }

    @Override
    public BaseResponse getProSafetyLedgerPageYWNew(ProSafetyLedgerDtlDTO request, String token) {
        long begin = System.currentTimeMillis();
        log.info("性能监控getProSafetyLedgerPageYW begin time :" + begin);
        QueryMap<ProSafetyLedgerDtlDTO> query = new QueryMap<>(request);
        Map<String, QsType> qsTypeMap = getQsTypeMap();
        List<String> allIds = new ArrayList<>();
        if (ValidateUtil.isNotNull(request.getQstId())) {
            allIds = findDescendants(request.getQstId(), qsTypeMap);
        }
        if (CollectionUtil.isNotEmpty(request.getQstIds())) {
            for (String qstId : request.getQstIds()) {
                allIds.addAll(findDescendants(qstId, qsTypeMap));
            }
        }
        allIds = allIds.stream().distinct().collect(Collectors.toList());
        log.info("allIds:{}", JacksonUtils.toJSONString(allIds));
        log.info("性能监控getProSafetyLedgerPageYW select qstype time :" + (System.currentTimeMillis() - begin));
        Date xcStart;
        Date xcEnd;
        if (ValidateUtil.isNotNull(request.getPtEvaluationTimes())) {
            if (ValidateUtil.isNotNull(request.getPtEvaluationTimes().get(0))) {
                if (StrUtil.isNotEmpty(request.getPtEvaluationTimes().get(0))) {
                    xcStart = DateUtil.parse(request.getPtEvaluationTimes().get(0), "yyyy-MM-dd");
                } else {
                    xcStart = null;
                }
            } else {
                xcStart = null;
            }
            if (ValidateUtil.isNotNull(request.getPtEvaluationTimes().get(1))) {
                if (StrUtil.isNotEmpty(request.getPtEvaluationTimes().get(1))) {
                    xcEnd = DateUtil.parse(request.getPtEvaluationTimes().get(1), "yyyy-MM-dd");
                } else {
                    xcEnd = null;
                }
            } else {
                xcEnd = null;
            }
        } else {
            xcEnd = null;
            xcStart = null;
        }
        QueryWrapper<PreProject> queryWrapper = new QueryWrapper<>();
        List<PreProject> pros = projectMapper.selectList(queryWrapper);
        pros = pros.stream().filter(n -> n.getDeleted() == false).collect(Collectors.toList());
        if (StrUtil.isNotEmpty(request.getPName())) {
            pros = pros.stream().filter(n -> n.getpName() != null && n.getpName().contains(request.getPName())).collect(Collectors.toList());
        }
        if (StrUtil.isNotEmpty(request.getPType())) {
            pros = pros.stream().filter(n -> request.getPType().equals(n.getpType())).collect(Collectors.toList());
        }
        List<String> projectIds = pros.stream().map(PreProject::getpId).filter(Objects::nonNull).collect(Collectors.toList());
        QueryWrapper<ConContract> queryWrapper2 = new QueryWrapper<>();
        List<ConContract> cons = contractMapper.selectList(queryWrapper2);
        cons = cons.stream().filter(n -> projectIds.contains(n.getConProjectId())).collect(Collectors.toList());
        List<String> conTypeCodes = getSysDictionaryCodeChildByParentId("05643475-f71d-42e3-84e5-eb39e2c636bf");
        conTypeCodes.add("01");
        conTypeCodes.add("JPMsoft_HTLX_SH02JLHT");
        log.info("conTypeCodes:{}", JacksonUtils.toJSONString(conTypeCodes));
        cons = cons.stream().filter(n -> conTypeCodes.contains(n.getConType())).collect(Collectors.toList());
        Map<String, Object> resultMap = new HashMap<>();
        List<ProSafetyLedgerDtlDTO> data = new ConcurrentSkipListSet<>(Comparator.comparing(ProSafetyLedgerDtlDTO::getpId)
                .thenComparing(ProSafetyLedgerDtlDTO::getConId)
                .thenComparing(ProSafetyLedgerDtlDTO::getConstructionUnitId))
                .stream().collect(Collectors.toList());
        query.put("pName").put("pCode").put(request.getPtEvaluationTimes(), "xcStart", "xcEnd")
                .put("ptCategory").put("pType").put("ptType").put("ptTypes").put("pProjectStatus").put("pStatus");
        log.info("query:{}", JacksonUtils.toJSONString(query));
        List<QsPlanTaskVo> qsPlanTasksAll = baseMapper.getProSafetyLedgerPageNew(query.getQuery());
        log.info("性能监控getProSafetyLedgerPageYW select qsplanTask time :" + (System.currentTimeMillis() - begin));
        if (xcStart != null) {
            qsPlanTasksAll = qsPlanTasksAll.stream().filter(n -> n.getPtEvaluationTime().after(xcStart)).collect(Collectors.toList());
        }
        if (xcEnd != null) {
            qsPlanTasksAll = qsPlanTasksAll.stream().filter(n -> n.getPtEvaluationTime().before(xcEnd)).collect(Collectors.toList());
        }
        log.info("qsPlanTasks size:{}", qsPlanTasksAll.size());
        List<String> qstIds = qsPlanTasksAll.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
        List<QsRecordResponse> qsRecords = baseMapper.getQsRecordList(qstIds);
        if (CollectionUtil.isNotEmpty(allIds)) {
            List<String> finalAllIds = allIds;
            qsRecords = qsRecords.stream().filter(n -> finalAllIds.contains(n.getQstId())).collect(Collectors.toList());
            List<String> ptIds = qsRecords.stream().map(QsRecordResponse::getPtId).collect(Collectors.toList());
            qsPlanTasksAll = qsPlanTasksAll.stream().filter(n -> ptIds.contains(n.getId())).collect(Collectors.toList());
        }
        log.info("qsRecords size:{}", qsRecords.size());
        QueryWrapper<SysCompany> queryWrapper3 = new QueryWrapper<>();
        List<SysCompany> sysCompanys = sysCompanyMapper.selectList(queryWrapper3);
        //log.info("qsRecords:{}",JacksonUtils.toJSONString(qsRecords));
        Map<String, List<QsPlanTaskVo>> dataByProject = qsPlanTasksAll.stream().filter(n -> StrUtil.isNotEmpty(n.getpId())).collect(Collectors.groupingBy(QsPlanTaskVo::getpId));
        log.info("性能监控getProSafetyLedgerPageYW select dataByProject time :" + (System.currentTimeMillis() - begin));
        log.info("dataByProject:{}", dataByProject.size());
        Map<String, PreProject> proMap = pros.stream()
                .collect(Collectors.toMap(PreProject::getpId, Function.identity()));
        Map<String, ConContract> conMap = cons.stream()
                .collect(Collectors.toMap(ConContract::getConId, Function.identity()));
        Map<String, SysCompany> sysCompanyMap = sysCompanys.stream()
                .collect(Collectors.toMap(SysCompany::getcId, Function.identity()));
        // 使用并发集合确保线程安全
        // 将原始 map 的 entrySet 并行化处理
        List<QsRecordResponse> finalQsRecords = qsRecords;
        List<ProSafetyLedgerDtlDTO> finalData = data;
        List<ConContract> finalCons = cons;
        for (Map.Entry<String, List<QsPlanTaskVo>> entry : dataByProject.entrySet()) {
            String projectId = entry.getKey();
            PreProject preProject = pros.stream().filter(p -> projectId.equals(p.getpId())).findFirst().orElse(null);
            if (preProject == null) {
                continue;
            }
            List<QsPlanTaskVo> qsPlanTaskVos = entry.getValue();
            if (CollectionUtil.isNotEmpty(qsPlanTaskVos)) {
                List<String> constructionUnitIds = qsPlanTaskVos.stream().map(
                        QsPlanTaskVo::getPtContractorId).distinct().collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(constructionUnitIds)) {
                    for (String constructionUnitId : constructionUnitIds) {
                        List<ProSafetyLedgerDtlDTO> proSafetyLedgerDtlDTOS = data.stream()
                                .filter(item ->
                                        entry.getKey().equals(item.getpId())
                                                && "".equals(item.getConId())
                                                && constructionUnitId.equals(item.getConstructionUnitId())).collect(Collectors.toList());
                        if (CollectionUtil.isEmpty(proSafetyLedgerDtlDTOS)) {
                            ProSafetyLedgerDtlDTO dto = new ProSafetyLedgerDtlDTO();
                            dto.setpId(entry.getKey());
                            dto.setPName(preProject.getpName());
                            dto.setPCode(preProject.getpCode());
                            dto.setPType(preProject.getpType());
                            dto.setPProjectStatus(preProject.getpProjectStatus());
                            dto.setPInvTotal(preProject.getpInvTotal());
                            dto.setContractId("");
                            dto.setConstructionUnitId(constructionUnitId);
                            SysCompany constructionUnit = sysCompanys.stream().filter(c -> constructionUnitId.equals(c.getcId())).findFirst().orElse(null);
                            if (constructionUnit != null) {
                                dto.setConstructionUnitName(constructionUnit.getcName());
                            }
                            List<ConContract> cons2 = cons.stream().filter(con ->
                                    conTypeCodes.contains(con.getConType()) && entry.getKey().equals(con.getConProjectId())).collect(Collectors.toList());
                            if (CollectionUtil.isNotEmpty(cons2)) {
                                BigDecimal constructionConMoney = cons2.stream().map(ConContract::getConMoneyTotal)
                                        .filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                                dto.setConstructionConMoney(constructionConMoney);
                            }
                            List<QsPlanTaskVo> listQsPlanTask = qsPlanTaskVos.stream().filter(item ->
                                    constructionUnitId.equals(item.getPtContractorId())).collect(Collectors.toList());
                            int qsPlanTaskCount = listQsPlanTask.size();
                            dto.setQsPlanTaskCount(qsPlanTaskCount);
                            int qsRecordCount = 0;
                            int qsFinishCount = 0;
                            BigDecimal qsRecordRate = BigDecimal.ZERO;
                            for (QsPlanTaskVo qsPlanTaskVO : listQsPlanTask) {
                                List<QsRecordResponse> qsRecordsTask = qsRecords
                                        .stream().filter(item -> qsPlanTaskVO.getId().equals(item.getPtId())).collect(Collectors.toList());
                                if (CollectionUtil.isNotEmpty(qsRecordsTask)) {
                                    qsRecordCount += qsRecordsTask.size();
                                    qsFinishCount += (int) qsRecordsTask.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count();
                                }
                            }
                            if (qsRecordCount < qsPlanTaskCount) {
                                log.info("问题统计：问题总数：{}，问题完成数：{}", qsRecordCount, qsFinishCount);
                            }
                            if (qsRecordCount > 0) {
                                qsRecordRate = BigDecimal.valueOf(qsFinishCount).divide(BigDecimal.valueOf(qsRecordCount), 2, RoundingMode.HALF_UP);
                            }
                            dto.setQsRecordCount(qsRecordCount);
                            dto.setQsFinishCount(qsFinishCount);
                            dto.setQsFinishRate(qsRecordRate);
                            data.add(dto);
                        }
                    }
                }
            }
        }
        log.info("getProSafetyLedgerPageYW datasize:{}", data.size());
        log.info("性能监控getProSafetyLedgerPageYW select getProSafetyLedgerDtlDTO time :" + (System.currentTimeMillis() - begin));
        data = getProSafetyLedgerDtlDTOSort(request, data);
        //log.info("getProSafetyLedgerPageYW data:{}", JacksonUtils.toJSONString(data));
        resultMap.put("data", data);
        Integer qsPlanTaskCount = data.stream().mapToInt(ProSafetyLedgerDtlDTO::getQsPlanTaskCount).sum();
        int qsRecordCount = data.stream().mapToInt(ProSafetyLedgerDtlDTO::getQsRecordCount).sum();
        int qsFinishCount = data.stream().mapToInt(ProSafetyLedgerDtlDTO::getQsFinishCount).sum();
        BigDecimal constructionConMoneySum = cons.stream()
                .map(ConContract::getConMoneyTotal)
                .filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal pInvTotalSum = pros.stream().map(PreProject::getpInvTotal)
                .filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal qsFinishRateSum = null;
        if (qsRecordCount > 0) {
            qsFinishRateSum = BigDecimal.valueOf(qsFinishCount).divide(BigDecimal.valueOf(qsRecordCount), 4, RoundingMode.HALF_UP);
        }
        resultMap.put("constructionConMoneySum", constructionConMoneySum.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP));
        resultMap.put("pInvTotalSum", pInvTotalSum.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP));
        resultMap.put("qsPlanTaskCount", qsPlanTaskCount);
        resultMap.put("qsRecordCount", qsRecordCount);
        resultMap.put("qsFinishCount", qsFinishCount);
        resultMap.put("qsFinishRateSum", qsFinishRateSum);
        String code = "inspectionStatistics";
        String inspectionStatisticsConfig = sysConfigService.getConfigByCode(code);
        List<QsStatisticsConfigDto> statisticsConfigList = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(inspectionStatisticsConfig)) {
            statisticsConfigList = JSON.parseArray(inspectionStatisticsConfig, QsStatisticsConfigDto.class);
        }
        List<String> parentQstIdList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(statisticsConfigList)) {
            for (QsStatisticsConfigDto statisticsConfigDto : statisticsConfigList) {
                parentQstIdList.add(statisticsConfigDto.getParentQstId());
            }
        }
//        if(CollectionUtil.isNotEmpty(parentQstIdList)){
//            qsRecords=qsRecords.stream().filter(qsRecord -> parentQstIdList.contains(qsRecord.getQstId())).collect(Collectors.toList());
//        }
        List<QsStatisticsDto> statisticsDtoList = new ArrayList<>();
        String json = sysConfigService.getConfigByCode("ProblemDescriptionStatistics");
        JSONArray jsonArray = new JSONArray(json);
        Set<String> allIds0 = Collections.emptySet();
        Set<String> allIds1 = Collections.emptySet();

        if (!CollectionUtils.isEmpty(jsonArray) && jsonArray.size() >= 2) {
            try {
                JSONObject json0 = jsonArray.getJSONObject(0); // 质量
                JSONObject json1 = jsonArray.getJSONObject(1); // 安全
                allIds0 = new HashSet<>(findDescendants(json0.getStr("parentQstId"), qsTypeMap)); // 质量
                allIds1 = new HashSet<>(findDescendants(json1.getStr("parentQstId"), qsTypeMap)); // 安全
            } catch (Exception e) {
                // 日志记录或默认处理
                allIds0 = Collections.emptySet();
                allIds1 = Collections.emptySet();
            }
        }

        statisticsDtoList = BeanUtils.copyList(statisticsConfigList, QsStatisticsDto.class);
        Map<String, Integer> qualityCount = new HashMap<>();
        Map<String, Integer> safetyCount = new HashMap<>();

        for (QsStatisticsDto qsStatisticsDto : statisticsDtoList) {
            QsRecordStatisticsDto qsRecordStatisticsDto = new QsRecordStatisticsDto();
            List<QsRecordResponse> qsRecords2 = qsRecords.stream()
                    .filter(qsRecord -> ValidateUtil.isNotEmpty(qsRecord.getQstPath())
                            && qsRecord.getQstPath().contains(qsStatisticsDto.getParentQstId()))
                    .collect(Collectors.toList());

            List<QsRecordResponse> importantQuestions = qsRecords2.stream()
                    .filter(qsRecord -> qsRecord.getQstIsImportant() != null &&
                            qsRecord.getQstIsImportant().equals(1))
                    .collect(Collectors.toList());

            int questionNum = CollectionUtil.isEmpty(qsRecords2) ? 0 : qsRecords2.size();
            int importantQuestionNum = CollectionUtil.isEmpty(importantQuestions) ? 0 : importantQuestions.size();

            if ("质量".equals(qsStatisticsDto.getTitle()) || "安全".equals(qsStatisticsDto.getTitle())) {
                for (QsRecordResponse qsRecord : qsRecords2) {
                    String qstPath = qsRecord.getQstPath();
                    String ptId = qsRecord.getPtId();
                    String[] ids = qstPath.split("/");
                    for (String id : ids) {
                        if (allIds0.contains(id)) {
                            qualityCount.put(ptId, qualityCount.getOrDefault(ptId, 0) + 1);
                        } else if (allIds1.contains(id)) {
                            safetyCount.put(ptId, safetyCount.getOrDefault(ptId, 0) + 1);
                        }
                    }
                }
            }

            List<QsRecordResponse> rectificationList = qsRecords2.stream()
                    .filter(qsRecord -> StrUtil.isNotBlank(qsRecord.getQsrRectifyStatus()) && "03".equals(qsRecord.getQsrRectifyStatus()))
                    .collect(Collectors.toList());

            int rectificationNum = rectificationList.size();
            int importantRectificationNum = (int) rectificationList.stream()
                    .filter(qsRecord -> qsRecord.getQstIsImportant() != null &&
                            qsRecord.getQstIsImportant().equals(1))
                    .count();

            double rectificationRate = calculateRate(rectificationNum, questionNum);
            double importantRectificationRate = calculateRate(importantRectificationNum, importantQuestionNum);

            qsRecordStatisticsDto.setQuestionNum(questionNum);
            qsRecordStatisticsDto.setImportantQuestionNum(importantQuestionNum);
            qsRecordStatisticsDto.setRectificationNum(rectificationNum);
            qsRecordStatisticsDto.setImportantRectificationNum(importantRectificationNum);
            qsRecordStatisticsDto.setRectificationRate(String.format("%.2f", rectificationRate));
            qsRecordStatisticsDto.setImportantRectificationRate(String.format("%.2f", importantRectificationRate));

            qsStatisticsDto.setQsRecordStatisticsDto(qsRecordStatisticsDto);
        }

        log.info("性能监控getProSafetyLedgerPageYW setQsRecordStatisticsDto last time :" + (System.currentTimeMillis() - begin));
        resultMap.put("statisticList", statisticsDtoList);
        resultMap.put("qualityCount", qualityCount.size());
        resultMap.put("safetyCount", safetyCount.size());
        log.info("resultMap:{}", JacksonUtils.toJSONString(resultMap));
        return BaseResponse.ok(resultMap);
    }

    private void fillCommonFields(ProSafetyLedgerDtlDTO dto, PreProject project, String contractId,
                                  String contractorId, SysCompany company, ConContract contract) {
        dto.setpId(project.getpId());
        dto.setPName(project.getpName());
        dto.setPCode(project.getpCode());
        dto.setPType(project.getpType());
        dto.setPProjectStatus(project.getpProjectStatus());
        dto.setPInvTotal(project.getpInvTotal());
        dto.setContractId(contractId);
        dto.setContractName(contract != null ? contract.getConName() : null);
        dto.setConstructionUnitId(contractorId);
        dto.setConstructionUnitName(company != null ? company.getcName() : null);
        dto.setConstructionConMoney(contract != null ? contract.getConMoneyTotal() : BigDecimal.ZERO);
    }

    private double calculateRate(int numerator, int denominator) {
        return denominator == 0 ? 0 : ((double) numerator / denominator) * 100;
    }

    private BigDecimal calculateRate(BigDecimal childCount, BigDecimal allCount) {
        BigDecimal rate = BigDecimal.ZERO;
        if (allCount.compareTo(BigDecimal.ZERO) != 0) {
            rate = childCount.divide(allCount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        }
        return rate;
    }

    // 工具方法：计算统计信息
    private void calculateStats(List<QsPlanTask> tasks, List<QsRecordResponse> qsRecords, ProSafetyLedgerDtlDTO dto) {
        dto.setQsPlanTaskCount(tasks.size());
        int recordCount = 0;
        int finishCount = 0;
        for (QsPlanTask task : tasks) {
            List<QsRecordResponse> records = qsRecords.stream()
                    .filter(r -> task.getId().equals(r.getPtId()))
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(records)) {
                recordCount += records.size();
                finishCount += (int) records.stream()
                        .filter(r -> "03".equals(r.getQsrRectifyStatus()))
                        .count();
            }
        }
        BigDecimal rate = BigDecimal.ZERO;
        if (recordCount > 0) {
            rate = BigDecimal.valueOf(finishCount)
                    .divide(BigDecimal.valueOf(recordCount), 2, RoundingMode.HALF_UP);
        }

        dto.setQsRecordCount(recordCount);
        dto.setQsFinishCount(finishCount);
        dto.setQsFinishRate(rate);
    }

    @Override
    public List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageYWExportNew(ProSafetyLedgerDtlDTO request, String uid) {
        long begin = System.currentTimeMillis();
        log.info("性能监控getProSafetyLedgerPageYW begin time :" + begin);
        QueryMap<ProSafetyLedgerDtlDTO> query = new QueryMap<>(request);
        Map<String, QsType> qsTypeMap = getQsTypeMap();
        List<String> allIds = new ArrayList<>();
        if (ValidateUtil.isNotNull(request.getQstId())) {
            allIds = findDescendants(request.getQstId(), qsTypeMap);
        }
        if (CollectionUtil.isNotEmpty(request.getQstIds())) {
            for (String qstId : request.getQstIds()) {
                allIds.addAll(findDescendants(qstId, qsTypeMap));
            }
        }
        allIds = allIds.stream().distinct().collect(Collectors.toList());
        log.info("allIds:{}", JacksonUtils.toJSONString(allIds));
        log.info("性能监控getProSafetyLedgerPageYW select qstype time :" + (System.currentTimeMillis() - begin));
        Date xcStart;
        Date xcEnd;
        if (ValidateUtil.isNotNull(request.getPtEvaluationTimes())) {
            if (ValidateUtil.isNotNull(request.getPtEvaluationTimes().get(0))) {
                if (StrUtil.isNotEmpty(request.getPtEvaluationTimes().get(0))) {
                    xcStart = DateUtil.parse(request.getPtEvaluationTimes().get(0), "yyyy-MM-dd");
                } else {
                    xcStart = null;
                }
            } else {
                xcStart = null;
            }
            if (ValidateUtil.isNotNull(request.getPtEvaluationTimes().get(1))) {
                if (StrUtil.isNotEmpty(request.getPtEvaluationTimes().get(1))) {
                    xcEnd = DateUtil.parse(request.getPtEvaluationTimes().get(1), "yyyy-MM-dd");
                } else {
                    xcEnd = null;
                }
            } else {
                xcEnd = null;
            }
        } else {
            xcEnd = null;
            xcStart = null;
        }
        QueryWrapper<PreProject> queryWrapper = new QueryWrapper<>();
        List<PreProject> pros = projectMapper.selectList(queryWrapper);
        pros = pros.stream().filter(n -> n.getDeleted() == false).collect(Collectors.toList());
        if (StrUtil.isNotEmpty(request.getPName())) {
            queryWrapper.like("P_Name", request.getPName());
            pros = pros.stream().filter(n -> request.getPName().contains(n.getpName())).collect(Collectors.toList());
        }
        if (StrUtil.isNotEmpty(request.getPType())) {
            pros = pros.stream().filter(n -> request.getPType().contains(n.getpType())).collect(Collectors.toList());
        }
        QueryWrapper<ConContract> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.in("Con_Project_Id", pros.stream().map(PreProject::getpId).collect(Collectors.toList()));
        List<ConContract> cons = contractMapper.selectList(queryWrapper2);
        List<String> conTypeCodes = getSysDictionaryCodeChildByParentId("05643475-f71d-42e3-84e5-eb39e2c636bf");
        Map<String, Object> resultMap = new HashMap<>();
        List<ProSafetyLedgerDtlDTO> data = new ArrayList<>();
        query.put("pName").put("pCode").put(request.getPtEvaluationTimes(), "xcStart", "xcEnd")
                .put("ptCategory").put("pType").put("ptType").put("ptTypes").put("pProjectStatus").put("pStatus");
        List<QsPlanTaskVo> qsPlanTasks = baseMapper.getProSafetyLedgerPageNew(query.getQuery());
        log.info("性能监控getProSafetyLedgerPageYW select qsplanTask time :" + (System.currentTimeMillis() - begin));

        if (xcStart != null) {
            qsPlanTasks = qsPlanTasks.stream().filter(n -> n.getPtEvaluationTime().after(xcStart)).collect(Collectors.toList());
        }
        if (xcEnd != null) {
            qsPlanTasks = qsPlanTasks.stream().filter(n -> n.getPtEvaluationTime().before(xcEnd)).collect(Collectors.toList());
        }
        log.info("qsPlanTasks size:{}", qsPlanTasks.size());
        List<String> qstIds = qsPlanTasks.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
        List<QsRecordResponse> qsRecords = baseMapper.getQsRecordList(qstIds);
        if (CollectionUtil.isNotEmpty(allIds)) {
            List<String> finalAllIds = allIds;
            qsRecords = qsRecords.stream().filter(n -> finalAllIds.contains(n.getQstId())).collect(Collectors.toList());
            List<String> ptIds = qsRecords.stream().map(QsRecordResponse::getPtId).collect(Collectors.toList());
            qsPlanTasks = qsPlanTasks.stream().filter(n -> ptIds.contains(n.getId())).collect(Collectors.toList());
        }
        log.info("qsRecords size:{}", qsRecords.size());
        QueryWrapper<SysCompany> queryWrapper3 = new QueryWrapper<>();
        List<SysCompany> sysCompanys = sysCompanyMapper.selectList(queryWrapper3);
        //log.info("qsRecords:{}",JacksonUtils.toJSONString(qsRecords));
        Map<String, List<QsPlanTaskVo>> dataByProject = qsPlanTasks.stream().filter(n -> StrUtil.isNotEmpty(n.getpId()))
                .collect(Collectors.groupingBy(QsPlanTaskVo::getpId));
        log.info("性能监控getProSafetyLedgerPageYW select dataByProject time :" + (System.currentTimeMillis() - begin));
        log.info("dataByProject:{}", dataByProject.size());
        Map<String, ConContract> conContractMap = cons.stream()
                .filter(c -> c.getConId() != null)
                .collect(Collectors.toMap(ConContract::getConId, Function.identity()));

        Map<String, SysCompany> sysCompanyMap = sysCompanys.stream()
                .filter(c -> c.getcId() != null)
                .collect(Collectors.toMap(SysCompany::getcId, Function.identity()));

        Map<String, List<QsRecordResponse>> qsRecordByPtId = qsRecords.stream()
                .filter(r -> r.getPtId() != null)
                .collect(Collectors.groupingBy(QsRecordResponse::getPtId));

        for (Map.Entry<String, List<QsPlanTaskVo>> entry : dataByProject.entrySet()) {
            String projectId = entry.getKey();
            PreProject preProject = pros.stream()
                    .filter(p -> projectId.equals(p.getpId()))
                    .findFirst()
                    .orElse(null);
            if (preProject == null) {
                continue;
            }

            List<QsPlanTaskVo> qsPlanTaskVos = entry.getValue();
            if (CollectionUtil.isEmpty(qsPlanTaskVos)) {
                continue;
            }

            // 按合同分组
            Map<String, List<QsPlanTaskVo>> groupedByContract = qsPlanTaskVos.stream()
                    .filter(n -> StrUtil.isNotEmpty(n.getConId()))
                    .collect(Collectors.groupingBy(QsPlanTaskVo::getConId));

            for (Map.Entry<String, List<QsPlanTaskVo>> contractEntry : groupedByContract.entrySet()) {
                String contractId = contractEntry.getKey();
                ConContract conContract = conContractMap.get(contractId);
                List<QsPlanTaskVo> tasks = contractEntry.getValue();

                processAndAddDto(data, entry, preProject, conContract, tasks, qsRecordByPtId, sysCompanyMap, cons,
                        conTypeCodes);
            }

            // 处理无合同的任务
            List<QsPlanTaskVo> noContractTasks = qsPlanTaskVos.stream()
                    .filter(n -> StrUtil.isEmpty(n.getConId()))
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(noContractTasks)) {
                processAndAddDto(data, entry, preProject, null, noContractTasks, qsRecordByPtId, sysCompanyMap, cons,
                        conTypeCodes);
            }
        }
        log.info("getProSafetyLedgerPageYW datasize:{}", data.size());
        log.info("性能监控getProSafetyLedgerPageYW select getProSafetyLedgerDtlDTO time :" + (System.currentTimeMillis() - begin));
        data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishRate).reversed()).collect(Collectors.toList());
        log.info("getProSafetyLedgerPageYW data:{}", JacksonUtils.toJSONString(data));
        return data;
    }

    private void processAndAddDto(
            List<ProSafetyLedgerDtlDTO> data,
            Map.Entry<String, List<QsPlanTaskVo>> entry,
            PreProject preProject,
            ConContract conContract,
            List<QsPlanTaskVo> tasks,
            Map<String, List<QsRecordResponse>> qsRecordByPtId,
            Map<String, SysCompany> sysCompanyMap,
            List<ConContract> cons,
            List<String> conTypeCodes) {

        List<String> constructionUnitIds = tasks.stream()
                .map(QsPlanTaskVo::getPtContractorId)
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtil.isEmpty(constructionUnitIds)) return;

        for (String constructionUnitId : constructionUnitIds) {
            String contractId = conContract != null ? conContract.getConId() : "";
            List<ProSafetyLedgerDtlDTO> existing = data.stream()
                    .filter(item -> entry.getKey().equals(item.getpId())
                            && contractId.equals(item.getConId())
                            && constructionUnitId.equals(item.getConstructionUnitId()))
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(existing)) continue;

            ProSafetyLedgerDtlDTO dto = new ProSafetyLedgerDtlDTO();
            dto.setpId(entry.getKey());
            dto.setPName(preProject.getpName());
            dto.setPCode(preProject.getpCode());
            dto.setPType(preProject.getpType());
            dto.setPProjectStatus(preProject.getpProjectStatus());
            dto.setPInvTotal(preProject.getpInvTotal());
            dto.setContractId(contractId);
            dto.setContractName(conContract != null ? conContract.getConName() : "");
            dto.setConstructionUnitId(constructionUnitId);

            SysCompany constructionUnit = sysCompanyMap.get(constructionUnitId);
            if (constructionUnit != null) {
                dto.setConstructionUnitName(constructionUnit.getcName());
            }

            List<ConContract> cons2 = cons.stream()
                    .filter(con -> conTypeCodes.contains(con.getConType()) && entry.getKey().equals(con.getConProjectId()))
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(cons2)) {
                BigDecimal constructionConMoney = cons2.stream()
                        .map(ConContract::getConMoneyTotal)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO);
                dto.setConstructionConMoney(constructionConMoney);
            }

            List<QsPlanTaskVo> listQsPlanTask = tasks.stream()
                    .filter(item -> constructionUnitId.equals(item.getPtContractorId()))
                    .collect(Collectors.toList());

            int qsPlanTaskCount = listQsPlanTask.size();
            dto.setQsPlanTaskCount(qsPlanTaskCount);

            int qsRecordCount = 0;
            int qsFinishCount = 0;

            for (QsPlanTaskVo task : listQsPlanTask) {
                List<QsRecordResponse> records = qsRecordByPtId.getOrDefault(task.getId(), Collections.emptyList());
                if (CollectionUtil.isNotEmpty(records)) {
                    qsRecordCount += records.size();
                    qsFinishCount += (int) records.stream()
                            .filter(r -> "03".equals(r.getQsrRectifyStatus()))
                            .count();
                }
            }

            if (qsRecordCount < qsPlanTaskCount) {
                log.debug("问题统计：问题总数：{}，问题完成数：{}", qsRecordCount, qsFinishCount);
            }

            BigDecimal qsRecordRate = BigDecimal.ZERO;
            if (qsRecordCount > 0) {
                qsRecordRate = BigDecimal.valueOf(qsFinishCount)
                        .divide(BigDecimal.valueOf(qsRecordCount), 2, RoundingMode.HALF_UP);
            }
            dto.setQsRecordCount(qsRecordCount);
            dto.setQsFinishCount(qsFinishCount);
            dto.setQsFinishRate(qsRecordRate);

            List<String> ptIds = listQsPlanTask.stream()
                    .map(QsPlanTaskVo::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            BigDecimal questionFinishedLate = qsRecordByPtId.entrySet().stream()
                    .filter(e -> ptIds.contains(e.getKey()))
                    .flatMap(e -> e.getValue().stream())
                    .filter(r -> r.getQsrRectifyActualTime() != null
                            && r.getQsrRectifyActualTime().compareTo(r.getQsrRectifyRequireTime()) > 0)
                    .count() > 0 ? BigDecimal.valueOf(qsFinishCount) : BigDecimal.ZERO;

            BigDecimal questionFinishedLateRate = calculateRate(questionFinishedLate, BigDecimal.valueOf(qsFinishCount));
            dto.setQsFinishOverdueCount(questionFinishedLate);
            dto.setQsFinishOverdueRate(questionFinishedLateRate);

            List<QsPlanTaskVo> questionFinishedOnTime = listQsPlanTask.stream()
                    .filter(q -> q.getQptIsCheck() == 1)
                    .collect(Collectors.toList());

            BigDecimal questionFinishedOnTimeSum = BigDecimal.valueOf(questionFinishedOnTime.size());
            BigDecimal questionFinishedOnTimeRate = calculateRate(questionFinishedOnTimeSum, BigDecimal.valueOf(qsFinishCount));

            dto.setQsFinishOnTimeCount(questionFinishedOnTimeSum);
            dto.setQsFinishOnTimeRate(questionFinishedOnTimeRate);

            data.add(dto);
        }
    }

    @Override
    public BaseResponse approveData(QsPlanTaskDto request) {
        String id = request.getId();
        QsPlanTask entity;
        SysFile fileEntity;
        if (StrUtil.isEmpty(id)) {
            return BaseResponse.fail("缺少请求参数ID");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null)
                return BaseResponse.fail("信息不存在，请刷新重试！");

            fileEntity = sysFileService.getById(id);
            /**
             * 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
             * 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(DateTime.now());
            List<String> ids = new ArrayList<>();
            ids.add(entity.getId());
            List<QsRecordResponse> qsRecordsAll = leaderBoardMapper.getQsRecordList(ids);
            List<QsRecordResponse> qsRecords = qsRecordsAll.stream().filter(item -> item.getPtId()
                    .equals(entity.getId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(qsRecords)) {
                entity.setPtRectifyRequireTime(qsRecords.stream().map(QsRecordResponse::getQsrRectifyRequireTime)
                        .max(Date::compareTo).orElse(null));
            }
            List<QsRecordResponse> qsRecordsNotFinished = qsRecords.stream()
                    .filter(qsRecord -> !"03".equals(qsRecord.getQsrRectifyStatus())).collect(Collectors.toList());
            if (ApprovalStatusEnum.Already_Over.getIndex().toString().equals(fileFlowStatus) && CollectionUtil.isEmpty(qsRecordsNotFinished)) {
                Date time = qsRecords.stream().map(QsRecordResponse::getQsrRectifyActualTime).filter(Objects::nonNull)
                        .max(Date::compareTo).orElse(null);
                if (time != null) {
                    entity.setPtRectifyActualTime(time);
                } else {
                    QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
                    wrapper.eq("tp_entityid", id);
                    List<TaskProcessin> taskProcessins = taskProcessinService.list(wrapper);
                    time = taskProcessins.stream().map(TaskProcessin::getTpDotime)
                            .max(Date::compareTo).orElse(null);
                    entity.setPtRectifyActualTime(time);
                }
                getOverDueDays(entity);
            }
            baseMapper.updateById(entity);
        }

        return BaseResponse.ok();
    }

    private void getOverDueDays(QsPlanTask d) {
        BigDecimal overdueDays = BigDecimal.ZERO;
        if (d.getPtRectifyActualTime() != null) {
            if (d.getPtRectifyRequireTime() != null) {
                long days = DateUtil.between(d.getPtRectifyRequireTime(), d.getPtRectifyActualTime(), DateUnit.DAY, false);
                overdueDays = BigDecimal.valueOf(days);
            }
            if (overdueDays.compareTo(BigDecimal.ZERO) > 0) {
                d.setOverdueDays(overdueDays);
            } else {
                d.setOverdueDays(null);
            }
        }
    }

    @Override
    public BaseResponse refreshQsPlanTask(QsPlanTaskDto request) {
        log.info("refreshQsPlanTask");
        QueryWrapper<QsPlanTask> queryWrapper = new QueryWrapper<>();
        List<QsPlanTask> entitys = baseMapper.selectList(queryWrapper);
        List<String> ids = entitys.stream().map(BaseEntity::getId).collect(Collectors.toList());
        List<QsRecordResponse> qsRecordsAll = leaderBoardMapper.getQsRecordList(ids);
        QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
        wrapper.in("tp_entityid", ids);
        List<TaskProcessin> taskProcessins = taskProcessinService.list(wrapper);
        if (CollectionUtil.isNotEmpty(entitys)) {
            getInfos(entitys, qsRecordsAll, taskProcessins);
        }
        return BaseResponse.ok();
    }

    /**
     * 🟢绿色：所有问题按时整改完成（闭合率100%且无超期）
     * 🟡黄色：存在临期整改项目（距离整改期限≤3天）
     * 红色：存在超期未整改项目或未整改项目
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse timingRefreshOutTimeStatus() {
        List<QsPlanTask> data = baseMapper.selectRefreshOutTimeStatus();
        if (!CollectionUtils.isEmpty(data)) {
            List<String> ids = data.stream().map(QsPlanTask::getId).collect(Collectors.toList());
            LambdaQueryWrapper<QsRecord> qrWrapper = new LambdaQueryWrapper<>();
            qrWrapper.in(QsRecord::getPtId, ids);
            List<QsRecord> qsRecords = qsRecordService.list(qrWrapper);
            List<QsPlanTask> updateStatus = new ArrayList<>();//需要更新状态的数据
            for (QsPlanTask d : data) {
                if (ValidateUtil.isEmpty(d.getPtCategory())) {
                    log.error("未知类型ptCategory！");
                    continue;
                }
                List<QsRecord> records = qsRecords.stream().filter(item -> item.getPtId().equals(d.getId())).collect(Collectors.toList());
                if (d.getPtCategory().equals("01")) //质量检查  要求整改时间在子表
                {
                    String zgStatus = null;//预警状态
                    for (QsRecord record : records) {
                        Date time = ValidateUtil.isNull(record.getQsrRectifyFinishTime()) ? new Date() : record.getQsrRectifyFinishTime();
                        Integer day = com.jpmsoft.projectProducer.utils.DateUtil.daysBetween(time, record.getQsrRectifyRequireTime());
                        if (day == null) continue;
                        if (day < 0) {
                            if (ValidateUtil.isEmpty(record.getQsrRectifyStatus()) || !record.getQsrRectifyStatus().equals("03")) {
                                zgStatus = "3"; //逾期未整改
                                break;
                            }
                        } else if (day <= 3 && (ValidateUtil.isEmpty(record.getQsrRectifyStatus()) || !record.getQsrRectifyStatus().equals("03"))) {//存在临期未整改
                            zgStatus = "2";//存在临期未整改
                        }
                    }
                    if (ValidateUtil.isEmpty(zgStatus)) //不存在超时以及临期未完成
                    {
                        List<QsRecord> finishs = records.stream().filter(item -> ValidateUtil.isNotEmpty(item.getQsrRectifyStatus()) && item.getQsrRectifyStatus().equals("03")).collect(Collectors.toList());
                        if (finishs.size() == records.size()) //全部整改完成
                        {
                            zgStatus = "1";
                            if (ValidateUtil.isNotEmpty(d.getOutTimeStatus()) && d.getOutTimeStatus().equals("3")) {//由逾期改为逾期完成
                                zgStatus = "4";
                            }
                        }
                    }
                    if ((!ValidateUtil.isAnObjEmpty(zgStatus, d.getOutTimeStatus()) && !d.getOutTimeStatus().equals(zgStatus)) ||
                            (ValidateUtil.isEmpty(d.getOutTimeStatus()) && ValidateUtil.isNotEmpty(zgStatus))) {//需要更新状态
                        d.setOutTimeStatus(zgStatus);
                        updateStatus.add(d);
                    }
                } else if (d.getPtCategory().equals("02")) //安全检查 要求整改时间在主表
                {
                    if (ValidateUtil.isNotNull(d.getPtRectifyRequireTime())) continue; //数据没有要求完成整改时间
                    String zgStatus = null;//预警状态
                    for (QsRecord record : records) {
                        Integer day = com.jpmsoft.projectProducer.utils.DateUtil.daysBetween(record.getQsrRectifyFinishTime(), d.getPtRectifyRequireTime());
                        if (day == null) continue;
                        if (day < 0) {
                            if (ValidateUtil.isEmpty(record.getQsrRectifyStatus()) || !record.getQsrRectifyStatus().equals("03")) //存在临期未整改
                            {
                                zgStatus = "3";//存在超时（超时整改以及超时未整改）
                                break;
                            }
                        } else if (day <= 3 && (ValidateUtil.isEmpty(record.getQsrRectifyStatus()) || !record.getQsrRectifyStatus().equals("03"))) {//存在零七
                            zgStatus = "2";//存在临期未整改
                        }
                    }
                    System.out.println("安全检查zgStatus:  " + zgStatus);
                    if (ValidateUtil.isEmpty(zgStatus)) //不存在超时以及临期未完成
                    {
                        List<QsRecord> finishs = records.stream().filter(item -> ValidateUtil.isNotEmpty(item.getQsrRectifyStatus()) && item.getQsrRectifyStatus().equals("03")).collect(Collectors.toList());
                        if (finishs.size() == records.size())//全部整改完成
                        {
                            zgStatus = "1";
                            if (ValidateUtil.isNotEmpty(d.getOutTimeStatus()) && d.getOutTimeStatus().equals("3")) {//由逾期改为逾期完成
                                zgStatus = "4";
                            }
                        }
                    }
                    if ((!ValidateUtil.isAnObjEmpty(zgStatus, d.getOutTimeStatus()) && !d.getOutTimeStatus().equals(zgStatus)) ||
                            (ValidateUtil.isEmpty(d.getOutTimeStatus()) && ValidateUtil.isNotEmpty(zgStatus))) {//需要更新状态
                        d.setOutTimeStatus(zgStatus);
                        updateStatus.add(d);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(updateStatus)) {
                updateBatchById(updateStatus, 100);
                //生成平台消息
                sysMessageService.createMessage(updateStatus);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse generateSubFileRecordsAndNotifyAssignees(QsPlanTaskDto request,String token) {

        // 1. 参数校验
        if (ValidateUtil.isEmpty(request.getId())) {
            return BaseResponse.fail(ResMsg.notId);
        }

        QsPlanTask qsPlanTask = getById(request.getId());
        if (ValidateUtil.isNull(qsPlanTask)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (qsPlanTask.getPushStatus() != null && qsPlanTask.getPushStatus().equals("02")){
            return BaseResponse.fail(ResMsg.infoAlreadyPush);
        }

        // 2. 处理旧待办与抄送（删除主表待办，发送抄送通知）
        delegateTodoAndNotifyCcUsers(request.getId(), redisUtils.getUid(token));



        // 3. 定义状态值：区分主表（7）和子表（1）
        // 主表状态：7 (Already_Over - 已结束)
        Integer mainState = ApprovalStatusEnum.Already_Over.getIndex();
        // 子表/子文件状态：1 (Under_Approval - 审批中/待办)
        Integer recordState  = ApprovalStatusEnum.Under_Approval.getIndex();

        // 4. 更新主表 QsPlanTask 的状态为 7
        if (qsPlanTask.getState() == null || !qsPlanTask.getState().equals(mainState)) {
            qsPlanTask.setState(mainState);
            qsPlanTask.setPushStatus("02");
            this.updateById(qsPlanTask);
        }

        // 5. 更新主任务关联的 SysFile 状态为 7
        // 此处用于同步主文件状态，使其与主表状态一致
        sysFileService.onlyUpdateState(mainState, qsPlanTask.getId());

        // 6. 查询需要处理的子记录（仅限已分配整改人的）
        List<QsRecord> records = qsRecordService.list(
                new LambdaQueryWrapper<QsRecord>()
                        .eq(QsRecord::getPtId, request.getId())
                        .isNotNull(QsRecord::getQsrRectifyUid)
        );

        // 7. 过滤出在 SysFile 中不存在的新记录 (toInsert)
        List<QsRecord> toInsert = filterRecordsNotExistInSysFile(records);

        // 8. 【关键修正】仅更新需要插入 SysFile 的“新” QsRecord 记录的状态为 1
        // 已存在的旧 QsRecord 状态保持不变
        if (!toInsert.isEmpty()) {
            for (QsRecord record : toInsert) {
                record.setQsrState(recordState); // recordState = 1
            }
            qsRecordService.updateBatchById(toInsert); // 仅更新 toInsert 列表
        }

        // 9. 插入新的 SysFile 记录 (Fileflowstatus = "1")
        // 内部逻辑确保状态为 1
        List<SysFile> sysFiles = insertSysFilesIfNotExist(toInsert, qsPlanTask, request);


        // 11. 事务提交后，发送子表待办任务 (toInsert)
        // 仅为新生成的子记录发送待办
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                bathSendQsRecord(toInsert,request.getTCode(), request.getTeId(),token);
            }
        });

        return BaseResponse.ok(toInsert.stream().map(QsRecord::getQsrId).collect(Collectors.toList()));
    }



    @Override
    public BaseResponse delegateTodoAndNotifyCcUsers(String id, String uid) {

        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        // 1. 获取主任务和文件信息
        QsPlanTask qsPlanTask = getById(id);
        SysFile sysFile = sysFileService.getById(id);
        if (ValidateUtil.isNull(qsPlanTask) || ValidateUtil.isNull(sysFile)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        // 2. 获取所有子记录
        List<QsRecord> qsRecordList = qsRecordService.list(
                new LambdaQueryWrapper<QsRecord>()
                        .eq(QsRecord::getPtId, id)
        );

        // 3. 收集所有子记录中的抄送人并去重
        Set<String> ccUserList = new HashSet<>();
        qsRecordList.forEach(item -> {
            String ccUsers = item.getQsrCcUser();
            // 假设抄送人ID使用全角分号“；”分隔
            if (ValidateUtil.isNotEmpty(ccUsers)) {
                String[] ccUserArray = ccUsers.split("；");
                ccUserList.addAll(Arrays.asList(ccUserArray));
            }
        });

        // 4. 删除主任务的待办任务
        // 此处不进行幂等性检查，确保每次流程转移时都会清理旧待办
        taskMapper.deleteTaskByTaskEntityID(id);

        return BaseResponse.ok("主任务待办已删除");

        // todo 抄送通知 - 已取消
/*
        // 5. 如果没有抄送人，直接返回
        if (CollectionUtils.isEmpty(ccUserList)) {
            return BaseResponse.ok();
        }

        // =========================================================
        // 6. 【幂等性检查和过滤】查询已发送用户列表
        // 目标：防止对同一个任务的同一个用户重复发送抄送通知
        // =========================================================
        List<CarbonCopy> existingCopies = carbonCopyService.list(
                new LambdaQueryWrapper<CarbonCopy>()
                        .eq(CarbonCopy::getCcFileid, id) // 根据主任务 ID (CcFileid) 查询
        );

        // 将已发送的用户ID收集到一个Set中，方便快速查找
        Set<String> sentCcUsers = existingCopies.stream()
                .map(CarbonCopy::getCcUser)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // =========================================================

        // 7. 准备抄送数据
        SysCcSendDto ccSendDto = new SysCcSendDto();
        ccSendDto.setTaskid(id);
        ccSendDto.setFileId(id);
        ccSendDto.setUserId(uid);
        ccSendDto.setCcIds(new ArrayList<>(ccUserList));
        ccSendDto.setTaskname(sysFile.getFiletitle());

        // 直接调用本地方法替代远程调用
        String user = ccSendDto.getUserId(); // 当前用户
        List<String> ccuser = ccSendDto.getCcIds(); // 抄送用户 (来自 ccUserList)
        String fileid = ccSendDto.getFileId(); // 流程实例id
        List<CarbonCopy> pojos = new ArrayList<>();

        // 8. 最终用户去空去重（继承自 ccUserList，此处是二次确认）
        ccuser = ccuser.stream().filter(n -> !ValidateUtil.isEmpty(n)).distinct().collect(Collectors.toList());

        // 9. 循环生成抄送信息，仅针对未发送过的用户
        for (String u : ccuser) {
            // 【过滤逻辑】如果该用户ID已经存在于已发送列表 (sentCcUsers) 中，则跳过
            if (sentCcUsers.contains(u)) {
                continue;
            }

            CarbonCopy pojo = new CarbonCopy();
            pojo.setCcId(GenerateUUID.getUUID());
            pojo.setCcActid("");
            pojo.setCcUser(u);
            pojo.setCcTitle(ccSendDto.getTaskname());
            pojo.setCcFlowid("");
            pojo.setCcTime(new Date());
            pojo.setCcCreateuser(user);
            pojo.setCcRead("未阅");
            pojo.setCcReadtime(null);
            pojo.setCcFileid(fileid);
            pojo.setCcProcessid("");
            pojo.setCcExplain(null);
            pojo.setIsSystem("user");
            // 注意：customerId参数需要从上下文中获取 (此处省略)
            pojos.add(pojo);
        }

        // 10. 保存抄送信息 (pojos 中只包含新用户的记录)
        if (!pojos.isEmpty()) {
//            carbonCopyService.saveBatch(pojos);
            return BaseResponse.ok(pojos.size() + " new CarbonCopy records created.");
        }

        return BaseResponse.ok("No new CC users found to notify.");*/
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncQsRecordState(String id) {
        QsPlanTask qsPlanTask = baseMapper.selectById(id);
        if (qsPlanTask == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        Integer invalidState = ApprovalStatusEnum.Invalid.getIndex();
        if (!invalidState.equals(qsPlanTask.getState())) {
            return BaseResponse.ok("该安全检查未作废，无需同步");
        }

        List<String> recordIds = qsRecordService.listObjs(Wrappers.lambdaQuery(QsRecord.class)
                .select(QsRecord::getQsrId)
                .eq(QsRecord::getPtId, id), Object::toString);

        if (CollectionUtils.isEmpty(recordIds)) {
            return BaseResponse.ok("该安全检查未创建安全检查清单");
        }

        sysFileService.onlyUpdateState(invalidState, recordIds);

        boolean updateQsState = qsRecordService.update(Wrappers.lambdaUpdate(QsRecord.class)
                .set(QsRecord::getQsrState, invalidState)
                .set(QsRecord::getQsrStateTime, new Date())
                .in(QsRecord::getQsrId, recordIds));

        return updateQsState ? BaseResponse.ok("同步成功") : BaseResponse.fail("同步失败");
    }


    private void bathSendQsRecord(List<QsRecord> toInsert, String tCode,String teId,String token) {

        List<BatchhandlingTaskListDto> taskListDtos = new ArrayList<>();
        BatchhandlingTaskDto task = new BatchhandlingTaskDto();
        task.setCurrentNode("startNode");
        task.setOnlineConditions(String.valueOf(true));
        task.settCode(tCode);
        task.setTeId(ValidateUtil.isEmpty(teId) ? "All" : teId);

//        String uId = redisUtils.getUid(token);
        // 待办接收人
        String processedFieldName = sysConfigService.getConfigByCode("qsRecordProcessed");
        if (StrUtil.isBlank(processedFieldName)) {
            processedFieldName = "qsrCcUser"; // 默认字段名
        }


        for (QsRecord record : toInsert) {
            BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();

            Object processedValue = ReflectUtil.getFieldValue(record, processedFieldName);
            String processedId = Convert.toStr(processedValue, "");

            tkl.setFileId(record.getQsrId())
                    .setUId(record.getQsrSuperviseUid())
//                    .setProcessed(record.getQsrRectifyUid())  // 现在改为 项目负责人
                    .setProcessed(processedId)
                    .setOperator(record.getQsrSuperviseUid());
            taskListDtos.add(tkl);
        }
        if (!CollectionUtils.isEmpty(taskListDtos)) {
            task.setSendList(taskListDtos);
            BaseResponse taskRes = bpmFeign.batchSendTask(task,token);
            log.info("taskRes:  " + taskRes);
            if (taskRes == null || !taskRes.getSuccess()) {
                throw new JPMException(ResMsg.taskCreateFail);
            }
        }
    }

    private List<QsRecord> filterRecordsNotExistInSysFile(List<QsRecord> records) {
        if (CollectionUtils.isEmpty(records)) return Collections.emptyList();

        List<String> ids = records.stream()
                .map(QsRecord::getQsrId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (ids.isEmpty()) return Collections.emptyList();

        Set<String> existIds = sysFileService.list(
                        new LambdaQueryWrapper<SysFile>().in(SysFile::getFileid, ids)
                ).stream()
                .map(SysFile::getFileid)
                .collect(Collectors.toSet());

        return records.stream()
                .filter(r -> r.getQsrId() != null && !existIds.contains(r.getQsrId()))
                .collect(Collectors.toList());
    }

    private List<SysFile> insertSysFilesIfNotExist(List<QsRecord> records,
                                                   QsPlanTask qsPlanTask,
                                                   QsPlanTaskDto request) {

        if (CollectionUtils.isEmpty(records)) return Collections.emptyList();

        List<SysFile> inserted = new ArrayList<>();

        for (QsRecord record : records) {
            SysFile file = new SysFile();
            file.setFileid(record.getQsrId());
            file.setFiletitle(request.getFileTitle());
            file.setFilecode(request.getTCode());
            file.setFilesectionid(request.getSecId());
            file.settCode(request.getTCode());
            file.settId(request.getTId());
            file.setFileproject(record.getPId());
            file.setFilecontract(record.getConId());
            file.setFileflowstatus(ApprovalStatusEnum.Under_Approval.getIndex().toString());
            file.setFilecreateuser(qsPlanTask.getCreateUid());
            file.setFilecreateuserName(qsPlanTask.getCreateUname());
            file.setFilecreatedate(new Date());

            inserted.add(file);

            sysFileService.saveFile(
                    record.getQsrId(),
                    request.getTId(),
                    ApprovalStatusEnum.Under_Approval.getIndex().toString(),
                    qsPlanTask.getCreateUid(),
                    qsPlanTask.getCreateUname(),
                    new Date(),
                    record.getPId(),
                    request.getFileTitle(),
                    request.getTCode(),
                    "create",
                    GenerateUUID.getUUID(),
                    request.getSecId(),
                    request.getTCode(),
                    record.getConId()
            );
        }

        return inserted;
    }



    private void getInfos(List<QsPlanTask> data, List<QsRecordResponse> qsRecordsAll, List<TaskProcessin> taskProcessins) {
        List<QsPlanTask> safeList = new ArrayList<>();
        data.forEach(d -> {
            List<QsRecordResponse> qsRecords = qsRecordsAll.stream().filter(item -> item.getPtId().equals(d.getId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(qsRecords)) {
                d.setPtRectifyRequireTime(qsRecords.stream().map(QsRecordResponse::getQsrRectifyRequireTime)
                        .max(Date::compareTo).orElse(null));
            }
            if (ApprovalStatusEnum.Already_Over.getIndex().equals(d.getState())) {
                Date time = qsRecords.stream().map(QsRecordResponse::getQsrRectifyActualTime).filter(Objects::nonNull)
                        .max(Date::compareTo).orElse(null);
                if (time != null) {
                    d.setPtRectifyActualTime(time);
                } else {
                    List<TaskProcessin> taskProcessins2 = taskProcessins.stream()
                            .filter(n -> n.getTpEntityid().equals(d.getId())).collect(Collectors.toList());
                    //实际整改时间：取审批过程结束后，最后一个审批人的审批时间
                    time = taskProcessins2.stream().map(TaskProcessin::getTpDotime)
                            .max(Date::compareTo).orElse(null);
                    d.setPtRectifyActualTime(time);
                }
            } else {
                d.setPtRectifyActualTime(null);
            }
            getOverDueDays(d);
            safeList.add(d);
        });
        for (QsPlanTask entity : safeList) {
            baseMapper.updateTimeById(entity);
        }
    }

    private static List<ProSafetyLedgerDtlDTO> getProSafetyLedgerDtlDTOSort(ProSafetyLedgerDtlDTO request, List<ProSafetyLedgerDtlDTO> data) {
        if (CollectionUtil.isNotEmpty(data)) {
            String sortSideFiled = request.getSortSideFiled();
            String sortType = request.getSortType();
            if (ValidateUtil.isNotEmpty(sortSideFiled) && ValidateUtil.isNotEmpty(sortType)) {
                switch (sortSideFiled) {
                    case "qsRecordCount":
                        if ("desc".equalsIgnoreCase(sortType)) {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsRecordCount).reversed()).collect(Collectors.toList());
                        } else {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsRecordCount)).collect(Collectors.toList());
                        }
                        break;
                    case "qsFinishCount":
                        if ("desc".equalsIgnoreCase(sortType)) {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishCount).reversed()).collect(Collectors.toList());
                        } else {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishCount)).collect(Collectors.toList());
                        }
                        break;
                    case "qsFinishRate":
                        if ("desc".equalsIgnoreCase(sortType)) {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishRate).reversed()).collect(Collectors.toList());
                        } else {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsFinishRate)).collect(Collectors.toList());
                        }
                        break;
                    case "qsPlanTaskCount":
                        if ("desc".equalsIgnoreCase(sortType)) {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsPlanTaskCount).reversed()).collect(Collectors.toList());
                        } else {
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsPlanTaskCount)).collect(Collectors.toList());
                            data = data.stream().sorted(Comparator.comparing(ProSafetyLedgerDtlDTO::getQsPlanTaskCount)).collect(Collectors.toList());
                        }
                    default:
                        break;
                }
            }
        }
        return data;
    }

    private void getCount(List<ProSafetyLedgerDtlDTO> data, List<QsRecord> qsRecords, List<QsPlanTask> qsPlanTaskVosNoContract, ProSafetyLedgerDtlDTO dto) {
        Integer qsPlanTaskCount = qsPlanTaskVosNoContract.size();
        dto.setQsPlanTaskCount(qsPlanTaskCount);
        int qsRecordCount = 0;
        int qsFinishCount = 0;
        BigDecimal qsRecordRate = BigDecimal.ZERO;
        for (QsPlanTask qsPlanTaskVO : qsPlanTaskVosNoContract) {
            List<QsRecord> qsRecordsTask = qsRecords.stream().filter(item -> qsPlanTaskVO.getId().equals(item.getPtId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(qsRecordsTask)) {
                qsRecordCount += qsRecordsTask.size();
                qsFinishCount += (int) qsRecordsTask.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count();
            }
        }
        if (qsRecordCount > 0) {
            qsRecordRate = BigDecimal.valueOf(qsFinishCount).divide(BigDecimal.valueOf(qsRecordCount), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        }
        dto.setQsRecordCount(qsRecordCount);
        dto.setQsFinishCount(qsFinishCount);
        dto.setQsFinishRate(qsRecordRate);
        data.add(dto);
    }

    private Map<String, QsType> getQsTypeMap() {
        List<QsType> qsTypeList = qsTypeService.list();
        Map<String, QsType> qsTypeMap = new HashMap<>();
        for (QsType qsType : qsTypeList) {
            qsTypeMap.put(qsType.getQstId(), qsType);
        }
        return qsTypeMap;
    }

    private String getSysDictionaryIdByCode(String code) {
        return leaderBoardMapper.getSysDictionaryIdByCode(code);
    }

    private List<String> getSysDictionaryCodeByParentId(String parentId) {
        //log.info("sysDictionaryCode:{}", JacksonUtils.toJSONString(sysDictionaryCode));
        return leaderBoardMapper.getSysDictionaryCodeByParentId(parentId);
    }

    private List<String> getSysDictionaryCodeChildByParentId(String parentId) {
        List<SysDictionary> sysDictionarys = getSysDictionaryChildByParentId(parentId);
        //log.info("sysDictionaryCode:{}", JacksonUtils.toJSONString(sysDictionaryCode));
        return sysDictionarys.stream().map(SysDictionary::getdCode).collect(Collectors.toList());
    }

    private List<SysDictionary> getSysDictionaryChildByParentId(String parentId) {
        List<SysDictionary> sysDictionaryCodes = new ArrayList<>();
        List<SysDictionary> sysDictionaryList = leaderBoardMapper.getSysDictionaryByParentId(parentId);
        if (CollectionUtil.isNotEmpty(sysDictionaryList)) {
            for (SysDictionary sysDictionary : sysDictionaryList) {
                sysDictionaryCodes.add(sysDictionary);
                List<SysDictionary> sysDictionaryListChild = getSysDictionaryChildByParentId(sysDictionary.getdId());
                if (CollectionUtil.isNotEmpty(sysDictionaryListChild)) {
                    sysDictionaryCodes.addAll(sysDictionaryListChild);
                }
            }
        }
        return sysDictionaryCodes;
    }

    private List<SysDictionary> getSysDictionaryByParentId(String parentId) {
        return leaderBoardMapper.getSysDictionaryByParentId(parentId);
    }

    @Override
    public BaseResponse getQsPlanTaskListSH(QsPlanTaskDto request, String uid) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String authSql = sysUserService.getAuthSql("Qs_Plan_Task", uid, "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<QsPlanTaskDto> query = new QueryMap<>(request);
        query.put("pId").put("conId").put("ptEvaluationTime").put("pName")
                .put("conName").put("ptTeamLeaderName").put("createUname").put("state");
        List<QsRecord> list = qsRecordService.list(Wrappers.<QsRecord>lambdaQuery()
                .eq(QsRecord::getQsrRectifyUid, uid)
                .or()
                .eq(QsRecord::getQsrCcUser, uid)
                .select(QsRecord::getPtId)
        );
        if (ValidateUtil.isNotEmpty(list)) {
            Set<String> ptIds = list.stream().map(QsRecord::getPtId).filter(Objects::nonNull).collect(Collectors.toSet());
            if (sysUserService.checkShowAllDataAuthSH(uid)) {
                query.put("authSql", authSql).put("ptIds", null);
            } else {
                authSql = authSql.replaceFirst(" where ", " where ( ");
                query.put("authSql", authSql).put("ptIds", ptIds);
            }
        } else {
            if (sysUserService.checkShowAllDataAuthSH(uid)) {
                query.put("authSql", " where 1=1 ").put("ptIds", null);
            } else {
                query.put("authSql", authSql).put("ptIds", null);
            }
        }
        Page<QsPlanTaskVo> page = new Page<>(request.getPage(), request.getSize());
        List<QsPlanTaskVo> data = baseMapper.getQsPlanTaskListSH(page, query.getQuery());
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }
}