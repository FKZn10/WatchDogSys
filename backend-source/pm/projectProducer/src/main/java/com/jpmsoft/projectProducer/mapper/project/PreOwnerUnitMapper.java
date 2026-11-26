package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.project.PreOwnerUnitDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreOwnerUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PreOwnerUnitMapper extends BaseMapper<PreOwnerUnit> {
    /**
     * 获取业主单位人员详情列表
     *
     * @param pprId
     * @return
     */
    List<PreOwnerUnitDTO> getPreOwnerUnitByPprId(String pprId);
}
