package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearSearchRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProInvInfoModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvCompleteMonthResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvPlanYearMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.investment.*;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvPlanYearResultVo;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvPlanYearVo;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectInvStatisticsVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.investment.*;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class InvPlanYearImpl extends ServiceImpl<InvPlanYearMapper, InvPlanYear> implements IInvPlanYearService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IInvMonthlyScheduleService invMonthlyScheduleService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IInvCompleteMonthService invCompleteMonthService;

    @Autowired
    private DataSource dataSource;


    @Resource
    private ISysDepartmentService iSysDepartmentService;

    @Resource
    private IPreProjectService iPreProjectService;

    @Resource
    private IConContractService conContractService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IInvMonthVersionService invMonthVersionService;
    @Resource
    private InvPlanYearYwService invPlanYearYwService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvPlanYear(InvPlanYearRequest request) {
        InvPlanYear entity = new InvPlanYear();
        BeanUtils.copyProperties(request, entity);
        if (!checkProjectInvPlanYear(entity.getPId(), entity.getIpvDeclareYear(), entity.getIpvId()))
            return BaseResponse.fail(ResMsg.ipvDeclareYearExists);
        if (ValidateUtil.isNotEmpty(request.getpId())) {
            PreProject preProject = preProjectService.getById(request.getpId());
            if (ValidateUtil.isNotNull(preProject)) {
                preProject.setpCategory(request.getIpvPType());
                //项目数据完整性验证
                InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
                preProject.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), preProject));
                preProjectService.updateById(preProject);
            }
        }
        entity.setIpvBudgetTotal(MathUtil.DecimalMultiplicationInt(entity.getIpvBudgetTotal(), 10000));
        entity.setIpvDoneLastYear(MathUtil.DecimalMultiplicationInt(entity.getIpvDoneLastYear(), 10000));
        entity.setIpvCarryOver(MathUtil.DecimalMultiplicationInt(entity.getIpvCarryOver(), 10000));
        entity.setIpvScheduledPlan(MathUtil.DecimalMultiplicationInt(entity.getIpvScheduledPlan(), 10000));
        entity.setIpvReleasedPlan(MathUtil.DecimalMultiplicationInt(entity.getIpvReleasedPlan(), 10000));
        entity.setIpvTotalFunds(MathUtil.DecimalMultiplicationInt(entity.getIpvTotalFunds(), 10000));
        entity.setIpvPlannedInvestment(MathUtil.DecimalMultiplicationInt(entity.getIpvPlannedInvestment(), 10000));
        entity.setIpvPlannedPay(MathUtil.DecimalMultiplicationInt(entity.getIpvPlannedPay(), 10000));
        entity.setIpvCumulativePay(MathUtil.DecimalMultiplicationInt(entity.getIpvCumulativePay(), 10000));
        entity.setIpvAnnualInv(MathUtil.DecimalMultiplicationInt(entity.getIpvAnnualInv(), 10000));
        entity.setIpvAnnualPay(MathUtil.DecimalMultiplicationInt(entity.getIpvAnnualPay(), 10000));

        if (ValidateUtil.isEmpty(entity.getIpvId())) {
            entity.setIpvCreateTime(new Date());
            entity.setIpvId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getIpvId(), request.gettId(), entity.getIpvState() + "", entity.getIpvCreateUid(), entity.getIpvCreateUname()
                    , entity.getIpvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getIpvId());

        } else {
            InvPlanYear model = baseMapper.selectById(request.getIpvId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setIpvState(model.getIpvState());
            entity.setIpvStateTime(model.getIpvStateTime());
            entity.setIpvCreateTime(model.getIpvCreateTime());
            entity.setIpvCreateUid(model.getIpvCreateUid());
            entity.setIpvCreateUname(model.getIpvCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getIpvId(), request.gettId(), entity.getIpvState() + "", entity.getIpvCreateUid(), entity.getIpvCreateUname()
                    , entity.getIpvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getIpvId());

        }
        invMonthlyScheduleService.createOrUpdateInvMonthlySchedule(request.getInvMonthlySchedules(), entity.getIpvId());
        return BaseResponse.ok(entity.getIpvId());
    }

    @Override
    public BaseResponse getInvPlanYearList(InvPlanYearRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Ipv_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Ipv_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvPlanYearRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("ipvDId").put("ipvDeclareYear").put("conName")
                .put("ipvConstStatus").put("ipvLibrary").put("pId").put("ipvState")
                .put("ipvNewly").put("ipvCompleted").put("ipvPType").put("pCompetentDepartment").put("ipvCName")
                .put("ipvConstructionStatus").put("projectName").put("contractName");
        IPage<InvPlanYearResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvPlanYearResponse> datas = baseMapper.getInvPlanYearList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public PageResult<InvPlanYearResponse> getInvPlanYearSearchList(InvPlanYearSearchRequest request, String uId) {
        PageResult<InvPlanYearResponse> result = new PageResult<>();
        String authSql = sysUserService.getAuthSql(null, uId, "Ipv_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Ipv_State");
        if (ValidateUtil.isEmpty(authSql)) {
            return result;
        }
        IPage<InvPlanYearResponse> page = new Page<>(request.getPageNum(), request.getPageSize());
        List<InvPlanYearResponse> list = baseMapper.queryInvPlanYearSearchList(page, request, authSql);
        result.setTotal(page.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public List<ClassInfoAnnotation> getSearchTableList() {
        List<ClassInfoAnnotation> result = new ArrayList<>();
        try {
            result = ClassInfoUtils.getClassInfo(InvPlanYearSearchRequest.class.getName());
        } catch (ClassNotFoundException e) {
            return result;
        }

        for (ClassInfoAnnotation classInfoAnnotation : result) {
            if (!classInfoAnnotation.getType().equals("date") && !classInfoAnnotation.getType().equals("month")) {
                if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                    List<Object> dictionaryValues;
                    if ("projectName".equals(classInfoAnnotation.getValue()) || "projectCode".equals(classInfoAnnotation.getValue()) || "invTotal".equals(classInfoAnnotation.getValue())) {
                        dictionaryValues = baseMapper.getProjectValueByCode(classInfoAnnotation.getDbValue());
                    } else if ("competentDepartmentName".equals(classInfoAnnotation.getValue())) {
                        dictionaryValues = baseMapper.getDepartValueByCode(classInfoAnnotation.getDbValue());
                    } else {
                        dictionaryValues = baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                    }
                    classInfoAnnotation.setDictionaryValues(dictionaryValues);
                }
            }
        }
        result = result.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        return result;
    }

    @Override
    public BaseResponse getInvPlanYearByIpvId(String ipvId) {
        return BaseResponse.ok(baseMapper.getInvPlanYearByIpvId(ipvId));
    }

    @Override
    public BaseResponse getInvPlanYearByPIdAndYear(InvPlanYearRequest request) {
        InvPlanYear entity = getPlanYearByPidAndYear(request.getpId(), request.getIpvDeclareYear());
        return BaseResponse.ok(baseMapper.getInvPlanYearByIpvId(entity.getIpvId()));
    }

    @Override
    public BaseResponse canToCreateInvPlanYear(InvPlanYearRequest request) {
        if (ValidateUtil.isEmpty(request.getpId()) || ValidateUtil.isEmpty(request.getIpvDeclareYear()))
            return BaseResponse.fail(ResMsg.notReqParam);
        return BaseResponse.ok(checkProjectInvPlanYear(request.getpId(), request.getIpvDeclareYear(), request.getIpvId()));
    }

    @Override
    public BaseResponse getInvPlanYearByPDYear(InvPlanYearRequest request) {
        if (ValidateUtil.isAnObjEmpty(request.getpId(), request.getIpvDeclareYear()))
            return BaseResponse.fail(ResMsg.notReqParam);
        InvPlanYear entity = getPlanYearByPidAndYear(request.getpId(), request.getIpvDeclareYear());
        Map<String, Object> data = new HashMap<>();
        if (entity != null) {
            //前一年
            InvPlanYear bfYear = getPlanYearByPidAndYear(request.getpId(), Integer.parseInt(request.getIpvDeclareYear()) - 1 + "");
            data.put("ipvAccumulatedSchedule", bfYear != null ? bfYear.getIpvAccumulatedSchedule() : BigDecimal.ZERO);
            data.put("ipvId", entity.getIpvId());
            data.put("ipvBudgetTotal", entity.getIpvBudgetTotal() == null ? 0 : entity.getIpvBudgetTotal());
            data.put("ipvPlannedInvestment", entity.getIpvPlannedInvestment() == null ? 0 : entity.getIpvPlannedInvestment());
            List<InvMonthlySchedule> mothData = invMonthlyScheduleService.getInvMonthlyScheduleByIpvId(entity.getIpvId());
            if (!CollectionUtils.isEmpty(mothData)) {
                List<BigDecimal> imsPlannedInv = new ArrayList<>();
                List<BigDecimal> imsPlannedPay = new ArrayList<>();
                List<String> imsImgProgress = new ArrayList<>();
                mothData.forEach(m -> {
                    imsPlannedInv.add(m.getImsPlannedInv());
                    imsPlannedPay.add(m.getImsPlannedPay());
                    imsImgProgress.add(m.getImsImgProgress());
                });
                data.put("imsPlannedInv", imsPlannedInv);
                data.put("imsPlannedPay", imsPlannedPay);
                data.put("imsImgProgress", imsImgProgress);
            }
            BigDecimal budgetInv = invCompleteMonthService.getLastIcmBudgetInvestmentByPid(entity.getPId());
            data.put("icmBudgetInvestment", budgetInv == null ? 0 : budgetInv);
            BigDecimal currentYearIcmInv = invCompleteMonthService.getCurrentYearIcmBudgetInvByPid(entity.getPId(), request.getIpvDeclareYear());
            data.put("currentYearIcmInv", currentYearIcmInv == null ? 0 : currentYearIcmInv);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getInvStatisticsOverview(String teId) {
        Map<String, BigDecimal> datas = baseMapper.getInvStatisticsOverview(teId);
        if (datas == null) {
            datas = new HashMap<>();
            datas.put("yearPlanInv", BigDecimal.ZERO);
            datas.put("currentMonInv", BigDecimal.ZERO);
            datas.put("completeInv", BigDecimal.ZERO);
            datas.put("invRate", BigDecimal.ZERO);
        } else {
            if (!datas.containsKey("yearPlanInv"))
                datas.put("yearPlanInv", BigDecimal.ZERO);
            if (!datas.containsKey("currentMonInv"))
                datas.put("currentMonInv", BigDecimal.ZERO);
            if (!datas.containsKey("completeInv"))
                datas.put("completeInv", BigDecimal.ZERO);
            if (!Objects.equals(datas.get("completeInv"), BigDecimal.ZERO) && !Objects.equals(datas.get("currentMonInv"), BigDecimal.ZERO))
                datas.put("invRate", datas.get("completeInv").divide(datas.get("currentMonInv"), 3, RoundingMode.UP));
            else
                datas.put("invRate", BigDecimal.ZERO);
        }
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse getInvPlanYearStatisticsOverviewList(InvPlanYearRequest request, String teId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<InvPlanYearResponse> page = new Page<>(request.getPage(), request.getSize());
        request.setTeId(teId);
        List<InvPlanYearResponse> datas = baseMapper.getInvPlanYearStatisticsOverviewList(page, request);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getInvStatisticsGroupDep(String teId) {
        List<Map<String, Object>> datas = baseMapper.getInvStatisticsGroupDep(teId);
        List<Object> result = new ArrayList<>();
        List<Map<String, Object>> oneDep = new ArrayList<>();
        Integer month = 0;
        for (Map<String, Object> data : datas) {
            if (month < Integer.parseInt(data.get("invMonth").toString()) && month != 0) {
                result.add(oneDep);
                oneDep = new ArrayList<>();
            }
            oneDep.add(data);
            month = Integer.parseInt(data.get("invMonth").toString());
        }
        return BaseResponse.ok(result);
    }

    @Override
    public BaseResponse getInvStatisticsByComplete(String teId) {
        List<Map<String, Object>> planInv = baseMapper.getInvPlanAmtByMonth(teId);
        List<Map<String, Object>> realInv = baseMapper.getInvAmtByMonth(teId);
        List<BigDecimal> planAmt = new ArrayList<>();
        List<BigDecimal> realAmt = new ArrayList<>();
        List<BigDecimal> invRate = new ArrayList<>();
        List<Integer> invMonth = new ArrayList<>();
        Integer bigMonth = Math.max(planInv.size(), realInv.size());
        for (int i = 0; i < bigMonth; i++) {
            invMonth.add(i + 1);
            if (i >= planInv.size()) {
                planAmt.add(BigDecimal.ZERO);
                continue;
            }
            if (i >= realAmt.size()) {
                realAmt.add(BigDecimal.ZERO);
                continue;
            }
            planAmt.add(new BigDecimal(planInv.get(i).get("invPlanAmt").toString()));
            realAmt.add(new BigDecimal(realInv.get(i).get("invAmt").toString()));
            invRate.add(realAmt.get(i).divide(planAmt.get(i), 3, RoundingMode.UP));
        }
        Map<String, Object> data = new HashMap<>();
        data.put("planAmt", planAmt);
        data.put("realAmt", realAmt);
        data.put("invRate", invRate);
        data.put("invMonth", invMonth);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getInvStatisticsByRanking(Integer size, String teId) {
        return BaseResponse.ok(baseMapper.getInvStatisticsByRanking(size, teId));
    }

    @Override
    public ProInvInfoModel getInvInfoByPidAndTimeYear(String pId, Integer start, Integer end) {
        ProInvInfoModel model = baseMapper.getInvInfoByPid(pId);
        if (model == null)
            model = new ProInvInfoModel();
        if (start != null && end != null)
            model.setIpvScheduledSum(baseMapper.getInvInfoByTimeYear(pId, start, end));
        return model;
    }

    @Override
    public List<InvPlanYear> getInvPlanYearByYearAndPids(List<String> pIds, Integer year) {
        QueryWrapper<InvPlanYear> wrapper = new QueryWrapper<>();
        wrapper.eq("Ipv_Declare_Year", year);
        wrapper.eq("Ipv_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.and(wr -> {
            wr.eq("P_Id", pIds.get(0));
            for (int i = 1; i < pIds.size(); i++) {
                wr.or().eq("P_Id", pIds.get(i));
            }
        });
        return baseMapper.selectList(wrapper);
    }

    @Override
    public ProjectInvStatisticsVo getInvPlanYearInvStatistics(Integer year) {
        ProjectInvStatisticsVo data = baseMapper.getInvPlanYearInvStatistics(year);
        return data;
    }


    //查询计划总览
    @Override
    public BaseResponse getPlanOverview(String pName, String strYear, Integer page, Integer size, String pCompetentDepartment, String pSecType, String projectState) {
        IPage<InvPlanYearVo> invPlanYears = baseMapper.getInvPlanYearDataList(new Page<>(page, size), pName, strYear, pCompetentDepartment, pSecType, projectState);
        if (!ValidateUtil.isNull(invPlanYears.getRecords()) && !invPlanYears.getRecords().isEmpty()) {
            List<String> ipvIds = invPlanYears.getRecords().stream().map(e -> e.getIpvId()).collect(Collectors.toList());
            List<InvMonthlySchedule> mothData = invMonthlyScheduleService.getInvMonthlyScheduleByIpvIdList(ipvIds);
            List<InvCompleteMonth> months = invCompleteMonthService.getActualInvestment(ipvIds);
            List<InvPlanYearResultVo> invs = new ArrayList<>();
            for (InvPlanYearVo invPlanYear : invPlanYears.getRecords()) {
                InvPlanYearResultVo vo = new InvPlanYearResultVo();
                if (!CollectionUtils.isEmpty(mothData)) {
                    List<InvMonthlySchedule> collect = mothData.stream().filter(du -> du.getIpvId().equals(invPlanYear.getIpvId())).collect(Collectors.toList());
                    vo.setScheduleList(collect);  //月度投资计划
                    BigDecimal amt = collect.stream()
                            .map(InvMonthlySchedule::getImsPlannedInv)
                            .reduce(BigDecimal.ZERO, BigDecimal::add); // 汇总
                    vo.setAdvancePaymentAmt(amt);
                }
                if (!CollectionUtils.isEmpty(months)) {
                    List<InvCompleteMonth> monthList = months.stream().filter(mo -> mo.getIpvId().equals(invPlanYear.getIpvId())).collect(Collectors.toList());
                    BigDecimal amt = monthList.stream()
                            .map(InvCompleteMonth::getIcmMonthlyInvestment) // 获取
                            .reduce(BigDecimal.ZERO, BigDecimal::add); // 汇总
                    vo.setMonthList(monthList);
                    vo.setActualPaymentAmt(amt);
                }
                vo.setId(invPlanYear.getIpvId());
                vo.setPId(invPlanYear.getPId());
                vo.setIpvPType(invPlanYear.getPSecType());
                vo.setDName(invPlanYear.getDName());
                vo.setProjectState(invPlanYear.getProjectState());
                vo.setPName(invPlanYear.getPName());
                BigDecimal bigDecimalSum = MathUtil.DecimalMultiplicationInt(MathUtil.decimalDivide(vo.getActualPaymentAmt(), vo.getAdvancePaymentAmt(), 4, RoundingMode.HALF_UP), 100);
                vo.setInvestmentRatio(bigDecimalSum);
                invs.add(vo);
            }
            return BaseResponse.ok(invs, (int) invPlanYears.getTotal());
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse writeBackMonthPlan(String ipvId, String uid) {
        // 通过用户id查到用户信息
        SysUser userInfo = sysUserService.getById(uid);
        LambdaQueryWrapper<InvPlanYear> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InvPlanYear::getIpvId, ipvId)
                .eq(InvPlanYear::getIpvState, 7);
        List<InvPlanYear> invPlanYears = list(queryWrapper);
        if (invPlanYears.isEmpty()) {
            return BaseResponse.ok("流程状态不允许操作");
        }
        // 获取所有的月度计划
        LambdaQueryWrapper<InvMonthlySchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InvMonthlySchedule::getIpvId, ipvId);
        LambdaQueryWrapper<ConContract> conContractLambdaQueryWrapper = new LambdaQueryWrapper<>();
        conContractLambdaQueryWrapper.eq(ConContract::getConProjectId, invPlanYears.get(0).getPId());
        conContractLambdaQueryWrapper.last("limit 1");
        ConContract conId = conContractService.getOne(conContractLambdaQueryWrapper);
        List<InvMonthlySchedule> monthlySchedules = invMonthlyScheduleService.list(wrapper);
        // 批量处理每个月的数据
        List<InvCompleteMonth> invCompleteMonths = new ArrayList<>();
        for (InvMonthlySchedule schedule : monthlySchedules) {
            // 检查该月份是否已存在完成记录
            long count = invCompleteMonthService.count(new LambdaQueryWrapper<InvCompleteMonth>()
                    .eq(InvCompleteMonth::getIpvId, ipvId)
                    .eq(InvCompleteMonth::getIcmReportingMonth, String.valueOf(schedule.getImsMonth())));

            if (count == 0) {
                InvCompleteMonth invCompleteMonth = buildCompleteMonth(schedule, invPlanYears.get(0), userInfo, conId == null ? null : conId.getConId());
                invCompleteMonths.add(invCompleteMonth);
            }
        }
        if (!invCompleteMonths.isEmpty()) {
            invCompleteMonthService.saveBatch(invCompleteMonths);
            // 保存最初的版本
            List<InvMonthVersion> versionList = invCompleteMonths.stream().map(month -> {
                InvMonthVersion version = new InvMonthVersion();
                BeanUtils.copyProperties(month, version);
                version.setVersion(new Date());
                return version;
            }).collect(Collectors.toList());
            invMonthVersionService.saveBatch(versionList);
        }
        return BaseResponse.ok();
    }

    /*    String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Plan_State");
            if (ValidateUtil.isEmpty(authSql))
                return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<GanttPlanRequest> params = new QueryMap<>(request);
            params.put("authSql", authSql).put("uId", uId).put("planType").put("planName").put("planTypes").put("excludeTypes")
                    .put("pName").put("pId").put("planDId").put("planState").put("conName").put("pPlanStartYear").put("pCompetentDepartment")
                    .put("pDepartmentNameList").put("pBuildModelList").put("pCompetentDepartmentNameList")
                    .put("pNameList").put("createUnameList").put("stateList").put("createTimeList");
        //region (2022年5月12日新增：外部用户只能查看自己创建的、自己单位人员创建的、需要自己办理的、需要自己单位人员办理的进度计划信息,同时的拥有项目权限)zhang；
        //（2022年5月17日追加：项目下的监理单位和造价咨询单位可以查看外部单位在该项目下创建的所有进度计划数据）
        SysUser user = sysUserService.getById(uId);
            if (user != null) {
            if (!ValidateUtil.isEmpty(user.getUType()) && user.getUType().equals("2")) {
                params.put("uType", user.getUType());
            } else {
                params.put("uType", null);
            }
        } else {
            params.put("uType", null);
        }*/
    @Override
    public BaseResponse getMonthplanClassInfo(InvCompleteMonthRequest request, LogUserInfoVo user) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        int i = 0;
        try {
            DepartmentPCjConfDTO gwConf = sysConfigService.getDepartmentPCjConf();
            String authSql = sysUserService.getAuthSql(null, user.getId(), "Icm_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "transfer_status");
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(InvCompleteMonthRequest.class.getName());
            QueryMap<InvCompleteMonthRequest> params = new QueryMap<>(request);
            params.put("pId").put("ipvStage").put("completeResultList").put("planCompleteResultList")
                    .put("icmReportingMonth").put("icmReportingYear").put("monthCumulativeInvProportion")
                    .put("ipvPType").put("ipvConstructionStage").put("pName").put("actualStartTimes").put("actualEndTimes")
                    .put("pCompetentDepartment").put("icmState").put("transferStatusList")
                    .put("pNameList").put("pTypeList").put("cjJbrList").put("actualStartTime").put("actualEndTime")
                    .put("pCompetentDepartmentNameList").put("pDepartmentNameList")
                    .put("ipvStageList").put("createUnameList").put("stateList");
            params.put("jbr", gwConf.getJtJbr());
            params.put("authSql", authSql);
            params.put("warningFilter").put("ipvConstructionStatusList");
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if (!"datetime1".equalsIgnoreCase(classInfoAnnotation.getType()) && !"datetime2".equalsIgnoreCase(classInfoAnnotation.getType()) && !"date".equals(classInfoAnnotation.getType()) && !"cjJbrList".equals(classInfoAnnotation.getValue()) && !"cjZrdwList".equals(classInfoAnnotation.getType()) && !"ipvStageList".equalsIgnoreCase(classInfoAnnotation.getValue())) {
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                        List<Object> dictionaryValues = baseMapper.getComMonthvalueoCode(classInfoAnnotation.getDbValue(), params.getQuery());
                        dictionaryValues = ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                        classInfoAnnotation.setDictionaryValues(dictionaryValues);
                    }
                }  else if ("cjJbrList".equals(classInfoAnnotation.getValue())) {
                List<Object> dictionaryValues = baseMapper.getCjJbr(params.getQuery());
                dictionaryValues = ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                classInfoAnnotation.setDictionaryValues(dictionaryValues);
                classInfoAnnotation.setNo(i++);
                } else if ("cjZrdwList".equals(classInfoAnnotation.getType())) {
                    List<Object> dictionaryValues = baseMapper.getCjZrdw(params.getQuery());
                    dictionaryValues = ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                classInfoAnnotation.setDictionaryValues(dictionaryValues);
                classInfoAnnotation.setNo(i++);
                } else if ("ipvStageList".equalsIgnoreCase(classInfoAnnotation.getValue())) {
                    List<Object> dictionaryValues = baseMapper.getComMonthvalueoCode(classInfoAnnotation.getDbValue(), params.getQuery());
                    dictionaryValues = ClassInfoUtils.processDictionaryValues(dictionaryValues)
                            .flatMap(value -> {
                                if (value instanceof String) {
                                    return Arrays.stream(((String) value).split(","));
                                }
                                return Stream.of(value);
                            })
                            .distinct()
                            .sorted(Comparator.comparing(Object::toString))
                            .collect(Collectors.toList());
                    classInfoAnnotation.setDictionaryValues(dictionaryValues);
                } else if ("datetime1".equalsIgnoreCase(classInfoAnnotation.getType())) {
                    List<Object> dictionaryValues = baseMapper.getActualTime(classInfoAnnotation.getDbValue(), params.getQuery());
                    dictionaryValues = ClassInfoUtils.processDictionaryValues(dictionaryValues, "未开工").collect(Collectors.toList());
                    classInfoAnnotation.setDictionaryValues(dictionaryValues);
                    dictionaryValues.replaceAll(value ->
                            value instanceof Date
                                    ? DateFormatUtil.dateToString((Date) value, DateFormatUtil.FORMAT_PATTERN_OF_MONTH)
                                    : value
                    );
                } else if ("datetime2".equalsIgnoreCase(classInfoAnnotation.getType())) {
                    List<Object> dictionaryValues = baseMapper.getActualTime(classInfoAnnotation.getDbValue(), params.getQuery());
                    dictionaryValues = ClassInfoUtils.processDictionaryValues(dictionaryValues, "未完工").collect(Collectors.toList());
                    classInfoAnnotation.setDictionaryValues(dictionaryValues);
                    dictionaryValues.replaceAll(value ->
                            value instanceof Date
                                    ? DateFormatUtil.dateToString((Date) value, DateFormatUtil.FORMAT_PATTERN_OF_MONTH)
                                    : value
                    );
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        classInfoAnnotationList = classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getMonthplanList(InvCompleteMonthRequest request, LogUserInfoVo user) {
        //城建要求指定角色鉴权（项目同部门） 指定角色不鉴权  菜单控制权限 基础配置控制不鉴权 其他鉴权
//        InvPlanYearCjAuthDTO conf = sysConfigService.getInvPlanYearCjAuth();
        DepartmentPCjConfDTO gwConf = sysConfigService.getDepartmentPCjConf();
//        if (ValidateUtil.isNull(conf)) throw new JPMException(String.format(ResMsg.notConf, "invPlanYearCjAuthConf"));
//        Boolean auth = false;
//        for (String s : user.getRoleNames()) {
//            if (conf.getNoAuthRoles().equals(s)) {
//                auth = true;
//            }
//        }
        String authSql = sysUserService.getAuthSql(null, user.getId(), "Icm_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        QueryMap<InvCompleteMonthRequest> params = new QueryMap<>(request);
        params.put("pId").put("ipvStage").put("completeResultList").put("planCompleteResultList")
                .put("icmReportingMonth").put("icmReportingYear").put("monthCumulativeInvProportion")
                .put("ipvPType").put("ipvConstructionStage").put("pName").put("actualStartTimes").put("actualEndTimes")
                .put("pCompetentDepartment").put("icmState").put("transferStatusList")
                .put("pNameList").put("pTypeList").put("cjJbrList").put("actualStartTime").put("actualEndTime")
                .put("pCompetentDepartmentNameList").put("pDepartmentNameList")
                .put("ipvStageList").put("createUnameList").put("stateList");
        params.put("jbr", gwConf.getJtJbr());
        params.put("authSql", authSql);
        params.put("warningFilter").put("ipvConstructionStatusList");
//        if (!auth)
//            params.put("userDepIds", user.getDepartmentIds());
        IPage<InvCompleteMonthRequest> page = new Page<>(request.getPage(), request.getSize());
        List<InvCompleteMonthResponse> datas = baseMapper.getMonthplanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    public InvCompleteMonth buildCompleteMonth(InvMonthlySchedule invMonthlySchedule, InvPlanYear invPlanYear, SysUser user, String conId) {
        InvCompleteMonth invCompleteMonth = new InvCompleteMonth();
        invCompleteMonth.setIcmId(UUID.randomUUID().toString());
        invCompleteMonth.setIpvId(invPlanYear.getIpvId());
        invCompleteMonth.setPId(invPlanYear.getPId());
        invCompleteMonth.setConId(conId);
//        invCompleteMonth.setIcmCompleted();
//        invCompleteMonth.setIcmImgProgress(invMonthlySchedule.getImsImgProgress());
//        invCompleteMonth.setIcmDId(invPlanYear.getIpvCId());
        invCompleteMonth.setIcmReportingMonth(String.valueOf(invMonthlySchedule.getImsMonth()));
//        invCompleteMonth.setIcmMonthlyInvestment(invMonthlySchedule.getImsPlannedInv());
//        invCompleteMonth.setIcmMonthlyProportion(invMonthlySchedule.getImsAnnualProportion());
//        invCompleteMonth.setIcmAnnualInvestment(invMonthlySchedule.getImsCumulativeInv());
//        invCompleteMonth.setIcmAnnualProportion(invMonthlySchedule.getImsCumulativeInvProportion());
        invCompleteMonth.setMonthCumulativeInv(invMonthlySchedule.getImsCumulativeInv());
        invCompleteMonth.setIcmState(0);
        invCompleteMonth.setIcmReportingYear(invPlanYear.getIpvDeclareYear());
        invCompleteMonth.setIcmStateTime(new Date());
        invCompleteMonth.setTransferStatus("1");
        invCompleteMonth.setIcmCreateTime(new Date());
        invCompleteMonth.setPCompetentDepartments(invPlanYear.getPCompetentDepartments());
        invCompleteMonth.setPlanStartTime(invPlanYear.getIpvStart());
        invCompleteMonth.setPlanEndTime(invPlanYear.getIpvEnd());
        return invCompleteMonth;
    }


    @Override
    public BaseResponse createOrUpdateInvPlanYearXz(InvPlanYearRequest request) {
        InvPlanYear entity = new InvPlanYear();
        BeanUtils.copyProperties(request, entity);

        QueryWrapper<InvPlanYear> squery = new QueryWrapper<>();
        squery.eq("p_id", request.getpId());
        squery.ne("Ipv_State", 3);
        InvPlanYear invPlanYear = baseMapper.selectOne(squery);
        if (invPlanYear != null) {  //如果是3就允许
            if (ValidateUtil.isNotEmpty(request.getIpvId())) {
                if (!invPlanYear.getIpvId().equals(request.getIpvId())) {
                    return BaseResponse.fail("该年度计划已存在,请勿重复填报");  //该年度计划已存在
                }
            } else {
                return BaseResponse.fail("该年度计划已存在,请勿重复填报");  //项目年度计划已存在
            }
        }
        entity.setIpvBudgetTotal(MathUtil.DecimalMultiplicationInt(entity.getIpvBudgetTotal(), 10000));
        entity.setIpvDoneLastYear(MathUtil.DecimalMultiplicationInt(entity.getIpvDoneLastYear(), 10000));
        entity.setIpvCarryOver(MathUtil.DecimalMultiplicationInt(entity.getIpvCarryOver(), 10000));
        entity.setIpvScheduledPlan(MathUtil.DecimalMultiplicationInt(entity.getIpvScheduledPlan(), 10000));
        entity.setIpvReleasedPlan(MathUtil.DecimalMultiplicationInt(entity.getIpvReleasedPlan(), 10000));
        entity.setIpvTotalFunds(MathUtil.DecimalMultiplicationInt(entity.getIpvTotalFunds(), 10000));
        entity.setIpvPlannedInvestment(MathUtil.DecimalMultiplicationInt(entity.getIpvPlannedInvestment(), 10000));
        entity.setIpvPlannedPay(MathUtil.DecimalMultiplicationInt(entity.getIpvPlannedPay(), 10000));
        entity.setIpvCumulativePay(MathUtil.DecimalMultiplicationInt(entity.getIpvCumulativePay(), 10000));
        entity.setIpvAnnualInv(MathUtil.DecimalMultiplicationInt(entity.getIpvAnnualInv(), 10000));
        entity.setIpvAnnualPay(MathUtil.DecimalMultiplicationInt(entity.getIpvAnnualPay(), 10000));

        if (ValidateUtil.isEmpty(entity.getIpvId())) {
            entity.setIpvCreateTime(new Date());
            entity.setIpvId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getIpvId(), request.gettId(), entity.getIpvState() + "", entity.getIpvCreateUid(), entity.getIpvCreateUname()
                    , entity.getIpvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getIpvId());

        } else {
            InvPlanYear model = baseMapper.selectById(request.getIpvId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setIpvState(model.getIpvState());
            entity.setIpvStateTime(model.getIpvStateTime());
            entity.setIpvCreateTime(model.getIpvCreateTime());
            entity.setIpvCreateUid(model.getIpvCreateUid());
            entity.setIpvCreateUname(model.getIpvCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getIpvId(), request.gettId(), entity.getIpvState() + "", entity.getIpvCreateUid(), entity.getIpvCreateUname()
                    , entity.getIpvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getIpvId());

        }
        invMonthlyScheduleService.createOrUpdateInvMonthlySchedule(request.getInvMonthlySchedules(), entity.getIpvId());
        return BaseResponse.ok(entity.getIpvId());

    }

    private InvPlanYear getPlanYearByPidAndYearStr(String pid, String year) {
        QueryWrapper<InvPlanYear> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pid);
//        wrapper.eq("Ipv_Declare_Year", year);
        wrapper.le("EXTRACT(YEAR FROM ipv_start)", year);
        wrapper.ge("EXTRACT(YEAR FROM ipv_end)", year);
        wrapper.ne("Ipv_State", ApprovalStatusEnum.Invalid.getIndex());
        InvPlanYear entity = baseMapper.selectOne(wrapper);
        return entity;
    }

    public String getDatabaseType() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            return databaseProductName;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }


    @Override
    public BaseResponse getInvPlanYearByPDYearXz(InvPlanYearRequest request) {
        if (ValidateUtil.isAnObjEmpty(request.getpId(), request.getIpvDeclareYear()))
            return BaseResponse.fail(ResMsg.notReqParam);
        InvPlanYearResultVo vo = new InvPlanYearResultVo();
        InvPlanYear yearsAgobfYear = getPlanYearByPidAndYearStr(request.getpId(), request.getIpvDeclareYear()); //查询
        List<List<InvPlanYearResultVo>> groupedByYear = new ArrayList<>();
        String dataBase = getDatabaseType();
        if (yearsAgobfYear != null) {
            if (yearsAgobfYear.getIpvState().equals(7)) {
                //总投资
                vo.setLastYeartotalInvestment(yearsAgobfYear.getIpvBudgetTotal());
                List<InvMonthlySchedule> yearsAgomothData = invMonthlyScheduleService.getInvMonthlyScheduleByIpvId(yearsAgobfYear.getIpvId());
                QueryWrapper<InvCompleteMonth> queryWrapper = new QueryWrapper<>();
                if (dataBase.equals("DM DBMS") || dataBase.equals("DM") || dataBase.equals("dm")) {
                    queryWrapper.orderByDesc("TO_NUMBER(Icm_Reporting_Month)");
                } else if (dataBase.equals("MYSQL") || dataBase.equals("mysql")) {
                    queryWrapper.orderByDesc("CONVERT(Icm_Reporting_Month, SIGNED INTEGER)");
                }
                queryWrapper.eq("ipv_id", yearsAgobfYear.getIpvId());
                List<InvCompleteMonth> list = invCompleteMonthService.getBaseMapper().selectList(queryWrapper);
                if (yearsAgomothData != null) {
                    for (InvMonthlySchedule tums : yearsAgomothData) {
                        List<InvPlanYearResultVo> data = new ArrayList<>();
                        InvPlanYearResultVo resultvo = new InvPlanYearResultVo();
                        // 获取当前记录的年份
                        Integer year = tums.getImsYear();
                        List<InvMonthlySchedule> sameYearSchedules = yearsAgomothData.stream()
                                .filter(schedule -> schedule.getImsYear().equals(year))
                                .collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(sameYearSchedules) && !year.toString().equals(request.getIpvDeclareYear())) {
                            Optional<InvMonthlySchedule> maxMonthData = sameYearSchedules.stream().filter(i -> i.getImsImgProgress() != null).max(Comparator.comparing(InvMonthlySchedule::getImsMonth));

                            if (maxMonthData.isPresent()) {
                                InvMonthlySchedule maxSchedule = maxMonthData.get();
                                //计划形象进度
                                resultvo.setLastYearPlanImageProgress(maxSchedule.getImsImgProgress());
                            }
                            //年度
                            resultvo.setYear(year);
                            //计划投资
                            BigDecimal totalPlannedInvestment = sameYearSchedules.stream().map(InvMonthlySchedule::getImsPlannedInv)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            resultvo.setLastYearipvPlannedInvestment(totalPlannedInvestment);

                            // 计划投资占比
                            resultvo.setLastYearPlannedInvestment(MathUtil.DecimalDivisionInt(totalPlannedInvestment, yearsAgobfYear.getIpvBudgetTotal()));
                            BigDecimal entPro = yearsAgomothData.stream().map(InvMonthlySchedule::getImsPlannedInv).reduce(BigDecimal.ZERO, BigDecimal::add);
                            // 累计计划投资占比   月完成情况表
                            resultvo.setLastYearInvestmentProportion(MathUtil.DecimalDivisionInt(entPro, yearsAgobfYear.getIpvBudgetTotal()));

                            //概算投资(总投资)    取年度表
                            resultvo.setLastYeartotalInvestment(yearsAgobfYear.getIpvBudgetTotal());

                            if (!CollectionUtils.isEmpty(list)) {
                                // 查找所有与当前记录同一年份的记录
                                List<InvCompleteMonth> completeMonths = list.stream()
                                        .filter(schedule -> schedule.getIcmReportingYear().equals(year.toString())).filter(e -> e.getIcmState().equals(7))
                                        .collect(Collectors.toList());

                                BigDecimal InvComptment = completeMonths.stream().map(InvCompleteMonth::getIcmMonthlyInvestment).reduce(BigDecimal.ZERO, BigDecimal::add);
                                //实际投资    取月完成情况表   月度完成投资
                                resultvo.setLastYearActualInvestment(InvComptment);
                                //实际投资占比
                                resultvo.setLastYearActualInvestmentProportion(MathUtil.DecimalDivisionInt(InvComptment, yearsAgobfYear.getIpvBudgetTotal()));

                                BigDecimal LastYearAccumu = completeMonths.stream().map(InvCompleteMonth::getIcmAnnualInvestment).reduce(BigDecimal.ZERO, BigDecimal::add);
                                // 积累实际投资占比
                                resultvo.setLastYearAccumulateInvestment(MathUtil.DecimalDivisionInt(LastYearAccumu, yearsAgobfYear.getIpvBudgetTotal()));
                                //实际形象进度
                                Optional<InvCompleteMonth> completeMonth = completeMonths.stream().filter(i -> i.getIcmImageProgress() != null)
                                        .max(Comparator.comparingInt(month -> Integer.parseInt(month.getIcmReportingMonth())));
                                if (completeMonth.isPresent()) {
                                    InvCompleteMonth maxSchedule = completeMonth.get();
                                    //去年实际形象进度
                                    resultvo.setLastYearActualImageProgress(maxSchedule.getIcmImageProgress());

                                }
                            }
                            data.add(resultvo);
                            // 确保不重复添加已经存在的组
                            if (!groupedByYear.contains(data)) {
                                groupedByYear.add(data);
                            }
                        }
                    }
                }
            } else {
                return BaseResponse.fail("该项目没有年度计划或审批未结束");
            }
        }
        //今年==============================
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (yearsAgobfYear != null) {
            vo.setId(yearsAgobfYear.getIpvId());
            List<InvMonthlySchedule> thisYearmothDatamothData = invMonthlyScheduleService.getInvMonthlyScheduleByIpvId(yearsAgobfYear.getIpvId());
            List<InvMonthlySchedule> MonthlySchedules = thisYearmothDatamothData.stream().filter(e -> e.getImsYear().toString().equals(request.getIpvDeclareYear())).sorted(Comparator.comparingInt(InvMonthlySchedule::getImsMonth)).collect(Collectors.toList());
            QueryWrapper<InvCompleteMonth> thisQueryWrapper = new QueryWrapper<>();
            thisQueryWrapper.eq("ipv_id", yearsAgobfYear.getIpvId());
            thisQueryWrapper.eq("Icm_Reporting_Year", request.getIpvDeclareYear());
            thisQueryWrapper.eq("icm_state", "7");
            List<InvCompleteMonth> thisYearCompleteMonth = invCompleteMonthService.getBaseMapper().selectList(thisQueryWrapper);
            if (!CollectionUtils.isEmpty(MonthlySchedules)) {

                for (InvMonthlySchedule datum : MonthlySchedules) {
                    Map<String, Object> mapData = new HashMap<>();
                    String month = datum.getImsMonth().toString();
                    mapData.put("month", month);  //月份
                    mapData.put("year", datum.getImsYear());
                    mapData.put("ImsPlannedInv", datum.getImsPlannedInv());  //计划完成投资
                    mapData.put("plannedInvestment", MathUtil.DecimalDivisionInt(datum.getImsPlannedInv(), yearsAgobfYear.getIpvBudgetTotal()));  //计划投资占比
                    mapData.put("InvestmentProportion", datum.getImsAnnualProportion());  //计划投资占比
                    mapData.put("imsImgProgress", datum.getImsImgProgress());  //计划形象进度
                    for (InvCompleteMonth invCompleteMonth : thisYearCompleteMonth) {
                        if (month.equals(invCompleteMonth.getIcmReportingMonth())) {
                            mapData.put("ActualInvestment", invCompleteMonth.getIcmMonthlyInvestment()); //实际投资
                            mapData.put("ActualInvestmentProportion", invCompleteMonth.getIcmMonthlyProportion()); //实际投资占比
                            mapData.put("AccumulateActualInvestment", invCompleteMonth.getIcmBudgetProportion()); //积累实际投资占比
                            mapData.put("ActualImageProgress", invCompleteMonth.getIcmImageProgress()); //实际形象进度
                            break;
                        }
                    }
                    listMap.add(mapData);
                }
            }
        }
        vo.setPId(request.getpId());

        vo.setThisYearList(listMap);
        vo.setQiTaYear(groupedByYear);
        return BaseResponse.ok(vo);  //
    }

    public static List<Map<String, Object>> sortListMap(List<Map<String, Object>> listMap) {
        // 排序
        listMap.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                int year1 = Integer.parseInt(map1.get("year").toString());
                int month1 = Integer.parseInt(map1.get("month").toString());
                int year2 = Integer.parseInt(map2.get("year").toString());
                int month2 = Integer.parseInt(map2.get("month").toString());

                // 比较年份和月份
                if (year1 != year2) {
                    return Integer.compare(year1, year2);
                } else {
                    return Integer.compare(month1, month2);
                }
            }
        });

        // 返回排序后的 listMap
        return listMap;
    }

    @Override
    public BaseResponse updatePreState(String pid, String preState, String icmId) {
        if (ValidateUtil.isAnObjEmpty(pid, preState, icmId)) return BaseResponse.fail(ResMsg.notReqParam);

        InvCompleteMonth month = invCompleteMonthService.getBaseMapper().selectById(icmId);
        if (month.getIcmState().equals(7)) {
            Boolean b = iPreProjectService.updatePreState(pid, preState);
            return b ? BaseResponse.ok() : BaseResponse.fail();
        } else {
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse getInvPlanYearByIpvIdXz(String ipvId) {
        return BaseResponse.ok(baseMapper.getInvPlanYearByIpvIdXz(ipvId));
    }

    @Override
    public BaseResponse determineAnnualPlan(String pId) {
        if (ValidateUtil.isEmpty(pId)) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<InvPlanYear> squery = new QueryWrapper<>();
        squery.eq("p_id", pId);
        squery.ne("Ipv_State", 3);
        if (baseMapper.selectCount(squery) > 0) {
            return BaseResponse.fail("1");  //该年度计划已存在
        } else {
            return BaseResponse.ok(true);
        }
    }

    public Boolean determineYaer(Integer year, Integer month, String pId) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1); // 获取该月的第一天
        LocalDate endDate = yearMonth.atEndOfMonth(); // 获取该月的最后一天
        QueryWrapper<InvPlanYear> wrapper = new QueryWrapper<>();
        wrapper.le("ipv_start", endDate);
        wrapper.ge("ipv_end", startDate);
        wrapper.eq("P_Id", pId);
        wrapper.ne("Ipv_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public BaseResponse determinemonthlyPlan(String pId, Integer year, Integer month) {
        if (ValidateUtil.isEmpty(pId)) return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(year)) return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(month)) return BaseResponse.fail(ResMsg.notReqParam);
        Boolean b = false;
        String msg = "";
        if (determineYaer(year, month, pId)) {
            QueryWrapper<InvPlanYear> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("p_id", pId);
            queryWrapper.ne("Ipv_State", 3);
            InvPlanYear invPlanYear = baseMapper.selectOne(queryWrapper);
            if (invPlanYear != null) {
                QueryWrapper<InvCompleteMonth> squery = new QueryWrapper<>();
                squery.eq("Icm_Reporting_Year", year);
                squery.eq("Icm_Reporting_Month", month);
                squery.eq("ipv_id", invPlanYear.getIpvId());
                squery.ne("Icm_State", 3);
                if (invCompleteMonthService.getBaseMapper().selectCount(squery) > 0) {
                    msg = "1";  // *月已经填报,请重新选中其他月份
                    b = false;
                } else {
                    b = true;
                }
            } else {
                msg = "2";   //当前计划不在时间范围内
                b = false;
            }
        } else {
            msg = "3";   //没有年度计划
            b = false;
        }
        return b ? BaseResponse.ok() : BaseResponse.fail(msg);
    }

    private InvPlanYear getPlanYearByPidAndYear(String pid, String year) {
        QueryWrapper<InvPlanYear> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pid);
        wrapper.eq("Ipv_Declare_Year", year);
        wrapper.ne("Ipv_State", ApprovalStatusEnum.Invalid.getIndex());
        InvPlanYear entity = baseMapper.selectOne(wrapper);
        return entity;
    }

    private boolean checkProjectInvPlanYear(String pId, String ipvDeclareYear, String ipvId) {
        QueryWrapper<InvPlanYear> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("Ipv_Declare_Year", ipvDeclareYear);
        if (!ValidateUtil.isEmpty(ipvId))
            wrapper.ne("Ipv_Id", ipvId);
        wrapper.ne("Ipv_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return false;
        else
            return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncToInvPlanYearYw(String ipvId) {
        if (ValidateUtil.isEmpty(ipvId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        InvPlanYear planYear = this.getById(ipvId);
        if (ValidateUtil.isNull(planYear)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 12个月的
        List<InvMonthlySchedule> monthlySchedules = invMonthlyScheduleService.list(Wrappers.<InvMonthlySchedule>lambdaQuery().eq(InvMonthlySchedule::getIpvId, ipvId));
        Map<String, InvMonthlySchedule> monthMap = monthlySchedules.stream().collect(Collectors.toMap(item -> item.getImsMonth().toString(), item -> item));
        // 项目
        String pId = planYear.getPId();
        // 合同
        String conId = planYear.getConId();
        // 填报年份
        String ipvDeclareYear = planYear.getIpvDeclareYear();
        // 查询出对应的
        List<InvPlanYearYw> planYearYws = invPlanYearYwService.list(Wrappers.<InvPlanYearYw>lambdaQuery()
                .eq(InvPlanYearYw::getConId, conId)
                .eq(InvPlanYearYw::getPId, pId)
                .eq(InvPlanYearYw::getIpvDeclareYear, ipvDeclareYear)
        );
        if (ValidateUtil.isEmpty(planYearYws)) {
            return BaseResponse.ok();
        }
        // 批量更新
        planYearYws.forEach(item -> {
            InvMonthlySchedule monthlySchedule = monthMap.get(item.getIpvConstructionStage());
            if (ValidateUtil.isNotNull(monthlySchedule)) {
                // 资金需求
                item.setIpvAnnualInv(monthlySchedule.getImsPlannedInv());
                // 总产值
                item.setIpvAnnualPay(monthlySchedule.getImsCumulativeInv());
                // 投资
                item.setIpvCumulativePay(monthlySchedule.getImsCumulativePay());
            }
        });
        invPlanYearYwService.updateBatchById(planYearYws);
        return BaseResponse.ok();
    }
}