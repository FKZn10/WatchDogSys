package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecExpertCertificateDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCertificate;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/27 10:12
 */
public interface ISecExpertCertificateService extends IService<SecExpertCertificate> {

    void createOrUpdateSecExpertCertificate(List<SecExpertCertificateDto> datas, String exId);

    void delSecExpertCertificateByExId(String exId);
}
