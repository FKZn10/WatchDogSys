package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.SupervisionUnitWonRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.SupervisionUnitWon;


public interface ISupervisionUnitWonService extends IService<SupervisionUnitWon> {
    /**
     * ..监理单位中标通知书
     */
    BaseResponse createSupervisionUnitWon(SupervisionUnitWonRequest request);

    BaseResponse getSupervisionUnitWonList(SupervisionUnitWonRequest request);

    BaseResponse getSupervisionUnitWonInfoById(String uuId);

    BaseResponse deleteSupervisionUnitWon(String uuId);

    BaseResponse updateSupervisionUnitWon(SupervisionUnitWonRequest request);

    BaseResponse updateApproveStatusSupervisionUnitWon(SupervisionUnitWonRequest request);
}
