package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class SecExpertCheckDetailModel  extends BaseFileRequest {
    private String id;

    // 专家选取 id
    private String srId;

    // 专家考核评分 id
    private String ecId;

    // 专家id
    private String exId;

    // 扣分值
    private BigDecimal totalDeduction;

    //专家选取记录详情 id
    private String srdId;

    // 专家选取记录详情结果
    private Integer secdResult;

//    private Map<String,String> selection;


    List<SecExpertCheckDtlModel> rawResult;

}
