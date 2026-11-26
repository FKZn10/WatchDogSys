package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWinNoticeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinNotice;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecWinNoticeService extends IService<SecWinNotice> {

    BaseResponse createOrUpdateWinNotice(SecWinNoticeRequest request);

    BaseResponse getWinNoticeList(SecWinNoticeRequest request);

    BaseResponse getWinNoticeByWnId(String wnId);

    BaseResponse getLatestApproveWinNotice(String secId);

    BaseResponse deleteWinNoticeByWnId(String wnId);

    BaseResponse getWinNoticeListOne2Many(SecWinNoticeRequest request);

    BaseResponse getWinNoticeCompleteBySecId(String secId);

    BaseResponse getWinNoticeListClass(SecWinNoticeRequest request, LogUserInfoVo user);

    BaseResponse getWinNoticeListClassInfo(SecWinNoticeRequest request, LogUserInfoVo user);

    BaseResponse getWinningBidInformation(String secId);
}
