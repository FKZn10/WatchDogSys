package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConEcoDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEco;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:28
 */
public interface IConEcoService extends IService<ConEco> {

    BaseResponse createOrUpdateConEco(ConEcoDTO request);

    BaseResponse getConEcoList(ConEcoDTO request, String uId);

    BaseResponse getConEcoById(String ecoId);
}
