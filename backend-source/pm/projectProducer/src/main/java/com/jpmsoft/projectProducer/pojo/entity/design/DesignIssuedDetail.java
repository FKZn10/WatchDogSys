package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("design_Issued_detail")
public class DesignIssuedDetail {

    @TableId
    private String id; // 主键

    private String didUnitId; //单位id

    private String didUnitName; // 单位名称

    private String didUid;  // 用户id

    private String didUname; //用户名称

    private Integer didPaperNum;  // 纸质价数


    private Integer didElectron;  //电子版

    private Integer didNumber;   // 序号

    // 01 - 参见单位   02  -  其他单位
    private String didType;

    private String diId; // 外键，关联design_Issued表
}
