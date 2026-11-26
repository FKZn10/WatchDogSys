package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.SysUserManualDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysUserManual;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/26 9:07
 */
public interface ISysUserManualService extends IService<SysUserManual> {

    BaseResponse createOrUpdateManual(SysUserManualDTO request, String token);

    BaseResponse getUserManualTree(SysUserManualDTO request);

    BaseResponse getUserManualById(String id);

    BaseResponse getUserManualByKey(String key);

    BaseResponse getUserManualByCode(String code);

    BaseResponse deleteUserManualById(SysUserManualDTO request);
}
