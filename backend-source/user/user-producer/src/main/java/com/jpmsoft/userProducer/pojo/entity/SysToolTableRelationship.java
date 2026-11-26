package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Sys_Tool_Table_Relationship")
public class SysToolTableRelationship {
    @TableId(type = IdType.INPUT)
    private String id;
    private String sttrMainTable;
    private String sttrMainDescribe;
    private String sttrMainKey;
    private String sttrAssociationTable;
    private String sttrAssociationKey;
    private String sttrAssociationDescribe;
    private String sttrOwner;
}
