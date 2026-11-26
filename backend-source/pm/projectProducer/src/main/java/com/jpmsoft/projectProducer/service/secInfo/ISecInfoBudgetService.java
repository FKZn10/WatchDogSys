package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetConSecModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoBudgetModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudget;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;

import java.util.List;

public interface ISecInfoBudgetService extends IService<SecInfoBudget> {

    void createOrUpdateSecInfoBudget(List<SecInfoBudgetModel> datas, String secId);

    BaseResponse getSecInfoBudgetList(String sibCost,String teId);

    List<SecInfoBudgetModel> getSecInfoBudgetListBySecId(String secId);


    List<InvBudgetConSecModel> getSecInfoBudgetById(String sibCost);

    List<SecInfoBudgetDeclareDtl> getSecInfoBudgetListByPId(String pId);



}
