package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import com.jpmsoft.projectProducer.pojo.dto.blxw.BlxwInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfo;
import com.jpmsoft.projectProducer.pojo.request.blxw.BlxwInfoRequest;

public interface INoticeInfoService extends IService<NoticeInfo> {

    BaseResponse saveOrUpdateNoticeInfo(NoticeInfoModel dto) ;
    /***
     * 审核汇总结果
     * @param request
     * @return
     */
    BaseResponse approvalData(NoticeInfoModel request);

    /**
     * 获取汇总信息列表
     * @param request
     * @param token
     * @return
     */
    BaseResponse getNoticeInfoList(NoticeInfoModel request,String token);

    /***
     * 获取汇总信息详情
     * @param id
     * @return
     */
    BaseResponse getNoticeInfoDetail(String id);
}
