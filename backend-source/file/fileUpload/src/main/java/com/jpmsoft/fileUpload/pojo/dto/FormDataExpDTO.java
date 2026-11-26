package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通用模板导出
 *
 * @Author: 李文涛 aaron
 * @date: 2024/1/18 8:56
 */
@Data
public class FormDataExpDTO implements Serializable {

    private static final long serialVersionUID = 5741684300930776142L;
    /**
     * 主键筛选
     * 主键值集合
     */
    private List<String> pks;
    /**
     * 文档模板编码
     */
    @JsonProperty("tCodes")
    private List<String> tCodes;
    /**
     * 表编码
     */
    private List<String> tableCodes;
    /**
     * 仅导出模板
     */
    private boolean onlyTemp;

    /**
     * 项目ID  用于生成项目选择下拉列表
     * 主键值集合
     */
    private List<String> projectIds;

    /**
     * 合同ID 用于生成合同选择下拉列表
     * 主键值集合
     */
    private List<String> contractIds;

}
