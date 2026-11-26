package com.jpmsoft.projectProducer.pojo.vo.oa;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;

/**
 * 年度计划变更清单
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:01
 */
@Data
public class OaAnnualPlanChangeDtlVo extends ProjectCommonRes implements Serializable {


    private static final long serialVersionUID = -3015390671874684321L;
    private String id;
    private String apcId;
    /**
     *
     * 1新增
     * 2删除
     */
    private String apdType;
    private Integer apdSort;
    private String apdCheckType;
}
