package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreConstructionPermit;

public interface IPreConstructionPermitService extends IService<PreConstructionPermit> {

    BaseResponse createOrUpdatePreConstructionPermit(PreConstructionPermitModel request);

    BaseResponse getPreConstructionPermitList(PreConstructionPermitModel request, String uId);

    BaseResponse getPreConstructionPermitStatisticsList(PreConstructionPermitModel request, String uId);

    BaseResponse getPreConstructionPermitById(String cpId);

    /**
     * 审批后更新项目信息（经纬度）
     *
     * @param cpId
     * @return
     */
    BaseResponse approval2RefershProjectInfo(String cpId);

    BaseResponse getPreConstructionPermitUnitListByPid(String pId);
}
