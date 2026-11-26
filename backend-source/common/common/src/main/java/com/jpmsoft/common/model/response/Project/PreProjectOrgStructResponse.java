package com.jpmsoft.common.model.response.Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PreProjectOrgStructResponse{
    private String id;
    /**
     * 项目id
     */
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    //项目管理单位
    private String pCompetentDepartment;

    private String pCompetentDepartmentName;

    //立项形式 01可研批复、02投资备案证、03其他
    private String pProjectFormType;
    /**
     * 证明文件
     */
    private String proofFile;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人id
     */
    private String createUid;
    /**
     * 创建人姓名
     */
    private String createUname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 流程状态
     */
    private Integer state;
    /**
     * 审批时间
     */
    private Date stateTime;


    private List<PreProjectOrgStructUnitResponse> preProjectOrgStructUnitList;


}
