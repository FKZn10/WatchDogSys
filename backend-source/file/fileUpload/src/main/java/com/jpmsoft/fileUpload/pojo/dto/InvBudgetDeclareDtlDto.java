package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 概算申报清单列表
 * @author: 李文涛 aaron
 * @date: 2022/7/18 17:07
 */
@Data
public class InvBudgetDeclareDtlDto implements Serializable {


    private static final long serialVersionUID = -4373056507837188246L;
    private String bddId;
    private String bddNumber;
    private String bddName;
    private String bddCode;
    private String bddUnit;
    private Float bddCount;
}
