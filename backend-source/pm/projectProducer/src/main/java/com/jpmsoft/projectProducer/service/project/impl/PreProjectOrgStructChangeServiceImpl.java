package com.jpmsoft.projectProducer.service.project.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.projectProducer.mapper.project.PreProjectOrgStructChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructChange;
import com.jpmsoft.projectProducer.pojo.vo.project.PreProjectOrgStructChangeVo;
import com.jpmsoft.projectProducer.service.project.IPreProjectOrgStructChangeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目组织机构（变更表）
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
@Service
public class PreProjectOrgStructChangeServiceImpl extends ServiceImpl<PreProjectOrgStructChangeMapper, PreProjectOrgStructChange> implements IPreProjectOrgStructChangeService {

    @Override
    public BaseResponse getDatapId(String pId, int page, int size) {
        IPage<PreProjectOrgStructChangeVo> data = baseMapper.selectData(new Page<>(page, size), pId);
        return BaseResponse.ok(data.getRecords(),(int)data.getTotal());
    }

    @Override
    public PreProjectOrgStructResponse getPreProjectOrgStructChangeById(String id) {
        return baseMapper.getPreProjectOrgStructChangeById(id);
    }

    @Override
    public List<PreProjectOrgStructChange> getdataBypId(String pId) {
        LambdaQueryWrapper<PreProjectOrgStructChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreProjectOrgStructChange::getPId,pId);
        return baseMapper.selectList(queryWrapper);
    }
}
