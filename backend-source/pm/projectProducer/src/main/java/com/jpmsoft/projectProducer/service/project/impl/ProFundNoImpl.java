package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.ProFundNoDto;
import com.jpmsoft.common.model.response.Project.ProFundNoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProFundNoMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProFundNo;
import com.jpmsoft.projectProducer.service.project.IProFundNoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/20 14:51
 */
@Service
public class ProFundNoImpl extends ServiceImpl<ProFundNoMapper, ProFundNo> implements IProFundNoService {
    @Override
    public void createOrUpdateProFundNo(List<ProFundNoDto> datas, String pId) {
        delProFundNoByPid(pId);
        if (ValidateUtil.isNotEmpty(datas)) {
            Date now = new Date();
            List<ProFundNo> entitys = datas.stream().map(fundNo -> {
                ProFundNo entity = new ProFundNo();
                BeanUtils.copyProperties(fundNo, entity);
                entity.setId(ValidateUtil.isEmpty(fundNo.getId()) ? GenerateUUID.getUUID() : fundNo.getId());
                entity.setCreateTime(ValidateUtil.isEmpty(fundNo.getCreateTime()) ? now : fundNo.getCreateTime());
                entity.setPId(pId);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delProFundNoByPid(String pId) {
        QueryWrapper<ProFundNo> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ProFundNoVo> getProFundNoByPid(String pId) {
        QueryWrapper<ProFundNo> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        List<ProFundNo> entitys = baseMapper.selectList(wrapper);
        List<ProFundNoVo> datas = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(entitys))
            datas = entitys.stream().map(entity -> {
                ProFundNoVo data = new ProFundNoVo();
                BeanUtils.copyProperties(entity, data);
                return data;
            }).collect(Collectors.toList());
        return datas;
    }
}
