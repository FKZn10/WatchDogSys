package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.LandUsePlanningRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.LandUsePlanning;

public interface ILandUsePlanningService extends IService<LandUsePlanning> {
    /**
     * 用地规划许可证
     */
    BaseResponse createLandUsePlanning(LandUsePlanningRequest request);

    BaseResponse getLandUsePlanningList(LandUsePlanningRequest request);

    BaseResponse getLandUsePlanningInfoById(String uuId);

    BaseResponse deleteLandUsePlanning(String uuId);

    BaseResponse updateLandUsePlanning(LandUsePlanningRequest request);

    BaseResponse updateApproveStatusLandUsePlanning(LandUsePlanningRequest request);

}
