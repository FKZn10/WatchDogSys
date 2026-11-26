package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 开标结果
 */
@Data
@TableName("Sec_Open_Result")
public class SecOpenResult {

    @TableId(type = IdType.INPUT)
    private String orId;
    //标段id
    private String secId;
    //是否为应急工程施工总承包类招标
    private Integer orEmergency;
    //截标日期
    private Date orEndDate;
    //开标日期
    private Date orOpenDate;
    //备注
    private String orRemark;
    //采购服务机构
    private String orProcurementAgency;
    //采购服务机构名称
    private String orProcurementAgencyName;
    //开标地点
    private String orPlaceBidOpening;
    //开标记录文件
    private String orOpeningDocuments;
    //创建人ID
    private String orCreateUid;
    //创建人姓名
    private String orCreateUname;
    //创建时间
    private Date orCreateTime;
    //审批状态
    private Integer orState;
    //审批时间
    private Date orStateTime;
    //公告平台
    private String orBulletinPlatform;
    //第一候选人
    private String orCandidateOne;
    //第二候选人
    private String orCandidateTwo;
    //第三候选人
    private String orCandidateThree;
    //报名人数
    private Integer orApplyNum;
    //有效报价人数
    private Integer orValidQuote;
    //第一候选人报价
    private BigDecimal orQuoteOne;
    //第二候选人报价
    private BigDecimal orQuoteTwo;
    //第三候选人报价
    private BigDecimal orQuoteThree;
    //公告开始时间
    private Date orBulletinStart;
    //公告结束时间
    private Date orBulletinEnd;
    //公告开始时间（采购结果）
    private Date orBulletinResStart;
    //公告结束时间（采购结果）
    private Date orBulletinResEnd;

    //评标方法
    private String orBidMethod;
    //开评标开始时间
    private Date orBidStart;
    //开评标结束时间
    private Date orBidEnd;
    //第一候选人项目经理
    private String orPmOne;
    //第二候选人项目经理
    private String orPmTwo;
    //第三候选人项目经理
    private String orPmThree;
    //投标人数量
    private Integer orBidNum;
    //是否联合体投标 1、是 0、否
    private Integer orCoalitionBid;
    private String teId;
}
