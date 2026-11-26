package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRelatedRelationshipsModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IInvBudgetDeclareDtlService extends IService<InvBudgetDeclareDtl> {

    void createOrUpdateInvBudgetDeclareDtl(List<InvBudgetDeclareDtlModel> datas, String bdId);

    void createOrUpdateInvRelatedRelationships(List<InvRelatedRelationshipsModel> datas, String bdId);

    List<InvBudgetDeclareDtlModel> getInvBudgetDeclareDtlModelByBdId(String bdId,String pId);

    /**
     * 概算树结构
     *
     * @param pId
     * @return BudgetConPayVo
     */
    BaseResponse getInvBudgetConPayInfo(String pId);

    /**
     * 单个清单ID获取其所有子项ID
     *
     * @param bddId
     * @return
     */
    List<String> getAllChildIdByBddId(String bddId);

    List<String> getAllChildIdByPId(String pId);

    Map<String, List<InvBudgetDeclareDtlModel>> getInvBudgetDeclareDtlModelByBdIds(List<String> pIds, List<String> bdIds);

    void deleteAllByBdId(String bdId);

    void deleteAllByBdId2(String bdId);

    /**
     * 项目ID查询审批结束的概算申报清单树
     *
     * @param pId
     * @return
     */
    BaseResponse getInvBudgetDeclareDtlTreeByPid(String pId);


    /**
     * 项目ID查询审批结束的概算申报清单树
     *
     * @param pId
     * @return
     */
    List<InvBudgetDeclareDtlModel> getInvBudgetDeclareDtlTreeListByPid(String pId,String type);


    List<InvBudgetDeclareDtlModel> getInvBudgetDeclareDtlDetailsListByPid(String pId,String type);


    /**
     * 项目ID查询审批结束的概算申报清单列表
     *
     * @param pId
     * @return
     */
    BaseResponse getInvBudgetDeclareDtlListByPid(String pId);

    BigDecimal getInvBudgetDeclareDtlAmountByPid(String pId);


    List<InvBudgetDeclareDtl> getInvBudgetDeclareDtlByPid(String pId);

    //Rough：匡算 、Estimate：估算 、Budget：概算
    List<InvBudgetDeclareDtl> getInvTypeBudgetDeclareDtlByPid(String pId);


    List<InvBudgetDeclareDtl> getInvTypeBudgetDeclareDtlByBdIdList(List<String> bdIdList);

}
