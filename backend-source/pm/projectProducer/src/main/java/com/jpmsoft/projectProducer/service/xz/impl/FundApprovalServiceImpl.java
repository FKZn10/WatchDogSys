package com.jpmsoft.projectProducer.service.xz.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.xz.FundApprovalCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApprovalVO;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.mapper.xz.FundApprovalMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.xz.FundApproval;
import com.jpmsoft.projectProducer.pojo.entity.xz.FundApprovalDetail;
import com.jpmsoft.projectProducer.pojo.entity.xz.ProjectApplication;
import com.jpmsoft.projectProducer.pojo.entity.xz.ProjectApproval;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.xz.FundApprovalDetailService;
import com.jpmsoft.projectProducer.service.xz.FundApprovalService;
import com.jpmsoft.projectProducer.service.xz.ProjectApplicationService;
import com.jpmsoft.projectProducer.service.xz.ProjectApprovalService;
import com.jpmsoft.projectProducer.utils.BeanUtilsUpgrade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:53
 * @Description:
 * @Version: 1.0
 */
@Service
public class FundApprovalServiceImpl extends ServiceImpl<FundApprovalMapper, FundApproval> implements FundApprovalService {

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Autowired
    private FundApprovalDetailService FundApprovalDetailService;

    @Autowired
    private ProjectApplicationService projectApplicationService;

    @Autowired
    private ProjectApprovalService projectApprovalService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<FundApprovalVO> page(FundApprovalQueryRequest request) {
        PageResult<FundApprovalVO> result = new PageResult<>();
        IPage<FundApprovalVO> page = new Page<>(request.getPageNum(), request.getPageSize());
        List<FundApprovalVO> vos = this.baseMapper.queryPage(page, request.getFileName(), request.getPlanYear(), request.getApproveStatus(), request.getBatchNum(), request.getCreateId());
        result.setTotal(page.getTotal());
        result.setRows(vos);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(FundApprovalCreateRequest request) {
        FundApproval entity = BeanUtilsUpgrade.copyObject(request, FundApproval.class);
        entity.setApproveStatus("0");
        this.save(entity);

        //详情表
        LambdaQueryWrapper<FundApprovalDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundApprovalDetail::getFundApprovalId, entity.getId());
        FundApprovalDetailService.remove(queryWrapper);

        if (CollectionUtils.isNotEmpty(request.getDetails())) {
            List<FundApprovalDetail> details = request.getDetails().stream().map(e -> {
                FundApprovalDetail detail = new FundApprovalDetail();
                detail.setFundApprovalId(entity.getId());
                detail.setProjectApplicationId(e.getId());
                return detail;
            }).collect(Collectors.toList());
            FundApprovalDetailService.saveBatch(details);
        }

        //流程创建
        sysFileService.saveFileOrUpdate(entity.getId(), "资金批复", "0", request.getCreateId(), request.getCreateName(), "FundApproval", true, request.getTId(), request.getTCode());

        //附件
        if (ValidateUtil.isNotEmpty(entity.getFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getFileRelationId(), request.getFileIds(), entity.getId());
        }

        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(FundApprovalCreateRequest request) {
        FundApproval entity = BeanUtilsUpgrade.copyObject(request, FundApproval.class);
        entity.setCreateId(null);
        entity.setCreateName(null);
        this.updateById(entity);

        //流程创建
        SysFile sysFilePojo = sysFileService.getById(request.getId());
        if (null == sysFilePojo) {
            sysFileService.saveFileOrUpdate(entity.getId(), "资金批复", "0", request.getCreateId(), request.getCreateName(), "FundApproval", true, request.getTId(), request.getTCode());
        }

        //详情表
        LambdaQueryWrapper<FundApprovalDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundApprovalDetail::getFundApprovalId, entity.getId());
        FundApprovalDetailService.remove(queryWrapper);

        if (CollectionUtils.isNotEmpty(request.getDetails())) {
            List<FundApprovalDetail> details = request.getDetails().stream().map(e -> {
                FundApprovalDetail detail = new FundApprovalDetail();
                detail.setFundApprovalId(entity.getId());
                detail.setProjectApplicationId(e.getId());
                return detail;
            }).collect(Collectors.toList());
            FundApprovalDetailService.saveBatch(details);
        }

        //附件
        if (ValidateUtil.isNotEmpty(entity.getFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getFileRelationId(), request.getFileIds(), entity.getId());
        }

        return entity.getId();
    }

