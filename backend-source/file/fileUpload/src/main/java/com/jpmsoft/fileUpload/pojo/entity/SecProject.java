package com.jpmsoft.fileUpload.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 项目标段
 */
@Data
@TableName("Sec_Project")
public class SecProject {
    @TableId(type = IdType.INPUT)
    private String spId;
    private String secId;
    private String pId;
    private String secInfoCreateUid;
}
