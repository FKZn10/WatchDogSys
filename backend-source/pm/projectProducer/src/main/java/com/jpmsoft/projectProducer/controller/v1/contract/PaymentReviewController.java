package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequestDto;
import com.jpmsoft.common.model.request.Contract.PaymentReviewRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractStampDutyModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractBudgetDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractFundStatisticsDTO;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IPaymentReviewService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/paymentReview")
public class PaymentReviewController {
    //付款请审
    @Resource
    private IPaymentReviewService paymentReviewService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改付款请审
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdatePaymentReview")
    public BaseResponse createOrUpdatePaymentReview(@RequestBody PaymentReviewRequest request) {
        log.info("createOrUpdatePaymentReview");
        return paymentReviewService.createOrUpdatePaymentReview(request);
    }


    /**
     * 获取付款请审列表
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getPaymentReviewList")
    public BaseResponse getPaymentReviewList(@RequestBody PaymentReviewRequest request) {
        log.info("getPaymentReviewList");
        return paymentReviewService.getPaymentReviewList(request);
    }

    /**
     * ID查询付款请审详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getPaymentReviewById")
    public BaseResponse getPaymentReviewById(@RequestParam String id) {
        log.info("getPaymentReviewById");
        return paymentReviewService.getPaymentReviewById(id);
    }

    /**
     * 删除付款请审
     *
     * @param id
     * @return
     */
    @GetMapping("/deletePaymentReviewById")
    public BaseResponse deletePaymentReviewById(@RequestParam String id) {
        log.info("deletePaymentReviewById");
        return paymentReviewService.deletePaymentReviewById(id);
    }
}
