package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRelationListRequest;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRecordDto;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRequest;
import com.jpmsoft.common.model.response.Contract.ConEcFeeResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.ConPayEnum;
import com.jpmsoft.projectProducer.enums.EcfTypeEnum;
import com.jpmsoft.projectProducer.mapper.contract.ConEcFeeMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEcFee;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEcFeeRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.contract.PayChangeAssociation;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConEcFeeStandingBookVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConEcFeeDesignChangeRelationVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConEcFeeSiteVisaRelationVO;
import com.jpmsoft.projectProducer.service.contract.IConEcFeeRecordService;
import com.jpmsoft.projectProducer.service.contract.IConEcFeeService;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.contract.IPayChangeAssociationService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConEcFeeImpl extends ServiceImpl<ConEcFeeMapper, ConEcFee> implements IConEcFeeService {

    private static final String DESIGN_CHANGE_TYPE = "01";
    private static final String SITE_VISA_TYPE = "02";

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IConEcFeeRecordService conEcFeeRecordService;

    @Resource
    private IPayChangeAssociationService payChangeAssociationService;

    @Resource
    private IConPayService conPayService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "ecfId", codeParam = "ecfCode", method = "setEcfCode", codeRule = "ConEcFee", beanClass = ConEcFeeRequest.class)
    public BaseResponse createOrUpdateConEcFee(ConEcFeeRequest request) {
        request.setEcfType(EcfTypeEnum.EcFee.getkey());
        return createOrUpdateEntity(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConEcFeeOrRecord(ConEcFeeRequest request) {
        request.setEcfType(EcfTypeEnum.EcFeeChange.getkey());
        if (ValidateUtil.isEmpty(request.getEcfId()) && ValidateUtil.isNull(request.getEcfState())) {
            request.setEcfState(ApprovalStatusEnum.Not_Started.getIndex());
        }
        BaseResponse orUpdateEntity = createOrUpdateEntity(request);

        if (!orUpdateEntity.getSuccess()) {
            return orUpdateEntity;
        }
        conEcFeeRecordService.createOrUpdateConEcFeeRecord(request.getConMeasureRecords(), orUpdateEntity.getData().toString());
        return orUpdateEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "ecfId", codeParam = "ecfCode", method = "setEcfCode", codeRule = "ConEcFeeClear", beanClass = ConEcFeeRequest.class)
    public BaseResponse createOrUpdateConEcFeeClear(ConEcFeeRequest request) {
        request.setEcfType(EcfTypeEnum.EcFeeClear.getkey());
        return createOrUpdateEntity(request);
    }


    /**
     * 创建修改逻辑
     */
    private BaseResponse createOrUpdateEntity(ConEcFeeRequest request) {
        ConEcFee entity = new ConEcFee();
        if (ValidateUtil.isEmpty(request.getEcfCode()) && !EcfTypeEnum.EcFeeChange.getkey().equals(request.getEcfType()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<ConEcFee> wrapper = new QueryWrapper<>();

        if (!EcfTypeEnum.EcFeeChange.getkey().equals(request.getEcfType())) {
            wrapper.eq("Ecf_Code", request.getEcfCode());
            wrapper.eq("Ecf_Type", request.getEcfType());
            if (!ValidateUtil.isEmpty(entity.getEcfId()))
                wrapper.ne("Ecf_Id", request.getEcfId());
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(ResMsg.codeIsRepeat);
        }


        if (ValidateUtil.isEmpty(request.getEcfId())) {
            entity.setEcfId(GenerateUUID.getUUID());
            entity.setEcfCreateTime(new Date());
            entity.setEcfStateTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEcfId(), request.gettId(), entity.getEcfState() + "", entity.getEcfCreateUid(), entity.getEcfCreateUname()
                    , entity.getEcfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getEcfId());


        } else {
            ConEcFee model = baseMapper.selectById(request.getEcfId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEcfState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEcfCreateTime(model.getEcfCreateTime());
            entity.setEcfCreateUid(model.getEcfCreateUid());
            entity.setEcfCreateUname(model.getEcfCreateUname());
            entity.setEcfState(model.getEcfState());
            entity.setEcfStateTime(model.getEcfStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEcfId(), request.gettId(), entity.getEcfState() + "", entity.getEcfCreateUid(), entity.getEcfCreateUname()
                    , entity.getEcfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getEcfId());
        }
        return BaseResponse.ok(entity.getEcfId());
    }

    @Override
    public BaseResponse getConEcFeeList(ConEcFeeRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Ec_Fee", uId, "Ecf_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ecf_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEcFeeRequest> params = setFilterParams(request);
        params.put("authSql", authSql);
        IPage<ConEcFeeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcFeeResponse> datas = baseMapper.getConEcFeeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEcFeeRelationList(ConEcFeeRelationListRequest request, String uId) {
        if (request == null || ValidateUtil.isEmpty(request.getType())
                || ValidateUtil.isEmpty(request.getProjectId())) {
            return BaseResponse.fail(ResMsg.noMustParam);
        }

        if (DESIGN_CHANGE_TYPE.equals(request.getType())) {
            String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID",
                    OutUserAuthEnum.Organization.getIndex(), "State");
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            IPage<ConEcFeeDesignChangeRelationVO> page = new Page<>(request.getPage(), request.getSize());
            List<ConEcFeeDesignChangeRelationVO> data = baseMapper.getMultiContractConEcRelationList(
                    page, request.getType(), request.getProjectId(), request.getConId(),
                    ApprovalStatusEnum.Invalid.getIndex(), authSql);
            return BaseResponse.ok(data, (int) page.getTotal());
        }

        if (SITE_VISA_TYPE.equals(request.getType())) {
            String authSql = sysUserService.getAuthSql("Con_Svis", uId, "Svis_Create_Uid",
                    OutUserAuthEnum.Unit_Auth.getIndex(), "Svis_State");
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            IPage<ConEcFeeSiteVisaRelationVO> page = new Page<>(request.getPage(), request.getSize());
            List<ConEcFeeSiteVisaRelationVO> data = baseMapper.getConSvisRelationList(
                    page, request.getProjectId(), request.getConId(), request.getTeId(),
                    ApprovalStatusEnum.Invalid.getIndex(), authSql);
            return BaseResponse.ok(data, (int) page.getTotal());
        }

        return BaseResponse.fail("type参数仅支持01或02");
    }

    @Override
    public BaseResponse getConEcFeeListorRecord(ConEcFeeRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Ec_Fee", uId, "Ecf_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ecf_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEcFeeRequest> params = setFilterParams(request);
        params.put("authSql", authSql);
        IPage<ConEcFeeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcFeeResponse> datas = baseMapper.getConEcFeeListorRecord(page, params.getQuery());
        if (datas == null || datas.isEmpty()) {
            return BaseResponse.ok(datas, 0);
        }
        List<String> ecfIds = datas.stream().map(ConEcFeeResponse::getEcfId).collect(Collectors.toList());
        LambdaQueryWrapper<ConEcFeeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ConEcFeeRecord::getMeaId, ecfIds);
        List<ConEcFeeRecord> conEcFeeRecords = conEcFeeRecordService.list(queryWrapper);
        List<ConEcFeeRecordDto> con = conEcFeeRecords.stream().map(conEcFeeRecord -> {
            ConEcFeeRecordDto conEcFeeRecordDto = new ConEcFeeRecordDto();
            BeanUtils.copyProperties(conEcFeeRecord, conEcFeeRecordDto);
            return conEcFeeRecordDto;
        }).collect(Collectors.toList());
        datas.forEach(data -> {
            data.setConMeasureRecords(con.stream().filter(conEcFeeRecord -> conEcFeeRecord.getMeaId().equals(data.getEcfId())).
                    collect(Collectors.toList()));
        });
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    @Override
    public BaseResponse getConEcFeeClassInfo(ConEcFeeRequest request, String uId) {

        String authSql = sysUserService.getAuthSql("Con_Ec_Fee", uId, "Ecf_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ecf_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEcFeeRequest> params = setFilterParams(request);
        params.put("authSql", authSql);

        List<ClassInfoAnnotation> classInfoAnnotationList;
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(ConEcFeeRequest.class.getName());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
        List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, params.getQuery());
        classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        return BaseResponse.ok(classInfoAnnotationList);
    }


    @Override
    public BaseResponse getConEcFeeStandingBook(ConEcFeeRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Ec_Fee", uId, "Ecf_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ecf_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEcFeeRequest> params = setFilterParams(request);
        params.put("authSql", authSql);
        ConEcFeeStandingBookVo data = baseMapper.getConEcFeeStandingBook(params.getQuery());
        IPage<ConEcFeeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcFeeResponse> datas = baseMapper.getConEcFeeStandingBookPage(page, params.getQuery());
        if (ValidateUtil.isNull(data)) data = new ConEcFeeStandingBookVo();
        data.setData(datas);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEcFeeListNoAuth(ConEcFeeRequest request) {
        QueryMap<ConEcFeeRequest> params = setFilterParams(request);
        IPage<ConEcFeeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcFeeResponse> datas = baseMapper.getConEcFeeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEcFeeByEcfId(ConEcFeeRequest request, String uId) {
        ConEcFeeResponse data = baseMapper.getConEcFeeByEcfId(request.getEcfId());
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getConEcFeeorRecordByEcfId(ConEcFeeRequest request, String uId) {
        if (ValidateUtil.isEmpty(request.getEcfId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        ConEcFeeResponse data = baseMapper.getConEcFeeorRecordByEcfId(request.getEcfId());

        if (ValidateUtil.isNull(data))
            return BaseResponse.ok(data);
        LambdaQueryWrapper<ConEcFeeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConEcFeeRecord::getMeaId, request.getEcfId());
        List<ConEcFeeRecord> conEcFeeRecords = conEcFeeRecordService.list(queryWrapper);
        List<ConEcFeeRecordDto> con = conEcFeeRecords.stream().map(conEcFeeRecord -> {
            ConEcFeeRecordDto conEcFeeRecordDto = new ConEcFeeRecordDto();
            BeanUtils.copyProperties(conEcFeeRecord, conEcFeeRecordDto);
            return conEcFeeRecordDto;
        }).collect(Collectors.toList());

        data.setConMeasureRecords(con);
        return BaseResponse.ok(data);
    }


    @Override
    public BaseResponse getConEcFeeChangePayList(ConEcFeeRequest request, String uid) {
        String conId = request.getConId();
        String payType = request.getPayType();

        if (ValidateUtil.isAnObjEmpty(conId, payType)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        // 查询合同下所有审批通过的变更单
        List<ConEcFee> conEcFees = this.list(new LambdaQueryWrapper<ConEcFee>()
                .eq(ConEcFee::getConId, conId)
                .eq(ConEcFee::getEcfState, ApprovalStatusEnum.Already_Over.getIndex())
        );

        if (ValidateUtil.isEmpty(conEcFees)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        // 收集 ecfId（用于关联查询），已去 null
        List<String> ecfIds = conEcFees.stream()
                .map(ConEcFee::getEcfId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (ecfIds.isEmpty()) {
            return BaseResponse.ok(Collections.emptyList());
        }

        // 查询 Pay_Change_Association
        List<PayChangeAssociation> associations = payChangeAssociationService.list(
                new LambdaQueryWrapper<PayChangeAssociation>().in(PayChangeAssociation::getEcfId, ecfIds)
        );

        Set<String> paidEcfIds;
        if (payType.equals("02")) { //进度款排除已支付变更费用
            List<String> pcEcfIds = CollectionUtils.isEmpty(associations) ? new ArrayList<>() :
                    associations.stream().map(PayChangeAssociation::getEcfId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(pcEcfIds)) ecfIds.removeAll(pcEcfIds);
            paidEcfIds = new HashSet<>(ecfIds);
        } else {
            if (!ValidateUtil.isEmpty(associations)) {
                // 收集 payId
                List<String> payIds = associations.stream()
                        .map(PayChangeAssociation::getPayId)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList());

                if (!ValidateUtil.isEmpty(payIds)) {
                    List<ConPay> conPays = conPayService.list(
                            new LambdaQueryWrapper<ConPay>()
                                    .in(ConPay::getPayId, payIds)
                                    .eq(ConPay::getPayType, payType)
                    );

                    Set<String> validPaidPayIds = new HashSet<>();
                    if (!ValidateUtil.isEmpty(conPays)) {
                        for (ConPay p : conPays) {
                            // JD: 已审批通过才算已支付；JG: 排除作废的
                            if (ConPayEnum.JD.getKey().equals(payType) && Objects.equals(p.getPayState(), ApprovalStatusEnum.Already_Over.getIndex())) {
                                validPaidPayIds.add(p.getPayId());
                            } else if (ConPayEnum.JG.getKey().equals(payType) && !Objects.equals(p.getPayState(), ApprovalStatusEnum.Invalid.getIndex())) {
                                validPaidPayIds.add(p.getPayId());
                            }
                        }
                    }

                    // 找出已支付的 ecfId
                    paidEcfIds = associations.stream()
                            .filter(a -> validPaidPayIds.contains(a.getPayId()))
                            .map(PayChangeAssociation::getEcfId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
                } else {
                    paidEcfIds = new HashSet<>();
                }
            } else {
                paidEcfIds = new HashSet<>();
            }
        }

        // 筛选未支付的变更单，并排除 ecfId 为 null 的项 进度款（02）则查询未支付变更费用的数据
        List<ConEcFee> unpaidFees = payType.equals("02") ? conEcFees.stream()
                .filter(fee -> paidEcfIds.contains(fee.getEcfId()))
                .collect(Collectors.toList()) : conEcFees.stream()
                .filter(fee -> !paidEcfIds.contains(fee.getEcfId()))
                .collect(Collectors.toList());
        // 转为 ConEcFeeResponse 并附加子表
        List<ConEcFeeResponse> responses = attachRecordsToResponses(unpaidFees);
        return BaseResponse.ok(responses, responses.size());
    }

    private List<ConEcFeeResponse> attachRecordsToResponses(List<ConEcFee> fees) {
        if (ValidateUtil.isEmpty(fees)) {
            return Collections.emptyList();
        }

        // 收集非空 ecfId
        List<String> ecfIds = fees.stream()
                .map(ConEcFee::getEcfId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 若没有有效 ecfId，则直接返回只包含空子表的 responses
        List<ConEcFeeRecord> allRecords = Collections.emptyList();
        if (!ecfIds.isEmpty()) {
            allRecords = conEcFeeRecordService.list(
                    new LambdaQueryWrapper<ConEcFeeRecord>().in(ConEcFeeRecord::getMeaId, ecfIds)
            );
        }

        Map<String, List<ConEcFeeRecord>> recordMap = allRecords.stream()
                .collect(Collectors.groupingBy(ConEcFeeRecord::getMeaId));

        List<ConEcFeeResponse> responses = new ArrayList<>();
        for (ConEcFee fee : fees) {
            ConEcFeeResponse response = new ConEcFeeResponse();
            BeanUtils.copyProperties(fee, response);

            List<ConEcFeeRecordDto> records = recordMap.getOrDefault(fee.getEcfId(), Collections.emptyList())
                    .stream()
                    .map(record -> {
                        ConEcFeeRecordDto recordDto = new ConEcFeeRecordDto();
                        BeanUtils.copyProperties(record, recordDto);
                        return recordDto;
                    })
                    .collect(Collectors.toList());

            response.setConMeasureRecords(records);
            responses.add(response);
        }
        return responses;
    }


    @Override
    public BaseResponse getConEcFeeApplyMoneySum(String conId) {
        BigDecimal money = baseMapper.getConEcFeeApplyMoneySum(conId);
        if (money == null) money = BigDecimal.ZERO;
        return BaseResponse.ok(money);
    }


    @Override
    public BaseResponse getConEcFeeClearList(ConEcFeeRequest request, String uId) {
        //Con_Ec_Fee 鉴权链表定制（根据Con_Ec链表） 此处不传则用当前本表链表鉴权
        String authSql = sysUserService.getAuthSql(null, uId, "Ecf_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ecf_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEcFeeRequest> params = setFilterParams(request);
        params.put("authSql", authSql);
        IPage<ConEcFeeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcFeeResponse> datas = baseMapper.getConEcFeeClearList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEcFeeClearById(String id) {
        return BaseResponse.ok(baseMapper.getConEcFeeClearById(id));
    }

    @Override
    public BaseResponse getPreliminaryAmountSumByConId(String conId) {
        if (ValidateUtil.isEmpty(conId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        BigDecimal total = baseMapper.getPreliminaryAmountSumByConId(conId);
        if (total == null) {
            total = BigDecimal.ZERO;
        }
        return BaseResponse.ok(total);
    }

    private QueryMap<ConEcFeeRequest> setFilterParams(ConEcFeeRequest request) {
        QueryMap<ConEcFeeRequest> query = new QueryMap<>(request);
        query.put("ecfContractor").put("ecfSupervisor").put("ecId").put("conId").put("conPartyBName")
                .put("ecfState").put("conName").put("pName").put("pId").put("ecfContractorName")
                .put("ecfSupervisorName").put("ecfPm").put("ecfCode").put("ecfCode").put("ecfChangeType")
                .put("dcoName").put("dcoCode").put("dcoProjectName").put("dcoSubCode")
                .put(request.getEcfApplyMoneys(), "ecfApplyMoneyStart", "ecfApplyMoneyEnd")
                .put(request.getEcfDurations(), "ecfDurationStart", "ecfDurationEnd")
                .put(request.getEcfCreateTimes(), "cfCreateTimeStart", "ecfCreateTimeEnd")
                .put("pNameList").put("conNameList").put("ecChangeTypeList").put("createUnameList").put("stateList")
                .put("ecConstructionNameList");
        ;
        return query;
    }
}
