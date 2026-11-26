package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoDetailStandardModel;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoDetailStandard;

import java.util.List;

public interface IPeInfoDetailStandardService extends IService<PeInfoDetailStandard> {

    void createPeInfoStandard(List<PeInfoDetailStandardModel> models);


    void deleteOldAllPeInfoStandard(List<String> pedIds);


    List<PeInfoDetailStandardModel> getPeInfoDetailStandardByPeId(String peId);
}
