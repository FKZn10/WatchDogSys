package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecFormDetailMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecFormDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecFormDetail;
import com.jpmsoft.projectProducer.service.secInfo.ISecFormDetailService;
import com.jpmsoft.projectProducer.service.secInfo.ISecFormService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 17:01
 */
@Service
public class SecFormDetailImpl extends ServiceImpl<SecFormDetailMapper, SecFormDetail> implements ISecFormDetailService {

    @Resource
    private ISecFormService secFormService;

    @Override
    public BaseResponse createOrUpdateSecFormDetail(SecFormDetailDTO request) {
        SecFormDetail entity = new SecFormDetail();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(request.getFdId()))
            baseMapper.updateById(entity);
        else {
            entity.setFdId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok(entity.getFdId());
    }

    @Override
    public BaseResponse getSecFormDetailList(SecFormDetailDTO request) {
        QueryWrapper<SecFormDetail> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getFId()))
            wrapper.eq("F_Id", request.getFId());
        if (ValidateUtil.isNotEmpty(request.getFdType()))
            wrapper.eq("Fd_Type", request.getFdType());
        if (ValidateUtil.isNotEmpty(request.getFdState()))
            wrapper.eq("Fd_State", request.getFdState());
        if (ValidateUtil.isNotEmpty(request.getFdContent()))
            wrapper.like("Fd_Content", request.getFdContent());
        if (ValidateUtil.isNotEmpty(request.getFdCode()))
            wrapper.like("Fd_Code", request.getFdCode());
        if (ValidateUtil.isNotEmpty(request.getFdCalculationRule()))
            wrapper.like("Fd_Calculation_Rule", request.getFdCalculationRule());
        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) wrapper.orderByAsc(request.getSortFiled());
            else wrapper.orderByDesc(request.getSortFiled());
        } else wrapper.orderByAsc("Fd_Order");
        IPage<SecFormDetail> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse deleteSecFormDetailById(String id) {
        SecFormDetail entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecFormDetailLatestList(String teId) {
        return BaseResponse.ok(getByFid(secFormService.getSecFormLatestId(teId), "1"));
    }

    @Override
    public void createOrUpdateSecFormDetail(List<SecFormDetailDTO> data, String fid, String teId) {
        deleteByFid(fid);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecFormDetail> entities = data.stream().map(e -> {
                SecFormDetail entity = new SecFormDetail();
                BeanUtils.copyProperties(e, entity);
                entity.setFId(fid);
                entity.setTeId(teId);
                entity.setFdId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteByFid(String fid) {
        QueryWrapper<SecFormDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("F_Id", fid);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecFormDetailDTO> getByFid(String fid, String state) {
        QueryWrapper<SecFormDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("F_Id", fid);
//        if (ValidateUtil.isNotEmpty(state))
//            wrapper.eq("Fd_State", state);
        wrapper.orderByAsc("Fd_Order");
        List<SecFormDetail> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(e -> {
                SecFormDetailDTO d = new SecFormDetailDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<SecFormDetailDTO> getByFids(List<String> fIds) {
        QueryWrapper<SecFormDetail> wrapper = new QueryWrapper<>();
        wrapper.in("F_Id", fIds);
        wrapper.orderByAsc("Fd_Order");
        List<SecFormDetail> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(e -> {
                SecFormDetailDTO d = new SecFormDetailDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return null;
    }
}
