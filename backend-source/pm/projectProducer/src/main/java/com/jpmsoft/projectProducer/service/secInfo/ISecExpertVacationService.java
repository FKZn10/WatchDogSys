package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertVacationDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertVacation;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:08
 */
public interface ISecExpertVacationService extends IService<SecExpertVacation> {

    BaseResponse createOrUpdateSecExpertVacation(SecExpertVacationDTO request);

    BaseResponse getSecExpertVacationList(SecExpertVacationDTO request, LogUserInfoVo user);

    BaseResponse getSecExpertVacationById(String id);

    BaseResponse approveSecExpertVacationById(String id,String token );
}
