package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.Contract.PaymentReviewRequest;
import com.jpmsoft.common.model.request.SecInfo.SecContractSignPleaseReviewRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractStampDutyModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.Contract.PaymentReviewResponse;
import com.jpmsoft.common.model.response.SecInfo.SecContractSignPleaseReviewResponse;
import com.jpmsoft.common.model.response.SecInfo.SecReviewMaterialResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.PaymentReview;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.MpcFunds;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface PaymentReviewMapper extends BaseMapper<PaymentReview> {

    List<PaymentReviewResponse> getPaymentReviewList(@Param("params") Map<String, Object> params, @Param("page") IPage<PaymentReviewRequest> page);

    PaymentReviewResponse getPaymentReviewById(@Param("id") String id);
}
