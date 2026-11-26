package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDccsCompanyDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDccsCompany;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:49
 */
public interface IConDccsCompanyService extends IService<ConDccsCompany> {

    void createOrUpdateBath(List<ConDccsCompanyDTO> datas, String dccsId);

    void delDccsCompanyByDccsId(String dccsId);
}
