package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertShieldDto;
import lombok.Data;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:52
 */
@Data
public class SecExpertSupplementVO extends SecExpertSelectVo {

    private String sevId;
    private String srId;
    private String esId;
    private String sesRemark;

    private List<SecExpertShieldDto> secExpertShields;
    private List<SecSupplementRecordDtlVO> secSupplementRecordDtls;
}
