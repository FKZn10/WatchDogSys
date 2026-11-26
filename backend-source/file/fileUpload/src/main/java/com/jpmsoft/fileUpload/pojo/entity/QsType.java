package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 质量安全类别
 */
@Data
@TableName("Qs_Type")
public class QsType {
    @TableId(type = IdType.INPUT)
    private String qstId;

    /**
     * 类别名称
     */
    private String qstName;

    /**
     * 类别编码
     */
    private String qstCode;

    /**
     * 父级id
     */
    private String qstParentId;

    /**
     * 类别归类（备用字段）
     */
    private String qstCategory;

    /**
     * 安全检查评价项目：JPMsoft_AQJCPJXM
     * 01：一般项目
     * 02：保证项目
     */
    private String qstItem;

    /**
     * 排序号
     */
    private Integer qstSort;

    /**
     * 备注
     */
    private String qstRemark;

    /**
     * 状态:0-禁用;1-可用
     */
    private Integer qstState;
    private Date qstCreateTime;
    private String qstCreateUid;
    private String qstCreateUname;
}
