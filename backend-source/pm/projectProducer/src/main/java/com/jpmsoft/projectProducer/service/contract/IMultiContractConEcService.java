package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Contract.MultiContractConEcRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.MultiContractConEc;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractConEcHistoryVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.MultiContractConEcDetailVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.MultiContractConEcPageVO;

import java.math.BigDecimal;
import java.util.List;

public interface IMultiContractConEcService extends IService<MultiContractConEc> {

    Page<MultiContractConEcPageVO> page(Integer pageNum, Integer pageSize, String projectName,String pId, String ecChangeType,String uId);

    MultiContractConEcDetailVO detail(String uuid, String userId);

    BigDecimal getProjectTotalConEcMoney(String projectId, String ecChangeType, String contractId);

    /**
     * 获取当前用户可见项目下的合同已审批变更、签证历史，不包含指定的当前子表记录。
     *
     * @param projectId   项目ID
     * @param contractId  合同ID
     * @param excludeEcId 排除的当前子表记录ID，可为空
     * @param userId      当前登录用户ID
     * @return 按主流程审批时间降序排列的历史金额、余额明细
     */
    List<ContractConEcHistoryVO> getContractChangeHistory(
            String projectId, String contractId, String excludeEcId, String userId);

    /**
     * 根据项目ID汇总审批结束的设计变更申报金额。
     *
     * @param projectId 项目ID
     * @param userId    当前用户ID
     * @return 申报金额合计
     */
    BigDecimal getFinishedDesignChangeApplyMoneyByProjectId(String projectId, String userId);

    MultiContractConEc createOrUpdate(MultiContractConEcRequest multiContractConEcRequest);

    void processSyn(String uuid);

    Integer syncChildState(String id, String type);
}
