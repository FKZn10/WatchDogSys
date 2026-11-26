package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.userProducer.pojo.entity.FiledChangeRecord;
import com.jpmsoft.userProducer.pojo.entity.SysAuditLog;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessage;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessageLog;
import com.jpmsoft.userProducer.service.*;
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
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysAuditLogService auditLogService;
    @Resource
    private IFiledChangeRecordService filedChangeRecordService;
    @Resource
    private ISysShortMessageLogService sysShortMessageLogService;


    @GetMapping("/refreshShortMessageLogSign")
    public BaseResponse refreshShortMessageLogSign() {
        log.info("refreshShortMessageLogSign");
        List<SysShortMessageLog> prs = sysShortMessageLogService.list();
        if (!CollectionUtils.isEmpty(prs)) {
            InterfaceEncDecDTO conf = sysConfigEntityService.getInterfaceEncDec();
            prs.forEach(p -> {
                p.setSign(apiUtilsService.sign(conf, conf.getAuditSign(), p));
            });
            sysShortMessageLogService.updateBatchById(prs, 200);
        }
        return BaseResponse.ok();
    }


    @GetMapping("/refreshSysAuditLogSign")
    public BaseResponse refreshSysAuditLogSign() {
        log.info("refreshSysAuditLogSign");
        List<SysAuditLog> prs = auditLogService.list();
        if (!CollectionUtils.isEmpty(prs)) {
            InterfaceEncDecDTO conf = sysConfigEntityService.getInterfaceEncDec();
            prs.forEach(p -> {
                p.setSign(apiUtilsService.sign(conf, conf.getAuditSign(), p));
            });
            auditLogService.updateBatchById(prs, 200);
        }
        return BaseResponse.ok();
    }

    @GetMapping("/refreshFiledChangeRecordSign")
    public BaseResponse refreshFiledChangeRecordSign() {
        log.info("refreshFiledChangeRecordSign");
        List<FiledChangeRecord> prs = filedChangeRecordService.list();
        if (!CollectionUtils.isEmpty(prs)) {
            InterfaceEncDecDTO conf = sysConfigEntityService.getInterfaceEncDec();
            prs.forEach(p -> {
                p.setSign(apiUtilsService.sign(conf, conf.getFieldChangeSign(), p));
            });
            filedChangeRecordService.updateBatchById(prs, 200);
        }
        return BaseResponse.ok();
    }
}
