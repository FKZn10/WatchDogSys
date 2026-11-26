package com.jpmsoft.jpbpm.pojo.vo;

import java.util.Date;

/**
 * 打印接口返回值（字段清单）
 */
public class SysFileTemplateApiFieldVo {

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

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldDiccode() {
        return fieldDiccode;
    }

    public void setFieldDiccode(String fieldDiccode) {
        this.fieldDiccode = fieldDiccode;
    }

    public String getFieldUnit() {
        return fieldUnit;
    }

    public void setFieldUnit(String fieldUnit) {
        this.fieldUnit = fieldUnit;
    }

    public String getFieldCreateUid() {
        return fieldCreateUid;
    }

    public void setFieldCreateUid(String fieldCreateUid) {
        this.fieldCreateUid = fieldCreateUid;
    }

    public String getFieldCreateUname() {
        return fieldCreateUname;
    }

    public void setFieldCreateUname(String fieldCreateUname) {
        this.fieldCreateUname = fieldCreateUname;
    }

    public Date getFieldCreateTime() {
        return fieldCreateTime;
    }

    public void setFieldCreateTime(Date fieldCreateTime) {
        this.fieldCreateTime = fieldCreateTime;
    }

    public Integer getFieldState() {
        return fieldState;
    }

    public void setFieldState(Integer fieldState) {
        this.fieldState = fieldState;
    }
}
