package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.FlowStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.request.Contract.MultiContractConEcRequest;
import com.jpmsoft.common.model.response.Contract.ConEcResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConEcMapper;
import com.jpmsoft.projectProducer.mapper.contract.MultiContractConEcMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEc;
import com.jpmsoft.projectProducer.pojo.entity.contract.MultiContractConEc;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractConEcHistoryVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.MultiContractConEcDetailVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.MultiContractConEcPageVO;
import com.jpmsoft.projectProducer.service.contract.IConEcService;
import com.jpmsoft.projectProducer.service.contract.IMultiContractConEcService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.LogicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MultiContractConEcServiceImpl extends ServiceImpl<MultiContractConEcMapper, MultiContractConEc> implements IMultiContractConEcService {

    private static final String DESIGN_CHANGE_TYPE = "01";
    private static final String SITE_VISA_TYPE = "02";

    @Resource
    private PreProjectMapper preProjectMapper;

    @Resource
    private ConEcMapper conEcMapper;

    @Resource
    private IConEcService conEcService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysConfigService sysConfigService;
    @Override
    public Page<MultiContractConEcPageVO> page(Integer pageNum, Integer pageSize, String projectName, String pId,String ecChangeType,String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        return baseMapper.page(new Page<>(pageNum, pageSize), projectName, pId, ecChangeType,authSql);
    }

    private String getAuthSql(Integer OutUserAuthEnum, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum, "State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        String code = "projectDateLimit";
        String str = sysConfigService.getConfigByCode(code);
        if (StringUtils.isNotEmpty(str)) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return authSql;
    }

    @Override
    public MultiContractConEcDetailVO detail(String uuid, String userId) {
        MultiContractConEc multiContractConEc = getById(uuid);
        if (multiContractConEc == null) {
            return null;
        }
        MultiContractConEcDetailVO multiContractConEcDetailVO = new MultiContractConEcDetailVO();
        BeanUtil.copyProperties(multiContractConEc, multiContractConEcDetailVO);
        // 项目名称
        PreProject preProject = preProjectMapper.selectById(multiContractConEc.getProjectId());
        if (preProject != null) {
            multiContractConEcDetailVO.setProjectName(preProject.getpName());
        }

        Map<String, Object> authParams = getAuthParams(new ConEcRequest(), userId);
        authParams.put("multiContractConEcId", uuid);
        // 子表信息
        List<ConEcResponse> conEcResponseList = conEcMapper.listConEc(authParams);

        multiContractConEcDetailVO.setConEcResponseList(conEcResponseList);

        return multiContractConEcDetailVO;
    }

    @Override
    public BigDecimal getProjectTotalConEcMoney(String projectId, String ecChangeType, String contractId) {
        BigDecimal projectTotalConEcMoney = conEcMapper.getProjectTotalConEcMoney(projectId, ecChangeType, contractId);
        return projectTotalConEcMoney == null ? BigDecimal.ZERO : projectTotalConEcMoney;
    }

    /**
     * {@inheritDoc}
     * 以主流程状态为准，避免子表状态、审批时间未同步导致跨流程数据遗漏。
     */
    @Override
    public List<ContractConEcHistoryVO> getContractChangeHistory(
            String projectId, String contractId, String excludeEcId, String userId) {
        if (StrUtil.isBlank(projectId) || StrUtil.isBlank(contractId)) {
            throw new JPMException(ResMsg.notReqParam);
        }
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), userId);
        return conEcMapper.getContractChangeHistory(
                projectId, contractId, StrUtil.trimToNull(excludeEcId),
                ApprovalStatusEnum.Already_Over.getIndex(), authSql);
    }

    @Override
    public BigDecimal getFinishedDesignChangeApplyMoneyByProjectId(String projectId, String userId) {
        if (ValidateUtil.isEmpty(projectId)) {
            throw new JPMException(ResMsg.notReqParam);
        }
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), userId);
        BigDecimal totalMoney = baseMapper.getFinishedDesignChangeApplyMoneyByProjectId(
                projectId,
                ApprovalStatusEnum.Already_Over.getIndex(),
                authSql);
        return totalMoney == null ? BigDecimal.ZERO : totalMoney;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MultiContractConEc createOrUpdate(MultiContractConEcRequest multiContractConEcRequest) {
        MultiContractConEc multiContractConEc = null;
        boolean isCreate = StrUtil.isBlank(multiContractConEcRequest.getUuid());
        if (isCreate) {
            multiContractConEc = new MultiContractConEc();
            multiContractConEc.setUuid(UUID.randomUUID().toString());
            multiContractConEc.setCreateTime(new Date());
            multiContractConEc.setCreateUid(multiContractConEcRequest.getCreateUid());
            multiContractConEc.setCreateUname(multiContractConEcRequest.getCreateUname());
            multiContractConEc.setState(FlowStatusEnum.Null.getIndex());
        } else {
            multiContractConEc = getById(multiContractConEcRequest.getUuid());
            if (multiContractConEc == null) {
                throw new JPMException("无效数据");
            }
        }
        BeanUtil.copyProperties(multiContractConEcRequest, multiContractConEc
                , CopyOptions.create().setIgnoreProperties("createUid","createUname", "createTime")
                        .setIgnoreNullValue(true));

        saveOrUpdate(multiContractConEc);
        if (isCreate){
            sysFileService.saveFile(multiContractConEc.getUuid(), null, multiContractConEc.getState() + "", multiContractConEc.getCreateUid(), multiContractConEc.getCreateUname()
                    , multiContractConEc.getCreateTime(), multiContractConEc.getProjectId(), multiContractConEcRequest.getFileTitle(), multiContractConEcRequest.getFileCode(),
                    "create", multiContractConEcRequest.getFileRelationId(), null, multiContractConEcRequest.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(multiContractConEcRequest.getOtherFileRelationId(),multiContractConEcRequest.getApprovalFileRelationId(),
                    multiContractConEcRequest.getChangeAndTechSolutionAndChangeDesignFileRelationId(),multiContractConEcRequest.getChangeGroupFileRelationId(),multiContractConEcRequest.getVisaGroupFileRelationId()), multiContractConEc.getUuid());
        }else {
            sysFileService.saveFile(multiContractConEc.getUuid(), null, multiContractConEc.getState() + "", multiContractConEc.getCreateUid(), multiContractConEc.getCreateUname()
                    , multiContractConEc.getCreateTime(), multiContractConEc.getProjectId(), multiContractConEcRequest.getFileTitle(), multiContractConEcRequest.getFileCode(),
                    "update", multiContractConEcRequest.getFileRelationId(), null, multiContractConEcRequest.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(multiContractConEcRequest.getOtherFileRelationId(),multiContractConEcRequest.getApprovalFileRelationId(),
                    multiContractConEcRequest.getChangeAndTechSolutionAndChangeDesignFileRelationId(),multiContractConEcRequest.getChangeGroupFileRelationId(),multiContractConEcRequest.getVisaGroupFileRelationId())
                    ,Arrays.asList(multiContractConEcRequest.getOtherFileRelationIds(),multiContractConEcRequest.getApprovalFileRelationIds(),
                            multiContractConEcRequest.getChangeAndTechSolutionAndChangeDesignFileRelationIds(),multiContractConEcRequest.getChangeGroupFileRelationIds(),multiContractConEcRequest.getVisaGroupFileRelationIds()), multiContractConEc.getUuid());
        }
        // 找出需要删除的变更签证记录
        List<String> oldConEcIdList = conEcService.list(new LambdaQueryWrapper<ConEc>()
                        .select(ConEc::getEcId)
                        .eq(ConEc::getMultiContractConEcId, multiContractConEcRequest.getUuid()))
                .stream().map(ConEc::getEcId).collect(Collectors.toList());
        List<ConEcRequest> conEcRequestList = multiContractConEcRequest.getConEcRequestList();
        conEcRequestList = conEcRequestList == null ? Collections.emptyList() : conEcRequestList;
        List<String> newConEcIdList = conEcRequestList.stream().map(ConEcRequest::getEcId).collect(Collectors.toList());
        List<String> needRemoveConEcIdList = oldConEcIdList.stream().filter(e -> !newConEcIdList.contains(e)).collect(Collectors.toList());
        conEcService.removeByIds(needRemoveConEcIdList);
        if (CollUtil.isNotEmpty(conEcRequestList)) {
            for (ConEcRequest conEcRequest : conEcRequestList) {
                conEcRequest.setMultiContractConEcId(multiContractConEc.getUuid());
                conEcRequest.setEcChangeType(multiContractConEc.getEcChangeType());
                conEcRequest.setEcCreateUid(multiContractConEc.getCreateUid());
                conEcRequest.setEcCreateUname(multiContractConEc.getCreateUname());
                conEcService.createOrUpdateConEc(conEcRequest);
            }
        }
        return multiContractConEc;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processSyn(String uuid) {
        MultiContractConEc multiContractConEc = getById(uuid);
        if (multiContractConEc == null) {
            return;
        }
        List<ConEc> conEcList = conEcService.list(new LambdaQueryWrapper<ConEc>()
                .eq(ConEc::getMultiContractConEcId, multiContractConEc.getUuid()));
        for (ConEc conEc : conEcList) {
            conEc.setEcState(multiContractConEc.getState());
            conEc.setEcStateTime(multiContractConEc.getStateTime());
        }
        conEcService.saveOrUpdateBatch(conEcList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer syncChildState(String id, String type) {
        if (ValidateUtil.isEmpty(id) || ValidateUtil.isEmpty(type)) {
            throw new JPMException(ResMsg.notReqParam);
        }
        Integer finishedState = ApprovalStatusEnum.Already_Over.getIndex();
        if (DESIGN_CHANGE_TYPE.equals(type)) {
            return conEcMapper.updateDesignChangeChildState(id, type, finishedState);
        }
        if (SITE_VISA_TYPE.equals(type)) {
            return conEcMapper.updateSiteVisaChildState(id, type, finishedState);
        }
        throw new JPMException("type参数仅支持01或02");
    }

    private Map<String, Object> getAuthParams(ConEcRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Ec", uId, "Ec_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ec_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", null);
        setFilterParams(params, request);
        return params;
    }

    private void setFilterParams(Map<String, Object> params, ConEcRequest request) {
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "Ec_Create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);

        params.put("ecApplyMoneyType`", ValidateUtil.isEmpty(request.getEcApplyMoneyType()) ? null : request.getEcApplyMoneyType());
        params.put("ecDurationType", ValidateUtil.isEmpty(request.getEcDurationType()) ? null : request.getEcDurationType());
        params.put("ecDesignOrder", ValidateUtil.isEmpty(request.getEcDesignOrder()) ? null : request.getEcDesignOrder());
        params.put("ecCode", ValidateUtil.isEmpty(request.getEcCode()) ? null : request.getEcCode());
        params.put("ecResponsibleParty", ValidateUtil.isEmpty(request.getEcResponsibleParty()) ? null : request.getEcResponsibleParty());
        params.put("ecGrade", ValidateUtil.isEmpty(request.getEcGrade()) ? null : request.getEcGrade());
        params.put("ecWarn", ValidateUtil.isEmpty(request.getEcWarn()) ? null : request.getEcWarn());
        params.put("pName", ValidateUtil.isEmpty(request.getpName()) ? null : request.getpName());
        params.put("conName", ValidateUtil.isEmpty(request.getConName()) ? null : request.getConName());
        params.put("ecDesignOrderCode", ValidateUtil.isEmpty(request.getEcDesignOrderCode()) ? null : request.getEcDesignOrderCode());
        params.put("pId", ValidateUtil.isEmpty(request.getpId()) ? null : request.getpId());
        params.put("ecState", ValidateUtil.isEmpty(request.getEcState()) ? null : request.getEcState());
        params.put("conEcFeeFilter", ValidateUtil.isEmpty(request.getConEcFeeFilter()) ? null : request.getConEcFeeFilter());
        params.put("ecCreateUname", ValidateUtil.isEmpty(request.getEcCreateUname()) ? null : request.getEcCreateUname());
        params.put("ecPartName", ValidateUtil.isEmpty(request.getEcPartName()) ? null : request.getEcPartName());
        params.put("ecType", ValidateUtil.isEmpty(request.getEcType()) ? null : request.getEcType());
        params.put("ecDep", ValidateUtil.isEmpty(request.getEcDep()) ? null : request.getEcDep());
        params.put("ecAuditType", ValidateUtil.isEmpty(request.getEcAuditType()) ? null : request.getEcAuditType());
        params.put("ecDepName", ValidateUtil.isEmpty(request.getEcDepName()) ? null : request.getEcDepName());
        params.put("pConstructionUnitName", ValidateUtil.isEmpty(request.getpConstructionUnitName()) ? null : request.getpConstructionUnitName());

        params.put("ecConstName", ValidateUtil.isEmpty(request.getEcConstName()) ? null : request.getEcConstName());
        params.put("ecCostConsulName", ValidateUtil.isEmpty(request.getEcCostConsulName()) ? null : request.getEcCostConsulName());
        params.put("ecSupervisorName", ValidateUtil.isEmpty(request.getEcSupervisorName()) ? null : request.getEcSupervisorName());
        params.put("ecReason", ValidateUtil.isEmpty(request.getEcReason()) ? null : request.getEcReason());
        params.put("ecContent", ValidateUtil.isEmpty(request.getEcContent()) ? null : request.getEcContent());
        params.put("enisCode", ValidateUtil.isEmpty(request.getEnisCode()) ? null : request.getEnisCode());
        params.put("ecName", ValidateUtil.isEmpty(request.getEcName()) ? null : request.getEcName());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("ecChangeType", ValidateUtil.isEmpty(request.getEcChangeType()) ? null : request.getEcChangeType());
        params.put("enisName", ValidateUtil.isEmpty(request.getEnisName()) ? null : request.getEnisName());
        params.put("ecMainCause", ValidateUtil.isEmpty(request.getEcMainCause()) ? null : request.getEcMainCause());
        params.put("ecDrawingLibrary", ValidateUtil.isEmpty(request.getEcDrawingLibrary()) ? null : request.getEcDrawingLibrary());
        params.put("ecConstructionName", ValidateUtil.isEmpty(request.getEcConstructionName()) ? null : request.getEcConstructionName());
        LogicUtil.putParamToMap(params, request.getEcTotalMoneys(), "ecTotalMoneyStart", "ecTotalMoneyEnd");
        LogicUtil.putParamToMap(params, request.getEcTotalMoneyPluses(), "ecTotalMoneyPlusStart", "ecTotalMoneyPlusEnd");
        LogicUtil.putParamToMap(params, request.getEcTotalMoneyMinuses(), "ecTotalMoneyMinusStart", "ecTotalMoneyMinusEnd");
        LogicUtil.putParamToMap(params, request.getEcFinalMoneyAfters(), "ecFinalMoneyAfterStart", "ecFinalMoneyAfterEnd");
        LogicUtil.putParamToMap(params, request.getEcFinalMoneys(), "ecFinalMoneyStart", "ecFinalMoneyEnd");
        LogicUtil.putParamToMap(params, request.getEcTotalMoneyCs(), "ecTotalMoneyCStart", "ecTotalMoneyCEnd");
        LogicUtil.putParamToMap(params, request.getEcTotalMoneyCRates(), "ecTotalMoneyCRateStart", "ecTotalMoneyCRateEnd");

        LogicUtil.putParamToMap(params, request.getEcCreateTimes(), "ecCreateTimeStart", "ecCreateTimeEnd");
        LogicUtil.putParamToMap(params, request.getEcApplyMoneys(), "ecApplyMoneyStart", "ecApplyMoneyEnd");
        LogicUtil.putParamToMap(params, request.getEcDurations(), "ecDurationStart", "ecDurationEnd");
    }
}
