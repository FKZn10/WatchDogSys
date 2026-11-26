package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment.MePriceListDto;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 核价单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/4 15:05
 */
@Slf4j
@RestController
@Api(tags = "核价单", value = "核价单")
@RequestMapping("/material/v1/mePriceList")
public class MePriceListController {

    @Autowired
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改核价单")
    @PostMapping("/createOrUpdateMePriceList")
    public BaseResponse createOrUpdateMePriceList(@RequestBody MePriceListDto request) {
        log.info("createOrUpdateMePriceList");
        return materialFeign.createOrUpdateMePriceList(request);
    }

    @ApiOperation(value = "分页查询核价单")
    @PostMapping("/getMePriceListList")
    public BaseResponse getMePriceListList(@RequestBody MePriceListDto request, @RequestHeader String token) {
        log.info("getMePriceListList");
        return materialFeign.getMePriceListList(request, token);
    }

    @ApiOperation(value = "ID查询核价单")
    @GetMapping("/getMePriceListById")
    public BaseResponse getMePriceListById(@RequestParam String id) {
        log.info("getMePriceListById");
        return materialFeign.getMePriceListById(id);
    }
}
