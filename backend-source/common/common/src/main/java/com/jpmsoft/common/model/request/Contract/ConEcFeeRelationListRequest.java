package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 变更费用申报关联来源列表查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConEcFeeRelationListRequest extends BasePageEntity {

    /**
     * 变更类型：01-多合同设计变更，02-现场签证。
     */
    private String type;

    /**
     * 项目ID。
     */
    private String projectId;

    /**
     * 合同ID。
     */
    private String conId;
}
