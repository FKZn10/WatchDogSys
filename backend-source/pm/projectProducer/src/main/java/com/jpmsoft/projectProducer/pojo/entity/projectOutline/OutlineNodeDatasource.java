package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 体系模板节点数据源
 *
 * @author tianhelin
 * @date 2025/6/16
 */
@Data
@TableName("Outline_Node_Datasource")
public class OutlineNodeDatasource {
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 数据源配置ID
     */
    private String odId;
    /**
     * 体系模板-目录节点ID
     */
    private String oslId;
    private Date  createTime;
    private String createUid;
    private String createUname;
}