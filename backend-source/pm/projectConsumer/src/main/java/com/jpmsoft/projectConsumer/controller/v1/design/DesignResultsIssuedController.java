package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/designresult")
public class DesignResultsIssuedController {

    @Autowired
    private BaseFeign baseFeign;

    /**
     * 创建或更新
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "创建或更新设计表信息")
    @PostMapping("/createOrupdateDesignResultsIssued")
    public BaseResponse createOrupdateDesignResultsIssued(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token) {
        log.info("createOrupdateDesignResultsIssued");
        return baseFeign.createOrupdateDesignResultsIssued(request,token);
    }

    /**
     * 获取列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "获取列表，多条件筛选")
    @PostMapping("/getDesignResultsIssued")
    public BaseResponse getDesignResultsIssued(@RequestBody DesignResultsIssuedRequest request, @RequestHeader String token) {
        log.info("getDesignResultsIssued");
        return baseFeign.getDesignResultsIssuedList(request, token);
    }

    /**
     * 查询信息
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "查询信息，多条件筛选")
    @PostMapping("/getDesignResultsIssuedClassInfo")
    public BaseResponse getDesignResultsIssuedClassInfo(@RequestBody DesignResultsIssuedRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneClassInfo");
        return baseFeign.getDesignResultsIssuedClassInfo(request, token);
    }

    /**
     * 根据id查询信息
     * @param id
     * @param token
     * @return
     */
    @ApiOperation(value = "根据id查询信息")
    @GetMapping("/getDesignResultsIssuedById")
    public BaseResponse getDesignResultsIssuedById(@RequestParam String id, @RequestHeader String token) {
        log.info("getDesignResultsIssuedById");
        return baseFeign.getDesignResultsIssuedById(id, token);
    }

    /**
     * 统计台账 树形结构
     * 此方法已弃用
     */
    @GetMapping("/getDesignResultsIssuedTree")
    public BaseResponse getDesignResultsIssuedTree(@RequestParam String dirType, @RequestHeader String token) {
        log.info("getDesignResultsLssuedTree");
        return baseFeign.getDesignResultsLssuedTree(dirType, token);
    }

    /**
     * 删除
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "删除设计表")
    @PostMapping("/delDesignResultsIssuedById")
    public BaseResponse delDesignResultsIssuedById(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token) {
        log.info("delDesignResultsIssuedById");
        return baseFeign.delDesignResultsIssuedById(request, token);
    }


    /**
     * 根据补发表id查询模块表详情
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation(value = "根据补发表id查询模块表详情")
    @GetMapping("/getDesignIssuedInfoById")
    public BaseResponse getDesignIssuedInfoById(@RequestParam String id, @RequestHeader String token) {
        log.info("getDesignIssuedInfoById");
        return baseFeign.getDesignIssuedInfoById(id, token);
    }

    @PostMapping("/getDownloadLogListForDesign")
    public BaseResponse getDownloadLogListForDesign(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token) {
        log.info("getDownloadLogListForDesign");
        return baseFeign.getDownloadLogListForDesign(request, token);
    }


    @PostMapping("/backWriteIssuedParent")
    public BaseResponse backWriteIssuedParent(@RequestBody DesignResultsIssuedModel request, @RequestHeader String token) {
        return baseFeign.backWriteIssuedParent(request,token);
    }

}
