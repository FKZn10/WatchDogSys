package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.SysImpLogDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysImpLog;
import com.jpmsoft.fileUpload.service.ISysImpLogService;
import com.jpmsoft.fileUpload.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/20 15:45
 */
@Slf4j
@RestController
@Api(tags = "通用导入日志", value = "通用导入日志")
@RequestMapping("/impLog")
public class SysImpLogController {
    @Resource
    private ISysImpLogService sysImpLogService;

    @ApiOperation(value = "分页查询通用导入日志")
    @PostMapping("/page")
    public BaseResponse page(@RequestBody SysImpLogDTO request) {
        return sysImpLogService.page(request);
    }

    @ApiOperation(value = "下载通用导入上传的文件")
    @GetMapping("/down")
    public void down(HttpServletResponse response, @RequestParam String id) {
        SysImpLog log = sysImpLogService.getById(id);
        if (log == null) throw new JPMException(FileMsg.infoNotExist);
        FileUtil.download(log.getImpPath(), response, log.getImpFileName());
    }

    @ApiOperation(value = "删除通用导入日志")
    @GetMapping("/del")
    public BaseResponse del(@RequestParam String id) {
        SysImpLog log = sysImpLogService.getById(id);
        if (log == null) throw new JPMException(FileMsg.infoNotExist);
        sysImpLogService.removeById(id);
        File file = new File(log.getImpPath());
        if (file.exists() && file.isFile()) file.delete();
        return BaseResponse.ok();
    }
}
