package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 项目变更-项目建筑面积详情
 */
@Data
@TableName("Pc_Construction_Area_Dtl")
public class PcConstructionAreaDtl {

    @TableId(type = IdType.INPUT)
    private String catId;
    private String pcId;
    private String catName;
    private Float catArea;
    private String catRemark;
    private Integer catSort;
    private String catParentId;
}
