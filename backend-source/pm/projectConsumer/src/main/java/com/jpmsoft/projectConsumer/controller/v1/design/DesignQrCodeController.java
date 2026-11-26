package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignQrCodeRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignQrCodeModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/designQrCode")
public class DesignQrCodeController {


    @Autowired
    private BaseFeign baseFeign;


    /**
     * 创建或更新
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "创建或更新二维码信息")
    @PostMapping("/createOrupdateDesignQrCode")
    public BaseResponse createOrupdateDesignQrCode(@RequestBody DesignQrCodeModel request, @RequestHeader String token) {
        log.info("createOrupdateDesignQrCode");
        return baseFeign.createOrupdateDesignQrCode(request, token);
    }

    /**
     * 获取信息
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation(value = "根据id获取二维码信息")

    @GetMapping("/getDesignQrCodeById")
    public BaseResponse getDesignQrCodeById(@RequestParam String id, @RequestHeader String token) {
        log.info("getDesignQrCodeById");
        return baseFeign.getDesignQrCodeById(id, token);
    }

    /**
     * 删除信息
     * @param id
     * @param token
     * @return
     */
    @ApiOperation(value = "根据id删除二维码信息")
    @GetMapping("/getDeleteDesignQrCodeById")
    public BaseResponse deleteDesignQrCodeById(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteDesignQrCodeById");
        return baseFeign.getDeleteDesignQrCodeById(id, token);
    }

    /**
     * 转化二维码的使用状态
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "转化二维码的使用状态")
    @PostMapping("/checkDqcuserd")
    public BaseResponse checkDqcuserd(@RequestBody DesignQrCodeModel request, @RequestHeader String token) {
        log.info("checkDqcuserd");
        return baseFeign.checkDqcuserd(request, token);
    }


    /**
     * 获取列表，多条件筛选
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "获取列表，多条件筛选")
    @PostMapping("/getDesignQrCodeList")
    public BaseResponse getDesignQrCodeList(@RequestBody DesignQrCodeRequest request, @RequestHeader String token) {
        log.info("getDesignQrCodeList");
        return baseFeign.getDesignQrCodeList(request, token);
    }

    /**
     * 查询信息，多条件筛选
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "查询信息，多条件筛选")
    @PostMapping("/getDesignQrCodeClassInfo")
    public BaseResponse getDesignQrCodeClassInfo(@RequestBody DesignQrCodeRequest request, @RequestHeader String token) {
        log.info("getDesignQrCodeClassInfo");
        return baseFeign.getDesignQrCodeClassInfo(request, token);
    }

    /**
     * 获取设计成果表中关联的二维码信息
     * @param id
     * @param token
     * @return
     */
    @ApiOperation(value = "获取设计成果表中关联的二维码信息")
    @GetMapping("/getDesignQrCodeDateById")
    public BaseResponse getDesignQrCodeDateById(@RequestParam String id, @RequestHeader String token) {
        log.info("getDesignQrCodeDateById");
        return baseFeign.getDesignQrCodeDateById(id, token);
    }
}
