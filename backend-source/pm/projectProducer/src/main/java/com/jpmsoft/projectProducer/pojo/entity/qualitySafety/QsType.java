package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
     * 全路径
     */
    private String qstPath;
    /**
     * 是否影响功能和安全使用的重大质量问题  0：否  1：是
     */
    private Integer qstIsImportant;

    /**
     * 严重程度：
     * 1： 空 或者 /
     * 2：严重     等同于重大问题，qstIsImportant=1
     * 3：较严重
     * 4：一般
     * 5：轻微
     */
    private Integer severityLevel;

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
    private String teId;
    //处罚
    private String qstPunish;
    //分值
    private String qstScore;
    //单位
    private String qstUnit;

    /**
     * 处置要求
     */
    private String qstDisposal;

    @TableField(exist = false)
    private List<QsType> children;
}
