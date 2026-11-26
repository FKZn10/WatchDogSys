package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertShieldDto;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import lombok.Data;

import java.util.List;

/**
 * 专家抽取
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/10 14:23
 */
@Data
public class SecExpertExtractVo extends SecExpertSelectVo {

    private static final long serialVersionUID = 6938747196943620103L;

    private String srId;
    private String esId;
    private Integer esState;
    private List<SecSelectionRecordDtlModel> secSelectionRecordDtlModels;
    private List<SecExpertShieldDto> secExpertShields;
}
