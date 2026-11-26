package com.jpmsoft.projectProducer.pojo.dto.fund;

import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/***
 * 工程用款申请表Dto
 */
@Data
public class EngineeringFundApplicationDto extends BaseFileDto {
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
    //Efa_Plan_Amount 本期计划金额（工程部审核数）
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
    //4:支付失败---支付失败。发送到集团业财系统错误
    //5:支付中---支付中。已发送到集团业财系统
    private Integer efaPayState;
	@ProcessFields(type = ProcessFieldEnum.state)
	private Integer state;
	@ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
	@ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
	@ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
	@ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    private List<EngineeringFundApplicationFundNumberDetailDto> detailDtos;

    private List<EngineeringFundApplicationBankDetailDto> bankDetailDtos;
}
