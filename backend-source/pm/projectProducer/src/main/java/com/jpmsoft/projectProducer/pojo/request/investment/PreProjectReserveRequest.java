package com.jpmsoft.projectProducer.pojo.request.investment;

import com.jpmsoft.common.annotations.ClassInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PreProjectReserveRequest {
    @ClassInfo(title = "项目名称",type="string",value = "pprNameList",dbValue = "ppr_Name")
    private List<String> pprNameList;
    @ClassInfo(title = "建设模式",type="dictionary",value = "pprModelList",dbValue = "ppr_Model",dictionary = "JPMsoft_CBJSMS")
    private List<String> pprModelList;
    @ClassInfo(title = "匡算总投资",type="money",value = "pprRoughList",dbValue = "ppr_Rough")
    private List<BigDecimal> pprRoughList;
    @ClassInfo(title = "储备单位",type="string",value = "pprReserveCpyNameList",dbValue = "ppr_Reserve_Cpy")
    private List<String> pprReserveCpyNameList;
    @ClassInfo(title = "项目管理单位",type="string",value = "pprManagerUnitNameList",dbValue = "ppr_Manager_Unit")
    private List<String> pprManagerUnitNameList;
    @ClassInfo(title = "项目管理单位所在部门",type="string",value = "pprManagerDepNameList",dbValue = "ppr_Manager_Dep")
    private List<String> pprManagerDepNameList;
    @ClassInfo(title = "储备阶段",type="dictionary",value = "pprReserveStageList",dbValue = "ppr_Reserve_Stage",dictionary = "JPMsoft_CBJD")
    private List<String> pprReserveStageList;
    @ClassInfo(title = "储备状态",type="dictionary",value = "pprReserveStatusList",dbValue = "ppr_Reserve_Status",dictionary = "JPMsoft_CBZT")
    private List<String> pprReserveStatusList;
    @ClassInfo(title = "计划立项时间",type="month",value = "pprPlanTimeStr",dbValue = "ppr_Plan_Time")
    private List<Date> pprPlanTimeStr;
    @ClassInfo(title = "创建/维护人",type="string",value = "createUnameList",dbValue = "Create_Uname")
    private List<String> createUnameList;
    @ClassInfo(title = "发起/维护时间",type="date",value = "createTimeStr",dbValue = "create_Time")
    private List<Date> createTimeStr;
    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dbValue = "state",dictionary = "JPMsoft_LCZT")
    private List<Integer> stateList;
}
