package com.jpmsoft.projectProducer.mapper.project;

import com.jpmsoft.projectProducer.pojo.vo.project.ContractChangeSummaryVO.ChangeRow;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectContractCostControlVO.ContractRow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 施工合同造价控制表查询。
 */
public interface ProjectContractCostControlMapper {
    /**
     * 批量查询项目全部已审批合同及按状态聚合的变更金额。
     *
     * @param pId 项目 ID
     * @return 一合同一行，变更先聚合后关联，避免重复累计合同金额
     */
    List<ContractRow> selectContractRows(@Param("pId") String pId);

    /**
     * 按合同和审批分类查询变更明细，不增加合同审批状态条件。
     *
     * @param conId    合同 ID
     * @param approved true 查询状态7，false查询非7、3、0且非NULL的状态
     * @return 按状态时间、创建时间及 ID 倒序排列的变更明细
     */
    List<ChangeRow> selectChangeRows(@Param("conId") String conId, @Param("approved") boolean approved);
}
