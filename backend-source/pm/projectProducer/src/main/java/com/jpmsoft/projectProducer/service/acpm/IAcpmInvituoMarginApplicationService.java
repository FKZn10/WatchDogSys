package com.jpmsoft.projectProducer.service.acpm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmInvituoMarginApplicationDto;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmInvituoMarginApplication;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:36
 */
public interface IAcpmInvituoMarginApplicationService extends IService<AcpmInvituoMarginApplication> {

    BaseResponse createOrUpdate(AcpmInvituoMarginApplicationDto request);

    BaseResponse getList(AcpmInvituoMarginApplicationDto request);

    BaseResponse getById(String id);

}
