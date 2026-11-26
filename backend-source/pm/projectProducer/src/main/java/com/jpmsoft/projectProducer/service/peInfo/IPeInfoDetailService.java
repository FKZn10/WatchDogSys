package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoDetailModel;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoDetail;

import java.util.List;

public interface IPeInfoDetailService extends IService<PeInfoDetail> {

    /**
     * 新增
     *
     * @param peId  履约评价ID
     * @param model
     */
    void createOrUpdatePeInfoDetail(String peId, List<PeInfoDetailModel> model);

    List<PeInfoDetailModel> getPeInfoDetailByPeId(String peId);

    void deletePeInfoToDeleteDetail(String peId);
}
