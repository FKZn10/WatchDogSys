package com.jpmsoft.custom.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 链表关系
 *
 * @Author: 李文涛 aaron
 * @date: 2023/6/1 13:41
 */
@Data
public class LinkedRelationDTO implements Serializable {

    private static final long serialVersionUID = -2719710744232555551L;

    private String id;
    private String tableCode;
    private String tableAlias;
    private String tableField;
    private String associationType;
    private String associationTable;
    private String associationTableId;
    private String associationField;
    private Integer num;
}
