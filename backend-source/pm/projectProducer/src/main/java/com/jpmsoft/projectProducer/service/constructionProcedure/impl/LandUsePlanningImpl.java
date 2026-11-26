package com.jpmsoft.projectProducer.service.constructionProcedure.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.LandUsePlanningRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.LandUsePlanningMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.LandUsePlanning;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.ILandUsePlanningService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class LandUsePlanningImpl extends
        ServiceImpl<LandUsePlanningMapper, LandUsePlanning>
        implements ILandUsePlanningService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    /**
     * 用地规划许可证
     */

    @Override
    public BaseResponse createLandUsePlanning(LandUsePlanningRequest request) {
        request.setUuid(GenerateUUID.getUUID());
        if (ValidateUtil.isNull(request.getApproveStatus())) {
            request.setApproveStatus("0");
        }
        LandUsePlanning entity = new LandUsePlanning();
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
    public BaseResponse getLandUsePlanningList(LandUsePlanningRequest request) {
        QueryWrapper<LandUsePlanning> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            queryWrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getProductName())) {
            queryWrapper.like("Product_Name", request.getProductName());
        }
        if (ValidateUtil.isNotEmpty(request.getCreateName())) {
            queryWrapper.like("Create_Name", request.getCreateName());
        }
        queryWrapper.orderByDesc("Create_Time");
        IPage<LandUsePlanning> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


    @Override
    public BaseResponse getLandUsePlanningInfoById(String uuId) {
        LandUsePlanning landUsePlanning = baseMapper.selectById(uuId);
        return BaseResponse.ok(landUsePlanning);
    }


    @Override
    public BaseResponse deleteLandUsePlanning(String uuId) {
        baseMapper.deleteById(uuId);
        sysFileService.deleteFileById(uuId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse updateLandUsePlanning(LandUsePlanningRequest request) {
        LandUsePlanning entity = new LandUsePlanning();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "update", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }


    @Override
    public BaseResponse updateApproveStatusLandUsePlanning(LandUsePlanningRequest request) {
        LandUsePlanning landUsePlanning = baseMapper.selectById(request.getUuid());
        SysFile File = sysFileService.getById(request.getUuid());
        landUsePlanning.setApproveTime(new Date());
        landUsePlanning.setApproveStatus(File.getFileflowstatus());
        baseMapper.updateById(landUsePlanning);
        return BaseResponse.ok();
    }

}
