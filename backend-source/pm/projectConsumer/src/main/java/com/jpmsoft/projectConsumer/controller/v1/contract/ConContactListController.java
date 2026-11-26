package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContactListRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "工程联系单", value = "工程联系单")
@RequestMapping("/v1/contactList")
public class ConContactListController {
    @Autowired
    private ConFeign conFeign;

    /**
     * 新增或修改工程联系单
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或修改工程联系单")
    @PostMapping("/createOrUpdateContactList")
    public BaseResponse createOrUpdateContactList(@RequestBody ConContactListRequest request) {
        log.info("createOrUpdateContactList");
        return conFeign.createOrUpdateContactList(request);
    }

    /**
     * ID删除
     *
     * @param clId
     * @return
     */
    @ApiOperation(value = "ID删除工程联系单")
    @GetMapping("/deleteContactLisByClId")
    public BaseResponse deleteContactLisByClId(@RequestParam String clId) {
        log.info("deleteContactLisByClId");
        return conFeign.deleteContactLisByClId(clId);
    }

    @ApiOperation(value = "ID查询工程联系单")
    @GetMapping("/getContactLisByClId")
    public BaseResponse getContactLisByClId(@RequestParam String clId) {
        log.info("getContactLisByClId");
        return conFeign.getContactLisByClId(clId);
    }

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "工程联系单表分页查询")
    @PostMapping("/getConContactList")
    public BaseResponse getConContactList(@RequestBody ConContactListRequest request, @RequestHeader String token) {
        log.info("getConContactList");
        return conFeign.getConContactList(request, token);
    }

    @ApiOperation(value = "工程联系单表分页查询-不鉴权")
    @PostMapping("/getConContactListNoAuth")
    public BaseResponse getConContactListNoAuth(@RequestBody ConContactListRequest request) {
        log.info("getConContactListNoAuth");
        return conFeign.getConContactListNoAuth(request);
    }

    @GetMapping("/processSync/{uuid}")
    public BaseResponse processSyn(@PathVariable("uuid") String uuid){
        return conFeign.processSynContactList(uuid);
    }
}
