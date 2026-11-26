package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 工程用款申请表
 */
@Data
@TableName("Engineering_Fund_Application")
public class EngineeringFundApplication extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 123001088745036084L;
    private String id;
    private String pId;
    //Efa_Owner_Company_Name 业主单位名称
    private String efaOwnerCompanyName;
    //Efa_Department_Name 经办部门名称
    private String efaDepartmentName;
    //Efa_User_ID 经办人员ID
    private String efaUserId;
    //Efa_User_Name 经办人员名称
    private String efaUserName;
    //Efa_Leader_ID 分管领导ID
    private String efaLeaderId;
    //Efa_Leader_Name 分管领导名称
    private String efaLeaderName;
    //Efa_Gfp_ID 集团资金计划ID
    private String efaGfpId;
    //工程总投资
    private BigDecimal efaProjectTotal;
    //Efa_Plan_Amount 本期计划金额
    private BigDecimal efaPlanAmount;
    private BigDecimal efaPlanAmountGc;
    //Efa_Arrange_Amount 集团已安排金额
    private BigDecimal efaArrangeAmount;
    //Efa_Balance_Amount 集团账户余额
    private BigDecimal efaBalanceAmount;
    //Efa_Apply_Amount 本次申请金额
    private BigDecimal efaApplyAmount;
    //Efa_Basis 付款依据
    private String efaBasis;
    //Efa_Basis_QT 付款依据其他
    private String efaBasisQt;
    //Efa_Remark 备注
    private String efaRemark;
    //Efa_Pay_State 付款状态
    //集团业财系统，TA那边走完流程、支付完毕将支付结果（状态、支付时间）
    //0:未支付---指本次支付未开始。 未发送到集团业财系统
    //1:支付成功---指本次支付成功，发送到集团业财系统并返回成功
    //2:退回---退回业务系统。发送到集团业财系统并返回退回
    //3:放弃支付---业财系统不支付。 发送到集团业财系统并放弃支付
    //4:支付中---支付中。已发送到集团业财系统
    private Integer efaPayState;

    private Date createTime;
}
