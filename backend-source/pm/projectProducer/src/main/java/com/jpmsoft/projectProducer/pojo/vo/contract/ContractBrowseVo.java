package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ContractBrowseVo  {



    private String conId;


    private String preName;   //项目名

    private String conName;  //合同名


    private String  companyPartybName;  //乙方单位

    private String  conCategory;   //合同类别



    private String  conCode;   //编号

    private String  companyPartyAName;  //甲方单位

    private BigDecimal pFinal;  //竣工决算


    //所有金额返回否使用 *Str
    private BigDecimal conAmtStr;  //合同金额str
    private BigDecimal conAmtEnd;  //合同金额
    private List<String> conAmts;  //合同金额


    private List<String> conSupplementAmts; //合同金额  含补充合同
    private BigDecimal  conSupplementAmtStr;   //合同金额  含补充合同
    private BigDecimal  conSupplementAmtEnd;   //合同金额  含补充合同



    private List<String> settlementAmts; //结算金额
    private BigDecimal   settlementAmtStr; //结算金额
    private BigDecimal   settlementAmtEnd; //结算金额

    private Integer page;

    private Integer size;


    //签订日期
    private List<String> signDate;

    private String signDateStr;
    private String signDateEnd;

    //竣工日期
    private List<String> completedDate;

    private String completedDateStr;
    private String completedDateEnd;

    //主管部门
    private String dName;


    private String   conPartyAAgent;  //甲方联系人

    private String   conPartyBAgent;  //乙方联系人

    private String   conPartyBAgentTel;  //乙方电话

    //流程状态
    private String state;

    //合同可支付状态
    private Integer conPaymentState;


    //合同创建人
    private String contrCreatePeople;

    //合同创建时间
    private List<String> contrCreateDate;
    private String contrCreateDateStr;
    private String contrCreateDateEnd;


    private BigDecimal   conApplyAmtApprovalStr;   //合同申请金额审批中
    private BigDecimal   conApplyAmtApprovalEnd;   //合同申请金额审批中
    private List<String> conApplyAmtApprovals; //合同申请金额审批中


    private BigDecimal  conApplyAmtApprovalComEnd;  //合同申请金额审批完成
    private BigDecimal  conApplyAmtApprovalComStr;  //合同申请金额审批完成
    private List<String> conApplyAmtApprovalComs; //合同申请金额审批完成


    private List<String> conDisAmtApprovals; //合同拨付金额审批中
    private BigDecimal   conDisAmtApprovalStr;   //合同拨付金额审批中
    private BigDecimal   conDisAmtApprovalEnd;   //合同拨付金额审批中



    private List<String> conDisApprovalCompleteds; //合同拨付金额审批完成

    private BigDecimal  conDisApprovalCompletedStr;  //合同拨付金额审批完成

    private BigDecimal  conDisApprovalCompletedEnd;  //合同拨付金额审批完成


    //合同资金支付
    private List<String> fundsPayment;

    private BigDecimal fundsPaymentStr;
    private BigDecimal fundsPaymentEnd;
    //合同当前申请实际支付
    private BigDecimal applyActualPay;

    //合同当前未付额度
    private BigDecimal ContrNotAmt;
}
