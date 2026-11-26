package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("Sys_TableColumn")
public class SysTableColumn {
    @TableId(type = IdType.INPUT)
    public String columnId;
    public String tableId;
    public String columnName;
    public String columnCode;
    public String columnType;
    public String columnRemark;
    public String columnNull;
    public String columnPk;
    public Integer columnLength;
    public String columnCsharptype;
    public String columnCsharpcode;
    public String columnFormvisible;
    public String columnFormtype;
    public Date columnTime;
    public Integer columnNumber;
    public String columnJavaSmallHump;
    private String tableCode;
    /**
     * 是否是附件
     */
    private Boolean columnFile;
    /**
     * 1 普通字段
     * 2 链表字段
     * 3 编码字段
     */
    private Integer filterType;
    /**
     * 关联的表编码或者字典编码
     */
    private String assocTableCode;
    /**
     * 关联为表编码时 其导入导出对应的字段编码
     */
    private String assocColumnCode;
    /**
     * 关联为表编码时其关联表的主键ID编码
     */
    private String assocColumnPk;
    /**
     *关联为表编码时 额外导出字段编码
     */
    private String assocShowCode;
    /**
     * 关联为表编码时 额外导出字段名称
     */
    private String assocShowName;
    /**
     * 导入时 是否唯一校验
     */
    private Boolean columnOnly;
    /**
     *  导出时 是否导出列
     */
    private Boolean columnExp;
    /**
     * 导入时 是否导入列
     */
    private Boolean columnImp;
}
