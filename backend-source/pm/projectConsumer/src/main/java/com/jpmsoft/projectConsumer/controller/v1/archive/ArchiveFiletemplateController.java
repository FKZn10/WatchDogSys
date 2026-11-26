package com.jpmsoft.projectConsumer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "归档文档模板", value = "归档文档模板")
@RequestMapping("/Archive/v1/archiveFiletemplate")
public class ArchiveFiletemplateController {
    @Autowired
    private ArchiveFeign archiveFeign;

    @ApiOperation(value = "目录ID查询归档文档模板")
    @GetMapping("/getArchiveFiletemplateByAdId")
    public BaseResponse getArchiveFiletemplateByAdId(@RequestParam String adId) {
        log.info("getArchiveFiletemplateByAdId");
        return archiveFeign.getArchiveFiletemplateByAdId(adId);
    }
}
