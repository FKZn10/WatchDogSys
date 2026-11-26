package com.jpmsoft.projectProducer.controller.v1.design;



import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.projectProducer.service.design.IDesignResultsIssuedService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designresult")
public class DesignResultsIssuedController {

    @Resource
    private IDesignResultsIssuedService designResultsIssuedService;

    @Resource
    private RedisUtils redisUtils;


    @PostMapping("/createOrupdateDesignResultsIssued")
    public BaseResponse createOrupdateDesignResultsLssued(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token) {
        log.info("createOrupdateDesignResultsIssued");
        return designResultsIssuedService.createOrupdateDesignResultsIssued(request,token);
    }

    @PostMapping("/delDesignResultsIssuedById")
    public BaseResponse delDesignResultsIssuedById(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token) {
        log.info("delDesignResultsIssuedById");
        return designResultsIssuedService.delDesignResultsIssuedById(request,token);
    }
    /**
     * 获取列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getDesignResultsIssued")
    public BaseResponse getDesignResultsLssuedList(@RequestBody DesignResultsIssuedRequest request, @RequestHeader String token) {
        log.info("getDesignResultsIssued");
        return designResultsIssuedService.getDesignResultsIssuedList(request, redisUtils.getUid(token));
    }

    /**
     * 查询信息
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getDesignResultsIssuedClassInfo")
    public BaseResponse getDesignResultsLssuedClassInfo(@RequestBody DesignResultsIssuedRequest request, @RequestHeader String token) {
        log.info("getDesignResultsIssuedClassInfo");
        return designResultsIssuedService.getDesignResultsIssuedClassInfo(request, redisUtils.getUid(token));
    }

    @GetMapping("/getDesignResultsIssuedById")
    public BaseResponse getDesignResultsIssuedById(@RequestParam String id, @RequestHeader String token) {
        log.info("getDesignResultsIssuedById");
        return designResultsIssuedService.getDesignResultsIssuedById(id, redisUtils.getUid(token));
    }

    /**
     * 此方法被弃用
     *
     * @param dirType
     * @param token
     * @return
     */
    @GetMapping("/getDesignResultsIssuedTree")
    public BaseResponse getDesignResultsLssuedTree(@RequestParam String dirType, @RequestHeader String token) {
        log.info("getDesignResultsLssuedTree");
        return designResultsIssuedService.getDesignResultsLssuedTree(dirType, redisUtils.getUid(token));
    }


    /**
     * 根据补发表的id查对应模块表详情
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/getDesignIssuedInfoById")
    public BaseResponse getDesignIssuedInfoById(@RequestParam String id, @RequestHeader String token) {
        log.info("getDesignResultsIssuedModuleInfo");
        return designResultsIssuedService.getDesignIssuedInfoById(id, redisUtils.getUid(token));
    }



    @PostMapping("/getDownloadLogListForDesign")
    public BaseResponse getDownloadLogListForDesign(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token) {
        return designResultsIssuedService.getDownloadLogListForDesign(request,redisUtils.getUid(token));
    }
    //回写下发父级
    @PostMapping("/backWriteIssuedParent")
    public BaseResponse backWriteIssuedParent(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token) {
        return designResultsIssuedService.backWriteIssuedParent(request,redisUtils.getUid(token));
    }

}
