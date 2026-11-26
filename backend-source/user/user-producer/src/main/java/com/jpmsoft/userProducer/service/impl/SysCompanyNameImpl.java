package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysCompanyNameMapper;
import com.jpmsoft.userProducer.pojo.dto.CompanyNameDto;
import com.jpmsoft.userProducer.pojo.entity.SysCompany;
import com.jpmsoft.userProducer.pojo.entity.SysCompanyName;
import com.jpmsoft.userProducer.service.ISysCompanyNameService;
import com.jpmsoft.userProducer.service.ISysCompanyService;
import com.jpmsoft.userProducer.service.ISysFileAnnexService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysCompanyNameImpl extends ServiceImpl<SysCompanyNameMapper, SysCompanyName> implements ISysCompanyNameService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysCompanyService sysCompanyService;

    @Override
    public BaseResponse GetCompanyNameList(CompanyNameDto request) {
        QueryWrapper<SysCompanyName> wrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getCnCode()))
            wrapper.like("CN_Code", request.getCnCode());
        if (!ValidateUtil.isEmpty(request.getCnOldname()))
            wrapper.like("CN_OldName", request.getCnOldname());
        if (!ValidateUtil.isEmpty(request.getCnNewname()))
            wrapper.like("CN_NewName", request.getCnNewname());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("CN_CreateTime");
        IPage<SysCompanyName> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateCompanyName(CompanyNameDto request) {
        SysCompanyName entity;
        if (ValidateUtil.isEmpty(request.getCnId()))
            entity = new SysCompanyName();
        else {
            entity = baseMapper.selectById(request.getCnId());
            if (entity == null)
                return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        entity.setcId(request.getcId());
        entity.setCnRemark(request.getCnRemark());
        entity.setCnOldname(request.getCnOldname());
        entity.setCnNewname(request.getCnNewname());
        entity.setCnCode(request.getCnCode());
        entity.setCnFile(request.getCnFile());
        if (ValidateUtil.isEmpty(request.getCnId())) {
            entity.setCnId(GenerateUUID.getUUID());
            entity.setCnCreatetime(new Date());
            entity.setCnCreateuName(request.getCnCreateuName());
            entity.setCnCreateuId(request.getCnCreateuId());
            baseMapper.insert(entity);
            SysCompany companyPojo = sysCompanyService.getById(request.getcId());
            if (ValidateUtil.isEmpty(companyPojo.getcNameHistory()))
                companyPojo.setcNameHistory(request.getCnNewname());
            else
                companyPojo.setcNameHistory(companyPojo.getcNameHistory() + "；" + request.getCnNewname());
            companyPojo.setcName(request.getCnNewname());
            sysCompanyService.updateById(companyPojo);
            sysFileAnnexService.addFormRefreshRelationFile(request.getCnFile(), entity.getCnId());
        } else {
            //暂时不能修改
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse DeleteCompanyName(CompanyNameDto request) {
        SysCompanyName entity = baseMapper.selectById(request.getCnId());
        if (entity == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        else
            baseMapper.deleteById(request.getCnId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getCompanyNameUsedBeforeListById(String cId) {
        QueryWrapper<SysCompanyName> wrapper = new QueryWrapper<>();
        wrapper.eq("C_Id", cId);
        wrapper.orderByDesc("CN_CreateTime");
        List<SysCompanyName> datas = baseMapper.selectList(wrapper);
        SysCompany companyPojo = sysCompanyService.getById(cId);
        Map<String, Object> map = new HashMap<>();
        map.put("company", companyPojo);
        map.put("changeNameList", datas);
        return BaseResponse.ok(map);
    }
}
