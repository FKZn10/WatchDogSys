package com.jpmsoft.projectProducer.service.bim.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.mapper.bim.ProjectBimTreeMapMapper;
import com.jpmsoft.projectProducer.pojo.dto.bim.*;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractDto;
import com.jpmsoft.projectProducer.pojo.entity.bim.ProjectBimTreeMap;
import com.jpmsoft.projectProducer.pojo.request.bim.RiskCategoryHiddenCountRequest;
import com.jpmsoft.projectProducer.pojo.vo.bim.*;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractVo;
import com.jpmsoft.projectProducer.service.bim.IProjectBimTreeMapService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/10 10:10
 * @Description:
 * @Version: 1.0
 */
@Service
public class ProjectBimTreeMapServiceImpl extends ServiceImpl<ProjectBimTreeMapMapper, ProjectBimTreeMap> implements IProjectBimTreeMapService {

    @Override
    public List<String> listProjectIdsByName(String projectName) {
        return this.baseMapper.queryProjectIdsByProjectName(projectName);
    }

    @Override
    public List<String> listContractIdsByName(String contractName) {
        return this.baseMapper.queryContractIdsByContractName(contractName);
    }

    @Override
    public List<ProjectVO> listProjectsByName(String projectName) {
        List<ProjectVO> result = new ArrayList<>();
        List<ProjectDto> projectDtos = this.baseMapper.queryProjectsByProjectName(projectName);
        if (!CollectionUtils.isEmpty(projectDtos)) {
            result = projectDtos.stream().map(e -> {
                ProjectVO vo = new ProjectVO();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<ProjectVO> listProjectsByProjectIds(List<String> projectIds) {
        List<ProjectVO> result = new ArrayList<>();
        List<ProjectDto> projectDtos = this.baseMapper.queryProjectsByProjectIds(projectIds);
        if (!CollectionUtils.isEmpty(projectDtos)) {
            result = projectDtos.stream().map(e -> {
                ProjectVO vo = new ProjectVO();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<ContractVo> listContractsByName(String contractName) {
        List<ContractVo> result = new ArrayList<>();
        List<ContractDto> contractDtos = this.baseMapper.queryContractsByContractName(contractName);
        if (!CollectionUtils.isEmpty(contractDtos)) {
            result = contractDtos.stream().map(e -> {
                ContractVo vo = new ContractVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<ContractVo> listContractByContractIds(List<String> contractIds) {
        List<ContractVo> result = new ArrayList<>();
        List<ContractDto> contractDtos = this.baseMapper.queryContractByConContractIds(contractIds);
        if (!CollectionUtils.isEmpty(contractDtos)) {
            result = contractDtos.stream().map(e -> {
                ContractVo vo = new ContractVo();
                BeanUtils.copyProperties(e, vo);
                return vo;

            }).collect(Collectors.toList());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProjectBimInfoVO> listProjectsByProjectIdsAndProjectName(
            List<String> projectIds, String projectName, String projectCategoryDetails) {
        return this.baseMapper.queryProjectsByProjectIdsAndProjectName(projectIds, projectName, projectCategoryDetails);
    }

    @Override
    public PageResult<QsBimPageVO> pageQs(List<String> qsrIds, Integer pageNum, Integer pageSize) {
        PageResult<QsBimPageVO> result = new PageResult<>();
        IPage<QsBimPageDto> page = new Page<>(pageNum, pageSize);
        List<QsBimPageDto> qsBimPageDtoList = this.baseMapper.queryPageQs(page, qsrIds);
        result.setTotal(page.getTotal());
        if (!CollectionUtils.isEmpty(qsBimPageDtoList)){
            List<QsBimPageVO> voList = qsBimPageDtoList.stream().map(e -> {
                QsBimPageVO vo = new QsBimPageVO();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            result.setRows(voList);
        }
        return result;
    }

    @Override
    public QsCountVO countQs(List<String> qsrIds) {
        QsCountVO result = new QsCountVO();
        result.setResolvedNum(this.baseMapper.queryCountQsForResolved(qsrIds));
        result.setUnresolvedNum(this.baseMapper.queryCountQsForUnResolved(qsrIds));
        return result;
    }

    @Override
    public QsRecordBimResponse getQsByQsrId(String qsrId) {
        return this.baseMapper.queryQsRecordByQsrId(qsrId);
    }

    @Override
    public List<QsRecordBimResponse> listQsByQsrIds(List<String> qsrIds) {
        return this.baseMapper.queryListQsRecordByQsrIds(qsrIds);
    }

    @Override
    public List<PlanTaskBimPageVO> listPlanTaskByTaskIds(List<String> taskIds) {
        return this.baseMapper.queryListPlanTaskByTaskIds(taskIds);
    }

    @Override
    public List<ConBoqDtlBimPageVO> listBoqDtlByDtlIds(List<String> boqDtlIds) {
        return this.baseMapper.queryListBoqDtlByDtlIds(boqDtlIds);
    }

    @Override
    public PageResult<PlanTaskBimPageVO> pagePlanTask(List<String> taskIds, Integer pageNum, Integer pageSize) {
        PageResult<PlanTaskBimPageVO> result = new PageResult<>();
        IPage<PlanTaskBimPageDto> page = new Page<>(pageNum, pageSize);
        List<PlanTaskBimPageDto> dtoList = this.baseMapper.queryPagePlanTask(page, taskIds);
        result.setTotal(page.getTotal());
        if (!CollectionUtils.isEmpty(dtoList)){
            List<PlanTaskBimPageVO> voList = dtoList.stream().map(e -> {
                PlanTaskBimPageVO vo = new PlanTaskBimPageVO();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            result.setRows(voList);
        }
        return result;
    }

    @Override
    public PageResult<PlanBimPageVO> pagePlan(List<String> planIds, Integer pageNum, Integer pageSize) {
        PageResult<PlanBimPageVO> result = new PageResult<>();
        IPage<PlanBimPageDto> page = new Page<>(pageNum, pageSize);
        List<PlanBimPageDto> dtoList = this.baseMapper.queryPagePlan(page, planIds);
        result.setTotal(page.getTotal());
        if (!CollectionUtils.isEmpty(dtoList)){
            List<PlanBimPageVO> voList = dtoList.stream().map(e -> {
                PlanBimPageVO vo = new PlanBimPageVO();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            result.setRows(voList);
        }
        return result;
    }

    @Override
    public List<PlanTaskBimPageVO> listPlanTaskByPlanId(String planId) {
        List<PlanTaskBimPageVO> result = new ArrayList<>();
        List<PlanTaskBimPageDto> dtoList = this.baseMapper.queryListPlanTaskByPlanId(planId);
        processPlanTasks(result,dtoList,"0");
        return result;
    }

    @Override
    public PageResult<ConBoqBimPageVO> pageBoq(List<String> boqIds, Integer pageNum, Integer pageSize) {
        PageResult<ConBoqBimPageVO> result = new PageResult<>();
        IPage<ConBoqBimPageDto> page = new Page<>(pageNum, pageSize);
        List<ConBoqBimPageDto> dtoList = this.baseMapper.queryPageBoq(page, boqIds);
        result.setTotal(page.getTotal());
        if (!CollectionUtils.isEmpty(dtoList)){
            List<ConBoqBimPageVO> voList = dtoList.stream().map(e -> {
                ConBoqBimPageVO vo = new ConBoqBimPageVO();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            result.setRows(voList);
        }
        return result;
    }

    @Override
    public PageResult<ConBoqDtlBimPageVO> pageBoqDtl(List<String> boqDtlIds, Integer pageNum, Integer pageSize) {
        PageResult<ConBoqDtlBimPageVO> result = new PageResult<>();
        IPage<ConBoqDtlBimPageDto> page = new Page<>(pageNum, pageSize);
        List<ConBoqDtlBimPageDto> dtoList = this.baseMapper.queryPageBoqDtl(page, boqDtlIds);
        result.setTotal(page.getTotal());
        if (!CollectionUtils.isEmpty(dtoList)){
            List<ConBoqDtlBimPageVO> voList = dtoList.stream().map(e -> {
                ConBoqDtlBimPageVO vo = new ConBoqDtlBimPageVO();
                BeanUtils.copyProperties(e, vo);
                vo.setBoqDtlId(e.getBoqdId());
                return vo;
            }).collect(Collectors.toList());
            result.setRows(voList);
        }
        return result;
    }

    @Override
    public List<ConBoqDtlBimPageVO> listBoqDtlByBoqId(String boqId) {
        List<ConBoqDtlBimPageVO> result = new ArrayList<>();
        List<ConBoqDtlBimPageDto> dtoList = this.baseMapper.queryListBoqDtlByBoqId(boqId);
        processBoqDtls(result,dtoList,"0");
        return result;
    }

    @Override
    public Long countHiddenByRiskEvaluateIds(RiskCategoryHiddenCountRequest request) {
        return this.baseMapper.queryCountHiddenByRiskEvaluateIds(request.getRiskEvaluateIds(), request.getStartTime(), request.getEndTime(), request.getRectifyStatus());
    }

    @Override
    public List<QsRecordCountVO> countQsRecord(RiskCategoryHiddenCountRequest request) {
        List<QsRecordCountVO> result = this.baseMapper.queryCountQsRecord(request.getProjectId(), request.getStartTime(), request.getEndTime());
        Map<String, Long> groupMap = result.stream().collect(Collectors.groupingBy(QsRecordCountVO::getProblemDesc, Collectors.counting()));
        result = new ArrayList<>();
        for (Map.Entry<String, Long> entry : groupMap.entrySet()) {
            QsRecordCountVO vo = new QsRecordCountVO();
            vo.setProblemDesc(entry.getKey());
            vo.setProblemNum(entry.getValue());
            result.add(vo);
        }

        List<QsRecordCountVO> sortedResult = result.stream().sorted(Comparator.comparing(QsRecordCountVO::getProblemNum).reversed()).collect(Collectors.toList());
        return sortedResult;
    }

    private void processPlanTasks(List<PlanTaskBimPageVO> result,List<PlanTaskBimPageDto> dtoList, String parentId){
        List<PlanTaskBimPageDto> roots = dtoList.stream().filter(n -> parentId.equals(n.getTaskParent())).collect(Collectors.toList());
        for (PlanTaskBimPageDto root : roots) {
            PlanTaskBimPageVO vo = new PlanTaskBimPageVO();
            BeanUtils.copyProperties(root, vo);
            result.add(vo);
            processPlanTasks(result,dtoList,vo.getTaskId());
        }
    }

    private void processBoqDtls(List<ConBoqDtlBimPageVO> result,List<ConBoqDtlBimPageDto> dtoList, String parentId){
        List<ConBoqDtlBimPageDto> roots = dtoList.stream().filter(n -> parentId.equals(n.getBoqdParentId())).collect(Collectors.toList());
        for (ConBoqDtlBimPageDto root : roots) {
            ConBoqDtlBimPageVO vo = new ConBoqDtlBimPageVO();
            BeanUtils.copyProperties(root, vo);
            vo.setBoqDtlId(root.getBoqdId());
            result.add(vo);
            processBoqDtls(result,dtoList,vo.getBoqDtlId());
        }
    }
}
