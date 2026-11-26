package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 大纲延迟数据源实体类
 *
 * @author tianhelin
 * @date 2025/8/11
 */
@Data
@TableName("Outline_Delay_Datasource")
public class OutlineDelayDatasource {
    @TableId(type = IdType.INPUT)
    private String id;
    private String oplId;
    private String odId;
    private String tCode;
    private String pId;
    private String sfId;
}