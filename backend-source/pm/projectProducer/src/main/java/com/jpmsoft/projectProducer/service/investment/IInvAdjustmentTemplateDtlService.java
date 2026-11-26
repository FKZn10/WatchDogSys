package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentTemplateDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRelatedRelationshipsModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplateDtl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IInvAdjustmentTemplateDtlService extends IService<InvAdjustmentTemplateDtl> {

    void createOrUpdateInvAdjustmentTemplateDtl(List<InvAdjustmentTemplateDtlModel> datas, String atId);
    /**
     * 单个清单ID获取其所有子项ID
     *
     * @param atdId
     * @return
     */
    List<String> getAllChildIdByAtdId(String atdId);


    void deleteAllByAtId(String atId);




    List<InvAdjustmentTemplateDtlModel> getInvAdjustmentTemplateDtlModelByAtId(String atId);
}
