package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecFundingSourceModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecFundingSource;

import java.util.List;

public interface ISecFundingSourceService extends IService<SecFundingSource> {

    void createOrUpdateSecFundingSource(List<SecFundingSourceModel> datas, String foreignId);

    void deleteSecFundingSourceByForeignId(String foreignId);
}
