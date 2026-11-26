package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecFormMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecFormDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecFormDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecForm;
import com.jpmsoft.projectProducer.service.secInfo.ISecFormDetailService;
import com.jpmsoft.projectProducer.service.secInfo.ISecFormService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 17:01
 */
@Service
public class SecFormImpl extends ServiceImpl<SecFormMapper, SecForm> implements ISecFormService {
    @Resource
    private ISecFormDetailService secFormDetailService;

    @Override
    public SecForm createOrUpdate(SecFormDTO request) {
        SecForm entity = new SecForm();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(request.getFId())) {
            SecForm old = baseMapper.selectById(request.getFId());
            if (old == null) throw new JPMException(ResMsg.infoNotExist);
            entity.setFCreateTime(old.getFCreateTime());
            entity.setFCreateUname(old.getFCreateUname());
            entity.setFCreateUid(old.getFCreateUid());
            baseMapper.updateById(entity);
        } else {
            entity.setFId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        }
        return entity;
    }

    @Override
    public BaseResponse createOrUpdateSecForm(SecFormDTO request) {
        SecForm entity = createOrUpdate(request);
        secFormDetailService.createOrUpdateSecFormDetail(request.getSecFormDetails(), entity.getFId(),request.getTeId());
        return BaseResponse.ok(entity.getFId());
    }

    @Override
    public BaseResponse getSecFormList(SecFormDTO request) {
        QueryWrapper<SecForm> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotNull(request.getFVersion()))
            wrapper.eq("F_Version", request.getFVersion());
        if (ValidateUtil.isNotEmpty(request.getFType()))
            wrapper.eq("F_Type", request.getFType());
        if (ValidateUtil.isNotEmpty(request.getFState()))
            wrapper.eq("F_State", request.getFState());
        if (ValidateUtil.isNotEmpty(request.getFCreateUname()))
            wrapper.like("F_Create_Uname", request.getFCreateUname());
        if (ValidateUtil.isNotEmpty(request.getFName()))
            wrapper.like("F_Name", request.getFName());
        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) wrapper.orderByAsc(request.getSortFiled());
            else wrapper.orderByDesc(request.getSortFiled());
        } else wrapper.orderByDesc("F_Create_Time");
        IPage<SecForm> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        if (!CollectionUtils.isEmpty(iPage.getRecords())) {
            List<SecFormDetailDTO> details = secFormDetailService.getByFids(iPage.getRecords().stream().map(SecForm::getFId).collect(Collectors.toList()));
            List<SecFormDTO> models = new ArrayList<>();
            for (SecForm d : iPage.getRecords()) {
                SecFormDTO e = new SecFormDTO();
                BeanUtils.copyProperties(d, e);
                if (!CollectionUtils.isEmpty(details))
                    e.setSecFormDetails(details.stream().filter(f -> f.getFId().equals(d.getFId())).collect(Collectors.toList()));
                models.add(e);
            }
            return BaseResponse.ok(models, total.intValue());
        }
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse getSecFormLatest(String teId) {
        QueryWrapper<SecForm> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("F_State", "1");
        wrapper.orderByDesc("F_Create_Time");
        wrapper.last("limit 1");
        SecForm data = baseMapper.selectOne(wrapper);
        if (data != null) {
            SecFormDTO d = new SecFormDTO();
            BeanUtils.copyProperties(data, d);
            d.setSecFormDetails(secFormDetailService.getByFid(d.getFId(), "1"));
            return BaseResponse.ok(d);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecFormById(String id) {
        SecForm entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        SecFormDTO data = new SecFormDTO();
        BeanUtils.copyProperties(entity, data);
        data.setSecFormDetails(secFormDetailService.getByFid(id, null));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse deleteSecFormById(String id) {
        SecForm entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        secFormDetailService.deleteByFid(id);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public String getSecFormLatestId(String teId) {
        QueryWrapper<SecForm> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("F_State", "1");
        wrapper.orderByDesc("F_Create_Time");
        wrapper.last("limit 1");
        SecForm data = baseMapper.selectOne(wrapper);
        if (data != null) return data.getFId();
        return null;
    }
}
