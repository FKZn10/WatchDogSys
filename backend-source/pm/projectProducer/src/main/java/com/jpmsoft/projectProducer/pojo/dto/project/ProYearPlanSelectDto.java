package com.jpmsoft.projectProducer.pojo.dto.project;

import lombok.Data;

import java.io.Serializable;

/**
 * 年度申报计划选择清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:10
 */
@Data
public class ProYearPlanSelectDto implements Serializable {
    private String id;
    private String cplId;
    private String ycpId;

}
