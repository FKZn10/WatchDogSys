package com.jpmsoft.common.model.request.Construction;

import lombok.Data;

import java.io.Serializable;

/**
 * 工程验收——标段信息记录表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:33
 */
@Data
public class CompletionAcceptanceSectionDto implements Serializable {

    private static final long serialVersionUID = -7220972802489660861L;

    private String casId;
    private String caId;
    private String secId;
}
