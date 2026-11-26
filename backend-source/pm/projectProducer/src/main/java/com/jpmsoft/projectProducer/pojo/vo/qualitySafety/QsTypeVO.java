package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import lombok.Data;

import java.util.List;

@Data
/**
 * QsTypeVO类用于表示质量问题类型的视图对象。
 * 它包含了与质量问题类型相关的信息，可以被用在前端展示或后端数据处理中。
 */
public class QsTypeVO {
    private String qstId; // 质量问题类型ID

    private int qstLevel; // 质量问题类型级别

    /**
     * 类别名称
     */
    private String qstName; // 质量问题类型名称

    /**
     * 类别编码
     */
    private String qstCode; // 质量问题类型编码

    /**
     * 父级id，用于表示当前质量问题类型所属的上一级类型
     */
    private String qstParentId; // 父级类型ID


}
