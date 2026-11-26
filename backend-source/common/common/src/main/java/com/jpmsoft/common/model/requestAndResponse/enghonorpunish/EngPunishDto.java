package com.jpmsoft.common.model.requestAndResponse.enghonorpunish;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EngPunishDto extends BaseFileRequest {

    private String id;
    /**
     * 处罚名称
     */
    private String punishName;
    /**
     * 处罚类型
     */
    private String punishType;
    /**
     * 处罚所属项目
     */
    private String punishProject;

    /**
     * 处罚金额（万元）
     */
    private BigDecimal punishMoney;
    /**
     * 处罚机构
     */
    private String punishUnit;
    /**
     * 处罚时间
     */
    private Date punishTime;
    /**
     * 处罚文件
     */
    private String punishFile;
    /**
     * 处罚情况说明
     */
    private String punishRemark;
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
