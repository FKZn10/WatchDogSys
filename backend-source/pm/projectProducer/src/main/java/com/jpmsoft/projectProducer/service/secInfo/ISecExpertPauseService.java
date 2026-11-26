package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertPauseDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertPause;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:08
 */
public interface ISecExpertPauseService extends IService<SecExpertPause> {

    BaseResponse createOrUpdateSecExpertPause(SecExpertPauseDTO request);

    BaseResponse getSecExpertPauseList(SecExpertPauseDTO request, LogUserInfoVo user);

    BaseResponse getSecExpertPauseListClass(SecExpertPauseDTO request, LogUserInfoVo user);

    BaseResponse getSecExpertPauseById(String id);

    BaseResponse approveSecExpertPauseById(String id);

    BaseResponse approveSecExpertPause();

    Integer getSepDaysYear(String exId, Integer year);

    BaseResponse invalidProcessData(List<String> exIds);

    BaseResponse handleExpertPause(Map<String, Integer> exIdToDays);
}
