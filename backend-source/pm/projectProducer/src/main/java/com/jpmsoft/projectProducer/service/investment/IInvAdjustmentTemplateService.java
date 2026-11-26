package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentTemplateRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

public interface IInvAdjustmentTemplateService extends IService<InvAdjustmentTemplate> {

    BaseResponse createOrUpdateInvAdjustmentTemplate(InvAdjustmentTemplateRequest request);

    BaseResponse getInvAdjustmentTemplateList(InvAdjustmentTemplateRequest request, String uId);

    BaseResponse getInvAdjustmentTemplateByAtId(String atId);

    BaseResponse deleteInvAdjustmentTemplateByAtId(String atId);
}
