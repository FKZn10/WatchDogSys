package com.jpmsoft.projectProducer.service.xz.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.xz.FundApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApplicationVO;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.mapper.xz.FundApplicationMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.xz.*;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.xz.*;
import com.jpmsoft.projectProducer.utils.BeanUtilsUpgrade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class FundApplicationServiceImpl extends ServiceImpl<FundApplicationMapper, FundApplication> implements FundApplicationService {

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Autowired
    private FundApplicationDetailService fundApplicationDetailService;

    @Autowired
    private ProjectApplicationService projectApplicationService;

    @Autowired
    private FundApprovalService fundApprovalService;

    @Autowired
    private FundApprovalDetailService fundApprovalDetailService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<FundApplicationVO> page(FundApplicationQueryRequest request) {
        PageResult<FundApplicationVO> result = new PageResult<>();
        IPage<FundApplicationVO> page = new Page<>(request.getPageNum(), request.getPageSize());
        List<FundApplicationVO> vos = this.baseMapper.queryPage(page, request.getFileName(), request.getPlanYear(), request.getApproveStatus(), request.getBatchNum(), request.getCreateId());
        result.setTotal(page.getTotal());
        result.setRows(vos);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(FundApplicationCreateRequest request) {
        FundApplication entity = BeanUtilsUpgrade.copyObject(request, FundApplication.class);
        entity.setApproveStatus("0");
        this.save(entity);

        //详情表
        LambdaQueryWrapper<FundApplicationDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundApplicationDetail::getFundApplicationId, entity.getId());
        fundApplicationDetailService.remove(queryWrapper);

        if (CollectionUtils.isNotEmpty(request.getDetails())) {
            List<FundApplicationDetail> details = request.getDetails().stream().map(e -> {
                FundApplicationDetail detail = new FundApplicationDetail();
                detail.setFundApplicationId(entity.getId());
                detail.setProjectApplicationId(e.getId());
                return detail;
            }).collect(Collectors.toList());
            fundApplicationDetailService.saveBatch(details);
        }


        //流程创建
        sysFileService.saveFileOrUpdate(entity.getId(), "资金立项申报", "0", request.getCreateId(), request.getCreateName(), "FundApplication", true, request.getTId(), request.getTCode());

        //附件
        if (ValidateUtil.isNotEmpty(entity.getFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getFileRelationId(), request.getFileIds(), entity.getId());
        }

        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(FundApplicationCreateRequest request) {
        FundApplication entity = BeanUtilsUpgrade.copyObject(request, FundApplication.class);
        entity.setCreateId(null);
        entity.setCreateName(null);
        this.updateById(entity);

        //详情表
        LambdaQueryWrapper<FundApplicationDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundApplicationDetail::getFundApplicationId, entity.getId());
        fundApplicationDetailService.remove(queryWrapper);

        if (CollectionUtils.isNotEmpty(request.getDetails())) {
            List<FundApplicationDetail> details = request.getDetails().stream().map(e -> {
                FundApplicationDetail detail = new FundApplicationDetail();
                detail.setFundApplicationId(entity.getId());
                detail.setProjectApplicationId(e.getId());
                return detail;
            }).collect(Collectors.toList());
            fundApplicationDetailService.saveBatch(details);
        }

        //附件
        if (ValidateUtil.isNotEmpty(entity.getFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getFileRelationId(), request.getFileIds(), entity.getId());
        }

        return entity.getId();
    }

    @Override
    public FundApplicationVO detail(FundApplicationCreateRequest request) {
        FundApplicationVO result = this.baseMapper.queryDetailById(request.getId());
        LambdaQueryWrapper<FundApplicationDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundApplicationDetail::getFundApplicationId, request.getId());
        List<FundApplicationDetail> details = fundApplicationDetailService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(details)) {
            List<String> projectApplicationIds = details.stream().map(FundApplicationDetail::getProjectApplicationId).collect(Collectors.toList());
            List<ProjectApplicationVO> projectApplicationVOS = projectApplicationService.listProjectApplicationByIds(projectApplicationIds);
            result.setDetails(projectApplicationVOS);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncApproveState(FundApplicationCreateRequest request) {
        SysFile sysFilePojo = sysFileService.getById(request.getId());
        FundApplication entity = this.getById(request.getId());
        if (sysFilePojo == null || entity == null) {
            return;
        }
        entity.setApproveStatus(sysFilePojo.getFileflowstatus());
        entity.setApproveTime(new Date());
        this.updateById(entity);

        //审批通过时，将项目表状态修改为资金立项申报已通过
        //生成资金批复表数据
        if ("7".equals(entity.getApproveStatus())){
            LambdaQueryWrapper<FundApplicationDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FundApplicationDetail::getFundApplicationId, request.getId());
            List<FundApplicationDetail> details = fundApplicationDetailService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(details)){
                List<String> projectApplicationIds = details.stream().map(FundApplicationDetail::getProjectApplicationId).collect(Collectors.toList());
                List<ProjectApplication> projectApplications = projectApplicationService.listByIds(projectApplicationIds);
                for (ProjectApplication detail : projectApplications) {
                    //采购资金默认使用预算资金
                    detail.setGeneralAmount(detail.getGeneralBudget());
                    detail.setGovernmentAmount(detail.getGovernmentBudget());
                    detail.setOtherAmount(detail.getOtherBudget());
                    detail.setTaskStatus("fundPass");
                }
                projectApplicationService.updateBatchById(projectApplications);
            }

            //生成资金批复表数据
            FundApproval fundApproval = BeanUtilsUpgrade.copyObject(entity, FundApproval.class);
            fundApproval.setFileRelationId(null);
            fundApproval.setId(null);
            fundApproval.setApproveStatus("0");
            fundApprovalService.save(fundApproval);
            if (CollectionUtils.isNotEmpty(details)){
                List<FundApprovalDetail> fundApprovalDetailList = new ArrayList<>();
                for (FundApplicationDetail detail : details) {
                    FundApprovalDetail fundApprovalDetail = new FundApprovalDetail();
                    fundApprovalDetail.setFundApprovalId(fundApproval.getId());
                    fundApprovalDetail.setProjectApplicationId(detail.getProjectApplicationId());
                    fundApprovalDetailList.add(fundApprovalDetail);
                }
                fundApprovalDetailService.saveBatch(fundApprovalDetailList);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(FundApplicationCreateRequest request) {
        this.removeById(request.getId());
        //详情表
        LambdaQueryWrapper<FundApplicationDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundApplicationDetail::getFundApplicationId, request.getId());
        fundApplicationDetailService.remove(queryWrapper);
    }
}
