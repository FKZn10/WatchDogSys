package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 合同计量计价低代码表单模型
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContractMeasurementValuationModel extends BasePageEntity {
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
    private String conId;
    private String conName;
    private String attachment;

    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "pp.P_Name", no = 1)
    private List<String> pNameList;

    @ClassInfo(title = "合同名称", type = "string", value = "conNameList", dbValue = "con.Con_Name", no = 2)
    private List<String> conNameList;

    @ClassInfo(title = "流程状态", type = "dictionary", value = "stateList", dbValue = "mn.State",
            dictionary = "JPMsoft_LCZT", no = 3)
    private List<Integer> stateList;

    @ClassInfo(title = "创建人", type = "string", value = "createUnameList", dbValue = "mn.Create_Uname", no = 4)
    private List<String> createUnameList;

    @JsonAlias({"contractPaymentApplicationList", "paymentApplicationList"})
    private List<ContractPaymentApplicationModel> contractPaymentApplications;
}
