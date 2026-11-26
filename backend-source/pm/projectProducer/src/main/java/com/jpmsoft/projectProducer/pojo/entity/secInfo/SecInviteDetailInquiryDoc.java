package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 招标工作方案及询价文件-邀请名单
 *
 */
@Data
@TableName("Sec_Invite_Detail_Inquiry_Doc")
public class SecInviteDetailInquiryDoc {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String wsidId;
    //单位ID
    @JsonProperty("cId")
    private String cId;
    //单位资质
    private String sirAptitude;
    //备注
    private String sirRemark;
    //序号
    private Integer sirSort;
}
