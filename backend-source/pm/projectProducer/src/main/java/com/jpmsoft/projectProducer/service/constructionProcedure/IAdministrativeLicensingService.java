package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.AdministrativeLicensingRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.AdministrativeLicensing;

public interface IAdministrativeLicensingService extends IService<AdministrativeLicensing> {


    BaseResponse createAdministrativeLicensing(AdministrativeLicensingRequest request);

    BaseResponse getAdministrativeLicensingList(AdministrativeLicensingRequest request);

    BaseResponse getAdministrativeLicensingInfoById(String uuId);

    BaseResponse deleteAdministrativeLicensing(String uuId);

    BaseResponse updateAdministrativeLicensing(AdministrativeLicensingRequest request);

    BaseResponse updateApproveStatusAdministrativeLicensing(AdministrativeLicensingRequest request);
}
