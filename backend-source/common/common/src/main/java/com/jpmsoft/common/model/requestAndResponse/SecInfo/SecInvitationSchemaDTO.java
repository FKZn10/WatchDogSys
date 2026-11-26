package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 邀标工作方案
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SecInvitationSchemaDTO extends BasePageEntity {
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
    private String secId;
    private String secName;
    private String remark;

    @JsonProperty("pCategory")
    private String pCategory;
    @JsonProperty("pNames")
    @ClassInfo(title = "项目名称", type = "string", value = "pNames", dbValue = "p.p_name", no = 1)
    private List<String> pNames;
    @ClassInfo(title = "标段名称", type = "string", value = "secNames", dbValue = "s.sec_name", no = 2)
    private List<String> secNames;
    @JsonProperty("pCategorys")
    @ClassInfo(title = "项目分类", type = "dictionary", value = "pCategorys", dbValue = "p.p_category",dictionary = "JPMsoft_NDTZXMFL", no = 3)
    private List<String> pCategorys;
    @ClassInfo(title = "发起人", type = "string", value = "createUnames", dbValue = "mn.Create_Uname", no = 4)
    private List<String> createUnames;
    @ClassInfo(title = "发起时间", type = "date", value = "createTimes", dbValue = "mn.create_time", no = 5)
    private List<String> createTimes;
    @ClassInfo(title = "流程状态", type = "dictionary", value = "states", dbValue = "mn.state",dictionary = "JPMsoft_LCZT", no = 6)
    private List<String> states;

    private List<String> participationOrFinished;
}
