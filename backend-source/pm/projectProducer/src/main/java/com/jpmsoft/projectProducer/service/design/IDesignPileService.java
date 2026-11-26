package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignPileDto;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignPile;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 13:58
 */

public interface IDesignPileService extends IService<DesignPile> {

    BaseResponse createOrUpdateDesignPile(DesignPileDto request);

    BaseResponse getDesignPileList(DesignPileDto request, String uId);

    BaseResponse getDesignPileListNoAuth(DesignPileDto request);

    BaseResponse getDesignPileById(String dpId);
}
