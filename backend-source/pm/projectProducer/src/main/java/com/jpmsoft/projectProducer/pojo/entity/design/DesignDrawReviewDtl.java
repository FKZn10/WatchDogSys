package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 施工图审查详情
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 10:59
 */
@Data
@TableName("Design_Draw_Review_Dtl")
public class DesignDrawReviewDtl {

    @TableId(type = IdType.INPUT)
    private String id;
    private String drId;
    //审查单位
    private String drdReviewUnit;
    //审查意见附件
    private String drdReviewFile;
    //回复意见附件
    private String drdReplyFile;
    //经办人
    private String drdHandlers;
    //经办确认
    private String drdConfirm;
    //排序号
    private Integer drdSort;
}
