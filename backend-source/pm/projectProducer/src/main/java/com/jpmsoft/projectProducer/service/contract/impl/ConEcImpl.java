package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.response.Contract.ConEcResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.DockFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConEcMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConVisaFeeMapper;
import com.jpmsoft.projectProducer.mapper.other.SysFileMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEc;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConVisaFee;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataFileRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.ThirdUserRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.WorkFlowRequest;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConEcStandingBookVo;
import com.jpmsoft.projectProducer.service.contract.IConEcService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.LogicUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConEcImpl extends ServiceImpl<ConEcMapper, ConEc> implements IConEcService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private SysFileMapper sysFileMapper;

    @Resource
    private DockFeign dockFeign;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ConContractMapper conContractMapper;

    @Resource
    private ConVisaFeeMapper conVisaFeeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "ecId", codeParam = "ecCode", method = "setEcCode", codeRule = "ConEc", beanClass = ConEcRequest.class)
    public BaseResponse createOrUpdateConEc(ConEcRequest request) {
        ConEc entity = new ConEc();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(request.getEcCode())) {
            QueryWrapper<ConEc> wrapper = new QueryWrapper<>();
            wrapper.eq("Ec_Code", request.getEcCode());
            wrapper.ne("Ec_State", ApprovalStatusEnum.Invalid.getIndex())
                    .ne("Ec_State", ApprovalStatusEnum.Not_Started.getIndex())
                    .ne("Ec_State", ApprovalStatusEnum.Returned_Initiator.getIndex());
            if (!ValidateUtil.isEmpty(entity.getEcId()))
                wrapper.ne("Ec_Id", request.getEcId());
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(ResMsg.codeIsRepeat);
        }
        if (ValidateUtil.isEmpty(request.getEcId())) {
/*            String seqCode = "EcCode";
            String code = generateCodeByTCode(seqCode,request);
            if (ValidateUtil.isEmpty(code))
                return new BaseResponse("编号生成失败");
            refershAutoNo(seqCode,request);
            entity.setEcCode(code);*/
            entity.setEcId(GenerateUUID.getUUID());
            entity.setEcCreateTime(new Date());
            entity.setEcStateTime(null);
            entity.setEcState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEcId(), request.gettId(), entity.getEcState() + "", entity.getEcCreateUid(), entity.getEcCreateUname()
                    , entity.getEcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getEcDoc(), request.getEcDrawing(), request.getEcCostBook()
                    , request.getEcReqFile(), request.getEcApprovalFile(), request.getEcRelevantFile(), request.getEcApplyFile(), request.getEcMaterialFile(), request.getEcOpinionFile()
                    , request.getEcFilingsFile(), request.getEcEnisFile(), request.getEcJlFile(), request.getEcSjFile()), entity.getEcId());
        } else {
            ConEc model = baseMapper.selectById(request.getEcId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEcState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEcCreateCId(model.getEcCreateCId());
            entity.setEcCreateTime(model.getEcCreateTime());
            entity.setEcCreateUid(model.getEcCreateUid());
            entity.setEcCreateUname(model.getEcCreateUname());
            entity.setEcState(model.getEcState());
            entity.setEcStateTime(model.getEcStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEcId(), request.gettId(), entity.getEcState() + "", entity.getEcCreateUid(), entity.getEcCreateUname()
                    , entity.getEcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getEcDoc(), request.getEcDrawing(), request.getEcCostBook()
                            , request.getEcReqFile(), request.getEcApprovalFile(), request.getEcRelevantFile(), request.getEcApplyFile(), request.getEcMaterialFile(), request.getEcOpinionFile(), request.getEcFilingsFile(), request.getEcEnisFile(), request.getEcJlFile(), request.getEcSjFile())
                    , Arrays.asList(request.getFileIds(), request.getEcDocIds(), request.getEcDrawingIds(), request.getEcCostBookIds()
                            , request.getEcReqFileIds(), request.getEcApprovalFileIds(), request.getEcRelevantFileIds(), request.getEcApplyFileIds(), request.getEcMaterialFileIds(), request.getEcOpinionFileIds(), request.getEcFilingsFileIds(), request.getEcEnisFileIds(), request.getEcJlFileIds(), request.getEcSjFileIds())
                    , entity.getEcId());
        }
        return BaseResponse.ok(entity.getEcId());
    }
    public String generateCodeByTCode(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/generateCodeByTCode?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }
    public String refershAutoNo(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/refershAutoNo?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }
    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        log.info("服务调用时:{}", JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
    @Override
    public BaseResponse updateConEc(ConEcRequest request) {
        ConEc entity = baseMapper.selectById(request.getEcId());
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.nonModifyState(entity.getEcState()))
            return BaseResponse.fail(ResMsg.stateNotModify);
        if (ValidateUtil.isNotNull(request.getEcDuration()))
            entity.setEcDuration(request.getEcDuration());
        if (ValidateUtil.isNotNull(request.getEcApplyMoney()))
            entity.setEcApplyMoney(request.getEcApplyMoney());
        if (ValidateUtil.isNotNull(request.getEcTotalMoneyC()))
            entity.setEcTotalMoneyC(request.getEcTotalMoneyC());
        if (ValidateUtil.isNotNull(request.getEcTotalMoneyCRate()))
            entity.setEcTotalMoneyCRate(request.getEcTotalMoneyCRate());
        if (ValidateUtil.isNotNull(request.getEcFinalMoneyAfter()))
            entity.setEcFinalMoneyAfter(request.getEcFinalMoneyAfter());
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteConEcByEcId(String ecId) {
        return BaseResponse.ok();
//        ConEcPojo model = baseMapper.selectById(ecId);
//        if (model == null)
//            return  BaseResponse.fail(ResMsg.infoNotExist);
//        baseMapper.deleteById(ecId);
//        sysFileService.deleteFileById(ecId);
//        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getConEcByEcId(String ecId) {
        ConEcResponse data = baseMapper.getConEcByEcId(ecId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getConEcByConId(String conId) {
        List<ConEcResponse> conEcByConId = baseMapper.getConEcByConId(conId);
        // 计算ecTotalMoney的总和
        BigDecimal ecTotalMoneySum = conEcByConId.stream()
                .map(ConEcResponse::getEcApplyMoney)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 为每个ConEcResponse对象设置ecTotalMoneySum字段
        conEcByConId.forEach(conEc -> {
            conEc.setEcTotalMoneySum(ecTotalMoneySum);
        });

        return BaseResponse.ok(conEcByConId);
    }

    @Override
    public BaseResponse getConEcCountByConId(String conId) {

        Double total = baseMapper.getConEcCountPlusByConId(conId) == null ? 0.0 : baseMapper.getConEcCountPlusByConId(conId);
        Double total2 = baseMapper.getConEcCountMinusByConId(conId) == null ? 0.0 : baseMapper.getConEcCountMinusByConId(conId);

        DecimalFormat df = new DecimalFormat("0.00");

        Map<String,String> Map1 = new HashMap<>();
        Map1.put("PlusSum", df.format(total));
        Map1.put("MinusSum", df.format(total2));

        return BaseResponse.ok(Map1);
    }

    @Override
    public BaseResponse getConEcList(ConEcRequest request, String uId) {
        Map<String, Object> params = getAuthParams(request, uId);
        IPage<ConEcResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcResponse> datas = baseMapper.getConEcList(page, params);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEcListClasInfo(ConEcRequest request, String uId) {
        Map<String, Object> params = getAuthParams(request, uId);
        List<ClassInfoAnnotation> classInfoAnnotationList;
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(ConEcRequest.class.getName());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
        List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, params);
        classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        return BaseResponse.ok(classInfoAnnotationList);
    }


    @Override
    public BaseResponse checkClzgAndZlje(ConEcRequest request) {
        if (ValidateUtil.isEmpty(request.getConId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        ConContract contract = conContractMapper.selectById(request.getConId());
        if (ValidateUtil.isNull(contract)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        List<ConEc> conEcList = baseMapper.selectList(
                new LambdaQueryWrapper<ConEc>()
                        .eq(ConEc::getConId, request.getConId())
                        .ne(ConEc::getEcState, ApprovalStatusEnum.Invalid.getIndex())
                        .ne(ConEc::getEcState, ApprovalStatusEnum.Not_Started.getIndex())
        );

        List<ConVisaFee> conVisaFeeList = conVisaFeeMapper.selectList(
                new LambdaQueryWrapper<ConVisaFee>()
                        .eq(ConVisaFee::getConId, request.getConId())
                        .ne(ConVisaFee::getVfState, ApprovalStatusEnum.Invalid.getIndex())
                        .ne(ConVisaFee::getVfState, ApprovalStatusEnum.Not_Started.getIndex())
        );

        BigDecimal currentEcApply = Optional.ofNullable(request.getEcApplyMoney()).orElse(BigDecimal.ZERO);
        BigDecimal currentVfApply = Optional.ofNullable(request.getVfApplyMoney()).orElse(BigDecimal.ZERO);
        String currentEcType = request.getEcDataSeparateType();
        String currentVfType = request.getVfDataSeparateType();

        BigDecimal zanLieTotal = BigDecimal.ZERO;
        BigDecimal zanGuTotal = BigDecimal.ZERO;

        for (ConEc c : conEcList) {
            BigDecimal apply = Optional.ofNullable(c.getEcApplyMoney()).orElse(BigDecimal.ZERO);
            String type = c.getEcDataSeparateType();
            if (type == null || "01".equals(type)) {
                zanLieTotal = zanLieTotal.add(apply);
            } else if ("02".equals(type)) {
                zanGuTotal = zanGuTotal.add(apply);
            }
        }

        for (ConVisaFee v : conVisaFeeList) {
            BigDecimal apply = Optional.ofNullable(v.getVfApplyMoney()).orElse(BigDecimal.ZERO);
            String type = v.getVfDataSeparateType();
            if (type == null || "01".equals(type)) {
                zanLieTotal = zanLieTotal.add(apply);
            } else if ("02".equals(type)) {
                zanGuTotal = zanGuTotal.add(apply);
            }
        }

        if (currentEcType == null || "01".equals(currentEcType)) {
            zanLieTotal = zanLieTotal.add(currentEcApply);
        } else if ("02".equals(currentEcType)) {
            zanGuTotal = zanGuTotal.add(currentEcApply);
        }

        if (currentVfType == null || "01".equals(currentVfType)) {
            zanLieTotal = zanLieTotal.add(currentVfApply);
        } else if ("02".equals(currentVfType)) {
            zanGuTotal = zanGuTotal.add(currentVfApply);
        }

        BigDecimal contractZanLie = Optional.ofNullable(contract.getConMoneyTentative()).orElse(BigDecimal.ZERO);
        BigDecimal contractZanGu = Optional.ofNullable(contract.getConProvisionalPe()).orElse(BigDecimal.ZERO);

        boolean exceedZanLieJin = zanLieTotal.compareTo(contractZanLie) > 0;
        boolean exceedZanGuJia = zanGuTotal.compareTo(contractZanGu) > 0;

        String result;
        if (exceedZanLieJin && exceedZanGuJia) {
            result = "04";
        } else if (exceedZanLieJin) {
            result = "02";
        } else if (exceedZanGuJia) {
            result = "03";
        } else {
            result = "01";
        }

        return BaseResponse.ok(result);
    }

    @Override
    public BaseResponse getConEcListByPermission(ConEcRequest request, String uId) {
        SysUser user=sysUserService.getById(uId);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", " where 1=1 ");
        setFilterParams(params, request);
        params.put("authCpy",user.getCId());//合同权限单位授权
        params.put("authUser",uId);//合同权限用户授权
        IPage<ConEcResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcResponse> datas = baseMapper.getConEcList(page, params);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private Map<String, Object> getAuthParams(ConEcRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Ec", uId, "Ec_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ec_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", authSql);
        setFilterParams(params, request);
        return params;
    }

    @Override
    public BaseResponse getConEcStandingBook(ConEcRequest request, String uId) {
        Map<String, Object> params = getAuthParams(request, uId);
        ConEcStandingBookVo data = baseMapper.getConEcStandingBook(params);
        IPage<ConEcResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcResponse> datas = baseMapper.getConEcList(page, params);
        if (ValidateUtil.isNull(data)) data = new ConEcStandingBookVo();
        data.setData(datas);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEcListNoAuth(ConEcRequest request) {
        Map<String, Object> params = new HashMap<>();
        setFilterParams(params, request);
        IPage<ConEcResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcResponse> datas = baseMapper.getConEcList(page, params);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public Boolean contactListAssociateData(String clId) {
        QueryWrapper<ConEc> wrapper = new QueryWrapper<>();
        wrapper.eq("Cl_Id", clId);
        return baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public BaseResponse getConEcStatisticsOverview(String teId) {
        Map<String, Object> datas = baseMapper.getConEcStatisticsOverview(teId);
        if (!datas.containsKey("changeAmtCount"))
            datas.put("changeAmtCount", 0);
        if (!datas.containsKey("changeAmtNew"))
            datas.put("changeAmtNew", 0);
        if (!datas.containsKey("changeCount"))
            datas.put("changeCount", 0);
        if (!datas.containsKey("changeNewCount"))
            datas.put("changeNewCount", 0);
        if (!datas.containsKey("closeCount"))
            datas.put("closeCount", 0);
        if (!datas.containsKey("closeNewCount"))
            datas.put("closeNewCount", 0);
        datas.put("processCount", Integer.parseInt(datas.get("changeCount").toString()) - Integer.parseInt(datas.get("closeCount").toString()));
        BigDecimal closeCount = new BigDecimal(datas.get("closeCount").toString());
        BigDecimal changeCount = new BigDecimal(datas.get("changeCount").toString());
        if (closeCount.compareTo(BigDecimal.ZERO) != 0 && changeCount.compareTo(BigDecimal.ZERO) != 0)
            datas.put("processRate", closeCount.divide(changeCount, 3, RoundingMode.UP));
        else
            datas.put("processRate", 0);
        return BaseResponse.ok(datas);
    }

    /**
     * 获取变更总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getConEcStatisticsOverviewList(ConEcRequest request, String teId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<ConEcResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcResponse> datas = baseMapper.getConEcStatisticsOverviewList(page, request, teId);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getConEcStatisticsByGrade(String teId) {
        return BaseResponse.ok(baseMapper.getConEcStatisticsGroupByGrade(teId));
    }

    @Override
    public BaseResponse getConEcStatisticsChangeSituation(String teId) {
        return BaseResponse.ok(baseMapper.getConEcStatisticsChangeSituation(teId));
    }

    @Override
    public BaseResponse getConEcStatisticsByRanking(Integer size, String teId) {
        return BaseResponse.ok(baseMapper.getConEcStatisticsByRanking(size, teId));
    }

    @Override
    public BaseResponse approve2CancelPreEc(String ecId) {
        ConEc ce = baseMapper.selectById(ecId);
        if (ce == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(ce.getEcState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        if (ValidateUtil.isNotNull(ce.getEcNewEc()) && !ce.getEcNewEc()
                && ValidateUtil.isNotEmpty(ce.getPreEcId())) {
            baseMapper.updateState(ApprovalStatusEnum.Invalid.getIndex(), ce.getPreEcId());
            sysFileService.onlyUpdateState(ApprovalStatusEnum.Invalid.getIndex(), ce.getPreEcId());
        }
        return BaseResponse.ok();
    }

    @Override
    public void processSyn(String uuid) {
        SysFile sysFile = sysFileMapper.selectById(uuid);
        if (sysFile == null) {
            throw new JPMException("无效数据");
        }
        ConEc conEc = getById(uuid);
        if (conEc == null) {
            throw new JPMException("无效数据");
        }
        String fileRelationId = sysFile.getFileRelationId();
        String fileflowstatus = sysFile.getFileflowstatus();
        if("7".equals(fileflowstatus)){
            //查询云上建工用户信息
            CommonResponse<ThirdUserRequest> thirdUserCommonResponse = dockFeign.getThirdUser(conEc.getEcCreateUid());
            if (thirdUserCommonResponse.isSuccess() && thirdUserCommonResponse.getData() != null) {
                ThirdUserRequest thirdUserRequest = thirdUserCommonResponse.getData();
                conEc.setThirdUserId(thirdUserRequest.getUserId());
                WorkFlowRequest workFlowRequest = new WorkFlowRequest();
                workFlowRequest.setRequestName(conEc.getEcReason());
                workFlowRequest.setWorkflowId(sysConfigService.getConfigByCode("YSJG_TECHNICAL_SEAL_WFID"));
                workFlowRequest.setBizId(conEc.getEcId());
                workFlowRequest.setBizType("ConEc");
                workFlowRequest.setThirdUserId(thirdUserRequest.getUserId());

                if (org.apache.commons.lang3.StringUtils.isNotBlank(conEc.getThirdRequestId())) {
                    workFlowRequest.setRequestId(conEc.getThirdRequestId());
                }
                List<MainDataRequest> mainData = new ArrayList<>();
                MainDataRequest sqr = new MainDataRequest();
                sqr.setFieldName("sqr");
                sqr.setFieldValue(thirdUserRequest.getUserId());
                mainData.add(sqr);

                MainDataRequest sqbm = new MainDataRequest();
                sqbm.setFieldName("sqbm");
                sqbm.setFieldValue(thirdUserRequest.getDepartmentId());
                mainData.add(sqbm);

                MainDataRequest sqdw = new MainDataRequest();
                sqdw.setFieldName("sqdw");
                sqdw.setFieldValue(thirdUserRequest.getSubCompanyId());
                mainData.add(sqdw);

                MainDataRequest sqrq = new MainDataRequest();
                sqrq.setFieldName("sqrq");
                sqrq.setFieldValue(DateFormatUtil.dateToString(new Date(), DateFormatUtil.FORMAT_PATTERN_OF_DAY));
                mainData.add(sqrq);

                // 印章类别
                mainData.add(MainDataRequest.builder()
                        .fieldName("yzlb")
                        .fieldValue(conEc.getSealCategory())
                        .build());
                // 是否盖章
                mainData.add(MainDataRequest.builder()
                        .fieldName("sfgz")
                        .fieldValue("0")
                        .build());

                // 专业审核
                mainData.add(MainDataRequest.builder()
                        .fieldName("zysh")
                        .fieldValue(conEc.getProfessionalReview())
                        .build());

                // 项目类别
                mainData.add(MainDataRequest.builder()
                        .fieldName("xmlb")
                        .fieldValue(conEc.getProjectCategory())
                        .build());

                // 事由
                mainData.add(MainDataRequest.builder()
                        .fieldName("sy")
                        .fieldValue(conEc.getReason())
                        .build());

                // 公共附件
                if (org.apache.commons.lang3.StringUtils.isNotBlank(fileRelationId)) {
                    String rootDirectory = sysConfigService.getConfigByCode("rootDirectory");
                    String archiveDirectory = sysConfigService.getConfigByCode("archiveDirectory");
                    LambdaQueryWrapper<SysFileannex> fileQueryWrapper = new LambdaQueryWrapper<>();
                    fileQueryWrapper.eq(SysFileannex::getFileRelationId, fileRelationId);
                    fileQueryWrapper.eq(SysFileannex::getUploadStatus, 1);
                    List<SysFileannex> fileList = sysFileAnnexService.list(fileQueryWrapper);
                    if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(fileList)) {
                        List<MainDataFileRequest> fileMainDatas = new ArrayList<>();
                        for (SysFileannex sysFileannex : fileList) {
                            String fileDir;
                            if (sysFileannex.getFileLocation() == 1) {
                                fileDir = archiveDirectory;
                            } else {
                                fileDir = rootDirectory;
                            }
                            MainDataFileRequest fileData = new MainDataFileRequest();
                            fileData.setFileName(sysFileannex.getFileName());
                            fileData.setFilePath("base64:" + Base64.encode(new File(fileDir + sysFileannex.getFilePath())));
                            fileMainDatas.add(fileData);
                        }

                        MainDataRequest fj = new MainDataRequest();
                        fj.setFieldName("fj");
                        fj.setFieldValue(fileMainDatas);
                        mainData.add(fj);
                    }

                }

                workFlowRequest.setMainData(mainData);

                CommonResponse<String> workFLowCreateResponse;
                if (StrUtil.isNotBlank(conEc.getThirdRequestId())){
                    //驳回后重新提交流程
                    workFLowCreateResponse = dockFeign.resubmitFLow(workFlowRequest);
                }else{
                    //创建流程
                    workFLowCreateResponse = dockFeign.createWorkFLow(workFlowRequest);
                }
                if (workFLowCreateResponse.isSuccess() && StrUtil.isNotBlank(workFLowCreateResponse.getData())) {
                    conEc.setThirdRequestId(workFLowCreateResponse.getData());
                    //默认审批中
                    conEc.setThirdStatus("1");
                }
                this.updateById(conEc);

            }
        }


    }



    @Override
    public BaseResponse changeTtems(String conId) {
        if(ValidateUtil.isEmpty(conId))return BaseResponse.fail("缺少请求参数");
        LambdaQueryWrapper<ConEc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConEc::getEcState,7);
        queryWrapper.eq(ConEc::getConId,conId);
        List<ConEc> list = baseMapper.selectList(queryWrapper);
        Map<String,Object> data = new HashMap<>();
        if(ValidateUtil.isNotEmpty(list)){
            //增加金额
            BigDecimal increaseAmt = list.stream().filter(e -> ValidateUtil.isNotNull(e.getEcApplyMoneyType()) && e.getEcApplyMoneyType().equals(1)).map(ConEc::getEcApplyMoney).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            //减少金额
            BigDecimal reduceAmt = list.stream().filter(e -> ValidateUtil.isNotNull(e.getEcApplyMoneyType()) && e.getEcApplyMoneyType().equals(0)).map(ConEc::getEcApplyMoney).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal subtrAmt =  MathUtil.decSubtract(increaseAmt,reduceAmt);
            data.put("increaseAmt",increaseAmt);
            data.put("reduceAmt",reduceAmt);
            data.put("subtrAmt",subtrAmt);
            Integer increaseDay = list.stream().filter(e -> ValidateUtil.isNotNull(e.getEcDurationType()) && e.getEcDurationType().equals(1)).map(ConEc::getEcDuration).filter(Objects::nonNull).reduce(0,Integer::sum);
            Integer reduceDay = list.stream().filter(e -> ValidateUtil.isNotNull(e.getEcDurationType()) && e.getEcDurationType().equals(0)).map(ConEc::getEcDuration).filter(Objects::nonNull).reduce(0,Integer::sum);
            Integer subtrDay =  Integer.parseInt(String.valueOf(increaseDay))-Integer.parseInt(String.valueOf(reduceDay));
            data.put("increaseDay",increaseDay);
            data.put("reduceDay",reduceDay);
            data.put("subtrDay",subtrDay);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public List<ConEc> selectApprovedByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        QueryWrapper<ConEc> wrapper=new QueryWrapper<>();
        wrapper.eq("Ec_State",ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.in("Ec_Id",ids);
        wrapper.orderByAsc("Cc_Create_Time");
        return baseMapper.selectList(wrapper);
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
        params.put("conId",ValidateUtil.isEmpty(request.getConId()) ? null : request.getConId());
        params.put("ecDataSeparate", ValidateUtil.isEmpty(request.getEcDataSeparate()) ? null : request.getEcDataSeparate());
        params.put("pNameList", ValidateUtil.isEmpty(request.getpNameList()) ? null : request.getpNameList());
        params.put("conNameList", ValidateUtil.isEmpty(request.getConNameList()) ? null : request.getConNameList());
        params.put("ecChangeTypeList", ValidateUtil.isEmpty(request.getEcChangeTypeList()) ? null : request.getEcChangeTypeList());
        params.put("createUnameList", ValidateUtil.isEmpty(request.getEcCreateUnameList()) ? null : request.getEcCreateUnameList());
        params.put("ecStateList", ValidateUtil.isEmpty(request.getEcStateList()) ? null : request.getEcStateList());
        params.put("ecConstructionNameList", ValidateUtil.isEmpty(request.getEcConstructionNameList()) ? null : request.getEcConstructionNameList());
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


    @Override
    public BaseResponse saveBgzl(ConEcRequest request) {
        if(ValidateUtil.isAnObjEmpty(request.getEcId(),request.getEcBgzlId(),request.getFileIds()))return BaseResponse.fail("缺少必填参数");
        ConEc conEc = new ConEc();
        BeanUtils.copyProperties(request,conEc);
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getEcBgzlId()),Arrays.asList(request.getFileIds()),request.getEcId());
        conEc.setEcIsBgzl("1");
        baseMapper.updateById(conEc);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse invalidConEcByEcId(String ecId) {
        log.info("invalidConEcByEcId");
        SysFile sysFile = sysFileMapper.selectById(ecId);
        ConEc model = baseMapper.selectById(ecId);
        log.info("model:{}",JacksonUtils.toJSONString(model));
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (sysFile != null){
            sysFile.setFileflowstatus(ApprovalStatusEnum.Invalid.getIndex().toString());
            sysFileMapper.updateById(sysFile);
        }
        model.setEcState(ApprovalStatusEnum.Invalid.getIndex());
        baseMapper.updateById(model);
        return BaseResponse.ok(model);
    }

    @Override
    public BaseResponse isMajorChangeTtems(ConEcRequest request) {

        if (ValidateUtil.isEmpty(request.getpId()) || ValidateUtil.isEmpty(request.getConId())) {
            return BaseResponse.ok(ResMsg.notReqParam);
        }

        List<ConEc> conEcList = baseMapper.selectList(new LambdaQueryWrapper<ConEc>()
                .eq(ConEc::getPId, request.getpId())
                .ne(ConEc::getEcState, ApprovalStatusEnum.Invalid.getIndex())
        );

        Map<String, BigDecimal> ecApplyMoneyMap = conEcList.stream()
                .filter(e -> e.getConId() != null && e.getEcApplyMoney() != null)
                .collect(Collectors.groupingBy(
                        ConEc::getConId,
                        Collectors.reducing(BigDecimal.ZERO, ConEc::getEcApplyMoney, BigDecimal::add)
                ));

        BigDecimal ecApplyMoneySum = conEcList.stream()
                .map(ConEc::getEcApplyMoney)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (ValidateUtil.isNotNull(request.getEcApplyMoney())) {
            ecApplyMoneySum = ecApplyMoneySum.add(request.getEcApplyMoney());

            BigDecimal conIdTotal = ecApplyMoneyMap.getOrDefault(request.getConId(), BigDecimal.ZERO);
            conIdTotal = conIdTotal.add(request.getEcApplyMoney());
            ecApplyMoneyMap.put(request.getConId(), conIdTotal);
        }

        boolean isOverFiveMillion = ecApplyMoneySum.compareTo(new BigDecimal("500000")) >= 0;

        boolean isMajorChange = false;
        try {
            ConContract conContract = conContractMapper.selectById(request.getConId());
            if (conContract != null && conContract.getConMoneyTotal() != null) {
                BigDecimal contractTotalMoney = conContract.getConMoneyTotal();
                BigDecimal conIdContractApplyMoney = ecApplyMoneyMap.getOrDefault(request.getConId(), BigDecimal.ZERO);

                if (contractTotalMoney.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal ratio = conIdContractApplyMoney.divide(contractTotalMoney, 4, RoundingMode.HALF_UP);
                    isMajorChange = ratio.compareTo(new BigDecimal("0.1")) >= 0;
                }
            }
        } catch (Exception e) {
            log.error("合同变更占比计算异常", e);
        }

        boolean isImportant = isOverFiveMillion || isMajorChange;

        return BaseResponse.ok(isImportant);
    }

    /**
     * 获取合同累计变更金额统计
     *
     * @param conId
     * @return
     */
    @Override
    public BaseResponse getMoneyStatistics(String conId, String itemCategory) {
        if (ValidateUtil.isEmpty(conId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<ConEc> conEcs = this.list(Wrappers.<ConEc>lambdaQuery()
                .eq(ConEc::getItemCategory, itemCategory)
                .eq(ConEc::getConId, conId)
                .eq(ConEc::getEcState, ApprovalStatusEnum.Already_Over.getIndex())
                .select(ConEc::getEcFinalMoney)
        );
        BigDecimal total = conEcs.stream().map(ConEc::getEcFinalMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        return BaseResponse.ok(total);
    }
}