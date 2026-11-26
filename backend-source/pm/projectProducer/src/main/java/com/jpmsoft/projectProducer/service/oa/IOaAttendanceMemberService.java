package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceMemberModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceMember;

import java.util.List;

public interface IOaAttendanceMemberService extends IService<OaAttendanceMember> {

    BaseResponse createOrUpdateAttendanceMember(OaAttendanceMemberModel request);

    BaseResponse createOrUpdateAttendanceMemberByBath(List<OaAttendanceMemberModel> request,String teId);

    BaseResponse getAttendanceMemberList(OaAttendanceMemberModel request);

    void delAttendanceMemberByAgIds(List<String> agIds);
}
