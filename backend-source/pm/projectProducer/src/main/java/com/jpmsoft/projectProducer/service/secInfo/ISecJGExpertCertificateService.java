package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecJGExpertCertificateDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertCertificate;

import java.util.List;

public interface ISecJGExpertCertificateService extends IService<SecJGExpertCertificate> {


    void createOrUpdateSecExpertCertificate(List<SecJGExpertCertificateDto> secJGExpertCertificates,  String exId);

    void delSecExpertCertificateByExId(String exId);

}
