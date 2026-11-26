package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthSearchRequest;
import com.jpmsoft.common.model.response.InvestmentControl.*;
import com.jpmsoft.common.model.response.Project.PreProjectStreamlineResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvCompleteMonthMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlySchedule;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYear;
import com.jpmsoft.projectProducer.pojo.entity.investment.OperationRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.investment.*;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Collator;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvCompleteMonthImpl extends ServiceImpl<InvCompleteMonthMapper, InvCompleteMonth> implements IInvCompleteMonthService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IInvMonthlyScheduleService invMonthlyScheduleService;
    @Resource
    private IInvPlanYearService invPlanYearService;

    @Autowired
    private IPreProjectService preProjectService;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IInvCompleteMonthService owner;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysDepartmentService iSysDepartmentService;
    @Resource
    private IOperationRecordService operationRecordService;
    @Resource
    private IInvMonthVersionService invMonthVersionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvCompleteMonth(InvCompleteMonthRequest request) {
        String getWriteUserInfo = sysConfigService.getConfigByCode("getWriteUserInfo");
        int buttonValue = 0;
        if (ValidateUtil.isNotEmpty(getWriteUserInfo)) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(getWriteUserInfo);
                JsonNode buttonNode = jsonNode.get("button");
                if (buttonNode != null && buttonNode.isInt()) {
                    buttonValue = buttonNode.asInt();
                } else {
                    log.info("基础配置 ： getWriteUserInfo " + "配置有问题");
                }
            } catch (JsonProcessingException e) {
                log.error("解析 JSON 字符串失败", e);
            }
        }
        InvCompleteMonth entity = new InvCompleteMonth();
        BeanUtils.copyProperties(request, entity);
        if (!checkProjectInvCompleteMonth(entity.getPId(), entity.getConId(), entity.getIcmReportingYear(), entity.getIcmReportingMonth(), entity.getIcmId()))
            return BaseResponse.fail(ResMsg.icmReportingMonthExists);
        if (buttonValue == 1) {
            entity.setIcmCreateUid(entity.getIcmCreateUid());
            entity.setIcmCreateUname(entity.getIcmCreateUname());
        }
        entity.setIcmMonthlyInvestment(MathUtil.DecimalMultiplicationInt(entity.getIcmMonthlyInvestment(), 10000));
        entity.setIcmAnnualInvestment(MathUtil.DecimalMultiplicationInt(entity.getIcmAnnualInvestment(), 10000));
        entity.setIcmBudgetInvestment(MathUtil.DecimalMultiplicationInt(entity.getIcmBudgetInvestment(), 10000));

        entity.setIcmLanInvAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmLanInvAmt(), 10000));
        entity.setIcmLanExpAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmLanExpAmt(), 10000));
        entity.setIcmDeviceInvAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmDeviceInvAmt(), 10000));
        entity.setIcmDeviceExpAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmDeviceExpAmt(), 10000));
        entity.setIcmInfrastructureInvAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmInfrastructureInvAmt(), 10000));
        entity.setIcmInfrastructureExpAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmInfrastructureExpAmt(), 10000));
        entity.setIcmOtherInvAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmOtherInvAmt(), 10000));
        entity.setIcmOtherExpAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmOtherExpAmt(), 10000));
        entity.setIcmPayComposition(MathUtil.DecimalMultiplicationInt(entity.getIcmPayComposition(), 10000));
        entity.setIcmOutputValue(MathUtil.DecimalMultiplicationInt(entity.getIcmOutputValue(), 10000));
        entity.setIcmCompletionValue(MathUtil.DecimalMultiplicationInt(entity.getIcmCompletionValue(), 10000));

        entity.setIcmActualCompletion(MathUtil.DecimalMultiplicationInt(entity.getIcmActualCompletion(), 10000));
        entity.setIcmBuildProject(MathUtil.DecimalMultiplicationInt(entity.getIcmBuildProject(), 10000));
        entity.setIcmInstallProject(MathUtil.DecimalMultiplicationInt(entity.getIcmInstallProject(), 10000));
        entity.setIcmEquipmentPurchase(MathUtil.DecimalMultiplicationInt(entity.getIcmEquipmentPurchase(), 10000));
        entity.setIcmOtherExpenses(MathUtil.DecimalMultiplicationInt(entity.getIcmOtherExpenses(), 10000));
        entity.setIcmActualReport(MathUtil.DecimalMultiplicationInt(entity.getIcmActualReport(), 10000));
        entity.setIcmMonthInvestTask(MathUtil.DecimalMultiplicationInt(entity.getIcmMonthInvestTask(), 10000));
        entity.setIcmLagInvest(MathUtil.DecimalMultiplicationInt(entity.getIcmLagInvest(), 10000));
        entity.setIcmCumulativeInvest(MathUtil.DecimalMultiplicationInt(entity.getIcmCumulativeInvest(), 10000));
        entity.setIcmNotReported(MathUtil.DecimalMultiplicationInt(entity.getIcmNotReported(), 10000));
        entity.setMonthCumulativeInv(MathUtil.DecimalMultiplicationInt(entity.getMonthCumulativeInv(), 10000));

        if (ValidateUtil.isEmpty(entity.getIcmId())) {
            entity.setIcmCreateTime(new Date());
            entity.setIcmId(GenerateUUID.getUUID());
            entity.setTransferStatus("01");
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getIcmId(), request.gettId(), entity.getIcmState() + "", entity.getIcmCreateUid(), entity.getIcmCreateUname()
                    , entity.getIcmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getIcmImg()), entity.getIcmId());
        } else {
            InvCompleteMonth model = baseMapper.selectById(request.getIcmId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getIcmState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setIcmState(model.getIcmState());
            entity.setIcmStateTime(model.getIcmStateTime());
            entity.setIcmCreateTime(model.getIcmCreateTime());
            if (buttonValue == 0) {
                entity.setIcmCreateUid(model.getIcmCreateUid());
                entity.setIcmCreateUname(model.getIcmCreateUname());
            }

            // TODO 时间校验判断，时间限制选择当月及之间年月，当月过后时间不可选择
            // // 当前选择年月
            // Calendar nowCal = Calendar.getInstance();
            // nowCal.set(Calendar.YEAR, Integer.parseInt(request.getIcmReportingYear()));
            // nowCal.set(Calendar.MONTH, Integer.parseInt(request.getIcmReportingMonth()) - 1);
            // nowCal.set(Calendar.DAY_OF_MONTH, 1);
            // nowCal.set(Calendar.HOUR_OF_DAY, 0);
            // nowCal.set(Calendar.MINUTE, 0);
            // nowCal.set(Calendar.SECOND, 0);
            // nowCal.set(Calendar.MILLISECOND, 0);
            // Date nowMonth = nowCal.getTime(); // 当前月份的起始时间
            //
            // // 计划开工年月
            // Date planStartTime = entity.getPlanStartTime();
            // // 计划开工年月的起始时间
            // Calendar planCal = Calendar.getInstance();
            // planCal.setTime(planStartTime);
            // planCal.set(Calendar.DAY_OF_MONTH, 1);
            // planCal.set(Calendar.HOUR_OF_DAY, 0);
            // planCal.set(Calendar.MINUTE, 0);
            // planCal.set(Calendar.SECOND, 0);
            // planCal.set(Calendar.MILLISECOND, 0);
            // Date planStartMonth = planCal.getTime();
            //
            // // 计划完工年月
            // Date planEndTime = entity.getPlanEndTime();
            // // 计划完工年月的起始时间
            // Calendar planCal1 = Calendar.getInstance();
            // planCal.setTime(planEndTime);
            // planCal.set(Calendar.DAY_OF_MONTH, 1);
            // planCal.set(Calendar.HOUR_OF_DAY, 0);
            // planCal.set(Calendar.MINUTE, 0);
            // planCal.set(Calendar.SECOND, 0);
            // planCal.set(Calendar.MILLISECOND, 0);
            // Date planEndMonth = planCal1.getTime();
            //
            // // 实际开工年月
            // Date actualStartTime = entity.getActualStartTime();
            // // 实际开工年月的起始时间
            // Calendar actualStartCal = Calendar.getInstance();
            // actualStartCal.setTime(actualStartTime);
            // actualStartCal.set(Calendar.DAY_OF_MONTH, 1);
            // actualStartCal.set(Calendar.HOUR_OF_DAY, 0);
            // actualStartCal.set(Calendar.MINUTE, 0);
            // actualStartCal.set(Calendar.SECOND, 0);
            // actualStartCal.set(Calendar.MILLISECOND, 0);
            //
            // // 实际完工年月
            // Date actualEndTime = entity.getActualEndTime();
            // // 实际完工年月的起始时间
            // Calendar actualCompleteCal = Calendar.getInstance();
            // actualCompleteCal.setTime(actualEndTime);
            // actualCompleteCal.set(Calendar.DAY_OF_MONTH, 1);
            // actualCompleteCal.set(Calendar.HOUR_OF_DAY, 0);
            // actualCompleteCal.set(Calendar.MINUTE, 0);
            // actualCompleteCal.set(Calendar.SECOND, 0);
            // actualCompleteCal.set(Calendar.MILLISECOND, 0);
            //
            // // 判断输入的开工完工时间是否合理
            // int planStartCompared = nowMonth.compareTo(planStartMonth);
            // int actualStartCompared = planStartMonth.compareTo(actualStartTime);
            // int planEndCompared = nowMonth.compareTo(planEndMonth);
            // int actualEndCompared = planEndMonth.compareTo(actualEndTime);
            // if ()

            // String currentMonthStart = entity.getCurrentMonthStart(); // 当前月份是否开工
            // String currentMonthComplete = entity.getCurrentMonthComplete(); // 当前月份是否完工

            // if (ValidateUtil.isNotEmpty(currentMonthStart) && ValidateUtil.isEmpty(entity.getPlanCompleteResult())) {
            //     // 处理plan_complete_result赋值
            //     Date planStartTime = entity.getPlanStartTime(); // 计划开工年月
            //     if (planStartTime != null) {
            //         // 计划开工年月的起始时间
            //         Calendar planCal = Calendar.getInstance();
            //         planCal.setTime(planStartTime);
            //         planCal.set(Calendar.DAY_OF_MONTH, 1);
            //         planCal.set(Calendar.HOUR_OF_DAY, 0);
            //         planCal.set(Calendar.MINUTE, 0);
            //         planCal.set(Calendar.SECOND, 0);
            //         planCal.set(Calendar.MILLISECOND, 0);
            //         Date planStartMonth = planCal.getTime();
            //
            //         // 比较当前月份和计划开工年月
            //         int compared = nowMonth.compareTo(planStartMonth);
            //
            //         if (compared > 0) { // 当前选择年月大于计划开工年月
            //             if (StringUtils.equals("0", currentMonthStart)) {
            //                 // 滞后未完成（01）
            //                 entity.setPlanCompleteResult("01");
            //             } else {
            //                 // 滞后完成（02）
            //                 entity.setPlanCompleteResult("02");
            //             }
            //         } else if (compared == 0) { // 当前选择年月等于计划开工年月
            //             if (StringUtils.equals("1", currentMonthStart)) {
            //                 // 正常完成（03）
            //                 entity.setPlanCompleteResult("03");
            //             } else {
            //                 // 滞后未完成（01）
            //                 entity.setPlanCompleteResult("01");
            //             }
            //         } else { // 当前选择年月小于计划开工年月
            //             if (StringUtils.equals("1", currentMonthStart)) {
            //                 // 提前完成（04）
            //                 entity.setPlanCompleteResult("04");
            //             } else {
            //                 // entity.setPlanCompleteResult("01");
            //                 // 不特殊显示
            //                 entity.setPlanCompleteResult("05");
            //             }
            //         }
            //     } else {
            //         entity.setPlanCompleteResult("01");
            //     }
            // }

            // if (ValidateUtil.isNotEmpty(currentMonthComplete) && ValidateUtil.isEmpty(entity.getCompleteResult())) {
            //     // 处理complete_result赋值
            //     Date planEndTime = entity.getPlanEndTime(); // 计划完工年月
            //     if (planEndTime != null) {
            //         // 计划完工年月的起始时间
            //         Calendar planCal = Calendar.getInstance();
            //         planCal.setTime(planEndTime);
            //         planCal.set(Calendar.DAY_OF_MONTH, 1);
            //         planCal.set(Calendar.HOUR_OF_DAY, 0);
            //         planCal.set(Calendar.MINUTE, 0);
            //         planCal.set(Calendar.SECOND, 0);
            //         planCal.set(Calendar.MILLISECOND, 0);
            //         Date planEndMonth = planCal.getTime();
            //
            //         // 比较当前月份和计划完工年月
            //         int compared = nowMonth.compareTo(planEndMonth);
            //
            //         if (compared > 0) { // 当前选择年月大于计划开工年月
            //             if (StringUtils.equals("0", currentMonthComplete)) {
            //                 // 滞后未完成（01）
            //                 entity.setCompleteResult("01");
            //             } else {
            //                 // 滞后完成（02）
            //                 entity.setCompleteResult("02");
            //             }
            //         } else if (compared == 0) { // 当前选择年月等于计划开工年月
            //             if (StringUtils.equals("1", currentMonthComplete)) {
            //                 // 正常完成（03）
            //                 entity.setCompleteResult("03");
            //             } else {
            //                 // 滞后未完成（01）
            //                 entity.setCompleteResult("01");
            //             }
            //         } else { // 当前选择年月小于计划开工年月
            //             if (StringUtils.equals("1", currentMonthComplete)) {
            //                 // 提前完成（04）
            //                 entity.setCompleteResult("04");
            //             } else {
            //                 // entity.setCompleteResult("01");
            //                 // 不特殊显示
            //                 entity.setCompleteResult("05");
            //             }
            //         }
            //     } else {
            //         entity.setCompleteResult("01");
            //     }
            // }

            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getIcmId(), request.gettId(), entity.getIcmState() + "", entity.getIcmCreateUid(), entity.getIcmCreateUname()
                    , entity.getIcmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getIcmImg()), Arrays.asList(request.getFileIds(), request.getIcmImgIds()), entity.getIcmId());
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    TransferStatusUpdateRequest updateRequest = new TransferStatusUpdateRequest();
                    updateRequest.setIds(Collections.singletonList(entity.getIcmId()));
                    updateRequest.setTransferStatus(entity.getTransferStatus());
                    owner.completeSubsequentInvCompleteMonth(updateRequest);
                    owner.refreshIcmAnnualInvestment(Collections.singletonList(entity.getIpvId()));
                }
            });
        }
        //更新同项目、同年份下的实际开工、完工时间, 为null时，更新为null
        // if (StringUtils.isNotBlank(entity.getCurrentMonthStart())) {
        //     this.baseMapper.updateActualStartTime(entity.getPId(), entity.getIcmReportingYear(), entity.getActualStartTime());
        // }
        // if (StringUtils.isNotBlank(entity.getCurrentMonthComplete())) {
        //     this.baseMapper.updateActualEndTime(entity.getPId(), entity.getIcmReportingYear(), entity.getActualEndTime());
        // }

        //同步更新后续月份的年度累计完成投资额， 年度累计投资完成率
        //年度计划投资额
        LambdaQueryWrapper<InvPlanYear> invPlanYearLambdaQueryWrapper = new LambdaQueryWrapper<>();
        invPlanYearLambdaQueryWrapper.eq(InvPlanYear::getPId, entity.getPId());
        invPlanYearLambdaQueryWrapper.eq(InvPlanYear::getIpvDeclareYear, entity.getIcmReportingYear());
        invPlanYearLambdaQueryWrapper.ne(InvPlanYear::getIpvState, ApprovalStatusEnum.Invalid.getIndex());
        List<InvPlanYear> invPlanYears = invPlanYearService.list(invPlanYearLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(invPlanYears)) {
            InvPlanYear invPlanYear = invPlanYears.get(0);
            BigDecimal ipvPlannedInvestment = invPlanYear.getIpvPlannedInvestment();
            int currentMonth = Integer.parseInt(entity.getIcmReportingMonth());
            BigDecimal currentIcmAnnualInvestment = entity.getIcmAnnualInvestment();

            //当前年份的已填报数据
            LambdaQueryWrapper<InvCompleteMonth> icmQueryWrapper = new LambdaQueryWrapper<>();
            icmQueryWrapper.eq(InvCompleteMonth::getPId, entity.getPId());
            icmQueryWrapper.eq(InvCompleteMonth::getIcmReportingYear, entity.getIcmReportingYear());
            List<InvCompleteMonth> icmList = this.list(icmQueryWrapper);
            if (!CollectionUtils.isEmpty(icmList)) {
                List<InvCompleteMonth> updateEntities = new ArrayList<>();
                for (int i = currentMonth + 1; i <= 12; i++) {
                    currentMonth = i;
                    int finalCurrentMonth = currentMonth;
                    Optional<InvCompleteMonth> first = icmList.stream().filter(m -> String.valueOf(finalCurrentMonth).equals(m.getIcmReportingMonth())).findFirst();
                    if (first.isPresent()) {
                        InvCompleteMonth invCompleteMonth = first.get();
                        //年度累计完成投资额 = 上月年度累计投资完成额 + 当月完成投资额
                        currentIcmAnnualInvestment = MathUtil.decAdd(currentIcmAnnualInvestment, invCompleteMonth.getIcmMonthlyInvestment());
                        //年度累计投资完成率 = 年度累计完成投资额/年度计划投资额
                        BigDecimal icmAnnualProportion = MathUtil.decMultiply(MathUtil.decimalDivide(currentIcmAnnualInvestment, ipvPlannedInvestment, 4, RoundingMode.HALF_UP), new BigDecimal(100));
                        invCompleteMonth.setIcmAnnualInvestment(currentIcmAnnualInvestment);
                        invCompleteMonth.setIcmAnnualProportion(icmAnnualProportion.floatValue());

                        //如果当前月 是否开工或是否完工选择了值，那则将后面月份的累计开工，累计完工也修改成对应的值
                        //后面月份的当月是否开工，当月是否完工，开工计划完成情况，完工计划完成情况更新成null
                        // if (StringUtils.isNotBlank(entity.getCurrentMonthStart())) {
                        //     invCompleteMonth.setCumulativeStart(entity.getCurrentMonthStart());
                        //     // invCompleteMonth.setCurrentMonthStart(null);
                        //     // invCompleteMonth.setPlanCompleteResult(null);
                        //     this.baseMapper.updateStartById(invCompleteMonth.getIcmId(), entity.getCurrentMonthStart());
                        // }
                        // if (StringUtils.isNotBlank(entity.getCurrentMonthComplete())) {
                        //     invCompleteMonth.setCumulativeComplete(entity.getCurrentMonthComplete());
                        //     // invCompleteMonth.setCurrentMonthComplete(null);
                        //     // invCompleteMonth.setCompleteResult(null);
                        //     this.baseMapper.updateCompleteById(invCompleteMonth.getIcmId(), entity.getCurrentMonthComplete());
                        // }

                        updateEntities.add(invCompleteMonth);
                    }
                }
                if (!CollectionUtils.isEmpty(updateEntities)) {
                    this.updateBatchById(updateEntities);
                }
            }

        }


        return BaseResponse.ok(entity.getIcmId());
    }

    @Override
    public BaseResponse getInvCompleteMonthList(InvCompleteMonthRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Icm_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Icm_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvCompleteMonthRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("uIdToken", uId).put("pName").put("icmDId").put("pId")
                .put("icmReportingMonth").put("icmReportingYear").
                put("icmState").put("pCompetentDepartment").
                put("transferStatusList").put("createdBy").
                put("noAuth").put("planCompleteResultList").
                put("completeResultList").put("monthCumulativeInvProportion").
                put("ipvTypeList").put("sortSideFiled").put("sortType");
        IPage<InvCompleteMonthResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvCompleteMonthResponse> datas = baseMapper.getInvCompleteMonthList(page, params.getQuery());
        List<InvCompleteMonthResponse> models = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            datas.forEach(data -> {
                data.setIcmMonthlyInvestment(MathUtil.DecimalDivisionInt(data.getIcmMonthlyInvestment(), 10000));
                data.setIcmAnnualInvestment(MathUtil.DecimalDivisionInt(data.getIcmAnnualInvestment(), 10000));
                data.setIcmBudgetInvestment(MathUtil.DecimalDivisionInt(data.getIcmBudgetInvestment(), 10000));
                data.setIcmLanInvAmt(MathUtil.DecimalDivisionInt(data.getIcmLanInvAmt(), 10000));
                data.setIcmLanExpAmt(MathUtil.DecimalDivisionInt(data.getIcmLanExpAmt(), 10000));
                data.setIcmDeviceInvAmt(MathUtil.DecimalDivisionInt(data.getIcmDeviceInvAmt(), 10000));
                data.setIcmDeviceExpAmt(MathUtil.DecimalDivisionInt(data.getIcmDeviceExpAmt(), 10000));
                data.setIcmInfrastructureInvAmt(MathUtil.DecimalDivisionInt(data.getIcmInfrastructureInvAmt(), 10000));
                data.setIcmInfrastructureExpAmt(MathUtil.DecimalDivisionInt(data.getIcmInfrastructureExpAmt(), 10000));
                data.setIcmOtherInvAmt(MathUtil.DecimalDivisionInt(data.getIcmOtherInvAmt(), 10000));
                data.setIcmOtherExpAmt(MathUtil.DecimalDivisionInt(data.getIcmOtherExpAmt(), 10000));
                data.setIcmPayComposition(MathUtil.DecimalDivisionInt(data.getIcmPayComposition(), 10000));
                data.setIcmOutputValue(MathUtil.DecimalDivisionInt(data.getIcmOutputValue(), 10000));
                data.setIcmCompletionValue(MathUtil.DecimalDivisionInt(data.getIcmCompletionValue(), 10000));

                data.setIcmActualCompletion(MathUtil.DecimalDivisionInt(data.getIcmActualCompletion(), 10000));
                data.setIcmBuildProject(MathUtil.DecimalDivisionInt(data.getIcmBuildProject(), 10000));
                data.setIcmInstallProject(MathUtil.DecimalDivisionInt(data.getIcmInstallProject(), 10000));
                data.setIcmEquipmentPurchase(MathUtil.DecimalDivisionInt(data.getIcmEquipmentPurchase(), 10000));
                data.setIcmOtherExpenses(MathUtil.DecimalDivisionInt(data.getIcmOtherExpenses(), 10000));
                data.setIcmActualReport(MathUtil.DecimalDivisionInt(data.getIcmActualReport(), 10000));
                data.setIcmMonthInvestTask(MathUtil.DecimalDivisionInt(data.getIcmMonthInvestTask(), 10000));
                data.setIcmLagInvest(MathUtil.DecimalDivisionInt(data.getIcmLagInvest(), 10000));
                data.setIcmCumulativeInvest(MathUtil.DecimalDivisionInt(data.getIcmCumulativeInvest(), 10000));
                data.setIcmNotReported(MathUtil.DecimalDivisionInt(data.getIcmNotReported(), 10000));
                data.setMonthCumulativeInv(MathUtil.DecimalDivisionInt(data.getMonthCumulativeInv(), 10000));
                data.setImsCumulativeInv(MathUtil.DecimalDivisionInt(data.getImsCumulativeInv(), 10000));
                /**
                 * 当月计划投资
                 */
                data.setImsPlannedInv(MathUtil.DecimalDivisionInt(data.getImsPlannedInv(), 10000));

                models.add(data);
            });
        }
        return BaseResponse.ok(models, (int) page.getTotal());
    }

    @Override
    public PageResult<InvCompleteMonthResponse> getInvCompleteMonthSearchList(InvCompleteMonthSearchRequest request, String uId) {
        PageResult<InvCompleteMonthResponse> result = new PageResult<>();
        String authSql = sysUserService.getAuthSql(null, uId, "Ipv_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Ipv_State");
        if (ValidateUtil.isEmpty(authSql)) {
            return result;
        }
        IPage<InvCompleteMonthResponse> page = new Page<>(request.getPageNum(), request.getPageSize());
        List<InvCompleteMonthResponse> list = baseMapper.queryInvCompleteMonthSearchList(page, request, authSql);
        result.setTotal(page.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public List<ClassInfoAnnotation> getInvSearchTableList() {
        List<ClassInfoAnnotation> result = new ArrayList<>();
        try {
            result = ClassInfoUtils.getClassInfo(InvCompleteMonthSearchRequest.class.getName());
        } catch (ClassNotFoundException e) {
            return result;
        }

        for (ClassInfoAnnotation classInfoAnnotation : result) {
            if (!classInfoAnnotation.getType().equals("date") && !classInfoAnnotation.getType().equals("month")) {
                if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                    List<Object> dictionaryValues;
                    if ("projectName".equals(classInfoAnnotation.getValue())) {
                        dictionaryValues = baseMapper.getProjectValueByCode(classInfoAnnotation.getDbValue());
                    } else if ("competentDepartmentName".equals(classInfoAnnotation.getValue())) {
                        dictionaryValues = baseMapper.getDepartValueByCode(classInfoAnnotation.getDbValue());
                    } else if ("reportTableNum".equals(classInfoAnnotation.getValue()) || "ipvType".equals(classInfoAnnotation.getValue())) {
                        dictionaryValues = baseMapper.getInvPlanYearValueByCode(classInfoAnnotation.getDbValue());
                    } else if ("icmReportingMonth".equals(classInfoAnnotation.getValue())) {
                        dictionaryValues = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
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
    public BaseResponse getInvCompleteMonthByIcmId(String icmId) {
        InvCompleteMonthResponse data = baseMapper.getInvCompleteMonthByIcmId(icmId);
        if (data != null) {
            InvPlanYear planYear = invPlanYearService.getById(data.getIpvId());
            if (planYear != null) {
                //总概算，总投资
                data.setIpvBudgetTotal(planYear.getIpvBudgetTotal());
                data.setIpvPlannedInvestment(planYear.getIpvPlannedInvestment());
            }
            List<InvMonthlySchedule> imsDatas = invMonthlyScheduleService.getInvMonthlyScheduleByIpvId(data.getIpvId());
            if (imsDatas != null && imsDatas.size() > 0) {
                List<InvMonthlyScheduleResponse> invMonthlySchedules = new ArrayList<>();
                imsDatas.forEach(m -> {
                    InvMonthlyScheduleResponse model = new InvMonthlyScheduleResponse();
                    model.setImsPlannedInv(m.getImsPlannedInv());
                    model.setImsPlannedPay(m.getImsPlannedPay());
                    model.setImsImgProgress(m.getImsImgProgress());
                    invMonthlySchedules.add(model);
                });
                data.setInvMonthlySchedules(invMonthlySchedules);
            }
            data.setIcmMonthlyInvestment(MathUtil.DecimalDivisionInt(data.getIcmMonthlyInvestment(), 10000));
            data.setIcmAnnualInvestment(MathUtil.DecimalDivisionInt(data.getIcmAnnualInvestment(), 10000));
            data.setIcmBudgetInvestment(MathUtil.DecimalDivisionInt(data.getIcmBudgetInvestment(), 10000));
            data.setIcmLanInvAmt(MathUtil.DecimalDivisionInt(data.getIcmLanInvAmt(), 10000));
            data.setIcmLanExpAmt(MathUtil.DecimalDivisionInt(data.getIcmLanExpAmt(), 10000));
            data.setIcmDeviceInvAmt(MathUtil.DecimalDivisionInt(data.getIcmDeviceInvAmt(), 10000));
            data.setIcmDeviceExpAmt(MathUtil.DecimalDivisionInt(data.getIcmDeviceExpAmt(), 10000));
            data.setIcmInfrastructureInvAmt(MathUtil.DecimalDivisionInt(data.getIcmInfrastructureInvAmt(), 10000));
            data.setIcmInfrastructureExpAmt(MathUtil.DecimalDivisionInt(data.getIcmInfrastructureExpAmt(), 10000));
            data.setIcmOtherInvAmt(MathUtil.DecimalDivisionInt(data.getIcmOtherInvAmt(), 10000));
            data.setIcmOtherExpAmt(MathUtil.DecimalDivisionInt(data.getIcmOtherExpAmt(), 10000));
            data.setIcmPayComposition(MathUtil.DecimalDivisionInt(data.getIcmPayComposition(), 10000));
            data.setIcmOutputValue(MathUtil.DecimalDivisionInt(data.getIcmOutputValue(), 10000));
            data.setIcmCompletionValue(MathUtil.DecimalDivisionInt(data.getIcmCompletionValue(), 10000));

            data.setIcmActualCompletion(MathUtil.DecimalDivisionInt(data.getIcmActualCompletion(), 10000));
            data.setIcmBuildProject(MathUtil.DecimalDivisionInt(data.getIcmBuildProject(), 10000));
            data.setIcmInstallProject(MathUtil.DecimalDivisionInt(data.getIcmInstallProject(), 10000));
            data.setIcmEquipmentPurchase(MathUtil.DecimalDivisionInt(data.getIcmEquipmentPurchase(), 10000));
            data.setIcmOtherExpenses(MathUtil.DecimalDivisionInt(data.getIcmOtherExpenses(), 10000));
            data.setIcmActualReport(MathUtil.DecimalDivisionInt(data.getIcmActualReport(), 10000));
            data.setIcmMonthInvestTask(MathUtil.DecimalDivisionInt(data.getIcmMonthInvestTask(), 10000));
            data.setIcmLagInvest(MathUtil.DecimalDivisionInt(data.getIcmLagInvest(), 10000));
            data.setIcmCumulativeInvest(MathUtil.DecimalDivisionInt(data.getIcmCumulativeInvest(), 10000));
            data.setIcmNotReported(MathUtil.DecimalDivisionInt(data.getIcmNotReported(), 10000));
            data.setMonthCumulativeInv(MathUtil.DecimalDivisionInt(data.getMonthCumulativeInv(), 10000));
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse canToCreateInvCompleteMonth(InvCompleteMonthRequest request) {
        if (JudgeUtil.checkObject(request.getpId(), request.getIcmReportingYear(), request.getIcmReportingMonth()))
            return BaseResponse.fail(ResMsg.notReqParam);
        return BaseResponse.ok(checkProjectInvCompleteMonth(request.getpId(), request.getConId(), request.getIcmReportingYear(), request.getIcmReportingMonth(), request.getIcmId()));
    }

    @Override
    public BaseResponse exportInvCompleteMonthStatistics(InvCompleteMonthRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("beginTime", ValidateUtil.isEmpty(request.getBeginTime()) ? null : request.getBeginTime());
        params.put("endTime", ValidateUtil.isEmpty(request.getEndTime()) ? null : request.getEndTime());
        params.put("pId", ValidateUtil.isEmpty(request.getpId()) ? null : request.getpId());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("icmDId", ValidateUtil.isEmpty(request.getIcmDId()) ? null : request.getIcmDId());
        List<InvCompleteMonthResponse> datas = baseMapper.exportInvCompleteMonthStatistics(params);
        List<InvCompleteMonthResponse> models = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            List<String> ipvIds = new ArrayList<>();
            datas.forEach(data -> {
                if (!ipvIds.contains(data.getIpvId()))
                    ipvIds.add(data.getIpvId());
            });
            List<InvMonthlyScheduleResponse> schedules = invMonthlyScheduleService.getInvMonthlyScheduleByIpvIds(ipvIds);
            datas.forEach(data -> {
                List<InvMonthlyScheduleResponse> schedule = schedules.stream().filter(n -> n.getIpvId().equals(data.getIpvId())
                        && (data.getIcmReportingMonth() != null && n.getImsMonth() == Integer.parseInt(data.getIcmReportingMonth()))).collect(Collectors.toList());
                if (schedule != null && schedule.size() > 0) {
                    data.setIcmAllBudgetInv(data.getIcmAllBudgetInv() == null ? data.getIcmBudgetInvestment() : data.getIcmAllBudgetInv().add(data.getIcmBudgetInvestment()));
                    data.setImsPlannedInv(schedule.get(0).getImsPlannedInv());
                    //当年当月 截至本月末累计计划完成投资
                    data.setImsCumulativePay(schedule.get(0).getImsCumulativePay());
                    //往年12月份时的 截至本月末累计计划完成投资
                    List<InvMonthlyScheduleResponse> bfDecember = schedules.stream().filter(n -> n.getIpvId().equals(data.getIpvId())
                            && Integer.parseInt(n.getIpvDeclareYear()) < Integer.parseInt(data.getIpvDeclareYear())
                            && n.getImsMonth().equals("12")
                    ).collect(Collectors.toList());
                    if (bfDecember != null && bfDecember.size() > 0) {
                        bfDecember.forEach(m -> {
                                    data.setImsCumulativePay(data.getImsCumulativePay().add(m.getImsCumulativePay()));
                                }
                        );
                    }
                }
                models.add(data);
            });
        }
        return BaseResponse.ok(models);
    }

    @Override
    public BigDecimal getLastIcmBudgetInvestmentByPid(String pId) {
        return baseMapper.getLastIcmBudgetInvestmentByPid(pId);
    }

    @Override
    public BigDecimal getCurrentYearIcmBudgetInvByPid(String pId, String year) {
        //查询年度计划
        return baseMapper.getCurrentYearIcmBudgetInvByPid(pId, year);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateTransferStatus(TransferStatusUpdateRequest request, String token) {
        if (CollectionUtils.isEmpty(request.getIds())) return BaseResponse.ok();
        List<InvCompleteMonth> updateEntityList = new ArrayList<>();
        List<InvCompleteMonth> entities = baseMapper.selectBatchIds(request.getIds());
        if (CollectionUtils.isEmpty(entities)) return BaseResponse.ok();
        List<OperationRecord> operationRecords = new ArrayList<>();
        LogUserInfoVo uid = redisUtils.getUserInfoByToken(token);
        for (String id : request.getIds()) {
            List<InvCompleteMonth> data = entities.stream().filter(f -> f.getIcmId().equals(id)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(data)) {
                InvCompleteMonth entity = data.stream().findFirst().get();
                operationRecords.add(operationRecordService.entity(id, uid, "InvCompleteMonth", entity.getTransferStatus(), request.getTransferStatus(), request.getOrRemarks()));
                // 回退判断
                if ((Integer.parseInt(entity.getTransferStatus()) > Integer.parseInt(request.getTransferStatus())) && Integer.parseInt(request.getTransferStatus()) <= 2) {
                    // 退回，发起人更新为空
                    entity.setIcmCreateUname(null);
                    entity.setIcmCreateUid(null);
                }
                // 如果是从保存往上提交，重新更新发起人
                if ("2".equalsIgnoreCase(entity.getTransferStatus()) && Integer.parseInt(entity.getTransferStatus()) < Integer.parseInt(request.getTransferStatus())) {
                    entity.setIcmCreateUname(uid.getName());
                    entity.setIcmCreateUid(uid.getId());
                }
                entity.setTransferStatus(request.getTransferStatus());
                updateEntityList.add(entity);
            }
        }
        if (!CollectionUtils.isEmpty(updateEntityList)) this.updateBatchById(updateEntityList);
        if (!CollectionUtils.isEmpty(operationRecords)) operationRecordService.saveBatch(operationRecords);
        List<String> ipvIds = updateEntityList.stream().map(InvCompleteMonth::getIpvId).distinct().collect(Collectors.toList());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                owner.completeSubsequentInvCompleteMonth(request);
                owner.refreshIcmAnnualInvestment(ipvIds);
                // 将最新的版本存入版本表
                // invMonthVersionService.saveInvMonthVersionByIcmIds(request.getIds());
            }
        });
        if (operationRecords.size() == 1) {
            return BaseResponse.ok(operationRecords.get(0).getId());
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getCreateFieldData(GetCreateFieldDataRequest request) {
        GetCreateFieldDataResponse result = new GetCreateFieldDataResponse();
        LambdaQueryWrapper<InvPlanYear> invPlanYearLambdaQueryWrapper = new LambdaQueryWrapper<>();
        invPlanYearLambdaQueryWrapper.eq(InvPlanYear::getPId, request.getProjectId());
        invPlanYearLambdaQueryWrapper.eq(InvPlanYear::getIpvDeclareYear, request.getIcmReportingYear());
        invPlanYearLambdaQueryWrapper.ne(InvPlanYear::getIpvState, ApprovalStatusEnum.Invalid.getIndex());
        List<InvPlanYear> invPlanYears = invPlanYearService.list(invPlanYearLambdaQueryWrapper);
        if (CollectionUtils.isEmpty(invPlanYears)) {
            return BaseResponse.fail("项目下没有" + request.getIcmReportingYear() + "年度计划！");
        }
        InvPlanYear invPlanYear = invPlanYears.get(0);
        result.setIpvNewly(invPlanYear.getIpvNewly());
        result.setIpvCompleted(invPlanYear.getIpvCompleted());

        LambdaQueryWrapper<InvCompleteMonth> invCompleteMonthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        invCompleteMonthLambdaQueryWrapper.eq(InvCompleteMonth::getPId, request.getProjectId());
        invCompleteMonthLambdaQueryWrapper.eq(InvCompleteMonth::getIcmReportingYear, request.getIcmReportingYear());
        List<InvCompleteMonth> invCompleteMonths = this.list(invCompleteMonthLambdaQueryWrapper);

        //当月是否开工  1.计划录入中“当年新开工”选择“否”或“当月是否开工”之前月度数据维护时，已选过“是”，字段置灰；
        // 2.除去上述两种情况，字段值支持单选，数据字典值“是”“否”

        //累计是否开工 自动显示，规则如下：
        //1.计划录入中，“当年新开工”选择“否”，则显示“是”
        //2.“当月是否开工”选择过“是”，则显示“是”（包括之前月度+当前月度）
        result.setCurrentMonthStartEnable(true);
        result.setCumulativeStartEnable(false);
        result.setCumulativeStart("0");
        if (null != invPlanYear.getIpvNewly() && !invPlanYear.getIpvNewly()) {
            result.setCurrentMonthStartEnable(false);
            result.setCurrentMonthStart("1");
            result.setCumulativeStart("1");
        } else {
            for (InvCompleteMonth invCompleteMonth : invCompleteMonths) {
                int newMonth = Integer.parseInt(request.getIcmReportingMonth());
                int queryMonth = Integer.parseInt(invCompleteMonth.getIcmReportingMonth());
                if ((queryMonth < newMonth) && "1".equals(invCompleteMonth.getCurrentMonthStart())) {
                    result.setCurrentMonthStartEnable(false);
                    result.setCurrentMonthStart("1");
                    result.setCumulativeStart("1");

                    //实际开工年月
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, Integer.parseInt(invCompleteMonth.getIcmReportingYear()));
                    //Calendar月份从0开始
                    cal.set(Calendar.MONTH, Integer.parseInt(invCompleteMonth.getIcmReportingMonth()) - 1);
                    result.setActualStartTime(cal.getTime());
                    break;
                }
            }
        }

        //计划开工年月 自动抓取计划录入中的数据（该字段仅针对计划录入中“当年新开工”选择“是”的项目，否则置灰）
        result.setPlanStartTimeEnable(false);
        result.setPlanCompleteResultEnable(false);
        if (invPlanYear.getIpvNewly() != null && invPlanYear.getIpvNewly()) {
            result.setPlanStartTime(invPlanYear.getIpvStart());
        }

        //当月是否完工 数据字典选择“是”或“否”，若“当月是否完工”之前已选过“是”，则字段置灰
        //累计是否完工 自动显示，规则如下：“当月是否完工”选择过“是”，则显示“是”（包括之前月度+当前月度）
        result.setCurrentMonthCompleteEnable(true);
        result.setCumulativeCompleteEnable(false);
        result.setCumulativeComplete("0");
        for (InvCompleteMonth invCompleteMonth : invCompleteMonths) {
            int newMonth = Integer.parseInt(request.getIcmReportingMonth());
            int queryMonth = Integer.parseInt(invCompleteMonth.getIcmReportingMonth());
            if ((queryMonth < newMonth) && "1".equals(invCompleteMonth.getCurrentMonthComplete())) {
                result.setCurrentMonthCompleteEnable(false);
                result.setCumulativeComplete("1");
                //实际完工年月
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.parseInt(invCompleteMonth.getIcmReportingYear()));
                //Calendar月份从0开始
                cal.set(Calendar.MONTH, Integer.parseInt(invCompleteMonth.getIcmReportingMonth()) - 1);
                result.setActualEndTime(cal.getTime());
                break;
            }
        }

        //计划完工年月 自动抓取计划录入中的数据（计划录入中，“当年完工”选择“否”的项目该字段置灰）
        result.setPlanEndTimeEnable(false);
        result.setCompleteResultEnable(false);
        if (null != invPlanYear.getIpvCompleted() && invPlanYear.getIpvCompleted()) {
            result.setPlanEndTime(invPlanYear.getIpvEnd());
        }

        //上月的年度累计完成投资额(万元)
        int lastMonth = Integer.parseInt(request.getIcmReportingMonth()) - 1;
        result.setIcmAnnualInvestment(BigDecimal.ZERO);
        for (InvCompleteMonth invCompleteMonth : invCompleteMonths) {
            if (String.valueOf(lastMonth).equals(invCompleteMonth.getIcmReportingMonth())) {
                BigDecimal icmAnnualInvestment = invCompleteMonth.getIcmAnnualInvestment();
                result.setIcmAnnualInvestment(MathUtil.DecimalDivisionInt(icmAnnualInvestment, 10000));
                break;
            }
        }

        //年度计划投资额(万元)
        result.setIpvPlannedInvestment(MathUtil.DecimalDivisionInt(invPlanYear.getIpvPlannedInvestment(), 10000));

        //X月年度累计计划投资(万元)
        LambdaQueryWrapper<InvMonthlySchedule> invMonthlyScheduleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        invMonthlyScheduleLambdaQueryWrapper.eq(InvMonthlySchedule::getIpvId, invPlanYear.getIpvId());
        invMonthlyScheduleLambdaQueryWrapper.eq(InvMonthlySchedule::getImsMonth, request.getIcmReportingMonth());
        List<InvMonthlySchedule> invMonthlySchedules = invMonthlyScheduleService.list(invMonthlyScheduleLambdaQueryWrapper);
        result.setMonthCumulativeInv(BigDecimal.ZERO);
        if (!CollectionUtils.isEmpty(invMonthlySchedules)) {
            BigDecimal imsCumulativeInv = invMonthlySchedules.get(0).getImsCumulativeInv();
            result.setMonthCumulativeInv(MathUtil.DecimalDivisionInt(imsCumulativeInv, 10000));
        }

        return BaseResponse.ok(result);
    }

    @Override
    public BaseResponse countInvCompleteMonth(InvCompleteMonthRequest request, String uId) {
        return BaseResponse.ok(getCountInvCompleteMonth(request, uId));
    }

    private InvCompleteMonthCountResponse getCountInvCompleteMonth(InvCompleteMonthRequest request, String uId) {
        if (CollectionUtils.isEmpty(request.getIpvTypeList())) {
            return null;
        }
        InvCompleteMonthCountResponse result = new InvCompleteMonthCountResponse();
        List<String> allAuthProjectId = preProjectService.getAllAuthProjectId(uId);
        if (CollectionUtils.isEmpty(allAuthProjectId)) {
            return result;
        }
        result = analysisInvCompleteMonth(request, allAuthProjectId);
        return result;
    }

    @Override
    public BaseResponse countInvCompleteMonthForAll(InvCompleteMonthRequest request, String uId) {
        return BaseResponse.ok(getCountInvCompleteMonthForAll(request, uId));
    }

    private InvCompleteMonthCountForAllResponse getCountInvCompleteMonthForAll(InvCompleteMonthRequest request, String uId) {
        InvCompleteMonthCountForAllResponse result = new InvCompleteMonthCountForAllResponse();
        if (CollectionUtils.isEmpty(request.getIpvTypeList())) {
            return result;
        }

        //二分中心（隧桥）
        String departmentIdOne = "ae3d00ae-bca1-4f02-8ecd-85dc4f0d9b13";
        //南京城建城市运营集团有限公司
        String departmentIdTwo = "ef417131-e656-42ff-956a-e0a15737e71c";
        String departmentName = "二分中心（运营）";

        List<InvCompleteMonthScoreResponse> scores = new ArrayList<>();
        List<InvCompleteMonthCountStartResponse> starts = new ArrayList<>();
        List<InvCompleteMonthCountInvResponse> investments = new ArrayList<>();
        List<InvCompleteMonthCountEndResponse> ends = new ArrayList<>();

        List<PreProjectStreamlineResponse> projectsByInv = this.baseMapper.queryProjectByInv(request.getIcmReportingYear(), request.getIpvTypeList(), request.getCompetentDepartmentList());
        Map<String, List<PreProjectStreamlineResponse>> competentDepartmentIdMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(projectsByInv)) {
            competentDepartmentIdMap = projectsByInv.stream().collect(Collectors.groupingBy(PreProjectStreamlineResponse::getpCompetentDepartment));
            boolean processFlag = false;
            for (Map.Entry<String, List<PreProjectStreamlineResponse>> entry : competentDepartmentIdMap.entrySet()) {

                InvCompleteMonthScoreResponse scoreVO = new InvCompleteMonthScoreResponse();
                InvCompleteMonthCountStartResponse startVO = new InvCompleteMonthCountStartResponse();
                InvCompleteMonthCountInvResponse invVO = new InvCompleteMonthCountInvResponse();
                InvCompleteMonthCountEndResponse endVO = new InvCompleteMonthCountEndResponse();

                String competentDepartmentId = entry.getKey();
                String competentDepartmentName = entry.getValue().get(0).getpCompetentDepartmentName();
                List<String> projectIds = entry.getValue().stream().map(PreProjectStreamlineResponse::getpId).collect(Collectors.toList());
                //二分中心（隧桥）  南京城建城市运营集团有限公司  数据合并处理
                if (competentDepartmentId.equals(departmentIdOne) || competentDepartmentId.equals(departmentIdTwo)) {
                    if (processFlag) {
                        continue;
                    }
                    competentDepartmentId = departmentIdTwo;
                    competentDepartmentName = departmentName;
                    processFlag = true;
                    if (competentDepartmentIdMap.containsKey(departmentIdOne)) {
                        projectIds.addAll(competentDepartmentIdMap.get(departmentIdOne).stream().map(PreProjectStreamlineResponse::getpId).collect(Collectors.toList()));
                    }
                    if (competentDepartmentIdMap.containsKey(departmentIdTwo)) {
                        projectIds.addAll(competentDepartmentIdMap.get(departmentIdTwo).stream().map(PreProjectStreamlineResponse::getpId).collect(Collectors.toList()));
                    }

                }
                InvCompleteMonthCountResponse countResult = analysisInvCompleteMonth(request, projectIds);

                //综合得分及排名
                scoreVO.setCompetentDepartmentId(competentDepartmentId);
                scoreVO.setCompetentDepartmentName(competentDepartmentName);


                //开工情况
                startVO.setCompetentDepartmentId(competentDepartmentId);
                startVO.setCompetentDepartmentName(competentDepartmentName);
                startVO.setCheckStartNum(countResult.getCheckStartNum());
                startVO.setPlanCumulativeStartNum(countResult.getPlanCumulativeStartNum());
                startVO.setCurrentMonthStartNum(countResult.getCurrentMonthStartNum());
                startVO.setCumulativeStartNum(countResult.getCumulativeStartNum());
                startVO.setStartPercent(countResult.getStartPercent());
                startVO.setStartPlanCompletePercent(countResult.getStartPlanCompletePercent());

                //投资情况
                invVO.setCompetentDepartmentId(competentDepartmentId);
                invVO.setCompetentDepartmentName(competentDepartmentName);
                invVO.setCheckProjectNum(countResult.getCheckProjectNum());
                invVO.setCheckInv(countResult.getCheckInv());
                invVO.setPlanCumulativeInv(countResult.getPlanCumulativeInv());
                invVO.setCurrentMonthInv(countResult.getCurrentMonthInv());
                invVO.setCumulativeInv(countResult.getCumulativeInv());
                invVO.setInvCompletePercent(countResult.getInvCompletePercent());
                invVO.setShortProjectNum(countResult.getShortProjectNum());
                invVO.setInvShortPercent(countResult.getInvShortPercent());
                invVO.setInvPlanCompletePercent(countResult.getInvPlanCompletePercent());

                //完工情况
                endVO.setCompetentDepartmentId(competentDepartmentId);
                endVO.setCompetentDepartmentName(competentDepartmentName);
                endVO.setCheckEndNum(countResult.getCheckEndNum());
                endVO.setPlanCumulativeEndNum(countResult.getPlanCumulativeEndNum());
                endVO.setCurrentMonthEndNum(countResult.getCurrentMonthEndNum());
                endVO.setCumulativeEndNum(countResult.getCumulativeEndNum());
                endVO.setEndPercent(countResult.getEndPercent());
                endVO.setEndPlanCompletePercent(countResult.getEndPlanCompletePercent());

                scores.add(scoreVO);
                starts.add(startVO);
                investments.add(invVO);
                ends.add(endVO);
            }

            starts = starts.stream().sorted((o1, o2) -> Collator.getInstance(Locale.TRADITIONAL_CHINESE).compare(o1.getCompetentDepartmentName(), o2.getCompetentDepartmentName())).collect(Collectors.toList());
            investments = investments.stream().sorted((o1, o2) -> Collator.getInstance(Locale.TRADITIONAL_CHINESE).compare(o1.getCompetentDepartmentName(), o2.getCompetentDepartmentName())).collect(Collectors.toList());
            ends = ends.stream().sorted((o1, o2) -> Collator.getInstance(Locale.TRADITIONAL_CHINESE).compare(o1.getCompetentDepartmentName(), o2.getCompetentDepartmentName())).collect(Collectors.toList());

            //开工情况合计数据、开工贡献度
            InvCompleteMonthCountStartResponse startCount = new InvCompleteMonthCountStartResponse();
            startCount.setCompetentDepartmentId("开工情况合计");
            startCount.setCompetentDepartmentName("合计");
            startCount.setCheckStartNum(starts.stream().map(InvCompleteMonthCountStartResponse::getCheckStartNum).reduce(0L, Long::sum));
            startCount.setPlanCumulativeStartNum(starts.stream().map(InvCompleteMonthCountStartResponse::getPlanCumulativeStartNum).reduce(0L, Long::sum));
            startCount.setCurrentMonthStartNum(starts.stream().map(InvCompleteMonthCountStartResponse::getCurrentMonthStartNum).reduce(0L, Long::sum));
            startCount.setCumulativeStartNum(starts.stream().map(InvCompleteMonthCountStartResponse::getCumulativeStartNum).reduce(0L, Long::sum));
            startCount.setStartPercent(DataFormatUtil.getPercent(startCount.getCumulativeStartNum(), startCount.getCheckStartNum()));
            startCount.setStartPlanCompletePercent(DataFormatUtil.getPercent(startCount.getCumulativeStartNum(), startCount.getPlanCumulativeStartNum()));
            startCount.setStartContributionPercent("100.00%");
            //开工贡献度=各项目主管单位的当月开工数÷当月开工数合计
            BigDecimal startContributionPercentCount = BigDecimal.ZERO;
            for (InvCompleteMonthCountStartResponse start : starts) {
                start.setStartContributionPercent(DataFormatUtil.getPercent(start.getCurrentMonthStartNum(), startCount.getCurrentMonthStartNum()));
                startContributionPercentCount = startContributionPercentCount.add(new BigDecimal(Double.parseDouble(start.getStartContributionPercent().replace("%", "")))).setScale(2, RoundingMode.HALF_UP);
            }
            //所有项目主管单位开工贡献度之和
            startCount.setStartContributionPercent(startContributionPercentCount.toString() + "%");

            starts.add(startCount);

            //投资情况合计数据、投资贡献度
            InvCompleteMonthCountInvResponse invCount = new InvCompleteMonthCountInvResponse();
            invCount.setCompetentDepartmentId("投资情况合计");
            invCount.setCompetentDepartmentName("合计");
            invCount.setCheckProjectNum(investments.stream().map(InvCompleteMonthCountInvResponse::getCheckProjectNum).reduce(0L, Long::sum));
            invCount.setCheckInv(investments.stream().map(InvCompleteMonthCountInvResponse::getCheckInv).reduce(BigDecimal.ZERO, BigDecimal::add));
            invCount.setPlanCumulativeInv(investments.stream().map(InvCompleteMonthCountInvResponse::getPlanCumulativeInv).reduce(BigDecimal.ZERO, BigDecimal::add));
            invCount.setCurrentMonthInv(investments.stream().map(InvCompleteMonthCountInvResponse::getCurrentMonthInv).reduce(BigDecimal.ZERO, BigDecimal::add));
            invCount.setCumulativeInv(investments.stream().map(InvCompleteMonthCountInvResponse::getCumulativeInv).reduce(BigDecimal.ZERO, BigDecimal::add));
            invCount.setInvCompletePercent(DataFormatUtil.getPercentOfBigDecimal(invCount.getCumulativeInv(), invCount.getCheckInv()));
            invCount.setShortProjectNum(investments.stream().map(InvCompleteMonthCountInvResponse::getShortProjectNum).reduce(0L, Long::sum));

            invCount.setInvShortPercent(DataFormatUtil.getPercent(invCount.getShortProjectNum(), invCount.getCheckProjectNum()));
            invCount.setInvPlanCompletePercent(DataFormatUtil.getPercentOfBigDecimal(invCount.getCumulativeInv(), invCount.getPlanCumulativeInv()));
            //投资贡献度=各项目主管单位的当月投资额÷当月投资额合计
            BigDecimal invContributionPercentCount = BigDecimal.ZERO;
            for (InvCompleteMonthCountInvResponse inv : investments) {
                inv.setInvContributionPercent(DataFormatUtil.getPercentOfBigDecimal(inv.getCurrentMonthInv(), invCount.getCurrentMonthInv()));
                invContributionPercentCount = invContributionPercentCount.add(new BigDecimal(Double.parseDouble(inv.getInvContributionPercent().replace("%", "")))).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            //所有项目主管单位投资贡献度之和
            invCount.setInvContributionPercent(invContributionPercentCount.toString() + "%");

            investments.add(invCount);

            //完工情况合计数据
            InvCompleteMonthCountEndResponse endCount = new InvCompleteMonthCountEndResponse();
            endCount.setCompetentDepartmentId("完工情况合计");
            endCount.setCompetentDepartmentName("合计");
            endCount.setCheckEndNum(ends.stream().map(InvCompleteMonthCountEndResponse::getCheckEndNum).reduce(0L, Long::sum));
            endCount.setPlanCumulativeEndNum(ends.stream().map(InvCompleteMonthCountEndResponse::getPlanCumulativeEndNum).reduce(0L, Long::sum));
            endCount.setCurrentMonthEndNum(ends.stream().map(InvCompleteMonthCountEndResponse::getCurrentMonthEndNum).reduce(0L, Long::sum));
            endCount.setCumulativeEndNum(ends.stream().map(InvCompleteMonthCountEndResponse::getCumulativeEndNum).reduce(0L, Long::sum));
            endCount.setEndPercent(DataFormatUtil.getPercent(endCount.getCumulativeEndNum(), endCount.getCheckEndNum()));
            endCount.setEndPlanCompletePercent(DataFormatUtil.getPercent(endCount.getCumulativeEndNum(), endCount.getPlanCumulativeEndNum()));

            ends.add(endCount);

            BigDecimal startScoreSum = BigDecimal.ZERO;
            BigDecimal startScoreCount = BigDecimal.ZERO;

            BigDecimal endScoreSum = BigDecimal.ZERO;
            BigDecimal endScoreCount = BigDecimal.ZERO;

            //综合得分及排名
            for (InvCompleteMonthScoreResponse score : scores) {
                BigDecimal startComprehensiveScore = BigDecimal.ZERO;
                BigDecimal invComprehensiveScore = BigDecimal.ZERO;
                BigDecimal endComprehensiveScore = BigDecimal.ZERO;

                //开工综合得分
                //开工率×80+开工贡献度×20（1-9月）
                //=开工率×100（10-12月）
                //（若该项目主管单位的“考核开工数”=0，则开工综合得分=其余项目主管单位开工综合得分的平均值）
                Optional<InvCompleteMonthCountStartResponse> startVO = starts.stream().filter(e -> score.getCompetentDepartmentId().equals(e.getCompetentDepartmentId())).findFirst();
                if (startVO.isPresent()) {
                    InvCompleteMonthCountStartResponse startScore = startVO.get();
                    if (startScore.getCheckStartNum() > 0) {
                        BigDecimal startPercent = BigDecimal.valueOf(Double.parseDouble(startScore.getStartPercent().replace("%", ""))).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        BigDecimal startContributionPercent = BigDecimal.valueOf(Double.parseDouble(startScore.getStartContributionPercent().replace("%", ""))).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        if (Integer.parseInt(request.getIcmReportingMonth()) <= 9 && Integer.parseInt(request.getIcmReportingMonth()) >= 1) {
                            startComprehensiveScore = (startPercent.multiply(BigDecimal.valueOf(80))).add(startContributionPercent.multiply(BigDecimal.valueOf(20))).setScale(2, RoundingMode.HALF_UP);
                            startScoreSum = startScoreSum.add(startComprehensiveScore);
                            startScoreCount = startScoreCount.add(BigDecimal.valueOf(1));
                        }
                        if (Integer.parseInt(request.getIcmReportingMonth()) <= 12 && Integer.parseInt(request.getIcmReportingMonth()) >= 10) {
                            startComprehensiveScore = startPercent.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
                            startScoreSum = startScoreSum.add(startComprehensiveScore);
                            startScoreCount = startScoreCount.add(BigDecimal.valueOf(1));
                        }
                    }
                }

                //投资综合得分
                Optional<InvCompleteMonthCountInvResponse> invVO = investments.stream().filter(e -> score.getCompetentDepartmentId().equals(e.getCompetentDepartmentId())).findFirst();
                if (invVO.isPresent()) {
                    InvCompleteMonthCountInvResponse invScore = invVO.get();
                    BigDecimal invCompletePercent = BigDecimal.valueOf(Double.parseDouble(invScore.getInvCompletePercent().replace("%", ""))).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    BigDecimal invContributionPercent = BigDecimal.valueOf(Double.parseDouble(invScore.getInvContributionPercent().replace("%", ""))).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    BigDecimal invShortPercent = BigDecimal.valueOf(Double.parseDouble(invScore.getInvShortPercent().replace("%", ""))).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    //年度投资完成率×80+投资贡献度×20-投资短板率×10
                    invComprehensiveScore = (invCompletePercent.multiply(BigDecimal.valueOf(80))).add(invContributionPercent.multiply(BigDecimal.valueOf(20))).subtract(invShortPercent.multiply(BigDecimal.valueOf(10))).setScale(2, RoundingMode.HALF_UP);
                }

                //完工综合得分
                //完工率×100
                //（若该项目主管单位的“考核完工数”=0，则完工综合得分=其余项目主管单位完工综合得分的平均值）
                Optional<InvCompleteMonthCountEndResponse> endVO = ends.stream().filter(e -> score.getCompetentDepartmentId().equals(e.getCompetentDepartmentId())).findFirst();
                if (endVO.isPresent()) {
                    InvCompleteMonthCountEndResponse endScore = endVO.get();
                    if (endScore.getCheckEndNum() > 0) {
                        BigDecimal endPercent = BigDecimal.valueOf(Double.parseDouble(endScore.getEndPercent().replace("%", ""))).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        endComprehensiveScore = endPercent.multiply(BigDecimal.valueOf(100));
                        endScoreSum = endScoreSum.add(startComprehensiveScore);
                        endScoreCount = endScoreCount.add(BigDecimal.valueOf(1));
                    }
                }

                score.setStartComprehensiveScore(startComprehensiveScore);
                score.setInvComprehensiveScore(invComprehensiveScore);
                score.setEndComprehensiveScore(endComprehensiveScore);
            }

            //处理考核开工数=0 OR 考核完工数=0的得分数据、总分计算
            for (InvCompleteMonthScoreResponse score : scores) {
                BigDecimal startComprehensiveScore = score.getStartComprehensiveScore();
                BigDecimal invComprehensiveScore = score.getInvComprehensiveScore();
                BigDecimal endComprehensiveScore = score.getEndComprehensiveScore();
                BigDecimal totalScore = BigDecimal.ZERO;

                //开工综合得分
                //开工率×80+开工贡献度×20（1-9月）
                //=开工率×100（10-12月）
                //（若该项目主管单位的“考核开工数”=0，则开工综合得分=其余项目主管单位开工综合得分的平均值）
                Optional<InvCompleteMonthCountStartResponse> startVO = starts.stream().filter(e -> score.getCompetentDepartmentId().equals(e.getCompetentDepartmentId())).findFirst();
                if (startVO.isPresent()) {
                    InvCompleteMonthCountStartResponse startScore = startVO.get();
                    if (startScore.getCheckStartNum() == 0) {
                        if (startScoreCount.compareTo(BigDecimal.valueOf(0)) == 0) {
                            startComprehensiveScore = BigDecimal.valueOf(0);
                        } else {
                            startComprehensiveScore = startScoreSum.divide(startScoreCount, 2, RoundingMode.HALF_UP);
                        }
                    }
                }

                //完工综合得分
                //完工率×100
                //（若该项目主管单位的“考核完工数”=0，则完工综合得分=其余项目主管单位完工综合得分的平均值）
                Optional<InvCompleteMonthCountEndResponse> endVO = ends.stream().filter(e -> score.getCompetentDepartmentId().equals(e.getCompetentDepartmentId())).findFirst();
                if (endVO.isPresent()) {
                    InvCompleteMonthCountEndResponse endScore = endVO.get();
                    if (endScore.getCheckEndNum() == 0) {
                        if (endScoreCount.compareTo(BigDecimal.valueOf(0)) == 0) {
                            endComprehensiveScore = BigDecimal.valueOf(0);
                        } else {
                            endComprehensiveScore = endScoreSum.divide(endScoreCount, 2, RoundingMode.HALF_UP);
                        }
                    }
                }

                score.setStartComprehensiveScore(startComprehensiveScore);
                score.setInvComprehensiveScore(invComprehensiveScore);
                score.setEndComprehensiveScore(endComprehensiveScore);

                //总分
                //=开工综合得分×0.4+投资综合得分×0.4+完工综合得分×0.2（2-6月）
                //=开工综合得分×0.3+投资综合得分×0.5+完工综合得分×0.2（7-12月）
                if (Integer.parseInt(request.getIcmReportingMonth()) <= 6 && Integer.parseInt(request.getIcmReportingMonth()) >= 2) {
                    totalScore = (startComprehensiveScore.multiply(BigDecimal.valueOf(0.4))).add(invComprehensiveScore.multiply(BigDecimal.valueOf(0.4))).add(endComprehensiveScore.multiply(BigDecimal.valueOf(0.2))).setScale(2, RoundingMode.HALF_UP);
                }
                if (Integer.parseInt(request.getIcmReportingMonth()) <= 12 && Integer.parseInt(request.getIcmReportingMonth()) >= 7) {
                    totalScore = (startComprehensiveScore.multiply(BigDecimal.valueOf(0.3))).add(invComprehensiveScore.multiply(BigDecimal.valueOf(0.5))).add(endComprehensiveScore.multiply(BigDecimal.valueOf(0.2))).setScale(2, RoundingMode.HALF_UP);
                }
                score.setTotalScore(totalScore);
            }
        }

        //一月份不排名，选择1月的话，综合得分及排名这张表：（1）不显示；或者（2）没有任何数据
        if ("1".equals(request.getIcmReportingMonth())) {
            result.setScores(new ArrayList<>());
        } else {
            //排序，rank赋值
            List<InvCompleteMonthScoreResponse> sortedScores = scores.stream().sorted(Comparator.comparing(InvCompleteMonthScoreResponse::getTotalScore).reversed()).collect(Collectors.toList());
            for (int i = 0; i < sortedScores.size(); i++) {
                sortedScores.get(i).setRank(i + 1);
            }
            result.setScores(sortedScores);
        }
        result.setStarts(starts);
        result.setInvestments(investments);
        result.setEnds(ends);
        return result;
    }

    @Override
    public Workbook countInvCompleteMonthExportExcel(InvCompleteMonthRequest request, String uId) {
        String[] startRowTitle = new String[]{"考核开工数", "计划累计开工数", "当月开工数", "累计开工数", "开工率", "开工计划完成率"};
        String[] invRowTitle = new String[]{"考核项目数", "考核投资额(万元)", "计划累计投资额(万元)", "当月投资额(万元)", "累计投资额(万元)", "投资完成率", "投资短板率", "投资计划完成率"};
        String[] endRowTitle = new String[]{"考核完工数", "计划累计完工数", "当月完工数", "累计完工数", "完工率", "完工计划完成率"};

        InvCompleteMonthCountResponse countInvCompleteMonth = getCountInvCompleteMonth(request, uId);
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("统计分析表");
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        titleStyle.setFont(font);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell = row.createCell(0);
        cell.setCellValue("统计分析表");
        cell.setCellStyle(titleStyle);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 7);
        sheet.addMergedRegion(cellRangeAddress);

        rowNum += 2;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("开工情况");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 5);
        sheet.addMergedRegion(cellRangeAddress);
        cell.setCellStyle(style);
        rowNum++;
        row = sheet.createRow(rowNum++);
        for (int i = 0; i < startRowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(startRowTitle[i]);
            cell.setCellStyle(style);
        }

        if (countInvCompleteMonth != null) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(countInvCompleteMonth.getCheckStartNum());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(countInvCompleteMonth.getPlanCumulativeStartNum());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(countInvCompleteMonth.getCurrentMonthStartNum());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(countInvCompleteMonth.getCumulativeStartNum());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(countInvCompleteMonth.getStartPercent());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(countInvCompleteMonth.getStartPlanCompletePercent());
            cell.setCellStyle(style);
        }


        rowNum++;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("投资情况");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellStyle(style);
        cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 7);
        sheet.addMergedRegion(cellRangeAddress);

        rowNum++;
        row = sheet.createRow(rowNum++);
        for (int i = 0; i < invRowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(invRowTitle[i]);
            cell.setCellStyle(style);
        }

        if (countInvCompleteMonth != null) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(countInvCompleteMonth.getCheckProjectNum());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(countInvCompleteMonth.getCheckInv().toString());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(countInvCompleteMonth.getPlanCumulativeInv().toString());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(countInvCompleteMonth.getCurrentMonthInv().toString());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(countInvCompleteMonth.getCumulativeInv().toString());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(countInvCompleteMonth.getInvCompletePercent());
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue(countInvCompleteMonth.getInvShortPercent());
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue(countInvCompleteMonth.getInvPlanCompletePercent());
            cell.setCellStyle(style);
        }

        rowNum++;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("完工情况");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 5);
        sheet.addMergedRegion(cellRangeAddress);
        rowNum++;
        row = sheet.createRow(rowNum++);
        for (int i = 0; i < endRowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(endRowTitle[i]);
            cell.setCellStyle(style);
        }

        if (countInvCompleteMonth != null) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(countInvCompleteMonth.getCheckEndNum());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(countInvCompleteMonth.getPlanCumulativeEndNum());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(countInvCompleteMonth.getCurrentMonthEndNum());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(countInvCompleteMonth.getCumulativeEndNum());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(countInvCompleteMonth.getEndPercent());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(countInvCompleteMonth.getEndPlanCompletePercent());
            cell.setCellStyle(style);
        }

        return workbook;
    }

    @Override
    public Workbook invCompleteMonthListExportExcel(InvCompleteMonthRequest request, String uId) {
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();
        String authSql = sysUserService.getAuthSql(null, uId, "Icm_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return workbook;
        QueryMap<InvCompleteMonthRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("icmDId").put("pId")
                .put("icmReportingMonth").put("icmReportingYear").put("icmState")
                .put("pCompetentDepartment").put("transferStatusList").put("createdBy")
                .put("noAuth").put("planCompleteResultList").put("completeResultList")
                .put("monthCumulativeInvProportion").put("ipvTypeList").put("sortSideFiled")
                .put("sortType").put("warningFilter").put("ipvStage").put("ipvPType")
                .put("ipvConstructionStage").put("pNameList").put("pTypeList").put("cjJbrList")
                .put("pCompetentDepartmentNameList").put("pDepartmentNameList")
                .put("ipvStageList").put("createUnameList").put("stateList")
                .put("ipvConstructionStatusList");
        IPage<InvCompleteMonthResponse> page = new Page<>(request.getPage(), request.getSize());
        DepartmentPCjConfDTO gwConf = sysConfigService.getDepartmentPCjConf();
        params.put("jbr", gwConf.getJtJbr());
        List<InvCompleteMonthResponse> datas = baseMapper.getInvCompleteMonthList(page, params.getQuery());
        List<InvCompleteMonthResponse> models = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            datas.forEach(data -> {
                data.setIcmMonthlyInvestment(MathUtil.DecimalDivisionInt(data.getIcmMonthlyInvestment(), 10000));
                data.setIcmAnnualInvestment(MathUtil.DecimalDivisionInt(data.getIcmAnnualInvestment(), 10000));
                data.setIcmBudgetInvestment(MathUtil.DecimalDivisionInt(data.getIcmBudgetInvestment(), 10000));
                data.setIcmLanInvAmt(MathUtil.DecimalDivisionInt(data.getIcmLanInvAmt(), 10000));
                data.setIcmLanExpAmt(MathUtil.DecimalDivisionInt(data.getIcmLanExpAmt(), 10000));
                data.setIcmDeviceInvAmt(MathUtil.DecimalDivisionInt(data.getIcmDeviceInvAmt(), 10000));
                data.setIcmDeviceExpAmt(MathUtil.DecimalDivisionInt(data.getIcmDeviceExpAmt(), 10000));
                data.setIcmInfrastructureInvAmt(MathUtil.DecimalDivisionInt(data.getIcmInfrastructureInvAmt(), 10000));
                data.setIcmInfrastructureExpAmt(MathUtil.DecimalDivisionInt(data.getIcmInfrastructureExpAmt(), 10000));
                data.setIcmOtherInvAmt(MathUtil.DecimalDivisionInt(data.getIcmOtherInvAmt(), 10000));
                data.setIcmOtherExpAmt(MathUtil.DecimalDivisionInt(data.getIcmOtherExpAmt(), 10000));
                data.setIcmPayComposition(MathUtil.DecimalDivisionInt(data.getIcmPayComposition(), 10000));
                data.setIcmOutputValue(MathUtil.DecimalDivisionInt(data.getIcmOutputValue(), 10000));
                data.setIcmCompletionValue(MathUtil.DecimalDivisionInt(data.getIcmCompletionValue(), 10000));

                data.setIcmActualCompletion(MathUtil.DecimalDivisionInt(data.getIcmActualCompletion(), 10000));
                data.setIcmBuildProject(MathUtil.DecimalDivisionInt(data.getIcmBuildProject(), 10000));
                data.setIcmInstallProject(MathUtil.DecimalDivisionInt(data.getIcmInstallProject(), 10000));
                data.setIcmEquipmentPurchase(MathUtil.DecimalDivisionInt(data.getIcmEquipmentPurchase(), 10000));
                data.setIcmOtherExpenses(MathUtil.DecimalDivisionInt(data.getIcmOtherExpenses(), 10000));
                data.setIcmActualReport(MathUtil.DecimalDivisionInt(data.getIcmActualReport(), 10000));
                data.setIcmMonthInvestTask(MathUtil.DecimalDivisionInt(data.getIcmMonthInvestTask(), 10000));
                data.setIcmLagInvest(MathUtil.DecimalDivisionInt(data.getIcmLagInvest(), 10000));
                data.setIcmCumulativeInvest(MathUtil.DecimalDivisionInt(data.getIcmCumulativeInvest(), 10000));
                data.setIcmNotReported(MathUtil.DecimalDivisionInt(data.getIcmNotReported(), 10000));
                data.setMonthCumulativeInv(MathUtil.DecimalDivisionInt(data.getMonthCumulativeInv(), 10000));
                /**
                 * 当月计划投资
                 */
                data.setImsPlannedInv(MathUtil.DecimalDivisionInt(data.getImsPlannedInv(), 10000));

                data.setIpvPlannedInvestment(MathUtil.DecimalDivisionInt(data.getIpvPlannedInvestment(), 10000));

                data.setPInvTotal(MathUtil.DecimalDivisionInt(data.getPInvTotal(), 10000));

                data.setpApproveBudget(MathUtil.DecimalDivisionInt(data.getpApproveBudget(), 10000));
                data.setpRecordAmount(MathUtil.DecimalDivisionInt(data.getpRecordAmount(), 10000));
                data.setpApprovalAmount(MathUtil.DecimalDivisionInt(data.getpApprovalAmount(), 10000));
                models.add(data);
                System.out.println(data.getpId() + "," + data.getPInvTotal() + "," + data.getpApproveBudget() + "," + data.getpRecordAmount() + "," + data.getpApprovalAmount());
            });
        }
        String[] rowTitle = new String[]{"序号", "项目名称", "项目概况", "项目分类", "年度建设状态", "年度计划类别", "责任单位", "事业部/分支机构", "发起人", "集团工程部经办人", "当年计划投资(万元)", "计划开工年月", "计划完工年月", "项目总投资(万元)", "实际开工年月", "实际完工年月", "当月完成投资额(万元)", "年度累计完成投资额(万元)", "年度累计投资完成率(%)", "形象进度", "形象进度补充说明", "状态"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("年度投资计划执行");
        List<Integer> wideColumns = Arrays.asList(1, 3, 5, 6, 21); // 宽列的索引
        List<Integer> superColumns = Arrays.asList(2, 20);
        for (int i = 0; i < rowTitle.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 2000);
            } else if (wideColumns.contains(i)) {
                sheet.setColumnWidth(i, 7000);
            } else if (superColumns.contains(i)) {
                sheet.setColumnWidth(i, 10000);
            } else {
                sheet.setColumnWidth(i, 4000);
            }
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        CellStyle stylePercent = workbook.createCellStyle();
        stylePercent.setAlignment(HorizontalAlignment.CENTER);
        stylePercent.setVerticalAlignment(VerticalAlignment.CENTER);
        stylePercent.setBorderTop(BorderStyle.MEDIUM);
        stylePercent.setBorderBottom(BorderStyle.MEDIUM);
        stylePercent.setBorderLeft(BorderStyle.MEDIUM);
        stylePercent.setBorderRight(BorderStyle.MEDIUM);
        DataFormat dataFormat = workbook.createDataFormat();
        stylePercent.setDataFormat(dataFormat.getFormat("0.00%"));

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dateStyle.setBorderTop(BorderStyle.MEDIUM);
        dateStyle.setBorderBottom(BorderStyle.MEDIUM);
        dateStyle.setBorderLeft(BorderStyle.MEDIUM);
        dateStyle.setBorderRight(BorderStyle.MEDIUM);
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/MM"));

        CellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(HorizontalAlignment.LEFT);
        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        textStyle.setBorderTop(BorderStyle.MEDIUM);
        textStyle.setBorderBottom(BorderStyle.MEDIUM);
        textStyle.setBorderLeft(BorderStyle.MEDIUM);
        textStyle.setBorderRight(BorderStyle.MEDIUM);
        textStyle.setWrapText(true);

        CellStyle textCenterStyle = workbook.createCellStyle();
        textCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        textCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        textCenterStyle.setBorderTop(BorderStyle.MEDIUM);
        textCenterStyle.setBorderBottom(BorderStyle.MEDIUM);
        textCenterStyle.setBorderLeft(BorderStyle.MEDIUM);
        textCenterStyle.setBorderRight(BorderStyle.MEDIUM);
        textCenterStyle.setWrapText(true);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }

        if (!CollectionUtils.isEmpty(models)) {
            Map<String, String> completeResultMap = new HashMap<>();
            completeResultMap.put("01", "滞后未完成");
            completeResultMap.put("02", "滞后完成");
            completeResultMap.put("03", "正常完成");
            completeResultMap.put("04", "提前完成");

            Map<String, String> transferStatusMap = new HashMap<>();
            transferStatusMap.put("01", "未提交");
            transferStatusMap.put("02", "被项目主管单位经办人驳回");
            transferStatusMap.put("03", "项目主管单位经办人待审批");
            transferStatusMap.put("04", "被集团工程部经办人驳回");
            transferStatusMap.put("05", "集团工程部经办人待审批");
            transferStatusMap.put("06", "完结");
            JSONArray NDTZXMFL = DictionaryUtils.dictList("JPMsoft_NDTZXMFL");
            JSONArray NDJHLB = DictionaryUtils.dictList("JPMsoft_NDJHLB");
            JSONArray SJSPZT = DictionaryUtils.dictList("JPMsoft_SJSPZT");
            JSONArray XXJD = DictionaryUtils.dictList("JPMsoft_XXJD");
            JSONArray NDJSZT = DictionaryUtils.dictList("JPMsoft_NDJSZT");
            for (InvCompleteMonthResponse model : models) {
                row = sheet.createRow(rowNum++);

                cell = row.createCell(0);
                String reportTableNum = model.getReportTableNum();
                if (StringUtils.isBlank(reportTableNum)) {
                    cell.setCellValue("");
                } else {
                    cell.setCellValue(reportTableNum);
                }
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue(model.getPName());
                cell.setCellStyle(textCenterStyle);

                cell = row.createCell(2);
                cell.setCellValue(StringUtils.isBlank(model.getPOverview()) ? "/" : model.getPOverview());
                cell.setCellStyle(textStyle);

                cell = row.createCell(3);
                cell.setCellValue(StringUtils.isBlank(model.getIpvPType()) ? "/" : DictionaryUtils.processDictList(NDTZXMFL, model.getIpvPType()));
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(StringUtils.isBlank(model.getIpvConstructionStatus()) ? "/" : DictionaryUtils.processDictList(NDJSZT, model.getIpvConstructionStatus()));
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(StringUtils.isBlank(model.getIpvConstructionStage()) ? "/" : DictionaryUtils.processDictList(NDJHLB, model.getIpvConstructionStage().split(",")));
                cell.setCellStyle(textCenterStyle);

                cell = row.createCell(6);
                cell.setCellValue(StringUtils.isBlank(model.getpCompetentDepartmentNames()) ? "/" : model.getpCompetentDepartmentNames());
                cell.setCellStyle(textCenterStyle);

                cell = row.createCell(7);
                cell.setCellValue(StringUtils.isBlank(model.getpDepartmentName()) ? "/" : model.getpDepartmentName());
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue(StringUtils.isBlank(model.getIcmCreateUname()) ? "/" : model.getIcmCreateUname());
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(StringUtils.isBlank(model.getCjJbr()) ? "/" : model.getCjJbr());
                cell.setCellStyle(style);

                cell = row.createCell(10);
                cell.setCellValue(model.getIpvPlannedInvestment().doubleValue());
                cell.setCellStyle(style);

                if (model.getIpvStart() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(model.getIpvStart());
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cell = row.createCell(11);
                    cell.setCellValue(cal.getTime());
                    cell.setCellStyle(dateStyle);
                } else {
                    cell = row.createCell(11);
                    cell.setCellValue("/");
                    cell.setCellStyle(dateStyle);
                }

                if (model.getIpvEnd() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(model.getIpvEnd());
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cell = row.createCell(12);
                    cell.setCellValue(cal.getTime());
                    cell.setCellStyle(dateStyle);
                } else {
                    cell = row.createCell(12);
                    cell.setCellValue("/");
                    cell.setCellStyle(dateStyle);
                }

                cell = row.createCell(13);
                if (model.getPInvTotal().compareTo(BigDecimal.ZERO) != 0) {
                    cell.setCellValue(model.getPInvTotal().doubleValue());
                } else {
                    String type = model.getpProjectFormType();
                    if ("01".equalsIgnoreCase(type)) {
                        cell.setCellValue(model.getpApproveBudget().doubleValue());
                    } else if ("02".equalsIgnoreCase(type)) {
                        cell.setCellValue(model.getpRecordAmount().doubleValue());
                    } else if ("03".equalsIgnoreCase(type)){
                        cell.setCellValue(model.getpApprovalAmount().doubleValue());
                    } else {
                        cell.setCellValue("/");
                    }
                }
                cell.setCellStyle(style);

                if (model.getActualStartTime() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(model.getActualStartTime());
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cell = row.createCell(14);
                    cell.setCellValue(cal.getTime());
                    cell.setCellStyle(dateStyle);
                } else {
                    cell = row.createCell(14);
                    cell.setCellValue("/");
                    cell.setCellStyle(dateStyle);
                }

                if (model.getActualEndTime() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(model.getActualEndTime());
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cell = row.createCell(15);
                    cell.setCellValue(cal.getTime());
                    cell.setCellStyle(dateStyle);
                } else {
                    cell = row.createCell(15);
                    cell.setCellValue("/");
                    cell.setCellStyle(dateStyle);
                }

//                cell = row.createCell(6);
//                Integer cellValue = null;
//                if (StringUtils.isNotEmpty(model.getReportTableNum())) {
//                    try {
//                        cellValue = Integer.parseInt(model.getReportTableNum());
//                    } catch (NumberFormatException e){
//                        log.error("不规范的转化" + e);
//                    }
//                }
//                cell.setCellValue(model.getReportTableNum());
//                cell.setCellStyle(style);

//                cell = row.createCell(4);
//                cell.setCellValue(Integer.parseInt(model.getIcmReportingYear()));
//                cell.setCellStyle(style);

//                cell = row.createCell(5);
//                cell.setCellValue(Integer.parseInt(model.getIcmReportingMonth()));
//                cell.setCellStyle(style);

//                cell = row.createCell(7);
//                cell.setCellValue(StringUtils.isBlank(model.getCurrentMonthStart()) ? "/" : "1".equals(model.getCurrentMonthStart()) ? "是" : "否");
//                cell.setCellStyle(style);
//
//                cell = row.createCell(8);
//                cell.setCellValue(StringUtils.isBlank(model.getCumulativeStart()) ? "/" : "1".equals(model.getCumulativeStart()) ? "是" : "否");
//                cell.setCellStyle(style);


//                cell = row.createCell(8);
//                cell.setCellValue(null == model.getPlanStartTime() ? "/" : DateFormatUtil.dateToString(model.getPlanStartTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
//                cell.setCellStyle(style);


//                cell = row.createCell(7);
//                cell.setCellValue(StringUtils.isBlank(model.getPlanCompleteResult()) ? "/" : completeResultMap.get(model.getPlanCompleteResult()));
//                cell.setCellStyle(style);

//                cell = row.createCell(10);
//                cell.setCellValue(StringUtils.isBlank(model.getCurrentMonthComplete()) ? "/" : "1".equals(model.getCurrentMonthComplete()) ? "是" : "否");
//                cell.setCellStyle(style);
//
//                cell = row.createCell(11);
//                cell.setCellValue(StringUtils.isBlank(model.getCumulativeComplete()) ? "/" : "1".equals(model.getCumulativeComplete()) ? "是" : "否");
//                cell.setCellStyle(style);

//                cell = row.createCell(9);
//                cell.setCellValue(null == model.getPlanEndTime() ? "/" : DateFormatUtil.dateToString(model.getPlanEndTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
//                cell.setCellStyle(style);

//                cell = row.createCell(11);
//                cell.setCellValue(StringUtils.isBlank(model.getCompleteResult()) ? "/" : completeResultMap.get(model.getCompleteResult()));
//                cell.setCellStyle(style);

                cell = row.createCell(16);
                cell.setCellValue(model.getIcmMonthlyInvestment().doubleValue());
                cell.setCellStyle(style);

                cell = row.createCell(17);
                cell.setCellValue(model.getIcmAnnualInvestment().doubleValue());
                cell.setCellStyle(style);

                cell = row.createCell(18);
                cell.setCellValue(MathUtil.floatDivideFloat(model.getIcmAnnualProportion(), 100F));
                cell.setCellStyle(stylePercent);

//                cell = row.createCell(12);
//                cell.setCellValue(model.getMonthCumulativeInv().doubleValue());
//                cell.setCellStyle(style);
//
//                cell = row.createCell(13);
////                cell.setCellValue(null == model.getMonthCumulativeInvProportion() ? "/" : String.format("%.2f", model.getMonthCumulativeInvProportion()) + "%");
//                cell.setCellValue(model.getMonthCumulativeInvProportion() / 100);
//                cell.setCellStyle(stylePercent);

                cell = row.createCell(19);
                cell.setCellValue(StringUtils.isBlank(model.getIcmImageProgress()) ? "/" : DictionaryUtils.processDictList(XXJD, model.getIcmImageProgress()));
                cell.setCellStyle(style);

                cell = row.createCell(20);
                cell.setCellValue(model.getIcmImgProgress());
                cell.setCellStyle(textStyle);

                cell = row.createCell(21);
                cell.setCellValue(StringUtils.isBlank(model.getTransferStatus()) ? "/" : DictionaryUtils.processDictList(SJSPZT, model.getTransferStatus()));
                cell.setCellStyle(style);
            }
        }
        return workbook;
    }

    @Override
    public Workbook countInvCompleteMonthForAllExportExcel(InvCompleteMonthRequest request, String uId) {
        String[] scoreRowTitle = new String[]{"单位名称", "开工综合得分", "投资综合得分", "完工综合得分", "总分", "排名"};
        String[] startRowTitle = new String[]{"单位名称", "考核开工数", "计划累计开工数", "当月开工数", "累计开工数", "开工率", "开工贡献度", "开工计划完成率"};
        String[] invRowTitle = new String[]{"单位名称", "考核项目数", "考核投资额(万元)", "计划累计投资额(万元)", "当月投资额(万元)", "累计投资额(万元)", "投资完成率", "投资贡献度", "投资短板率", "投资计划完成率"};
        String[] endRowTitle = new String[]{"单位名称", "考核完工数", "计划累计完工数", "当月完工数", "累计完工数", "完工率", "完工计划完成率"};

        InvCompleteMonthCountForAllResponse countInvCompleteMonthForAll = getCountInvCompleteMonthForAll(request, uId);
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("内部企业晒榜");
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 5000);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        titleStyle.setFont(font);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell = row.createCell(0);
        cell.setCellValue("内部企业晒榜");
        cell.setCellStyle(titleStyle);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 9);
        sheet.addMergedRegion(cellRangeAddress);

        rowNum += 2;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("综合得分及排名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 5);
        sheet.addMergedRegion(cellRangeAddress);
        cell.setCellStyle(style);
        rowNum++;
        row = sheet.createRow(rowNum++);
        for (int i = 0; i < scoreRowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(scoreRowTitle[i]);
            cell.setCellStyle(style);
        }

        if (countInvCompleteMonthForAll != null && org.apache.commons.collections4.CollectionUtils.isNotEmpty(countInvCompleteMonthForAll.getScores())) {
            for (InvCompleteMonthScoreResponse data : countInvCompleteMonthForAll.getScores()) {
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(data.getCompetentDepartmentName());
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(data.getStartComprehensiveScore().toString());
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue(data.getInvComprehensiveScore().toString());
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue(data.getEndComprehensiveScore().toString());
                cell.setCellStyle(style);
                cell = row.createCell(4);
                cell.setCellValue(data.getTotalScore().toString());
                cell.setCellStyle(style);
                cell = row.createCell(5);
                cell.setCellValue(data.getRank());
                cell.setCellStyle(style);
            }
        }

        rowNum++;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("开工情况");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellStyle(style);
        cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 7);
        sheet.addMergedRegion(cellRangeAddress);
        cell.setCellStyle(style);
        rowNum++;
        row = sheet.createRow(rowNum++);
        for (int i = 0; i < startRowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(startRowTitle[i]);
            cell.setCellStyle(style);
        }

        if (countInvCompleteMonthForAll != null && org.apache.commons.collections4.CollectionUtils.isNotEmpty(countInvCompleteMonthForAll.getStarts())) {
            for (InvCompleteMonthCountStartResponse data : countInvCompleteMonthForAll.getStarts()) {
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(data.getCompetentDepartmentName());
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(data.getCheckStartNum());
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue(data.getPlanCumulativeStartNum());
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue(data.getCurrentMonthStartNum());
                cell.setCellStyle(style);
                cell = row.createCell(4);
                cell.setCellValue(data.getCumulativeStartNum());
                cell.setCellStyle(style);
                cell = row.createCell(5);
                cell.setCellValue(data.getStartPercent());
                cell.setCellStyle(style);
                cell = row.createCell(6);
                cell.setCellValue(data.getStartContributionPercent());
                cell.setCellStyle(style);
                cell = row.createCell(7);
                cell.setCellValue(data.getStartPlanCompletePercent());
                cell.setCellStyle(style);
            }
        }


        rowNum++;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("投资情况");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellStyle(style);
        cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 9);
        sheet.addMergedRegion(cellRangeAddress);

        rowNum++;
        row = sheet.createRow(rowNum++);
        for (int i = 0; i < invRowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(invRowTitle[i]);
            cell.setCellStyle(style);
        }

        if (countInvCompleteMonthForAll != null && org.apache.commons.collections4.CollectionUtils.isNotEmpty(countInvCompleteMonthForAll.getScores())) {
            for (InvCompleteMonthCountInvResponse data : countInvCompleteMonthForAll.getInvestments()) {
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(data.getCompetentDepartmentName());
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(data.getCheckProjectNum());
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue(data.getCheckInv().toString());
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue(data.getPlanCumulativeInv().toString());
                cell.setCellStyle(style);
                cell = row.createCell(4);
                cell.setCellValue(data.getCurrentMonthInv().toString());
                cell.setCellStyle(style);
                cell = row.createCell(5);
                cell.setCellValue(data.getCumulativeInv().toString());
                cell.setCellStyle(style);
                cell = row.createCell(6);
                cell.setCellValue(data.getInvCompletePercent());
                cell.setCellStyle(style);
                cell = row.createCell(7);
                cell.setCellValue(data.getInvContributionPercent());
                cell.setCellStyle(style);
                cell = row.createCell(8);
                cell.setCellValue(data.getInvShortPercent());
                cell.setCellStyle(style);
                cell = row.createCell(9);
                cell.setCellValue(data.getInvPlanCompletePercent());
                cell.setCellStyle(style);
            }
        }

        rowNum++;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("完工情况");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellStyle(style);
        cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 0, 7);
        sheet.addMergedRegion(cellRangeAddress);
        rowNum++;
        row = sheet.createRow(rowNum++);
        for (int i = 0; i < endRowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(endRowTitle[i]);
            cell.setCellStyle(style);
        }

        if (countInvCompleteMonthForAll != null && org.apache.commons.collections4.CollectionUtils.isNotEmpty(countInvCompleteMonthForAll.getScores())) {
            for (InvCompleteMonthCountEndResponse data : countInvCompleteMonthForAll.getEnds()) {
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(data.getCompetentDepartmentName());
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(data.getCheckEndNum());
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue(data.getPlanCumulativeEndNum());
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue(data.getCurrentMonthEndNum());
                cell.setCellStyle(style);
                cell = row.createCell(4);
                cell.setCellValue(data.getCumulativeEndNum());
                cell.setCellStyle(style);
                cell = row.createCell(5);
                cell.setCellValue(data.getEndPercent());
                cell.setCellStyle(style);
                cell = row.createCell(6);
                cell.setCellValue(data.getEndPlanCompletePercent());
                cell.setCellStyle(style);
            }
        }
        return workbook;
    }

    @Override
    public List<InvCompleteMonth> getActualInvestment(List<String> ipvIds) {
        if (CollectionUtils.isEmpty(ipvIds)) return null;

        LambdaQueryWrapper<InvCompleteMonth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InvCompleteMonth::getIpvId, ipvIds);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public BaseResponse createOrUpdateInvCompleteMonthXz(InvCompleteMonthRequest request) {
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(request.getIcmReportingYear()), Integer.parseInt(request.getIcmReportingMonth()));
        LocalDate startDate = yearMonth.atDay(1); // 获取该月的第一天
        LocalDate endDate = yearMonth.atEndOfMonth(); // 获取该月的最后一天
        QueryWrapper<InvPlanYear> wrapper = new QueryWrapper<>();
        wrapper.le("ipv_start", endDate);
        wrapper.ge("ipv_end", startDate);
        wrapper.eq("P_Id", request.getpId());
        wrapper.ne("Ipv_State", ApprovalStatusEnum.Invalid.getIndex());
        if (invPlanYearService.getBaseMapper().selectCount(wrapper) > 0) {
            QueryWrapper<InvCompleteMonth> squery = new QueryWrapper<>();
            squery.eq("Icm_Reporting_Year", request.getIcmReportingYear());
            squery.eq("Icm_Reporting_Month", request.getIcmReportingMonth());
            squery.eq("ipv_id", request.getIpvId());
            squery.ne("Icm_State", 3);
            InvCompleteMonth month = baseMapper.selectOne(squery);
            if (month != null) {
                if (ValidateUtil.isNotEmpty(request.getIcmId())) {
                    if (!month.getIcmId().equals(request.getIcmId())) {
                        return BaseResponse.fail("本月已填报"); // 本月已填报
                    }
                } else {
                    return BaseResponse.fail("本月已填报");  //本月已填报
                }
            }
        } else {
            return BaseResponse.fail("不在时间范围内");
        }

        InvCompleteMonth entity = new InvCompleteMonth();
        BeanUtils.copyProperties(request, entity);
        if (!checkProjectInvCompleteMonth(entity.getPId(), entity.getConId(), entity.getIcmReportingYear(), entity.getIcmReportingMonth(), entity.getIcmId()))
            return BaseResponse.fail(ResMsg.icmReportingMonthExists);
        entity.setIcmMonthlyInvestment(MathUtil.DecimalMultiplicationInt(entity.getIcmMonthlyInvestment(), 10000));
        entity.setIcmAnnualInvestment(MathUtil.DecimalMultiplicationInt(entity.getIcmAnnualInvestment(), 10000));
        entity.setIcmBudgetInvestment(MathUtil.DecimalMultiplicationInt(entity.getIcmBudgetInvestment(), 10000));

        entity.setIcmLanInvAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmLanInvAmt(), 10000));
        entity.setIcmLanExpAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmLanExpAmt(), 10000));
        entity.setIcmDeviceInvAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmDeviceInvAmt(), 10000));
        entity.setIcmDeviceExpAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmDeviceExpAmt(), 10000));
        entity.setIcmInfrastructureInvAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmInfrastructureInvAmt(), 10000));
        entity.setIcmInfrastructureExpAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmInfrastructureExpAmt(), 10000));
        entity.setIcmOtherInvAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmOtherInvAmt(), 10000));
        entity.setIcmOtherExpAmt(MathUtil.DecimalMultiplicationInt(entity.getIcmOtherExpAmt(), 10000));
        entity.setIcmPayComposition(MathUtil.DecimalMultiplicationInt(entity.getIcmPayComposition(), 10000));
        entity.setIcmOutputValue(MathUtil.DecimalMultiplicationInt(entity.getIcmOutputValue(), 10000));
        entity.setIcmCompletionValue(MathUtil.DecimalMultiplicationInt(entity.getIcmCompletionValue(), 10000));

        entity.setIcmActualCompletion(MathUtil.DecimalMultiplicationInt(entity.getIcmActualCompletion(), 10000));
        entity.setIcmBuildProject(MathUtil.DecimalMultiplicationInt(entity.getIcmBuildProject(), 10000));
        entity.setIcmInstallProject(MathUtil.DecimalMultiplicationInt(entity.getIcmInstallProject(), 10000));
        entity.setIcmEquipmentPurchase(MathUtil.DecimalMultiplicationInt(entity.getIcmEquipmentPurchase(), 10000));
        entity.setIcmOtherExpenses(MathUtil.DecimalMultiplicationInt(entity.getIcmOtherExpenses(), 10000));
        entity.setIcmActualReport(MathUtil.DecimalMultiplicationInt(entity.getIcmActualReport(), 10000));
        entity.setIcmMonthInvestTask(MathUtil.DecimalMultiplicationInt(entity.getIcmMonthInvestTask(), 10000));
        entity.setIcmLagInvest(MathUtil.DecimalMultiplicationInt(entity.getIcmLagInvest(), 10000));
        entity.setIcmCumulativeInvest(MathUtil.DecimalMultiplicationInt(entity.getIcmCumulativeInvest(), 10000));
        entity.setIcmNotReported(MathUtil.DecimalMultiplicationInt(entity.getIcmNotReported(), 10000));
        entity.setMonthCumulativeInv(MathUtil.DecimalMultiplicationInt(entity.getMonthCumulativeInv(), 10000));

        if (ValidateUtil.isEmpty(entity.getIcmId())) {
            entity.setIcmCreateTime(new Date());
            entity.setIcmId(GenerateUUID.getUUID());
            entity.setTransferStatus("01");
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getIcmId(), request.gettId(), entity.getIcmState() + "", entity.getIcmCreateUid(), entity.getIcmCreateUname()
                    , entity.getIcmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getIcmImg()), entity.getIcmId());
        } else {
            InvCompleteMonth model = baseMapper.selectById(request.getIcmId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getIcmState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setIcmState(model.getIcmState());
            entity.setIcmStateTime(model.getIcmStateTime());
            entity.setIcmCreateTime(model.getIcmCreateTime());
            entity.setIcmCreateUid(model.getIcmCreateUid());
            entity.setIcmCreateUname(model.getIcmCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getIcmId(), request.gettId(), entity.getIcmState() + "", entity.getIcmCreateUid(), entity.getIcmCreateUname()
                    , entity.getIcmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getIcmImg()), Arrays.asList(request.getFileIds(), request.getIcmImgIds()), entity.getIcmId());
        }
        //更新同项目、同年份下的实际开工、完工时间, 为null时，更新为null
        if (StringUtils.isNotBlank(entity.getCurrentMonthStart())) {
            this.baseMapper.updateActualStartTime(entity.getPId(), entity.getIcmReportingYear(), entity.getActualStartTime());
        }
        if (StringUtils.isNotBlank(entity.getCurrentMonthComplete())) {
            this.baseMapper.updateActualEndTime(entity.getPId(), entity.getIcmReportingYear(), entity.getActualEndTime());
        }

        //同步更新后续月份的年度累计完成投资额， 年度累计投资完成率
        //年度计划投资额
        LambdaQueryWrapper<InvPlanYear> invPlanYearLambdaQueryWrapper = new LambdaQueryWrapper<>();
        invPlanYearLambdaQueryWrapper.eq(InvPlanYear::getPId, entity.getPId());
        invPlanYearLambdaQueryWrapper.eq(InvPlanYear::getIpvDeclareYear, entity.getIcmReportingYear());
        invPlanYearLambdaQueryWrapper.ne(InvPlanYear::getIpvState, ApprovalStatusEnum.Invalid.getIndex());
        List<InvPlanYear> invPlanYears = invPlanYearService.list(invPlanYearLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(invPlanYears)) {
            InvPlanYear invPlanYear = invPlanYears.get(0);
            BigDecimal ipvPlannedInvestment = invPlanYear.getIpvPlannedInvestment();
            int currentMonth = Integer.parseInt(entity.getIcmReportingMonth());
            BigDecimal currentIcmAnnualInvestment = entity.getIcmAnnualInvestment();

            //当前年份的已填报数据
            LambdaQueryWrapper<InvCompleteMonth> icmQueryWrapper = new LambdaQueryWrapper<>();
            icmQueryWrapper.eq(InvCompleteMonth::getPId, entity.getPId());
            icmQueryWrapper.eq(InvCompleteMonth::getIcmReportingYear, entity.getIcmReportingYear());
            List<InvCompleteMonth> icmList = this.list(icmQueryWrapper);
            if (!CollectionUtils.isEmpty(icmList)) {
                List<InvCompleteMonth> updateEntities = new ArrayList<>();
                for (int i = currentMonth + 1; i <= 12; i++) {
                    currentMonth = i;
                    int finalCurrentMonth = currentMonth;
                    Optional<InvCompleteMonth> first = icmList.stream().filter(m -> String.valueOf(finalCurrentMonth).equals(m.getIcmReportingMonth())).findFirst();
                    if (first.isPresent()) {
                        InvCompleteMonth invCompleteMonth = first.get();
                        //年度累计完成投资额 = 上月年度累计投资完成额 + 当月完成投资额
                        currentIcmAnnualInvestment = currentIcmAnnualInvestment.add(invCompleteMonth.getIcmMonthlyInvestment());
                        //年度累计投资完成率 = 年度累计完成投资额/年度计划投资额
                        BigDecimal icmAnnualProportion = currentIcmAnnualInvestment.divide(ipvPlannedInvestment, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                        invCompleteMonth.setIcmAnnualInvestment(currentIcmAnnualInvestment);
                        invCompleteMonth.setIcmAnnualProportion(icmAnnualProportion.floatValue());

                        //如果当前月 是否开工或是否完工选择了值，那则将后面月份的累计开工，累计完工也修改成对应的值
                        //后面月份的当月是否开工，当月是否完工，开工计划完成情况，完工计划完成情况更新成null
                        if (StringUtils.isNotBlank(entity.getCurrentMonthStart())) {
                            invCompleteMonth.setCumulativeStart(entity.getCurrentMonthStart());
                            invCompleteMonth.setCurrentMonthStart(null);
                            invCompleteMonth.setPlanCompleteResult(null);
                            this.baseMapper.updateStartById(invCompleteMonth.getIcmId(), entity.getCurrentMonthStart());
                        }
                        if (StringUtils.isNotBlank(entity.getCurrentMonthComplete())) {
                            invCompleteMonth.setCumulativeComplete(entity.getCurrentMonthComplete());
                            invCompleteMonth.setCurrentMonthComplete(null);
                            invCompleteMonth.setCompleteResult(null);
                            this.baseMapper.updateCompleteById(invCompleteMonth.getIcmId(), entity.getCurrentMonthComplete());
                        }

                        updateEntities.add(invCompleteMonth);
                    }
                }
                if (!CollectionUtils.isEmpty(updateEntities)) {
                    this.updateBatchById(updateEntities);
                }
            }
        }
        return BaseResponse.ok(entity.getIcmId());

    }

    @Override
    public BaseResponse getNearCompleteMonthData(GetCreateFieldDataRequest request) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(request.getIcmReportingYear()), Integer.parseInt(request.getIcmReportingMonth()) - 1, 1);
        calendar.add(Calendar.MONTH, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", " where 1=1 ");
        params.put("icmReportingYear", year);
        params.put("icmReportingMonth", month);
        params.put("pId", request.getProjectId());
        params.put("transferStatusList", Arrays.asList("05", "06"));
        List<InvCompleteMonthResponse> datas = baseMapper.getInvCompleteMonthList(null, params);
        InvCompleteMonthResponse data = null;
        if (CollectionUtil.isNotEmpty(datas)) {
            data = datas.get(0);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getMonthlyInvestmentSum(String pid, String year, Integer month) {
        LambdaQueryWrapper<InvCompleteMonth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InvCompleteMonth::getPId, pid);
        queryWrapper.eq(InvCompleteMonth::getIcmReportingYear, year);
        queryWrapper.le(InvCompleteMonth::getIcmReportingMonth, month);
        List<InvCompleteMonth> data = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<BigDecimal> money = data.stream().map(InvCompleteMonth::getIcmMonthlyInvestment).filter(ValidateUtil::isNotNull).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(money))
                return BaseResponse.ok(money.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        return BaseResponse.ok(BigDecimal.ZERO);
    }

    private InvCompleteMonthCountResponse analysisInvCompleteMonth(InvCompleteMonthRequest request, List<String> allAuthProjectId) {
        InvCompleteMonthCountResponse result = new InvCompleteMonthCountResponse();

        LambdaQueryWrapper<InvPlanYear> invPlanYearQueryWrapper = new LambdaQueryWrapper<>();
        invPlanYearQueryWrapper.eq(InvPlanYear::getIpvDeclareYear, request.getIcmReportingYear());
        invPlanYearQueryWrapper.eq(InvPlanYear::getIpvState, ApprovalStatusEnum.Already_Over.getIndex());
        invPlanYearQueryWrapper.in(!CollectionUtils.isEmpty(request.getIpvTypeList()), InvPlanYear::getIpvPType, request.getIpvTypeList());
        invPlanYearQueryWrapper.in(InvPlanYear::getPId, allAuthProjectId);
        List<InvPlanYear> invPlanYears = invPlanYearService.list(invPlanYearQueryWrapper);

        List<InvMonthlySchedule> invMonthlySchedules = new ArrayList<>();
        if (!CollectionUtils.isEmpty(invPlanYears)) {
            List<String> ipvIds = invPlanYears.stream().map(InvPlanYear::getIpvId).collect(Collectors.toList());
            LambdaQueryWrapper<InvMonthlySchedule> invMonthlyScheduleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            invMonthlyScheduleLambdaQueryWrapper.in(InvMonthlySchedule::getIpvId, ipvIds);
            invMonthlyScheduleLambdaQueryWrapper.eq(InvMonthlySchedule::getImsMonth, Integer.parseInt(request.getIcmReportingMonth()));
            invMonthlySchedules = invMonthlyScheduleService.list(invMonthlyScheduleLambdaQueryWrapper);
        }

        List<InvCompleteMonth> invCompleteMonths = new ArrayList<>();
        if (!CollectionUtils.isEmpty(invPlanYears)) {
            List<String> ipvPIds = invPlanYears.stream().map(InvPlanYear::getPId).collect(Collectors.toList());
            LambdaQueryWrapper<InvCompleteMonth> invCompleteMonthQueryWrapper = new LambdaQueryWrapper<>();
            invCompleteMonthQueryWrapper.eq(InvCompleteMonth::getIcmReportingYear, request.getIcmReportingYear());
            invCompleteMonthQueryWrapper.in(InvCompleteMonth::getPId, ipvPIds);
            invCompleteMonths = this.list(invCompleteMonthQueryWrapper);
        }

        //开工情况--考核开工数
        //当前年度，该项目主管单位，年度投资计划录入模块中“当年新开工”字段选择“是”的项目数
        long checkStartNum = invPlanYears.stream().filter(e -> e.getIpvNewly() != null && e.getIpvNewly()).count();
        result.setCheckStartNum(checkStartNum);

        //开工情况--计划累计开工数
        //截至当月，该项目主管单位，年度投资计划录入模块中“当年新开工”字段选择“是”的项目数
        long planCumulativeStartNum = invPlanYears.stream().filter(e -> {
            if (e.getIpvNewly() != null && e.getIpvNewly()) {
                String month = DateFormatUtil.dateToString(e.getIpvStart(), DateFormatUtil.FORMAT_PATTERN_SINGLE_MONTH);
                return Integer.parseInt(month) <= Integer.parseInt(request.getIcmReportingMonth());
            }
            return false;
        }).count();
        result.setPlanCumulativeStartNum(planCumulativeStartNum);

        //开工情况--当月开工数
        //当前月份，该项目主管单位，“当月是否开工”字段选择“是”的项目数
        long currentMonthStartNum = invCompleteMonths.stream().filter(e -> "1".equals(e.getCurrentMonthStart()) && request.getIcmReportingMonth().equals(e.getIcmReportingMonth())).count();
        result.setCurrentMonthStartNum(currentMonthStartNum);

        //开工情况--累计开工数
        //当月开工数+上月累计开工数
        long cumulativeStartNum = invCompleteMonths.stream().filter(e -> "1".equals(e.getCurrentMonthStart()) && (Integer.parseInt(e.getIcmReportingMonth()) <= Integer.parseInt(request.getIcmReportingMonth()))).count();
        result.setCumulativeStartNum(cumulativeStartNum);

        //开工情况--开工率
        //累计开工数÷考核开工数
        String startPercent = DataFormatUtil.getPercent(cumulativeStartNum, checkStartNum);
        result.setStartPercent(startPercent);

        //开工情况--开工计划完成率
        //累计开工数÷计划累计开工数
        String startPlanCompletePercent = DataFormatUtil.getPercent(cumulativeStartNum, planCumulativeStartNum);
        result.setStartPlanCompletePercent(startPlanCompletePercent);

        //投资情况--考核项目数
        //当前年度，该项目主管单位，年度投资计划录入模块中录入的项目数
        long checkProjectNum = invPlanYears.size();
        result.setCheckProjectNum(checkProjectNum);

        //投资情况--考核投资额(万元)
        //当前年度，该项目主管单位，年度投资计划录入模块中录入的所有项目的“当年计划投资”之和
        BigDecimal checkInv = invPlanYears.stream().map(InvPlanYear::getIpvPlannedInvestment).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        result.setCheckInv(MathUtil.DecimalDivisionInt(checkInv, 10000));

        //投资情况--计划累计投资额(万元)
        //当前年度，该项目主管单位，年度投资计划录入中所有项目的“当月年度累计计划投资”之和
        BigDecimal planCumulativeInv = invMonthlySchedules.stream().map(InvMonthlySchedule::getImsCumulativeInv).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        result.setPlanCumulativeInv(MathUtil.DecimalDivisionInt(planCumulativeInv, 10000));

        //投资情况--当月投资额(万元)
        //当前年度，该项目主管单位，所有项目的“当月完成投资额”之和
        BigDecimal currentMonthInv = invCompleteMonths.stream().filter(e -> request.getIcmReportingMonth().equals(e.getIcmReportingMonth())).map(InvCompleteMonth::getIcmMonthlyInvestment).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        result.setCurrentMonthInv(MathUtil.DecimalDivisionInt(currentMonthInv, 10000));

        //投资情况--累计投资额(万元)
        //当月投资额+上月累计投资额
        BigDecimal cumulativeInv = invCompleteMonths.stream().filter(e -> request.getIcmReportingMonth().equals(e.getIcmReportingMonth())).map(InvCompleteMonth::getIcmAnnualInvestment).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        result.setCumulativeInv(MathUtil.DecimalDivisionInt(cumulativeInv, 10000));

        //投资情况--投资完成率
        //累计投资额÷考核投资额
        String invCompletePercent = DataFormatUtil.getPercentOfBigDecimal(cumulativeInv, checkInv);
        result.setInvCompletePercent(invCompletePercent);

        //投资情况--“投资计划完成率”<100%的项目数
        long shortProjectNum = invCompleteMonths.stream().filter(e -> e.getMonthCumulativeInvProportion() < 1.0 && request.getIcmReportingMonth().equals(e.getIcmReportingMonth())).count();

        //投资情况--投资短板率
        //当前月份，该项目主管单位，“投资计划完成率”<100%的项目数÷考核项目数
        String invShortPercent = DataFormatUtil.getPercent(shortProjectNum, checkProjectNum);
        result.setInvShortPercent(invShortPercent);

        //投资情况--投资计划完成率
        //累计投资额÷计划累计投资额
        String invPlanCompletePercent = DataFormatUtil.getPercentOfBigDecimal(cumulativeInv, planCumulativeInv);
        result.setInvPlanCompletePercent(invPlanCompletePercent);

        //完工情况--考核完工数
        //当前年度，该项目主管单位，年度投资计划录入模块中“当年完工”字段选择“是”的项目数
        long checkEndNum = invPlanYears.stream().filter(e -> e.getIpvCompleted() != null && e.getIpvCompleted()).count();
        result.setCheckEndNum(checkEndNum);

        //完工情况--计划累计完工数
        //截至当月，该项目主管单位，年度投资计划录入模块中“当年完工”字段选择“是”的项目数
        long planCumulativeEndNum = invPlanYears.stream().filter(e -> {
            if (e.getIpvCompleted() != null && e.getIpvCompleted()) {
                String month = DateFormatUtil.dateToString(e.getIpvEnd(), DateFormatUtil.FORMAT_PATTERN_SINGLE_MONTH);
                return Integer.parseInt(month) <= Integer.parseInt(request.getIcmReportingMonth());
            }
            return false;
        }).count();
        result.setPlanCumulativeEndNum(planCumulativeEndNum);

        //完工情况--当月完工数
        //当前月份，该项目主管单位，“当月是否完工”字段选择“是”的项目数
        long currentMonthEndNum = invCompleteMonths.stream().filter(e -> "1".equals(e.getCurrentMonthComplete()) && request.getIcmReportingMonth().equals(e.getIcmReportingMonth())).count();
        result.setCurrentMonthEndNum(currentMonthEndNum);

        //完工情况--累计完工数
        //当月完工数+上月累计完工数
        long cumulativeEndNum = invCompleteMonths.stream().filter(e -> "1".equals(e.getCurrentMonthComplete()) && (Integer.parseInt(e.getIcmReportingMonth()) <= Integer.parseInt(request.getIcmReportingMonth()))).count();
        result.setCumulativeEndNum(cumulativeEndNum);

        //完工情况--完工率
        //累计完工数÷考核完工数
        String endPercent = DataFormatUtil.getPercent(cumulativeEndNum, checkEndNum);
        result.setEndPercent(endPercent);

        //完工情况--完工划完成率
        //累计完工数÷计划累计完工数
        String endPlanCompletePercent = DataFormatUtil.getPercent(cumulativeEndNum, planCumulativeEndNum);
        result.setEndPlanCompletePercent(endPlanCompletePercent);

        return result;
    }

    private boolean checkProjectInvCompleteMonth(String pId, String conId, String icmReportingYear, String icmReportingMonth, String icmId) {
        QueryWrapper<InvCompleteMonth> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        if (!ValidateUtil.isEmpty(conId)) {
            wrapper.eq("Con_Id", conId);
        }
        wrapper.eq("Icm_Reporting_Year", icmReportingYear);
        wrapper.eq("Icm_Reporting_Month", icmReportingMonth);
        if (!ValidateUtil.isEmpty(icmId))
            wrapper.ne("Icm_Id", icmId);
        wrapper.ne("Icm_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return false;
        else
            return true;
    }

    /**
     * 批量完成后续的月完成情况
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public BaseResponse completeSubsequentInvCompleteMonth(TransferStatusUpdateRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (!"7".equals(request.getTransferStatus())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        List<InvCompleteMonth> months = this.listByIds(request.getIds());
        if (ValidateUtil.isEmpty(months)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        // 一次性查询所有相关的月份的月完成情况
        List<String> projectIds = months.stream().map(InvCompleteMonth::getPId).distinct().collect(Collectors.toList());
        List<InvCompleteMonth> allSubsequentMonths = this.list(Wrappers.<InvCompleteMonth>lambdaQuery()
                .in(InvCompleteMonth::getPId, projectIds)
        );

        // 按pId分组
        Map<String, List<InvCompleteMonth>> subsequentMonthsMap = allSubsequentMonths.stream()
                .collect(Collectors.groupingBy(InvCompleteMonth::getPId));

        List<InvCompleteMonth> updateList = new ArrayList<>();
        for (InvCompleteMonth month : months) {
            // 先找出该月之后的所有月份
            List<InvCompleteMonth> allSubsequentMonthList = subsequentMonthsMap.getOrDefault(month.getPId(), Collections.emptyList())
                .stream()
                .filter(item -> Integer.parseInt(item.getIcmReportingMonth()) > Integer.parseInt(month.getIcmReportingMonth()))
                .collect(Collectors.toList());

            // 未完结的后续月份
            List<InvCompleteMonth> subsequentMonths = allSubsequentMonthList.stream()
                .filter(item -> "1".equals(item.getTransferStatus()))
                .collect(Collectors.toList());

            if (ValidateUtil.isEmpty(subsequentMonths)) {
                continue;
            }

            // 后续月份有已完结且有完成时间的
            boolean hasActualEndTime = allSubsequentMonthList.stream()
                .anyMatch(item -> ValidateUtil.isNotNull(item.getActualEndTime()) && "7".equals(item.getTransferStatus()));

            if (ValidateUtil.isNotNull(month.getActualEndTime())) {
                String oldImg = month.getIcmImg();
                subsequentMonths.forEach(item -> {
                    String newImgId = GenerateUUID.getUUID();
                    // BeanUtil.copyProperties(month, item,
                    //         "icmId", "ipvId", "pId", "icmReportingMonth", "icmReportingYear", "icmAnnualInvestment", "icmAnnualProportion", "monthCumulativeInvProportion"
                    // );
                    item.setActualStartTime(month.getActualStartTime());
                    item.setActualEndTime(month.getActualEndTime());
                    item.setIcmImgProgress(month.getIcmImgProgress());
                    item.setIcmImageProgress(month.getIcmImageProgress());
                    item.setIcmPersonnelNum(month.getIcmPersonnelNum());
                    item.setIcmImg(newImgId);
                    // item.setCurrentMonthStart("1");
                    item.setIcmMonthlyInvestment(BigDecimal.ZERO);
                    item.setTransferStatus("1");

                    item.setIcmCreateUid(null);
                    item.setIcmCreateUname(null);
                    sysFileAnnexService.copyFileAnnex2NewRelation(Collections.singletonList(oldImg), Collections.singletonList(newImgId), item.getIcmId());
                });
                updateList.addAll(subsequentMonths);
            } else if (ValidateUtil.isNotNull(month.getActualStartTime())) {
                subsequentMonths.forEach(item -> {
                    // item.setCurrentMonthStart("1");
                    // item.setPlanCompleteResult(month.getPlanCompleteResult());
                    item.setActualStartTime(month.getActualStartTime());
                    item.setCurrentTimeStart(month.getCurrentTimeStart());

                    if (!hasActualEndTime) {
                        item.setActualEndTime(month.getActualEndTime());
                        item.setIcmImgProgress("");
                        item.setIcmImageProgress("");
                        item.setIcmPersonnelNum(0);
                        item.setIcmMonthlyInvestment(BigDecimal.ZERO);
                        item.setIcmImg("");
                    }

                    item.setIcmCreateUid(null);
                    item.setIcmCreateUname(null);
                });
                updateList.addAll(subsequentMonths);
            }
        }
        if (ValidateUtil.isNotEmpty(updateList)) {
            this.updateBatchById(updateList);
        }
        return BaseResponse.ok();
    }

    /**
     * 根据ipvId列表，重新计算年度累计完成投资额和完成百分比
     *
     * @param ipvIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public BaseResponse refreshIcmAnnualInvestment(List<String> ipvIds) {
        log.info("refreshIcmAnnualInvestment");
        if (ValidateUtil.isEmpty(ipvIds)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<InvCompleteMonthResponse> invCompleteMonths = baseMapper.getInvCompleteMonthByIpvIds(ipvIds);
        if (ValidateUtil.isEmpty(invCompleteMonths)) {
            return BaseResponse.ok();
        }
        // ==============================================排除未完结的===============================================
        // 每个ipvId分开计算
        // 在1-12个月里，第一个有Icm_Monthly_Investment的月份，且transfer_status=7的Icm_Annual_Investment和Icm_Monthly_Investment相等
        // 后续月份的Icm_Annual_Investment=上月Icm_Annual_Investment+当月Icm_Monthly_Investment，如果当月的transfer_status=7才累加，不然保持不变

        // 年度累计投资完成率Icm_Annual_Proportion = Icm_Annual_Investment / 年度计划投资额 * 100%
        // 当月的统计时除了前面月份还是必须transfer_status=7以外，当月的这个值不受transfer_status影响，还是要累加自己的Icm_Monthly_Investment再算
        // 年度计划从Ipv_Planned_Investment获取

        // 当月投资计划完成率month_cumulative_inv_proportion = Icm_Annual_Investment / 年度累计计划 * 100%
        // 当月的统计时除了前面月份还是必须transfer_status=7以外，当月的这个值不受transfer_status影响，还是要累加自己的Icm_Monthly_Investment再算
        // 年度累计计划从Inv_Monthly_Schedule的Ims_Cumulative_Inv获取

        // ===============================================全部（现在）================================================
        // 每个ipvId分开计算
        // 在1-12月里，第一个有Icm_Monthly_Investment的月份的Icm_Annual_Investment和Icm_Monthly_Investment相等，不考虑transfer_status
        // 后续月份里Icm_Annual_Investment的统计也不考虑transfer_status，且要把自己的加上，不管自己的transfer_status是几

        // 年度累计投资完成率Icm_Annual_Proportion = Icm_Annual_Investment / 年度计划投资额 * 100%
        // 当月的和前面的统计时都不考虑transfer_status，直接把自己的Icm_Monthly_Investment加上再算
        // 年度计划从Ipv_Planned_Investment获取

        // 当月投资计划完成率month_cumulative_inv_proportion = Icm_Annual_Investment / 年度累计计划 * 100%
        // 当月的和前面的统计时都不考虑transfer_status，直接把自己的Icm_Monthly_Investment加上再算
        // 年度累计计划从Inv_Monthly_Schedule的Ims_Cumulative_Inv获取

        // 按ipvId分组处理
        Map<String, List<InvCompleteMonthResponse>> byIpv = invCompleteMonths.stream()
                .collect(Collectors.groupingBy(InvCompleteMonthResponse::getIpvId));

        for (Map.Entry<String, List<InvCompleteMonthResponse>> e : byIpv.entrySet()) {
            List<InvCompleteMonthResponse> months = e.getValue();

            // 按月份排序
            months.sort(Comparator.comparingInt(m -> Integer.parseInt(m.getIcmReportingMonth())));

            int firstIdx = -1;
            for (int i = 0; i < months.size(); i++) {
                InvCompleteMonthResponse m = months.get(i);
                boolean ok = m.getIcmMonthlyInvestment() != null
                        && m.getIcmMonthlyInvestment().compareTo(BigDecimal.ZERO) != 0;
                if (ok) {
                    firstIdx = i;
                    break;
                }
            }

            if (firstIdx == -1) {
                // 全年没有首个有效月 全部置空
                for (InvCompleteMonthResponse m : months) {
                    m.setIcmAnnualInvestment(null);
                }
            } else {
                // 首月
                InvCompleteMonthResponse first = months.get(firstIdx);
                BigDecimal firstMonthly = first.getIcmMonthlyInvestment() == null ? BigDecimal.ZERO : first.getIcmMonthlyInvestment();
                first.setIcmAnnualInvestment(firstMonthly);

                // 首月之前全部置空
                for (int i = 0; i < firstIdx; i++) {
                    months.get(i).setIcmAnnualInvestment(null);
                }

                // 后续月份
                BigDecimal prevAnnual = firstMonthly;
                for (int i = firstIdx + 1; i < months.size(); i++) {
                    InvCompleteMonthResponse cur = months.get(i);
                    BigDecimal curAnnual;
                    curAnnual = prevAnnual.add(cur.getIcmMonthlyInvestment() == null ? BigDecimal.ZERO : cur.getIcmMonthlyInvestment());
                    cur.setIcmAnnualInvestment(curAnnual);
                    prevAnnual = curAnnual;
                }
            }

            BigDecimal strictTillPrev = BigDecimal.ZERO;

            for (InvCompleteMonthResponse cur : months) {
                BigDecimal curMonthly = cur.getIcmMonthlyInvestment() == null ? BigDecimal.ZERO : cur.getIcmMonthlyInvestment();

                // 当月统计使用的累计实际
                BigDecimal adjustedCumulative = strictTillPrev.add(curMonthly);

                // 年度累计投资完成率 Icm_Annual_Proportion = adjustedCumulative / 年度计划 * 100%
                BigDecimal annualPlan = cur.getIpvPlannedInvestment();
                BigDecimal annualProportion = percent(adjustedCumulative, annualPlan);

                // 当月计划累计完成率 month_cumulative_inv_proportion = adjustedCumulative / 年度累计计划 * 100%
                BigDecimal monthCumPlan = cur.getImsCumulativeInv();
                BigDecimal monthCumProportion = percent(adjustedCumulative, monthCumPlan);

                if (annualProportion != null) {
                    cur.setIcmAnnualProportion(annualProportion.floatValue());
                } else {
                    cur.setIcmAnnualProportion(0.0f);
                }
                if (monthCumProportion != null) {
                    cur.setMonthCumulativeInvProportion(monthCumProportion.floatValue());
                } else {
                    cur.setMonthCumulativeInvProportion(0.0f);
                }

                strictTillPrev = strictTillPrev.add(curMonthly);
            }
        }

        // 批量更新
        this.updateBatchById(BeanUtil.copyToList(invCompleteMonths, InvCompleteMonth.class), 12);
        return BaseResponse.ok();
    }

    /**
     * 计算百分比（num/den*100），den 为空或 0 返回 null；保留两位小数
     *
     * @param num
     * @param den
     * @return
     */
    private BigDecimal percent(BigDecimal num, BigDecimal den) {
        if (den == null || den.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return num
                .divide(den, 6, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 重新计算所有的年度累计完成投资额和完成百分比
     *
     * @return
     */
    @Override
    public BaseResponse refreshAllIcmAnnualInvestment() {
        List<InvCompleteMonth> invCompleteMonths = lambdaQuery()
                .isNotNull(InvCompleteMonth::getIpvId)
                .select(InvCompleteMonth::getIpvId)
                .list();
        if (ValidateUtil.isEmpty(invCompleteMonths)) {
            return BaseResponse.ok();
        }
        List<String> allIpvIds = invCompleteMonths.stream()
                .map(InvCompleteMonth::getIpvId)
                .filter(ValidateUtil::isNotNull)
                .distinct()
                .collect(Collectors.toList());
        // 分批处理
        int batchSize = 10;
        for (int i = 0; i < allIpvIds.size(); i += batchSize) {
            List<String> batch = allIpvIds.subList(i, Math.min(i + batchSize, allIpvIds.size()));
            owner.refreshIcmAnnualInvestment(batch);
            log.info("处理进度 {}/{}", Math.min(i + batchSize, allIpvIds.size()), allIpvIds.size());
        }
        return BaseResponse.ok();
    }

    /**
     * 获取月度投资完成统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getInvCompleteMonthStatisticsOverviewList(InvCompleteMonthRequest request, String teId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<InvCompleteMonthResponse> page = new Page<>(request.getPage(), request.getSize());
        request.setTeId(teId);
        List<InvCompleteMonthResponse> datas = baseMapper.getInvCompleteMonthStatisticsOverviewList(page, request);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }
}
