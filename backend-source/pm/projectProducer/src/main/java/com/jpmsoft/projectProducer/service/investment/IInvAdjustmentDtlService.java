package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentDtl;

import java.util.List;

public interface IInvAdjustmentDtlService extends IService<InvAdjustmentDtl> {

    void createOrUpdateInvAdjustmentDtl(List<InvAdjustmentDtlModel> datas, String iaId);
    /**
     * 单个清单ID获取其所有子项ID
     *
     * @param adId
     * @return
     */
    List<String> getAllChildIdByAdId(String adId);


    void deleteAllByIaId(String iaId);




    List<InvAdjustmentDtlModel> getInvAdjustmentDtlModelByIaId(String iaId);
}
