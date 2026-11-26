package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveFileNameDTO;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveFileName;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:09
 */
public interface IArchiveFileNameService extends IService<ArchiveFileName> {

    BaseResponse createOrUpdate(ArchiveFileNameDTO request, String token);

    BaseResponse getArchiveFileNameList(ArchiveFileNameDTO request);

    BaseResponse deleteById(String id);

    String name(SysFileAnnex file);
}
