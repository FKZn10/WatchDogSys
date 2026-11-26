package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.PreProjectDeviceCoordinateRequest;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.projectProducer.mapper.project.PreProjectDeviceCoordinateMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectDeviceCoordinate;
import com.jpmsoft.projectProducer.pojo.vo.project.PreProjectDeviceCoordinateVo;
import com.jpmsoft.projectProducer.service.project.IPreProjectDeviceCoordinateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: lzh
 */

@Slf4j
@Service
public class PreProjectDeviceCoordinateServiceImpl extends ServiceImpl<PreProjectDeviceCoordinateMapper, PreProjectDeviceCoordinate>
        implements IPreProjectDeviceCoordinateService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrUpdate(List<PreProjectDeviceCoordinate> preProjectDeviceCoordinateList) {
        if (CollUtil.isEmpty(preProjectDeviceCoordinateList)) {
            return;
        }
        // 删除项目下的所有点
        List<String> projectIdList = preProjectDeviceCoordinateList.stream().map(PreProjectDeviceCoordinate::getProjectId).collect(Collectors.toList());
        remove(new LambdaQueryWrapper<PreProjectDeviceCoordinate>()
                .in(PreProjectDeviceCoordinate::getProjectId,projectIdList));
        saveOrUpdateBatch(preProjectDeviceCoordinateList);
    }

    @Override
    public List<PreProjectDeviceCoordinateVo> getList(PreProjectDeviceCoordinateRequest request) {
        Map<String,Object> params=new HashMap<>(16);
        params.put("pId",request.getPId());
        params.put("projectStatus",request.getProjectStatus());
        params.put("eachDistrictList",request.getEachDistrictList());
        List<PreProjectDeviceCoordinate> preProjectDeviceCoordinateList = baseMapper.getList(params);
        List<PreProjectDeviceCoordinateVo> list=new ArrayList<>();
        if(CollUtil.isNotEmpty(preProjectDeviceCoordinateList)){
            list=BeanUtils.copyList(preProjectDeviceCoordinateList,PreProjectDeviceCoordinateVo.class);
        }
        return list;
    }

    @Override
    public Page<PreProjectDeviceCoordinate> getPage(Page<Object> page) {
        return baseMapper.getPageDistinctProject(page);
    }

}
