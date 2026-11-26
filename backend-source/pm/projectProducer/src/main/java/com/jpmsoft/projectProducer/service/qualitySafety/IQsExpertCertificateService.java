package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Qs.QsExpertCertificateDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsExpertCertificate;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/27 10:12
 */
public interface IQsExpertCertificateService extends IService<QsExpertCertificate> {

    void createOrUpdateQsExpertCertificate(List<QsExpertCertificateDto> datas, String exId);

    void delQsExpertCertificateByExId(String exId);
}
