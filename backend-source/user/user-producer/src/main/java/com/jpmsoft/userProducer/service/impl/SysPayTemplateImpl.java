package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPayTemplateMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPayTemplateDto;
import com.jpmsoft.userProducer.pojo.entity.SysPayTemplate;
import com.jpmsoft.userProducer.pojo.vo.SysPayTemplateVo;
import com.jpmsoft.userProducer.service.ISysPayTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:09
 */
@Service
public class SysPayTemplateImpl extends ServiceImpl<SysPayTemplateMapper, SysPayTemplate> implements ISysPayTemplateService {


    @Override
    public BaseResponse createOrUpdateSysPayTemplate(SysPayTemplateDto request) {
        SysPayTemplate entity = new SysPayTemplate();
        BeanUtils.copyProperties(request, entity);
        Date now = new Date();
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setCreateTime(now);
            entity.setId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            SysPayTemplate old = baseMapper.selectById(request.getId());
            if (ValidateUtil.isNull(old)) return BaseResponse.fail(BaseMsg.infoNotExist);
            entity.setCreateUid(old.getCreateUid());
            entity.setCreateTime(old.getCreateTime());
            entity.setCreateUname(old.getCreateUname());
            entity.setUpdateTime(now);
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSysPayTemplateList(SysPayTemplateDto request) {
        QueryWrapper<SysPayTemplate> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotNull(request.getPtStatus()))
            wrapper.eq("Pt_Status", request.getPtStatus());
        if (ValidateUtil.isNotEmpty(request.getPtPayType()))
            wrapper.eq("Pt_Pay_Type", request.getPtPayType());
        if (ValidateUtil.isNotEmpty(request.getCreateUid()))
            wrapper.eq("Create_Uid", request.getCreateUid());
        if (ValidateUtil.isNotEmpty(request.getPtName()))
            wrapper.like("Pt_Name", request.getPtName());
        if (ValidateUtil.isNotEmpty(request.getPtCode()))
            wrapper.like("Pt_Code", request.getPtCode());
        if (ValidateUtil.isNotEmpty(request.getPtConType()))
            wrapper.like("Pt_Con_Type", request.getPtConType());
        if (ValidateUtil.isNotEmpty(request.getPtExplain()))
            wrapper.like("Pt_Explain", request.getPtExplain());
        if (ValidateUtil.isNotEmpty(request.getCreateUname()))
            wrapper.like("Create_Uname", request.getCreateUname());
        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) {
                wrapper.orderByAsc(request.getSortFiled());
            } else {
                wrapper.orderByDesc(request.getSortFiled());
            }
        } else
            wrapper.orderByDesc("Create_Time");
        IPage<SysPayTemplate> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long longTotal = iPage.getTotal();
        List<SysPayTemplateVo> vos = new ArrayList<>();
        for (SysPayTemplate record : iPage.getRecords()) {
            SysPayTemplateVo vo = new SysPayTemplateVo();
            BeanUtils.copyProperties(record, vo);
            vos.add(vo);
        }
        return BaseResponse.ok(vos, longTotal.intValue());
    }

    @Override
    public BaseResponse getSysPayTemplateById(String id) {
        SysPayTemplate entity = baseMapper.selectById(id);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(BaseMsg.infoNotExist);
        return BaseResponse.ok(assembleVo(entity));
    }

    @Override
    public BaseResponse deleteSysPayTemplateById(String id) {
        SysPayTemplate entity = baseMapper.selectById(id);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }


    private SysPayTemplateVo assembleVo(SysPayTemplate entity) {
        SysPayTemplateVo vo = new SysPayTemplateVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
