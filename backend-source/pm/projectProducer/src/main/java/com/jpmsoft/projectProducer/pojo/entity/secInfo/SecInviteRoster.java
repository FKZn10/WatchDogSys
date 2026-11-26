package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 招标文件-邀请名单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/23 10:29
 */
@Data
@TableName("Sec_Invite_Roster")
public class SecInviteRoster {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("cId")
    private String cId;
    private String sirAptitude;
    private String sirRemark;
    private Integer sirSort;
}
