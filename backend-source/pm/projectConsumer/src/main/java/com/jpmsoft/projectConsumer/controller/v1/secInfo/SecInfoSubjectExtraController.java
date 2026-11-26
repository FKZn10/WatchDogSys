package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectExtraDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 科目信息扩展
 */
@Slf4j
@RestController
@RequestMapping("/v1/secInfoSubjectExtra")
public class SecInfoSubjectExtraController {
    @Resource
    private SecFeign secFeign;

    /**
     * 创建或编辑科目信息扩展
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdateSecInfoSubjectExtra")
    public BaseResponse createOrUpdateSecInfoSubjectExtra(@RequestBody SecInfoSubjectExtraDTO request) {
        log.info("createOrUpdateSecInfoSubjectExtra");
        return secFeign.createOrUpdateSecInfoSubjectExtra(request);
    }

    /**
     * 删除科目信息扩展
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteSecInfoSubjectExtraById")
    public BaseResponse deleteSecInfoSubjectExtraById(@RequestParam String id) {
        log.info("deleteSecInfoSubjectExtraById");
        return secFeign.deleteSecInfoSubjectExtraById(id);
    }
}