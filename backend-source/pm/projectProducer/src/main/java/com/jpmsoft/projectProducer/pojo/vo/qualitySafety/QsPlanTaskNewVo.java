package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import lombok.Data;

/**
 * @author: 刘凯锋
 * @date: 2022/10/17
 */
@Data
public class QsPlanTaskNewVo {

    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 问题总数
     */
    private Integer ptProblemCount;
    /**
     * 已整改完成数量
     */
    private Integer ptFinishCount;
    /**
     * 未整改完成数量
     */
    private String ptNoFinishCount;
}
