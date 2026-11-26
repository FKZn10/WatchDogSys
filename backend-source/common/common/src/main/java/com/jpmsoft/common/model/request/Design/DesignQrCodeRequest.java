package com.jpmsoft.common.model.request.Design;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class DesignQrCodeRequest extends BasePageEntity {

    @JsonProperty("pName")
    @ClassInfo(title = "项目名称", type = "string", value = "pName", dbValue = "pre.P_Name",no = 1)
    private List<String> pName;

    @JsonProperty("dqcUsed")
    @ClassInfo(title = "是否已使用", type = "dictionary", value = "dqcUsed", dbValue = "mn.Dqc_Used",no = 2,dictionary = "JPMsoft_SJSFLX")
    private List<String> dqcUsed;

//    @JsonProperty("dqcApplyTime")
//    @ClassInfo(title = "申请时间", type = "date", value = "dqcApplyTime", dbValue = "mn.Dqc_ApplyTime",no = 3)
//    private Date dqcApplyTime;

}
