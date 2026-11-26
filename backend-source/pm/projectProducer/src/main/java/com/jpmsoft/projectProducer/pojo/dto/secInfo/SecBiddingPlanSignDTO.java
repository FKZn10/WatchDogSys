package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标计划 数据完整性签名生成实体
 */
@Data
public class SecBiddingPlanSignDTO {

    private String bpId;
    //计划名称
    private String bpName;
    //年度
    private Integer bpYear;
    //月度
    private Integer bpMonth;
    //备注
    private String bpRemark;
    private String bpCreateUid;
    private String bpCreateUname;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bpCreateTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date stateTime;
    private Integer state;
    //招标人Id
    private String bpTenderee;
    //批复文件标题
    private String bpApprovalTitle;
    //批复编号
    private String bpApprovalNum;
    //批复时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bpApprovalTime;
    //批复文件
    private String bpApprovalFile;
    private String teId;

    //招标开始时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bpSecTime;
    //申请单位
    private String bpCid;
    //申请部门
    private String bpDid;
    //单位性质
    private String bpCnature;
    //部门性质
    private String bpDnature;
    //申请日期
    private Date bpTime;
    //招标内容
    private String bpSecContent;
    //估算价
    private BigDecimal bpGsAmt;
    //招标方式
    private String bpBiddingType;
    //招标完成时间
    private Date bpEndTime;
    //标段名称
    private String bpSecName;
    //是否工程招标
    private Boolean bpGczb;
    //项目规模
    private String bpProScale;
    //项目总投资（万元）
    private BigDecimal bpProAmt;
    //资金来源
    private String bpFundSource;
    //项目范围及工程概况
    private String bpScopeOverview;
    //说明
    private String bpSm;
    //概算金额
    private BigDecimal bpEsAmt;
//    //委托类型（JPMsoft_WEITUOLX）
//    private String bpEntrustmentType = "";


    public String signContent() {
        StringBuilder sb=new StringBuilder();
        sb.append(this.bpName).append(this.bpYear).append(this.bpMonth).append(this.bpRemark).append(bpCreateUid)
                .append(this.bpCreateUname).append(this.bpCid).append(this.bpDid).append(this.state).append(this.bpTenderee)
                .append(this.bpApprovalFile).append(this.bpApprovalNum).append(this.bpApprovalTitle).append(this.bpCnature)
                .append(this.bpDnature).append(this.bpSecContent).append(this.bpBiddingType).append(this.bpSecName).append(this.bpProScale)
                .append(this.bpFundSource).append(this.bpScopeOverview).append(this.bpSm);
        return sb.toString();
    }
}
