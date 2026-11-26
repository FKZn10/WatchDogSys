package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConEcoDTO;
import com.jpmsoft.projectProducer.service.contract.IConEcoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:43
 */
@Slf4j
@RestController
@RequestMapping("/v1/conEco")
public class ConEcoController {

    @Resource
    private IConEcoService conEcoService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConEco")
    public BaseResponse createOrUpdateConEco(@RequestBody ConEcoDTO request) {
        log.info("createOrUpdateConEco");
        return conEcoService.createOrUpdateConEco(request);
    }

    @TenantId
    @PostMapping("/getConEcoList")
    public BaseResponse getConEcoList(@RequestBody ConEcoDTO request, @RequestHeader String token) {
        log.info("getConEcoList");
        return conEcoService.getConEcoList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConEcoById")
    public BaseResponse getConEcoById(@RequestParam String ecoId) {
        log.info("getConEcoById");
        return conEcoService.getConEcoById(ecoId);
    }
}
