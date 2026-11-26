package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveDirectory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/22 15:50
 */
public interface IArchiveDirectoryService extends IService<ArchiveDirectory> {

    void exportArchiveDirectory(HttpServletResponse response);

    BaseResponse importArchiveDirectory(MultipartFile file, LogUserInfoVo user);


    List<ArchiveDirectory> getDirByCodes(List<String> codes);

    List<ArchiveDirectory> getDirByIds(List<String> Ids);
}
