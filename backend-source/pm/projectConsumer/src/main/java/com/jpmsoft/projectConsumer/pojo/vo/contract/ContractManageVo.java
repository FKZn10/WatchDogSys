package com.jpmsoft.projectConsumer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

//合同支付管理vo
@Data
public class ContractManageVo implements Serializable {

    private static final long serialVersionUID = 2772389171905524358L;


    private String conId;


    private String preName;   //项目名

    private String conName;  //合同名

    private String  companyPartybName;  //乙方单位

    private String  conCategory;   //合同类别

    private String  conCode;   //编号


    //所有金额返回否使用 *Str
    private BigDecimal conAmtStr;  //合同金额str
    private BigDecimal conAmtEnd;  //合同金额
    private List<String> conAmts;  //合同金额

    private Integer page;

    private Integer size;



    private List<String> conSupplementAmts; //合同金额  含补充合同
    private BigDecimal  conSupplementAmtStr;   //合同金额  含补充合同
    private BigDecimal  conSupplementAmtEnd;   //合同金额  含补充合同



    private List<String> settlementAmts; //结算金额
    private BigDecimal   settlementAmtStr; //结算金额
    private BigDecimal   settlementAmtEnd; //结算金额



    private BigDecimal   conApplyAmtApprovalStr;   //合同申请金额审批中
    private BigDecimal   conApplyAmtApprovalEnd;   //合同申请金额审批中
    private List<String> conApplyAmtApprovals; //合同申请金额审批中


    private BigDecimal  conApplyAmtApprovalComEnd;  //合同申请金额审批完成
    private BigDecimal  conApplyAmtApprovalComStr;  //合同申请金额审批完成
    private List<String> conApplyAmtApprovalComs; //合同申请金额审批完成


    private String   conPartyAAgent;  //甲方联系人

    private String   conPartyBAgent;  //乙方联系人

    private String   conPartyBAgentTel;  //乙方电话



    private List<String> conDisAmtApprovals; //合同拨付金额审批中
    private BigDecimal   conDisAmtApprovalStr;   //合同拨付金额审批中
    private BigDecimal   conDisAmtApprovalEnd;   //合同拨付金额审批中



    private List<String> conDisApprovalCompleteds; //合同拨付金额审批完成

    private BigDecimal  conDisApprovalCompletedStr;  //合同拨付金额审批完成

    private BigDecimal  conDisApprovalCompletedEnd;  //合同拨付金额审批完成       审批结束的
    private Integer conPaymentState;  //合同支付状态

    private String rmk;

    private String state;

    private String  companyPartyAName;  //甲方单位

    private List<String>  comPayIds;  //甲方单位

    private String year;

    //资金支付
   private List<Map<String,Object>> fundLists;

   //拨付支付
   private List<Map<String,Object>> disburLists;





}
