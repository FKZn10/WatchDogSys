package com.jpmsoft.projectProducer.service.contract.impl;

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
import com.jpmsoft.common.model.request.Contract.ContractMeasurementContractPageRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractMeasurementValuationModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractPaymentApplicationModel;
import com.jpmsoft.common.model.response.Contract.ContractMeasurementContractResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ContractMeasurementValuationMapper;
import com.jpmsoft.projectProducer.mapper.contract.ContractPaymentApplicationMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractMeasurementValuation;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPaymentApplication;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.contract.IContractMeasurementValuationService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 合同计量计价低代码表单 Service 实现
 */
@Service
public class ContractMeasurementValuationServiceImpl
        extends ServiceImpl<ContractMeasurementValuationMapper, ContractMeasurementValuation>
        implements IContractMeasurementValuationService {

    @Resource
    private ContractPaymentApplicationMapper contractPaymentApplicationMapper;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateContractMeasurementValuation(ContractMeasurementValuationModel request,
                                                                    String token) {
        if (ValidateUtil.isNull(request)
                || ValidateUtil.isEmpty(request.getPId())
                || ValidateUtil.isEmpty(request.getConId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        if (!canAccessContract(request.getConId(), request.getPId(), user)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        ContractMeasurementValuation entity = new ContractMeasurementValuation();
        BeanUtils.copyProperties(request, entity);
        boolean isNew = ValidateUtil.isEmpty(request.getId());
        if (isNew) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            entity.setCreateTime(new Date());
            entity.setState(ValidateUtil.isNull(request.getState())
                    ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            entity.setStateTime(null);
            if (baseMapper.insert(entity) != 1) {
                throw new JPMException(ResMsg.actError);
            }
        } else {
            ContractMeasurementValuation old = baseMapper.selectById(request.getId());
            if (ValidateUtil.isNull(old)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            if (!canAccessContract(old.getConId(), old.getPId(), user)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            if (!Objects.equals(old.getConId(), request.getConId())
                    || !Objects.equals(old.getPId(), request.getPId())) {
                return BaseResponse.fail("项目和合同不可修改！");
            }
            if (JudgeUtil.nonModifyState(old.getState())) {
                return BaseResponse.fail(ResMsg.stateNotModify);
            }
            entity.setCreateUid(old.getCreateUid());
            entity.setCreateUname(old.getCreateUname());
            entity.setCreateTime(old.getCreateTime());
            entity.setState(old.getState());
            entity.setStateTime(old.getStateTime());
            if (baseMapper.updateById(entity) != 1) {
                throw new JPMException(ResMsg.actError);
            }
        }

        replacePaymentApplications(request.getContractPaymentApplications(), entity, user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getContractMeasurementValuationById(String id, String token) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ContractMeasurementValuationModel response = baseMapper.getContractMeasurementValuationById(id);
        if (ValidateUtil.isNull(response)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        if (!canAccessContract(response.getConId(), response.getPId(), user)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        List<ContractPaymentApplication> applications = contractPaymentApplicationMapper.selectList(
                Wrappers.<ContractPaymentApplication>lambdaQuery()
                        .eq(ContractPaymentApplication::getContractMeasurementValuationId, id)
                        .orderByAsc(ContractPaymentApplication::getCreateTime)
                        .orderByAsc(ContractPaymentApplication::getId)
        );
        response.setContractPaymentApplications(
                ValidateUtil.isEmpty(applications)
                        ? Collections.emptyList()
                        : BeanUtils.copyList(applications, ContractPaymentApplicationModel.class)
        );
        return BaseResponse.ok(response);
    }

    @Override
    public BaseResponse getContractMeasurementValuationList(ContractMeasurementValuationModel request,
                                                             String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        String authSql = getMeasurementValuationAuthSql(user);
        if (ValidateUtil.isEmpty(authSql) || !hasValidTenant(user)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<ContractMeasurementValuationModel> params = buildListParams(request, user, authSql);
        int pageNumber = request.getPage() <= 0 ? 1 : request.getPage();
        int pageSize = request.getSize() <= 0 ? 10 : Math.min(request.getSize(), 100);
        IPage<ContractMeasurementValuationModel> page = new Page<>(pageNumber, pageSize);
        List<ContractMeasurementValuationModel> data = baseMapper
                .getContractMeasurementValuationList(page, params.getQuery());
        fillPaymentApplications(data);
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getContractMeasurementValuationClassInfo(ContractMeasurementValuationModel request,
                                                                  String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        String authSql = getMeasurementValuationAuthSql(user);
        if (ValidateUtil.isEmpty(authSql) || !hasValidTenant(user)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<ContractMeasurementValuationModel> params = buildListParams(request, user, authSql);
        List<ClassInfoAnnotation> classInfoAnnotations;
        try {
            classInfoAnnotations = ClassInfoUtils
                    .getClassInfo(ContractMeasurementValuationModel.class.getName());
        } catch (ClassNotFoundException e) {
            throw new JPMException(ResMsg.actError);
        }
        String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotations);
        List<Map<String, Object>> values = baseMapper
                .getContractMeasurementValuationAllValues(selectClause, params.getQuery());
        return BaseResponse.ok(ClassInfoUtils.resultsHandle(values, classInfoAnnotations));
    }

    @Override
    public BaseResponse getFinishedConsultAndInternalAgencyContractListByPId(
            ContractMeasurementContractPageRequest request, String token) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getPId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        int pageNumber = request.getPage() <= 0 ? 1 : request.getPage();
        int pageSize = request.getSize() <= 0 ? 10 : Math.min(request.getSize(), 100);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        String authSql = getContractAuthSql(user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        String teId = user.isEnableTenant() ? user.getTeId() : null;
        if (user.isEnableTenant() && ValidateUtil.isEmpty(teId)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        Page<ContractMeasurementContractResponse> page = new Page<>(pageNumber, pageSize);
        List<ContractMeasurementContractResponse> contracts = baseMapper
                .getFinishedConsultAndInternalAgencyContractListByPId(
                        page, request.getPId(), teId, authSql);
        if (ValidateUtil.isEmpty(contracts)) {
            return BaseResponse.ok(Collections.emptyList(), Math.toIntExact(page.getTotal()));
        }
        return BaseResponse.ok(contracts, Math.toIntExact(page.getTotal()));
    }

    private boolean canAccessContract(String conId, String pId, LogUserInfoVo user) {
        String authSql = getContractAuthSql(user);
        if (ValidateUtil.isEmpty(authSql)) {
            return false;
        }
        String teId = user.isEnableTenant() ? user.getTeId() : null;
        if (user.isEnableTenant() && ValidateUtil.isEmpty(teId)) {
            return false;
        }
        return baseMapper.countAccessibleContract(conId, pId, teId, authSql) > 0;
    }

    private String getContractAuthSql(LogUserInfoVo user) {
        if (ValidateUtil.isNull(user) || ValidateUtil.isEmpty(user.getId())) {
            return null;
        }
        return sysUserService.onlyCreateorAndApprover(
                "Con_Contract", user.getId(), "Con_Create_User_Id",
                OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State", "Con_Id"
        );
    }

    private String getMeasurementValuationAuthSql(LogUserInfoVo user) {
        if (ValidateUtil.isNull(user) || ValidateUtil.isEmpty(user.getId())) {
            return null;
        }
        return sysUserService.getAuthSql(
                "contract_measurement_valuation", user.getId(), "Create_Uid",
                OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id"
        );
    }

    private boolean hasValidTenant(LogUserInfoVo user) {
        return ValidateUtil.isNotNull(user)
                && (!user.isEnableTenant() || ValidateUtil.isNotEmpty(user.getTeId()));
    }

    private QueryMap<ContractMeasurementValuationModel> buildListParams(
            ContractMeasurementValuationModel request, LogUserInfoVo user, String authSql) {
        QueryMap<ContractMeasurementValuationModel> params = new QueryMap<>(request);
        params.put("pId").put("conId").put("state").put("pName").put("conName").put("createUname")
                .put("pNameList").put("conNameList").put("stateList").put("createUnameList");
        params.put("authSql", authSql);
        params.put("teId", user.isEnableTenant() ? user.getTeId() : null);
        return params;
    }

    private void fillPaymentApplications(List<ContractMeasurementValuationModel> data) {
        if (ValidateUtil.isEmpty(data)) {
            return;
        }
        List<String> valuationIds = data.stream()
                .map(ContractMeasurementValuationModel::getId)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(valuationIds)) {
            data.forEach(item -> item.setContractPaymentApplications(Collections.emptyList()));
            return;
        }
        List<ContractPaymentApplication> applications = contractPaymentApplicationMapper.selectList(
                Wrappers.<ContractPaymentApplication>lambdaQuery()
                        .in(ContractPaymentApplication::getContractMeasurementValuationId, valuationIds)
                        .orderByAsc(ContractPaymentApplication::getCreateTime)
                        .orderByAsc(ContractPaymentApplication::getId)
        );
        List<ContractPaymentApplicationModel> applicationModels = ValidateUtil.isEmpty(applications)
                ? Collections.emptyList()
                : BeanUtils.copyList(applications, ContractPaymentApplicationModel.class);
        Map<String, List<ContractPaymentApplicationModel>> applicationMap = applicationModels.stream()
                .collect(Collectors.groupingBy(
                        ContractPaymentApplicationModel::getContractMeasurementValuationId));
        data.forEach(item -> item.setContractPaymentApplications(
                applicationMap.getOrDefault(item.getId(), Collections.emptyList())));
    }

    private void replacePaymentApplications(List<ContractPaymentApplicationModel> applications,
                                            ContractMeasurementValuation valuation,
                                            LogUserInfoVo user) {
        contractPaymentApplicationMapper.delete(
                Wrappers.<ContractPaymentApplication>lambdaQuery()
                        .eq(ContractPaymentApplication::getContractMeasurementValuationId, valuation.getId())
        );
        if (ValidateUtil.isEmpty(applications)) {
            return;
        }
        Date now = new Date();
        List<ContractPaymentApplication> entities = applications.stream().map(application -> {
            ContractPaymentApplication entity = new ContractPaymentApplication();
            BeanUtils.copyProperties(application, entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setContractMeasurementValuationId(valuation.getId());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            entity.setCreateTime(now);
            entity.setState(valuation.getState());
            entity.setStateTime(valuation.getStateTime());
            return entity;
        }).collect(Collectors.toList());
        for (ContractPaymentApplication entity : entities) {
            if (contractPaymentApplicationMapper.insert(entity) != 1) {
                throw new JPMException(ResMsg.actError);
            }
        }
    }
}
