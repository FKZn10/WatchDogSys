package com.jpmsoft.common.model.requestAndResponse.dh;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MonthlyInspectionReportDTO extends BasePageEntity {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "pp.p_Name", no = 1)
    private List<String> pNameList;
    private String secId;
    private String secName;
    @ClassInfo(title = "标段名称", type = "string", value = "secNameList", dbValue = "sec.sec_name", no = 2)
    private List<String> secNameList;
    private String conId;
    private String conName;
    @ClassInfo(title = "合同名称", type = "string", value = "conNameList", dbValue = "cc.Con_Name", no = 4)
    private List<String> conNameList;
    private String secEngineeringContent;
    private String mainProjectProgress;
    private Date fillDate;
    @ClassInfo(title = "填报时间", type = "date", value = "fillDateList", dbValue = "mn.Fill_Date", no = 3)
    private List<String> fillDateList;
    private String sectionManager;
    private List<MonthlyInspectionReportQdDTO> qdList;
}
