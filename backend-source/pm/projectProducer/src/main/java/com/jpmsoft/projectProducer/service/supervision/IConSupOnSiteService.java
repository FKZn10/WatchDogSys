package com.jpmsoft.projectProducer.service.supervision;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupOnSiteRequest;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupOnSite;

public interface IConSupOnSiteService extends IService<ConSupOnSite> {


    BaseResponse createOrUpdateConSupOnSite(ConSupOnSiteRequest request);

    BaseResponse getConSupOnSiteList(ConSupOnSiteRequest request, String uId);

    BaseResponse getConSupOnSiteById(String osId);
}
