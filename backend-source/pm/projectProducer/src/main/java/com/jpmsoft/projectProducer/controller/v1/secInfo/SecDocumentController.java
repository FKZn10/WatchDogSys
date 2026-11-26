package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDocumentRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecDocumentService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/secDocument")
public class SecDocumentController {
    @Resource
    private ISecDocumentService secDocumentService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecDocument")
    public BaseResponse createOrUpdateSecDocument(@RequestBody SecDocumentRequest request) {
        log.info("createOrUpdateSecDocument");
        return secDocumentService.createOrUpdateSecDocument(request);
    }

    @GetMapping("/can2CreateSecDocument")
    public BaseResponse can2CreateSecDocument(@RequestParam String secId) {
        log.info("can2CreateSecDocument");
        return secDocumentService.can2CreateSecDocument(secId);
    }

    @GetMapping("/deleteSecDocumentBydId")
    public BaseResponse deleteSecDocumentBydId(@RequestParam String dId) {
        log.info("deleteSecDocumentBydId");
        return secDocumentService.deleteSecDocumentBydId(dId);
    }

    @GetMapping("/getSecDocumentBydId")
    public BaseResponse getSecDocumentBydId(@RequestParam String dId) {
        log.info("getSecDocumentBydId");
        return secDocumentService.getSecDocumentBydId(dId);
    }

    @GetMapping("/getSecDocumentBySecId")
    public BaseResponse getSecDocumentBySecId(@RequestParam String secId) {
        log.info("getSecDocumentBySecId");
        return secDocumentService.getSecDocumentBySecId(secId);
    }

    @PostMapping("/getpBusinessUnit")
    public BaseResponse getpBusinessUnit(@RequestBody List<String> pIds) {
        log.info("getpBusinessUnit");
        return secDocumentService.getpBusinessUnit(pIds);
    }
    @TenantId
    @PostMapping("/getSecDocumentListClassInfo")
    public BaseResponse getSecDocumentListClassInfo(@RequestBody SecDocumentRequest request,@RequestHeader String token) {
        log.info("getSecDocumentListClassInfo");
        return secDocumentService.getSecDocumentListClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecDocumentListClass")
    public BaseResponse getSecDocumentListClass(@RequestBody SecDocumentRequest request,@RequestHeader String token) {
        log.info("getSecDocumentListClass");
        return secDocumentService.getSecDocumentListClass(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecDocumentList")
    public BaseResponse getSecDocumentList(@RequestBody SecDocumentRequest request) {
        log.info("getSecDocumentList");
        return secDocumentService.getSecDocumentList(request);
    }

    @TenantId
    @PostMapping("/getSecDocumentListNJCJ")
    public BaseResponse getSecDocumentListNJCJ(@RequestBody SecDocumentRequest request) {
        log.info("getSecDocumentListNJCJ");
        return secDocumentService.getSecDocumentListNJCJ(request);
    }

    @PostMapping("/updateSecDocumentById")
    public BaseResponse updateSecDocumentById(@RequestBody SecDocumentRequest request) {
        log.info("updateSecDocumentById");
        return secDocumentService.updateSecDocumentById(request);
    }

    @TenantId
    @PostMapping("/getSecDocumentListOne2Many")
    public BaseResponse getSecDocumentListOne2Many(@RequestBody SecDocumentRequest request) {
        log.info("getSecDocumentListOne2Many");
        return secDocumentService.getSecDocumentListOne2Many(request);
    }

    @TenantId
    @PostMapping("/getSecDocumentGroupByProject")
    public BaseResponse getSecDocumentGroupByProject(@RequestBody SecDocumentRequest request, @RequestHeader String token) {
        log.info("getSecDocumentGroupByProject");
        return secDocumentService.getSecDocumentGroupByProject(request, redisUtils.getUid(token));
    }

    @GetMapping("/getDocumentRate")
    public BaseResponse getDocumentRate(@RequestParam String secId) {
        log.info("getDocumentRate");
        return secDocumentService.getDocumentRate(secId);
    }
}
