package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标公告
 */
@Data
@TableName("Sec_Notice")
public class SecNotice {

    @TableId(type = IdType.INPUT)
    private String nId;
    //标段id
    private String secId;
    //招标代理单位ID
    private String nBiddingAgency;
    //招标金额
    private BigDecimal nAmount;
    //招标范围
    private String nScope;
    //公告计划发布时间
    private Date nPlandate;
    //计划截标时间
    private Date nPlanenddate;
    //是否发出公告
    private Integer nRelease;
    //联系人
    private String nContact;
    //联系电话
    private String nContacttel;
    private String nCreateUid;
    private String nCreateUname;
    private Date nCreateTime;
    private Integer nState;
    private Date nStateTime;
    //公告实际发布时间
    private Date nDate;
    //实际截标时间
    private Date nEnddate;
    //招标方式
    private String nBiddingType;
    //资格审查方式
    private String nQeType;
    private String nRemark;
    //费率
    private BigDecimal nRate;
    //其他文件
    private String nOtherValuation;
    //公告答疑时间
    private Date nOfficeHours;
    //公告质疑时间
    private Date nQuestiTime;
    //公告计划发布结束时间
    private Date nPlanDateEnd;
    //公告质疑结束时间
    private Date nQuestiTimeEnd;
    //公告答疑结束时间
    private Date nOfficeHoursEnd;
    //投资人资质要求
    private String nInvestorsQuali;
    //项目负责人资格要求
    private String nBidderManagerCon;
    //呈批事项
    private String nApprovalMatter;
    private String teId;
    //公告地址
    private String nUrl;
    private String nReceiveJointBidd;
    private String nDeterminType;
    private String nFunction;
}
