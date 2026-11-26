package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.NoticeInfoCloseModel;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfoClose;

public interface INoticeInfoCloseService extends IService<NoticeInfoClose> {
    BaseResponse saveOrUpdateNoticeInfoClose(NoticeInfoCloseModel request);

    BaseResponse approvalData(NoticeInfoCloseModel request);

    BaseResponse getNoticeInfoCloseList(NoticeInfoCloseModel request, String token);

    BaseResponse getNoticeInfoCloseDetail(String id);
}