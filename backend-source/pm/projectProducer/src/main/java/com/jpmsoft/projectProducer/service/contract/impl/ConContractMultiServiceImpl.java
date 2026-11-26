package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Contract.ConContractMultiProjectRequest;
import com.jpmsoft.common.model.request.Contract.ConContractMultiRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.common.model.response.Contract.ConContractMultiFundingResponse;
import com.jpmsoft.common.model.response.Contract.ConContractMultiProjectResponse;
import com.jpmsoft.common.model.response.Contract.ConContractMultiResponse;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMultiMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMulti;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMultiFunding;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMultiProject;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.contract.IConContractMultiFundingService;
import com.jpmsoft.projectProducer.service.contract.IConContractMultiProjectService;
import com.jpmsoft.projectProducer.service.contract.IConContractMultiService;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.other.ISysDictionaryService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 多项目合同
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Slf4j
@Service
public class ConContractMultiServiceImpl extends ServiceImpl<ConContractMultiMapper, ConContractMulti> implements IConContractMultiService {
    @Resource
    private IConContractMultiProjectService conContractMultiProjectService;
    @Resource
    private IConContractMultiFundingService conContractMultiFundingService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IConContractService conContractService;
    @Resource
    private ISysDictionaryService sysDictionaryService;

    /**
     * 创建或更新多项目合同及扩展字段，并维护合同扫描件、发包依据、开工令和通用附件关系。
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateContractMulti(ConContractMultiRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        boolean isNew = ValidateUtil.isEmpty(request.getId());
        ConContractMulti entity = new ConContractMulti();
        BeanUtils.copyProperties(request, entity);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        if (isNew) {
            List<String> pIdList = Optional.ofNullable(request.getConContractMultiProject())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(ConContractMultiProjectRequest::getPId)
                    .distinct()
                    .collect(Collectors.toList());
            if (existProject(pIdList, request.getName())) {
                return BaseResponse.fail("所选的项目中存在已关联合同的项目，请重新选择");
            }
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            this.save(entity);
            createMultiSubtables(request, entity.getId());
            sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                    entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                    request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(
                    Arrays.asList(request.getContractFile(), request.getContractMainFile(), request.getFileRelationId(),
                            request.getConBasisFile(), request.getConStartsTo()),
                    entity.getId()
            );
        } else {
            ConContractMulti multi = this.getById(request.getId());
            if (ValidateUtil.isNull(multi)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            if (JudgeUtil.nonModifyState(multi.getState())) {
                return BaseResponse.fail(ResMsg.stateNotModify);
            }
            deleteMultiSubtables(request.getId());
            createMultiSubtables(request, request.getId());
            entity.setCreateUid(multi.getCreateUid());
            entity.setCreateUname(multi.getCreateUname());
            entity.setCreateTime(multi.getCreateTime());
            this.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                    entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                    request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(
                    Arrays.asList(request.getContractFile(), request.getContractMainFile(), request.getFileRelationId(),
                            request.getConBasisFile(), request.getConStartsTo()),
                    Arrays.asList(request.getContractFileIds(), request.getContractMainFileIds(), request.getFileIds(),
                            request.getConBasisFileIds(), request.getConStartsToIds()),
                    entity.getId()
            );
        }
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 删除多项目监理合同子表数据
     *
     * @param id
     */
    private void deleteMultiSubtables(String id) {
        conContractMultiProjectService.remove(Wrappers.<ConContractMultiProject>lambdaQuery().eq(ConContractMultiProject::getCcmId, id));
        conContractMultiFundingService.remove(Wrappers.<ConContractMultiFunding>lambdaQuery().eq(ConContractMultiFunding::getCcmId, id));
    }

    /**
     * 创建多项目监理合同子表数据
     *
     * @param request
     * @param id
     */
    private void createMultiSubtables(ConContractMultiRequest request, String id) {
        if (!ValidateUtil.isEmpty(request.getConContractMultiProject())) {
            List<ConContractMultiProject> projectList = request.getConContractMultiProject().stream().map(project -> {
                ConContractMultiProject entity = new ConContractMultiProject();
                BeanUtils.copyProperties(project, entity);
                entity.setId(GenerateUUID.getUUID());
                entity.setCcmId(id);
                return entity;
            }).collect(Collectors.toList());
            conContractMultiProjectService.saveBatch(projectList);
        }
        if (!ValidateUtil.isEmpty(request.getConContractMultiFunding())) {
            List<ConContractMultiFunding> fundingList = request.getConContractMultiFunding().stream().map(funding -> {
                ConContractMultiFunding entity = new ConContractMultiFunding();
                BeanUtils.copyProperties(funding, entity);
                funding.setId(GenerateUUID.getUUID());
                funding.setCcmId(id);
                return entity;
            }).collect(Collectors.toList());
            conContractMultiFundingService.saveBatch(fundingList);
        }
    }

