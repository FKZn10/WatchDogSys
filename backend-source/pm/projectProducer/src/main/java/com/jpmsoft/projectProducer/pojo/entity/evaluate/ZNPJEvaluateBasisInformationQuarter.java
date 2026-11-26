package com.jpmsoft.projectProducer.pojo.entity.evaluate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("znpj_basis_information_quarter")
public class ZNPJEvaluateBasisInformationQuarter implements Serializable {
    private static final long serialVersionUID = -9064094322294307121L;
    private String id;
    //类型 01 合同 02 企业
    private String type;
    /**
     * 评价年份
     */
    private Integer year;
    /**
     * 评价月度
     */
    private Integer month;
    private Integer quarter;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 合同ID
     */
    private String contractId;
    /**
     * 单位ID
     */
    private String companyId;
    /**
     * 单位ID
     */
    private String companyIdJl;
    /**
     * 指标表id
     */
    private String indicatorId;
    /**
     * 考核细则id
     */
    private String evaluateId;
    /**
     * 考核条目Versionid
     */
    private String evaluateVersionId;
    /**
     * 指标版本id
     */
    private String indicatorVersionId;
    /**
     * 次数
     */
    private Integer sum;
    //等级
    private String level;
    //分数
    private Double score;
    //使用期限
    private Date effectiveStartTime;
    private Date effectiveEndTime;
    /**
     * 有效开始年份
     */

    private Integer effectiveStartYear;
    /**
     * 有效开始季度
     */

    private Integer effectiveStartQuarter;
    /**
     * 有效结束年份
     */
    private Integer effectiveEndYear;
    /**
     * 有效结束季度
     */
    private Integer effectiveEndQuarter;
    //通报下文单位

    private String noticeUnit;
    //通报下文单位

    private String noticeUnitId;
    //竣工初验时间

    private Date initialInspectionTime;
    //竣工验收时间

    private Date completionInspectionTime;
    //现场观摩会类型

    private String siteInspectionType;
    //处罚措施

    private String punishmentMeasure;
    //罚金（元）

    private BigDecimal fine;
    //处罚日期

    private Date punishmentDate;
    //处罚结果

    private String punishmentResult;
    //分数类型

    private String scoreType;
    //业务类型

    private String infoType;
    //理由

    private String reason;
    //加减分类型
    /**
     * 加扣分类型： 1、加分 2、扣分
     */

    private Integer plusMinusScoreType;

    /**
     * 创建时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /**
     * 创建人
     */

    private String createBy;
    /**
     * 创建人姓名
     */

    private String createName;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 删除状态：1正常 2删除
     */
    private Integer delFlag;
    /**
     * 状态：1 有效，2 禁用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    //流程状态
    private Integer flowStatus;
    //流程审批时间
    private Date reviewTime;

    /**
     * 证明文件（批次id）
     */
    private String supportingDocument;
    /**
     * 是否包含监理单位并同步生成 ：0否 1是
     */
    private Integer isContainsJl;
    /**
     * 是否包含监理单位并同步生成 ：0否 1是
     */
    private Integer isContainsSg;
    private Date invalidTime;
    private String invalidReason;
    private String invalidFile;
    private String sourceCode;
    private String sourceId;
    private String basisInformationProjectType;
    //合同中单位类型
    private String contractUnitType;
}