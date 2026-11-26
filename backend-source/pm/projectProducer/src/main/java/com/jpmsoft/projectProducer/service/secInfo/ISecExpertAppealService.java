package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAppealDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAppeal;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 13:47
 */
public interface ISecExpertAppealService extends IService<SecExpertAppeal> {


    BaseResponse createOrUpdateSecExpertAppeal(SecExpertAppealDTO request);

    BaseResponse getSecExpertAppealList(SecExpertAppealDTO request, LogUserInfoVo user);

    BaseResponse getSecExpertAppealById(String id);

    BaseResponse approveSecExpertAppealById(String id);
}
