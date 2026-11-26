package com.jpmsoft.projectProducer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.archive.IArchiveFiletemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/archiveFiletemplate")
public class ArchiveFiletemplateController {
    @Resource
    private IArchiveFiletemplateService archiveFiletemplateService;

    @GetMapping("/getArchiveFiletemplateByAdId")
    public BaseResponse getArchiveFiletemplateByAdId(@RequestParam String adId) {
        log.info("getArchiveFiletemplateByAdId");
        return archiveFiletemplateService.getArchiveFiletemplateByAdId(adId);
    }
}
