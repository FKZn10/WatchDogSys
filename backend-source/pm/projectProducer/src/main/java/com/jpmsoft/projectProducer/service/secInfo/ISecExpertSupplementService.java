package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSupplementDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertSupplement;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:34
 */
public interface ISecExpertSupplementService extends IService<SecExpertSupplement> {

    BaseResponse createOrUpdate(SecExpertSupplementDTO request);


    BaseResponse selectList(SecExpertSupplementDTO request);

    BaseResponse selectById(String id);

    /**
     * 审批通过补充抽取的专家
     *
     * @param id
     * @return
     */
    BaseResponse approveAppend(String id);

}
