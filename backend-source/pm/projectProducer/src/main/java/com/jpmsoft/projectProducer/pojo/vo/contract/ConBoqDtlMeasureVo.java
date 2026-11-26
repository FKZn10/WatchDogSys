package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/5 13:42
 */
@Data
public class ConBoqDtlMeasureVo extends ConMeasureRecordVo {

    /**
     * 主表ID
     */
    private String boqId;
    /**
     * 项目编码
     */
    private String boqdCode;
    /**
     * 项目名称
     */
    private String boqdName;
    /**
     * 工程内容
     */
    private String boqdContent;
    /**
     * 计量单位
     */
    private String boqdUnit;
    /**
     * 工程量
     */
    private Float boqdAmount;
    /**
     * 备注
     */
    private String boqdRemark;
    /**
     * 状态
     */
    private Integer boqdState;
    /**
     * 排序号
     */
    private Integer boqdSort;
    /**
     * 所属项目概算科目表ID
     */
    private String boqdBddId;
    private String bddName;

    /**
     * 包含的子项为分部分项清单类型的数量
     */
    private Integer childDtlNum;
    /**
     * 包含的子项为分部分项清单类型的已填报数量
     */
    private Integer fillingNum;
    /**
     * 包含的子项为分部分项清单类型的审核通过数量
     */
    private Integer approvedNum;
    /**
     * 计量单下计量单审核记录数量
     */
    private Integer recordCheckNum;
    private List<ConBoqDtlMeasureVo> childNodes;
}
