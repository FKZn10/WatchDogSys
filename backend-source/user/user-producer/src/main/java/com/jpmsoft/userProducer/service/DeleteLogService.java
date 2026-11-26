package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.DeleteLogDto;
import com.jpmsoft.userProducer.pojo.entity.DeleteLog;

/**
 * @author yb.Sun
 * @date 2024/10/23 16:22
 */
public interface DeleteLogService extends IService<DeleteLog> {

    BaseResponse insertDeleteLog(DeleteLogDto model);


    BaseResponse getDeleteLogList(DeleteLogDto request);

    BaseResponse getDeleteLogById(String id);

    BaseResponse approveData(DeleteLogDto request);

}
