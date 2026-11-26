package com.jpmsoft.common.model.requestAndResponse.enghonorpunish;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;

@Data
public class EngHonorDto extends BaseFileRequest {

    private String id;
    /**
     * 奖项名称
     */
    private String awardName;
    /**
     * 奖项类别
     */
    private String awardType;
    /**
     * 奖项级别
     */
    private String awardLevel;
    /**
     * 报奖项目
     */
    private String awardWinningProject;
    /**
     * 获奖单位
     */
    private String winningUnit;
    /**
     * 获奖单位名称
     */
    private String winningUnitName;
    /**
     * 颁奖部门
     */
    private String awardDepartment;
    /**
     * 获奖时间
     */
    private Date winningTime;
    /**
     * 获奖文件
     */
    private String winningFile;
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
     * 审批状态
     */
    private Integer state;
    /**
     * 审批时间
     */
    private Date stateTime;
    /**
     * 项目名称
     */
    @JsonProperty("pName")
    private String pName;

}
