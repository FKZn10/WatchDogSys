package com.jpmsoft.projectProducer.service.other.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.ProgressStatisticsRequest;
import com.jpmsoft.common.model.response.ProgressStatisticsResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.other.MonthlyProgressMapper;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectActivityDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.TInformationFilling;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.response.AreaAndValueResponse;
import com.jpmsoft.projectProducer.service.other.IMonthlyProgressService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MonthlyProgressServiceImpl extends ServiceImpl<MonthlyProgressMapper, TInformationFilling> implements IMonthlyProgressService {


    @Resource
    private IPreProjectService preProjectService;




    @Override
    public BaseResponse getContractAmount(String year, String month, String pId) {
        if (year == null || month == null || pId == null) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        LambdaQueryWrapper<TInformationFilling> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TInformationFilling::getPId, pId)
                    .eq(TInformationFilling::getState, ApprovalStatusEnum.Already_Over.getIndex() + "");
        List<TInformationFilling> list = baseMapper.selectList(queryWrapper);

        //签订合同总额
        BigDecimal totalcontractamount = new BigDecimal(0);

        //上年结转合同金额
        BigDecimal carryforwardthecontractamount = new BigDecimal(0);

        //本年新签合同金额
        BigDecimal newcontractamount = new BigDecimal(0);

        int currentYear = Integer.parseInt(year);
        int currentMonth = Integer.parseInt(month);

        for (TInformationFilling filling : list) {
            String reportYearStr = filling.getReportingYear();
            String reportMonthStr = filling.getReportingMonth();

            if (reportYearStr != null && reportMonthStr != null) {

                int reportYear = Integer.parseInt(reportYearStr);
                int reportMonth = Integer.parseInt(reportMonthStr);


                BigDecimal contractAmount = filling.getNewMonthContractAmount() != null ? filling.getNewMonthContractAmount() : BigDecimal.ZERO;

                //签订合同总额：当前月之前的所有累加和
                if (reportYear < currentYear || (reportYear == currentYear && reportMonth < currentMonth)) {
                    totalcontractamount = totalcontractamount.add(contractAmount);
                }

                //上年结转合同金额：去年的所有累加和
                if (reportYear == (currentYear - 1)) {
                    carryforwardthecontractamount = carryforwardthecontractamount.add(contractAmount);
                }

                //本年新签合同金额：今年当前月之前的累加和
                if (reportYear == currentYear && reportMonth < currentMonth) {
                    newcontractamount = newcontractamount.add(contractAmount);
                }

            }
        }


        Map<String, Object> result = new HashMap<>();
        result.put("totalContractAmount", totalcontractamount);
        result.put("carryForwardContractAmount", carryforwardthecontractamount);
        result.put("newContractAmount", newcontractamount);
        return BaseResponse.ok(result);
    }

    @Override
    public BaseResponse getProjectActivity(ProjectActivityDto request, String uid) {
        if (request == null) {
            return BaseResponse.fail("参数不能为空");
        }

        // Step 1: 筛选项目
        LambdaQueryWrapper<PreProject> projectWrapper = new LambdaQueryWrapper<>();
        projectWrapper.eq(PreProject::getState, ApprovalStatusEnum.Already_Over.getIndex());
        if (StringUtils.isNotBlank(request.getPCompetentDepartment())) {
            projectWrapper.eq(PreProject::getpCompetentDepartment, request.getPCompetentDepartment());
        }

        List<PreProject> projectList = Optional.ofNullable(preProjectService.list(projectWrapper))
                .orElse(Collections.emptyList());

        if (projectList.isEmpty()) {
            return BaseResponse.ok(new ProjectActivityDto());
        }

        // Step 2: 年月参数（默认当前年月）
        LocalDate now = LocalDate.now();
        int targetYear = StringUtils.isNotBlank(request.getYear()) ? Integer.parseInt(request.getYear()) : now.getYear();
        int targetMonth = StringUtils.isNotBlank(request.getMonth()) ? Integer.parseInt(request.getMonth()) : now.getMonthValue();

        List<String> pIds = projectList.stream()
                .map(PreProject::getpId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (pIds.isEmpty()) {
            return BaseResponse.ok(new ProjectActivityDto());
        }

        // Step 3: 查询两类填报数据
        List<TInformationFilling> fillingsUpToTarget = Optional.ofNullable(baseMapper.selectList(
                new LambdaQueryWrapper<TInformationFilling>()
                        .in(TInformationFilling::getPId, pIds)
                        .eq(TInformationFilling::getState, ApprovalStatusEnum.Already_Over.getIndex())
                        .eq(TInformationFilling::getReportingYear, targetYear)
                        .le(TInformationFilling::getReportingMonth, targetMonth)
        )).orElse(Collections.emptyList());

        List<TInformationFilling> fillingsExactMonth = Optional.ofNullable(baseMapper.selectList(
                new LambdaQueryWrapper<TInformationFilling>()
                        .in(TInformationFilling::getPId, pIds)
                        .eq(TInformationFilling::getState, ApprovalStatusEnum.Already_Over.getIndex())
                        .eq(TInformationFilling::getReportingYear, targetYear)
                        .eq(TInformationFilling::getReportingMonth, targetMonth)
        )).orElse(Collections.emptyList());

        ProjectActivityDto result = new ProjectActivityDto();

        // 辅助：按项目取最新一条（reportingMonth 最大）
        Map<String, TInformationFilling> latestByProject = fillingsUpToTarget.stream()
                .filter(f -> f.getPId() != null)
                .collect(Collectors.toMap(
                        TInformationFilling::getPId,
                        Function.identity(),
                        (a, b) -> {
                            Integer monthA = parseMonth(a.getReportingMonth());
                            Integer monthB = parseMonth(b.getReportingMonth());
                            return monthA >= monthB ? a : b;
                        }
                ));

        // 工具 lambda：安全取 BigDecimal 并加法
        BiFunction<TInformationFilling, Function<TInformationFilling, BigDecimal>, BigDecimal> valOrZero =
                (f, getter) -> (f == null || getter.apply(f) == null) ? BigDecimal.ZERO : getter.apply(f);

        // 各字段聚合计算
        BigDecimal signSum = latestByProject.values().stream()
                .map(f -> valOrZero.apply(f, TInformationFilling::getTotalcontractamount))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(10000), 2, RoundingMode.HALF_UP);
        result.setSignContractAmount(signSum);

        BigDecimal carrySum = latestByProject.values().stream()
                .map(f -> valOrZero.apply(f, TInformationFilling::getCarryforwardthecontractamount))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(10000), 2, RoundingMode.HALF_UP);
        result.setCarryForwardContractAmount(carrySum);

        BigDecimal newSignSum = latestByProject.values().stream()
                .map(f -> valOrZero.apply(f, TInformationFilling::getNewcontractamount))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(10000), 2, RoundingMode.HALF_UP);
        result.setNewSignContractAmount(newSignSum);

        BigDecimal totalOutputSum = latestByProject.values().stream()
                .map(f -> valOrZero.apply(f, TInformationFilling::getProjectoutput))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(10000), 2, RoundingMode.HALF_UP);
        result.setTotalOutputValue(totalOutputSum);

        BigDecimal finishSum = latestByProject.values().stream()
                .map(f -> valOrZero.apply(f, TInformationFilling::getCompletionofthevalue))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(10000), 2, RoundingMode.HALF_UP);
        result.setFinishValue(finishSum);

        BigDecimal constructionAreaSum = latestByProject.values().stream()
                .map(f -> valOrZero.apply(f, TInformationFilling::getTheconstructionareaof))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.setConstructionArea(constructionAreaSum);

        String conunit = fillingsUpToTarget.stream()
                .map(TInformationFilling::getConunit)
                .filter(StringUtils::isNotBlank)
                .findFirst()
                .orElse("");
        result.setConunit(conunit);

        BigDecimal newlyStartSum = fillingsExactMonth.stream()
                .map(f -> f.getNewstartarea() == null ? BigDecimal.ZERO : f.getNewstartarea())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.setNewlyStartArea(newlyStartSum);

        String newsunit = fillingsExactMonth.stream()
                .map(TInformationFilling::getNewsunit)
                .filter(StringUtils::isNotBlank)
                .findFirst()
                .orElse("");
        result.setNewsunit(newsunit);

        Double avgStaff = latestByProject.values().stream()
                .map(TInformationFilling::getNumberofpersonnel)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        result.setAvgStaffCount(avgStaff);

        // Step 4: pType 分类
        Map<String, List<String>> pIdsByType = projectList.stream()
                .collect(Collectors.groupingBy(
                        p -> StringUtils.isBlank(p.getpType()) ? "other" : p.getpType(),
                        Collectors.mapping(PreProject::getpId, Collectors.toList())
                ));

        result.setGovFacilityValue(sumByTypeLatest(pIdsByType.get("1"), latestByProject));
        result.setSocialCauseValue(sumByTypeLatest(pIdsByType.get("2"), latestByProject));
        result.setInfrastructureValue(sumByTypeLatest(pIdsByType.get("3"), latestByProject));

        BigDecimal otherBd = sumByTypeLatest(pIdsByType.get("4"), latestByProject)
                .add(sumByTypeLatest(pIdsByType.get("other"), latestByProject));
        result.setOtherValue(otherBd.doubleValue());

        result.setProjectCount(projectList.size());
        return BaseResponse.ok(result);
    }

    /**
     * 按类型基于 latestByProject 求和 projectoutput 并除以 10000（返回 BigDecimal）
     */
    private BigDecimal sumByTypeLatest(List<String> pIdsForType, Map<String, TInformationFilling> latestByProject) {
        if (pIdsForType == null || pIdsForType.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = pIdsForType.stream()
                .map(latestByProject::get)
                .filter(Objects::nonNull)
                .map(f -> f.getProjectoutput() == null ? BigDecimal.ZERO : f.getProjectoutput())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(10000), 2, RoundingMode.HALF_UP);
        return sum;
    }

    private Integer parseMonth(String monthStr) {
        try {
            return Integer.parseInt(monthStr);
        } catch (Exception e) {
            return 0; // 如果为空或非数字，则返回0，保证比较不会出错
        }
    }

    /**
     * 累加指定字段并转万元，返回非空值
     */
    private BigDecimal sumBigDecimal(List<TInformationFilling> fillings, Function<TInformationFilling, BigDecimal> getter) {
        if (fillings == null || fillings.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return fillings.stream()
                .map(getter)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(10000), 2, RoundingMode.HALF_UP);
    }

    /**
     * 查询某一类项目在指定年月的产值（万元），返回非空值
     */
    private BigDecimal getProjectOutputByType(List<String> pIds, int year, int month) {
        if (pIds == null || pIds.isEmpty()) {
            return BigDecimal.ZERO;
        }
        List<TInformationFilling> fillings = Optional.ofNullable(baseMapper.selectList(
                new LambdaQueryWrapper<TInformationFilling>()
                        .in(TInformationFilling::getPId, pIds)
                        .eq(TInformationFilling::getState, ApprovalStatusEnum.Already_Over.getIndex())
                        .eq(TInformationFilling::getReportingYear, year)
                        .le(TInformationFilling::getReportingMonth, month)
        )).orElse(Collections.emptyList());

        return sumBigDecimal(fillings, TInformationFilling::getProjectoutput);
    }

    /**
     * 安全求和工具：防止 list 为 null、元素为 null 导致 NPE
     */
    private BigDecimal safeSum(List<TInformationFilling> list, Function<TInformationFilling, BigDecimal> mapper) {
        if (list == null || list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return list.stream()
                .map(mapper)               // 可能返回 null
                .filter(Objects::nonNull)  // 过滤 null
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BaseResponse selectAreaAndValue(String year, String month, String oId) {

        if(year == null || month == null || oId == null){
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        //查项目组织机构下面的项目
        LambdaQueryWrapper<PreProject> queryProject = new LambdaQueryWrapper<>();
        queryProject.eq(PreProject::getpCompetentDepartment, oId)
                .eq(PreProject::getState, ApprovalStatusEnum.Already_Over.getIndex());
        List<PreProject> projectList = preProjectService.list(queryProject);
        if(ValidateUtil.isEmpty(projectList)){
            return BaseResponse.ok();
        }
        //获取项目ids
        List<String> pIds = projectList.stream().map(PreProject::getpId).distinct().collect(Collectors.toList());

        //获取月度填报信息
        LambdaQueryWrapper<TInformationFilling> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TInformationFilling::getPId, pIds)
                .eq(TInformationFilling::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .eq(TInformationFilling::getReportingYear, year)
                .eq(TInformationFilling::getReportingMonth, month);

        List<TInformationFilling> tInformationFillingList = baseMapper.selectList(queryWrapper);

        AreaAndValueResponse res = new AreaAndValueResponse();
        if(ValidateUtil.isEmpty(tInformationFillingList)){
            return BaseResponse.ok(res);
        }

        Map<String, List<TInformationFilling>> groupedResult = separateByProfessionalType(tInformationFillingList, projectList);

        // 使用 getOrDefault 防止 groupedResult.get(...) 返回 null
        List<TInformationFilling> gov = groupedResult.getOrDefault("11", Collections.emptyList());
        List<TInformationFilling> inst = groupedResult.getOrDefault("12", Collections.emptyList());
        List<TInformationFilling> soc = groupedResult.getOrDefault("13", Collections.emptyList());
        List<TInformationFilling> other = groupedResult.getOrDefault("14", Collections.emptyList());
        List<TInformationFilling> sci = groupedResult.getOrDefault("21", Collections.emptyList());
        List<TInformationFilling> edu = groupedResult.getOrDefault("22", Collections.emptyList());
        List<TInformationFilling> culture = groupedResult.getOrDefault("23", Collections.emptyList());
        List<TInformationFilling> sport = groupedResult.getOrDefault("24", Collections.emptyList());
        List<TInformationFilling> health = groupedResult.getOrDefault("25", Collections.emptyList());
        List<TInformationFilling> social = groupedResult.getOrDefault("26", Collections.emptyList());
        List<TInformationFilling> pub = groupedResult.getOrDefault("30", Collections.emptyList());
        List<TInformationFilling> otherSpec = groupedResult.getOrDefault("40", Collections.emptyList());

        // 用 safeSum 替代直接 stream.reduce，避免元素为 null 导致 NPE
        res.setGovArea(safeSum(gov, TInformationFilling::getCompletionarea));
        res.setGovValue(safeSum(gov, TInformationFilling::getCompletionofthevalue));

        //事业单位用房
        res.setInstArea(safeSum(inst, TInformationFilling::getCompletionarea));
        res.setInstValue(safeSum(inst, TInformationFilling::getCompletionofthevalue));

        //社会团体用房
        res.setSocArea(safeSum(soc, TInformationFilling::getCompletionarea));
        res.setSocValue(safeSum(soc, TInformationFilling::getCompletionofthevalue));

        //其他相关设施
        res.setOtherArea(safeSum(other, TInformationFilling::getCompletionarea));
        res.setOtherValue(safeSum(other, TInformationFilling::getCompletionofthevalue));

        //科学研究用房屋
        res.setSciArea(safeSum(sci, TInformationFilling::getCompletionarea));
        res.setSciValue(safeSum(sci, TInformationFilling::getCompletionofthevalue));

        //教育用房屋
        res.setEduArea(safeSum(edu, TInformationFilling::getCompletionarea));
        res.setEduValue(safeSum(edu, TInformationFilling::getCompletionofthevalue));

        //文化用房屋
        res.setCultureArea(safeSum(culture, TInformationFilling::getCompletionarea));
        res.setCultureValue(safeSum(culture, TInformationFilling::getCompletionofthevalue));

        //体育用房屋
        res.setSportArea(safeSum(sport, TInformationFilling::getCompletionarea));
        res.setSportValue(safeSum(sport, TInformationFilling::getCompletionofthevalue));

        //卫生用房屋
        res.setHealthArea(safeSum(health, TInformationFilling::getCompletionarea));
        res.setHealthValue(safeSum(health, TInformationFilling::getCompletionofthevalue));

        //民政用房屋
        res.setSocialArea(safeSum(social, TInformationFilling::getCompletionarea));
        res.setSocialValue(safeSum(social, TInformationFilling::getCompletionofthevalue));

        //一、办公及业务用房屋
        res.setWorkArea(res.getGovArea().add(res.getInstArea()).add(res.getSocArea()).add(res.getOtherArea()));
        res.setWorkValue(res.getGovValue().add(res.getInstValue()).add(res.getSocValue()).add(res.getOtherValue()));

        //二、科教、文、体、卫、民政等社会事业工程
        res.setSocBusArea(res.getSciArea().add(res.getEduArea()).add(res.getCultureArea()).add(res.getSportArea()).add(res.getHealthArea()).add(res.getSocialArea()));
        res.setSocBusValue(res.getSciValue().add(res.getEduValue()).add(res.getCultureValue()).add(res.getSportValue()).add(res.getHealthValue()).add(res.getSocialValue()));

        //三、公共基础设施工程
        res.setPubArea(safeSum(pub, TInformationFilling::getCompletionarea));
        res.setPubValue(safeSum(pub, TInformationFilling::getCompletionofthevalue));

        //四、政府指定实行集中建设的其他工程
        res.setOtherSpecArea(safeSum(otherSpec, TInformationFilling::getCompletionarea));
        res.setOtherSpecValue(safeSum(otherSpec, TInformationFilling::getCompletionofthevalue));

        res.setTotalArea(res.getWorkArea().add(res.getSocBusArea()).add(res.getPubArea()).add(res.getOtherSpecArea()));
        res.setTotalValue(res.getWorkValue().add(res.getSocBusValue()).add(res.getPubValue()).add(res.getOtherSpecValue()));

        return BaseResponse.ok(res);

    }

    public Map<String, List<TInformationFilling>> separateByProfessionalType(
            List<TInformationFilling> informationFillings,
            List<PreProject> preProjects) {

        Set<String> PROFESSIONAL_TYPES = new HashSet<>(Arrays.asList( "11", "12", "13", "14", "21", "22", "23", "24", "25", "26", "30", "40"));

        // 处理重复 pId（若存在重复，保留第一个），避免 toMap 抛异常
        Map<String, PreProject> preProjectMap = preProjects.stream()
                .collect(Collectors.toMap(PreProject::getpId, project -> project, (a, b) -> a));

        // 初始化
        Map<String, List<TInformationFilling>> resultMap = new HashMap<>();
        for (String type : PROFESSIONAL_TYPES) {
            resultMap.put(type, new ArrayList<>());
        }

        // 分类处理TInformationFilling
        for (TInformationFilling info : informationFillings) {
            if (info == null) {
                continue;
            }
            PreProject preProject = preProjectMap.get(info.getPId());
            if (preProject != null) {
                String profType = preProject.getpProfessionalType();
                // 若 profType 为 null 或不在 PROFESSIONAL_TYPES 列表中，则跳过（保持原逻辑）
                if (profType != null && PROFESSIONAL_TYPES.contains(profType)) {
                    // resultMap 已初始化对应 key 的 list，直接 add 安全
                    resultMap.get(profType).add(info);
                }
            }
        }

        return resultMap;
    }

    /**
     * 政府投资工程项目 进展情况统计表
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse getProgressStatisticsList(ProgressStatisticsRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isEmpty(request.getYear())) {
            request.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        }
        if (ValidateUtil.isEmpty(request.getMonth())) {
            request.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
        }
        QueryMap<ProgressStatisticsRequest> map = new QueryMap<>(request);
        map.put("pId").put("pName").put("affiliatedArea").put("pType").put("pBuildModel")
                .put("pTotalDuration").put("pScaleDescribe").put("pActualStartTime").put("pPlanCompleteDate")
                .put("pCompetentDepartment").put("imageProgress").put("month").put("year")
                .put("state", ApprovalStatusEnum.Already_Over.getIndex());

        // 计算季度
        switch (request.getMonth()) {
            case 1:
            case 2:
            case 3:
                map.put("months", Arrays.asList(1, 2, 3));
                map.put("monthCumulative", Collections.singletonList(0));
                map.put("quarter", 1);
                break;
            case 4:
            case 5:
            case 6:
                map.put("months", Arrays.asList(4, 5, 6));
                map.put("monthCumulative", Arrays.asList(1, 2, 3));
                map.put("quarter", 2);
                break;
            case 7:
            case 8:
            case 9:
                map.put("months", Arrays.asList(7, 8, 9));
                map.put("monthCumulative", Arrays.asList(1, 2, 3, 4, 5, 6));
                map.put("quarter", 3);
                break;
            case 10:
            case 11:
            case 12:
            default:
                map.put("months", Arrays.asList(10, 11, 12));
                map.put("monthCumulative", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                map.put("quarter", 4);
        }
        Page<ProgressStatisticsResponse> page = new Page<>(request.getPage(), request.getSize(), false);
        Integer count = baseMapper.getProgressStatisticsListCount(map.getQuery());
        List<ProgressStatisticsResponse> list = new ArrayList<>();
        if (count > 0) {
            list = baseMapper.getProgressStatisticsList(page, map.getQuery());
        }
        return BaseResponse.ok(list, count);
    }
}