    /**
     * 项目是否已存在
     *
     * @param pIds
     * @return
     */
    private Boolean existProject(List<String> pIds, String contractName) {
        if (ValidateUtil.isEmpty(pIds)) {
            return false;
        }
        List<ConContractMultiProject> projectList = conContractMultiProjectService.list(
                Wrappers.<ConContractMultiProject>lambdaQuery().in(ConContractMultiProject::getPId, pIds)
        );
        if (ValidateUtil.isNotEmpty(projectList)) {
            List<String> ccmIds = projectList.stream()
                    .map(ConContractMultiProject::getCcmId)
                    .distinct()
                    .collect(Collectors.toList());
            List<ConContractMulti> multiList = this.list(
                    Wrappers.<ConContractMulti>lambdaQuery()
                            .in(ConContractMulti::getId, ccmIds)
                            .ne(ConContractMulti::getState, ApprovalStatusEnum.Invalid.getIndex())
            );
            boolean exist = multiList.stream().anyMatch(multi -> contractName.equals(multi.getName()));
            if (exist) {
                return true;
            }
        }
        List<ConContract> contracts = conContractService.list(
                Wrappers.<ConContract>lambdaQuery()
                        .in(ConContract::getConProjectId, pIds)
                        .ne(ConContract::getConApprovalState, ApprovalStatusEnum.Invalid.getIndex())
        );
        return contracts.stream().anyMatch(contract -> contractName.equals(contract.getConName()));
    }

