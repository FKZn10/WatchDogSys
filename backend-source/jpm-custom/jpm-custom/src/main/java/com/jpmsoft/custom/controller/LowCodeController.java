package com.jpmsoft.custom.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.custom.constants.CustomMsg;
import com.jpmsoft.custom.service.ILowCodeService;
import com.jpmsoft.custom.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 低代码
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/29 11:00
 */
@Slf4j
@RestController
@Api(tags = "自定义表单通用接口", value = "自定义表单通用接口")
@RequestMapping("/v1/custom")
public class LowCodeController {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ILowCodeService lowCodeService;


    @PostMapping("/getSwagger")
    public BaseResponse getSwagger(@RequestBody Map<String, Object> data, @RequestParam String pre) {
        for (Map.Entry<String, Object> d : data.entrySet()) {
            System.out.println(pre + d.getKey());
        }
        return BaseResponse.ok();
    }

    @ApiOperation(value = "通用表单创建修改")
    @PostMapping("/createOrUpdate/{formId}")
    public BaseResponse createOrUpdate(@RequestBody Map<String, Object> request, @PathVariable("formId") String formId, @RequestHeader String token) {
        return lowCodeService.createOrUpdate(request, formId, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "通用表单分页")
    @PostMapping("/selectList/{formId}")
    public BaseResponse selectList(@RequestBody Map<String, Object> request, @PathVariable("formId") String formId, @RequestHeader String token) {
        return lowCodeService.selectList(request, formId, redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "通用表单分页下拉筛选条件")
    @PostMapping("/selectedFilterCriteria/{formId}")
    public BaseResponse selectedFilterCriteria(@RequestBody Map<String, Object> request, @PathVariable("formId") String formId, @RequestHeader String token) {
        return lowCodeService.selectedFilterCriteria(request, formId, redisUtils.getUserInfoByToken(token));
    }


    @ApiOperation(value = "通用表单ID查询")
    @GetMapping("/selectById/{formId}")
    public BaseResponse selectById(@RequestParam("id") String id, @PathVariable("formId") String formId, @RequestHeader String token) {
        if (specialChar(id))
            return BaseResponse.fail(CustomMsg.reqIllegal);
        return lowCodeService.selectById(id, formId, redisUtils.getUserInfoByToken(token));
    }


    /**
     * 验证ID参数特殊字符 防止注入
     *
     * @param str
     * @return
     */
    private boolean specialChar(String str) {
        if (!str.trim().equals(str)) return true;
        if (str.contains(" ")) return true;
        if (str.contains("'")) return true;
        if (str.contains("\\\"")) return true;
        if (str.contains("=")) return true;
        return false;
    }
}
