package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignInitialRequest;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignInitial;


public interface IDesignInitialService extends IService<DesignInitial> {

    BaseResponse createOrUpdateDesignInitial(DesignInitialRequest request);

    BaseResponse getDesignInitialList(DesignInitialRequest request, String uId);

    BaseResponse getDesignInitialStatisticsList(DesignInitialRequest request, String uId);

    BaseResponse getDesignInitialListNoAuth(DesignInitialRequest request);

    BaseResponse deleteDesignInitialBydiId(String diId);

    BaseResponse getDesignInitialBydiId(String diId);

    BaseResponse updateDesignInitialApproveStatus(DesignInitialRequest request);

}
