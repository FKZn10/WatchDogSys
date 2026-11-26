package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPriceResultsfCompanyModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPriceResultsfCompany;

import java.util.List;

public interface ISecPriceResultsfCompanyService extends IService<SecPriceResultsfCompany> {


    /**
     * 询价结果ID查看是否有询价单位
     *
     * @param qegId
     * @return
     */
    Boolean havingPriceResultsfCompanyBySprId(String sprId);

    void createOrUpdatePriceResultsfCompany(List<SecPriceResultsfCompanyModel> datas, String sprId);


    List<SecPriceResultsfCompanyModel> getPriceResultsfCompanyBySprId(String sprId);
}
