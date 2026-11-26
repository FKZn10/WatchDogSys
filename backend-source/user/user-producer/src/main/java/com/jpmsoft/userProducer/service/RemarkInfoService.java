package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.RemarkInfoDto;
import com.jpmsoft.userProducer.pojo.entity.RemarkInfo;

/**
 * @author yb.Sun
 * @date 2024/10/25 09:17
 */
public interface RemarkInfoService extends IService<RemarkInfo> {
    BaseResponse insertRemarkInfo(RemarkInfoDto model);

    BaseResponse getRemarkInfoList(RemarkInfoDto request);
}
