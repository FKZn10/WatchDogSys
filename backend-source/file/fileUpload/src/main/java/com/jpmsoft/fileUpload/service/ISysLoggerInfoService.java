package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.dto.SysLoggerInfoDto;
import com.jpmsoft.fileUpload.pojo.entity.SysLoggerInfo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ISysLoggerInfoService extends IService<SysLoggerInfo> {

    void exportLoggerInfo2Excel();

    void delLoggerInfoByIds(List<String> ids);

    ResponseEntity<byte[]> ExportLoggerInfo(SysLoggerInfoDto sysLoggerInfo, HttpServletResponse response,String token);
}