    /**
     * 独立执行江南大学多项目合同同步：名称取主表name，金额取子项目conAmount。
     * 计价方式支持“金额”和“其他”，空值按历史默认值“金额”处理，并同步四类附件。
     * 以已保存数据为来源，空值清空目标扩展字段；金额计价时清空其他计价说明。
     *
     * @param request 包含多项目合同ID及目标合同模板编码tCode的请求
     * @return 同步结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncToContractJNDX(ConContractMultiRequest request) {
        if (request == null || ValidateUtil.isEmpty(request.getId()) || ValidateUtil.isEmpty(request.gettCode())) {
            return BaseResponse.fail("多项目合同ID和目标合同模板编码不能为空");
        }
        ConContractMulti multi = this.getById(request.getId());
        if (multi == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(multi.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        String pricingMethod = ValidateUtil.isEmpty(multi.getConPricingMethod()) ? "金额" : multi.getConPricingMethod();
        if (!"金额".equals(pricingMethod) && !"其他".equals(pricingMethod)) {
            return BaseResponse.fail("计价方式仅支持金额或其他");
        }
        if (ValidateUtil.isEmpty(multi.getName())) {
            return BaseResponse.fail("合同名称不能为空");
        }
        List<ConContractMultiProjectResponse> projectList =
                conContractMultiProjectService.getConPayMultiProjectByCcmId(multi.getId());
        if (ValidateUtil.isEmpty(projectList)) {
            return BaseResponse.fail("多项目合同未配置项目信息，无法同步至合同");
        }
        FileTemplateTree template = sysFileService.getFileTemplateByTCode(request.gettCode());
        if (template == null) {
            return BaseResponse.fail("目标合同表单模板不存在");
        }
        List<ConContractMultiFunding> fundingList = conContractMultiFundingService.list(
                Wrappers.<ConContractMultiFunding>lambdaQuery().eq(ConContractMultiFunding::getCcmId, multi.getId()));
        Map<String, ConContract> existingContracts = conContractService.list(
                        Wrappers.<ConContract>lambdaQuery().eq(ConContract::getCcmId, multi.getId())).stream()
                .collect(Collectors.toMap(ConContract::getConId, contract -> contract));
        for (ConContractMultiProjectResponse project : projectList) {
            if (ValidateUtil.isNotEmpty(project.getConId()) && !existingContracts.containsKey(project.getConId())) {
                return BaseResponse.fail("子项目关联合同不存在或不属于当前多项目合同");
            }
        }
        String sourceFileRelationId = sysFileService.fixFileRelationId(multi.getId());
        List<ConContractMultiProject> projectUpdates = new ArrayList<>();
        for (ConContractMultiProjectResponse project : projectList) {
            ConContract existing = existingContracts.get(project.getConId());
            ConContractRequest contractRequest = new ConContractRequest();
            contractRequest.setConId(project.getConId());
            contractRequest.setSkipApproval(true);
            contractRequest.setSkipCodeCheck(Boolean.TRUE);
            contractRequest.setCcmId(multi.getId());
            contractRequest.setConCreateTime(multi.getCreateTime());
            contractRequest.setConCreateUserId(multi.getCreateUid());
            contractRequest.setConCreateUserName(multi.getCreateUname());
            contractRequest.setConApprovalState(ApprovalStatusEnum.Already_Over.getIndex());
            contractRequest.setConApprovalTime(multi.getStateTime());
            contractRequest.setConName(multi.getName());
            contractRequest.setConCode(multi.getCode());
            contractRequest.setConMoneyTotal(project.getConAmount());
            contractRequest.setConPricingMethod(pricingMethod);
            contractRequest.setConOtherValuation("其他".equals(pricingMethod) ? multi.getConOtherValuation() : null);
            contractRequest.setConSectionId(multi.getConSectionId());
            contractRequest.setConBasis(multi.getConBasis());
            contractRequest.setConStartDate(multi.getConStartDate());
            contractRequest.setConAcPayRate(multi.getConAcPayRate());
            contractRequest.setConAcMoney(multi.getConAcMoney());
            contractRequest.setConSup(multi.getConSup());
            contractRequest.setConMasterConId(multi.getConMasterConId());
            contractRequest.setConSupIsChangeMoney(multi.getConSupIsChangeMoney());
            contractRequest.setConSupChangeMonType(multi.getConSupChangeMonType());
            contractRequest.setConSupChangeMoney(multi.getConSupChangeMoney());
            contractRequest.setConSupIsChangeDuration(multi.getConSupIsChangeDuration());
            contractRequest.setConSupChangeDurType(multi.getConSupChangeDurType());
            contractRequest.setConSupChangeDuration(multi.getConSupChangeDuration());
            contractRequest.setConConsortium(multi.getConConsortium());
            contractRequest.setConMultiSubject(multi.getConMultiSubject());
            contractRequest.setConPartyB1(multi.getConPartyB1());
            contractRequest.setConPartyB2(multi.getConPartyB2());
            contractRequest.setConPartyB3(multi.getConPartyB3());
            contractRequest.setConPartyB4(multi.getConPartyB4());
            contractRequest.setConPartyCId(multi.getConPartyCId());
            contractRequest.setConPartyDId(multi.getConPartyDId());
            contractRequest.setHtjkzffs(multi.getHtjkzffs());
            contractRequest.setConjgl(multi.getConjgl());
            contractRequest.setConContent(multi.getConContent());
            contractRequest.setConDate(multi.getSigningDate());
            contractRequest.setConPartyAId(multi.getClientUnitId());
            contractRequest.setConPartyBId(multi.getSupervisionUnitId());
            contractRequest.setConPartyBLegal(multi.getSupervisionSignatory());
            contractRequest.setConPartyBAgent(multi.getSupervisionAgent());
            contractRequest.setConPartyBAgentTel(multi.getSupervisionAgentTel());
            contractRequest.setConCaBank(multi.getDepositBank());
            contractRequest.setConCa(multi.getDepositAccount());
            contractRequest.setConPayCategory(multi.getPaymentType());
            contractRequest.setConType(multi.getContractType());
            contractRequest.setConCategory(multi.getConCategory());
            contractRequest.setConYear(multi.getContractYear());
            contractRequest.setConBiddingType(multi.getBiddingType());
            contractRequest.setConCurrency(multi.getContractCurrency());
            contractRequest.setConOpenBidding(multi.getBiddingPublic());
            contractRequest.setConExecution(multi.getContractExecution());
            contractRequest.setConState(multi.getContractStatus());
            contractRequest.setConTerm(multi.getProjectLeader());
            contractRequest.setConIsHistory(Boolean.TRUE.equals(multi.getContractHistory()) ? 1 : 0);
            contractRequest.setConPlanStartDate(multi.getPlanStartTime());
            contractRequest.setConPlanEndDate(multi.getPlanEndTime());
            contractRequest.setConTotalDuration(project.getDuration());
            contractRequest.setConProjectId(project.getPId());
            contractRequest.setConScan(existing == null ? GenerateUUID.getUUID() : existing.getConScan());
            List<ConContractPayPlanModel> payPlans = fundingList.stream().map(funding -> {
                ConContractPayPlanModel model = new ConContractPayPlanModel();
                model.setCppId(GenerateUUID.getUUID());
                model.setCppCode(funding.getFundingNo());
                model.setCppMoney(funding.getAmount());
                model.setCppSort(funding.getOrderNo());
                return model;
            }).collect(Collectors.toList());
            contractRequest.setPayPlanModels(payPlans);
            contractRequest.settId(template.gettId());
            contractRequest.setFileTitle(template.gettName());
            contractRequest.setFileCode(template.gettCode());
            contractRequest.settCode(template.gettCode());
            BaseResponse response = conContractService.createOrUpdateContract(contractRequest);
            if (response == null || !Boolean.TRUE.equals(response.getSuccess())
                    || !(response.getData() instanceof String) || ValidateUtil.isEmpty((String) response.getData())) {
                throw new IllegalStateException("江南大学多项目合同同步失败");
            }
            String conId = (String) response.getData();
            if (existing == null) {
                ConContractMultiProject update = new ConContractMultiProject();
                BeanUtils.copyProperties(project, update);
                update.setConId(conId);
                projectUpdates.add(update);
            }
            String basisFile = copyExtendedContractFile(multi.getConBasisFile(),
                    existing == null ? null : existing.getConBasisFile(), conId);
            String startsTo = copyExtendedContractFile(multi.getConStartsTo(),
                    existing == null ? null : existing.getConStartsTo(), conId);
            String scan = copyExtendedContractFile(multi.getContractFile(),
                    existing == null ? null : existing.getConScan(), conId);
            String targetFileRelationId = sysFileService.fixFileRelationId(conId);
            if (ValidateUtil.isEmpty(targetFileRelationId)) {
                throw new IllegalStateException("目标合同通用附件关系不存在");
            }
            sysFileAnnexService.remove(Wrappers.<SysFileannex>lambdaQuery()
                    .eq(SysFileannex::getFileEntityId, conId)
                    .eq(SysFileannex::getFileRelationId, targetFileRelationId));
            List<String> sourceRelations = Arrays.asList(sourceFileRelationId, multi.getContractMainFile()).stream()
                    .filter(ValidateUtil::isNotEmpty).distinct().collect(Collectors.toList());
            if (!sourceRelations.isEmpty()) {
                BaseResponse fileResult = sysFileAnnexService.copyFileAnnex2NewRelation(sourceRelations,
                        Collections.nCopies(sourceRelations.size(), targetFileRelationId), conId, true);
                if (fileResult == null || !Boolean.TRUE.equals(fileResult.getSuccess())) {
                    throw new IllegalStateException("同步合同通用附件失败");
                }
            }
            boolean updated = conContractService.update(Wrappers.<ConContract>lambdaUpdate()
                    .eq(ConContract::getConId, conId)
                    .set(ConContract::getConSectionId, multi.getConSectionId())
                    .set(ConContract::getConBasis, multi.getConBasis())
                    .set(ConContract::getConPricingMethod, pricingMethod)
                    .set(ConContract::getConOtherValuation,
                            "其他".equals(pricingMethod) ? multi.getConOtherValuation() : null)
                    .set(ConContract::getConStartDate, multi.getConStartDate())
                    .set(ConContract::getConAcPayRate, multi.getConAcPayRate())
                    .set(ConContract::getConAcMoney, multi.getConAcMoney())
                    .set(ConContract::getConSup, multi.getConSup())
                    .set(ConContract::getConMasterConId, multi.getConMasterConId())
                    .set(ConContract::getConSupIsChangeMoney, multi.getConSupIsChangeMoney())
                    .set(ConContract::getConSupChangeMonType, multi.getConSupChangeMonType())
                    .set(ConContract::getConSupChangeMoney, multi.getConSupChangeMoney())
                    .set(ConContract::getConSupIsChangeDuration, multi.getConSupIsChangeDuration())
                    .set(ConContract::getConSupChangeDurType, multi.getConSupChangeDurType())
                    .set(ConContract::getConSupChangeDuration, multi.getConSupChangeDuration())
                    .set(ConContract::getConConsortium, multi.getConConsortium())
                    .set(ConContract::getConMultiSubject, multi.getConMultiSubject())
                    .set(ConContract::getConPartyB1, multi.getConPartyB1())
                    .set(ConContract::getConPartyB2, multi.getConPartyB2())
                    .set(ConContract::getConPartyB3, multi.getConPartyB3())
                    .set(ConContract::getConPartyB4, multi.getConPartyB4())
                    .set(ConContract::getConPartyCId, multi.getConPartyCId())
                    .set(ConContract::getConPartyDId, multi.getConPartyDId())
                    .set(ConContract::getHtjkzffs, multi.getHtjkzffs())
                    .set(ConContract::getConjgl, multi.getConjgl())
                    .set(ConContract::getConContent, multi.getConContent())
                    .set(ConContract::getConBasisFile, basisFile)
                    .set(ConContract::getConStartsTo, startsTo)
                    .set(ConContract::getConScan, scan));
            if (!updated) {
                throw new IllegalStateException("同步合同扩展字段失败");
            }
        }
        if (!projectUpdates.isEmpty() && !conContractMultiProjectService.updateBatchById(projectUpdates)) {
            throw new IllegalStateException("回写子项目合同关联失败");
        }
        return BaseResponse.ok();
    }

    /**
     * 替换目标合同的扩展附件记录，每个合同使用独立的附件关联ID。
     *
     * @param sourceRelationId 多项目合同的源附件关联ID
     * @param oldRelationId    目标合同原附件关联ID
     * @param conId            目标合同ID
     * @return 新的附件关联ID，源关联ID为空时返回null
     */
    private String copyExtendedContractFile(String sourceRelationId, String oldRelationId, String conId) {
        if (ValidateUtil.isNotEmpty(oldRelationId)) {
            sysFileAnnexService.remove(Wrappers.<SysFileannex>lambdaQuery()
                    .eq(SysFileannex::getFileEntityId, conId)
                    .eq(SysFileannex::getFileRelationId, oldRelationId));
        }
        if (ValidateUtil.isEmpty(sourceRelationId)) {
            return null;
        }
        String relationId = GenerateUUID.getUUID();
        BaseResponse result = sysFileAnnexService.copyFileAnnex2NewRelation(
                Collections.singletonList(sourceRelationId), Collections.singletonList(relationId), conId, true);
        if (result == null || !Boolean.TRUE.equals(result.getSuccess())) {
            throw new IllegalStateException("同步合同扩展附件失败");
        }
        return relationId;
    }

