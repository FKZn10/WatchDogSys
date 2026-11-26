package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysPayFieldMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPayFieldDto;
import com.jpmsoft.userProducer.pojo.entity.SysPayField;
import com.jpmsoft.userProducer.pojo.entity.SysPayTemplate;
import com.jpmsoft.userProducer.pojo.vo.SysPayFieldVo;
import com.jpmsoft.userProducer.service.ISysPayFieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:10
 */
@Service
public class SysPayFieldImpl extends ServiceImpl<SysPayFieldMapper, SysPayField> implements ISysPayFieldService {


    @Override
    public void createOrUpdateSysPayField(List<SysPayFieldDto> datas, SysPayTemplate payTemplate) {
        deleteSysPayFieldByPtId(payTemplate.getId());
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SysPayField> fields = new ArrayList<>();
            fields = datas.stream().map(n -> {
                SysPayField field = new SysPayField();
                BeanUtils.copyProperties(n, field);
                field.setPtId(payTemplate.getId());
                field.setCreateUid(payTemplate.getCreateUid());
                field.setCreateTime(payTemplate.getCreateTime());
                field.setCreateUname(payTemplate.getCreateUname());
                return field;
            }).collect(Collectors.toList());
            saveBatch(fields);
        }
    }

    @Override
    public void deleteSysPayFieldByPtId(String ptId) {
        QueryWrapper<SysPayField> wrapper = new QueryWrapper<>();
        wrapper.eq("Pt_Id", ptId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SysPayFieldVo> getSysPayFieldByTmpId(String ptId) {
        if (ValidateUtil.isEmpty(ptId)) return new ArrayList<>();
        QueryWrapper<SysPayField> wrapper = new QueryWrapper<>();
        wrapper.eq("Pt_Id", ptId);
        wrapper.orderByAsc("Pf_Sort");
        List<SysPayField> entitys = new ArrayList<>();
        List<SysPayFieldVo> datas = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(entitys)) {
            datas = entitys.stream().map(n -> {
                SysPayFieldVo vo = new SysPayFieldVo();
                BeanUtils.copyProperties(n, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return datas;
    }
}
