package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 专家执业证书（建管）
 */
@Data
@TableName("Sec_JG_Expert_Certificate")
public class SecJGExpertCertificate {

    @TableId(type = IdType.INPUT)
    private String id;
    //专家ID(监管)
    private String exId;
    //执业证书名称
    private String ecName;
    //执业等级
    private String ecLevel;
    //执业证书专业
    private String ecMajor;
    //发证部门
    private String ecDepartment;
    //发证时间
    private Date ecTime;
    //执业资格
    private String ecPracticing;
    //执业证书附件
    private String ecDoc;
    //序号
    private Integer ecSort;
    private String createUid;
    private String createUname;
    private Date createTime;
}
