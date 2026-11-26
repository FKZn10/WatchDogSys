package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.ProFundingSourceModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProFundingSource;

import java.util.List;

public interface IProFundingSourceService extends IService<ProFundingSource> {

    void createOrUpdateProFundingSource(List<ProFundingSourceModel> datas, String paId);

    void deleteProFundingSourceByPaId(String paId);

    List<ProFundingSource> getProFundingSourceByPaId(String paId);
}
