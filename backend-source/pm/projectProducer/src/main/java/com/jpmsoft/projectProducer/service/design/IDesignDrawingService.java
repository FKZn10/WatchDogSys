package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawing;

import java.util.List;


public interface IDesignDrawingService extends IService<DesignDrawing> {

    BaseResponse createOrUpdateDesignDrawing(DesignDrawingModel request);

    BaseResponse getDesignDrawingList(DesignDrawingModel request, String uId);

    BaseResponse getDesignDrawingListNoAuth(DesignDrawingModel request);

    BaseResponse getDesignDrawingById(String ddId);

    BaseResponse getDesignDrawingListRW(DesignDrawingModel request, String uId);

    List<DesignDrawingModel> getDesignDrawingExportRW(DesignDrawingModel request, String uId);
}
