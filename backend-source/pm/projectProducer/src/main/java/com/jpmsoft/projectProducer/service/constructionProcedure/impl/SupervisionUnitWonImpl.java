package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.SupervisionUnitWonRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.SupervisionUnitWonMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.SupervisionUnitWon;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.ISupervisionUnitWonService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SupervisionUnitWonImpl extends
        ServiceImpl<SupervisionUnitWonMapper, SupervisionUnitWon>
        implements ISupervisionUnitWonService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    /**
     * ..监理单位中标通知书
     */
    @Override
    public BaseResponse createSupervisionUnitWon(SupervisionUnitWonRequest request) {
        request.setUuid(GenerateUUID.getUUID());
        if (ValidateUtil.isNull(request.getApproveStatus())) {
            request.setApproveStatus("0");
        }
        SupervisionUnitWon entity = new SupervisionUnitWon();
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
    public BaseResponse getSupervisionUnitWonList(SupervisionUnitWonRequest request) {
        QueryWrapper<SupervisionUnitWon> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            queryWrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getProductName())) {
            queryWrapper.like("Product_Name", request.getProductName());
        }
        if (ValidateUtil.isNotEmpty(request.getCreateName())) {
            queryWrapper.like("Create_Name", request.getCreateName());
        }
        queryWrapper.orderByDesc("Create_Time");
        IPage<SupervisionUnitWon> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


    @Override
    public BaseResponse getSupervisionUnitWonInfoById(String uuId) {
        SupervisionUnitWon supervisionUnitWonPojo = baseMapper.selectById(uuId);
        return BaseResponse.ok(supervisionUnitWonPojo);
    }


    @Override
    public BaseResponse deleteSupervisionUnitWon(String uuId) {
        baseMapper.deleteById(uuId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse updateSupervisionUnitWon(SupervisionUnitWonRequest request) {
        SupervisionUnitWon entity = new SupervisionUnitWon();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "update", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }


    @Override
    public BaseResponse updateApproveStatusSupervisionUnitWon(SupervisionUnitWonRequest request) {
        SupervisionUnitWon supervisionUnitWonPojo = baseMapper.selectById(request.getUuid());
        SysFile File = sysFileService.getById(request.getUuid());
        supervisionUnitWonPojo.setApproveTime(new Date());
        supervisionUnitWonPojo.setApproveStatus(File.getFileflowstatus());
        baseMapper.updateById(supervisionUnitWonPojo);
        return BaseResponse.ok();
    }
}
