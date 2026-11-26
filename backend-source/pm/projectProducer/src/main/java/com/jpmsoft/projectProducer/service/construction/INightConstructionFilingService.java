package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.construction.NightConstructionFilingDTO;
import com.jpmsoft.projectProducer.pojo.entity.construction.NightConstructionFiling;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/24 10:50
 */
public interface INightConstructionFilingService extends IService<NightConstructionFiling> {

    BaseResponse createOrUpdateNightConstructionFiling(NightConstructionFilingDTO request);

    BaseResponse getNightConstructionFilingList(NightConstructionFilingDTO request, String uId);

    BaseResponse getNightConstructionFilingById(String id);
}
