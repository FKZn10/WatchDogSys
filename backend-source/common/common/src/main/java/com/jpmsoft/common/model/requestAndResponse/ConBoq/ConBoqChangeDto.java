package com.jpmsoft.common.model.requestAndResponse.ConBoq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单变更
 */
@Data
public class ConBoqChangeDto extends BaseFileRequest implements Serializable {

    /**
     * AI识别后台任务ID。
     */
    private String runId;

    private static final long serialVersionUID = 7851239641078523015L;

    /**
     * 变更主键
     */
    @ProcessFields(type = ProcessFieldEnum.id)
    private String boqcId;
    /**
     * 标题
     */
    private String boqcTitle;
    /**
     * 备注
     */
    private String boqcRemark;
    /**
     * 版本号
     */
    private String boqcVersion;
    /**
     * 项目ID
     */
    private String boqcProductId;
    /**
     * 项目名
     */
    private String boqcProductName;
    /**
     * 合同ID
     */
    private String boqcContractId;
    /**
     * 合同名
     */
    private String boqcContractName;
    /**
     * 创建人ID
     */
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String boqcCreateUid;
    /**
     * 创建人姓名
     */
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String boqcCreateUname;
    /**
     * 创建时间
     */
    @ProcessFields(type = ProcessFieldEnum.createTime)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqcCreateTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqcUpdateTime;
    /**
     * 审批状态
     */
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer boqcState;
    /**
     * 审批时间
     */
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqcStateTime;
    /**
     * 变更子表数据
     */
    private List<ConBoqDtlChangeDto> dtlList;
    /**
     * 附件关联ID
     */
    private String rteFileIds;
    /**
     * 项目名称
     */
    @JsonProperty("pName")
    private String pName;
    /**
     * 合同名称
     */
    private String conName;

    private String conBoqVersion;
    private String conBoqType;
    private String boqId;
    private String reason;
}
