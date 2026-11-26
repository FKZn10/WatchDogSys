package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 13:47
 */
@Data
@TableName("OA_Knowledge_Group")
public class OaKnowledgeGroup {

    @TableId(type = IdType.INPUT)
    private String id;
    private String kbId;
    private String kbgName;
    private String kbgType;
    private String kbgGroupId;
    private String kbgSecrecyLevel;
    private String kbgRole;
    private String kbgRoleType;
    private String kbgPower;
    private Date createTime;
    private String teId;
}
