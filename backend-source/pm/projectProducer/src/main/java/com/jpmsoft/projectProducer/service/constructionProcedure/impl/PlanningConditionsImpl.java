package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.PlanningConditionsRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.PlanningConditionsMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.PlanningConditions;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.IPlanningConditionsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class PlanningConditionsImpl extends
        ServiceImpl<PlanningConditionsMapper, PlanningConditions>
        implements IPlanningConditionsService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    /**
     * 规划条件
     */

    @Override
    public BaseResponse createPlanningConditions(PlanningConditionsRequest request) {
        request.setUuid(GenerateUUID.getUUID());
        if (ValidateUtil.isNull(request.getApproveStatus())) {
            request.setApproveStatus("0");
        }
        PlanningConditions entity = new PlanningConditions();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        baseMapper.insert(entity);
        //调用流程
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "create", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }


    @Override
    public BaseResponse getPlanningConditionsList(PlanningConditionsRequest request) {
        QueryWrapper<PlanningConditions> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            queryWrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getProductName())) {
            queryWrapper.like("Product_Name", request.getProductName());
        }
        if (ValidateUtil.isNotEmpty(request.getCreateName())) {
            queryWrapper.like("Create_Name", request.getCreateName());
        }
        queryWrapper.orderByDesc("Create_Time");
        IPage<PlanningConditions> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


    @Override
    public BaseResponse getPlanningConditionsInfoById(String uuId) {
        PlanningConditions planningConditions = baseMapper.selectById(uuId);
        return BaseResponse.ok(planningConditions);
    }


    public BaseResponse deletePlanningConditions(String uuId) {
        baseMapper.deleteById(uuId);
        sysFileService.deleteFileById(uuId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse updatePlanningConditions(PlanningConditionsRequest request) {
        PlanningConditions entity = new PlanningConditions();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "update", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }


    @Override
    public BaseResponse updateApproveStatusPlanningConditions(PlanningConditionsRequest request) {
        PlanningConditions planningConditions = baseMapper.selectById(request.getUuid());
        SysFile File = sysFileService.getById(request.getUuid());
        planningConditions.setApproveTime(new Date());
        planningConditions.setApproveStatus(File.getFileflowstatus());
        baseMapper.updateById(planningConditions);
        return BaseResponse.ok();
    }
}
