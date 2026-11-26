package com.jpmsoft.projectProducer.pojo.entity.secInfo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 标段项目关联表
 */
@Data
@TableName("Sec_Project")
public class SecProject {
    @TableId(type = IdType.INPUT)
    private String spId;
    //标段id
    private String secId;
    //项目id
    private String pId;
    //招采计划表id
    private String ppId;
    //创建人ID
    private String secInfoCreateUid;
}
