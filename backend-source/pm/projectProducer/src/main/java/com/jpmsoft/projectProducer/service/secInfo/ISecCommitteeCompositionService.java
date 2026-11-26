package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCommitteeCompositionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCommitteeComposition;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:50
 */
public interface ISecCommitteeCompositionService extends IService<SecCommitteeComposition> {

    BaseResponse createOrUpdateSecCommitteeComposition(SecCommitteeCompositionDTO request);

    BaseResponse getSecCommitteeCompositionById(String id);
}
