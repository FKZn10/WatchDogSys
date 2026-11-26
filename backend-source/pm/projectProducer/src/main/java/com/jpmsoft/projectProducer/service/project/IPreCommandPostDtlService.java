package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreCommandPostDtl;

import java.util.List;

public interface IPreCommandPostDtlService extends IService<PreCommandPostDtl> {

    void createOrUpdatePreCommandPostDtl(List<PreCommandPostDtlModel> datas, String comId);

    void deletePreCommandPostDtlByComId(String comId);
}
