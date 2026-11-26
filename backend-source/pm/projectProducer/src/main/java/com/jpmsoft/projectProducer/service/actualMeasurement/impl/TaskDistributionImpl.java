package com.jpmsoft.projectProducer.service.actualMeasurement.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.ActualMeasurement.TaskDistributionRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.CheckItemResponse;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.CheckItemMapper;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.DrawingManagementCatalogueMapper;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.TaskDistributionMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingManagementCatalogue;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistribution;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistributionCheck;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionCheckService;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
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
public class TaskDistributionImpl extends ServiceImpl<TaskDistributionMapper, TaskDistribution> implements ITaskDistributionService {

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ITaskDistributionCheckService taskDistributionCheckService;

    @Resource
    private CheckItemMapper checkItemMapper;

    @Resource
    private DrawingManagementCatalogueMapper drawingManagementCatalogueMapper;

    @Resource
    private PreProjectMapper preProjectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateTaskDistributionAndCheck(TaskDistributionRequest request) {
        TaskDistribution taskDistribution = new TaskDistribution();
        BeanUtils.copyProperties(request, taskDistribution);
        if (ValidateUtil.isEmpty(request.getTdId())) {
            taskDistribution.setTdId(GenerateUUID.getUUID());
            taskDistribution.setCreateTime(new Date());
            taskDistribution.setState(ApprovalStatusEnum.Not_Started.getIndex());
            taskDistribution.setStateTime(new Date());
            // todo diId 改为 dmId
            taskDistribution.setDmId(request.getDmId());
            baseMapper.insert(taskDistribution);
            sysFileService.saveFile(taskDistribution.getTdId(), request.gettId(), taskDistribution.getState() + "", request.getCreateUid(), taskDistribution.getCreateUname()
                    , taskDistribution.getCreateTime(), request.getpId(), request.getFileTitle(), request.gettCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Collections.singletonList(request.getFileRelationId()), taskDistribution.getTdId());
        } else {
            TaskDistribution entity = getById(request.getTdId());
            if (entity == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            taskDistribution.setCreateTime(entity.getCreateTime());
            taskDistribution.setCreateUname(entity.getCreateUname());
            taskDistribution.setCreateUid(entity.getCreateUid());
            baseMapper.updateById(taskDistribution);
            sysFileService.saveFile(taskDistribution.getTdId(), request.gettId(), taskDistribution.getState() + "", taskDistribution.getCreateUid(), taskDistribution.getCreateUname(),
                    taskDistribution.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Collections.singletonList(request.getFileRelationId())
                    , Collections.singletonList(request.getFileIds()), entity.getTdId());
        }
        taskDistributionCheckService.createOrUpdateTaskDistributionCheck(request.getTaskDistributionCheckModels(), taskDistribution.getTdId());
        return BaseResponse.ok(taskDistribution.getTdId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse batchCreateTaskDistributionByDmIds(TaskDistributionRequest request) {
        if (ValidateUtil.isEmpty(request) || ValidateUtil.isEmpty(request.getpId())
                || ValidateUtil.isEmpty(request.getDmIds()) || ValidateUtil.isEmpty(request.getTaskUserIds())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        String normalizedTaskUserIds = normalizeUserIds(request.getTaskUserIds());
        if (ValidateUtil.isEmpty(normalizedTaskUserIds)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        List<String> dmIds = normalizeDmIds(request.getDmIds());
        if (ValidateUtil.isEmpty(dmIds)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        List<String> queryDmIds = dmIds.stream().distinct().collect(Collectors.toList());
        Map<String, DrawingManagementCatalogue> catalogueMap = drawingManagementCatalogueMapper.selectBatchIds(queryDmIds)
                .stream()
                .collect(Collectors.toMap(DrawingManagementCatalogue::getDmId, Function.identity(), (left, right) -> left));
        for (String dmId : queryDmIds) {
            if (!catalogueMap.containsKey(dmId)) {
                return BaseResponse.fail("图纸目录不存在：" + dmId);
            }
        }

        PreProject project = preProjectMapper.selectById(request.getpId());
        if (project == null || ValidateUtil.isEmpty(project.getpName())) {
            return BaseResponse.fail("项目不存在或项目名称为空：" + request.getpId());
        }
        String projectName = project.getpName();

        List<String> tdIds = new ArrayList<>();
        List<TaskDistribution> toSave = new ArrayList<>();
        Date now = new Date();

        for (String dmId : dmIds) {
            DrawingManagementCatalogue catalogue = catalogueMap.get(dmId);
            if (ValidateUtil.isEmpty(catalogue.getDmRegionName())) {
                return BaseResponse.fail("图纸目录区域名称为空：" + dmId);
            }
            if (ValidateUtil.isEmpty(catalogue.getDmCode())) {
                return BaseResponse.fail("图纸目录区域编码为空：" + dmId);
            }
            String taskName = projectName+catalogue.getDmRegionName()+"测量任务";

            TaskDistribution taskDistribution = new TaskDistribution();
            taskDistribution.setTdId(GenerateUUID.getUUID());
            taskDistribution.setPId(request.getpId());
            taskDistribution.setDmId(dmId);
            // 批量创建任务的名称和编码必须由后端统一生成，不能再用前端传入值覆盖。
            // taskName = 项目名称-测量区域名称-测量任务；taskCode = 项目名称-测量区域名称-区域编码-taskName。
            // 这样同一批授权任务在列表、流程文件和后续排查时都有稳定、可读的命名规则。
            taskDistribution.setTaskName(taskName);
            taskDistribution.setTaskCode(buildBatchTaskCode(projectName, catalogue.getDmRegionName(), catalogue.getDmCode(), taskName));
            taskDistribution.setTaskUserIds(normalizedTaskUserIds);
            taskDistribution.setTaskUserNames(request.getTaskUserNames());
            taskDistribution.setCreateUid(request.getCreateUid());
            taskDistribution.setCreateUname(request.getCreateUname());
            taskDistribution.setCreateTime(now);
            taskDistribution.setStateTime(now);
            taskDistribution.setTeId(request.getTeId());
            // 现有 App/图纸查询普遍只查 state=7 的任务。批量生成的是“图纸授权载体任务”，
            // 不预生成检查点，也不走原表单流程；默认置为已完成以兼容现有查询，不扩大旧接口状态范围。
            taskDistribution.setState(ValidateUtil.isNotNull(request.getState())
                    ? request.getState() : ApprovalStatusEnum.Already_Over.getIndex());
            toSave.add(taskDistribution);
            tdIds.add(taskDistribution.getTdId());
        }

        if (ValidateUtil.isNotEmpty(toSave)) {
            saveBatch(toSave);
        }
        for (TaskDistribution taskDistribution : toSave) {
            // sysFileService/saveFile 和附件刷新当前只有按单据ID处理的接口，不能和任务主表一样直接批量落库。
            // 所以任务主表批量保存后，再逐条补齐流程文件和表单附件关系，保证批量生成的任务与普通新建任务一致。
            sysFileService.saveFile(taskDistribution.getTdId(), request.gettId(), taskDistribution.getState() + "", request.getCreateUid(), request.getCreateUname()
                    , taskDistribution.getCreateTime(), request.getpId(), request.getFileTitle(), request.gettCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Collections.singletonList(request.getFileRelationId()), taskDistribution.getTdId());
        }

        return BaseResponse.ok(tdIds);
    }

    @Override
    public BaseResponse createOrUpdateTaskDistributionCheckNew(List<TaskDistributionCheckModel> request) {
        return taskDistributionCheckService.createOrUpdateTaskDistributionCheckNew(request);
    }

    @Override
    public BaseResponse getTaskDistributionByTdId(String tdId) {
        TaskDistributionResponse taskDistributionResponses = baseMapper.getTaskDistributionByTdId(tdId);
        List<TaskDistributionCheckModel> taskDistributionCheckModels =taskDistributionCheckService.getTaskDistributionCheckByTdId(tdId);
        taskDistributionResponses.setTaskDistributionCheckModels(taskDistributionCheckModels);
        return BaseResponse.ok(taskDistributionResponses);
    }


    private static final String MEASURED_NO = "01";   // 未测量
    private static final String MEASURED_YES = "02";  // 已测量
    private static final String HFHG_PROBLEM = "01";  // 爆点 -> 整改

    @Override
    public BaseResponse getTaskDistributionList(TaskDistributionRequest request, String uid) {
        QueryMap<TaskDistributionRequest> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("taskName").put("taskCode").put("state");

        IPage<TaskDistributionResponse> page = new Page<>(request.getPage(), request.getSize());
        List<TaskDistributionResponse> datas = baseMapper.getTaskDistributionList(page, params.getQuery());

        if (datas == null || datas.isEmpty()) {
            return BaseResponse.ok(datas, (int) page.getTotal());
        }

        List<String> tdIds = datas.stream()
                .map(TaskDistributionResponse::getTdId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (tdIds.isEmpty()) {
            // 若没有 tdId，全部置 0
            datas.forEach(d -> {
                d.setCheckItemCount(0);
                d.setCheckItemCheckedCount(0);
                d.setCheckItemUnCheckedCount(0);
                d.setCheckItemRectificationCount(0);
            });
            return BaseResponse.ok(datas, (int) page.getTotal());
        }

        List<TaskDistributionCheck> checks = taskDistributionCheckService
                .list(Wrappers.<TaskDistributionCheck>lambdaQuery().in(TaskDistributionCheck::getTdId, tdIds));

        Map<String, List<TaskDistributionCheck>> checksByTdId = checks == null
                ? Collections.emptyMap()
                : checks.stream().collect(Collectors.groupingBy(TaskDistributionCheck::getTdId));

        // 对每个任务分配项逐条遍历其检查项，按规则分类计数
        for (TaskDistributionResponse resp : datas) {

            List<TaskDistributionCheck> list = checksByTdId.getOrDefault(resp.getTdId(), Collections.emptyList());

            int total = list.size();
            int checked = 0;       // 已检测项（不包括整改项）
            int rectification = 0; // 整改项（爆点）
            int unchecked = 0;     // 未检测项

            for (TaskDistributionCheck c : list) {
                String measured = c.getMeasuredFlag() == null ? null : c.getMeasuredFlag().trim();
                String hfHg = c.getHfHgFlag() == null ? null : c.getHfHgFlag().trim();

                if (measured == null || MEASURED_NO.equals(measured)) {
                    // 未测量 -> 未检查
                    unchecked++;
                } else if (MEASURED_YES.equals(measured)) {
                    // 已测量，区分是否为爆点（需要整改）或为已完成
                    if (HFHG_PROBLEM.equals(hfHg)) {
                        rectification++;
                    } else {
                        // hfHg == "02" 或 null 都算已完成检测
                        checked++;
                    }
                } else {
                    // 其他异常值：按未检查处理
                    unchecked++;
                }
            }

            // 保险：保证 total = checked + rectification + unchecked
            // 若存在差异则以 total 为准，优先保证非负
            int sum = checked + rectification + unchecked;
            if (sum != total) {
                // 调整 unchecked 以保证和为 total（通常不会触发）
                unchecked = Math.max(0, total - checked - rectification);
            }

            resp.setCheckItemCount(total);
            resp.setCheckItemCheckedCount(checked);
            resp.setCheckItemUnCheckedCount(unchecked);
            resp.setCheckItemRectificationCount(rectification);
        }

        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse deleteTaskDistributionByTdId(String tdId) {
        List<TaskDistributionCheckModel> taskDistributionCheckByTdId = taskDistributionCheckService.getTaskDistributionCheckByTdId(tdId);
        if (taskDistributionCheckByTdId != null && !taskDistributionCheckByTdId.isEmpty()) {
            return BaseResponse.fail(ResMsg.delAssociated);
        }
        baseMapper.deleteById(tdId);
        return BaseResponse.ok(tdId);
    }

    @Override
    public BaseResponse deleteTaskDistributionCheckByTdId(String tdId) {
        return taskDistributionCheckService.deleteByTdId(tdId);
    }

    @Override
    public List<TaskDistributionCheckModel> getTaskDistributionCheckByDiId(String diId) {
        return baseMapper.getTaskDistributionCheckByDiId(diId);
    }

    @Override
    public BaseResponse getTaskDistributionByDiId(String diId) {

        if (ValidateUtil.isEmpty(diId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        TaskDistributionResponse taskDistributionResponse = baseMapper.getTaskDistributionByDiId(diId);

        taskDistributionResponse.setTaskDistributionCheckModels(getTaskDistributionCheckByDiId(diId));

        return BaseResponse.ok(taskDistributionResponse);
    }



    @Override
    public BaseResponse getTaskDistributionByDmId(String dmId) {

        if (ValidateUtil.isEmpty(dmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        TaskDistributionResponse taskDistributionResponse = baseMapper.getTaskDistributionByDmId(dmId);

        if (taskDistributionResponse == null) {
            DrawingManagementCatalogue drawingManagementCatalogue = drawingManagementCatalogueMapper.selectById(dmId);
            if (drawingManagementCatalogue == null) {
                return BaseResponse.fail("当前区域没有图纸");
            }
            taskDistributionResponse = new TaskDistributionResponse();
            taskDistributionResponse.setDiDrawing(drawingManagementCatalogue.getDmDrawing());
            taskDistributionResponse.setDiRegionCode(drawingManagementCatalogue.getDmCode());
            taskDistributionResponse.setDiRegionName(drawingManagementCatalogue.getDmRegionName());
            return BaseResponse.ok(taskDistributionResponse);
        }
        taskDistributionResponse.setTaskDistributionCheckModels(getTaskDistributionCheckByDmId(dmId));

        return BaseResponse.ok(taskDistributionResponse);
    }


    @Override
    public List<TaskDistributionCheckModel> getTaskDistributionCheckByDmId(String dmId) {
        List<TaskDistributionCheckModel> checkModels = baseMapper.getTaskDistributionCheckByDmId(dmId);

        List<CheckItemResponse> allItems = checkItemMapper.selectAll();
        Map<String, CheckItemResponse> checkItemMap = allItems.stream()
                .collect(Collectors.toMap(CheckItemResponse::getCiId, Function.identity()));

        for (TaskDistributionCheckModel model : checkModels) {
            if (ValidateUtil.isEmpty(model.getCiId())) {
                model.setCiName("");
                continue;
            }
            String ciParentName = buildCiParentName(model.getCiId(), checkItemMap);
            model.setCiParentName(ciParentName);
            CheckItemResponse checkItem = checkItemMap.get(model.getCiId());
            model.setDynamicId(checkItem == null ? null : checkItem.getDynamicId());
            model.setDynamicName(checkItem == null ? null : checkItem.getDynamicName());
            model.setIsUseDynamic(checkItem == null ? null : checkItem.getIsUseDynamic());
        }

        return checkModels;
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

    private String normalizeUserIds(String taskUserIds) {
        if (ValidateUtil.isEmpty(taskUserIds)) {
            return "";
        }
        LinkedHashSet<String> userIds = Arrays.stream(taskUserIds.split(","))
                .map(String::trim)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return String.join(",", userIds);
    }

    private List<String> normalizeDmIds(List<String> dmIds) {
        if (ValidateUtil.isEmpty(dmIds)) {
            return Collections.emptyList();
        }
        return dmIds.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toList());
    }



    private String buildBatchTaskCode(String projectName, String regionName, String regionCode, String taskName) {
        return joinTaskNameParts(projectName, regionName, regionCode, taskName);
    }

    private String joinTaskNameParts(String... parts) {
        return Arrays.stream(parts)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.joining("-"));
    }



}
