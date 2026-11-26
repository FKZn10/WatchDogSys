package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.projectProducer.mapper.project.PpOwnerUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PpOwnerUnit;
import com.jpmsoft.projectProducer.service.project.IPpOwnerUnitService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PpOwnerUnitServiceImpl extends ServiceImpl<PpOwnerUnitMapper, PpOwnerUnit> implements IPpOwnerUnitService {
    /**
     * 根据项目id查询项目所有业主单位人员详情
     *
     * @param pId
     * @return
     */
    @Override
    public List<PpOwnerUnitDTO> getPpOwnerUnitByPId(String pId) {
        return baseMapper.getPpOwnerUnitByPId(pId);
    }

    @Override
    public List<PpOwnerUnitDTO> listPpOwnerUnitByPIds(ArrayList<String> pIds) {
        return baseMapper.listPpOwnerUnitByPIds(pIds);
    }
}
