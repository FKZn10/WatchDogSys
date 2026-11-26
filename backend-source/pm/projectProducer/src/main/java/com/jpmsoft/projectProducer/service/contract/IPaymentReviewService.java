package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
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
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.PaymentReview;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

public interface IPaymentReviewService extends IService<PaymentReview> {

    /**
     * 新增或修改付款请审
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdatePaymentReview(PaymentReviewRequest request);

    /**
     * 获取付款请审列表
     *
     * @param request
     * @return
     */
    BaseResponse getPaymentReviewList(PaymentReviewRequest request);

    /**
     * ID查询付款请审详情
     *
     * @param id
     * @return
     */
    BaseResponse getPaymentReviewById(String id);

    BaseResponse deletePaymentReviewById(String id);
}
