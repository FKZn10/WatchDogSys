package com.jpmsoft.projectConsumer.controller.v1.siteform;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.siteform.DocDistributionUnitDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 下发单位表
 */
@Slf4j
@RestController
@RequestMapping("/v1/docDistributionUnit")
public class DocDistributionUnitController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 批量新增或更新下发单位
     *
     * @param requests
     * @return
     */
    @PostMapping("/batchCreateOrUpdateDocDistributionUnit")
    public BaseResponse batchCreateOrUpdateDocDistributionUnit(@RequestBody List<DocDistributionUnitDTO> requests) {
        log.info("batchCreateOrUpdateDocDistributionUnit");
        return baseFeign.batchCreateOrUpdateDocDistributionUnit(requests);
    }
}
