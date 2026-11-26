package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeMeetingConfirmBackRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMeetingConfirmBack;

public interface IPeMeetingConfirmBackService extends IService<PeMeetingConfirmBack> {

    BaseResponse meetingConfirmBack(PeMeetingConfirmBackRequest request);

    BaseResponse getMeetingConfirmBackList(PeMeetingConfirmBackRequest request, String uId);
}
