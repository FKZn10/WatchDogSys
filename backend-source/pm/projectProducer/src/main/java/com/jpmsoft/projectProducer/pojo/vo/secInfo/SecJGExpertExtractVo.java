package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertShieldDto;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import lombok.Data;

import java.util.List;

/**
 * 专家抽取 （建管）
 *
 */
@Data
public class SecJGExpertExtractVo extends SecJGExpertSelectVo {

    private static final long serialVersionUID = 6938747196943620103L;

    private String srId;
    private String esId;
    private Integer esState;
    private List<SecJGSelectionRecordDtlModel> secJGSelectionRecordDtlModels;
    private List<SecJGExpertShieldDto> secJGExpertShields;
}
