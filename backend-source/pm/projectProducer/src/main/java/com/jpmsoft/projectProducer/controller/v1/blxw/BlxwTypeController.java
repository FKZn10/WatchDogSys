package com.jpmsoft.projectProducer.controller.v1.blxw;

import com.jpmsoft.common.base.BaseResponse;

import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwTypeModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.blxw.IBlxwTypeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/BlxwType")
@Slf4j
public class BlxwTypeController {
    @Autowired
    private IBlxwTypeService blxwTypeService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdateBlxwType")
    public BaseResponse createOrUpdateBlxwType(@RequestBody BlxwTypeModel request) {
        log.info("createOrUpdateBlxwType");
        return blxwTypeService.createOrUpdateBlxwType(request);
    }

    /**
     * ID删除
     *
     * @param
     * @return
     */
    @GetMapping("/deleteBlxwTypeById")
    public BaseResponse deleteBlxwTypeById(@RequestParam String id) {
        log.info("deleteBlxwTypeById");
        return blxwTypeService.deleteBlxwTypeById(id);
    }

    /**
     * 获取List集合
     *
     * @return
     */
    @PostMapping("/getBlxwTypeTreeList")
    public BaseResponse getBlxwTypeTreeList(@RequestBody BlxwTypeModel request, @RequestHeader String token) {
        log.info("getBlxwTypeTreeList");
        return blxwTypeService.getBlxwTypeTreeList(request, redisUtils.getUid(token));
    }

    /**
     * 上移或者下移
     *
     * @param id
     * @param action
     * @return
     */
    @GetMapping("/upOrDownBlxwType")
    public BaseResponse upOrDownBlxwType(@RequestParam String id, @RequestParam String action,@RequestHeader String token) {
        log.info("upOrDownBlxwType");
        return blxwTypeService.upOrDownBlxwType(id, action);
    }

    @GetMapping("/getBlxwTypeByParentId")
    public BaseResponse getBlxwTypeByParentId(@RequestParam String parentId) {
        log.info("getBlxwTypeByParentId");
        return blxwTypeService.getBlxwTypeByParentId(parentId);
    }
}
