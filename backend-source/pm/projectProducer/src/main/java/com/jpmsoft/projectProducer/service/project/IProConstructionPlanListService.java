package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanListModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionPlan;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionPlanList;

import java.util.List;

public interface IProConstructionPlanListService extends IService<ProConstructionPlanList> {
    void createOrUpdateProConstructionPlanList(List<ProConstructionPlanListModel> datas, ProConstructionPlan pcp);

    BaseResponse createOrUpdateProConstructionPlanList(ProConstructionPlanListModel request);

    BaseResponse getProConstructionPlanList(ProConstructionPlanListModel request);

    BaseResponse getProConstructionPlanSelectList(ProConstructionPlanListModel request);

    BaseResponse getProConstructionPlanListById(String cplId);

    BaseResponse getProConstructionPlanListByPcpId(String pcpId);
}
