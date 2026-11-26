package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 控制价请审
 */
@Data
@TableName("Sec_Control_Price_Please_Review")
public class SecControlPricePleaseReview implements Serializable {
    private static final long serialVersionUID = -69056458592805447L;

    @TableId(type = IdType.INPUT)
    private String cpprId;
    
    private String secId;
    /**
     * 报审事项
     */
    private String cpprReviewMatter;
    /**
     * 报审单位
     */
    private String cpprApprovalUnit;
    /**
     * 报审内容
     */
    private String cpprApprovalContent;
    
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

