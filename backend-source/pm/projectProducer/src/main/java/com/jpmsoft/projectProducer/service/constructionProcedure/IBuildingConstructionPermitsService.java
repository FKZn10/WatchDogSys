package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.BuildingConstructionPermitsRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.BuildingConstructionPermits;

public interface IBuildingConstructionPermitsService extends IService<BuildingConstructionPermits> {
    /**
     * 建筑工程施工许可证
     */
    BaseResponse createBuildingConstructionPermits(BuildingConstructionPermitsRequest request);

    BaseResponse getBuildingConstructionPermitsList(BuildingConstructionPermitsRequest request);

    BaseResponse getBuildingConstructionPermitsInfoById(String uuId);

    BaseResponse deleteBuildingConstructionPermits(String uuId);

    BaseResponse updateBuildingConstructionPermits(BuildingConstructionPermitsRequest request);

    BaseResponse updateApproveStatusBuildingConstructionPermits(BuildingConstructionPermitsRequest request);
}
