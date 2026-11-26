package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.common.model.response.ganttPlan.GanttMilestoneStatisticsResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttMilestoneStatisticsMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MonthlyReportDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.EndPlanAnnex;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttMilestoneStatistics;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.OutProjectVo;
import com.jpmsoft.projectProducer.pojo.vo.ganttPlan.MonthlyReportVO;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttMilestoneStatisticsService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttMilestoneYearService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
@Service
public class GanttMilestoneStatisticsImpl extends ServiceImpl<GanttMilestoneStatisticsMapper, GanttMilestoneStatistics> implements GanttMilestoneStatisticsService {
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IGanttMilestoneYearService ganttMilestoneYearService;

    /**
     * 获取计划管理列表
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getGanttMilestoneList(GanttMilestoneStatisticsRequest request, String uId) {
        return this.getGanttMilestoneListByIds(request, uId, null);
    }

    /**
     * 查询计划管理信息
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getGanttMilestoneClassInfo(GanttMilestoneStatisticsRequest request, String uId) {
        return this.getGanttMilestoneClassInfoByIds(request, uId, null);
    }

    /**
     * 导出年度收尾计划列表
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    public Workbook ganttMilestoneListExportExcel(GanttMilestoneStatisticsRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("gantt_milestone_statistics", uId, null, OutUserAuthEnum.Organization.getIndex());
        Workbook workbook = new XSSFWorkbook();
        if (ValidateUtil.isEmpty(authSql)) {
            return workbook;
        }
        Page<GanttMilestoneStatisticsRequest> page = new Page<>(request.getPage(), request.getSize());
        // 处理月份参数
        boolean isPlanMonth = "0".equalsIgnoreCase(request.getIcmReportingMonth());
        LocalDate dayOfMonth = getLastDayOfMonth(request.getIcmReportingYear(), request.getIcmReportingMonth());
        String lastDayOfMonth = dayOfMonth == null ? null : dayOfMonth.toString();
        // 计划内
        request.setIsPlan("00");
        List<GanttMilestoneStatisticsResponse> planDatas = baseMapper.getGanttMilestoneList(page, request, authSql, lastDayOfMonth, isPlanMonth, null);
        if (!CollectionUtils.isEmpty(planDatas)) {
            planDatas.forEach(d -> d.setIsPlan(true));
        }
        // 计划外
        request.setIsPlan("01");
        List<GanttMilestoneStatisticsResponse> outDatas = baseMapper.getGanttMilestoneList(page, request, authSql, lastDayOfMonth, isPlanMonth, null);
        if (!CollectionUtils.isEmpty(outDatas)) {
            outDatas.forEach(d -> d.setIsPlan(false));
        }
        // 合并
        List<GanttMilestoneStatisticsResponse> datas = Stream
                .concat(planDatas.stream(), outDatas.stream())
                .sorted(Comparator.comparing(
                        GanttMilestoneStatisticsResponse::getPCreateTime,
                        Comparator.nullsFirst(Comparator.naturalOrder())
                ))
                .collect(Collectors.toList());

        String[] rowTitle = new String[]{"序号", "项目类型", "项目名称", "标段名称", "项目分类", "项目管理单位", "事业部/分支机构", "总投资(万元)", "项目阶段", "立项时间", "开工时间", "完工时间", "竣工验收时间", "竣工备案时间", "工程移交时间", "决算上报时间", "决算批复时间", "备注"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("年度收尾计划");
        // 宽列的索引
        List<Integer> wideColumns = Arrays.asList(2, 3, 5, 6, 17);
        for (int i = 0; i < rowTitle.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 2000);
            } else if (wideColumns.contains(i)) {
                sheet.setColumnWidth(i, 8000);
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

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }
        if (!CollectionUtils.isEmpty(datas)) {
            JSONArray NDTZXMFL = DictionaryUtils.dictList("JPMsoft_NDTZXMFL");
            JSONArray XMJDZT = DictionaryUtils.dictList("JPMsoft_XMJDZT");
            int count = 1;
            for (GanttMilestoneStatisticsResponse data : datas) {
                row = sheet.createRow(rowNum++);
                int index = 0;

                cell = row.createCell(index++);
                cell.setCellValue(count++);
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getIsPlan() ? "年度计划" : "计划外完成");
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getPName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(StringUtils.isBlank(data.getGsName()) ? "/" : data.getGsName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(StringUtils.isBlank(data.getPCategory()) ? "/" : DictionaryUtils.processDictList(NDTZXMFL, data.getPCategory()));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(StringUtils.isBlank(data.getPCompetentDepartmentName()) ? "/" : data.getPCompetentDepartmentName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(StringUtils.isBlank(data.getPDepartmentName()) ? "/" : data.getPDepartmentName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getPInvTotal()) ? "/" : String.format("%.2f", data.getPInvTotal()));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(StringUtils.isBlank(data.getPProjectStage()) ? "/" : DictionaryUtils.processDictList(XMJDZT, data.getPProjectStage()));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getSetTime()) ? "/" : DateFormatUtil.dateToString(data.getSetTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getStartTime()) ? "/" : DateFormatUtil.dateToString(data.getStartTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getFinishTime()) ? "/" : DateFormatUtil.dateToString(data.getFinishTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getCompleteTime()) ? "/" : DateFormatUtil.dateToString(data.getCompleteTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getCompleteRecordsTime()) ? "/" : DateFormatUtil.dateToString(data.getCompleteRecordsTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getPRemoveTime()) ? "/" : DateFormatUtil.dateToString(data.getPRemoveTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getAccountSubmitTime()) ? "/" : DateFormatUtil.dateToString(data.getAccountSubmitTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isNull(data.getAccountReplyTime()) ? "/" : DateFormatUtil.dateToString(data.getAccountReplyTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(index);
                cell.setCellValue(StringUtils.isBlank(data.getRemark()) ? "/" : data.getRemark());
                cell.setCellStyle(style);
            }
        }
        return workbook;
    }

    private boolean getIsOverMonth(String year, String month) {
        boolean isOverMonth = false;
        if (StrUtil.isNotBlank(month) && StrUtil.isNotBlank(year) && !"0".equalsIgnoreCase(month)) {
            isOverMonth = LocalDate.now().isAfter(getLastDayOfMonth(year, month));
        }
        return isOverMonth;
    }

    private LocalDate getLastDayOfMonth(String year, String month) {
        if (StrUtil.isNotBlank(year) && StrUtil.isNotBlank(month) && !"0".equalsIgnoreCase(month)) {
            LocalDate selectedMonth = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(month)), 1);
            return selectedMonth.with(TemporalAdjusters.lastDayOfMonth());
        }
        return null;
    }

    public BaseResponse getMonthlyReport(MonthlyReportDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("gantt_milestone_statistics", uId, null, OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        // 初始化月度报告对象
        MonthlyReportVO  monthlyReportVO=new MonthlyReportVO();
        MonthlyReportVO  yearPlan = baseMapper.getYearPlan(request, authSql);
        MonthlyReportVO MonthlyReport = baseMapper.getMonthlyReport(request, authSql);

        List<OutProjectVo> completeOutTimeProjectVOList=baseMapper.getCompleteOutTimeProjects(request, authSql);
        List<OutProjectVo> completeOutPlanProjectVOList=baseMapper.getCompleteOutOfPlanProjects(request, authSql);
        List<OutProjectVo> completeRecordsOutTimeProjectVOList=baseMapper.getRecordsOutTimeProjects(request, authSql);
        List<OutProjectVo> completeRecordsOutPlanProjectVOList=baseMapper.getRecordsOutOfPlanProjects(request, authSql);
        List<OutProjectVo> pRemoveOutTimeProjectVOList=baseMapper.getRemoveOutTimeProjects(request, authSql);
        List<OutProjectVo> pRemoveOutPlanProjectVOList=baseMapper.getRemoveOutOfPlanProjects(request, authSql);
        List<OutProjectVo> accountSubmitOutTimeProjectVOList=baseMapper.getAccountOutTimeProjects(request, authSql);
        List<OutProjectVo> accountSubmitOutPlanProjectVOList=baseMapper.getAccountOutOfPlanProjects(request, authSql);

        monthlyReportVO.setCompleteOutTimeProjectVOList(convertListToMap(completeOutTimeProjectVOList));
        monthlyReportVO.setCompleteOutPlanProjectVOList(convertListToMap(completeOutPlanProjectVOList));
        monthlyReportVO.setCompleteRecordsOutTimeProjectVOList(convertListToMap(completeRecordsOutTimeProjectVOList));
        monthlyReportVO.setCompleteRecordsOutPlanProjectVOList(convertListToMap(completeRecordsOutPlanProjectVOList));
        monthlyReportVO.setPRemoveOutTimeProjectVOList(convertListToMap(pRemoveOutTimeProjectVOList));
        monthlyReportVO.setPRemoveOutPlanProjectVOList(convertListToMap(pRemoveOutPlanProjectVOList));
        monthlyReportVO.setAccountSubmitOutTimeProjectVOList(convertListToMap(accountSubmitOutTimeProjectVOList));
        monthlyReportVO.setAccountSubmitOutPlanProjectVOList(convertListToMap(accountSubmitOutPlanProjectVOList));

        // 获取年度计划和月度数据
        monthlyReportVO.setYear(request.getYear());
        monthlyReportVO.setMonth(request.getMonth());

        // 复制月度数据属性（确保不为null）
        if(MonthlyReport!=null) {
            BeanUtils.copyProperties(MonthlyReport,monthlyReportVO);
        }

        // 设置年度计划相关数据（确保不为null）
        if (yearPlan != null) {
            monthlyReportVO.setCompleteNum(yearPlan.getCompleteNum());
            monthlyReportVO.setCompleteRecordsNum(yearPlan.getCompleteRecordsNum());
            monthlyReportVO.setPRemoveNum(yearPlan.getPRemoveNum());
            monthlyReportVO.setAccountSubmit(yearPlan.getAccountSubmit());
            monthlyReportVO.setNodeNum(yearPlan.getNodeNum());
            monthlyReportVO.setProjectNum(yearPlan.getProjectNum());
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.000");

        // 计算完成率
        monthlyReportVO.setNodeNeedFinishNum(
                monthlyReportVO.getCompleteNeedFinishNum() +
                        monthlyReportVO.getCompleteRecordsNeedFinishNum() +
                        monthlyReportVO.getPRemoveNeedFinishNum() +
                        monthlyReportVO.getAccountSubmitNeedFinishNum()
        );
        monthlyReportVO.setNodeFinishNum(
                monthlyReportVO.getCompleteFinishNum() +
                        monthlyReportVO.getCompleteRecordsFinishNum() +
                        monthlyReportVO.getPRemoveFinishNum() +
                        monthlyReportVO.getAccountSubmitFinishNum()
        );

        monthlyReportVO.setNodeFinishRate(monthlyReportVO.getNodeNeedFinishNum() == 0 ?
                0.0 : Double.parseDouble(decimalFormat.format((double) monthlyReportVO.getNodeFinishNum() / monthlyReportVO.getNodeNeedFinishNum() * 100)));
        monthlyReportVO.setCompleteFinishRate(monthlyReportVO.getCompleteNeedFinishNum() == 0 ?
                0.0 : Double.parseDouble(decimalFormat.format((double) monthlyReportVO.getCompleteFinishNum() / monthlyReportVO.getCompleteNeedFinishNum() * 100)));
        monthlyReportVO.setCompleteRecordsFinishRate(monthlyReportVO.getCompleteRecordsNeedFinishNum() == 0 ?
                0.0 : Double.parseDouble(decimalFormat.format((double) monthlyReportVO.getCompleteRecordsFinishNum() / monthlyReportVO.getCompleteRecordsNeedFinishNum() * 100)));
        monthlyReportVO.setPRemoveFinishRate(monthlyReportVO.getPRemoveNeedFinishNum() == 0 ?
                0.0 : Double.parseDouble(decimalFormat.format((double) monthlyReportVO.getPRemoveFinishNum() / monthlyReportVO.getPRemoveNeedFinishNum() * 100)));
        monthlyReportVO.setAccountSubmitFinishRate(monthlyReportVO.getAccountSubmitNeedFinishNum() == 0 ?
                0.0 : Double.parseDouble(decimalFormat.format((double) monthlyReportVO.getAccountSubmitFinishNum() / monthlyReportVO.getAccountSubmitNeedFinishNum() * 100)));

        List<EndPlanAnnex> endPlanInAnnexes = baseMapper.getEndPlanInAnnex(request, authSql);
        setEndPlanAnnex(endPlanInAnnexes);
        List<EndPlanAnnex> endPlanOutAnnexes = baseMapper.getEndPlanOutAnnex(request, authSql);
        setEndPlanAnnex(endPlanOutAnnexes);

        monthlyReportVO.setEndPlanInAnnex(endPlanInAnnexes);
        monthlyReportVO.setEndPlanOutAnnex(endPlanOutAnnexes);

        EndPlanAnnex total=new EndPlanAnnex();
        endPlanAnnexCount(total,endPlanInAnnexes);
        endPlanAnnexCount(total,endPlanOutAnnexes);
        List<EndPlanAnnex> totalList=new ArrayList<>();
        totalList.add(total);
        setEndPlanAnnex(totalList);
        monthlyReportVO.setTotal(totalList.get(0));



        return BaseResponse.ok(monthlyReportVO);
    }

    private void setEndPlanAnnex(List<EndPlanAnnex> endPlanAnnexes) {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        if (CollectionUtil.isNotEmpty(endPlanAnnexes)) {
            for (EndPlanAnnex endPlanAnnex : endPlanAnnexes) {
                double completeRate = endPlanAnnex.getCompleteNum() == 0 ? 0.0 : (double) endPlanAnnex.getCompleteNum() / endPlanAnnex.getAnnualCompleteNum() * 100;
                endPlanAnnex.setCompleteRate(Double.parseDouble(decimalFormat.format(completeRate)));

                double completeRecordsRate = endPlanAnnex.getCompleteRecordsNum() == 0 ? 0.0 : (double) endPlanAnnex.getCompleteRecordsNum() / endPlanAnnex.getAnnualCompleteRecordsNum() * 100;
                endPlanAnnex.setCompleteRecordsRate(Double.parseDouble(decimalFormat.format(completeRecordsRate)));

                double pRemoveRate = endPlanAnnex.getPRemoveNum() == 0 ? 0.0 : (double) endPlanAnnex.getPRemoveNum() / endPlanAnnex.getAnnualPRemoveNum() * 100;
                endPlanAnnex.setPRemoveRate(Double.parseDouble(decimalFormat.format(pRemoveRate)));

                double accountSubmitRate = endPlanAnnex.getAccountSubmitNum() == 0 ? 0.0 : (double) endPlanAnnex.getAccountSubmitNum() / endPlanAnnex.getAnnualAccountSubmitNum() * 100;
                endPlanAnnex.setAccountSubmitRate(Double.parseDouble(decimalFormat.format(accountSubmitRate)));

                endPlanAnnex.setTotal(endPlanAnnex.getCompleteNum() + endPlanAnnex.getCompleteRecordsNum() + endPlanAnnex.getPRemoveNum() + endPlanAnnex.getAccountSubmitNum());
                endPlanAnnex.setTotalNeed(endPlanAnnex.getAnnualCompleteNum() + endPlanAnnex.getAnnualCompleteRecordsNum() + endPlanAnnex.getAnnualPRemoveNum() + endPlanAnnex.getAnnualAccountSubmitNum());
                double totalRate = endPlanAnnex.getTotalNeed() == 0 ? 0.0 : (double) endPlanAnnex.getTotal() / endPlanAnnex.getTotalNeed() * 100;
                endPlanAnnex.setTotalRate(Double.parseDouble(decimalFormat.format(totalRate)));
            }
        }
    }

    private HashMap<String, List<OutProjectVo>> convertListToMap (List < OutProjectVo > projectList) {
        HashMap<String, List<OutProjectVo>> resultMap = new HashMap<>();

        if (projectList != null) {
            projectList.stream()
                    .filter(Objects::nonNull)
                    .forEach(project -> {
                        String department = project.getCompetentDepartmentName();
                        if (department != null) {
                            resultMap.computeIfAbsent(department, k -> new ArrayList<>())
                                    .add(project);
                        }
                    });
        }

        return resultMap;
    }

    private void endPlanAnnexCount(EndPlanAnnex total, List<EndPlanAnnex> endPlanAnnexes) {
        if (CollectionUtil.isNotEmpty(endPlanAnnexes)) {
            endPlanAnnexes.forEach(annex -> {
                total.setCompleteNum(total.getCompleteNum() + annex.getCompleteNum());
                total.setCompleteRecordsNum(total.getCompleteRecordsNum() + annex.getCompleteRecordsNum());
                total.setPRemoveNum(total.getPRemoveNum() + annex.getPRemoveNum());
                total.setAccountSubmitNum(total.getAccountSubmitNum() + annex.getAccountSubmitNum());

                total.setAnnualCompleteNum(total.getAnnualCompleteNum() + annex.getAnnualCompleteNum());
                total.setAnnualCompleteRecordsNum(total.getAnnualCompleteRecordsNum() + annex.getAnnualCompleteRecordsNum());
                total.setAnnualPRemoveNum(total.getAnnualPRemoveNum() + annex.getAnnualPRemoveNum());
                total.setAnnualAccountSubmitNum(total.getAnnualAccountSubmitNum() + annex.getAnnualAccountSubmitNum());

                total.setTotal(total.getTotal() + annex.getTotal());
                total.setTotalNeed(total.getTotalNeed() + annex.getTotalNeed());
            });
        }
    }

    /**
     * 根据id查询计划管理信息
     *
     * @param request
     * @param ids
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getGanttMilestoneListByIds(GanttMilestoneStatisticsRequest request, String uId, List<String> ids) {
        String authSql = sysUserService.getAuthSql("gantt_milestone_statistics", uId, null, OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        Page<GanttMilestoneStatisticsRequest> page = new Page<>(request.getPage(), request.getSize());
        // 处理月份参数
        boolean isPlanMonth = "0".equalsIgnoreCase(request.getIcmReportingMonth());
        LocalDate dayOfMonth = getLastDayOfMonth(request.getIcmReportingYear(), request.getIcmReportingMonth());
        String lastDayOfMonth = dayOfMonth == null ? null : dayOfMonth.toString();
        if ("01".equalsIgnoreCase(request.getIsPlan())) {
            // 已经生成的年度计划的gmsId
            List<String> idList = ganttMilestoneYearService.getGanttMilestoneYearList(request.getIcmReportingYear());
            if (ValidateUtil.isNotEmpty(idList)) {
                Set<String> idSet = new HashSet<>(idList);
                // 计划外查询
                List<GanttMilestoneStatisticsResponse> isNotPlan = new ArrayList<>(Optional.ofNullable(baseMapper.getGanttMilestoneList(page, request, authSql, lastDayOfMonth, isPlanMonth, null))
                        .orElse(new ArrayList<>()));
                // 计划内查询
                request.setIsPlan("00");
                List<GanttMilestoneStatisticsResponse> isPlan = baseMapper.getGanttMilestoneList(page, request, authSql, lastDayOfMonth, isPlanMonth, null);
                if (ValidateUtil.isNotEmpty(isPlan)) {
                    // 将计划内的数据排除已经生成的idList，然后附加到计划外的数据中
                    isPlan.removeIf(item -> idSet.contains(item.getId()));
                    isNotPlan.addAll(isPlan);
                }
                return BaseResponse.ok(
                        isNotPlan.stream()
                                .distinct()
                                .sorted(Comparator.comparing(
                                        GanttMilestoneStatisticsResponse::getPCreateTime,
                                        Comparator.nullsFirst(Comparator.naturalOrder())
                                ))
                                .collect(Collectors.toList()),
                        Long.valueOf(page.getTotal()).intValue()
                );
            }
        }
        List<GanttMilestoneStatisticsResponse> data = baseMapper.getGanttMilestoneList(page, request, authSql, lastDayOfMonth, isPlanMonth, ids);
        if (CollectionUtils.isEmpty(data)) {
            return BaseResponse.ok(new ArrayList<>(), 0);
        }
        return BaseResponse.ok(
                data.stream()
                        .sorted(Comparator.comparing(
                                GanttMilestoneStatisticsResponse::getPCreateTime,
                                Comparator.nullsFirst(Comparator.naturalOrder())
                        ))
                        .collect(Collectors.toList()),
                Long.valueOf(page.getTotal()).intValue()
        );
    }

    /**
     * 根据id查询计划管理信息
     *
     * @param request
     * @param uId
     * @param ids
     * @return
     */
    @Override
    public BaseResponse getGanttMilestoneClassInfoByIds(GanttMilestoneStatisticsRequest request, String uId, List<String> ids) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(GanttMilestoneStatisticsRequest.class.getName());
            String authSql = sysUserService.getAuthSql("gantt_milestone_statistics", uId, null, OutUserAuthEnum.Organization.getIndex());

            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }

            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            // 处理月份参数
            boolean isPlanMonth = "0".equalsIgnoreCase(request.getIcmReportingMonth());
            LocalDate dayOfMonth = getLastDayOfMonth(request.getIcmReportingYear(), request.getIcmReportingMonth());
            String lastDayOfMonth = dayOfMonth == null ? null : dayOfMonth.toString();

            // 计划内
            request.setIsPlan("00");
            List<Map<String, Object>> planRawResult = baseMapper.getAllValues(selectClause, request, authSql, lastDayOfMonth, isPlanMonth, ids);

            // 计划外
            request.setIsPlan("01");
            List<Map<String, Object>> outRawResult = baseMapper.getAllValues(selectClause, request, authSql, lastDayOfMonth, isPlanMonth, ids);

            List<Map<String, Object>> rawResult = Stream
                    .concat(planRawResult.stream(), outRawResult.stream())
                    .collect(Collectors.toList());

            classInfoAnnotationList = ClassInfoUtils.resultsHandle(rawResult, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getGanttMilestoneClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }
}
