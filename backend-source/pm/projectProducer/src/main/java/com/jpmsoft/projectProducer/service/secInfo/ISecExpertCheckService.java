package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertCheckDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheck;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 13:32
 */
public interface ISecExpertCheckService extends IService<SecExpertCheck> {

    BaseResponse createOrUpdateSecExpertCheck(SecExpertCheckDTO request);

    BaseResponse getSecExpertCheckList(SecExpertCheckDTO request, LogUserInfoVo user);

    BaseResponse getSecExpertCheckById(String id);

    BaseResponse getSecEXpertCheckSendToDo();

    BaseResponse getSecExpertCheckBySrId(String srId, String uid);

    BaseResponse getSecExpertCheckTaskTimeout(SecExpertCheckDTO request);

    BaseResponse syncSecExpertPause(String id);
}
