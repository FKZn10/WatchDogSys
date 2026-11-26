package com.jpmsoft.projectProducer.pojo.request.fund;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
/*
    工程用款申请参数
 */
public class EngineeringApplyRequest {
    //支付申请id
    private String payApplyId;
    //经办人员
    private String applyerName;
    //付款类型(业财系统提供)
    private String applyTypeId;
    //业务单所属公司NO(业财系统提供视图)
    private String companyNo;
    //业务单所属公司(业财系统提供视图)
    private String companyName;
    //业务单编号（业务单唯一标识）
    private String busiNo;
    //备注说明 （限长200字符）
    private String remark;
    //业务单查看连接
    private String busiUrl;
    //回调接口http://****/***
    private String callBackFun;
    //接入端ID(appId)
    private String appId;
    //项目名称(业财系统提供视图)
    private String prjName;
    //项目编号(业财系统提供视图)
    private String prjNo;
    //业主单位(业财系统提供)
    private String yzdw;
    //工程总投资
    private String gcztz;
    //经办部门
    private String jbbm;
    //分管领导
    private String fgld;
    //本期计划金额
    private BigDecimal bqjhje;
    //集团已安排金额
    private BigDecimal jtyapje;
    //集团账户余额
    private BigDecimal jtzhye;
    //本次申请金额
    private BigDecimal bcsqje;
    //付款依据
    private String fkyj;
    //依据表格
    private List<EngineeringApplyYjbgRequest> yjbg;
    //工程账户-收款单位
    private String gskdw;
    //工程账户-收款单位开户银行
    private String gskkhh;
    //工程账户-收款单位账号
    private String gskzh;
    //数字货币账户-数字人民币收款单位
    private String sskdw;
    //数字货币账户-数字人民币开户银行
    private String skhyh;
    //数字货币账户-数字人民币收款ID
    private String sskid;
    //本次付款账户类型（值：工程账户/数字货币账户）
    private String zhlx;
    //支付申请经办人（即申请单在业财系统中的经办人）
    private String handlerAccount;

}
