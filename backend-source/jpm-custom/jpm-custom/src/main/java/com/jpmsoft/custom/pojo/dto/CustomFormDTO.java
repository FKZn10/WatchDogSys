package com.jpmsoft.custom.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义表单 表单信息JSON对应实体
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/29 15:26
 */
@Data
public class CustomFormDTO implements Serializable {
    /**
     * 表ID
     */
    private String mainTable;
    /**
     * 表名 数据库字段
     */
    private String mainTableCode;
    /**
     * 主键编码
     */
    private String primaryKeyCode;
    /**
     * 创建人ID字段
     */
    private String createIdCode;
    /**
     * 创建名字段
     */
    private String createNameCode;
    /**
     * 创建时间字段
     */
    private String createTimeCode;
    /**
     * 审批字段
     */
    private String stateCode;
    /**
     * 审批时间字段
     */
    private String stateTimeCode;
    /**
     * 是否生成编码
     */
    private boolean generate;
    /**
     * 编码字段名
     */
    private String codeField;
    /**
     * 编码使用规则
     */
    private String codeRule;
    /**
     * 鉴权类型
     */
    private Integer authentication;
    /**
     * 表关联关系
     */
    private List<LinkedRelationDTO> dataTables;
    /**
     * 文件字段
     */
    private List<FileCodeDTO> fileCodes;
    /**
     * 查询字段以及字段过滤条件
     */
    private List<FieldAndFilterDTO> minTableSet;
    /**
     * 下拉选择查询格式转换
     */
    private List<FieldFormatConvertDTO> multipleQuery;
    /**
     * 子表配置信息
     */
    private List<CustomFormDTO> childTables;
    /**
     * 子表信息：入参字段名
     */
    private String requestField;

    /**
     * 子表信息：外键字段 数据库字段
     */
    private String foreignKey;
    /**
     * 外键值 由java代码层传入
     */
    private String foreignValue;
    /**
     * 表单结构模式
     * 0:单表
     * 1:一对多
     */
    private String structureModel;
    /**
     * 编码生成
     * 校验忽略编码的入参key
     */
    private String ignoreCode;
    /**
     * 编码生成
     * 忽略编码生成的key对应值范围
     */
    private List<String> ignoreCodeValues;
    /**
     * 不鉴权的用户ID集合
     */
    private List<String> noAuthUsers;
    /**
     * 不鉴权的部门ID集合
     */
    private List<String> noAuthDeps;
    /**
     * 通用新增修改接口主逻辑执行完成后调用接口
     */
    private CreateServiceCallApiDTO createServiceCallApi;
}
