package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment.MeBrandRecommendDto;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:07
 */
@Slf4j
@RestController
@Api(tags = "材料设备品牌推荐表", value = "材料设备品牌推荐表")
@RequestMapping("/v1/meBrandRecommend")
public class MeBrandRecommendController {

    @Resource
    private MaterialFeign materialFeign;


    @ApiOperation(value = "新增或修改材料设备品牌推荐表")
    @PostMapping("/createOrUpdateMeBrandRecommend")
    public BaseResponse createOrUpdateMeBrandRecommend(@RequestBody MeBrandRecommendDto request) {
        log.info("createOrUpdateMeBrandRecommend");
        return materialFeign.createOrUpdateMeBrandRecommend(request);
    }

    @ApiOperation(value = "分页查询材料设备品牌推荐表")
    @PostMapping("/getMeBrandRecommendList")
    public BaseResponse getMeBrandRecommendList(@RequestBody MeBrandRecommendDto request, @RequestHeader String token) {
        log.info("getMeBrandRecommendList");
        return materialFeign.getMeBrandRecommendList(request, token);
    }

    @ApiOperation(value = "ID查询材料设备品牌推荐表")
    @GetMapping("/getMeBrandRecommendById")
    public BaseResponse getMeBrandRecommendById(@RequestParam String id) {
        log.info("getMeBrandRecommendById");
        return materialFeign.getMeBrandRecommendById(id);
    }
}
