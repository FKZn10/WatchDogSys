package com.jpmsoft.projectProducer.service.project.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUnitResponse;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUserResponse;
import com.jpmsoft.projectProducer.mapper.project.PreProjectOrgStructUnitChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructChange;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUnitChange;
import com.jpmsoft.projectProducer.service.project.IPreProjectOrgStructChangeService;
import com.jpmsoft.projectProducer.service.project.IPreProjectOrgStructUnitChangeService;
import com.jpmsoft.projectProducer.service.project.IPreProjectOrgStructUserChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目组织机构单位变更表
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
@Service
public class PreProjectOrgStructUnitChangeServiceImpl extends ServiceImpl<PreProjectOrgStructUnitChangeMapper, PreProjectOrgStructUnitChange> implements IPreProjectOrgStructUnitChangeService {


    @Autowired
    private IPreProjectOrgStructUserChangeService preProjectOrgStructUserChangeService;

    @Autowired
    private IPreProjectOrgStructChangeService preProjectOrgStructChangeService;

    @Override
    public List<PreProjectOrgStructUnitResponse> getByOrgStructId(String id) {
        PreProjectOrgStructResponse by = preProjectOrgStructChangeService.getPreProjectOrgStructChangeById(id);
        List<PreProjectOrgStructChange> data = preProjectOrgStructChangeService.getdataBypId(by.getPId());
        List<String> ids = data.stream().map(e -> e.getId()).collect(Collectors.toList());

//        List<PreProjectOrgStructUnitResponse> list = baseMapper.getByOrgStructChangeId(id);
        List<PreProjectOrgStructUnitResponse> list = baseMapper.getByOrgStructChangeIds(ids);

        if (CollectionUtil.isNotEmpty(list)) {
            List<String> orgStructUnitIdList = list.stream().map(item -> item.getId()).collect(Collectors.toList());
//            List<PreProjectOrgStructUserResponse> userList = preProjectOrgStructUserChangeService.getByOrgStructUnitIdList(orgStructUnitIdList);
            List<PreProjectOrgStructUserResponse> userList = preProjectOrgStructUserChangeService.getByOrgStructUnitIdsList(orgStructUnitIdList, id);
            for (PreProjectOrgStructUnitResponse structUnit : list) {
                List<PreProjectOrgStructUserResponse> userConllectList = userList.stream().filter(item -> item.getOrgStructUnitChangeId().equals(structUnit.getId()) && item.getChangeType().equals(id)).collect(Collectors.toList());
                structUnit.setPreProjectOrgStructUserList(userConllectList);
            }
        }
        return list;
    }
}
