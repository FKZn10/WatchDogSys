package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.OutlineDatasourceDto;
import com.jpmsoft.userProducer.pojo.entity.OutlineDatasource;

public interface OutlineDatasourceService extends IService<OutlineDatasource> {
    BaseResponse createOrUpdateOutlineDatasource(OutlineDatasourceDto request, String token);

    BaseResponse getOutlineDatasourceList(OutlineDatasourceDto request);

    BaseResponse getOutlineDatasourceById(String id);
}
