package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawing;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignProjectInfo;

import java.util.List;


public interface IDesignProjectInfoService extends IService<DesignProjectInfo> {


    void createOrUpdateDesignProjectInfo(DesignProjectInfoModel designProjectInfoModel);


    List<DesignProjectInfoModel> getDesignProjectInfoList(DesignProjectInfoModel designProjectInfoModel,String uId);


    DesignProjectInfoModel getDesignProjectInfoByBusinessId(String businessId,Integer businessType,String pId);


    void delDesignProjectInfoByBusinessId(String businessId,Integer businessType);

}
