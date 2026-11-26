package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.AdministrativeLicensingRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.AdministrativeLicensingMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.AdministrativeLicensing;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.IAdministrativeLicensingService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AdministrativeLicensingImpl extends
        ServiceImpl<AdministrativeLicensingMapper, AdministrativeLicensing>
        implements IAdministrativeLicensingService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    /**
     * .人防行政许可
     */
    @Override
    public BaseResponse createAdministrativeLicensing(AdministrativeLicensingRequest request) {
        request.setUuid(GenerateUUID.getUUID());
        if (ValidateUtil.isEmpty(request.getApproveStatus())) {
            request.setApproveStatus("0");
        }
        AdministrativeLicensing entity = new AdministrativeLicensing();
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

    /**
     * @param request
     * @return
     */
    @Override
    public BaseResponse getAdministrativeLicensingList(AdministrativeLicensingRequest request) {

        QueryWrapper<AdministrativeLicensing> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            queryWrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getProductName())) {
            queryWrapper.like("Product_Name", request.getProductName());
        }
        if (ValidateUtil.isNotEmpty(request.getCreateName())) {
            queryWrapper.like("Create_Name", request.getCreateName());
        }
        queryWrapper.orderByDesc("Create_Time");
        IPage<AdministrativeLicensing> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    /**
     * @param
     * @return
     */
    @Override
    public BaseResponse getAdministrativeLicensingInfoById(String uuId) {
        AdministrativeLicensing administrativeLicensing = baseMapper.selectById(uuId);
        return BaseResponse.ok(administrativeLicensing);
    }


    @Override
    public BaseResponse deleteAdministrativeLicensing(String uuId) {
        baseMapper.deleteById(uuId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateAdministrativeLicensing(AdministrativeLicensingRequest request) {
        AdministrativeLicensing entity = new AdministrativeLicensing();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "update", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }

    public BaseResponse updateApproveStatusAdministrativeLicensing(AdministrativeLicensingRequest request) {
        AdministrativeLicensing administrativeLicensing = baseMapper.selectById(request.getUuid());
        SysFile File = sysFileService.getById(request.getUuid());
        administrativeLicensing.setApproveTime(new Date());
        administrativeLicensing.setApproveStatus(File.getFileflowstatus());
        baseMapper.updateById(administrativeLicensing);
        return BaseResponse.ok();
    }
}
