package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.construction.TechnicalCheckDTO;
import com.jpmsoft.projectProducer.pojo.entity.construction.TechnicalCheck;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 16:23
 */
public interface ITechnicalCheckService extends IService<TechnicalCheck> {

    BaseResponse createOrUpdate(TechnicalCheckDTO request);

    BaseResponse getTechnicalCheckList(TechnicalCheckDTO request, String uid);

    BaseResponse getTechnicalCheckById(String id);
}
