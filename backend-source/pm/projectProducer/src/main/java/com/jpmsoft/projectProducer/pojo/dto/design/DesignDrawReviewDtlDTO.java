package com.jpmsoft.projectProducer.pojo.dto.design;

import lombok.Data;

import java.io.Serializable;

/**
 * 施工图审查详情
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 10:59
 */
@Data
public class DesignDrawReviewDtlDTO implements Serializable {

    private static final long serialVersionUID = -7963172200617412422L;
    private String id;
    private String drId;
    private String drdReviewUnit;
    private String drdReviewFile;
    private String drdReplyFile;
    private String drdHandlers;
    private String drdConfirm;
    private Integer drdSort;

    private String drdReviewFileIds;
    private String drdReplyFileIds;
    private String drdHandlersName;
}
