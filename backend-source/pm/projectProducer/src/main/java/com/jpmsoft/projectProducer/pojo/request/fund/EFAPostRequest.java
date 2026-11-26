package com.jpmsoft.projectProducer.pojo.request.fund;

import com.jpmsoft.projectProducer.pojo.vo.fund.EngineeringFundApplicationBankDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.EngineeringFundApplicationFundNumberDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanVo;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class EFAPostRequest {
    private String id;
    private String pName;
    //Efa_Owner_Company_Name 业主单位名称
    private String efaOwnerCompanyName;
    //工程总投资
    private Double efaPTotal;
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
    //Efa_Plan_Amount 本期计划金额
    private Double efaPlanAmount;
    //Efa_Arrange_Amount 集团已安排金额
    private Double efaArrangeAmount;
    //Efa_Balance_Amount 集团账户余额
    private Double efaBalanceAmount;
    //Efa_Apply_Amount 本次申请金额
    private Double efaApplyAmount;
    //Efa_Gfp_ID 集团资金计划ID
    private String efaGfpId;
    //资金计划
    private GroupFundPlanVo groupFundPlanVo;
    //付款依据
    //Efa_Basis 付款依据
    private String efaBasis;
    //Efa_Basis_QT 付款依据其他
    private String efaBasisQt;
    private List<EngineeringFundApplicationFundNumberDetailVo> engineeringFundApplicationFundNumberList;

    private List<EngineeringFundApplicationBankDetailVo> bankDetailVo;
    //Efa_Remark 备注
    private String efaRemark;
}
