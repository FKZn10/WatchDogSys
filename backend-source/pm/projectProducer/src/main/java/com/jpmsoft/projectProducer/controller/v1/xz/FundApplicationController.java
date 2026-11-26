package com.jpmsoft.projectProducer.controller.v1.xz;

import com.jpmsoft.common.model.request.xz.FundApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApplicationVO;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.service.xz.FundApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**v1/xz/fundApplication/page
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:56
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/xz/fundApplication")
public class FundApplicationController {

    @Autowired
    private FundApplicationService fundApplicationService;

    @PostMapping("/page")
    public CommonResponse<PageResult<FundApplicationVO>> page(@RequestBody FundApplicationQueryRequest request){
        return CommonResponse.ok(fundApplicationService.page(request));
    }

    @PostMapping("/create")
    public CommonResponse<String> create(@RequestBody FundApplicationCreateRequest request){
        return CommonResponse.ok(fundApplicationService.create(request));
    }

    @PostMapping("/update")
    public CommonResponse<String> update(@RequestBody FundApplicationCreateRequest request){
        return CommonResponse.ok(fundApplicationService.update(request));
    }

    @PostMapping("/detail")
    public CommonResponse<FundApplicationVO> detail(@RequestBody FundApplicationCreateRequest request){
        return CommonResponse.ok(fundApplicationService.detail(request));
    }

    @PostMapping("/syncApproveState")
    public CommonResponse<Void> syncApproveState(@RequestBody FundApplicationCreateRequest request) {
        fundApplicationService.syncApproveState(request);
        return CommonResponse.ok();
    }

    @PostMapping("/remove")
    public CommonResponse<Void> remove(@RequestBody FundApplicationCreateRequest request) {
        fundApplicationService.remove(request);
        return CommonResponse.ok();
    }
}
