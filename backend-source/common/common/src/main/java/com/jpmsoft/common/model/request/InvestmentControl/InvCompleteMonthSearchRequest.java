package com.jpmsoft.common.model.request.InvestmentControl;

import com.jpmsoft.common.annotations.ClassInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/9/19 9:16
 * @Description:
 * @Version: 1.0
 */
@Data
public class InvCompleteMonthSearchRequest implements Serializable {

    private static final long serialVersionUID = 1563293007677720058L;

    private Integer pageNum;

    private Integer pageSize;

    @ClassInfo(title = "上报表格编号",type="string",value = "reportTableNum",dbValue = "report_table_num",no = 10)
    private List<String> reportTableNum;

    @ClassInfo(title = "项目名称",type="string",value = "projectName",dbValue = "p_name",no = 20)
    private List<String> projectName;

    @ClassInfo(title = "项目管理单位",type="string",value = "competentDepartmentName",dbValue = "d_name",no = 30)
    private List<String> competentDepartmentName;

    @ClassInfo(title = "项目类别",type="dictionary",value = "ipvType", dictionary = "JPMsoft_TZXMLB", dbValue = "ipv_p_type",no = 40)
    private List<String> ipvType;

    @ClassInfo(title = "申报年份",type="string",value = "icmReportingYear",dbValue = "icm_reporting_year",no = 50)
    private List<String> icmReportingYear;

    @ClassInfo(title = "申报月份",type="string",value = "icmReportingMonth",dbValue = "icm_reporting_month",no = 60)
    private List<String> icmReportingMonth;

    @ClassInfo(title = "当月是否开工",type="dictionary",value = "currentMonthStart",dictionary = "JPMsoft_ZDTY",dbValue = "current_month_start" ,no = 70)
    private List<String> currentMonthStart;

    @ClassInfo(title = "累计是否开工",type="dictionary",value = "cumulativeStart",dictionary = "JPMsoft_ZDTY",dbValue = "cumulative_start" ,no = 80)
    private List<String> cumulativeStart;

    @ClassInfo(title = "计划开工年月",type="month",value = "planStartTime",dbValue = "plan_start_time",no = 90)
    private String planStartTime;

    @ClassInfo(title = "开工计划完成情况",type="dictionary",value = "planCompleteResult",dictionary = "JPMsoft_KGJHWCQK",dbValue = "plan_complete_result" ,no = 100)
    private List<String> planCompleteResult;

    @ClassInfo(title = "当月是否完工",type="dictionary",value = "currentMonthComplete",dictionary = "JPMsoft_ZDTY",dbValue = "current_month_complete" ,no = 70)
    private List<String> currentMonthComplete;

    @ClassInfo(title = "累计是否完工",type="dictionary",value = "cumulativeComplete",dictionary = "JPMsoft_ZDTY",dbValue = "cumulative_complete" ,no = 80)
    private List<String> cumulativeComplete;

    @ClassInfo(title = "计划完工年月",type="month",value = "planEndTime",dbValue = "plan_end_time",no = 90)
    private String planEndTime;

    @ClassInfo(title = "完工计划完成情况",type="dictionary",value = "completeResult",dictionary = "JPMsoft_WGJHWCQK",dbValue = "complete_result" ,no = 100)
    private List<String> completeResult;

    @ClassInfo(title = "实际开工年月",type="month",value = "actualStartTime",dbValue = "actual_start_time",no = 110)
    private String actualStartTime;

    @ClassInfo(title = "实际完工年月",type="month",value = "actualEndTime",dbValue = "actual_end_time",no = 120)
    private String actualEndTime;

    @ClassInfo(title = "当月完成投资额(元)",type="money",value = "icmMonthlyInvestment",dbValue = "icm_monthly_investment",no = 130)
    private List<BigDecimal> icmMonthlyInvestment;

    @ClassInfo(title = "年度累计完成投资额(元)",type="money",value = "icmAnnualInvestment",dbValue = "icm_annual_investment",no = 140)
    private List<BigDecimal> icmAnnualInvestment;

    @ClassInfo(title = "年度累计投资完成率(%)",type="float",value = "icmAnnualProportion",dbValue = "icm_annual_proportion",no = 150)
    private List<Float> icmAnnualProportion;

    @ClassInfo(title = "当月年度累计计划投资(元)",type="money",value = "monthCumulativeInv",dbValue = "month_cumulative_inv",no = 160)
    private List<BigDecimal> monthCumulativeInv;

    @ClassInfo(title = "投资计划完成率(%)",type="float",value = "monthCumulativeInvProportion",dbValue = "month_cumulative_inv_proportion",no = 170)
    private List<Float> monthCumulativeInvProportion;

    @ClassInfo(title = "在场人数",type="integer",value = "icmPersonnelNum",dbValue = "icm_personnel_num",no = 180)
    private List<Integer> icmPersonnelNum;

    @ClassInfo(title = "形象进度",type="dictionary",value = "icmImageProgress",dictionary = "JPMsoft_XXJD",dbValue = "icm_image_progress" ,no = 190)
    private List<String> icmImageProgress;

    @ClassInfo(title = "形象进度补充说明",type="string",value = "icmImgProgress",dbValue = "icm_img_progress",no = 200)
    private List<String> icmImgProgress;

    @ClassInfo(title = "状态",type="dictionary",value = "transferStatus",dictionary = "JPMsoft_SJSPZT",dbValue = "transfer_status" ,no = 210)
    private List<String> transferStatus;

    @ClassInfo(title = "填报时间",type="date",value = "icmCreateTime",dbValue = "icm_create_time",no = 250)
    private String icmCreateTime;

    @ClassInfo(title = "填报人",type="string",value = "icmCreateUname",dbValue = "icm_create_uname",no = 260)
    private List<String> icmCreateUname;

}
