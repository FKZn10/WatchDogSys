package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * 编码规则配置详情
 */
public class SysSequenceSetDtlDto extends BaseModeRequest  implements Serializable {

    private static final long serialVersionUID = -2792411558313837911L;
    private String sdId;
    private String sId;
    private Integer sdRuleOrder;
    private String sdRuleType;
    private String sdRuleValue;
    private String sdPaddingSide;
    private Integer sdPaddingWidth;
    private Character sdPaddingChar;
    private String sdCreateUid;
    private String sdCreateUname;
    private Date sdCreateTime;
    private String sdUpdateUid;
    private String sdUpdateUname;
    private Date sdUpdateTime;
    // 常量类型时：常量是否拼接
    // 链表类型时: 需要获取的表字段、当前表单的指定字段值
    private String sdTableField;
    private String sdFieldValue;
    // 链表类型时: 表的主键名
    private String sdTableKey;

    public String getSdId() {
        return sdId;
    }

    public void setSdId(String sdId) {
        this.sdId = sdId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public Integer getSdRuleOrder() {
        return sdRuleOrder;
    }

    public void setSdRuleOrder(Integer sdRuleOrder) {
        this.sdRuleOrder = sdRuleOrder;
    }

    public String getSdRuleType() {
        return sdRuleType;
    }

    public void setSdRuleType(String sdRuleType) {
        this.sdRuleType = sdRuleType;
    }

    public String getSdRuleValue() {
        return sdRuleValue;
    }

    public void setSdRuleValue(String sdRuleValue) {
        this.sdRuleValue = sdRuleValue;
    }

    public String getSdPaddingSide() {
        return sdPaddingSide;
    }

    public void setSdPaddingSide(String sdPaddingSide) {
        this.sdPaddingSide = sdPaddingSide;
    }

    public Integer getSdPaddingWidth() {
        return sdPaddingWidth;
    }

    public void setSdPaddingWidth(Integer sdPaddingWidth) {
        this.sdPaddingWidth = sdPaddingWidth;
    }

    public Character getSdPaddingChar() {
        return sdPaddingChar;
    }

    public void setSdPaddingChar(Character sdPaddingChar) {
        this.sdPaddingChar = sdPaddingChar;
    }

    public String getSdCreateUid() {
        return sdCreateUid;
    }

    public void setSdCreateUid(String sdCreateUid) {
        this.sdCreateUid = sdCreateUid;
    }

    public String getSdCreateUname() {
        return sdCreateUname;
    }

    public void setSdCreateUname(String sdCreateUname) {
        this.sdCreateUname = sdCreateUname;
    }

    public Date getSdCreateTime() {
        return sdCreateTime;
    }

    public void setSdCreateTime(Date sdCreateTime) {
        this.sdCreateTime = sdCreateTime;
    }

    public String getSdUpdateUid() {
        return sdUpdateUid;
    }

    public void setSdUpdateUid(String sdUpdateUid) {
        this.sdUpdateUid = sdUpdateUid;
    }

    public String getSdUpdateUname() {
        return sdUpdateUname;
    }

    public void setSdUpdateUname(String sdUpdateUname) {
        this.sdUpdateUname = sdUpdateUname;
    }

    public Date getSdUpdateTime() {
        return sdUpdateTime;
    }

    public void setSdUpdateTime(Date sdUpdateTime) {
        this.sdUpdateTime = sdUpdateTime;
    }

    public String getSdTableField() {
        return sdTableField;
    }

    public void setSdTableField(String sdTableField) {
        this.sdTableField = sdTableField;
    }

    public String getSdFieldValue() {
        return sdFieldValue;
    }

    public void setSdFieldValue(String sdFieldValue) {
        this.sdFieldValue = sdFieldValue;
    }

    public String getSdTableKey() {
        return sdTableKey;
    }

    public void setSdTableKey(String sdTableKey) {
        this.sdTableKey = sdTableKey;
    }
}
