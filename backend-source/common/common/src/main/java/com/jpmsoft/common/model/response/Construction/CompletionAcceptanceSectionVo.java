package com.jpmsoft.common.model.response.Construction;

import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceSectionDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 工程验收——标段信息记录表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:33
 */
@Data
public class CompletionAcceptanceSectionVo extends CompletionAcceptanceSectionDto implements Serializable {

    private static final long serialVersionUID = -793926319801493844L;
    private String secName;
}
