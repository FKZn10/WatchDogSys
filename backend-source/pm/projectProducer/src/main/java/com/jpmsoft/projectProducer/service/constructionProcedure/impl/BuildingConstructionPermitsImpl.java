package com.jpmsoft.projectProducer.service.constructionProcedure.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.BuildingConstructionPermitsRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.BuildingConstructionPermitsMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.BuildingConstructionPermits;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.IBuildingConstructionPermitsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class BuildingConstructionPermitsImpl extends
        ServiceImpl<BuildingConstructionPermitsMapper, BuildingConstructionPermits>
        implements IBuildingConstructionPermitsService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    /**
     * 建筑工程施工许可证
     */
    @Override
    public BaseResponse createBuildingConstructionPermits(BuildingConstructionPermitsRequest request) {
        request.setUuid(GenerateUUID.getUUID());

        if (ValidateUtil.isNull(request.getApproveStatus())) {
            request.setApproveStatus("0");
        }
        BuildingConstructionPermits entity = new BuildingConstructionPermits();
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
    public BaseResponse getBuildingConstructionPermitsList(BuildingConstructionPermitsRequest request) {
        QueryWrapper<BuildingConstructionPermits> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            queryWrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getProductName())) {
            queryWrapper.like("Product_Name", request.getProductName());
        }
        if (ValidateUtil.isNotEmpty(request.getCreateName())) {
            queryWrapper.like("Create_Name", request.getCreateName());
        }
        queryWrapper.orderByDesc("Create_Time");
        IPage<BuildingConstructionPermits> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


    @Override
    public BaseResponse getBuildingConstructionPermitsInfoById(String uuId) {
        BuildingConstructionPermits approvalOfGreeningSchemePojo = baseMapper.selectById(uuId);
        return BaseResponse.ok(approvalOfGreeningSchemePojo);
    }


    @Override
    public BaseResponse deleteBuildingConstructionPermits(String uuId) {
        baseMapper.deleteById(uuId);
        sysFileService.deleteFileById(uuId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse updateBuildingConstructionPermits(BuildingConstructionPermitsRequest request) {
        BuildingConstructionPermits entity = new BuildingConstructionPermits();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "update", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }


    @Override
    public BaseResponse updateApproveStatusBuildingConstructionPermits(BuildingConstructionPermitsRequest request) {
        BuildingConstructionPermits administrativeLicensingPojo = baseMapper.selectById(request.getUuid());
        SysFile File = sysFileService.getById(request.getUuid());
        administrativeLicensingPojo.setApproveTime(new Date());
        administrativeLicensingPojo.setApproveStatus(File.getFileflowstatus());
        baseMapper.updateById(administrativeLicensingPojo);
        return BaseResponse.ok();
    }


}
