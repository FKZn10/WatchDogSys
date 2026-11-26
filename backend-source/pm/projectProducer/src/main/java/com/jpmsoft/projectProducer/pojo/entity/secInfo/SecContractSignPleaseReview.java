package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同签订请审
 */
@Data
@TableName("Sec_Contract_Sign_Please_Review")
public class SecContractSignPleaseReview implements Serializable {
    private static final long serialVersionUID = -72267436553199601L;

    @TableId(type = IdType.INPUT)
    private String csprId;
    
    private String secId;
    /**
     * 报审事项
     */
    private String csprReviewMatter;
    /**
     * 报审单位
     */
    private String csprApprovalUnit;
    /**
     * 中标单位
     */
    private String csprWinningBidder;
    /**
     * 报审内容
     */
    private String csprApprovalContent;
    /**
     * 签订合同价
     */
    private Double csprSignContractPrice;
    
    private String createUid;
    
    private String createUname;
    
    private Date createTime;
    
    private String createDid;
    
    private String updateUid;
    
    private Date updateTime;
    
    private Integer state;
    
    private Date stateTime;
    
    private String remark;



}

