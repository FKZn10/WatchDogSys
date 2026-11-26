package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserveChange;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:22
 */
public interface IPreProjectReserveChangeService extends IService<PreProjectReserveChange> {

    BaseResponse createOrUpdateProjectReserveChange(PreProjectReserveChangeDTO request);

    BaseResponse getProjectReserveChangeList(PreProjectReserveChangeDTO request);

    BaseResponse getProjectReserveChangeById(String id);

    BaseResponse syncProjectReserve(String id);

    void deleteByPprId(String pprId);
}
