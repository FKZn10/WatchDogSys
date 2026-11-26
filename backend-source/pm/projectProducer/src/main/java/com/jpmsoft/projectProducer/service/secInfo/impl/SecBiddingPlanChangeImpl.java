package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanChangeRequest;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingProjectRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoBudgetDeclareDtlDto;
import com.jpmsoft.common.model.request.SecInfo.SecProjectRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectRemarkDTO;
import com.jpmsoft.common.model.response.SecInfo.SecBiddingPlanChangeRes;
import com.jpmsoft.common.model.response.SecInfo.SecInfoBudgetDeclareDtlChangeRes;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectChangeResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentBudgetBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBiddingPlanChangeMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SubjectNodeDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdown;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdown;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.*;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownService;
import com.jpmsoft.projectProducer.service.investment.IInvestmentEstimateBreakdownService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SecBiddingPlanChangeImpl extends ServiceImpl<SecBiddingPlanChangeMapper, SecBiddingPlanChange> implements ISecBiddingPlanChangeService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecInfoChangeService secInfoChangeService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISecInfoService secInfoService;
    @Resource
    private ISecBiddingPlanService secBiddingPlanService;
    @Resource
    private ISecBiddingProjectService secBiddingProjectService;
    @Resource
    private ISecProjectService secProjectService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecInfoBudgetDeclareDtlService secInfoBudgetDeclareDtlService;
    @Resource
    private ISecInfoBudgetDeclareDtlChangeService secInfoBudgetDeclareDtlChangeService;
    @Resource
    private ISecInfoSubjectService secInfoSubjectService;
    @Resource
    private ISecInfoSubjectChangeService secInfoSubjectChangeService;
    @Resource
    private ISecInfoSubjectRemarkService secInfoSubjectRemarkService;
    @Resource
    private InvestmentEstimateBreakdownDetailsMapper investmentEstimateBreakdownDetailsMapper;
    @Resource
    private IInvestmentEstimateBreakdownService investmentEstimateBreakdownService;
    @Resource
    private IInvestmentBudgetBreakdownService investmentBudgetBreakdownService;
    @Resource
    private InvestmentBudgetBreakdownDetailsMapper investmentBudgetBreakdownDetailsMapper;
    @Resource
    private ISecInfoSubjectExtraChangeService secInfoSubjectExtraChangeService;
    @Resource
    private ISecInfoSubjectExtraService secInfoSubjectExtraService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecBiddingPlanChange(SecBiddingPlanChangeRequest request, LogUserInfoVo user) {
        SecBiddingPlanChange sec = new SecBiddingPlanChange();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getBpId())) return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getBpcId())) {
           if (haveNoClosePlan(request.getBpId())) return BaseResponse.fail(ResMsg.secBidPlanChangeExist);
            sec.setBpCreateTime(new Date());
            sec.setBpcId(GenerateUUID.getUUID());
            sec.setState(ValidateUtil.isNull(request.getState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getBpcId(), request.gettId(), sec.getState() + "", sec.getBpCreateUid(), sec.getBpCreateUname()
                    , sec.getBpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBpApprovalFile()), sec.getBpcId());
            secInfoChangeService.createOrUpdateSecInfoChange(request.getSecInfos(), sec.getBpcId(), user, "create",request.getpId());
        } else {
            SecBiddingPlanChange entity = baseMapper.selectById(request.getBpcId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            /*if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);*/
            sec.setBpCreateTime(entity.getBpCreateTime());
            sec.setBpCreateUid(entity.getBpCreateUid());
            sec.setBpCreateUname(entity.getBpCreateUname());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getBpcId(), request.gettId(), sec.getState() + "", sec.getBpCreateUid(), sec.getBpCreateUname()
                    , sec.getBpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBpApprovalFile()),
                    Arrays.asList(request.getFileIds(), request.getBpApprovalFileIds()), sec.getBpcId());
            secInfoChangeService.createOrUpdateSecInfoChange(request.getSecInfos(), sec.getBpcId(), user, "edit",request.getpId());
        }
        secInfoSubjectRemarkService.createOrUpdateSecInfoSubjectRemark(request.getRemarkList(), sec.getBpcId());
        return BaseResponse.ok(sec.getBpcId());
    }

    @Override
    public BaseResponse getSecBiddingPlanChangeList(SecBiddingPlanChangeRequest request, String uId) {
        String authSql = null;
        if (ValidateUtil.isNotEmpty(request.getIsNew())) {
            // 创建人、流程处理人、抄送人、协同人
            authSql = sysUserService.getAuthSql("Sec_Bidding_Plan", uId, "sbp.Bp_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "sbp.Bp_Id");
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            authSql = authSql.replaceFirst("(?i)\\bwhere\\b", " left join Sec_Bidding_Plan sbp on sbpr.Bp_Id=sbp.Bp_Id left join Pre_Project fpro on fpro.P_Id=sbpr.P_ID where ");
        } else {
            authSql = sysUserService.getAuthSql(null, uId, "Sbp_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        }
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<SecBiddingPlanChangeRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("bpName").put("bpCreateUname")
                .put("state").put("pName").put("bpId");
        List<SecBiddingPlanChangeRes> datas = baseMapper.getSecBiddingPlanChangeList(params.getQuery());
        Integer total = baseMapper.getSecBiddingPlanChangeCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    /**
     * 获取招标计划变更详情
     *
     * @param bpcId
     * @return
     */
    @Override
    public BaseResponse getSecBiddingPlanChangeById(String bpcId) {
        SecBiddingPlanChangeRes data = baseMapper.getSecBiddingPlanChangeById(bpcId);
        if (ValidateUtil.isNull(data)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        List<SecInfoSubjectRemark> list = secInfoSubjectRemarkService.list(
                Wrappers.<SecInfoSubjectRemark>lambdaQuery().eq(SecInfoSubjectRemark::getMainId, bpcId));
        if (ValidateUtil.isNotEmpty(list)) {
            data.setRemarkList(BeanUtil.copyToList(list, SecInfoSubjectRemarkDTO.class));
        }

        List<SecInfoChangeRes> secInfoChangeList = secInfoChangeService.getSecInfoChangeList(bpcId);
        if (ValidateUtil.isNotEmpty(secInfoChangeList)) {
            List<String> sicIdList = secInfoChangeList.stream().map(SecInfoChangeRes::getSicId).collect(Collectors.toList());
            List<SecInfoBudgetDeclareDtlChangeRes> secInfoBudgetDeclareDtlChangeList = new ArrayList<>();
            List<SecInfoSubjectChangeResponse> subjectChangeResponses = new ArrayList<>();
            List<SecInfoSubjectChangeResponse> subjectExtraChangeResponses = getSecInfoSubjectExtraChangeList(bpcId, data.getBpPlanPhase());
            if (ValidateUtil.isNotEmpty(sicIdList)) {
                secInfoBudgetDeclareDtlChangeList = secInfoBudgetDeclareDtlChangeService.getSecInfoBudgetDeclareDtlChangeList(sicIdList);
                subjectChangeResponses = secInfoSubjectChangeService.getSecInfoSubjectChangeList(sicIdList);
            }
            for (SecInfoChangeRes secInfoChangeRes : secInfoChangeList) {
                List<SecInfoBudgetDeclareDtlChangeRes> collect = secInfoBudgetDeclareDtlChangeList.stream()
                        .filter(e -> e.getSicId().equals(secInfoChangeRes.getSicId()))
                        .collect(Collectors.toList());
                secInfoChangeRes.setSecInfoBudgetDeclareDtlChangeList(collect);

                List<SecInfoSubjectChangeResponse> subjectChangeResponseList = subjectChangeResponses.stream()
                        .filter(e -> e.getSicId().equals(secInfoChangeRes.getSicId()))
                        .collect(Collectors.toList());
                subjectChangeResponseList = appendSubjectExtraChanges(
                        subjectChangeResponseList, subjectExtraChangeResponses, secInfoChangeRes);
                secInfoChangeRes.setSecInfoSubjects(subjectChangeResponseList);
            }
            data.setSecInfos(secInfoChangeList);
        }

        List<SecBiddingPlanChange> changeList = this.list(
                Wrappers.<SecBiddingPlanChange>lambdaQuery()
                        .eq(SecBiddingPlanChange::getBpId, data.getBpId())
                        .orderByDesc(SecBiddingPlanChange::getBpCreateTime)
                        .orderByDesc(SecBiddingPlanChange::getBpcId)
                        .select(SecBiddingPlanChange::getBpcId,
                                SecBiddingPlanChange::getBpPlanPhase,
                                SecBiddingPlanChange::getBpId,
                                SecBiddingPlanChange::getReserveItems,
                                SecBiddingPlanChange::getEnterEstimate)
        );
        if (ValidateUtil.isEmpty(changeList) || ValidateUtil.isEmpty(secInfoChangeList)) {
            return BaseResponse.ok(data);
        }

        int index = -1;
        for (int i = 0; i < changeList.size(); i++) {
            if (Objects.equals(changeList.get(i).getBpcId(), bpcId)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return BaseResponse.ok(data);
        }

        Set<String> changedSecIds = secInfoChangeList.stream()
                .filter(item -> ("变更".equals(item.getSecChangeType()) || "关联估算科目".equals(item.getSecChangeType()) || "关联概算科目".equals(item.getSecChangeType())))
                .map(SecInfoChangeRes::getSecId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (changedSecIds.isEmpty()) {
            return BaseResponse.ok(data);
        }

        SecBiddingPlanChange change;
        List<SecInfoChangeRes> history;
        List<SecInfoSubjectChangeResponse> subjectChangeList;
        if (changeList.size() == 1) {
            change = changeList.get(index);
            List<SecInfoDto> secInfoDtos = secInfoService.getSecInfoBySecInfoIds(changedSecIds);
            if (ValidateUtil.isEmpty(secInfoDtos)) {
                return BaseResponse.ok(data);
            }

            Map<String, SecInfoDto> secInfoMap = secInfoDtos.stream()
                    .collect(Collectors.toMap(SecInfoDto::getSecId, item -> item, (a, b) -> a));
            history = secInfoChangeList.stream()
                    .filter(item -> changedSecIds.contains(item.getSecId()))
                    .map(item -> secInfoMap.get(item.getSecId()))
                    .filter(Objects::nonNull)
                    .map(item -> BeanUtil.copyProperties(item, SecInfoChangeRes.class))
                    .collect(Collectors.toList());

            subjectChangeList = BeanUtil.copyToList(secInfoSubjectService.getSecInfoSubjectsBySecIds(changedSecIds), SecInfoSubjectChangeResponse.class);
            Map<String, List<SecInfoSubjectChangeResponse>> subjectMap = subjectChangeList.stream()
                    .collect(Collectors.groupingBy(SecInfoSubjectChangeResponse::getSecId));
            history.forEach(item -> item.setSecInfoSubjects(subjectMap.get(item.getSecId())));
        } else {
            if (index >= changeList.size() - 1) {
                return BaseResponse.ok(data);
            }

            change = changeList.get(index + 1);
            String prevBpcId = change.getBpcId();
            List<SecInfoChangeRes> prevList = secInfoChangeService.getSecInfoChangeList(prevBpcId);
            if (ValidateUtil.isEmpty(prevList)) {
                return BaseResponse.ok(data);
            }

            history = prevList.stream()
                    .filter(item -> changedSecIds.contains(item.getSecId()))
                    .collect(Collectors.toList());

            List<String> sicIds = history.stream().map(SecInfoChangeRes::getSicId).collect(Collectors.toList());
            subjectChangeList = secInfoSubjectChangeService.getSecInfoSubjectChangeList(sicIds);
        }

        if (ValidateUtil.isNotEmpty(history)) {
            String bpPlanPhase = change.getBpPlanPhase();
            List<SecInfoSubjectChangeResponse> subjectExtraChangeList = getSecInfoSubjectExtraChangeList(change.getBpcId(), bpPlanPhase);

            SecBiddingProject secBiddingProject = secBiddingProjectService.getOne(
                    Wrappers.<SecBiddingProject>lambdaQuery()
                            .eq(SecBiddingProject::getBpId, change.getBpId())
                            .select(SecBiddingProject::getPId)
                            .last(" limit 1 ")
            );
            if (ValidateUtil.isNull(secBiddingProject) || ValidateUtil.isEmpty(secBiddingProject.getPId())) {
                populateHistorySecInfoSubjects(history, subjectChangeList, subjectExtraChangeList);
                data.setSecInfoChangeList(history);
                return BaseResponse.ok(data);
            }

            String pId = secBiddingProject.getPId();

            Map<String, SubjectNodeDTO> subjectNodeMap = new HashMap<>();
            if ("02".equals(bpPlanPhase) || "03".equals(bpPlanPhase)) {
                // 估算
                InvestmentEstimateBreakdown breakdown = investmentEstimateBreakdownService.getOne(
                        Wrappers.<InvestmentEstimateBreakdown>lambdaQuery()
                                .eq(InvestmentEstimateBreakdown::getProjectId, pId)
                                .ne(InvestmentEstimateBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
                                .orderByDesc(InvestmentEstimateBreakdown::getCreateTime)
                                .select(InvestmentEstimateBreakdown::getId)
                                .last(" limit 1 ")
                );
                if (ValidateUtil.isNotNull(breakdown)) {
                    List<InvestmentEstimateBreakdownDetails> breakdownDetails =
                            investmentEstimateBreakdownDetailsMapper.selectList(
                                    Wrappers.<InvestmentEstimateBreakdownDetails>lambdaQuery()
                                            .eq(InvestmentEstimateBreakdownDetails::getIsbId, breakdown.getId())
                            );
                    subjectNodeMap = buildEstimateSubjectNodeMap(breakdownDetails);
                }
            } else if ("04".equals(bpPlanPhase)) {
                // 概算
                InvestmentBudgetBreakdown breakdown = investmentBudgetBreakdownService.getOne(
                        Wrappers.<InvestmentBudgetBreakdown>lambdaQuery()
                                .eq(InvestmentBudgetBreakdown::getProjectId, pId)
                                .ne(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
                                .orderByDesc(InvestmentBudgetBreakdown::getCreateTime)
                                .select(InvestmentBudgetBreakdown::getId)
                                .last(" limit 1 ")
                );
                if (ValidateUtil.isNotNull(breakdown)) {
                    List<InvestmentBudgetBreakdownDetails> breakdownDetails =
                            investmentBudgetBreakdownDetailsMapper.selectList(
                                    Wrappers.<InvestmentBudgetBreakdownDetails>lambdaQuery()
                                            .eq(InvestmentBudgetBreakdownDetails::getIsbId, breakdown.getId())
                            );
                    subjectNodeMap = buildBudgetSubjectNodeMap(breakdownDetails);
                }
            } else if ("1".equals(change.getReserveItems()) || "1".equals(change.getEnterEstimate())) {
                // 储备和无需关联估算，没有估算概算
                populateHistorySecInfoSubjects(history, subjectChangeList, subjectExtraChangeList);
                data.setSecInfoChangeList(history);
                return BaseResponse.ok(data);
            } else {
                return BaseResponse.ok(data);
            }

            Map<String, SubjectNodeDTO> finalSubjectNodeMap = subjectNodeMap;
            populateHistorySecInfoSubjects(history, subjectChangeList, subjectExtraChangeList);
            history.forEach(item -> {
                String summary = buildSecInfoSubjectSummary(item.getSecInfoSubjects(), finalSubjectNodeMap);
                item.setSecSubjectName1(summary);
            });

            data.setSecInfoChangeList(history);
        }

        return BaseResponse.ok(data);
    }

    private void populateHistorySecInfoSubjects(List<SecInfoChangeRes> history,
                                                List<SecInfoSubjectChangeResponse> subjectChangeList,
                                                List<SecInfoSubjectChangeResponse> subjectExtraChangeList) {
        if (ValidateUtil.isEmpty(history)) {
            return;
        }
        List<SecInfoSubjectChangeResponse> baseSubjectChanges = ValidateUtil.isEmpty(subjectChangeList)
                ? Collections.emptyList()
                : subjectChangeList;
        history.forEach(item -> {
            List<SecInfoSubjectChangeResponse> subjectChanges = baseSubjectChanges.stream()
                    .filter(e -> Objects.equals(e.getSicId(), item.getSicId())
                            || (ValidateUtil.isEmpty(item.getSicId()) && Objects.equals(e.getSecId(), item.getSecId())))
                    .collect(Collectors.toList());
            subjectChanges = appendSubjectExtraChanges(subjectChanges, subjectExtraChangeList, item);
            item.setSecInfoSubjects(subjectChanges);
        });
    }

    private List<SecInfoSubjectChangeResponse> getSecInfoSubjectExtraChangeList(String bpcId, String bpPlanPhase) {
        if (ValidateUtil.isEmpty(bpcId)) {
            return Collections.emptyList();
        }
        List<SecInfoSubjectExtraChange> extraChanges = secInfoSubjectExtraChangeService.list(
                Wrappers.<SecInfoSubjectExtraChange>lambdaQuery()
                        .eq(SecInfoSubjectExtraChange::getBpcId, bpcId)
                        .orderByAsc(SecInfoSubjectExtraChange::getSort)
                        .orderByAsc(SecInfoSubjectExtraChange::getId)
        );
        if (ValidateUtil.isEmpty(extraChanges)) {
            return Collections.emptyList();
        }
        return extraChanges.stream()
                .map(extra -> buildSubjectExtraChangeResponse(extra, bpPlanPhase))
                .collect(Collectors.toList());
    }

    private SecInfoSubjectChangeResponse buildSubjectExtraChangeResponse(SecInfoSubjectExtraChange extra, String bpPlanPhase) {
        SecInfoSubjectChangeResponse response = new SecInfoSubjectChangeResponse();
        response.setId(extra.getParentId());
        response.setSicId(extra.getSicId());
        response.setSecId(extra.getSecId());
        response.setSubMoney(extra.getSeMoney());
        if ("04".equals(bpPlanPhase)) {
            response.setSubIbbId(extra.getId());
        } else {
            response.setSubIebId(extra.getId());
        }
        response.setSubName(extra.getSeName());
        response.setLevelCode(extra.getLevelCode());
        return response;
    }

    private List<SecInfoSubjectChangeResponse> appendSubjectExtraChanges(List<SecInfoSubjectChangeResponse> subjectChanges,
                                                                         List<SecInfoSubjectChangeResponse> subjectExtraChangeList,
                                                                         SecInfoChangeRes item) {
        if (ValidateUtil.isEmpty(subjectExtraChangeList) || ValidateUtil.isNull(item)) {
            return subjectChanges;
        }
        List<SecInfoSubjectChangeResponse> extraChanges = subjectExtraChangeList.stream()
                .filter(e -> Objects.equals(e.getSicId(), item.getSicId()))
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(extraChanges)) {
            return subjectChanges;
        }
        List<SecInfoSubjectChangeResponse> result = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(subjectChanges)) {
            result.addAll(subjectChanges);
        }
        result.addAll(extraChanges);
        return result;
    }

    /**
     * 构建一个估算科目节点的映射表，以ID为键，科目节点DTO为值
     *
     * @param list 包含投资估算明细的列表，该列表用于构建科目节点的映射表
     * @return 一个映射表，其中键为投资估算明细的ID，值为与之对应的科目节点DTO
     */
    private Map<String, SubjectNodeDTO> buildEstimateSubjectNodeMap(List<InvestmentEstimateBreakdownDetails> list) {
        if (ValidateUtil.isEmpty(list)) {
            return new LinkedHashMap<>();
        }
        return list.stream().collect(Collectors.toMap(
                InvestmentEstimateBreakdownDetails::getId,
                e -> {
                    SubjectNodeDTO dto = new SubjectNodeDTO();
                    dto.setId(e.getId());
                    dto.setParentId(e.getParentId());
                    dto.setName(e.getIsbdFeeTypeName());
                    dto.setLevelCode(e.getLevelCode());
                    return dto;
                },
                (a, b) -> a,
                LinkedHashMap::new
        ));
    }

    /**
     * 构建一个概算科目节点的映射表，以ID为键，科目节点DTO为值
     *
     * @param list 包含概算明细的列表，该列表用于构建科目节点的映射表
     * @return 一个映射表，其中键为概算明细的ID，值为与之对应的科目节点DTO
     */
    private Map<String, SubjectNodeDTO> buildBudgetSubjectNodeMap(List<InvestmentBudgetBreakdownDetails> list) {
        if (ValidateUtil.isEmpty(list)) {
            return new LinkedHashMap<>();
        }
        return list.stream().collect(Collectors.toMap(
                InvestmentBudgetBreakdownDetails::getId,
                e -> {
                    SubjectNodeDTO dto = new SubjectNodeDTO();
                    dto.setId(e.getId());
                    dto.setParentId(e.getParentId());
                    dto.setName(e.getIsbdFeeTypeName());
                    dto.setLevelCode(e.getLevelCode());
                    return dto;
                },
                (a, b) -> a,
                LinkedHashMap::new
        ));
    }

    /**
     * 构建安全信息主题摘要，基于提供的主题变更以及主题节点映射生成分类组合的摘要信息
     *
     * @param subjectChanges 主题变更的列表,包含需要处理的主题变更数据
     * @param subjectNodeMap 主题节点映射,其中键为主题ID,值为对应的主题节点对象
     * @return 构建完毕的主题摘要字符串,如果输入数据为空、无效或无法生成摘要则返回空字符串
     */
    private String buildSecInfoSubjectSummary(List<SecInfoSubjectChangeResponse> subjectChanges,
                                              Map<String, SubjectNodeDTO> subjectNodeMap) {
        if (ValidateUtil.isEmpty(subjectChanges) || ValidateUtil.isEmpty(subjectNodeMap)) {
            return "";
        }

        // 使用LinkedHashMap保持插入顺序,存储一级节点和其对应的二级节点映射
        LinkedHashMap<String, LinkedHashMap<String, SubjectNodeDTO>> resultMap = new LinkedHashMap<>();

        // 遍历每一个主题变更记录
        for (SecInfoSubjectChangeResponse subject : subjectChanges) {
            // 获取主题ID,优先使用SubIebId(估算ID),如果为空则使用SubIbbId(概算ID)
            String subjectId = ValidateUtil.isNotEmpty(subject.getSubIebId()) ? subject.getSubIebId() : subject.getSubIbbId();
            if (ValidateUtil.isEmpty(subjectId)) {
                continue;
            }

            // 从节点映射中获取当前节点
            SubjectNodeDTO current = subjectNodeMap.get(subjectId);
            if (current == null) {
                continue;
            }

            // 构建当前节点到根节点的完整路径
            List<SubjectNodeDTO> path = buildAncestorPath(current, subjectNodeMap);
            if (ValidateUtil.isEmpty(path)) {
                continue;
            }

            // 初始化一级节点和二级节点
            SubjectNodeDTO level1Node = null;
            SubjectNodeDTO level2Node = null;

            // 在路径中查找一级节点和二级节点
            for (SubjectNodeDTO node : path) {
                if (isLevel1Node(node, subjectNodeMap)) {
                    level1Node = node;
                } else if (isLevel2Node(node, subjectNodeMap)) {
                    level2Node = node;
                }
            }

            // 如果没有找到一级节点,跳过当前主题
            if (level1Node == null) {
                continue;
            }

            // 将一级节点添加到结果映射中,如果不存在则创建新的二级节点映射
            resultMap.putIfAbsent(level1Node.getId(), new LinkedHashMap<>());
            // 如果存在二级节点,将其添加到对应一级节点的二级节点映射中
            if (level2Node != null) {
                resultMap.get(level1Node.getId()).putIfAbsent(level2Node.getId(), level2Node);
            }
        }

        // 如果结果映射为空,返回空字符串
        if (resultMap.isEmpty()) {
            return "";
        }

        // 构建最终的摘要字符串
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, LinkedHashMap<String, SubjectNodeDTO>> entry : resultMap.entrySet()) {
            // 获取一级节点信息
            SubjectNodeDTO level1Node = subjectNodeMap.get(entry.getKey());
            if (level1Node == null) {
                continue;
            }

            // 添加格式化后的一级节点信息
            sb.append(formatLevel1(level1Node));

            // 获取该一级节点下的所有二级节点
            LinkedHashMap<String, SubjectNodeDTO> level2Map = entry.getValue();
            for (SubjectNodeDTO level2Node : level2Map.values()) {
                // 添加换行符和格式化后的二级节点信息
                sb.append("<br/>").append(formatLevel2(level2Node));
            }

            // 在每个一级节点组后添加换行符
            sb.append("<br/>");
        }

        // 移除最后多余的换行符(5个字符对应"<br/>")
        if (sb.length() >= 5) {
            sb.setLength(sb.length() - 5);
        }
        return sb.toString();
    }

    /**
     * 构建指定节点的祖先路径，包括当前节点本身，递归查找其父节点直到根节点为止。
     *
     * @param current 当前节点，表示路径的起点。
     * @param subjectNodeMap 一个以节点ID为键、节点对象为值的映射，用于查找父节点。
     * @return 包含从根节点到当前节点的路径列表，列表按照层级顺序排序（根节点在首，当前节点在尾）。
     */
    private List<SubjectNodeDTO> buildAncestorPath(SubjectNodeDTO current,
                                                   Map<String, SubjectNodeDTO> subjectNodeMap) {
        LinkedList<SubjectNodeDTO> path = new LinkedList<>();
        SubjectNodeDTO node = current;
        Set<String> visited = new HashSet<>();

        while (node != null && ValidateUtil.isNotEmpty(node.getId()) && !visited.contains(node.getId())) {
            path.addFirst(node);
            visited.add(node.getId());

            if (ValidateUtil.isEmpty(node.getParentId())) {
                break;
            }
            node = subjectNodeMap.get(node.getParentId());
        }
        return path;
    }

    /**
     * 判断给定的主题节点是否为一级节点
     *
     * @param node           主题节点对象
     * @param subjectNodeMap 主题节点映射，其中键为主题ID，值为对应的主题节点对象
     * @return 如果节点为一级节点则返回true，否则返回false
     */
    private boolean isLevel1Node(SubjectNodeDTO node, Map<String, SubjectNodeDTO> subjectNodeMap) {
        return node != null
                && ValidateUtil.isNotEmpty(node.getLevelCode())
                && ValidateUtil.isEmpty(node.getParentId())
                && !"0".equals(node.getLevelCode());
    }

    /**
     * 判断给定的主题节点是否为二级节点
     *
     * @param node           主题节点对象
     * @param subjectNodeMap 主题节点映射，其中键为主题ID，值为对应的主题节点对象
     * @return 如果节点为二级节点则返回true，否则返回false
     */
    private boolean isLevel2Node(SubjectNodeDTO node, Map<String, SubjectNodeDTO> subjectNodeMap) {
        if (node == null || ValidateUtil.isEmpty(node.getParentId())) {
            return false;
        }
        SubjectNodeDTO parent = subjectNodeMap.get(node.getParentId());
        return isLevel1Node(parent, subjectNodeMap);
    }

    /**
     * 格式化一级节点信息，返回格式为 "一级节点代码、一级节点名称"
     *
     * @param node 一级节点对象
     * @return 格式化后的字符串，如果节点为空则返回空字符串
     */
    private String formatLevel1(SubjectNodeDTO node) {
        return node.getLevelCode() + "、" + node.getName();
    }

    /**
     * 格式化二级节点信息，返回格式为 "二级节点代码. 二级节点名称"
     *
     * @param node 二级节点对象
     * @return 格式化后的字符串，如果节点为空则返回空字符串
     */
    private String formatLevel2(SubjectNodeDTO node) {
        return node.getLevelCode() + ". " + node.getName();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approval2UpdateSecInfo(SecBiddingPlanChangeRequest request, String uId) {
        if (ValidateUtil.isEmpty(request.getBpcId())) return BaseResponse.fail(ResMsg.notReqParam);
        SecBiddingPlanChange sbpc = baseMapper.selectById(request.getBpcId());
        if (sbpc == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!JudgeUtil.isApprovedState(sbpc.getState()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        String olcBpId = sbpc.getBpId();

        SecBiddingPlan sbp = secBiddingPlanService.getById(olcBpId);
        if (ValidateUtil.isNotEmpty(request.getSyncOriginal())) {
            long count = this.count(Wrappers.<SecBiddingPlanChange>lambdaQuery()
                    .eq(SecBiddingPlanChange::getBpId, olcBpId)
                    .eq(SecBiddingPlanChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
            );
            if (count <= 1) {
                SecBiddingPlanChange secBiddingPlanChange = new SecBiddingPlanChange();
                BeanUtils.copyProperties(sbp, secBiddingPlanChange);
                secBiddingPlanChange.setBpcId(GenerateUUID.getUUID());
                secBiddingPlanChange.setBpId(sbp.getBpId());
                this.save(secBiddingPlanChange);
                SysFile sysFile = sysFileService.getById(sbp.getBpId());
                sysFileService.saveFile(secBiddingPlanChange.getBpcId(), sysFile.gettId(), secBiddingPlanChange.getState() + "", secBiddingPlanChange.getBpCreateUid(), secBiddingPlanChange.getBpCreateUname()
                        , secBiddingPlanChange.getBpCreateTime(), sysFile.getFileproject(), sysFile.getFiletitle(), sysFile.getFilecode(), "create",
                        GenerateUUID.getUUID(), null, sysFile.gettCode(), null);
                List<SecInfo> secInfos = secInfoService.list(Wrappers.<SecInfo>lambdaQuery().eq(SecInfo::getBpId, olcBpId));
                if (ValidateUtil.isNotEmpty(secInfos)) {
                    List<SecInfoChange> secInfoChanges = new ArrayList<>();
                    List<String> secIds = secInfos.stream().map(SecInfo::getSecId).collect(Collectors.toList());
                    List<SecInfoSubject> secInfoSubjects = secInfoSubjectService.list(Wrappers.<SecInfoSubject>lambdaQuery().in(ValidateUtil.isNotEmpty(secIds), SecInfoSubject::getSecId, secIds));
                    Map<String, List<SecInfoSubject>> secId2SubjectMap = secInfoSubjects.stream().collect(Collectors.groupingBy(SecInfoSubject::getSecId));

                    // 扩展
                    List<SecInfoSubjectExtra> extraList = secInfoSubjectExtraService.list(Wrappers.<SecInfoSubjectExtra>lambdaQuery().eq(SecInfoSubjectExtra::getBpId, olcBpId));
                    Map<String, SecInfoSubjectExtra> extraMap = extraList.stream().collect(Collectors.toMap(SecInfoSubjectExtra::getSecId, Function.identity()));

                    List<SecInfoSubjectChange> secInfoSubjectChanges = new ArrayList<>();
                    List<SecInfoSubjectExtraChange> secInfoSubjectExtraChanges = new ArrayList<>();
                    secInfos.forEach(secInfo -> {
                        SecInfoChange secInfoChange = new SecInfoChange();
                        BeanUtils.copyProperties(secInfo, secInfoChange);
                        secInfoChange.setSicId(GenerateUUID.getUUID());
                        secInfoChange.setBpcId(secBiddingPlanChange.getBpcId());
                        // secInfoChange.setSecApprovalFile(GenerateUUID.getUUID());
                        // 将变更中的标段文件复制到即将创建的标段信息中
                        // sysFileAnnexService.copyFileAnnex2NewRelation(Arrays.asList(secInfo.getSecApprovalFile()), Arrays.asList(secInfoChange.getSecApprovalFile()), secInfoChange.getSicId());
                        secInfoChanges.add(secInfoChange);

                        // 处理科目
                        List<SecInfoSubject> infoSubjects = secId2SubjectMap.get(secInfo.getSecId());
                        if (ValidateUtil.isNotEmpty(infoSubjects)) {
                            for (SecInfoSubject secInfoSubject : infoSubjects) {
                                SecInfoSubjectChange subject = new SecInfoSubjectChange();
                                BeanUtils.copyProperties(secInfoSubject, subject);
                                subject.setId(GenerateUUID.getUUID());
                                subject.setSicId(secInfoChange.getSicId());
                                secInfoSubjectChanges.add(subject);
                            }
                        }
                        // 处理扩展
                        SecInfoSubjectExtra extra = extraMap.get(secInfo.getSecId());
                        if (ValidateUtil.isNotNull(extra)) {
                            SecInfoSubjectExtraChange subjectExtra = new SecInfoSubjectExtraChange();
                            BeanUtils.copyProperties(extra, subjectExtra);
                            subjectExtra.setId(GenerateUUID.getUUID());
                            subjectExtra.setSicId(secInfoChange.getSicId());
                            subjectExtra.setBpcId(secBiddingPlanChange.getBpcId());
                            secInfoSubjectExtraChanges.add(subjectExtra);
                        }
                    });
                    secInfoChangeService.saveBatch(secInfoChanges);
                    if (CollectionUtil.isNotEmpty(secInfoSubjectChanges)) {
                        secInfoSubjectChangeService.saveBatch(secInfoSubjectChanges);
                    }
                    if (ValidateUtil.isNotEmpty(secInfoSubjectExtraChanges)) {
                        secInfoSubjectExtraChangeService.saveBatch(secInfoSubjectExtraChanges);
                    }
                }
                // 处理备注
                secInfoSubjectRemarkService.approval2UpdateSecInfoSubjectRemark(olcBpId, secBiddingPlanChange.getBpcId());
            }
            sbp.setBpCreateTime(sbpc.getBpCreateTime());
        }

        //region 标段信息的更新以及新增
        List<SecInfoChangeRes> secInfoChanges = secInfoChangeService.getSecInfoChangeList(request.getBpcId());
        if (ValidateUtil.isNotEmpty(secInfoChanges)) {
            List<String> sicIdList = new ArrayList<>();
            sicIdList = secInfoChanges.stream().map(SecInfoChangeRes::getSicId).collect(Collectors.toList());

            // 处理科目
            List<SecInfoSubjectChange> list = secInfoSubjectChangeService.list(Wrappers.<SecInfoSubjectChange>lambdaQuery().in(SecInfoSubjectChange::getSicId, sicIdList));
            for (SecInfoChangeRes secInfo : secInfoChanges) {
                List<SecInfoSubjectChange> secInfoSubjectChanges = list.stream().filter(n -> n.getSicId().equals(secInfo.getSicId())).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(secInfoSubjectChanges)) {
                    secInfo.setSecInfoSubjects(BeanUtil.copyToList(secInfoSubjectChanges, SecInfoSubjectChangeResponse.class));
                }
            }

            List<SecInfoBudgetDeclareDtlChangeRes> secInfoBudgetDeclareDtlChangeList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(sicIdList)){
                secInfoBudgetDeclareDtlChangeList=secInfoBudgetDeclareDtlChangeService.getSecInfoBudgetDeclareDtlChangeList(sicIdList);
           }
            if (ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtlChangeList)) {
                List<SecInfoBudgetDeclareDtlChangeRes> dtlAdds = secInfoBudgetDeclareDtlChangeList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSecChangeType()) && n.getSecChangeType().equals("新增")).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(dtlAdds)){
                    List<SecInfoBudgetDeclareDtl> secInfoBudgetDeclareDtls = new ArrayList<>();
                    for (SecInfoBudgetDeclareDtlChangeRes dtlAdd : dtlAdds) {
                        SecInfoBudgetDeclareDtl secInfoBudgetDeclareDtl = new SecInfoBudgetDeclareDtl();
                        BeanUtils.copyProperties(dtlAdd, secInfoBudgetDeclareDtl);
                        secInfoBudgetDeclareDtls.add(secInfoBudgetDeclareDtl);
                    }
                    //新增标段概算
                    if(ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtls)){
                        secInfoBudgetDeclareDtlService.saveOrUpdateBatch(secInfoBudgetDeclareDtls);
                    }
                }

                List<SecInfoBudgetDeclareDtlChangeRes> dtlUpdates = secInfoBudgetDeclareDtlChangeList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSecChangeType()) && n.getSecChangeType().equals("变更")).collect(Collectors.toList());
                List<SecInfoBudgetDeclareDtl> updateDtlList=new ArrayList<>();
                //用于生产字段修改记录
                List<Object> news = new ArrayList<>();
                List<Object> olds = new ArrayList<>();
                if(CollectionUtil.isNotEmpty(dtlUpdates)){
                    List<String> secIdList = secInfoBudgetDeclareDtlChangeList.stream().map(e -> e.getSecId()).collect(Collectors.toList());
                    List<SecInfoBudgetDeclareDtlDto> oldSecInfoBudgetDeclareDtls = secInfoBudgetDeclareDtlService.getSecInfoBudgetDeclareDtlBySecIds(secIdList);

                    for (SecInfoBudgetDeclareDtlChangeRes dtlUpdate : dtlUpdates) {
                        List<SecInfoBudgetDeclareDtlDto> secInfoBudgetDeclareDtlDtos = oldSecInfoBudgetDeclareDtls.stream().filter(n -> n.getSibdId().equals(dtlUpdate.getSibdId())).collect(Collectors.toList());
                        if(CollectionUtil.isNotEmpty(secInfoBudgetDeclareDtlDtos)){
                            SecInfoBudgetDeclareDtl secInfoBudgetDeclareDtl = new SecInfoBudgetDeclareDtl();
                            BeanUtils.copyProperties(dtlUpdate, secInfoBudgetDeclareDtl);
                            updateDtlList.add(secInfoBudgetDeclareDtl);
                            news.add(secInfoBudgetDeclareDtl);
                            olds.add(secInfoBudgetDeclareDtlDtos.stream().findFirst());
                        }
                    }

                    //更新标段信息
                    secInfoBudgetDeclareDtlService.updateBatchById(updateDtlList);
                    apiUtilsService.insertFiledChangeRecordBath(olds, news, "Sec_Info_Budget_Declare_Dtl", uId);
                }
            }

            List<SecInfoChangeRes> adds = secInfoChanges.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSecChangeType()) && "新增".equals(n.getSecChangeType())).collect(Collectors.toList());
            List<SecInfoChangeRes> updates = secInfoChanges.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSecChangeType()) && ("变更".equals(n.getSecChangeType()) || "关联概算科目".equals(n.getSecChangeType()) || "关联估算科目".equals(n.getSecChangeType()))).collect(Collectors.toList());
            // 判断是否已存在，存在就走更新
            if (ValidateUtil.isNotEmpty(adds)) {
                List<SecInfo> existingSecInfos = secInfoService.list(Wrappers.<SecInfo>lambdaQuery()
                        .eq(SecInfo::getBpId, olcBpId)
                        .in(SecInfo::getSecId, adds.stream().map(SecInfoChangeRes::getSecId).collect(Collectors.toSet()))
                        .select(SecInfo::getSecId)
                );
                if (ValidateUtil.isNotEmpty(existingSecInfos)) {
                    Set<String> existSecIdSet = existingSecInfos.stream()
                            .map(SecInfo::getSecId)
                            .collect(Collectors.toSet());
                    List<SecInfoChangeRes> realAdds = new ArrayList<>();
                    for (SecInfoChangeRes sic : adds) {
                        if (existSecIdSet.contains(sic.getSecId())) {
                            updates.add(sic);
                        } else {
                            realAdds.add(sic);
                        }
                    }
                    adds = realAdds;
                }
            }

            //region 需要新增的标段信息
            if (ValidateUtil.isNotEmpty(adds)) {
                List<SecInfo> secInfoPojos = new ArrayList<>();
                List<String> secIds = new ArrayList<>();
                for (SecInfoChangeRes n : adds) {
                    SecInfo sec = new SecInfo();
                    BeanUtils.copyProperties(n, sec);
                    sec.setBpId(olcBpId);
                    sec.setSecApprovalFile(GenerateUUID.getUUID());
                    // 标段的流程默认为已结束
                    sec.setSecFlowState(ApprovalStatusEnum.Already_Over.getIndex());
                    secInfoPojos.add(sec);
                    secIds.add(sec.getSecId());
                    //将变更中的标段文件复制到即将创建的标段信息中
                    sysFileAnnexService.copyFileAnnex2NewRelation(Arrays.asList(n.getSecApprovalFile()), Arrays.asList(sec.getSecApprovalFile()), sec.getSecId());
                    // 处理科目
                    List<SecInfoSubjectChangeResponse> secInfoSubjects = n.getSecInfoSubjects();
                    if (ValidateUtil.isNotEmpty(secInfoSubjects)) {
                        List<SecInfoSubject> secInfoSubjectList = new ArrayList<>();
                        for (SecInfoSubjectChangeResponse secInfoSubject : secInfoSubjects) {
                            SecInfoSubject subject = new SecInfoSubject();
                            BeanUtils.copyProperties(secInfoSubject, subject);
                            subject.setId(GenerateUUID.getUUID());
                            subject.setSecId(sec.getSecId());
                            secInfoSubjectList.add(subject);
                        }
                        if (CollectionUtil.isNotEmpty(secInfoSubjectList)) {
                            secInfoSubjectService.saveBatch(secInfoSubjectList);
                        }
                    }
                }
                //创建标段信息
                secInfoService.saveOrUpdateBatch(secInfoPojos);
                //创建标段项目关系数据
                Map<String, Object> secBiddingProjectMap = secBiddingProjectService.getSecBiddingProject(olcBpId);
                List<SecBiddingProjectRequest> secBiddingProjectRequestList  = (List<SecBiddingProjectRequest>) secBiddingProjectMap.get("secBiddingProjectRequestList");
                List<SecProjectRequest> secProjectRequestList=new ArrayList<>();
                if(CollectionUtil.isNotEmpty(secBiddingProjectRequestList)){
                    for (SecBiddingProjectRequest secBiddingProjectRequest : secBiddingProjectRequestList) {
                        SecProjectRequest secProjectRequest = new SecProjectRequest();
                        secProjectRequest.setpId(secBiddingProjectRequest.getpId());
                        secProjectRequest.setPpId(secBiddingProjectRequest.getPpId());
                        secProjectRequestList.add(secProjectRequest);
                    }
                }
                secProjectService.createOrUpdateSecProjectByBath((List<String>) secBiddingProjectMap.get("pIds"), secProjectRequestList,
                        secIds, sbpc.getBpCreateUid());
                //创建标段对应的流程文档
                sysFileService.createSecInfoFileByBath(adds, request.getpId(), request.gettId(), request.getFileTitle(), request.getFileCode(), request.gettCode(), request.getFileRelationId());
            }
            //endregion

            //region需要修改的标段信息
            if (ValidateUtil.isNotEmpty(updates)) {
                List<String> secIds = updates.stream().map(SecInfoChangeRes::getSecId).collect(Collectors.toList());
                List<SecInfoDto> secInfoDtos = secInfoService.getSecInfoBySecInfoIds(secIds);
                // 删除旧的标段科目
                secInfoSubjectService.remove(Wrappers.<SecInfoSubject>lambdaQuery().in(SecInfoSubject::getSecId, secIds));
                List<SecInfo> secInfoPojos = new ArrayList<>();
                //用于生产字段修改记录
                List<Object> news = new ArrayList<>();
                List<Object> olds = new ArrayList<>();
                for (SecInfoChangeRes n : updates) {
                    List<SecInfoDto> secInfo = secInfoDtos.stream().filter(s -> s.getSecId().equals(n.getSecId())).collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(secInfo)) {
                        SecInfo sec = new SecInfo();
                        BeanUtils.copyProperties(n, sec);
                        sec.setBpId(olcBpId);
                        sec.setSecApprovalFile(GenerateUUID.getUUID());
                        secInfoPojos.add(sec);
                        news.add(sec);
                        olds.add(secInfo.stream().findFirst().get());
                        //将变更中的标段文件复制到即将更新的标段信息中，并删除原标段附件
                        sysFileAnnexService.replaceFormFile(Arrays.asList(n.getSecApprovalFile()), Arrays.asList(sec.getSecApprovalFile()), sec.getSecId());
                        // 处理科目
                        List<SecInfoSubjectChangeResponse> secInfoSubjects = n.getSecInfoSubjects();
                        if (ValidateUtil.isNotEmpty(secInfoSubjects)) {
                            List<SecInfoSubject> secInfoSubjectList = new ArrayList<>();
                            for (SecInfoSubjectChangeResponse secInfoSubject : secInfoSubjects) {
                                SecInfoSubject subject = new SecInfoSubject();
                                BeanUtils.copyProperties(secInfoSubject, subject);
                                subject.setId(GenerateUUID.getUUID());
                                subject.setSecId(sec.getSecId());
                                secInfoSubjectList.add(subject);
                            }
                            if (CollectionUtil.isNotEmpty(secInfoSubjectList)) {
                                secInfoSubjectService.saveBatch(secInfoSubjectList);
                            }
                        }
                    }
                }
                //更新标段信息
                secInfoService.updateBatchById(secInfoPojos);
                apiUtilsService.insertFiledChangeRecordBath(olds, news, "Sec_Info", uId);
            }
            //endregion
            List<SecInfoChangeRes> delete = secInfoChanges.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSecChangeType()) && "删除".equals(n.getSecChangeType())).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(delete)) {
                List<String> delSecIds = delete.stream().map(SecInfoChangeRes::getSecId).collect(Collectors.toList());
                secProjectService.remove(Wrappers.<SecProject>lambdaQuery().in(SecProject::getSecId, delSecIds));
                // 删除标段信息
                secInfoService.removeByIds(delSecIds);
                // 删除标段对应的流程文档
                sysFileService.removeBatchByIds(delSecIds);

                // secInfoSubjectService.remove(Wrappers.<SecInfoSubject>lambdaQuery().in(SecInfoSubject::getSecId, delSecIds));
                // secInfoChangeService.remove(Wrappers.<SecInfoChange>lambdaQuery().in(SecInfoChange::getSecId, delSecIds));
            }
        }
        //endregion

        //region 更新招标计划本身
        sbp.setBpPlanPhase(sbpc.getBpPlanPhase());
        sbp.setBpRemark(sbpc.getBpRemark());
        sbp.setReserveItems(sbpc.getReserveItems());
        sbp.setEnterEstimate(sbpc.getEnterEstimate());
        //sbp.setBpApprovalFile(GenerateUUID.getUUID());
        //sbp.setBpApprovalNum(sbpc.getBpApprovalNum());
        //sbp.setBpApprovalTime(sbpc.getBpApprovalTime());
        //sbp.setBpApprovalTitle(sbpc.getBpApprovalTitle());
        //sysFileAnnexService.replaceFormFile(Arrays.asList(sbpc.getBpApprovalFile()), Arrays.asList(sbp.getBpApprovalFile()), sbp.getBpId());
        secBiddingPlanService.updateById(sbp);
        //endregion

        // 同步备注
        secInfoSubjectRemarkService.approval2UpdateSecInfoSubjectRemark(sbpc.getBpcId(), sbp.getBpId());

        // 同步子表
        secInfoSubjectExtraService.approval2UpdateSecInfoSubjectExtra(sbpc.getBpcId(), sbp.getBpId());
        return BaseResponse.ok();
    }


    private Boolean haveNoClosePlan(String bpId) {
        QueryWrapper<SecBiddingPlanChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Bp_Id", bpId);
        wrapper.ne("State", ApprovalStatusEnum.Invalid.getIndex());
        wrapper.ne("State", ApprovalStatusEnum.Already_Over.getIndex());
        if (baseMapper.exists(wrapper)) return true;
        return false;
    }

    @Override
    public BaseResponse getSecInfoChangeListByBpId(String bpId) {
        if (ValidateUtil.isEmpty(bpId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        return BaseResponse.ok(baseMapper.getSecInfoChangeListByBpId(bpId));
    }

    /**
     * 获取指定标段是否有流程中的变更
     *
     * @param secId
     * @return
     */
    @Override
    public BaseResponse haveChangeSecInfoBySecId(String secId) {
        if (ValidateUtil.isEmpty(secId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<SecInfoChange> secInfoChanges = secInfoChangeService.list(Wrappers.<SecInfoChange>lambdaQuery()
                .eq(SecInfoChange::getSecId, secId)
                .eq(SecInfoChange::getSecChangeType, "变更")
        );
        if (ValidateUtil.isNotEmpty(secInfoChanges)) {
            Set<String> bpcIds = secInfoChanges.stream().map(SecInfoChange::getBpcId).collect(Collectors.toSet());
            List<SecBiddingPlanChange> secBiddingPlanChanges = this.list(Wrappers.<SecBiddingPlanChange>lambdaQuery()
                    .in(SecBiddingPlanChange::getBpcId, bpcIds)
                    .ne(SecBiddingPlanChange::getState, ApprovalStatusEnum.Invalid.getIndex())
                    .ne(SecBiddingPlanChange::getState, ApprovalStatusEnum.Not_Started.getIndex())
                    .ne(SecBiddingPlanChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
            );
            if (ValidateUtil.isNotEmpty(secBiddingPlanChanges)) {
                return BaseResponse.ok(true);
            }
        }
        return BaseResponse.ok(false);
    }

    /**
     * 更新指定计划的项目分管领导
     *
     * @param bpcId
     * @param responsibleLeader
     * @return
     */
    @Override
    public BaseResponse updateSecBiddingPlanChangeResponsibleLeader(String bpcId, String responsibleLeader) {
        if (ValidateUtil.isAnObjEmpty(bpcId, responsibleLeader)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecBiddingPlanChange plan = baseMapper.selectById(bpcId);
        if (ValidateUtil.isNull(plan)) {
            return BaseResponse.fail(ResMsg.noData);
        }
        plan.setResponsibleLeader(responsibleLeader);
        this.updateById(plan);
        return BaseResponse.ok();
    }
}
