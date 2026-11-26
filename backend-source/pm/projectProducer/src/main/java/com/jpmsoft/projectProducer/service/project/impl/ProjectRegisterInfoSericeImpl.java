package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterInfoScoreModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoUnitModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.evaluate.ZNPJEvaluateObjectContractMapper;
import com.jpmsoft.projectProducer.mapper.other.SysUserMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectSolutionDetailsMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectSolutionMapper;
import com.jpmsoft.projectProducer.mapper.project.ProProjectRegisterMapper;
import com.jpmsoft.projectProducer.mapper.project.ProjectRegisterInfoMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.evaluate.ZNPJEvaluateObjectContract;
import com.jpmsoft.projectProducer.pojo.entity.project.*;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.*;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectRegisterInfoSericeImpl extends ServiceImpl<ProjectRegisterInfoMapper, ProjectRegisterInfo> implements IProjectRegisterInfoSerice {
    @Autowired
    private IPreProjectService preProjectService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PreProjectSolutionMapper preProjectSolutionMapper;
    @Autowired
    private ZNPJEvaluateObjectContractMapper znpjEvaluateObjectContractMapper;
    @Autowired
    private PreProjectSolutionDetailsMapper preProjectSolutionDetailsMapper;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IProjectRegisterInfoUnitService projectRegisterInfoUnitService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private PreProjectRegisterInfoScoreService preProjectRegisterInfoScoreService;

    @Resource
    private ProProjectRegisterMapper proProjectRegisterMapper;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ProjectRegisterDataPermission dataPermission;

    /**
     * 校验当前用户与创建人、关联登记的单位权限后，检查发起限制并保存申报或论证。
     * 更新时未传论证目的则沿用原值，支持仅提交论证结果和意见。
     *
     * @param request 登记信息，registerType 可选，仅支持 01、02
     * @return 保存结果及登记信息主键
     */
    @Override
    public BaseResponse createOrUpdatePreProjectRegisterInfo(ProjectRegisterInfoModel request) {
        if (!normalizeRegisterType(request)) {
            return BaseResponse.fail("登记业务类型仅支持01（项目申报）或02（项目论证）");
        }
        String validationMessage = validateInitiation(request, true);
        if (validationMessage != null) {
            return BaseResponse.fail(validationMessage);
        }
        // 新增以真实登录用户为创建人；编辑时保持数据库中的归属，禁止客户端篡改。
        LogUserInfoVo user = dataPermission.currentUser();
        if (StrUtil.isBlank(request.getId())) {
            request.setCreateUid(user.getId());
            request.setCreateUname(user.getName());
        } else {
            ProjectRegisterInfo original = baseMapper.selectById(request.getId());
            request.setCreateUid(original.getCreateUid());
            request.setCreateUname(original.getCreateUname());
            request.setCreateTime(original.getCreateTime());
            request.setVersionId(original.getVersionId());
        }
        log.info("createOrUpdatePreProjectRegisterInfo:{}", JacksonUtils.toJSONString(request));
        String primaryId = request.getId();
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto = new SupportDocumentDto();
        supportDocumentDto.setSupportDocument(request.getProjectRegisterInfoFileId());
        supportDocumentDto.setSupportDocumentFileIDs(request.getProjectRegisterInfoFileIds());
        documentDtos.add(supportDocumentDto);
        ProjectRegisterInfo entity;
        if (StrUtil.isEmpty(primaryId)) {
            primaryId = IdWorker.getGenerateID();
            entity = new ProjectRegisterInfo();
            BeanUtil.copyProperties(request, entity);
            entity.setVersionId(GenerateUUID.getUUID());
            entity.setLastest(1);
            if (StrUtil.isNotEmpty(request.getEvaluateObjectContractId())) {
                ZNPJEvaluateObjectContract znpjEvaluateObjectContract = znpjEvaluateObjectContractMapper.selectById(request.getEvaluateObjectContractId());
                entity.setEvaluateObjectContractVersionId(znpjEvaluateObjectContract.getVersionId());
            }
            entity.setId(primaryId);
            entity.setCreateTime(new Date());
            saveFile(entity, documentDtos);
            List<PreProjectRegisterInfoScoreModel> scoreModelList = request.getScoreList();
            if (scoreModelList != null && scoreModelList.size() > 0) {
                List<PreProjectRegisterInfoScore> scoreList = new ArrayList<>();
                scoreModelList.forEach(scoreModel -> {
                    PreProjectRegisterInfoScore preProjectRegisterInfoScore = new PreProjectRegisterInfoScore();
                    BeanUtil.copyProperties(scoreModel, preProjectRegisterInfoScore);
                    preProjectRegisterInfoScore.setId(GenerateUUID.getUUID());
                    preProjectRegisterInfoScore.setPId(entity.getId());
                    scoreList.add(preProjectRegisterInfoScore);
                });
                preProjectRegisterInfoScoreService.saveBatch(scoreList);
            }

            sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(),
                    entity.getCreateUname(), entity.getCreateTime(), request.getProjectId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), request.getSecId(), request.getTCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getApprovalFileId(),
                    request.getLandContractFileId(), request.getPlanningPermitFileId(), request.getDrawingReviewFileId(), request.getConstructionLicenseFileId(),request.getOtherFileId()), entity.getId());
            baseMapper.insert(entity);
        } else {
            entity = baseMapper.selectById(primaryId);
            //删除原来的分数记录信息
            LambdaQueryWrapper<PreProjectRegisterInfoScore> scoreWrapper = new LambdaQueryWrapper<>();
            scoreWrapper.eq(PreProjectRegisterInfoScore::getPId, entity.getId());
            List<PreProjectRegisterInfoScoreModel> scoreModelList = request.getScoreList();
            preProjectRegisterInfoScoreService.remove(scoreWrapper);
            if (scoreModelList != null && scoreModelList.size() > 0) {
                List<PreProjectRegisterInfoScore> scoreList = new ArrayList<>();
                scoreModelList.forEach(scoreModel -> {
                    PreProjectRegisterInfoScore preProjectRegisterInfoScore = new PreProjectRegisterInfoScore();
                    BeanUtil.copyProperties(scoreModel, preProjectRegisterInfoScore);
                    preProjectRegisterInfoScore.setId(GenerateUUID.getUUID());
                    preProjectRegisterInfoScore.setPId(entity.getId());
                    scoreList.add(preProjectRegisterInfoScore);
                });
                preProjectRegisterInfoScoreService.saveBatch(scoreList);
            }
            BeanUtil.copyProperties(request, entity);
            if (StrUtil.isNotEmpty(request.getEvaluateObjectContractId())) {
                ZNPJEvaluateObjectContract znpjEvaluateObjectContract = znpjEvaluateObjectContractMapper.selectById(request.getEvaluateObjectContractId());
                entity.setEvaluateObjectContractVersionId(znpjEvaluateObjectContract.getVersionId());
            }
            saveFile(entity, documentDtos);
            if (entity.getState() != null && entity.getState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                //项目申报流程结束，同步生成项目表数据
                entity.setProjectId(createPreProject(entity));
            }
            //判断是否有file信息，如果没有就新建
            String type = "update";
            if (sysFileService.getById(entity.getId()) == null) {
                type = "create";
            }
            sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(),
                    entity.getCreateUname(), entity.getCreateTime(), entity.getProjectId(), request.getFileTitle(), request.getFileCode(),
                    type, request.getFileRelationId(), request.getSecId(), request.getTCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getApprovalFileId(),
                            request.getLandContractFileId(), request.getPlanningPermitFileId(), request.getDrawingReviewFileId(), request.getConstructionLicenseFileId(),request.getOtherFileId()),
                    Arrays.asList(request.getFileIds(), request.getApprovalFileIds(), request.getLandContractFileIds(), request.getPlanningPermitFileIds(), request.getDrawingReviewFileIds(), request.getConstructionLicenseFileIds(),request.getOtherFileIds()), entity.getId());
            baseMapper.updateById(entity);
        }
        projectRegisterInfoUnitService.saveOrUpdateProjectRegisterInfoUnit(request.getUnitList(), primaryId);
        return BaseResponse.ok(primaryId);
    }

    /**
     * 根据同一项目登记关联的申报、论证记录查找项目并同步数据，项目不存在时新建。
     *
     * @param entity 当前申报或论证记录
     * @return 同步后的项目 id
     */
    private String createPreProject(ProjectRegisterInfo entity) {
        PreProject preProject = getByPrId(entity);
        if (ValidateUtil.isNull(preProject)) {
            preProject = new PreProject();
            preProject.setpId(IdWorker.getGenerateID());
        }
        generateProject(entity, preProject);
        preProjectService.saveOrUpdate(preProject);
        return preProject.getpId();
    }

    /**
     * 按当前记录关联的项目登记查找项目：单条关联记录直接使用其项目 id，
     * 多条关联记录使用审批时间最新记录的项目 id；无审批时间的记录排在有审批时间的记录之后。
     * 未关联项目登记的历史记录使用自身的项目 id。
     *
     * @param entity 当前申报或论证记录
     * @return 关联项目，关联记录、项目 id 或项目不存在时返回 null
     */
    private PreProject getByPrId(ProjectRegisterInfo entity) {
        String projectId = entity.getProjectId();
        if (StrUtil.isNotBlank(entity.getProProjectRegisterId())) {
            List<ProjectRegisterInfo> records = baseMapper.selectList(Wrappers.<ProjectRegisterInfo>lambdaQuery()
                    .eq(ProjectRegisterInfo::getProProjectRegisterId, entity.getProProjectRegisterId()));
            ProjectRegisterInfo latest = records.stream()
                    .max(Comparator.comparing(ProjectRegisterInfo::getStateTime,
                            Comparator.nullsFirst(Comparator.naturalOrder())))
                    .orElse(null);
            projectId = latest == null ? null : latest.getProjectId();
        }
        return StrUtil.isBlank(projectId) ? null : preProjectService.getById(projectId);
    }

    /**
     * 同步项目申报信息到项目表 Pre_Project
     *
     * @return 项目 id
     */
    private String syncPreProject(ProjectRegisterInfo entity) {
        String projectId = createPreProject(entity);
        entity.setProjectId(projectId);
        baseMapper.updateById(entity);
        log.info("项目申报同步项目表完成, 申报id:{}, 项目id:{}", entity.getId(), projectId);
        return projectId;
    }

    /**
     * 校验申报创建人单位后同步已结束的项目申报，重复调用返回已有项目ID。
     *
     * @param id 项目申报ID
     * @return 同步后的项目ID或失败原因
     */
    @Override
    public BaseResponse syncPreProject(String id) {
        log.info("syncPreProject:{}", id);
        if (StrUtil.isEmpty(id)) {
            return BaseResponse.fail("缺失项目申报id");
        }
        ProjectRegisterInfo entity = baseMapper.selectById(id);
        if (entity == null) {
            return BaseResponse.fail("项目申报信息不存在");
        }
        // dataPermission.checkCreator(entity.getCreateUid(), dataPermission.currentUser());
        if (!ApprovalStatusEnum.Already_Over.getIndex().equals(entity.getState())) {
            return BaseResponse.fail("项目申报流程未结束，不能同步项目");
        }
        //已同步过直接返回项目id，保证重复调用幂等
        if (StrUtil.isNotEmpty(entity.getProjectId())) {
            return BaseResponse.ok(entity.getProjectId());
        }
        return BaseResponse.ok(syncPreProject(entity));
    }

    @Override
    public BaseResponse syncPreProjectCompensate() {
        List<ProjectRegisterInfo> list = baseMapper.selectUnSyncPreProjectRegisterInfoList(ApprovalStatusEnum.Already_Over.getIndex());
        if (ValidateUtil.isEmpty(list)) {
            log.info("项目申报同步项目表补偿：暂无需要同步的数据");
            return BaseResponse.ok("暂无需要同步的数据");
        }
        log.info("项目申报同步项目表补偿数据量:{}", list.size());
        int successCount = 0;
        int failCount = 0;
        for (ProjectRegisterInfo entity : list) {
            try {
                syncPreProject(entity);
                successCount++;
            } catch (Exception e) {
                failCount++;
                log.error("项目申报同步项目表补偿失败, 申报id:{}", entity.getId(), e);
            }
        }
        log.info("项目申报同步项目表补偿完成, 成功:{}, 失败:{}", successCount, failCount);
        return BaseResponse.ok("同步完成，成功 " + successCount + " 条，失败 " + failCount + " 条");
    }

    private void generateProject(ProjectRegisterInfo entity, PreProject preProject) {
        preProject.setpName(entity.getName());
        preProject.setPrId(entity.getId());
        preProject.setpAddress(entity.getAddress());
        preProject.setpCode(entity.getProjectCode());
        //项目类型取申报的建设类型
        preProject.setpType(entity.getBuildingType());
        preProject.setpPlanStartDate(entity.getStartDate());
        preProject.setpPlanCompleteDate(entity.getEndDate());
        preProject.setpOverview(entity.getDescription());
        preProject.setDeleted(false);
        preProject.setpStatus("1");
        preProject.setpParentId("0");
        preProject.setState(ApprovalStatusEnum.Already_Over.getIndex());
        preProject.setpCreateUserId(entity.getCreateUid());
        preProject.setpCreateUserName(entity.getCreateUname());
        preProject.setpCreateTime(entity.getCreateTime());
    }

    private void saveFile(ProjectRegisterInfo entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}", JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto : documentDtos) {
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()), Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
        }
    }

    /**
     * 校验创建人单位后删除申报或论证及其关联数据。
     *
     * @param request 含待删除记录ID的参数
     * @return 删除结果
     */
    @Override
    public BaseResponse deletePreProjectRegisterInfo(ProjectRegisterInfoModel request) {
        log.info("deletePreProjectRegisterInfo");
        if (request == null || StrUtil.isBlank(request.getId())) {
            return BaseResponse.fail("缺失申报或论证ID");
        }
        ProjectRegisterInfo entity = baseMapper.selectById(request.getId());
        if (entity == null) {
            return BaseResponse.fail("申报或论证记录不存在");
        }
        dataPermission.checkCreator(entity.getCreateUid(), dataPermission.currentUser());
        PreProjectSolution preProjectSolution = preProjectSolutionMapper.selectById(entity.getSolutionId());
        List<PreProjectSolutionDetails> preProjectSolutionDetailsList
                = preProjectSolutionDetailsMapper.selectBatchId(preProjectSolution.getId());
        if (preProjectSolutionDetailsList != null && !preProjectSolutionDetailsList.isEmpty()) {
            preProjectSolutionDetailsMapper.deleteBatchIds(preProjectSolutionDetailsList);
        }
        baseMapper.deleteById(request.getId());
        preProjectSolutionMapper.deleteById(preProjectSolution.getId());
        projectRegisterInfoUnitService.remove(Wrappers.<ProjectRegisterInfoUnit>lambdaQuery().eq(ProjectRegisterInfoUnit::getPriId, request.getId()));
        return BaseResponse.ok();
    }


    /**
     * 按当前登录用户单位分页查询最新申报或论证，类型为空时默认项目申报（01）。
     *
     * @param request 分页与筛选条件，registerType 可选，仅支持 01、02
     * @param uid     当前用户主键
     * @return 当前用户可见的登记信息及总数
     */
    @Override
    public BaseResponse getPreProjectRegisterInfoList(ProjectRegisterInfoModel request, String uid) {
        if (!normalizeRegisterType(request)) {
            return BaseResponse.fail("登记业务类型仅支持01（项目申报）或02（项目论证）");
        }
        LogUserInfoVo userInfoVo = dataPermission.currentUser();
        QueryMap<ProjectRegisterInfoModel> queryMap = new QueryMap<>(request);
        queryMap.put("projectCode").put("name").put("buildingType").put("declareUnit")
                .put("jointDeclareUnit").put("designUnit").put("constructionUnit").put("supervisionUnit")
                .put("serviceUnit").put("isIntegratedPlatform").put("declareYear").put("declareType").put("declarePurpose")
                .put("scoreState").put("state").put("address").put("registerType").put("proProjectRegisterId");
        dataPermission.applyScope(queryMap.getQuery(), userInfoVo);
        IPage<ProjectRegisterInfoModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectRegisterInfoModel> list = baseMapper.getPreProjectRegisterInfoList(queryMap.getQuery(), page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    /**
     * 校验创建人单位后查询申报或论证详情及子表。
     *
     * @param id 申报或论证ID
     * @return 当前用户可访问的详情
     */
    @Override
    public BaseResponse getPreProjectRegisterInfoById(String id) {
        ProjectRegisterInfoModel entity = baseMapper.getPreProjectRegisterInfoById(id);
        if (entity == null) {
            return BaseResponse.fail("项目不存在");
        }
        dataPermission.checkCreator(entity.getCreateUid(), dataPermission.currentUser());
        List<ProjectRegisterInfoUnit> infoUnits = projectRegisterInfoUnitService.list(Wrappers.<ProjectRegisterInfoUnit>lambdaQuery().eq(ProjectRegisterInfoUnit::getPriId, id));
        if (ValidateUtil.isNotEmpty(infoUnits)) {
            List<ProjectRegisterInfoUnitModel> infoUnitModels = BeanUtils.copyList(infoUnits, ProjectRegisterInfoUnitModel.class);
            entity.setUnitList(infoUnitModels);
        }
        LambdaQueryWrapper<PreProjectRegisterInfoScore> scoreWrapper = new LambdaQueryWrapper<>();
        scoreWrapper.eq(PreProjectRegisterInfoScore::getPId, id);
        List<PreProjectRegisterInfoScore> scoreList = preProjectRegisterInfoScoreService.list(scoreWrapper);
        if (scoreList != null && scoreList.size() > 0) {
            List<PreProjectRegisterInfoScoreModel> scoreModels = BeanUtils.copyList(scoreList, PreProjectRegisterInfoScoreModel.class);
            entity.setScoreList(scoreModels);
        }
        return BaseResponse.ok(entity);
    }

    /**
     * 校验整个版本链的创建人单位及目标ID后办理变更，创建版本时检查发起限制。
     *
     * @param request 变更信息，registerType 可选，仅支持 01、02
     * @return 变更办理结果，创建时包含新版本主键
     */
    @Override
    public BaseResponse modifyPreProjectRegisterInfo(ProjectRegisterInfoModel request) {
        log.info("modifyPreProjectSolution request:{}", JacksonUtils.toJSONString(request));
        if (request == null) {
            return BaseResponse.fail("参数错误");
        }
        if (StrUtil.isEmpty(request.getVersionId())) {
            return BaseResponse.fail("缺失版本id");
        }
        if (!normalizeRegisterType(request)) {
            return BaseResponse.fail("登记业务类型仅支持01（项目申报）或02（项目论证）");
        }
        LogUserInfoVo user = dataPermission.currentUser();
        List<ProjectRegisterInfo> versions = baseMapper.selectList(Wrappers.<ProjectRegisterInfo>lambdaQuery()
                .eq(ProjectRegisterInfo::getVersionId, request.getVersionId()));
        if (versions.isEmpty()) {
            return BaseResponse.fail("申报或论证版本不存在");
        }
        for (ProjectRegisterInfo version : versions) {
            dataPermission.checkCreator(version.getCreateUid(), user);
            if (!request.getRegisterType().equals(StrUtil.blankToDefault(version.getRegisterType(), "01"))) {
                return BaseResponse.fail("变更业务类型与原记录不一致");
            }
        }
        if (StrUtil.isNotBlank(request.getId()) && versions.stream()
                .noneMatch(version -> version.getId().equals(request.getId()))) {
            return BaseResponse.fail("变更记录不属于指定版本");
        }
        if ("update".equals(request.getOperationType()) && StrUtil.isBlank(request.getId())) {
            return BaseResponse.fail("缺失变更记录ID");
        }
        String primaryId = null;
        if ("create".equals(request.getOperationType())) {
            ProjectRegisterInfoModel validationRequest = new ProjectRegisterInfoModel();
            BeanUtil.copyProperties(request, validationRequest);
            validationRequest.setId(null);
            String message = validateInitiation(validationRequest, true);
            if (message != null) {
                return BaseResponse.fail(message);
            }
            List<SupportDocumentDto> documentDtos = new ArrayList<>();
            SupportDocumentDto supportDocumentDto = new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(request.getProjectRegisterInfoFileId());
            supportDocumentDto.setSupportDocumentFileIDs(request.getProjectRegisterInfoFileIds());
            documentDtos.add(supportDocumentDto);
            primaryId = IdWorker.getGenerateID();
            ProjectRegisterInfo entity = new ProjectRegisterInfo();
            BeanUtil.copyProperties(request, entity);
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            entity.setId(primaryId);
            // 标识为变更
            entity.setLastest(2);
            if (StrUtil.isNotEmpty(request.getEvaluateObjectContractId())) {
                ZNPJEvaluateObjectContract znpjEvaluateObjectContract = znpjEvaluateObjectContractMapper.selectById(request.getEvaluateObjectContractId());
                entity.setEvaluateObjectContractVersionId(znpjEvaluateObjectContract.getVersionId());
            }
            entity.setCreateTime(new Date());
            List<PreProjectRegisterInfoScoreModel> scoreModelList = request.getScoreList();
            if (scoreModelList != null && scoreModelList.size() > 0) {
                List<PreProjectRegisterInfoScore> scoreList = new ArrayList<>();
                scoreModelList.forEach(scoreModel -> {
                    PreProjectRegisterInfoScore preProjectRegisterInfoScore = new PreProjectRegisterInfoScore();
                    BeanUtil.copyProperties(scoreModel, preProjectRegisterInfoScore);
                    preProjectRegisterInfoScore.setId(GenerateUUID.getUUID());
                    preProjectRegisterInfoScore.setPId(entity.getId());
                    scoreList.add(preProjectRegisterInfoScore);
                });
                preProjectRegisterInfoScoreService.saveBatch(scoreList);
            }
            saveFile(entity, documentDtos);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(),
                    entity.getCreateUname(), entity.getCreateTime(), request.getProjectId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), request.getSecId(), request.getTCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getApprovalFileId(),
                    request.getLandContractFileId(), request.getPlanningPermitFileId(), request.getDrawingReviewFileId(), request.getConstructionLicenseFileId()), entity.getId());
            projectRegisterInfoUnitService.saveOrUpdateProjectRegisterInfoUnit(request.getUnitList(), primaryId);
        } else if ("update".equals(request.getOperationType())) {
            LambdaQueryWrapper<ProjectRegisterInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProjectRegisterInfo::getVersionId, request.getVersionId());
            List<ProjectRegisterInfo> oldSolution = baseMapper.selectList(queryWrapper);
            //删除原来的分数记录信息
            LambdaQueryWrapper<PreProjectRegisterInfoScore> scoreWrapper = new LambdaQueryWrapper<>();
            scoreWrapper.eq(PreProjectRegisterInfoScore::getPId, request.getId());
            List<PreProjectRegisterInfoScoreModel> scoreModelList = request.getScoreList();
            preProjectRegisterInfoScoreService.remove(scoreWrapper);
            if (scoreModelList != null && scoreModelList.size() > 0) {
                List<PreProjectRegisterInfoScore> scoreList = new ArrayList<>();
                scoreModelList.forEach(scoreModel -> {
                    PreProjectRegisterInfoScore preProjectRegisterInfoScore = new PreProjectRegisterInfoScore();
                    BeanUtil.copyProperties(scoreModel, preProjectRegisterInfoScore);
                    preProjectRegisterInfoScore.setId(GenerateUUID.getUUID());
                    preProjectRegisterInfoScore.setPId(request.getId());
                    scoreList.add(preProjectRegisterInfoScore);
                });
                preProjectRegisterInfoScoreService.saveBatch(scoreList);
            }
            // 变更
            List<ProjectRegisterInfo> toUpdate = new ArrayList<>();
            oldSolution.stream()
                    .filter(change -> change.getLastest() == 1)
                    .forEach(change -> {
                        if (change.getState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                            change.setLastest(0);
                            toUpdate.add(change);
                        }
                    });
            oldSolution.stream()
                    .filter(change -> change.getLastest() == 2)
                    .forEach(change -> {
                        if (change.getState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                            change.setLastest(1);
                            toUpdate.add(change);
                        }
                    });
            if (!toUpdate.isEmpty()) {
                this.updateBatchById(toUpdate);
            }
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getApprovalFileId(),
                            request.getLandContractFileId(), request.getPlanningPermitFileId(), request.getDrawingReviewFileId(), request.getConstructionLicenseFileId()),
                    Arrays.asList(request.getFileIds(), request.getApprovalFileIds(), request.getLandContractFileIds(), request.getPlanningPermitFileIds(), request.getDrawingReviewFileIds(), request.getConstructionLicenseFileIds()), request.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    /**
     * 按当前登录用户单位分页查询变更记录，类型为空时默认项目申报（01）。
     *
     * @param request 分页与筛选条件，registerType 可选，仅支持 01、02
     * @param uid     当前用户主键
     * @return 当前用户可见的变更记录及总数
     */
    @Override
    public BaseResponse getPreProjectRegisterInfoModifyInfo(ProjectRegisterInfoModel request, String uid) {
        if (!normalizeRegisterType(request)) {
            return BaseResponse.fail("登记业务类型仅支持01（项目申报）或02（项目论证）");
        }
        LogUserInfoVo userInfoVo = dataPermission.currentUser();
        QueryMap<ProjectRegisterInfoModel> queryMap = new QueryMap<>(request);
        queryMap.put("projectCode").put("name").put("buildingType").put("declareUnit")
                .put("jointDeclareUnit").put("designUnit").put("constructionUnit").put("supervisionUnit")
                .put("serviceUnit").put("isIntegratedPlatform").put("versionId").put("registerType").put("proProjectRegisterId");
        dataPermission.applyScope(queryMap.getQuery(), userInfoVo);
        IPage<ProjectRegisterInfoModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectRegisterInfoModel> list = baseMapper.getPreProjectRegisterInfoModifyInfo(queryMap.getQuery(), page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    /**
     * 为前端检查当前项目能否发起申报或论证，与保存入口使用相同的业务规则。
     *
     * @param request 登记主键、业务类型、论证目的及编辑时的单据主键
     * @return 允许时成功，否则返回具体阻止原因
     */
    @Override
    public BaseResponse checkProjectRegisterInfo(ProjectRegisterInfoModel request) {
        if (!normalizeRegisterType(request)) {
            return BaseResponse.fail("登记业务类型仅支持01（项目申报）或02（项目论证）");
        }
        String message = validateInitiation(request, false);
        return message == null ? BaseResponse.ok() : BaseResponse.fail(message);
    }

    /**
     * 校验当前记录与关联登记的单位权限及发起限制；草稿不占用流程，编辑排除当前单据。
     * 编辑时未传论证目的则先沿用原值，再执行目的数量和冲突校验；显式空串仍参与校验。
     *
     * @param request 保存或预校验参数，编辑时未提供登记主键或论证目的则沿用原值
     * @param lock    是否在当前保存事务中锁定登记行
     * @return 校验通过返回 null，否则返回失败原因
     */
    private String validateInitiation(ProjectRegisterInfoModel request, boolean lock) {
        LogUserInfoVo user = dataPermission.currentUser();
        if (StrUtil.isNotBlank(request.getId())) {
            ProjectRegisterInfo old = baseMapper.selectById(request.getId());
            if (old == null) {
                return "申报或论证记录不存在";
            }
            dataPermission.checkCreator(old.getCreateUid(), user);
            String oldType = StrUtil.blankToDefault(old.getRegisterType(), "01");
            if (!oldType.equals(request.getRegisterType())) {
                return "不允许修改申报或论证的业务类型";
            }
            if (StrUtil.isBlank(request.getProProjectRegisterId())) {
                request.setProProjectRegisterId(old.getProProjectRegisterId());
            }
            if (request.getDeclarePurpose() == null) {
                request.setDeclarePurpose(old.getDeclarePurpose());
            }
            if (StrUtil.isNotBlank(old.getProProjectRegisterId())
                    && !old.getProProjectRegisterId().equals(request.getProProjectRegisterId())) {
                return "不允许修改关联的项目登记";
            }
        }
        if (StrUtil.isBlank(request.getProProjectRegisterId())) {
            return "请选择项目登记";
        }
        ProProjectRegister register = lock
                ? proProjectRegisterMapper.selectByIdForUpdate(request.getProProjectRegisterId())
                : proProjectRegisterMapper.selectById(request.getProProjectRegisterId());
        if (register == null) {
            return "项目登记不存在";
        }
        dataPermission.checkCreator(register.getCreateUid(), user);
        // 可选项目的 state=7 筛选由前端负责，后端只检查关联及发起冲突。
        List<ProjectRegisterInfo> records = baseMapper.selectList(Wrappers.<ProjectRegisterInfo>lambdaQuery()
                .eq(ProjectRegisterInfo::getProProjectRegisterId, request.getProProjectRegisterId())
                .ne(StrUtil.isNotBlank(request.getId()), ProjectRegisterInfo::getId, request.getId())
                .last(lock ? "for update" : ""));
        boolean declaration = "01".equals(request.getRegisterType());
        for (ProjectRegisterInfo record : records) {
            String type = StrUtil.blankToDefault(record.getRegisterType(), "01");
            if (declaration && "01".equals(type)
                    && (isProcessing(record.getState()) || JudgeUtil.isApprovedState(record.getState()))) {
                return "该项目已存在进行中或审批通过的项目申报";
            }
            if (!declaration && "02".equals(type) && isProcessing(record.getState())) {
                return "该项目已存在进行中的项目论证，请在审批结束后再次发起";
            }
        }
        if (declaration) {
            return null;
        }
        Set<String> purposes = splitPurpose(request.getDeclarePurpose());
        if (purposes.size() != 1) {
            return "项目论证每次必须且只能选择一个论证目的";
        }
        request.setDeclarePurpose(purposes.iterator().next());
        Set<String> conflictPurposes = splitPurpose(sysConfigService.getConfigByCode(
                "projectRegisterInfo.declarationConflictPurposes"));
        Set<String> nonRepeatablePurposes = splitPurpose(sysConfigService.getConfigByCode(
                "projectRegisterInfo.nonRepeatablePurposes"));
        for (ProjectRegisterInfo record : records) {
            if (!JudgeUtil.isApprovedState(record.getState())) {
                continue;
            }
            String type = StrUtil.blankToDefault(record.getRegisterType(), "01");
            if ("01".equals(type) && !Collections.disjoint(purposes, conflictPurposes)) {
                return "该项目已存在审批通过的项目申报，不能发起所选目的的论证";
            }
            if ("02".equals(type) && !Collections.disjoint(purposes, nonRepeatablePurposes)
                    && !Collections.disjoint(purposes, splitPurpose(record.getDeclarePurpose()))) {
                return "该项目所选论证目的已审批通过，不能重复发起";
            }
        }
        return null;
    }

    /**
     * 判断是否为未结束的流程，包含退回、暂停及流程改变状态，草稿和停止不占用。
     *
     * @param state 审批状态
     * @return 是否存在进行中的流程
     */
    private boolean isProcessing(Integer state) {
        return state != null && Arrays.asList(1, 2, 4, 5, 6, 8, 9).contains(state);
    }

    /**
     * 将逗号分隔的目的编码转换为去空白、去重集合，使用完整编码比较。
     *
     * @param value 目的编码或系统配置值，支持中英文逗号
     * @return 非空目的编码集合
     */
    private Set<String> splitPurpose(String value) {
        Set<String> result = new LinkedHashSet<>();
        if (StrUtil.isNotBlank(value)) {
            for (String item : value.split("[,，]")) {
                if (StrUtil.isNotBlank(item)) {
                    result.add(item.trim());
                }
            }
        }
        return result;
    }

    /**
     * 补齐并校验登记业务类型，缺省、null、空串或纯空白均默认项目申报（01）。
     *
     * @param request 待规范化的登记请求
     * @return 请求非空且业务类型为 01 或 02 时返回 true
     */
    private boolean normalizeRegisterType(ProjectRegisterInfoModel request) {
        if (request == null) {
            return false;
        }
        if (StrUtil.isBlank(request.getRegisterType())) {
            request.setRegisterType("01");
        }
        return "01".equals(request.getRegisterType()) || "02".equals(request.getRegisterType());
    }

    /**
     * 按类型统计当前用户可见的最新申报或论证，同一登记已有申报时不再计入论证。
     *
     * @param registerType 必填，01项目申报、02项目论证
     * @return 总数、审批中和流程结束数量
     */
    @Override
    public BaseResponse getDeclarationStatistics(String registerType) {
        if (!"01".equals(registerType) && !"02".equals(registerType)) {
            return BaseResponse.fail("registerType必须为01（项目申报）或02（项目论证）");
        }
        Map<String, Object> params = new HashMap<>();
        dataPermission.applyScope(params, dataPermission.currentUser());
        params.put("registerType", registerType);
        return BaseResponse.ok(baseMapper.countDeclarationStatistics(params));
    }
}