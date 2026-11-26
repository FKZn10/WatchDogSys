package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConBoqEcchangeDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/24 9:42
 */
public interface IConBoqEcchangeDtlService extends IService<ConBoqEcchangeDtl> {

    BaseResponse createOrUpdateBoqEcChange(List<ConBoqDtlDto> request, String ecId);

    BaseResponse getBoqEcChangeDtlByEcId(String ecId);

    BaseResponse getBoqEcChangeDtlByBoqdId(String boqdId);

    BaseResponse can2Change(ConBoqDtlDto request);

    void approved2RefreshConBoqDtl(String ecId);

    void deleteByEcId(String ecId);
}
