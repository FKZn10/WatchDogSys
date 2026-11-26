package com.jpmsoft.projectProducer.controller.v1.qualitySafety;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsManageSystemDto;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsManageSystemService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/qsManageSystem")
public class QsManageSystemController {

    @Resource
    private IQsManageSystemService qsManageSystemService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateQsManageSystem")
    public BaseResponse createOrUpdateQsManageSystem(@RequestBody QsManageSystemDto request) {
        log.info("createOrUpdateQsManageSystem");
        return qsManageSystemService.createOrUpdate(request);
    }

    @TenantId
    @PostMapping("/getQsManageSystemList")
    public BaseResponse getQsManageSystemList(@RequestBody QsManageSystemDto request, @RequestHeader String token) {
        log.info("getQsManageSystemList");
        return qsManageSystemService.getQsManageSystemList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getQsManageSystemById")
    public BaseResponse getQsManageSystemById(@RequestParam String id) {
        log.info("getQsManageSystemById");
        return qsManageSystemService.getQsManageSystemById(id);
    }

    @GetMapping("/getQsNodePerson")
    public BaseResponse getQsNodePersonByMark(@RequestParam String pId) {
        log.info("getQsNodePerson");
        return qsManageSystemService.getQsNodePersonByMark(pId);
    }

}
