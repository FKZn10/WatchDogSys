package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeterageApplyItemParam;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeteragePageRequest;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.FullProcessConsultServiceProtocolMeterageApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlan;
import com.jpmsoft.projectProducer.pojo.entity.contract.FullProcessConsultServiceProtocolMeterageApply;
import com.jpmsoft.projectProducer.pojo.entity.contract.FullProcessConsultServiceProtocolMeterageApplyItem;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdown;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolMeterageApplyVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolMeterageDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.MeteragePayVo;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanService;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IFullProcessConsultServiceProtocolMeterageApplyItemService;
import com.jpmsoft.projectProducer.service.contract.IFullProcessConsultServiceProtocolMeterageApplyService;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@Service
public class FullProcessConsultServiceProtocolMeterageApplyServiceImpl extends ServiceImpl<FullProcessConsultServiceProtocolMeterageApplyMapper, FullProcessConsultServiceProtocolMeterageApply> implements IFullProcessConsultServiceProtocolMeterageApplyService {
    @Resource
    private IConContractService contractService;
    @Resource
    private IConContractPayPlanService conContractPayPlanService;
    @Resource
    private IInvestmentBudgetBreakdownService investmentBudgetBreakdownService;
    @Resource
    private IFullProcessConsultServiceProtocolMeterageApplyItemService fullProcessConsultServiceProtocolMeterageApplyItemService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public FullProcessConsultServiceProtocolVo getServiceProtocolContentItemByProjectId(String projectId) {
        ConContract conContract = contractService.getOne(new LambdaQueryWrapper<ConContract>()
                .eq(ConContract::getConProjectId, projectId)
                .eq(ConContract::getConApprovalState, ApprovalStatusEnum.Already_Over.getIndex())
                .in(ConContract::getConType, "0701", "全过程咨询")
                .orderByDesc(ConContract::getConApprovalTime)
                .last("limit 1"));
        if (conContract == null) {
            return null;
        }
        List<MeteragePayVo> meteragePayVoList = new ArrayList<>();

        // 合同支付条款
        List<ConContractPayPlan> conContractPayPlanList = conContractPayPlanService.list(new LambdaQueryWrapper<ConContractPayPlan>()
                .eq(ConContractPayPlan::getConId, conContract.getConId()));
        InvestmentBudgetBreakdown investmentBudgetBreakdown = investmentBudgetBreakdownService.getInvestmentBudgetBreakdownByPIdData(projectId);
        BigDecimal budgetApproveAmount = BigDecimal.ZERO;
        if (ValidateUtil.isNotNull(investmentBudgetBreakdown)) {
            budgetApproveAmount = investmentBudgetBreakdown.getProjectBudgetInvestmentAmount();
        }
        // 查询之前的累计付款金额
        FullProcessConsultServiceProtocolMeterageApply apply = this.getOne(Wrappers.<FullProcessConsultServiceProtocolMeterageApply>lambdaQuery().eq(FullProcessConsultServiceProtocolMeterageApply::getProjectId, projectId)
                .eq(FullProcessConsultServiceProtocolMeterageApply::getContractId, conContract.getConId())
                .eq(FullProcessConsultServiceProtocolMeterageApply::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(FullProcessConsultServiceProtocolMeterageApply::getStateTime)
                .last("limit 1"));
        Map<String, BigDecimal> lastPayMap = new HashMap<>();
        if (ValidateUtil.isNotNull(apply)) {
            List<FullProcessConsultServiceProtocolMeterageApplyItem> list = fullProcessConsultServiceProtocolMeterageApplyItemService.list(Wrappers.<FullProcessConsultServiceProtocolMeterageApplyItem>lambdaQuery()
                    .eq(FullProcessConsultServiceProtocolMeterageApplyItem::getFullProcessConsultServiceProtocolMeterageApplyId, apply.getId())
            );
            list.forEach(item -> {
                BigDecimal last = Optional.ofNullable(item.getLastTotalPayAmount()).orElse(BigDecimal.ZERO);
                BigDecimal curr = Optional.ofNullable(item.getThisStagePlanPayAmount()).orElse(BigDecimal.ZERO);
                lastPayMap.put(item.getContent(), last.add(curr));
            });
        }
        for (ConContractPayPlan conContractPayPlan : conContractPayPlanList) {
            MeteragePayVo meteragePayVo = new MeteragePayVo();
            meteragePayVo.setId(conContractPayPlan.getCppId());
            meteragePayVo.setContent(conContractPayPlan.getCppName());
            meteragePayVo.setContractAmount(conContractPayPlan.getCppMoney());
            meteragePayVo.setPayTerms(conContractPayPlan.getCppClause());
            meteragePayVo.setBudgetApproveAmount(budgetApproveAmount);
            // TODO 截至上期累计支付需等待9-1-5集团资金支付完成后进行累加
            if (!ValidateUtil.isEmpty(lastPayMap)) {
                meteragePayVo.setLastTotalPayAmount(lastPayMap.get(conContractPayPlan.getCppName()));
            }
            meteragePayVoList.add(meteragePayVo);
        }

        FullProcessConsultServiceProtocolVo fullProcessConsultServiceProtocolVo = new FullProcessConsultServiceProtocolVo();
        fullProcessConsultServiceProtocolVo.setContractId(conContract.getConId());
        fullProcessConsultServiceProtocolVo.setContractName(conContract.getConName());
        fullProcessConsultServiceProtocolVo.setMeteragePayVoList(meteragePayVoList);
        fullProcessConsultServiceProtocolVo.setHasInvestBudgetBreakDown(ValidateUtil.isNotNull(investmentBudgetBreakdown));
        // fullProcessConsultServiceProtocolVo.setConMoneyDtlFlag(false);
        return fullProcessConsultServiceProtocolVo;
    }

    @Override
    public FullProcessConsultServiceProtocolMeterageDetailVo getDetailById(String id) {
        FullProcessConsultServiceProtocolMeterageApply entity = getById(id);
        if (entity == null) {
            return null;
        }
        List<FullProcessConsultServiceProtocolMeterageApplyItem> itemList = fullProcessConsultServiceProtocolMeterageApplyItemService.list(new LambdaQueryWrapper<FullProcessConsultServiceProtocolMeterageApplyItem>()
                .eq(FullProcessConsultServiceProtocolMeterageApplyItem::getFullProcessConsultServiceProtocolMeterageApplyId, entity.getId()));
        FullProcessConsultServiceProtocolMeterageDetailVo fullProcessConsultServiceProtocolMeterageDetailVo = new FullProcessConsultServiceProtocolMeterageDetailVo();
        BeanUtil.copyProperties(entity, fullProcessConsultServiceProtocolMeterageDetailVo);
        fullProcessConsultServiceProtocolMeterageDetailVo.setItemList(itemList);
        PreProject preProject = preProjectService.getOne(new LambdaQueryWrapper<PreProject>()
                .select(PreProject::getpName)
                .eq(PreProject::getpId, entity.getProjectId()));
        if (preProject != null) {
            fullProcessConsultServiceProtocolMeterageDetailVo.setProjectName(preProject.getpName());
        }
        ConContract contract = contractService.getOne(new LambdaQueryWrapper<ConContract>()
                .select(ConContract::getConName)
                .eq(ConContract::getConId, entity.getContractId()));
        if (contract != null) {
            fullProcessConsultServiceProtocolMeterageDetailVo.setContractName(contract.getConName());
        }
        SysFile sysFile = sysFileService.getOne(new LambdaQueryWrapper<SysFile>()
                .eq(SysFile::getFileid, entity.getId())
                .last("limit 1"));
        if (sysFile != null) {
            fullProcessConsultServiceProtocolMeterageDetailVo.setFileRelationId(sysFile.getFileRelationId());
        }
        InvestmentBudgetBreakdown investmentBudgetBreakdown = investmentBudgetBreakdownService.getOne(new LambdaQueryWrapper<InvestmentBudgetBreakdown>()
                .eq(InvestmentBudgetBreakdown::getProjectId, entity.getProjectId())
                .eq(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentBudgetBreakdown::getStateTime)
                .last("limit 1"));
        fullProcessConsultServiceProtocolMeterageDetailVo.setHasInvestBudgetBreakDown(investmentBudgetBreakdown != null);
        return fullProcessConsultServiceProtocolMeterageDetailVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FullProcessConsultServiceProtocolMeterageApply createOrUpdate(FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest request) {
        FullProcessConsultServiceProtocolMeterageApply entity = new FullProcessConsultServiceProtocolMeterageApply();
        BeanUtil.copyProperties(request, entity, CopyOptions.create().setIgnoreProperties("state", "stateTime"));
        boolean isCreate = StrUtil.isBlank(entity.getId());
        if (StrUtil.isBlank(entity.getId())) {
            entity.setId(GenerateUUID.getUUID());
        }
        List<FullProcessConsultServiceProtocolMeterageApplyItemParam> itemParamList = request.getItemList();
        List<FullProcessConsultServiceProtocolMeterageApplyItem> itemList = new ArrayList<>(itemParamList.size());
        if (ValidateUtil.isNotEmpty(itemParamList)) {
            for (FullProcessConsultServiceProtocolMeterageApplyItemParam itemParam : itemParamList) {
                itemParam.setFullProcessConsultServiceProtocolMeterageApplyId(entity.getId());
                FullProcessConsultServiceProtocolMeterageApplyItem item = new FullProcessConsultServiceProtocolMeterageApplyItem();
                BeanUtil.copyProperties(itemParam, item);
                item.setId(GenerateUUID.getUUID());
                itemList.add(item);
            }
        }
        if (isCreate) {
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setCreateTime(new Date());
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", request.getCreateUid(), request.getCreateUname(),
                    entity.getCreateTime(), request.getProjectId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), request.getId());
        } else {
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(),
                    entity.getCreateTime(), entity.getProjectId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()), Arrays.asList(request.getFileIds()), entity.getId());
        }
        fullProcessConsultServiceProtocolMeterageApplyItemService.remove(Wrappers.<FullProcessConsultServiceProtocolMeterageApplyItem>lambdaQuery()
                .eq(FullProcessConsultServiceProtocolMeterageApplyItem::getFullProcessConsultServiceProtocolMeterageApplyId, entity.getId()));
        if (CollUtil.isNotEmpty(itemList)) {
            fullProcessConsultServiceProtocolMeterageApplyItemService.saveBatch(itemList);
        }
        this.saveOrUpdate(entity);
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        FullProcessConsultServiceProtocolMeterageApply fullProcessConsultServiceProtocolMeterageApply = getById(id);
        if (fullProcessConsultServiceProtocolMeterageApply == null) {
            return;
        }
        if (!ApprovalStatusEnum.Not_Started.getIndex().equals(fullProcessConsultServiceProtocolMeterageApply.getState())) {
            throw new JPMException("仅允许删除未发起流程的记录");
        }
        removeById(id);
        fullProcessConsultServiceProtocolMeterageApplyItemService.remove(Wrappers.<FullProcessConsultServiceProtocolMeterageApplyItem>lambdaQuery()
                .eq(FullProcessConsultServiceProtocolMeterageApplyItem::getFullProcessConsultServiceProtocolMeterageApplyId, id));
        sysFileService.removeById(id);
    }

    @Override
    public BaseResponse pageFullProcessConsultServiceProtocolMeterageApply(FullProcessConsultServiceProtocolMeteragePageRequest request, String token) {
        String uId = redisUtils.getUid(token);
        if (ValidateUtil.isEmpty(uId)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        Page<FullProcessConsultServiceProtocolMeterageApplyVO> page = baseMapper.pageFullProcessConsultServiceProtocolMeterageApply(new Page<>(request.getPageNum(), request.getPageSize()), request, uId);
        return BaseResponse.ok(page.getRecords(), Math.toIntExact(page.getTotal()));
    }

    @Override
    public List<FullProcessConsultServiceProtocolMeterageApplyVO> listFullProcessConsultServiceProtocolMeterageApply(FullProcessConsultServiceProtocolMeteragePageRequest request, String token) {
        String uId = redisUtils.getUid(token);
        if (ValidateUtil.isEmpty(uId)) {
            throw new JPMException(ResMsg.noPermission);
        }
        return baseMapper.listFullProcessConsultServiceProtocolMeterageApply(request, uId);
    }

    @Override
    public BaseResponse getTZClassInfo(List<String> projectIdList, String token) throws ClassNotFoundException, IllegalAccessException {
        String uId = redisUtils.getUid(token);
        if (ValidateUtil.isEmpty(uId)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        List<ClassInfoAnnotation> projectClassInfoAnnotations = ClassInfoUtils.getClassInfo(FullProcessConsultServiceProtocolMeteragePageRequest.class.getName());
        List<Object> dictionaryValues = new ArrayList<>();
        FullProcessConsultServiceProtocolMeteragePageRequest request = new FullProcessConsultServiceProtocolMeteragePageRequest();
        request.setProjectIds(projectIdList);
        List<FullProcessConsultServiceProtocolMeterageApplyVO> voList = baseMapper.listFullProcessConsultServiceProtocolMeterageApply(request, uId);
        for (ClassInfoAnnotation classInfoAnnotation : projectClassInfoAnnotations) {
            dictionaryValues = new ArrayList<>();
            if (!classInfoAnnotation.getType().equals("date")) {
                if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                    if (CollectionUtil.isNotEmpty(voList)) {
                        for (FullProcessConsultServiceProtocolMeterageApplyVO secBiddingPlanTZRespose : voList) {
                            Class<FullProcessConsultServiceProtocolMeterageApplyVO> secBiddingPlanTZResposeClass = FullProcessConsultServiceProtocolMeterageApplyVO.class;
                            Field[] declaredFields = secBiddingPlanTZResposeClass.getDeclaredFields();
                            for (int i = 0; i < declaredFields.length; i++) {
                                Field field = declaredFields[i];
                                field.setAccessible(true);
                                String fieldName = field.getName();
                                if (fieldName.equals(classInfoAnnotation.getDbValue())) {
                                    Object o = field.get(secBiddingPlanTZRespose);
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
        return BaseResponse.ok(projectClassInfoAnnotations);
    }


    @Override
    public BaseResponse getLastDeadline(String pId, String conId) {
        if (ValidateUtil.isAllEmpty(pId, conId)) return BaseResponse.fail("请求参数缺少");
        LambdaQueryWrapper<FullProcessConsultServiceProtocolMeterageApply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FullProcessConsultServiceProtocolMeterageApply::getProjectId, pId);
        queryWrapper.eq(FullProcessConsultServiceProtocolMeterageApply::getContractId, conId);
        queryWrapper.eq(FullProcessConsultServiceProtocolMeterageApply::getState, 7);
        queryWrapper.orderByDesc(FullProcessConsultServiceProtocolMeterageApply::getStateTime);
        queryWrapper.last(" limit 1 ");
        FullProcessConsultServiceProtocolMeterageApply apply = baseMapper.selectOne(queryWrapper);
        if (ValidateUtil.isNull(apply)) return BaseResponse.ok();
        List<FullProcessConsultServiceProtocolMeterageApplyItem> list = fullProcessConsultServiceProtocolMeterageApplyItemService.getAppIdList(apply.getId());
        list.forEach(e -> {
            e.setLastTotalPayAmount(e.getTotalShouldPayAmount());
        });
        return BaseResponse.ok(list);
    }
}
