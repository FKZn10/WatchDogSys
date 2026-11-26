package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaNoticeReadRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaNoticeReadMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaNoticeRead;
import com.jpmsoft.projectProducer.service.oa.IOaNoticeReadService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OaNoticeReadImpl extends ServiceImpl<OaNoticeReadMapper, OaNoticeRead> implements IOaNoticeReadService {

    @Override
    public BaseResponse createOaNoticeRead(OaNoticeReadRequest request) {
        OaNoticeRead entity = new OaNoticeRead();
        BeanUtils.copyProperties(request, entity);
        entity.setNrId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        return BaseResponse.ok(entity.getNrId());
    }

    @Override
    public BaseResponse deleteOaNoticeReadByNrId(String nrId) {
        OaNoticeRead entity = baseMapper.selectById(nrId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(nrId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getOaNoticeReadListByNid(String nId, String name ,String isRead) {
        Map<String,Object> map = new HashMap();
        QueryWrapper<OaNoticeRead> wrapper = new QueryWrapper<>();
        wrapper.eq("N_Id", nId);
        if (!ValidateUtil.isEmpty(name))
            wrapper.like("Nr_Uname", name);
        if (!ValidateUtil.isEmpty(isRead))
            wrapper.eq("is_read", isRead);
        List<OaNoticeRead> list = baseMapper.selectList(wrapper);
        QueryWrapper<OaNoticeRead> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("N_Id", nId);
        Long all = baseMapper.selectCount(wrapper1);
        wrapper1.eq("is_read","1");
        Long isReadNum = baseMapper.selectCount(wrapper1);

        map.put("isRead",isReadNum);
        map.put("isNotRead",all-isReadNum);
        map.put("list",list);
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse getOaNoticeReadList(OaNoticeReadRequest request) {
        QueryWrapper<OaNoticeRead> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (!ValidateUtil.isEmpty(request.getnId()))
            wrapper.eq("N_Id", request.getnId());
        if (!ValidateUtil.isEmpty(request.getNrUname()))
            wrapper.eq("Nr_Uname", request.getNrUname());
        if (!ValidateUtil.isEmpty(request.getIsRead()))
            wrapper.eq("is_read", request.getIsRead());
        if (!ValidateUtil.isEmpty(request.getSortFiled()) && !ValidateUtil.isEmpty(request.getSortType())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("Nr_Time");
        IPage<OaNoticeRead> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public Boolean havingOaNoticeReadListByNid(String nId) {
        QueryWrapper<OaNoticeRead> wrapper = new QueryWrapper<>();
        wrapper.eq("N_Id", nId);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }

    @Override
    public BaseResponse updateOaNoticeRead(OaNoticeReadRequest request) {
        OaNoticeRead entity = new OaNoticeRead();
        BeanUtils.copyProperties(request, entity);
        entity.setNrTime(new Date());
        LambdaQueryWrapper<OaNoticeRead> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OaNoticeRead::getNId,entity.getNId());
        wrapper.eq(OaNoticeRead::getNrUid,entity.getNrUid());
        baseMapper.update(entity,wrapper);
        return BaseResponse.ok(entity.getNrId());
    }
}
