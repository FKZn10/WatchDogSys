package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.SecInfo.*;
import com.jpmsoft.common.model.request.SignAndVerifyDTO;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownChangeDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectRemarkDTO;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecModularsGroupByProjectModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSourcesFundsModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.InvestmentControl.InvestmentBreakdownSecInfoModel;
import com.jpmsoft.common.model.response.SecInfo.*;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentBudgetBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownChangeDetailsMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.other.TaskProcessinMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBiddingPlanMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBiddingProjectMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecBiddingPlanSignDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoPrecheckConfigDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdown;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdown;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.*;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecBiddingPlanStatisticsVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownService;
import com.jpmsoft.projectProducer.service.investment.IInvestmentEstimateBreakdownService;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecBiddingPlanImpl extends ServiceImpl<SecBiddingPlanMapper, SecBiddingPlan> implements ISecBiddingPlanService {
    private static final BigDecimal TEN_THOUSAND = new BigDecimal(10000);

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecBiddingProjectService secBiddingProjectService;
    @Resource
    private ISecInfoService secInfoService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISecSourcesFundsService secSourcesFundsService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISecInfoBudgetDeclareDtlService secInfoBudgetDeclareDtlService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private IInvestmentBudgetBreakdownService investmentBudgetBreakdownService;
    @Resource
    private SecBiddingProjectMapper secBiddingProjectMapper;
    @Resource
    private InvestmentEstimateBreakdownChangeDetailsMapper investmentEstimateBreakdownChangeDetailsMapper;
    @Resource
    private IInvestmentEstimateBreakdownService investmentEstimateBreakdownService;
    @Resource
    private InvestmentEstimateBreakdownDetailsMapper investmentEstimateBreakdownDetailsMapper;
    @Resource
    private InvestmentBudgetBreakdownDetailsMapper investmentBudgetBreakdownDetailsMapper;
    @Resource
    private ISecProjectService secProjectService;
    @Resource
    private ISecInfoSubjectService secInfoSubjectService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISecInfoSubjectRemarkService secInfoSubjectRemarkService;
    @Resource
    private ISecBiddingPlanChangeService secBiddingPlanChangeService;
    @Resource
    private ISecInfoChangeService secInfoChangeService;
    @Resource
    private ISecInfoSubjectChangeService secInfoSubjectChangeService;
    @Resource
    private ISecInfoSubjectExtraService secInfoSubjectExtraService;
    @Resource
    private ISecInfoSubjectExtraChangeService secInfoSubjectExtraChangeService;
    @Resource
    private TaskProcessinMapper taskProcessinMapper;

    @Override
    public BaseResponse refreshSecBiddingPlanSign() {
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        List<SecBiddingPlan> data=this.list();
        if (!CollectionUtils.isEmpty(data))
        {
            data.forEach(d->{
                d.setBpSign(sign(ied, ied.getSecSign(), d.signContent()));
            });
            updateBatchById(data);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecBiddingPlan(SecBiddingPlanRequest request) {
        SecBiddingPlan sec = new SecBiddingPlan();
        BeanUtils.copyProperties(request, sec);
        String secHaveConductSB = sysConfigService.getConfigByCode("secHaveConductSB");
        if(StrUtil.isBlank(secHaveConductSB) || (StrUtil.isNotBlank(secHaveConductSB) && !"0".equals(secHaveConductSB))){
            if (secBiddingProjectService.haveSBPCount(request.getpIds(), request.getBpId()))
                return BaseResponse.fail(ResMsg.recordUnFinish);
        }
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        if (ValidateUtil.isEmpty(request.getBpId())) {
            sec.setBpCreateTime(new Date());
            sec.setBpId(GenerateUUID.getUUID());
            sec.setState(ApprovalStatusEnum.Not_Started.getIndex());
            //数据完整性签名
            sec.setBpSign(sign(ied, ied.getSecSign(), sec.signContent()));
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getBpId(), request.gettId(), sec.getState() + "", sec.getBpCreateUid(), sec.getBpCreateUname()
                    , sec.getBpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBpApprovalFile()), sec.getBpId());
        } else {
            SecBiddingPlan entity = baseMapper.selectById(request.getBpId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setBpCreateTime(entity.getBpCreateTime());
            sec.setBpCreateUid(entity.getBpCreateUid());
            sec.setBpCreateUname(entity.getBpCreateUname());
            //数据完整性签名
            sec.setBpSign(sign(ied, ied.getSecSign(), sec.signContent(entity)));
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getBpId(), request.gettId(), sec.getState() + "", sec.getBpCreateUid(), sec.getBpCreateUname()
                    , sec.getBpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBpApprovalFile()),
                    Arrays.asList(request.getFileIds(), request.getBpApprovalFileIds()), sec.getBpId());
        }
        secBiddingProjectService.createOrUpdateSecBiddingProject(request.getpIds(), request.getSecBiddingProjectList(), sec.getBpId(), sec.getBpCreateUid());
        BaseResponse secInfoRes = secInfoService.createOrUpdateSecInfo(request.getSecInfos(), request.getpIds(), request.getSecProjectList(), sec);
        if (secInfoRes.getSuccess() == false)
            throw new JPMException("生成标段信息异常!");

        secInfoSubjectRemarkService.createOrUpdateSecInfoSubjectRemark(request.getRemarkList(), sec.getBpId());
        return BaseResponse.ok(sec.getBpId());
    }

    @Override
    public BaseResponse getSecBiddingPlanList(SecBiddingPlanRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Sbp_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        return getSecBiddingPlanList(request, authSql, "child");
    }

    @Override
    public BaseResponse getSecBiddingPlanListCjdz(SecBiddingPlanRequest request, LogUserInfoVo user) {
        String conf = sysConfigService.getConfigByCode("secBiddingPlanListCreateorAndApprover");
        String uId = user.getId();
        if (ValidateUtil.isNotEmpty(conf) && "1".equals(conf)) {
            String authSql = sysUserService.getAuthSql("Sec_Bidding_Plan", uId, "Bp_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(" where (mn.").append("bp_Create_Uid").append("='").append(uId).append("' or ");
            sb.append(" (select count(*) from Sys_Task_Processin st1p where st1p.tp_entityid=mn.").append("Bp_Id")
                    .append(" and st1p.tp_user='").append(uId).append("')>0");
            sb.append(" or ").append(" (select count(*) from SYS_CarbonCopy scc where scc.CC_FileID=mn.Bp_Id ")
                    .append(" and scc.CC_User='").append(uId).append("')>0");
            sb.append(")");
            authSql = authSql.replace("where 1=1", sb.toString())
                    .replace("Pre_Project fpro on fpro.P_Id=mn.P_ID", " Sec_Bidding_Project sbp on sbp.Bp_Id=mn.Bp_Id left join Pre_Project fpro on fpro.P_Id=sbp.P_ID");
            return getSecBiddingPlanList(request, authSql, "main");
        } else if (Objects.equals(request.getIsNew(), "1")) {
            // 创建人、流程处理人、抄送人、协同人 外加创建人同单位的
            String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
            if (ValidateUtil.isEmpty(njcj)) {
                njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
            }
            String authSql = sysUserService.getAuthSql("Sec_Bidding_Plan", uId, "Bp_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "bp_id");
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            if (Objects.equals(njcj, user.getCId())) {
                authSql = appendOrAuthSql(authSql, "mn.State not in (0, 3)");
            }
            authSql = authSql.replaceFirst("(?i)\\bwhere\\b", " left join Sec_Bidding_Project sbp on sbp.Bp_Id=mn.Bp_Id left join Pre_Project fpro on fpro.P_Id=sbp.P_ID left join SYS_User suuus on suuus.u_id = mn.Bp_Create_Uid left join SYS_User suuu on suuu.u_id = '" + uId + "' where ");
            authSql = authSql.replaceAll("(mn\\.Bp_Create_Uid\\s*=\\s*'[^']*')", "(suuu.c_id = suuus.c_id or $1)");
            return getSecBiddingPlanList(request, authSql, "main");
        } else {
            return getSecBiddingPlanList(request, uId);
        }
    }

    private String appendOrAuthSql(String authSql, String orSql) {
        if (ValidateUtil.isEmpty(orSql)) {
            return authSql;
        }
        int whereIndex = authSql.toLowerCase().indexOf("where");
        if (whereIndex < 0) {
            return authSql;
        }
        return authSql.substring(0, whereIndex) + "where ((" + authSql.substring(whereIndex + "where".length()) + ") or (" + orSql + ")) ";
    }


    /**
     * 按权限和筛选条件分页查询招标计划，并返回统计信息。
     * 统计结果为空时使用零值统计对象，确保分页列表正常返回。
     *
     * @param request   分页及筛选条件
     * @param authSql   权限过滤 SQL
     * @param authTable 权限作用位置，main 为主表，其余为项目关联表
     * @return 包含统计信息、当前页列表和总条数的响应
     */
    public BaseResponse getSecBiddingPlanList(SecBiddingPlanRequest request, String authSql, String authTable) {
        QueryMap<SecBiddingPlanRequest> params = new QueryMap<>(request);
        params.put("bpName").put("bpCreateUname").put("state").put("pName").put("pId")
                .put("pBuildModel").put("pType").put("pUseUnit").put("pImpCompany")
                .put("pCompetentDepartmentName").put("projectNameList").put("pBuildModelList").put("bpEntrustmentType")
                .put("pTypeList").put("pUseUnitList").put("pImpCompanyList").put("pCompetentDepartmentNameList")
                .put("isNew").put("pCategory");
        if (authTable.equalsIgnoreCase("main")) {
            params.put("authSql", " where 1=1 ").put("mainAuthSql", authSql);
        } else
            params.put("authSql", authSql).put("mainAuthSql", " where 1=1 ");
        IPage<SecBiddingPlanResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SecBiddingPlanResponse> datas = baseMapper.getSecBiddingPlanList(page, params.getQuery());
        SecBiddingPlanStatisticsVO data = baseMapper.statisticsSecBiddingPlanList(params.getQuery());
        if (!CollectionUtils.isEmpty(datas)) {
            InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
            if (ied.getSecListVerify()) {
                datas.forEach(d -> {
                    SecBiddingPlanSignDTO sd = new SecBiddingPlanSignDTO();
                    BeanUtils.copyProperties(d, sd);
                    //验证数据完整性
                    if (!signVerify(ied, ied.getSecListVerify(), sd.signContent(), d.getBpSign()))
                        throw new JPMException(ResMsg.dataVerifyError);
                    d.setBpSign(null);
                });
            }
        }
        if (data == null) {
            data = new SecBiddingPlanStatisticsVO();
            data.setSecMoneySum(BigDecimal.ZERO);
            data.setSecInfoSum(0);
            data.setFinishSum(0);
        }
        data.setData(datas);
        return BaseResponse.ok(data, Integer.parseInt(String.valueOf(page.getTotal())));
    }


    @Override
    public BaseResponse getSecBiddingPlanById(String bpId) {
        SecBiddingPlanResponse data = baseMapper.getSecBiddingPlanById(bpId);
        List<SecInfoSubjectRemark> list = secInfoSubjectRemarkService.list(Wrappers.<SecInfoSubjectRemark>lambdaQuery().eq(SecInfoSubjectRemark::getMainId, bpId));
        if (ValidateUtil.isNotEmpty(list)) {
            data.setRemarkList(BeanUtil.copyToList(list, SecInfoSubjectRemarkDTO.class));
        }

        List<SecBiddingProject> secBiddingProject = secBiddingProjectMapper.getSecBiddingProject(bpId);
         if (ValidateUtil.isEmpty(secBiddingProject)) {
             return BaseResponse.fail(ResMsg.infoNotExist);
         }
         String pId = secBiddingProject.get(0).getPId();
         BaseResponse investmentList = investmentBudgetBreakdownService.getInvestmentSecInfoByPId(pId);

        //最新的估算分解数据
        List<InvestmentEstimateBreakdownChangeDetailsModel> modelDe = null;
         BaseResponse invList = investmentEstimateBreakdownService.getInvestmentEstimateBreakdownByProjectId(pId);
         if(!ValidateUtil.isEmpty(invList.getData())){
             String jsonString  =  JSONUtil.toJsonStr(invList.getData());
             JSONObject jsonObject = JSONUtil.parseObj(jsonString);
             String jsaa = jsonObject.getStr("details");
             JSONArray jsonArray = JSONUtil.parseArray(jsaa);

              modelDe =JSONUtil.toList(jsonArray, InvestmentEstimateBreakdownChangeDetailsModel.class);
             if(!CollectionUtils.isEmpty(modelDe)){
                 modelDe =  modelDe.stream().filter(e -> ValidateUtil.isNotEmpty(e.getChangeType()) && e.getChangeType().equals("新增") && ValidateUtil.isNotEmpty(e.getIsbdType()) && e.getIsbdType().equals("01")).collect(Collectors.toList());
             }
         }
        List<InvestmentBreakdownSecInfoModel> secInfoList = new ArrayList<>();
         if(!ValidateUtil.isEmpty(investmentList.getData())){
              secInfoList = JSONUtil.toList(JSONUtil.parseArray(investmentList.getData()), InvestmentBreakdownSecInfoModel.class);

         }
        List<SecInfoResponse> secInfos = data.getSecInfos();
        if (ValidateUtil.isNotEmpty(secInfos)) {
            List<String> secIds = secInfos.stream().parallel().map(SecInfoResponse::getSecId).collect(Collectors.toList());
            List<SecSourcesFundsModel> ssfs = secSourcesFundsService.getSecSourcesFundsBySecIds(secIds);
            // 查询子表
            List<SecInfoSubjectResponse> subjects = secInfoSubjectService.getSecInfoSubjectsBySecIds(secIds);
            // 查询扩展
            List<SecInfoSubjectResponse> secInfoExtends = secInfoSubjectExtraService.getSecInfoSubjectExtraBySecIds(secIds, data.getBpPlanPhase());
            //查询概算清单
            List<SecInfoBudgetDeclareDtlDto> secInfoBudgetDeclareDtlDtos = secInfoBudgetDeclareDtlService.getSecInfoBudgetDeclareDtlBySecIds(secIds);
            List<SecInfoResponse> newSecInfos = new ArrayList<>();
            for (SecInfoResponse secInfo : secInfos) {
                if (ValidateUtil.isNotEmpty(ssfs)) {
                    List<SecSourcesFundsModel> ssf = ssfs.stream().filter(n -> n.getSecId().equals(secInfo.getSecId())).collect(Collectors.toList());
                    secInfo.setSourcesFundsModels(ssf);
                }
                if (ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtlDtos)) {
                    List<SecInfoBudgetDeclareDtlDto> collect = secInfoBudgetDeclareDtlDtos.stream().filter(n -> n.getSecId().equals(secInfo.getSecId())).collect(Collectors.toList());
                    secInfo.setSecInfoBudgetDeclareDtlDtoList(collect);
                }
                if(!CollectionUtils.isEmpty(secInfoList)){
                    secInfoList.stream().filter(e-> ValidateUtil.isNotEmpty(e.getIsbdSectionName())&& e.getIsbdSectionName().equals(secInfo.getSecName()))
                            .findFirst()
                            .ifPresent(model -> {
                                secInfo.setBudgetMoney(ValidateUtil.isNull(model.getIsbdBudgetAmount()) ? BigDecimal.ZERO: model.getIsbdBudgetAmount());
                                secInfo.setSecMoney(ValidateUtil.isNull(model.getIsbdAmount()) ? BigDecimal.ZERO : model.getIsbdAmount());
                            });
                }
                // 设置子表
                if (ValidateUtil.isNotEmpty(subjects)) {
                    List<SecInfoSubjectResponse> subject = subjects.stream().filter(n -> n.getSecId().equals(secInfo.getSecId())).collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(subject)) {
                        secInfo.setSecInfoSubjects(subject);
                    }
                }
                if (ValidateUtil.isNotEmpty(secInfoExtends)) {
                    List<SecInfoSubjectResponse> subject = secInfoExtends.stream().filter(n -> n.getSecId().equals(secInfo.getSecId())).collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(subject)) {
                        secInfo.setSecInfoSubjects(subject);
                    }
                }
                newSecInfos.add(secInfo);
            }
             String bpIdd = newSecInfos.stream().map(e -> e.getBpId()).findFirst().get();
            if(!CollectionUtils.isEmpty(modelDe)){
                for (InvestmentEstimateBreakdownChangeDetailsModel model : modelDe) {
                    SecInfoResponse secInfo1 = new SecInfoResponse();
                    secInfo1.setBpId(bpIdd);
                    secInfo1.setBudgetMoney(new BigDecimal(0));
                    secInfo1.setIeDetailId(Objects.isNull(model.getIeDetailId()) ? "" : model.getIeDetailId());
                    secInfo1.setIsbdSort(model.getSort() != null ? String.valueOf(model.getSort()) : "");
                    secInfo1.setIsbdType(Objects.isNull(model.getIsbdType()) ? "" : model.getIsbdType());
                    secInfo1.setNodeApproval(0);
                    secInfo1.setNodeComplete(0);
                    secInfo1.setNodeTotal(0);
                    secInfo1.setNodeUnStart(0);
                    secInfo1.setSecId(GenerateUUID.getUUID());
                    secInfo1.setSecMoney(model.getIsbdAmount() != null ? BigDecimal.valueOf(model.getIsbdAmount()) : BigDecimal.ZERO);
                    secInfo1.setSecName(Objects.isNull(model.getIsbdSectionName()) ? "" : model.getIsbdSectionName());
                    secInfo1.setSecType(Objects.isNull(model.getIsbdSectionType()) ? "" : model.getIsbdSectionType());
                    secInfo1.setUnalterable(0);
                    secInfo1.setChangeType(Objects.isNull(model.getChangeType()) ? "" : model.getChangeType());
                    newSecInfos.add(secInfo1);
                }
            }
            data.setSecInfos(newSecInfos);
        }
        // 查询上一个 change
        SecBiddingPlanChange change = secBiddingPlanChangeService.getOne(
                Wrappers.<SecBiddingPlanChange>lambdaQuery()
                        .eq(SecBiddingPlanChange::getBpId, data.getBpId())
                        .orderByDesc(SecBiddingPlanChange::getBpCreateTime)
                        .orderByDesc(SecBiddingPlanChange::getBpcId)
                        .last(" limit 1 ")
        );
        if (ValidateUtil.isNull(change)) {
            return BaseResponse.ok(data);
        }
        List<SecInfoChangeRes> secInfoChangeList = secInfoChangeService.getSecInfoChangeList(change.getBpcId());
        if (ValidateUtil.isEmpty(secInfoChangeList)) {
            return BaseResponse.ok(data);
        }

        List<SecInfoChangeRes> history = secInfoChangeList.stream()
                .filter(item -> "变更".equals(item.getSecChangeType()))
                .collect(Collectors.toList());

        if (ValidateUtil.isNotEmpty(history)) {
            List<String> sicIds = history.stream().map(SecInfoChangeRes::getSicId).collect(Collectors.toList());
            List<SecInfoSubjectChangeResponse> subjectChangeList = secInfoSubjectChangeService.getSecInfoSubjectChangeList(sicIds);
            history.forEach(item -> {
                List<SecInfoSubjectChangeResponse> subjectChanges = subjectChangeList.stream().filter(e -> e.getSicId().equals(item.getSicId())).collect(Collectors.toList());
                item.setSecInfoSubjects(subjectChanges);
            });
            data.setSecInfoChangeList(history);
        }
        return BaseResponse.ok(data, 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse appoval2CreateSecInfoSysFile(SecInfoRequest request) {
        if (ValidateUtil.isEmpty(request.getBpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        SecBiddingPlan data = baseMapper.selectById(request.getBpId());
        if (data == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (!ValidateUtil.isEmpty(data.getState()) && Objects.equals(data.getState(), ApprovalStatusEnum.Already_Over.getIndex())) {
            try {
                secInfoService.createSecInfoSysFileByBath(request);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(),ex);
                return BaseResponse.fail(ResMsg.actError);
            }
        }
        //return  BaseResponse.fail(ResMsg.stateCannotAct);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecBiddingPlanGroupByProject(SecBiddingPlanRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", authSql);
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("secFlowState", !JudgeUtil.checkObject(request.getSecFlowState()) ? request.getSecFlowState() : null);
        params.put("teId", !JudgeUtil.checkObject(request.getTeId()) ? request.getTeId() : null);
        List<SecModularsGroupByProjectModel> pros = baseMapper.getSbpProjectList(params);
        Integer total = baseMapper.getSbpProjectListCount(params);
        if (ValidateUtil.isNotEmpty(pros)) {
            //项目ID
            List<String> pIds = pros.stream().parallel().map(SecModularsGroupByProjectModel::getpId).collect(Collectors.toList());
            List<SecBiddingPlanResponse> sbps = baseMapper.getSecBiddingPlanListByPids(pIds, params);
            List<SecModularsGroupByProjectModel> datas = new ArrayList<>();
            pros.forEach(data -> {
                List<SecBiddingPlanResponse> sbp = sbps.stream().parallel().filter(n -> n.getpId().equals(data.getpId())).collect(Collectors.toList());
                data.setSecModulars(sbp);
                datas.add(data);
            });
            pros = null;
            return BaseResponse.ok(datas, total);
        }
        return BaseResponse.ok(pros, total);
    }

    @Override
    public BaseResponse getTZClassInfo(SecBiddingPlanTZRequest request, String uid) {
        List<ClassInfoAnnotation> projectClassInfoAnnotations = getClassInfoAnnotations(request);
        log.info("projectClassInfoAnnotations:{}",JacksonUtils.toJSONString(projectClassInfoAnnotations));
        return BaseResponse.ok(projectClassInfoAnnotations);
    }

    @Override
    public BaseResponse getSecBiddingPlanByPId(String pId) {
        List<SecBiddingPlanResponse> secBiddingPlanResponseList = baseMapper.getSecBiddingPlanByPId(pId);
        return BaseResponse.ok(secBiddingPlanResponseList);
    }
    private static final String BLANK_LABEL = "空白数据";

    private List<ClassInfoAnnotation> getClassInfoAnnotations(SecBiddingPlanTZRequest requests) {
        List<ClassInfoAnnotation> projectClassInfoAnnotations;

        List<SecBiddingPlanTZRespose> secBiddingPlanTZResposeList = baseMapper.getValueByCodeWithPid(requests);
        try {
            projectClassInfoAnnotations = ClassInfoUtils.getClassInfo(SecBiddingPlanTZRequest.class.getName());
            List<Object> dictionaryValues =new ArrayList<>();
            for(ClassInfoAnnotation classInfoAnnotation:projectClassInfoAnnotations){
                dictionaryValues =new ArrayList<>();
                boolean hasEmptyValue = false;

                if (!classInfoAnnotation.getType().equals("date")) {
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                        if (CollectionUtil.isNotEmpty(secBiddingPlanTZResposeList)) {
                            for (SecBiddingPlanTZRespose secBiddingPlanTZRespose : secBiddingPlanTZResposeList) {
                                Class<SecBiddingPlanTZRespose> secBiddingPlanTZResposeClass = SecBiddingPlanTZRespose.class;
                                Field[] declaredFields = secBiddingPlanTZResposeClass.getDeclaredFields();

                                for (Field field : declaredFields) {
                                    field.setAccessible(true);
                                    if (field.getName().equals(classInfoAnnotation.getDbValue())) {
                                        Object o = field.get(secBiddingPlanTZRespose);

                                        if (o == null || (o instanceof String && StrUtil.isBlank((String) o))) {
                                            hasEmptyValue = true;
                                        } else if (!dictionaryValues.contains(o)) {
                                            dictionaryValues.add(o);
                                        }
                                    }
                                }
                            }
                        }
                        if (hasEmptyValue && !dictionaryValues.contains(BLANK_LABEL)) {
                            dictionaryValues.add(BLANK_LABEL);
                        }

                        dictionaryValues.sort((o1, o2) -> {
                            if (BLANK_LABEL.equals(o1)) return 1;
                            if (BLANK_LABEL.equals(o2)) return -1;
                            return o1.toString().compareTo(o2.toString());
                        });

                        classInfoAnnotation.setDictionaryValues(dictionaryValues);
                    }
                }
            }

            // 按 no 排序
            projectClassInfoAnnotations = projectClassInfoAnnotations.stream()
                    .sorted(Comparator.comparing(ClassInfoAnnotation::getNo))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return projectClassInfoAnnotations;
    }

    /**
     * 加签 验证完整性
     *
     * @param content
     * @param sign
     * @return
     */
    public Boolean signVerify(InterfaceEncDecDTO ied, Boolean verify, String content, String sign) {
        if (verify) {
            if (ValidateUtil.isNotEmpty(ied.getVerifyUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(content);
                sg.setSign(sign);
                BaseResponse res = apiUtilsService.sendPost(ied.getVerifyUrl(), sg);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    System.out.println("验签结果:" + res.getData());
                    return Boolean.parseBoolean(res.getData().toString());
                }
            }
        } else return true;
        return false;
    }

    /**
     * 招标计划数据完整性签名
     * @param ied
     * @param sign
     * @param content
     * @return
     */
    public String sign(InterfaceEncDecDTO ied, Boolean sign, String content) {
        if (sign) {
            if (ValidateUtil.isNotEmpty(ied.getSignUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(content);
                BaseResponse res = apiUtilsService.sendPost(ied.getSignUrl(), sg);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    System.out.println("签名结果:" + res.getData().toString());
                    return res.getData().toString();
                }
                throw new JPMException(ResMsg.dataWzSignError);
            }
            throw new JPMException(String.format(ResMsg.confFormatError, "interfaceEncDecConfig"));
        } else return null;
    }

    @Override
    public SecBiddingPlan getSecBiddingPlanByPIdList(List<String> bpIds) {
        LambdaQueryWrapper<SecBiddingPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SecBiddingPlan::getBpId,bpIds)
                .eq(SecBiddingPlan::getState,7)
                .orderByDesc(SecBiddingPlan::getStateTime)
                .last(" limit 1 ");
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public BaseResponse judgmentSecBiddingPlanByPId(String pId) {
        return BaseResponse.ok(this.judgmentSecBiddingPlanType(pId));
    }

    @Override
    public String judgmentSecBiddingPlanType(String pId) {
        InvestmentEstimateBreakdown estimate = investmentEstimateBreakdownService.getOne(Wrappers.<InvestmentEstimateBreakdown>lambdaQuery()
                .eq(InvestmentEstimateBreakdown::getProjectId, pId)
                .ne(InvestmentEstimateBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
        );
        InvestmentBudgetBreakdown budgetEstimates = investmentBudgetBreakdownService.getOne(Wrappers.<InvestmentBudgetBreakdown>lambdaQuery()
                .eq(InvestmentBudgetBreakdown::getProjectId, pId)
                .ne(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
        );
        return this.judgmentSecBiddingPlanType(estimate, budgetEstimates);
    }

    private String judgmentSecBiddingPlanType(InvestmentEstimateBreakdown estimate, InvestmentBudgetBreakdown budgetEstimates) {
        // 估算
        boolean existsEstimate = ValidateUtil.isNotNull(estimate);
        // 概算
        boolean existsBudgetEstimates = ValidateUtil.isNotNull(budgetEstimates);

        // 有估算，且估算已完成，才能进行后续判断
        if (existsEstimate && Objects.equals(estimate.getState(), ApprovalStatusEnum.Already_Over.getIndex())) {
            if (existsBudgetEstimates && Objects.equals(budgetEstimates.getState(), ApprovalStatusEnum.Already_Over.getIndex())) {
                // 已完成概算，返回04
                return "04";
            }
            if ("01".equalsIgnoreCase(estimate.getEstReview())) {
                // 只有估算数据，且需概算审核，且没有完成概算，返回03
                return "03";
            }
            // 只有估算数据，且无需概算审核，返回02
            return "02";
        }
        // 没有概算和估算数据，返回01
        return "01";
    }

    @Override
    public BaseResponse judgmentSecBiddingPlanAmount(String type, String id, BigDecimal money) {
        if ("01".equalsIgnoreCase(type)) {
            return BaseResponse.fail("没有概算和估算数据，无法进行金额判断");
        }
        BigDecimal amount = BigDecimal.ZERO;
        if ("02".equalsIgnoreCase(type) || "03".equalsIgnoreCase(type)) {
            // 只有估算数据，且无需概算审核，或者只有估算数据，且需概算审核，且没有完成概算，使用估算的数据
            InvestmentEstimateBreakdownDetails breakdownDetails = investmentEstimateBreakdownDetailsMapper.selectById(id);
            if (ValidateUtil.isNull(breakdownDetails) || ValidateUtil.isNull(breakdownDetails.getIsbdAmount())) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            amount = breakdownDetails.getIsbdAmount();
        } else if ("04".equalsIgnoreCase(type)) {
            // 已完成概算，就使用概算数据
            InvestmentBudgetBreakdownDetails breakdownDetails = investmentBudgetBreakdownDetailsMapper.selectById(id);
            if (ValidateUtil.isNull(breakdownDetails) || ValidateUtil.isNull(breakdownDetails.getIsbdBudgetAmount())) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            amount = breakdownDetails.getIsbdBudgetAmount();
        }

        if (money.compareTo(amount) > 0) {
            return BaseResponse.fail("输入金额不能大于估算金额");
        }
        // 余额
        return BaseResponse.ok(amount.subtract(money));
    }

    @Override
    public BaseResponse getSecBiddingPlanSubjectListByPId(String pId, String type, String excludeExtra, String bpcId) {
        InvestmentEstimateBreakdown estimate = investmentEstimateBreakdownService.getOne(Wrappers.<InvestmentEstimateBreakdown>lambdaQuery()
                .eq(InvestmentEstimateBreakdown::getProjectId, pId)
                .ne(InvestmentEstimateBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
        );
        InvestmentBudgetBreakdown budgetEstimates = investmentBudgetBreakdownService.getOne(Wrappers.<InvestmentBudgetBreakdown>lambdaQuery()
                .eq(InvestmentBudgetBreakdown::getProjectId, pId)
                .ne(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
        );
        if (ValidateUtil.isEmpty(type)) {
            type = this.judgmentSecBiddingPlanType(estimate, budgetEstimates);
        }
        if ("01".equalsIgnoreCase(type)) {
            return BaseResponse.fail("没有概算和估算数据，无法进行标段科目查询");
        }
        // 查询现有的数据
        List<SecInfoSubjectResponse> subjects;
        List<SecProject> list = secProjectService.list(Wrappers.<SecProject>lambdaQuery().eq(SecProject::getPId, pId).select(SecProject::getSecId));
        if (ValidateUtil.isNotEmpty(list)) {
            subjects = secInfoSubjectService.getSecInfoSubjectsBySecIds(list.stream().map(SecProject::getSecId).collect(Collectors.toList()));
        } else {
            subjects = new ArrayList<>();
        }
        if ("02".equalsIgnoreCase(type) || "03".equalsIgnoreCase(type)) {
            // 只有估算数据，且无需概算审核，或者只有估算数据，且需概算审核，且没有完成概算，使用估算的数据
            List<InvestmentEstimateBreakdownDetails> detailsList = investmentEstimateBreakdownDetailsMapper.selectList(Wrappers.<InvestmentEstimateBreakdownDetails>lambdaQuery()
                    .eq(InvestmentEstimateBreakdownDetails::getIsbId, estimate.getId()));
            List<InvestmentEstimateBreakdownDetailsModel> vos = detailsList.stream().map(entity -> {
                InvestmentEstimateBreakdownDetailsModel model = new InvestmentEstimateBreakdownDetailsModel();
                BeanUtils.copyProperties(entity, model);
                // 计算总计和余额
                BigDecimal isbdAmount = ValidateUtil.isNull(model.getIsbdAmount()) ? BigDecimal.ZERO : model.getIsbdAmount();
                Optional<SecInfoSubjectResponse> subjectOpt = subjects.stream()
                        .filter(subject ->
                                subject.getSubIebId() != null
                                        && entity.getId() != null
                                        && subject.getSubIebId().equals(entity.getId())
                        )
                        .findFirst();
                if (subjectOpt.isPresent()) {
                    SecInfoSubjectResponse subject = subjectOpt.get();
                    model.setAmountOccupied(subject.getSubMoney());
                    model.setAmountBalance(isbdAmount.subtract(subject.getSubMoney()));
                } else {
                    model.setAmountOccupied(BigDecimal.ZERO);
                    model.setAmountBalance(isbdAmount);
                }
                // 标识
                model.setEstimateBudgetFlag("01");
                model.setSubjectSourceFlag("01");
                return model;
            }).collect(Collectors.toList());
            if (ValidateUtil.isEmpty(excludeExtra)) {
                // 科目信息扩展部分
                Map<String, List<SecInfoSubjectExtra>> extraMap = getSubjectExtraMap(vos, InvestmentEstimateBreakdownDetailsModel::getId, bpcId);
                Map<String, BigDecimal> currentUseMap = buildCurrentUseMap(subjects, true);
                // 在对应的父级下的子集附加上并计算sort、levelCode
                vos = appendSubjectExtras(vos, extraMap,
                        InvestmentEstimateBreakdownDetailsModel::getId,
                        InvestmentEstimateBreakdownDetailsModel::getParentId,
                        (parent, extra) -> buildEstimateExtraNode(parent, extra, currentUseMap));
            }
            return BaseResponse.ok(vos);
        } else if ("04".equalsIgnoreCase(type)) {
            // 已完成概算，就使用概算数据
            List<InvestmentBudgetBreakdownDetails> detailsList = investmentBudgetBreakdownDetailsMapper.selectList(Wrappers.<InvestmentBudgetBreakdownDetails>lambdaQuery()
                    .eq(InvestmentBudgetBreakdownDetails::getIsbId, budgetEstimates.getId()));
            List<InvestmentBudgetBreakdownDetailsModel> vos = detailsList.stream().map(entity -> {
                InvestmentBudgetBreakdownDetailsModel model = new InvestmentBudgetBreakdownDetailsModel();
                BeanUtils.copyProperties(entity, model);
                // 计算总计和余额
                BigDecimal isbdBudgetAmount = ValidateUtil.isNull(model.getIsbdBudgetAmount()) ? BigDecimal.ZERO : model.getIsbdBudgetAmount();
                Optional<SecInfoSubjectResponse> subjectOpt = subjects.stream()
                            .filter(subject ->
                                subject.getSubIbbId() != null
                                && entity.getId() != null
                                && subject.getSubIbbId().equals(entity.getId())
                            )
                            .findFirst();
                if (subjectOpt.isPresent()) {
                    SecInfoSubjectResponse subject = subjectOpt.get();
                    model.setAmountOccupied(subject.getSubMoney());
                    model.setAmountBalance(isbdBudgetAmount.subtract(subject.getSubMoney()));
                } else {
                    model.setAmountOccupied(BigDecimal.ZERO);
                    model.setAmountBalance(isbdBudgetAmount);
                }
                // 标识
                model.setEstimateBudgetFlag("02");
                model.setSubjectSourceFlag("01");
                return model;
            }).collect(Collectors.toList());
            if (ValidateUtil.isEmpty(excludeExtra)) {
                // 科目信息扩展部分
                Map<String, List<SecInfoSubjectExtra>> extraMap = getSubjectExtraMap(vos, InvestmentBudgetBreakdownDetailsModel::getId, bpcId);
                Map<String, BigDecimal> currentUseMap = buildCurrentUseMap(subjects, false);
                // 在对应的父级下的子集附加上并计算sort、levelCode
                vos = appendSubjectExtras(vos, extraMap,
                        InvestmentBudgetBreakdownDetailsModel::getId,
                        InvestmentBudgetBreakdownDetailsModel::getParentId,
                        (parent, extra) -> buildBudgetExtraNode(parent, extra, currentUseMap));
            }
            return BaseResponse.ok(vos);
        }
        return BaseResponse.ok();
    }

    /**
     * 获取科目信息扩展部分
     *
     * @param vos
     * @param idGetter
     * @return
     * @param <T>
     */
    private <T> Map<String, List<SecInfoSubjectExtra>> getSubjectExtraMap(List<T> vos, Function<T, String> idGetter, String bpcId) {
        if (CollectionUtils.isEmpty(vos)) {
            return new LinkedHashMap<>();
        }
        Set<String> subjectIds = vos.stream()
                .map(idGetter)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(subjectIds)) {
            return new LinkedHashMap<>();
        }
        List<SecInfoSubjectExtra> extraList;
        if (ValidateUtil.isNotEmpty(bpcId)) {
            List<SecInfoSubjectExtraChange> extraChangeList = secInfoSubjectExtraChangeService.list(Wrappers.<SecInfoSubjectExtraChange>lambdaQuery()
                    .eq(SecInfoSubjectExtraChange::getBpcId, bpcId));
            extraList = extraChangeList.stream()
                    .map(extraChange -> {
                        SecInfoSubjectExtra extra = new SecInfoSubjectExtra();
                        BeanUtils.copyProperties(extraChange, extra);
                        return extra;
                    })
                    .filter(extra -> subjectIds.contains(extra.getParentId()))
                    .collect(Collectors.toList());
        } else {
            extraList = secInfoSubjectExtraService.list(Wrappers.<SecInfoSubjectExtra>lambdaQuery()
                    .in(SecInfoSubjectExtra::getParentId, subjectIds));
        }
        if (CollectionUtils.isEmpty(extraList)) {
            return new LinkedHashMap<>();
        }
        return extraList.stream()
                .sorted(Comparator.comparing(SecInfoSubjectExtra::getSort, Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.groupingBy(SecInfoSubjectExtra::getParentId, LinkedHashMap::new, Collectors.toList()));
    }

    /**
     * 获取科目信息扩展部分
     *
     * @param vos
     * @param extraMap
     * @param idGetter
     * @param parentIdGetter
     * @param extraNodeBuilder
     * @return
     * @param <T>
     */
    private <T> List<T> appendSubjectExtras(List<T> vos,
                                            Map<String, List<SecInfoSubjectExtra>> extraMap,
                                            Function<T, String> idGetter,
                                            Function<T, String> parentIdGetter,
                                            BiFunction<T, SecInfoSubjectExtra, T> extraNodeBuilder) {
        if (CollectionUtils.isEmpty(vos) || CollectionUtils.isEmpty(extraMap)) {
            return vos;
        }
        Map<String, T> idMap = vos.stream()
                .filter(model -> ValidateUtil.isNotEmpty(idGetter.apply(model)))
                .collect(Collectors.toMap(idGetter, Function.identity(), (a, b) -> a, LinkedHashMap::new));
        Map<String, List<T>> childrenMap = vos.stream()
                .filter(model -> ValidateUtil.isNotEmpty(parentIdGetter.apply(model)))
                .collect(Collectors.groupingBy(parentIdGetter, LinkedHashMap::new, Collectors.toList()));
        List<T> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        vos.stream()
                .filter(model -> ValidateUtil.isEmpty(parentIdGetter.apply(model)) || !idMap.containsKey(parentIdGetter.apply(model)))
                .forEach(model -> flattenWithExtras(model, childrenMap, extraMap, idGetter, extraNodeBuilder, result, visited));
        vos.stream()
                .filter(model -> !visited.contains(idGetter.apply(model)))
                .forEach(model -> flattenWithExtras(model, childrenMap, extraMap, idGetter, extraNodeBuilder, result, visited));
        return result;
    }

    /**
     * 获取科目信息扩展部分
     *
     * @param parent
     * @param childrenMap
     * @param extraMap
     * @param idGetter
     * @param extraNodeBuilder
     * @param result
     * @param visited
     * @param <T>
     */
    private <T> void flattenWithExtras(T parent,
                                       Map<String, List<T>> childrenMap,
                                       Map<String, List<SecInfoSubjectExtra>> extraMap,
                                       Function<T, String> idGetter,
                                       BiFunction<T, SecInfoSubjectExtra, T> extraNodeBuilder,
                                       List<T> result,
                                       Set<String> visited) {
        if (parent == null || !visited.add(idGetter.apply(parent))) {
            return;
        }
        result.add(parent);
        String parentId = idGetter.apply(parent);
        List<T> children = childrenMap.get(parentId);
        if (!CollectionUtils.isEmpty(children)) {
            children.forEach(child -> flattenWithExtras(child, childrenMap, extraMap, idGetter, extraNodeBuilder, result, visited));
        }
        List<SecInfoSubjectExtra> extras = extraMap.get(parentId);
        if (CollectionUtils.isEmpty(extras)) {
            return;
        }
        for (SecInfoSubjectExtra extra : extras) {
            result.add(extraNodeBuilder.apply(parent, extra));
        }
    }

    /**
     * 获取科目信息扩展部分
     *
     * @param vos
     * @param parentIdMap
     * @param idGetter
     * @param extraNodeBuilder
     * @param <T>
     */
    private <T> void appendTreeSubjectExtras(List<T> vos,
                                             Map<String, List<T>> parentIdMap,
                                             Function<T, String> idGetter,
                                             String bpcId,
                                             BiFunction<T, SecInfoSubjectExtra, T> extraNodeBuilder) {
        Map<String, List<SecInfoSubjectExtra>> extraMap = getSubjectExtraMap(vos, idGetter, bpcId);
        if (CollectionUtils.isEmpty(extraMap)) {
            return;
        }
        Map<String, T> idMap = vos.stream()
                .filter(model -> ValidateUtil.isNotEmpty(idGetter.apply(model)))
                .collect(Collectors.toMap(idGetter, Function.identity(), (a, b) -> a));
        extraMap.forEach((parentId, extras) -> {
            T parent = idMap.get(parentId);
            if (parent == null || CollectionUtils.isEmpty(extras)) {
                return;
            }
            List<T> children = parentIdMap.computeIfAbsent(parentId, k -> new ArrayList<>());
            for (SecInfoSubjectExtra extra : extras) {
                children.add(extraNodeBuilder.apply(parent, extra));
            }
        });
    }

    /**
     * 获取科目信息扩展部分
     *
     * @param parent
     * @param extra
     * @param currentUseMap
     * @return
     */
    private InvestmentEstimateBreakdownDetailsModel buildEstimateExtraNode(InvestmentEstimateBreakdownDetailsModel parent,
                                                                          SecInfoSubjectExtra extra,
                                                                          Map<String, BigDecimal> currentUseMap) {
        InvestmentEstimateBreakdownDetailsModel node = new InvestmentEstimateBreakdownDetailsModel();
        node.setId(extra.getId());
        node.setIsbId(parent.getIsbId());
        node.setIsbdFeeTypeName(extra.getSeName());
        node.setIsbdAmount(ValidateUtil.isNull(extra.getSeMoney()) ? BigDecimal.ZERO : extra.getSeMoney());
        node.setSort(ValidateUtil.isNull(extra.getSort()) ? null : BigDecimal.valueOf(extra.getSort()));
        String levelCode = extra.getLevelCode();
        levelCode = levelCode.substring(0, levelCode.lastIndexOf(" "));
        node.setLevelCode(levelCode);
        node.setParentId(parent.getId());
        node.setChildNodes(new ArrayList<>());
        BigDecimal occupied = currentUseMap.getOrDefault(extra.getId(), BigDecimal.ZERO);
        node.setAmountOccupied(occupied);
        node.setAmountBalance(BigDecimal.ZERO);
        node.setEstimateBudgetFlag(parent.getEstimateBudgetFlag());
        node.setSubjectSourceFlag("02");
        return node;
    }

    /**
     * 获取科目信息扩展部分
     *
     * @param parent
     * @param extra
     * @param currentUseMap
     * @return
     */
    private InvestmentBudgetBreakdownDetailsModel buildBudgetExtraNode(InvestmentBudgetBreakdownDetailsModel parent,
                                                                      SecInfoSubjectExtra extra,
                                                                      Map<String, BigDecimal> currentUseMap) {
        BigDecimal money = ValidateUtil.isNull(extra.getSeMoney()) ? BigDecimal.ZERO : extra.getSeMoney();
        InvestmentBudgetBreakdownDetailsModel node = new InvestmentBudgetBreakdownDetailsModel();
        node.setId(extra.getId());
        node.setIsbId(parent.getIsbId());
        node.setIsbdFeeTypeName(extra.getSeName());
        node.setIsbdAmount(money);
        node.setIsbdBudgetAmount(money);
        node.setSort(ValidateUtil.isNull(extra.getSort()) ? null : BigDecimal.valueOf(extra.getSort()));
        String levelCode = extra.getLevelCode();
        levelCode = levelCode.substring(0, levelCode.lastIndexOf(" "));
        node.setLevelCode(levelCode);
        node.setParentId(parent.getId());
        node.setChildNodes(new ArrayList<>());
        BigDecimal occupied = currentUseMap.getOrDefault(extra.getId(), BigDecimal.ZERO);
        node.setAmountOccupied(occupied);
        node.setAmountBalance(BigDecimal.ZERO);
        node.setEstimateBudgetFlag(parent.getEstimateBudgetFlag());
        node.setSubjectSourceFlag("02");
        return node;
    }

    @Override
    public BaseResponse getSecBiddingPlanSubjectListByPIdTree(String pId, String type, String excludeExtra, String bpcId) {
        InvestmentEstimateBreakdown estimate = investmentEstimateBreakdownService.getOne(Wrappers.<InvestmentEstimateBreakdown>lambdaQuery()
                .eq(InvestmentEstimateBreakdown::getProjectId, pId)
                .ne(InvestmentEstimateBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
        );
        InvestmentBudgetBreakdown budgetEstimates = investmentBudgetBreakdownService.getOne(Wrappers.<InvestmentBudgetBreakdown>lambdaQuery()
                .eq(InvestmentBudgetBreakdown::getProjectId, pId)
                .ne(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
        );
        if (ValidateUtil.isEmpty(type)) {
            type = this.judgmentSecBiddingPlanType(estimate, budgetEstimates);
        }
        if ("01".equalsIgnoreCase(type)) {
            return BaseResponse.fail("没有概算和估算数据，无法进行标段科目查询");
        }
        // 查询现有的数据
        List<SecInfoSubjectResponse> subjects;
        List<SecProject> list = secProjectService.list(Wrappers.<SecProject>lambdaQuery().eq(SecProject::getPId, pId).select(SecProject::getSecId));
        if (ValidateUtil.isNotEmpty(list)) {
            subjects = secInfoSubjectService.getSecInfoSubjectsBySecIds(list.stream().map(SecProject::getSecId).collect(Collectors.toList()));
        } else {
            subjects = new ArrayList<>();
        }
        if ("02".equalsIgnoreCase(type) || "03".equalsIgnoreCase(type)) {
            // 只有估算数据，且无需概算审核，或者只有估算数据，且需概算审核，且没有完成概算，使用估算的数据
            List<InvestmentEstimateBreakdownDetails> detailsList = investmentEstimateBreakdownDetailsMapper.selectList(Wrappers.<InvestmentEstimateBreakdownDetails>lambdaQuery()
                    .eq(InvestmentEstimateBreakdownDetails::getIsbId, estimate.getId()));
            List<InvestmentEstimateBreakdownDetailsModel> vos = detailsList.stream().map(entity -> {
                InvestmentEstimateBreakdownDetailsModel model = new InvestmentEstimateBreakdownDetailsModel();
                BeanUtils.copyProperties(entity, model);
                // 计算总计和余额
                BigDecimal isbdAmount = ValidateUtil.isNull(model.getIsbdAmount()) ? BigDecimal.ZERO : model.getIsbdAmount();
                Optional<SecInfoSubjectResponse> subjectOpt = subjects.stream()
                        .filter(subject ->
                                subject.getSubIebId() != null
                                        && entity.getId() != null
                                        && subject.getSubIebId().equals(entity.getId())
                        )
                        .findFirst();
                if (subjectOpt.isPresent()) {
                    SecInfoSubjectResponse subject = subjectOpt.get();
                    model.setAmountOccupied(subject.getSubMoney());
                    model.setAmountBalance(isbdAmount.subtract(subject.getSubMoney()));
                } else {
                    model.setAmountOccupied(BigDecimal.ZERO);
                    model.setAmountBalance(isbdAmount);
                }
                // 标识
                model.setEstimateBudgetFlag("01");
                model.setSubjectSourceFlag("01");
                return model;
            }).collect(Collectors.toList());
            // 根据parentId构建树形结构
            Map<String, List<InvestmentEstimateBreakdownDetailsModel>> parentIdMap = vos.stream()
                    .filter(model -> ValidateUtil.isNotEmpty(model.getParentId()))
                    .collect(Collectors.groupingBy(InvestmentEstimateBreakdownDetailsModel::getParentId));
            if (ValidateUtil.isEmpty(excludeExtra)) {
                Map<String, BigDecimal> currentUseMap = buildCurrentUseMap(subjects, true);
                appendTreeSubjectExtras(vos, parentIdMap,
                        InvestmentEstimateBreakdownDetailsModel::getId,
                        bpcId,
                        (parent, extra) -> buildEstimateExtraNode(parent, extra, currentUseMap));
            }
            vos.forEach(model -> {
                if (parentIdMap.containsKey(model.getId())) {
                    model.setChildNodes(parentIdMap.get(model.getId()));
                }
            });
            // 返回根节点数据
            List<InvestmentEstimateBreakdownDetailsModel> rootNodes = vos.stream()
                    .filter(model -> ValidateUtil.isEmpty(model.getParentId()))
                    .collect(Collectors.toList());
            return BaseResponse.ok(rootNodes);
        } else if ("04".equalsIgnoreCase(type)) {
            // 已完成概算，就使用概算数据
            List<InvestmentBudgetBreakdownDetails> detailsList = investmentBudgetBreakdownDetailsMapper.selectList(Wrappers.<InvestmentBudgetBreakdownDetails>lambdaQuery()
                    .eq(InvestmentBudgetBreakdownDetails::getIsbId, budgetEstimates.getId()));
            List<InvestmentBudgetBreakdownDetailsModel> vos = detailsList.stream().map(entity -> {
                InvestmentBudgetBreakdownDetailsModel model = new InvestmentBudgetBreakdownDetailsModel();
                BeanUtils.copyProperties(entity, model);
                // 计算总计和余额
                BigDecimal isbdBudgetAmount = ValidateUtil.isNull(model.getIsbdBudgetAmount()) ? BigDecimal.ZERO : model.getIsbdBudgetAmount();
                Optional<SecInfoSubjectResponse> subjectOpt = subjects.stream()
                        .filter(subject ->
                                subject.getSubIbbId() != null
                                        && entity.getId() != null
                                        && subject.getSubIbbId().equals(entity.getId())
                        )
                        .findFirst();
                if (subjectOpt.isPresent()) {
                    SecInfoSubjectResponse subject = subjectOpt.get();
                    model.setAmountOccupied(subject.getSubMoney());
                    model.setAmountBalance(isbdBudgetAmount.subtract(subject.getSubMoney()));
                } else {
                    model.setAmountOccupied(BigDecimal.ZERO);
                    model.setAmountBalance(isbdBudgetAmount);
                }
                // 标识
                model.setEstimateBudgetFlag("02");
                model.setSubjectSourceFlag("01");
                return model;
            }).collect(Collectors.toList());
            // 根据parentId构建树形结构
            Map<String, List<InvestmentBudgetBreakdownDetailsModel>> parentIdMap = vos.stream()
                    .filter(model -> ValidateUtil.isNotEmpty(model.getParentId()))
                    .collect(Collectors.groupingBy(InvestmentBudgetBreakdownDetailsModel::getParentId));
            if (ValidateUtil.isEmpty(excludeExtra)) {
                Map<String, BigDecimal> currentUseMap = buildCurrentUseMap(subjects, false);
                appendTreeSubjectExtras(vos, parentIdMap,
                        InvestmentBudgetBreakdownDetailsModel::getId,
                        bpcId,
                        (parent, extra) -> buildBudgetExtraNode(parent, extra, currentUseMap));
            }
            vos.forEach(model -> {
                if (parentIdMap.containsKey(model.getId())) {
                    model.setChildNodes(parentIdMap.get(model.getId()));
                }
            });
            // 返回根节点数据
            List<InvestmentBudgetBreakdownDetailsModel> rootNodes = vos.stream()
                    .filter(model -> ValidateUtil.isEmpty(model.getParentId()))
                    .collect(Collectors.toList());
            return BaseResponse.ok(rootNodes);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecFileRelationIdByPId(String pId) {
        PreProject project = preProjectService.getById(pId);
        if (ValidateUtil.isNull(project)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        InvestmentBudgetBreakdown budgetEstimates = investmentBudgetBreakdownService.getOne(Wrappers.<InvestmentBudgetBreakdown>lambdaQuery()
                .eq(InvestmentBudgetBreakdown::getProjectId, pId)
                .eq(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        Map<String, String> res = new HashMap<>();
        if ("01".equalsIgnoreCase(project.getpProjectFormType())) {
            res.put("pApplyFile", project.getpCommitteeApprovaDocument());
        } else if ("02".equalsIgnoreCase(project.getpProjectFormType())) {
            res.put("pApplyFile", project.getpRecordFile());
        } else {
            res.put("pApplyFile", project.getpApplyFile());
        }
        if (ValidateUtil.isNotNull(budgetEstimates)) {
            res.put("projectBudgetFile", budgetEstimates.getProjectBudgetFile());
        }
        return BaseResponse.ok(res);
    }

    @Override
    public BaseResponse getSecBiddingPlanByPIdNJCJ(String pId) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecBiddingPlanResponse data = baseMapper.getSecBiddingPlanByPId2(pId);
        if (ValidateUtil.isNull(data)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<SecBiddingProject> secBiddingProject = secBiddingProjectMapper.getSecBiddingProject(data.getBpId());
        if (ValidateUtil.isEmpty(secBiddingProject)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        String pId2 = secBiddingProject.get(0).getPId();
        BaseResponse investmentList = investmentBudgetBreakdownService.getInvestmentSecInfoByPId(pId2);

        // 最新的估算分解数据
        List<InvestmentEstimateBreakdownChangeDetailsModel> modelDe = null;
        BaseResponse invList = investmentEstimateBreakdownService.getInvestmentEstimateBreakdownByProjectId(pId2);
        if (!ValidateUtil.isEmpty(invList.getData())) {
            String jsonString = JSONUtil.toJsonStr(invList.getData());
            JSONObject jsonObject = JSONUtil.parseObj(jsonString);
            String jsaa = jsonObject.getStr("details");
            JSONArray jsonArray = JSONUtil.parseArray(jsaa);

            modelDe = JSONUtil.toList(jsonArray, InvestmentEstimateBreakdownChangeDetailsModel.class);
            if (!CollectionUtils.isEmpty(modelDe)) {
                modelDe = modelDe.stream().filter(e -> ValidateUtil.isNotEmpty(e.getChangeType()) && "新增".equals(e.getChangeType()) && ValidateUtil.isNotEmpty(e.getIsbdType()) && "01".equals(e.getIsbdType())).collect(Collectors.toList());
            }
        }
        List<InvestmentBreakdownSecInfoModel> secInfoList = new ArrayList<>();
        if (!ValidateUtil.isEmpty(investmentList.getData())) {
            secInfoList = JSONUtil.toList(JSONUtil.parseArray(investmentList.getData()), InvestmentBreakdownSecInfoModel.class);

        }
        List<SecInfoResponse> secInfos = data.getSecInfos();
        if (ValidateUtil.isNotEmpty(secInfos)) {
            List<String> secIds = secInfos.stream().parallel().map(SecInfoResponse::getSecId).collect(Collectors.toList());
            List<SecSourcesFundsModel> ssfs = secSourcesFundsService.getSecSourcesFundsBySecIds(secIds);
            // 查询子表
            List<SecInfoSubjectResponse> subjects = secInfoSubjectService.getSecInfoSubjectsBySecIds(secIds);
            // 查询概算清单
            List<SecInfoBudgetDeclareDtlDto> secInfoBudgetDeclareDtlDtos = secInfoBudgetDeclareDtlService.getSecInfoBudgetDeclareDtlBySecIds(secIds);
            List<SecInfoResponse> newSecInfos = new ArrayList<>();
            for (SecInfoResponse secInfo : secInfos) {
                if (ValidateUtil.isNotEmpty(ssfs)) {
                    List<SecSourcesFundsModel> ssf = ssfs.stream().filter(n -> n.getSecId().equals(secInfo.getSecId())).collect(Collectors.toList());
                    secInfo.setSourcesFundsModels(ssf);
                }
                if (ValidateUtil.isNotEmpty(secInfoBudgetDeclareDtlDtos)) {
                    List<SecInfoBudgetDeclareDtlDto> collect = secInfoBudgetDeclareDtlDtos.stream().filter(n -> n.getSecId().equals(secInfo.getSecId())).collect(Collectors.toList());
                    secInfo.setSecInfoBudgetDeclareDtlDtoList(collect);
                }
                if (!CollectionUtils.isEmpty(secInfoList)) {
                    secInfoList.stream().filter(e -> ValidateUtil.isNotEmpty(e.getIsbdSectionName()) && e.getIsbdSectionName().equals(secInfo.getSecName()))
                            .findFirst()
                            .ifPresent(model -> {
                                secInfo.setBudgetMoney(ValidateUtil.isNull(model.getIsbdBudgetAmount()) ? BigDecimal.ZERO : model.getIsbdBudgetAmount());
                                secInfo.setSecMoney(ValidateUtil.isNull(model.getIsbdAmount()) ? BigDecimal.ZERO : model.getIsbdAmount());
                            });
                }
                // 设置子表
                if (ValidateUtil.isNotEmpty(subjects)) {
                    List<SecInfoSubjectResponse> subject = subjects.stream().filter(n -> n.getSecId().equals(secInfo.getSecId())).collect(Collectors.toList());
                    secInfo.setSecInfoSubjects(subject);
                }
                newSecInfos.add(secInfo);
            }
            String bpIdd = newSecInfos.stream().map(SecInfoResponse::getBpId).findFirst().get();
            if (!CollectionUtils.isEmpty(modelDe)) {
                for (InvestmentEstimateBreakdownChangeDetailsModel model : modelDe) {
                    SecInfoResponse secInfo1 = new SecInfoResponse();
                    secInfo1.setBpId(bpIdd);
                    secInfo1.setBudgetMoney(new BigDecimal(0));
                    secInfo1.setIeDetailId(Objects.isNull(model.getIeDetailId()) ? "" : model.getIeDetailId());
                    secInfo1.setIsbdSort(model.getSort() != null ? String.valueOf(model.getSort()) : "");
                    secInfo1.setIsbdType(Objects.isNull(model.getIsbdType()) ? "" : model.getIsbdType());
                    secInfo1.setNodeApproval(0);
                    secInfo1.setNodeComplete(0);
                    secInfo1.setNodeTotal(0);
                    secInfo1.setNodeUnStart(0);
                    secInfo1.setSecId(GenerateUUID.getUUID());
                    secInfo1.setSecMoney(model.getIsbdAmount() != null ? BigDecimal.valueOf(model.getIsbdAmount()) : BigDecimal.ZERO);
                    secInfo1.setSecName(Objects.isNull(model.getIsbdSectionName()) ? "" : model.getIsbdSectionName());
                    secInfo1.setSecType(Objects.isNull(model.getIsbdSectionType()) ? "" : model.getIsbdSectionType());
                    secInfo1.setUnalterable(0);
                    secInfo1.setChangeType(Objects.isNull(model.getChangeType()) ? "" : model.getChangeType());
                    newSecInfos.add(secInfo1);
                }
            }
            data.setSecInfos(newSecInfos);
        }

        return BaseResponse.ok(data, 0);
    }

    /**
     * 通过项目获取估算概算总金额
     *
     * @param pId
     * @param type
     * @return
     */
    @Override
    public BaseResponse getEstimatedBudgetAmountByPId(String pId, String type) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 概算
        InvestmentBudgetBreakdown budgetBreakdown = investmentBudgetBreakdownService.getOne(Wrappers.<InvestmentBudgetBreakdown>lambdaQuery()
                .eq(InvestmentBudgetBreakdown::getProjectId, pId)
                .eq(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentBudgetBreakdown::getCreateTime)
                .last(" limit 1 ")
        );
        // 估算
        InvestmentEstimateBreakdown breakdown = investmentEstimateBreakdownService.getOne(Wrappers.<InvestmentEstimateBreakdown>lambdaQuery()
                .eq(InvestmentEstimateBreakdown::getProjectId, pId)
                .eq(InvestmentEstimateBreakdown::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentEstimateBreakdown::getCreateTime)
                .last(" limit 1 ")
        );
        Map<String, BigDecimal> res = new HashMap<>();
        if (ValidateUtil.isEmpty(type)) {
            type = this.judgmentSecBiddingPlanType(breakdown, budgetBreakdown);
        }
        List<InvestmentEstimateBreakdownDetails> estimateBreakdownDetails = null;
        if (ValidateUtil.isNotNull(breakdown)) {
            estimateBreakdownDetails = investmentEstimateBreakdownDetailsMapper.selectList(Wrappers.<InvestmentEstimateBreakdownDetails>lambdaQuery()
                    .eq(InvestmentEstimateBreakdownDetails::getIsbId, breakdown.getId())
                    .eq(InvestmentEstimateBreakdownDetails::getIsbdFeeTypeName, "工程总投资")
            );
        }
        if ("04".equalsIgnoreCase(type)) {
            List<InvestmentBudgetBreakdownDetails> budgetBreakdownDetails = investmentBudgetBreakdownDetailsMapper.selectList(Wrappers.<InvestmentBudgetBreakdownDetails>lambdaQuery()
                    .eq(InvestmentBudgetBreakdownDetails::getIsbId, budgetBreakdown.getId())
                    .eq(InvestmentBudgetBreakdownDetails::getIsbdFeeTypeName, "工程总投资")
            );
            if (ValidateUtil.isEmpty(budgetBreakdownDetails)) {
                res.put("budgetAmount", BigDecimal.ZERO);
            } else {
                res.put("budgetAmount", budgetBreakdownDetails.get(0).getIsbdAmount());
            }
            return BaseResponse.ok(res);
        } else if ("02".equalsIgnoreCase(type)) {
            if (ValidateUtil.isEmpty(estimateBreakdownDetails)) {
                res.put("budgetAmount", BigDecimal.ZERO);
            } else {
                res.put("budgetAmount", estimateBreakdownDetails.get(0).getIsbdAmount());
            }
            return BaseResponse.ok(res);
        } else if ("03".equalsIgnoreCase(type)) {
            if (ValidateUtil.isEmpty(estimateBreakdownDetails)) {
                res.put("estimateAmount", BigDecimal.ZERO);
            } else {
                res.put("estimateAmount", estimateBreakdownDetails.get(0).getIsbdAmount());
            }
            return BaseResponse.ok(res);
        }
        res.put("estimateAmount", BigDecimal.ZERO);
        return BaseResponse.ok(res);
    }

    /**
     * 导出招标计划明细Excel
     *
     * @param bpId
     * @return
     */
    @Override
    public Workbook getSecBiddingPlanDetailExportExcel(String bpId) {
        // 创建excel
        Workbook workbook = new XSSFWorkbook();
        if (ValidateUtil.isEmpty(bpId)) {
            return workbook;
        }
        // 查询数据
        BaseResponse secBiddingPlanById = this.getSecBiddingPlanById(bpId);
        if (Boolean.FALSE.equals(secBiddingPlanById.getSuccess())) {
            return workbook;
        }
        SecBiddingPlanResponse biddingPlan = JSONUtil.toBean(JSONUtil.toJsonStr(secBiddingPlanById.getData()), SecBiddingPlanResponse.class);
        if (ValidateUtil.isNull(biddingPlan)) {
            return workbook;
        }
        // 项目部分
        List<SecProjectResponse> secProjects = biddingPlan.getSecProjects();
        if (ValidateUtil.isEmpty(secProjects)) {
            return workbook;
        }
        String pId = secProjects.get(0).getpId();

        String planStatus = null;
        if ("1".equals(biddingPlan.getEnterEstimate())) {
            planStatus = "无需关联估算";
        } else if ("1".equals(biddingPlan.getReserveItems())) {
            planStatus = "储备";
        }
        boolean noEstimateBudget = ValidateUtil.isNotEmpty(planStatus);

        String type = biddingPlan.getBpPlanPhase();
        if (!noEstimateBudget && ValidateUtil.isEmpty(type)) {
            InvestmentEstimateBreakdown estimateBreakdown = investmentEstimateBreakdownService.getOne(Wrappers.<InvestmentEstimateBreakdown>lambdaQuery()
                    .eq(InvestmentEstimateBreakdown::getProjectId, pId)
                    .ne(InvestmentEstimateBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
                    .orderByDesc(InvestmentEstimateBreakdown::getCreateTime)
                    .last(" limit 1 ")
            );
            InvestmentBudgetBreakdown budgetEstimates = investmentBudgetBreakdownService.getOne(Wrappers.<InvestmentBudgetBreakdown>lambdaQuery()
                    .eq(InvestmentBudgetBreakdown::getProjectId, pId)
                    .ne(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Invalid.getIndex())
                    .orderByDesc(InvestmentBudgetBreakdown::getCreateTime)
                    .last(" limit 1 ")
            );
            type = this.judgmentSecBiddingPlanType(estimateBreakdown, budgetEstimates);
        }
        if (!noEstimateBudget && "01".equalsIgnoreCase(type)) {
            return workbook;
        }
        List<InvestmentEstimateBreakdownDetailsModel> modelDe;
        List<InvestmentBudgetBreakdownDetailsModel> modelDb;
        boolean estimate = !noEstimateBudget && ("02".equalsIgnoreCase(type) || "03".equalsIgnoreCase(type));
        boolean budget = !noEstimateBudget && "04".equalsIgnoreCase(type);
        if (noEstimateBudget) {
            modelDe = null;
            modelDb = null;
        } else {
            BaseResponse subjectListByPId = this.getSecBiddingPlanSubjectListByPId(pId, type, null, null);
            if (!subjectListByPId.getSuccess()) {
                return workbook;
            }
            if (estimate) {
                modelDb = null;
                modelDe = JSONUtil.toList(JSONUtil.parseArray(JSONUtil.toJsonStr(subjectListByPId.getData())), InvestmentEstimateBreakdownDetailsModel.class);
            } else {
                modelDe = null;
                if (budget) {
                    modelDb = JSONUtil.toList(JSONUtil.parseArray(JSONUtil.toJsonStr(subjectListByPId.getData())), InvestmentBudgetBreakdownDetailsModel.class);
                } else {
                    modelDb = null;
                }
            }
        }

        // 要渲染的招采计划部分
        List<SecInfoResponse> secInfos = biddingPlan.getSecInfos();
        List<SecInfoResponse> contractPlanning = new ArrayList<>();
        Map<String, InvestmentEstimateBreakdownDetailsModel> estimateIdMap = estimate
                ? buildEstimateIdMap(modelDe)
                : Collections.emptyMap();
        Map<String, InvestmentBudgetBreakdownDetailsModel> budgetIdMap = budget
                ? buildBudgetIdMap(modelDb)
                : Collections.emptyMap();

        if (ValidateUtil.isNotEmpty(secInfos)) {
            com.alibaba.fastjson2.JSONArray jpMsoftBdlxNew = DictionaryUtils.dictList("JPMsoft_BDLX_NEW");
            com.alibaba.fastjson2.JSONArray jpMsoftZbfs = DictionaryUtils.dictList("JPMsoft_ZBFS");
            com.alibaba.fastjson2.JSONArray jpMsoftZbglzbfl = DictionaryUtils.dictList("JPMsoft_ZBGLZBFL");
            secInfos.forEach(item -> {
                SecInfoResponse secInfo = new SecInfoResponse();
                // 序号
                secInfo.setSecOrderNumber(item.getSecOrderNumber());
                // 标段名称
                secInfo.setSecName(item.getSecName());
                // 标段类型 JPMsoft_BDLX_NEW
                secInfo.setSecType(DictionaryUtils.processDictList(jpMsoftBdlxNew, item.getSecType()));
                // 招标人
                secInfo.setTendereeName(ValidateUtil.isNotEmpty(item.getTendereeName()) ? item.getTendereeName() : item.getTenderName());
                // 估算科目
                String estimateSubject = noEstimateBudget ? "/" : buildEstimateSubject(item, estimate, estimateIdMap, budgetIdMap);
                secInfo.setSecEstimateSubject(estimateSubject);
                // 估算金额（万元）
                secInfo.setSecMoney(toWanAmount(item.getSecMoney()));
                // 标段分类 JPMsoft_ZBGLZBFL
                String bdfl = DictionaryUtils.processDictList(jpMsoftZbglzbfl, item.getSecSectionClassify());
                secInfo.setSecSectionClassify(ValidateUtil.isNotEmpty(bdfl) ? bdfl : "/");
                // 招标方式 JPMsoft_ZBFS
                String zbfs = DictionaryUtils.processDictList(jpMsoftZbfs, item.getSecBiddingType());
                secInfo.setSecBiddingType(ValidateUtil.isNotEmpty(zbfs) ? zbfs : "/");
                // 标段范围及实施内容
                secInfo.setSecPlace(item.getSecPlace());
                // 备注
                secInfo.setSecRemark(item.getSecRemark());
                contractPlanning.add(secInfo);
            });
        }

        // 创建一个工作表
        Sheet sheet = workbook.createSheet("招标计划明细");
        // 设置表头1
        String amountStr;
        if ("03".equalsIgnoreCase(type)) {
            amountStr = "估（概）算金额（万元）";
        } else if ("04".equalsIgnoreCase(type)) {
            amountStr = "概算金额（万元）";
        } else {
            amountStr = "估算金额（万元）";
        }
        String subjectStr;
        if ("03".equalsIgnoreCase(type)) {
            subjectStr = "估（概）算科目";
        } else if ("04".equalsIgnoreCase(type)) {
            subjectStr = "概算科目";
        } else {
            subjectStr = "估算科目";
        }
        String amountHeaderStr = amountStr.replace("（万元）", "\n（万元）");
        String[] rowTitle1 = new String[]{"序号", "标段名称", "标段类型", "招标人", subjectStr, amountHeaderStr, "标段分类", "招标方式", "标段范围及实施内容", "备注"};
        // 前三行的固定内容
        int rowNum = 0;
        // 标题
        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("招标计划信息");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, rowTitle1.length - 1));
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

        // 项目名称
        Row projectNameRow = sheet.createRow(rowNum++);
        Cell projectNameCell = projectNameRow.createCell(0);
        projectNameCell.setCellValue("项目名称：" + secProjects.get(0).getPName());
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, rowTitle1.length - 1));
        CellStyle projectStyle = workbook.createCellStyle();
        projectStyle.setAlignment(HorizontalAlignment.LEFT);
        projectStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        projectNameCell.setCellStyle(projectStyle);

        // 金额
        Row amountRow = sheet.createRow(rowNum++);
        Cell amountCell = amountRow.createCell(0);
        String amountTitle;
        if (noEstimateBudget) {
            amountTitle = "计划状态：" + planStatus;
        } else {
            amountTitle = amountStr + "：";
            if (estimate && ValidateUtil.isNotEmpty(modelDe)) {
                amountTitle += toWanAmount(modelDe.get(0).getIsbdAmount()).toPlainString();
            } else if (budget && ValidateUtil.isNotEmpty(modelDb)) {
                amountTitle += toWanAmount(modelDb.get(0).getIsbdAmount()).toPlainString();
            }
        }
        amountCell.setCellValue(amountTitle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, rowTitle1.length - 1));
        CellStyle amountStyle = workbook.createCellStyle();
        amountStyle.setAlignment(HorizontalAlignment.LEFT);
        amountStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        amountCell.setCellStyle(amountStyle);

        // 空行
        sheet.createRow(rowNum++);

        Row titleRow1 = sheet.createRow(rowNum++);
        Cell titleCell1 = titleRow1.createCell(0);
        titleCell1.setCellValue("一、招采计划");
        sheet.addMergedRegion(new CellRangeAddress(
                titleRow1.getRowNum(),
                titleRow1.getRowNum(),
                0,
                rowTitle1.length - 1
        ));
        CellStyle titleStyle1 = workbook.createCellStyle();
        Font titleFont1 = workbook.createFont();
        titleFont1.setFontHeightInPoints((short) 14);
        titleStyle1.setFont(titleFont1);
        titleStyle1.setAlignment(HorizontalAlignment.CENTER);
        titleStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        titleCell1.setCellStyle(titleStyle1);

        // 一、招采计划
        // 宽列的索引
        int[] columnWidths = noEstimateBudget
                ? new int[]{2000, 6000, 3500, 6000, 3000, 3000, 2500, 5000, 13000, 2000}
                : new int[]{2000, 6000, 3500, 6000, 8000, 3000, 2500, 5000, 13000, 2000};
        for (int i = 0; i < rowTitle1.length; i++) {
            sheet.setColumnWidth(i, i < columnWidths.length ? columnWidths[i] : 4000);
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setWrapText(true);

        // 金额专用样式
        CellStyle amountStyleCell = workbook.createCellStyle();
        amountStyleCell.setAlignment(HorizontalAlignment.CENTER);
        amountStyleCell.setVerticalAlignment(VerticalAlignment.CENTER);
        amountStyleCell.setBorderTop(BorderStyle.MEDIUM);
        amountStyleCell.setBorderBottom(BorderStyle.MEDIUM);
        amountStyleCell.setBorderLeft(BorderStyle.MEDIUM);
        amountStyleCell.setBorderRight(BorderStyle.MEDIUM);
        DataFormat dataFormat = workbook.createDataFormat();
        // 保留两位小数
        amountStyleCell.setDataFormat(dataFormat.getFormat("#,##0.00"));

        // 备注列样式
        CellStyle remarkStyle = workbook.createCellStyle();
        remarkStyle.setAlignment(HorizontalAlignment.LEFT);
        remarkStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        remarkStyle.setBorderTop(BorderStyle.MEDIUM);
        remarkStyle.setBorderBottom(BorderStyle.MEDIUM);
        remarkStyle.setBorderLeft(BorderStyle.MEDIUM);
        remarkStyle.setBorderRight(BorderStyle.MEDIUM);
        remarkStyle.setWrapText(true);

        CellStyle totalSubjectExtraStyle = workbook.createCellStyle();
        totalSubjectExtraStyle.cloneStyleFrom(style);
        totalSubjectExtraStyle.setAlignment(HorizontalAlignment.LEFT);

        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle1.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle1[i]);
            cell.setCellStyle(style);
        }
        row.setHeightInPoints(sheet.getDefaultRowHeightInPoints() * 2);

        // 排序
        contractPlanning.sort((a, b) -> compareSecOrderNumber(a.getSecOrderNumber(), b.getSecOrderNumber()));

        Set<String> allOrderNos = contractPlanning.stream()
                .map(SecInfoResponse::getSecOrderNumber)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet());

        BigDecimal totalAmount = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(contractPlanning)) {
            Set<String> parentOrderNos = new HashSet<>();
            for (String no : allOrderNos) {
                if (ValidateUtil.isEmpty(no)) {
                    continue;
                }
                int idx = no.lastIndexOf('.');
                if (idx > 0) {
                    parentOrderNos.add(no.substring(0, idx));
                }
            }
            for (SecInfoResponse data : contractPlanning) {
                row = sheet.createRow(rowNum++);
                int index = 0;

                // 序号
                cell = row.createCell(index++);
                cell.setCellValue(data.getSecOrderNumber());
                cell.setCellStyle(style);

                // 标段名称
                cell = row.createCell(index++);
                cell.setCellValue(data.getSecName());
                cell.setCellStyle(style);

                // 标段类型
                cell = row.createCell(index++);
                cell.setCellValue(data.getSecType());
                cell.setCellStyle(style);

                // 招标人
                cell = row.createCell(index++);
                cell.setCellValue(data.getTendereeName());
                cell.setCellStyle(style);

                // 估算科目
                String secEstimateSubject = data.getSecEstimateSubject();
                cell = row.createCell(index++);
                cell.setCellValue(secEstimateSubject);
                cell.setCellStyle(style);
                // 估算金额（万元）
                cell = row.createCell(index++);
                cell.setCellValue(safeAmount(data.getSecMoney()).doubleValue());
                cell.setCellStyle(amountStyleCell);
                String curNo = data.getSecOrderNumber();
                if (!parentOrderNos.contains(curNo)) {
                    totalAmount = totalAmount.add(safeAmount(data.getSecMoney()));
                }

                // 标段分类
                cell = row.createCell(index++);
                cell.setCellValue(data.getSecSectionClassify());
                cell.setCellStyle(style);

                // 招标方式
                cell = row.createCell(index++);
                cell.setCellValue(data.getSecBiddingType());
                cell.setCellStyle(style);

                // 标段范围及实施内容
                cell = row.createCell(index++);
                cell.setCellValue(data.getSecPlace());
                cell.setCellStyle(remarkStyle);

                // 备注
                cell = row.createCell(index++);
                cell.setCellValue(data.getSecRemark());
                cell.setCellStyle(remarkStyle);

                adjustRowHeightForWrappedText(sheet, row,
                        estimateWrappedLines(sheet, data.getSecName(), 1, 1),
                        estimateWrappedLines(sheet, secEstimateSubject, 4, 4),
                        estimateWrappedLines(sheet, data.getSecPlace(), 8, 8),
                        estimateWrappedLines(sheet, data.getSecRemark(), 9, 9));
            }
        }

        // 合计1
        Row totalRow = sheet.createRow(rowNum++);
        for (int i = 0; i < rowTitle1.length; i++) {
            Cell totalRowCell = totalRow.createCell(i);
            if (i == 0) {
                totalRowCell.setCellValue("小计");
                totalRowCell.setCellStyle(style);
            } else if (i == 5) {
                totalRowCell.setCellValue(totalAmount.setScale(3, RoundingMode.HALF_UP).doubleValue());
                totalRowCell.setCellStyle(amountStyleCell);
            } else {
                totalRowCell.setCellStyle(style);
            }
        }
        if (noEstimateBudget) {
            appendApprovalRecordRows(sheet, rowNum, bpId, titleStyle1, style, remarkStyle);
            return workbook;
        }
        // 空行
        sheet.createRow(rowNum++);

        List<SecInfoSubjectResponse> allSecInfoSubjects = collectAllSecInfoSubjects(secInfos);

        final int nameStartCol = 1;
        final int nameEndCol = 4;
        final int amountCol = 5;
        final int remarkCol = 6;
        final int lastCol = remarkCol + 3;

        Row subjectTitleRow = sheet.createRow(rowNum++);
        Cell subjectTitleCell = subjectTitleRow.createCell(0);
        subjectTitleCell.setCellValue("二、合同外工程及费用");
        subjectTitleCell.setCellStyle(titleStyle1);

        mergeRegionAndFill(sheet, subjectTitleRow, 0, lastCol, titleStyle);

        Row subjectHeaderRow = sheet.createRow(rowNum++);

        Cell h0 = subjectHeaderRow.createCell(0);
        h0.setCellValue("序号");
        h0.setCellStyle(style);

        Cell h1 = subjectHeaderRow.createCell(nameStartCol);
        h1.setCellValue(subjectStr);
        h1.setCellStyle(style);
        mergeRegionAndFill(sheet, subjectHeaderRow, nameStartCol, nameEndCol, style);

        Cell h5 = subjectHeaderRow.createCell(amountCol);
        h5.setCellValue(amountHeaderStr);
        h5.setCellStyle(style);
        subjectHeaderRow.setHeightInPoints(sheet.getDefaultRowHeightInPoints() * 2);

        Function<BigDecimal, BigDecimal> toWan = this::toWanAmount;

        BigDecimal subjectExtraAmount = secInfoSubjectExtraService.list(
                        Wrappers.<SecInfoSubjectExtra>lambdaQuery().eq(SecInfoSubjectExtra::getBpId, bpId)
                ).stream()
                .map(SecInfoSubjectExtra::getSeMoney)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String subjectExtraAmountText = "估(概)算科目外金额 " + toWan.apply(subjectExtraAmount).toPlainString() + "万元。";

        BigDecimal subTotal = BigDecimal.ZERO;

        Cell h6 = subjectHeaderRow.createCell(remarkCol);
        h6.setCellValue("备注");
        h6.setCellStyle(style);
        mergeRegionAndFill(sheet, subjectHeaderRow, remarkCol, lastCol, style);

        if (estimate && ValidateUtil.isNotEmpty(modelDe)) {
            Map<String, List<String>> estimateChildrenIndex = buildEstimateChildrenIndex(modelDe);

            // 顶层
            List<InvestmentEstimateBreakdownDetailsModel> topNodes = modelDe.stream()
                    .filter(n -> n != null
                            && isTopLevel(n.getLevelCode())
                            && ValidateUtil.isEmpty(n.getParentId()))
                    .sorted(Comparator.comparingInt(n -> Convert.chineseToNumber(n.getLevelCode())))
                    .collect(Collectors.toList());

            // 备注
            List<SecInfoSubjectRemark> remarkList = secInfoSubjectRemarkService.list(Wrappers.<SecInfoSubjectRemark>lambdaQuery().eq(SecInfoSubjectRemark::getMainId, bpId));
            // 根据估算id创建map
            Map<String, SecInfoSubjectRemark> remarkMap = remarkList.stream().filter(r -> ValidateUtil.isNotEmpty(r.getSubIebId())).collect(Collectors.toMap(SecInfoSubjectRemark::getSubIebId, r -> r));

            Map<String, BigDecimal> secInfoSubjectMap = allSecInfoSubjects.stream()
                    .filter(s -> ValidateUtil.isNotEmpty(s.getSubIebId()))
                    .collect(Collectors.toMap(
                            SecInfoSubjectResponse::getSubIebId,
                            s -> s.getSubMoney() == null ? BigDecimal.ZERO : s.getSubMoney(),
                            BigDecimal::add
                    ));

            for (InvestmentEstimateBreakdownDetailsModel top : topNodes) {
                BigDecimal topDisplay = sumLeafAvailableEstimate(
                        top.getId(), estimateIdMap, estimateChildrenIndex, secInfoSubjectMap);

                if (topDisplay.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }

                Row rTop = sheet.createRow(rowNum++);
                writeSubjectDetailRow(sheet, rTop, top.getLevelCode(), top.getIsbdFeeTypeName(), topDisplay,
                        getSubjectRemarkText(top.getId(), remarkMap), style, amountStyleCell, remarkStyle, toWan);

                subTotal = subTotal.add(topDisplay);

                // 子节点
                rowNum = writeEstimateChildrenRows(
                        sheet, rowNum,
                        top.getId(),
                        estimateIdMap, estimateChildrenIndex,
                        secInfoSubjectMap,
                        remarkMap,
                        style, amountStyleCell, remarkStyle, toWan
                );
            }

            // 小计行
            Row subRow = sheet.createRow(rowNum++);
            Cell s0 = subRow.createCell(0);
            s0.setCellValue("小计");
            s0.setCellStyle(style);

            Cell sName = subRow.createCell(nameStartCol);
            sName.setCellStyle(style);
            mergeRegionAndFill(sheet, subRow, nameStartCol, nameEndCol, style);

            Cell sAmt = subRow.createCell(amountCol);
            sAmt.setCellValue(toWan.apply(subTotal).doubleValue());
            sAmt.setCellStyle(amountStyleCell);

            Cell sRemark = subRow.createCell(remarkCol);
            sRemark.setCellStyle(style);
            mergeRegionAndFill(sheet, subRow, remarkCol, lastCol, style);

            // 合计行
            BigDecimal totalWan = safeAmount(modelDe.get(0).getIsbdAmount()).add(subjectExtraAmount)
                    .divide(TEN_THOUSAND, 2, RoundingMode.HALF_UP);

            Row totalRow2 = sheet.createRow(rowNum++);
            Cell t0 = totalRow2.createCell(0);
            t0.setCellValue("合计");
            t0.setCellStyle(style);

            Cell tName = totalRow2.createCell(nameStartCol);
            tName.setCellStyle(style);
            mergeRegionAndFill(sheet, totalRow2, nameStartCol, nameEndCol, style);

            Cell tAmt = totalRow2.createCell(amountCol);
            tAmt.setCellValue(totalWan.doubleValue());
            tAmt.setCellStyle(amountStyleCell);

            Cell tRemark = totalRow2.createCell(remarkCol);
            tRemark.setCellValue(subjectExtraAmountText);
            tRemark.setCellStyle(totalSubjectExtraStyle);
            mergeRegionAndFill(sheet, totalRow2, remarkCol, lastCol, style);
        } else if (budget && ValidateUtil.isNotEmpty(modelDb)) {
            Map<String, List<String>> budgetChildrenIndex = buildBudgetChildrenIndex(modelDb);

            List<InvestmentBudgetBreakdownDetailsModel> topNodes = modelDb.stream()
                    .filter(n -> n != null
                            && isTopLevel(n.getLevelCode())
                            && ValidateUtil.isEmpty(n.getParentId()))
                    .sorted(Comparator.comparingInt(n -> Convert.chineseToNumber(n.getLevelCode())))
                    .collect(Collectors.toList());

            // 备注
            List<SecInfoSubjectRemark> remarkList = secInfoSubjectRemarkService.list(Wrappers.<SecInfoSubjectRemark>lambdaQuery().eq(SecInfoSubjectRemark::getMainId, bpId));
            // 根据概算id创建map
            Map<String, SecInfoSubjectRemark> remarkMap = remarkList.stream().filter(r -> ValidateUtil.isNotEmpty(r.getSubIebId())).collect(Collectors.toMap(SecInfoSubjectRemark::getSubIebId, r -> r));

            Map<String, BigDecimal> secInfoSubjectMap = allSecInfoSubjects.stream()
                    .filter(s -> ValidateUtil.isNotEmpty(s.getSubIbbId()))
                    .collect(Collectors.toMap(
                            SecInfoSubjectResponse::getSubIbbId,
                            s -> s.getSubMoney() == null ? BigDecimal.ZERO : s.getSubMoney(),
                            BigDecimal::add
                    ));

            for (InvestmentBudgetBreakdownDetailsModel top : topNodes) {
                BigDecimal topDisplay = sumLeafAvailableBudget(
                        top.getId(), budgetIdMap, budgetChildrenIndex, secInfoSubjectMap);

                if (topDisplay.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }

                Row rTop = sheet.createRow(rowNum++);
                writeSubjectDetailRow(sheet, rTop, top.getLevelCode(), top.getIsbdFeeTypeName(), topDisplay,
                        getSubjectRemarkText(top.getId(), remarkMap), style, amountStyleCell, remarkStyle, toWan);

                subTotal = subTotal.add(topDisplay);

                // 子节点
                rowNum = writeBudgetChildrenRows(
                        sheet, rowNum,
                        top.getId(),
                        budgetIdMap, budgetChildrenIndex,
                        secInfoSubjectMap,
                        remarkMap,
                        style, amountStyleCell, remarkStyle, toWan
                );
            }

            // 小计行
            Row subRow = sheet.createRow(rowNum++);
            Cell s0 = subRow.createCell(0);
            s0.setCellValue("小计");
            s0.setCellStyle(style);

            Cell sName = subRow.createCell(nameStartCol);
            sName.setCellStyle(style);
            mergeRegionAndFill(sheet, subRow, nameStartCol, nameEndCol, style);

            Cell sAmt = subRow.createCell(amountCol);
            sAmt.setCellValue(toWan.apply(subTotal).doubleValue());
            sAmt.setCellStyle(amountStyleCell);

            Cell sRemark = subRow.createCell(remarkCol);
            sRemark.setCellStyle(style);
            mergeRegionAndFill(sheet, subRow, remarkCol, lastCol, style);

            // 合计行
            BigDecimal totalWan = safeAmount(modelDb.get(0).getIsbdAmount()).add(subjectExtraAmount)
                    .divide(TEN_THOUSAND, 2, RoundingMode.HALF_UP);

            Row totalRow3 = sheet.createRow(rowNum++);
            Cell t0 = totalRow3.createCell(0);
            t0.setCellValue("合计");
            t0.setCellStyle(style);

            Cell tName = totalRow3.createCell(nameStartCol);
            tName.setCellStyle(style);
            mergeRegionAndFill(sheet, totalRow3, nameStartCol, nameEndCol, style);

            Cell tAmt = totalRow3.createCell(amountCol);
            tAmt.setCellValue(totalWan.doubleValue());
            tAmt.setCellStyle(amountStyleCell);

            Cell tRemark = totalRow3.createCell(remarkCol);
            tRemark.setCellValue(subjectExtraAmountText);
            tRemark.setCellStyle(totalSubjectExtraStyle);
            mergeRegionAndFill(sheet, totalRow3, remarkCol, lastCol, style);
        }
        appendApprovalRecordRows(sheet, rowNum, bpId, titleStyle1, style, remarkStyle);
        return workbook;
    }

    private void appendApprovalRecordRows(Sheet sheet, int rowNum, String bpId, CellStyle titleStyle, CellStyle style, CellStyle remarkStyle) {
        final int lastCol = 9;
        rowNum++;

        Row approvalTitleRow = sheet.createRow(rowNum++);
        Cell approvalTitleCell = approvalTitleRow.createCell(0);
        approvalTitleCell.setCellValue("审批记录");
        approvalTitleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(approvalTitleRow.getRowNum(), approvalTitleRow.getRowNum(), 0, lastCol));
        for (int c = 1; c <= lastCol; c++) {
            approvalTitleRow.createCell(c).setCellStyle(titleStyle);
        }

        Row headerRow = sheet.createRow(rowNum++);
        setStyledCell(headerRow, 0, "序号", style);
        setStyledCell(headerRow, 1, "流程步骤", style);
        setStyledCell(headerRow, 2, "", style);
        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), 1, 2));
        setStyledCell(headerRow, 3, "审批人", style);
        setStyledCell(headerRow, 4, "审批时间", style);
        setStyledCell(headerRow, 5, "审批结果", style);
        setStyledCell(headerRow, 6, "审批意见", style);
        for (int c = 7; c <= lastCol; c++) {
            setStyledCell(headerRow, c, "", style);
        }
        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), 6, lastCol));

        List<TaskProcessin> taskProcessList = taskProcessinMapper.getTaskProcessListByEntityId(bpId);
        if (ValidateUtil.isEmpty(taskProcessList)) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int index = 1;
        for (TaskProcessin taskProcessin : taskProcessList) {
            Row dataRow = sheet.createRow(rowNum++);
            setStyledCell(dataRow, 0, String.valueOf(index++), style);
            setStyledCell(dataRow, 1, nullToEmpty(taskProcessin.getActname()), style);
            setStyledCell(dataRow, 2, "", style);
            sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(), dataRow.getRowNum(), 1, 2));
            setStyledCell(dataRow, 3, nullToEmpty(taskProcessin.getUName()), style);
            setStyledCell(dataRow, 4, taskProcessin.getTpDotime() == null ? "" : sdf.format(taskProcessin.getTpDotime()), style);
            setStyledCell(dataRow, 5, nullToEmpty(taskProcessin.getTpResult()), style);
            setStyledCell(dataRow, 6, nullToEmpty(taskProcessin.getTpExplain()), remarkStyle);
            for (int c = 7; c <= lastCol; c++) {
                setStyledCell(dataRow, c, "", remarkStyle);
            }
            sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(), dataRow.getRowNum(), 6, lastCol));
            adjustRowHeightForWrappedText(sheet, dataRow,
                    estimateWrappedLines(sheet, taskProcessin.getActname(), 1, 2),
                    estimateWrappedLines(sheet, taskProcessin.getTpExplain(), 6, lastCol));
        }
    }

    private void setStyledCell(Row row, int columnIndex, String value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value == null ? "" : value);
        cell.setCellStyle(style);
    }

    private void mergeRegionAndFill(Sheet sheet, Row row, int firstCol, int lastCol, CellStyle fillStyle) {
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), firstCol, lastCol));
        for (int c = firstCol + 1; c <= lastCol; c++) {
            row.createCell(c).setCellStyle(fillStyle);
        }
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private BigDecimal safeAmount(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private BigDecimal toWanAmount(BigDecimal value) {
        return safeAmount(value).divide(TEN_THOUSAND, 2, RoundingMode.HALF_UP);
    }

    private int compareSecOrderNumber(String left, String right) {
        if (ValidateUtil.isEmpty(left) && ValidateUtil.isEmpty(right)) {
            return 0;
        }
        if (ValidateUtil.isEmpty(left)) {
            return 1;
        }
        if (ValidateUtil.isEmpty(right)) {
            return -1;
        }

        String[] leftParts = left.split("\\.");
        String[] rightParts = right.split("\\.");
        int len = Math.max(leftParts.length, rightParts.length);
        for (int i = 0; i < len; i++) {
            int result = compareOrderPart(
                    i < leftParts.length ? leftParts[i] : "0",
                    i < rightParts.length ? rightParts[i] : "0"
            );
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private int compareOrderPart(String left, String right) {
        try {
            return Integer.compare(Integer.parseInt(left), Integer.parseInt(right));
        } catch (NumberFormatException ignored) {
            return nullToEmpty(left).compareTo(nullToEmpty(right));
        }
    }

    private void writeSubjectDetailRow(Sheet sheet,
                                       Row row,
                                       Object levelCode,
                                       String name,
                                       BigDecimal amount,
                                       String remarkText,
                                       CellStyle style,
                                       CellStyle amountStyleCell,
                                       CellStyle remarkStyle,
                                       Function<BigDecimal, BigDecimal> toWan) {
        final int nameStartCol = 1;
        final int nameEndCol = 4;
        final int amountCol = 5;
        final int remarkCol = 6;
        final int lastCol = remarkCol + 3;

        Cell c0 = row.createCell(0);
        c0.setCellValue(levelCode == null ? "" : String.valueOf(levelCode));
        c0.setCellStyle(style);

        Cell cName = row.createCell(nameStartCol);
        cName.setCellValue(nullToEmpty(name));
        cName.setCellStyle(style);
        mergeRegionAndFill(sheet, row, nameStartCol, nameEndCol, style);

        Cell cAmt = row.createCell(amountCol);
        cAmt.setCellValue(toWan.apply(amount).doubleValue());
        cAmt.setCellStyle(amountStyleCell);

        Cell cRemark = row.createCell(remarkCol);
        cRemark.setCellValue(nullToEmpty(remarkText));
        cRemark.setCellStyle(remarkStyle);
        mergeRegionAndFill(sheet, row, remarkCol, lastCol, remarkStyle);
        adjustRowHeightForWrappedText(sheet, row,
                estimateWrappedLines(sheet, name, nameStartCol, nameEndCol),
                estimateWrappedLines(sheet, remarkText, remarkCol, lastCol));
    }

    private String getSubjectRemarkText(String subjectId, Map<String, SecInfoSubjectRemark> remarkMap) {
        if (ValidateUtil.isEmpty(subjectId) || CollectionUtils.isEmpty(remarkMap)) {
            return "";
        }
        SecInfoSubjectRemark remark = remarkMap.get(subjectId);
        return remark != null && StrUtil.isNotBlank(remark.getRemark()) ? remark.getRemark() : "";
    }

    /**
     * 投资估算分解明细
     */
    private Map<String, InvestmentEstimateBreakdownDetailsModel> buildEstimateIdMap(List<InvestmentEstimateBreakdownDetailsModel> list) {

        Map<String, InvestmentEstimateBreakdownDetailsModel> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }

        for (InvestmentEstimateBreakdownDetailsModel node : list) {
            if (node == null || node.getId() == null) {
                continue;
            }

            map.put(node.getId(), node);

            // 递归处理子节点
            if (node.getChildNodes() != null && !node.getChildNodes().isEmpty()) {
                map.putAll(buildEstimateIdMap(node.getChildNodes()));
            }
        }

        return map;
    }

    /**
     * 投资概算分解明细
     */
    private Map<String, InvestmentBudgetBreakdownDetailsModel> buildBudgetIdMap(List<InvestmentBudgetBreakdownDetailsModel> list) {
        Map<String, InvestmentBudgetBreakdownDetailsModel> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }

        for (InvestmentBudgetBreakdownDetailsModel node : list) {
            if (node == null || node.getId() == null) {
                continue;
            }

            map.put(node.getId(), node);

            // 优先 childNodes，其次 children
            if (node.getChildNodes() != null && !node.getChildNodes().isEmpty()) {
                map.putAll(buildBudgetIdMap(node.getChildNodes()));
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                map.putAll(buildBudgetIdMap(node.getChildren()));
            }
        }

        return map;
    }

    /**
     * 构建“估算科目/概算科目”展示文本
     */
    private String buildEstimateSubject(SecInfoResponse secInfo,
                                        boolean estimate,
                                        Map<String, InvestmentEstimateBreakdownDetailsModel> estimateIdMap,
                                        Map<String, InvestmentBudgetBreakdownDetailsModel> budgetIdMap
    ) {
        if (secInfo == null || secInfo.getSecInfoSubjects() == null || secInfo.getSecInfoSubjects().isEmpty()) {
            return "详见子集";
        }

        final Map<String, Object> idMap = new HashMap<>();
        if (estimate) {
            idMap.putAll(estimateIdMap);
        } else {
            idMap.putAll(budgetIdMap);
        }

        Function<Object, String> getId = (o) -> {
            if (o instanceof InvestmentEstimateBreakdownDetailsModel) {
                return ((InvestmentEstimateBreakdownDetailsModel) o).getId();
            }
            return ((InvestmentBudgetBreakdownDetailsModel) o).getId();
        };
        Function<Object, String> getParentId = (o) -> {
            if (o instanceof InvestmentEstimateBreakdownDetailsModel) {
                return ((InvestmentEstimateBreakdownDetailsModel) o).getParentId();
            }
            return ((InvestmentBudgetBreakdownDetailsModel) o).getParentId();
        };
        Function<Object, String> getLevelCode = (o) -> {
            if (o instanceof InvestmentEstimateBreakdownDetailsModel) {
                return ((InvestmentEstimateBreakdownDetailsModel) o).getLevelCode();
            }
            return ((InvestmentBudgetBreakdownDetailsModel) o).getLevelCode();
        };
        Function<Object, String> getName = (o) -> {
            if (o instanceof InvestmentEstimateBreakdownDetailsModel) {
                return ((InvestmentEstimateBreakdownDetailsModel) o).getIsbdFeeTypeName();
            }
            return ((InvestmentBudgetBreakdownDetailsModel) o).getIsbdFeeTypeName();
        };

        // 一级id -> 一级节点
        Map<String, Object> topMap = new LinkedHashMap<>();
        // 一级id -> (二级id -> 二级节点)
        Map<String, Map<String, Object>> topSecondMap = new LinkedHashMap<>();

        // 从叶子回溯到（一级、二级）
        for (SecInfoSubjectResponse s : secInfo.getSecInfoSubjects()) {
            String leafId = estimate ? s.getSubIebId() : s.getSubIbbId();
            if (leafId == null || leafId.isEmpty()) {
                continue;
            }

            Object leaf = idMap.get(leafId);
            if (leaf == null) {
                continue;
            }

            Object[] pair = resolveTopAndSecond(leaf, idMap, getParentId);
            Object top = pair[0];
            Object second = pair[1];

            if (top == null) {
                continue;
            }

            String topId = getId.apply(top);
            topMap.putIfAbsent(topId, top);
            topSecondMap.putIfAbsent(topId, new LinkedHashMap<>());

            if (second != null) {
                String secondId = getId.apply(second);
                topSecondMap.get(topId).putIfAbsent(secondId, second);
            }
        }

        if (topMap.isEmpty()) {
            return "详见子集";
        }

        // 一级/二级按 levelCode 数字顺序
        List<Object> tops = new ArrayList<>(topMap.values());
        tops.sort(Comparator.comparingInt(o -> safeCodeToInt(getLevelCode.apply(o))));

        // 拼接：一级和二级都按行展示
        List<String> resultParts = new ArrayList<>();

        for (Object top : tops) {
            String topId = getId.apply(top);

            StringBuilder sb = new StringBuilder();
            sb.append(formatTop(getLevelCode.apply(top), getName.apply(top)));

            Map<String, Object> secondsMap = topSecondMap.getOrDefault(topId, Collections.emptyMap());
            if (!secondsMap.isEmpty()) {
                List<Object> seconds = new ArrayList<>(secondsMap.values());
                seconds.sort(Comparator.comparingInt(o -> safeCodeToInt(getLevelCode.apply(o))));

                for (Object second : seconds) {
                    sb.append("\n").append(formatSecond(getLevelCode.apply(second), getName.apply(second)));
                }
            }

            resultParts.add(sb.toString());
        }

        return String.join("\n", resultParts);
    }

    private int estimateWrappedLines(Sheet sheet, String text, int firstCol, int lastCol) {
        if (ValidateUtil.isEmpty(text)) {
            return 1;
        }
        int columnWidth = 0;
        for (int i = firstCol; i <= lastCol; i++) {
            columnWidth += sheet.getColumnWidth(i);
        }
        int charsPerLine = Math.max(1, columnWidth / 256);
        int lines = 0;
        for (String part : text.split("\\R", -1)) {
            int textWidth = 0;
            for (int i = 0; i < part.length(); i++) {
                textWidth += part.charAt(i) < 128 ? 1 : 2;
            }
            lines += Math.max(1, (textWidth + charsPerLine - 1) / charsPerLine);
        }
        return lines;
    }

    private void adjustRowHeightForWrappedText(Sheet sheet, Row row, int... lineCounts) {
        int maxLines = 1;
        for (int lineCount : lineCounts) {
            maxLines = Math.max(maxLines, lineCount);
        }
        if (maxLines > 1) {
            row.setHeightInPoints(sheet.getDefaultRowHeightInPoints() * maxLines);
        }
    }

    private Object[] resolveTopAndSecond(Object leaf,
                                         Map<String, Object> idMap,
                                         java.util.function.Function<Object, String> getParentId) {
        Object curr = leaf;
        Object prev = null;

        while (curr != null) {
            String pid = getParentId.apply(curr);
            if (pid == null || pid.isEmpty()) {
                break;
            }

            Object parent = idMap.get(pid);
            if (parent == null) {
                break;
            }

            // 记录当前节点
            prev = curr;
            curr = parent;
        }

        // 最高可达节点
        Object top = curr;
        Object second = prev;

        if (top != null && second != null) {
            String secondPid = getParentId.apply(second);
            if (secondPid == null || secondPid.isEmpty()) {
                second = null;
            }
        }

        return new Object[]{top, second};
    }

    /**
     * 一级格式
     *
     * @param levelCode
     * @param name
     * @return
     */
    private String formatTop(String levelCode, String name) {
        levelCode = (levelCode == null ? "" : levelCode.trim());
        name = (name == null ? "" : name.trim());
        return levelCode + "、" + name;
    }

    /**
     * 二级格式
     *
     * @param levelCode
     * @param name
     * @return
     */
    private String formatSecond(String levelCode, String name) {
        levelCode = (levelCode == null ? "" : levelCode.trim());
        name = (name == null ? "" : name.trim());
        return levelCode + "." + name;
    }

    private int safeCodeToInt(String code) {
        if (code == null) {
            return Integer.MAX_VALUE;
        }
        String s = code.trim();
        if (s.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        try {
            // 仅取开头连续数字
            int i = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                i++;
            }
            if (i == 0) {
                return Integer.MAX_VALUE;
            }
            return Integer.parseInt(s.substring(0, i));
        } catch (Exception ignore) {
            return Integer.MAX_VALUE;
        }
    }

    private boolean isTopLevel(String levelCode) {
        if (ValidateUtil.isEmpty(levelCode)) {
            return false;
        }
        return levelCode.matches("^[一二三四五六七八九十]+$");
    }

    private Map<String, List<String>> buildEstimateChildrenIndex(List<InvestmentEstimateBreakdownDetailsModel> list) {
        Map<String, List<String>> map = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }

        for (InvestmentEstimateBreakdownDetailsModel node : list) {
            if (node == null || ValidateUtil.isEmpty(node.getParentId())) {
                continue;
            }
            map.computeIfAbsent(node.getParentId(), k -> new ArrayList<>())
                    .add(node.getId());
        }
        return map;
    }

    private Map<String, List<String>> buildBudgetChildrenIndex(List<InvestmentBudgetBreakdownDetailsModel> list) {
        Map<String, List<String>> map = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }

        for (InvestmentBudgetBreakdownDetailsModel node : list) {
            if (node == null || ValidateUtil.isEmpty(node.getParentId())) {
                continue;
            }
            map.computeIfAbsent(node.getParentId(), k -> new ArrayList<>())
                    .add(node.getId());
        }
        return map;
    }

    private Map<String, BigDecimal> buildCurrentUseMap(
            List<SecInfoSubjectResponse> subjects,
            boolean estimate) {

        Map<String, BigDecimal> map = new HashMap<>();
        if (CollectionUtils.isEmpty(subjects)) {
            return map;
        }

        for (SecInfoSubjectResponse s : subjects) {
            BigDecimal use = s.getSubMoney() == null
                    ? BigDecimal.ZERO
                    : s.getSubMoney();

            String subjectId = estimate
                    ? s.getSubIebId()
                    : s.getSubIbbId();

            if (ValidateUtil.isNotEmpty(subjectId)) {
                map.merge(subjectId, use, BigDecimal::add);
            }
        }
        return map;
    }

    private BigDecimal calcSelfAvailable(BigDecimal historyBalance) {
        BigDecimal available = (historyBalance == null ? BigDecimal.ZERO : historyBalance);

        return available.compareTo(BigDecimal.ZERO) > 0
                ? available
                : BigDecimal.ZERO;
    }

    private BigDecimal sumLeafAvailableEstimate(
            String rootId,
            Map<String, InvestmentEstimateBreakdownDetailsModel> idMap,
            Map<String, List<String>> childrenIndex,
            Map<String, BigDecimal> secInfoSubjectMap
    ) {
        List<String> children = childrenIndex.get(rootId);
        if (CollectionUtils.isEmpty(children)) {
            InvestmentEstimateBreakdownDetailsModel self = idMap.get(rootId);
            BigDecimal occupied = secInfoSubjectMap.getOrDefault(rootId, BigDecimal.ZERO);
            BigDecimal history = getHistoryAvailableForEstimate(self, occupied);
            return calcSelfAvailable(history);
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (String cid : children) {
            sum = sum.add(sumLeafAvailableEstimate(cid, idMap, childrenIndex, secInfoSubjectMap));
        }
        return sum;
    }

    private int writeEstimateChildrenRows(
            Sheet sheet,
            int rowNum,
            String parentId,
            Map<String, InvestmentEstimateBreakdownDetailsModel> idMap,
            Map<String, List<String>> childrenIndex,
            Map<String, BigDecimal> secInfoSubjectMap,
            Map<String, SecInfoSubjectRemark> remarkMap,
            CellStyle style,
            CellStyle amountStyleCell,
            CellStyle remarkStyle,
            Function<BigDecimal, BigDecimal> toWan
    ) {
        if (ValidateUtil.isEmpty(parentId) || idMap == null || childrenIndex == null) {
            return rowNum;
        }

        List<String> childIds = childrenIndex.get(parentId);
        if (ValidateUtil.isEmpty(childIds)) {
            return rowNum;
        }

        List<String> sorted = childIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparingInt(id -> {
                    InvestmentEstimateBreakdownDetailsModel m = idMap.get(id);
                    return m == null ? Integer.MAX_VALUE : levelCodeOrder(m.getLevelCode());
                }))
                .collect(Collectors.toList());

        for (String childId : sorted) {
            InvestmentEstimateBreakdownDetailsModel child = idMap.get(childId);
            if (child == null) {
                continue;
            }

            BigDecimal display = sumLeafAvailableEstimate(
                    child.getId(), idMap, childrenIndex, secInfoSubjectMap
            );

            if (display == null || display.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            Row r = sheet.createRow(rowNum++);
            writeSubjectDetailRow(sheet, r, child.getLevelCode(), child.getIsbdFeeTypeName(), display,
                    getSubjectRemarkText(child.getId(), remarkMap), style, amountStyleCell, remarkStyle, toWan);


            rowNum = writeEstimateChildrenRows(
                    sheet, rowNum,
                    child.getId(),
                    idMap, childrenIndex,
                    secInfoSubjectMap,
                    remarkMap,
                    style, amountStyleCell, remarkStyle, toWan
            );
        }

        return rowNum;
    }

    private int writeBudgetChildrenRows(
            Sheet sheet,
            int rowNum,
            String parentId,
            Map<String, InvestmentBudgetBreakdownDetailsModel> idMap,
            Map<String, List<String>> childrenIndex,
            Map<String, BigDecimal> secInfoSubjectMap,
            Map<String, SecInfoSubjectRemark> remarkMap,
            CellStyle style,
            CellStyle amountStyleCell,
            CellStyle remarkStyle,
            Function<BigDecimal, BigDecimal> toWan
    ) {
        if (ValidateUtil.isEmpty(parentId) || idMap == null || childrenIndex == null) {
            return rowNum;
        }

        List<String> childIds = childrenIndex.get(parentId);
        if (ValidateUtil.isEmpty(childIds)) {
            return rowNum;
        }

        List<String> sorted = childIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparingInt(id -> {
                    InvestmentBudgetBreakdownDetailsModel m = idMap.get(id);
                    return m == null ? Integer.MAX_VALUE : levelCodeOrder(m.getLevelCode());
                }))
                .collect(Collectors.toList());

        for (String childId : sorted) {
            InvestmentBudgetBreakdownDetailsModel child = idMap.get(childId);
            if (child == null) {
                continue;
            }

            BigDecimal display = sumLeafAvailableBudget(
                    child.getId(), idMap, childrenIndex, secInfoSubjectMap
            );

            if (display == null || display.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            Row r = sheet.createRow(rowNum++);
            writeSubjectDetailRow(sheet, r, child.getLevelCode(), child.getIsbdFeeTypeName(), display,
                    getSubjectRemarkText(child.getId(), remarkMap), style, amountStyleCell, remarkStyle, toWan);


            rowNum = writeBudgetChildrenRows(
                    sheet, rowNum,
                    child.getId(),
                    idMap, childrenIndex,
                    secInfoSubjectMap,
                    remarkMap,
                    style, amountStyleCell, remarkStyle, toWan
            );
        }
        return rowNum;
    }

    private BigDecimal sumLeafAvailableBudget(
            String rootId,
            Map<String, InvestmentBudgetBreakdownDetailsModel> idMap,
            Map<String, List<String>> childrenIndex,
            Map<String, BigDecimal> secInfoSubjectMap
    ) {
        List<String> children = childrenIndex.get(rootId);
        if (CollectionUtils.isEmpty(children)) {
            InvestmentBudgetBreakdownDetailsModel self = idMap.get(rootId);
            BigDecimal occupied = secInfoSubjectMap.getOrDefault(rootId, BigDecimal.ZERO);
            BigDecimal history = getHistoryAvailableForBudget(self, occupied);
            return calcSelfAvailable(history);
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (String cid : children) {
            sum = sum.add(sumLeafAvailableBudget(cid, idMap, childrenIndex, secInfoSubjectMap));
        }
        return sum;
    }

    /**
     * 汇总招标计划里所有标段的科目占用明细
     */
    private List<SecInfoSubjectResponse> collectAllSecInfoSubjects(List<SecInfoResponse> secInfos) {
        if (CollectionUtils.isEmpty(secInfos)) {
            return Collections.emptyList();
        }
        List<SecInfoSubjectResponse> all = new ArrayList<>();
        for (SecInfoResponse info : secInfos) {
            if (info == null || CollectionUtils.isEmpty(info.getSecInfoSubjects())) {
                continue;
            }
            all.addAll(info.getSecInfoSubjects());
        }
        return all;
    }

    private BigDecimal getHistoryAvailableForEstimate(InvestmentEstimateBreakdownDetailsModel n, BigDecimal occupied) {
        if (n == null) {
            return BigDecimal.ZERO;
        }

        // BigDecimal balance = n.getAmountBalance();
        // if (balance != null && balance.compareTo(BigDecimal.ZERO) != 0) {
        //     return balance;
        // }

        BigDecimal isbdAmount = n.getIsbdAmount() == null ? BigDecimal.ZERO : n.getIsbdAmount();
        BigDecimal useAmount = occupied == null ? BigDecimal.ZERO : occupied;
        BigDecimal fallback = isbdAmount.subtract(useAmount);

        return fallback.compareTo(BigDecimal.ZERO) > 0 ? fallback : BigDecimal.ZERO;
    }

    private BigDecimal getHistoryAvailableForBudget(InvestmentBudgetBreakdownDetailsModel n, BigDecimal occupied) {
        if (n == null) {
            return BigDecimal.ZERO;
        }

        // BigDecimal balance = n.getAmountBalance();
        // if (balance != null && balance.compareTo(BigDecimal.ZERO) != 0) {
        //     return balance;
        // }

        BigDecimal isbdAmount = n.getIsbdAmount() == null ? BigDecimal.ZERO : n.getIsbdAmount();
        BigDecimal useAmount = occupied == null ? BigDecimal.ZERO : occupied;
        BigDecimal fallback = isbdAmount.subtract(useAmount);

        return fallback.compareTo(BigDecimal.ZERO) > 0 ? fallback : BigDecimal.ZERO;
    }

    private int levelCodeOrder(Object levelCodeObj) {
        if (levelCodeObj == null) {
            return Integer.MAX_VALUE;
        }

        String s = String.valueOf(levelCodeObj).trim();
        if (s.isEmpty()) {
            return Integer.MAX_VALUE;
        }

        if (s.matches("^\\d+$")) {
            try {
                return Integer.parseInt(s);
            } catch (Exception ignored) {
            }
        }

        String leadingNum = s.replaceAll("^(\\d+).*$", "$1");
        if (leadingNum.matches("^\\d+$")) {
            try {
                return Integer.parseInt(leadingNum);
            } catch (Exception ignored) {
            }
        }

        String cn = s.replaceAll("[（）()、.．\\s]", "");
        if (!cn.isEmpty()) {
            String first = cn.substring(0, 1);
            try {
                return Convert.chineseToNumber(first);
            } catch (Exception ignored) {
            }
        }

        return Integer.MAX_VALUE;
    }

    /**
     * 前置校验
     *
     * @param tCode
     * @param secId
     * @return
     */
    @Override
    public BaseResponse getSecPrecheck(String tCode, String secId) {
        if (ValidateUtil.isAnObjEmpty(tCode, secId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 查询标段
        SecInfo secInfo = secInfoService.getById(secId);
        if (ValidateUtil.isNull(secInfo)) {
            return BaseResponse.fail(ResMsg.noData);
        }

        // 获取配置
        String secInfoPrecheckConfig = sysConfigService.getConfigByCode("secInfoPrecheckConfig");
        if (ValidateUtil.isEmpty(secInfoPrecheckConfig)) {
            return BaseResponse.fail(ResMsg.confError);
        }
        List<SecInfoPrecheckConfigDTO> config = JSONUtil.toList(secInfoPrecheckConfig, SecInfoPrecheckConfigDTO.class);
        if (ValidateUtil.isEmpty(config)) {
            return BaseResponse.fail(ResMsg.confError);
        }

        // 找到当前tCode且匹配当前标段类型的配置项
        SecInfoPrecheckConfigDTO dto = config.stream()
                .filter(Objects::nonNull)
                .filter(c -> tCode.equals(c.getCurrentTCode()))
                .filter(c -> {
                    // secBiddingTypes为空或不包含任何元素时，视为不限制，匹配所有
                    if (CollectionUtils.isEmpty(c.getSecBiddingTypes())) {
                        return true;
                    }
                    // secInfo.getSecBiddingType()为空时，只匹配secBiddingTypes也为空的配置
                    if (ValidateUtil.isEmpty(secInfo.getSecBiddingType())) {
                        return false;
                    }
                    return c.getSecBiddingTypes().contains(secInfo.getSecBiddingType());
                })
                .findFirst()
                .orElse(null);
        if (ValidateUtil.isNull(dto)) {
            return BaseResponse.ok();
        }

        // 获取前置tCode列表
        List<String> precheckTCodeList = dto.getPrecheckTCodeList();
        List<FileTemplateTree> template = baseMapper.getFileTemplateByTCodes(precheckTCodeList);
        Map<String, FileTemplateTree> templateMap = template.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(FileTemplateTree::gettCode, t -> t));

        // 逐个前置tCode校验
        for (String preTCode : precheckTCodeList) {
            FileTemplateTree fileTemplate = templateMap.get(preTCode);
            if (ValidateUtil.isNull(fileTemplate)) {
                continue;
            }
            // 表名
            String table = fileTemplate.gettTable();
            // 流程字段名
            String stateKey = fileTemplate.gettStateKey();
            // 查询
            Integer secPrecheckCount = baseMapper.getSecPrecheckCount(table, stateKey, secId);
            if (secPrecheckCount <= 0) {
                return BaseResponse.fail("请先完成前置模块【" + fileTemplate.gettName() + "】的流程审批");
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 更新指定计划的项目分管领导
     *
     * @param bpId
     * @param responsibleLeader
     * @return
     */
    @Override
    public BaseResponse updateSecBiddingPlanResponsibleLeader(String bpId, String responsibleLeader) {
        if (ValidateUtil.isAnObjEmpty(bpId, responsibleLeader)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecBiddingPlan plan = baseMapper.selectById(bpId);
        if (ValidateUtil.isNull(plan)) {
            return BaseResponse.fail(ResMsg.noData);
        }
        plan.setResponsibleLeader(responsibleLeader);
        this.updateById(plan);
        return BaseResponse.ok();
    }

    /**
     * 更新或新增由旧版招标计划转换的新版招标计划
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateFromOldSecBiddingPlan(SecBiddingPlanRequest request) {
        SecBiddingPlan sec = new SecBiddingPlan();
        BeanUtils.copyProperties(request, sec);
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        if (ValidateUtil.isEmpty(request.getBpId())) {
            sec.setBpCreateTime(new Date());
            sec.setBpId(GenerateUUID.getUUID());
            sec.setState(ApprovalStatusEnum.Already_Over.getIndex());
            // 数据完整性签名
            sec.setBpSign(sign(ied, ied.getSecSign(), sec.signContent()));
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getBpId(), request.gettId(), sec.getState() + "", sec.getBpCreateUid(), sec.getBpCreateUname()
                    , sec.getBpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBpApprovalFile()), sec.getBpId());

            // 作废旧数据
            String pId = null;
            List<SecBiddingProjectRequest> projectList = request.getSecBiddingProjectList();

            if (ValidateUtil.isNotEmpty(projectList) && projectList.get(0) != null) {
                pId = projectList.get(0).getpId();
            }

            if (ValidateUtil.isEmpty(pId) && ValidateUtil.isNotEmpty(request.getpIds())) {
                pId = request.getpIds().get(0);
            }

            if (ValidateUtil.isNotEmpty(pId)) {
                List<SecBiddingProject> data = secBiddingProjectService.getSecBiddingPidData(pId);
                if (ValidateUtil.isNotEmpty(data)) {
                    List<String> bpIds = data.stream()
                            .filter(Objects::nonNull)
                            .map(SecBiddingProject::getBpId)
                            .filter(ValidateUtil::isNotEmpty)
                            .collect(Collectors.toList());

                    if (ValidateUtil.isNotEmpty(bpIds)) {
                        this.update(Wrappers.<SecBiddingPlan>lambdaUpdate()
                                .in(SecBiddingPlan::getBpId, bpIds)
                                .set(SecBiddingPlan::getState,
                                        ApprovalStatusEnum.Invalid.getIndex()));

                        sysFileService.update(Wrappers.<SysFile>lambdaUpdate()
                                .in(SysFile::getFileid, bpIds)
                                .set(SysFile::getFileflowstatus,
                                        ApprovalStatusEnum.Invalid.getIndex().toString()));
                    }
                }
            }
        } else {
            SecBiddingPlan entity = baseMapper.selectById(request.getBpId());
            if (entity == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            if (JudgeUtil.nonModifyState(entity.getState())) {
                return BaseResponse.fail(ResMsg.stateNotModify);
            }
            sec.setBpCreateTime(entity.getBpCreateTime());
            sec.setBpCreateUid(entity.getBpCreateUid());
            sec.setBpCreateUname(entity.getBpCreateUname());
            // 数据完整性签名
            sec.setBpSign(sign(ied, ied.getSecSign(), sec.signContent(entity)));
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getBpId(), request.gettId(), sec.getState() + "", sec.getBpCreateUid(), sec.getBpCreateUname()
                    , sec.getBpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBpApprovalFile()),
                    Arrays.asList(request.getFileIds(), request.getBpApprovalFileIds()), sec.getBpId());
        }
        secBiddingProjectService.createOrUpdateSecBiddingProject(request.getpIds(), request.getSecBiddingProjectList(), sec.getBpId(), sec.getBpCreateUid());
        BaseResponse secInfoRes = secInfoService.createOrUpdateSecInfoFromOld(request.getSecInfos(), request.getpIds(), request.getSecProjectList(), sec);
        if (secInfoRes.getSuccess() == false) {
            return secInfoRes;
        }
        secInfoSubjectRemarkService.createOrUpdateSecInfoSubjectRemark(request.getRemarkList(), sec.getBpId());
        return BaseResponse.ok(sec.getBpId());
    }
}
