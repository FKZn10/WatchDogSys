package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecInfoBudgetDeclareDtlChangeRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoChangeRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoSubjectChangeRequest;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentBudgetBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoChangeMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoChange;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectChange;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectExtraChange;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecInfoChangeImpl extends ServiceImpl<SecInfoChangeMapper, SecInfoChange> implements ISecInfoChangeService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecProjectService secProjectService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecInfoService secInfoService;
    @Resource
    private ISecInfoBudgetDeclareDtlChangeService secInfoBudgetDeclareDtlChangeService;
    @Resource
    private ISecInfoSubjectChangeService secInfoSubjectChangeService;
    @Resource
    private ISecInfoSubjectExtraChangeService secInfoSubjectExtraChangeService;
    @Resource
    private InvestmentEstimateBreakdownDetailsMapper investmentEstimateBreakdownDetailsMapper;
    @Resource
    private InvestmentBudgetBreakdownDetailsMapper investmentBudgetBreakdownDetailsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdateSecInfoChange(List<SecInfoChangeRequest> datas, String bpcId, LogUserInfoVo user, String action, String pId) {
        List<SecInfoBudgetDeclareDtlChangeRequest> secInfoBudgetDeclareDtlChangeRequestList = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecInfoSubjectExtraChange> oldSubjectExtraChanges = secInfoSubjectExtraChangeService.list(Wrappers.<SecInfoSubjectExtraChange>lambdaQuery().eq(SecInfoSubjectExtraChange::getBpcId, bpcId));
            Map<String, Deque<SecInfoSubjectExtraChange>> oldSubjectExtraChangeMap = buildOldSubjectExtraChangeMap(oldSubjectExtraChanges);
            List<SecInfoSubjectExtraChange> currentSubjectExtraChanges = new ArrayList<>();
            secInfoSubjectExtraChangeService.remove(Wrappers.<SecInfoSubjectExtraChange>lambdaQuery().eq(SecInfoSubjectExtraChange::getBpcId, bpcId));
            if (action.equals("create")) {
                secInfoBudgetDeclareDtlChangeRequestList = createSecInfoChange(datas, bpcId, user, "create",pId, oldSubjectExtraChangeMap, oldSubjectExtraChanges, currentSubjectExtraChanges);
            } else if (action.equals("edit")) {
                List<SecInfoChangeRes> changeRes = getSecInfoChangeList(bpcId);
                List<String> sicIds = ValidateUtil.isEmpty(changeRes) ? new ArrayList<>() : changeRes.stream().map(SecInfoChangeRes::getSicId).collect(Collectors.toList());
                List<String> editIds = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getSicId())).map(SecInfoChangeRequest::getSicId).collect(Collectors.toList());
                List<SecInfoChangeRequest> adds = datas.stream().filter(n -> ValidateUtil.isEmpty(n.getSicId()) || !sicIds.contains(n.getSicId())).collect(Collectors.toList());
                List<SecInfoChangeRequest> updates = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getSicId()) && sicIds.contains(n.getSicId())).collect(Collectors.toList());
                List<String> dels = sicIds.stream().filter(n -> !editIds.contains(n)).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(dels)) {
                    this.removeBatchByIds(dels);
                    //删除关联的概算
                    secInfoBudgetDeclareDtlChangeService.delSecInfoBudgetDeclareDtlChangeBySicId(dels);
                    // 删除关联的科目
                    secInfoSubjectChangeService.remove(Wrappers.<SecInfoSubjectChange>lambdaQuery().in(SecInfoSubjectChange::getSicId, dels));
                }
                secInfoBudgetDeclareDtlChangeRequestList=createSecInfoChange(adds, bpcId, user,"edit",pId, oldSubjectExtraChangeMap, oldSubjectExtraChanges, currentSubjectExtraChanges);
                if (ValidateUtil.isNotEmpty(updates)) {
                    List<SecInfoChange> upSecs = new ArrayList<>();
                    List<Object> olds = new ArrayList<>();
                    List<Object> news = new ArrayList<>();
                    List<SecInfoSubjectChange> changes = new ArrayList<>();
                    List<SecInfoSubjectExtraChange> extraChanges = new ArrayList<>();
                    // 先删除全部的子表
                    List<String> upSicIds = updates.stream().map(SecInfoChangeRequest::getSicId).filter(sicId -> !ValidateUtil.isEmpty(sicId)).collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(upSicIds)) {
                        secInfoSubjectChangeService.remove(Wrappers.<SecInfoSubjectChange>lambdaQuery().in(SecInfoSubjectChange::getSicId, upSicIds));
                    }
                    for (SecInfoChangeRequest update : updates) {
                        SecInfoChange upSec = new SecInfoChange();
                        BeanUtils.copyProperties(update, upSec);
                        //将修改的标段里面的概算添加进来
                        List<SecInfoBudgetDeclareDtlChangeRequest> secInfoBudgetDeclareDtlChangeList = update.getSecInfoBudgetDeclareDtlChangeList();
                        if (ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtlChangeList)) {
                            secInfoBudgetDeclareDtlChangeList.stream().forEach(n -> {n.setSicId(update.getSicId());});
                            secInfoBudgetDeclareDtlChangeRequestList.addAll(secInfoBudgetDeclareDtlChangeList);
                        }
                        upSec.setBpcId(bpcId);
                        upSecs.add(upSec);
                        sysFileAnnexService.addFormCopyAndRefreshRelationFile(Arrays.asList(update.getFileRelationId(), update.getSecApprovalFile(), update.getSecValuationFile()),
                                Arrays.asList(update.getFileIds(), update.getSecApprovalFileIds(), update.getSecValuationFileIds()), upSec.getSicId());
                        news.add(upSec);
                        olds.add(changeRes.stream().filter(n -> n.getSicId().equals(upSec.getSicId())).findFirst().get());

                        // 处理子表
                        List<SecInfoSubjectChangeRequest> secInfoSubjects = update.getSecInfoSubjects();
                        if (ValidateUtil.isNotEmpty(secInfoSubjects)) {
                            if (ValidateUtil.isNotEmpty(update.getSecSubjectNameType()) && "估/概算外的二类费".equals(update.getSecSubjectNameType())) {
                                secInfoSubjects.forEach(sub -> {
                                    SecInfoSubjectExtraChange extra = buildSubjectExtraChange(sub, upSec, bpcId, user, oldSubjectExtraChangeMap, oldSubjectExtraChanges, currentSubjectExtraChanges);
                                    extraChanges.add(extra);
                                    currentSubjectExtraChanges.add(extra);
                                });
                            } else {
                                secInfoSubjects.forEach(sub -> {
                                    SecInfoSubjectChange change = new SecInfoSubjectChange();
                                    BeanUtils.copyProperties(sub, change);
                                    change.setSicId(update.getSicId());
                                    change.setId(GenerateUUID.getUUID());
                                    changes.add(change);
                                });
                            }
                        }
                    }
                    if (ValidateUtil.isNotEmpty(changes)) {
                        secInfoSubjectChangeService.saveBatch(changes);
                    }
                    if (ValidateUtil.isNotEmpty(extraChanges)) {
                        secInfoSubjectExtraChangeService.saveBatch(extraChanges);
                    }
                    updateBatchById(upSecs);
                    apiUtilsService.insertFiledChangeRecordBath(olds, news, "Sec_Info_Change", user.getId());
                }
            }


            //新增标段的概算变更
            if (ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtlChangeRequestList)) {
                secInfoBudgetDeclareDtlChangeService.createOrUpdateSecInfoBudgetDeclareDtlChange(secInfoBudgetDeclareDtlChangeRequestList, user.getId(), action);
            }

        } else
            deleteByBpcId(bpcId);
    }


    private List<SecInfoBudgetDeclareDtlChangeRequest> createSecInfoChange(List<SecInfoChangeRequest> datas, String bpcId, LogUserInfoVo user, String action,String pId,
                                                                           Map<String, Deque<SecInfoSubjectExtraChange>> oldSubjectExtraChangeMap,
                                                                           List<SecInfoSubjectExtraChange> oldSubjectExtraChanges,
                                                                           List<SecInfoSubjectExtraChange> currentSubjectExtraChanges) {
        List<SecInfoBudgetDeclareDtlChangeRequest> secInfoBudgetDeclareDtlChangeRequestList = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecInfoChange> entitys = new ArrayList<>();
            List<SecInfoSubjectChange> changes = new ArrayList<>();
            List<SecInfoSubjectExtraChange> extraChanges = new ArrayList<>();
            // 先删除全部的子表
            List<String> sicIds = datas.stream().map(SecInfoChangeRequest::getSicId).filter(sicId -> !ValidateUtil.isEmpty(sicId)).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(sicIds)) {
                secInfoSubjectChangeService.remove(Wrappers.<SecInfoSubjectChange>lambdaQuery().in(SecInfoSubjectChange::getSicId, sicIds));
            }
            // 处理主表
            entitys = datas.stream().map(n -> {

                SecInfoChange pojo = new SecInfoChange();
                BeanUtils.copyProperties(n, pojo);
                pojo.setBpcId(bpcId);
                pojo.setSicId(GenerateUUID.getUUID(n.getSicId()));
                List<SecInfoBudgetDeclareDtlChangeRequest> secInfoBudgetDeclareDtlChangeList = n.getSecInfoBudgetDeclareDtlChangeList();
                if (ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtlChangeList)) {
                    for (SecInfoBudgetDeclareDtlChangeRequest secInfoBudgetDeclareDtlChangeRequest : secInfoBudgetDeclareDtlChangeList) {
                        secInfoBudgetDeclareDtlChangeRequest.setSicId(pojo.getSicId());
                        secInfoBudgetDeclareDtlChangeRequestList.add(secInfoBudgetDeclareDtlChangeRequest);
                    }
                }

                sysFileAnnexService.addFormCopyAndRefreshRelationFile(Arrays.asList(n.getFileRelationId(), n.getSecApprovalFile(), n.getSecValuationFile()),
                        Arrays.asList(n.getFileIds(), n.getSecApprovalFileIds(), n.getSecValuationFileIds()), pojo.getSicId());

                // 处理子表
                List<SecInfoSubjectChangeRequest> secInfoSubjects = n.getSecInfoSubjects();
                if (ValidateUtil.isNotEmpty(secInfoSubjects)) {
                    if (ValidateUtil.isNotEmpty(n.getSecSubjectNameType()) && "估/概算外的二类费".equals(n.getSecSubjectNameType())) {
                        secInfoSubjects.forEach(sub -> {
                            SecInfoSubjectExtraChange extra = buildSubjectExtraChange(sub, pojo, bpcId, user, oldSubjectExtraChangeMap, oldSubjectExtraChanges, currentSubjectExtraChanges);
                            extraChanges.add(extra);
                            currentSubjectExtraChanges.add(extra);
                        });
                    } else {
                        secInfoSubjects.forEach(sub -> {
                            SecInfoSubjectChange change = new SecInfoSubjectChange();
                            BeanUtils.copyProperties(sub, change);
                            change.setSicId(pojo.getSicId());
                            change.setId(GenerateUUID.getUUID());
                            changes.add(change);
                        });
                    }
                }
                return pojo;
            }).collect(Collectors.toList());
            saveBatch(entitys);
            if (ValidateUtil.isNotEmpty(changes)) {
                secInfoSubjectChangeService.saveBatch(changes);
            }
            if (ValidateUtil.isNotEmpty(extraChanges)) {
                secInfoSubjectExtraChangeService.saveBatch(extraChanges);
            }

            List<SecInfoChange> secinfoChanges = entitys.stream().filter(n -> n.getSecChangeType().equals("变更")).collect(Collectors.toList());
            List<String> secIds = ValidateUtil.isEmpty(secinfoChanges) ? null : secinfoChanges.stream().map(SecInfoChange::getSecId).collect(Collectors.toList());
            List<SecInfoDto> secInfos = secInfoService.getSecInfoBySecInfoIds(secIds);
            if (ValidateUtil.isNotEmpty(secInfos)) {
                List<Object> olds = new ArrayList<>();
                List<Object> news = new ArrayList<>();
                for (SecInfoDto secInfo : secInfos) {
                    List<SecInfoChange> secinfoChange = secinfoChanges.stream().filter(n -> n.getSecId().equals(secInfo.getSecId())).collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(secinfoChange)) {
                        olds.add(secInfo);
                        news.add(secinfoChange.stream().findFirst().get());
                    }
                }
                apiUtilsService.insertFiledChangeRecordBath(olds, news, "Sec_Info_Change", user.getId());
            }

            //新增
