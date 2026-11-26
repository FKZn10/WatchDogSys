package com.jpmsoft.jputils.controller;

import com.alibaba.fastjson.JSON;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.jputils.feign.SysConfigFeign;
import com.jpmsoft.jputils.request.InvoiceInfoRequest;
import com.jpmsoft.jputils.service.InvoiceUtilService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author yb.Sun
 * @date 2025/1/17 09:21
 */
@RestController
@RequestMapping("/v1/Invoice")
public class InvoiceController {
    @Autowired
    private InvoiceUtilService invoiceUtilService;
    @Autowired
    private SysConfigFeign sysConfigFeign;
    @ApiOperation(value = "获取发票信息")
    @PostMapping("/getInvoiceInfo")
    public BaseResponse getInvoiceInfo(@RequestBody MultipartFile file) {

        // 通过token从redis获取用户信息，并调用服务层方法处理上传的排名文件
        String code="invoiceAppCode";
        String appCode="";
        BaseResponse baseResponse = sysConfigFeign.GetConfigValuesByCodes(code);
        //Map<String,Object> postMap = new HashMap<>(16);
        if(baseResponse.getSuccess()) {
            String jsonStr = JSON.toJSONString(baseResponse.getData());
            Map map = JacksonUtils.parseObject(jsonStr,Map.class);
            if (map != null) {
                appCode = (String) map.get(code);
            }
        }
        return invoiceUtilService.getInvoiceInfo(file,appCode,"1");
    }

    @ApiOperation(value = "获取发票信息")
    @PostMapping("/checkInvoiceInfo")
    public BaseResponse checkInvoiceInfo(@RequestBody InvoiceInfoRequest request) {

        // 通过token从redis获取用户信息，并调用服务层方法处理上传的排名文件
        String code="invoiceAppCode";
        String appCode="";
        BaseResponse baseResponse = sysConfigFeign.GetConfigValuesByCodes(code);
        //Map<String,Object> postMap = new HashMap<>(16);
        if(baseResponse.getSuccess()) {
            String jsonStr = JSON.toJSONString(baseResponse.getData());
            Map map = JacksonUtils.parseObject(jsonStr,Map.class);
            if (map != null) {
                appCode = (String) map.get(code);
            }
        }
        return invoiceUtilService.checkInvoiceInfo(request,appCode);
    }
}
