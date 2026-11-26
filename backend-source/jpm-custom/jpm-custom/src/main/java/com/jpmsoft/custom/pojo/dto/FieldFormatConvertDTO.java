package com.jpmsoft.custom.pojo.dto;

import com.jpmsoft.common.exception.JPMException;
import lombok.Data;

import java.util.List;

/**
 * 字段格式转换
 * 列表下拉查询
 */
@Data
public class FieldFormatConvertDTO {
    /**
     * 列中文名
     */
    private String columnName;
    /**
     * 查询列名
     */
    private String columnCode;
    /**
     * 格式
     * 时间格式 YYYY-MM-DD
     * 字典格式 字典key
     */
    private String dynamicKey;
    /**
     * number  普通数值
     * date     时间
     * select   下拉选择
     * dictionary 字典
     * text        文本
     */
    private String inputType;

    /**
     * 低代码下拉选择或者radio或者checkbox
     * inputType为select 时options有值
     */
    private List<FieldFormatOptionsDTO> options;

    /**
     * 时间格式化格式
     * inputType为select 时dateFormat有值
     */
    private String format = "YYYY-MM-DD";
    /**
     * 时间查询方式
     * 1 范围查询
     * 2 时间单选
     * 3 数据下拉选择
     */
    private Integer dateSearchType;

    /**
     * 数据库筛选时时间格式转换
     *
     * @return
     */
    public String formatConvert() {
        if (this.format.equalsIgnoreCase("YYYY-MM-DD HH:MM:SS")) return "%Y-%m-%d %H:%i:%s";
        if (this.format.equalsIgnoreCase("YYYY-MM-DD HH:MM")) return "%Y-%m-%d %H:%i";
        if (this.format.equalsIgnoreCase("YYYY-MM-DD HH")) return "%Y-%m-%d %H";
        if (this.format.equalsIgnoreCase("YYYY-MM-DD")) return "%Y-%m-%d";
        if (this.format.equalsIgnoreCase("YYYY-MM")) return "%Y-%m";
        if (this.format.equalsIgnoreCase("YYYY")) return "%Y";
        if (this.format.equalsIgnoreCase("MM")) return "%m";
        throw new JPMException("时间格式错误[" + this.format + "]");
    }

    /**
     * 查询返回结果集时时间类型转换
     *
     * @return
     */
    public String javaConvert() {
        if (this.format.equalsIgnoreCase("YYYY-MM-DD HH:MM:SS")) return "yyyy-MM-dd HH:mm:ss";
        if (this.format.equalsIgnoreCase("YYYY-MM-DD HH:MM")) return "yyyy-MM-dd HH:mm";
        if (this.format.equalsIgnoreCase("YYYY-MM-DD HH")) return "yyyy-MM-dd HH";
        if (this.format.equalsIgnoreCase("YYYY-MM-DD")) return "yyyy-MM-dd";
        if (this.format.equalsIgnoreCase("YYYY-MM")) return "yyyy-MM";
        if (this.format.equalsIgnoreCase("YYYY")) return "yyyy";
        if (this.format.equalsIgnoreCase("MM")) return "mm";
        throw new JPMException("时间格式错误[" + this.format + "]");
    }


}
