package com.jpmsoft.projectProducer.pojo.dto.project;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectAcceptanceSettlementDto {
    @ExcelProperty(index = 0, value = "序号")
    private String num;
    @ExcelProperty(index = 1, value = "项目/合同名称")
    private String projectNameTemp;
    @ExcelProperty(index = 2, value = "合同类别")
    private String projectType;
    @ExcelProperty(index = 3, value = "施工单位")
    private String constructionUnitName;
    @ExcelProperty(index = 4, value = "开工日期")
    private String startDate;
    //初步验收日期
    @ExcelProperty(index = 5, value = "初步验收日期")
    private String acceptanceDate;
    //制度规定竣工验收时限（天）
    @ExcelProperty(index = 6, value = "制度规定竣工验收时限（天）")
    private String standardCompletionTime;
    //拟定最迟竣工验收日期  初步验收日期+制度规定竣工验收时限（天）
    @ExcelProperty(index = 7, value = "拟定最迟竣工验收日期")
    private String draftLatestCompletionDate;
    //实际竣工验收组日期
    @ExcelProperty(index = 8, value = "实际竣工验收组日期")
    private String actualCompletionDate;
    //竣工验收超期（天） MIN(TODAY(),实际竣工验收组日期)-拟定最迟竣工验收日期
    @ExcelProperty(index = 9, value = "实际竣工验收组日期")
    private String completionOverdueDays;
    //是否已竣工验收 0：否 1：是 IF(ISBLANK(实际竣工验收组日期),"否","是")
    @ExcelProperty(index = 10, value = "是否已竣工验收")
    private String isCompleted;
    //合同约定结算送审时限（天）
    @ExcelProperty(index = 11, value = "合同约定结算送审时限（天）")
    private String standardSettlementTime;
    //制度规定结算送审时间（天）
    @ExcelProperty(index = 12, value = "制度规定结算送审时间（天）")
    private String draftSettlementTime;
    //拟定最迟结算送审日期
    @ExcelProperty(index = 13, value = "拟定最迟结算送审日期")
    private String draftLatestSettlementDate;
    //实际结算送审日期
    @ExcelProperty(index = 14, value = "实际结算送审日期")
    private String actualSettlementDate;
    //结算送审超期（天） MIN(TODAY(),实际结算送审日期)-拟定最迟结算送审日期
    @ExcelProperty(index = 15, value = "结算送审超期（天）")
    private String settlementOverdueDays;
    //是否已结算送审 0：否 1：是 IF(ISBLANK(实际结算送审日期),"否","是")
    @ExcelProperty(index = 16, value = "是否已结算送审")
    private String isSettlementApproved;
    //制度规定结算审计时间（天）
    @ExcelProperty(index = 17, value = "制度规定结算审计时间（天）")
    private String standardAuditTime;
    //拟定最迟结算审定日期  MAX(拟定最迟结算送审日期,实际结算送审日期)+制度规定结算审计时间（天）
    @ExcelProperty(index = 18, value = "拟定最迟结算审定日期")
    private String draftLatestAuditDate;
    //实际审定日期
    @ExcelProperty(index = 19, value = "实际审定日期")
    private String actualAuditDate;
    //审计超期（天） MIN(TODAY(),实际审定日期)-拟定最迟结算审定日期
    @ExcelProperty(index = 20, value = "审计超期（天）")
    private String auditOverdueDays;
    //是否已结算审定 0：否 1：是 IF(ISBLANK(实际审定日期),"否","是")
    @ExcelProperty(index = 21, value = "是否已结算审定")
    private String isAudited;
    //当前进展
    @ExcelProperty(index = 22, value = "当前进展")
    private String currentProgress;
    /**
     * 竣工超期扣分
     * IF(是否已竣工验收="是",0,IF(AND(制度规定竣工验收时限（天）<竣工验收超期（天）<=2*制度规定竣工验收时限（天）),
     * -1,IF(AND(2*制度规定竣工验收时限（天）<竣工验收超期（天）<=3*制度规定竣工验收时限（天）),-2,IF(3*制度规定竣工验收时限（天）<竣工验收超期（天）,-3,0))))
     */
    @ExcelProperty(index = 23, value = "竣工超期扣分")
    private String completionOverdueDeductScore;
    /**
     * 结算送审超期扣分  IF(AND(是否已竣工验收="是",是否已结算送审="是"),0,
     * IF(AND(是否已竣工验收="是",是否已结算送审="否"),
     * 合同约定结算送审时限（天）+制度规定结算送审时间（天）<结算送审超期（天）<=合同约定结算送审时限（天）+2*制度规定结算送审时间（天） -1
     * 合同约定结算送审时限（天）+2*制度规定结算送审时间（天）<结算送审超期（天）<=合同约定结算送审时限（天）+3*制度规定结算送审时间（天 ） -2
     * 合同约定结算送审时限（天）+3*制度规定结算送审时间（天）<结算送审超期（天）-3
     *
     */
    @ExcelProperty(index = 24, value = "结算送审超期扣分")
    private String settlementOverdueDeductScore;
    /**
     * 结算审计超期扣分
     * K2="是",Q2="是",V2="是",0
     * K2="是",Q2="是",V2="否",
     * R2<U2<=2*R2,-1,
     * 2*R2<U2<=3*R2,-2
     * 3*R2<U2,-3
     */
    @ExcelProperty(index = 25, value = "结算审计超期扣分")
    private String auditOverdueDeductScore;
}
