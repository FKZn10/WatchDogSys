package com.jpmsoft.projectConsumer.controller.v1.xz;

import com.jpmsoft.common.model.request.xz.FundApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApplicationQueryRequest;
import com.jpmsoft.common.model.request.xz.FundApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApplicationVO;
import com.jpmsoft.common.model.response.xz.FundApplicationVO;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import com.jpmsoft.projectConsumer.feign.FundApplicationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:56
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/xz/fundApplication")
public class FundApplicationController {

    @Autowired
    private FundApplicationFeign fundApplicationFeign;

    @PostMapping("/page")
    public CommonResponse<PageResult<FundApplicationVO>> page(@RequestBody FundApplicationQueryRequest request){
        return fundApplicationFeign.page(request);
    }

    @PostMapping("/create")
    public CommonResponse<String> create(@RequestBody FundApplicationCreateRequest request){
        return fundApplicationFeign.create(request);
    }

    @PostMapping("/update")
    public CommonResponse<String> update(@RequestBody FundApplicationCreateRequest request){
        return fundApplicationFeign.update(request);
    }

    @PostMapping("/detail")
    public CommonResponse<FundApplicationVO> detail(@RequestBody FundApplicationCreateRequest request){
        return fundApplicationFeign.detail(request);
    }

    @PostMapping("/syncApproveState")
    public CommonResponse<Void> syncApproveState(@RequestBody FundApplicationCreateRequest request) {
        fundApplicationFeign.syncApproveState(request);
        return CommonResponse.ok();
    }

    @PostMapping("/remove")
    public CommonResponse<Void> remove(@RequestBody FundApplicationCreateRequest request) {
        fundApplicationFeign.remove(request);
        return CommonResponse.ok();
    }
}
