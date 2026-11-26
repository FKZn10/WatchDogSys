package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUserResponse;
import com.jpmsoft.projectProducer.mapper.project.PreProjectOrgStructUserChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUserChange;
import com.jpmsoft.projectProducer.service.project.IPreProjectOrgStructUserChangeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目组织机构的单位人员变更(PreProjectOrgStructUserChange)表服务实现类
 *
 * @author c
 * @since 2024-12-11 17:39:12
 */
@Service
public class PreProjectOrgStructUserChangeServiceImpl extends ServiceImpl<PreProjectOrgStructUserChangeMapper, PreProjectOrgStructUserChange> implements IPreProjectOrgStructUserChangeService {

    @Override
    public List<PreProjectOrgStructUserResponse> getByOrgStructUnitIdList(List<String> orgStructUnitIdList) {
        List<PreProjectOrgStructUserResponse> list =  baseMapper.getByOrgStructUnitChangeIdList(orgStructUnitIdList);
        return list;
    }

    @Override
    public List<PreProjectOrgStructUserResponse> getByOrgStructUnitIdsList(List<String> orgStructUnitIdList, String id) {
        List<PreProjectOrgStructUserResponse> list =  baseMapper.getByOrgStructUnitChangeIdsList(orgStructUnitIdList,id);
        return list;
    }
}
