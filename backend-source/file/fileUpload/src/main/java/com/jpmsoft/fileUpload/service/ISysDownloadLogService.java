package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.dto.SysDownloadLogDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysDownloadLog;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/28 9:29
 */
public interface ISysDownloadLogService extends IService<SysDownloadLog> {

    void create(String uid,String uname,String fileId,String fileName,String formId);

    void create(LogUserInfoVo user, String fileId, String fileName, String formId);

    void create(LogUserInfoVo user, SysFileAnnex file,Boolean down);

    void create(LogUserInfoVo user, String fileIds,Boolean down);

    void create(LogUserInfoVo user, List<SysFileAnnex> files);

    SysDownloadLog getEntity(String uid,String uname,String fileId,String fileName,String formId, InterfaceEncDecDTO ied);

    BaseResponse getDownloadLogList(SysDownloadLogDTO request);

    ResponseEntity<byte[]> ExportDownloadLog(SysDownloadLogDTO request, HttpServletResponse response);
}
