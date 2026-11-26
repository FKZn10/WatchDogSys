package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 图纸专业附件信息
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/25 14:11
 */
@Data
@TableName("Design_Construction_Dtl")
public class DesignConstructionDtl {

    @TableId(type = IdType.INPUT)
    private String id;
    private String dcId;
    //图纸专业字典（多选）
    private String dcdSpecialty;
    //附件
    private String dcdFile;

}