    /**
     * 审批结束后同步到合同表
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncToContract(ConContractMultiRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String id = request.getId();
        ConContractMulti multi = this.getById(id);
        if (ValidateUtil.isNull(multi)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(multi.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        List<ConContractMultiProjectResponse> projectList = conContractMultiProjectService.getConPayMultiProjectByCcmId(id);
        if (ValidateUtil.isEmpty(projectList)) {
            return BaseResponse.fail("多项目合同未配置项目信息，无法同步至合同");
        }
        List<ConContractMultiProject> projectUpdateList = new ArrayList<>();
        List<ConContractMultiFunding> fundingList = conContractMultiFundingService.list(Wrappers.<ConContractMultiFunding>lambdaQuery().eq(ConContractMultiFunding::getCcmId, id));
        List<ConContract> existingContracts = conContractService.list(Wrappers.<ConContract>lambdaQuery().eq(ConContract::getCcmId, id));
        Map<String, ConContract> existingContractMap = existingContracts.stream()
                .collect(Collectors.toMap(ConContract::getConId, contract -> contract, (a, b) -> a));
        List<SysDictionary> code = sysDictionaryService.getSysDictionaryByCode("JPMsoft_HTLX");
        Map<String, String> codeMap = code.stream().collect(Collectors.toMap(SysDictionary::getdCode, SysDictionary::getdName, (a, b) -> a));

        // 查找当前表单的表单附件
        // List<String> entityIds = existingContracts.stream().map(ConContract::getConId).collect(Collectors.toList());
        // entityIds.add(id);
        // List<SysFileannex> fileannexes = sysFileAnnexService.list(Wrappers.<SysFileannex>lambdaQuery()
        //         .in(SysFileannex::getFileEntityId, entityIds)
        //         .eq(SysFileannex::getUploadStatus, 1)
        //         .and(q -> q.isNull(SysFileannex::getColumnName).or().eq(SysFileannex::getColumnName, ""))
        //         .and(q -> q.isNull(SysFileannex::getColumnCode).or().eq(SysFileannex::getColumnCode, ""))
        // );
        // Map<String, List<SysFileannex>> fileMap = fileannexes.stream().collect(Collectors.groupingBy(SysFileannex::getFileEntityId));
        String fileRelationId = sysFileService.fixFileRelationId(id);

        for (ConContractMultiProjectResponse multiProject : projectList) {
            ConContractRequest contract = new ConContractRequest();
            String existingConId = multiProject.getConId();
            String conFileRelationId = "";
            if (ValidateUtil.isNotEmpty(existingConId)) {
                ConContract existingContract = existingContractMap.get(existingConId);
                contract.setConId(existingConId);
                conFileRelationId = sysFileService.fixFileRelationId(existingConId);
                List<String> ids = new ArrayList<>();
                ids.add(existingContract.getConScan());
                // ids.add(existingContract.getConMainScan());
                if (ValidateUtil.isNotEmpty(conFileRelationId)) {
                    ids.add(conFileRelationId);
                }
                // 维护时先删除已经同步过去的文件
                sysFileAnnexService.remove(Wrappers.<SysFileannex>lambdaQuery().in(SysFileannex::getFileRelationId, ids));
            }
            // 跳过合同创建接口的审批赋值
            contract.setSkipApproval(true);
            // 跳过合同创建接口的编码重复校验
            contract.setSkipCodeCheck(Boolean.TRUE);
            // 合同关联多项目合同id
            contract.setCcmId(multi.getId());
            contract.setConCreateTime(multi.getCreateTime());
            contract.setConCreateUserId(multi.getCreateUid());
            contract.setConCreateUserName(multi.getCreateUname());
            contract.setConApprovalState(ApprovalStatusEnum.Already_Over.getIndex());
            contract.setConApprovalTime(multi.getStateTime());
            // 合同名称
            contract.setConName(multiProject.getPName() + codeMap.get(multi.getContractType()));
            // 合同编码
            contract.setConCode(multi.getCode());
            // 合同金额
            contract.setConPricingMethod("金额");
            contract.setConMoneyTotal(multiProject.getConAmount());
            // 合同签订日期
            contract.setConDate(multi.getSigningDate());
            // 委托方
            contract.setConPartyAId(multi.getClientUnitId());
            // 委托方签约代表
            // contract.setConPartyALegal(multi.getClientSignatory());
            // 委托方经办人
            // contract.setConPartyAAgent(multi.getClientAgent());
            // 委托方经办人电话
            // contract.setConPartyAAgentTel(multi.getClientAgentTel());
            // 委托方地址
            // contract.setConPartyAAddress(multi.getClientAddress());

            // 监理方
            contract.setConPartyBId(multi.getSupervisionUnitId());
            // 监理方签约代表
            contract.setConPartyBLegal(multi.getSupervisionSignatory());
            // 监理方经办人
            contract.setConPartyBAgent(multi.getSupervisionAgent());
            // 监理方经办人电话
            contract.setConPartyBAgentTel(multi.getSupervisionAgentTel());
            // 监理方地址
            // contract.setConPartyBAddress(multi.getSupervisionAddress());

            // 开户行
            contract.setConCaBank(multi.getDepositBank());
            // 开户行账号
            contract.setConCa(multi.getDepositAccount());
            // 款项类别
            contract.setConPayCategory(multi.getPaymentType());

            // 合同类型
            contract.setConType(multi.getContractType());
            contract.setConCategory(multi.getConCategory());

            // 合同签订年份
            contract.setConYear(multi.getContractYear());
            // 招标方式
            contract.setConBiddingType(multi.getBiddingType());
            // 合同币种
            contract.setConCurrency(multi.getContractCurrency());
            // 公开招投标
            contract.setConOpenBidding(multi.getBiddingPublic());
            // 合同执行情况
            contract.setConExecution(multi.getContractExecution());
            // 合同状态
            contract.setConState(multi.getContractStatus());
            // 项目负责人id
            contract.setConTerm(multi.getProjectLeader());
            // 是否历史合同
            contract.setConIsHistory(Boolean.TRUE.equals(multi.getContractHistory()) ? 1 : 0);
            // 计划开始时间
            contract.setConPlanStartDate(multi.getPlanStartTime());
            // 计划结束时间
            contract.setConPlanEndDate(multi.getPlanEndTime());

            // 合同金额
            contract.setConTotalDuration(multiProject.getDuration());
            // 项目id
            contract.setConProjectId(multiProject.getPId());

            // 签字盖章合同扫描件
            String scan = GenerateUUID.getUUID();
            contract.setConScan(scan);
            // 合同事由及主要内容
            // String main = GenerateUUID.getUUID();
            // contract.setConMainScan(main);

            // 项目经费号子表
            List<ConContractPayPlanModel> models = fundingList.stream().map(funding -> {
                ConContractPayPlanModel model = new ConContractPayPlanModel();
                model.setCppId(GenerateUUID.getUUID());
                model.setCppCode(funding.getFundingNo());
                model.setCppMoney(funding.getAmount());
                model.setCppSort(funding.getOrderNo());
                return model;
            }).collect(Collectors.toList());
            contract.setPayPlanModels(models);

            // 流程表字段补充
            FileTemplateTree fileTemplate = sysFileService.getFileTemplateByTCode(request.gettCode());
            contract.settId(fileTemplate.gettId());
            contract.setFileTitle(fileTemplate.gettName());
            contract.setFileCode(fileTemplate.gettCode());
            contract.settCode(fileTemplate.gettCode());

            BaseResponse response = conContractService.createOrUpdateContract(contract);
            if (response.getSuccess() && ValidateUtil.isNotNull(response.getData())) {
                String conId = (String) response.getData();
                if (ValidateUtil.isEmpty(existingConId)) {
                    ConContractMultiProject entity = new ConContractMultiProject();
                    BeanUtils.copyProperties(multiProject, entity);
                    entity.setConId(conId);
                    projectUpdateList.add(entity);
                }
                List<String> oldIds = new ArrayList<>();
                List<String> newIds = new ArrayList<>();
                oldIds.add(multi.getContractFile());
                // oldIds.add(multi.getContractMainFile());
                newIds.add(scan);
                // newIds.add(main);
                if (ValidateUtil.isEmpty(conFileRelationId)) {
                    conFileRelationId = sysFileService.fixFileRelationId(conId);
                }
                if (ValidateUtil.isNotEmpty(conFileRelationId) && ValidateUtil.isNotEmpty(fileRelationId)) {
                    oldIds.add(multi.getContractMainFile());
                    newIds.add(conFileRelationId);
                    oldIds.add(fileRelationId);
                    newIds.add(conFileRelationId);
                }
                sysFileAnnexService.copyFileAnnex2NewRelation(oldIds, newIds, conId, true);
            } else {
                throw new RuntimeException("多项目合同同步失败");
            }
        }
        if (ValidateUtil.isNotEmpty(projectUpdateList)) {
            conContractMultiProjectService.updateBatchById(projectUpdateList);
        }
        return BaseResponse.ok();
    }

    /**
     * 获取多项目合同分页列表
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse getContractMultiPage(ConContractMultiRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<ConContractMultiRequest> map = new QueryMap<>(request);
        map.put("name").put("clientUnitName").put("supervisionUnitName").put("state")
                .put("conCategory").put("createUname");
        Page<ConContractMultiResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractMultiResponse> list = baseMapper.getContractMultiPage(page, map.getQuery());
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取多项目合同详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getContractMultiById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConContractMulti multi = this.getById(id);
        if (ValidateUtil.isNull(multi)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        ConContractMultiResponse response = new ConContractMultiResponse();
        BeanUtils.copyProperties(multi, response);
        List<ConContractMultiProjectResponse> projectList = conContractMultiProjectService.getConPayMultiProjectByCcmId(id);
        response.setConContractMultiProject(projectList);
        List<ConContractMultiFunding> fundingList = conContractMultiFundingService.list(Wrappers.<ConContractMultiFunding>lambdaQuery().eq(ConContractMultiFunding::getCcmId, id).orderByAsc(ConContractMultiFunding::getOrderNo));
        response.setConContractMultiFunding(BeanUtils.copyList(fundingList, ConContractMultiFundingResponse.class));
        return BaseResponse.ok(response);
    }
}
