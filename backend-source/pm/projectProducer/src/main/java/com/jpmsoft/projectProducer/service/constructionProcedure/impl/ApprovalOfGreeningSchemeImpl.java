package com.jpmsoft.projectProducer.service.constructionProcedure.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.ApprovalOfGreeningSchemeRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ApprovalOfGreeningSchemeMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ApprovalOfGreeningScheme;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.IApprovalOfGreeningSchemeService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ApprovalOfGreeningSchemeImpl extends
        ServiceImpl<ApprovalOfGreeningSchemeMapper, ApprovalOfGreeningScheme>
        implements IApprovalOfGreeningSchemeService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    /**
     * .绿化方案审批
     */
    @Override
    public BaseResponse createApprovalOfGreeningScheme(ApprovalOfGreeningSchemeRequest request) {

        request.setUuid(GenerateUUID.getUUID());

        if (ValidateUtil.isNull(request.getApproveStatus())) {
            request.setApproveStatus("0");
        }
        ApprovalOfGreeningScheme entity = new ApprovalOfGreeningScheme();
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
    public BaseResponse getApprovalOfGreeningSchemeList(ApprovalOfGreeningSchemeRequest request) {
        QueryWrapper<ApprovalOfGreeningScheme> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            queryWrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getProductName())) {
            queryWrapper.like("Product_Name", request.getProductName());
        }

        if (ValidateUtil.isNotEmpty(request.getCreateName())) {
            queryWrapper.like("Create_Name", request.getCreateName());
        }
        queryWrapper.orderByDesc("Create_Time");
        IPage<ApprovalOfGreeningScheme> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


    @Override
    public BaseResponse getApprovalOfGreeningSchemeInfoById(String uuId) {
        ApprovalOfGreeningScheme approvalOfGreeningScheme = baseMapper.selectById(uuId);
        return BaseResponse.ok(approvalOfGreeningScheme);
    }


    @Override
    public BaseResponse deleteApprovalOfGreeningScheme(String uuId) {
        baseMapper.deleteById(uuId);
        sysFileService.deleteFileById(uuId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse updateApprovalOfGreeningScheme(ApprovalOfGreeningSchemeRequest request) {
        ApprovalOfGreeningScheme entity = new ApprovalOfGreeningScheme();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "update", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }


    @Override
    public BaseResponse updateApproveStatusApprovalOfGreeningScheme(ApprovalOfGreeningSchemeRequest request) {
        ApprovalOfGreeningScheme administrativeLicensingPojo = baseMapper.selectById(request.getUuid());
        SysFile File = sysFileService.getById(request.getUuid());
        administrativeLicensingPojo.setApproveTime(new Date());
        administrativeLicensingPojo.setApproveStatus(File.getFileflowstatus());
        baseMapper.updateById(administrativeLicensingPojo);
        return BaseResponse.ok();
    }

}
