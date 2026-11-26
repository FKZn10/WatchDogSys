package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 专家考核申诉内容
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:53
 */
@Data
@TableName("Sec_Expert_Appeal_Dtl")
public class SecExpertAppealDtl {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String seaId;
    //申诉内容
    private String seadContent;
    //扣分值
    private BigDecimal seadScore;
    //申诉说明
    private String seadReason;
    //序号
    private Integer seadSort;
    //是否同意申诉申请
    private Boolean seadIsAgree;
    //专家考核内容ID
    private String secdId;
}
