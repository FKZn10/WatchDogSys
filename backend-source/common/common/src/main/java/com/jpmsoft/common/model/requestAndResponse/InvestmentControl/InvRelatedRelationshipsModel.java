package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 三算关联信息
 */
@Data
public class InvRelatedRelationshipsModel extends BaseModeRequest {
    private String id;
    private String mainId;
    private String oldSubId;
    private String nowSubId;
}
