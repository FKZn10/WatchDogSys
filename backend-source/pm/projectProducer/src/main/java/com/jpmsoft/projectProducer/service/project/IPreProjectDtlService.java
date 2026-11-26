package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegisterDtl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IPreProjectDtlService extends IService<PreProjectDtl> {

    void createOrUpdatePreProjectDtl(List<PreProjectDtlModel> datas, String prId);

    void deletePreProjectDtlByPrId(String prId);

    List<PreProjectDtlModel> getPreProjectDtlByPrId(String prId,String pId);

    Map<String, List<PreProjectDtlModel>> getPreProjectDtlByIds(List<String> pId);


    List<PreProjectDtlModel> getPreProjectDtlByPId(String pId);

    BigDecimal getPreProjectDtlAmountByPid(String pId);

}
