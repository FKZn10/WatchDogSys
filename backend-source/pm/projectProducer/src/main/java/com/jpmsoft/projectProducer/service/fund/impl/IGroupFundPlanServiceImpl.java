package com.jpmsoft.projectProducer.service.fund.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.fund.GroupFundPlanDetailSubjectDTO;
import com.jpmsoft.common.model.response.InvestmentControl.InvFinalAccountsApplyResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvSettlementApplyResponse;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMapper;
import com.jpmsoft.projectProducer.mapper.fund.GroupFundPlanMapper;
import com.jpmsoft.projectProducer.mapper.fund.PayAmountInitMapper;
import com.jpmsoft.projectProducer.mapper.fund.PaymentReceiptMapper;
import com.jpmsoft.projectProducer.mapper.investment.*;
import com.jpmsoft.projectProducer.mapper.other.TaskProcessinMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanDetailDto;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanDto;
import com.jpmsoft.projectProducer.pojo.dto.system.DictDto;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.fund.*;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlySchedule;
import com.jpmsoft.projectProducer.pojo.entity.other.FlowActivity;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanDetailService;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanDetailSubjectService;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanService;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanSummaryService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IGroupFundPlanServiceImpl extends BaseFileImpl<GroupFundPlanMapper, GroupFundPlan, GroupFundPlanDto> implements IGroupFundPlanService {
    @Resource
    private IGroupFundPlanDetailService groupFundPlanDetailService;
    @Autowired
    private PreProjectMapper preProjectMapper;
    @Autowired
    private ConContractMapper conContractMapper;
    @Autowired
    private InvCompleteMonthMapper invCompleteMonthMapper;
    @Autowired
    private InvMonthlyScheduleMapper invMonthlyScheduleMapper;
    @Autowired
    private InvBudgetDeclareMapper invBudgetDeclareMapper;
    @Autowired
    private InvFinalAccountsApplyMapper invFinalAccountsApplyMapper;
    @Autowired
    private InvSettlementApplyMapper invSettlementApplyMapper;
    @Autowired
    private PaymentReceiptMapper paymentReceiptMapper;
    @Autowired
    private PayAmountInitMapper payAmountInitMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private ConPayMapper conPayMapper;
    @Resource
    private IGroupFundPlanDetailSubjectService groupFundPlanDetailSubjectService;
    @Resource
    private IGroupFundPlanSummaryService groupFundPlanSummaryService;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TaskProcessinMapper taskProcessinMapper;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGroupFundPlan(GroupFundPlanDto dto) {
        String primaryId = dto.getId();
        List<GroupFundPlanDetailSubjectDTO> subjectList = new ArrayList<>();
        if (StrUtil.isEmpty(primaryId)) {
            primaryId = GenerateUUID.getUUID();
            if ("create".equals(dto.getOperationalType()) && existProject(dto.getProjectId(), dto.getGfpMonth(), dto.getGfpYear(), null)) {
                return BaseResponse.fail("该项目所选年月的资金计划已存在，不能重复创建！");
            }
            GroupFundPlan groupFundPlan = new GroupFundPlan();
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            dto.setState(flowStatus);
            BeanUtil.copyProperties(dto, groupFundPlan);
            groupFundPlan.setId(primaryId);
            groupFundPlan.setDelFlag(0);
            groupFundPlan.setCreateTime(DateTime.now());
            if (ValidateUtil.isEmpty(groupFundPlan.getGroupType())) {
                groupFundPlan.setGroupType("01");
            }
            List<GroupFundPlanDetail> details = new ArrayList<>();
            if (dto.getDetailDtos() != null) {
                List<GroupFundPlanDetailDto> dtos = dto.getDetailDtos();
                if (CollectionUtil.isNotEmpty(dtos)) {
                    for (GroupFundPlanDetailDto detailsDtos : dtos) {
                        detailsDtos.setId(GenerateUUID.getUUID());
                        detailsDtos.setGfpId(primaryId);
                        if (!"change".equals(dto.getOperationalType()) && existConMeasurePrice(groupFundPlan.getId(), detailsDtos.getCmpId())) {
                            return BaseResponse.fail("合同" + detailsDtos.getGfpdContractName() + "的计量计价信息已被使用，请重新选择！");
                        }
                        GroupFundPlanDetail detailsObj = new GroupFundPlanDetail();
                        BeanUtil.copyProperties(detailsDtos, detailsObj);
                        details.add(detailsObj);

                        List<GroupFundPlanDetailSubjectDTO> budgetSubjects = detailsDtos.getBudgetSubjects();
                        if (ValidateUtil.isNotEmpty(budgetSubjects)) {
                            budgetSubjects.forEach(item -> {
                                item.setGfpdId(detailsDtos.getId());
                                item.setGfpId(groupFundPlan.getId());
                            });
                            subjectList.addAll(budgetSubjects);
                        }
                    }
                }
            } else {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            List<String> fileIds = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(details)) {
                fileIds = details.stream().map(GroupFundPlanDetail::getGfpdAttachment).collect(Collectors.toList());
                groupFundPlanDetailService.saveBatch(details);
            }
            baseMapper.insert(groupFundPlan);
            fileIds.add(dto.getFileRelationId());
            fileIds.add(dto.getFundApplicationLetter());
            sysFileAnnexService.addFormRefreshRelationFile(fileIds, groupFundPlan.getId());
            // 创建流程
            if (sysFileService.FileHelper(groupFundPlan, dto, "create", dto.getFileRelationId())) {

            } else {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        } else {
            if (!"change".equals(dto.getOperationalType()) && existProject(dto.getProjectId(), dto.getGfpMonth(), dto.getGfpYear(), dto.getId())) {
                return BaseResponse.fail("该项目所选年月的资金计划已存在，不能重复创建！");
            }
            GroupFundPlan groupFundPlan = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(dto, groupFundPlan);
            // 禁用旧的填报值
            deleteDetailByEfaId(primaryId);
            List<GroupFundPlanDetail> details = new ArrayList<>();
            if (dto.getDetailDtos() != null) {
                List<GroupFundPlanDetailDto> dtos = dto.getDetailDtos();
                if (CollectionUtil.isNotEmpty(dtos)) {
                    for (GroupFundPlanDetailDto detailsDtos : dtos) {
                        detailsDtos.setId(GenerateUUID.getUUID());
                        detailsDtos.setGfpId(primaryId);
                        if (!"change".equals(dto.getOperationalType()) && existConMeasurePrice(groupFundPlan.getId(), detailsDtos.getCmpId())) {
                            return BaseResponse.fail("合同" + detailsDtos.getGfpdContractName() + "的计量计价信息已被使用，请重新选择！");
                        }
                        GroupFundPlanDetail detailsObj = new GroupFundPlanDetail();
                        BeanUtil.copyProperties(detailsDtos, detailsObj);
                        details.add(detailsObj);

                        List<GroupFundPlanDetailSubjectDTO> budgetSubjects = detailsDtos.getBudgetSubjects();
                        if (ValidateUtil.isNotEmpty(budgetSubjects)) {
                            budgetSubjects.forEach(item -> {
                                item.setGfpdId(detailsDtos.getId());
                                item.setGfpId(groupFundPlan.getId());
                            });
                            subjectList.addAll(budgetSubjects);
                        }
                    }
                }
            } else {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            List<String> fileRelationIds = new ArrayList<>();
            List<String> fileIds = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(details)) {
                fileRelationIds = details.stream().map(GroupFundPlanDetail::getGfpdAttachment).collect(Collectors.toList());
                fileIds = details.stream().map(GroupFundPlanDetail::getGfpdAttachmentFileIds).collect(Collectors.toList());
                groupFundPlanDetailService.saveBatch(details);
            }
            baseMapper.updateById(groupFundPlan);
            fileRelationIds.add(dto.getFileRelationId());
            fileIds.add(dto.getFileIds());
            fileRelationIds.add(dto.getFundApplicationLetter());
            fileIds.add(dto.getFundApplicationLetterIds());
            sysFileAnnexService.refreshRelationFile(fileRelationIds, fileIds, groupFundPlan.getId());
        }
        groupFundPlanDetailSubjectService.createSubjectBatch(subjectList);
        return BaseResponse.ok(primaryId);
    }

    private void deleteDetailByEfaId(String gfpId) {
        groupFundPlanDetailService.remove(Wrappers.<GroupFundPlanDetail>lambdaQuery()
                .eq(GroupFundPlanDetail::getGfpId, gfpId)
        );
    }

    private boolean existProject(String pId, Integer gfpMonth, Integer gfpYear, String excludeId) {
        return baseMapper.exists(Wrappers.<GroupFundPlan>lambdaQuery()
                .eq(GroupFundPlan::getProjectId, pId)
                .eq(GroupFundPlan::getGfpMonth, gfpMonth)
                .eq(GroupFundPlan::getGfpYear, gfpYear)
                .eq(GroupFundPlan::getGroupType, "01")
                .ne(BaseEntity::getState, ApprovalStatusEnum.Invalid.getIndex())
                .ne(ValidateUtil.isNotEmpty(excludeId), GroupFundPlan::getId, excludeId)
        );
    }

    private boolean existProject(String groupId) {
        return baseMapper.exists(Wrappers.<GroupFundPlan>lambdaQuery()
                .eq(GroupFundPlan::getGroupType, "01")
                .eq(GroupFundPlan::getGroupId, groupId)
                .ne(BaseEntity::getState, ApprovalStatusEnum.Invalid.getIndex())
                .ne(BaseEntity::getState, ApprovalStatusEnum.Already_Over.getIndex())
        );
    }

    @Override
    public BaseResponse getGroupFundPlanList(GroupFundPlanRequest request, String uid) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isEmpty(uid)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        String authSql = sysUserService.getAuthSql("Group_Fund_Plan", uid, "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        QueryMap<GroupFundPlanRequest> params = new QueryMap<>(request);
        params.put("projectName").put("projectId").put("authSql", authSql).put("groupType")
                .put("usaId").put("gfpApplicationCid").put("gfpApplicationCidName").put("state")
                .put("createUname").put("groupId").put("groupMainType")
                .put(request.getGfpFundDemandAmounts(), "gfpFundDemandAmountStart", "gfpFundDemandAmountEnd")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd");
        IPage<GroupFundPlanVo> page = new Page<>(request.getPage(), request.getSize());
        List<GroupFundPlanVo> groupFundPlanVos
                = baseMapper.getGroupFundPlanList(params.getQuery(), page);
        for (GroupFundPlanVo groupFundPlanVo : groupFundPlanVos) {
            groupFundPlanVo.setIsNew(0);
            setProjectInfo(groupFundPlanVo);
        }
        groupFundPlanVos.forEach(item -> item.setCanChange(!this.existProject(item.getGroupId())));
        // 同一项目可发起同一月份的多条资金计划，且最新一笔的资金计划需要字体标红
        Map<String, List<GroupFundPlanVo>> groupedGroupFundPlanVos = groupFundPlanVos.stream().collect(Collectors.groupingBy(GroupFundPlanVo::getProjectId));
        for (Map.Entry<String, List<GroupFundPlanVo>> entry : groupedGroupFundPlanVos.entrySet()) {
            List<GroupFundPlanVo> list = entry.getValue();
            if (list.size() > 1) {
                Map<Integer, List<GroupFundPlanVo>> groupedProducts = list.stream()
                        .filter(vo -> vo.getGfpMonth() != null)
                        .collect(Collectors.groupingBy(GroupFundPlanVo::getGfpMonth));
                for (Map.Entry<Integer, List<GroupFundPlanVo>> entry1 : groupedProducts.entrySet()) {
                    List<GroupFundPlanVo> list1 = entry1.getValue();
                    list1 = list1.stream().filter(groupFundPlanVo -> groupFundPlanVo.getCreateTime() != null).collect(Collectors.toList());
                    if (list1.size() > 1) {
                        GroupFundPlanVo groupFundPlanVo1 = list1.stream().sorted(Comparator.comparing(GroupFundPlanVo::getCreateTime).reversed()).collect(Collectors.toList()).get(0);
                        groupFundPlanVo1.setIsNew(1);
                    }
                }
            }
        }
        log.info("查询列表信息：{}", JacksonUtils.toJSONString(groupFundPlanVos));
        return BaseResponse.ok(groupFundPlanVos, Long.valueOf(page.getTotal()).intValue());
    }

    private void setProjectInfo(GroupFundPlanVo groupFundPlanVo) {
        PreProject project = preProjectMapper.selectById(groupFundPlanVo.getProjectId());
        if (groupFundPlanVo.getProjectInvTotal() == null) {
            if (project != null) {
                if (project.getpInvTotal() != null) {
                    groupFundPlanVo.setProjectInvTotal(project.getpInvTotal());
                } else {
                    groupFundPlanVo.setProjectInvTotal(BigDecimal.ZERO);
                }
            }
        }
        if (groupFundPlanVo.getProjectApproveBudget() == null) {
            if (project != null) {
                if (project.getpApproveBudget() != null) {
                    groupFundPlanVo.setProjectInvTotal(project.getpApproveBudget());
                } else {
                    groupFundPlanVo.setProjectInvTotal(BigDecimal.ZERO);
                }
            }
        }
        if (groupFundPlanVo.getProjectApproveBudgetCode() == null) {
            if (project != null) {
                if (project.getpApproveBudgetCode() != null) {
                    groupFundPlanVo.setProjectApproveBudgetCode(project.getpApproveBudgetCode());
                } else {
                    groupFundPlanVo.setProjectApproveBudgetCode("");
                }
            }
        }
        if (groupFundPlanVo.getBdApprovalNum() == null) {
            if (project != null) {
                if (project.getpInitialApprovalNumber() != null) {
                    groupFundPlanVo.setBdApprovalNum(project.getpInitialApprovalNumber());
                } else {
                    groupFundPlanVo.setBdApprovalNum("");
                }
            }
        }
        if (groupFundPlanVo.getProjectTotal() == null) {
            Double finalSettlementAmount = 0.0;
            InvFinalAccountsApplyResponse invFinalAccountsApplyResponse =
                    invFinalAccountsApplyMapper.getInvFinalAccountsApplyAndDtlByPid(groupFundPlanVo.getProjectId());
            if (invFinalAccountsApplyResponse != null && invFinalAccountsApplyResponse.getFaApprovedAmt() != null)
                finalSettlementAmount = invFinalAccountsApplyResponse.getFaApprovedAmt().doubleValue();
            // 结算金额
            Double settlementAmount = 0.0;
            List<InvSettlementApplyResponse> invSettlementApplyResponses =
                    invSettlementApplyMapper.getLastApprovalInvSettlementApplyByProjectId(groupFundPlanVo.getProjectId());
            if (CollectionUtil.isNotEmpty(invSettlementApplyResponses)) {
                List<BigDecimal> settlementAmounts = invSettlementApplyResponses.stream().map(InvSettlementApplyResponse::getSaApprovedAmt).collect(Collectors.toList());
                settlementAmount = settlementAmounts.stream().filter(Objects::nonNull).mapToDouble(BigDecimal::doubleValue).sum();
            }
            // 概算金额
            BigDecimal estimatedAmount = groupFundPlanVo.getProjectApproveBudget();
            // 估算金额
            BigDecimal estimateAmount = groupFundPlanVo.getProjectInvTotal();
            if (groupFundPlanVo.getProjectTotal() == null) {
                if (project != null) {
                    groupFundPlanVo.setProjectTotal(project.getpApproveBudget() != null ? project.getpApproveBudget() : BigDecimal.ZERO);
                } else {
                    groupFundPlanVo.setProjectTotal(BigDecimal.ZERO);
                }
            }
            // if (finalSettlementAmount!=0.0){
            //     groupFundPlanVo.setProjectTotal(finalSettlementAmount);
            // }else if (settlementAmount!=0.0){
            //     groupFundPlanVo.setProjectTotal(settlementAmount);
            // }else if (estimatedAmount!=null && estimatedAmount!=0.0){
            //     groupFundPlanVo.setProjectTotal(estimatedAmount);
            // }else if (estimateAmount!=null && estimateAmount!=0.0){
            //     groupFundPlanVo.setProjectTotal(estimateAmount);
            // }
        }
        if (groupFundPlanVo.getGfpNextMonthFundsTotal() == null) {
            //=集团账面余额+次月预计来款金额
            groupFundPlanVo.setGfpNextMonthFundsTotal(groupFundPlanVo.getGfpMemberBalanceFunds().add(groupFundPlanVo.getGfpNextMonthFunds()));
        }
    }

    @Override
    public BaseResponse getGroupFundPlanById(String id) {
        GroupFundPlanVo groupFundPlanVo = baseMapper.getGroupFundPlanById(id);
        if (groupFundPlanVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        setProjectInfo(groupFundPlanVo);
        List<GroupFundPlanDetailSubjectDTO> detailSubjects = groupFundPlanDetailSubjectService.getByGfpId(id);
        Map<String, List<GroupFundPlanDetailSubjectDTO>> subjectMap = detailSubjects.stream()
                .collect(Collectors.groupingBy(GroupFundPlanDetailSubjectDTO::getGfpdId));
        List<GroupFundPlanDetailVo> detailDtos = groupFundPlanVo.getDetailDtos();
        detailDtos.forEach(item -> {
            if (subjectMap.containsKey(item.getId())) {
                List<GroupFundPlanDetailSubjectDTO> subjects = subjectMap.get(item.getId());
                item.setBudgetSubjects(subjects);
            }
        });
        groupFundPlanVo.setDetailDtos(detailDtos);
        log.info("查询信息：{}", groupFundPlanVo);
        return BaseResponse.ok(groupFundPlanVo);
    }

    @Override
    public BaseResponse getFundInfo(GroupFundPlanRequest request) {
        if (StrUtil.isEmpty(request.getProjectId())) {
            return BaseResponse.fail("缺失项目信息");
        }
        GroupFundVo groupFundVo = new GroupFundVo();
        PreProject project = preProjectMapper.selectById(request.getProjectId());
        if (project != null) {
            // 估算批复金额
            if (project.getpInvTotal() != null) {
                groupFundVo.setPInvTotal(project.getpInvTotal());
            } else {
                groupFundVo.setPInvTotal(BigDecimal.ZERO);
            }
            // 概算批复金额[概算总投资]//数据源:投资控制-投资概算分解模块~概算总投资。
            if (project.getpApproveBudget() != null) {
                groupFundVo.setPApproveBudget(project.getpApproveBudget());
            } else {
                groupFundVo.setPApproveBudget(BigDecimal.ZERO);
            }
            // 批复编号立项文号
            if (project.getpApproveBudgetCode() != null) {
                groupFundVo.setPApproveBudgetCode(project.getpApproveBudgetCode());
            } else {
                groupFundVo.setPApproveBudgetCode("");
            }
            // 概算文号
            groupFundVo.setBdApprovalNum(project.getpInitialApprovalNumber());
//            Double finalSettlementAmount=0.0;
//            InvFinalAccountsApplyResponse invFinalAccountsApplyResponse=
//                    invFinalAccountsApplyMapper.getInvFinalAccountsApplyAndDtlByPid(request.getProjectId());
//            if (invFinalAccountsApplyResponse!=null && invFinalAccountsApplyResponse.getFaApprovedAmt()!=null)
//                finalSettlementAmount=invFinalAccountsApplyResponse.getFaApprovedAmt().doubleValue();
//            //结算金额
//            Double settlementAmount=0.0;
//            List<InvSettlementApplyResponse> invSettlementApplyResponses =
//                    invSettlementApplyMapper.getLastApprovalInvSettlementApplyByProjectId(request.getProjectId());
//            if (CollectionUtil.isNotEmpty(invSettlementApplyResponses)){
//                List<BigDecimal> settlementAmounts=invSettlementApplyResponses.stream().map(InvSettlementApplyResponse::getSaApprovedAmt).collect(Collectors.toList());
//                settlementAmount=settlementAmounts.stream().mapToDouble(BigDecimal::doubleValue).sum();
//            }
//            //概算金额
//            Double estimatedAmount = groupFundVo.getPApproveBudget();
//            //估算金额
//            Double estimateAmount = groupFundVo.getPInvTotal();
//            if (finalSettlementAmount!=0.0){
//                groupFundVo.setPTotal(finalSettlementAmount);
//            }else if (settlementAmount!=0.0){
//                groupFundVo.setPTotal(settlementAmount);
//            }else if (estimatedAmount!=0.0){
//                groupFundVo.setPTotal(estimatedAmount);
//            }else if (estimateAmount!=0.0){
//                groupFundVo.setPTotal(estimateAmount);
//            }
            // 项目总投资[估算总投资] pApprovalInvestmentAmount
            if (project.getpApproveBudget() != null) {
                groupFundVo.setPTotal(project.getpApproveBudget());
            } else {
                groupFundVo.setPTotal(BigDecimal.ZERO);
            }
            // 项目进展
            // 文本：形象进度+形象进度补充说明
            String gfpProjectProgress = "";
            InvCompleteMonth invCompleteMonth = invCompleteMonthMapper.getInvCompleteMonth(request.getProjectId(), request.getGfpMonth(), request.getGfpYear());
            InvMonthlySchedule invMonthlySchedule = invMonthlyScheduleMapper.getInvMonthlySchedule(request.getProjectId(), request.getGfpMonth(), request.getGfpYear());
            if (invCompleteMonth != null) {
                if (StrUtil.isNotEmpty(invCompleteMonth.getIcmImageProgress())) {
                    List<DictDto> dictList = DictionaryUtils.getDictList("JPMsoft_XXJD");
                    if (CollectionUtil.isNotEmpty(dictList)) {
                        for (DictDto dictDto : dictList) {
                            if (invCompleteMonth.getIcmImageProgress().equals(dictDto.getCode())) {
                                gfpProjectProgress += dictDto.getName();
                            }
                        }
                    }
                }
                if (StrUtil.isNotEmpty(gfpProjectProgress)) {
                    gfpProjectProgress += ":";
                }
                if (StrUtil.isNotEmpty(invCompleteMonth.getIcmImgProgress())) {
                    gfpProjectProgress += invCompleteMonth.getIcmImgProgress();
                }
            }
            groupFundVo.setGfpProjectProgress(gfpProjectProgress);
            BigDecimal gfpArrivedFunds = BigDecimal.ZERO;// 项目已到位资金
            BigDecimal gfpArrangeFunds = BigDecimal.ZERO;// 集团已安排资金
            BigDecimal gfpBalanceFunds = BigDecimal.ZERO;// 集团账面余额
            BigDecimal gfpMemberBalanceFunds = BigDecimal.ZERO;// 成员企业账面余额
            List<PayAmountInit> payAmountInitList = payAmountInitMapper.getPayAmountInit(request.getProjectId());
            List<GroupFundPlan> groupFundPlans = baseMapper.getGroupFundPlans(request.getProjectId());
            if (CollectionUtil.isNotEmpty(groupFundPlans)) {
                // Gfp_Arrived_Funds  项目已到位资金（后续在此基础上累加已核销的收据金额）
                if (CollectionUtil.isNotEmpty(payAmountInitList)) {
                    BigDecimal sum = payAmountInitList.stream()
                            .map(PayAmountInit::getPiFundProjectAmount)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    gfpArrivedFunds = gfpArrivedFunds.add(sum);
                }
                List<PaymentReceipt> paymentReceipts = paymentReceiptMapper.getReceipt(request.getProjectId());
                if (CollectionUtil.isNotEmpty(paymentReceipts)) {
                    BigDecimal sum = payAmountInitList.stream()
                            .map(PayAmountInit::getPiFundProjectAmount)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    gfpArrivedFunds = gfpArrivedFunds.add(sum);
                }
                // Gfp_Arrange_Funds 集团已安排资金 第二次及以后取之前集团已安排资金+用款申请金额
                BigDecimal arrangeFundsSum = groupFundPlans.stream()
                        .map(GroupFundPlan::getGfpArrangeFunds)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                gfpArrangeFunds = gfpArrangeFunds.add(arrangeFundsSum);
                List<EngineeringFundApplication> engineeringFundApplications = baseMapper.getEngineeringFundApplicationByPId(request.getProjectId());
                if (CollectionUtil.isNotEmpty(engineeringFundApplications)) {
                    BigDecimal sum = engineeringFundApplications.stream()
                            .map(EngineeringFundApplication::getEfaApplyAmount)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    gfpArrangeFunds = gfpArrangeFunds.add(sum);
                }
                // Gfp_Balance_Funds 集团账面余额 取差值（项目已到位资金减去集团已安排资金）
                gfpBalanceFunds = gfpArrivedFunds.subtract(gfpArrangeFunds);
                // gfpMemberBalanceFunds 成员企业账面余额
                // 1.建管项目：集团已安排资金
                // 减去该项目所有合同的最新一条“合同支付~【截止上期累计实际支付+本期实际支付】”的合计值，
                // 再减去该项目最新一条“规费支付~【截止上期累计实际支付+本期实际支付】”
                // 2.其他企业项目：集团已安排资金减去该项目所有“已支付数据维护~本期已付款金额”的合计值。
                if (project.getpCompetentDepartment().equals("7943b1b1-e2fb-4563-b485-4321146ecc39")) {
                    ConPayResponse conPayResponse = conPayMapper.getConPayByPId(request.getProjectId(), null, "01");
                    ConPayResponse projectPayResponse = conPayMapper.getConPayByPId(request.getProjectId(), null, "02");
                    BigDecimal conBG = BigDecimal.ZERO;
                    if (conPayResponse != null) {
                        conBG.add(conPayResponse.getPayTotalLast() != null ? conPayResponse.getPayTotalLast() : BigDecimal.ZERO);
                        conBG.add(conPayResponse.getPayApproval() != null ? conPayResponse.getPayApproval() : BigDecimal.ZERO);
                    }
                    BigDecimal proBG = BigDecimal.ZERO;
                    if (projectPayResponse != null) {
                        proBG.add(projectPayResponse.getPayTotalLast() != null ? projectPayResponse.getPayTotalLast() : BigDecimal.ZERO);
                        proBG.add(projectPayResponse.getPayApproval() != null ? projectPayResponse.getPayApproval() : BigDecimal.ZERO);
                    }
                    gfpMemberBalanceFunds = gfpArrangeFunds.subtract(conBG).subtract(proBG);
                } else {
                    List<ConPayResponse> payResponse = conPayMapper.getConPayWithoutPayTypeByPId(request.getProjectId(), null);
                    BigDecimal payBG = BigDecimal.ZERO;
                    for (ConPayResponse response : payResponse) {
                        if (response != null && response.getPayPayable() != null) {
                            payBG.add(response.getPayPayable());
                        }
                    }
                    gfpMemberBalanceFunds = gfpArrangeFunds.subtract(BigDecimal.valueOf(payBG.doubleValue()));
                }
            } else {
                // 项目已到位资金
                // 初始化取【项目资金信息初始化】中的“已到位资金”（
                if (CollectionUtil.isNotEmpty(payAmountInitList)) {
                    BigDecimal sum = payAmountInitList.stream()
                            .map(PayAmountInit::getPiFundProjectAmount)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    gfpArrivedFunds = gfpArrivedFunds.add(sum);
                }
                // 集团账面余额
                // 初始化取【项目资金信息初始化】中的“集团账面余额”
                if (CollectionUtil.isNotEmpty(payAmountInitList)) {
                    BigDecimal sum = payAmountInitList.stream()
                            .map(PayAmountInit::getPiGroupBookBalancesAmount)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    gfpBalanceFunds = gfpBalanceFunds.add(sum);
                }
                // 集团已安排资金
                // 初始化取项目已到位资金-集团账面余额，并且将数据存下来，
                //（支持手动修改）
                gfpArrangeFunds = gfpArrivedFunds.subtract(gfpBalanceFunds);
                // 成员企业账面余额
                // 第一次取初始化
                if (CollectionUtil.isNotEmpty(payAmountInitList)) {
                    BigDecimal sum = payAmountInitList.stream()
                            .map(PayAmountInit::getPiMemberFirmsBookBalancesAmount)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    gfpMemberBalanceFunds = gfpMemberBalanceFunds.add(sum);
                }
            }
            groupFundVo.setGfpArrivedFunds(gfpArrivedFunds);
            groupFundVo.setGfpArrangeFunds(gfpArrangeFunds);
            groupFundVo.setGfpBalanceFunds(gfpBalanceFunds);
            groupFundVo.setGfpMemberBalanceFunds(gfpMemberBalanceFunds);
        } else {
            groupFundVo.setPInvTotal(BigDecimal.ZERO);
            groupFundVo.setPTotal(BigDecimal.ZERO);
            groupFundVo.setPApproveBudget(BigDecimal.ZERO);
            groupFundVo.setPApproveBudgetCode("");
            groupFundVo.setBdApprovalNum("");
            groupFundVo.setGfpArrivedFunds(BigDecimal.ZERO);
            groupFundVo.setGfpArrangeFunds(BigDecimal.ZERO);
            groupFundVo.setGfpBalanceFunds(BigDecimal.ZERO);
            groupFundVo.setGfpMemberBalanceFunds(BigDecimal.ZERO);
        }

        return BaseResponse.ok(groupFundVo);
    }

    @Override
    public BaseResponse getContractFundInfo(GroupFundPlanRequest request) {
        if (StrUtil.isEmpty(request.getProjectId())) {
            return BaseResponse.fail("缺失项目信息");
        }
        if (StrUtil.isEmpty(request.getContractId())) {
            return BaseResponse.fail("缺失合同信息");
        }
        Double fund = (double) 0;
        PreProject project = preProjectMapper.selectById(request.getProjectId());
        ConContract contract = conContractMapper.selectById(request.getContractId());
        if (project != null && contract != null) {
            if (project.getpCompetentDepartment().equals("7943b1b1-e2fb-4563-b485-4321146ecc39")) {
                // 最新的流程已结束的“合同支付”中的“截止上期累计实际支付”+“本期实际支付”。（项目管理单位为一分中心的项目）
                ConPayResponse conPayResponse = conPayMapper.getConPayByPId(request.getProjectId(), request.getContractId(), "01");
                Double conBG = 0.0;
                if (conPayResponse != null) {
                    if (conPayResponse.getPayTotalLast() != null) {
                        conBG += conPayResponse.getPayTotalLast().doubleValue();
                    }
                    if (conPayResponse.getPayApply() != null) {
                        conBG += conPayResponse.getPayApply().doubleValue();
                    }
                }
                fund = conBG;
            } else {
                // 最新已结束的“合同已支付数据维护”中的“截止本期累计已付款金额”
                List<ConPayResponse> payResponse = conPayMapper.getConPayWithoutPayTypeByPId(request.getProjectId(), request.getContractId());
                Double payBG = 0.0;
                if (CollectionUtil.isNotEmpty(payResponse)) {
                    payBG += payResponse.get(0).getPayTotal().doubleValue();
                    ;
                }
                fund = payBG.doubleValue();
            }
        } else {
            return BaseResponse.fail("缺失项目信息");
        }
        return BaseResponse.ok(fund);
    }


    @Override
    public BaseResponse getFundPlanInfo(GroupFundPlanRequest request) {
        QueryMap<GroupFundPlanRequest> params = new QueryMap<>(request);
        params.put("pName").put("projectId").put("gfpMonth").put("gfpYear");
        List<GroupFundPlanVo> groupFundPlanVos
                = baseMapper.getFundPlanInfo(params.getQuery());
        GroupFundPlanVo groupFundVo = new GroupFundPlanVo();
        if (groupFundPlanVos.size() > 0) {
            groupFundVo = groupFundPlanVos.stream().findFirst().get();
        }
        BigDecimal gfpArrivedFunds = BigDecimal.ZERO;// 项目已到位资金
        BigDecimal gfpArrangeFunds = BigDecimal.ZERO;// 集团已安排资金
        BigDecimal gfpBalanceFunds = BigDecimal.ZERO;// 集团账面余额
        BigDecimal gfpMemberBalanceFunds = BigDecimal.ZERO;// 成员企业账面余额
        List<PayAmountInit> payAmountInitList = payAmountInitMapper.getPayAmountInit(request.getProjectId());
        List<GroupFundPlan> groupFundPlans = baseMapper.getGroupFundPlans(request.getProjectId());
        if (CollectionUtil.isNotEmpty(groupFundPlans)) {
            gfpArrivedFunds = groupFundVo.getGfpArrivedFunds();
            // Gfp_Arrived_Funds  项目已到位资金（后续在此基础上累加已核销的收据金额）
            if (CollectionUtil.isNotEmpty(payAmountInitList)) {
                BigDecimal sum = payAmountInitList.stream()
                        .map(PayAmountInit::getPiFundProjectAmount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                gfpArrivedFunds = gfpArrivedFunds.add(sum);
            }
            List<PaymentReceipt> paymentReceipts = paymentReceiptMapper.getReceipt(request.getProjectId());
            if (CollectionUtil.isNotEmpty(paymentReceipts)) {
                BigDecimal sum = paymentReceipts.stream()
                        .map(PaymentReceipt::getPrAmount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                gfpArrivedFunds = gfpArrivedFunds.add(sum);
            }
            // Gfp_Arrange_Funds 集团已安排资金 第二次及以后取之前集团已安排资金+用款申请金额
            BigDecimal arrangeFundsSum = groupFundPlans.stream()
                    .map(GroupFundPlan::getGfpArrangeFunds)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            gfpArrangeFunds = gfpArrangeFunds.add(arrangeFundsSum);
            List<EngineeringFundApplication> engineeringFundApplications = baseMapper.getEngineeringFundApplicationByPId(request.getProjectId());
            if (CollectionUtil.isNotEmpty(engineeringFundApplications)) {
                BigDecimal sum = engineeringFundApplications.stream()
                        .map(EngineeringFundApplication::getEfaApplyAmount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                gfpArrangeFunds = gfpArrangeFunds.add(sum);
            }
            // Gfp_Balance_Funds 集团账面余额 取差值（项目已到位资金减去集团已安排资金）
            log.info("gfpArrivedFunds:{}", gfpArrivedFunds);
            log.info("gfpArrangeFunds:{}", gfpArrangeFunds);
            gfpBalanceFunds = gfpBalanceFunds.add(gfpArrivedFunds.subtract(gfpArrangeFunds));
            // gfpMemberBalanceFunds 成员企业账面余额
            // 1.建管项目：集团已安排资金
            // 减去该项目所有合同的最新一条“合同支付~【截止上期累计实际支付+本期实际支付】”的合计值，
            // 再减去该项目最新一条“规费支付~【截止上期累计实际支付+本期实际支付】”
            // 2.其他企业项目：集团已安排资金减去该项目所有“已支付数据维护~本期已付款金额”的合计值。
            PreProject project = preProjectMapper.selectById(request.getProjectId());
            if (project.getpCompetentDepartment().equals("7943b1b1-e2fb-4563-b485-4321146ecc39")) {
                ConPayResponse conPayResponse = conPayMapper.getConPayByPId(request.getProjectId(), null, "01");
                ConPayResponse projectPayResponse = conPayMapper.getConPayByPId(request.getProjectId(), null, "02");
                BigDecimal conBG = BigDecimal.ZERO;
                if (conPayResponse != null) {
                    conBG.add(conPayResponse.getPayTotalLast() != null ? conPayResponse.getPayTotalLast() : BigDecimal.ZERO);
                    conBG.add(conPayResponse.getPayApproval() != null ? conPayResponse.getPayApproval() : BigDecimal.ZERO);
                }
                BigDecimal proBG = BigDecimal.ZERO;
                if (projectPayResponse != null) {
                    proBG.add(projectPayResponse.getPayTotalLast() != null ? projectPayResponse.getPayTotalLast() : BigDecimal.ZERO);
                    proBG.add(projectPayResponse.getPayApproval() != null ? projectPayResponse.getPayApproval() : BigDecimal.ZERO);
                }
                gfpMemberBalanceFunds = gfpMemberBalanceFunds.add(gfpArrangeFunds.subtract(conBG).subtract(proBG));
            } else {
                List<ConPayResponse> payResponse = conPayMapper.getConPayWithoutPayTypeByPId(request.getProjectId(), null);
                BigDecimal payBG = BigDecimal.ZERO;
                for (ConPayResponse response : payResponse) {
                    if (response != null && response.getPayPayable() != null) {
                        payBG.add(response.getPayPayable());
                    }
                }
                gfpMemberBalanceFunds = gfpMemberBalanceFunds.add(gfpArrangeFunds.subtract(payBG));
            }
        } else {
            // 项目已到位资金
            // 初始化取【项目资金信息初始化】中的“已到位资金”（
            if (CollectionUtil.isNotEmpty(payAmountInitList)) {
                BigDecimal sum = payAmountInitList.stream()
                        .map(PayAmountInit::getPiFundProjectAmount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                gfpArrivedFunds = gfpArrivedFunds.add(sum);
            }
            // 集团账面余额
            // 初始化取【项目资金信息初始化】中的“集团账面余额”
            if (CollectionUtil.isNotEmpty(payAmountInitList)) {
                BigDecimal sum = payAmountInitList.stream()
                        .map(PayAmountInit::getPiGroupBookBalancesAmount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                gfpBalanceFunds = gfpBalanceFunds.add(sum);
            }
            // 集团已安排资金
            // 初始化取项目已到位资金-集团账面余额，并且将数据存下来，
            //（支持手动修改）
            gfpArrangeFunds = gfpArrivedFunds.subtract(gfpBalanceFunds);
            // 成员企业账面余额
            // 第一次取初始化
            if (CollectionUtil.isNotEmpty(payAmountInitList)) {
                BigDecimal sum = payAmountInitList.stream()
                        .map(PayAmountInit::getPiMemberFirmsBookBalancesAmount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                gfpMemberBalanceFunds = gfpMemberBalanceFunds.add(sum);
            }
        }
        // 集团账面余额
        groupFundVo.setGfpBalanceFunds(gfpBalanceFunds);
        log.info("groupFundVo:{}", JacksonUtils.toJSONString(groupFundVo));
        return BaseResponse.ok(groupFundVo);
    }

    @Override
    public BaseResponse getProjectInfo(String pId) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        GroupFundPlanVo projectInfo = baseMapper.getProjectInfo(pId);
        if (ValidateUtil.isNull(projectInfo)) {
            return BaseResponse.ok();
        }
        JSONArray jpMsoftXxjd = DictionaryUtils.dictList("JPMsoft_XXJD");
        projectInfo.setGfpProjectProgress(DictionaryUtils.processDictList(jpMsoftXxjd, projectInfo.getGfpProjectProgress()));
        return BaseResponse.ok(projectInfo);
    }

    @Override
    public BaseResponse getGroupFundPlanDetail(GroupFundPlanRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<GroupFundPlanDetailVo> groupFundPlanDetail = baseMapper.getGroupFundPlanDetail(request);

        Map<String, List<GroupFundPlanDetailVo>> groupedById = groupFundPlanDetail.stream()
                .collect(Collectors.groupingBy(GroupFundPlanDetailVo::getId));

        List<GroupFundPlanDetailVo> result = new ArrayList<>();
        for (Map.Entry<String, List<GroupFundPlanDetailVo>> entry : groupedById.entrySet()) {
            List<GroupFundPlanDetailVo> items = entry.getValue();
            if (CollectionUtil.isNotEmpty(items)) {
                GroupFundPlanDetailVo merged = items.get(0);

                String mergedNames = items.stream()
                        .map(GroupFundPlanDetailVo::getBudgetSubjectsName)
                        .filter(ValidateUtil::isNotEmpty)
                        .distinct()
                        .collect(Collectors.joining("；"));

                merged.setBudgetSubjectsName(mergedNames);
                result.add(merged);
            }
        }
        return BaseResponse.ok(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteGroupFundPlanById(GroupFundPlanRequest request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 判断是否已经使用
        boolean existed = groupFundPlanSummaryService.existUseByGfpId(request.getId());
        if (existed) {
            return BaseResponse.fail("该项目资金计划已被使用，无法删除");
        }
        GroupFundPlan groupFundPlan = this.getById(request.getId());
        if (!ApprovalStatusEnum.Not_Started.getIndex().equals(groupFundPlan.getState()) && !ApprovalStatusEnum.Invalid.getIndex().equals(groupFundPlan.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        this.removeById(groupFundPlan.getId());
        // List<GroupFundPlanDetail> list = groupFundPlanDetailService.list(Wrappers.<GroupFundPlanDetail>lambdaQuery().eq(GroupFundPlanDetail::getGfpId, groupFundPlan.getId()));
        // List<String> fileIds = list.stream().map(GroupFundPlanDetail::getGfpdAttachment).collect(Collectors.toList());
        // deleteDetailByEfaId(groupFundPlan.getId());
        // groupFundPlanDetailSubjectService.remove(Wrappers.<GroupFundPlanDetailSubject>lambdaQuery().eq(GroupFundPlanDetailSubject::getGfpId, groupFundPlan.getId()));
        // fileIds.add(groupFundPlan.getFundApplicationLetter());
        // sysFileAnnexService.remove(Wrappers.<SysFileannex>lambdaQuery().in(SysFileannex::getFileRelationId, fileIds));
        // sysFileService.removeById(groupFundPlan.getId());
        // baseMapper.deleteUseSealApplycationByUsaId(groupFundPlan.getUsaId());

        if ("1".equals(groupFundPlan.getGroupMainType())) {
            // 查询同一组的最新版本
            List<GroupFundPlan> list = this.list(Wrappers.<GroupFundPlan>lambdaQuery()
                    .eq(GroupFundPlan::getGroupId, groupFundPlan.getGroupId())
                    .orderByDesc(BaseEntity::getStateTime)
            );
            if (ValidateUtil.isNotEmpty(list)) {
                GroupFundPlan latest = list.get(0);
                latest.setGroupMainType("1");
                this.updateById(latest);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse refreshHistoryData(GroupFundPlanDto dto) {
        if (ValidateUtil.isNull(dto)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 当前最新的版本的id
        String mainId = dto.getId();
        GroupFundPlan plan = this.getById(mainId);
        if (ValidateUtil.isNull(plan)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!ApprovalStatusEnum.Already_Over.getIndex().equals(plan.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        // 历史组id
        String historyGroupId = dto.getGroupId();
        this.update(Wrappers.<GroupFundPlan>lambdaUpdate()
                .eq(GroupFundPlan::getGroupId, historyGroupId)
                .set(GroupFundPlan::getGroupMainType, "0")
                .ne(GroupFundPlan::getId, mainId)
        );
        this.update(Wrappers.<GroupFundPlan>lambdaUpdate()
                .set(GroupFundPlan::getGroupMainType, "1")
                .set(GroupFundPlan::getGroupId, historyGroupId)
                .eq(GroupFundPlan::getId, mainId)
        );
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(token)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 查询原来的数据
        GroupFundPlan groupFundPlan = this.getById(request.getFileId());
        if (ValidateUtil.isNull(groupFundPlan)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<GroupFundPlanDetail> details = groupFundPlanDetailService.list(Wrappers.<GroupFundPlanDetail>lambdaQuery().eq(GroupFundPlanDetail::getGfpId, groupFundPlan.getId()));
        List<GroupFundPlanDetailSubject> detailSubjects = groupFundPlanDetailSubjectService.list(Wrappers.<GroupFundPlanDetailSubject>lambdaQuery().eq(GroupFundPlanDetailSubject::getGfpId, groupFundPlan.getId()));
        Map<String, List<GroupFundPlanDetailSubject>> subjectMap = detailSubjects.stream()
                .collect(Collectors.groupingBy(GroupFundPlanDetailSubject::getGfpdId));
        GroupFundPlan entity = new GroupFundPlan();
        BeanUtil.copyProperties(groupFundPlan, entity);
        entity.setId(GenerateUUID.getUUID());
        entity.setState(ApprovalStatusEnum.Returned_Initiator.getIndex());
        entity.setCreateTime(new Date());
        entity.setStateTime(null);
        entity.setGroupMainType(null);
        entity.setFundApplicationLetter(GenerateUUID.getUUID());
        SysFile sysFile = sysFileService.getById(groupFundPlan.getId());
        String fileRelationId = GenerateUUID.getUUID();
        sysFileService.saveFile(entity.getId(), sysFile.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                , entity.getCreateTime(), entity.getProjectId(), sysFile.getFiletitle(), sysFile.getFilecode(),
                "create", fileRelationId, sysFile.getFilesectionid(), sysFile.gettCode(), sysFile.getFilecontract());
        this.save(entity);
        List<GroupFundPlanDetail> newDetails = new ArrayList<>();
        List<GroupFundPlanDetailSubject> newSubjects = new ArrayList<>();
        List<String> oldIds = new ArrayList<>();
        List<String> newIds = new ArrayList<>();
        for (GroupFundPlanDetail detail : details) {
            GroupFundPlanDetail newDetail = new GroupFundPlanDetail();
            BeanUtil.copyProperties(detail, newDetail);
            newDetail.setId(GenerateUUID.getUUID());
            newDetail.setGfpId(entity.getId());
            newDetail.setGfpdAttachment(GenerateUUID.getUUID());
            oldIds.add(detail.getGfpdAttachment());
            newIds.add(newDetail.getGfpdAttachment());
            newDetails.add(newDetail);
            if (subjectMap.containsKey(detail.getId())) {
                List<GroupFundPlanDetailSubject> subjects = subjectMap.get(detail.getId());
                for (GroupFundPlanDetailSubject subject : subjects) {
                    GroupFundPlanDetailSubject newSubject = new GroupFundPlanDetailSubject();
                    BeanUtil.copyProperties(subject, newSubject);
                    newSubject.setId(GenerateUUID.getUUID());
                    newSubject.setGfpId(entity.getId());
                    newSubject.setGfpdId(newDetail.getId());
                    newSubjects.add(newSubject);
                }
            }
        }
        if (ValidateUtil.isNotEmpty(newDetails)) {
            groupFundPlanDetailService.saveBatch(newDetails);
        }
        if (ValidateUtil.isNotEmpty(newSubjects)) {
            groupFundPlanDetailSubjectService.saveBatch(newSubjects);
        }
        oldIds.add(groupFundPlan.getFundApplicationLetter());
        newIds.add(entity.getFundApplicationLetter());
        sysFileAnnexService.copyFileAnnex2NewRelation(oldIds, newIds, entity.getId(), true);
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
        his.setTpSend(entity.getCreateUid());
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
        batchhandlingTaskListDto.setProcessed(entity.getCreateUid());
        // batchhandlingTaskListDto.setOpinion(request.getOpinion());
        batchhandlingTaskListDto.setUId(uid);
        batchhandlingTaskListDto.setOperator(uid);
        batchhandlingTaskDto.settCode(sysFile.gettCode());
        batchhandlingTaskDto.setSendList(Collections.singletonList(batchhandlingTaskListDto));
        batchhandlingTaskDto.setTeId("All");
        batchhandlingTaskDto.setCurrentNode("startNode");
        batchhandlingTaskDto.setOnlineConditions("false");
        batchhandlingTaskDto.setNotGenerateTask("1");
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                bpmFeign.batchSendTask(batchhandlingTaskDto, token);
            }
        });
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse existProject(GroupFundPlanRequest request) {
        return BaseResponse.ok(!existProject(request.getGroupId()));
    }

    private boolean existConMeasurePrice(String gfpId, String cmpId) {
        Integer integer = baseMapper.existConMeasurePrice(gfpId, cmpId);
        return integer != null && integer > 0;
    }
}
