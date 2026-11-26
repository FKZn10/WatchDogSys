package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Design.DesignInitialBudgetDtlRequest;
import com.jpmsoft.common.model.response.Design.DesignInitialBudgetDtlResponse;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignInitialBudgetDtl;

import java.util.List;

public interface IDesignInitialBudgetDtlService extends IService<DesignInitialBudgetDtl> {


    void createOrUpdateDesignInitialBudgetDtl(List<DesignInitialBudgetDtlRequest> diBudgetDtls, String  diId);

    /**
     * 获取初步设计概算明细
     * @param diId
     * @return
     */
    List<DesignInitialBudgetDtlResponse> getDesignInitialBudgetDtlListByDiId(String diId);


    void deleteDesignInitialBudgetDtlByDiId(String diId);
}
