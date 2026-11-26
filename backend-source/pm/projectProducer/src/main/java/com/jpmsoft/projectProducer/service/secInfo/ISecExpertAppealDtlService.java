package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAppealDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAppealDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 13:47
 */
public interface ISecExpertAppealDtlService extends IService<SecExpertAppealDtl> {

    void createOrUpdateSecExpertAppealDtl(List<SecExpertAppealDtlDTO> data, String seaId);

    void deleteBySeaId(String seaId);

    void approveSecExpertAppealById(String seaId);

    BaseResponse updateSecExpertAppealDtl(SecExpertAppealDtlDTO request);

    BaseResponse updateSecExpertAppealDtlBath(List<SecExpertAppealDtlDTO> request);
}
