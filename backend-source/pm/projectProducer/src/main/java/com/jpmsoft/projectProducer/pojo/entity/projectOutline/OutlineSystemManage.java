package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 体系模板管理
 *
 * @author tianhelin
 * @date 2025/6/10
 */
@Data
@TableName("Outline_System_Manage")
public class OutlineSystemManage {
    @TableId(type = IdType.INPUT)
    private String id;
    private String osmName;
    private String osmCode;
    private String osmStatus;
    private String osmRemark;
    private String osmProjectType;
    private String osmDepartmentId;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Integer state;
    private Date stateTime;
    private Date updateTime;
    private String updateUid;
    private String updateUname;
    private String sortRules;
}