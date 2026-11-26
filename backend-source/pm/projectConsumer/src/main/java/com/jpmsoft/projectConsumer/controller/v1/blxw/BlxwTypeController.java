package com.jpmsoft.projectConsumer.controller.v1.blxw;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwTypeModel;
import com.jpmsoft.projectConsumer.feign.BlxwInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/BlxwType")
@Slf4j
public class BlxwTypeController {
    @Autowired
    private BlxwInfoFeign blxwInfoFeign;

    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdateBlxwType")
    public BaseResponse createOrUpdateBlxwType(@RequestBody BlxwTypeModel request) {
        log.info("createOrUpdateBlxwType");
        return blxwInfoFeign.createOrUpdateBlxwType(request);
    }

    /**
     * ID删除
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteBlxwTypeById")
    public BaseResponse deleteBlxwTypeById(@RequestParam String id) {
        log.info("deleteBlxwTypeById");
        return blxwInfoFeign.deleteBlxwTypeById(id);
    }

    /**
     * 获取List集合
     *
     * @return
     */
    @PostMapping("/getBlxwTypeTreeList")
    public BaseResponse getBlxwTypeTreeList(@RequestBody BlxwTypeModel request, @RequestHeader String token) {
        log.info("getBlxwTypeTreeList");
        return blxwInfoFeign.getBlxwTypeTreeList(request, token);
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
        return blxwInfoFeign.upOrDownBlxwType(id, action,token);
    }

    @GetMapping("/getBlxwTypeByParentId")
    public BaseResponse getBlxwTypeByParentId(@RequestParam String parentId) {
        log.info("getBlxwTypeByParentId");
        return blxwInfoFeign.getBlxwTypeByParentId(parentId);
    }
}
