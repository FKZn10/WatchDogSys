package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRoughDeclareDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRoughDeclareDtl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IInvRoughDeclareDtlService extends IService<InvRoughDeclareDtl> {

    void createOrUpdateInvRoughDeclareDtl(List<InvRoughDeclareDtlModel> datas, String bdId);

    List<InvRoughDeclareDtlModel> getInvRoughDeclareDtlModelByRdId(String bdId);


    /**
     * 单个清单ID获取其所有子项ID
     *
     * @param rddId
     * @return
     */
    List<String> getAllChildIdByRddId(String rddId);

    List<String> getAllChildIdByPId(String pId);

    Map<String, List<InvRoughDeclareDtlModel>> getInvRoughDeclareDtlModelByRdIds(List<String> pIds, List<String> rdIds);

    void deleteAllByRdId(String rdId);

    /**
     * 项目ID查询审批结束匡算申报清单树
     *
     * @param pId
     * @return
     */
    BaseResponse getInvRoughDeclareDtlTreeByPid(String pId);


    /**
     * 项目ID查询审批结束的概算申报清单树
     *
     * @param pId
     * @return
     */
    List<InvRoughDeclareDtlModel> getInvRoughDeclareDtlTreeListByPid(String pId);

    /**
     * 项目ID查询审批结束的概算申报清单列表
     *
     * @param pId
     * @return
     */
    BaseResponse getInvRoughDeclareDtlListByPid(String pId);

    BigDecimal getInvRoughDeclareDtlAmountByPid(String pId);
}
