package com.jpmsoft.common.model.request.Design;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class DesignResultsIssuedRequest extends BasePageEntity {

    @JsonProperty("pName")
    @ClassInfo(title = "项目名称", type = "string", value = "pName", dbValue = "pre.P_Name",no = 1)
    private List<String> pName;

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("driName")
    @ClassInfo(title = "成果名称", type = "string", value = "driName", dbValue = "mn.Dri_Name",no = 2)
    private List<String> driName;

    private String driNameLike;

    @JsonProperty("driType")
    @ClassInfo(title = "成果类型", type = "dictionary", value = "driType", dbValue = "mn.Dri_Type",dictionary = "JPMsoft_SJCGLX",no = 3)
    private List<String> driType;


    @JsonProperty("createUname")
    @ClassInfo(title = "发起人", type = "string", value = "createUname", dbValue = "mn.Create_Uname", no = 4)
    private List<String> createUname;

/*    @JsonProperty("createTime")
    @ClassInfo(title = "发起时间", type = "date", value = "createTime", dbValue = "mn.Create_Time", no = 5)
    private List<Date> createTime;*/

    private String driFormType;

    private Integer state;

    // 01 是 统计台账 02 模块表 多条件筛选  默认 null 模块表 多条件筛选
    private String ledger;
}
