package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContactListRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IConContactListService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/contactList")
public class ConContactListController {
    @Resource
    private IConContactListService conContactListService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改工程联系单
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateContactList")
    public BaseResponse createOrUpdateContactList(@RequestBody ConContactListRequest request) {
        log.info("createOrUpdateContactList");
        return conContactListService.createOrUpdateContactList(request);
    }

    /**
     * ID删除
     *
     * @param clId
     * @return
     */
    @GetMapping("/deleteContactLisByClId")
    public BaseResponse deleteContactLisByClId(@RequestParam String clId) {
        log.info("deleteContactLisByClId");
        return conContactListService.deleteContactLisByClId(clId);
    }


    @GetMapping("/getContactLisByClId")
    public BaseResponse getContactLisByClId(@RequestParam String clId) {
        log.info("getContactLisByClId");
        return conContactListService.getContactLisByClId(clId);
    }

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getConContactList")
    public BaseResponse getConContactList(@RequestBody ConContactListRequest request, @RequestHeader String token) {
        log.info("getConContactList");
        return conContactListService.getConContactList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConContactListNoAuth")
    public BaseResponse getConContactListNoAuth(@RequestBody ConContactListRequest request) {
        log.info("getConContactListNoAuth");
        return conContactListService.getConContactListNoAuth(request);
    }

    @TenantId
    @GetMapping("/processSync/{uuid}")
    public BaseResponse processSyn(@PathVariable("uuid") String uuid){
        conContactListService.processSyn(uuid);
        return BaseResponse.ok();
    }

}
