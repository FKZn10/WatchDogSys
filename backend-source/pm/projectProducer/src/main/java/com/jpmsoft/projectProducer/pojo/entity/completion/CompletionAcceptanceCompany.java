package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 参建单位
 *
 * @author: 李文涛 aaron
 * @date: 2022/07/10 19:37
 * @TableName Completion_Acceptance_Company
 */
@TableName(value = "Completion_Acceptance_Company")
@Data
public class CompletionAcceptanceCompany implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     *
     */
    @TableField(value = "Ca_Id")
    private String caId;
    /**
     *
     */
    @TableField(value = "C_Id")
    private String cId;
    /**
     *
     */
    @TableField(value = "C_Type")
    private String cType;
    /**
     *
     */
    @TableField(value = "Cac_Person_Liable")
    private String cacPersonLiable;
    /**
     *
     */
    @TableField(value = "Cac_Telephone")
    private String cacTelephone;
}