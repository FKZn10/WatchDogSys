package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.dto.SysLoginLogDto;
import com.jpmsoft.fileUpload.pojo.entity.SysLoginLog;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:26
 */
public interface ISysLoginLogService extends IService<SysLoginLog> {


    ResponseEntity<byte[]> ExportUserLoginLog(SysLoginLogDto sysLoginLogDto, HttpServletResponse response);
}
