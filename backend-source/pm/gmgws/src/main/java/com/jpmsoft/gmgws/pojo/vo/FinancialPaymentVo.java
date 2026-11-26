package com.jpmsoft.gmgws.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.gmgws.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


//财政支付统计excel导入vo
@Data
public class FinancialPaymentVo extends BaseFileRequest implements Serializable {


    private static final long serialVersionUID = 441519539831217796L;

    @ProcessFields(type = ProcessFieldEnum.id)
    public String id;

    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;

    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;

    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;


    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;

    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;

    private String fpApplicantName;  //申请单位名称



    private String fpApplicantCode; //申请单位编号


    @ExcelProperty(index=0,value="批次号")
    private String  fpBatchNumber;  //  批次号


    @ExcelValid(message = "申请单号必填")
    @ExcelProperty(index=1,value="申请单号*")
    private String  fpApplicationNumber ; //申请单号

    @ExcelValid(message = "资金用途必填")
    @ExcelProperty(index=2,value="资金用途*")
    private String   fpUseFunds ; //资金用途

    @ExcelValid(message = "部门经济分类必填")
    @ExcelProperty(index=3,value="部门经济分类*")
    private String   fpSectoralEc ;  //部门经济分类

    @ExcelValid(message = "申请金额必填")
    @ExcelProperty(index=4,value="申请金额*")
    private BigDecimal  fpAmountApplied  ;//申请金额

    @ExcelValid(message = "收款单位必填")
    @ExcelProperty(index=5,value="收款单位*")
    private String  fpCollectingUnit ;//收款单位

    @ExcelValid(message = "开户行必填")
    @ExcelProperty(index=6,value="开户行*")
    private String  fpBankDeposit; // 开户行

    @ExcelValid(message = "银行账号必填")
    @ExcelProperty(index=7,value="银行账号*")
    private String   fpBankNumber; // 银行账号

    @ExcelValid(message = "指标摘要必填")
    @ExcelProperty(index=8,value="指标摘要*")
    private String  fpIndexSummary;  //指标摘要;

    @ExcelValid(message = "政府经济分类必填")
    @ExcelProperty(index=9,value="政府经济分类*")
    private String   fpGovernmentEc; //政府经济分类

    @ExcelValid(message = "功能科目必填")
    @ExcelProperty(index=10,value="功能科目*")
    private String   fpFunctionalAccount;  //功能科目

    @ExcelValid(message = "资金性质必填")
    @ExcelProperty(index=11,value="资金性质*")
    private String  fpNatureFunds; // 资金性质

    @ExcelValid(message = "资金来源必填")
    @ExcelProperty(index=12,value="资金来源*")
    private String  fpSourceFunds; //资金来源

    @ExcelValid(message = "业务类型必填")
    @ExcelProperty(index=13,value="业务类型*")
    private String fpBusinessType; //业务类型

    @ExcelValid(message = "结算方式必填")
    @ExcelProperty(index=14,value="结算方式*")
    private String fpSettlementMethod; //结算方式

    @ExcelValid(message = "支付方式必填")
    @ExcelProperty(index=15,value="支付方式*")
    private String fpPaymentMethod;  //支付方式

}
