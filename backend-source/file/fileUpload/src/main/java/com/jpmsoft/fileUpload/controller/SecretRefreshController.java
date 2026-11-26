package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysDownloadLog;
import com.jpmsoft.fileUpload.service.IApiUtilsService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysDownloadLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@ApiIgnore
@RequestMapping("/v1/secret")
public class SecretRefreshController {

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysDownloadLogService sysDownloadLogService;

    @GetMapping("/refreshDownloadLogSign")
    public BaseResponse refreshSysAuditLogSign() {
        log.info("refreshDownloadLogSign");
        List<SysDownloadLog> prs = sysDownloadLogService.list();
        if (!CollectionUtils.isEmpty(prs)) {
            InterfaceEncDecDTO conf = sysConfigService.getInterfaceEncDec();
            prs.forEach(p -> {
                p.setSign(apiUtilsService.sign(conf, conf.getAuditSign(), p));
            });
            sysDownloadLogService.updateBatchById(prs, 200);
        }
        return BaseResponse.ok();
    }
}
