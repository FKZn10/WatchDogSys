package com.jpmsoft.projectProducer.pojo.vo.oa;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;

/**
 * 年度计划清单
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:01
 */
@Data
public class OaAnnualPlanDtlVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -8547751126235452659L;
    private String id;
    private String apId;
    private String apdType;
    private Integer apdSort;
    private String apdCheckType;
}
