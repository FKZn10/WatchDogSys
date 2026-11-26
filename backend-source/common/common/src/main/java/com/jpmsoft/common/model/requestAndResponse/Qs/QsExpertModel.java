package com.jpmsoft.common.model.requestAndResponse.Qs;


import com.jpmsoft.common.model.request.Qs.QsExpertCertificateDto;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import lombok.Data;

import java.util.List;

@Data
public class QsExpertModel extends SecExpertModel {
    //质量安全专项检查: 开启 1/关闭0
    private Integer qsSpecialCheck;
    private List<QsExpertCertificateDto> qsExpertCertificates;
}
