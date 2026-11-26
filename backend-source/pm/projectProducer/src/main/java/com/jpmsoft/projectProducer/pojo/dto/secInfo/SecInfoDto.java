package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.model.request.SecInfo.QuotationRequestDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 标段信息
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 10:52
 */
@Data
public class SecInfoDto implements Serializable {

    private static final long serialVersionUID = 1879838726429576799L;

    private String secId;
    private String bpcId;
    private String secName;
    private String secCode;
    private String secParentId;
    private Integer secYear;
    private String secBiddingType;
    private String secConType;
    private String secType;
    private BigDecimal secMoney;
    private Date secStartTime;
    private Date secEndTime;
    private String secRemark;
    private Date secFlowTime;
    private Integer secFlowState;
    private String secCreateUid;
    private String secCreateUname;
    private Date secCreateTime;
    private String secPid;
    private String secTenderee;
    private String secFeatures;
    private String secPlace;
    private String secScale;
    private BigDecimal secRate;
    private String secOtherValuation;
    private String secDeterminType;
    private String secOrganizeForm;
    private String secTradingPlace;
    private Integer secBiddingCycle;
    private String secApprovalTitle;
    private String secApprovalNum;
    private Date secApprovalTime;
    private String secApprovalFile;
    private String secPricingMethod;
    private String secPurchaseType;

    /**
     * 采购组织。
     */
    private String secBuyOrg;

    /**
     * 采购方式。
     */
    private String secBuyType;

    /**
     * 项目概况。
     */
    private String projectOverview;

    /**
     * 标段所属部门。
     */
    private String secDepartment;

    /**
     * 会议编号。
     */
    private String meetingNumber;

    /**
     * 完成日期。
     */
    private String finishDate;

    /**
     * 联系邮箱。
     */
    private String contactEmail;

    /**
     * 联系固定电话。
     */
    private String contactLandlinePhone;

    /**
     * 联系人电话。
     */
    private String contactPersonPhone;

    /**
     * 联系人。
     */
    private String contactPerson;

    /**
     * 经费项目。
     */
    private String fundsProject;

    /**
     * 预算负责人姓名。
     */
    private String budgetManagerName;

    /**
     * 预算负责人账号。
     */
    private String budgetManagerAccount;

    /**
     * 经费部门。
     */
    private String fundsDepartment;

    /**
     * 经费名称。
     */
    private String fundsName;

    /**
     * 经费账号。
     */
    private String fundsAccount;
    private String secEvalMethod;
    private Boolean secEvaluateSeparation;
    private Integer secDuration;
    private String secQualiReview;
    private String secInvestorsQuali;
    private String secValuationFile;
    private Date secBidTime;
    private String secLoaction;
    private String secAgency;

    /**
     * 序号
     */
    private String secOrderNumber;

    /**
     * 标段计划类型
     */
    private String secCostType;

    /**
     * 标段分类
     */
    private String secSectionClassify;

    /**
     * 排序
     */
    private String secSort;

    private String tenderName;

    private String secSubjectNameType;

    /**
     * 采购编号
     */
    private String purchaseNumber;

    /**
     * 变更阶段
     */
    private String secChangePhase;

    /**
     * 标段类型（,分隔）
     */
    private String secTypeStr;

    /**
     * 询价调研明细。
     */
    private List<QuotationRequestDTO> quotationRequest;
}
