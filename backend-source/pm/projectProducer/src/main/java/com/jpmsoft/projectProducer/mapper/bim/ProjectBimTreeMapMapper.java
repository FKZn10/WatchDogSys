package com.jpmsoft.projectProducer.mapper.bim;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.bim.*;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractDto;
import com.jpmsoft.projectProducer.pojo.entity.bim.ProjectBimTreeMap;
import com.jpmsoft.projectProducer.pojo.vo.bim.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/10 10:08
 * @Description:
 * @Version: 1.0
 */
public interface ProjectBimTreeMapMapper extends BaseMapper<ProjectBimTreeMap> {

    List<String> queryIdsByProject(@Param("projectId") String projectId, @Param("projectName") String projectName);


    List<ProjectBimTreeDto> queryProjectsByTreeIds(@Param("treeIds") List<String> treeIds);

    List<ProjectBimInfoDto> queryListProjectForBim(@Param("page") IPage<ProjectBimInfoDto> page, @Param("projectName") String projectName);

    List<String> queryProjectIdsByProjectName(@Param("projectName") String projectName);

    List<String> queryContractIdsByContractName(@Param("contractName") String contractName);

    List<ProjectDto> queryProjectsByProjectIds(@Param("projectIds") List<String> projectIds);

    List<ProjectDto> queryProjectsByProjectName(@Param("projectName") String projectName);

    List<ContractDto> queryContractsByContractName(@Param("contractName") String contractName);

    List<ContractDto> queryContractByConContractIds(@Param("contractIds") List<String> contractIds);

    /**
     * 查询指定项目，按名称和细分类别进行包含匹配。
     *
     * @param projectIds             项目 ID 列表
     * @param projectName            项目名称关键字
     * @param projectCategoryDetails 可选的细分类别关键字
     * @return 匹配的项目信息
     */
    List<ProjectBimInfoVO> queryProjectsByProjectIdsAndProjectName(
            @Param("projectIds") List<String> projectIds,
            @Param("projectName") String projectName,
            @Param("projectCategoryDetails") String projectCategoryDetails);

    List<QsBimPageDto> queryPageQs(@Param("page") IPage<QsBimPageDto> page, @Param("qsrIds") List<String> qsrIds);

    Long queryCountQsForResolved(@Param("qsrIds") List<String> qsrIds);

    Long queryCountQsForUnResolved(@Param("qsrIds") List<String> qsrIds);

    QsRecordBimResponse queryQsRecordByQsrId(@Param("qsrId") String qsrId);

    List<QsRecordBimResponse> queryListQsRecordByQsrIds(@Param("qsrIds") List<String> qsrIds);

    List<PlanTaskBimPageVO> queryListPlanTaskByTaskIds(@Param("taskIds") List<String> taskIds);

    List<ConBoqDtlBimPageVO> queryListBoqDtlByDtlIds(@Param("boqDtlIds") List<String> boqDtlIds);

    List<PlanTaskBimPageDto> queryPagePlanTask(@Param("page") IPage<PlanTaskBimPageDto> page, @Param("taskIds") List<String> taskIds);

    List<PlanTaskBimPageDto> queryListPlanTaskByPlanId(@Param("planId") String planId);

    List<ConBoqDtlBimPageDto> queryListBoqDtlByBoqId(@Param("boqId") String boqId);

    Long queryCountHiddenByRiskEvaluateIds(@Param("riskEvaluateIds") List<String> riskEvaluateIds,
                                           @Param("startTime") String startTime,
                                           @Param("endTime") String endTime,
                                           @Param("rectifyStatus") String rectifyStatus);

    List<QsRecordCountVO> queryCountQsRecord(@Param("projectId") String projectId,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime);

    List<PlanBimPageDto> queryPagePlan(@Param("page") IPage<PlanBimPageDto> page, @Param("taskIds") List<String> taskIds);


    List<ConBoqBimPageDto> queryPageBoq(@Param("page") IPage<ConBoqBimPageDto> page, @Param("boqIds") List<String> boqIds);

    List<ConBoqDtlBimPageDto> queryPageBoqDtl(@Param("page") IPage<ConBoqDtlBimPageDto> page, @Param("boqDtlIds") List<String> boqDtlIds);


}
