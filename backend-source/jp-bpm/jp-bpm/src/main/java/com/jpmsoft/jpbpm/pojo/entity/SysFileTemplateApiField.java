package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 打印接口返回值（字段清单）
 */
@Data
@TableName("SYS_FileTemplate_Api_Field")
public class SysFileTemplateApiField {

    @TableId(type = IdType.INPUT)
    private String fieldId;
    private String tpId;
    private String fieldName;
    private String fieldCode;
    private String fieldType;
    private String fieldDiccode;
    private String fieldUnit;
    private String fieldCreateUid;
    private String fieldCreateUname;
    private Date fieldCreateTime;
    private Integer fieldState;
    private String teId;
}
