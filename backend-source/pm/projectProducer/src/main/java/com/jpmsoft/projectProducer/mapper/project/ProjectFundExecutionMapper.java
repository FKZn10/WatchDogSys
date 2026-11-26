package com.jpmsoft.projectProducer.mapper.project;

import com.jpmsoft.projectProducer.pojo.vo.project.ProjectFundExecutionVO.ContractDetail;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectFundExecutionVO.SubjectPayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目资金执行明细的批量查询，避免逐科目查询合同及支付。
 */
public interface ProjectFundExecutionMapper {
    /**
     * 查询选定概算的科目，按概算排序号及主键稳定排序。
     *
     * @param bdId 概算申报 ID
     * @return 概算科目原始数据
     */
    List<ContractDetail> selectSubjects(@Param("bdId") String bdId);

    /**
     * 按当前项目及选定概算科目汇总合同金额，不额外筛选合同审批状态。
     *
     * @param pId  项目 ID
     * @param bdId 概算申报 ID
     * @return 科目直接关联合同金额，不包含下级金额
     */
    List<ContractDetail> selectContractAmounts(@Param("pId") String pId, @Param("bdId") String bdId);

    /**
     * 汇总流程结束、资金来源为 02 的自筹支付，以申请日期提取年份。
     *
     * @param pId  项目 ID
     * @param bdId 概算申报 ID
     * @return 按概算科目与实际年份聚合的 Pay_Payable；空日期归入空年份
     */
    List<SubjectPayment> selectSelfPaidAmounts(@Param("pId") String pId, @Param("bdId") String bdId);

    /**
     * 汇总流程结束、资金来源为 01 或为空的国拨支付，以申请日期提取年份。
     *
     * @param pId  项目 ID
     * @param bdId 概算申报 ID
     * @return 按概算科目与实际年份聚合的 Pay_Payable；空日期归入空年份
     */
    List<SubjectPayment> selectGovernmentPaidAmounts(@Param("pId") String pId, @Param("bdId") String bdId);

    /**
     * 通过本项目关联合同的标段，汇总各概算科目的招标控制价。
     * 同科目相同标段先去重；每个标段取 D_Create_Time 最新的一份招标文件，
     * 创建时间相同时以 D_Id 降序确定，不额外筛选审批状态。
     *
     * @param pId  项目 ID
     * @param bdId 概算申报 ID
     * @return 科目直接关联标段的 D_Amount 合计，缺失文件或金额为空时计 0
     */
    List<ContractDetail> selectTenderControlAmounts(@Param("pId") String pId, @Param("bdId") String bdId);
}
