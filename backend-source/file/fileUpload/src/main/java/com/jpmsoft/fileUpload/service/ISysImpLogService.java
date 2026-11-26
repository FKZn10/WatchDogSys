package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.dto.SysImpLogDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysImpLog;

import java.io.File;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/20 15:27
 */
public interface ISysImpLogService extends IService<SysImpLog> {

    void create(LogUserInfoVo user, File file,String msg,String fileName);

    void create(LogUserInfoVo user, File file,String msg,String fileName, Boolean deleteImportZip);

    BaseResponse page(SysImpLogDTO request);
}
