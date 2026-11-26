package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceWeekdayModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceWeekday;

import java.util.List;

public interface IOaAttendanceWeekdayService extends IService<OaAttendanceWeekday> {

    BaseResponse createOrUpdateAttendanceWeekday(OaAttendanceWeekdayModel request);

    BaseResponse getAttendanceWeekdayList(OaAttendanceWeekdayModel request);

    void deleteThenToCreateWeekday(List<OaAttendanceWeekdayModel> models, String agId, String teId);
}
