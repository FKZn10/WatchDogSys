package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvEstimateDeclareDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvEstimateDeclareDtl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IInvEstimateDeclareDtlService extends IService<InvEstimateDeclareDtl> {

    void createOrUpdateInvEstimateDeclareDtl(List<InvEstimateDeclareDtlModel> datas, String edId);

    List<InvEstimateDeclareDtlModel> getInvEstimateDeclareDtlModelByEdId(String edId);


    /**
     * 单个清单ID获取其所有子项ID
     *
     * @param eddId
     * @return
     */
    List<String> getAllChildIdByEddId(String eddId);

    List<String> getAllChildIdByPId(String pId);

    Map<String, List<InvEstimateDeclareDtlModel>> getInvEstimateDeclareDtlModelByEdIds(List<String> pIds, List<String> edIds);

    void deleteAllByEdId(String edId);

    /**
     * 项目ID查询审批结束匡算申报清单树
     *
     * @param pId
     * @return
     */
    BaseResponse getInvEstimateDeclareDtlTreeByPid(String pId);


    /**
     * 项目ID查询审批结束的概算申报清单树
     *
     * @param pId
     * @return
     */
    List<InvEstimateDeclareDtlModel> getInvEstimateDeclareDtlTreeListByPid(String pId);

    /**
     * 项目ID查询审批结束的概算申报清单列表
     *
     * @param pId
     * @return
     */
    BaseResponse getInvEstimateDeclareDtlListByPid(String pId);

    BigDecimal getInvEstimateDeclareDtlAmountByPid(String pId);
}
