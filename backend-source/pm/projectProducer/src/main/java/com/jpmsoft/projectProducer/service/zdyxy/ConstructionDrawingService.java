package com.jpmsoft.projectProducer.service.zdyxy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionDrawingModel;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.ConstructionDrawing;

public interface ConstructionDrawingService extends IService<ConstructionDrawing> {
    BaseResponse saveOrUpdateData(ConstructionDrawingModel model);

    BaseResponse approvalData(ConstructionDrawingModel request);

    BaseResponse getList(ConstructionDrawingModel request, String token);

    BaseResponse getDetail(String id);
}
