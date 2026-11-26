package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertChangeModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.FileMsg;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.other.TaskMapper;
import com.jpmsoft.projectProducer.mapper.other.TaskProcessinMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertChangeMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.other.*;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpert;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAudit;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertChange;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAuditService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertChangeService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 专家库变更记录
 */
@Slf4j
@Service
public class SecExpertChangeServiceImpl extends ServiceImpl<SecExpertChangeMapper, SecExpertChange> implements ISecExpertChangeService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private ISecExpertAuditService secExpertAuditService;
    @Resource
    private TaskProcessinMapper taskProcessinMapper;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecExpertChange(SecExpertChangeModel request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (secExpertAuditService.count(Wrappers.<SecExpertAudit>lambdaQuery()
                .eq(SecExpertAudit::getYear, request.getYear())
                .ne(BaseEntity::getState, ApprovalStatusEnum.Invalid.getIndex())
                .eq(SecExpertAudit::getSeaCid, ValidateUtil.isEmpty(request.getCId()) ? request.getcId() : request.getCId())
        ) > 0) {
            return BaseResponse.fail("企业管理员已提交审批，无法修改或新增！");
        }
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        SecExpertChange secExpertChange;
        if (ValidateUtil.isEmpty(request.getId())) {
            // 创建
            // 去重验证
            if (this.count(Wrappers.<SecExpertChange>lambdaQuery()
                    .eq(SecExpertChange::getExId, request.getExId())
                    .ne(SecExpertChange::getExState, ApprovalStatusEnum.Invalid.getIndex())
            ) > 0) {
                return BaseResponse.fail("存在变更记录，请勿重复提交！");
            }
            secExpertChange = new SecExpertChange();
            BeanUtils.copyProperties(request, secExpertChange);
            secExpertChange.setId(GenerateUUID.getUUID());
            secExpertChange.setExCreateTime(new Date());
            secExpertChange.setExCreateUid(user.getId());
            secExpertChange.setExCreateUname(user.getName());
            // 校验是否变更
            secExpertChange.setChanged(this.isChanged(secExpertChange));
            sysFileService.saveFile(secExpertChange.getId(), request.gettId(), secExpertChange.getExState() + "", secExpertChange.getExCreateUid(), secExpertChange.getExCreateUname(), secExpertChange.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), secExpertChange.getId());
            if (ValidateUtil.isNotEmpty(secExpertChange.getExIdNumber())) {
                secExpertChange.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(secExpertChange.getExIdNumber()));
            }
            secExpertService.updateChangeStatus(secExpertChange.getExId(), "04");
            this.save(secExpertChange);
        } else {
            // 更新
            secExpertChange = this.getById(request.getId());
            if (ValidateUtil.isNull(secExpertChange)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            BeanUtils.copyProperties(request, secExpertChange);
            // 校验是否变更
            secExpertChange.setChanged(this.isChanged(secExpertChange));
            sysFileService.saveFile(secExpertChange.getId(), request.gettId(), secExpertChange.getExState() + "", secExpertChange.getExCreateUid(), secExpertChange.getExCreateUname(), secExpertChange.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), Arrays.asList(request.getExCardFileIds(), request.getExDegreeIds(), request.getExPerformFileIds(), request.getExTitleFileIds(), request.getExPromiseFileIds(), request.getExAvoidFileIds(), request.getExOtherFileIds(), request.getFileIds()), secExpertChange.getId());
            if (ValidateUtil.isNotEmpty(secExpertChange.getExIdNumber())) {
                secExpertChange.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(secExpertChange.getExIdNumber()));
            }
            this.updateById(secExpertChange);
        }
        return BaseResponse.ok(secExpertChange.getId());
    }

    private String isChanged(SecExpertChange secExpertChange) {
        SecExpert expert = secExpertService.getById(secExpertChange.getExId());
        if (ValidateUtil.isNull(expert)) {
            return "01";
        }

        // 先比较基础字段
        if (isBaseInfoChanged(expert, secExpertChange)) {
            return "01";
        }

        // 再比较附件
        if (isAttachmentChanged(expert, secExpertChange)) {
            return "01";
        }

        return "00";
    }

    private boolean isBaseInfoChanged(SecExpert expert, SecExpertChange change) {
        return Stream.of(
                fieldChanged(expert.getCId(), change.getCId()),
                fieldChanged(expert.getExName(), change.getExName()),
                fieldChanged(expert.getExSex(), change.getExSex()),
                fieldChanged(expert.getExBirthday(), change.getExBirthday()),
                fieldChanged(expert.getExFamily(), change.getExFamily()),
                fieldChanged(expert.getExIdType(), change.getExIdType()),
                fieldChanged(apiUtilsService.SelfSm2Decrypt(expert.getExIdNumber()), apiUtilsService.SelfSm2Decrypt(change.getExIdNumber())),
                fieldChanged(expert.getExLocation(), change.getExLocation()),
                fieldChanged(expert.getExPolitics(), change.getExPolitics()),
                fieldChanged(expert.getExEducation(), change.getExEducation()),
                fieldChanged(expert.getExMajor(), change.getExMajor()),
                fieldChanged(expert.getExSchool(), change.getExSchool()),
                fieldChanged(expert.getExWorkState(), change.getExWorkState()),
                fieldChanged(expert.getExProfessionalYears(), change.getExProfessionalYears()),
                fieldChanged(expert.getExWorkUnit(), change.getExWorkUnit()),
                fieldChanged(expert.getExPost(), change.getExPost()),
                fieldChanged(expert.getExTitle(), change.getExTitle()),
                fieldChanged(expert.getExTitleNumber(), change.getExTitleNumber()),
                fieldChanged(expert.getExProfessionalCategory(), change.getExProfessionalCategory()),
                fieldChanged(expert.getExIndustry(), change.getExIndustry()),
                fieldChanged(expert.getExWorkAddress(), change.getExWorkAddress()),
                fieldChanged(expert.getExCellphoneNumber1(), change.getExCellphoneNumber1()),
                fieldChanged(expert.getExCellphoneNumber2(), change.getExCellphoneNumber2()),
                fieldChanged(expert.getExPostcode(), change.getExPostcode()),
                fieldChanged(expert.getExFax(), change.getExFax()),
                fieldChanged(expert.getExHomePhone(), change.getExHomePhone()),
                fieldChanged(expert.getExEmail(), change.getExEmail()),
                fieldChanged(expert.getExMailAddress(), change.getExMailAddress()),
                fieldChanged(expert.getExBidMajor(), change.getExBidMajor()),
                fieldChanged(expert.getExPracticeQualification(), change.getExPracticeQualification()),
                fieldChanged(expert.getExBidArea(), change.getExBidArea()),
                fieldChanged(expert.getExPersonalResearch(), change.getExPersonalResearch()),
                fieldChanged(expert.getExProjects(), change.getExProjects()),
                fieldChanged(expert.getExAvoidanceUnit(), change.getExAvoidanceUnit()),
                fieldChanged(expert.getExCategory(), change.getExCategory()),
                fieldChanged(expert.getExStartDate(), change.getExStartDate()),
                fieldChanged(expert.getExTilteDate(), change.getExTilteDate()),
                fieldChanged(expert.getExPracticeDate(), change.getExPracticeDate()),
                fieldChanged(expert.getExPracticeCode(), change.getExPracticeCode()),
                fieldChanged(expert.getExMainWork(), change.getExMainWork()),
                fieldChanged(expert.getExPersonalResult(), change.getExPersonalResult()),
                fieldChanged(expert.getExStatus(), change.getExStatus()),
                fieldChanged(expert.getExType(), change.getExType()),
                fieldChanged(expert.getExQualification(), change.getExQualification()),
                fieldChanged(expert.getExDiplomaMajor(), change.getExDiplomaMajor()),
                fieldChanged(expert.getExWorkTime(), change.getExWorkTime()),
                fieldChanged(expert.getExWorkYear(), change.getExWorkYear()),
                fieldChanged(expert.getExWorkMon(), change.getExWorkMon()),
                fieldChanged(expert.getExCpy(), change.getExCpy()),
                fieldChanged(expert.getExCpyPost(), change.getExCpyPost()),
                fieldChanged(expert.getExBank(), change.getExBank()),
                fieldChanged(expert.getExPostNum(), change.getExPostNum()),
                fieldChanged(expert.getExFinishDate(), change.getExFinishDate()),
                fieldChanged(expert.getExBankAccount(), change.getExBankAccount()),
                fieldChanged(expert.getExMajorNum(), change.getExMajorNum()),
                fieldChanged(expert.getExIsAvoidance(), change.getExIsAvoidance()),
                fieldChanged(expert.getExApplicantId(), change.getExApplicantId()),
                fieldChanged(expert.getExSysWorkYear(), change.getExSysWorkYear()),
                fieldChanged(expert.getExDeclareMajorOne(), change.getExDeclareMajorOne()),
                fieldChanged(expert.getExDeclareMajorTwo(), change.getExDeclareMajorTwo()),
                fieldChanged(expert.getExAvoid(), change.getExAvoid()),
                fieldChanged(expert.getPersonnelState(), change.getPersonnelState()),
                fieldChanged(expert.getPersonnelTimeStr(), change.getPersonnelTimeStr()),
                fieldChanged(expert.getPersonnelTimeEnd(), change.getPersonnelTimeEnd()),
                fieldChanged(expert.getKeyExpert(), change.getKeyExpert()),
                fieldChanged(expert.getExAge(), change.getExAge()),
                fieldChanged(expert.getExRank(), change.getExRank()),
                fieldChanged(expert.getExRemark(), change.getExRemark()),
                fieldChanged(expert.getYear(), change.getYear())
        ).anyMatch(Boolean::booleanValue);
    }

    private <T> boolean fieldChanged(T source, T target) {
        return !Objects.equals(source, target);
    }

    private boolean isAttachmentChanged(SecExpert expert, SecExpertChange change) {
        List<String> sourceRelationIds = buildRelationIds(
                expert.getExCardFile(),
                expert.getExDegree(),
                expert.getExPerformFile(),
                expert.getExTitleFile(),
                expert.getExPromiseFile(),
                expert.getExAvoidFile(),
                expert.getExOtherFile()
        );

        List<String> changeRelationIds = buildRelationIds(
                change.getExCardFile(),
                change.getExDegree(),
                change.getExPerformFile(),
                change.getExTitleFile(),
                change.getExPromiseFile(),
                change.getExAvoidFile(),
                change.getExOtherFile()
        );

        // 槽位数量不同直接认为变更
        if (sourceRelationIds.size() != changeRelationIds.size()) {
            return true;
        }

        List<String> allRelationIds = Stream.concat(sourceRelationIds.stream(), changeRelationIds.stream())
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());

        if (allRelationIds.isEmpty()) {
            return false;
        }

        List<SysFileannex> allFiles = sysFileAnnexService.list(
                Wrappers.<SysFileannex>lambdaQuery()
                        .in(SysFileannex::getFileRelationId, allRelationIds)
        );

        Map<String, List<SysFileannex>> fileMap = allFiles.stream()
                .filter(f -> ValidateUtil.isNotEmpty(f.getFileRelationId()))
                .collect(Collectors.groupingBy(SysFileannex::getFileRelationId));

        String root = sysConfigService.getConfigByCode("rootDirectory");
        String archive = sysConfigService.getConfigByCode("archiveDirectory");
        if (ValidateUtil.isAnObjEmpty(root, archive)) {
            throw new JPMException(FileMsg.missSysConfig);
        }

        Map<String, String> md5Cache = new HashMap<>();

        for (int i = 0; i < sourceRelationIds.size(); i++) {
            List<String> sourceMd5List = extractSortedMd5List(fileMap.get(sourceRelationIds.get(i)), root, archive, md5Cache);
            List<String> changeMd5List = extractSortedMd5List(fileMap.get(changeRelationIds.get(i)), root, archive, md5Cache);
            if (!Objects.equals(sourceMd5List, changeMd5List)) {
                return true;
            }
        }

        return false;
    }

    private List<String> buildRelationIds(String... relationIds) {
        return Arrays.stream(relationIds)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toList());
    }

    private List<String> extractSortedMd5List(List<SysFileannex> files,
                                              String root,
                                              String archive,
                                              Map<String, String> md5Cache) {
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        return files.stream()
                // 保证同一槽位下文件顺序稳定，避免数据库返回顺序导致误判
                .sorted(Comparator
                        .comparing((SysFileannex f) -> Optional.ofNullable(f.getFileUploadTime()).orElse(new Date(0)))
                        .thenComparing(f -> Optional.ofNullable(f.getFileName()).orElse("")))
                .map(f -> getFileMd5(f, root, archive, md5Cache))
                .collect(Collectors.toList());
    }

    private String getFileMd5(SysFileannex file,
                              String root,
                              String archive,
                              Map<String, String> md5Cache) {
        if (ValidateUtil.isNotEmpty(file.getFileMd5Code())) {
            return file.getFileMd5Code().toLowerCase(Locale.ROOT);
        }

        String fullPath = buildFullPath(file, root, archive);
        return md5Cache.computeIfAbsent(fullPath, path -> {
            File localFile = new File(path);
            if (localFile.exists() && localFile.isFile()) {
                return SecureUtil.md5(localFile).toLowerCase(Locale.ROOT);
            }
            return "";
        });
    }

    private String buildFullPath(SysFileannex file, String root, String archive) {
        if (Objects.equals(file.getFileLocation(), 1)) {
            return archive + "/" + file.getFilePath();
        }
        return root + "/" + file.getFilePath();
    }

    @Override
    public BaseResponse getSecExpertChangeList(SecExpertChangeModel request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<SecExpertChangeModel> queryMap = new QueryMap<>(request);
        Page<SecExpertChangeModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertChangeModel> datas = baseMapper.getSecExpertChangeList(page, queryMap.getQuery());
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getSecExpertChangeById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertChangeModel secExpertChange = baseMapper.getSecExpertChangeById(id);
        if (ValidateUtil.isNull(secExpertChange)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isNotEmpty(secExpertChange.getExIdNumber())) {
            secExpertChange.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(secExpertChange.getExIdNumber()));
        }
        return BaseResponse.ok(secExpertChange);
    }

    @Override
    public BaseResponse syncSecExpertChangeData(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertChange secExpertChange = this.getById(id);
        if (ValidateUtil.isNull(secExpertChange)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(secExpertChange.getExState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        SecExpert expert = secExpertService.getById(secExpertChange.getExId());
        if (ValidateUtil.isNull(expert)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        BeanUtils.copyProperties(secExpertChange, expert);
        expert.setExState(ApprovalStatusEnum.Already_Over.getIndex());
        expert.setExCreateTime(new Date());
        expert.setExCardFile(GenerateUUID.getUUID());
        expert.setExDegree(GenerateUUID.getUUID());
        expert.setExPerformFile(GenerateUUID.getUUID());
        expert.setExTitleFile(GenerateUUID.getUUID());
        expert.setExPromiseFile(GenerateUUID.getUUID());
        expert.setExAvoidFile(GenerateUUID.getUUID());
        expert.setExOtherFile(GenerateUUID.getUUID());
        secExpertService.updateById(expert);
        sysFileAnnexService.copyFileAnnex2NewRelation(
                Arrays.asList(secExpertChange.getExCardFile(), secExpertChange.getExDegree(), secExpertChange.getExPerformFile(), secExpertChange.getExTitleFile(), secExpertChange.getExPromiseFile(), secExpertChange.getExAvoidFile(), secExpertChange.getExOtherFile()),
                Arrays.asList(expert.getExCardFile(), expert.getExDegree(), expert.getExPerformFile(), expert.getExTitleFile(), expert.getExPromiseFile(), expert.getExAvoidFile(), expert.getExOtherFile()),
                expert.getExId(), true);
        return BaseResponse.ok(expert.getExId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse invalidProcessData(List<String> exIds) {
        if (ValidateUtil.isEmpty(exIds)) {
            return BaseResponse.ok();
        }

        List<String> cleanExIds = exIds.stream()
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(cleanExIds)) {
            return BaseResponse.ok();
        }

        List<String> invalidIds = this.list(Wrappers.<SecExpertChange>lambdaQuery()
                        .select(SecExpertChange::getId)
                        .in(SecExpertChange::getExId, cleanExIds)
                        .ne(SecExpertChange::getExState, ApprovalStatusEnum.Already_Over.getIndex())
                        .ne(SecExpertChange::getExState, ApprovalStatusEnum.Invalid.getIndex()))
                .stream()
                .map(SecExpertChange::getId)
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(invalidIds)) {
            return BaseResponse.ok();
        }

        this.update(Wrappers.<SecExpertChange>lambdaUpdate()
                .set(SecExpertChange::getExState, ApprovalStatusEnum.Invalid.getIndex())
                .in(SecExpertChange::getId, invalidIds));
        // 作废
        sysFileService.update(Wrappers.<SysFile>lambdaUpdate()
                .set(SysFile::getFileflowstatus, ApprovalStatusEnum.Invalid.getIndex())
                .in(SysFile::getFileid, invalidIds));
        // 删除待办
        taskMapper.delete(Wrappers.<Task>lambdaQuery()
                .in(Task::getTaskentityid, invalidIds));
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse batchSyncChange(List<String> exIds) {
        if (ValidateUtil.isEmpty(exIds)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 查询全部数据，单个专家只会有一个变更记录
        List<SecExpertChange> changes = this.list(Wrappers.<SecExpertChange>lambdaQuery().in(SecExpertChange::getExId, exIds)
                .eq(SecExpertChange::getExState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        List<SecExpert> experts = secExpertService.list(Wrappers.<SecExpert>lambdaQuery().in(SecExpert::getExId, exIds)
                .ne(SecExpert::getExState, ApprovalStatusEnum.Invalid.getIndex())
        );
        if (ValidateUtil.isNotEmpty(changes) && ValidateUtil.isNotEmpty(experts)) {
            // 要更新的专家信息
            List<SecExpert> list = new ArrayList<>();
            Map<String, SecExpert> expertMap = experts.stream().filter(e -> e.getExId() != null).collect(Collectors.toMap(SecExpert::getExId, e -> e, (e1, e2) -> e1));
            for (SecExpertChange change : changes) {
                SecExpert expert = expertMap.get(change.getExId());
                if (ValidateUtil.isNull(expert)) {
                    continue;
                }
                BeanUtils.copyProperties(change, expert,
                        "exState", "exStatus", "exStateTime", "exCreateTime", "exCreateUname", "exCreateUid", "keyExpert"
                );
                expert.setExState(ApprovalStatusEnum.Already_Over.getIndex());
                expert.setExCreateTime(new Date());
                expert.setExCardFile(GenerateUUID.getUUID());
                expert.setExDegree(GenerateUUID.getUUID());
                expert.setExPerformFile(GenerateUUID.getUUID());
                expert.setExTitleFile(GenerateUUID.getUUID());
                expert.setExPromiseFile(GenerateUUID.getUUID());
                expert.setExAvoidFile(GenerateUUID.getUUID());
                expert.setExOtherFile(GenerateUUID.getUUID());
                list.add(expert);
                sysFileAnnexService.copyFileAnnex2NewRelation(
                        Arrays.asList(change.getExCardFile(), change.getExDegree(), change.getExPerformFile(), change.getExTitleFile(), change.getExPromiseFile(), change.getExAvoidFile(), change.getExOtherFile()),
                        Arrays.asList(expert.getExCardFile(), expert.getExDegree(), expert.getExPerformFile(), expert.getExTitleFile(), expert.getExPromiseFile(), expert.getExAvoidFile(), expert.getExOtherFile()),
                        expert.getExId(), true);
            }
            secExpertService.updateBatchById(list);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(token)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 查询原来的数据
        SecExpertChange entity = this.getById(request.getFileId());
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        entity.setExState(ApprovalStatusEnum.Returned_Initiator.getIndex());
        this.updateById(entity);
        SysFile sysFile = sysFileService.getById(entity.getId());
        sysFileService.saveFile(entity.getId(), sysFile.gettId(), ApprovalStatusEnum.Returned_Initiator.getIndex() + "", entity.getExCreateUid(), entity.getExCreateUname()
                , entity.getExCreateTime(), null, sysFile.getFiletitle(), sysFile.getFilecode(),
                "update", sysFile.getFileRelationId(), sysFile.getFilesectionid(), sysFile.gettCode(), sysFile.getFilecontract());
        // 生成一条已办
        // 获取当前节点
        FlowActivity flowActivity = taskProcessinMapper.getCurrentNode(sysFile.getFlowId());
        LogUserInfoVo userInfoByToken = redisUtils.getUserInfoByToken(token);
        TaskProcessin his = new TaskProcessin();
        his.setTpId(GenerateUUID.getUUID());
        his.setActname(flowActivity.getActname());
        his.setTpDotime(new Date());
        his.setTpExplain(request.getOpinion());
        his.setTpFlowid(sysFile.getFlowId());
        his.setTpProject(sysFile.getFileproject());
        his.setTpId(GenerateUUID.getUUID());
        his.setTpResult("退回");
        his.setTpEntityid(entity.getId());
        his.setTpName(sysFile.getFiletitle());
        his.setTpUser(userInfoByToken.getId());
        his.setTpType("退回");
        his.setTpActtype("userTask");
        his.setTpActhour(flowActivity.getActhour() != null ? Float.parseFloat(flowActivity.getActhour() + "") : null);
        his.setTpFrom(userInfoByToken.getId());
        his.setTpFileid(entity.getId());
        his.setTpSend(entity.getExCreateUid());
        his.setTpTemplatecodeo(sysFile.gettCode());
        his.setTpActid(flowActivity.getActid());
        his.setTpSigntime(new Date());
        his.setTpDotime(new Date());
        if (!"All".equals(sysFile.getTeId())) {
            his.setTeId(sysFile.getTeId());
        }
        taskProcessinMapper.insert(his);

        // 生成一条待办
        String uid = redisUtils.getUid(token);
        BatchhandlingTaskDto batchhandlingTaskDto = new BatchhandlingTaskDto();
        BatchhandlingTaskListDto batchhandlingTaskListDto = new BatchhandlingTaskListDto();
        batchhandlingTaskListDto.setFileId(entity.getId());
        batchhandlingTaskListDto.setProcessed(entity.getExCreateUid());
        // batchhandlingTaskListDto.setOpinion(request.getOpinion());
        batchhandlingTaskListDto.setUId(uid);
        batchhandlingTaskListDto.setOperator(uid);
        batchhandlingTaskDto.settCode(sysFile.gettCode());
        batchhandlingTaskDto.setSendList(Collections.singletonList(batchhandlingTaskListDto));
        batchhandlingTaskDto.setTeId("All");
        batchhandlingTaskDto.setCurrentNode("returnStartNode");
        batchhandlingTaskDto.setOnlineConditions("false");
        batchhandlingTaskDto.setNotGenerateTask("1");
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                bpmFeign.batchSendTask(batchhandlingTaskDto, token);
            }
        });
        secExpertService.updateChangeStatus(entity.getExId(), "04");
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse updateChangeStatus(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertChange secExpertChange = this.getById(id);
        if (ValidateUtil.isNull(secExpertChange)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(secExpertChange.getExState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        if ("00".equals(secExpertChange.getChanged())) {
            secExpertService.updateChangeStatus(secExpertChange.getExId(), "00");
        } else {
            secExpertService.updateChangeStatus(secExpertChange.getExId(), "02");
        }
        return BaseResponse.ok();
    }
}
