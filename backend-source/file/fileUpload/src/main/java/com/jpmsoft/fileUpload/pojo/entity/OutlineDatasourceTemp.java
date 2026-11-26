package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 业务功能数据源关联
 *
 * @author tianhelin
 * @date 2025/6/26
 */
@Data
@TableName("Outline_Datasource_Temp")
public class OutlineDatasourceTemp {
    @TableId(type = IdType.INPUT)
    private String id;
    private String odId;
    private String tCode;
    private String tName;
}