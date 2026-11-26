package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.*;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoBudgetModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProjectModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSourcesFundsModel;
import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import com.jpmsoft.common.model.response.SecInfo.SecInfoTZResponse;
import com.jpmsoft.common.model.response.SecInfo.SecPublicityResultsResponse;
import com.jpmsoft.common.model.response.SecInfo.SecWinNoticeResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentBudgetBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.*;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoAmountDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecNodeTableAndFieldDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.*;
import com.jpmsoft.projectProducer.pojo.vo.project.GDGYSecInfoVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecEntrustVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecInfoAmountBudgetDeclareDtlVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecInquiryRecordVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecProjectStatisticsVo;
import com.jpmsoft.projectProducer.service.contract.IConEstimateService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecInfoImpl extends ServiceImpl<SecInfoMapper, SecInfo> implements ISecInfoService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecProjectService secProjectService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISecInfoBudgetService secInfoBudgetService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecSourcesFundsService secSourcesFundsService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private ISecProcurementDetailsService secProcurementDetailsService;
    @Resource
    private ISecFundsService secFundsService;
    @Resource
    private IQuotationRequestService quotationRequestService;
    @Resource
    private ConContractMapper conContractMapper;
    @Resource
    private IConEstimateService conEstimateService;
    @Resource
    private SecInfoChangeMapper secInfoChangeMapper;
    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;
    @Resource
    private ISecProcurementPlanAmountService secProcurementPlanAmountService;
    @Resource
    private ISecInfoBudgetDeclareDtlService secInfoBudgetDeclareDtlService;

    @Resource
    private ISecPublicityResultsService secPublicityResultsService;

    @Resource
    private IPreProjectService projectService;

    @Resource
    private ISysCompanyService sysCompanyService;

    @Resource
    private SecInquiryRecordMapper secInquiryRecordMapper;

    @Resource
    private SecWinNoticeMapper secWinNoticeMapper;
    @Resource
    private ISecInfoSubjectService secInfoSubjectService;
    @Resource
    private SecEntrustMapper secEntrustMapper;
    @Resource
    private ISecInfoSubjectExtraService secInfoSubjectExtraService;
    @Resource
    private InvestmentEstimateBreakdownDetailsMapper investmentEstimateBreakdownDetailsMapper;
    @Resource
    private InvestmentBudgetBreakdownDetailsMapper investmentBudgetBreakdownDetailsMapper;


    /**
     * 新增或修改标段，并在同一事务内同步询价调研等子表数据。
     *
     * @param request 标段信息，询价明细不传则保留，空列表则清空
     * @return 保存结果及标段ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecInfo(SecInfoRequest request) {
        if (CollectionUtils.isEmpty(request.getSecProjects()))
            return BaseResponse.fail(ResMsg.notReqParam);
        SecInfo sec = new SecInfo();
        String codeRule = null;
        if (ValidateUtil.isEmpty(request.getSecId()) && apiUtilsService.sequenceRestEnable("SecInfo") == true) {
            request.setSecCode(apiUtilsService.generateCodeByTCode("SecInfo", request));
            if (ValidateUtil.isEmpty(request.getSecCode()))
                return BaseResponse.fail(ResMsg.codeGenerError);
            codeRule = "SecInfo";
        }
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId())) {
            sec.setSecCreateTime(new Date());
            sec.setSecId(GenerateUUID.getUUID());
            baseMapper.insert(sec);
            secProjectService.createOrUpdateSecProject(request.getSecProjects(), sec.getSecId(), sec.getSecCreateUid());
            secInfoBudgetService.createOrUpdateSecInfoBudget(request.getSecInfoBudgets(), sec.getSecId());
            sysFileService.saveFile(sec.getSecId(), request.gettId(), sec.getSecFlowState() + "", sec.getSecCreateUid(), sec.getSecCreateUname()
                    , sec.getSecCreateTime(), null, request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSecApprovalFile(), request.getSecValuationFile()
                    , request.getSecProofFile(), request.getSecBlankFile(), request.getSecOtherFile(), request.getSecOtherImg(), request.getProcurementListDoc(), request.getProcurementPlanDoc(), request.getNoIntentionDiscloseDoc(),
                    request.getSupportMaterialsDoc(), request.getExpenditureApprovalDoc(), request.getDemandSurveyDoc(), request.getOtherDoc()), sec.getSecId());

        } else {
            SecInfo entity = baseMapper.selectById(request.getSecId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            sec.setSecCreateTime(entity.getSecCreateTime());
            sec.setSecCreateUid(entity.getSecCreateUid());
            sec.setSecCreateUname(entity.getSecCreateUname());
            baseMapper.updateById(sec);
            secProjectService.createOrUpdateSecProject(request.getSecProjects(), sec.getSecId(), sec.getSecCreateUid());
            secInfoBudgetService.createOrUpdateSecInfoBudget(request.getSecInfoBudgets(), sec.getSecId());
            sysFileService.saveFile(sec.getSecId(), request.gettId(), sec.getSecFlowState() + "", sec.getSecCreateUid(), sec.getSecCreateUname()
                    , sec.getSecCreateTime(), null, request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSecApprovalFile(), request.getSecValuationFile(),
                            request.getSecProofFile(), request.getSecBlankFile(), request.getSecOtherFile(), request.getSecOtherImg(), request.getProcurementListDoc(), request.getProcurementPlanDoc(), request.getNoIntentionDiscloseDoc(),
                            request.getSupportMaterialsDoc(), request.getExpenditureApprovalDoc(), request.getDemandSurveyDoc(), request.getOtherDoc()),
                    Arrays.asList(request.getFileIds(), request.getSecApprovalFileIds(), request.getSecValuationFileIds()
                            , request.getSecProofFileIds(), request.getSecBlankFileIds(), request.getSecOtherFileIds(), request.getSecOtherImgIds(), request.getProcurementListDocFileIds(), request.getProcurementPlanDocFileIds(), request.getNoIntentionDiscloseDocFileIds(),
                            request.getSupportMaterialsDocFileIds(), request.getExpenditureApprovalDocFileIds(), request.getDemandSurveyDocFileIds(), request.getOtherDocFileIds()), sec.getSecId());

        }
        secSourcesFundsService.createOrUpdateSecSourcesFunds(request.getSourcesFundsModels(), sec.getSecId());
        secProcurementDetailsService.createOrUpdate(request.getSecProcurementDetails(), sec.getSecId());
        secFundsService.createOrUpdate(request.getSecFunds(), sec.getSecId());
        quotationRequestService.createOrUpdate(request.getQuotationRequest(), sec.getSecId());
        if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, request);
        return BaseResponse.ok(sec.getSecId());
    }


    /**
     * 按招标计划重建标段及关联子表，询价调研明细随标段重新生成主键。
     *
     * @param datas          标段信息列表
     * @param pIds           项目ID列表
     * @param secProjectList 标段项目关联
     * @param bp             招标计划
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecInfo(List<SecInfoRequest> datas, List<String> pIds, List<SecProjectRequest> secProjectList, SecBiddingPlan bp) {
        //删除old数据
        String bpId = bp.getBpId();
        secProcurementDetailsService.deleteByBpId(bpId);
        secFundsService.deleteByBpId(bpId);
        quotationRequestService.deleteByBpId(bpId);
        secSourcesFundsService.delSecSourcesFundsByBpId(bpId);
        secProjectService.deleteAllOldByBpId(bpId);
        secInfoBudgetDeclareDtlService.delSecInfoBudgetDeclareDtlByBpId(bpId);
        secInfoSubjectService.deleteAllOldByBpId(bpId);
        List<SecInfoSubjectExtra> oldSubjectExtras = secInfoSubjectExtraService.list(Wrappers.<SecInfoSubjectExtra>lambdaQuery().eq(SecInfoSubjectExtra::getBpId, bpId));
        secInfoSubjectExtraService.remove(Wrappers.<SecInfoSubjectExtra>lambdaQuery().eq(SecInfoSubjectExtra::getBpId, bpId));
        baseMapper.deleteAllOldByBpId(bpId);
        //新增数据
        List<SecInfo> entitys = new ArrayList<>();
        List<List<SecSourcesFundsModel>> ssfms = new ArrayList<>();
        List<String> ssfSecIds = new ArrayList<>();
        List<List<String>> relationId = new ArrayList<>();
        List<List<String>> fileIds = new ArrayList<>();
        List<String> secIds = new ArrayList<>();
        List<SecInfoRequest> refershs = new ArrayList<>();
        List<SecInfoBudgetDeclareDtl> secInfoBudgetDeclareDtlList = new ArrayList<>();
        List<SecInfoSubject> secInfoSubjects = new ArrayList<>();
        List<SecInfoSubjectExtra> secInfoSubjectExtras = new ArrayList<>();
        Map<String, Boolean> extraParentTypeMap = new HashMap<>();
        Map<String, Deque<SecInfoSubjectExtra>> oldSubjectExtraMap = buildOldSubjectExtraMap(oldSubjectExtras);
        for (SecInfoRequest request : datas) {
            SecInfo sec = new SecInfo();
            if (ValidateUtil.isEmpty(request.getSecId()) && apiUtilsService.sequenceRestEnable("SecInfo") == true) {
                request.setSecCode(apiUtilsService.generateCodeByTCode("SecInfo", request));
                if (ValidateUtil.isEmpty(request.getSecCode()))
                    return BaseResponse.fail(ResMsg.codeGenerError);
                refershs.add(request);
            }
            BeanUtils.copyProperties(request, sec);
            sec.setSecCreateTime(new Date());
            if (ValidateUtil.isEmpty(sec.getSecParentId())) {
                sec.setSecId(GenerateUUID.getUUID());
            }
            sec.setBpId(bpId);
            // 计算金额
            // if (ValidateUtil.isNotEmpty(sec.getSecParentId()) && "0".equals(sec.getSecParentId())) {
            //     BigDecimal totalMoney = BigDecimal.ZERO;
            //     String id = sec.getSecId();
            //     for (SecInfoRequest data : datas) {
            //         if (id.equals(data.getSecParentId()) && ValidateUtil.isNotNull(data.getSecMoney())) {
            //             totalMoney = totalMoney.add(data.getSecMoney());
            //         }
            //     }
            //     sec.setSecMoney(totalMoney);
            // }
            entitys.add(sec);
            if (ValidateUtil.isNotEmpty(request.getSourcesFundsModels())) {
                ssfms.add(request.getSourcesFundsModels());
                ssfSecIds.add(sec.getSecId());
            }

            if (!ValidateUtil.isEmpty(request.getSecInfoBudgetDeclareDtlDtoList())) {
                List<SecInfoBudgetDeclareDtlDto> secInfoBudgetDeclareDtlDtoList = request.getSecInfoBudgetDeclareDtlDtoList();
                for (SecInfoBudgetDeclareDtlDto secInfoBudgetDeclareDtlDto : secInfoBudgetDeclareDtlDtoList) {
                    SecInfoBudgetDeclareDtl secInfoBudgetDeclareDtl = new SecInfoBudgetDeclareDtl();
                    BeanUtil.copyProperties(secInfoBudgetDeclareDtlDto, secInfoBudgetDeclareDtl);
                    secInfoBudgetDeclareDtl.setSibdId(GenerateUUID.getUUID());
                    secInfoBudgetDeclareDtl.setSecId(sec.getSecId());
                    secInfoBudgetDeclareDtlList.add(secInfoBudgetDeclareDtl);
                }
            }

            relationId.add(Arrays.asList(request.getFileRelationId(), request.getSecApprovalFile(), request.getSecValuationFile(), request.getSecProofFile(), request.getSecBlankFile(), request.getSecOtherFile(), request.getSecOtherImg(), request.getProcurementPlanDoc(), request.getNoIntentionDiscloseDoc(),
                    request.getSupportMaterialsDoc(), request.getExpenditureApprovalDoc(), request.getDemandSurveyDoc(), request.getOtherDoc()));
            fileIds.add(Arrays.asList(request.getFileIds(), request.getSecApprovalFileIds(), request.getSecValuationFileIds(), request.getSecProofFileIds(), request.getSecBlankFileIds(), request.getSecOtherFileIds(), request.getSecOtherImgIds(), request.getProcurementPlanDocFileIds(), request.getNoIntentionDiscloseDocFileIds(),
                    request.getSupportMaterialsDocFileIds(), request.getExpenditureApprovalDocFileIds(), request.getDemandSurveyDocFileIds(), request.getOtherDocFileIds()));
            secIds.add(sec.getSecId());

            // 处理子表
            List<SecInfoSubjectRequest> subjects = request.getSecInfoSubjects();
            if (ValidateUtil.isNotEmpty(subjects)) {
                if (ValidateUtil.isNotEmpty(request.getSecSubjectNameType()) && "估/概算外的二类费".equals(request.getSecSubjectNameType())) {
                    subjects.forEach(sub -> {
                        SecInfoSubjectExtra extra = new SecInfoSubjectExtra();
                        extra.setId(GenerateUUID.getUUID());
                        extra.setSecId(sec.getSecId());
                        extra.setBpId(bpId);
                        boolean budgetParent = ValidateUtil.isNotEmpty(sub.getSubIbbId());
                        extra.setParentId(sub.getId());
                        extraParentTypeMap.put(getSubjectExtraParentKey(extra.getParentId()), budgetParent);
                        extra.setSeName(sub.getSubName());
                        extra.setSeMoney(sub.getSubMoney());
                        // SecInfoSubjectExtra oldExtra = pollOldSubjectExtra(oldSubjectExtraMap, extra);
                        // if (oldExtra != null && ValidateUtil.isNotNull(oldExtra.getSort())) {
                        //     extra.setSort(oldExtra.getSort());
                        // } else {
                        //     extra.setSort(resolveSubjectExtraSort(extra.getParentId(), budgetParent, secInfoSubjectExtras, oldSubjectExtras));
                        // }
                        extra.setLevelCode(sub.getLevelCode());
                        extra.setSort(ValidateUtil.isNotEmpty(sub.getLevelCode()) ? Integer.valueOf(sub.getLevelCode().split(" ")[0]) : null);
                        extra.setCreateTime(new Date());
                        extra.setCreateUid(bp.getBpCreateUid());
                        extra.setCreateUname(bp.getBpCreateUname());
                        secInfoSubjectExtras.add(extra);
                    });
                } else {
                    subjects.forEach(sub -> {
                        SecInfoSubject secInfoSubject = new SecInfoSubject();
                        BeanUtil.copyProperties(sub, secInfoSubject);
                        secInfoSubject.setId(GenerateUUID.getUUID());
                        secInfoSubject.setSecId(sec.getSecId());
                        secInfoSubjects.add(secInfoSubject);
                    });
                }
            }
        }

        if (entitys.size() > 0) {
            saveBatch(entitys);
            saveQuotationRequests(datas, entitys, true);
            for (int i = 0; i < relationId.size(); i++)
                sysFileAnnexService.refreshRelationFile(relationId.get(i), fileIds.get(i), secIds.get(i));
            //标段项目关联表
            secProjectService.createOrUpdateSecProjectByBath(pIds, secProjectList, entitys.stream().map(SecInfo::getSecId).collect(Collectors.toList()), bp.getBpCreateUid());
            //标段资金来源
            if (ValidateUtil.isNotEmpty(ssfms))
                secSourcesFundsService.createOrUpdateSecSourcesFundsByBath(ssfms, ssfSecIds);

            //标段概算申报明细
            if (ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtlList)) {
                secInfoBudgetDeclareDtlService.createOrUpdateSecInfoBudgetDeclareDtl(secInfoBudgetDeclareDtlList);
            }

            // 科目
            if (ValidateUtil.isNotEmpty(secInfoSubjects)) {
                secInfoSubjectService.saveBatch(secInfoSubjects);
            }

            if (ValidateUtil.isNotEmpty(secInfoSubjectExtras)) {
                // refreshSubjectExtraLevelCodes(secInfoSubjectExtras, extraParentTypeMap);
                secInfoSubjectExtraService.saveBatch(secInfoSubjectExtras);
            }
        }
        refershs.forEach(data -> {
            apiUtilsService.refershAutoNo("SecInfo", data);
        });
        return BaseResponse.ok();
    }

    /**
     * 根据父级ID和预算标识计算科目附加项的排序值，并更新排序映射表
     *
     * @param parentId             父科目ID，用于区分附加项的父级关系。
     * @param budgetParent         是否为预算父级标识，若为true表示预算，false表示估算。
     * @param secInfoSubjectExtras 当前科目附加项列表，用于判断相同父级下的附加项数量。
     * @param oldSubjectExtras     原有科目附加项列表，用于获取最大排序值。
     * @return 更新后的排序值，用于标识当前插入科目附加项的排序顺序。
     */
    private Integer resolveSubjectExtraSort(String parentId, boolean budgetParent, List<SecInfoSubjectExtra> secInfoSubjectExtras, List<SecInfoSubjectExtra> oldSubjectExtras) {
        Integer baseSort = getSubjectExtraBaseSort(parentId, budgetParent);
        Integer oldSort = getMaxSubjectExtraSort(oldSubjectExtras, parentId);
        Integer currentSort = getMaxSubjectExtraSort(secInfoSubjectExtras, parentId);
        return Math.max(baseSort, Math.max(ValidateUtil.isNull(oldSort) ? 0 : oldSort, ValidateUtil.isNull(currentSort) ? 0 : currentSort)) + 1;
    }

    /**
     * 获取指定父节点的附加信息主题的最大排序值
     *
     * @param subjectExtras 附加信息主题列表
     * @param parentId      父节点ID，用于筛选匹配的附加信息主题
     * @return 匹配的附加信息主题的最大排序值；如果列表为空或没有匹配的排序值，则返回null
     */
    private Integer getMaxSubjectExtraSort(List<SecInfoSubjectExtra> subjectExtras, String parentId) {
        if (ValidateUtil.isEmpty(subjectExtras)) {
            return null;
        }
        return subjectExtras.stream()
                .filter(e -> isSameSubjectExtraParent(e.getParentId(), parentId))
                .map(SecInfoSubjectExtra::getSort)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(null);
    }

    /**
     * 获取指定科目附加基础分类的最大排序值
     *
     * @param parentId     科目父级ID，用于确定查询范围
     * @param budgetParent 是否为预算父节点标志，true表示预算父节点，false表示非预算父节点
     * @return 返回最大排序值，如果parentId为空或无有效排序值则返回null
     */
    private Integer getSubjectExtraBaseSort(String parentId, Boolean budgetParent) {
        if (ValidateUtil.isEmpty(parentId) || ValidateUtil.isNull(budgetParent)) {
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

    /**
     * 判断两个父类ID是否表示相同的科目。
     * 如果两个父类ID均为空，则返回true。
     * 如果其中一个为空，另一个不为空，则返回false。
     * 如果两个ID均不为空，通过不区分大小写的比较判断是否相等。
     *
     * @param sourceParentId 源父类ID
     * @param targetParentId 目标父类ID
     * @return 若两个父类ID为相同科目则返回true，否则返回false
     */
    private boolean isSameSubjectExtraParent(String sourceParentId, String targetParentId) {
        if (ValidateUtil.isEmpty(sourceParentId) || ValidateUtil.isEmpty(targetParentId)) {
            return ValidateUtil.isEmpty(sourceParentId) && ValidateUtil.isEmpty(targetParentId);
        }
        return sourceParentId.equalsIgnoreCase(targetParentId);
    }

    /**
     * 构建旧科目额外信息的映射表。
     * 根据输入的旧科目额外信息列表，按照排序规则对其进行排序，
     * 并将其按唯一键分组后以双端队列的形式存储到映射表中。
     *
     * @param oldSubjectExtras 旧的科目额外信息列表，可能为空。
     * @return 一个映射表，键为科目额外信息的唯一键，值为包含排序后的科目额外信息的双端队列。
     * 如果输入列表为空，则返回一个空的映射表。
     */
    private Map<String, Deque<SecInfoSubjectExtra>> buildOldSubjectExtraMap(List<SecInfoSubjectExtra> oldSubjectExtras) {
        if (ValidateUtil.isEmpty(oldSubjectExtras)) {
            return new HashMap<>();
        }
        return oldSubjectExtras.stream()
                .sorted(Comparator.comparing(
                        SecInfoSubjectExtra::getSort,
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

    /**
     * 从指定的旧科目附加信息映射中查询并移除符合条件的科目附加信息。
     *
     * @param oldSubjectExtraMap 包含旧科目附加信息的映射，键为唯一标识，值为与该标识对应的科目附加信息队列
     * @param extra              当前的科目附加信息，用于生成唯一标识匹配旧记录
     * @return 如果匹配到符合条件的科目附加信息，则返回该对象；否则返回 null
     */
    private SecInfoSubjectExtra pollOldSubjectExtra(Map<String, Deque<SecInfoSubjectExtra>> oldSubjectExtraMap, SecInfoSubjectExtra extra) {
        Deque<SecInfoSubjectExtra> oldExtras = oldSubjectExtraMap.get(getSubjectExtraUniqueKey(extra));
        return ValidateUtil.isEmpty(oldExtras) ? null : oldExtras.pollFirst();
    }

    /**
     * 获取科目额外信息的唯一键。
     * 唯一键由父类ID和名称组成，以竖线分隔。
     * 如果名称为空，则使用空字符串代替。
     *
     * @param extra 科目额外信息对象
     * @return 唯一键字符串
     */
    private String getSubjectExtraUniqueKey(SecInfoSubjectExtra extra) {
        return getSubjectExtraParentKey(extra.getParentId()) + "|"
                + (ValidateUtil.isEmpty(extra.getSeName()) ? "" : extra.getSeName());
    }

    /**
     * 刷新科目的附加层级代码。
     * 根据传入的科目附加信息列表和附加类型映射表，重新计算并设置每个附加信息的层级代码。
     *
     * @param secInfoSubjectExtras 科目附加信息列表，用于获取待处理的附加层级数据。
     * @param extraParentTypeMap   父级类型映射表，键为附加层级的父级标识，值为布尔值，表示是否启用特定的父级类型逻辑。
     */
    private void refreshSubjectExtraLevelCodes(List<SecInfoSubjectExtra> secInfoSubjectExtras, Map<String, Boolean> extraParentTypeMap) {
        Map<String, List<SecInfoSubjectExtra>> parentExtraMap = secInfoSubjectExtras.stream()
                .collect(Collectors.groupingBy(e -> getSubjectExtraParentKey(e.getParentId())));
        parentExtraMap.forEach((parentKey, extras) -> {
            extras.sort(Comparator.comparing(
                    SecInfoSubjectExtra::getSort,
                    Comparator.nullsLast(Integer::compareTo)
            ));
            int baseLevelCodeSort = getSubjectExtraBaseSort(extras.get(0).getParentId(), extraParentTypeMap.get(parentKey));
            for (int i = 0; i < extras.size(); i++) {
                Integer levelCodeSort = baseLevelCodeSort + i + 1;
                extras.get(i).setLevelCode(levelCodeSort + " " + levelCodeSort);
            }
        });
    }

    /**
     * 获取科目附加项的父级键。
     * 如果父类ID为空，则返回空字符串。
     * 否则，返回父类ID。
     *
     * @param parentId 父类ID
     * @return 父级键
     */
    private String getSubjectExtraParentKey(String parentId) {
        return ValidateUtil.isEmpty(parentId) ? "" : parentId;
    }

    @Override
    public BaseResponse getSecInfoList(SecInfoRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Sec_Project", uId, "Sec_Info_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<SecInfoRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("secYear").put("secBiddingType").put("secConType").put("secConTypes").put("secType")
                .put("secName").put("secCode").put("pName").put("secPid").put("secFlowState").put("pBuildModel").put("pType")
                .put("secOrganizeForm").put("secApprovalTitle").put("secApprovalNum").put("secDeterminType").put("bpId").put("bpState").put("secContractingType")
                .put("filterCompleted").put("tendereeName").put("secCreateUname").put("secTypes").put("secBiddingTypes").put("pId")
                .put("excludeCancel", sysUserService.ShowCancelData(uId, "sec.Sec_Create_Uid", "Sec_Flow_State"))
                .put("secConsortium").put("secSubpackage").put("secEnterprise").put("secImport").put("secProjectNature").put("secSourceFund")
                .put(request.getSecMoneys(), "scMoneyStart", "scMoneyEnd").put("excludeCostCsf").put("purchaseNumber")
                .put("pCampus").put("unitName").put("amount").put("commentaryTime")
                .put(request.getAmounts(), "amountStart", "amountEnd").put("commentaryTimeStart").put("commentaryTimeEnd")
                .put(request.getSecCreateTimes(), "secCreateTimeStart", "secCreateTimeEnd")
                .put("filterWinNotice", ValidateUtil.isNull(request.getFilterWinNotice()) ? Boolean.FALSE : request.getFilterWinNotice());
        if (!ValidateUtil.isEmpty(request.getToken())) {
            SysUser user = sysUserService.getById(uId);
            params.put("cId", user != null && user.getCId() != null ? user.getCId() : null);
        } else
            params.put("cId", null);
        List<SecInfoResponse> datas = baseMapper.getSecInfoList(params.getQuery());
        Integer total = baseMapper.getSecInfoListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }


    @Override
    public BaseResponse getSecInfoListNoAuth(SecInfoRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecInfoResponse> datas = baseMapper.getSecInfoList(params);
        Integer total = baseMapper.getSecInfoListCount(params);
        return BaseResponse.ok(datas, total);
    }

    /**
     * 删除标段，并清理询价调研等子表和关联文件。
     *
     * @param secId 标段ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecInfoById(String secId) {
        SecInfo entity = baseMapper.selectById(secId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        //TODO:校验外键表
        try {
            secSourcesFundsService.delSecSourcesFundsBySecId(secId);
            secProcurementDetailsService.deleteBySecId(secId);
            secFundsService.deleteBySecId(secId);
            quotationRequestService.deleteBySecId(secId);
            baseMapper.deleteById(secId);
        } catch (DataIntegrityViolationException ex) {
            return BaseResponse.fail(ResMsg.delAssociated);
        }
        sysFileService.deleteFileById(secId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecInfoByPId(SecInfoRequest request, String uId) {
        if (ValidateUtil.isEmpty(request.getSecPid()))
            return BaseResponse.fail(ResMsg.notReqParam);
        Map<String, Object> params = getSecinfoParams(request, uId);
        Integer total = baseMapper.getSecInfoByPIdCount(params);
        if (total <= 0) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        List<SecInfoResponse> datas = baseMapper.getSecInfoByPId(params);

        List<SecInfo> allSecList = list(
                Wrappers.<SecInfo>lambdaQuery().eq(SecInfo::getSecPid, request.getSecPid())
        );
        Map<String, SecInfo> secMap = allSecList.stream()
                .collect(Collectors.toMap(SecInfo::getSecId, Function.identity(), (a, b) -> a));
        datas.forEach(vo -> vo.setSecParentPath(getParentPath(vo.getSecId(), secMap)));

        return BaseResponse.ok(datas, total);
    }

    private String getParentPath(String secId, Map<String, SecInfo> secMap) {
        if (ValidateUtil.isEmpty(secId) || secMap == null) {
            return "";
        }
        List<String> names = new ArrayList<>();
        SecInfo current = secMap.get(secId);

        while (current != null && current.getSecParentId() != null && !"0".equals(current.getSecParentId())) {
            SecInfo parent = secMap.get(current.getSecParentId());
            if (parent == null) {
                break;
            }
            names.add(parent.getSecName());
            current = parent;
        }

        Collections.reverse(names);
        return String.join("/", names);
    }

    @Override
    public BaseResponse getCjSecInfoByPId(SecInfoRequest request, String uId) {
        if (ValidateUtil.isEmpty(request.getSecPid()))
            return BaseResponse.fail(ResMsg.notReqParam);
        Map<String, Object> params = getSecinfoParams(request, uId);
        params.put("filterBidType", true);
        List<SecInfoResponse> datas = baseMapper.getSecInfoByPId(params);
        Integer total = baseMapper.getSecInfoByPIdCount(params);
        return BaseResponse.ok(datas, total);
    }

    private Map<String, Object> getSecinfoParams(SecInfoRequest request, String uId) {
        QueryMap<SecInfoRequest> params = new QueryMap<>(request);
        params.put("secPid").put("secName").put("secFlowState").put("isbdType").put("secBiddingType").put("secSectionClassify")
                .put("secTypes").put("secBiddingTypes")
                .put("filterWinNotice", request.getFilterWinNotice() == null ? Boolean.FALSE : request.getFilterWinNotice());
        //20220608(zhang):新增筛选条件，开启中标通知书筛选时，当前用户是外部用户时，只查询出中标单位是当前用户所属单位的工程信息，内部用户则不进行过滤
        SysUser user = sysUserService.getById(uId);
        if (user != null) {
            params.put("cId", !ValidateUtil.isEmpty(user.getCId()) ? user.getCId() : null);
            if (!ValidateUtil.isEmpty(user.getUType()) && user.getUType().equals("2")) {
                params.put("uType", user.getUType());
            } else {
                params.put("uType", null);
            }
        } else {
            params.put("uType", null);
            params.put("cId", null);
        }
        return params.getQuery();
    }

    /**
     * 查询标段详情及询价调研等子表明细。
     *
     * @param secId 标段ID
     * @return 标段详情
     */
    @Override
    public BaseResponse getSecInfoById(String secId) {
        SecInfoResponse data = baseMapper.getSecInfoById(secId);
        if (data != null) {
            data.setSourcesFundsModels(secSourcesFundsService.getSecSourcesFundsBySecId(secId));
            data.setSecProcurementDetails(secProcurementDetailsService.selectBySecId(secId));
            data.setSecFunds(secFundsService.selectBySecId(secId));
            data.setQuotationRequest(quotationRequestService.selectBySecId(secId));
        }
        return BaseResponse.ok(data, 0);
    }

    /**
     * 按请求与实体的对应顺序同步询价调研子表。
     *
     * @param requests 标段请求列表
     * @param entities 已保存的标段实体列表
     * @param recreate 是否随标段重建明细主键
     */
    private void saveQuotationRequests(List<SecInfoRequest> requests, List<SecInfo> entities, boolean recreate) {
        for (int i = 0; i < entities.size(); i++) {
            List<QuotationRequestDTO> details = requests.get(i).getQuotationRequest();
            if (recreate && details != null) {
                List<QuotationRequestDTO> copies = new ArrayList<>();
                for (QuotationRequestDTO detail : details) {
                    if (detail == null) {
                        throw new IllegalArgumentException("询价调研明细不能为空");
                    }
                    QuotationRequestDTO copy = new QuotationRequestDTO();
                    BeanUtils.copyProperties(detail, copy);
                    copy.setId(null);
                    copies.add(copy);
                }
                details = copies;
            }
            quotationRequestService.createOrUpdate(details, entities.get(i).getSecId());
        }
    }

    @Override
    public void createSecInfoSysFileByBath(SecInfoRequest request) {
        LambdaQueryWrapper<SecInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecInfo::getBpId, request.getBpId());
        wrapper.and(w -> w.notIn(SecInfo::getSecCostType, "02", "03")
                .or().isNull(SecInfo::getSecCostType));
        wrapper.and(w -> w.ne(SecInfo::getSecSectionClassify, "/")
                .or().isNull(SecInfo::getSecSectionClassify));
        List<SecInfo> datas = baseMapper.selectList(wrapper);

        if (!CollectionUtils.isEmpty(datas)) {
            List<SysFile> filePojos = new ArrayList<>();
            //川大定制
            for (SecInfo sec : datas) {
                if (request.getSecFlowState() != null && !request.getSecFlowState().equals(sec.getSecFlowState())) {
                    sec.setSecFlowState(request.getSecFlowState());
                }
                filePojos.add(sysFileService.structureFile(sec.getSecId(), request.gettId(),
                        sec.getSecFlowState() + "", sec.getSecCreateUid(), sec.getSecCreateUname()
                        , sec.getSecCreateTime(), null, request.getFileTitle(), request.getFileCode(),
                        GenerateUUID.getUUID(), sec.getSecId(), request.gettCode()));
            }
            if (filePojos.size() > 0)
                sysFileService.saveBatch(filePojos);
        }
    }

    @Override
    public BaseResponse getSecInfoListGroupByProject(SecInfoRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Sec_Project", uId, "Sec_Info_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<SecInfoRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("secYear").put("secBiddingType").put("secConType").put("secType")
                .put("secName").put("secCode").put("pName").put("secPid").put("secFlowState");
        if (ValidateUtil.isNotEmpty(request.getToken())) {
            SysUser user = sysUserService.getById(uId);
            params.put("cId", user != null && user.getCId() != null ? user.getCId() : null);
        } else
            params.put("cId", null);
        List<Map<String, String>> map = secProjectService.getSecInfoListGroupByProject(params.getQuery());
        if (map != null && map.size() > 0) {
            List<String> secIds = new ArrayList<>();
            for (Map<String, String> data : map) {
                if (data.containsKey("secIds"))
                    secIds.addAll(Arrays.asList(data.get("secIds").split(",")));
            }
            List<SecInfoResponse> secInfos = baseMapper.getSecInfoListGroupByProject(secIds);
            List<Map<String, Object>> datas = new ArrayList<>();
            for (Map<String, String> data : map) {
                Map<String, Object> res = new HashMap<>();
                List<SecInfoResponse> secs = secInfos.stream().filter(n -> Arrays.asList(data.get("secIds").split(",")).contains(n.getSecId())).collect(Collectors.toList());
                if (data.containsKey("pName"))
                    res.put("pName", data.get("pName").replaceAll(",", ";"));
                res.put("secInfos", secs);
                datas.add(res);
            }
            Integer total = secProjectService.getSecInfoListGroupByProjectCount(params.getQuery());
            return BaseResponse.ok(datas, total);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateSecBiddingTypeById(SecInfoRequest request) {
        if (ValidateUtil.isAnObjEmpty(request.getSecId(), request.getSecBiddingType()))
            return BaseResponse.fail(ResMsg.notReqParam);
        SecInfo entity = baseMapper.selectById(request.getSecId());
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        entity.setSecBiddingType(request.getSecBiddingType());
        baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getSecId());
    }

    @Override
    public BaseResponse getSecProjectList(SecProjectModel request, String uId) {
        QueryMap<SecProjectModel> params = getSecProjectFilter(request, uId);
        IPage<SecProjectModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecProjectModel> datas = baseMapper.getSecProjectList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private QueryMap<SecProjectModel> getSecProjectFilter(SecProjectModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Pre_Project", uId, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        QueryMap<SecProjectModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pCode").put("pCreateUname").put("pUseUnit")
                .put("pCompetentDepartmentName").put("state").put("pCreateUid").put("pBuildModel").put("pImpCompany")
                .put(request.getSecTotals(), "secTotalStart", "secTotalEnd").put("pType")
                .put(request.getSecMoneyTotals(), "secMoneyTotalStart", "secMoneyTotalEnd")
                .put("isNew");
        return params;
    }

    @Override
    public BaseResponse getSecProjectStatisticsList(SecProjectModel request, String uId) {
        QueryMap<SecProjectModel> params = getSecProjectFilter(request, uId);
        IPage<SecProjectModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecProjectModel> datas = baseMapper.getSecProjectList(page, params.getQuery());
        BigDecimal secMoneyTotalSum = baseMapper.getSecProjectListSum(params.getQuery());
        SecProjectStatisticsVo res = new SecProjectStatisticsVo();
        res.setSecMoneyTotalSum(secMoneyTotalSum);
        res.setData(datas);
        return BaseResponse.ok(res, (int) page.getTotal());
    }


    @Override
    public BaseResponse getSecInfoListOne2Many(SecInfoRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecInfoResponse> datas = baseMapper.getSecInfoListOne2Many(params);
        Integer total = baseMapper.getSecInfoListOne2ManyCount(params);
        String conf = sysConfigService.getConfigByCode("secNodeStatisticsConf");
        Map<String, String> corrs = JacksonUtils.parseObject(sysConfigService.getConfigByCode("secNodeTableCorrStateField"), Map.class);
        if (!CollectionUtils.isEmpty(datas)) {
            if (ValidateUtil.isNotEmpty(conf) || corrs == null) {
                Map<String, Object> map = JacksonUtils.parseObject(conf, Map.class);
                if (map != null) {
                    List<SecInfoResponse> resDate = new ArrayList<>();
                    BaseResponse bpmRes = bpmFeign.getBiddingProcedures(datas.stream().map(SecInfoResponse::getSecId).collect(Collectors.toList()));
                    Map<String, Object> nodeStates = JacksonUtils.parseObject(JacksonUtils.toJSONString(bpmRes.getData()), Map.class);
                    for (SecInfoResponse data : datas) {
                        //nodes对应表明列表 通过当前标段ID去查询数据
                        List<String> nodes = JacksonUtils.parseArray(JacksonUtils.toJSONString(map.get(data.getSecBiddingType())), String.class);
                        if (!CollectionUtils.isEmpty(nodes)) {
                            data.setNodeTotal(nodes.size());
                            //region 获取业务表名 状态字段名 标段ID字段名
                            List<SecNodeTableAndFieldDto> tbs = new ArrayList<>();
                            for (String node : nodes) {
                                String str = corrs.get(node);
                                if (ValidateUtil.isNotEmpty(str)) {
                                    String[] sp = str.split("\\|");
                                    SecNodeTableAndFieldDto nodeTF = new SecNodeTableAndFieldDto();
                                    nodeTF.setTable(node);
                                    nodeTF.setStateField(sp[0]);
                                    nodeTF.setSecIdField(sp[1]);
                                    tbs.add(nodeTF);
                                }
                            }
                            //endregion
                            if (!CollectionUtils.isEmpty(tbs)) {
                                Map<String, Integer> stateMap = baseMapper.getNodeTableState(tbs, data.getSecId());
                                //完成
                                int nodeComplete = 0;
                                //审批中
                                int nodeApproval = 0;
                                if (stateMap != null) {
                                    for (Map.Entry<String, Integer> entry : stateMap.entrySet()) {
                                        if (!entry.getValue().equals(ApprovalStatusEnum.Not_Started.getIndex())) {
                                            if (entry.getValue().equals(ApprovalStatusEnum.Already_Over.getIndex()))
                                                nodeComplete = +1;
                                            else nodeApproval += 1;
                                        }
                                    }
                                }
                                data.setNodeComplete(nodeComplete);
                                data.setNodeApproval(nodeApproval);
                                data.setNodeUnStart(data.getNodeTotal() - nodeComplete - nodeApproval);
                            }
                        }
                        if (nodeStates != null) {
                            Map<String, Object> current = new HashMap<>();
                            current.put("name", nodeStates.get(data.getSecId()));
                            current.put("state", nodeStates.get(data.getSecId() + "_state"));
                            data.setCurrentNode(current);
                        }
                        resDate.add(data);
                    }
                    return BaseResponse.ok(resDate, total);
                }
            }
        }
        return BaseResponse.ok(datas, total);
    }

    /**
     * 按标段ID批量查询标段信息，并批量加载询价调研明细。
     *
     * @param secIds 标段ID集合
     * @return 标段信息列表，各标段无询价明细时返回空列表
     */
    @Override
    public List<SecInfoDto> getSecInfoBySecInfoIds(Collection<String> secIds) {
        if (ValidateUtil.isEmpty(secIds)) return new ArrayList<>();
        List<SecInfoDto> datas = baseMapper.getSecInfoBySecInfoIds(secIds);
        if (CollectionUtils.isEmpty(datas)) {
            return datas;
        }
        List<String> ids = datas.stream().map(SecInfoDto::getSecId).collect(Collectors.toList());
        Map<String, List<QuotationRequestDTO>> detailsBySecId = quotationRequestService.list(
                        Wrappers.<QuotationRequest>lambdaQuery().in(QuotationRequest::getSecId, ids)
                                .orderByAsc(QuotationRequest::getCreateTime, QuotationRequest::getId))
                .stream().map(entity -> {
                    QuotationRequestDTO dto = new QuotationRequestDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.groupingBy(QuotationRequestDTO::getSecId));
        datas.forEach(data -> data.setQuotationRequest(
                detailsBySecId.getOrDefault(data.getSecId(), Collections.emptyList())));
        return datas;
    }

    @Override
    public BaseResponse updateApproveStatusSecInfo(SecInfoRequest request) {
        String secId = request.getSecId();
        if (StrUtil.isBlank(secId)) {
            throw new JPMException("标段ID不能为空");
        }
        SysFile sysFile = sysFileService.getById(request.getSecId());
        String fileflowstatus = sysFile.getFileflowstatus();
        if ("7".equals(fileflowstatus)) {
            List<ConContract> contractList = conContractMapper.getConContractBySecId(secId);

            List<ConEstimateModel> conEstimateModelList = new ArrayList<>();
            List<SecInfoBudgetModel> secInfoBudgetList = secInfoBudgetService.getSecInfoBudgetListBySecId(secId);
            if (CollectionUtil.isNotEmpty(secInfoBudgetList)) {
                for (SecInfoBudgetModel secInfoBudget : secInfoBudgetList) {
                    ConEstimateModel conEstimateModel = new ConEstimateModel();
                    conEstimateModel.setCeCost(secInfoBudget.getSibCost());
                    conEstimateModel.setCeCostName(secInfoBudget.getSibCostName());
                    conEstimateModel.setCeMainCost(secInfoBudget.getSibMainCost());
                    conEstimateModel.setCeMainCostName(secInfoBudget.getSibMainCostName());
                    conEstimateModel.setCeCompany(secInfoBudget.getSibCompany());
                    conEstimateModel.setCeQuantities(secInfoBudget.getSibQuantities());
                    conEstimateModel.setCeUnitPrice(secInfoBudget.getSibUnitPrice());
                    conEstimateModel.setCeInvestment(secInfoBudget.getSibInvestment());
                    conEstimateModel.setCeRemarks(secInfoBudget.getSibRemarks());
                    conEstimateModel.setCeTotalExpenses(secInfoBudget.getSibTotalExpenses());
                    conEstimateModel.setCeExpenseType(secInfoBudget.getSibExpenseType());
                    conEstimateModel.setCeStageType(secInfoBudget.getSibStageType());
                    conEstimateModelList.add(conEstimateModel);
                }
            }

            if (CollectionUtil.isNotEmpty(contractList)) {
                for (ConContract conContract : contractList) {
                    conEstimateService.createOrUpdateConEstimate(conEstimateModelList, conContract.getConId());
                }
            }

        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse secInfoCanHandle(String secId) {
        List<SecInfoChange> secInfoChangeList = secInfoChangeMapper.getSecInfoChangeListById(Arrays.asList(3, 7), secId);
        //true可以办理，false不可办理
        boolean flag = true;
        if (CollectionUtil.isNotEmpty(secInfoChangeList)) {
            flag = false;
        }
        return BaseResponse.ok(flag);
    }

    @Override
    public BaseResponse getSecInfoAmountTree(SecInfoAmountDto planAmountDto) {
        String pId = planAmountDto.getpId();
        String ppId = planAmountDto.getPpId();
        Map<String, Float> amountMap = secInfoBudgetDeclareDtlService.getAmountMap(pId, ppId);


        if (StrUtil.isBlank(pId) && StrUtil.isBlank(ppId)) {
            throw new JPMException("请选择项目或者招采计划");
        }

        List<SecInfoAmountBudgetDeclareDtlVo> declareDtlVos = new ArrayList<>();
        if (StrUtil.isNotBlank(ppId)) {//如果招采计划不为空，先查询招采计划下的概算
            List<SecProcurementPlanAmount> planAmountList = secProcurementPlanAmountService.getInvBudgetDeclareDtlByPpid(ppId);
            if (CollectionUtil.isNotEmpty(planAmountList)) {
                for (SecProcurementPlanAmount secProcurementPlanAmount : planAmountList) {
                    SecInfoAmountBudgetDeclareDtlVo declareDtlVo = new SecInfoAmountBudgetDeclareDtlVo();
                    BeanUtil.copyProperties(secProcurementPlanAmount, declareDtlVo);
                    declareDtlVo.setBddTotal(secProcurementPlanAmount.getSelectAmount());//选择的钱作为造价
                    declareDtlVo.setBddLeafNode(1);
                    if (amountMap.containsKey(secProcurementPlanAmount.getBddId())) {
                        Float planAmount = amountMap.get(declareDtlVo.getBddId());
                        declareDtlVo.setResidueProportion(100 - planAmount);
                    } else {
                        declareDtlVo.setResidueProportion(100F);
                    }
                    declareDtlVos.add(declareDtlVo);
                }
            }

        } else {
            List<InvBudgetDeclareDtl> invBudgetDeclareDtls = invBudgetDeclareDtlService.getInvBudgetDeclareDtlByPid(pId);
            if (CollectionUtil.isNotEmpty(invBudgetDeclareDtls)) {
                for (InvBudgetDeclareDtl invBudgetDeclareDtl : invBudgetDeclareDtls) {
                    SecInfoAmountBudgetDeclareDtlVo declareDtlVo = new SecInfoAmountBudgetDeclareDtlVo();
                    BeanUtil.copyProperties(invBudgetDeclareDtl, declareDtlVo);
                    declareDtlVos.add(declareDtlVo);
                }
                List<SecInfoAmountBudgetDeclareDtlVo> firstList = new ArrayList<>();
                firstList = declareDtlVos.stream().filter(e -> "0".equals(e.getBddParentId())).collect(Collectors.toList());
                for (SecInfoAmountBudgetDeclareDtlVo budgetDeclareDtlVo : firstList) {
                    budgetDeclareDtlVo.setBddLevel(1);
                    budgetDeclareDtlVo.setBddPath(budgetDeclareDtlVo.getBddId());
                    if (amountMap.containsKey(budgetDeclareDtlVo.getBddId())) {
                        Float planAmount = amountMap.get(budgetDeclareDtlVo.getBddId());
                        budgetDeclareDtlVo.setResidueProportion(100 - planAmount);
                    }
                    List<SecInfoAmountBudgetDeclareDtlVo> childList = getChildList(budgetDeclareDtlVo, declareDtlVos, amountMap);
                    if (CollectionUtil.isEmpty(childList)) {
                        if (!amountMap.containsKey(budgetDeclareDtlVo.getBddId())) {
                            budgetDeclareDtlVo.setResidueProportion(100F);
                        }
                        budgetDeclareDtlVo.setBddLeafNode(1);
                    } else {
                        budgetDeclareDtlVo.setBddLeafNode(0);
                    }
                    budgetDeclareDtlVo.setChildren(childList);
                }

            }
        }

        return BaseResponse.ok(declareDtlVos);
    }

    @Override
    public BaseResponse getSecInfoTZClassInfo(List<String> projectIds, String uid) {
        List<ClassInfoAnnotation> projectClassInfoAnnotations = getClassInfoAnnotations(projectIds);
        log.info("projectClassInfoAnnotations:{}", JacksonUtils.toJSONString(projectClassInfoAnnotations));
        return BaseResponse.ok(projectClassInfoAnnotations);
    }

    @Override
    public BaseResponse getInvestmentSecInfo(InvestmentSecInfoRequest request) {
        if (StrUtil.isBlank(request.getIsbdSectionName())) {
            return BaseResponse.ok(null);
        }
        Map<String, Object> params = new HashMap<>(16);
        params.put("projectId", request.getProjectId());
        params.put("isbdSectionName", request.getIsbdSectionName());
        params.put("ieId", request.getIeId());
        params.put("ieDetailId", request.getIeDetailId());
        params.put("ibId", request.getIbId());
        params.put("ibDetailId", request.getIbDetailId());
        SecInfoResponse secInfoResponse = baseMapper.getInvestmentSecInfo(params);
        if (secInfoResponse == null) {//投资控制估算、概算可能从原表或者变更表取id。所以当通过id查不到，需要通过标段名称查询。
            params = new HashMap<>(16);
            params.put("projectId", request.getProjectId());
            params.put("isbdSectionName", request.getIsbdSectionName());
            secInfoResponse = baseMapper.getInvestmentSecInfo(params);
        }
        return BaseResponse.ok(secInfoResponse);
    }

    private List<ClassInfoAnnotation> getClassInfoAnnotations(List<String> projectIds) {
        List<ClassInfoAnnotation> projectClassInfoAnnotations;

        List<SecInfoTZResponse> secInfoTZResponseList = baseMapper.getValueByCodeWithPid(projectIds);
        try {
            projectClassInfoAnnotations = ClassInfoUtils.getClassInfo(SecBiddingPlanTZRequest.class.getName());
            List<Object> dictionaryValues = new ArrayList<>();
            for (ClassInfoAnnotation classInfoAnnotation : projectClassInfoAnnotations) {
                dictionaryValues = new ArrayList<>();
                if (!classInfoAnnotation.getType().equals("date")) {
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                        if (CollectionUtil.isNotEmpty(secInfoTZResponseList)) {
                            for (SecInfoTZResponse secInfoTZRespose : secInfoTZResponseList) {
                                Class<SecInfoTZResponse> secInfoTZResposeClass = SecInfoTZResponse.class;
                                Field[] declaredFields = secInfoTZResposeClass.getDeclaredFields();
                                for (int i = 0; i < declaredFields.length; i++) {
                                    Field field = declaredFields[i];
                                    field.setAccessible(true);
                                    String fieldName = field.getName();
                                    if (fieldName.equals(classInfoAnnotation.getDbValue())) {
                                        Object o = field.get(secInfoTZRespose);
                                        if (o != null && !dictionaryValues.contains(o)) {
                                            dictionaryValues.add(o);
                                        }
                                    }
                                }
                            }
                        }
                        classInfoAnnotation.setDictionaryValues(dictionaryValues);
                    }
                }
            }
            log.info("projectClassInfoAnnotations:{}", JacksonUtils.toJSONString(projectClassInfoAnnotations));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //no正序排列
        projectClassInfoAnnotations = projectClassInfoAnnotations.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        return projectClassInfoAnnotations;
    }

    private List<SecInfoAmountBudgetDeclareDtlVo> getChildList(SecInfoAmountBudgetDeclareDtlVo parentBudgetDeclareDtlVo, List<SecInfoAmountBudgetDeclareDtlVo> budgetDeclareDtlVos, Map<String, Float> amountMap) {
        List<SecInfoAmountBudgetDeclareDtlVo> declareDtlVos = new ArrayList<>();
        String bddId = parentBudgetDeclareDtlVo.getBddId();
        String bddPath = parentBudgetDeclareDtlVo.getBddPath();
        Integer bddLevel = parentBudgetDeclareDtlVo.getBddLevel();
        if (CollectionUtil.isNotEmpty(budgetDeclareDtlVos)) {
            declareDtlVos = budgetDeclareDtlVos.stream().filter(e -> bddId.equals(e.getBddParentId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(declareDtlVos)) {
                for (SecInfoAmountBudgetDeclareDtlVo child : declareDtlVos) {
                    child.setBddLevel(bddLevel + 1);
                    child.setBddPath(bddPath + "/" + child.getBddId());
                    if (amountMap.containsKey(child.getBddId())) {
                        Float planAmount = amountMap.get(child.getBddId());
                        child.setResidueProportion(100 - planAmount);
                    }
                    List<SecInfoAmountBudgetDeclareDtlVo> childList = getChildList(child, budgetDeclareDtlVos, amountMap);
                    if (CollectionUtil.isEmpty(childList)) {
                        if (!amountMap.containsKey(child.getBddId())) {
                            child.setResidueProportion(100F);
                        }
                        child.setBddLeafNode(1);
                    } else {
                        child.setBddLeafNode(0);
                    }
                    child.setChildren(childList);
                }
            }
        }
        return declareDtlVos;

    }

    private Map<String, Object> getFilterMap(SecInfoRequest request) {
        QueryMap<SecInfoRequest> params = new QueryMap<>(request);
        params.put("secBiddingType").put("secYear").put("secConType").put("secType").put("secPricingMethod").put("bpId").put("bpState")
                .put("secName").put("secCode").put("pName").put("secPid").put("secFlowState").put("secRemark").put("secTypes")
                .put("secCreateUname").put("filterWinNotice").put("secDeterminType").put("secOrganizeForm").put("pType").put("secBiddingTypes")
                .put("tendereeName").put("secTradingPlace").put("secPlace").put("secRemark").put("pBuildModel").put("filterCompleted")
                .put("secConsortium").put("secSubpackage").put("secEnterprise").put("secImport").put("secProjectNature").put("secSourceFund")
                .put(request.getSecMoneys(), "scMoneyStart", "scMoneyEnd")
                .put(request.getSecStartTimes(), "secStartTimeStart", "secStartTimeEnd")
                .put(request.getSecEndTimes(), "secEndTimeStart", "secEndTimeEnd")
                .put(request.getSecCreateTimes(), "secCreateTimeStart", "secCreateTimeEnd")
                .put(request.getSecBiddingCycles(), "secBiddingCycleStart", "secBiddingCycleEnd");
        return params.getQuery();
    }


    @Override
    public List<SecInfo> getSecInfoByBpId(String bpId) {
        LambdaQueryWrapper<SecInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecInfo::getBpId, bpId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean existDuplicateName(String name, String secId) {
        if (ValidateUtil.isEmpty(name)) return true;
        LambdaQueryWrapper<SecInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecInfo::getSecName, name);
        if (ValidateUtil.isNotEmpty(secId))
            wrapper.ne(SecInfo::getSecId, secId);
        wrapper.ne(SecInfo::getSecFlowState, ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }


    @Override
    public BaseResponse getSecBiddingTypeByIds(String secIds) {
        if (ValidateUtil.isEmpty(secIds)) {
            return BaseResponse.fail("secId不能为空");
        }

        // 分割多个 ID 并去除空格
        List<String> idList = Arrays.stream(secIds.split(","))
                .map(String::trim)
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());

        LambdaQueryWrapper<SecInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SecInfo::getSecId, idList); // 使用 IN 查询
        List<SecInfo> secInfoList = baseMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(secInfoList)) {
            return BaseResponse.fail("没有找到对应的数据");
        }

        // 分类处理
        List<String> type06List = new ArrayList<>();
        List<String> typeList = new ArrayList<>();

        for (SecInfo secInfo : secInfoList) {
            String biddingType = secInfo.getSecBiddingType();
            if ("06".equals(biddingType)) {
                type06List.add(secInfo.getSecId());
            } else {
                typeList.add(secInfo.getSecId());
            }
        }

        // 存放查询结果
        List<SecInquiryRecordVo> inquiryRecords = new ArrayList<>();
        List<SecWinNoticeResponse> winNotices = new ArrayList<>();

        // 调用接口获取数据
        if (!CollectionUtils.isEmpty(type06List)) {
            // 获取 "06" 类型的数据
            inquiryRecords = secInquiryRecordMapper.getSecInquiryRecordBySecIds(type06List);
        }

        if (!CollectionUtils.isEmpty(typeList)) {
            // 获取其他类型的数据
            winNotices = secWinNoticeMapper.getLatestApproveWinNotices(typeList);
        }


        return buildUnifiedResult(inquiryRecords, winNotices);
    }

    public BaseResponse buildUnifiedResult(List<SecInquiryRecordVo> inquiryRecords,
                                              List<SecWinNoticeResponse> winNotices) {

        List<Map<String, Object>> result = new ArrayList<>();

        // 处理 inquiryRecords
        if (inquiryRecords != null && !inquiryRecords.isEmpty()) {
            for (SecInquiryRecordVo record : inquiryRecords) {
                Map<String, Object> item = new HashMap<>();
                item.put("unit", record.getSirIntendedUnit());
                item.put("unitName", record.getSirIntendedUnitName());
                item.put("amount", record.getSirConMoney());
                item.put("secId", record.getSecId());
                result.add(item);
            }
        }

        // 处理 winNotices
        if (winNotices != null && !winNotices.isEmpty()) {
            for (SecWinNoticeResponse notice : winNotices) {
                Map<String, Object> item = new HashMap<>();
                item.put("unit", notice.getWnWinUnit());
                item.put("unitName", notice.getWnWinUnitName());
                item.put("amount", notice.getWnAmount());
                item.put("secId", notice.getSecId());
                result.add(item);
            }
        }

        return BaseResponse.ok(result);
    }


    @Override
    public BaseResponse getSecInfoGDGY(SecInfoRequest request) {


        // 调用 Mapper 查询数据
        IPage<SecInfoResponse> page = new Page<>(request.getPage(), request.getSize());
        IPage<SecInfoResponse> secInfoListGDGY = baseMapper.getSecInfoListGDGY(page, request);
        List<SecInfoResponse> responseList = secInfoListGDGY.getRecords();

        if (CollectionUtils.isEmpty(responseList)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        // 从所有 Sec_Publicity_Results 中提取中标金额并求和
        BigDecimal bidAmountSum = responseList.stream()
                .filter(info -> !CollectionUtils.isEmpty(info.getSecPublicityResults()))
                .flatMap(info -> info.getSecPublicityResults().stream())
                .map(SecPublicityResultsResponse::getPrBidAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 从所有 Sec_Info 中提取预算金额并求和
        BigDecimal budgetAmountSum = responseList.stream()
                .map(SecInfoResponse::getSecMoney)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 构造返回值
        GDGYSecInfoVo gdgySecInfoVo = new GDGYSecInfoVo();
        gdgySecInfoVo.setData(responseList);
        gdgySecInfoVo.setBidAmountSum(bidAmountSum);
        gdgySecInfoVo.setBudgetAmountSum(budgetAmountSum);

        return BaseResponse.ok(gdgySecInfoVo, (int) secInfoListGDGY.getTotal());

    }

    @Override
    public BaseResponse getConSecInfoGgById(String secId) {
        if (ValidateUtil.isEmpty(secId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecInfo entity = this.getById(secId);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        String secBiddingType = entity.getSecBiddingType();
        String keyId = null;
        BigDecimal amount = null;
        String partyBUnit = null;
        String partyBUnitName = null;
        if (!"03".equalsIgnoreCase(secBiddingType) && !"05".equalsIgnoreCase(secBiddingType)) {
            SecWinNoticeResponse winNotice = secWinNoticeMapper.getLatestApproveWinNotice(secId);
            if (ValidateUtil.isNotNull(winNotice)) {
                keyId = winNotice.getWnId();
                amount = winNotice.getWnAmount();
                partyBUnit = winNotice.getWnWinUnit();
                partyBUnitName = winNotice.getWnWinUnitName();
            }
        } else if ("05".equalsIgnoreCase(secBiddingType)) {
            SecInquiryRecordVo secInquiryRecord = secInquiryRecordMapper.getSecInquiryRecordBySecId(secId);
            if (ValidateUtil.isNotNull(secInquiryRecord)) {
                keyId = secInquiryRecord.getId();
                amount = secInquiryRecord.getSirConMoney();
                partyBUnit = secInquiryRecord.getSirIntendedUnit();
                partyBUnitName = secInquiryRecord.getSirIntendedUnitName();
            }
        } else if ("03".equalsIgnoreCase(secBiddingType)) {
            SecEntrustVo secEntrust = secEntrustMapper.getSecEntrustBySecId(secId);
            if (ValidateUtil.isNotNull(secEntrust)) {
                keyId = secEntrust.getId();
                amount = secEntrust.getSeAmt();
                partyBUnit = secEntrust.getSeCpyId();
                partyBUnitName = secEntrust.getSeCpyName();
            }
        }
        Map<String, Object> result = new HashMap<>(5);
        result.put("secId", secId);
        result.put("keyId", keyId);
        result.put("amount", amount);
        result.put("secBiddingType", secBiddingType);
        result.put("partyBUnit", partyBUnit);
        result.put("partyBUnitName", partyBUnitName);
        return BaseResponse.ok(result);
    }

    /**
     * 从旧版招标计划同步标段信息。已有标段保留主键并更新，新增标段生成主键。
     * 同步询价调研明细；未提交明细时保留已有数据。
     * 同步后从主表实体中筛选新增标段，排除费用类型 02、03 和标段分类 /，两个字段均允许为空。
     *
     * @param datas          标段信息
     * @param pIds           项目 ID 列表
     * @param secProjectList 标段项目关联
     * @param bp             招标计划
     * @return 同步结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecInfoFromOld(List<SecInfoRequest> datas, List<String> pIds, List<SecProjectRequest> secProjectList, SecBiddingPlan bp) {
        String bpId = bp.getBpId();
        Map<String, List<SecInfo>> oldSecsByCode = list(Wrappers.<SecInfo>lambdaQuery().eq(SecInfo::getBpId, bpId)).stream()
                .filter(oldSec -> ValidateUtil.isNotEmpty(oldSec.getSecCode()))
                .collect(Collectors.groupingBy(SecInfo::getSecCode));
        Set<String> processedSecIds = new HashSet<>();
        // 标段主表保留原记录，按主键分别新增或更新。
        List<SecInfo> entitys = new ArrayList<>();
        List<List<SecSourcesFundsModel>> ssfms = new ArrayList<>();
        List<String> ssfSecIds = new ArrayList<>();
        List<List<String>> relationId = new ArrayList<>();
        List<List<String>> fileIds = new ArrayList<>();
        List<String> secIds = new ArrayList<>();
        List<SecInfoRequest> refershs = new ArrayList<>();
        List<SecInfoBudgetDeclareDtl> secInfoBudgetDeclareDtlList = new ArrayList<>();
        List<SecInfoSubject> secInfoSubjects = new ArrayList<>();
        List<SecInfoSubjectExtra> secInfoSubjectExtras = new ArrayList<>();
        List<String> newSecIds = new ArrayList<>();
        for (SecInfoRequest request : datas) {
            SecInfo sec = new SecInfo();
            if (ValidateUtil.isEmpty(request.getSecId()) && ValidateUtil.isNotEmpty(request.getSecCode())) {
                List<SecInfo> matchedSecs = oldSecsByCode.get(request.getSecCode());
                if (matchedSecs != null) {
                    if (matchedSecs.size() != 1) {
                        return BaseResponse.fail(ResMsg.infoNotExist);
                    }
                    request.setSecId(matchedSecs.get(0).getSecId());
                }
            }
            if (ValidateUtil.isEmpty(request.getSecId()) && apiUtilsService.sequenceRestEnable("SecInfo") == true) {
                request.setSecCode(apiUtilsService.generateCodeByTCode("SecInfo", request));
                if (ValidateUtil.isEmpty(request.getSecCode())) {
                    return BaseResponse.fail(ResMsg.codeGenerError);
                }
                refershs.add(request);
            }
            BeanUtils.copyProperties(request, sec);
            if (ValidateUtil.isEmpty(sec.getSecId())) {
                sec.setSecId(GenerateUUID.getUUID());
                sec.setSecCreateTime(new Date());
                sec.setSecFlowState(ApprovalStatusEnum.Already_Over.getIndex());
                newSecIds.add(sec.getSecId());
            } else {
                SecInfo oldSec = getById(sec.getSecId());
                if (oldSec != null) {
                    sec.setSecCreateTime(oldSec.getSecCreateTime());
                    sec.setSecCreateUid(oldSec.getSecCreateUid());
                    sec.setSecCreateUname(oldSec.getSecCreateUname());
                } else {
                    sec.setSecCreateTime(new Date());
                    sec.setSecFlowState(ApprovalStatusEnum.Already_Over.getIndex());
                    newSecIds.add(sec.getSecId());
                }
            }
            if (!processedSecIds.add(sec.getSecId())) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            sec.setBpId(bpId);
            entitys.add(sec);
            if (ValidateUtil.isNotEmpty(request.getSourcesFundsModels())) {
                ssfms.add(request.getSourcesFundsModels());
                ssfSecIds.add(sec.getSecId());
            }

            if (!ValidateUtil.isEmpty(request.getSecInfoBudgetDeclareDtlDtoList())) {
                List<SecInfoBudgetDeclareDtlDto> secInfoBudgetDeclareDtlDtoList = request.getSecInfoBudgetDeclareDtlDtoList();
                for (SecInfoBudgetDeclareDtlDto secInfoBudgetDeclareDtlDto : secInfoBudgetDeclareDtlDtoList) {
                    SecInfoBudgetDeclareDtl secInfoBudgetDeclareDtl = new SecInfoBudgetDeclareDtl();
                    BeanUtil.copyProperties(secInfoBudgetDeclareDtlDto, secInfoBudgetDeclareDtl);
                    secInfoBudgetDeclareDtl.setSibdId(GenerateUUID.getUUID());
                    secInfoBudgetDeclareDtl.setSecId(sec.getSecId());
                    secInfoBudgetDeclareDtlList.add(secInfoBudgetDeclareDtl);
                }
            }

            relationId.add(Arrays.asList(request.getFileRelationId(), request.getSecApprovalFile(), request.getSecValuationFile(), request.getSecProofFile(), request.getSecBlankFile(), request.getSecOtherFile(), request.getSecOtherImg(), request.getProcurementPlanDoc(), request.getNoIntentionDiscloseDoc(),
                    request.getSupportMaterialsDoc(), request.getExpenditureApprovalDoc(), request.getDemandSurveyDoc(), request.getOtherDoc()));
            fileIds.add(Arrays.asList(request.getFileIds(), request.getSecApprovalFileIds(), request.getSecValuationFileIds(), request.getSecProofFileIds(), request.getSecBlankFileIds(), request.getSecOtherFileIds(), request.getSecOtherImgIds(), request.getProcurementPlanDocFileIds(), request.getNoIntentionDiscloseDocFileIds(),
                    request.getSupportMaterialsDocFileIds(), request.getExpenditureApprovalDocFileIds(), request.getDemandSurveyDocFileIds(), request.getOtherDocFileIds()));
            secIds.add(sec.getSecId());

            // 处理子表
            List<SecInfoSubjectRequest> subjects = request.getSecInfoSubjects();
            if (ValidateUtil.isNotEmpty(subjects)) {
                if (ValidateUtil.isNotEmpty(request.getSecSubjectNameType()) && "估/概算外的二类费".equals(request.getSecSubjectNameType())) {
                    subjects.forEach(sub -> {
                        SecInfoSubjectExtra extra = new SecInfoSubjectExtra();
                        extra.setId(GenerateUUID.getUUID());
                        extra.setSecId(sec.getSecId());
                        extra.setBpId(bpId);
                        extra.setParentId(sub.getId());
                        extra.setSeName(sub.getSubName());
                        extra.setSeMoney(sub.getSubMoney());
                        extra.setLevelCode(sub.getLevelCode());
                        extra.setSort(ValidateUtil.isNotEmpty(sub.getLevelCode()) ? Integer.valueOf(sub.getLevelCode().split(" ")[0]) : null);
                        extra.setCreateTime(new Date());
                        extra.setCreateUid(bp.getBpCreateUid());
                        extra.setCreateUname(bp.getBpCreateUname());
                        secInfoSubjectExtras.add(extra);
                    });
                } else {
                    subjects.forEach(sub -> {
                        SecInfoSubject secInfoSubject = new SecInfoSubject();
                        BeanUtil.copyProperties(sub, secInfoSubject);
                        secInfoSubject.setId(GenerateUUID.getUUID());
                        secInfoSubject.setSecId(sec.getSecId());
                        secInfoSubjects.add(secInfoSubject);
                    });
                }
            }
        }

        // 完成主表数据校验后，再刷新旧的标段关联数据。
        secSourcesFundsService.delSecSourcesFundsByBpId(bpId);
        secProjectService.deleteAllOldByBpId(bpId);
        secInfoBudgetDeclareDtlService.delSecInfoBudgetDeclareDtlByBpId(bpId);
        secInfoSubjectService.deleteAllOldByBpId(bpId);
        secInfoSubjectExtraService.remove(Wrappers.<SecInfoSubjectExtra>lambdaQuery().eq(SecInfoSubjectExtra::getBpId, bpId));

        if (!entitys.isEmpty()) {
            saveOrUpdateBatch(entitys);
            saveQuotationRequests(datas, entitys, false);
            for (int i = 0; i < relationId.size(); i++) {
                sysFileAnnexService.refreshRelationFile(relationId.get(i), fileIds.get(i), secIds.get(i));
            }
            // 标段项目关联表
            secProjectService.createOrUpdateSecProjectByBath(pIds, secProjectList, entitys.stream().map(SecInfo::getSecId).collect(Collectors.toList()), bp.getBpCreateUid());
            // 标段资金来源
            if (ValidateUtil.isNotEmpty(ssfms)) {
                secSourcesFundsService.createOrUpdateSecSourcesFundsByBath(ssfms, ssfSecIds);
            }

            // 标段概算申报明细
            if (ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtlList)) {
                secInfoBudgetDeclareDtlService.createOrUpdateSecInfoBudgetDeclareDtl(secInfoBudgetDeclareDtlList);
            }

            // 科目
            if (ValidateUtil.isNotEmpty(secInfoSubjects)) {
                secInfoSubjectService.saveBatch(secInfoSubjects);
            }

            if (ValidateUtil.isNotEmpty(secInfoSubjectExtras)) {
                secInfoSubjectExtraService.saveBatch(secInfoSubjectExtras);
            }

            // 从 entitys 中筛选新增标段，排除费用类型 02、03 和标段分类 /，保留空值。
            List<SecInfo> newSecInfos = entitys.stream()
                    .filter(sec -> newSecIds.contains(sec.getSecId()))
                    .filter(sec -> !"02".equals(sec.getSecCostType()) && !"03".equals(sec.getSecCostType()))
                    .filter(sec -> !"/".equals(sec.getSecSectionClassify()))
                    .collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(newSecInfos)) {
                List<String> secId = newSecInfos.stream().map(SecInfo::getSecId).collect(Collectors.toList());
                List<SysFile> filePojos = new ArrayList<>();
                List<SecInfoRequest> secInfoRequests = datas.stream().filter(sec -> secId.contains(sec.getSecId())).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(secInfoRequests)) {
                    for (SecInfoRequest sec : secInfoRequests) {
                        filePojos.add(sysFileService.structureFile(sec.getSecId(), sec.gettId(),
                                sec.getSecFlowState() + "", sec.getSecCreateUid(), sec.getSecCreateUname()
                                , sec.getSecCreateTime(), null, sec.getFileTitle(), sec.getFileCode(),
                                GenerateUUID.getUUID(), sec.getSecId(), sec.gettCode()));
                    }
                }
                if (!filePojos.isEmpty()) {
                    sysFileService.saveBatch(filePojos);
                }
            }
        }
        refershs.forEach(data -> {
            apiUtilsService.refershAutoNo("SecInfo", data);
        });
        return BaseResponse.ok();
    }
}