//             List<SecInfo> secInfoList = datas.stream().filter(e -> ValidateUtil.isNotNull(e.getChangeType()) && e.getChangeType().equals("新增")).map(e -> {
//                SecInfo sec = new SecInfo();
//                if(ValidateUtil.isNotNull(e.getSecId())){
//                    BeanUtils.copyProperties(e, sec);
//                    sec.setSecFlowState(7);
//                    sec.setSecFlowTime(new Date());
//                }
//                return sec;
//            }).filter(Objects::nonNull).collect(Collectors.toList());
//            if(!CollectionUtils.isEmpty(secInfoList)){
//                secInfoService.saveBatch(secInfoList);
//                List<SecProject> secProjects = secInfoList.stream().map(e -> {
//                    SecProject secProject = new SecProject();
//                    secProject.setSpId(GenerateUUID.getUUID());
//                    secProject.setSecId(e.getSecId());
//                    secProject.setPId(pId);
//                    secProject.setSecInfoCreateUid(uId);
//                    return secProject;
//                }).collect(Collectors.toList());
//                if(ValidateUtil.isNotNull(secProjects)){
//                    secProjectService.saveBatch(secProjects);
//                }
//            }
        }
        return secInfoBudgetDeclareDtlChangeRequestList;
    }

    private SecInfoSubjectExtraChange buildSubjectExtraChange(SecInfoSubjectChangeRequest sub,
                                                              SecInfoChange secInfo,
                                                              String bpcId,
                                                              LogUserInfoVo user,
                                                              Map<String, Deque<SecInfoSubjectExtraChange>> oldSubjectExtraChangeMap,
                                                              List<SecInfoSubjectExtraChange> oldSubjectExtraChanges,
                                                              List<SecInfoSubjectExtraChange> currentSubjectExtraChanges) {
        SecInfoSubjectExtraChange extra = new SecInfoSubjectExtraChange();
        extra.setId(GenerateUUID.getUUID());
        extra.setSicId(secInfo.getSicId());
        extra.setSecId(secInfo.getSecId());
        extra.setBpcId(bpcId);
        extra.setSeName(secInfo.getSecName());
        boolean budgetParent = ValidateUtil.isNotEmpty(sub.getSubIbbId());
        extra.setParentId(sub.getId());
        extra.setSeMoney(sub.getSubMoney());
        SecInfoSubjectExtraChange oldExtra = pollOldSubjectExtraChange(oldSubjectExtraChangeMap, extra);
        if (oldExtra != null && ValidateUtil.isNotNull(oldExtra.getSort())) {
            extra.setId(oldExtra.getId());
            // extra.setSort(oldExtra.getSort());
            // extra.setLevelCode(oldExtra.getLevelCode());
            extra.setCreateUid(oldExtra.getCreateUid());
            extra.setCreateUname(oldExtra.getCreateUname());
            extra.setCreateTime(oldExtra.getCreateTime());
        } else {
            // extra.setSort(resolveSubjectExtraSort(extra.getParentId(), budgetParent, currentSubjectExtraChanges, oldSubjectExtraChanges));
            // extra.setLevelCode(resolveSubjectExtraLevelCode(extra.getParentId(), extra.getSort()));
            extra.setCreateUid(user.getId());
            extra.setCreateUname(user.getName());
            extra.setCreateTime(new Date());
        }
        extra.setLevelCode(sub.getLevelCode());
        extra.setSort(ValidateUtil.isNotEmpty(sub.getLevelCode()) ? Integer.valueOf(sub.getLevelCode().split(" ")[0]) : null);

        return extra;
    }

    private Integer resolveSubjectExtraSort(String parentId, boolean budgetParent,
                                            List<SecInfoSubjectExtraChange> currentSubjectExtraChanges,
                                            List<SecInfoSubjectExtraChange> oldSubjectExtraChanges) {
        Integer baseSort = getSubjectExtraBaseSort(parentId, budgetParent);
        Integer oldSort = getMaxSubjectExtraSort(oldSubjectExtraChanges, parentId);
        Integer currentSort = getMaxSubjectExtraSort(currentSubjectExtraChanges, parentId);
        return Math.max(baseSort, Math.max(ValidateUtil.isNull(oldSort) ? 0 : oldSort, ValidateUtil.isNull(currentSort) ? 0 : currentSort)) + 1;
    }
    private Integer getMaxSubjectExtraSort(List<SecInfoSubjectExtraChange> subjectExtras, String parentId) {
        if (ValidateUtil.isEmpty(subjectExtras)) {
            return null;
        }
        return subjectExtras.stream()
                .filter(e -> isSameSubjectExtraParent(e.getParentId(), parentId))
                .map(SecInfoSubjectExtraChange::getSort)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(null);
    }

    private Map<String, Deque<SecInfoSubjectExtraChange>> buildOldSubjectExtraChangeMap(List<SecInfoSubjectExtraChange> oldSubjectExtraChanges) {
        if (ValidateUtil.isEmpty(oldSubjectExtraChanges)) {
            return new HashMap<>();
        }
        return oldSubjectExtraChanges.stream()
                .sorted(Comparator.comparing(
                        SecInfoSubjectExtraChange::getSort,
                        Comparator.nullsLast(Integer::compareTo)
                ))
                .collect(Collectors.groupingBy(
                        this::getSubjectExtraUniqueKey,
                        Collectors.collectingAndThen(
                                Collectors.toCollection(ArrayDeque::new),
                                deque -> deque
                        )
                ));
    }

    private SecInfoSubjectExtraChange pollOldSubjectExtraChange(Map<String, Deque<SecInfoSubjectExtraChange>> oldSubjectExtraChangeMap, SecInfoSubjectExtraChange extra) {
        Deque<SecInfoSubjectExtraChange> oldExtras = oldSubjectExtraChangeMap.get(getSubjectExtraUniqueKey(extra));
        return ValidateUtil.isEmpty(oldExtras) ? null : oldExtras.pollFirst();
    }

    private String getSubjectExtraUniqueKey(SecInfoSubjectExtraChange extra) {
        return getSubjectExtraParentKey(extra.getParentId()) + "|"
                + (ValidateUtil.isEmpty(extra.getSicId()) ? "" : extra.getSicId());
    }

    private String getSubjectExtraParentKey(String parentId) {
        return ValidateUtil.isEmpty(parentId) ? "" : parentId;
    }

    private boolean isSameSubjectExtraParent(String leftParentId, String rightParentId) {
        if (ValidateUtil.isEmpty(leftParentId) && ValidateUtil.isEmpty(rightParentId)) {
            return true;
        }
        if (ValidateUtil.isEmpty(leftParentId) || ValidateUtil.isEmpty(rightParentId)) {
            return false;
        }
        return leftParentId.equalsIgnoreCase(rightParentId);
    }

    private Integer getSubjectExtraBaseSort(String parentId, boolean budgetParent) {
        if (ValidateUtil.isEmpty(parentId)) {
            return 0;
        }
        if (budgetParent) {
            List<InvestmentBudgetBreakdownDetails> children = investmentBudgetBreakdownDetailsMapper.selectList(Wrappers.<InvestmentBudgetBreakdownDetails>lambdaQuery()
                    .eq(InvestmentBudgetBreakdownDetails::getParentId, parentId)
                    .select(InvestmentBudgetBreakdownDetails::getSort));
            return children.stream()
                    .map(InvestmentBudgetBreakdownDetails::getSort)
                    .filter(Objects::nonNull)
                    .mapToInt(BigDecimal::intValue)
                    .max()
                    .orElse(0);
        }
        List<InvestmentEstimateBreakdownDetails> children = investmentEstimateBreakdownDetailsMapper.selectList(Wrappers.<InvestmentEstimateBreakdownDetails>lambdaQuery()
                .eq(InvestmentEstimateBreakdownDetails::getParentId, parentId)
                .select(InvestmentEstimateBreakdownDetails::getSort));
        return children.stream()
                .map(InvestmentEstimateBreakdownDetails::getSort)
                .filter(Objects::nonNull)
                .mapToInt(BigDecimal::intValue)
                .max()
                .orElse(0);
    }

    private String resolveSubjectExtraLevelCode(String parentId, Integer sort) {
        if (ValidateUtil.isEmpty(parentId) || ValidateUtil.isNull(sort)) {
            return ValidateUtil.isNull(sort) ? null : String.valueOf(sort);
        }
        return sort + " " + sort;
    }

    @Override
    public void deleteByBpcId(String bpcId) {
        QueryWrapper<SecInfoChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Bpc_Id", bpcId);
        List<SecInfoChange> datas = baseMapper.selectList(wrapper);
        secInfoSubjectExtraChangeService.remove(Wrappers.<SecInfoSubjectExtraChange>lambdaQuery().eq(SecInfoSubjectExtraChange::getBpcId, bpcId));
        if (!CollectionUtils.isEmpty(datas)) {
            List<String> sicIds = datas.stream().map(SecInfoChange::getSicId).collect(Collectors.toList());
            baseMapper.deleteBatchIds(sicIds);
        }
    }

    @Override
    public List<SecInfoChangeRes> getSecInfoChangeList(String bpcId) {
        List<SecInfoChangeRes> results = baseMapper.getSecInfoChangeList(bpcId);
        List<SecInfoChangeRes> datas = results.stream().map(result -> {
            SecInfoChangeRes data = new SecInfoChangeRes();
            BeanUtils.copyProperties(result, data);
            return data;
        }).collect(Collectors.toList());
        return datas;
    }

    @Override
    public List<String> getSecInfoChangeIds(String bpcId) {
        if (ValidateUtil.isEmpty(bpcId)) return new ArrayList<>();
        return baseMapper.getSecInfoChangeIds(bpcId);
    }
}
