package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标控制价
 */
@Data
@TableName("Sec_Control_Price")
public class SecControlPrice {
    @TableId(type = IdType.INPUT)
    private String cpId;
    //标段id
    private String secId;
    //类型（预算/控制价）
    private String cpType;
    //编制单位ID
    private String cpPreparedBy;
    //编制单位编制价
    private BigDecimal cpPreparedPrice;
    //建设单位审核造价
    private BigDecimal cpConstructPrice;
    //总建筑面积经济指标(元/㎡)
    private BigDecimal cpEconomicIndicator;
    //不可竞争费合计
    private BigDecimal cpNonCompetitive;
    //工程总价、单位工程、措施费等清单计价合计汇总表
    private String cpBoqDoc;
    //造价咨询出具盖章的招标控制价封面扫描件
    private String cpConsultUnitDoc;
    private String cpCreateUid;
    private String cpCreateUname;
    private Date cpCreateTime;
    private Integer cpState;
    private Date cpStateTime;
    private String cpRemark;
    //政府造价审核备案回执
    private String cpFilingReceipt;
    //预算价
    private BigDecimal cpBiddingPrice;
    //招标控制价
    private BigDecimal cpBudgetPrice;
    //控制价审核单位
    private String cpAuditUnit;
    //控制价审核价
    private BigDecimal cpAuditPrice;
    //费率
    private BigDecimal cpRate;
    //费率描述(最高限价或费率)
    private String cpDescribe;
    //合同模式
    private String cpConMode;
    //企业资质和业绩要求
    private String cpEnterprise;
    //投标人项目经理要求
    private String cpPmRequire;
    //采购文件审核单位
    private String cpAuditCmp;
    //信誉要求
    private String cpReputation;
    //自行采购附件
    private String cpSelfPurchase;
    //集中采购附件
    private String cpFocusPurchase;
    //采购部门（岗位）定稿版
    private String cpPurchaseDep;
    //第三方送审版
    private String cpSubmitCensor;
    //第三方审核意见
    private String cpReviewComments;
    //最终发售版
    private String cpFinalOffer;
    //补充答疑文件
    private String cpSupplement;
    //修改调整版
    private String cpModify;
    //委托记录
    private String cpDelegation;
    //编制：图纸等技术资料、编制报告、软件
    private String cpDrawingFile;
    //审核：编制报告、软件
    private String cpCompileFile;
    //审核：审核报告、软件
    private String cpExamineFile;
    //投标价上限
    private String cpQuotationCeiling;
    //招标控制价公示表及封面盖章件扫描件
    private String cpPublicityTable;
    //呈批事项
    private String cpApprovalMatter;
    private String teId;
    //咨询单位
    private String cpConsultingId;
    //造价咨询公司
    private String costCompany;
    //造价咨询公司控制价
    private String costCompanyPrice;
    //会审意见
    private String cpReviewOpinion;
    //预估合同金额
    private BigDecimal cpConAmount;
}
