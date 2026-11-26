package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目管理大纲体系节点数据源配置
 *
 * @author tianhelin
 * @date 2025/6/16
 */
@Data
@TableName("Outline_Projec_Datasource")
public class OutlineProjecDatasource {
    @TableId(type = IdType.INPUT)
    private String id;
    private String oplId;
    private String odId;
    private String odName;
    private String odType;
    private String odSource;
    private Boolean odStatus;
    private String odRemark;
    private String odAddress;
    private String odMethod;
    private String odBody;
    private String odDataType;
    private Date createTime;
    private String createUid;
    private String createUname;
}