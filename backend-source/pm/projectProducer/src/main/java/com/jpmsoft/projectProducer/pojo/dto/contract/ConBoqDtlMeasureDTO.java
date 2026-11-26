package com.jpmsoft.projectProducer.pojo.dto.contract;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/5 11:16
 */
@Data
public class ConBoqDtlMeasureDTO implements Serializable {

    private static final long serialVersionUID = -4911486534855963102L;

    /**
     * 1 查询排除分布分项清单类型的清单
     * 0 仅查询分布分项清单类型的清单
     * 缺省其他 查询所有
     */
    private Integer filterMeasureType;
    /**
     * 是否关联查询返回计量单信息
     * true 时meaId必填
     */
    private boolean joinMeasure;
    private String conId;
    private String meaId;
    private String boqdParentId;
    /**
     * 排除未报审的工程量清单
     */
    private boolean excludeUnApproval;
    /**
     * 过滤申报超量
     */
    private boolean filterOut;
}
