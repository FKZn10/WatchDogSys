package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.NoticeOfConstructionUnitRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.NoticeOfConstructionUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.NoticeOfConstructionUnit;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.INoticeOfConstructionUnitService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class NoticeOfConstructionUnitImpl extends
        ServiceImpl<NoticeOfConstructionUnitMapper, NoticeOfConstructionUnit>
        implements INoticeOfConstructionUnitService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    /**
     * 施工单位中标通知书
     */
    @Override
    public BaseResponse createNoticeOfConstructionUnit(NoticeOfConstructionUnitRequest request) {
        request.setUuid(GenerateUUID.getUUID());
        if (ValidateUtil.isNull(request.getApproveStatus())) {
            request.setApproveStatus("0");
        }
        NoticeOfConstructionUnit entity = new NoticeOfConstructionUnit();
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
    public BaseResponse getNoticeOfConstructionUnitList(NoticeOfConstructionUnitRequest request) {
        QueryWrapper<NoticeOfConstructionUnit> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            queryWrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getProductName())) {
            queryWrapper.like("Product_Name", request.getProductName());
        }
        if (ValidateUtil.isNotEmpty(request.getCreateName())) {
            queryWrapper.like("Create_Name", request.getCreateName());
        }
        queryWrapper.orderByDesc("Create_Time");
        IPage<NoticeOfConstructionUnit> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


    @Override
    public BaseResponse getNoticeOfConstructionUnitInfoById(String uuId) {
        NoticeOfConstructionUnit noticeOfConstructionUnit = baseMapper.selectById(uuId);
        return BaseResponse.ok(noticeOfConstructionUnit);
    }


    @Override
    public BaseResponse deleteNoticeOfConstructionUnit(String uuId) {
        baseMapper.deleteById(uuId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse updateNoticeOfConstructionUnit(NoticeOfConstructionUnitRequest request) {
        NoticeOfConstructionUnit entity = new NoticeOfConstructionUnit();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "update", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }


    @Override
    public BaseResponse updateApproveStatusNoticeOfConstructionUnit(NoticeOfConstructionUnitRequest request) {
        NoticeOfConstructionUnit noticeOfConstructionUnit = baseMapper.selectById(request.getUuid());
        SysFile File = sysFileService.getById(request.getUuid());
        noticeOfConstructionUnit.setApproveTime(new Date());
        noticeOfConstructionUnit.setApproveStatus(File.getFileflowstatus());
        baseMapper.updateById(noticeOfConstructionUnit);
        return BaseResponse.ok();
    }
}
