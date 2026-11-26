package com.jpmsoft.projectConsumer.controller.v1.xz;

import com.jpmsoft.common.model.request.xz.FundApprovalCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApprovalVO;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import com.jpmsoft.projectConsumer.feign.FundApprovalFeign;
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
@RequestMapping("/v1/xz/fundApproval")
public class FundApprovalController {

    @Autowired
    private FundApprovalFeign fundApprovalFeign;

    @PostMapping("/page")
    public CommonResponse<PageResult<FundApprovalVO>> page(@RequestBody FundApprovalQueryRequest request){
        return fundApprovalFeign.page(request);
    }

    @PostMapping("/create")
    public CommonResponse<String> create(@RequestBody FundApprovalCreateRequest request){
        return fundApprovalFeign.create(request);
    }

    @PostMapping("/update")
    public CommonResponse<String> update(@RequestBody FundApprovalCreateRequest request){
        return fundApprovalFeign.update(request);
    }

    @PostMapping("/detail")
    public CommonResponse<FundApprovalVO> detail(@RequestBody FundApprovalCreateRequest request){
        return fundApprovalFeign.detail(request);
    }

    @PostMapping("/syncApproveState")
    public CommonResponse<Void> syncApproveState(@RequestBody FundApprovalCreateRequest request) {
        fundApprovalFeign.syncApproveState(request);
        return CommonResponse.ok();
    }

    @PostMapping("/remove")
    public CommonResponse<Void> remove(@RequestBody FundApprovalCreateRequest request) {
        fundApprovalFeign.remove(request);
        return CommonResponse.ok();
    }
}
