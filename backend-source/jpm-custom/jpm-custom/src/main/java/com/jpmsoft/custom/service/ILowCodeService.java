package com.jpmsoft.custom.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.custom.pojo.vo.LogUserInfoVo;

import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/30 9:35
 */
public interface ILowCodeService {


    BaseResponse createOrUpdate(Map<String, Object> request, String formId, LogUserInfoVo user);


    BaseResponse selectList(Map<String, Object> request, String formId, LogUserInfoVo user);

    BaseResponse selectedFilterCriteria(Map<String, Object> request, String formId, LogUserInfoVo user);

    BaseResponse selectById(String id, String formId, LogUserInfoVo user);

}
