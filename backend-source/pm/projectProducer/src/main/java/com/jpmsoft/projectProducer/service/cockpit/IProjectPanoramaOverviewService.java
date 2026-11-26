package com.jpmsoft.projectProducer.service.cockpit;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.ProjectPanoramaOverviewModel;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.ProjectPanoramaOverview;


public interface IProjectPanoramaOverviewService extends IService<ProjectPanoramaOverview> {

    BaseResponse createOrUpdateProjectPanoramaOverview(ProjectPanoramaOverviewModel request);

    BaseResponse getProjectPanoramaOverviewList(ProjectPanoramaOverviewModel request, String uId);

    BaseResponse getProjectPanoramaList(String teId);

    BaseResponse getProjectPanoramaOverviewById(String ppoId);

    BaseResponse deleteProjectPanoramaOverviewById(String ppoId);

    /**
     * 获取图片地址用于轮播图
     *
     * @param request
     * @return
     */
    BaseResponse getProjectPanoramaOverviewFile(ProjectPanoramaOverviewModel request);
}
