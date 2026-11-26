package com.jpmsoft.projectProducer.service.actualMeasurement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.CheckItemResponse;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.CheckItemMapper;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.TaskDistributionCheckMapper;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.CheckItem;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistribution;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistributionCheck;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecord;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionCheckService;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordService;
import com.jpmsoft.projectProducer.utils.ExpressionUtils;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jjz
 */
@Service
@Slf4j
public class TaskDistributionCheckImpl extends ServiceImpl<TaskDistributionCheckMapper, TaskDistributionCheck> implements ITaskDistributionCheckService {

    @Resource
    private ITaskDistributionService taskDistributionService;

    @Resource
    private IQsRecordService qsRecordService;

    @Resource
    private QsRecordMapper qsRecordMapper;

    @Resource
    private CheckItemMapper checkItemMapper;

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdateTaskDistributionCheck(List<TaskDistributionCheckModel> taskDistributionCheckModels, String tdId) {
        if (ValidateUtil.isEmpty(taskDistributionCheckModels) || ValidateUtil.isEmpty(tdId)) {
            return;
        }

        deleteByTdId(tdId);

        Integer maxOrderNo = 0;

        int nextOrderNo = maxOrderNo + 1;

        // 构建新的列表
        List<TaskDistributionCheck> taskDistributionChecks = new ArrayList<>();
        for (TaskDistributionCheckModel model : taskDistributionCheckModels) {
            TaskDistributionCheck entity = new TaskDistributionCheck();
            BeanUtils.copyProperties(model, entity);
            entity.setTdcId(GenerateUUID.getUUID());
            entity.setTdId(tdId);
            entity.setOrderNo(nextOrderNo++);
            taskDistributionChecks.add(entity);
        }

        this.saveBatch(taskDistributionChecks);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateTaskDistributionCheckSingle(TaskDistributionCheckModel model) {
        if (ValidateUtil.isEmpty(model) || ValidateUtil.isEmpty(model.getTdId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        TaskDistributionCheck entity = new TaskDistributionCheck();
        BeanUtils.copyProperties(model, entity);

        Boolean checkResult = null;
        if (ValidateUtil.isNotEmpty(model.getCheckItemExpression()) &&
                ValidateUtil.isNotEmpty(model.getCheckItemMeasureData())) {
            try {
                checkResult = validateCheckItemMeasureData(model.getCheckItemExpression(), model.getCheckItemMeasureData());
                entity.setCheckItemCheckResult(String.valueOf(checkResult));
                entity.setMeasuredFlag("02");
                if (!checkResult) {
                    entity.setHfHgFlag("01");
                } else if ("01".equals(entity.getHfHgFlag())) {
                    entity.setHfHgFlag("02");
                }
                entity.setMeasureTime(new Date());
            } catch (Exception e) {
                log.error("验证检查项测量数据时发生异常", e);
                return BaseResponse.fail("数据解析异常: " + e.getMessage());
            }
        }

        String tdcId;
        if (ValidateUtil.isNotEmpty(model.getTdcId())) {
            updateById(entity);
            tdcId = entity.getTdcId();
        } else {
            String permissionFail = checkCreatePermissionByTaskUsers(model.getTdId(), model.getCheckPersonUid());
            if (permissionFail != null) {
                return BaseResponse.fail(permissionFail);
            }
            entity.setTdcId(GenerateUUID.getUUID());
            tdcId = entity.getTdcId();

            // 锁定该 tdId，防止并发
            baseMapper.lockTdId(model.getTdId());

            Integer maxOrderNo = baseMapper.selectMaxOrderNo(model.getTdId());
            if (maxOrderNo == null) {
                maxOrderNo = 0;
            }
            entity.setOrderNo(maxOrderNo + 1);

            save(entity);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("tdcId", tdcId);
        result.put("checkResult", checkResult);

        return BaseResponse.ok(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateTaskDistributionCheckNew(List<TaskDistributionCheckModel> taskDistributionCheckModels) {
        if (ValidateUtil.isEmpty(taskDistributionCheckModels)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        List<TaskDistributionCheck> toSave = new ArrayList<>();
        List<TaskDistributionCheck> toUpdate = new ArrayList<>();
        List<String> tdcIds = new ArrayList<>();

        Map<String, List<TaskDistributionCheckModel>> groupedByTdId =
                taskDistributionCheckModels.stream()
                        .filter(m -> ValidateUtil.isNotEmpty(m.getTdId()))
                        .collect(Collectors.groupingBy(TaskDistributionCheckModel::getTdId));

        for (Map.Entry<String, List<TaskDistributionCheckModel>> entry : groupedByTdId.entrySet()) {
            String tdId = entry.getKey();
            List<TaskDistributionCheckModel> models = entry.getValue();

            // 锁定该 tdId，防止并发
            baseMapper.lockTdId(tdId);

            Integer maxOrderNo = baseMapper.selectMaxOrderNo(tdId);
            if (maxOrderNo == null) {
                maxOrderNo = 0;
            }
            int nextOrderNo = maxOrderNo + 1;

            for (TaskDistributionCheckModel model : models) {
                TaskDistributionCheck entity = new TaskDistributionCheck();
                BeanUtils.copyProperties(model, entity);

                if (ValidateUtil.isNotEmpty(model.getTdcId())) {
                    toUpdate.add(entity);
                    tdcIds.add(entity.getTdcId());
                } else {
                    String permissionFail = checkCreatePermissionByTaskUsers(tdId, model.getCheckPersonUid());
                    if (permissionFail != null) {
                        return BaseResponse.fail(permissionFail);
                    }
                    entity.setTdcId(GenerateUUID.getUUID());
                    entity.setTdId(tdId);
                    entity.setOrderNo(nextOrderNo++);
                    toSave.add(entity);
                    tdcIds.add(entity.getTdcId());
                }
            }
        }

        if (!toSave.isEmpty()) {
            saveBatch(toSave);
        }
        if (!toUpdate.isEmpty()) {
            updateBatchById(toUpdate);
        }

        return BaseResponse.ok(tdcIds);
    }




    @Override
    public List<TaskDistributionCheckModel> getTaskDistributionCheckByTdId(String tdId) {
        List<TaskDistributionCheckModel> taskDistributionCheckModels = baseMapper.getTaskDistributionCheckByTdId(tdId);
        if (taskDistributionCheckModels == null || taskDistributionCheckModels.isEmpty()) {
            return taskDistributionCheckModels;
        }

        taskDistributionCheckModels.forEach(item -> {
            String measured = item.getMeasuredFlag();
            String hfHg = item.getHfHgFlag();

            String status = (measured == null || "01".equals(measured.trim()))
                    ? "01"
                    : ("02".equals(measured.trim())
                    ? ("01".equals(hfHg == null ? null : hfHg.trim()) ? "03" : "02")
                    : "01");

            item.setCheckStatus(status);
        });

        return taskDistributionCheckModels;
    }


    @Override
    public BaseResponse deleteByTdId(String tdId) {
        baseMapper.delete(new QueryWrapper<TaskDistributionCheck>().eq("td_id", tdId));
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse backWriteTaskDistributionCheck(TaskDistributionCheckModel request) {
        if (ValidateUtil.isEmpty(request.getTdcId()) || ValidateUtil.isEmpty(request.getCheckItemMeasureData())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        TaskDistributionCheck entity = baseMapper.selectById(request.getTdcId());
        if (entity == null || ValidateUtil.isEmpty(entity.getTdId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
//        TaskDistribution taskDistribution = taskDistributionService.getById(entity.getTdId());

        if (ValidateUtil.isEmpty(entity.getCheckItemExpression()) || ValidateUtil.isEmpty(request.getCheckItemMeasureData())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        Boolean check = validateCheckItemMeasureData(entity.getCheckItemExpression(), request.getCheckItemMeasureData());
        entity.setCheckItemCheckResult(String.valueOf(check));
        entity.setMeasuredFlag("02");
        if (!check) {
            // 已测量是爆点
            entity.setHfHgFlag("01");
        } else if (ValidateUtil.isNotEmpty(entity.getHfHgFlag()) || "01".equals(entity.getHfHgFlag())){
            // 整改后合格 通过
            entity.setHfHgFlag("02");
        }

        entity.setMeasureTime(new Date());
        entity.setCheckItemMeasureData(request.getCheckItemMeasureData());
        baseMapper.updateById(entity);


        return BaseResponse.ok(check);
    }

    /**
     * 验证检查项测量数据是否符合表达式要求
     *
     * @param checkItemExpression 检查项表达式
     * @param checkItemMeasureData 测量数据JSON字符串
     * @return 验证结果，true表示符合要求，false表示不符合要求
     */
    private boolean validateCheckItemMeasureData(String checkItemExpression, String checkItemMeasureData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<NumItem> numList = mapper.readTree(checkItemMeasureData).get("numlist") != null
                    ? mapper.readValue(mapper.readTree(checkItemMeasureData).get("numlist").toString(), new TypeReference<List<NumItem>>() {})
                    : Collections.emptyList();

            for (NumItem item : numList) {
                List<Double> xValues;

                if (item.getNum() == null || item.getNum().trim().isEmpty()) {
                    xValues = Collections.emptyList();
                } else {
                    xValues = Arrays.stream(item.getNum().split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());
                }

                List<Double> aValues = Arrays.stream(
                                item.getDesignNum() == null ? new String[0] : item.getDesignNum().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());

                boolean checkExpression = ExpressionUtils.check(checkItemExpression, xValues, aValues);
                if (!checkExpression) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("验证检查项测量数据时发生异常", e);
            throw new RuntimeException("数据解析异常", e);
        }
    }

    @Override
    public List<TaskDistributionCheck> getTaskDistributionCheckByCiId(String ciId) {
        return lambdaQuery()
                .eq(TaskDistributionCheck::getCiId, ciId)
                .list();
    }

    @Override
    public BaseResponse getTaskDistributionCheckList(TaskDistributionCheckModel request,String uid) {
        QueryMap<TaskDistributionCheckModel> params = new QueryMap<>(request);
        params.put("checkPersonUid").put("checkType").put("measuredFlag").put("checkPersonUname")
                .put("ciName").put("ciCode").put("ciType").put("pId").put("tdId").put("hfHgFlag");
        IPage<TaskDistributionCheckModel> page = new Page<>(request.getPage(), request.getSize());
/*        LambdaQueryWrapper<CheckItem> wrapper = new LambdaQueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId())) {
            wrapper.eq(CheckItem::getTeId, request.getTeId());
        }*/
        List<CheckItemResponse> allItems = checkItemMapper.getCheckItemList(request.getTeId());

        Map<String, CheckItemResponse> checkItemMap = allItems.stream()
                .collect(Collectors.toMap(CheckItemResponse::getCiId, Function.identity(), (existing, replacement) -> existing));
        List<TaskDistributionCheckModel> data = baseMapper.getTaskDistributionCheckList(page, params.getQuery());
        for (TaskDistributionCheckModel model : data) {
            if (ValidateUtil.isEmpty(model.getCiId())) {
                model.setCiName("");
                continue;
            }
            String ciParentName = buildCiParentName(model.getCiId(), checkItemMap);
            model.setCiParentName(ciParentName);
        }
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getTaskDistributionCheckByDmId(TaskDistributionCheckModel request) {
        if (ValidateUtil.isEmpty(request.getDmId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<TaskDistributionResponse> taskDistributionResponses = baseMapper.getTaskDistributionCheckByDmId(request.getDmId());
        if (ValidateUtil.isEmpty(taskDistributionResponses)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<String> tdIds = taskDistributionResponses.stream()
                .map(TaskDistributionResponse::getTdId)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toList());
        log.info("taskDistributionResponses:{}", taskDistributionResponses);
        List<String> diIds = taskDistributionResponses.stream().map(TaskDistributionResponse::getDiId).collect(Collectors.toList());
        log.info("diIds:{}", diIds);

        if (ValidateUtil.isEmpty(tdIds)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        LambdaQueryWrapper<TaskDistributionCheck> queryWrapper = new LambdaQueryWrapper<TaskDistributionCheck>()
                .in(TaskDistributionCheck::getTdId, tdIds);

        if (!ValidateUtil.isEmpty(request.getCheckPersonUid())) {
            queryWrapper.eq(TaskDistributionCheck::getCheckPersonUid, request.getCheckPersonUid());
        }

        List<TaskDistributionCheckModel> taskDistributionCheckModels = list(queryWrapper)
                .stream()
                .map(taskDistributionCheck -> {
                    TaskDistributionCheckModel model = new TaskDistributionCheckModel();
                    BeanUtils.copyProperties(taskDistributionCheck, model);
                    return model;
                })
                .collect(Collectors.toList());
        Map<String, List<TaskDistributionCheckModel>> checksByTdId = taskDistributionCheckModels.stream()
                .collect(Collectors.groupingBy(TaskDistributionCheckModel::getTdId));

        String checkPersonUid = request.getCheckPersonUid();
        TaskDistributionResponse taskDistributionResponse = null;
        for (TaskDistributionResponse response : taskDistributionResponses) {
            List<TaskDistributionCheckModel> userChecks = checksByTdId.getOrDefault(response.getTdId(), Collections.emptyList());

            // 兼容旧可见性和新增授权可见性：
            // 旧逻辑：用户已有检查点，说明用户能看到该图纸任务；
            // 新逻辑：任务主表 taskUserIds 包含用户，即使还没有检查点，也允许看到图纸并动态新增点位。
            // 点位列表仍然只返回 check_person_uid 等于当前用户的记录，避免把其他用户点位暴露出来。
            boolean visible = ValidateUtil.isEmpty(checkPersonUid)
                    || ValidateUtil.isNotEmpty(userChecks)
                    || containsUserId(response.getTaskUserIds(), checkPersonUid);
            if (visible) {
                taskDistributionResponse = response;
                taskDistributionResponse.setTaskDistributionCheckModels(userChecks);
                break;
            }
        }

        if (taskDistributionResponse == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(taskDistributionResponse);
    }

    @Override
    public BaseResponse getTaskDistributionCheckByCreateTimeNew(TaskDistributionCheckModel request) {
        QueryMap<TaskDistributionCheckModel> params = new QueryMap<>(request);
        params.put("pId");
        TaskDistributionResponse taskDistributionResponse = baseMapper.getTaskDistributionCheckByCreateTimeNew(params.getQuery());

        // 检查是否获取到taskDistributionResponse
        if (ValidateUtil.isEmpty(taskDistributionResponse)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }


        List<TaskDistributionCheckModel> taskDistributionCheckByTdId = baseMapper.getTaskDistributionCheckByTdId(taskDistributionResponse.getTdId())
                .stream()
                .filter(taskDistributionCheckModel ->
                        taskDistributionCheckModel != null &&
                                ValidateUtil.isNotEmpty(request.getCheckPersonUid()) &&
                                request.getCheckPersonUid().equals(taskDistributionCheckModel.getCheckPersonUid()))
                .collect(Collectors.toList());

        log.info("查询到的检查记录数量: {}", taskDistributionCheckByTdId.size());


        List<CheckItemResponse> allItems = checkItemMapper.selectAll();
        Map<String, CheckItemResponse> checkItemMap = allItems.stream()
                .collect(Collectors.toMap(CheckItemResponse::getCiId, Function.identity()));

        for (TaskDistributionCheckModel model : taskDistributionCheckByTdId) {
            if (ValidateUtil.isEmpty(model.getCiId())) {
                model.setCiName("");
                continue;
            }
            String ciParentName = buildCiParentName(model.getCiId(), checkItemMap);
            model.setCiParentName(ciParentName);
            
            // 填充动态指标信息
            CheckItemResponse checkItem = checkItemMap.get(model.getCiId());
            if (checkItem != null) {
                log.info("填充动态指标 - ciId: {}, dynamicId: {}, dynamicName: {}, isUseDynamic: {}", 
                    model.getCiId(), checkItem.getDynamicId(), checkItem.getDynamicName(), checkItem.getIsUseDynamic());
                model.setDynamicId(checkItem.getDynamicId());
                model.setDynamicName(checkItem.getDynamicName());
                model.setIsUseDynamic(checkItem.getIsUseDynamic());
            } else {
                log.warn("未找到检查项信息 - ciId: {}", model.getCiId());
            }
        }

        if (ValidateUtil.isNotEmpty(taskDistributionCheckByTdId)) {

            String checkPersonUid = request.getCheckPersonUid();

            List<TaskDistributionCheckModel> taskDistributionChecks;
            if (ValidateUtil.isNotEmpty(checkPersonUid)) {
                taskDistributionChecks = taskDistributionCheckByTdId.stream()
                        .filter(taskDistributionCheckModel ->
                                taskDistributionCheckModel != null &&
                                        checkPersonUid.equals(taskDistributionCheckModel.getCheckPersonUid()))
                        .collect(Collectors.toList());
            } else {
                taskDistributionChecks = taskDistributionCheckByTdId;
            }
            taskDistributionResponse.setTaskDistributionCheckModels(taskDistributionChecks);
            log.info("最终返回的检查记录数量: {}, 第一条记录的dynamicId: {}", 
                taskDistributionChecks.size(), 
                taskDistributionChecks.isEmpty() ? "无" : taskDistributionChecks.get(0).getDynamicId());
        } else {
            taskDistributionResponse.setTaskDistributionCheckModels(Collections.emptyList());
        }

        return BaseResponse.ok(taskDistributionResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse generateQualityCheckReport(QsRecordRequest request,String token) {
        if (ValidateUtil.isEmpty(request.getTdcId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        TaskDistributionCheck taskDistributionCheck = getById(request.getTdcId());
        if (taskDistributionCheck == null || ValidateUtil.isEmpty(taskDistributionCheck.getTdId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        TaskDistribution taskDistribution = taskDistributionService.getById(taskDistributionCheck.getTdId());
        if (taskDistribution == null || ValidateUtil.isEmpty(taskDistribution.getPId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // todo 要传的参数为 要求整改时间 整改责任人id 创建人，创建时间;
        QsRecord qsRecord = new QsRecord();
        BeanUtils.copyProperties(request, qsRecord);
        if (ValidateUtil.isEmpty(qsRecord.getQstId())) {
            String qsrId = GenerateUUID.getUUID();
            qsRecord.setQsrId(qsrId);
            taskDistributionCheck.setQsrId(qsrId);
            qsRecord.setPId(taskDistribution.getPId());
            qsRecord.setQsrType("03");
            qsRecord.setQsrTime(new Date());
            qsRecord.setQsrRectifyStatus("02");
            qsRecord.setQsrCreateTime(new Date());
            qsRecord.setQsrCategory(0);
            if (ValidateUtil.isNotNull(request.getQsrState())) {
                qsRecord.setQsrState(request.getQsrState());
            } else {
                qsRecord.setQsrState(0);
            }
            qsRecordMapper.insert(qsRecord);
            updateById(taskDistributionCheck);
            String fileRelationId = GenerateUUID.getUUID();
            sysFileService.saveFileTeId(qsRecord.getQsrId(), request.gettId(), qsRecord.getQsrState() + "", qsRecord.getQsrCreateUid(), qsRecord.getQsrCreateUname()
                    , qsRecord.getQsrCreateTime(), request.getpId(),"质量检查", request.getFileCode(), "create",
                    fileRelationId, null, "QualityRecord_GX", qsRecord.getConId(),redisUtils.getTeId(token));
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getQsrPic(), request.getQsrRectifyPic(), fileRelationId), qsRecord.getQsrId());
        } else {
            QsRecord model = qsRecordMapper.selectById(qsRecord.getQsrId());
            final String fileRelationId = sysFileService.getById(qsRecord.getQsrId()).getFileRelationId();
            if (model == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            qsRecord.setQsrCreateTime(model.getQsrCreateTime());
            qsRecord.setQsrCreateUid(model.getQsrCreateUid());
            qsRecord.setQsrCreateUname(model.getQsrCreateUname());

            qsRecordMapper.updateById(qsRecord);
            sysFileService.saveFileTeId(qsRecord.getQsrId(), request.gettId(), qsRecord.getQsrState() + "", qsRecord.getQsrCreateUid(), qsRecord.getQsrCreateUname()
                    , qsRecord.getQsrCreateTime(), request.getpId(), "质量检查" ,request.getFileCode(), "update",
                    fileRelationId, null, "QualityRecord_GX", qsRecord.getConId(),redisUtils.getTeId(token));
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getQsrPic(), request.getQsrRectifyPic(), fileRelationId),
                    Arrays.asList(request.getQsrPicIds(), request.getQsrRectifyPicIds(), request.getFileIds()), qsRecord.getQsrId());
        }
        return BaseResponse.ok(qsRecord.getQsrId());
    }


    private String buildCiParentName(String ciId, Map<String, CheckItemResponse> checkItemMap) {
        List<String> pathElements = new ArrayList<>();
        String currentCiId = ciId;

        while (currentCiId != null && !"0".equals(currentCiId)) {
            CheckItemResponse checkItem = checkItemMap.get(currentCiId);
            if (checkItem != null) {
                pathElements.add(0, checkItem.getCiName());
                currentCiId = checkItem.getCiParent();
            } else {
                break;
            }
        }

        return String.join("-", pathElements);
    }

    // 判断用户是否在任务主表授权集合内。
    // 这里只做逗号拆分后的精确匹配，避免字符串 contains 导致短 ID 误命中长 ID。
    private boolean containsUserId(String taskUserIds, String uid) {
        if (ValidateUtil.isEmpty(taskUserIds) || ValidateUtil.isEmpty(uid)) {
            return false;
        }
        return Arrays.stream(taskUserIds.split(","))
                .map(String::trim)
                .anyMatch(uid::equals);
    }

    private String checkCreatePermissionByTaskUsers(String tdId, String checkPersonUid) {
        TaskDistribution taskDistribution = taskDistributionService.getById(tdId);
        if (taskDistribution == null) {
            return ResMsg.infoNotExist;
        }

        // 只在新授权模型启用时校验：taskUserIds 为空说明是历史任务或后台仍按旧分配模式维护，
        // 继续兼容旧逻辑；一旦任务主表配置了授权用户集合，新增点位的检查人必须在集合内，
        // 避免用户仅凭 tdId 给未授权图纸动态新增检查点。
        if (ValidateUtil.isEmpty(taskDistribution.getTaskUserIds())) {
            return null;
        }
        if (containsUserId(taskDistribution.getTaskUserIds(), checkPersonUid)) {
            return null;
        }
        return "当前检查人未被授权操作该图纸任务";
    }

    @Setter
    @Getter
    public static class NumItem {
        private String num;
        private String designNum;

    }

}
