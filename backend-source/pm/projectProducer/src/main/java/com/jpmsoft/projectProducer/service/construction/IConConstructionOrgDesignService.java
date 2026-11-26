package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Construction.ConConstructionOrgDesignRequest;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConConstructionOrgDesign;

public interface IConConstructionOrgDesignService extends IService<ConConstructionOrgDesign> {


    BaseResponse createOrUpdateConConstructionOrgDesign(ConConstructionOrgDesignRequest request);

    BaseResponse getConConstructionOrgDesignList(ConConstructionOrgDesignRequest request, String uId);

    BaseResponse getConConstructionOrgDesignById(String codId);

}