    @Override
    public FundApprovalVO detail(FundApprovalCreateRequest request) {
        FundApprovalVO result = this.baseMapper.queryDetailById(request.getId());
        LambdaQueryWrapper<FundApprovalDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundApprovalDetail::getFundApprovalId, request.getId());
        List<FundApprovalDetail> details = FundApprovalDetailService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(details)) {
            List<String> projectApplicationIds = details.stream().map(FundApprovalDetail::getProjectApplicationId).collect(Collectors.toList());
            List<ProjectApplicationVO> projectApplicationVOS = projectApplicationService.listProjectApplicationByIds(projectApplicationIds);
            result.setDetails(projectApplicationVOS);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncApproveState(FundApprovalCreateRequest request) {
        SysFile sysFilePojo = sysFileService.getById(request.getId());
        FundApproval entity = this.getById(request.getId());
        if (sysFilePojo == null || entity == null) {
            return;
        }
        entity.setApproveStatus(sysFilePojo.getFileflowstatus());
        entity.setApproveTime(new Date());
        this.updateById(entity);

        //审批通过时，将项目表状态修改为资金批复已通过
        //生成项目批复表数据，分成政府采购金额 和 非政府采购金额两种项目
        if ("7".equals(entity.getApproveStatus())) {
            LambdaQueryWrapper<FundApprovalDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FundApprovalDetail::getFundApprovalId, request.getId());
            List<FundApprovalDetail> details = FundApprovalDetailService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(details)) {
                List<ProjectApplication> updateList = new ArrayList<>();
                for (FundApprovalDetail detail : details) {
                    ProjectApplication projectApplication = new ProjectApplication();
                    projectApplication.setId(detail.getProjectApplicationId());
                    projectApplication.setTaskStatus("fundApproval");
                    updateList.add(projectApplication);
                }
                projectApplicationService.updateBatchById(updateList);
            }

            //生成项目批复表数据，分成政府采购金额 和 非政府采购金额两种项目
            if (CollectionUtils.isNotEmpty(details)) {
                List<ProjectApproval> createList = new ArrayList<>();
                List<String> projectApplicationIds = details.stream().map(FundApprovalDetail::getProjectApplicationId).collect(Collectors.toList());
                List<ProjectApplication> projectApplications = projectApplicationService.listByIds(projectApplicationIds);
                for (ProjectApplication projectApplication : projectApplications) {
                    //government-政府采购金额；
                    if (projectApplication.getGovernmentAmount().compareTo(BigDecimal.ZERO) > 0){
                        ProjectApproval governmentEntity = BeanUtilsUpgrade.copyObject(projectApplication, ProjectApproval.class);
                        governmentEntity.setId(null);
                        governmentEntity.setParentId(projectApplication.getId());
                        governmentEntity.setGeneralBudget(BigDecimal.ZERO);
                        governmentEntity.setGeneralAmount(projectApplication.getGovernmentAmount());
                        governmentEntity.setOtherBudget(BigDecimal.ZERO);
                        governmentEntity.setOtherAmount(BigDecimal.ZERO);
                        governmentEntity.setProjectAmountType("government");
                        createList.add(governmentEntity);
                    }

                    //other-非政府采购金额
                    if (projectApplication.getOtherAmount().compareTo(BigDecimal.ZERO) > 0){
                        ProjectApproval otherEntity = BeanUtilsUpgrade.copyObject(projectApplication, ProjectApproval.class);
                        otherEntity.setId(null);
                        otherEntity.setParentId(projectApplication.getId());
                        otherEntity.setGeneralBudget(BigDecimal.ZERO);
                        otherEntity.setGeneralAmount(projectApplication.getOtherAmount());
                        otherEntity.setGovernmentBudget(BigDecimal.ZERO);
                        otherEntity.setGovernmentAmount(BigDecimal.ZERO);
                        otherEntity.setProjectAmountType("other");
                        createList.add(otherEntity);
                    }
                }
                projectApprovalService.saveBatch(createList);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(FundApprovalCreateRequest request) {
        this.removeById(request.getId());
        //详情表
        LambdaQueryWrapper<FundApprovalDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundApprovalDetail::getFundApprovalId, request.getId());
        FundApprovalDetailService.remove(queryWrapper);
    }
}
