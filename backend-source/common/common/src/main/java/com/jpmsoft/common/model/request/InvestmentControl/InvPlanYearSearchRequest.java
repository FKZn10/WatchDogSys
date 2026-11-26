package com.jpmsoft.common.model.request.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 年度投资计划
 */
@Data
public class InvPlanYearSearchRequest implements Serializable {

    private static final long serialVersionUID = 2452547846548653309L;

    private Integer pageNum;

    private Integer pageSize;

    @ClassInfo(title = "申报年份",type="string",value = "ipvDeclareYear",dbValue = "ipv_declare_year",no = 1)
    private List<String> ipvDeclareYear;

    @ClassInfo(title = "项目名称",type="string",value = "projectName",dbValue = "p_name",no = 2)
    private List<String> projectName;

    @ClassInfo(title = "项目编码",type="string",value = "projectCode",dbValue = "p_code",no = 3)
    private List<String> projectCode;

    @ClassInfo(title = "项目管理单位",type="string",value = "competentDepartmentName",dbValue = "d_name",no = 4)
    private List<String> competentDepartmentName;

    @ClassInfo(title = "项目类别",type="dictionary",value = "ipvType", dictionary = "JPMsoft_TZXMLB", dbValue = "ipv_p_type",no = 5)
    private List<String> ipvType;

    @ClassInfo(title = "总投资(元)",type="money",value = "invTotal",dbValue = "p_inv_total",no = 6)
    private List<BigDecimal> invTotal;

    @ClassInfo(title = "去年年底累计投资(元)",type="money",value = "ipvDoneLastYear",dbValue = "ipv_done_last_year",no = 7)
    private List<BigDecimal> ipvDoneLastYear;

    @ClassInfo(title = "当年计划投资(元)",type="money",value = "ipvPlannedInvestment",dbValue = "ipv_planned_investment",no = 8)
    private List<BigDecimal> ipvPlannedInvestment;

    @ClassInfo(title = "当年新开工",type="dictionary",value = "ipvNewly",dictionary = "JPMsoft_ZDTY",dbValue = "ipv_newly" ,no = 9)
    private List<String> ipvNewly;

    @ClassInfo(title = "当年完工",type="dictionary",value = "ipvCompleted",dictionary = "JPMsoft_ZDTY",dbValue = "ipv_completed" ,no = 10)
    private List<String> ipvCompleted;

    @ClassInfo(title = "计划开工年月",type="month",value = "ipvStart",dbValue = "ipv_start",no = 12)
    private String ipvStart;

    @ClassInfo(title = "计划完工年月",type="month",value = "ipvEnd",dbValue = "ipv_end",no = 13)
    private String ipvEnd;

    @ClassInfo(title = "填报时间",type="date",value = "ipvCreateTime",dbValue = "ipv_create_time",no = 14)
    private String ipvCreateTime;

    @ClassInfo(title = "填报人",type="string",value = "ipvCreateUname",dbValue = "ipv_create_uname",no = 15)
    private List<String> ipvCreateUname;

    @ClassInfo(title = "流程状态",type="dictionary",value = "ipvState",dictionary = "JPMsoft_LCZT",dbValue = "ipv_state",no = 16)
    private List<Integer> ipvState;

    @ClassInfo(title = "上报表格编号",type="string",value = "reportTableNum",dbValue = "report_table_num",no = 17)
    private List<String> reportTableNum;



}
