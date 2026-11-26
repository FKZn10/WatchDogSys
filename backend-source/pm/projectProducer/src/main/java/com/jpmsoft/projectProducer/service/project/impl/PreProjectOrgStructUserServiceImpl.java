package com.jpmsoft.projectProducer.service.project.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUserResponse;
import com.jpmsoft.projectProducer.mapper.project.PreProjectOrgStructUserMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUser;
import com.jpmsoft.projectProducer.service.project.IPreProjectOrgStructUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目组织机构的单位人员(PreProjectOrgStructUser)表服务实现类
 *
 * @author c
 * @since 2024-12-11 17:39:12
 */
@Service
public class PreProjectOrgStructUserServiceImpl extends ServiceImpl<PreProjectOrgStructUserMapper, PreProjectOrgStructUser> implements IPreProjectOrgStructUserService {


    @Override
    public List<PreProjectOrgStructUserResponse> getByOrgStructUnitIdList(List<String> orgStructUnitIdList) {
        List<PreProjectOrgStructUserResponse> list =  baseMapper.getByOrgStructUnitIdList(orgStructUnitIdList);
        return list;
    }

    @Override
    public void deletepId(List<String> ids) {
        LambdaQueryWrapper<PreProjectOrgStructUser> queryWrapper  = new LambdaQueryWrapper<>();
        queryWrapper.in(PreProjectOrgStructUser::getOrgStructUnitId,ids);
        baseMapper.delete(queryWrapper);
    }
}
