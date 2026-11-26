package com.jpmsoft.projectProducer.service.bim;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.entity.bim.ProjectBimTreeMap;
import com.jpmsoft.projectProducer.pojo.request.bim.RiskCategoryHiddenCountRequest;
import com.jpmsoft.projectProducer.pojo.vo.bim.*;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractVo;

import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/10 10:09
 * @Description:
 * @Version: 1.0
 */
public interface IProjectBimTreeMapService extends IService<ProjectBimTreeMap> {

    /**
     * 根据项目名称查询项目ids
     *
     * @param projectName
     * @return
     */
    List<String> listProjectIdsByName(String projectName);

    /**
     * 根据合同名称查询合同ids
     * @param contractName
     * @return
     */
    List<String> listContractIdsByName(String contractName);

    /**
     * 根据项目名称查询项目信息
      * @param projectName
     * @return
     */
    List<ProjectVO> listProjectsByName(String projectName);

    /**
     * 根据项目ids查询项目信息
     *
     * @param projectIds
     * @return
     */
    List<ProjectVO> listProjectsByProjectIds(List<String> projectIds);

    /**
     * 根据合同名称查询合同信息
     */
    List<ContractVo> listContractsByName(String contractName);


    /**
     * 根据合同id查询合同名称
     */
    List<ContractVo> listContractByContractIds(List<String> contractIds);

    /**
     * 按项目 ID、名称和细分类别查询项目信息。
     *
     * @param projectIds 项目 ID 列表
     * @param projectName 项目名称关键字
     * @param projectCategoryDetails 可选的细分类别关键字
     * @return 匹配的项目信息
     */
    List<ProjectBimInfoVO> listProjectsByProjectIdsAndProjectName(
            List<String> projectIds, String projectName, String projectCategoryDetails);

    PageResult<QsBimPageVO> pageQs(List<String> qsrIds,
                                   Integer pageNum,
                                   Integer pageSize);

    QsCountVO countQs(List<String> qsrIds);

    QsRecordBimResponse getQsByQsrId(String qsrId);

    List<QsRecordBimResponse> listQsByQsrIds(List<String> qsrIds);

    List<PlanTaskBimPageVO> listPlanTaskByTaskIds(List<String> taskIds);

    List<ConBoqDtlBimPageVO> listBoqDtlByDtlIds(List<String> boqDtlIds);

    PageResult<PlanTaskBimPageVO> pagePlanTask(List<String> taskIds,
                                         Integer pageNum,
                                         Integer pageSize);

    PageResult<PlanBimPageVO> pagePlan(List<String> planIds,
                                           Integer pageNum,
                                           Integer pageSize);

    List<PlanTaskBimPageVO> listPlanTaskByPlanId(String planId);


    PageResult<ConBoqBimPageVO> pageBoq(List<String> boqIds,
                                       Integer pageNum,
                                       Integer pageSize);

    PageResult<ConBoqDtlBimPageVO> pageBoqDtl(List<String> boqDtlIds,
                                              Integer pageNum,
                                              Integer pageSize);

    List<ConBoqDtlBimPageVO> listBoqDtlByBoqId(String boqId);

    Long countHiddenByRiskEvaluateIds(RiskCategoryHiddenCountRequest request);

    List<QsRecordCountVO> countQsRecord(RiskCategoryHiddenCountRequest request);


}
