package com.jpmsoft.projectProducer.controller.v1.xz;

import com.jpmsoft.common.model.request.xz.FundApprovalCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApprovalVO;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.service.xz.FundApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**v1/xz/FundApproval/page
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:56
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/xz/fundApproval")
public class FundApprovalontroller {

    @Autowired
    private FundApprovalService fundApprovalService;

    @PostMapping("/page")
    public CommonResponse<PageResult<FundApprovalVO>> page(@RequestBody FundApprovalQueryRequest request){
        return CommonResponse.ok(fundApprovalService.page(request));
    }

    @PostMapping("/create")
    public CommonResponse<String> create(@RequestBody FundApprovalCreateRequest request){
        return CommonResponse.ok(fundApprovalService.create(request));
    }

    @PostMapping("/update")
    public CommonResponse<String> update(@RequestBody FundApprovalCreateRequest request){
        return CommonResponse.ok(fundApprovalService.update(request));
    }

    @PostMapping("/detail")
    public CommonResponse<FundApprovalVO> detail(@RequestBody FundApprovalCreateRequest request){
        return CommonResponse.ok(fundApprovalService.detail(request));
    }

    @PostMapping("/syncApproveState")
    public CommonResponse<Void> syncApproveState(@RequestBody FundApprovalCreateRequest request) {
        fundApprovalService.syncApproveState(request);
        return CommonResponse.ok();
    }

    @PostMapping("/remove")
    public CommonResponse<Void> remove(@RequestBody FundApprovalCreateRequest request) {
        fundApprovalService.remove(request);
        return CommonResponse.ok();
    }
}
