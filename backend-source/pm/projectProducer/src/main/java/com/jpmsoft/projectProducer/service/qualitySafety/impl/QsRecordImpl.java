package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsRecordDutyBathDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecord;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsType;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QualitySafetyStatisticFowYWVO;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordDutyService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsTypeService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import org.apache.commons.collections.list.CursorableLinkedList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class QsRecordImpl extends ServiceImpl<QsRecordMapper, QsRecord> implements IQsRecordService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IQsRecordDutyService qsRecordDutyService;
    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private IQsTypeService qsTypeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateQsRecord(QsRecordRequest request) {
        QsRecord entity = new QsRecord();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getQsrId())) {
            entity.setQsrCreateTime(new Date());
            entity.setQsrId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getQsrId(), request.gettId(), entity.getQsrState() + "", entity.getQsrCreateUid(), entity.getQsrCreateUname()
                    , entity.getQsrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getQsrPic(), request.getQsrRectifyPic(), request.getFileRelationId()), entity.getQsrId());

            return BaseResponse.ok(entity.getQsrId());
        } else {
            QsRecord model = baseMapper.selectById(request.getQsrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setQsrCreateTime(model.getQsrCreateTime());
            entity.setQsrCreateUid(model.getQsrCreateUid());
            entity.setQsrCreateUname(model.getQsrCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getQsrId(), request.gettId(), entity.getQsrState() + "", entity.getQsrCreateUid(), entity.getQsrCreateUname()
                    , entity.getQsrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getQsrPic(), request.getQsrRectifyPic(), request.getFileRelationId()),
                    Arrays.asList(request.getQsrPicIds(), request.getQsrRectifyPicIds(), request.getFileIds()), entity.getQsrId());

            return BaseResponse.ok(entity.getQsrId());
        }
    }

    @Override
    public void createOrUpdateQsRecord(List<QsRecordRequest> datas, String ptId) {
        QueryWrapper<QsRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Pt_Id", ptId);
        List<QsRecord> olds = baseMapper.selectList(wrapper);
        Date now = new Date();
        List<QsRecordDutyBathDTO> childTables = new ArrayList<>();
        if (CollectionUtils.isEmpty(olds)) {
            createQsRecord(datas, ptId, now, childTables);
            qsRecordDutyService.createOrUpdate(childTables);
        } else {
            //region 需要新增修改的数据
            List<String> oldIds = olds.stream().map(QsRecord::getQsrId).collect(Collectors.toList());
            //需要新增的数据
            List<QsRecordRequest> addReqs = datas.stream().filter(n -> ValidateUtil.isEmpty(n.getQsrId()) || !oldIds.contains(n.getQsrId())).collect(Collectors.toList());
            //需要更新的数据
            List<QsRecordRequest> upReqs = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getQsrId()) && oldIds.contains(n.getQsrId())).collect(Collectors.toList());
            createQsRecord(addReqs, ptId, now, childTables);
            updateQsRecord(upReqs, ptId, childTables);
            qsRecordDutyService.createOrUpdate(childTables);
            //endregion
            //region需要删除的数据
            if (!CollectionUtils.isEmpty(addReqs))
                oldIds.removeAll(addReqs.stream().map(QsRecordRequest::getQsrId).collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(upReqs))
                oldIds.removeAll(upReqs.stream().map(QsRecordRequest::getQsrId).collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(oldIds)) {
                //删除子表
                qsRecordDutyService.deleteByQsrIds(oldIds);
                //删除表单
                baseMapper.deleteBatchIds(oldIds);
                //删除流程文档
                sysFileService.deleteByFileIds(oldIds);
            }
            //endregion
        }
    }

    private void updateQsRecord(List<QsRecordRequest> datas, String ptId, List<QsRecordDutyBathDTO> childTables) {
        List<QsRecord> ups = new ArrayList<>();
        for (QsRecordRequest data : datas) {
            QsRecord entity = new QsRecord();
            BeanUtils.copyProperties(data, entity);
            entity.setPtId(ptId);
            ups.add(entity);
            sysFileService.saveFile(entity.getQsrId(), data.gettId(), entity.getQsrState() == null ? null : entity.getQsrState() + "", entity.getQsrCreateUid(), entity.getQsrCreateUname()
                    , entity.getQsrCreateTime(), data.getpId(), data.getFileTitle(), data.getFileCode(), "update",
                    data.getFileRelationId(), null, data.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(data.getQsrPic(), data.getQsrRectifyPic(), data.getFileRelationId()),
                    Arrays.asList(data.getQsrPicIds(), data.getQsrRectifyPicIds(), data.getFileIds()), entity.getQsrId());
            QsRecordDutyBathDTO qrd = new QsRecordDutyBathDTO();
            qrd.setQsrId(entity.getQsrId());
            qrd.setQsRecordDutys(data.getQsRecordDutys());
            childTables.add(qrd);
        }
        if (ups.size() > 0) updateBatchById(ups);
    }

    private void createQsRecord(List<QsRecordRequest> datas, String ptId, Date now, List<QsRecordDutyBathDTO> childTables) {
        if (CollectionUtils.isEmpty(datas)) return;
        List<QsRecord> adds = new ArrayList<>();
        for (QsRecordRequest data : datas) {
            QsRecord entity = new QsRecord();
            BeanUtils.copyProperties(data, entity);
            //entity.setQsrCreateTime(now);
            entity.setQsrId(GenerateUUID.getUUID());
            entity.setPtId(ptId);
            if (data.getQsrState() == null)
                entity.setQsrState(ApprovalStatusEnum.Not_Started.getIndex());
            adds.add(entity);
            sysFileService.saveFile(entity.getQsrId(), data.gettId(), entity.getQsrState() + "", entity.getQsrCreateUid(), entity.getQsrCreateUname()
                    , entity.getQsrCreateTime(), data.getpId(), data.getFileTitle(), data.getFileCode(), "create",
                    data.getFileRelationId(), null, data.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(data.getQsrPic(), data.getQsrRectifyPic(), data.getFileRelationId()), entity.getQsrId());
            QsRecordDutyBathDTO qrd = new QsRecordDutyBathDTO();
            qrd.setQsrId(entity.getQsrId());
            qrd.setQsRecordDutys(data.getQsRecordDutys());
            childTables.add(qrd);
        }
        if (adds.size() > 0) saveBatch(adds);
    }

    @Override
    public BaseResponse getQsRecordList(QsRecordRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Qs_Record", uId, "Qsr_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Qsr_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<QsRecordRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("conName").put("pId").put("qsrType").put("conId")
                .put("pName").put("qsrRectifyRealUname").put("qsrRectifyReply").put("qsrCategory")
                .put("qsrPatrolObject").put("qsrHazardLevel").put("qsrReasonType").put("qsrFormation");
        IPage<QsRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<QsRecordResponse> datas = baseMapper.getQsRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getQsRecordLedgerList(QsRecordRequest request, String uId) {
        // 台账"已整改数量"按流程状态 Qsr_State = 7（流程审核通过/已结束）统计，
        // 与领导看板"已完成(closeCount)"口径保持一致；
        // 不使用 Qsr_Rectify_Status='03'，避免整改回复未回写整改状态字段导致统计为 0。
        String authSql = sysUserService.getAuthSql("Qs_Record", uId, "Qsr_Create_Uid",
                OutUserAuthEnum.Organization.getIndex(), "Qsr_State");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<QsRecordRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql)
                .put("pId")
                .put("pName")
                .put("qsrCategory")
                .put("responsibleUnitId")
                .put("responsibleUnitName")
                .put("qsrState");
        IPage<QsRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<QsRecordResponse> datas = baseMapper.getQsRecordLedgerList(page, params.getQuery());
        datas.forEach(data -> {
            int problemCount = data.getProblemCount() == null ? 0 : data.getProblemCount();
            int rectifiedCount = data.getRectifiedCount() == null ? 0 : data.getRectifiedCount();
            BigDecimal rectificationRate = BigDecimal.ZERO;
            if (problemCount > 0) {
                rectificationRate = BigDecimal.valueOf(rectifiedCount)
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(problemCount), 2, RoundingMode.HALF_UP);
            }
            data.setRectificationRate(rectificationRate);
        });
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getQsRecordByQsrId(String qsrId) {
        QsRecordResponse data = baseMapper.getQsRecordByQsrId(qsrId);
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateRectifyPic(QsRecordRequest request) {
        QsRecord model = baseMapper.selectById(request.getQsrId());
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (StrUtil.isNotBlank(request.getQstId())) {
            model.setQstId(request.getQstId());
        }
        if (StrUtil.isNotBlank(request.getQsrLocation())) {
            model.setQsrLocation(request.getQsrLocation());
        }
        model.setQsrRectifyReply(request.getQsrRectifyReply());
        model.setQsrRectifyFinishTime(request.getQsrRectifyFinishTime());
        model.setQsrRectifyStatus(request.getQsrRectifyStatus());
        //为了兼容一对多的领导看板统计。
        if (StrUtil.isNotBlank(model.getPtId()) && StrUtil.isNotBlank(request.getQsrRectifyStatus())) {
            if ("03".equals(model.getQsrRectifyStatus())) {
                model.setQsrState(ApprovalStatusEnum.Already_Over.getIndex());
            } else {
                model.setQsrState(ApprovalStatusEnum.Not_Started.getIndex());
            }
        }
        model.setQsrRectifyRealUname(request.getQsrRectifyRealUname());
        model.setQsrRectifyActualTime(request.getQsrRectifyActualTime());
        baseMapper.updateById(model);
        sysFileAnnexService.refreshRelationFile(request.getQsrRectifyPic(), request.getQsrRectifyPicIds(), request.getQsrId());
        return BaseResponse.ok(null);
    }

    @Override
    public Boolean canToDeleteQsType(String qstId) {
        QueryWrapper<QsRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Qst_Id", qstId);
        if (baseMapper.selectCount(wrapper) > 0)
            return false;
        else
            return true;
    }

    @Override
    public Object getWorkbenchDetailStatistics(Integer qsrCategory, String teId) {
        return baseMapper.getWorkbenchDetailStatistics(qsrCategory, teId);
    }

    /**
     * 质量安全二级页面 统计点开后的详情
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getWorkbenchDetailStatisticsList(QsRecordRequest request, String teId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<QsRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        request.setTeId(teId);
        List<QsRecordResponse> datas = baseMapper.getWorkbenchDetailStatisticsList(page, request);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getLastSevenDaysStatistics(Integer qsrCategory, String teId) {
        //质量检查今日新增数量，默认是按创建时间统计。 1为按检查日期统计
        String qsAddTodayCode = sysConfigService.getConfigByCode("qsAddToday");
        Map<String, Integer> createMap = new HashMap<>(16);
        if (StrUtil.isNotBlank(qsAddTodayCode) && "1".equals(qsAddTodayCode)) {
            createMap = baseMapper.getLastSevenDaysCreateOther(qsrCategory, teId);
        } else {
            createMap = baseMapper.getLastSevenDaysCreate(qsrCategory, teId);
        }
        Map<String, Integer> closeMap = baseMapper.getLastSevenDaysClose(qsrCategory, teId);
        List<Integer> creates = MapToListInt(createMap);
        List<Integer> closes = MapToListInt(closeMap);
        List<String> date = DateUtil.getBeforeSevenDay();
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("create", creates);
        map.put("close", closes);
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse getQsMonthlyStatisticsByYear(Integer qsrCategory, String teId) {
        String qsAddTodayCode = sysConfigService.getConfigByCode("qsAddToday");
        String timeColumn = (CharSequenceUtil.isNotBlank(qsAddTodayCode) && "1".equals(qsAddTodayCode))
                ? "Qsr_Time" : "Qsr_Create_Time";

        List<Map<String, Object>> createStats = baseMapper.getMonthlyCreateStats(qsrCategory, teId, timeColumn);
        List<Map<String, Object>> closeStats = baseMapper.getMonthlyCloseStats(qsrCategory, teId);

        Map<String, Object> result = new HashMap<>();
        result.put("months", getRecentTwelveMonths());
        result.put("create", processMonthlyData(createStats));
        result.put("close", processMonthlyData(closeStats));

        return BaseResponse.ok(result);
    }

    /**
     * 获取最近12个月的字符串列表 (yyyy-MM)
     */
    private List<String> getRecentTwelveMonths() {
        List<String> months = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 11; i >= 0; i--) {
            LocalDateTime targetMonth = now.minusMonths(i);
            months.add(targetMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")));
        }
        return months;
    }

    /**
     * 将数据库统计结果映射为连续的数字列表
     * @param stats 数据库返回的 List，包含 month(yyyy-MM) 和 count 字段
     * @return 对应最近12个月的计数值列表
     */
    private List<Integer> processMonthlyData(List<Map<String, Object>> stats) {
        List<String> monthsAxis = getRecentTwelveMonths();
        Map<String, Integer> dataMap = new HashMap<>();

        if (stats != null) {
            for (Map<String, Object> row : stats) {
                String month = null;
                if (row.containsKey("month")) month = String.valueOf(row.get("month"));
                else if (row.containsKey("MONTH")) month = String.valueOf(row.get("MONTH"));

                Object countObj = row.get("count");
                if (countObj == null) countObj = row.get("COUNT");

                if (month != null) {
                    Integer count = countObj != null ? Integer.parseInt(countObj.toString()) : 0;
                    dataMap.put(month, count);
                }
            }
        }

        List<Integer> resultList = new ArrayList<>();
        for (String monthKey : monthsAxis) {
            resultList.add(dataMap.getOrDefault(monthKey, 0));
        }
        return resultList;
    }

    @Override
    public BaseResponse getYearQuarterMonthStatistics(QsRecordRequest request) {
        String unit = ValidateUtil.isEmpty(request.getpConstructionUnit()) ? null : request.getpConstructionUnit();
        String department = ValidateUtil.isEmpty(request.getpCompetentDepartment()) ? null : request.getpCompetentDepartment();
        String teId = ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId();
        List<Map<String, Integer>> datas = baseMapper.getYearQuarterMonthStatistics(request.getQsrCategory(), unit, department, teId);
        List<Map<String, Integer>> newDatas = new ArrayList<>();
        datas.forEach(data -> {
            if (data != null)
                newDatas.add(data);
            else {
                Map<String, Integer> map = new HashMap<>();
                map.put("processCount", 0);
                map.put("closeCount", 0);
                map.put("allCount", 0);
                newDatas.add(map);
            }
        });
        return BaseResponse.ok(newDatas);
    }

    @Override
    public BaseResponse updateRecord(QsRecordRequest request) {
        QsRecord model = baseMapper.selectById(request.getQsrId());
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (StrUtil.isNotBlank(request.getQstId())) {
            model.setQstId(request.getQstId());
        }
        if (StrUtil.isNotBlank(request.getConId())) {
            model.setConId(request.getConId());
        }
        model.setQsrUpdateUid(request.getQsrUpdateUid());
        model.setQsrUpdateTime(new Date());
        baseMapper.updateById(model);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getQualitySafetyStatisticFowYW(QualitySafetyStatisticFowYWRequest request) {
        // 筛选出问题列表

        List<QsType> qsTypeList  = qsTypeService.list();
        Map<String, QsType> qsTypeMap = new HashMap<>();
        for (QsType qsType : qsTypeList) {
            qsTypeMap.put(qsType.getQstId(), qsType);
        }

        if(ValidateUtil.isNotNull(request.getQstId())){
            List<String> allIds = findDescendants(request.getQstId(), qsTypeMap);
            request.setQstIds(allIds);
        }
        for (QsType qsType : qsTypeList) {
            QsType parentType = qsTypeMap.get(qsType.getQstParentId());
            if (parentType == null) {
                continue;
            }
            if (parentType.getChildren() == null) {
                parentType.setChildren(new ArrayList<>());
            }
            parentType.getChildren().add(qsType);
        }
        List<String> typeIdList = null;
        if (StrUtil.isNotBlank(request.getRootTypeNodeId())) {
            QsType qsType = qsTypeList.stream().filter(e -> StrUtil.equals(request.getRootTypeNodeId(), e.getQstId())).findFirst().orElse(null);
            if (qsType == null) {
                return BaseResponse.ok();
            }
            typeIdList = new ArrayList<>();
            getTypeIdByRoot(qsType, typeIdList);
        }
        // 设置节点所在路径
        setQsTypePath(qsTypeList.stream().filter(e -> "0".equals(e.getQstParentId())).collect(Collectors.toList()), "/");
        Page<QualitySafetyStatisticFowYWVO> qualitySafetyStatisticFowYWVOPage = baseMapper.countGroupByQstTypeIdList(request,
                typeIdList, new Page<>(request.getPageNum(), request.getPageSize()));


        List<QualitySafetyStatisticFowYWVO> qualitySafetyStatisticFowYWVOPageSum = baseMapper.countGroupByQstTypeIdListSum(request, typeIdList);
        int sum = qualitySafetyStatisticFowYWVOPageSum.stream().mapToInt(QualitySafetyStatisticFowYWVO::getCount).sum();
        int qsFinishSum = qualitySafetyStatisticFowYWVOPageSum.stream().mapToInt(QualitySafetyStatisticFowYWVO::getQsFinish).sum();

        for (QualitySafetyStatisticFowYWVO record : qualitySafetyStatisticFowYWVOPage.getRecords()) {
            QsType qsType = qsTypeMap.get(record.getQstId());
            if (qsType == null) {
                continue;
            }
            if (qsType.getSeverityLevel() != null) {
                switch (qsType.getSeverityLevel()){
                    case 2:
                        record.setSeverityLevelName("严重");
                        break;
                    case 3:
                        record.setSeverityLevelName("较严重");
                        break;
                    case 4:
                        record.setSeverityLevelName("一般");
                        break;
                    case 5:
                        record.setSeverityLevelName("轻微");
                        break;
                }
            }
            BigDecimal qsFinishRate = BigDecimal.valueOf(record.getQsFinish()).divide(BigDecimal.valueOf(record.getCount()), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            record.setQsFinishRateStr(qsFinishRate+"%");
            record.setQstName(qsType.getQstName());
            record.setQstPath(qsType.getQstPath());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("list",qualitySafetyStatisticFowYWVOPage.getRecords());
        map.put("total",qualitySafetyStatisticFowYWVOPage.getTotal());
        map.put("countSum",sum);
        map.put("qsFinishSum",qsFinishSum);
        return BaseResponse.ok(map);
    }



    private List<String> findDescendants(String parentId, Map<String, QsType> nodeMap) {
        List<String> allIds = new ArrayList<>();

        // 如果当前parentId不存在于map中，则返回空列表
        if (!nodeMap.containsKey(parentId)) {
            return allIds;
        }

        // 将自身id加入结果集中
        allIds.add(parentId);

        // 查找所有直接子节点，并递归地查找它们的子节点
        for (QsType qsType : nodeMap.values()) {
            if (qsType.getQstParentId() != null && qsType.getQstParentId().equals(parentId)) {
                // 将当前子节点的id加入结果集中
                allIds.add(qsType.getQstId());
                // 递归查找当前子节点的子节点，并将结果合并
                allIds.addAll(findDescendants(qsType.getQstId(), nodeMap));
            }
        }

        return allIds;
    }

    @Override
    public List<QualitySafetyStatisticFowYWVO> getQualitySafetyStatisticFowYWExport(QualitySafetyStatisticFowYWRequest request) {
        List<QsType> qsTypeList  = qsTypeService.list();
        Map<String, QsType> qsTypeMap = new HashMap<>();
        for (QsType qsType : qsTypeList) {
            qsTypeMap.put(qsType.getQstId(), qsType);
        }

        if(ValidateUtil.isNotNull(request.getQstId())){
            List<String> allIds = findDescendants(request.getQstId(), qsTypeMap);
            request.setQstIds(allIds);
        }
        for (QsType qsType : qsTypeList) {
            QsType parentType = qsTypeMap.get(qsType.getQstParentId());
            if (parentType == null) {
                continue;
            }
            if (parentType.getChildren() == null) {
                parentType.setChildren(new ArrayList<>());
            }
            parentType.getChildren().add(qsType);
        }
        List<String> typeIdList = null;
        if (StrUtil.isNotBlank(request.getRootTypeNodeId())) {
            QsType qsType = qsTypeList.stream().filter(e -> StrUtil.equals(request.getRootTypeNodeId(), e.getQstId())).findFirst().orElse(null);
            if (qsType == null) {
                return new ArrayList<>();
            }
            typeIdList = new ArrayList<>();
            getTypeIdByRoot(qsType, typeIdList);
        }
        // 设置节点所在路径
        setQsTypePath(qsTypeList.stream().filter(e -> "0".equals(e.getQstParentId())).collect(Collectors.toList()), "/");
        List<QualitySafetyStatisticFowYWVO> qualitySafetyStatisticFowYWVOPage = baseMapper.countGroupByQstTypeIdListImport(request, typeIdList);
        for (QualitySafetyStatisticFowYWVO record : qualitySafetyStatisticFowYWVOPage) {
            QsType qsType = qsTypeMap.get(record.getQstId());
            if (qsType == null) {
                continue;
            }
            if (qsType.getSeverityLevel() != null) {
                switch (qsType.getSeverityLevel()){
                    case 2:
                        record.setSeverityLevelName("严重");
                        break;
                    case 3:
                        record.setSeverityLevelName("较严重");
                        break;
                    case 4:
                        record.setSeverityLevelName("一般");
                        break;
                    case 5:
                        record.setSeverityLevelName("轻微");
                        break;
                }
            }
            BigDecimal qsFinishRate = BigDecimal.valueOf(record.getQsFinish()).divide(BigDecimal.valueOf(record.getCount()), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            record.setQsFinishRateStr(qsFinishRate+"%");
            record.setQstName(qsType.getQstName());
            record.setQstPath(qsType.getQstPath());
        }
        return qualitySafetyStatisticFowYWVOPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateQsRecordRequest(QsRecordRequest request) {
        QsRecord entity = new QsRecord();
        BeanUtils.copyProperties(request, entity);

        if (StringUtils.isBlank(request.getQsrId())) {
            entity.setQsrId(GenerateUUID.getUUID());
            entity.setQsrCreateTime(new Date());
            entity.setQsrState(0);
            entity.setQsrStateTime(new Date());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getQsrPic(), request.getQsrRectifyPic(), request.getFileRelationId()),
                    entity.getQsrId());
            this.save(entity);
        } else {
            entity.setQsrUpdateTime(new Date());

            entity.setQsrCreateTime(null);
            entity.setQsrCreateUid(null);
            entity.setQsrCreateUname(null);

            boolean updated = this.updateById(entity);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getQsrPic(), request.getQsrRectifyPic(), request.getFileRelationId()),
                    Arrays.asList(request.getQsrPicIds(), request.getQsrRectifyPicIds(), request.getFileIds()), entity.getQsrId());
            if (!updated) {
                return BaseResponse.fail("更新失败，记录可能已被删除");
            }
        }

        return BaseResponse.ok(entity.getQsrId());
    }

    @Override
    public BaseResponse deleteByQsrId(String qsrId) {

        if (StringUtils.isBlank(qsrId)) {
            return BaseResponse.fail(ResMsg.notId);
        }

        return this.removeById(qsrId)
                ? BaseResponse.ok("删除成功")
                : BaseResponse.fail("删除失败，记录不存在");
    }

    private void setQsTypePath(List<QsType> qsTypeList, String path) {
        if (CollUtil.isEmpty(qsTypeList)) {
            return;
        }
        for (QsType qsType : qsTypeList) {
            qsType.setQstPath(path);
            String currentPath = StrUtil.format("{}{}{}", qsType.getQstPath(), "/".equals(qsType.getQstPath()) ? "" : "/", qsType.getQstName());
            setQsTypePath(qsType.getChildren(), currentPath);
        }
    }

    private void getTypeIdByRoot(QsType rootType, List<String> typeIdList) {
        if (rootType == null) {
            return;
        }
        typeIdList.add(rootType.getQstId());
        if (CollUtil.isEmpty(rootType.getChildren())) {
            return;
        }
        for (QsType child : rootType.getChildren()) {
            getTypeIdByRoot(child, typeIdList);
        }
    }

    private List<Integer> MapToListInt(Map<String, Integer> map) {
        List<Integer> data = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                data.add(entry.getValue());
            }
        } else {
            for (int i = 0; i < 7; i++)
                data.add(0);
        }
        return data;
    }
}
