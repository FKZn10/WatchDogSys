package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.NoticeOfConstructionUnitRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.NoticeOfConstructionUnit;

public interface INoticeOfConstructionUnitService extends IService<NoticeOfConstructionUnit> {

    /**
     * 施工单位中标通知书
     */
    BaseResponse createNoticeOfConstructionUnit(NoticeOfConstructionUnitRequest request);

    BaseResponse getNoticeOfConstructionUnitList(NoticeOfConstructionUnitRequest request);

    BaseResponse getNoticeOfConstructionUnitInfoById(String uuId);

    BaseResponse deleteNoticeOfConstructionUnit(String uuId);

    BaseResponse updateNoticeOfConstructionUnit(NoticeOfConstructionUnitRequest request);

    BaseResponse updateApproveStatusNoticeOfConstructionUnit(NoticeOfConstructionUnitRequest request);

}
